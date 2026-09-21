package gregtech.common.MaterialSystem.Api;

public enum MaterialType {

    INGOT("ingot"),
    PLATE("plate"),
    BLOCK("block"),
    CASING("casing"),
    FLUID("fluid");

    private final String textureName;

    MaterialType(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}
