package gregtech.client.iconContainers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import gregtech.api.interfaces.IIconContainer;

public class IconContainer {

    public static IIcon MISSING_ICON;
    public static IIcon INVISIBLE_ICON;

    public static IIconContainer MISSING_CONTAINER;
    public static IIconContainer INVISIBLE_CONTAINER;
    public static IIconContainer MISSING_INVISIBLE_CONTAINER;

    public static void init() {
        // spotless:off
        MISSING_ICON = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        INVISIBLE_ICON = new IIcon() {
            @Override public int    getIconWidth()                      { return 1; }
            @Override public int    getIconHeight()                     { return 1; }
            @Override public float  getMinU()                           { return 0.999f; }
            @Override public float  getMaxU()                           { return 1.0f; }
            @Override public float  getInterpolatedU(double p_94214_1_) { return 1.0f; }
            @Override public float  getMinV()                           { return 0.999f; }
            @Override public float  getMaxV()                           { return 1.0f; }
            @Override public float  getInterpolatedV(double p_94207_1_) { return 1.0f; }
            @Override public String getIconName()                       { return "invisible"; }
        };

        MISSING_CONTAINER = new IIconContainer() {
            @Override public IIcon            getIcon()        { return MISSING_ICON; }
            @Override public IIcon            getOverlayIcon() { return MISSING_ICON; }
            @Override public ResourceLocation getTextureFile() { return null; }
        };

        INVISIBLE_CONTAINER = new IIconContainer() {
            @Override public IIcon            getIcon()        { return INVISIBLE_ICON; }
            @Override public IIcon            getOverlayIcon() { return INVISIBLE_ICON; }
            @Override public ResourceLocation getTextureFile() { return null; }
        };

        MISSING_INVISIBLE_CONTAINER = new IIconContainer() {
            @Override public IIcon            getIcon()        { return MISSING_ICON; }
            @Override public IIcon            getOverlayIcon() { return INVISIBLE_ICON; }
            @Override public ResourceLocation getTextureFile() { return null; }
        };
        // spotless:on
    }
}
