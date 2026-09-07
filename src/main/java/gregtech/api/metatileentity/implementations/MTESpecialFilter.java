package gregtech.api.metatileentity.implementations;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;

public abstract class MTESpecialFilter extends MTEFilterBase {

    private boolean allowNbt = false;

    public MTESpecialFilter(int aID, String aName, String aNameRegional, int aTier, String[] aDescription,
        IMTE[] mteArray) {
        // 9 buffer slot, 1 representation slot, 1 holo slot. last seems not needed...
        super(aID, aName, aNameRegional, aTier, 11, aDescription, mteArray);
    }

    public MTESpecialFilter(String aName, int aTier, int aInvSlotCount, String[] aDescription,
        ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public boolean isAllowNbt() {
        return allowNbt;
    }

    public void setAllowNbt(boolean allowNbt) {
        this.allowNbt = allowNbt;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot == FILTER_SLOT_INDEX ? 1 : super.getSlotLimit(slot);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        nbt.setBoolean("bNBTAllowed", this.allowNbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        this.allowNbt = nbt.getBoolean("bNBTAllowed");
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return (super.allowPutStack(gte, index, side, itemStack)) && ((this.allowNbt) || (!itemStack.hasTagCompound()))
            && (this.isStackAllowed(itemStack) != this.invertFilter);
    }

    protected abstract boolean isStackAllowed(ItemStack aStack);
}
