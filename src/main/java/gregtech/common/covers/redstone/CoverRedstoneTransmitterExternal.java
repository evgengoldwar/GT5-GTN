package gregtech.common.covers.redstone;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverContext;
import gregtech.api.interfaces.ITexture;
import gregtech.common.covers.Base.CoverRedstoneWirelessBase;

public class CoverRedstoneTransmitterExternal extends CoverRedstoneWirelessBase {

    public CoverRedstoneTransmitterExternal(CoverContext context, ITexture coverTexture) {
        super(context, coverTexture);
    }

    @Override
    public void onCoverRemoval() {
        GregTechAPI.WIRELESS_REDSTONE.remove(getMapFrequency());
    }

    @Override
    public void onBaseTEDestroyed() {
        GregTechAPI.WIRELESS_REDSTONE.remove(getMapFrequency());
    }

    @Override
    public void doCoverThings(byte aInputRedstone, long aTimer) {
        GregTechAPI.WIRELESS_REDSTONE.put(getMapFrequency(), aInputRedstone);
    }

    @Override
    public boolean letsRedstoneGoIn() {
        return true;
    }

    // TODO: Remove this in 2.9 unless class moved from CoverLegacyData
    @Override
    protected void readDataFromNbt(NBTBase nbt) {
        if (nbt instanceof NBTTagInt nbtInt) {
            int data = nbtInt.func_150287_d();
            processCoverData(getFlagFrequency(data), getFlagCheckbox(data));
            return;
        }
        NBTTagCompound tag = (NBTTagCompound) nbt;
        processCoverData(tag.getInteger("frequency"), tag.getBoolean("privateChannel"));
    }
}
