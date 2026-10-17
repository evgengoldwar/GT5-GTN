package gregtech.api.modpack.machine;

import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * Textures of tiered machine hulls and machine overlays.
 * <p>
 * Hull textures ({@code assets/gregtech/textures/blocks/machines/casing_<side>.png}) are grayscale and tinted with the
 * tier color. A tier can get its own textures with {@link #setHull(int, Hull)}.
 */
public final class MachineTextures {

    // spotless:off
    /** Hull tint per tier: ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV, UEV, UIV, UMV, UXV, MAX */
    private static final int[] TIER_COLORS = {
        0x5A5A5A, 0xD2D2D2, 0xC8A064, 0xB4C8DC, 0xE6E6F0, 0xF0F0F5, 0xE6C8F0, 0x8CE6C8,
        0x3C8CE6, 0xE65A5A, 0xF0A03C, 0x7864F0, 0x50F078, 0xF0F050, 0xFFFFFF };
    // spotless:on

    private static final Hull[] HULLS = new Hull[TIER_COLORS.length];

    private MachineTextures() {}

    /**
     * Hull textures: one icon for top, bottom and the sides plus a tint.
     */
    public record Hull(IIconContainer top, IIconContainer bottom, IIconContainer side, short[] rgba) {

        public static Hull of(String top, String bottom, String side, int rgb) {
            return new Hull(ResourceIcon.block(top), ResourceIcon.block(bottom), ResourceIcon.block(side),
                MachineTextures.rgba(rgb));
        }

        public ITexture get(ForgeDirection direction) {
            IIconContainer icon = switch (direction) {
                case UP -> top;
                case DOWN -> bottom;
                default -> side;
            };
            return TextureFactory.of(icon, rgba);
        }
    }

    public static short[] rgba(int rgb) {
        return new short[] { (short) ((rgb >> 16) & 0xFF), (short) ((rgb >> 8) & 0xFF), (short) (rgb & 0xFF), 255 };
    }

    public static int clampTier(int tier) {
        return Math.max(0, Math.min(TIER_COLORS.length - 1, tier));
    }

    public static int getTierColor(int tier) {
        return TIER_COLORS[clampTier(tier)];
    }

    public static Hull getHull(int tier) {
        int t = clampTier(tier);
        Hull hull = HULLS[t];
        if (hull == null) {
            hull = Hull.of("machines/casing_top", "machines/casing_bottom", "machines/casing_side", TIER_COLORS[t]);
            HULLS[t] = hull;
        }
        return hull;
    }

    /** Replaces the hull of a tier (e.g. a steam or primitive tier with its own textures). */
    public static void setHull(int tier, Hull hull) {
        HULLS[clampTier(tier)] = hull;
    }

    public static ITexture hull(int tier, ForgeDirection side) {
        return getHull(tier).get(side);
    }
}
