package gregtech.client.iconContainers.blocks;

import static gregtech.api.enums.Mods.GregTech;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.client.ResourceUtils;

public class GTCustomAlphaFallbackBlockIconContainer extends AbstractBlockIconContainer implements Runnable {

    private static final int CACHE_SIZE = 256;
    private static Map<String, GTCustomAlphaFallbackBlockIconContainer> INSTANCES = new ConcurrentHashMap<>(CACHE_SIZE);

    private final ResourceLocation iconResource, overlayResource;
    private final String iconName, overlayName;
    private final IIconContainer fallback;
    private IIcon icon;
    private IIcon overlay;
    private boolean hasIcon;
    private boolean hasOverlay;

    GTCustomAlphaFallbackBlockIconContainer(@NotNull String iconName, @NotNull IIconContainer fallback) {
        this.iconName = iconName.contains(":") ? iconName : GregTech.getResourcePath(iconName);
        this.overlayName = this.iconName + Textures.OverlaySuffix;
        this.iconResource = ResourceUtils.getCompleteBlockTextureResourceLocation(this.iconName);
        this.overlayResource = ResourceUtils.getCompleteBlockTextureResourceLocation(this.overlayName);
        this.fallback = fallback;
    }

    public static @NotNull IIconContainer create(@NotNull String iconName, @NotNull IIconContainer fallback) {
        String key = iconName + '\0' + System.identityHashCode(fallback);
        return INSTANCES
            .computeIfAbsent(key, ignored -> new GTCustomAlphaFallbackBlockIconContainer(iconName, fallback));
    }

    public static void cleanup() {
        INSTANCES = new ConcurrentHashMap<>(CACHE_SIZE);
    }

    @Override
    public void run() {
        hasIcon = ResourceUtils.resourceExists(iconResource);
        hasOverlay = ResourceUtils.resourceExists(overlayResource);

        if (hasIcon) {
            icon = GregTechAPI.sBlockIcons.registerIcon(iconName);
        } else icon = null;
        if (hasOverlay) {
            overlay = GregTechAPI.sBlockIcons.registerIcon(overlayName);
        } else overlay = null;
    }

    @Override
    public IIcon getIcon() {
        if (hasIcon) return icon;
        return hasOverlay ? Textures.InvisibleIcon.INVISIBLE_ICON : fallback.getIcon();
    }

    @Override
    public IIcon getOverlayIcon() {
        if (hasOverlay) return overlay;
        return hasIcon ? Textures.InvisibleIcon.INVISIBLE_ICON : fallback.getOverlayIcon();
    }

    @Override
    public int getRenderIconPass() {
        return 1;
    }
}
