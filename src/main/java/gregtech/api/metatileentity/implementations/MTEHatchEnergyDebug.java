package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GTValues.V;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregtech.api.enums.GTAuthors;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTSplit;
import gregtech.common.gui.modularui.hatch.MTEHatchEnergyDebugGui;

@IMTE.SkipGenerateDescription
public class MTEHatchEnergyDebug extends MTEHatchEnergy {

    public MTEHatchEnergyDebug(int aID, String aName, String aNameRegional, int aTier, IMTE[] mteArray) {
        super(aID, aName, aNameRegional, aTier, mteArray);
    }

    public MTEHatchEnergyDebug(String aName, byte aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public long getInputTier() {
        return voltageTier;
    }

    @Override
    public byte getTierForStructure() {
        return (byte) voltageTier;
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_ENERGY_IN_DEBUG) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_ENERGY_IN_DEBUG) };
    }

    @Override
    public MetaTileEntity newMTE(IGTE gte) {
        return new MTEHatchEnergyDebug(name, mTier, new String[] { "" }, mTextures);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        nbt.setInteger("debugAmperage", amperage);
        nbt.setInteger("debugVTier", voltageTier);
        nbt.setInteger("refillInterval", refillInterval);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        amperage = nbt.getInteger("debugAmperage");
        voltageTier = nbt.getInteger("debugVTier");
        refillInterval = nbt.getInteger("refillInterval");
    }

    @Override
    public String[] getDescription() {
        return GTSplit.splitLocalizedWithSuffix(
            "gt.blockmachines.energy_hatch_debug.desc",
            GTAuthors.buildAuthorsWithFormat(GTAuthors.AuthorChrom));
    }

    @Override
    public boolean isEnetInput() {
        return false;
    }

    // 0 = ulv -> 14 MAX+
    private int voltageTier = 15;
    private int amperage = 2;

    public int getRefillInterval() {
        return refillInterval;
    }

    public void setRefillInterval(int refillInterval) {
        this.refillInterval = refillInterval;
    }

    public int getAmperage() {
        return amperage;
    }

    public void setAmperage(int amperage) {
        this.amperage = amperage;
    }

    public int getVoltageTier() {
        return voltageTier;
    }

    public void setVoltageTier(int voltageTier) {
        this.voltageTier = voltageTier;
    }

    // Refills every [refill interval] ticks, default 600 (30 seconds)
    private int refillInterval = 600;

    @Override
    public long getMinimumStoredEU() {
        return amperage * V[voltageTier];
    }

    @Override
    public long maxEUStore() {
        return Long.MAX_VALUE;
    }

    @Override
    public long maxEUInput() {
        return V[voltageTier];
    }

    @Override
    public long maxAmperesIn() {
        return amperage;
    }

    @Override
    public void onFirstTick(IGTE gte) {
        if (gte.isServerSide()) fetchEnergy();
    }

    @Override
    public void doExplosion(long explosionPower) {
        return;
    }

    @Override
    public void onPreTick(IGTE gte, long tick) {
        super.onPreTick(gte, tick);
        if (gte.isServerSide()) {
            // refill entirely every [refillInterval] ticks, default is 600/30 seconds
            // minimum value of 1, to avoid div by 0
            if (tick % ((long) Math.max(1, refillInterval)) == 0L) {
                fetchEnergy();
            }
        }
    }

    private void fetchEnergy() {
        setEUVar(Long.MAX_VALUE);
    }

    @Override
    public boolean onRightclick(IGTE aBaseMetaTileEntity, EntityPlayer aPlayer) {
        openGui(aPlayer);
        return true;
    }

    @Override
    protected boolean useMui2() {
        return true;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        return new MTEHatchEnergyDebugGui(this).build(data, syncManager, uiSettings);
    }

}
