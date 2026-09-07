package gregtech.api.metatileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.network.NetworkUtils;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.gtnewhorizon.gtnhlib.capability.item.ItemIO;
import com.gtnewhorizon.gtnhlib.capability.item.ItemSink;
import com.gtnewhorizon.gtnhlib.capability.item.ItemSource;
import com.gtnewhorizon.gtnhlib.item.InventoryItemSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.gui.modularui.GTUIInfos;
import gregtech.api.implementation.items.GTItemSink;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.modularui.IAddUIWidgets;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modularui2.GTGuiTheme;
import gregtech.api.modularui2.GTGuiThemes;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.modularui2.GTModularScreen;
import gregtech.api.modularui2.MetaTileEntityGuiHandler;
import gregtech.api.render.ISBRInventoryContext;
import gregtech.api.render.ISBRWorldContext;
import gregtech.api.util.GTUtility;
import gregtech.common.covers.Base.Cover;

public abstract class CommonMTE implements IMTE {

    private volatile ITexture[][] inventoryTextureCache;

    public final ItemStack[] inventory;

    public final String name;

    public boolean doTickProfilingInThisTick = true;

    public long soundRequests = 0;

    protected IMTE[] mteArray;

    protected CommonMTE(int id, String basicName, int invSlotCount, IMTE[] mteArray) {
        if (mteArray[id] == null) {
            mteArray[id] = this;
            this.mteArray = mteArray;
        } else {
            var existing = mteArray[id];
            throw new IllegalArgumentException(
                "MetaTileEntity id " + id
                    + " is already occupied! Existing MTE is "
                    + existing.getMetaName()
                    + "("
                    + existing.getClass()
                        .getCanonicalName()
                    + ").");
        }
        inventory = new ItemStack[invSlotCount];
        name = basicName.replace(" ", "_")
            .toLowerCase(Locale.ENGLISH);
    }

    protected CommonMTE(String name, int invSlotCount) {
        inventory = new ItemStack[invSlotCount];
        this.name = name;
    }

    /**
     * @inheritDoc
     */
    @Nullable
    @Override
    public <T> T getCapability(@NotNull Class<T> capability, @NotNull ForgeDirection side) {
        if (capability == ItemSink.class) {
            return capability.cast(getItemSink(side));
        }
        if (capability == ItemSource.class) {
            return capability.cast(getItemSource(side));
        }
        if (capability == ItemIO.class) {
            return capability.cast(getItemIO(side));
        }

        return null;
    }

    protected ItemSink getItemSink(ForgeDirection side) {
        return getSizeInventory() == 0 ? null : new GTItemSink(this, side);
    }

    protected ItemSource getItemSource(ForgeDirection side) {
        return getSizeInventory() == 0 ? null : new InventoryItemSource(this, side);
    }

    protected ItemIO getItemIO(ForgeDirection side) {
        return null;
    }

    @Override
    public void onConfigLoad() {}

    @Override
    public void setItemNBT(NBTTagCompound nbt) {}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {}

    @Override
    public boolean allowCoverOnSide(ForgeDirection side, ItemStack coverItem) {
        return true;
    }

    @Override
    public void onFirstTick(IGTE gte) {}

    @Override
    public boolean needsClientTick() {
        return true;
    }

    @Override
    public void onClientSoundStateChanged() {}

    @Override
    public void onPreTick(IGTE gte, long tick) {}

    @Override
    public void onPostTick(IGTE gte, long tick) {}

    public void onTickFail(IGTE baseMetaTileEntity, long tick) {}

    public void onSetActive(boolean active) {}

    public void onEnableWorking() {}

    public void onDisableWorking() {}

    @Override
    public void inValidate() {}

    @Override
    public void onRemoval() {}

    @Override
    public void initDefaultModes(NBTTagCompound nbt) {}

