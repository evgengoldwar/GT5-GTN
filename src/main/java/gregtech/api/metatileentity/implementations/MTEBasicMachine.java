package gregtech.api.metatileentity.implementations;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.enums.Textures.BlockIcons.MACHINE_CASINGS;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_PIPE_OUT;
import static gregtech.api.metatileentity.BaseTileEntity.FLUID_TRANSFER_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.ITEM_TRANSFER_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.NEI_TRANSFER_STEAM_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.NEI_TRANSFER_VOLTAGE_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.POWER_SOURCE_KEY;
import static gregtech.api.metatileentity.BaseTileEntity.SPECIAL_SLOT_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.STALLED_STUTTERING_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.STALLED_VENT_TOOLTIP;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static gregtech.api.metatileentity.BaseTileEntity.UNUSED_SLOT_TOOLTIP;
import static gregtech.api.util.GTRecipeConstants.EXPLODE;
import static gregtech.api.util.GTRecipeConstants.ON_FIRE;
import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UNKNOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.api.widget.Widget;
import com.gtnewhorizons.modularui.common.fluid.FluidStackTank;
import com.gtnewhorizons.modularui.common.widget.CycleButtonWidget;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.FluidSlotWidget;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.GTMod;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.TieredVariant;
import gregtech.api.gui.modularui.CircularGaugeDrawable;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.gui.modularui.SteamTexture;
import gregtech.api.interfaces.ICleanroom;
import gregtech.api.interfaces.IConfigurationCircuitSupport;
import gregtech.api.interfaces.INonConsumedItemDisplay;
import gregtech.api.interfaces.IPhysicalCircuitDisplay;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.modularui.IAddGregtechLogo;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.interfaces.tileentity.IOverclockDescriptionProvider;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.objects.overclockdescriber.EUOverclockDescriber;
import gregtech.api.objects.overclockdescriber.OverclockDescriber;
import gregtech.api.recipe.BasicUIProperties;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.FakeCleanroom;
import gregtech.api.util.GTClientPreference;
import gregtech.api.util.GTItemTransfer;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTTooltipDataCache;
import gregtech.api.util.GTUtility;
import gregtech.api.util.GTWaila;
import gregtech.api.util.OverclockCalculator;
import gregtech.client.GTSoundLoop;
import gregtech.common.config.Client;
import gregtech.common.gui.modularui.UIHelper;
import gregtech.common.items.ItemIntegratedCircuit;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.overlay.tooltiprenderers.TTRenderBar;
import mcp.mobius.waila.overlay.tooltiprenderers.TTRenderStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor Extend this class to make a simple
 * Machine
 */
