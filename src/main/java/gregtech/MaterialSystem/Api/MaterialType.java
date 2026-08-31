package gregtech.MaterialSystem.Api;

public enum MaterialType {

    INGOT("ingot"),
    PLATE("plate"),
    BLOCK("block");

    private final String textureName;

    MaterialType(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}
