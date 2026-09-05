#!/usr/bin/env python3
import os
import re
from pathlib import Path
from typing import Set, Dict, List, Tuple
from collections import defaultdict

def clean_java_comments(content: str) -> str:
    """Удаляет Java-комментарии из содержимого файла"""
    # Удаляем многострочные комментарии /* ... */
    content = re.sub(r'/\*.*?\*/', '', content, flags=re.DOTALL)
    # Удаляем однострочные комментарии // ...
    content = re.sub(r'//.*?$', '', content, flags=re.MULTILINE)
    return content

def find_all_java_classes(project_root: str) -> Dict[str, str]:
    """
    Находит все Java-классы в проекте.
    Возвращает словарь {имя_класса: путь_к_файлу}
    """
    classes = {}

    # Папки для игнорирования
    ignore_dirs = ['target', 'build', '.git', 'node_modules', 'out', 'gradle', '.idea', '.gradle', 'classes', 'generated']

    for root, dirs, files in os.walk(project_root):
        # Пропускаем системные и временные папки
        skip = False
        for ignore in ignore_dirs:
            if ignore in root.split(os.sep):
                skip = True
                break
        if skip:
            continue

        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        content = clean_java_comments(content)

                        # Ищем объявление класса/интерфейса/перечисления
                        pattern = r'(?:public|private|protected|abstract|final)?\s*(?:class|interface|enum)\s+([A-Za-z_][A-Za-z0-9_]*)'
                        matches = re.finditer(pattern, content)

                        for match in matches:
                            class_name = match.group(1)
                            # Добавляем только если это не вложенный класс (упрощенная проверка)
                            if class_name not in classes:
                                classes[class_name] = file_path

                except (UnicodeDecodeError, PermissionError) as e:
                    continue

    return classes

def find_class_references(project_root: str, class_name: str, class_file_path: str) -> List[Tuple[str, int]]:
    """
    Ищет ссылки на указанный класс во всем проекте.
    Возвращает список кортежей (путь_к_файлу, номер_строки)
    """
    references = []
    extensions = ['.java', '.kt', '.groovy']

    # Паттерны для поиска использования класса
    patterns = [
        rf'import\s+.*\.{class_name}\s*;',
        rf'\b{class_name}\b',
        rf'@\s*{class_name}\b',
        rf'(?:extends|implements)\s+{class_name}\b',
        rf'<[^>]*{class_name}[^>]*>',
        rf'new\s+{class_name}\s*\(',
        rf'{class_name}\s*\.',
    ]

    ignore_dirs = ['target', 'build', '.git', 'node_modules', 'out', 'gradle', '.idea', '.gradle', 'classes', 'generated']

    for root, dirs, files in os.walk(project_root):
        skip = False
        for ignore in ignore_dirs:
            if ignore in root.split(os.sep):
                skip = True
                break
        if skip:
            continue

        for file in files:
            if any(file.endswith(ext) for ext in extensions):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        lines = f.readlines()

                    for line_num, line in enumerate(lines, 1):
                        clean_line = clean_java_comments(line)
                        if not clean_line.strip():
                            continue

                        # Проверяем каждый паттерн
                        for pattern in patterns:
                            if re.search(pattern, clean_line):
                                # Проверяем, не является ли это объявлением класса
                                if file_path == class_file_path:
                                    if re.search(rf'(?:class|interface|enum)\s+{class_name}\b', clean_line):
                                        continue
                                references.append((file_path, line_num))
                                break

                except (UnicodeDecodeError, PermissionError):
                    continue

    return references

