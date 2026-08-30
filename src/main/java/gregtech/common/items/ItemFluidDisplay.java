package gregtech.common.items;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatFluid;
import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.IOreMaterial;
import gregtech.api.items.GTGenericItem;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Client;
import gregtech.common.fluid.GTFluid;

public class ItemFluidDisplay extends GTGenericItem implements IFluidContainerItem {

    public static final List<ItemStack> ITEM_STACKS = new ArrayList<>();

    private static final int CAPACITY = 0;

    public ItemFluidDisplay() {
        super("GregTech_FluidDisplay", "Fluid Display", null);
        ItemList.Display_Fluid.set(this);
    }

    @Override
    protected void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        if (FluidRegistry.getFluid(aStack.getItemDamage()) != null) {
            addTooltipForFluid(FluidRegistry.getFluid(aStack.getItemDamage()), aList);
        }

        if (GTValues.D1 || Minecraft.getMinecraft().gameSettings.advancedItemTooltips) {
            Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
            if (tFluid != null) {
                aList.add(StatCollector.translateToLocalFormatted("GT5U.tooltip.fluid.registry", tFluid.getName()));
            }
        }

        NBTTagCompound aNBT = aStack.getTagCompound();

        if (aNBT != null) {
            if (Client.tooltip.showFluidAmount) {
                long tToolTipAmount = aNBT.getLong("mFluidDisplayAmount");
                if (tToolTipAmount > 0L) {
                    aList.add(
                        EnumChatFormatting.BLUE
                            + StatCollector
                                .translateToLocalFormatted("GT5U.tooltip.fluid.amount", formatFluid(tToolTipAmount))
                            + EnumChatFormatting.GRAY);
                }
            }
            if (Client.tooltip.showFluidTemperature) {
                aList.add(
                    EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
                        "GT5U.tooltip.fluid.temperature",
                        formatNumber(aNBT.getLong("mFluidDisplayHeat"))) + EnumChatFormatting.GRAY);
            }
            if (Client.tooltip.showFluidState) {
                aList.add(
                    EnumChatFormatting.GREEN + StatCollector.translateToLocalFormatted(
                        "GT5U.tooltip.fluid.stat",
                        aNBT.getBoolean("mFluidState") ? StatCollector.translateToLocal("GT5U.tooltip.fluid.stat.gas")
                            : StatCollector.translateToLocal("GT5U.tooltip.fluid.stat.liquid"))
                        + EnumChatFormatting.GRAY);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aIconRegister) {}

    @Override
    public IIcon getIconFromDamage(int aMetaData) {
        return Stream.of(FluidRegistry.getFluid(aMetaData), FluidRegistry.WATER)
            .filter(Objects::nonNull)
            .map(Fluid::getStillIcon)
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack aStack, int aRenderPass) {
        Fluid tFluid = FluidRegistry.getFluid(aStack.getItemDamage());
        return tFluid == null ? 16777215 : tFluid.getColor();
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        if (aStack != null) {
            return GTUtility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), false);
        }
        return "";
    }

    @Override
    public String getItemStackDisplayName(ItemStack aStack) {
        if (aStack != null) {
            return GTUtility.getFluidName(FluidRegistry.getFluid(aStack.getItemDamage()), true);
        }
        return "";
    }

    @SideOnly(Side.CLIENT)
    public static IOreMaterial getMaterial(Fluid fluid) {
        if (fluid instanceof GTFluid gtFluid) {
            return Materials.FLUID_MAP.get(gtFluid);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static void addTooltipForFluid(Fluid fluid, List<String> list) {
        IOreMaterial material = getMaterial(fluid);
        if (material != null) {
            material.addTooltips(list);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item aItem, CreativeTabs aTab, List<ItemStack> aList) {
        for (ItemStack itemStack : ITEM_STACKS) {
            if (itemStack != null) {
                aList.add(itemStack);
            }
        }
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        if (container == null) {
            return null;
        }

        Fluid fluid = FluidRegistry.getFluid(container.getItemDamage());

        if (fluid == null) {
            return null;
        }

        return new FluidStack(fluid, CAPACITY);
    }

    @Override
    public int getCapacity(ItemStack container) {
        return 0;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        return null;
    }

    public static void setupFluids() {
        int i = 0;
        for (int j = FluidRegistry.getMaxID(); i < j; i++) {
            ItemStack tStack = GTUtility.getFluidDisplayStack(FluidRegistry.getFluid(i));
            if (tStack != null) {
                ITEM_STACKS.add(tStack);
            }
        }
    }
}
