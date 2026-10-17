package gregtech.api.metatileentity.implementations;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.tile.powersink.IC2;
import gregtech.api.enums.MTEArrays;
import gregtech.api.graphs.Node;
import gregtech.api.graphs.NodeList;
import gregtech.api.graphs.PowerNode;
import gregtech.api.graphs.PowerNodes;
import gregtech.api.graphs.consumers.ConsumerNode;
import gregtech.api.graphs.paths.PowerNodePath;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.metatileentity.IMTECable;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.metatileentity.BasePipeGTE;
import gregtech.api.metatileentity.PipeMTE;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTSplit;
import gregtech.api.util.GTUtility;
import gregtech.api.util.tooltip.TooltipHelper;
import gregtech.common.covers.Base.Cover;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

@IMTE.SkipGenerateDescription
public class MTECable extends PipeMTE implements IMTECable {

    public final float mThickNess;
    public final long mCableLossPerMeter, mAmperage, mVoltage;
    public final boolean mInsulated, mCanShock;

    public int mTransferredAmperage = 0;

    public MTECable(MTEArrays mteArrays, int meta, String name, float size, long lossPerMeter, long amperage,
        long voltage, boolean insulated, boolean canShock) {
        super(mteArrays, meta, name, 0);
        mThickNess = size;
        mAmperage = amperage;
        mVoltage = voltage;
        mInsulated = insulated;
        mCanShock = canShock;
        mCableLossPerMeter = lossPerMeter;
    }

