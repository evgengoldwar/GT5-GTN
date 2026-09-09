package gregtech.api.metatileentity;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.interfaces.tileentity.IColoredTileEntity.UNCOLOURED;

import java.util.List;
import java.util.function.Supplier;

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
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.pathing.IPathingGrid;
import appeng.api.util.AECableType;
import appeng.core.localization.WailaText;
import appeng.me.helpers.AENetworkProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.TieredVariant;
import gregtech.api.gui.GUIColorOverride;
import gregtech.api.gui.modularui.GUITextureSet;
import gregtech.api.interfaces.IConfigurationCircuitSupport;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.implementations.MTECable;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTTooltipDataCache;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Client;
import gregtech.common.gui.modularui.util.MTEItemStackHandler;
import gregtech.mixin.interfaces.accessors.EntityPlayerMPAccessor;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public abstract class MetaTileEntity extends CommonMTE implements ICraftingIconProvider {

    public final ItemStackHandler inventoryHandler;

    protected GUIColorOverride colorOverride;
    public final GTTooltipDataCache tooltipDataCache = new GTTooltipDataCache();

    private static final String[] FACING_DIRECTION_NAMES = new String[] { "GT5U.waila.facing.down",
        "GT5U.waila.facing.up", "GT5U.waila.facing.north", "GT5U.waila.facing.south", "GT5U.waila.facing.west",
        "GT5U.waila.facing.east", "GT5U.waila.facing.unknown" };

    @Override
    public IItemHandlerModifiable getInventoryHandler() {
        return inventoryHandler;
    }

    private IGTE gte;

    private String playerLang;

    public MetaTileEntity(MTEArrays mteArrays, int meta, String name, int slotCount) {
        super(mteArrays, meta, name, slotCount);
        setGTE(GregTechAPI.constructBaseMetaTileEntity());
        getGTE().setMetaTileID((short) meta);

        inventoryHandler = new MTEItemStackHandler(inventory, this);
    }

    public MetaTileEntity(String name, int slotCount) {
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
            this.gte.getMetaTileEntity()
                .inValidate();
            this.gte.setMetaTileEntity(null);
        }
        this.gte = gte;
        if (this.gte != null) {
            this.gte.setMetaTileEntity(this);
        }
        if (oldBase != gte && oldBase instanceof CommonBaseMetaTileEntity oldMeta) {
            oldMeta.refreshMetaTileEntityValidity();
        }
    }

    public boolean isValid() {
        return getGTE() != null && getGTE().getMetaTileEntity() == this && !getGTE().isDead();
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
        return new ItemStack(GregTechAPI.sBlockMachines, (int) amount, getGTE().getMetaTileID());
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
        if (!meta.isValidFacing(wrenchingSide)) return false;
        meta.setFrontFacing(wrenchingSide);

        for (final ForgeDirection s : ForgeDirection.VALID_DIRECTIONS) {
            final IGTE gte = meta.getIGregTechTileEntityAtSide(s);
        }
        return true;
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {

        if (!player.isSneaking()) return false;
        final ForgeDirection oppositeSide = wrenchingSide.getOpposite();
        final TileEntity tTileEntity = getGTE().getTileEntityAtSide(wrenchingSide);
        if ((tTileEntity instanceof IGTE gtTE) && (gtTE.getMetaTileEntity() instanceof MTECable)) {

            // The tile entity we're facing is a cable, let's try to connect to it
            return gtTE.getMetaTileEntity()
                .onWireCutterRightClick(wrenchingSide, oppositeSide, player, x, y, z, tool);
        }
        return false;
    }

    @Override
    public boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {

        if (!player.isSneaking()) return false;
        final ForgeDirection oppositeSide = wrenchingSide.getOpposite();
        TileEntity tTileEntity = getGTE().getTileEntityAtSide(wrenchingSide);
        if ((tTileEntity instanceof IGTE gtTE) && (gtTE.getMetaTileEntity() instanceof MTECable)) {

            // The tile entity we're facing is a cable, let's try to connect to it
            return gtTE.getMetaTileEntity()
                .onSolderingToolRightClick(wrenchingSide, oppositeSide, player, x, y, z, tool);
        }
        return false;
    }

    @Override
    public void onExplosion() {
        GTLog.writeExplosionLog(this, "Machine exploded");
    }

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

    public boolean isPneumatic() {
        return false;
    }

    public boolean isSteampowered() {
        return false;
    }

    public TieredVariant getTieredVariant() {
        return TieredVariant.STANDARD;
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
        return ((BaseMetaTileEntity) gte).mStoredEnergy;
    }

    public void setEUVar(long energy) {
        if (energy != ((BaseMetaTileEntity) gte).mStoredEnergy) {
            markDirty();
            ((BaseMetaTileEntity) gte).mStoredEnergy = energy;
        }
    }

    public long getSteamVar() {
        return ((BaseMetaTileEntity) gte).mStoredSteam;
    }

    public void setSteamVar(long aSteam) {
        if (((BaseMetaTileEntity) gte).mStoredSteam != aSteam) {
            markDirty();
            ((BaseMetaTileEntity) gte).mStoredSteam = aSteam;
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
    public void onBlockDestroyed() {
        final IGTE meta = getGTE();
    }

    @Override
    public void onColorChangeServer(byte color) {
        final IGTE meta = getGTE();
        final int aX = meta.getXCord(), aY = meta.getYCord(), aZ = meta.getZCord();
        for (final ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            // Flag surrounding pipes/cables to revaluate their connection with us if we got painted
            final TileEntity tTileEntity = meta.getTileEntityAtSide(side);
            if (tTileEntity instanceof BaseMetaPipeEntity pipe) {
                pipe.onNeighborBlockChange(aX, aY, aZ);
            }

            final IGTE gte = meta.getIGregTechTileEntityAtSide(side);
        }
    }

    @Override
    public void doExplosion(long explosionPower) {
        float tStrength = GTValues.getExplosionPowerForVoltage(explosionPower);
        final int tX = getGTE().getXCord();
        final int tY = getGTE().getYCord();
        final int tZ = getGTE().getZCord();
        final World tWorld = getGTE().getWorld();
        GTUtility.sendSoundToPlayers(
            tWorld,
            SoundResource.IC2_MACHINES_MACHINE_OVERLOAD,
            1.0F,
            -1,
            tX + .5,
            tY + .5,
            tZ + .5);
        tWorld.setBlock(tX, tY, tZ, Blocks.air);
        if (GregTechAPI.sMachineExplosions) tWorld.createExplosion(null, tX + 0.5, tY + 0.5, tZ + 0.5, tStrength, true);
    }

    @Override
    public int getLightOpacity() {
        return ((BaseMetaTileEntity) getGTE()).getLightValue() > 0 ? 0 : 255;
    }

    public boolean shouldTriggerBlockUpdate() {
        return false;
    }

    // === AE2 compat ===

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

    // === Waila compat ===

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

    protected String getAEDiagnostics() {
        try {
            if (getProxy() == null) return StatCollector.translateToLocal("GT5U.infodata.hatch.me.diagnostics.proxy");
            if (getProxy().getNode() == null)
                return StatCollector.translateToLocal("GT5U.infodata.hatch.me.diagnostics.node");
            if (getProxy().getNode()
                .getGrid() == null) return StatCollector.translateToLocal("GT5U.infodata.hatch.me.diagnostics.grid");
            if (!getProxy().getNode()
                .meetsChannelRequirements())
                return StatCollector.translateToLocal("GT5U.infodata.hatch.me.diagnostics.channels");
            IPathingGrid pg = getProxy().getNode()
                .getGrid()
                .getCache(IPathingGrid.class);
            if (!pg.isNetworkBooting())
                return StatCollector.translateToLocal("GT5U.infodata.hatch.me.diagnostics.booting");
            IEnergyGrid eg = getProxy().getNode()
                .getGrid()
                .getCache(IEnergyGrid.class);
            if (!eg.isNetworkPowered())
                return StatCollector.translateToLocal("GT5U.infodata.hatch.me.diagnostics.power");
        } catch (Exception ex) {
            GT_FML_LOGGER.error(ex);
        }
        return "";
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

    protected Supplier<Integer> COLOR_TITLE = () -> getTextColorOrDefault("title", 0x404040);
    protected Supplier<Integer> COLOR_TITLE_WHITE = () -> getTextColorOrDefault("title_white", 0xfafaff);
    protected Supplier<Integer> COLOR_TEXT_WHITE = () -> getTextColorOrDefault("text_white", 0xfafaff);
    protected Supplier<Integer> COLOR_TEXT_GRAY = () -> getTextColorOrDefault("text_gray", 0x404040);
    protected Supplier<Integer> COLOR_TEXT_RED = () -> getTextColorOrDefault("text_red", 0xff0000);

    // For MUI2 guis (which are usually built in a different class).
    public int getTitleColor() {
        return COLOR_TITLE.get();
    }

    public int getColorTitleWhite() {
        return COLOR_TITLE_WHITE.get();
    }

    public int getColorTextWhite() {
        return COLOR_TEXT_WHITE.get();
    }

    public int getColorTextGray() {
        return COLOR_TEXT_GRAY.get();
    }

    public int getColorTextRed() {
        return COLOR_TEXT_RED.get();
    }

    public boolean isItemValidForPhantomSlot(int index, ItemStack itemStack) {
        return false;
    }
}
