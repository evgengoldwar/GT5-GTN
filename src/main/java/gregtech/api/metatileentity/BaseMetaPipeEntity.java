package gregtech.api.metatileentity;

import static gregtech.GTLoggers.GT_FML_LOGGER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.graphs.Lock;
import gregtech.api.graphs.Node;
import gregtech.api.graphs.paths.NodePath;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IConnectable;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IPipeRenderedTileEntity;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.covers.Base.Cover;
import io.netty.buffer.ByteBuf;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main TileEntity for EVERYTHING.
 */
public class BaseMetaPipeEntity extends CommonGTE implements IPipeRenderedTileEntity {

    public byte mConnections = IConnectable.NO_CONNECTION;
    protected MetaPipeEntity mMetaTileEntity;
    private boolean mWorkUpdate = false, mWorks = true;
    private byte oldConnections = 0;
    protected Node node;
    protected NodePath nodePath;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public NodePath getNodePath() {
        return nodePath;
    }

    public void setNodePath(NodePath nodePath) {
        this.nodePath = nodePath;
    }

    public void addToLock(TileEntity tileEntity, ForgeDirection side) {
        if (node != null) {
            final Lock lock = node.locks[side.ordinal()];
            if (lock != null) {
                lock.addTileEntity(tileEntity);
            }
        } else if (nodePath != null) {
            nodePath.lock.addTileEntity(tileEntity);
        }
    }

    public void removeFromLock(TileEntity tileEntity, ForgeDirection side) {
        if (node != null) {
            final Lock lock = node.locks[side.ordinal()];
            if (lock != null) {
                lock.removeTileEntity(tileEntity);
            }
        } else if (nodePath != null) {
            nodePath.lock.removeTileEntity(tileEntity);
        }
    }

