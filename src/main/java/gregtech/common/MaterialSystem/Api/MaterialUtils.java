package gregtech.common.MaterialSystem.Api;

public class MaterialUtils {

    public static final int MAX_MATERIAL_META = 32_767;

    public static String getTexturePath(MaterialType materialType) {
        return "gregtech:MaterialSystems/" + materialType.getFolder() + "/" + materialType.getTextureName();
    }

    public static short[] getColorRGBA(int color) {
        return new short[] { (short) ((color >> 16) & 0xFF), (short) ((color >> 8) & 0xFF), (short) (color & 0xFF),
            255 };
    }
}
