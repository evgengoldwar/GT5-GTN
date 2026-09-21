package gregtech.common.MaterialSystem.Api;

public enum MaterialType {

    INGOT("ingot", "Items", Kind.ITEM),
    PLATE("plate", "Items", Kind.ITEM),
    BLOCK("block", "Blocks", Kind.BLOCK),
    CASING("casing", "Blocks", Kind.BLOCK),
    FLUID("fluid", "Fluids", Kind.FLUID);

    public enum Kind {
        ITEM,
        BLOCK,
        FLUID
    }

    private final String textureName;
    private final String folder;
    private final Kind kind;

    MaterialType(String textureName, String folder, Kind kind) {
        this.textureName = textureName;
        this.folder = folder;
        this.kind = kind;
    }

    public String getTextureName() {
        return textureName;
    }

    public String getFolder() {
        return folder;
    }

    public Kind getKind() {
        return kind;
    }
}
