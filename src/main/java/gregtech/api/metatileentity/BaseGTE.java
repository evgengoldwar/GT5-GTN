package gregtech.api.metatileentity;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.objects.XSTR.XSTR_INSTANCE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.IAlignment;
import com.gtnewhorizon.structurelib.alignment.IAlignmentProvider;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructableProvider;

import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.helpers.ICustomNameObject;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.TexturesIcons;
import gregtech.api.graphs.GenerateNodeMap;
import gregtech.api.graphs.GenerateNodeMapPower;
import gregtech.api.graphs.Node;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gregtech.api.util.shutdown.ShutDownReason;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.common.covers.Base.Cover;
import gregtech.common.render.IMTERenderer;
import gregtech.mixin.interfaces.accessors.EntityItemAccessor;
import ic2.api.Direction;
import io.netty.buffer.ByteBuf;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class BaseGTE extends CommonGTE
    implements IActionHost, IGridProxyable, IAlignmentProvider, IConstructableProvider, ICustomNameObject {

    private final boolean[] mActiveEUInputs = new boolean[] { false, false, false, false, false, false };
    private final boolean[] mActiveEUOutputs = new boolean[] { false, false, false, false, false, false };
    public long mLastSoundTick = 0;
    public boolean mWasShutdown = false;
    public @Nonnull ShutDownReason lastShutDownReason = ShutDownReasonRegistry.NONE;
    protected MTE mte;
    protected long mStoredEnergy = 0, mStoredSteam = 0;
    protected int mAverageEUInputIndex = 0, mAverageEUOutputIndex = 0;
    protected boolean mReleaseEnergy = false;
    protected long[] mAverageEUInput;
    protected long[] mAverageEUOutput;
    private boolean mTexturePacketScheduled = false;
    private boolean mHasEnoughEnergy = true, mRunningThroughTick = false, mInputDisabled = false,
        mOutputDisabled = false;
    private boolean mWorkUpdate = false;
    private byte oldTextureData = 0;
    private byte oldLightValueClient = 0, oldLightValue = -1, mLightValue = 0, mOtherUpgrades = 0;
    private ForgeDirection mFacing = ForgeDirection.DOWN, oldFacing = ForgeDirection.DOWN;
    private long oldOutput = 0, mAcceptedAmperes = Long.MAX_VALUE;
    private long mLastCheckTick = 0;
    private String mOwnerName = "";
    private UUID mOwnerUuid = GTUtility.defaultUuid;
    private int cableUpdateDelay = 30;

    // Texture data, must call scheduleTexturePacket when changed.
    private boolean mActive = false, mRedstone = false, mLockUpgrade = false, mWorks = true, mMuffler = false;

    public BaseGTE() {}

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        try {
            super.writeToNBT(nbt);
        } catch (Exception e) {
            GT_FML_LOGGER.error("Encountered CRITICAL ERROR while saving MetaTileEntity.", e);
        }
        try {
            nbt.setBoolean("mWasShutdown", mWasShutdown);
            nbt.setInteger("mID", meta);
            nbt.setLong("mStoredSteam", mStoredSteam);
            nbt.setLong("mStoredEnergy", mStoredEnergy);
            writeCoverNBT(nbt, false);
            writeCommonNBT(nbt);
            nbt.setByte("mLightValue", mLightValue);
            nbt.setByte("mOtherUpgrades", mOtherUpgrades);
            nbt.setShort("mFacing", (short) mFacing.ordinal());
            nbt.setString("mOwnerName", mOwnerName);
            nbt.setString("mOwnerUuid", mOwnerUuid == null ? "" : mOwnerUuid.toString());
            nbt.setBoolean("mLockUpgrade", mLockUpgrade);
            nbt.setBoolean("mMuffler", mMuffler);
            nbt.setBoolean("mActive", mActive);
            nbt.setBoolean("mWorks", !mWorks);
            nbt.setBoolean("mRedstone", mRedstone);
            nbt.setBoolean("mInputDisabled", mInputDisabled);
            nbt.setBoolean("mOutputDisabled", mOutputDisabled);
            nbt.setString("shutDownReasonID", getLastShutDownReason().getID());
            nbt.setTag("shutDownReason", getLastShutDownReason().writeToNBT(new NBTTagCompound()));
        } catch (Exception e) {
            GT_FML_LOGGER.error("Encountered CRITICAL ERROR while saving MetaTileEntity.", e);
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
            mStoredSteam = nbt.getLong("mStoredSteam");
            mStoredEnergy = nbt.getLong("mStoredEnergy");
            mLightValue = nbt.getByte("mLightValue");
            mFacing = oldFacing = ForgeDirection.getOrientation(nbt.getShort("mFacing"));
            mOwnerName = nbt.getString("mOwnerName");
            setShutdownStatus(nbt.getBoolean("mWasShutdown"));
            String shutDownReasonID = nbt.getString("shutDownReasonID");
            if (ShutDownReasonRegistry.isRegistered(shutDownReasonID)) {
                ShutDownReason reason = ShutDownReasonRegistry.getSampleFromRegistry(shutDownReasonID)
                    .newInstance();
                reason.readFromNBT(nbt.getCompoundTag("shutDownReason"));
                setShutDownReason(reason);
            }
            try {
                mOwnerUuid = UUID.fromString(nbt.getString("mOwnerUuid"));
            } catch (IllegalArgumentException e) {
                mOwnerUuid = null;
            }
            mLockUpgrade = nbt.getBoolean("mLockUpgrade");
            mMuffler = nbt.getBoolean("mMuffler");
            mActive = nbt.getBoolean("mActive");
            mWorks = !nbt.getBoolean("mWorks");
            mRedstone = nbt.getBoolean("mRedstone");
            mInputDisabled = nbt.getBoolean("mInputDisabled");
            mOutputDisabled = nbt.getBoolean("mOutputDisabled");
            mOtherUpgrades = (byte) (nbt.getByte("mOtherUpgrades") + nbt.getByte("mBatteries")
                + nbt.getByte("mLiBatteries"));

            final int nbtVersion = nbt.getInteger("nbtVersion");
            readCommonNBT(nbt);
            readCoverNBT(nbt);
            loadMetaTileNBT(nbt);
        }
    }

    /**
     * Called when trying to charge Items
     */
    public void chargeItem(ItemStack aStack) {
        decreaseStoredEU(
            GTModHandler.chargeElectricItem(
                aStack,
                (int) Math.min(Integer.MAX_VALUE, getStoredEU()),
                (int) Math.min(Integer.MAX_VALUE, mte.getOutputTier()),
                false,
                false),
            true);
    }

    /**
     * Called when trying to discharge Items
     */
    public void dischargeItem(ItemStack aStack) {
        increaseStoredEnergyUnits(
            GTModHandler.dischargeElectricItem(
                aStack,
                (int) Math.min(Integer.MAX_VALUE, getEUCapacity() - getStoredEU()),
                (int) Math.min(Integer.MAX_VALUE, mte.getInputTier()),
                false,
                false,
                false),
            true);
    }

    protected boolean isRainPossible() {
        BiomeGenBase biome = getBiome();
        // see net.minecraft.client.renderer.EntityRenderer.renderRainSnow
        return biome.rainfall > 0 && (biome.canSpawnLightningBolt() || biome.getEnableSnow());
    }

    /**
     * Check if this is exposed to rain
     *
     * @return True if exposed to rain, else false
     */
    public boolean isRainExposed() {
        final int precipitationHeightAtSide2 = worldObj.getPrecipitationHeight(xCoord, zCoord - 1);
        final int precipitationHeightAtSide3 = worldObj.getPrecipitationHeight(xCoord, zCoord + 1);
        final int precipitationHeightAtSide4 = worldObj.getPrecipitationHeight(xCoord - 1, zCoord);
        final int precipitationHeightAtSide5 = worldObj.getPrecipitationHeight(xCoord + 1, zCoord);
        return (!hasCoverAtSide(ForgeDirection.UP) && worldObj.getPrecipitationHeight(xCoord, zCoord) - 2 < yCoord)
            || (!hasCoverAtSide(ForgeDirection.NORTH) && precipitationHeightAtSide2 - 1 < yCoord
                && precipitationHeightAtSide2 > -1)
            || (!hasCoverAtSide(ForgeDirection.SOUTH) && precipitationHeightAtSide3 - 1 < yCoord
                && precipitationHeightAtSide3 > -1)
            || (!hasCoverAtSide(ForgeDirection.WEST) && precipitationHeightAtSide4 - 1 < yCoord
                && precipitationHeightAtSide4 > -1)
            || (!hasCoverAtSide(ForgeDirection.EAST) && precipitationHeightAtSide5 - 1 < yCoord
                && precipitationHeightAtSide5 > -1);
    }

    @Override
    public final void updateEntityProfiled() {
        if (!hasValidMetaTileEntity()) {
            if (mte == null) return;
            mte.setGTE(this);
        }
        mRunningThroughTick = true;
        if (hasValidMetaTileEntity()) {
            final boolean isServerSide = isServerSide();
            if (tickTimer++ == 0) {
                handleFirstTick(isServerSide);
                if (!hasValidMetaTileEntity()) {
                    mRunningThroughTick = false;
                    return;
                }
            }
            if (!isServerSide) {
                applyClientLightValue();

                handleBlockUpdateClient();

                if (!mNeedsClientTick) {
                    mWorkUpdate = mInventoryChanged = mRunningThroughTick = false;
                    tryDisableTicking();
                    return;
                }
            } else {
                if (tickTimer > 10 && !doCoverThings()) {
                    mRunningThroughTick = false;
                    return;
                }
                updateAvgEUIO();
            }

            doPreTick();

            if (!hasValidMetaTileEntity()) {
                mRunningThroughTick = false;
                return;
            }
            if (isServerSide) {
                handlePositionChange();
                handleFacingChange();

                if (mNeedsTileUpdate) {
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    mNeedsTileUpdate = false;
                }

                if (!handleElectricUpdates()) {
                    mRunningThroughTick = false;
                    return;
                }

                handleInventoryDechargingServer();
                handleInventoryChargingServer();
            }
            doPostTick();
            if (!hasValidMetaTileEntity()) {
                mRunningThroughTick = false;
                return;
            }
            if (isServerSide) {
                handleCableUpdates();

                if (tickTimer > 10) {
                    maybeSendTextureData();
                    handleUpdateDataChangeServer();
                    handleSidedRedstoneChangeServer();

                    if (mLightValue != oldLightValue) {
                        updateLightValue();
                        issueTextureUpdate();
                        oldLightValue = mLightValue;
                        sendBlockEvent(GregTechTileClientEvents.CHANGE_LIGHT, mLightValue);
                    }
                }

                if (needsBlockUpdate) {
                    doBlockUpdateServer();
                }
            }
        }
        mWorkUpdate = mInventoryChanged = mRunningThroughTick = false;
    }

    private void applyClientLightValue() {
        if (mLightValue == oldLightValueClient) return;
        updateLightValue();
        oldLightValueClient = mLightValue;
        issueTextureUpdate();
    }

    private void doPreTick() {
        mte.onPreTick(this, tickTimer);
    }

    private void doPostTick() {
        mte.onPostTick(this, tickTimer);
    }

    /**
     * Updates the light value for the tile entity
     */
    private void updateLightValue() {
        worldObj.setLightValue(EnumSkyBlock.Block, xCoord, yCoord, zCoord, mLightValue);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord + 1, yCoord, zCoord);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord - 1, yCoord, zCoord);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord + 1, zCoord);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord - 1, zCoord);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord + 1);
        worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord - 1);
    }

    /**
     * Updates the average EU I/O
     */
    private void updateAvgEUIO() {
        ensureAverageEUArrays();
        if (++mAverageEUInputIndex >= mAverageEUInput.length) {
            mAverageEUInputIndex = 0;
        }
        if (++mAverageEUOutputIndex >= mAverageEUOutput.length) {
            mAverageEUOutputIndex = 0;
        }
        mAverageEUInput[mAverageEUInputIndex] = 0;
        mAverageEUOutput[mAverageEUOutputIndex] = 0;
    }

    private void ensureAverageEUArrays() {
        if (mAverageEUInput == null) {
            mAverageEUInput = new long[5];
            mAverageEUOutput = new long[5];
        }
    }

    /**
     * Handles the tile entity's facing changing
     */
    private void handleFacingChange() {
        if (mFacing == oldFacing) {
            return;
        }
        oldFacing = mFacing;
        checkDropCover();
        issueBlockUpdate();
    }

    /**
     * Handles electricity-related updates
     *
     * @return true if the MetaTileEntity is still valid
     */
    private boolean handleElectricUpdates() {
        if (tickTimer <= 20 || !mte.isElectric()) {
            return true;
        }
        mAcceptedAmperes = 0;
        oldOutput = getOutputVoltage();
        updateActiveEUIOFaces();
        handleEUOutput();
        return handleEnvironmentalEffects();
    }

    /**
     * Updates which faces are active for EU I/O
     */
    private void updateActiveEUIOFaces() {
        if (!mte.isEnetOutput() && !mte.isEnetInput()) {
            return;
        }
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            int ordinalSide = side.ordinal();
            mActiveEUInputs[ordinalSide] = isEnergyInputSide(side);
            mActiveEUOutputs[ordinalSide] = isEnergyOutputSide(side);
        }
    }

    /**
     * Performs EU output to the energy network
     */
    private void handleEUOutput() {
        if (!mte.isEnetOutput() || oldOutput <= 0) {
            return;
        }
        int voltageTier = Math.max(0, GTUtility.getTier(oldOutput) - 1);
        // voltage + output loss
        long outputVoltage = Math.max(oldOutput, oldOutput + (1L << voltageTier));
        long availableAmperage = (getStoredEU() - mte.getMinimumStoredEU()) / outputVoltage;
        long usableAmperage = Math.min(getOutputAmperage(), availableAmperage);
        if (usableAmperage <= 0) {
            return;
        }
        final long eu = outputVoltage * Util.emitEnergyToNetwork(oldOutput, usableAmperage, this);
        ensureAverageEUArrays();
        mAverageEUOutput[mAverageEUOutputIndex] += eu;
        decreaseStoredEU(eu, true);
    }

    /**
     * Handles environmental effects
     *
     * @return true if the MetaTileEntity is still valid
     */
    private boolean handleEnvironmentalEffects() {
        if (getEUCapacity() <= 0) {
            return true;
        }
        if (!handleFireExposure()) {
            return false;
        }
        return handleRainExposure();
    }

    /**
     * Handles environmental affects due to adjacent fire blocks
     *
     * @return true if the MetaTileEntity is still valid
     */
    private boolean handleFireExposure() {
        if (!GregTechAPI.sMachineFireExplosions || getRandomNumber(1000) != 0) {
            return true;
        }
        Block block = getBlockAtSide(ForgeDirection.getOrientation(getRandomNumber(6)));
        if (block instanceof BlockFire) {
            doEnergyExplosion();
            return hasValidMetaTileEntity();
        }
        return true;
    }

    /**
     * Handles environmental affects due to rain and thunder exposure
     *
     * @return true if the MetaTileEntity is still valid
     */
    private boolean handleRainExposure() {
        if (!GregTechAPI.sMachineRainExplosions || !mte.willExplodeInRain()) {
            return true;
        }
        if (getRandomNumber(1000) != 0 || !isRainPossible()) {
            return true;
        }
        // Short-circuit so world.isRaining() check happens before isRainExposed, saving TPS since rain exposed check
        // can be slow.
        // This logic can be compressed further by only checking for isRainExposed once IF we can guarantee it never
        // thunders without raining, but I don't know if this is true or not.
        if (worldObj.isRaining() && isRainExposed()) {
            if (getRandomNumber(10) == 0) {
                GTLog.writeExplosionLog(this, this.getLocalName(), "explosion due to rain!");
                doEnergyExplosion();
            } else {
                GTLog.writeExplosionLog(this, this.getLocalName(), "set to fire due to rain!");
                setOnFire();
            }
        }
        if (!hasValidMetaTileEntity()) {
            return false;
        }
        if (!GregTechAPI.sMachineThunderExplosions || !worldObj.isThundering()) {
            return true;
        }
        if (getRandomNumber(3) != 0 || !isRainExposed()) {
            return true;
        }
        GTLog.writeExplosionLog(this, this.getLocalName(), "explosion due to thunderstorm!");
        doEnergyExplosion();
        return hasValidMetaTileEntity();
    }

    /**
     * Handles discharging items from the de-charger slot
     */
    private void handleInventoryDechargingServer() {
        if (mte.dechargerSlotCount() <= 0 || getStoredEU() >= getEUCapacity()) {
            return;
        }
        for (int i = mte.dechargerSlotStartIndex(), k = mte.dechargerSlotCount() + i; i < k; i++) {
            if (mte.inventory[i] == null || getStoredEU() >= getEUCapacity()) {
                continue;
            }
            // electric batteries
            dischargeItem(mte.inventory[i]);
            // consumable, e.g. redstone
            double ic2Energy = ic2.api.info.Info.itemEnergy.getEnergyValue(mte.inventory[i]);
            if (ic2Energy > 0 && getStoredEU() + ic2Energy < getEUCapacity()) {
                increaseStoredEnergyUnits((long) ic2Energy, false);
                mte.inventory[i].stackSize--;
                mInventoryChanged = true;
            }
            if (mte.inventory[i].stackSize <= 0) {
                mte.inventory[i] = null;
                mInventoryChanged = true;
            }
        }
    }

    /**
     * Handles charging items in the charger slot
     */
    private void handleInventoryChargingServer() {
        if (mte.rechargerSlotCount() <= 0 || getStoredEU() <= 0) {
            return;
        }
        for (int i = mte.rechargerSlotStartIndex(), k = mte.rechargerSlotCount() + i; i < k; i++) {
            if (getStoredEU() <= 0 || mte.inventory[i] == null) {
                continue;
            }
            chargeItem(mte.inventory[i]);
            if (mte.inventory[i].stackSize <= 0) {
                mte.inventory[i] = null;
                mInventoryChanged = true;
            }
        }
    }

    /**
     * Handles cable power node updates
     */
    private void handleCableUpdates() {
        if (tickTimer > 20 && cableUpdateDelay == 0) {
            generatePowerNodes();
        }
        cableUpdateDelay--;
    }

    @Override
    protected void onTickFail() {
        mte.onTickFail(this, tickTimer);
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

    @Override
    public void scheduleTexturePacket() {
        if (isClientSide()) return;
        mTexturePacketScheduled = true;
        if (mTickDisabled) {
            maybeSendTextureData();
        }
    }

    @Override
    public void setGenericRedstoneOutput(boolean aOnOff) {
        mRedstone = aOnOff;
        scheduleTexturePacket();
        if (isServerSide()) {
            issueBlockUpdate();
        }
    }

    private byte getTextureData() {
        return (byte) ((mFacing.ordinal() & 7) | (mActive ? 8 : 0)
            | (mRedstone ? 16 : 0)
            | (mLockUpgrade ? 32 : 0)
            | (mWorks ? 64 : 0)
            | (mMuffler ? 128 : 0));
    }

    private byte getUpdateData() {
        IMTE mte = getMTE();
        return mte == null ? 0 : mte.getUpdateData();
    }

    @Override
    public final void tileWriteToStream(ByteBuf buffer) {
        super.tileWriteToStream(buffer);
        buffer.writeByte(getTextureData());
        buffer.writeByte(getUpdateData());
        buffer.writeByte(getSidedRedstoneMask());
        buffer.writeByte(getColorRaw());
    }

    @Override
    public final void tileReadFromStream(ByteBuf buffer) {
        super.tileReadFromStream(buffer);
        receiveClientEvent(GregTechTileClientEvents.CHANGE_COMMON_DATA, buffer.readByte());
        receiveClientEvent(GregTechTileClientEvents.CHANGE_CUSTOM_DATA, buffer.readByte() & 0x7F);
        receiveClientEvent(GregTechTileClientEvents.CHANGE_REDSTONE_OUTPUT, buffer.readByte());
        receiveClientEvent(GregTechTileClientEvents.CHANGE_COLOR, buffer.readByte());
    }

    @Override
    public boolean receiveClientEvent(int aEventID, int aValue) {
        super.receiveClientEvent(aEventID, aValue);

        if (hasValidMetaTileEntity()) {
            try {
                mte.receiveClientEvent((byte) aEventID, (byte) aValue);
            } catch (Exception e) {
                GT_FML_LOGGER.error(
                    "Encountered Exception while receiving Data from the Server, the Client should've been crashed by now, but I prevented that. Please report immediately to GregTech Intergalactical!!!");
                GT_FML_LOGGER.error(e);
            }
        }

        if (isClientSide()) {
            switch (aEventID) {
                case GregTechTileClientEvents.CHANGE_COMMON_DATA -> {
                    mFacing = ForgeDirection.getOrientation((byte) (aValue & 7));
                    mActive = ((aValue & 8) != 0);
                    mRedstone = ((aValue & 16) != 0);
                    // mLockUpgrade = ((aValue&32) != 0);
                    mWorks = ((aValue & 64) != 0);
                    mMuffler = ((aValue & 128) != 0);
                }
                case GregTechTileClientEvents.CHANGE_CUSTOM_DATA -> {
                    if (hasValidMetaTileEntity()) {
                        mte.onValueUpdate((byte) (aValue & 0x7F));
                    }
                }
                case GregTechTileClientEvents.CHANGE_COLOR -> {
                    if (aValue > 16 || aValue < 0) aValue = 0;
                    setColorRaw((byte) aValue);
                }
                case GregTechTileClientEvents.CHANGE_REDSTONE_OUTPUT -> setRedstoneOutput(aValue);
                case GregTechTileClientEvents.DO_SOUND -> {
                    if (hasValidMetaTileEntity() && isClientSettled())
                        mte.doSound((byte) aValue, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                }
                case GregTechTileClientEvents.START_SOUND_LOOP -> {
                    if (hasValidMetaTileEntity() && isClientSettled())
                        mte.startSoundLoop((byte) aValue, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                }
                case GregTechTileClientEvents.STOP_SOUND_LOOP -> {
                    if (hasValidMetaTileEntity() && isClientSettled())
                        mte.stopSoundLoop((byte) aValue, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                }
                case GregTechTileClientEvents.CHANGE_LIGHT -> {
                    mLightValue = (byte) aValue;
                    if (mTickDisabled) applyClientLightValue();
                }
            }
            issueTextureUpdate();
            if ((aEventID == GregTechTileClientEvents.CHANGE_COMMON_DATA
                || aEventID == GregTechTileClientEvents.CHANGE_CUSTOM_DATA) && hasValidMetaTileEntity()) {
                mte.onClientSoundStateChanged();
            }
        }
        return true;
    }

    @Override
    public boolean isGivingInformation() {
        return canAccessData() && mte.isGivingInformation();
    }

    @Override
    public String[] getInfoData() {
        return canAccessData() ? getMTE().getInfoData() : GTValues.emptyStringArray;
    }

    @Override
    public void getExtraInfoData(List<String> info) {
        if (canAccessData()) {
            getMTE().getExtraInfoData(info);
        }
    }

    @Override
    public Map<String, String> getInfoMap() {
        return canAccessData() ? getMTE().getInfoMap() : Collections.emptyMap();
    }

    @Override
    public ForgeDirection getBackFacing() {
        return mFacing.getOpposite();
    }

    @Override
    public ForgeDirection getFrontFacing() {
        return mFacing;
    }

    @Override
    public void setFrontFacing(ForgeDirection aFacing) {
        if (isValidFacing(aFacing)) {
            mFacing = aFacing;
            scheduleTexturePacket();
            mte.onFacingChange();
            cableUpdateDelay = 10;

            if (mte.shouldTriggerBlockUpdate()) {
                // If we're triggering a block update this will call onMachineBlockUpdate()
                GregTechAPI.causeMachineUpdate(worldObj, xCoord, yCoord, zCoord);
            } else {
                // If we're not trigger a cascading one, call the update here.
                onMachineBlockUpdate();
            }
        }
    }

    @Override
    public int getSizeInventory() {
        if (canAccessData()) return mte.getSizeInventory();
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        if (canAccessData()) return mte.getStackInSlot(aIndex);
        return null;
    }

    @Override
    public void setInventorySlotContents(int aIndex, ItemStack aStack) {
        mInventoryChanged = true;
        if (canAccessData()) {
            markDirty();
            mte.setInventorySlotContents(aIndex, aStack);
        }
    }

    @Override
    public String getInventoryName() {
        if (canAccessData()) return mte.getInventoryName();
        if (mteArray[meta] != null) return mteArray[meta].getInventoryName();
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        if (canAccessData()) return mte.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return canAccessData() && playerOwnsThis(aPlayer, false)
            && tickTimer > 1
            && getTileEntityOffset(0, 0, 0) == this
            && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64
            && mte.isAccessAllowed(aPlayer);
    }

    @Override
    public void validate() {
        super.validate();
        tickTimer = 0;
    }

    @Override
    public void invalidate() {
        tileEntityInvalid = false;
        if (canAccessData()) {
            invalidateAE();
            mte.onRemoval();
            mte.setGTE(null);
        }
        super.invalidate();
    }

    @Override
    public void onUnload() {
        if (canAccessData()) {
            onCoverUnload();
            mte.onUnload();
        }
        super.onUnload();
        onChunkUnloadAE();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        final ItemStack stack = getStackInSlot(slot);
        if (stack != null) setInventorySlotContents(slot, null);
        return stack;
    }

    /**
     * Checks validity of meta tile and delegates to it
     */
    @Override
    public void onMachineBlockUpdate() {
        if (canAccessData()) mte.onMachineBlockUpdate();
        cableUpdateDelay = 10;
    }

    /**
     * Checks validity of meta tile and delegates to it
     */
    @Override
    public boolean isMachineBlockUpdateRecursive() {
        return canAccessData() && mte.isMachineBlockUpdateRecursive();
    }

    @Override
    public void onAdjacentBlockChange(int x, int y, int z) {
        super.onAdjacentBlockChange(x, y, z);
        if (hasValidMetaTileEntity()) {
            mte.onAdjacentBlockChange(x, y, z);
        }
    }

    @Override
    public int getProgress() {
        return canAccessData() ? mte.getProgressTime() : 0;
    }

    @Override
    public int getMaxProgress() {
        return canAccessData() ? mte.maxProgressTime() : 0;
    }

    @Override
    public boolean increaseProgress(int aProgressAmountInTicks) {
        return canAccessData() && mte.increaseProgress(aProgressAmountInTicks) != aProgressAmountInTicks;
    }

    @Override
    public boolean hasThingsToDo() {
        return getMaxProgress() > 0;
    }

    @Override
    public void enableWorking() {
        if (!mWorks) mWorkUpdate = true;
        mWorks = true;
        scheduleTexturePacket();
        setShutdownStatus(false);
        setShutDownReason(ShutDownReasonRegistry.NONE);
        if (hasValidMetaTileEntity()) {
            mte.onEnableWorking();
        }
    }

    @Override
    public void disableWorking() {
        mWorks = false;
        scheduleTexturePacket();
        if (hasValidMetaTileEntity()) {
            mte.onDisableWorking();
        }
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

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Class<T> capability, @NotNull ForgeDirection side) {
        if (canAccessData()) {
            return mte.getCapability(capability, side);
        }
        return null;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void setActive(boolean aActive) {
        mActive = aActive;
        scheduleTexturePacket();
        if (hasValidMetaTileEntity()) {
            mte.onSetActive(aActive);
        }
    }

    private void maybeSendTextureData() {
        if (mTexturePacketScheduled) {
            byte textureData = getTextureData();
            if (textureData != oldTextureData) {
                oldTextureData = textureData;
                sendBlockEvent(GregTechTileClientEvents.CHANGE_COMMON_DATA, oldTextureData);
            }
            mTexturePacketScheduled = false;
        }
    }

    @Override
    public long getTimer() {
        return tickTimer;
    }

    @Override
    public boolean decreaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooLessEnergy) {
        if (!canAccessData()) return false;
        return mHasEnoughEnergy = decreaseStoredEU(aEnergy, aIgnoreTooLessEnergy) || decreaseStoredSteam(aEnergy, false)
            || (aIgnoreTooLessEnergy && (decreaseStoredSteam(aEnergy, true)));
    }

    @Override
    public boolean increaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        if (!canAccessData()) return false;
        if (getStoredEU() < getEUCapacity() || aIgnoreTooMuchEnergy) {
            setStoredEU(mte.getEUVar() + aEnergy);
            return true;
        }
        return false;
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection side) {
        return inputEnergyFrom(side, true);
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection side, boolean waitForActive) {
        if (side == ForgeDirection.UNKNOWN) return true;
        if (isServerSide() && waitForActive) return mActiveEUInputs[side.ordinal()] && !mReleaseEnergy;
        return isEnergyInputSide(side);
    }

    @Override
    public boolean outputsEnergyTo(ForgeDirection side) {
        return outputsEnergyTo(side, true);
    }

    @Override
    public boolean outputsEnergyTo(ForgeDirection side, boolean waitForActive) {
        if (side == ForgeDirection.UNKNOWN) return true;
        if (isServerSide() && waitForActive) return (mActiveEUOutputs[side.ordinal()]) || mReleaseEnergy;
        return isEnergyOutputSide(side);
    }

    @Override
    public boolean isEnetOutput() {
        return mte != null && mte.isEnetOutput();
    }

    @Override
    public boolean isEnetInput() {
        return mte != null && mte.isEnetInput();
    }

    public void generatePowerNodes() {
        if (isServerSide() && (isEnetInput() || isEnetOutput())) {
            final int time = MinecraftServer.getServer()
                .getTickCounter();
            for (final ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (outputsEnergyTo(side, false) || inputEnergyFrom(side, false)) {
                    final IGTE TE = getIGregTechTileEntityAtSide(side);
                    if (TE instanceof BasePipeGTE pipe && (pipe.getConnections() & side.getOpposite().flag) != 0) {
                        final Node node = pipe.getNode();
                        if (node == null) {
                            new GenerateNodeMapPower(pipe);
                        } else if (node.mCreationTime != time) {
                            GenerateNodeMap.clearNodeMap(node, -1);
                            new GenerateNodeMapPower(pipe);
                        }
                    }
                }
            }
        }
    }

    public void setCableUpdateDelay(int delay) {
        cableUpdateDelay = delay;
    }

    @Override
    public long getOutputAmperage() {
        if (canAccessData() && mte.isElectric()) return mte.maxAmperesOut();
        return 0;
    }

    @Override
    public long getOutputVoltage() {
        if (canAccessData() && mte.isElectric() && mte.isEnetOutput()) return mte.maxEUOutput();
        return 0;
    }

    @Override
    public long getInputAmperage() {
        if (canAccessData() && mte.isElectric()) return mte.maxAmperesIn();
        return 0;
    }

    @Override
    public long getInputVoltage() {
        if (canAccessData() && mte.isElectric()) return mte.maxEUInput();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean increaseStoredSteam(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        if (!canAccessData()) return false;
        if (mte.getSteamVar() < getSteamCapacity() || aIgnoreTooMuchEnergy) {
            setStoredSteam(mte.getSteamVar() + aEnergy);
            return true;
        }
        return false;
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
        if (canAccessData()) {
            final long cap = mte.maxEUStore();
            final long stored = mte.getEUVar();
            return Math.min(stored, cap);
        }
        return 0;
    }

    @Override
    public long getEUCapacity() {
        if (canAccessData()) return mte.maxEUStore();
        return 0;
    }

    @Override
    public long getStoredSteam() {
        if (canAccessData()) return Math.min(mte.getSteamVar(), getSteamCapacity());
        return 0;
    }

    @Override
    public long getSteamCapacity() {
        if (canAccessData()) return mte.maxSteamStore();
        return 0;
    }

    @Override
    public ITexture[] getTexture(Block aBlock, ForgeDirection side) {
        final ITexture coverTexture = getCoverTexture(side);
        final ITexture[] textureUncovered = hasValidMetaTileEntity()
            ? mte.getTexture(this, side, mFacing, getColorization(), mActive, getOutputRedstoneSignal(side) > 0)
            : new ITexture[] { TextureFactory.of(TexturesIcons.MISSING) };
        final ITexture[] textureCovered;
        if (coverTexture != null) {
            textureCovered = Arrays.copyOf(textureUncovered, textureUncovered.length + 1);
            textureCovered[textureUncovered.length] = coverTexture;
            return textureCovered;
        } else {
            return textureUncovered;
        }
    }

    private boolean isEnergyInputSide(ForgeDirection side) {
        if (side != ForgeDirection.UNKNOWN) {
            if (!getCoverAtSide(side).letsEnergyIn()) return false;
            if (isInvalid() || mReleaseEnergy) return false;
            if (canAccessData() && mte.isElectric() && mte.isEnetInput()) return mte.isInputFacing(side);
        }
        return false;
    }

    private boolean isEnergyOutputSide(ForgeDirection side) {
        if (side != ForgeDirection.UNKNOWN) {
            if (!getCoverAtSide(side).letsEnergyOut()) return false;
            if (isInvalid() || mReleaseEnergy) return mReleaseEnergy;
            if (canAccessData() && mte.isElectric() && mte.isEnetOutput()) return mte.isOutputFacing(side);
        }
        return false;
    }

    @Override
    void refreshMetaTileEntityValidity() {
        mMetaTileEntityValid = mte != null && mte.getGTE() == this;
        mNeedsClientTick = mte == null || mte.needsClientTick();
    }

    public boolean setStoredEU(long aEnergy) {
        if (!canAccessData()) return false;
        if (aEnergy < 0) aEnergy = 0;
        mte.setEUVar(aEnergy);
        return true;
    }

    public boolean setStoredSteam(long aEnergy) {
        if (!canAccessData()) return false;
        if (aEnergy < 0) aEnergy = 0;
        mte.setSteamVar(aEnergy);
        return true;
    }

    public boolean decreaseStoredEU(long aEnergy, boolean aIgnoreTooLessEnergy) {
        if (!canAccessData()) {
            return false;
        }
        if (aIgnoreTooLessEnergy || mte.getEUVar() - aEnergy >= 0) {
            setStoredEU(mte.getEUVar() - aEnergy);
            if (mte.getEUVar() < 0) {
                setStoredEU(0);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean decreaseStoredSteam(long aEnergy, boolean aIgnoreTooLessEnergy) {
        if (!canAccessData()) return false;
        if (aIgnoreTooLessEnergy || mte.getSteamVar() - aEnergy >= 0) {
            setStoredSteam(mte.getSteamVar() - aEnergy);
            if (mte.getSteamVar() < 0) {
                setStoredSteam(0);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean playerOwnsThis(EntityPlayer aPlayer, boolean aCheckPrecicely) {
        if (!canAccessData()) return false;
        if (aCheckPrecicely || privateAccess() || (mOwnerName.isEmpty()))
            if ((mOwnerName.isEmpty()) && isServerSide()) {
                setOwnerName(aPlayer.getDisplayName());
                setOwnerUuid(aPlayer.getUniqueID());
            } else return !privateAccess() || aPlayer.getDisplayName()
                .equals("Player") || mOwnerName.equals("Player") || mOwnerName.equals(aPlayer.getDisplayName());
        return true;
    }

    public boolean privateAccess() {
        if (!canAccessData()) return mLockUpgrade;
        return mLockUpgrade || mte.ownerControl();
    }

    public void doEnergyExplosion() {
        if (getUniversalEnergyCapacity() > 0 && getUniversalEnergyStored() >= getUniversalEnergyCapacity() / 5) {
            String reason = "Energy Explosion, injected " + getUniversalEnergyStored()
                + "EU >= "
                + getUniversalEnergyCapacity() / 5D
                + "Capacity of the Machine!";
            GTLog.writeExplosionLog(this, this.getLocalName(), reason);

            doExplosion(
                oldOutput * (getUniversalEnergyStored() >= getUniversalEnergyCapacity() ? 4
                    : getUniversalEnergyStored() >= getUniversalEnergyCapacity() / 2 ? 2 : 1));
        }
    }

    @Override
    public void doExplosion(long explosionEu) {
        if (canAccessData()) {
            // Keep a reference: a chained explosion can invalidate this TE mid-call and null out mMetaTileEntity.
            final MTE mte = this.mte;
            // This is only for Electric Machines
            if (GregTechAPI.sMachineWireFire && mte.isElectric()) {
                try {
                    mReleaseEnergy = true;
                    IEnergyConnected.Util.emitEnergyToNetwork(V[5], Math.max(1, getStoredEU() / V[5]), this);
                } catch (Exception ignored) {}
            }
            mReleaseEnergy = false;
            // Normal Explosion Code
            mte.onExplosion();
            for (int i = 0; i < this.getSizeInventory(); i++) {
                final ItemStack tItem = this.getStackInSlot(i);
                if ((tItem != null) && (tItem.stackSize > 0) && (this.isValidSlot(i))) {
                    dropItems(tItem);
                    this.setInventorySlotContents(i, null);
                }
            }
            mte.doExplosion(explosionEu);
        }
    }

    public void dropItems(ItemStack tItem) {
        if (tItem == null) return;
        final EntityItem tItemEntity = new EntityItem(
            this.worldObj,
            this.xCoord + XSTR_INSTANCE.nextFloat() * 0.8F + 0.1F,
            this.yCoord + XSTR_INSTANCE.nextFloat() * 0.8F + 0.1F,
            this.zCoord + XSTR_INSTANCE.nextFloat() * 0.8F + 0.1F,
            new ItemStack(tItem.getItem(), tItem.stackSize, tItem.getItemDamage()));
        if (tItem.hasTagCompound()) {
            tItemEntity.getEntityItem()
                .setTagCompound(
                    (NBTTagCompound) tItem.getTagCompound()
                        .copy());
        }
        tItemEntity.motionX = (XSTR_INSTANCE.nextGaussian() * 0.0500000007450581D);
        tItemEntity.motionY = (XSTR_INSTANCE.nextGaussian() * 0.0500000007450581D + 0.2000000029802322D);
        tItemEntity.motionZ = (XSTR_INSTANCE.nextGaussian() * 0.0500000007450581D);
        tItemEntity.hurtResistantTime = 999999;
        tItemEntity.lifespan = 60000;
        ((EntityItemAccessor) tItemEntity).gt5$setHealth(99999999);
        this.worldObj.spawnEntityInWorld(tItemEntity);
        tItem.stackSize = 0;
    }

    @Override
    public ArrayList<ItemStack> getDrops() {
        final ItemStack rStack = new ItemStack(GregTechAPI.sBlockMachines, 1, meta);
        final NBTTagCompound tNBT = new NBTTagCompound();
        if (mLockUpgrade) tNBT.setBoolean("mLockUpgrade", true);
        if (getColorRaw() > 0) tNBT.setByte("mColor", getColorRaw());
        if (mOtherUpgrades > 0) tNBT.setByte("mOtherUpgrades", mOtherUpgrades);

        writeCoverNBT(tNBT, true);

        if (hasValidMetaTileEntity()) mte.setItemNBT(tNBT);
        if (!tNBT.hasNoTags()) rStack.setTagCompound(tNBT);

        onBaseTEDestroyed();
        return new ArrayList<>(Collections.singletonList(rStack));
    }

    public int getUpgradeCount() {
        return (mLockUpgrade ? 1 : 0) + mOtherUpgrades;
    }

    @Override
    public boolean onRightClick(final EntityPlayer player, final ForgeDirection side, final float x, final float y,
        final float z) {
        final ForgeDirection wrenchingSide = GTUtility.determineWrenchingSide(side, x, y, z);
        final ForgeDirection effectiveSide = !hasCoverAtSide(side) ? wrenchingSide : side;
        Cover effectiveSideCover = getCoverAtSide(effectiveSide);
        if (isClientSide()) {
            // Place/configure Cover, sneak can also be: screwdriver, wrench, side cutter, soldering iron
            if (player.isSneaking()) {
                return (effectiveSideCover.hasCoverGUI());
            }

            if (!getCoverAtSide(side).isGUIClickable()) return false;
        }

        if (isServerSide()) {
            if (!privateAccess() || player.getDisplayName()
                .equalsIgnoreCase(getOwnerName())) {
                final ItemStack tCurrentItem = player.inventory.getCurrentItem();
                if (tCurrentItem != null) {
                    final boolean isHardHammer = GTUtility.isStackInList(tCurrentItem, GregTechAPI.sHardHammerList);
                    final boolean isJackhammer = GTUtility.isStackInList(tCurrentItem, GregTechAPI.sJackhammerList);
                    if (getColorization() >= 0
                        && GTUtility.areStacksEqual(new ItemStack(Items.water_bucket, 1), tCurrentItem)) {
                        tCurrentItem.func_150996_a(Items.bucket);
                        setColorization((byte) (getColorization() >= 16 ? -2 : -1));
                        return true;
                    }
                    if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sWrenchList)) {
                        if (mte.onWrenchRightClick(side, wrenchingSide, player, x, y, z, tCurrentItem)) {
                            GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player);
                            sendSoundToPlayers(SoundResource.GTCEU_OP_WRENCH, 1.0F, 1);
                            cableUpdateDelay = 10;
                        }

                        if (tCurrentItem.stackSize == 0) ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                        return true;
                    }

                    if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sScrewdriverList)) {
                        if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 200, player)) {
                            getCoverAtSide(side).onCoverScrewdriverClick(player, x, y, z);
                            mte.onScrewdriverRightClick(side, player, x, y, z, tCurrentItem);
                            sendSoundToPlayers(SoundResource.GTCEU_OP_SCREWDRIVER, 1.0F, 1);
                            if (tCurrentItem.stackSize == 0)
                                ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                        }
                        return true;
                    }

                    if (!hasCoverAtSide(effectiveSide) && (isHardHammer || isJackhammer)) {
                        if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                            if (player.isSneaking()) {
                                mInputDisabled = !mInputDisabled;
                                if (mInputDisabled) mOutputDisabled = !mOutputDisabled;
                                GTUtility.sendChatComp(
                                    player,
                                    new ChatComponentTranslation(
                                        mInputDisabled ? "GT5U.chat.machine.auto_input.disable"
                                            : "GT5U.chat.machine.auto_input.enable").appendText("  ")
                                                .appendSibling(
                                                    new ChatComponentTranslation(
                                                        mOutputDisabled ? "GT5U.chat.machine.auto_output.disable"
                                                            : "GT5U.chat.machine.auto_output.enable")));
                                sendSoundToPlayers(SoundResource.GTCEU_LOOP_FORGE_HAMMER, 1.0F, 1);
                            } else {
                                mMuffler = !mMuffler;
                                scheduleTexturePacket();
                                GTUtility.sendChatTrans(
                                    player,
                                    mMuffler ? "GT5U.machines.muffled.on" : "GT5U.machines.muffled.off");
                            }
                            if (tCurrentItem.stackSize == 0)
                                ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                        }
                        return true;
                    }

                    if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sSoftMalletList)) {
                        if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                            if (!mWorks) {
                                if (isServerSide()) {}
                                enableWorking();
                            } else if (mWorks) {
                                disableWorking();
                            }
                            if (getMTE() != null && getMTE().hasAlternativeModeText()) {
                                GTUtility.sendChatTrans(player, getMTE().getAlternativeModeText());
                            } else {
                                GTUtility.sendChatTrans(
                                    player,
                                    isAllowedToWork() ? "GT5U.chat.machine.processing.enable"
                                        : "GT5U.chat.machine.processing.disable");
                            }
                            sendSoundToPlayers(SoundResource.GTCEU_OP_SOFT_HAMMER, 1.0F, 1);
                            if (tCurrentItem.stackSize == 0) {
                                ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                            }
                        }
                        return true;
                    }

                    if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sSolderingToolList)) {
                        if (mte.onSolderingToolRightClick(side, wrenchingSide, player, x, y, z, tCurrentItem)) {
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
                        if (tCurrentItem.stackSize == 0) ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                        cableUpdateDelay = 10;
                        return true;
                    }

                    if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sWireCutterList)) {
                        if (mte.onWireCutterRightClick(side, wrenchingSide, player, x, y, z, tCurrentItem)) {
                            // logic handled internally
                            sendSoundToPlayers(SoundResource.GTCEU_OP_WIRECUTTER, 1.0F, 1);
                            if (tCurrentItem.stackSize == 0)
                                ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                        }
                        cableUpdateDelay = 10;
                        return true;
                    }

                    if (!hasCoverAtSide(effectiveSide)) {
                        if (player.isSneaking() && CoverRegistry.isCover(tCurrentItem)) {
                            if (CoverRegistry.getCoverPlacer(tCurrentItem)
                                .isCoverPlaceable(effectiveSide, tCurrentItem, this)
                                && mte.allowCoverOnSide(effectiveSide, tCurrentItem)) {

                                CoverRegistry.getCoverPlacer(tCurrentItem)
                                    .placeCover(player, tCurrentItem, this, effectiveSide);

                                if (!player.capabilities.isCreativeMode) tCurrentItem.stackSize--;
                                sendSoundToPlayers(SoundResource.GTCEU_OP_WRENCH, 1.0F, 1);
                                issueTileUpdate();
                            }
                            return true;
                        }
                    } else {
                        if (player.isSneaking() && GTUtility.isStackInList(tCurrentItem, GregTechAPI.sCrowbarList)) {
                            if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                                sendSoundToPlayers(SoundResource.RANDOM_BREAK, 1.0F, -1);
                                dropCover(effectiveSide, side);
                                if (tCurrentItem.stackSize == 0)
                                    ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                            }
                            return true;
                        } else if (isHardHammer || isJackhammer) {
                            // Configuration of delicate electronics calls for a tool with precision and subtlety.
                            if (GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, player)) {
                                if (effectiveSideCover.isValid()) {
                                    if (effectiveSideCover.allowsTickRateAddition()) {
                                        effectiveSideCover.onCoverJackhammer(player);
                                        sendSoundToPlayers(SoundResource.IC2_TOOLS_DRILL_DRILL_SOFT, 1.0F, 1);

                                    } else {
                                        GTUtility.sendChatTrans(player, "gt.cover.info.chat.tick_rate_not_allowed");
                                    }
                                    if (tCurrentItem.stackSize == 0)
                                        ForgeEventFactory.onPlayerDestroyItem(player, tCurrentItem);
                                    return true;
                                }
                            }
                        }
                    }
                    // End item != null
                } else if (player.isSneaking()) { // Sneak click, no tool -> open cover config if possible.
                    return effectiveSideCover.isValid() && effectiveSideCover.onCoverShiftRightClick(player);
                }

                if (getCoverAtSide(side).onCoverRightClick(player, x, y, z)) return true;

                if (!getCoverAtSide(side).isGUIClickable()) return false;
            }
        }

        try {
            if (!player.isSneaking() && hasValidMetaTileEntity()) return mte.onRightClick(this, player, side, x, y, z);
        } catch (Exception e) {
            GT_FML_LOGGER.error(
                "Encountered Exception while rightclicking TileEntity, the Game should've crashed now, but I prevented that. Please report immediately to GregTech Intergalactical!!!");
            GT_FML_LOGGER.error(e);
            GT_FML_LOGGER.error(e);
        }

        return false;
    }

    @Override
    public void onLeftClick(EntityPlayer player) {
        try {
            if (player != null && hasValidMetaTileEntity()) mte.onLeftClick(this, player);
        } catch (Exception e) {
            GT_FML_LOGGER.error(
                "Encountered Exception while leftclicking TileEntity, the Game should've crashed now, but I prevented that. Please report immediately to GregTech Intergalactical!!!");
            GT_FML_LOGGER.error(e);
        }
    }

    /**
     * Can put aStack into Slot
     */
    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return canAccessData() && mte.isItemValidForSlot(aIndex, aStack);
    }

    /**
     * returns all valid Inventory Slots, no matter which Side (Unless it's covered). The Side Stuff is done in the
     * following two Functions.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int ordinalSide) {
        final Cover cover = getCoverAtSide(ForgeDirection.getOrientation(ordinalSide));
        if (canAccessData() && (cover.letsItemsOut(-1) || cover.letsItemsIn(-1)))
            return mte.getAccessibleSlotsFromSide(ordinalSide);
        return GTValues.emptyIntArray;
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int slotIndex, ItemStack stack, int ordinalSide) {
        return canAccessData() && (mRunningThroughTick || !mInputDisabled)
            && getCoverAtSide(ForgeDirection.getOrientation(ordinalSide)).letsItemsIn(slotIndex)
            && mte.canInsertItem(slotIndex, stack, ordinalSide);
    }

    /**
     * Can pull stack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int slotIndex, ItemStack stack, int ordinalSide) {
        final ForgeDirection side = ForgeDirection.getOrientation(ordinalSide);
        return canAccessData() && (mRunningThroughTick || !mOutputDisabled)
            && getCoverAtSide(side).letsItemsOut(slotIndex)
            && mte.canExtractItem(slotIndex, stack, ordinalSide);
    }

    @Override
    public boolean isUpgradable() {
        return canAccessData() && getUpgradeCount() < 8;
    }

    @Override
    public byte getGeneralRS(ForgeDirection side) {
        if (mte == null) return 0;
        return mte.allowGeneralRedstoneOutput() ? sidedRedstone[side.ordinal()] : 0;
    }

    @Override
    public boolean isMuffled() {
        return mMuffler;
    }

    @Override
    public boolean isMufflerUpgradable() {
        return isUpgradable() && !isMuffled();
    }

    @Override
    public boolean addMufflerUpgrade() {
        if (isMufflerUpgradable()) {
            mMuffler = true;
            scheduleTexturePacket();
            return true;
        }
        return false;
    }

    @Override
    public void setMuffler(boolean value) {
        mMuffler = value;
        scheduleTexturePacket();
    }

    @Override
    public void markInventoryBeenModified() {
        mInventoryChanged = true;
    }

    @Override
    public final IMTE getMTE() {
        return hasValidMetaTileEntity() ? mte : null;
    }

    @Override
    public final void setMTE(IMTE mte) {
        if (mte instanceof MTE || mte == null) this.mte = (MTE) mte;
        else {
            GT_FML_LOGGER.error(
                "Unknown meta tile entity set! Class {}, inventory name {}.",
                mte.getClass(),
                mte.getInventoryName());
        }
        refreshMetaTileEntityValidity();
    }

    public byte getLightValue() {
        return mLightValue;
    }

    @Override
    public void setLightValue(byte aLightValue) {
        mLightValue = (byte) (aLightValue & 15);
    }

    @Override
    public long getAverageElectricInput() {
        if (mAverageEUInput == null) return 0;
        long rEU = 0;
        for (int i = 0; i < mAverageEUInput.length; ++i) if (i != mAverageEUInputIndex) rEU += mAverageEUInput[i];
        return rEU / (mAverageEUInput.length - 1);
    }

    @Override
    public long getAverageElectricOutput() {
        if (mAverageEUOutput == null) return 0;
        long rEU = 0;
        for (int i = 0; i < mAverageEUOutput.length; ++i) if (i != mAverageEUOutputIndex) rEU += mAverageEUOutput[i];
        return rEU / (mAverageEUOutput.length - 1);
    }

    @Override
    public String getOwnerName() {
        if (GTUtility.isStringInvalid(mOwnerName)) return "Player";
        return mOwnerName;
    }

    @Override
    public void setOwnerName(String name) {
        if (GTUtility.isStringInvalid(name)) {
            mOwnerName = "Player";
            return;
        }
        mOwnerName = name;
    }

    @Override
    public UUID getOwnerUuid() {
        return mOwnerUuid;
    }

    @Override
    public void setOwnerUuid(UUID uuid) {
        mOwnerUuid = uuid;
    }

    @Override
    public byte getComparatorValue(ForgeDirection side) {
        return canAccessData() ? mte.getComparatorValue(side) : 0;
    }

    @Override
    public ItemStack decrStackSize(int aIndex, int aAmount) {
        if (canAccessData()) {
            mInventoryChanged = true;
            return mte.decrStackSize(aIndex, aAmount);
        }
        return null;
    }

    @Override
    public long injectEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        if (!canAccessData() || !mte.isElectric()
            || !inputEnergyFrom(side)
            || aAmperage <= 0
            || aVoltage <= 0
            || getStoredEU() >= getEUCapacity()
            || mte.maxAmperesIn() <= mAcceptedAmperes) return 0;
        if (aVoltage > getInputVoltage()) {
            GTLog.writeExplosionLog(
                this.mte,
                "Energy Explosion, injected " + aVoltage + "EU/t in a " + getInputVoltage() + "EU/t Machine!");
            doExplosion(aVoltage);
            return 0;
        }
        if (increaseStoredEnergyUnits(
            aVoltage * (aAmperage = Math.min(
                aAmperage,
                Math.min(mte.maxAmperesIn() - mAcceptedAmperes, 1 + ((getEUCapacity() - getStoredEU()) / aVoltage)))),
            true)) {
            ensureAverageEUArrays();
            mAverageEUInput[mAverageEUInputIndex] += aVoltage * aAmperage;
            mAcceptedAmperes += aAmperage;
            return aAmperage;
        }
        return 0;
    }

    /** @return whether {@link #injectEnergyUnits} could still accept energy this tick. */
    public boolean canAcceptEnergyThisTick() {
        if (!canAccessData() || mte.maxAmperesIn() <= mAcceptedAmperes) return false;
        return mte.getEUVar() < mte.maxEUStore();
    }

    @Override
    public boolean drainEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        if (!canAccessData() || !mte.isElectric()
            || !outputsEnergyTo(side)
            || getStoredEU() - (aVoltage * aAmperage) < mte.getMinimumStoredEU()) return false;
        if (decreaseStoredEU(aVoltage * aAmperage, false)) {
            ensureAverageEUArrays();
            mAverageEUOutput[mAverageEUOutputIndex] += aVoltage * aAmperage;
            return true;
        }
        return false;
    }

    @Override
    public int fill(ForgeDirection side, FluidStack aFluid, boolean doFill) {
        if (tickTimer > 5 && canAccessData()
            && (mRunningThroughTick || !mInputDisabled)
            && (side == ForgeDirection.UNKNOWN || (mte.isLiquidInput(side)
                && getCoverAtSide(side).letsFluidIn(aFluid == null ? null : aFluid.getFluid()))))
            return mte.fill(side, aFluid, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection side, int maxDrain, boolean doDrain) {
        if (tickTimer > 5 && canAccessData()
            && (mRunningThroughTick || !mOutputDisabled)
            && (side == ForgeDirection.UNKNOWN || (mte.isLiquidOutput(side) && getCoverAtSide(side).letsFluidOut(
                mte.getFluid() == null ? null
                    : mte.getFluid()
                        .getFluid()))))
            return mte.drain(side, maxDrain, doDrain);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection side, FluidStack aFluid, boolean doDrain) {
        if (tickTimer > 5 && canAccessData()
            && (mRunningThroughTick || !mOutputDisabled)
            && (side == ForgeDirection.UNKNOWN || (mte.isLiquidOutput(side)
                && getCoverAtSide(side).letsFluidOut(aFluid == null ? null : aFluid.getFluid()))))
            return mte.drain(side, aFluid, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection side, Fluid aFluid) {
        if (tickTimer > 5 && canAccessData()
            && (mRunningThroughTick || !mInputDisabled)
            && (side == ForgeDirection.UNKNOWN
                || (mte.isLiquidInput(side) && getCoverAtSide(side).letsFluidIn(aFluid))))
            return mte.canFill(side, aFluid);
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection side, Fluid aFluid) {
        if (tickTimer > 5 && canAccessData()
            && (mRunningThroughTick || !mOutputDisabled)
            && (side == ForgeDirection.UNKNOWN
                || (mte.isLiquidOutput(side) && getCoverAtSide(side).letsFluidOut(aFluid))))
            return mte.canDrain(side, aFluid);
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection side) {
        if (canAccessData()) return mte.getTankInfo(side);
        return GTValues.emptyFluidTankInfo;
    }

    public double getOutputEnergyUnitsPerTick() {
        return oldOutput;
    }

    public boolean isTeleporterCompatible(ForgeDirection side) {
        return canAccessData() && mte.isTeleporterCompatible();
    }

    public double demandedEnergyUnits() {
        if (mReleaseEnergy || !canAccessData() || !mte.isEnetInput()) return 0;
        return getEUCapacity() - getStoredEU();
    }

    public double injectEnergyUnits(ForgeDirection aDirection, double aAmount) {
        return injectEnergyUnits(aDirection, (int) aAmount, 1) > 0 ? 0 : aAmount;
    }

    public boolean acceptsEnergyFrom(TileEntity aEmitter, ForgeDirection aDirection) {
        return inputEnergyFrom(aDirection);
    }

    public boolean emitsEnergyTo(TileEntity aReceiver, ForgeDirection aDirection) {
        return outputsEnergyTo(aDirection);
    }

    public double getOfferedEnergy() {
        return (canAccessData() && getStoredEU() - mte.getMinimumStoredEU() >= oldOutput) ? Math.max(0, oldOutput) : 0;
    }

    public void drawEnergy(double amount) {
        ensureAverageEUArrays();
        mAverageEUOutput[mAverageEUOutputIndex] += amount;
        decreaseStoredEU((int) amount, true);
    }

    public int injectEnergy(ForgeDirection aForgeDirection, int aAmount) {
        return injectEnergyUnits(aForgeDirection, aAmount, 1) > 0 ? 0 : aAmount;
    }

    public int addEnergy(int aEnergy) {
        if (!canAccessData()) return 0;
        if (aEnergy > 0) increaseStoredEnergyUnits(aEnergy, true);
        else decreaseStoredEU(-aEnergy, true);
        return (int) Math.min(Integer.MAX_VALUE, mte.getEUVar());
    }

    public boolean isAddedToEnergyNet() {
        return false;
    }

    @Override
    public boolean isSteampowered() {
        return getSteamCapacity() > 0;
    }

    public int demandsEnergy() {
        if (mReleaseEnergy || !canAccessData() || !mte.isEnetInput()) return 0;
        return getCapacity() - getStored();
    }

    public int getCapacity() {
        return (int) Math.min(Integer.MAX_VALUE, getEUCapacity());
    }

    public int getStored() {
        return (int) Math.min(Integer.MAX_VALUE, Math.min(getStoredEU(), getCapacity()));
    }

    public void setStored(int aEU) {
        if (canAccessData()) setStoredEU(aEU);
    }

    public int getMaxSafeInput() {
        return (int) Math.min(Integer.MAX_VALUE, getInputVoltage());
    }

    public int getMaxEnergyOutput() {
        if (mReleaseEnergy) return Integer.MAX_VALUE;
        return getOutput();
    }

    public long getStoredEUuncapped() {
        return mte.getEUVar();
    }

    public int getOutput() {
        return (int) Math.min(Integer.MAX_VALUE, oldOutput);
    }

    public int injectEnergy(Direction aDirection, int aAmount) {
        return injectEnergyUnits(aDirection.toForgeDirection(), aAmount, 1) > 0 ? 0 : aAmount;
    }

    public boolean isTeleporterCompatible(Direction ignoredDirection) {
        return canAccessData() && mte.isTeleporterCompatible();
    }

    public boolean acceptsEnergyFrom(TileEntity ignoredTileEntity, Direction aDirection) {
        return inputEnergyFrom(aDirection.toForgeDirection());
    }

    public boolean emitsEnergyTo(TileEntity ignoredTileEntity, Direction aDirection) {
        return outputsEnergyTo(aDirection.toForgeDirection());
    }

    @Override
    public boolean addStackToSlot(int slotIndex, ItemStack stack) {
        if (GTUtility.isStackInvalid(stack)) return true;
        if (slotIndex < 0 || slotIndex >= getSizeInventory()) return false;
        final ItemStack toStack = getStackInSlot(slotIndex);
        if (GTUtility.isStackInvalid(toStack)) {
            setInventorySlotContents(slotIndex, stack);
            return true;
        }
        final ItemStack fromStack = stack;
        if (GTUtility.areStacksEqual(toStack, fromStack) && toStack.stackSize + fromStack.stackSize
            <= Math.min(fromStack.getMaxStackSize(), getInventoryStackLimit())) {
            toStack.stackSize += fromStack.stackSize;
            markDirty();
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
        byte colorRaw = (byte) (aColor + 1);
        setColorRaw(colorRaw);
        return colorRaw;
    }

    @Override
    public boolean isUniversalEnergyStored(long aEnergyAmount) {
        if (getUniversalEnergyStored() >= aEnergyAmount) return true;
        mHasEnoughEnergy = false;
        return false;
    }

    @Override
    public int getLightOpacity() {
        return mte == null ? getLightValue() > 0 ? 0 : 255 : mte.getLightOpacity();
    }

    @Override
    public IGridNode getGridNode(ForgeDirection forgeDirection) {
        final AENetworkProxy gp = getProxy();
        return gp != null ? gp.getNode() : null;
    }

    @Override
    public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
        return mte == null ? AECableType.NONE : mte.getCableConnectionType(forgeDirection);
    }

    @Override
    public void securityBreak() {}

    @Override
    public IGridNode getActionableNode() {
        final AENetworkProxy gp = getProxy();
        return gp != null ? gp.getNode() : null;
    }

    @Override
    public AENetworkProxy getProxy() {
        return mte == null ? null : mte.getProxy();
    }

    @Override
    public DimensionalCoord getLocation() {
        return new DimensionalCoord(this);
    }

    @Override
    public void gridChanged() {
        if (mte != null) mte.gridChanged();
    }

    void onChunkUnloadAE() {
        final AENetworkProxy gp = getProxy();
        if (gp != null) gp.onChunkUnload();
    }

    void invalidateAE() {
        final AENetworkProxy gp = getProxy();
        if (gp != null) gp.invalidate();
    }

    @Override
    public boolean wasShutdown() {
        return mWasShutdown;
    }

    @Override
    public void setShutdownStatus(boolean newStatus) {
        mWasShutdown = newStatus;
    }

    @Override
    public void setShutDownReason(@NotNull ShutDownReason reason) {
        lastShutDownReason = reason;
    }

    @Override
    public @NotNull ShutDownReason getLastShutDownReason() {
        return lastShutDownReason;
    }

    @Override
    public IAlignment getAlignment() {
        return getMTE() instanceof IAlignmentProvider ? ((IAlignmentProvider) getMTE()).getAlignment()
            : getMTE() instanceof IAlignment ? (IAlignment) getMTE() : null;
    }

    @Nullable
    @Override
    public IConstructable getConstructable() {
        return getMTE() instanceof IConstructable ? (IConstructable) getMTE() : null;
    }

    @Nullable
    @Override
    public List<ItemStack> getItemsForHoloGlasses() {
        if (canAccessData()) {
            return mte.getItemsForHoloGlasses();
        }
        return null;
    }

    @Override
    public String getCustomName() {
        return getMTE() instanceof ICustomNameObject customNameObject ? customNameObject.getCustomName() : null;
    }

    @Override
    public boolean hasCustomName() {
        return getMTE() instanceof ICustomNameObject customNameObject && customNameObject.hasCustomName();
    }

    @Override
    public void setCustomName(String name) {
        if (getMTE() instanceof ICustomNameObject customNameObject) customNameObject.setCustomName(name);
    }

    @Override
    protected int getCoverTabHeightOffset() {
        return 0;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return getMTE() instanceof IMTERenderer mteRenderer ? mteRenderer.getRenderBoundingBox(xCoord, yCoord, zCoord)
            : super.getRenderBoundingBox();
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return getMTE() instanceof IMTERenderer mteRenderer ? mteRenderer.getMaxRenderDistanceSquared()
            : super.getMaxRenderDistanceSquared();
    }
}