public abstract class MTEBasicMachine extends MTEBasicTank implements RecipeMapWorkable, IConfigurationCircuitSupport,
    IOverclockDescriptionProvider, IAddGregtechLogo, INonConsumedItemDisplay, IPhysicalCircuitDisplay {

    /**
     * return values for checkRecipe()
     */
    protected static final int DID_NOT_FIND_RECIPE = 0, FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS = 1,
        FOUND_AND_SUCCESSFULLY_USED_RECIPE = 2;
    public static final String STEAM_AMOUNT_LANGKEY = "GT5U.machines.steam.amount";

    public static final int OTHER_SLOT_COUNT = 5;
    public final ItemStack[] mOutputItems;
    public final int mInputSlotCount;
    public int mAmperage;
    public boolean mAllowInputFromOutputSide = true, mFluidTransfer = false, mItemTransfer = false, mStuttering = false,
        mCharge = false, mDecharge = false;
    private int errorDisplayID;
    public boolean mDisableFilter = true;
    public boolean mDisableMultiStack = true;
    public int mProgresstime = 0, mMaxProgresstime = 0, mEUt = 0, mOutputBlocked = 0;
    public ForgeDirection mMainFacing = ForgeDirection.WEST;
    public FluidStack mOutputFluid;
    protected OverclockDescriber overclockDescriber;
    @SideOnly(Side.CLIENT)
    protected GTSoundLoop activitySoundLoop;

    /**
     * Contains the Recipe which has been previously used, or null if there was no previous Recipe, which could have
     * been buffered
     */
    protected GTRecipe mLastRecipe = null;

    private FluidStack mFluidOut;
    protected final FluidStackTank fluidOutputTank = new FluidStackTank(
        () -> mFluidOut,
        fluidStack -> mFluidOut = fluidStack,
        this::getCapacity);

    public FluidStackTank getFluidOutputTank() {
        return this.fluidOutputTank;
    }

    /**
     * Registers machine with single-line description.
     *
     * @param aOverlays 0 = SideFacingActive 1 = SideFacingInactive 2 = FrontFacingActive 3 = FrontFacingInactive 4 =
     *                  TopFacingActive 5 = TopFacingInactive 6 = BottomFacingActive 7 = BottomFacingInactive ----- Not
     *                  all Array Elements have to be initialised, you can also just use 8 Parameters for the Default
     *                  Pipe Texture Overlays ----- 8 = BottomFacingPipeActive 9 = BottomFacingPipeInactive 10 =
     *                  TopFacingPipeActive 11 = TopFacingPipeInactive 12 = SideFacingPipeActive 13 =
     *                  SideFacingPipeInactive
     */
    public MTEBasicMachine(int aID, String aName, String aNameRegional, int aTier, int aAmperage, String aDescription,
        int aInputSlotCount, int aOutputSlotCount, IMTE[] mteArray, ITexture... aOverlays) {
        super(
            aID,
            aName,
            aNameRegional,
            aTier,
            OTHER_SLOT_COUNT + aInputSlotCount + aOutputSlotCount + 1,
            aDescription,
            mteArray,
            aOverlays);
        mInputSlotCount = Math.max(0, aInputSlotCount);
        mOutputItems = new ItemStack[Math.max(0, aOutputSlotCount)];
        mAmperage = aAmperage;
        overclockDescriber = createOverclockDescriber();
    }

    /**
     * Registers machine with multi-line descriptions.
     */
    public MTEBasicMachine(int aID, String aName, String aNameRegional, int aTier, int aAmperage, String[] aDescription,
        int aInputSlotCount, int aOutputSlotCount, IMTE[] mteArray, ITexture... aOverlays) {
        super(
            aID,
            aName,
            aNameRegional,
            aTier,
            OTHER_SLOT_COUNT + aInputSlotCount + aOutputSlotCount + 1,
            aDescription,
            mteArray,
            aOverlays);
        mInputSlotCount = Math.max(0, aInputSlotCount);
        mOutputItems = new ItemStack[Math.max(0, aOutputSlotCount)];
        mAmperage = aAmperage;
        overclockDescriber = createOverclockDescriber();
    }

    /**
     * For {@link #newMTE}.
     */
    public MTEBasicMachine(String aName, int aTier, int aAmperage, String[] aDescription, ITexture[][][] aTextures,
        int aInputSlotCount, int aOutputSlotCount) {
        super(aName, aTier, OTHER_SLOT_COUNT + aInputSlotCount + aOutputSlotCount + 1, aDescription, aTextures);
        mInputSlotCount = Math.max(0, aInputSlotCount);
        mOutputItems = new ItemStack[Math.max(0, aOutputSlotCount)];
        mAmperage = aAmperage;
        overclockDescriber = createOverclockDescriber();
        // In world basic machine have mMainFacing UNKNOWN for rotation fixes.
        // During placement, mFacing initially mean main facing.
        // This is then written to mMainFacing when the block is initialized.
        // and mFacing become the output facing, initialized to the opposite side.
        // This quirk is due to historical reasons, and it is mostly to support
        // block saved with the old nbt to keep working. The ideal logic should be
        // mFacing -> main facing and a new field mOutputFacing, and with wrenchRightClick
        // overridden so that normal right click rotate the output face and sneak rotate
        // the main face.

        // The current logic is that the block is set to UNKNOWN before loading of NBT or rotation, and the
        // very first rotation will cause mMainFacing and mFacing to be swapped and initialized.
        mMainFacing = UNKNOWN;
    }

    public boolean isStuttering() {
        return mStuttering;
    }

    /**
     * To be called by the constructor to initialize this instance's overclock behavior
     */
    protected OverclockDescriber createOverclockDescriber() {
        return new EUOverclockDescriber(mTier, mAmperage);
    }

    protected boolean isValidMainFacing(ForgeDirection side) {
        return (side.flag & (UP.flag | DOWN.flag | UNKNOWN.flag)) == 0; // Horizontal
    }

    public boolean setMainFacing(ForgeDirection side) {
        if (!isValidMainFacing(side)) return false;
        mMainFacing = side;
        if (getGTE().getFrontFacing() == mMainFacing) {
            getGTE().setFrontFacing(side.getOpposite());
        }
        onFacingChange();
        onMachineBlockUpdate();
        return true;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[14][17][];
        aTextures = Arrays.copyOf(aTextures, 14);

        for (int i = 0; i < aTextures.length; i++) if (aTextures[i] != null) for (byte c = -1; c < 16; c++) {
            if (rTextures[i][c + 1] == null)
                rTextures[i][c + 1] = new ITexture[] { MACHINE_CASINGS[mTier][c + 1], aTextures[i] };
        }

        for (byte c = -1; c < 16; c++) {
            if (rTextures[0][c + 1] == null) rTextures[0][c + 1] = getSideFacingActive(c);
            if (rTextures[1][c + 1] == null) rTextures[1][c + 1] = getSideFacingInactive(c);
            if (rTextures[2][c + 1] == null) rTextures[2][c + 1] = getFrontFacingActive(c);
            if (rTextures[3][c + 1] == null) rTextures[3][c + 1] = getFrontFacingInactive(c);
            if (rTextures[4][c + 1] == null) rTextures[4][c + 1] = getTopFacingActive(c);
            if (rTextures[5][c + 1] == null) rTextures[5][c + 1] = getTopFacingInactive(c);
            if (rTextures[6][c + 1] == null) rTextures[6][c + 1] = getBottomFacingActive(c);
            if (rTextures[7][c + 1] == null) rTextures[7][c + 1] = getBottomFacingInactive(c);
            if (rTextures[8][c + 1] == null) rTextures[8][c + 1] = getBottomFacingPipeActive(c);
            if (rTextures[9][c + 1] == null) rTextures[9][c + 1] = getBottomFacingPipeInactive(c);
            if (rTextures[10][c + 1] == null) rTextures[10][c + 1] = getTopFacingPipeActive(c);
            if (rTextures[11][c + 1] == null) rTextures[11][c + 1] = getTopFacingPipeInactive(c);
            if (rTextures[12][c + 1] == null) rTextures[12][c + 1] = getSideFacingPipeActive(c);
            if (rTextures[13][c + 1] == null) rTextures[13][c + 1] = getSideFacingPipeInactive(c);
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection sideDirection, ForgeDirection facingDirection, int colorIndex,
        boolean active, boolean redstoneLevel) {
        final int textureIndex;
        if ((mMainFacing.flag & (UP.flag | DOWN.flag)) != 0) { // UP or DOWN
            if (sideDirection == facingDirection) {
                textureIndex = active ? 2 : 3;
            } else {
                textureIndex = switch (sideDirection) {
                    case DOWN -> active ? 6 : 7;
                    case UP -> active ? 4 : 5;
                    default -> active ? 0 : 1;
                };
            }
        } else {
            if (sideDirection == mMainFacing) {
                textureIndex = active ? 2 : 3;
            } else {
                if (showPipeFacing() && sideDirection == facingDirection) {
                    textureIndex = switch (sideDirection) {
                        case DOWN -> active ? 8 : 9;
                        case UP -> active ? 10 : 11;
                        default -> active ? 12 : 13;
                    };
                } else {
                    textureIndex = switch (sideDirection) {
                        case DOWN -> active ? 6 : 7;
                        case UP -> active ? 4 : 5;
                        default -> active ? 0 : 1;
                    };
                }
            }
        }
        return mTextures[textureIndex][colorIndex + 1];
    }

    @Override
    public boolean isValidSlot(int index) {
        return index > 0 && super.isValidSlot(index)
            && index != getCircuitSlot()
            && index != OTHER_SLOT_COUNT + mInputSlotCount + mOutputItems.length;
    }

    @Override
    public boolean isIOSlot(int slot) {
        // Ignore output slots, special slots, battery slots, and circuit slots
        return slot >= getInputSlot() && slot < getInputSlot() + mInputSlotCount;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        // Due to initialization quirks.
        if (mMainFacing == UNKNOWN) return isValidMainFacing(facing);
        return facing != mMainFacing;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        return side != mMainFacing;
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public boolean isLiquidInput(ForgeDirection side) {
        return side != mMainFacing && (mAllowInputFromOutputSide || side != getGTE().getFrontFacing());
    }

    @Override
    public boolean isLiquidOutput(ForgeDirection side) {
        return side != mMainFacing;
    }

    @Override
    public long getMinimumStoredEU() {
        return V[mTier] * 16L;
    }

    @Override
    public long maxEUStore() {
        return V[mTier] * 64L;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxAmperesIn() {
        return ((long) mEUt * 2L) / V[mTier] + 1L;
    }

    @Override
    public List<ItemStack> getNonConsumedInputDisplayItems() {
        List<ItemStack> result = new ArrayList<>();
        for (int i = getInputSlot(), j = i + mInputSlotCount; i < j; i++) {
            ItemStack stack = getStackInSlot(i);
            if (INonConsumedItemDisplay.isDisplayableItem(getRecipeMap(), stack)) {
                result.add(stack);
            }
        }
        return result;
    }

    @Override
    public List<Integer> getPhysicalCircuitNumbers() {
        return IPhysicalCircuitDisplay
            .collectCircuitNumbers(this, getInputSlot(), getInputSlot() + mInputSlotCount, getCircuitSlot());
    }

    @Override
    public int getInputSlot() {
        return OTHER_SLOT_COUNT;
    }

    @Override
    public int getOutputSlot() {
        return OTHER_SLOT_COUNT + mInputSlotCount;
    }

    public int getSpecialSlotIndex() {
        return 3;
    }

    @Override
    public int rechargerSlotStartIndex() {
        return 1;
    }

    @Override
    public int dechargerSlotStartIndex() {
        return 1;
    }

    @Override
    public int rechargerSlotCount() {
        return mCharge ? 1 : 0;
    }

    @Override
    public int dechargerSlotCount() {
        return mDecharge ? 1 : 0;
    }

    @Override
    public int getProgressTime() {
        return mProgresstime;
    }

    @Override
    public int maxProgressTime() {
        return mMaxProgresstime;
    }

    @Override
    public int increaseProgress(int aProgress) {
        mProgresstime += aProgress;
        return mMaxProgresstime - mProgresstime;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return getFillableStack() != null || (getRecipeMap() != null && getRecipeMap().containsInput(aFluid));
    }

    @Override
    public boolean doesFillContainers() {
        return false;
    }

    @Override
    public boolean doesEmptyContainers() {
        return false;
    }

    @Override
    public boolean canTankBeFilled() {
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    @Override
    public FluidStack getDrainableStack() {
        return mFluidOut;
    }

    @Override
    public FluidStack setDrainableStack(FluidStack aFluid) {
        markDirty();
        mFluidOut = aFluid;
        return mFluidOut;
    }

    @Override
    public boolean isDrainableStackSeparate() {
        return true;
    }

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player) {
        if (gte.isClientSide()) {
            return true;
        }

        openGui(player);
        return true;
    }

    @Override
    public void initDefaultModes(NBTTagCompound nbt) {
        ForgeDirection facing = getGTE().getFrontFacing();
        if (isValidMainFacing(facing)) {
            setMainFacing(facing);
        } else {
            // Just in case someone set the facing wrongly, we still initialize main facing.
            setMainFacing(ForgeDirection.WEST);
        }
        if (!getGTE().getWorld().isRemote) {
            final GTClientPreference tPreference = GTMod.proxy.getClientPreference(getGTE().getOwnerUuid());
            if (tPreference != null) {
                mDisableFilter = !tPreference.isSingleBlockInitialFilterEnabled();
                mDisableMultiStack = !tPreference.isSingleBlockInitialMultiStackEnabled();
            }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        nbt.setBoolean("mFluidTransfer", mFluidTransfer);
        nbt.setBoolean("mItemTransfer", mItemTransfer);
        nbt.setBoolean("mAllowInputFromOutputSide", mAllowInputFromOutputSide);
        nbt.setBoolean("mDisableFilter", mDisableFilter);
        nbt.setBoolean("mDisableMultiStack", mDisableMultiStack);
        nbt.setInteger("mEUt", mEUt);
        nbt.setInteger("mMainFacing", mMainFacing.ordinal());
        nbt.setInteger("mProgresstime", mProgresstime);
        nbt.setInteger("mMaxProgresstime", mMaxProgresstime);
        if (mOutputFluid != null) nbt.setTag("mOutputFluid", mOutputFluid.writeToNBT(new NBTTagCompound()));
        if (mFluidOut != null) nbt.setTag("mFluidOut", mFluidOut.writeToNBT(new NBTTagCompound()));

        for (int i = 0; i < mOutputItems.length; i++)
            if (mOutputItems[i] != null) GTUtility.saveItem(nbt, "mOutputItem" + i, mOutputItems[i]);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        mFluidTransfer = nbt.getBoolean("mFluidTransfer");
        mItemTransfer = nbt.getBoolean("mItemTransfer");
        mAllowInputFromOutputSide = nbt.getBoolean("mAllowInputFromOutputSide");
        mDisableFilter = nbt.getBoolean("mDisableFilter");
        mDisableMultiStack = nbt.getBoolean("mDisableMultiStack");
        mEUt = nbt.getInteger("mEUt");
        mMainFacing = ForgeDirection.getOrientation(nbt.getInteger("mMainFacing"));
        mProgresstime = nbt.getInteger("mProgresstime");
        mMaxProgresstime = nbt.getInteger("mMaxProgresstime");
        mOutputFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("mOutputFluid"));
        mFluidOut = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("mFluidOut"));

        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = GTUtility.loadItem(nbt, "mOutputItem" + i);
    }

    /**
     * Returns the error ID displayed on the GUI.
     */
    public int getErrorDisplayID() {
        return errorDisplayID;
    }

    /**
     * Sets the error ID displayed on the GUI.
     */
    public void setErrorDisplayID(int errorID) {
        this.errorDisplayID = errorID;
    }

    @Override
    public void onClientSoundStateChanged() {
        if (activitySoundLoop != null && activitySoundLoop.isDonePlaying()) {
            activitySoundLoop = null;
        }
        updateSounds(getActivitySoundLoop());
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);

        if (gte.isServerSide()) {
            mCharge = gte.getStoredEU() / 2 > gte.getEUCapacity() / 3;
            mDecharge = gte.getStoredEU() < gte.getEUCapacity() / 3;

            boolean tSucceeded = false;
            boolean isActive = mMaxProgresstime > 0;

            if (isActive && (mProgresstime >= 0 || gte.isAllowedToWork())) {
                markDirty();
                if (mProgresstime < 0 || drainEnergyForProcess(mEUt)) {
                    gte.setActive(mProgresstime >= 0);
                    if (++mProgresstime >= mMaxProgresstime) {
                        for (int i = 0; i < mOutputItems.length; i++) for (int j = 0; j < mOutputItems.length; j++)
                            if (gte.addStackToSlot(getOutputSlot() + ((j + i) % mOutputItems.length), mOutputItems[i]))
                                break;
                        if (mOutputFluid != null)
                            if (getDrainableStack() == null) setDrainableStack(mOutputFluid.copy());
                            else if (mOutputFluid.isFluidEqual(getDrainableStack()))
                                getDrainableStack().amount += mOutputFluid.amount;
                        Arrays.fill(mOutputItems, null);
                        mOutputFluid = null;
                        mEUt = 0;
                        mProgresstime = 0;
                        mMaxProgresstime = 0;
                        mStuttering = false;
                        tSucceeded = true;
                        endProcess();
                    }
                    if (mProgresstime > 5) mStuttering = false;
                } else {
                    if (!mStuttering) {
                        stutterProcess();
                        if (canHaveInsufficientEnergy()) mProgresstime = -100;
                        mStuttering = true;
                    }
                }
            } else {
                gte.setActive(false);
            }

            boolean tRemovedOutputFluid = false;

            if (doesAutoOutputFluids() && getDrainableStack() != null
                && gte.getFrontFacing() != mMainFacing
                && (tSucceeded || tick % 20 == 0)) {
                IFluidHandler tTank = gte.getITankContainerAtSide(gte.getFrontFacing());
                if (tTank != null) {
                    FluidStack tDrained = drain(1000, false);
                    if (tDrained != null) {
                        final int tFilledAmount = tTank.fill(gte.getBackFacing(), tDrained, false);
                        if (tFilledAmount > 0) tTank.fill(gte.getBackFacing(), drain(tFilledAmount, true), true);
                    }
                }
                if (getDrainableStack() == null) tRemovedOutputFluid = true;
            }

            if (doesAutoOutput() && !isOutputEmpty()
                && gte.getFrontFacing() != mMainFacing
                && (tSucceeded || mOutputBlocked % 300 == 1 || gte.hasInventoryBeenModified() || tick % 600 == 0)) {
                GTItemTransfer transfer = new GTItemTransfer();

                transfer.outOfMachine(this, gte.getFrontFacing());
                transfer.dropItems(this);

                transfer.setStacksToTransfer(mOutputItems.length);

                transfer.transfer();
            }

            if (mOutputBlocked != 0) if (isOutputEmpty()) mOutputBlocked = 0;
            else mOutputBlocked++;

            if (allowToCheckRecipe()) {
                if (mMaxProgresstime <= 0 && gte.isAllowedToWork()
                    && (tRemovedOutputFluid || tSucceeded
                        || gte.hasInventoryBeenModified()
                        || tick % 600 == 0
                        || gte.hasWorkJustBeenEnabled())
                    && hasEnoughEnergyToCheckRecipe()) {
                    if (checkRecipe() == FOUND_AND_SUCCESSFULLY_USED_RECIPE) {
                        if (getSpecialSlot() != null && getSpecialSlot().stackSize <= 0)
                            inventory[getSpecialSlotIndex()] = null;
                        for (int i = getInputSlot(), j = i + mInputSlotCount; i < j; i++)
                            if (inventory[i] != null && inventory[i].stackSize <= 0) inventory[i] = null;
                        for (int i = 0; i < mOutputItems.length; i++) {
                            mOutputItems[i] = GTUtility.copyOrNull(mOutputItems[i]);
                            if (mOutputItems[i] != null && mOutputItems[i].stackSize > 64)
                                mOutputItems[i].stackSize = 64;
                            mOutputItems[i] = GTOreDictUnificator.get(true, mOutputItems[i]);
                        }
                        if (mFluid != null && mFluid.amount <= 0) mFluid = null;
                        mMaxProgresstime = Math.max(1, mMaxProgresstime);
                        startProcess();
                    } else {
                        mMaxProgresstime = 0;
                        Arrays.fill(mOutputItems, null);
                        mOutputFluid = null;
                    }
                }
            } else {
                if (!mStuttering) {
                    stutterProcess();
                    gte.setActive(false);
                    mStuttering = true;
                }
            }
        }
        // Only using mNeedsSteamVenting right now and assigning it to 64 to space in the range for more single block
        // machine problems.
        // Value | Class | Field
        // 1 | MTEBasicMachine | mStuttering
        // 64 | MTEBasicMachineBronze | mNeedsSteamVenting
        setErrorDisplayID((getErrorDisplayID() & ~127)); // | (mStuttering ? 1 :
                                                         // 0));
    }

    protected boolean hasEnoughEnergyToCheckRecipe() {
        return getGTE().isUniversalEnergyStored(getMinimumStoredEU() / 2);
    }

    protected boolean drainEnergyForProcess(long aEUt) {
        return getGTE().decreaseStoredEnergyUnits(aEUt, false);
    }

    /**
     * Calculates overclock based on {@link #overclockDescriber}.
     */
    protected void calculateCustomOverclock(GTRecipe recipe) {
        OverclockCalculator calculator = overclockDescriber.createCalculator(
            new OverclockCalculator().setRecipeEUt(recipe.mEUt)
                .setDuration(recipe.mDuration),
            recipe);
        calculator.calculate();
        mEUt = (int) calculator.getConsumption();
        mMaxProgresstime = calculator.getDuration();
    }

    /**
     * Helper method for calculating simple overclock.
     */
    protected void calculateOverclockedNess(int eut, int duration) {
        OverclockCalculator calculator = new OverclockCalculator().setRecipeEUt(eut)
            .setEUt(V[mTier] * mAmperage)
            .setDuration(duration)
            .calculate();
        mEUt = (int) calculator.getConsumption();
        mMaxProgresstime = calculator.getDuration();
    }

    protected ItemStack getSpecialSlot() {
        return inventory[getSpecialSlotIndex()];
    }

    protected ItemStack getOutputAt(int aIndex) {
        return inventory[getOutputSlot() + aIndex];
    }

    protected ItemStack[] getAllOutputs() {
        ItemStack[] rOutputs = new ItemStack[mOutputItems.length];
        for (int i = 0; i < mOutputItems.length; i++) rOutputs[i] = getOutputAt(i);
        return rOutputs;
    }

    protected boolean canOutput(GTRecipe aRecipe) {
        return aRecipe != null && (aRecipe.mNeedsEmptyOutput ? isOutputEmpty() && getDrainableStack() == null
            : canOutput(aRecipe.getFluidOutput(0)) && canOutput(aRecipe.mOutputs));
    }

    protected boolean canOutput(ItemStack... aOutputs) {
        if (aOutputs == null) return true;
        ItemStack[] tOutputSlots = getAllOutputs();
        for (int i = 0; i < tOutputSlots.length && i < aOutputs.length; i++)
            if (tOutputSlots[i] != null && aOutputs[i] != null
                && (!GTUtility.areStacksEqual(tOutputSlots[i], aOutputs[i], false)
                    || tOutputSlots[i].stackSize + aOutputs[i].stackSize > tOutputSlots[i].getMaxStackSize())) {
                        mOutputBlocked++;
                        return false;
                    }
        return true;
    }

    protected boolean canOutput(FluidStack aOutput) {
        if (aOutput == null) return true;
        FluidStack drainableStack = getDrainableStack();
        if (drainableStack != null && !drainableStack.isFluidEqual(aOutput)) return false;
        return (drainableStack != null ? drainableStack.amount : 0) + aOutput.amount <= getCapacity();
    }

    protected ItemStack getInputAt(int aIndex) {
        return inventory[getInputSlot() + aIndex];
    }

    protected ItemStack[] getAllInputs() {
        int tRealInputSlotCount = this.mInputSlotCount + (allowSelectCircuit() ? 1 : 0);
        ItemStack[] rInputs = new ItemStack[tRealInputSlotCount];
        for (int i = 0; i < mInputSlotCount; i++) rInputs[i] = getInputAt(i);
        if (allowSelectCircuit()) rInputs[mInputSlotCount] = getStackInSlot(getCircuitSlot());
        return rInputs;
    }

    protected boolean isOutputEmpty() {
        boolean rIsEmpty = true;
        for (ItemStack tOutputSlotContent : getAllOutputs()) if (tOutputSlotContent != null) {
            rIsEmpty = false;
            break;
        }
        return rIsEmpty;
    }

    @Override
    public void onValueUpdate(byte value) {
        mMainFacing = ForgeDirection.getOrientation(value);
    }

    @Override
    public byte getUpdateData() {
        return (byte) mMainFacing.ordinal();
    }

    @Override
    public void doSound(byte index, double x, double y, double z) {
        super.doSound(index, x, y, z);
        if (index == 8) GTUtility.doSoundAtClient(SoundResource.IC2_MACHINES_INTERRUPT_ONE, 100, 1.0F, x, y, z);
    }

    public boolean doesAutoOutput() {
        return mItemTransfer;
    }

    public boolean doesAutoOutputFluids() {
        return mFluidTransfer;
    }

    public boolean allowToCheckRecipe() {
        return true;
    }

    public boolean showPipeFacing() {
        return true;
    }

    /**
     * Called whenever the Machine successfully started a Process, useful for Sound Effects
     */
    public void startProcess() {
        //
    }

    /**
     * Called whenever the Machine successfully finished a Process, useful for Sound Effects
     */
    public void endProcess() {
        //
    }

    /**
     * Called whenever the Machine aborted a Process, useful for Sound Effects
     */
    public void abortProcess() {
        //
    }

    /**
     * Called whenever the Machine aborted a Process but still works on it, useful for Sound Effects
     */
    public void stutterProcess() {
        if (useStandardStutterSound()) sendSound((byte) 8);
    }

    /**
     * Returns the sound resource to use for the activity loop.
     * Subclasses can override to provide their own.
     */
    @SideOnly(Side.CLIENT)
    protected SoundResource getActivitySoundLoop() {
        return null;
    }

    /**
     * Starts the activity sound loop if it isn't already playing.
     */
    @SideOnly(Side.CLIENT)
    protected void updateSounds(SoundResource activitySound) {
        if (activitySound == null) return;

        if (getGTE().isActive() && !getGTE().isMuffled()) {
            if (activitySoundLoop == null) {
                activitySoundLoop = new GTSoundLoop(activitySound.resourceLocation, getGTE(), false, true);
                Minecraft.getMinecraft()
                    .getSoundHandler()
                    .playSound(activitySoundLoop);
            }
        } else {
            stopActivitySound();
        }
    }

    /**
     * Stops the activity sound loop if playing.
     */
    @SideOnly(Side.CLIENT)
    protected void stopActivitySound() {
        if (activitySoundLoop != null) {
            activitySoundLoop.setFadeMe(true);
            activitySoundLoop = null;
        }
    }

    /**
     * If this Machine can have the Insufficient Energy Line Problem
     */
    public boolean canHaveInsufficientEnergy() {
        return true;
    }

    public boolean useStandardStutterSound() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.progress",
                EnumChatFormatting.GREEN + formatNumber((mProgresstime / 20)) + EnumChatFormatting.RESET,
                EnumChatFormatting.YELLOW + formatNumber(mMaxProgresstime / 20) + EnumChatFormatting.RESET),
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.energy",
                EnumChatFormatting.GREEN + formatNumber(getGTE().getStoredEU()) + EnumChatFormatting.RESET,
                EnumChatFormatting.YELLOW + formatNumber(getGTE().getEUCapacity()) + EnumChatFormatting.RESET),
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.currently_uses",
                EnumChatFormatting.RED + formatNumber(mEUt) + EnumChatFormatting.RESET,
                EnumChatFormatting.RED + formatNumber(mEUt == 0 ? 0 : mAmperage) + EnumChatFormatting.RESET) };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z,
        ItemStack tool) {
        if (side == getGTE().getFrontFacing() || side == mMainFacing) {
            if (player.isSneaking()) {
                mDisableFilter = !mDisableFilter;
                GTUtility.sendChatTrans(player, "GT5U.hatch.disableFilter." + mDisableFilter);
            } else {
                mAllowInputFromOutputSide = !mAllowInputFromOutputSide;
                GTUtility.sendChatTrans(
                    player,
                    mAllowInputFromOutputSide ? "gt.interact.desc.input_from_output_on"
                        : "gt.interact.desc.input_from_output_off");
            }
        }
    }

    @Override
    public boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {
        if (!player.isSneaking() || wrenchingSide != mMainFacing)
            return super.onSolderingToolRightClick(side, wrenchingSide, player, x, y, z, tool);
        mDisableMultiStack = !mDisableMultiStack;
        GTUtility.sendChatTrans(player, "GT5U.hatch.disableMultiStack." + mDisableMultiStack);
        return true;
    }

    @Override
    public boolean allowCoverOnSide(ForgeDirection side, ItemStack coverItem) {
        if (side != mMainFacing) return true;
        return CoverRegistry.getCoverPlacer(coverItem)
            .isGuiClickable();
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return side != mMainFacing && index >= getOutputSlot() && index < getOutputSlot() + mOutputItems.length;
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        if (side == mMainFacing) return false;
        if (index < getInputSlot()) return false;
        if (index >= getInputSlot() + mInputSlotCount) return false;
        if (!mAllowInputFromOutputSide && side == gte.getFrontFacing()) return false;

        for (int i = getInputSlot(), j = i + mInputSlotCount; i < j; i++) {
            if (GTUtility.areStacksEqual(GTOreDictUnificator.get(itemStack), inventory[i]) && mDisableMultiStack) {
                return i == index;
            }
        }

        return mDisableFilter || allowPutStackValidated(gte, index, side, itemStack);
    }

    /**
     * Test if given stack can be inserted into specified slot. If mDisableMultiStack is false, before execution of this
     * method it is ensured there is no such kind of item inside any input slots already. Otherwise, you don't need to
     * check for it anyway.
     */
    protected boolean allowPutStackValidated(IGTE aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return !mDisableMultiStack || inventory[aIndex] == null;
    }

    @Override
    public boolean allowSelectCircuit() {
        return false;
    }

    protected final ItemStack[] appendSelectedCircuit(ItemStack... inputs) {
        if (allowSelectCircuit()) {
            ItemStack circuit = getStackInSlot(getCircuitSlot());
            if (circuit != null) {
                ItemStack[] result = Arrays.copyOf(inputs, inputs.length + 1);
                result[inputs.length] = circuit;
                return result;
            }
        }
        return inputs;
    }

    @Override
    public int getCircuitSlot() {
        return 4;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return null;
    }

    /**
     * Override this to check the Recipes yourself, super calls to this could be useful if you just want to add a
     * special case
     * <p/>
     * I thought about Enum too, but Enum doesn't add support for people adding other return Systems.
     * <p/>
     * Funny how Eclipse marks the word Enum as not correctly spelled.
     *
     * @return see constants above
     */
    public int checkRecipe() {
        return checkRecipe(false);
    }

    public static boolean isValidForLowGravity(GTRecipe tRecipe, int dimId) {
        if (FakeCleanroom.isLowGravBypassEnabled()) return true;
        return // TODO check or get a better solution
        DimensionManager.getProvider(dimId)
            .getClass()
            .getName()
            .contains("Orbit")
            || DimensionManager.getProvider(dimId)
                .getClass()
                .getName()
                .endsWith("Space")
            || DimensionManager.getProvider(dimId)
                .getClass()
                .getName()
                .endsWith("Asteroids")
            || DimensionManager.getProvider(dimId)
                .getClass()
                .getName()
                .endsWith("SS")
            || DimensionManager.getProvider(dimId)
                .getClass()
                .getName()
                .contains("SpaceStation")
            || DimensionManager.getProvider(dimId)
                .getClass()
                .getName()
                .contains("Mothership");
    }

    /**
     *
     * @param skipOC disables OverclockedNess calculation and check - if you do you must implement your own method...
     * @return DID_NOT_FIND_RECIPE = 0, FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS = 1,
     *         FOUND_AND_SUCCESSFULLY_USED_RECIPE = 2;
     */
    public int checkRecipe(boolean skipOC) {
        RecipeMap<?> tMap = getRecipeMap();
        if (tMap == null) return DID_NOT_FIND_RECIPE;
        GTRecipe tRecipe = tMap.findRecipeQuery()
            .items(getAllInputs())
            .fluids(getFillableStack())
            .specialSlot(getSpecialSlot())
            .voltage(V[mTier])
            .cachedRecipe(mLastRecipe)
            .find();
        if (tRecipe == null) {
            return DID_NOT_FIND_RECIPE;
        }
        if (tRecipe.getMetadataOrDefault(EXPLODE, false) && getGTE() != null) {
            getGTE().doExplosion(V[mTier] * 4);
            return DID_NOT_FIND_RECIPE;
        }
        if (tRecipe.getMetadataOrDefault(ON_FIRE, false) && getGTE() != null) {
            getGTE().setOnFire();
            return DID_NOT_FIND_RECIPE;
        }
        if (tRecipe.mCanBeBuffered) mLastRecipe = tRecipe;
        if (!canOutput(tRecipe)) {
            mOutputBlocked++;
            return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
        }
        ICleanroom cleanroom = cleanroomReference.getCleanroom();
        if (tRecipe.mSpecialValue == -200 || tRecipe.mSpecialValue == -300) {
            if (cleanroom == null || !cleanroom.isValidCleanroom() || cleanroom.getCleanness() == 0) {
                return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
            }
        }
        if (!tRecipe.isRecipeInputEqual(true, new FluidStack[] { getFillableStack() }, getAllInputs()))
            return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = tRecipe.rollOutput(getGTE(), i);
        if (tRecipe.mSpecialValue == -200 || tRecipe.mSpecialValue == -300) {
            assert cleanroom != null;
            for (int i = 0; i < mOutputItems.length; i++)
                if (mOutputItems[i] != null && getGTE().getRandomNumber(10000) > cleanroom.getCleanness()) {
                    mOutputItems[i] = null;
                }
        }
        if (getGTE().getRandomNumber(10000) < tRecipe.getFluidOutputChance(0)) mOutputFluid = tRecipe.getFluidOutput(0);
        if (!skipOC) {
            calculateCustomOverclock(tRecipe);
            // In case recipe is too OP for that machine
            if (mMaxProgresstime == Integer.MAX_VALUE - 1 && mEUt == Integer.MAX_VALUE - 1)
                return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
        }
        return FOUND_AND_SUCCESSFULLY_USED_RECIPE;
    }

    public ITexture[] getSideFacingActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getSideFacingInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getFrontFacingActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getFrontFacingInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getTopFacingActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getTopFacingInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getBottomFacingActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getBottomFacingInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1] };
    }

    public ITexture[] getBottomFacingPipeActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1], TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    public ITexture[] getBottomFacingPipeInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1], TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    public ITexture[] getTopFacingPipeActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1], TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    public ITexture[] getTopFacingPipeInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1], TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    public ITexture[] getSideFacingPipeActive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1], TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    public ITexture[] getSideFacingPipeInactive(byte aColor) {
        return new ITexture[] { MACHINE_CASINGS[mTier][aColor + 1], TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        final NBTTagCompound tag = accessor.getNBTData();
        final IGTE gte = getGTE();
        long eu = tag.getLong("Eu");
        long maxEu = tag.getLong("MaxEu");
        int euT = tag.getInteger("eut");
        boolean isActive = tag.getBoolean("isActiveSingleBlock");
        String euText = formatNumber(eu) + " / " + formatNumber(maxEu);
        List<ItemStack> inputItems = new ArrayList<>();
        List<ItemStack> outputItems = new ArrayList<>();

        getWailaItemsWithNBTTag(getAllInputs(), "inputItems", inputItems, tag);
        getWailaItemsWithNBTTag(getAllOutputs(), "outputItems", outputItems, tag);

        inputItems.sort(
            Comparator.<ItemStack, Boolean>comparing(stack -> !(stack.getItem() instanceof ItemIntegratedCircuit))
                .thenComparingInt(ItemStack::getItemDamage));

        list.add(TTRenderBar.create(euText, 0xFFF5E32C, 0xFF9C7E00, (double) eu / maxEu));

        if (tag.getBoolean("stutteringSingleBlock")) {
            list.add(translateToLocal(getWailaStutteringLine(tag)));
        } else {
            if (isActive) {
                list.add(
                    GTWaila.getMachineProgressString(
                        true,
                        tag.getBoolean("isAllowedToWorkSingleBlock"),
                        tag.getInteger("maxProgressSingleBlock"),
                        tag.getInteger("progressSingleBlock")));

                if (!isSteampowered()) {
                    if (euT > 0) {
                        double exactAmps = GTUtility.getExactAmperageForTier(euT, (byte) getInputTier());
                        String ampString = String.format("%.2f", exactAmps);

                        list.add(
                            translateToLocalFormatted(
                                "GT5U.waila.energy.use_with_amperage",
                                formatNumber(euT),
                                ampString,
                                GTUtility.getColoredTierNameFromTier((byte) getInputTier())));
                    } else if (euT < 0) {
                        list.add(
                            translateToLocalFormatted(
                                "GT5U.waila.energy.produce_with_amperage",
                                formatNumber(-euT),
                                GTUtility.getAmperageForTier(-euT, (byte) getOutputTier()),
                                GTUtility.getColoredTierNameFromTier((byte) getOutputTier())));
                    }
                } else {
                    if (euT > 0) {
                        list.add(
                            translateToLocalFormatted(
                                "GTPP.waila.steam.use",
                                formatNumber(euT * 40L),
                                GTUtility.getColoredTierNameFromVoltage(euT)));
                    } else if (euT < 0) {
                        list.add(
                            translateToLocalFormatted(
                                "GTPP.waila.steam.use",
                                formatNumber(-euT * 40L),
                                GTUtility.getColoredTierNameFromVoltage(-euT)));
                    }
                }
            }
        }

        if (!inputItems.isEmpty()) {
            list.add(StatCollector.translateToLocal("GT5U.waila.machine.recipe_inputs"));
            getWailaRenderItems(list, inputItems);
        }

        list.add(
            StatCollector.translateToLocalFormatted(
                "GT5U.waila.machine.recipe_outputs",
                getFacingNameLocalized(tag.getInteger("outputFacingSingleBlock"))));

        if (!outputItems.isEmpty()) {
            getWailaRenderItems(list, outputItems);
        }

        if (Client.waila.showFacing) {
            if (gte != null) {
                list.add(
                    StatCollector.translateToLocalFormatted(
                        "GT5U.waila.facing",
                        getFacingNameLocalized(tag.getInteger("mainFacingSingleBlock"))));
            }
        }
    }

    private static @NotNull String getWailaStutteringLine(NBTTagCompound tag) {
        return tag.getBoolean("blockedSteamVentSingleBlock") ? "GT5U.waila.status.obstructed_steam_vent"
            : "GT5U.waila.status.insufficient_energy";
    }

    private void getWailaItemsWithNBTTag(ItemStack[] itemStacks, String tag, List<ItemStack> itemStackList,
        NBTTagCompound nbtTagCompound) {
        for (int i = 0; i < itemStacks.length; i++) {
            if (nbtTagCompound.hasKey(tag + i)) {
                ItemStack inputStack = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(tag + i));

                if (inputStack == null) {
                    continue;
                }

                if (inputStack.stackSize == 0) {
                    inputStack.stackSize = 1;
                }

                itemStackList.add(inputStack);
            }
        }
    }

    private void getWailaRenderItems(List<String> list, List<ItemStack> itemStacks) {
        for (int i = 0; i < itemStacks.size(); i++) {
            if (i == 5) {
                list.add(
                    StatCollector.translateToLocalFormatted("GT5U.waila.machine.more_items", itemStacks.size() - i));
                break;
            }

            ItemStack item = itemStacks.get(i);

            list.add(
                TTRenderStack.create(item, true) + StatCollector.translateToLocalFormatted(
                    "GT5U.waila.machine.render_item",
                    item.stackSize,
                    item.getDisplayName()));
        }
    }

    private void getWailaNBTTagWithItems(ItemStack[] itemStacks, String nameTag, NBTTagCompound tag) {
        for (int i = 0; i < itemStacks.length; i++) {
            ItemStack itemStack = itemStacks[i];
            if (itemStack != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemStack.writeToNBT(itemTag);
                tag.setTag(nameTag + i, itemTag);
            }
        }
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        final IGTE gte = getGTE();
        ItemStack[] inputs = getAllInputs();
        ItemStack[] outputs = getAllOutputs();

        tag.setInteger("progressSingleBlock", mProgresstime);
        tag.setInteger("maxProgressSingleBlock", mMaxProgresstime);
        tag.setInteger("mainFacingSingleBlock", mMainFacing.ordinal());
        tag.setBoolean("stutteringSingleBlock", mStuttering);
        tag.setBoolean("blockedSteamVentSingleBlock", cannotVentSteam());

        if (gte != null) {
            tag.setBoolean("isActiveSingleBlock", gte.isActive());
            tag.setBoolean("isAllowedToWorkSingleBlock", gte.isAllowedToWork());
            tag.setLong("Eu", gte.getStoredEU());
            tag.setLong("MaxEu", gte.getEUCapacity());
            tag.setInteger(
                "outputFacingSingleBlock",
                gte.getFrontFacing()
                    .ordinal());
            if (gte.isActive()) {
                tag.setInteger("eut", mEUt);
            }
        }

        getWailaNBTTagWithItems(inputs, "inputItems", tag);
        getWailaNBTTagWithItems(outputs, "outputItems", tag);
    }

    @Nonnull
    @Override
    public OverclockDescriber getOverclockDescriber() {
        return overclockDescriber;
    }

    // GUI stuff

    @Override
    public int getCircuitSlotX() {
        return 153;
    }

    @Override
    public int getCircuitSlotY() {
        return 63;
    }

    @Override
    public void addGregTechLogo(ModularWindow.Builder builder) {
        if (getRecipeMap() != null) {
            getRecipeMap().getFrontend()
                .addGregTechLogo(builder, new Pos2d(0, 0));
        } else {
            builder.widget(
                new DrawableWidget().setDrawable(getGUITextureSet().getGregTechLogo())
                    .setSize(17, 17)
                    .setPos(152, 63));
        }
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        if (!isSteampowered()) {
            builder.widget(createFluidAutoOutputButton());
            builder.widget(createItemAutoOutputButton());
        } else {
            builder.widget(createSteamProgressBar(builder));
        }

        BasicUIProperties uiProperties = getUIProperties();
        addIOSlots(builder, uiProperties);

        builder.widget(createChargerSlot(79, 62));

        addProgressBar(builder, uiProperties);

        builder.widget(createMuffleButton());

        builder.widget(
            createErrorStatusArea(
                builder,
                isSteampowered() ? GTUITextures.PICTURE_STALLED_STEAM : GTUITextures.PICTURE_STALLED_ELECTRICITY));
    }

    /**
     * Override to specify UI properties if this machine doesn't work with recipemap.
     */
    protected BasicUIProperties getUIProperties() {
        if (getRecipeMap() != null) {
            BasicUIProperties originalProperties = getRecipeMap().getFrontend()
                .getUIProperties();
            return originalProperties.toBuilder()
                .maxItemInputs(mInputSlotCount)
                .maxItemOutputs(mOutputItems.length)
                .maxFluidInputs(Math.min(originalProperties.maxFluidInputs, 1))
                .maxFluidOutputs(Math.min(originalProperties.maxFluidOutputs, 1))
                .build();
        }
        return BasicUIProperties.builder()
            .maxItemInputs(mInputSlotCount)
            .maxItemOutputs(mOutputItems.length)
            .maxFluidInputs(getCapacity() != 0 ? 1 : 0)
            .maxFluidOutputs(0)
            .build();
    }

    /**
     * Adds item I/O, special item, and fluid I/O slots.
     */
    protected void addIOSlots(ModularWindow.Builder builder, BasicUIProperties uiProperties) {
        UIHelper.forEachSlots(
            (i, backgrounds, pos) -> builder.widget(createItemInputSlot(i, backgrounds, pos)),
            (i, backgrounds, pos) -> builder.widget(createItemOutputSlot(i, backgrounds, pos)),
            (i, backgrounds, pos) -> builder.widget(createSpecialSlot(backgrounds, pos, uiProperties)),
            (i, backgrounds, pos) -> builder.widget(createFluidInputSlot(backgrounds, pos)),
            (i, backgrounds, pos) -> builder.widget(createFluidOutputSlot(backgrounds, pos)),
            getGUITextureSet().getItemSlot(),
            getGUITextureSet().getFluidSlot(),
            uiProperties,
            uiProperties.maxItemInputs,
            uiProperties.maxItemOutputs,
            uiProperties.maxFluidInputs,
            uiProperties.maxFluidOutputs,
            getTieredVariant(),
            Pos2d.ZERO);
    }

    protected void addProgressBar(ModularWindow.Builder builder, BasicUIProperties uiProperties) {
        boolean isSteamPowered = isSteampowered();
        RecipeMap<?> recipeMap = getRecipeMap();
        if (!isSteamPowered && uiProperties.progressBarTexture == null) {
            if (recipeMap != null) {
                // Require progress bar texture for machines working with recipemap, otherwise permit
                throw new RuntimeException("Missing progressbar texture for " + recipeMap.unlocalizedName);
            } else {
                return;
            }
        }
        if (isSteamPowered && uiProperties.progressBarTextureSteam == null) {
            if (recipeMap != null) {
                throw new RuntimeException("Missing steam progressbar texture for " + recipeMap.unlocalizedName);
            } else {
                return;
            }
        }

        builder.widget(
            setNEITransferRect(
                new ProgressBar()
                    .setProgress(() -> maxProgressTime() != 0 ? (float) getProgressTime() / maxProgressTime() : 0)
                    .setTexture(
                        isSteamPowered ? uiProperties.progressBarTextureSteam.get(getTieredVariant())
                            : uiProperties.progressBarTexture.get(),
                        uiProperties.progressBarImageSize)
                    .setDirection(uiProperties.progressBarDirection)
                    .setPos(uiProperties.progressBarPos)
                    .setSize(uiProperties.progressBarSize),
                uiProperties.neiTransferRectId));
        addProgressBarSpecialTextures(builder, uiProperties);
    }

    /**
     * Override this as needed instead of calling.
     */
    protected SlotWidget createItemInputSlot(int index, IDrawable[] backgrounds, Pos2d pos) {
        return (SlotWidget) new SlotWidget(inventoryHandler, getInputSlot() + index).setAccess(true, true)
            .setBackground(backgrounds)
            .setPos(pos);
    }

    /**
     * Override this as needed instead of calling.
     */
    protected SlotWidget createItemOutputSlot(int index, IDrawable[] backgrounds, Pos2d pos) {
        return (SlotWidget) new SlotWidget(inventoryHandler, getOutputSlot() + index).setAccess(true, false)
            .setBackground(backgrounds)
            .setPos(pos);
    }

    /**
     * Override this as needed instead of calling.
     */
    protected SlotWidget createSpecialSlot(IDrawable[] backgrounds, Pos2d pos, BasicUIProperties uiProperties) {
        return (SlotWidget) new SlotWidget(inventoryHandler, getSpecialSlotIndex()).setAccess(true, true)
            .disableShiftInsert()
            .setGTTooltip(
                () -> tooltipDataCache
                    .getData(uiProperties.useSpecialSlot ? SPECIAL_SLOT_TOOLTIP : UNUSED_SLOT_TOOLTIP))
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setBackground(backgrounds)
            .setPos(pos);
    }

    protected FluidSlotWidget createFluidInputSlot(IDrawable[] backgrounds, Pos2d pos) {
        return (FluidSlotWidget) new FluidSlotWidget(fluidTank).setBackground(backgrounds)
            .setPos(pos);
    }

    protected FluidSlotWidget createFluidOutputSlot(IDrawable[] backgrounds, Pos2d pos) {
        return (FluidSlotWidget) new FluidSlotWidget(fluidOutputTank).setInteraction(true, false)
            .setBackground(backgrounds)
            .setPos(pos);
    }

    @Override
    protected SlotWidget createChargerSlot(int x, int y) {
        if (isSteampowered()) {
            return (SlotWidget) createChargerSlot(x, y, UNUSED_SLOT_TOOLTIP, GTValues.emptyStringArray)
                .setBackground(getGUITextureSet().getItemSlot());
        } else {
            return super.createChargerSlot(x, y);
        }
    }

    protected CycleButtonWidget createItemAutoOutputButton() {
        return (CycleButtonWidget) new CycleButtonWidget().setToggle(() -> mItemTransfer, val -> mItemTransfer = val)
            .setStaticTexture(GTUITextures.OVERLAY_BUTTON_AUTOOUTPUT_ITEM)
            .setVariableBackground(GTUITextures.BUTTON_STANDARD_TOGGLE)
            .setGTTooltip(() -> tooltipDataCache.getData(ITEM_TRANSFER_TOOLTIP))
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setPos(25, 62)
            .setSize(18, 18);
    }

    protected CycleButtonWidget createFluidAutoOutputButton() {
        return (CycleButtonWidget) new CycleButtonWidget().setToggle(() -> mFluidTransfer, val -> mFluidTransfer = val)
            .setStaticTexture(GTUITextures.OVERLAY_BUTTON_AUTOOUTPUT_FLUID)
            .setVariableBackground(GTUITextures.BUTTON_STANDARD_TOGGLE)
            .setGTTooltip(() -> tooltipDataCache.getData(FLUID_TRANSFER_TOOLTIP))
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setPos(7, 62)
            .setSize(18, 18);
    }

    // Used for ui syncing
    private long getSteamVar = 0;

    protected Widget createSteamProgressBar(ModularWindow.Builder builder) {
        builder.widget(new FakeSyncWidget.LongSyncer(this::getSteamVar, val -> getSteamVar = val));

        boolean isSteel = getTieredVariant() == TieredVariant.STEEL;
        builder.widget(
            new DrawableWidget().setDrawable(isSteel ? GTUITextures.STEAM_GAUGE_BG_STEEL : GTUITextures.STEAM_GAUGE_BG)
                .dynamicTooltip(
                    () -> Collections.singletonList(
                        translateToLocalFormatted(
                            STEAM_AMOUNT_LANGKEY,
                            numberFormat.format(getSteamVar * 2), // internal steam storage is done at a 2:1 ratio
                            numberFormat.format(maxSteamStore() * 2))))// internal steam storage is done at a 2:1 ratio
                .setTooltipShowUpDelay(TOOLTIP_DELAY)
                .setUpdateTooltipEveryTick(true)
                .setSize(48, 42)
                .setPos(-48, -8));

        return new DrawableWidget().setDrawable(new CircularGaugeDrawable(() -> (float) getSteamVar / maxSteamStore()))
            .setPos(-48 + 21, -8 + 21)
            .setSize(18, 4);
    }

    protected Widget setNEITransferRect(Widget widget, String transferRectID) {
        if (GTUtility.isStringInvalid(transferRectID)) {
            return widget;
        }
        final String transferRectTooltip;
        if (isSteampowered()) {
            transferRectTooltip = translateToLocalFormatted(
                NEI_TRANSFER_STEAM_TOOLTIP,
                overclockDescriber.getTierString());
        } else {
            transferRectTooltip = translateToLocalFormatted(
                NEI_TRANSFER_VOLTAGE_TOOLTIP,
                overclockDescriber.getTierString());
        }
        widget.setNEITransferRect(transferRectID, new Object[] { overclockDescriber }, transferRectTooltip);
        return widget;
    }

    protected void addProgressBarSpecialTextures(ModularWindow.Builder builder, BasicUIProperties uiProperties) {
        if (isSteampowered()) {
            for (Pair<SteamTexture, Pair<Size, Pos2d>> specialTexture : uiProperties.specialTexturesSteam) {
                builder.widget(
                    new DrawableWidget().setDrawable(
                        specialTexture.getLeft()
                            .get(getTieredVariant()))
                        .setSize(
                            specialTexture.getRight()
                                .getLeft())
                        .setPos(
                            specialTexture.getRight()
                                .getRight()));
            }
        } else {
            for (Pair<IDrawable, Pair<Size, Pos2d>> specialTexture : uiProperties.specialTextures) {
                builder.widget(
                    new DrawableWidget().setDrawable(specialTexture.getLeft())
                        .setSize(
                            specialTexture.getRight()
                                .getLeft())
                        .setPos(
                            specialTexture.getRight()
                                .getRight()));
            }
        }
    }

    protected DrawableWidget createErrorStatusArea(ModularWindow.Builder builder, IDrawable picture) {
        return (DrawableWidget) new DrawableWidget().setDrawable(picture)
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setEnabled(
                widget -> !widget.getTooltip()
                    .isEmpty())
            .dynamicTooltip(this::getErrorDescriptions)
            .dynamicTooltipShift(this::getErrorDescriptionsShift)
            .setPos(79, 44)
            .setSize(18, 18)
            .attachSyncer(
                new FakeSyncWidget.BooleanSyncer(() -> mStuttering, val -> mStuttering = val),
                builder,
                (widget, val) -> widget.notifyTooltipChange())
            .attachSyncer(
                new FakeSyncWidget.IntegerSyncer(this::getErrorDisplayID, this::setErrorDisplayID),
                builder,
                (widget, val) -> widget.notifyTooltipChange());
    }

    protected List<String> getErrorDescriptions() {
        final GTTooltipDataCache.TooltipData tooltip = getErrorTooltip();
        return tooltip != null ? tooltip.text : Collections.emptyList();
    }

    protected List<String> getErrorDescriptionsShift() {
        final GTTooltipDataCache.TooltipData tooltip = getErrorTooltip();
        return tooltip != null ? tooltip.shiftText : Collections.emptyList();
    }

    protected GTTooltipDataCache.TooltipData getErrorTooltip() {
        if (isSteampowered()) {
            if (cannotVentSteam()) {
                return tooltipDataCache.getData(STALLED_VENT_TOOLTIP);
            }
        }
        if (mStuttering) {
            return tooltipDataCache.getData(
                STALLED_STUTTERING_TOOLTIP,
                translateToLocal(POWER_SOURCE_KEY + (isSteampowered() ? "steam" : "power")));
        }
        return null;
    }

    private boolean cannotVentSteam() {
        return (getErrorDisplayID() & 64) != 0;
    }

    protected static int getCapacityForTier(int tier) {
        return switch (tier) {
            case 0 -> 8000;
            case 1 -> 16000;
            case 2 -> 32000;
            case 3 -> 64000;
            case 4 -> 128000;
            default -> 256000;
        };
    }

    @Override
    protected boolean useMui2() {
        return false;
    }
}