    /**
     * Called when a player right-clicks this block. Shift-right-clicks will not get passed to this!
     */
    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player, ForgeDirection side, float x, float y, float z) {
        return false;
    }

    @Override
    public void onLeftClick(IGTE gte, EntityPlayer player) {}

    @Override
    public void onValueUpdate(byte value) {}

    @Override
    public byte getUpdateData() {
        return 0;
    }

    @Override
    public void doSound(byte index, double x, double y, double z) {}

    @Override
    public void startSoundLoop(byte index, double x, double y, double z) {}

    @Override
    public void stopSoundLoop(byte index, double x, double y, double z) {}

    @Override
    public final void sendSound(byte index) {
        if (!getGTE().isMuffled()) {
            getGTE().sendBlockEvent(GregTechTileClientEvents.DO_SOUND, index);
        }
    }

    @Override
    public final void sendLoopStart(byte index) {
        if (!getGTE().isMuffled()) {
            getGTE().sendBlockEvent(GregTechTileClientEvents.START_SOUND_LOOP, index);
        }
        soundRequests++;
    }

    @Override
    public final void sendLoopEnd(byte index) {
        if (!getGTE().isMuffled()) {
            getGTE().sendBlockEvent(GregTechTileClientEvents.STOP_SOUND_LOOP, index);
        }
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return false;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isValidSlot(int index) {
        return true;
    }

    @Override
    public boolean shouldDropItemAt(int index) {
        return true;
    }

    @Override
    public boolean setStackToZeroInsteadOfNull(int index) {
        return false;
    }

    @Override
    public ArrayList<String> getSpecialDebugInfo(IGTE gte, EntityPlayer player, int logLevel, ArrayList<String> list) {
        return list;
    }

    @Override
    public ArrayList<ItemStack> getDroppedItem() {
        return null;
    }

    /**
     * Returns the fluid this block contains.
     */
    @Override
    public FluidStack getFluid() {
        return null;
    }

    /**
     * Tries to fill this tank.
     */
    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    /**
     * Tries to empty this tank.
     */
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }

    /**
     * Returns capacity of the fluid.
     */
    @Override
    public int getCapacity() {
        return 0;
    }

    /**
     * Returns progress in ticks this machine has already made.
     */
    public int getProgresstime() {
        return 0;
    }

    /**
     * Returns total ticks required for this machine to finish one cycle of the progress.
     */
    public int maxProgresstime() {
        return 0;
    }

    /**
     * Increases the progress, returns the overflown progress.
     */
    public int increaseProgress(int progress) {
        return 0;
    }

    @Override
    public void onMachineBlockUpdate() {}

    @Override
    public void receiveClientEvent(byte eventId, byte value) {}

    /**
     * Gets the output for the comparator on the given side
     */
    @Override
    public byte getComparatorValue(ForgeDirection side) {
        return 0;
    }

    @Override
    public String getSpecialVoltageToolTip() {
        return null;
    }

    public boolean isDigitalChest() {
        return false;
    }

    public ItemStack[] getStoredItemData() {
        return null;
    }

    public void setItemCount(int count) {}

    public int getMaxItemCount() {
        return 0;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index >= 0 && index < inventory.length) {
            return inventory[index];
        }
        return null;
    }

    /**
     * Gets the first ItemStack in the bus, reading from the top left to bottom right
     *
     * @return the first ItemStack in the bus
     */
    public ItemStack getFirstStack() {
        for (int index = 0; index < inventory.length; index++) {
            ItemStack stackInSlot = getStackInSlot(index);
            if (stackInSlot != null) {
                return stackInSlot;
            }
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        if (index >= 0 && index < inventory.length) {
            inventory[index] = itemStack;
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        if (mteArray[getGTE().getMetaTileID()] != null) {
            return mteArray[getGTE().getMetaTileID()].getLocalNameKey();
        }
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return getGTE().isValidSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        ItemStack tStack = getStackInSlot(index), rStack = GTUtility.copyOrNull(tStack);
        if (tStack != null) {
            if (tStack.stackSize <= amount) {
                if (setStackToZeroInsteadOfNull(index)) {
                    tStack.stackSize = 0;
                    markDirty();
                } else setInventorySlotContents(index, null);
            } else {
                rStack = tStack.splitStack(amount);
                markDirty();
                if (tStack.stackSize == 0 && !setStackToZeroInsteadOfNull(index)) setInventorySlotContents(index, null);
            }
        }
        return rStack;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int ordinalSide) {
        final TIntList tList = new TIntArrayList();
        final IGTE tTileEntity = getGTE();
        if (tTileEntity == null || tTileEntity.isDead()) return GTValues.emptyIntArray;
        final Cover tileCover = tTileEntity.getCoverAtSide(ForgeDirection.getOrientation(ordinalSide));
        final boolean tSkip = tileCover.letsItemsIn(-2) || tileCover.letsItemsOut(-2);
        for (int i = 0; i < getSizeInventory(); i++) {
            if (isValidSlot(i) && (tSkip || tileCover.letsItemsOut(i) || tileCover.letsItemsIn(i))) {
                tList.add(i);
            }
        }
        return tList.toArray();
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStack, int ordinalSide) {
        return isValidSlot(index) && itemStack != null
            && index < inventory.length
            && (inventory[index] == null || GTUtility.areStacksEqual(itemStack, inventory[index]))
            && allowPutStack(getGTE(), index, ForgeDirection.getOrientation(ordinalSide), itemStack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack itemStack, int ordinalSide) {
        return isValidSlot(index) && itemStack != null
            && index < inventory.length
            && allowPullStack(getGTE(), index, ForgeDirection.getOrientation(ordinalSide), itemStack);
    }

    @Override
    public boolean canFill(ForgeDirection side, Fluid fluid) {
        return fill(side, new FluidStack(fluid, 1), false) == 1;
    }

    @Override
    public boolean canDrain(ForgeDirection side, Fluid fluid) {
        return drain(side, new FluidStack(fluid, 1), false) != null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection side) {
        if (getCapacity() <= 0 && !getGTE().isSteampowered()) {
            return GTValues.emptyFluidTankInfo;
        }
        return new FluidTankInfo[] { getInfo() };
    }

    @Override
    public FluidStack drain(ForgeDirection side, FluidStack fluidStack, boolean doDrain) {
        return drain(side, fluidStack, fluidStack == null ? 0 : fluidStack.amount, doDrain);
    }

    /**
     * Type-aware drain with an overridden amount. Avoids allocating a new {@link FluidStack} per call when the caller
     * needs to drain a different amount than {@code fluidStack.amount}.
     */
    public FluidStack drain(ForgeDirection side, FluidStack fluidStack, int amount, boolean doDrain) {
        if (getFluid() != null && fluidStack != null && getFluid().isFluidEqual(fluidStack)) {
            return drain(amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection side, int maxDrain, boolean doDrain) {
        return drain(maxDrain, doDrain);
    }

    @Override
    public int getFluidAmount() {
        return 0;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(this);
    }

    @Override
    public String getMetaName() {
        return name;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public boolean doTickProfilingMessageDuringThisTick() {
        return doTickProfilingInThisTick;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public boolean connectsToItemPipe(ForgeDirection side) {
        return false;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public ItemStack[] getRealInventory() {
        return inventory;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderInInventory(ISBRInventoryContext ctx) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    protected final ITexture[][] getOrCreateInventoryTextures() {
        ITexture[][] textures = inventoryTextureCache;
        if (textures == null) {
            final IGTE base = getGTE();
            textures = new ITexture[][] { getTexture(base, ForgeDirection.DOWN, ForgeDirection.WEST, -1, true, false),
                getTexture(base, ForgeDirection.UP, ForgeDirection.WEST, -1, true, false),
                getTexture(base, ForgeDirection.NORTH, ForgeDirection.WEST, -1, true, false),
                getTexture(base, ForgeDirection.SOUTH, ForgeDirection.WEST, -1, true, false),
                getTexture(base, ForgeDirection.WEST, ForgeDirection.WEST, -1, true, false),
                getTexture(base, ForgeDirection.EAST, ForgeDirection.WEST, -1, true, false) };
            inventoryTextureCache = textures;
        }
        return textures;
    }

    public final void clearInventoryTextureCache() {
        inventoryTextureCache = null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderInWorld(ISBRWorldContext ctx) {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB inputAABB,
        List<AxisAlignedBB> outputAABB, Entity collider) {
        AxisAlignedBB axisalignedbb1 = getCollisionBoundingBoxFromPool(world, x, y, z);
        if (axisalignedbb1 != null && inputAABB.intersectsWith(axisalignedbb1)) outputAABB.add(axisalignedbb1);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity collider) {}

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {}

    @Override
    public boolean allowGeneralRedstoneOutput() {
        return false;
    }

    @Override
    public boolean hasAlternativeModeText() {
        return false;
    }

    @Override
    public String getAlternativeModeText() {
        return "";
    }

    /**
     * Opens GUI for the specified player. Currently, we have two ways to create GUI: MUI1 and MUI2. We're gradually
     * migrating to MUI2. However, since cover panel is not supported for the time being, leave support for MUI1
     * ({@link IAddUIWidgets#addUIWidgets}) as well.
     */
    @SuppressWarnings("deprecation")
    public void openGui(EntityPlayer player) {
        if ((GTGuis.GLOBAL_SWITCH_MUI2 && useMui2()) || forceUseMui2()) {
            if (!NetworkUtils.isClient(player)) {
                MetaTileEntityGuiHandler.open(player, this);
            }
        } else {
            GTUIInfos.openGTTileEntityUI(getGTE(), player);
        }

        onGuiOpened(player);
    }

    protected void onGuiOpened(EntityPlayer player) {

    }

    /**
     * Whether to use MUI2 for creating GUI. Use {@link #buildUI} for MUI2, and {@link IAddUIWidgets#addUIWidgets} for
     * MUI1.
     */
    protected boolean useMui2() {
        return false;
    }

    /**
     * Returning true means opening GUI with MUI2, regardless of {@link GTGuis#GLOBAL_SWITCH_MUI2}. Note that cover tabs
     * are currently unfinished.
     */
    protected boolean forceUseMui2() {
        return false;
    }

    /**
     * A public method to verify if this MTE has a Mui2 GUI. Returning false indicates that do not try to open a Mui2
     * GUI
     * of this.
     */
    public boolean hasMui2Gui() {
        return useMui2() || forceUseMui2();
    }

    @Override
    public final String getGuiId() {
        return name;
    }

    /**
     * Specifies theme of this GUI. {@link GTGuiThemes} lists all the themes you can use.
     */
    public GTGuiTheme getGuiTheme() {
        return GTGuiThemes.STANDARD;
    }

    /**
     * Override to create GUI with MUI2. You also need to override {@link #useMui2}.
     * <p>
     * Called on server and client. Create only the main panel here. Only here you can add sync handlers to widgets
     * directly. If the widget to be synced is not in this panel yet (f.e. in another panel) the sync handler must be
     * registered here with {@link PanelSyncManager}.
     *
     * @inheritDoc
     */
    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModularScreen createScreen(PosGuiData data, ModularPanel mainPanel) {
        return new GTModularScreen(mainPanel, getGuiTheme());
    }
}
