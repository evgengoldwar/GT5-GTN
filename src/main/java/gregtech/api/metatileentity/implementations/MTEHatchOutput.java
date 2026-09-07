package gregtech.api.metatileentity.implementations;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatFluid;
import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;
import static gregtech.api.enums.Textures.BlockIcons.FLUID_OUT_SIGN;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_PIPE_OUT;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

import org.jetbrains.annotations.NotNull;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.gtnewhorizon.gtnhlib.chat.customcomponents.ChatComponentFluidName;

import gregtech.GTMod;
import gregtech.api.enums.OutputHatchType;
import gregtech.api.interfaces.IOutputHatch;
import gregtech.api.interfaces.IOutputHatchTransaction;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.fluid.IFluidStore;
import gregtech.api.interfaces.metatileentity.IFluidLockableMui2;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.metatileentity.Hatch.ISmartInputHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTSplit;
import gregtech.api.util.GTUtility;
import gregtech.common.gui.modularui.hatch.MTEHatchOutputGui;

@IMTE.SkipGenerateDescription
public class MTEHatchOutput extends MTEHatch
    implements IFluidStore, IFluidLockableMui2, IOutputHatch, ISmartInputHatch {

    protected Fluid lockedFluid = null;
    private WeakReference<EntityPlayer> playerThatLockedfluid = null;
    protected byte mMode = 0;

    public MTEHatchOutput(int aID, String aName, String aNameRegional, int aTier, IMTE[] mteArray) {
        super(aID, aName, aNameRegional, aTier, 4, (String) null, mteArray);
    }

    public MTEHatchOutput(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 4, aDescription, aTextures);
    }

    public MTEHatchOutput(int aID, String aName, String aNameRegional, int aTier, String[] aDescription,
        int inventorySize, IMTE[] mteArray) {
        super(aID, aName, aNameRegional, aTier, inventorySize, aDescription, mteArray);
    }

    public MTEHatchOutput(String name, int tier, int slots, String[] description, ITexture[][][] textures) {
        super(name, tier, slots, description, textures);
    }

    public byte getMode() {
        return mMode;
    }

    public void setMode(byte mode) {
        this.mMode = mode;
        lockFluid(isFluidLocked());
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return GTMod.proxy.mRenderIndicatorsOnHatch
            ? new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT), TextureFactory.of(FLUID_OUT_SIGN) }
            : new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return GTMod.proxy.mRenderIndicatorsOnHatch
            ? new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT), TextureFactory.of(FLUID_OUT_SIGN) }
            : new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean isLiquidInput(ForgeDirection side) {
        return false;
    }

    @Override
    public MetaTileEntity newMTE(IGTE gte) {
        return new MTEHatchOutput(name, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean onRightclick(IGTE aBaseMetaTileEntity, EntityPlayer aPlayer) {
        openGui(aPlayer);
        return true;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (gte.isServerSide() && gte.isAllowedToWork() && mFluid != null) {
            IFluidHandler tTileEntity = gte.getITankContainerAtSide(gte.getFrontFacing());
            if (tTileEntity != null) {
                GTUtility.moveFluid(gte, tTileEntity, gte.getFrontFacing(), Math.max(1, mFluid.amount), null);
            }
        }
        // A drained output hatch frees up space, which can unblock a recipe that failed with FLUID_OUTPUT_FULL. This
        // must run AFTER the auto-eject above: that eject marks the tank dirty within this same tick, and the dirty
        // flag is cleared at the end of the tick, so a self-eject that frees space would otherwise never push a check.
        if (gte.isServerSide()) {
            detectInventoryChange();
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);

        nbt.setByte("mMode", mMode);
        if (lockedFluid != null) nbt.setString("lockedFluidName", lockedFluid.getName());
        else nbt.removeTag("lockedFluidName");
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);

        mMode = nbt.getByte("mMode");
        if (nbt.hasKey("lockedFluidName")) setLockedFluid(FluidRegistry.getFluid(nbt.getString("lockedFluidName")));
    }

    @Override
    public boolean doesFillContainers() {
        return true;
    }

    @Override
    public boolean canTankBeFilled() {
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    public int getLockedDisplaySlot() {
        return 3;
    }

    @Override
    public boolean isValidSlot(int index) {
        // Because getStackDisplaySlot() only allow return one int, this place I only can manually set.
        return index != getStackDisplaySlot() && index != getLockedDisplaySlot();
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return side == gte.getFrontFacing() && index == 1;
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return side == gte.getFrontFacing() && index == 0;
    }

    @Override
    public int getCapacity() {
        return 8000 * (1 << mTier);
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z,
        ItemStack tool) {
        if (!getGTE().getCoverAtSide(side)
            .isGUIClickable()) return;
        if (player.isSneaking()) {
            mMode = (byte) ((mMode + 9) % 10);
        } else {
            mMode = (byte) ((mMode + 1) % 10);
        }
        final IChatComponent inBrackets;
        switch (mMode) {
            case 0, 1, 2, 3, 4, 5, 6, 7 -> {
                GTUtility.sendChatTrans(player, MTEHatchOutput.getLangKeyForMode(mMode));
                lockedFluid = null;
            }
            case 8, 9 -> {
                playerThatLockedfluid = new WeakReference<>(player);
                if (mFluid == null) {
                    lockedFluid = null;
                    inBrackets = new ChatComponentTranslation("GT5U.chat.hatch.output.in_brackets.none");
                } else {
                    this.setLockedFluid(
                        this.getDrainableStack()
                            .getFluid());
                    inBrackets = new ChatComponentFluidName(this.getDrainableStack());
                }
                GTUtility.sendChatTrans(player, MTEHatchOutput.getLangKeyForMode(mMode), inBrackets);
            }
        }
    }

    public static String getLangKeyForMode(byte mode) {
        return switch (mode) {
            case 0 -> "GT5U.chat.hatch.output.fluid_steam_item";
            case 1 -> "GT5U.chat.hatch.output.steam_item";
            case 2 -> "GT5U.chat.hatch.output.steam_fluid";
            case 3 -> "GT5U.chat.hatch.output.steam";
            case 4 -> "GT5U.chat.hatch.output.fluid_item";
            case 5 -> "GT5U.chat.hatch.output.item";
            case 6 -> "GT5U.chat.hatch.output.fluid";
            case 7 -> "GT5U.chat.hatch.output.nothing";
            case 8 -> "GT5U.chat.hatch.output.items_and_specific_fluid";
            case 9 -> "GT5U.chat.hatch.output.specific_fluid";
            default -> "";
        };
    }

    private boolean tryToLockHatch(EntityPlayer aPlayer, ForgeDirection side) {
        if (!getGTE().getCoverAtSide(side)
            .isGUIClickable()) return false;
        if (!isFluidLocked()) return false;
        final ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
        if (tCurrentItem == null) return false;
        FluidStack tFluid = FluidContainerRegistry.getFluidForFilledItem(tCurrentItem);
        if (tFluid == null && tCurrentItem.getItem() instanceof IFluidContainerItem)
            tFluid = ((IFluidContainerItem) tCurrentItem.getItem()).getFluid(tCurrentItem);
        if (tFluid != null) {
            if (lockedFluid != null && !lockedFluid.equals(tFluid.getFluid())) {
                String fluidName = lockedFluid.getName();
                FluidStack fluidStack = FluidRegistry.getFluidStack(fluidName, 1);
                GTUtility.sendChatTrans(
                    aPlayer,
                    "GT5U.chat.hatch.output.fluid.already_locked",
                    fluidStack == null ? fluidName : new ChatComponentFluidName(fluidStack));
            } else {
                setLockedFluid(tFluid.getFluid());
                GTUtility.sendChatTrans(
                    aPlayer,
                    mMode == 8 ? "GT5U.chat.hatch.output.items_and_specific_fluid"
                        : "GT5U.chat.hatch.output.specific_fluid",
                    new ChatComponentFluidName(tFluid));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player, ForgeDirection side, float x, float y, float z) {
        if (tryToLockHatch(player, side)) return true;
        return super.onRightClick(gte, player, side, x, y, z);
    }

    public boolean outputsLiquids() {
        return mMode < 4 || mMode % 2 == 0 || mMode == 9;
    }

    @Override
    public void setLockedFluid(Fluid lockedFluid) {
        this.lockedFluid = lockedFluid;
        markDirty();
    }

    @Override
    public Fluid getLockedFluid() {
        return lockedFluid;
    }

    @Override
    public void lockFluid(boolean lock) {
        if (lock) {
            if (!isFluidLocked()) {
                mMode = 9;
            }
            Fluid fluid = mFluid == null ? null : mFluid.getFluid();
            setLockedFluid(lockedFluid == null ? fluid : lockedFluid);
        } else {
            if (isFluidLocked()) {
                mMode = 0;
            }
            setLockedFluid(null);
        }
    }

    @Override
    public boolean isFluidLocked() {
        return mMode == 8 || mMode == 9;
    }

    @Override
    public boolean acceptsFluidLock(Fluid fluid) {
        return true;
    }

    @Override
    public boolean isEmptyAndAcceptsAnyFluid() {
        return mMode == 0 && getFluidAmount() == 0;
    }

    @Override
    public boolean canStoreFluid(@Nonnull FluidStack fluidStack) {
        if (mFluid != null && !GTUtility.areFluidsEqual(mFluid, fluidStack)) {
            return false;
        }
        if (isFluidLocked()) {
            if (lockedFluid == null) {
                return true;
            }
            return lockedFluid.equals(fluidStack.getFluid());
        }

        return outputsLiquids();
    }

    @Override
    protected void onEmptyingContainerWhenEmpty() {
        if (this.lockedFluid == null && this.mFluid != null && isFluidLocked()) {
            this.setLockedFluid(this.mFluid.getFluid());
            final EntityPlayer player;
            if (playerThatLockedfluid == null || (player = playerThatLockedfluid.get()) == null) return;
            GTUtility
                .sendChatTrans(player, "GT5U.chat.hatch.output.fluid.locked_to", new ChatComponentFluidName(mFluid));
            playerThatLockedfluid = null;
        }
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[] { "GT5U.infodata.hatch.output",
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.hatch.output.fluid",
                mFluid == null ? IGregTechDeviceInformation.translatable("GT5U.infodata.hatch.output.fluid.none")
                    : IGregTechDeviceInformation.translatable(mFluid.getUnlocalizedName())),
            EnumChatFormatting.GREEN + formatFluid(mFluid == null ? 0 : mFluid.amount)
                + EnumChatFormatting.RESET
                + " "
                + EnumChatFormatting.YELLOW
                + formatFluid(getCapacity())
                + EnumChatFormatting.RESET,
            (!isFluidLocked() || lockedFluid == null) ? "GT5U.infodata.hatch.output.fluid.locked_to.none"
                : IGregTechDeviceInformation.encode(
                    "GT5U.infodata.hatch.output.fluid.locked_to",
                    IGregTechDeviceInformation.translatable(
                        FluidRegistry.getFluidStack(lockedFluid.getName(), 1)
                            .getUnlocalizedName())) };
    }

    @Override
    public String[] getDescription() {
        return GTSplit.splitLocalizedFormatted("gt.blockmachines.output_hatch.desc", formatNumber(getCapacity()));
    }

    @Override
    protected boolean useMui2() {
        return true;
    }

    @Override
    public ModularPanel buildUI(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        return new MTEHatchOutputGui(this).build(guiData, syncManager, uiSettings);
    }

    @Override
    public boolean isFiltered() {
        return isFluidLocked();
    }

    @Override
    public boolean isFilteredToFluid(GTUtility.FluidId id) {
        if (lockedFluid == null) return false;

        return id.matches(lockedFluid);
    }

    @Override
    public OutputHatchType getHatchType() {
        return lockedFluid == null ? OutputHatchType.StandardUnfiltered : OutputHatchType.StandardFiltered;
    }

    @Override
    public boolean storePartial(FluidStack stack, boolean simulate) {
        int amount = fill(stack, !simulate);
        stack.amount -= amount;
        return stack.amount == 0;
    }

    @Override
    public IOutputHatchTransaction createTransaction() {
        return new StandardOutputHatchTransaction();
    }

    class StandardOutputHatchTransaction implements IOutputHatchTransaction {

        private FluidStack fluid = null;
        private int availableSpace;
        private boolean active = true;

        StandardOutputHatchTransaction() {
            if (getFillableStack() != null) {
                fluid = getFillableStack().copy();
                availableSpace = getCapacity() - fluid.amount;
            } else {
                availableSpace = getCapacity();
            }
        }

        @Override
        public IOutputHatch getHatch() {
            return MTEHatchOutput.this;
        }

        @Override
        public boolean storePartial(GTUtility.FluidId id, @NotNull FluidStack stack) {
            if (!active) throw new IllegalStateException("Cannot add to a transaction after committing it");
            int amount = Math.min(availableSpace, stack.amount);
            if (amount <= 0) return false;
            if (fluid != null && !fluid.isFluidEqual(stack) || !canStoreFluid(stack)) return false;
            if (fluid == null) {
                fluid = stack.copy();
                fluid.amount = amount;
            } else {
                fluid.amount += amount;
            }
            stack.amount -= amount;
            availableSpace -= amount;
            return stack.amount == 0;
        }

        @Override
        public void complete(GTUtility.FluidId id) {
            if (!active) throw new IllegalStateException("Cannot add to a transaction after committing it");
        }

        @Override
        public boolean hasAvailableSpace() {
            return availableSpace > 0;
        }

        @Override
        public void commit() {
            setFillableStack(fluid);
            markDirty();
            onEmptyingContainerWhenEmpty();
            active = false;
        }
    }
}
