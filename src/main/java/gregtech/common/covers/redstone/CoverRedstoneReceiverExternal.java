package gregtech.common.covers.redstone;

import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverContext;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.common.covers.Base.CoverRedstoneWirelessBase;

public class CoverRedstoneReceiverExternal extends CoverRedstoneWirelessBase {

    public CoverRedstoneReceiverExternal(CoverContext context, ITexture coverTexture) {
        super(context, coverTexture);
    }

    @Override
    public boolean isRedstoneSensitive(long aTimer) {
        return false;
    }

    @Override
    public void doCoverThings(byte aInputRedstone, long aTimer) {
        ICoverable coverable = coveredTile.get();
        if (coverable == null) {
            return;
        }
        coverable.setOutputRedstoneSignal(
            coverSide,
            GregTechAPI.WIRELESS_REDSTONE.get(getMapFrequency()) == null ? 0
                : GregTechAPI.WIRELESS_REDSTONE.get(getMapFrequency()));
    }

    @Override
    public boolean manipulatesSidedRedstoneOutput() {
        return true;
    }

}
