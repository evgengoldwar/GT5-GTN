import os
import re
from pathlib import Path
from typing import Set, Dict, List, Tuple
from collections import defaultdict

def clean_java_comments(content: str) -> str:
    content = re.sub(r'/\*.*?\*/', '', content, flags=re.DOTALL)
    content = re.sub(r'//.*?$', '', content, flags=re.MULTILINE)
    return content

def find_enum_elements(enum_file_path: str) -> Set[str]:
    with open(enum_file_path, 'r', encoding='utf-8') as file:
        content = file.read()

    content = clean_java_comments(content)

    enum_match = re.search(
        r'\benum\s+[A-Za-z_][A-Za-z0-9_]*'
        r'(?:\s+implements\s+[^{]+)?\s*\{',
        content
    )

    if not enum_match:
        print("ERROR: Enum declaration not found")
        return set()

    start_pos = enum_match.end()
    end_pos = content.find(';', start_pos)

    if end_pos == -1:
        print("ERROR: ';' after enum list not found")
        return set()

    enum_body = content[start_pos:end_pos]
    enum_elements = set()

    pattern = re.compile(
        r'^\s*([A-Za-z_][A-Za-z0-9_]*)\s*,?',
        re.MULTILINE
    )

    for match in pattern.finditer(enum_body):
        element = match.group(1)
        if element:
            enum_elements.add(element)

    return enum_elements

def get_enum_name_from_file(enum_file_path: str) -> str:
    with open(enum_file_path, 'r', encoding='utf-8') as file:
        content = file.read()
        content = clean_java_comments(content)

        pattern = r'enum\s+([A-Za-z_][A-Za-z0-9_]*)'
        match = re.search(pattern, content)
        if match:
            return match.group(1)

    return Path(enum_file_path).stem

