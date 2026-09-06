package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.Textures.BlockIcons.ITEM_OUT_SIGN;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_PIPE_OUT;
import static gregtech.api.util.GTUtility.areStacksEqual;
import static gregtech.api.util.GTUtility.isStackInvalid;
import static gregtech.api.util.GTUtility.isStackValid;

import java.util.BitSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.gtnewhorizons.modularui.api.forge.ItemHandlerHelper;

import gregtech.GTMod;
import gregtech.api.enums.OutputBusType;
import gregtech.api.interfaces.IDataCopyable;
import gregtech.api.interfaces.IOutputBus;
import gregtech.api.interfaces.IOutputBusTransaction;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IItemLockable;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.Hatch.ISmartInputHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTDataUtils;
import gregtech.api.util.GTItemTransfer;
import gregtech.api.util.GTSplit;
import gregtech.api.util.GTUtility;
import gregtech.common.gui.modularui.hatch.MTEHatchOutputBusGui;

@IMTE.SkipGenerateDescription
public class MTEHatchOutputBus extends MTEHatch implements IItemLockable, IDataCopyable, IOutputBus, ISmartInputHatch {

    private static final String DATA_STICK_DATA_TYPE = "outputBusFilter";
    private static final String LOCKED_ITEM_NBT_KEY = "lockedItem";

    protected ItemStack lockedItem = null;

    public MTEHatchOutputBus(int aID, String aName, String aNameRegional, int aTier) {
        this(aID, aName, aNameRegional, aTier, getSlots(aTier));
    }

    public MTEHatchOutputBus(int id, String name, String nameRegional, int tier, int slots) {
        super(id, name, nameRegional, tier, slots, (String) null);
    }

    public MTEHatchOutputBus(int aID, String aName, String aNameRegional, int aTier, String[] aDescription) {
        super(aID, aName, aNameRegional, aTier, getSlots(aTier), aDescription);
    }

    public MTEHatchOutputBus(int aID, String aName, String aNameRegional, int aTier, String[] aDescription,
        int inventorySize) {
        super(aID, aName, aNameRegional, aTier, inventorySize, aDescription);
    }

