package gregtech.client.iconContainers;

import static gregtech.api.util.GTUtility.getMissingNo;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import gregtech.api.interfaces.IIconContainer;

public class GTIconContainer implements IIconContainer {

    private final IIcon icon;
    private final IIcon overlay;
    private final ResourceLocation textureFile;

    public GTIconContainer(IIcon icon) {
        this(icon, null);
    }

    public GTIconContainer(IIcon icon, IIcon overlay) {
        this.icon = icon;
        this.overlay = overlay;
        this.textureFile = resolveTextureFile(icon);
    }

    @Override
    public IIcon getIcon() {
        return icon != null ? icon : getMissingNo();
    }

    @Override
    public IIcon getOverlayIcon() {
        return overlay != null ? overlay : getMissingNo();
    }

    @Override
    public ResourceLocation getTextureFile() {
        return textureFile;
    }

    private static ResourceLocation resolveTextureFile(IIcon icon) {
        if (icon == null) {
            return null;
        }

        String iconName = icon.getIconName();
        if (iconName == null) {
            return null;
        }

        int colon = iconName.indexOf(":");
        if (colon < 0) {
            return new ResourceLocation("minecraft", "textures/" + iconName + ".png");
        }
        String domain = iconName.substring(0, colon);
        String path = iconName.substring(colon + 1);
        return new ResourceLocation(domain, "textures/" + path + ".png");
    }
}
