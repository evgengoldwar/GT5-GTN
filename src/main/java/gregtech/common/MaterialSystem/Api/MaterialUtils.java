package gregtech.common.MaterialSystem.Api;

public class MaterialUtils {

    public static final int MAX_MATERIAL_META = 32_767;

    public static final String DEFAULT_ICON_SET = "default";

    public static String getTexturePath(MaterialType materialType) {
        return "gregtech:MaterialSystems/" + materialType.getFolder() + "/" + materialType.getTextureName();
    }

    /**
     * Texture of a type for a specific icon set. The {@link #DEFAULT_ICON_SET} lives directly in the type folder.
     */
    public static String getTexturePath(MaterialType materialType, String iconSet) {
        if (iconSet == null || DEFAULT_ICON_SET.equals(iconSet)) {
            return getTexturePath(materialType);
        }
        return "gregtech:MaterialSystems/" + materialType.getFolder()
            + "/"
            + iconSet
            + "/"
            + materialType.getTextureName();
    }

    public static short[] getColorRGBA(int color) {
        return new short[] { (short) ((color >> 16) & 0xFF), (short) ((color >> 8) & 0xFF), (short) (color & 0xFF),
            255 };
    }
}