    public void reloadLocks() {
        final IMTE meta = getMTE();
        if (meta instanceof MetaPipeEntity) {
            ((MetaPipeEntity) meta).reloadLocks();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        try {
            super.writeToNBT(nbt);
        } catch (Exception e) {
            GT_FML_LOGGER.error("Encountered CRITICAL ERROR while saving MetaTileEntity", e);
        }
        try {
            nbt.setInteger("mID", meta);
            writeCoverNBT(nbt, false);
            writeCommonNBT(nbt);
            nbt.setByte("mConnections", mConnections);
            nbt.setBoolean("mWorks", !mWorks);
        } catch (Exception e) {
            GT_FML_LOGGER.error("Encountered CRITICAL ERROR while saving MetaTileEntity", e);
        }
        saveMetaTileNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound aNBT) {
        super.readFromNBT(aNBT);
        setInitialValuesAsNBT(aNBT, (short) 0);
    }

    @Override
    public void setInitialValuesAsNBT(NBTTagCompound nbt, short meta) {
        if (nbt == null) {
            if (meta > 0) this.meta = meta;
            else this.meta = this.meta > 0 ? this.meta : 0;
            if (this.meta != 0) createNewMetatileEntity(this.meta);
        } else {
            if (meta <= 0) this.meta = (short) nbt.getInteger("mID");
            else this.meta = meta;
            mConnections = nbt.getByte("mConnections");
            mWorks = !nbt.getBoolean("mWorks");

            readCommonNBT(nbt);
            readCoverNBT(nbt);
            loadMetaTileNBT(nbt);
        }
    }

    @Override
    public void updateEntityProfiled() {
        if (!hasValidMetaTileEntity()) {
            if (mMetaTileEntity == null) return;
            mMetaTileEntity.setGTE(this);
        }
        final boolean isServerSide = isServerSide();
        if (hasValidMetaTileEntity()) {
            if (tickTimer++ == 0) {
                handleFirstTick(isServerSide);
                if (!hasValidMetaTileEntity()) {
                    return;
                }
            }
            if (!isServerSide) {
                handleBlockUpdateClient();

                if (!mNeedsClientTick) {
                    mWorkUpdate = mInventoryChanged = false;
                    tryDisableTicking();
                    return;
                }
            } else {
                if (tickTimer > 10) {
                    if (!doCoverThings()) {
                        return;
                    }
                    updateConnections();
                }
            }
            mMetaTileEntity.onPreTick(this, tickTimer);
            if (!hasValidMetaTileEntity()) {
                return;
            }
            if (isServerSide) {
                if (tickTimer == 10) {
                    issueBlockUpdate();
                }
                handlePositionChange();
            }
            mMetaTileEntity.onPostTick(this, tickTimer);
            if (!hasValidMetaTileEntity()) {
                return;
            }
            if (isServerSide) {
                if (tickTimer > 10) {
                    handleConnectionsChangeServer();
                    handleUpdateDataChangeServer();
                    handleSidedRedstoneChangeServer();
                }

                if (needsBlockUpdate) {
                    doBlockUpdateServer();
                }

                if (mNeedsTileUpdate) {
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    mNeedsTileUpdate = false;
                }
            }
        }
        mWorkUpdate = mInventoryChanged = false;
    }

    @Override
    public void onUnload() {
        if (canAccessData()) {
            onCoverUnload();
            mMetaTileEntity.onUnload();
        }
        super.onUnload();
    }

    public void updateConnections() {
        if (mConnections == mMetaTileEntity.connections) {
            return;
        }
        mConnections = mMetaTileEntity.connections;
        GregTechAPI.causeCableUpdate(worldObj, xCoord, yCoord, zCoord);
    }

    private void handleConnectionsChangeServer() {
        if (mConnections == oldConnections) {
            return;
        }
        oldConnections = mConnections;
        sendBlockEvent(GregTechTileClientEvents.CHANGE_COMMON_DATA, oldConnections);
    }

    @Override
    protected void onTickFail() {
        mMetaTileEntity.onTickFail(this, tickTimer);
    }

    private byte getUpdateData() {
        IMTE mte = getMTE();
        return mte == null ? 0 : mte.getUpdateData();
    }

    @Override
    public final void tileWriteToStream(ByteBuf buffer) {
        super.tileWriteToStream(buffer);
        buffer.writeByte(mConnections);
        buffer.writeByte(getUpdateData());
        buffer.writeByte(getSidedRedstoneMask());
        buffer.writeByte(getColorRaw());
    }

    @Override
    public final void tileReadFromStream(ByteBuf buffer) {
        super.tileReadFromStream(buffer);
        receiveClientEvent(GregTechTileClientEvents.CHANGE_COMMON_DATA, buffer.readByte());
        receiveClientEvent(GregTechTileClientEvents.CHANGE_CUSTOM_DATA, buffer.readByte());
        receiveClientEvent(GregTechTileClientEvents.CHANGE_REDSTONE_OUTPUT, buffer.readByte());
        receiveClientEvent(GregTechTileClientEvents.CHANGE_COLOR, buffer.readByte());
    }

    @Override
    public boolean receiveClientEvent(int aEventID, int aValue) {
        super.receiveClientEvent(aEventID, aValue);

        if (hasValidMetaTileEntity()) {
            try {
                mMetaTileEntity.receiveClientEvent((byte) aEventID, (byte) aValue);
            } catch (Exception e) {
                GT_FML_LOGGER.error("Encountered Exception while receiving Data from the Server", e);
            }
        }

        if (isClientSide()) {
            switch (aEventID) {
                case GregTechTileClientEvents.CHANGE_COMMON_DATA -> mConnections = (byte) aValue;
                case GregTechTileClientEvents.CHANGE_CUSTOM_DATA -> {
                    if (hasValidMetaTileEntity()) mMetaTileEntity.onValueUpdate((byte) aValue);
                }
                case GregTechTileClientEvents.CHANGE_COLOR -> {
                    if (aValue > 16 || aValue < 0) aValue = 0;
                    setColorRaw((byte) aValue);
                }
                case GregTechTileClientEvents.CHANGE_REDSTONE_OUTPUT -> setRedstoneOutput(aValue);
                case GregTechTileClientEvents.DO_SOUND -> {
                    if (hasValidMetaTileEntity() && isClientSettled())
                        mMetaTileEntity.doSound((byte) aValue, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                }
                case GregTechTileClientEvents.START_SOUND_LOOP -> {
                    if (hasValidMetaTileEntity() && isClientSettled())
                        mMetaTileEntity.startSoundLoop((byte) aValue, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                }
                case GregTechTileClientEvents.STOP_SOUND_LOOP -> {
                    if (hasValidMetaTileEntity() && isClientSettled())
                        mMetaTileEntity.stopSoundLoop((byte) aValue, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                }
            }
            issueTextureUpdate();
        }
        return true;
    }

    @Override
    public boolean isGivingInformation() {
        return canAccessData() && mMetaTileEntity.isGivingInformation();
    }

    @Override
    public String[] getInfoData() {
        return canAccessData() ? getMTE().getInfoData() : GTValues.emptyStringArray;
    }

    @Override
    public Map<String, String> getInfoMap() {
        return canAccessData() ? getMTE().getInfoMap() : Collections.emptyMap();
    }

    @Override
    public ForgeDirection getBackFacing() {
        return getFrontFacing().getOpposite();
    }

    @Override
    public ForgeDirection getFrontFacing() {
        return ForgeDirection.UNKNOWN;
    }

    @Override
    public void setFrontFacing(ForgeDirection aFacing) {}

    @Override
    public int getSizeInventory() {
        if (canAccessData()) return mMetaTileEntity.getSizeInventory();
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        if (canAccessData()) return mMetaTileEntity.getStackInSlot(aIndex);
        return null;
    }

    @Override
    public void setInventorySlotContents(int aIndex, ItemStack aStack) {
        markDirty();
        mInventoryChanged = true;
        if (canAccessData()) mMetaTileEntity
            .setInventorySlotContents(aIndex, worldObj.isRemote ? aStack : GTOreDictUnificator.setStack(true, aStack));
    }

    @Override
    public String getInventoryName() {
        if (canAccessData()) return mMetaTileEntity.getInventoryName();
        if (mteArray[meta] != null) return mteArray[meta].getInventoryName();
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        if (canAccessData()) return mMetaTileEntity.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory() {
        /* Do nothing */
    }

    @Override
    public void closeInventory() {
        /* Do nothing */
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return hasValidMetaTileEntity() && tickTimer > 1
            && getTileEntityOffset(0, 0, 0) == this
            && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64
            && mMetaTileEntity.isAccessAllowed(aPlayer);
    }

    @Override
    public void validate() {
        super.validate();
        tickTimer = 0;
    }

    @Override
    public void invalidate() {
        tileEntityInvalid = false;
        if (hasValidMetaTileEntity()) {
            mMetaTileEntity.onRemoval();
            mMetaTileEntity.setGTE(null);
        }
        super.invalidate();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) setInventorySlotContents(slot, null);
        return stack;
    }

    /**
     * Checks validity of meta tile and delegates to it
     */
    @Override
    public void onMachineBlockUpdate() {
        if (canAccessData()) mMetaTileEntity.onMachineBlockUpdate();
    }

    /**
     * Checks validity of meta tile and delegates to it
     */
    @Override
    public boolean isMachineBlockUpdateRecursive() {
        return canAccessData() && mMetaTileEntity.isMachineBlockUpdateRecursive();
    }

    @Override
    public int getProgress() {
        return canAccessData() ? mMetaTileEntity.getProgressTime() : 0;
    }

    @Override
    public int getMaxProgress() {
        return canAccessData() ? mMetaTileEntity.maxProgressTime() : 0;
    }

    @Override
    public boolean increaseProgress(int aProgressAmountInTicks) {
        return canAccessData() && mMetaTileEntity.increaseProgress(aProgressAmountInTicks) != aProgressAmountInTicks;
    }

    @Override
    public boolean hasThingsToDo() {
        return getMaxProgress() > 0;
    }

    @Override
    public void enableWorking() {
        if (!mWorks) mWorkUpdate = true;
        mWorks = true;
        reloadLocks();
    }

    @Override
    public void disableWorking() {
        mWorks = false;
        reloadLocks();
    }

    @Override
    public boolean isAllowedToWork() {
        return mWorks;
    }

    @Override
    public boolean hasWorkJustBeenEnabled() {
        return mWorkUpdate;
    }

    @Override
    public int getMeta() {
        return meta;
    }

    @Override
    public void setMeta(short meta) {
        this.meta = meta;
    }

    @Override
    public void scheduleTexturePacket() {}

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Class<T> capability, @NotNull ForgeDirection side) {
        if (canAccessData()) {
            return mMetaTileEntity.getCapability(capability, side);
        }
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean aActive) {
        /* Do nothing */
    }

    @Override
    public long getTimer() {
        return tickTimer;
    }

    @Override
    public boolean decreaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooLessEnergy) {
        return false;
    }

    @Override
    public boolean increaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        return false;
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection side, boolean waitForActive) {
        return false;
    }

    @Override
    public boolean outputsEnergyTo(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean outputsEnergyTo(ForgeDirection side, boolean waitForActive) {
        return false;
    }

    @Override
    public long getOutputAmperage() {
        return 0;
    }

    @Override
    public long getOutputVoltage() {
        return 0;
    }

    @Override
    public long getInputAmperage() {
        return 0;
    }

    @Override
    public long getInputVoltage() {
        return 0;
    }

    @Override
    public long getUniversalEnergyStored() {
        return Math.max(getStoredEU(), getStoredSteam());
    }

    @Override
    public long getUniversalEnergyCapacity() {
        return Math.max(getEUCapacity(), getSteamCapacity());
    }

    @Override
    public long getStoredEU() {
        return 0;
    }

    @Override
    public long getEUCapacity() {
        return 0;
    }

    @Override
    public ITexture[] getTexture(Block aBlock, ForgeDirection side) {
        return getTexture(side);
    }

    @Override
    public ITexture[] getTexture(ForgeDirection side) {
        final ITexture rIcon = getCoverTexture(side);
        if (rIcon != null) return new ITexture[] { rIcon };
        return getTextureUncovered(side);
    }

    @Override
    public ITexture[] getTextureCovered(ForgeDirection side) {
        final ITexture coverTexture = getCoverTexture(side);
        final ITexture[] textureUncovered = getTextureUncovered(side);
        final ITexture[] textureCovered;
        if (coverTexture != null) {
            textureCovered = Arrays.copyOf(textureUncovered, textureUncovered.length + 1);
            textureCovered[textureUncovered.length] = coverTexture;
            return textureCovered;
        } else {
            return textureUncovered;
        }
    }

    @Override
    public ITexture[] getTextureUncovered(ForgeDirection sideDirection) {
        if (!hasValidMetaTileEntity()) return Textures.BlockIcons.ERROR_RENDERING;
        final int tConnections, connexions = mConnections;
        tConnections = switch (connexions) {
            case IConnectable.CONNECTED_WEST, IConnectable.CONNECTED_EAST -> IConnectable.CONNECTED_WEST
                | IConnectable.CONNECTED_EAST;
            case IConnectable.CONNECTED_DOWN, IConnectable.CONNECTED_UP -> IConnectable.CONNECTED_DOWN
                | IConnectable.CONNECTED_UP;
            case IConnectable.CONNECTED_NORTH, IConnectable.CONNECTED_SOUTH -> IConnectable.CONNECTED_NORTH
                | IConnectable.CONNECTED_SOUTH;
            default -> connexions;
        };
        return mMetaTileEntity.getTexture(
            this,
            sideDirection,
            tConnections,
            getColorization(),
            tConnections == 0 || (tConnections & sideDirection.flag) != 0,
            getOutputRedstoneSignal(sideDirection) > 0);
    }

    @Override
    void refreshMetaTileEntityValidity() {
        mMetaTileEntityValid = mMetaTileEntity != null && mMetaTileEntity.getGTE() == this;
        mNeedsClientTick = mMetaTileEntity == null || mMetaTileEntity.needsClientTick();
    }

    @Override
    public void doExplosion(long explosionEu) {
        if (canAccessData()) {
            mMetaTileEntity.onExplosion();
            mMetaTileEntity.doExplosion(explosionEu);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        final ItemStack rStack = new ItemStack(GregTechAPI.sBlockMachines, 1, meta);
        final NBTTagCompound tNBT = new NBTTagCompound();

        writeCoverNBT(tNBT, true);

        if (hasValidMetaTileEntity()) mMetaTileEntity.setItemNBT(tNBT);
        if (!tNBT.hasNoTags()) rStack.setTagCompound(tNBT);

        onBaseTEDestroyed();
        return new ArrayList<>(Collections.singletonList(rStack));
    }

    @Override
    public boolean onRightClick(final EntityPlayer player, final ForgeDirection side, final float x, final float y,
        final float z) {
        final ForgeDirection wrenchingSide = GTUtility.determineWrenchingSide(side, x, y, z);
        final ForgeDirection effectiveSide = (!hasCoverAtSide(side)) ? wrenchingSide : side;
        Cover effectiveSideCover = getCoverAtSide(effectiveSide);
        if (isClientSide()) {
            // Place/configure Cover, sneak can also be: screwdriver, wrench, side cutter, soldering iron
            if (player.isSneaking()) {
                return (effectiveSideCover.hasCoverGUI());
            }
        }
        if (isServerSide()) {
            final ItemStack tCurrentItem = player.inventory.getCurrentItem();
            if (tCurrentItem != null) {
                if (getColorization() >= 0
                    && GTUtility.areStacksEqual(new ItemStack(Items.water_bucket, 1), tCurrentItem)) {
                    mMetaTileEntity.markDirty();
                    tCurrentItem.func_150996_a(Items.bucket);
                    setColorization((byte) -1);
                    return true;
                }
                if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sWrenchList)) {
                    if (mMetaTileEntity.onWrenchRightClick(side, wrenchingSide, player, x, y, z, tCurrentItem)) {
                        mMetaTileEntity.markDirty();
                        GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player);
                        sendSoundToPlayers(SoundResource.GTCEU_OP_WRENCH, 1.0F, 1);
                    }
                    return true;
                }
                if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sScrewdriverList)) {
                    if (!hasCoverAtSide(side) && hasCoverAtSide(wrenchingSide)) {
                        if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 200, player)) {
                            getCoverAtSide(wrenchingSide).onCoverScrewdriverClick(player, 0.5F, 0.5F, 0.5F);
                            mMetaTileEntity.onScrewdriverRightClick(wrenchingSide, player, x, y, z, tCurrentItem);
                            mMetaTileEntity.markDirty();
                            sendSoundToPlayers(SoundResource.GTCEU_OP_SCREWDRIVER, 1.0F, 1);
                        }
                    } else {
                        if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                            getCoverAtSide(side).onCoverScrewdriverClick(player, x, y, z);
                            mMetaTileEntity.onScrewdriverRightClick(side, player, x, y, z, tCurrentItem);
                            mMetaTileEntity.markDirty();
                            sendSoundToPlayers(SoundResource.GTCEU_OP_SCREWDRIVER, 1.0F, 1);
                        }
                    }
                    return true;
                }

                if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sHardHammerList)) {
                    return true;
                }

                if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sSoftMalletList)) {
                    if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                        if (mWorks) disableWorking();
                        else enableWorking();
                        mMetaTileEntity.markDirty();
                        GTUtility.sendChatTrans(
                            player,
                            isAllowedToWork() ? "GT5U.chat.pipe.processing.enable"
                                : "GT5U.chat.pipe.processing.disable");
                        sendSoundToPlayers(SoundResource.GTCEU_OP_SOFT_HAMMER, 1.0F, 1);
                    }
                    return true;
                }

                if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sWireCutterList)) {
                    if (mMetaTileEntity.onWireCutterRightClick(side, wrenchingSide, player, x, y, z, tCurrentItem)) {
                        mMetaTileEntity.markDirty();
                        // logic handled internally
                        sendSoundToPlayers(SoundResource.GTCEU_OP_WIRECUTTER, 1.0F, 1);
                    }
                    return true;
                }

                if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sSolderingToolList)) {
                    if (mMetaTileEntity.onSolderingToolRightClick(side, wrenchingSide, player, x, y, z, tCurrentItem)) {
                        mMetaTileEntity.markDirty();
                        // logic handled internally
                        sendSoundToPlayers(SoundResource.IC2_TOOLS_BATTERY_USE, 1.0F, -1);
                    } else if (GTModHandler.useSolderingIron(tCurrentItem, player)) {
                        GTUtility.sendChatTrans(
                            player,
                            toggleStrongRedstone(wrenchingSide) ? "GT5U.chat.machine.redstone_output_set.strong"
                                : "GT5U.chat.machine.redstone_output_set.weak",
                            new ChatComponentTranslation(GTUtility.getUnlocalizedSideName(wrenchingSide)));
                        sendSoundToPlayers(SoundResource.IC2_TOOLS_BATTERY_USE, 3.0F, -1);
                    }
                    return true;
                }

                if (!hasCoverAtSide(effectiveSide)) {
                    if (player.isSneaking() && CoverRegistry.isCover(tCurrentItem)) {
                        if (CoverRegistry.getCoverPlacer(tCurrentItem)
                            .isCoverPlaceable(effectiveSide, tCurrentItem, this)
                            && mMetaTileEntity.allowCoverOnSide(effectiveSide, tCurrentItem)) {

                            CoverRegistry.getCoverPlacer(tCurrentItem)
                                .placeCover(player, tCurrentItem, this, effectiveSide);

                            mMetaTileEntity.markDirty();
                            if (!player.capabilities.isCreativeMode) tCurrentItem.stackSize--;
                            sendSoundToPlayers(SoundResource.GTCEU_OP_WRENCH, 1.0F, 1);
                        }
                        return true;
                    }
                } else {
                    if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sCrowbarList)) {
                        if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                            sendSoundToPlayers(SoundResource.RANDOM_BREAK, 1.0F, -1);
                            dropCover(effectiveSide, side);
                            mMetaTileEntity.markDirty();
                        }
                        return true;
                    }
                }
            } else if (player.isSneaking()) { // Sneak click, no tool -> open cover config or turn back.
                return effectiveSideCover.isValid() && effectiveSideCover.onCoverShiftRightClick(player);
            }

            if (getCoverAtSide(side).onCoverRightClick(player, x, y, z)) return true;
        }
        if (!getCoverAtSide(side).isGUIClickable()) return false;
        try {
            if (!player.isSneaking() && hasValidMetaTileEntity()) {
                boolean value = mMetaTileEntity.onRightClick(this, player, side, x, y, z);
                mMetaTileEntity.markDirty();
                return value;
            }
        } catch (Exception e) {
            GT_FML_LOGGER.error("Encountered Exception while right clicking TileEntity", e);
        }

        return false;
    }

    @Override
    public void onLeftClick(EntityPlayer player) {
        try {
            if (player != null && hasValidMetaTileEntity()) mMetaTileEntity.onLeftClick(this, player);
        } catch (Exception e) {
            GT_FML_LOGGER.error("Encountered Exception while left clicking TileEntity", e);
        }
    }

    /**
     * Can put aStack into Slot
     */
    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return canAccessData() && mMetaTileEntity.isItemValidForSlot(aIndex, aStack);
    }

    /**
     * returns all valid Inventory Slots, no matter which Side (Unless it's covered). The Side Stuff is done in the
     * following two Functions.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int ordinalSide) {
        final Cover cover = getCoverAtSide(ForgeDirection.getOrientation(ordinalSide));
        if (canAccessData() && (cover.letsItemsOut(-1) || cover.letsItemsIn(-1)))
            return mMetaTileEntity.getAccessibleSlotsFromSide(ordinalSide);
        return GTValues.emptyIntArray;
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int ordinalSide) {
        return canAccessData() && getCoverAtSide(ForgeDirection.getOrientation(ordinalSide)).letsItemsIn(aIndex)
            && mMetaTileEntity.canInsertItem(aIndex, aStack, ordinalSide);
    }

    /**
     * Can pull aStack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int aIndex, ItemStack aStack, int ordinalSide) {
        final ForgeDirection side = ForgeDirection.getOrientation(ordinalSide);
        return canAccessData() && getCoverAtSide(side).letsItemsOut(aIndex)
            && mMetaTileEntity.canExtractItem(aIndex, aStack, ordinalSide);
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public void setGenericRedstoneOutput(boolean aOnOff) {
        // Do nothing
    }

    @Override
    public final IMTE getMTE() {
        return hasValidMetaTileEntity() ? mMetaTileEntity : null;
    }

    @Override
    public final void setMTE(IMTE mte) {
        mMetaTileEntity = (MetaPipeEntity) mte;
        refreshMetaTileEntityValidity();
    }

    @Override
    public void setLightValue(byte aLightValue) {
        //
    }

    @Override
    public long getAverageElectricInput() {
        return 0;
    }

    @Override
    public long getAverageElectricOutput() {
        return 0;
    }

    @Override
    public String getOwnerName() {
        return "Player";
    }

    @Override
    public void setOwnerName(String name) {}

    @Override
    public UUID getOwnerUuid() {
        return GTUtility.defaultUuid;
    }

    @Override
    public void setOwnerUuid(UUID uuid) {}

    @Override
    public byte getComparatorValue(ForgeDirection side) {
        return canAccessData() ? mMetaTileEntity.getComparatorValue(side) : 0;
    }

    @Override
    public ItemStack decrStackSize(int aIndex, int aAmount) {
        if (canAccessData()) {
            mInventoryChanged = true;
            return mMetaTileEntity.decrStackSize(aIndex, aAmount);
        }
        return null;
    }

    @Override
    public long injectEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        if (canAccessData()) return mMetaTileEntity.injectEnergyUnits(side, aVoltage, aAmperage);
        return 0;
    }

    @Override
    public boolean drainEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        return false;
    }

    private boolean canMoveFluidOnSide(ForgeDirection side, Fluid fluid, boolean isFill) {
        if (side == ForgeDirection.UNKNOWN) return true;

        final IFluidHandler tTileEntity = getITankContainerAtSide(side);
        // Only require a connection if there's something to connect to - Allows fluid cells & buckets to interact with
        // the pipe
        if (tTileEntity != null && !mMetaTileEntity.isConnectedAtSide(side)) return false;

        if (isFill && mMetaTileEntity.isLiquidInput(side) && getCoverAtSide(side).letsFluidIn(fluid)) return true;

        return !isFill && mMetaTileEntity.isLiquidOutput(side) && getCoverAtSide(side).letsFluidOut(fluid);
    }

    @Override
    public int fill(ForgeDirection side, FluidStack aFluidStack, boolean doFill) {
        if (tickTimer > 5 && canAccessData()
            && canMoveFluidOnSide(side, aFluidStack == null ? null : aFluidStack.getFluid(), true))
            return mMetaTileEntity.fill(side, aFluidStack, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection side, int maxDrain, boolean doDrain) {
        if (tickTimer > 5 && canAccessData()
            && canMoveFluidOnSide(
                side,
                mMetaTileEntity.getFluid() == null ? null
                    : mMetaTileEntity.getFluid()
                        .getFluid(),
                false))
            return mMetaTileEntity.drain(side, maxDrain, doDrain);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection side, FluidStack aFluidStack, boolean doDrain) {
        if (tickTimer > 5 && canAccessData()
            && canMoveFluidOnSide(side, aFluidStack == null ? null : aFluidStack.getFluid(), false))
            return mMetaTileEntity.drain(side, aFluidStack, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection side, Fluid aFluid) {
        if (tickTimer > 5 && canAccessData() && canMoveFluidOnSide(side, aFluid, true))
            return mMetaTileEntity.canFill(side, aFluid);
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection side, Fluid aFluid) {
        if (tickTimer > 5 && canAccessData() && canMoveFluidOnSide(side, aFluid, false))
            return mMetaTileEntity.canDrain(side, aFluid);
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection side) {
        final Cover cover = getCoverAtSide(side);
        if (canAccessData()
            && (side == ForgeDirection.UNKNOWN || (mMetaTileEntity.isLiquidInput(side) && cover.letsFluidIn(null))
                || (mMetaTileEntity.isLiquidOutput(side) && cover.letsFluidOut(null))
            // Doesn't need to be connected to get Tank Info -- otherwise things can't connect
            )) return mMetaTileEntity.getTankInfo(side);
        return GTValues.emptyFluidTankInfo;
    }

    @Override
    public boolean addStackToSlot(int aIndex, ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack)) return true;
        if (aIndex < 0 || aIndex >= getSizeInventory()) return false;
        final ItemStack tStack = getStackInSlot(aIndex);
        if (GTUtility.isStackInvalid(tStack)) {
            setInventorySlotContents(aIndex, aStack);
            return true;
        }
        aStack = GTOreDictUnificator.get(aStack);
        if (GTUtility.areStacksEqual(tStack, aStack)
            && tStack.stackSize + aStack.stackSize <= Math.min(aStack.getMaxStackSize(), getInventoryStackLimit())) {
            markDirty();
            tStack.stackSize += aStack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public boolean addStackToSlot(int aIndex, ItemStack aStack, int aAmount) {
        return addStackToSlot(aIndex, GTUtility.copyAmount(aAmount, aStack));
    }

    @Override
    public byte getColorization() {
        return (byte) (getColorRaw() - 1);
    }

    @Override
    public byte setColorization(byte aColor) {
        if (aColor > 15 || aColor < -1) aColor = -1;
        byte rawColor = (byte) (aColor + 1);
        setColorRaw(rawColor);
        return rawColor;
    }

    @Override
    public float getThickNess() {
        if (canAccessData()) return mMetaTileEntity.getSize();
        return 1.0F;
    }

    public boolean renderInside(ForgeDirection side) {
        if (canAccessData()) return mMetaTileEntity.renderInside(side);
        return false;
    }

    @Override
    public boolean isMufflerUpgradable() {
        return false;
    }

    @Override
    public boolean addMufflerUpgrade() {
        return false;
    }

    @Override
    public boolean isMuffled() {
        return false;
    }

    @Override
    public boolean isUniversalEnergyStored(long aEnergyAmount) {
        return getUniversalEnergyStored() >= aEnergyAmount;
    }

    @Override
    public byte getConnections() {
        return mConnections;
    }

    public void onNeighborBlockChange(int aX, int aY, int aZ) {
        if (canAccessData()) {
            final IMTE meta = getMTE();
            if (meta instanceof MetaPipeEntity) {
                // Trigger a checking of connections in case someone placed down a block that the pipe/wire shouldn't be
                // connected to.
                // However; don't do it immediately in case the world isn't finished loading
                // (This caused issues with AE2 GTEU p2p connections.
                ((MetaPipeEntity) meta).setCheckConnections();
            }
        }
    }

    @Override
    public int getLightOpacity() {
        return mMetaTileEntity == null ? 0 : mMetaTileEntity.getLightOpacity();
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        if (hasValidMetaTileEntity()) {
            getMTE().getWailaBody(itemStack, list, accessor, config);
        }
        super.getWailaBody(itemStack, list, accessor, config);
    }

    @Override
    public boolean hasWailaAdvancedBody(ItemStack itemStack, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (hasValidMetaTileEntity()) {
            return getMTE().hasWailaAdvancedBody(itemStack, accessor, config);
        }
        return super.hasWailaAdvancedBody(itemStack, accessor, config);
    }

    @Override
    public void getWailaAdvancedBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        if (hasValidMetaTileEntity()) {
            getMTE().getWailaAdvancedBody(itemStack, currentTip, accessor, config);
        }
        super.getWailaAdvancedBody(itemStack, currentTip, accessor, config);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        if (hasValidMetaTileEntity()) {
            getMTE().getWailaNBTData(player, tile, tag, world, x, y, z);
        }
    }
}
