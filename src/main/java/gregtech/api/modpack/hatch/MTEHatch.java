package gregtech.api.modpack.hatch;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import io.netty.buffer.ByteBuf;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.MTE;
import gregtech.api.modpack.machine.MachineTextures;
import gregtech.api.modpack.util.Lang;
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * Base of all multiblock hatches (like {@code MTEHatch} in GTNH). Every hatch is its own class with two constructors:
 *
 * <pre>{@code
 * public class MTEHatchSteamInput extends MTEHatchInput {
 *
 *     // prototype: registers the hatch under its id
 *     public MTEHatchSteamInput(int id, String name, String localName, int tier) {
 *         super(id, name, localName, tier);
 *     }
 *
 *     // copy for every placed block
 *     public MTEHatchSteamInput(String name, int tier) {
 *         super(name, tier);
 *     }
 *
 *     public IMTE newMTE(IGTE gte) {
 *         return new MTEHatchSteamInput(name, tier);
 *     }
 * }
 *
 * // registration (LoaderMetaTileEntities or IModpackModule.registerMachines):
 * new MTEHatchSteamInput(5000, "hatch.steam_input", "Steam Input Hatch", 1).getStackForm(1);
 * }</pre>
 *
 * When the hatch is part of a formed multiblock it takes the casing texture of the multiblock
 * ({@link #setCasingTexture(int)}), otherwise it renders as a tiered hull. Overlay texture:
 * {@code assets/gregtech/textures/blocks/machines/hatches/overlay_<type>.png}.
 */
@IMTE.SkipGenerateDescription
public abstract class MTEHatch extends MTE {

    public final int tier;
    private int casingTextureId;

    /** Prototype constructor: registers the hatch under {@code id} and its english name. */
    protected MTEHatch(int id, String name, String localName, int tier, int slots) {
        super(MTEArrays.HATCH, id, name, slots);
        this.tier = tier;
        Lang.add(getLocalNameKey(), localName);
    }

    /** Copy constructor, used in {@link #newMTE(IGTE)}. */
    protected MTEHatch(String name, int tier, int slots) {
        super(name, slots);
        this.mteArrays = MTEArrays.HATCH;
        this.tier = tier;
    }

    /** What kind of hatch this is, used by multiblock structure elements. */
    public abstract HatchType getType();

    public int getTier() {
        return tier;
    }

    public long getVoltage() {
        return GTValues.V[MachineTextures.clampTier(tier)];
    }

    // region Casing texture

    /** Called by the multiblock controller during the structure check (server side). */
    public void setCasingTexture(int id) {
        if (casingTextureId != id) {
            casingTextureId = id;
            IGTE gte = getGTE();
            if (gte != null && gte.isServerSide()) {
                gte.issueTileUpdate();
            }
        }
    }

    public int getCasingTexture() {
        return casingTextureId;
    }

    @Override
    public void writeToStream(ByteBuf buffer) {
        buffer.writeInt(casingTextureId);
    }

    @Override
    public void readFromStream(ByteBuf buffer) {
        casingTextureId = buffer.readInt();
    }

    /** Front overlay, override for custom hatches. */
    protected ITexture getOverlay() {
        return TextureFactory.of(
            ResourceIcon.blockOptional(
                "machines/hatches/overlay_" + getType().textureName));
    }

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel) {
        ITexture casing = CasingTextures.get(casingTextureId);
        ITexture base = casing != null ? casing : MachineTextures.hull(tier, side);
        if (side != facing) {
            return new ITexture[] { base };
        }
        return new ITexture[] { base, getOverlay() };
    }

    // endregion

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player) {
        if (gte.isClientSide()) {
            return true;
        }
        openGui(player);
        return true;
    }

    @Override
    protected boolean useMui2() {
        return true;
    }

    @Override
    public String[] getDescription() {
        List<String> lines = new ArrayList<>();
        addDescription(lines);
        return lines.toArray(new String[0]);
    }

    protected void addDescription(List<String> lines) {
        lines.add("Multiblock part: " + getType().localName);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt.setInteger("casingTexture", casingTextureId);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        casingTextureId = nbt.getInteger("casingTexture");
    }
}
