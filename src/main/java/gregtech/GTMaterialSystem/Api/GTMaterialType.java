package gregtech.GTMaterialSystem.Api;

public enum GTMaterialType {

    INGOT("ingot"),
    PLATE("plate"),
    BLOCK("block");

    private final String textureName;

    GTMaterialType(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}
