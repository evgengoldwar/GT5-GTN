package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GTValues.V;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.common.gui.modularui.singleblock.base.MTEBasicGeneratorBaseGui;

public abstract class MTEBasicGenerator extends MTEBasicTank implements RecipeMapWorkable {

    public MTEBasicGenerator(int aID, String aName, String aNameRegional, int aTier, String aDescription,
        IMTE[] mteArray, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, 3, aDescription, mteArray, aTextures);
    }

    public MTEBasicGenerator(int aID, String aName, String aNameRegional, int aTier, String[] aDescription,
        IMTE[] mteArray, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, 3, aDescription, mteArray, aTextures);
    }

    public MTEBasicGenerator(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[10][17][];
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = getFront(i);
            rTextures[1][i + 1] = getBack(i);
            rTextures[2][i + 1] = getBottom(i);
            rTextures[3][i + 1] = getTop(i);
            rTextures[4][i + 1] = getSides(i);
            rTextures[5][i + 1] = getFrontActive(i);
            rTextures[6][i + 1] = getBackActive(i);
            rTextures[7][i + 1] = getBottomActive(i);
            rTextures[8][i + 1] = getTopActive(i);
            rTextures[9][i + 1] = getSidesActive(i);
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facingDirection, int colorIndex,
        boolean active, boolean redstoneLevel) {
        return mTextures[(active ? 5 : 0) + (side == facingDirection ? 0
            : side == facingDirection.getOpposite() ? 1
                : side == ForgeDirection.DOWN ? 2 : side == ForgeDirection.UP ? 3 : 4)][colorIndex + 1];
    }

    protected String[] getTooltipLines() {
        return mDescriptionArray;
    }

    @Override
    public String[] getDescription() {
        String[] base = getTooltipLines();
        String[] desc = new String[base.length + 1];
        System.arraycopy(base, 0, desc, 0, base.length);
        desc[base.length] = StatCollector.translateToLocalFormatted(
            "gt.blockmachines.basicgenerator.fuel_efficiency",
            String.valueOf(getEfficiency()));
        return desc;
    }

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player) {
        openGui(player);
        return true;
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getFrontActive(byte aColor) {
        return getFront(aColor);
    }

    public ITexture[] getBackActive(byte aColor) {
        return getBack(aColor);
    }

    public ITexture[] getBottomActive(byte aColor) {
        return getBottom(aColor);
    }

    public ITexture[] getTopActive(byte aColor) {
        return getTop(aColor);
    }

    public ITexture[] getSidesActive(byte aColor) {
        return getSides(aColor);
    }

    @Override
    public boolean isFacingValid(ForgeDirection side) {
        return true;
    }

    @Override
    public boolean isValidSlot(int index) {
        return index < 2;
    }

    @Override
    public boolean isEnetOutput() {
        return true;
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        return true;
    }

    @Override
    public long maxEUOutput() {
        return getGTE().isAllowedToWork() ? V[mTier] : 0L;
    }

    @Override
    public long maxEUStore() {
        return Math.max(getEUVar(), V[mTier] * 80L + getMinimumStoredEU());
    }

    @Override
    public boolean doesFillContainers() {
        return getGTE().isAllowedToWork();
        // return false;
    }

    @Override
    public boolean doesEmptyContainers() {
        return getGTE().isAllowedToWork();
    }

    @Override
    public boolean canTankBeFilled() {
        return getGTE().isAllowedToWork();
    }

    @Override
    public boolean canTankBeEmptied() {
        return getGTE().isAllowedToWork();
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return getFuelValue(aFluid) > 0;
    }

    @Override
    public boolean isLiquidOutput(ForgeDirection side) {
        // return super.isLiquidOutput(aSide);
        return false;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        if (gte.isServerSide() && gte.isAllowedToWork() && tick % 10 == 0) {
            if (mFluid != null) {
                long tFuelValue = getFuelValue(mFluid), tConsumed = consumedFluidPerOperation(mFluid);
                if (tFuelValue > 0 && tConsumed > 0 && mFluid.amount >= tConsumed) {
                    long tFluidAmountToUse = Math
                        .min(mFluid.amount / tConsumed, (maxEUStore() - gte.getUniversalEnergyStored()) / tFuelValue);
                    if (tFluidAmountToUse > 0 && gte.increaseStoredEnergyUnits(tFluidAmountToUse * tFuelValue, true)) {
                        mFluid.amount -= tFluidAmountToUse * tConsumed;
                    }
                }
            }

            if (inventory[getInputSlot()] != null
                && gte.getUniversalEnergyStored() < (maxEUOutput() * 20 + getMinimumStoredEU())
                && ((GTUtility.getFluidForFilledItem(inventory[getInputSlot()], true) != null))) {
                long tFuelValue = getFuelValue(inventory[getInputSlot()]);
                if (tFuelValue <= 0) tFuelValue = getFuelValue(inventory[getInputSlot()], true);

                if (tFuelValue > 0) {
                    ItemStack tEmptyContainer = getEmptyContainer(inventory[getInputSlot()]);
                    if (gte.addStackToSlot(getOutputSlot(), tEmptyContainer)) {
                        gte.increaseStoredEnergyUnits(tFuelValue, true);
                        gte.decrStackSize(getInputSlot(), 1);
                    }
                }
            }
        }

        if (gte.isServerSide()) gte
            .setActive(gte.isAllowedToWork() && gte.getUniversalEnergyStored() >= maxEUOutput() + getMinimumStoredEU());
    }

    public abstract int getPollution();

    public abstract int getEfficiency();

    public int consumedFluidPerOperation(FluidStack aLiquid) {
        return 1;
    }

    public int getFuelValue(FluidStack aLiquid) {
        long value = getFuelValue(aLiquid, true);
        return (value > Integer.MAX_VALUE) ? 0 : (int) value;
    }

    public long getFuelValue(FluidStack aLiquid, boolean aLong) {
        return (long) getEfficiency() * consumedFluidPerOperation(aLiquid) / 100;
    }

    public int getFuelValue(ItemStack aStack) {
        long value = getFuelValue(aStack, true);
        return (value > Integer.MAX_VALUE) ? 0 : (int) value;
    }

    public long getFuelValue(ItemStack aStack, boolean aLong) {
        if (GTUtility.isStackInvalid(aStack) || getRecipeMap() == null) return 0;
        GTRecipe tFuel = getRecipeMap().findRecipeQuery()
            .items(aStack)
            .find();
        if (tFuel == null) return 0;

        long liters = 10L; // 1000mb/100
        return (long) tFuel.mSpecialValue * liters * getEfficiency();
    }

    public ItemStack getEmptyContainer(ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack) || getRecipeMap() == null) return null;
        GTRecipe tFuel = getRecipeMap().findRecipeQuery()
            .items(aStack)
            .find();
        if (tFuel != null) return GTUtility.copyOrNull(tFuel.getOutput(0));
        return GTUtility.getContainerItem(aStack, true);
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return super.allowPutStack(gte, index, side, itemStack) && (getFuelValue(itemStack, true) > 0
            || getFuelValue(GTUtility.getFluidForFilledItem(itemStack, true), true) > 0);
    }

    @Override
    public int getCapacity() {
        return 16000;
    }

    @Override
    public ModularPanel buildUI(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        return new MTEBasicGeneratorBaseGui<>(this).build(guiData, syncManager, uiSettings);
    }
}