    public MTECable(String name, float size, long lossPerMeter, long amperage, long voltage, boolean insulated,
        boolean canShock) {
        super(name, 0);
        mThickNess = size;
        mAmperage = amperage;
        mVoltage = voltage;
        mInsulated = insulated;
        mCanShock = canShock;
        mCableLossPerMeter = lossPerMeter;
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTECable(name, mThickNess, mCableLossPerMeter, mAmperage, mVoltage, mInsulated, mCanShock)
            .setAppearance(appearance);
    }

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection sideDirection, int facingDirection, int colorIndex,
        boolean active, boolean redstoneLevel) {
        // if (!mInsulated) {
        // return new ITexture[] { TextureFactory.of(
        // mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()],
        // Dyes.getModulation(colorIndex, mMaterial.mRGBa)) };
        // }
        // if (active) {
        // float size = getSize();
        // if (size < 0.124F) {
        // return new ITexture[] { TextureFactory.of(
        // Textures.BlockIcons.INSULATION_FULL,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // }
        // if (size < 0.374F) // 0.375 x1
        // return new ITexture[] { TextureFactory
        // .of(mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()], mMaterial.mRGBa),
        // TextureFactory.of(
        // Textures.BlockIcons.INSULATION_TINY,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // if (size < 0.499F) // 0.500 x2
        // return new ITexture[] { TextureFactory
        // .of(mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()], mMaterial.mRGBa),
        // TextureFactory.of(
        // Textures.BlockIcons.INSULATION_SMALL,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // if (size < 0.624F) // 0.625 x4
        // return new ITexture[] { TextureFactory
        // .of(mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()], mMaterial.mRGBa),
        // TextureFactory.of(
        // Textures.BlockIcons.INSULATION_MEDIUM,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // if (size < 0.749F) // 0.750 x8
        // return new ITexture[] { TextureFactory
        // .of(mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()], mMaterial.mRGBa),
        // TextureFactory.of(
        // Textures.BlockIcons.INSULATION_MEDIUM_PLUS,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // if (size < 0.874F) // 0.825 x12
        // return new ITexture[] { TextureFactory
        // .of(mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()], mMaterial.mRGBa),
        // TextureFactory.of(
        // Textures.BlockIcons.INSULATION_LARGE,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // return new ITexture[] {
        // TextureFactory
        // .of(mMaterial.mIconSet.mTextures[MaterialIconRegistry.IconType.WIRE.ordinal()], mMaterial.mRGBa),
        // TextureFactory.of(
        // Textures.BlockIcons.INSULATION_HUGE,
        // Dyes.getModulation(colorIndex, Dyes.CABLE_INSULATION.getRGBA())) };
        // }
        return appearance.getTextures(active, colorIndex);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity aEntity) {
        if (!mCanShock) {
            return;
        }

        final BasePipeGTE baseEntity = (BasePipeGTE) getGTE();

        if (!(aEntity instanceof EntityLivingBase livingEntity)) return;
        if (!(baseEntity.getNodePath() instanceof PowerNodePath powerPath)) return;

        if (isCoverOnSide(baseEntity, livingEntity)) return;

        final long amperage = powerPath.getAmperage();
        final long voltage = powerPath.getVoltage();

        if (amperage == 0L) return;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return false;
    }

    @Override
    public boolean isValidSlot(int index) {
        return true;
    }

    @Override
    public final boolean renderInside(ForgeDirection side) {
        return false;
    }

    @Override
    public int getProgressTime() {
        return mTransferredAmperage * 64;
    }

    @Override
    public int maxProgressTime() {
        return (int) mAmperage * 64;
    }

    @Override
    public long injectEnergyUnits(ForgeDirection side, long voltage, long amperage) {
        if (!isConnectedAtSide(side) && side != ForgeDirection.UNKNOWN) return 0;
        if (!getGTE().getCoverAtSide(side)
            .letsEnergyIn()) return 0;
        return transferElectricity(side, voltage, amperage, null);
    }

    @Override
    public long transferElectricity(ForgeDirection side, long voltage, long amperage,
        HashSet<TileEntity> alreadyPassedSet) {
        if (amperage <= 0 || !getGTE().isServerSide() || (!isConnectedAtSide(side) && side != ForgeDirection.UNKNOWN))
            return 0;
        final BasePipeGTE tBase = (BasePipeGTE) getGTE();
        if (!(tBase.getNode() instanceof PowerNode tNode)) return 0;
        int tPlace = 0;
        final Node[] tToPower = new Node[tNode.mConsumers.size()];
        if (tNode.mHadVoltage) {
            for (ConsumerNode consumer : tNode.mConsumers) {
                if (consumer.needsEnergy()) tToPower[tPlace++] = consumer;
            }
        } else {
            tNode.mHadVoltage = true;
            for (ConsumerNode consumer : tNode.mConsumers) {
                tToPower[tPlace++] = consumer;
            }
        }
        return PowerNodes.powerNode(tNode, null, new NodeList(tToPower), (int) voltage, (int) amperage);
    }

    @Override
    public boolean needsClientTick() {
        return false;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (tick % 20 == 0 && gte.isServerSide() && (checkConnections)) {
            checkConnections();
        }
    }

    /**
     * Consumes enough durability to connect one cable.
     *
     * @param aPlayer The player using the tool.
     * @return True if the tool has enough durability.
     */
    private boolean consumeDurabilityForConnection(EntityPlayer aPlayer) {
        return GTModHandler.damageOrDechargeItem(aPlayer.inventory.getCurrentItem(), 1, 500, aPlayer);
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {
        if (consumeDurabilityForConnection(player)) {
            if (isConnectedAtSide(wrenchingSide)) {
                disconnect(wrenchingSide);
                GTUtility.sendChatTrans(player, "GT5U.chat.disconnected");
            } else if (connect(wrenchingSide) > 0) {
                GTUtility.sendChatTrans(player, "GT5U.chat.connected");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {
        if (GTModHandler.damageOrDechargeItem(player.inventory.getCurrentItem(), 1, 500, player)) {
            if (isConnectedAtSide(wrenchingSide)) {
                disconnect(wrenchingSide);
                GTUtility.sendChatTrans(player, "GT5U.chat.disconnected");
            } else if (connect(wrenchingSide) > 0) {
                GTUtility.sendChatTrans(player, "GT5U.chat.connected");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean letsIn(Cover cover) {
        return cover.letsEnergyIn();
    }

    @Override
    public boolean letsOut(Cover cover) {
        return cover.letsEnergyOut();
    }

    @Override
    public boolean canConnect(ForgeDirection side, TileEntity tileEntity) {
        final IGTE baseMetaTile = getGTE();
        final ForgeDirection oppositeSide = side.getOpposite();

        // GT Machine handling
        if (tileEntity instanceof IEnergyConnected energyConnected
            && (energyConnected.inputEnergyFrom(oppositeSide, false)
                || energyConnected.outputsEnergyTo(oppositeSide, false)))
            return true;

        // AE2-p2p Compat
        return tileEntity instanceof IC2 ic2sink && ic2sink.acceptsEnergyFrom((TileEntity) baseMetaTile, oppositeSide);
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public String[] getDescription() {
        return GTSplit.splitLocalizedFormatted(
            "gt.blockmachines.cable.desc",
            TooltipHelper.voltageText(mVoltage),
            TooltipHelper.ampText(mAmperage),
            TooltipHelper.cableLossText(mCableLossPerMeter));
    }

    @Override
    public float getCollisionThickness() {
        return mThickNess;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt.setByte("mConnections", connections);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        connections = nbt.getByte("mConnections");
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        final BasePipeGTE base = (BasePipeGTE) getGTE();
        final PowerNodePath path = (PowerNodePath) base.getNodePath();

        if (path == null) return new String[] { "GT5U.infodata.cable.failed" };

        path.reloadLocks();

        final long currAmp = path.getAmperage();
        final long currVoltage = path.getVoltage();

        final double avgAmp = path.getAvgAmperage();
        final double avgVoltage = path.getAvgVoltage();

        final long maxVoltageOut = (mVoltage - mCableLossPerMeter) * mAmperage;

        return new String[] {
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.cable.amperage",
                EnumChatFormatting.GREEN + formatNumber(currAmp) + EnumChatFormatting.RESET,
                EnumChatFormatting.YELLOW + formatNumber(mAmperage) + EnumChatFormatting.RESET),
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.cable.voltage_out",
                EnumChatFormatting.GREEN + formatNumber(currVoltage) + EnumChatFormatting.RESET,
                EnumChatFormatting.YELLOW + formatNumber(maxVoltageOut) + EnumChatFormatting.RESET),
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.cable.avg_amperage",
                EnumChatFormatting.YELLOW + formatNumber(avgAmp) + EnumChatFormatting.RESET),
            IGregTechDeviceInformation.encode(
                "GT5U.infodata.cable.avg_output",
                EnumChatFormatting.YELLOW + formatNumber(avgVoltage) + EnumChatFormatting.RESET) };
    }

    @Override
    public void reloadLocks() {
        final BasePipeGTE pipe = (BasePipeGTE) getGTE();
        if (pipe.getNode() != null) {
            for (final ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (isConnectedAtSide(side)) {
                    final Cover cover = pipe.getCoverAtSide(side);
                    if (!cover.isValid()) continue;
                    if (!letsIn(cover) || !letsOut(cover)) {
                        pipe.addToLock(pipe, side);
                    } else {
                        pipe.removeFromLock(pipe, side);
                    }
                }
            }
        } else {
            boolean dontAllow = false;
            for (final ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (isConnectedAtSide(side)) {
                    final Cover cover = pipe.getCoverAtSide(side);
                    if (!cover.isValid()) continue;

                    if (!letsIn(cover) || !letsOut(cover)) {
                        dontAllow = true;
                    }
                }
            }
            if (dontAllow) {
                pipe.addToLock(pipe, ForgeDirection.DOWN);
            } else {
                pipe.removeFromLock(pipe, ForgeDirection.DOWN);
            }
        }
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {

        Collections.addAll(
            currenttip,
            GTSplit.splitLocalizedFormatted(
                "gt.blockmachines.cable.desc",
                TooltipHelper.voltageText(mVoltage),
                TooltipHelper.ampText(mAmperage),
                TooltipHelper.cableLossText(mCableLossPerMeter)));
    }
}
