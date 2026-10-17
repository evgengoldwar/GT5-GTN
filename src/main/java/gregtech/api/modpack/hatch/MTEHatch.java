package gregtech.api.modpack.hatch;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
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
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * Base of all multiblock hatches. When the hatch is part of a formed multiblock it takes the casing texture of the
 * multiblock ({@link #setCasingTexture(int)}), otherwise it renders as a tiered hull.
 * <p>
 * Overlay texture: {@code assets/gregtech/textures/blocks/machines/hatches/overlay_<type>.png}.
 */
@IMTE.SkipGenerateDescription
public abstract class MTEHatch extends MTE {

    protected final HatchType type;
    protected final int tier;
    protected final String localName;
    private int casingTextureId;

    protected MTEHatch(HatchType type, int tier, String id, String localName, int slots) {
        super(id, slots);
        this.mteArrays = MTEArrays.HATCH;
        this.type = type;
        this.tier = tier;
        this.localName = localName;
    }

    public HatchType getType() {
        return type;
    }

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

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel) {
        ITexture casing = CasingTextures.get(casingTextureId);
        ITexture base = casing != null ? casing : MachineTextures.hull(tier, side);
        if (side != facing) {
            return new ITexture[] { base };
        }
        return new ITexture[] { base,
            TextureFactory.of(ResourceIcon.blockOptional("machines/hatches/overlay_" + type.textureName)) };
    }

    // endregion

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
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
        lines.add("Multiblock part: " + type.localName);
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
