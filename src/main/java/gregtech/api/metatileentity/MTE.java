package gregtech.api.metatileentity;

import static gregtech.api.interfaces.tileentity.IColoredTileEntity.UNCOLOURED;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.utils.item.IItemHandlerModifiable;
import com.cleanroommc.modularui.utils.item.ItemStackHandler;

import appeng.api.crafting.ICraftingIconProvider;
import appeng.api.implementations.IPowerChannelState;
import appeng.api.util.AECableType;
import appeng.core.localization.WailaText;
import appeng.me.helpers.AENetworkProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.enums.SoundResource;
import gregtech.api.gui.GUIColorOverride;
import gregtech.api.gui.modularui.GUITextureSet;
import gregtech.api.interfaces.IConfigurationCircuitSupport;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.implementations.MTECable;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Client;
import gregtech.common.gui.modularui.util.MTEItemStackHandler;
import gregtech.mixin.interfaces.accessors.EntityPlayerMPAccessor;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public abstract class MTE extends CommonMTE implements ICraftingIconProvider {

    public final ItemStackHandler inventoryHandler;

    protected GUIColorOverride colorOverride;

    // spotless:off
    private static final String[] FACING_DIRECTION_NAMES = new String[] {
        "GT5U.waila.facing.down",
        "GT5U.waila.facing.up",
        "GT5U.waila.facing.north",
        "GT5U.waila.facing.south",
        "GT5U.waila.facing.west",
        "GT5U.waila.facing.east",
        "GT5U.waila.facing.unknown" };
    // spotless:on

    @Override
    public IItemHandlerModifiable getInventoryHandler() {
        return inventoryHandler;
    }

    private IGTE gte;

    private String playerLang;

    public MTE(MTEArrays mteArrays, int meta, String name, int slotCount) {
        super(mteArrays, meta, name, slotCount);
        setGTE(new BaseGTE());
        getGTE().setMeta((short) meta);

        inventoryHandler = new MTEItemStackHandler(inventory, this);
    }

    public MTE(String name, int slotCount) {
        super(name, slotCount);
        inventoryHandler = new MTEItemStackHandler(inventory, this);
        colorOverride = GUIColorOverride.get(getGUITextureSet().getMainBackground().location);
    }

    @Nullable
    @Override
    public final IGTE getGTE() {
        return gte;
    }

    @Override
    public final void setGTE(IGTE gte) {
        final IGTE oldBase = this.gte;
        if (this.gte != null && gte == null) {
            this.gte.getMTE()
                .inValidate();
            this.gte.setMTE(null);
        }
        this.gte = gte;
        if (this.gte != null) {
            this.gte.setMTE(this);
        }
        if (oldBase != gte && oldBase instanceof CommonGTE oldMeta) {
            oldMeta.refreshMetaTileEntityValidity();
        }
    }

    public boolean isValid() {
        return getGTE() != null && getGTE().getMTE() == this && !getGTE().isDead();
    }

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Class<T> capability, @NotNull ForgeDirection side) {
        if (capability == ICraftingIconProvider.class) {
            return capability.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public ItemStack getStackForm(long amount) {
        return new ItemStack(GregTechAPI.sBlockMachines, (int) amount, getGTE().getMeta());
    }

    @Override
    public String getLocalNameKey() {
        return "gt.blockmachines." + name + ".name";
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z,
        ItemStack tool) {}

    @Override
    public boolean onWrenchRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player, float x,
        float y, float z, ItemStack tool) {
        final IGTE meta = getGTE();
        if (!meta.isValidFacing(wrenchingSide)) {
            return false;
        }

        meta.setFrontFacing(wrenchingSide);

        for (final ForgeDirection s : ForgeDirection.VALID_DIRECTIONS) {
            final IGTE gte = meta.getIGregTechTileEntityAtSide(s);
        }
        return true;
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {

        if (!player.isSneaking()) {
            return false;
        }

        final ForgeDirection oppositeSide = wrenchingSide.getOpposite();
        final TileEntity tTileEntity = getGTE().getTileEntityAtSide(wrenchingSide);
        if ((tTileEntity instanceof IGTE gtTE) && (gtTE.getMTE() instanceof MTECable)) {
            return gtTE.getMTE()
                .onWireCutterRightClick(wrenchingSide, oppositeSide, player, x, y, z, tool);
        }
        return false;
    }

    @Override
    public boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {

        if (!player.isSneaking()) {
            return false;
        }

        final ForgeDirection oppositeSide = wrenchingSide.getOpposite();
        TileEntity tTileEntity = getGTE().getTileEntityAtSide(wrenchingSide);
        if ((tTileEntity instanceof IGTE gtTE) && (gtTE.getMTE() instanceof MTECable)) {
            return gtTE.getMTE()
                .onSolderingToolRightClick(wrenchingSide, oppositeSide, player, x, y, z, tool);
        }
        return false;
    }

    @Override
    public void onExplosion() {}

    public boolean onRightClick(IGTE gte, EntityPlayer player) {
        return false;
    }

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player, ForgeDirection side, float x, float y, float z) {
        if (gte.isServerSide()) {
            if (player instanceof EntityPlayerMPAccessor) {
                playerLang = ((EntityPlayerMPAccessor) player).gt5u$getTranslator();
            }
        }

        return onRightClick(gte, player);
    }

    protected String translate(String key) {
        String base;

        if (playerLang != null) {
            // note: playerLang is set in onRightclick, so this may not be 100% correct in some situations
            // on server
            base = LanguageRegistry.instance()
                .getStringLocalization(key, playerLang);
        } else {
            // on client
            base = LanguageRegistry.instance()
                .getStringLocalization(key);
        }

        if (base.isEmpty()) {
            base = LanguageRegistry.instance()
                .getStringLocalization(key, "en_US");
        }

        return base;
    }

    protected String translate(String key, Object... params) {
        return String.format(translate(key), params);
    }

    public boolean isElectric() {
        return true;
    }

    public boolean isEnetOutput() {
        return false;
    }

    public boolean isEnetInput() {
        return false;
    }

    public long maxEUStore() {
        return 0;
    }

    public long maxEUInput() {
        return 0;
    }

    public long maxEUOutput() {
        return 0;
    }

    public long maxAmperesOut() {
        return 1;
    }

    public long maxAmperesIn() {
        return 1;
    }

    public boolean isOutputFacing(ForgeDirection side) {
        return false;
    }

    public boolean isInputFacing(ForgeDirection side) {
        return false;
    }

    public long getEUVar() {
        return ((BaseGTE) gte).mStoredEnergy;
    }

    public void setEUVar(long energy) {
        if (energy != ((BaseGTE) gte).mStoredEnergy) {
            markDirty();
            ((BaseGTE) gte).mStoredEnergy = energy;
        }
    }

    public long getSteamVar() {
        return ((BaseGTE) gte).mStoredSteam;
    }

    public void setSteamVar(long steam) {
        if (((BaseGTE) gte).mStoredSteam != steam) {
            markDirty();
            ((BaseGTE) gte).mStoredSteam = steam;
        }
    }

    public long maxSteamStore() {
        return 0;
    }

    public long getMinimumStoredEU() {
        return 512;
    }

    public long getInputTier() {
        return GTUtility.getTier(getGTE().getInputVoltage());
    }

    public long getOutputTier() {
        return GTUtility.getTier(getGTE().getOutputVoltage());
    }

    public int rechargerSlotStartIndex() {
        return 0;
    }

    public int rechargerSlotCount() {
        return 0;
    }

    public int dechargerSlotStartIndex() {
        return 0;
    }

    public int dechargerSlotCount() {
        return 0;
    }

    public boolean ownerControl() {
        return false;
    }

    @Override
    public boolean isLiquidInput(ForgeDirection side) {
        return true;
    }

    @Override
    public boolean isLiquidOutput(ForgeDirection side) {
        return true;
    }

    public int getRealCapacity() {
        return getCapacity();
    }

    public void onFacingChange() {}

    public boolean isTeleporterCompatible() {
        return isEnetOutput() && getGTE().getOutputVoltage() >= 128 && getGTE().getUniversalEnergyCapacity() >= 500000;
    }

    public boolean willExplodeInRain() {
        return true;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        if (this instanceof IConfigurationCircuitSupport ccs) {
            if (ccs.allowSelectCircuit() && index == ccs.getCircuitSlot() && itemStack != null) {
                inventory[index] = GTUtility.copyAmount(0, itemStack);
                markDirty();
                return;
            }
        }
        super.setInventorySlotContents(index, itemStack);
        onContentsChanged(index);
    }

    public void onContentsChanged(int slot) {
        markDirty();
    }

    @Override
    public int fill(ForgeDirection side, FluidStack fluidStack, boolean doFill) {
        // if (getBaseMetaTileEntity().isSteampowered() && GTModHandler.isSteam(fluidStack) && fluidStack.amount > 1) {
        // int tSteam = (int) Math.min(
        // Integer.MAX_VALUE,
        // Math.min(
        // fluidStack.amount / 2,
        // getBaseMetaTileEntity().getSteamCapacity() - getBaseMetaTileEntity().getStoredSteam()));
        // if (tSteam > 0) {
        // markDirty();
        // if (doFill) getBaseMetaTileEntity().increaseStoredSteam(tSteam, true);
        // return tSteam * 2;
        // }
        // }
        return 0;
    }

    @Override
    public void markDirty() {
        if (gte != null) {
            gte.markDirty();
        }
    }

    @Override
    public float getExplosionResistance(ForgeDirection side) {
        return 10.0F;
    }

    @Override
    public void onColorChangeServer(byte color) {
        final IGTE gte = getGTE();
        final int x = gte.getXCord();
        final int y = gte.getYCord();
        final int z = gte.getZCord();
        for (final ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            final TileEntity te = gte.getTileEntityAtSide(side);
            if (te instanceof BasePipeGTE pipe) {
                pipe.onNeighborBlockChange(x, y, z);
            }
        }
    }

    @Override
    public void doExplosion(long explosionPower) {
        float strength = GTValues.getExplosionPowerForVoltage(explosionPower);
        final int x = getGTE().getXCord();
        final int y = getGTE().getYCord();
        final int z = getGTE().getZCord();
        final World world = getGTE().getWorld();
        GTUtility
            .sendSoundToPlayers(world, SoundResource.IC2_MACHINES_MACHINE_OVERLOAD, 1.0F, -1, x + .5, y + .5, z + .5);
        world.setBlock(x, y, z, Blocks.air);
        if (GregTechAPI.sMachineExplosions) {
            world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, strength, true);
        }
    }

    @Override
    public int getLightOpacity() {
        return ((BaseGTE) getGTE()).getLightValue() > 0 ? 0 : 255;
    }

    public boolean shouldTriggerBlockUpdate() {
        return false;
    }

    public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
        return AECableType.NONE;
    }

    public AENetworkProxy getProxy() {
        return null;
    }

    public void gridChanged() {}

    @Override
    public ItemStack getMachineCraftingIcon() {
        return getStackForm(1);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        if (Client.waila.showFacing) {
            list.add(
                StatCollector.translateToLocalFormatted(
                    "GT5U.waila.facing",
                    getFacingNameLocalized(
                        gte.getFrontFacing()
                            .ordinal())));
        }

        if (this instanceof IPowerChannelState) {
            final NBTTagCompound tag = accessor.getNBTData();
            final boolean isActive = tag.getBoolean("isActive");
            final boolean isPowered = tag.getBoolean("isPowered");
            final boolean isBooting = tag.getBoolean("isBooting");
            list.add(WailaText.getPowerState(isActive, isPowered, isBooting));
        }
    }

    protected static @NotNull String getFacingNameLocalized(int id) {
        if (id >= 0 && id < FACING_DIRECTION_NAMES.length) {
            return StatCollector.translateToLocal(FACING_DIRECTION_NAMES[id]);
        }
        return StatCollector.translateToLocal("GT5U.waila.facing.unknown");
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        if (this instanceof IPowerChannelState state) {
            final boolean isActive = state.isActive();
            final boolean isPowered = state.isPowered();
            final boolean isBooting = state.isBooting();
            tag.setBoolean("isActive", isActive);
            tag.setBoolean("isPowered", isPowered);
            tag.setBoolean("isBooting", isBooting);
        }
    }

    @Override
    public GUITextureSet getGUITextureSet() {
        return GUITextureSet.DEFAULT;
    }

    @Override
    public int getGUIColorization() {
        Dyes dye = Dyes.dyeWhite;
        if (this.colorOverride.sLoaded()) {
            if (this.colorOverride.sGuiTintingEnabled() && getGTE() != null) {
                dye = Dyes.getOrDefault(getGTE().getColorization(), Dyes.GUI_METAL);
                return this.colorOverride.getGuiTintOrDefault(dye.mName, dye.toInt());
            }
        } else if (GregTechAPI.sColoredGUI) {
            if (GregTechAPI.sMachineMetalGUI) {
                dye = Dyes.GUI_METAL;
            } else if (getGTE() != null) {
                dye = Dyes.getOrDefault(getGTE().getColorization(), Dyes.GUI_METAL);
            }
        }
        return dye.toInt();
    }

    @Override
    public int getTextColorOrDefault(String textType, int defaultColor) {
        return colorOverride.getTextColorOrDefault(textType, defaultColor);
    }

    final public byte getColor() {
        IGTE baseMetaTileEntity = getGTE();
        if (baseMetaTileEntity == null) return UNCOLOURED;
        return baseMetaTileEntity.getColorization();
    }

    public boolean isItemValidForPhantomSlot(int index, ItemStack itemStack) {
        return false;
    }
}
