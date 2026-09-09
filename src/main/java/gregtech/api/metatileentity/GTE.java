package gregtech.api.metatileentity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import com.cleanroommc.modularui.utils.item.IItemHandlerModifiable;
import com.gtnewhorizons.modularui.api.drawable.ItemDrawable;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.screen.ITileWithModularUI;
import com.gtnewhorizons.modularui.api.screen.ModularUIContext;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.internal.network.NetworkUtils;
import com.gtnewhorizons.modularui.common.internal.wrapper.BaseSlot;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.MultiChildWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import gregtech.GTMod;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GTValues;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.gui.modularui.GUITextureSet;
import gregtech.api.interfaces.IConfigurationCircuitSupport;
import gregtech.api.interfaces.modularui.IAddGregtechLogo;
import gregtech.api.interfaces.modularui.IGetGUITextureSet;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.IGTEnet;
import gregtech.api.interfaces.tileentity.IWorldAccessor;
import gregtech.api.net.GTPacketSetConfigurationCircuit;
import gregtech.api.util.GTTooltipDataCache;
import gregtech.api.util.GTUtility;
import gregtech.common.data.GTBlockEventTracker;
import gregtech.common.gui.modularui.base.ItemSelectBaseGui;

public abstract class GTE extends TileEntity
    implements IWorldAccessor, IGTEnet, ITileWithModularUI, IAddGregtechLogo, IGetGUITextureSet {

    private final TileEntity[] bufferedTileEntities = new TileEntity[6];

    public boolean ignoreUnloadedChunks = true;

    public boolean isDead = false;

    protected GTTooltipDataCache tooltipCache = new GTTooltipDataCache();

    public static final int TOOLTIP_DELAY = 0;

    private final ChunkCoordinates returnedCoordinates = new ChunkCoordinates();

    public static ForgeDirection getSideForPlayerPlacing(Entity player, ForgeDirection defaultFacing,
        boolean[] aAllowedFacings) {

        final ForgeDirection facingFromPlayer = GTUtility.getSideFromPlayerFacing(player);
        if (facingFromPlayer != ForgeDirection.UNKNOWN && aAllowedFacings[facingFromPlayer.ordinal()])
            return facingFromPlayer;

        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (aAllowedFacings[dir.ordinal()]) {
                return dir;
            }
        }
        return defaultFacing;
    }

    private void clearNullMarkersFromTileEntityBuffer() {
        for (int i = 0; i < bufferedTileEntities.length; i++) {
            if (bufferedTileEntities[i] == this) {
                bufferedTileEntities[i] = null;
            }
        }
    }

    protected final void clearTileEntityBuffer() {
        Arrays.fill(bufferedTileEntities, null);
    }

    @Override
    public final World getWorld() {
        return worldObj;
    }

    @Override
    public final int getXCord() {
        return xCoord;
    }

    @Override
    public final short getYCord() {
        return (short) yCoord;
    }

    @Override
    public final int getZCord() {
        return zCoord;
    }

    @Override
    public final ChunkCoordinates getCords() {
        returnedCoordinates.posX = xCoord;
        returnedCoordinates.posY = yCoord;
        returnedCoordinates.posZ = zCoord;
        return returnedCoordinates;
    }

    @Override
    public final int getOffsetX(ForgeDirection side, int multiplier) {
        return xCoord + side.offsetX * multiplier;
    }

    @Override
    public final short getOffsetY(ForgeDirection side, int multiplier) {
        return (short) (yCoord + side.offsetY * multiplier);
    }

    @Override
    public final int getOffsetZ(ForgeDirection side, int multiplier) {
        return zCoord + side.offsetZ * multiplier;
    }

    abstract boolean isTickDisabled();

    @Override
    public final boolean isServerSide() {
        if (worldObj == null) {
            return FMLCommonHandler.instance()
                .getEffectiveSide() == Side.SERVER;
        }
        return !worldObj.isRemote;
    }

    @Override
    public final boolean isClientSide() {
        if (worldObj == null) {
            return FMLCommonHandler.instance()
                .getEffectiveSide() == Side.CLIENT;
        }
        return worldObj.isRemote;
    }

    @Override
    public final boolean isInvalidTileEntity() {
        return isInvalid();
    }

    @Override
    public final int getRandomNumber(int range) {
        return ThreadLocalRandom.current()
            .nextInt(range);
    }

    @Override
    public final BiomeGenBase getBiome(int x, int z) {
        return worldObj.getBiomeGenForCoords(x, z);
    }

    @Override
    public final BiomeGenBase getBiome() {
        return getBiome(xCoord, zCoord);
    }

    @Override
    public final Block getBlockOffset(int x, int y, int z) {
        return getBlock(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final Block getBlockAtSide(ForgeDirection side) {
        return getBlockAtSideAndDistance(side, 1);
    }

    @Override
    public final Block getBlockAtSideAndDistance(ForgeDirection side, int distance) {
        return getBlock(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final int getMetaIDOffset(int x, int y, int z) {
        return getMetaID(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final int getMetaIDAtSide(ForgeDirection side) {
        return getMetaIDAtSideAndDistance(side, 1);
    }

    @Override
    public final int getMetaIDAtSideAndDistance(ForgeDirection side, int distance) {
        return getMetaID(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final byte getLightLevelOffset(int x, int y, int z) {
        return getLightLevel(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final byte getLightLevelAtSide(ForgeDirection side) {
        return getLightLevelAtSideAndDistance(side, 1);
    }

    @Override
    public final byte getLightLevelAtSideAndDistance(ForgeDirection side, int distance) {
        return getLightLevel(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final boolean getOpacityOffset(int x, int y, int z) {
        return getOpacity(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final boolean getOpacityAtSide(ForgeDirection side) {
        return getOpacityAtSideAndDistance(side, 1);
    }

    @Override
    public final boolean getOpacityAtSideAndDistance(ForgeDirection side, int distance) {
        return getOpacity(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final boolean getSkyOffset(int x, int y, int z) {
        return getSky(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final boolean getSkyAtSide(ForgeDirection side) {
        return getSkyAtSideAndDistance(side, 1);
    }

    @Override
    public final boolean getSkyAtSideAndDistance(ForgeDirection side, int distance) {
        return getSky(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final boolean getAirOffset(int x, int y, int z) {
        return getAir(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final boolean getAirAtSide(ForgeDirection side) {
        return getAirAtSideAndDistance(side, 1);
    }

    @Override
    public final boolean getAirAtSideAndDistance(ForgeDirection side, int distance) {
        return getAir(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final TileEntity getTileEntityOffset(int x, int y, int z) {
        return getTileEntity(xCoord + x, yCoord + y, zCoord + z);
    }

    @Override
    public final TileEntity getTileEntityAtSideAndDistance(ForgeDirection side, int distance) {
        if (distance == 1) return getTileEntityAtSide(side);
        return getTileEntity(getOffsetX(side, distance), getOffsetY(side, distance), getOffsetZ(side, distance));
    }

    @Override
    public final IInventory getIInventory(int x, int y, int z) {
        final TileEntity tTileEntity = getTileEntity(x, y, z);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryOffset(int x, int y, int z) {
        final TileEntity tTileEntity = getTileEntityOffset(x, y, z);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryAtSide(ForgeDirection side) {
        final TileEntity tTileEntity = getTileEntityAtSide(side);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IInventory getIInventoryAtSideAndDistance(ForgeDirection side, int distance) {
        final TileEntity tTileEntity = getTileEntityAtSideAndDistance(side, distance);
        if (tTileEntity instanceof IInventory) return (IInventory) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainer(int x, int y, int z) {
        final TileEntity tTileEntity = getTileEntity(x, y, z);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerOffset(int x, int y, int z) {
        final TileEntity tTileEntity = getTileEntityOffset(x, y, z);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSide(ForgeDirection side) {
        final TileEntity tTileEntity = getTileEntityAtSide(side);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IFluidHandler getITankContainerAtSideAndDistance(ForgeDirection side, int distance) {
        final TileEntity tTileEntity = getTileEntityAtSideAndDistance(side, distance);
        if (tTileEntity instanceof IFluidHandler) return (IFluidHandler) tTileEntity;
        return null;
    }

    @Override
    public final IGTE getIGregTechTileEntity(int x, int y, int z) {
        final TileEntity tTileEntity = getTileEntity(x, y, z);
        if (tTileEntity instanceof IGTE) return (IGTE) tTileEntity;
        return null;
    }

    @Override
    public final IGTE getIGregTechTileEntityOffset(int x, int y, int z) {
        final TileEntity tTileEntity = getTileEntityOffset(x, y, z);
        if (tTileEntity instanceof IGTE) return (IGTE) tTileEntity;
        return null;
    }

    @Override
    public final IGTE getIGregTechTileEntityAtSide(ForgeDirection side) {
        final TileEntity tTileEntity = getTileEntityAtSide(side);
        if (tTileEntity instanceof IGTE) return (IGTE) tTileEntity;
        return null;
    }

    @Override
    public final IGTE getIGregTechTileEntityAtSideAndDistance(ForgeDirection side, int distance) {
        final TileEntity tTileEntity = getTileEntityAtSideAndDistance(side, distance);
        if (tTileEntity instanceof IGTE) return (IGTE) tTileEntity;
        return null;
    }

    @Override
    public final Block getBlock(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return Blocks.air;
        return worldObj.getBlock(x, y, z);
    }

    public Block getBlock(ChunkCoordinates aCoords) {
        if (worldObj == null) return Blocks.air;
        if (ignoreUnloadedChunks && crossedChunkBorder(aCoords)
            && !worldObj.blockExists(aCoords.posX, aCoords.posY, aCoords.posZ)) return Blocks.air;
        return worldObj.getBlock(aCoords.posX, aCoords.posY, aCoords.posZ);
    }

    @Override
    public final int getMetaID(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return 0;
        return worldObj.getBlockMetadata(x, y, z);
    }

    @Override
    public final byte getLightLevel(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return 0;
        return (byte) (worldObj.getLightBrightness(x, y, z) * 15);
    }

    @Override
    public final boolean getSky(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return true;
        return worldObj.canBlockSeeTheSky(x, y, z);
    }

    @Override
    public final boolean getOpacity(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return false;
        return GTUtility.isOpaqueBlock(worldObj, x, y, z);
    }

    @Override
    public final boolean getAir(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return true;
        return GTUtility.isBlockAir(worldObj, x, y, z);
    }

    @Override
    public TileEntity getTileEntity(int x, int y, int z) {
        if (ignoreUnloadedChunks && crossedChunkBorder(x, z) && !worldObj.blockExists(x, y, z)) return null;
        return worldObj.getTileEntity(x, y, z);
    }

    @Override
    public final TileEntity getTileEntityAtSide(ForgeDirection side) {
        final int ordinalSide = side.ordinal();
        if (side == ForgeDirection.UNKNOWN) return null;
        final int tX = getOffsetX(side, 1);
        final int tY = getOffsetY(side, 1);
        final int tZ = getOffsetZ(side, 1);
        if (crossedChunkBorder(tX, tZ)) {
            bufferedTileEntities[ordinalSide] = null;
            if (ignoreUnloadedChunks && !worldObj.blockExists(tX, tY, tZ)) return null;
        } else if (bufferedTileEntities[ordinalSide] == this) return null;
        if (bufferedTileEntities[ordinalSide] == null) {
            bufferedTileEntities[ordinalSide] = worldObj.getTileEntity(tX, tY, tZ);
            if (bufferedTileEntities[ordinalSide] == null) {
                bufferedTileEntities[ordinalSide] = this;
                return null;
            }
            return bufferedTileEntities[ordinalSide];
        }
        if (bufferedTileEntities[ordinalSide].isInvalid()) {
            bufferedTileEntities[ordinalSide] = null;
            return getTileEntityAtSide(side);
        }
        if (bufferedTileEntities[ordinalSide].xCoord == tX && bufferedTileEntities[ordinalSide].yCoord == tY
            && bufferedTileEntities[ordinalSide].zCoord == tZ) {
            return bufferedTileEntities[ordinalSide];
        }
        return null;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }

    @Override
    public final boolean isDead() {
        return isDead || isInvalidTileEntity();
    }

    @Override
    public void validate() {
        clearNullMarkersFromTileEntityBuffer();
        super.validate();
    }

    @Override
    public void invalidate() {
        clearNullMarkersFromTileEntityBuffer();
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        clearNullMarkersFromTileEntityBuffer();
        super.onChunkUnload();
        isDead = true;
    }

    @Override
    public void updateEntity() {
        isDead = false;
    }

    public void onAdjacentBlockChange(int x, int y, int z) {
        clearNullMarkersFromTileEntityBuffer();
    }

    public void updateNeighbours(int strongRedstoneOne, int strongRedstoneTwo) {
        final Block thisBlock = getBlockOffset(0, 0, 0);
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            final int x1 = xCoord + dir.offsetX, y1 = yCoord + dir.offsetY, z1 = zCoord + dir.offsetZ;

            if (worldObj.blockExists(x1, y1, z1)) {
                worldObj.notifyBlockOfNeighborChange(x1, y1, z1, thisBlock);

                // update if it was / is strong powered.
                if (((((strongRedstoneOne | strongRedstoneTwo) >>> dir.ordinal()) & 1) != 0)
                    && getBlock(x1, y1, z1).isNormalCube()) {
                    final int skipUpdateSide = dir.getOpposite()
                        .ordinal(); // Don't update this block. Still updates
                                    // diagonal blocks twice if conditions
                    // meet.

                    for (final ForgeDirection dir2 : ForgeDirection.VALID_DIRECTIONS) {
                        final int x2 = x1 + dir2.offsetX, y2 = y1 + dir2.offsetY, z2 = z1 + dir2.offsetZ;
                        if (dir2.ordinal() != skipUpdateSide && worldObj.blockExists(x2, y2, z2))
                            worldObj.notifyBlockOfNeighborChange(x2, y2, z2, thisBlock);
                    }
                }
            }
        }
    }

    @Override
    public final void sendBlockEvent(byte eventId, byte value) {
        GTBlockEventTracker.enqueue(worldObj, xCoord, yCoord, zCoord, eventId, value);
    }

    protected boolean crossedChunkBorder(int x, int z) {
        return x >> 4 != xCoord >> 4 || z >> 4 != zCoord >> 4;
    }

    public final boolean crossedChunkBorder(ChunkCoordinates cords) {
        return cords.posX >> 4 != xCoord >> 4 || cords.posZ >> 4 != zCoord >> 4;
    }

    public final void setOnFire() {
        GTUtility.setCoordsOnFire(worldObj, xCoord, yCoord, zCoord, false);
    }

    public final void setToFire() {
        worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
    }

    @Override
    public void markDirty() {
        // If some code calls markDirty before worldObj is assigned, e.g., in loadNBTData we will crash
        if (worldObj != null) {
            // Avoid sending neighbor updates, just mark the chunk as dirty to make sure it gets saved
            final Chunk chunk = worldObj.getChunkFromBlockCoords(xCoord, zCoord);
            if (chunk != null) {
                chunk.setChunkModified();
            }
        }
    }

    @Nullable
    public List<ItemStack> getItemsForHoloGlasses() {
        return null;
    }

    protected Supplier<Boolean> getValidator() {
        return () -> !this.isDead();
    }

    public boolean useModularUI() {
        return false;
    }

    public IItemHandlerModifiable getInventoryHandler() {
        return null;
    }

    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {}

    public void bindPlayerInventoryUI(ModularWindow.Builder builder, UIBuildContext buildContext) {
        builder.bindPlayerInventory(buildContext.getPlayer(), 7, getGUITextureSet().getItemSlot());
    }

    public String getLocalName() {
        return StatCollector.translateToLocal("GT5U.gui.title.unknown");
    }

    protected void addTitleToUI(ModularWindow.Builder builder) {
        addTitleToUI(builder, getLocalName());
    }

    protected void addTitleToUI(ModularWindow.Builder builder, String title) {
        if (GTMod.proxy.mTitleTabStyle == 2) {
            addTitleItemIconStyle(builder, title);
        } else {
            addTitleTextStyle(builder, title);
        }
    }

    protected void addTitleTextStyle(ModularWindow.Builder builder, String title) {
        final int TAB_PADDING = 3;
        final int TITLE_PADDING = 2;
        int titleWidth = 0, titleHeight = 0;
        if (NetworkUtils.isClient()) {
            final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            final List<String> titleLines = fontRenderer
                .listFormattedStringToWidth(title, getGUIWidth() - (TAB_PADDING + TITLE_PADDING) * 2);
            titleWidth = titleLines.size() > 1 ? getGUIWidth() - (TAB_PADDING + TITLE_PADDING) * 2
                : fontRenderer.getStringWidth(title);
            // noinspection PointlessArithmeticExpression
            titleHeight = titleLines.size() * fontRenderer.FONT_HEIGHT + (titleLines.size() - 1) * 1;
        }

        final DrawableWidget tab = new DrawableWidget();
        final TextWidget text = new TextWidget(title).setDefaultColor(getTitleColor())
            .setTextAlignment(Alignment.CenterLeft)
            .setMaxWidth(titleWidth);
        if (GTMod.proxy.mTitleTabStyle == 1) {
            tab.setDrawable(getGUITextureSet().getTitleTabAngular())
                .setPos(0, -(titleHeight + TAB_PADDING) + 1)
                .setSize(getGUIWidth(), titleHeight + TAB_PADDING * 2);
            text.setPos(TAB_PADDING + TITLE_PADDING, -titleHeight + TAB_PADDING);
        } else {
            tab.setDrawable(getGUITextureSet().getTitleTabDark())
                .setPos(0, -(titleHeight + TAB_PADDING * 2) + 1)
                .setSize(titleWidth + (TAB_PADDING + TITLE_PADDING) * 2, titleHeight + TAB_PADDING * 2 - 1);
            text.setPos(TAB_PADDING + TITLE_PADDING, -titleHeight);
        }
        builder.widget(tab)
            .widget(text);
    }

    protected void addTitleItemIconStyle(ModularWindow.Builder builder, String title) {
        builder.widget(
            new MultiChildWidget().addChild(
                new DrawableWidget().setDrawable(getGUITextureSet().getTitleTabNormal())
                    .setPos(0, 0)
                    .setSize(24, 24))
                .addChild(
                    new ItemDrawable(getStackForm(1)).asWidget()
                        .setPos(4, 4))
                .addTooltip(title)
                .setTooltipShowUpDelay(TOOLTIP_DELAY)
                .setPos(0, -24 + 3));
    }

    @Override
    public GUITextureSet getGUITextureSet() {
        return GUITextureSet.DEFAULT;
    }

    protected int getTitleColor() {
        return COLOR_TITLE.get();
    }

    @Override
    public void addGregTechLogo(ModularWindow.Builder builder) {
        builder.widget(
            new DrawableWidget().setDrawable(getGUITextureSet().getGregTechLogo())
                .setSize(17, 17)
                .setPos(152, 63));
    }

    protected int getGUIWidth() {
        return 176;
    }

    protected int getGUIHeight() {
        return 166;
    }

    protected boolean doesBindPlayerInventory() {
        return true;
    }

    public void addCoverTabs(ModularWindow.Builder builder, UIBuildContext buildContext) {}

    public IConfigurationCircuitSupport getConfigurationCircuitSupport() {
        if (!(this instanceof IConfigurationCircuitSupport)) return null;
        return (IConfigurationCircuitSupport) this;
    }

    protected void addConfigurationCircuitSlot(ModularWindow.Builder builder) {
        final IItemHandlerModifiable inventoryHandler = getInventoryHandler();
        if (inventoryHandler == null) return;

        if (!(this instanceof IInventory inv)) return;

        final IConfigurationCircuitSupport ccs = getConfigurationCircuitSupport();
        if (ccs == null) return;

        final AtomicBoolean dialogOpened = new AtomicBoolean(false);
        builder.widget(new SlotWidget(new BaseSlot(inventoryHandler, ccs.getCircuitSlot(), true)) {

            @Override
            protected void phantomClick(ClickData clickData, ItemStack cursorStack) {
                final ItemStack newCircuit;
                if (clickData.shift) {
                    if (clickData.mouseButton == 0) {
                        if (NetworkUtils.isClient() && !dialogOpened.get()) {
                            openSelectCircuitDialog(getContext(), dialogOpened);
                        }
                        return;
                    } else {
                        newCircuit = null;
                    }
                } else {
                    final List<ItemStack> tCircuits = GTUtility.getAllIntegratedCircuits();
                    final int index = GTUtility.findMatchingStackInList(tCircuits, cursorStack);
                    if (index < 0) {
                        int curIndex = GTUtility
                            .findMatchingStackInList(tCircuits, inv.getStackInSlot(ccs.getCircuitSlot())) + 1;
                        if (clickData.mouseButton == 0) {
                            curIndex += 1;
                        } else {
                            curIndex -= 1;
                        }
                        curIndex = Math.floorMod(curIndex, tCircuits.size() + 1) - 1;
                        newCircuit = curIndex < 0 ? null : tCircuits.get(curIndex);
                    } else {
                        // set to whatever it is
                        newCircuit = tCircuits.get(index);
                    }
                }
                inv.setInventorySlotContents(ccs.getCircuitSlot(), newCircuit);
            }

            @Override
            protected void phantomScroll(int direction) {
                if (GTMod.proxy.invertCircuitScrollDirection) {
                    phantomClick(new ClickData(direction > 0 ? 0 : 1, false, false, false));
                } else phantomClick(new ClickData(direction > 0 ? 1 : 0, false, false, false));
            }

            @Override
            public List<String> getExtraTooltip() {
                return Arrays.asList(
                    EnumChatFormatting.DARK_GRAY + EnumChatFormatting.getTextWithoutFormattingCodes(
                        StatCollector.translateToLocal("GT5U.machines.select_circuit.tooltip.1")),
                    EnumChatFormatting.DARK_GRAY + EnumChatFormatting.getTextWithoutFormattingCodes(
                        StatCollector.translateToLocal("GT5U.machines.select_circuit.tooltip.2")),
                    EnumChatFormatting.DARK_GRAY + EnumChatFormatting.getTextWithoutFormattingCodes(
                        StatCollector.translateToLocal("GT5U.machines.select_circuit.tooltip.3")));
            }
        }.setOverwriteItemStackTooltip(list -> {
            list.removeIf(
                line -> line.contains(StatCollector.translateToLocal("gt.integrated_circuit.tooltip.0"))
                    || line.contains(StatCollector.translateToLocal("gt.integrated_circuit.tooltip.1")));
            return list;
        })
            .disableShiftInsert()
            .setHandlePhantomActionClient(true)
            .setBackground(getGUITextureSet().getItemSlot(), GTUITextures.OVERLAY_SLOT_INT_CIRCUIT)
            .setGTTooltip(() -> tooltipCache.getData("GT5U.machines.select_circuit.tooltip"))
            .setTooltipShowUpDelay(TOOLTIP_DELAY)
            .setPos(ccs.getCircuitSlotX() - 1, ccs.getCircuitSlotY() - 1));
    }

    protected void openSelectCircuitDialog(ModularUIContext uiContext, AtomicBoolean dialogOpened) {
        final IConfigurationCircuitSupport ccs = getConfigurationCircuitSupport();
        if (ccs == null) return;

        if (!(this instanceof IInventory inv)) return;

        final List<ItemStack> circuits = GTUtility.getAllIntegratedCircuits();
        uiContext.openClientWindow(
            player -> new ItemSelectBaseGui(
                StatCollector.translateToLocal("GT5U.machines.select_circuit"),
                getStackForm(0),
                (ItemStack selected) -> {
                    this.onCircuitSelected(selected);
                    GTValues.NW.sendToServer(new GTPacketSetConfigurationCircuit(this, selected));
                },
                circuits,
                GTUtility.findMatchingStackInList(circuits, inv.getStackInSlot(ccs.getCircuitSlot())))
                    .setAnotherWindow(true, dialogOpened)
                    .setGuiTint(getGUIColorization())
                    .setCurrentGetter(() -> inv.getStackInSlot(ccs.getCircuitSlot()))
                    .createWindow(new UIBuildContext(player)));
    }

    protected void onCircuitSelected(ItemStack selected) {
        final IConfigurationCircuitSupport ccs = getConfigurationCircuitSupport();
        if (ccs == null) return;

        if (!(this instanceof IInventory inv)) return;

        inv.setInventorySlotContents(ccs.getCircuitSlot(), selected);
    }

    protected int getTextColorOrDefault(String textType, int defaultColor) {
        return defaultColor;
    }

    protected Supplier<Integer> COLOR_TITLE = () -> getTextColorOrDefault("title", 0x404040);

    public int getGUIColorization() {
        return Dyes.dyeWhite.toInt();
    }

    public ItemStack getStackForm(long amount) {
        return null;
    }
}
