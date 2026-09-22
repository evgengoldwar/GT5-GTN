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
import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.gui.modularui.GTUIInfos;
import gregtech.api.implementation.items.GTItemSink;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
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

    protected MTEArrays mteArrays;

    protected CommonMTE(MTEArrays mteArrays, int meta, String name, int slotCount) {
        this.mteArrays = mteArrays;
        IMTE[] mteArray = mteArrays.getMteArray();
        if (mteArray[meta] == null) {
            mteArray[meta] = this;
        } else {
            var existing = mteArray[meta];
            throw new IllegalArgumentException(
                "MetaTileEntity meta " + meta
                    + " is already occupied! Existing MTE is "
                    + existing.getMetaName()
                    + "("
                    + existing.getClass()
                        .getCanonicalName()
                    + ").");
        }
        inventory = new ItemStack[slotCount];
        this.name = name.replace(" ", "_")
            .toLowerCase(Locale.ENGLISH);
    }

    protected CommonMTE(String name, int slotCount) {
        inventory = new ItemStack[slotCount];
        this.name = name;
    }

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

    @Override
    public FluidStack getFluid() {
        return null;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    public int getProgressTime() {
        return 0;
    }

    public int maxProgressTime() {
        return 0;
    }

    public int increaseProgress(int progress) {
        return 0;
    }

    @Override
    public void onMachineBlockUpdate() {}

    @Override
    public void receiveClientEvent(byte eventId, byte value) {}

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

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        if (index >= 0 && index < inventory.length) {
            inventory[index] = itemStack;
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        IMTE[] mteArray = mteArrays.getMteArray();
        if (mteArray[getGTE().getMeta()] != null) {
            return mteArray[getGTE().getMeta()].getLocalNameKey();
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
            && (inventory[index] == null || GTUtility.stackEqual(itemStack, inventory[index]))
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
        AxisAlignedBB alignedBB = getCollisionBoundingBoxFromPool(world, x, y, z);
        if (alignedBB != null && inputAABB.intersectsWith(alignedBB)) outputAABB.add(alignedBB);
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

    protected boolean useMui2() {
        return false;
    }

    protected boolean forceUseMui2() {
        return false;
    }

    @Override
    public final String getGuiId() {
        return name;
    }

    public GTGuiTheme getGuiTheme() {
        return GTGuiThemes.STANDARD;
    }

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
