package gregtech.client.iconContainers;

import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.util.GTUtility.INVISIBLE_ICON;
import static gregtech.api.util.GTUtility.getMissingNo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.client.ResourceUtils;

public class GTBlockIconContainer implements IIconContainer, Runnable {

    private static final Map<String, GTBlockIconContainer> INSTANCES = new ConcurrentHashMap<>();
    private static final String OVERLAY_SUFFIX = "_OVERLAY";
    private final ResourceLocation iconResource;
    private final ResourceLocation overlayResource;
    private final String iconName;
    private final String overlayName;
    private IIcon icon = null;
    private IIcon overlay = null;

    public GTBlockIconContainer(String path, String iconName) {
        this.iconName = GregTech.resourceDomain + ":" + path + iconName;
        this.overlayName = GregTech.resourceDomain + ":" + path + iconName + OVERLAY_SUFFIX;
        this.iconResource = ResourceUtils.getCompleteBlockTextureResourceLocation(this.iconName);
        this.overlayResource = ResourceUtils.getCompleteBlockTextureResourceLocation(this.overlayName);
        GregTechAPI.sGTBlockIconload.add(this);
    }

    @Override
    public IIcon getIcon() {
        return icon;
    }

    @Override
    public IIcon getOverlayIcon() {
        return overlay;
    }

    @Override
    public ResourceLocation getTextureFile() {
        return TextureMap.locationBlocksTexture;
    }

    @Override
    public void run() {
        if (ResourceUtils.resourceExists(iconResource)) {
            icon = GregTechAPI.sBlockIcons.registerIcon(iconName);
        } else {
            icon = getMissingNo();
        }

        if (ResourceUtils.resourceExists(overlayResource)) {
            overlay = GregTechAPI.sBlockIcons.registerIcon(overlayName);
        } else {
            overlay = INVISIBLE_ICON;
        }
    }

    public static IIconContainer create(String path, String iconName) {
        return INSTANCES.computeIfAbsent(iconName, _ -> new GTBlockIconContainer(path, iconName));
    }
}