    public MTEHatchOutputBus(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, getSlots(aTier), aDescription, aTextures);
    }

    public MTEHatchOutputBus(String name, int tier, int slots, String[] description, ITexture[][][] textures) {
        super(name, tier, slots, description, textures);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return GTMod.proxy.mRenderIndicatorsOnHatch
            ? new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT), TextureFactory.of(ITEM_OUT_SIGN) }
            : new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return GTMod.proxy.mRenderIndicatorsOnHatch
            ? new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT), TextureFactory.of(ITEM_OUT_SIGN) }
            : new ITexture[] { aBaseTexture, TextureFactory.of(OVERLAY_PIPE_OUT) };
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean isValidSlot(int index) {
        return true;
    }

    @Override
    public MetaTileEntity newMTE(IGTE gte) {
        return new MTEHatchOutputBus(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean onRightclick(IGTE aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (!acceptsItemLock() || !(aPlayer instanceof EntityPlayerMP)) {
            openGui(aPlayer);
            return true;
        }

        final ItemStack dataStick = aPlayer.inventory.getCurrentItem();
        // if (!ItemList.Tool_DataStick.isStackEqual(dataStick, false, true)) {
        // openGui(aPlayer);
        // return true;
        // }

        if (!pasteCopiedData(aPlayer, dataStick.stackTagCompound)) {
            aPlayer.addChatMessage(new ChatComponentTranslation("GT5U.machines.output_bus.invalid"));
            return false;
        }

        aPlayer.addChatMessage(new ChatComponentTranslation("GT5U.machines.output_bus.loaded"));
        return true;

    }

    @Override
    public void onLeftClick(IGTE gte, EntityPlayer player) {
        if (!acceptsItemLock() || !(player instanceof EntityPlayerMP)) {
            return;
        }
        final ItemStack dataStick = player.inventory.getCurrentItem();
        // if (!ItemList.Tool_DataStick.isStackEqual(dataStick, false, true)) {
        // return;
        // }

        dataStick.stackTagCompound = getCopiedData(player);
        setDataStickName(dataStick);
        player.addChatMessage(new ChatComponentTranslation("GT5U.machines.output_bus.saved"));
    }

    protected void setDataStickName(ItemStack dataStick) {
        dataStick.setStackDisplayName("Output Bus Configuration");
    }

    @Override
    public NBTTagCompound getCopiedData(EntityPlayer player) {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type", DATA_STICK_DATA_TYPE);
        if (lockedItem != null) {
            nbt.setTag(LOCKED_ITEM_NBT_KEY, lockedItem.writeToNBT(new NBTTagCompound()));
        }
        return nbt;
    }

    @Override
    public boolean pasteCopiedData(EntityPlayer player, NBTTagCompound nbt) {
        if (nbt == null || !DATA_STICK_DATA_TYPE.equals(nbt.getString("type"))) return false;
        if (nbt.hasKey(LOCKED_ITEM_NBT_KEY)) {
            lockedItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(LOCKED_ITEM_NBT_KEY));
        } else {
            lockedItem = null;
        }
        return true;
    }

    @Override
    public String getCopiedDataIdentifier(EntityPlayer player) {
        return DATA_STICK_DATA_TYPE;
    }

    @Override
    public boolean storePartial(ItemStack stack, boolean simulate) {
        if (!simulate) markDirty();

        if (lockedItem != null && !lockedItem.isItemEqual(stack)) return false;

        int invLength = mInventory.length;

        for (int i = 0; i < invLength && stack.stackSize > 0; i++) {
            @Nullable
            ItemStack slot = mInventory[i];

            // the slot has an item and the stacks can't be merged; ignore it
            if (!isStackInvalid(slot) && !areStacksEqual(slot, stack)) continue;

            int inSlot = slot == null ? 0 : slot.stackSize;

            int toInsert = Math
                .min(Math.min(getInventoryStackLimit(), getStackSizeLimit(i, slot) - inSlot), stack.stackSize);

            if (toInsert == 0) continue;

            if (!simulate) {
                // if the slot is invalid create an empty stack in it
                if (isStackInvalid(slot)) mInventory[i] = slot = stack.splitStack(0);

                slot.stackSize += toInsert;
            }

            stack.stackSize -= toInsert;
        }

        return stack.stackSize == 0;
    }

    /**
     * Does this Output Bus push its content to the adjacent inventory.
     *
     * @return true if this Output Bus should push its contents, false otherwise.
     */
    public boolean pushOutputInventory() {
        return true;
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return side == gte.getFrontFacing();
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    protected int getStackTransferAmount() {
        return mInventory.length;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (gte.isServerSide() && gte.isAllowedToWork() && (tick & 0x7) == 0 && pushOutputInventory()) {

            GTItemTransfer transfer = new GTItemTransfer();

            transfer.push(gte, gte.getFrontFacing());

            transfer.setStacksToTransfer(getStackTransferAmount());
            transfer.setMaxItemsPerTransfer(getStackSizeLimit(-1, null));

            if (transfer.transfer() > 0) {
                GTUtility.cleanInventory(this);
            }
        }
        // A drained output bus frees up space, which can unblock a recipe that failed with ITEM_OUTPUT_FULL. This must
        // run AFTER the auto-eject above: that eject marks the inventory dirty within this same tick, and the dirty
        // flag is cleared at the end of the tick, so a self-eject that frees space would otherwise never push a check.
        if (gte.isServerSide()) {
            detectInventoryChange();
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        if (lockedItem != null) {
            nbt.setTag(LOCKED_ITEM_NBT_KEY, lockedItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        if (nbt.hasKey(LOCKED_ITEM_NBT_KEY)) {
            lockedItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(LOCKED_ITEM_NBT_KEY));
        }
    }

    @Override
    public void setLockedItem(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            clearLock();
        } else {
            lockedItem = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
        }
    }

    @Nullable
    @Override
    public ItemStack getLockedItem() {
        return lockedItem;
    }

    @Override
    public void clearLock() {
        lockedItem = null;
    }

    @Override
    public boolean isLocked() {
        return lockedItem != null;
    }

    @Override
    public boolean acceptsItemLock() {
        return true;
    }

    @Override
    public int getGUIWidth() {
        return super.getGUIWidth() + getOffsetX();
    }

    @Override
    public int getGUIHeight() {
        return super.getGUIHeight() + getOffsetY();
    }

    @Override
    public boolean isFiltered() {
        return isLocked();
    }

    @Override
    public boolean isFilteredToItem(GTUtility.ItemId id) {
        if (lockedItem == null) return false;

        return id.matches(lockedItem);
    }

    @Override
    public IOutputBusTransaction createTransaction() {
        return new StandardOutputBusTransaction();
    }

    @Override
    public OutputBusType getBusType() {
        return lockedItem == null ? OutputBusType.StandardUnfiltered : OutputBusType.StandardFiltered;
    }

    /**
     * Gets the max stack size limit for a slot and a stack.
     *
     * @param slot      The slot, or -1 for a general 'lowest slot' query.
     * @param itemStack The stack, or null for a general 'any standard stack' query (getMaxStackSize() == 64).
     */
    @Override
    public int getStackSizeLimit(int slot, @Nullable ItemStack itemStack) {
        return Math.min(getInventoryStackLimit(), itemStack == null ? 64 : itemStack.getMaxStackSize());
    }

    class StandardOutputBusTransaction implements IOutputBusTransaction {

        private final ItemStack[] inventory;

        private final BitSet availableSlots = new BitSet();

        private boolean active = true;

        StandardOutputBusTransaction() {
            inventory = GTDataUtils.mapToArray(mInventory, ItemStack[]::new, GTUtility::copy);

            for (int i = 0, inventoryLength = inventory.length; i < inventoryLength; i++) {
                ItemStack stack = inventory[i];

                int capacity = getStackSizeLimit(i, stack);

                if (stack == null || stack.stackSize < capacity) {
                    availableSlots.set(i);
                }
            }
        }

        @Override
        public IOutputBus getBus() {
            return MTEHatchOutputBus.this;
        }

        @Override
        public boolean storePartial(GTUtility.ItemId id, ItemStack stack) {
            if (!active) throw new IllegalStateException("Cannot add to a transaction after committing it");

            int maxStackSize = getStackSizeLimit(-1, stack);

            for (int i = availableSlots.nextSetBit(0); i >= 0; i = availableSlots.nextSetBit(i + 1)) {
                if (stack.stackSize <= 0) break;

                @Nullable
                ItemStack slot = inventory[i];

                // the slot has an item and the stacks can't be merged; ignore it
                if (isStackValid(slot) && !areStacksEqual(slot, stack)) continue;

                int inSlot = slot == null ? 0 : slot.stackSize;

                int toInsert = Math.min(Math.min(getInventoryStackLimit(), maxStackSize - inSlot), stack.stackSize);

                if (toInsert == 0) continue;

                // if the slot is invalid create an empty stack in it
                if (isStackInvalid(slot)) inventory[i] = slot = stack.splitStack(0);

                slot.stackSize += toInsert;
                stack.stackSize -= toInsert;

                if (slot.stackSize == maxStackSize) {
                    availableSlots.clear(i);
                }

                return true;
            }

            return false;
        }

        @Override
        public void complete(GTUtility.ItemId id) {
            if (!active) throw new IllegalStateException("Cannot add to a transaction after committing it");

            for (int i = 0, invLength = inventory.length; i < invLength; i++) {
                if (!availableSlots.get(i)) continue;

                @Nullable
                ItemStack slot = inventory[i];

                if (isStackValid(slot) && id.matches(slot)) {
                    availableSlots.clear(i);
                }
            }
        }

        @Override
        public boolean hasAvailableSpace() {
            return !availableSlots.isEmpty();
        }

        @Override
        public void commit() {
            assert mInventory.length == inventory.length;

            System.arraycopy(inventory, 0, mInventory, 0, inventory.length);

            MTEHatchOutputBus.this.markDirty();

            active = false;
        }
    }

    @Override
    public String[] getDescription() {
        return GTSplit.splitLocalizedFormatted(
            getSlots(mTier) >= 2 ? "gt.blockmachines.output_bus.desc" : "gt.blockmachines.output_bus.singular.desc",
            getSlots(mTier));
    }

    @Override
    protected boolean useMui2() {
        return true;
    }

    @Override
    public ModularPanel buildUI(PosGuiData guiData, PanelSyncManager syncManager, UISettings uiSettings) {
        return new MTEHatchOutputBusGui(this).build(guiData, syncManager, uiSettings);
    }
}
