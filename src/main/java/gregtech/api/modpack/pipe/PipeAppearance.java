package gregtech.api.modpack.pipe;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * How a cable / pipe looks: a grayscale side texture tinted by the material color, an optional texture for the
 * connected ends and an optional insulation layer (cables).
 */
public final class PipeAppearance {

    public static final PipeAppearance DEFAULT = new PipeAppearance(Textures.MISSING, null, null, null,
        new short[] { 255, 255, 255, 255 });

    private static final short[] INSULATION_COLOR = { 64, 64, 64, 255 };

    private final IIconContainer side;
    private final IIconContainer end;
    private final IIconContainer insulation;
    private final IIconContainer insulationEnd;
    private final short[] rgba;

    public PipeAppearance(IIconContainer side, IIconContainer end, IIconContainer insulation,
        IIconContainer insulationEnd, short[] rgba) {
        this.side = side;
        this.end = end;
        this.insulation = insulation;
        this.insulationEnd = insulationEnd;
        this.rgba = rgba;
    }

    /**
     * @param sideTexture block texture path, e.g. {@code "MaterialSystems/Pipes/pipe_side"}
     * @param endTexture  texture of a connected end, may be null (side texture is used)
     */
    public static PipeAppearance of(String sideTexture, String endTexture, short[] rgba) {
        return new PipeAppearance(
            ResourceIcon.block(sideTexture),
            endTexture == null ? null : ResourceIcon.block(endTexture),
            null,
            null,
            rgba);
    }

    /**
     * @param insulationTexture    texture of the insulated sides
     * @param insulationEndTexture ring drawn around the wire on connected ends
     */
    public PipeAppearance withInsulation(String insulationTexture, String insulationEndTexture) {
        return new PipeAppearance(
            side,
            end,
            ResourceIcon.block(insulationTexture),
            ResourceIcon.block(insulationEndTexture),
            rgba);
    }

    public boolean isInsulated() {
        return insulation != null;
    }

    private static short[] dyed(short[] base, int colorIndex) {
        if (!Dyes.isDyeIndex(colorIndex)) {
            return base;
        }
        short[] dye = Dyes.get(colorIndex).mRGBa;
        return new short[] { (short) ((base[0] + dye[0]) / 2), (short) ((base[1] + dye[1]) / 2),
            (short) ((base[2] + dye[2]) / 2), 255 };
    }

    /**
     * Textures of one face.
     *
     * @param connected  the face is a connected end
     * @param colorIndex dye of the pipe (spray can), -1 if not painted
     */
    public ITexture[] getTextures(boolean connected, int colorIndex) {
        if (insulation != null) {
            short[] insulationColor = Dyes.isDyeIndex(colorIndex) ? Dyes.get(colorIndex).mRGBa : INSULATION_COLOR;
            if (connected) {
                return new ITexture[] { TextureFactory.of(side, rgba),
                    TextureFactory.of(insulationEnd, insulationColor) };
            }
            return new ITexture[] { TextureFactory.of(insulation, insulationColor) };
        }
        IIconContainer icon = connected && end != null ? end : side;
        return new ITexture[] { TextureFactory.of(icon, dyed(rgba, colorIndex)) };
    }
}
