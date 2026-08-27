package gtneioreplugin.plugin.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.MinecraftForgeClient;

import com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import gtneioreplugin.GTNEIOrePlugin;
import gtneioreplugin.plugin.block.BlockDimensionDisplay;
import gtneioreplugin.plugin.block.ModBlocks;
import gtneioreplugin.plugin.renderer.ItemDimensionDisplayRenderer;

public class ItemDimensionDisplay extends ItemBlock {

    public ItemDimensionDisplay(Block block) {
        super(block);

        if (FMLCommonHandler.instance()
            .getEffectiveSide() == Side.CLIENT) {
            MinecraftForgeClient.registerItemRenderer(this, new ItemDimensionDisplayRenderer());
        }
    }

    public static ItemStack getItem(String dimension) {
        Block block = ModBlocks.getBlock(dimension);
        if (block != null) {
            return new ItemStack(block);
        }
        if (dimension != null) {
            GTNEIOrePlugin.LOG.warn("Unknown dimension queried for ItemDimensionDisplay: {}", dimension);
        }
        return null;
    }

    /** Gets the abbreviated dimension name for this block. */
    public static String getDimension(ItemStack stack) {
        if (stack.getItem() instanceof ItemDimensionDisplay) {
            return ((BlockDimensionDisplay) Block.getBlockFromItem(stack.getItem())).getDimension();
        }
        return null;
    }

    @Override
    public String getUnlocalizedName() {
        return "";
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return "";
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        super.addInformation(stack, player, list, advanced);

        if (stack.hasTagCompound() && stack.getTagCompound()
            .hasKey("VeinChance")) {

            double chance = stack.getTagCompound()
                .getDouble("VeinChance");

            list.add(
                StatCollector.translateToLocalFormatted(
                    "gtnop.gui.nei.orechunkchance.value",
                    NumberFormatUtil.formatNumber(100 * chance)));
        }
    }
}
