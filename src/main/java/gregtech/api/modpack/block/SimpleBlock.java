package gregtech.api.modpack.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.modpack.util.ResourceIcon;

/**
 * Block created by {@link BlockBuilder}. One block id per block - with EndlessIDs there is no practical limit.
 */
public class SimpleBlock extends Block {

    final BlockBuilder.Properties properties;

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    protected SimpleBlock(BlockBuilder.Properties properties) {
        super(properties.material);
        this.properties = properties;
        setBlockName(properties.unlocalizedName);
        setHardness(properties.hardness);
        setResistance(properties.resistance);
        setLightLevel(properties.lightLevel);
        setStepSound(properties.sound);
        if (properties.creativeTab != null) {
            setCreativeTab(properties.creativeTab);
        }
        if (properties.harvestTool != null) {
            setHarvestLevel(properties.harvestTool, properties.harvestLevel);
        }
        if (properties.transparent) {
            setLightOpacity(0);
        }
    }

    public String getId() {
        return properties.id;
    }

    public ItemStack getStack(int amount) {
        return new ItemStack(this, amount, 0);
    }

    public ItemStack getStack() {
        return getStack(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[6];
        for (int side = 0; side < 6; side++) {
            icons[side] = register.registerIcon(ResourceIcon.withDomain(properties.textures[side]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[side < 0 || side > 5 ? 0 : side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return properties.color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return properties.color;
    }

    @Override
    public boolean isOpaqueCube() {
        return !properties.transparent;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !properties.transparent;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return properties.translucent ? 1 : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        if (properties.transparent && world.getBlock(x, y, z) == this) {
            return false;
        }
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess world, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return properties.beaconBase;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return !properties.witherProof && super.canEntityDestroy(world, x, y, z, entity);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        if (properties.drop != null) {
            return properties.drop.getItem();
        }
        return super.getItemDropped(meta, random, fortune);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        if (properties.drop != null) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            int count = properties.drop.stackSize + (fortune > 0 ? world.rand.nextInt(fortune + 1) : 0);
            ItemStack drop = properties.drop.copy();
            drop.stackSize = count;
            drops.add(drop);
            return drops;
        }
        return super.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (properties.onRightClick != null) {
            return properties.onRightClick.onRightClick(world, x, y, z, player, side);
        }
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if (properties.onLeftClick != null) {
            properties.onLeftClick.onLeftClick(world, x, y, z, player);
        }
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (properties.onWalk != null) {
            properties.onWalk.onWalk(world, x, y, z, entity);
        }
    }
}
