package gregtech.common.MaterialSystem.Api;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

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
            return "tile." + material.getUnlocalizedName()
                + "_"
                + block.getMaterialType()
                    .getTextureName();
        }
        return super.getUnlocalizedName(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String key = getUnlocalizedName(stack) + ".name";
        if (StatCollector.canTranslate(key)) {
            return StatCollector.translateToLocal(key);
        }
        Material material = block.getMaterial(stack.getItemDamage());
        return material != null ? block.getMaterialType()
            .formatLocalName(material) : super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        Material material = block.getMaterial(stack.getItemDamage());
        if (material != null && material.getTooltip() != null) {
            tooltip.add(EnumChatFormatting.YELLOW + material.getTooltip());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        block.addSubItems(item, list);
    }
}
