package gregtech.common.MaterialSystem.Api;

/**
 * Every kind of object that can be generated from a {@link Material}.
 * <p>
 * Each ITEM type is backed by one {@link ItemMetaMaterial} (meta = material meta), each BLOCK type by one
 * {@link BlockMetaMaterial}, and each FLUID type registers one Forge fluid per material.
 * <p>
 * Textures are looked up as {@code gregtech:MaterialSystems/<folder>/<iconSet>/<textureName>} and fall back to
 * {@code gregtech:MaterialSystems/<folder>/<textureName>} (the "default" icon set).
 */
public enum MaterialType {

    // spotless:off
    // region Items
    DUST         ("dust",         Kind.ITEM,  "dust",        "%s Dust"),
    SMALL_DUST   ("dust_small",   Kind.ITEM,  "dustSmall",   "Small Pile of %s Dust"),
    TINY_DUST    ("dust_tiny",    Kind.ITEM,  "dustTiny",    "Tiny Pile of %s Dust"),
    NUGGET       ("nugget",       Kind.ITEM,  "nugget",      "%s Nugget"),
    INGOT        ("ingot",        Kind.ITEM,  "ingot",       "%s Ingot"),
    HOT_INGOT    ("ingot_hot",    Kind.ITEM,  "ingotHot",    "Hot %s Ingot"),
    GEM          ("gem",          Kind.ITEM,  "gem",         "%s"),
    PLATE        ("plate",        Kind.ITEM,  "plate",       "%s Plate"),
    DOUBLE_PLATE ("plate_double", Kind.ITEM,  "plateDouble", "Double %s Plate"),
    DENSE_PLATE  ("plate_dense",  Kind.ITEM,  "plateDense",  "Dense %s Plate"),
    FOIL         ("foil",         Kind.ITEM,  "foil",        "%s Foil"),
    ROD          ("rod",          Kind.ITEM,  "stick",       "%s Rod"),
    LONG_ROD     ("rod_long",     Kind.ITEM,  "stickLong",   "Long %s Rod"),
    BOLT         ("bolt",         Kind.ITEM,  "bolt",        "%s Bolt"),
    SCREW        ("screw",        Kind.ITEM,  "screw",       "%s Screw"),
    RING         ("ring",         Kind.ITEM,  "ring",        "%s Ring"),
    SPRING       ("spring",       Kind.ITEM,  "spring",      "%s Spring"),
    SMALL_SPRING ("spring_small", Kind.ITEM,  "springSmall", "Small %s Spring"),
    GEAR         ("gear",         Kind.ITEM,  "gearGt",      "%s Gear"),
    SMALL_GEAR   ("gear_small",   Kind.ITEM,  "gearGtSmall", "Small %s Gear"),
    ROTOR        ("rotor",        Kind.ITEM,  "rotor",       "%s Rotor"),
    FINE_WIRE    ("wire_fine",    Kind.ITEM,  "wireFine",    "Fine %s Wire"),
    LENS         ("lens",         Kind.ITEM,  "lens",        "%s Lens"),
    // endregion

    // region Blocks
    BLOCK        ("block",        Kind.BLOCK, "block",       "Block of %s"),
    CASING       ("casing",       Kind.BLOCK, "casing",      "%s Machine Casing"),
    FRAME        ("frame",        Kind.BLOCK, "frameGt",     "%s Frame Box"),
    // endregion

    // region Fluids
    FLUID        ("fluid",        Kind.FLUID, "",            "%s"),
    MOLTEN       ("molten",       Kind.FLUID, "molten",      "Molten %s"),
    GAS          ("gas",          Kind.FLUID, "gas",         "%s Gas"),
    PLASMA       ("plasma",       Kind.FLUID, "plasma",      "%s Plasma");
    // endregion
    // spotless:on

    public enum Kind {

        ITEM("Items"),
        BLOCK("Blocks"),
        FLUID("Fluids");

        private final String folder;

        Kind(String folder) {
            this.folder = folder;
        }

        public String getFolder() {
            return folder;
        }
    }

    private final String textureName;
    private final Kind kind;
    private final String oreDictPrefix;
    private final String localNamePattern;

    MaterialType(String textureName, Kind kind, String oreDictPrefix, String localNamePattern) {
        this.textureName = textureName;
        this.kind = kind;
        this.oreDictPrefix = oreDictPrefix;
        this.localNamePattern = localNamePattern;
    }

    public String getTextureName() {
        return textureName;
    }

    public String getFolder() {
        return kind.getFolder();
    }

    public Kind getKind() {
        return kind;
    }

    /**
     * Ore dictionary prefix, e.g. {@code "bolt"} -> {@code "boltSteel"}. Empty for types that are not registered.
     */
    public String getOreDictPrefix() {
        return oreDictPrefix;
    }

    /**
     * English name pattern, {@code %s} is replaced by the material local name.
     */
    public String getLocalNamePattern() {
        return localNamePattern;
    }

    public String formatLocalName(Material material) {
        return String.format(localNamePattern, material.getLocalName());
    }

    /**
     * Name of the generated fluid, e.g. {@code "steel"}, {@code "molten.steel"}, {@code "plasma.steel"}.
     */
    public String getFluidName(Material material) {
        String base = material.getUnlocalizedName();
        return this == FLUID ? base : textureName + "." + base;
    }

    public boolean isItem() {
        return kind == Kind.ITEM;
    }

    public boolean isBlock() {
        return kind == Kind.BLOCK;
    }

    public boolean isFluid() {
        return kind == Kind.FLUID;
    }
}