def find_unused_classes(
    project_root: str,
    blacklist_file: str = 'class_blacklist.txt',
    stats_file: str = 'class_stats.txt',
    unused_file: str = 'unused_classes.txt'
) -> None:
    """
    Основная функция для поиска неиспользуемых классов
    """
    if not os.path.exists(project_root):
        print(f"ERROR: Project root not found: {project_root}")
        return

    print(f"Analyzing project: {project_root}")
    print("=" * 60)

    # Загружаем черный список
    blacklist = set()
    if os.path.exists(blacklist_file):
        with open(blacklist_file, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if line and not line.startswith('#'):
                    blacklist.add(line)
    else:
        with open(blacklist_file, 'w', encoding='utf-8') as f:
            f.write("# Blacklist for Java classes\n")
            f.write("# Add one class name per line\n")
            f.write("# Lines starting with '#' are ignored\n")
            f.write("# Example:\n")
            f.write("# Main\n")
            f.write("# Application\n")

    print(f"Blacklist entries: {len(blacklist)}")

    # Находим все классы в проекте
    print("Scanning for Java classes...")
    all_classes = find_all_java_classes(project_root)
    print(f"Total Java classes found: {len(all_classes)}")

    if not all_classes:
        print("No Java classes found! Check the project path.")
        return

    # Проверяем использование каждого класса
    print("\nChecking class usage...")
    print("   (This may take some time for large projects)")

    used_classes = set()
    unused_classes = set()
    class_references = {}
    checked = 0

    for class_name, file_path in all_classes.items():
        checked += 1
        if checked % 50 == 0:
            print(f"   Processed {checked}/{len(all_classes)} classes...")

        # Пропускаем классы из черного списка
        if class_name in blacklist:
            continue

        # Проверяем, используется ли класс
        references = find_class_references(project_root, class_name, file_path)

        if references:
            used_classes.add(class_name)
            class_references[class_name] = references
        else:
            unused_classes.add(class_name)

    print(f"\nUsed classes: {len(used_classes)}")
    print(f"Unused classes: {len(unused_classes)}")

    # Сохраняем статистику
    with open(stats_file, 'w', encoding='utf-8') as f:
        f.write(f"Java Class Usage Statistics\n")
        f.write("=" * 60 + "\n")
        f.write(f"Project: {project_root}\n")
        f.write(f"Total classes: {len(all_classes)}\n")
        f.write(f"Blacklist: {len(blacklist)}\n")
        f.write(f"Used classes: {len(used_classes)}\n")
        f.write(f"Unused classes: {len(unused_classes)}\n")
        f.write("\n" + "=" * 60 + "\n")
        f.write("Unused Classes Details:\n")
        for class_name in sorted(unused_classes):
            f.write(f"  - {class_name} ({all_classes[class_name]})\n")

    # Сохраняем список неиспользуемых классов
    with open(unused_file, 'w', encoding='utf-8') as f:
        if unused_classes:
            f.write("# Unused Java Classes\n")
            f.write("# Format: class_name -> file_path\n")
            f.write("# " + "=" * 50 + "\n\n")
            for class_name in sorted(unused_classes):
                f.write(f"{class_name} -> {all_classes[class_name]}\n")
        else:
            f.write("No unused classes found!\n")

    # Выводим результаты
    if unused_classes:
        print(f"\nFound {len(unused_classes)} unused classes:")
        # Показываем первые 20
        sorted_unused = sorted(unused_classes)
        for class_name in sorted_unused[:20]:
            print(f"   - {class_name}")
        if len(unused_classes) > 20:
            print(f"   ... and {len(unused_classes) - 20} more")
        print(f"\nDetailed list saved to: {unused_file}")
        print(f"Statistics saved to: {stats_file}")

        # Сохраняем детальные ссылки для классов, которые используются
        refs_file = 'class_references.txt'
        with open(refs_file, 'w', encoding='utf-8') as f:
            f.write("Class References Details\n")
            f.write("=" * 60 + "\n\n")
            for class_name in sorted(class_references.keys()):
                f.write(f"Class: {class_name}\n")
                f.write(f"  References found: {len(class_references[class_name])}\n")
                f.write("  Locations:\n")
                for ref_path, line_num in class_references[class_name]:
                    # Относительный путь для удобства
                    try:
                        rel_path = os.path.relpath(ref_path, project_root)
                    except ValueError:
                        rel_path = ref_path
                    f.write(f"    - {rel_path}:{line_num}\n")
                f.write("\n")
        print(f"References details saved to: {refs_file}")
    else:
        print("\nAll classes are used in the project!")
        print(f"Statistics saved to: {stats_file}")

def main():
    # Настройка параметров - ИЗМЕНИТЕ ЭТОТ ПУТЬ НА ВАШ
    PROJECT_ROOT = "/home/evgenwargold/IdeaProjects/GT5-Unofficial-GregTechNightmare"
    BLACKLIST_FILE = "class_blacklist.txt"
    STATS_FILE = "class_stats.txt"
    UNUSED_FILE = "unused_classes.txt"

    find_unused_classes(
        PROJECT_ROOT,
        BLACKLIST_FILE,
        STATS_FILE,
        UNUSED_FILE
    )

if __name__ == "__main__":
    main()
