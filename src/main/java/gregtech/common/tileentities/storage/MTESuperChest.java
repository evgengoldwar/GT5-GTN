package gregtech.common.tileentities.storage;

import net.minecraft.util.StatCollector;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.MetaTileEntity;

public class MTESuperChest extends MTEQuantumChest {

    public MTESuperChest(int aID, String aName, String aNameRegional, int aTier, IMTE[] mteArray) {
        super(aID, aName, aNameRegional, aTier, mteArray);
    }

    public MTESuperChest(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    protected String localizedChestName() {
        return StatCollector.translateToLocal("GT5U.infodata.super_chest.name");
    }

    @Override
    public MetaTileEntity newMTE(IGTE gte) {
        return new MTESuperChest(name, mTier, mDescriptionArray, mTextures);
    }
}
