package gregtech.GTMaterialSystem.Api;

public class GTMaterialUtils {

    public static final int MAX_MATERIAL_META = 32_767;

    public static String getTextureItemPath(GTMaterialType materialType) {
        return "gregtech:MaterialSystems/Items/" + materialType.getTextureName() + ".png";
    }

    public static String getTextureBlockPath(GTMaterialType materialType) {
        return "gregtech:MaterialSystems/Blocks/" + materialType.getTextureName() + ".png";
    }
}
