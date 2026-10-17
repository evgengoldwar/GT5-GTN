package gregtech.api.modpack.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.client.ResourceUtils;
import gregtech.client.iconContainers.IconContainer;

/**
 * Icon container for an arbitrary texture path, e.g. {@code "machines/macerator_front"} (gregtech domain) or
 * {@code "mymod:blocks_custom/thing"}.
 * <p>
 * Block icons resolve to {@code assets/<domain>/textures/blocks/<path>.png}, item icons to
 * {@code assets/<domain>/textures/items/<path>.png}. If {@code <path>_OVERLAY.png} exists it is used as overlay icon.
 * Missing textures are replaced by the missing texture instead of crashing.
 * <p>
 * Instances are cached, so the same path always returns the same container.
 */
public final class ResourceIcon implements IIconContainer, Runnable {

    private static final Map<String, ResourceIcon> BLOCK_ICONS = new ConcurrentHashMap<>();
    private static final Map<String, ResourceIcon> ITEM_ICONS = new ConcurrentHashMap<>();
    private static final Map<String, ResourceIcon> OPTIONAL_BLOCK_ICONS = new ConcurrentHashMap<>();

    private final String iconName;
    private final String overlayName;
    private final boolean isBlock;
    private final boolean optional;
    private IIcon icon;
    private IIcon overlay;

    private ResourceIcon(String iconName, boolean isBlock, boolean optional) {
        this.iconName = iconName;
        this.overlayName = iconName + "_OVERLAY";
        this.isBlock = isBlock;
        this.optional = optional;
        if (isBlock) {
            GregTechAPI.sGTBlockIconload.add(this);
        } else {
            GregTechAPI.sGTItemIconload.add(this);
        }
    }

    /**
     * Adds the gregtech domain when the path has no {@code domain:} prefix.
     */
    public static String withDomain(String path) {
        return path.contains(":") ? path : "gregtech:" + path;
    }

    public static ResourceIcon block(String path) {
        return BLOCK_ICONS.computeIfAbsent(withDomain(path), name -> new ResourceIcon(name, true, false));
    }

    /**
     * Block icon that renders nothing (instead of the missing texture) when the file does not exist. Used for optional
     * machine overlays.
     */
    public static ResourceIcon blockOptional(String path) {
        return OPTIONAL_BLOCK_ICONS.computeIfAbsent(withDomain(path), name -> new ResourceIcon(name, true, true));
    }

    public static ResourceIcon item(String path) {
        return ITEM_ICONS.computeIfAbsent(withDomain(path), name -> new ResourceIcon(name, false, false));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        if (icon != null) {
            return icon;
        }
        return optional ? IconContainer.INVISIBLE_ICON : IconContainer.MISSING_ICON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayIcon() {
        return overlay != null ? overlay : IconContainer.INVISIBLE_ICON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getTextureFile() {
        return isBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture;
    }

    public String getIconName() {
        return iconName;
    }

    @Override
    public void run() {
        if (isBlock) {
            if (ResourceUtils.resourceExists(ResourceUtils.getCompleteBlockTextureResourceLocation(iconName))) {
                icon = GregTechAPI.sBlockIcons.registerIcon(iconName);
            } else {
                icon = optional ? IconContainer.INVISIBLE_ICON : IconContainer.MISSING_ICON;
            }
            if (ResourceUtils.resourceExists(ResourceUtils.getCompleteBlockTextureResourceLocation(overlayName))) {
                overlay = GregTechAPI.sBlockIcons.registerIcon(overlayName);
            } else {
                overlay = IconContainer.INVISIBLE_ICON;
            }
        } else {
            if (ResourceUtils.resourceExists(ResourceUtils.getCompleteItemTextureResourceLocation(iconName))) {
                icon = GregTechAPI.sItemIcons.registerIcon(iconName);
            } else {
                icon = IconContainer.MISSING_ICON;
            }
            if (ResourceUtils.resourceExists(ResourceUtils.getCompleteItemTextureResourceLocation(overlayName))) {
                overlay = GregTechAPI.sItemIcons.registerIcon(overlayName);
            } else {
                overlay = IconContainer.INVISIBLE_ICON;
            }
        }
    }
}