def load_blacklist(blacklist_file: str) -> Set[str]:
    blacklist = set()

    if os.path.exists(blacklist_file):
        with open(blacklist_file, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if line and not line.startswith('#'):
                    blacklist.add(line)
    else:
        with open(blacklist_file, 'w', encoding='utf-8') as f:
            f.write("# Blacklist for Enum elements\n")
            f.write("# Add one element per line\n")
            f.write("# Lines starting with '#' are ignored\n")

    return blacklist

def load_set_blacklist(set_blacklist_file: str) -> Set[str]:
    set_blacklist = set()

    if os.path.exists(set_blacklist_file):
        with open(set_blacklist_file, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if line and not line.startswith('#'):
                    set_blacklist.add(line)
    else:
        with open(set_blacklist_file, 'w', encoding='utf-8') as f:
            f.write("# Blacklist for .set() and register() calls\n")
            f.write("# Elements with these calls will be ignored\n")
            f.write("# Add one element per line\n")
            f.write("# Lines starting with '#' are ignored\n")

    return set_blacklist

def find_enum_references_with_locations(
    project_root: str,
    enum_name: str,
    enum_elements: Set[str]
) -> Dict[str, List[Tuple[str, int]]]:
    used_elements = defaultdict(list)
    extensions = ['.java', '.kt', '.groovy']

    enum_patterns = [
        (rf'{enum_name}\.([A-Za-z_][A-Za-z0-9_]*)', 'direct'),
        (rf'import\s+static\s+{enum_name}\.([A-Za-z_][A-Za-z0-9_]*)', 'static_import'),
        (rf'case\s+([A-Za-z_][A-Za-z0-9_]*)\s*[:]', 'case'),
        (rf'\.equals\s*\(\s*([A-Za-z_][A-Za-z0-9_]*)\s*\)', 'equals'),
        (rf'==\s*([A-Za-z_][A-Za-z0-9_]*)', 'equals_operator'),
        (rf'valueOf\s*\(\s*"([A-Za-z_][A-Za-z0-9_]*)"\s*\)', 'valueof'),
        (rf'get\s*\(\s*"([A-Za-z_][A-Za-z0-9_]*)"\s*\)', 'get'),
        (rf'containsKey\s*\(\s*"([A-Za-z_][A-Za-z0-9_]*)"\s*\)', 'containskey'),
    ]

    static_imported_elements = set()

    for root, dirs, files in os.walk(project_root):
        if any(ignore in root for ignore in ['target', 'build', '.git', 'node_modules', 'out', 'gradle']):
            continue

        for file in files:
            if any(file.endswith(ext) for ext in extensions):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        lines = f.readlines()

                    for line_num, line in enumerate(lines, 1):
                        clean_line = re.sub(r'//.*$', '', line)
                        clean_line = re.sub(r'/\*.*?\*/', '', clean_line)

                        static_import_match = re.search(
                            rf'import\s+static\s+{enum_name}\.([A-Za-z_][A-Za-z0-9_]*)',
                            clean_line
                        )
                        if static_import_match:
                            static_imported_elements.add(static_import_match.group(1))
                            used_elements[static_import_match.group(1)].append((file_path, line_num))
                            continue

                        for pattern, _ in enum_patterns[:1]:
                            matches = re.finditer(pattern, clean_line)
                            for match in matches:
                                element = match.group(1)
                                if element in enum_elements:
                                    used_elements[element].append((file_path, line_num))

                        if static_imported_elements:
                            for element in static_imported_elements:
                                if element in enum_elements:
                                    if re.search(rf'\b{element}\b', clean_line):
                                        if 'ItemList.java' not in file_path:
                                            used_elements[element].append((file_path, line_num))

                        for pattern, pattern_type in enum_patterns[1:]:
                            matches = re.finditer(pattern, clean_line)
                            for match in matches:
                                element = match.group(1)
                                if element in enum_elements:
                                    used_elements[element].append((file_path, line_num))

                except (UnicodeDecodeError, PermissionError):
                    continue

    return dict(used_elements)

def find_elements_with_set_or_register_calls(
    project_root: str,
    enum_name: str,
    enum_elements: Set[str],
    set_blacklist: Set[str]
) -> Set[str]:
    elements_with_set = set()
    extensions = ['.java', '.kt', '.groovy']

    patterns = [
        rf'{enum_name}\.([A-Za-z_][A-Za-z0-9_]*)\.set\s*\(',
        rf'\b([A-Za-z_][A-Za-z0-9_]*)\s*\.set\s*\(',
        rf'register\s*\(\s*[^,]+,\s*(?:{enum_name}\.)?([A-Za-z_][A-Za-z0-9_]*)\s*,',
        rf'register\s*\(\s*[^,]+,\s*(?:{enum_name}\.)?([A-Za-z_][A-Za-z0-9_]*)\s*\)',
    ]

    for root, dirs, files in os.walk(project_root):
        if any(ignore in root for ignore in ['target', 'build', '.git', 'node_modules', 'out', 'gradle']):
            continue

        for file in files:
            if any(file.endswith(ext) for ext in extensions):
                file_path = os.path.join(root, file)
                if 'ItemList.java' in file_path:
                    continue
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        content = clean_java_comments(content)

                        for pattern in patterns:
                            matches = re.finditer(pattern, content)
                            for match in matches:
                                element = match.group(1)
                                if element in enum_elements and element not in set_blacklist:
                                    elements_with_set.add(element)
                except (UnicodeDecodeError, PermissionError):
                    continue

    return elements_with_set

def find_unused_elements(
    enum_file_path: str,
    project_root: str = ".",
    blacklist_file: str = 'blacklist.txt',
    set_blacklist_file: str = 'blacklist_sets.txt',
    stats_file: str = 'stats.txt',
    unused_file: str = 'unused.txt',
    without_sets_file: str = 'without_sets.txt'
) -> None:
    if not os.path.exists(enum_file_path):
        print(f"ERROR: Enum file not found: {enum_file_path}")
        return

    print(f"Analyzing Enum file: {enum_file_path}")
    print("=" * 60)

    enum_name = get_enum_name_from_file(enum_file_path)
    print(f"Enum name: {enum_name}")

    all_elements = find_enum_elements(enum_file_path)
    print(f"Total enum elements: {len(all_elements)}")

    if not all_elements:
        print("ERROR: No enum elements found. Check the file.")
        return

    blacklist = load_blacklist(blacklist_file)
    print(f"Blacklist entries: {len(blacklist)}")

    set_blacklist = load_set_blacklist(set_blacklist_file)
    print(f"Set blacklist entries: {len(set_blacklist)}")

    elements_to_check = all_elements - blacklist
    print(f"Elements to check (excluding blacklist): {len(elements_to_check)}")
    print("=" * 60)

    print("Searching for enum references in project...")
    print("   (This may take some time for large projects)")

    used_elements_with_locations = find_enum_references_with_locations(
        project_root, enum_name, elements_to_check
    )

    used_elements = set(used_elements_with_locations.keys())
    print(f"Used elements: {len(used_elements)}")

    print("Searching for .set() and register() calls...")
    elements_with_set = find_elements_with_set_or_register_calls(
        project_root, enum_name, elements_to_check, set_blacklist
    )
    print(f"Elements with .set() or register() calls: {len(elements_with_set)}")

    unused_elements = elements_to_check - used_elements

    # Remove unused elements AND set_blacklist elements from without_sets
    without_sets = (elements_to_check - elements_with_set) - unused_elements - set_blacklist

    with open(stats_file, 'w', encoding='utf-8') as f:
        f.write(f"Enum Statistics: {enum_name}\n")
        f.write("=" * 60 + "\n")
        f.write(f"Total elements: {len(all_elements)}\n")
        f.write(f"Blacklist: {len(blacklist)}\n")
        f.write(f"Set blacklist: {len(set_blacklist)}\n")
        f.write(f"Checked elements: {len(elements_to_check)}\n")
        f.write(f"Used elements: {len(used_elements)}\n")
        f.write(f"Unused elements: {len(unused_elements)}\n")
        f.write(f"Elements with .set() or register() calls: {len(elements_with_set)}\n")
        f.write(f"Elements without .set() or register() calls: {len(without_sets)}\n")

    with open(unused_file, 'w', encoding='utf-8') as f:
        if unused_elements:
            for element in sorted(unused_elements):
                f.write(f"{element}\n")

    with open(without_sets_file, 'w', encoding='utf-8') as f:
        if without_sets:
            for element in sorted(without_sets):
                f.write(f"{element}\n")

    if unused_elements:
        sorted_unused = sorted(unused_elements)
        print(f"Found {len(unused_elements)} unused elements")
        print(f"Stats saved to: {stats_file}")
        print(f"Unused list saved to: {unused_file}")
    else:
        print("All checked enum elements are used in the project!")
        print(f"Stats saved to: {stats_file}")
        print(f"Unused list saved to: {unused_file} (empty)")

    if without_sets:
        sorted_without_sets = sorted(without_sets)
        print(f"\nFound {len(without_sets)} elements without .set() or register() calls")
        print(f"Without .set()/register() list saved to: {without_sets_file}")
    else:
        print("All checked elements have .set() or register() calls!")

def main():
    ENUM_FILE = "/home/evgenwargold/IdeaProjects/GT5-Unofficial-GregTechNightmare/src/main/java/gregtech/api/enums/ItemList.java"
    PROJECT_ROOT = "/home/evgenwargold/IdeaProjects/GT5-Unofficial-GregTechNightmare"
    BLACKLIST_FILE = "blacklist.txt"
    SET_BLACKLIST_FILE = "blacklist_sets.txt"
    STATS_FILE = "stats.txt"
    UNUSED_FILE = "unused.txt"
    WITHOUT_SETS_FILE = "without_sets.txt"

    find_unused_elements(
        ENUM_FILE,
        PROJECT_ROOT,
        BLACKLIST_FILE,
        SET_BLACKLIST_FILE,
        STATS_FILE,
        UNUSED_FILE,
        WITHOUT_SETS_FILE
    )

if __name__ == "__main__":
    main()
