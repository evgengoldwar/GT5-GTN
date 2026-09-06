package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GTValues.V;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GTUtility;

@IMTE.SkipGenerateDescription
public class MTEWetTransformer extends MTETransformer {

    private boolean mHalfMode = false;

    public MTEWetTransformer(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
    }

    public MTEWetTransformer(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public IMTE newMTE(IGregTechTileEntity gte) {
        return new MTEWetTransformer(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[12][17][];
        for (byte b = -1; b < 16; b++) {
            rTextures[0][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_64A[mTier + 1] };
            rTextures[1][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_64A[mTier + 1] };
            rTextures[2][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_64A[mTier + 1] };
            rTextures[3][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_16A[mTier + 2] };
            rTextures[4][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_16A[mTier + 2] };
            rTextures[5][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_16A[mTier + 2] };
            rTextures[6][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_64A[mTier + 1] };
            rTextures[7][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_64A[mTier + 1] };
            rTextures[8][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_64A[mTier + 1] };
            rTextures[9][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_16A[mTier + 2] };
            rTextures[10][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_16A[mTier + 2] };
            rTextures[11][b + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][b + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_16A[mTier + 2] };
        }
        return rTextures;
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }

    @Override
    public long maxEUStore() {
        return 512L + V[mTier + 1] * 128L;
    }

    @Override
    public long maxAmperesOut() {
        if (mHalfMode) {
            return getGTE().isAllowedToWork() ? 32 : 8;
        }
        return getGTE().isAllowedToWork() ? 64 : 16;
    }

    @Override
    public long maxAmperesIn() {
        if (mHalfMode) {
            return getGTE().isAllowedToWork() ? 10 : 40;
        }
        return getGTE().isAllowedToWork() ? 20 : 80;
    }

    @Override
    public long displayedAmperesIn() {
        if (mHalfMode) {
            return getGTE().isAllowedToWork() ? 8 : 32;
        }
        return getGTE().isAllowedToWork() ? 16 : 64;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        nbt.setBoolean("mHalfMode", mHalfMode);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        mHalfMode = nbt.getBoolean("mHalfMode");
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z,
        ItemStack tool) {
        this.mHalfMode = !mHalfMode;
        if (this.mHalfMode) {
            GTUtility.sendChatTrans(player, "GT5U.chat.wet_transformer.half_mode.enable");
        } else {
            GTUtility.sendChatTrans(player, "GT5U.chat.wet_transformer.half_mode.disable");
        }
    }
}
