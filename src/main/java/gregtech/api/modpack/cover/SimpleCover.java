package gregtech.api.modpack.cover;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fluids.Fluid;

import gregtech.api.covers.CoverContext;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.common.covers.Base.Cover;

/**
 * Cover driven by a {@link CoverBehavior}. One instance exists per placed cover.
 */
public class SimpleCover extends Cover {

    protected final CoverBehavior behavior;
    protected NBTTagCompound data = new NBTTagCompound();

    public SimpleCover(CoverContext context, ITexture texture, CoverBehavior behavior) {
        super(context, texture);
        this.behavior = behavior;
    }

    /** Free form data of this cover, saved with the machine. */
    public NBTTagCompound getData() {
        return data;
    }

    /** Integer stored in {@link #getData()}, handy for modes. */
    public int getMode() {
        return data.getInteger("mode");
    }

    public void setMode(int mode) {
        data.setInteger("mode", mode);
    }

    /** Cycles the mode in 0..count-1 and tells the player the new mode name. */
    public int cycleMode(EntityPlayer player, String... names) {
        int mode = (getMode() + 1) % names.length;
        setMode(mode);
        if (player != null) {
            player.addChatComponentMessage(new ChatComponentText(names[mode]));
        }
        return mode;
    }

    /** Sets the redstone output on the covered side (needs {@link CoverBehavior#emitsRedstone}). */
    public void setRedstoneOutput(int strength) {
        ICoverable tile = getTile();
        if (tile != null) {
            tile.setOutputRedstoneSignal(coverSide, (byte) Math.max(0, Math.min(15, strength)));
        }
    }

    // region Cover overrides

    @Override
    public int getMinimumTickRate() {
        return behavior.getTickRate();
    }

    @Override
    public void doCoverThings(byte redstone, long timer) {
        ICoverable tile = getTile();
        if (tile != null && !tile.isClientSide()) {
            behavior.onTick(this, redstone, timer);
        }
    }

    @Override
    public void onPlayerAttach(EntityPlayer player, ItemStack coverItem) {
        behavior.onAttach(this, player);
    }

    @Override
    public void onCoverRemoval() {
        behavior.onRemove(this);
    }

    @Override
    public boolean onCoverRightClick(EntityPlayer player, float x, float y, float z) {
        return behavior.onRightClick(this, player);
    }

    @Override
    public void onCoverScrewdriverClick(EntityPlayer player, float x, float y, float z) {
        behavior.onScrewdriver(this, player);
    }

    @Override
    public boolean letsEnergyIn() {
        return behavior.letsEnergyIn(this);
    }

    @Override
    public boolean letsEnergyOut() {
        return behavior.letsEnergyOut(this);
    }

    @Override
    public boolean letsItemsIn(int slot) {
        return behavior.letsItemsIn(this, slot);
    }

    @Override
    public boolean letsItemsOut(int slot) {
        return behavior.letsItemsOut(this, slot);
    }

    @Override
    public boolean letsFluidIn(Fluid fluid) {
        return behavior.letsFluidIn(this, fluid);
    }

    @Override
    public boolean letsFluidOut(Fluid fluid) {
        return behavior.letsFluidOut(this, fluid);
    }

    @Override
    public boolean letsRedstoneGoIn() {
        return behavior.letsRedstoneIn(this);
    }

    @Override
    public boolean letsRedstoneGoOut() {
        return behavior.emitsRedstone(this);
    }

    @Override
    public boolean manipulatesSidedRedstoneOutput() {
        return behavior.emitsRedstone(this);
    }

    @Override
    public boolean alwaysLookConnected() {
        return behavior.alwaysLookConnected(this);
    }

    @Override
    public List<String> getAdditionalTooltip() {
        List<String> tooltip = new ArrayList<>(super.getAdditionalTooltip());
        behavior.addTooltip(this, tooltip);
        return tooltip;
    }

    @Override
    protected void readDataFromNbt(NBTBase nbt) {
        if (nbt instanceof NBTTagCompound compound) {
            data = (NBTTagCompound) compound.copy();
        }
    }

    @Override
    protected @Nonnull NBTBase saveDataToNbt() {
        return data.copy();
    }

    // endregion
}
