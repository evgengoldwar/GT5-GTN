package gregtech.common.MaterialSystem.Api;

public class MaterialUtils {

    public static final int MAX_MATERIAL_META = 32_767;

    public static String getTextureItemPath(MaterialType materialType) {
        return "gregtech:MaterialSystems/Items/" + materialType.getTextureName() + ".png";
    }

    public static String getTextureBlockPath(MaterialType materialType) {
        return "gregtech:MaterialSystems/Blocks/" + materialType.getTextureName() + ".png";
    }
}
