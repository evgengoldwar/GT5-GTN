package gregtech.MaterialSystem.Api;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockMetaMaterial extends ItemBlock {

    private final BlockMetaMaterial block;

    public ItemBlockMetaMaterial(Block block) {
        super(block);
        this.block = (BlockMetaMaterial) block;
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return block.getIcon(0, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        Material material = block.getMaterial(stack.getItemDamage());

        if (material != null) {
            return material.getColor();
        }
        return 0xFFFFFF;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        Material material = block.getMaterial(stack.getItemDamage());

        if (material != null) {
            return "tile." + material.getName()
                .toLowerCase()
                + "_"
                + block.getMaterialType()
                    .name()
                    .toLowerCase();
        }
        return super.getUnlocalizedName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        block.addSubItems(item, list);
    }
}
