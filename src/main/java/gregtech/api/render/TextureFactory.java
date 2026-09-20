package gregtech.api.render;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.ITextureBuilder;
import gregtech.common.render.GTBlockTextureBuilder;
import gregtech.common.render.GTMultiTextureRender;
import gregtech.common.render.GTSidedTextureRender;
import gregtech.common.render.GTTextureBuilder;

public final class TextureFactory {

    private TextureFactory() {
        throw new AssertionError("Non-instantiable class");
    }

    public static ITexture of(final ITexture... textures) {
        return GTMultiTextureRender.create(textures);
    }

    public static ITexture of(final IIconContainer bottom, final IIconContainer top, final IIconContainer north,
        final IIconContainer south, final IIconContainer west, final IIconContainer east, final short[] rgba) {
        return new GTSidedTextureRender(bottom, top, north, south, west, east, rgba);
    }

    public static ITexture of(final IIconContainer bottom, final IIconContainer top, final IIconContainer sides,
        final short[] rgba) {
        return new GTSidedTextureRender(bottom, top, sides, sides, sides, sides, rgba);
    }

    public static ITexture of(final IIconContainer iconContainer, final short[] rgba) {
        return builder().addIcon(iconContainer)
            .setRGBA(rgba)
            .build();
    }

    public static ITexture of(final IIconContainer iconContainer) {
        return builder().addIcon(iconContainer)
            .build();
    }

    public static ITexture of(final Block block, final int meta, final ForgeDirection side, final short[] rgba) {
        return blockBuilder().setFromBlock(block, meta)
            .setFromSide(side)
            .setRGBA(rgba)
            .build();
    }

    public static ITexture of(final Block block, final int meta, final ForgeDirection side) {
        return blockBuilder().setFromBlock(block, meta)
            .setFromSide(side)
            .build();
    }

    public static ITexture of(final Block block, final int meta) {
        return blockBuilder().setFromBlock(block, meta)
            .build();
    }

    public static ITexture of(final Block block) {
        return of(block, 0);
    }

    public static ITextureBuilder builder() {
        return new GTTextureBuilder();
    }

    public static GTBlockTextureBuilder blockBuilder() {
        return new GTBlockTextureBuilder();
    }
}
