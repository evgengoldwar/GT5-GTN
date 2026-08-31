package gregtech.common.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.Nullable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.IBlockWithTextures;
import gregtech.api.interfaces.IOreMaterial;
import gregtech.api.interfaces.ITexture;
import gregtech.api.render.TextureFactory;
import gregtech.common.render.GTRendererBlock;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;

/**
 * A purely decorative frame box block (GT++ style): a non-functional, see-through block oredicted under
 * {@link OrePrefixes#frameGt}. Unlike {@link BlockFrameBox}/{@code MTEFrame} it has no covers, redstone passthrough or
 * scaffolding behavior -- it exists so {@link IOreMaterial}s that lack a real frame box (e.g. Werkstoffs) can still
 * satisfy {@code frameGt} in recipes.
 */
public class BlockDecorativeFrame extends BlockStorage implements IBlockWithTextures {

    final Int2ObjectFunction<IOreMaterial> materials;
    private final int maxMeta;

    public BlockDecorativeFrame(String aName, Int2ObjectFunction<IOreMaterial> materials, int maxMeta) {
        super(ItemStorage.class, aName, Material.iron);
        this.materials = materials;
        this.maxMeta = maxMeta;
    }

    @Override
    public String getLocalizedName(int meta) {
        IOreMaterial material = materials.get(meta);

        if (material == null) material = Materials_OLD._NULL;

        return OrePrefixes.frameGt.getLocalizedNameForItem(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item self, CreativeTabs tab, List<ItemStack> stacks) {
        for (int i = 0; i < maxMeta; i++) {
            IOreMaterial material = materials.get(i);

            if (material == null) continue;
            if (!material.generatesPrefix(OrePrefixes.frameGt)) continue;

            stacks.add(new ItemStack(self, 1, i));
        }
    }

    @Override
    public String getHarvestTool(int aMeta) {
        return "wrench";
    }

    @Override
    public int getHarvestLevel(int aMeta) {
        return 2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    @Override
    public int getRenderType() {
        return GTRendererBlock.RENDER_ID;
    }

    /**
     * {@inheritDoc}
     *
     * @implNote Can render in both opaque (pass 0) and alpha-blended (pass 1) rendering passes.
     */
    @Override
    public boolean canRenderInPass(int pass) {
        return pass == 0 || pass == 1;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    private final Int2ObjectLinkedOpenHashMap<ITexture[][]> textureCache = new Int2ObjectLinkedOpenHashMap<>();

    @Override
    public synchronized @Nullable ITexture[][] getTextures(int meta) {
        ITexture[][] cached = textureCache.getAndMoveToFirst(meta);

        if (cached != null) return cached;

        IOreMaterial material = materials.get(meta);

        ITexture texture;

        if (material != null) {
            texture = TextureFactory.builder()
                .addIcon(material.getTextureSet().mTextures[OrePrefixes.frameGt.getTextureIndex()])
                .setRGBA(material.getRGBA())
                .build();
        } else {
            texture = TextureFactory.builder()
                .addIcon(TextureSet.SET_NONE.mTextures[OrePrefixes.frameGt.getTextureIndex()])
                .build();
        }

        cached = new ITexture[][] { { texture }, { texture }, { texture }, { texture }, { texture }, { texture }, };

        textureCache.putAndMoveToFirst(meta, cached);

        while (textureCache.size() > 512) textureCache.removeLast();

        return cached;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int ordinalSide, int aMeta) {
        IOreMaterial material = materials.get(aMeta);
        if (material == null) return null;
        return material.getTextureSet().mTextures[OrePrefixes.frameGt.getTextureIndex()].getIcon();
    }
}
