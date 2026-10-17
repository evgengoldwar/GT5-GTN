package gregtech.api.modpack.machine;

import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * Overlay textures drawn on top of the hull. For {@code MachineOverlay.of("machines/macerator")} the files are
 * (all optional, missing ones are not drawn):
 *
 * <pre>
 * assets/gregtech/textures/blocks/machines/macerator/overlay_front.png
 * assets/gregtech/textures/blocks/machines/macerator/overlay_front_active.png
 * assets/gregtech/textures/blocks/machines/macerator/overlay_top.png
 * assets/gregtech/textures/blocks/machines/macerator/overlay_top_active.png
 * assets/gregtech/textures/blocks/machines/macerator/overlay_side.png
 * assets/gregtech/textures/blocks/machines/macerator/overlay_side_active.png
 * </pre>
 *
 * Animated textures work with a vanilla {@code .png.mcmeta} file.
 */
public final class MachineOverlay {

    public static final MachineOverlay NONE = new MachineOverlay(null);

    private final IIconContainer front, frontActive, top, topActive, side, sideActive;

    private MachineOverlay(String path) {
        if (path == null) {
            front = frontActive = top = topActive = side = sideActive = null;
            return;
        }
        front = ResourceIcon.blockOptional(path + "/overlay_front");
        frontActive = ResourceIcon.blockOptional(path + "/overlay_front_active");
        top = ResourceIcon.blockOptional(path + "/overlay_top");
        topActive = ResourceIcon.blockOptional(path + "/overlay_top_active");
        side = ResourceIcon.blockOptional(path + "/overlay_side");
        sideActive = ResourceIcon.blockOptional(path + "/overlay_side_active");
    }

    public static MachineOverlay of(String path) {
        return new MachineOverlay(path);
    }

    /**
     * @return overlay of a face or null
     */
    public ITexture get(ForgeDirection face, ForgeDirection facing, boolean active) {
        IIconContainer icon;
        if (face == facing) {
            icon = active ? frontActive : front;
        } else if (face == ForgeDirection.UP) {
            icon = active ? topActive : top;
        } else if (face != ForgeDirection.DOWN) {
            icon = active ? sideActive : side;
        } else {
            icon = null;
        }
        return icon == null ? null : TextureFactory.of(icon);
    }
}
