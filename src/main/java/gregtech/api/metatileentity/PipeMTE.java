package gregtech.api.metatileentity;

import static gregtech.common.render.GTRendererBlock.BLOCK_MAX;
import static gregtech.common.render.GTRendererBlock.BLOCK_MIN;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IConnectable;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IColoredTileEntity;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.ILocalizedMetaPipeEntity;
import gregtech.api.render.ISBRInventoryContext;
import gregtech.api.render.ISBRWorldContext;
import gregtech.api.util.WorldSpawnedEventBuilder;
import gregtech.common.covers.Base.Cover;

public abstract class PipeMTE extends CommonMTE implements IConnectable {

    private volatile ITexture[][] inventoryTextureCache;

    public byte connections = 0;

    protected boolean checkConnections = false;

    private IGTE gte;

    private MTEArrays mteArrays;

    public PipeMTE(MTEArrays mteArrays, int meta, String name, int slotCount) {
        super(mteArrays, meta, name, slotCount);
        setGTE(new BasePipeGTE());
        getGTE().setMeta((short) meta);
        this.mteArrays = mteArrays;
    }

    public PipeMTE(String name, int slotCount) {
        super(name, slotCount);
    }

    @Override
    public String getLocalNameKey() {
        return "gt.blockmachines." + name + ".name";
    }

    @Override
    public String getLocalName() {
        if (this instanceof ILocalizedMetaPipeEntity localizedPipe) {
            return localizedPipe.getLocalizedName();
        }
        return StatCollector.translateToLocal("gt.blockmachines." + name + ".name");
    }

    @Override
    public String getInventoryName() {
        IMTE prototype = mteArrays.getMteArray()[getGTE().getMeta()];
        if (prototype != null) {
            return prototype.getLocalName();
        }
        return "";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderInInventory(ISBRInventoryContext ctx) {
        final float tThickness = getSize();
        final float pipeMin = (BLOCK_MAX - tThickness) / 2.0F;
        final float pipeMax = BLOCK_MAX - pipeMin;
        final RenderBlocks renderBlocks = ctx.getRenderBlocks();
        renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, pipeMin, BLOCK_MAX, pipeMax, pipeMax);

        ITexture[][] textures = inventoryTextureCache;
        if (textures == null) {
            final IGTE mte = getGTE();
            textures = new ITexture[][] { getTexture(mte, DOWN, (CONNECTED_WEST | CONNECTED_EAST), -1, false, false),
                getTexture(mte, WEST, (CONNECTED_WEST | CONNECTED_EAST), -1, true, false) };
            inventoryTextureCache = textures;
        }
        final ITexture[] sideTexture = textures[0];
        final ITexture[] endTexture = textures[1];
        ctx.renderNegativeYFacing(sideTexture);
        ctx.renderPositiveYFacing(sideTexture);
        ctx.renderNegativeZFacing(sideTexture);
        ctx.renderPositiveZFacing(sideTexture);
        ctx.renderNegativeXFacing(endTexture);
        ctx.renderPositiveXFacing(endTexture);
        return true;
    }

    @SuppressWarnings("OverlyComplexMethod")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderInWorld(ISBRWorldContext ctx) {
        BasePipeGTE rte = (BasePipeGTE) getGTE();
        if (rte == null) return false;

        final byte aConnections = rte.getConnections();
        final float thickness = getSize();
        if (thickness >= 1) {
            ctx.renderNegativeYFacing(rte.getTextureCovered(DOWN));
            ctx.renderPositiveYFacing(rte.getTextureCovered(UP));
            ctx.renderNegativeZFacing(rte.getTextureCovered(NORTH));
            ctx.renderPositiveZFacing(rte.getTextureCovered(SOUTH));
            ctx.renderNegativeXFacing(rte.getTextureCovered(WEST));
            ctx.renderPositiveXFacing(rte.getTextureCovered(EAST));
            return true;
        }
        // Range of block occupied by pipe
        final float pipeMin = (BLOCK_MAX - thickness) / 2.0F;
        final float pipeMax = BLOCK_MAX - pipeMin;

        final ITexture[] textureUp = rte.getTextureUncovered(UP);
        final ITexture[] textureDown = rte.getTextureUncovered(DOWN);
        final ITexture[] textureNorth = rte.getTextureUncovered(NORTH);
        final ITexture[] textureSouth = rte.getTextureUncovered(SOUTH);
        final ITexture[] textureWest = rte.getTextureUncovered(WEST);
        final ITexture[] textureEast = rte.getTextureUncovered(EAST);

        final RenderBlocks renderBlocks = ctx.getRenderBlocks();
        switch (aConnections) {
            case NO_CONNECTION -> {
                renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                ctx.renderNegativeYFacing(textureDown);
                ctx.renderPositiveYFacing(textureUp);
                ctx.renderNegativeZFacing(textureNorth);
                ctx.renderPositiveZFacing(textureSouth);
                ctx.renderNegativeXFacing(textureWest);
                ctx.renderPositiveXFacing(textureEast);
            }
            case CONNECTED_EAST | CONNECTED_WEST -> {
                // EAST - WEST Pipe Sides
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, pipeMin, BLOCK_MAX, pipeMax, pipeMax);

                ctx.renderNegativeYFacing(textureDown);
                ctx.renderPositiveYFacing(textureUp);
                ctx.renderNegativeZFacing(textureNorth);
                ctx.renderPositiveZFacing(textureSouth);

                // EAST - WEST Pipe Ends
                ctx.renderNegativeXFacing(textureWest);
                ctx.renderPositiveXFacing(textureEast);
            }
            case CONNECTED_DOWN | CONNECTED_UP -> {
                // UP - DOWN Pipe Sides
                renderBlocks.setRenderBounds(pipeMin, BLOCK_MIN, pipeMin, pipeMax, BLOCK_MAX, pipeMax);

                ctx.renderNegativeZFacing(textureNorth);
                ctx.renderPositiveZFacing(textureSouth);
                ctx.renderNegativeXFacing(textureWest);
                ctx.renderPositiveXFacing(textureEast);

                // UP - DOWN Pipe Ends
                ctx.renderNegativeYFacing(textureDown);
                ctx.renderPositiveYFacing(textureUp);
            }
            case CONNECTED_NORTH | CONNECTED_SOUTH -> {
                // NORTH - SOUTH Pipe Sides
                renderBlocks.setRenderBounds(pipeMin, pipeMin, BLOCK_MIN, pipeMax, pipeMax, BLOCK_MAX);

                ctx.renderNegativeYFacing(textureDown);
                ctx.renderPositiveYFacing(textureUp);
                ctx.renderNegativeXFacing(textureWest);
                ctx.renderPositiveXFacing(textureEast);

                // NORTH - SOUTH Pipe Ends
                ctx.renderNegativeZFacing(textureNorth);
                ctx.renderPositiveZFacing(textureSouth);
            }
            default -> {
                if ((aConnections & CONNECTED_WEST) == 0) {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                } else {
                    renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, pipeMin, pipeMin, pipeMax, pipeMax);

                    ctx.renderNegativeYFacing(textureDown);
                    ctx.renderPositiveYFacing(textureUp);
                    ctx.renderNegativeZFacing(textureNorth);
                    ctx.renderPositiveZFacing(textureSouth);
                }
                ctx.renderNegativeXFacing(textureWest);
                if ((aConnections & CONNECTED_EAST) == 0) {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                } else {
                    renderBlocks.setRenderBounds(pipeMax, pipeMin, pipeMin, BLOCK_MAX, pipeMax, pipeMax);

                    ctx.renderNegativeYFacing(textureDown);
                    ctx.renderPositiveYFacing(textureUp);
                    ctx.renderNegativeZFacing(textureNorth);
                    ctx.renderPositiveZFacing(textureSouth);
                }
                ctx.renderPositiveXFacing(textureEast);
                if ((aConnections & CONNECTED_DOWN) == 0) {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                } else {
                    renderBlocks.setRenderBounds(pipeMin, BLOCK_MIN, pipeMin, pipeMax, pipeMin, pipeMax);

                    ctx.renderNegativeZFacing(textureNorth);
                    ctx.renderPositiveZFacing(textureSouth);
                    ctx.renderNegativeXFacing(textureWest);
                    ctx.renderPositiveXFacing(textureEast);
                }
                ctx.renderNegativeYFacing(textureDown);
                if ((aConnections & CONNECTED_UP) == 0) {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                } else {
                    renderBlocks.setRenderBounds(pipeMin, pipeMax, pipeMin, pipeMax, BLOCK_MAX, pipeMax);

                    ctx.renderNegativeZFacing(textureNorth);
                    ctx.renderPositiveZFacing(textureSouth);
                    ctx.renderNegativeXFacing(textureWest);
                    ctx.renderPositiveXFacing(textureEast);
                }
                ctx.renderPositiveYFacing(textureUp);
                if ((aConnections & CONNECTED_NORTH) == 0) {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                } else {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, BLOCK_MIN, pipeMax, pipeMax, pipeMin);

                    ctx.renderNegativeYFacing(textureDown);
                    ctx.renderPositiveYFacing(textureUp);
                    ctx.renderNegativeXFacing(textureWest);
                    ctx.renderPositiveXFacing(textureEast);
                }
                ctx.renderNegativeZFacing(textureNorth);
                if ((aConnections & CONNECTED_SOUTH) == 0) {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMin, pipeMax, pipeMax, pipeMax);

                } else {
                    renderBlocks.setRenderBounds(pipeMin, pipeMin, pipeMax, pipeMax, pipeMax, BLOCK_MAX);

                    ctx.renderNegativeYFacing(textureDown);
                    ctx.renderPositiveYFacing(textureUp);
                    ctx.renderNegativeXFacing(textureWest);
                    ctx.renderPositiveXFacing(textureEast);
                }
                ctx.renderPositiveZFacing(textureSouth);
            }
        }
        final boolean hasCoverDown = rte.hasCoverAtSide(DOWN);
        final boolean hasCoverUp = rte.hasCoverAtSide(UP);
        final boolean hasCoverNorth = rte.hasCoverAtSide(NORTH);
        final boolean hasCoverSouth = rte.hasCoverAtSide(SOUTH);
        final boolean hasCoverWest = rte.hasCoverAtSide(WEST);
        final boolean hasCoverEast = rte.hasCoverAtSide(EAST);

        final var coverThickness = Float.min(BLOCK_MAX / 8.0F, (BLOCK_MAX - thickness) / 2.0F);
        final float coverInnerMin = BLOCK_MIN + coverThickness;
        final float coverInnerMax = BLOCK_MAX - coverThickness;

        if (hasCoverDown) {
            final ITexture[] coverTexture = rte.getTexture(DOWN);
            renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, coverInnerMin, BLOCK_MAX);
            if (!hasCoverNorth) ctx.renderNegativeZFacing(coverTexture);
            if (!hasCoverSouth) ctx.renderPositiveZFacing(coverTexture);
            if (!hasCoverWest) ctx.renderNegativeXFacing(coverTexture);
            if (!hasCoverEast) ctx.renderPositiveXFacing(coverTexture);
            ctx.renderPositiveYFacing(coverTexture);
            if ((aConnections & CONNECTED_DOWN) != 0) {
                // Split outer face to leave hole for pipe
                // Lower panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, BLOCK_MIN, pipeMin);
                ctx.renderNegativeYFacing(coverTexture);
                // Upper panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, pipeMax, BLOCK_MAX, BLOCK_MIN, BLOCK_MAX);
                ctx.renderNegativeYFacing(coverTexture);
                // Middle left panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, pipeMin, pipeMin, BLOCK_MIN, pipeMax);
                ctx.renderNegativeYFacing(coverTexture);
                // Middle right panel
                renderBlocks.setRenderBounds(pipeMax, BLOCK_MIN, pipeMin, BLOCK_MAX, BLOCK_MIN, pipeMax);
            }
            ctx.renderNegativeYFacing(coverTexture);
        }

        if (hasCoverUp) {
            final ITexture[] coverTexture = rte.getTexture(UP);
            renderBlocks.setRenderBounds(BLOCK_MIN, coverInnerMax, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX);
            if (!hasCoverNorth) ctx.renderNegativeZFacing(coverTexture);
            if (!hasCoverSouth) ctx.renderPositiveZFacing(coverTexture);
            if (!hasCoverWest) ctx.renderNegativeXFacing(coverTexture);
            if (!hasCoverEast) ctx.renderPositiveXFacing(coverTexture);
            ctx.renderNegativeYFacing(coverTexture);
            if ((aConnections & CONNECTED_UP) != 0) {
                // Split outer face to leave hole for pipe
                // Lower panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MAX, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, pipeMin);
                ctx.renderPositiveYFacing(coverTexture);
                // Upper panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MAX, pipeMax, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX);
                ctx.renderPositiveYFacing(coverTexture);
                // Middle left panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MAX, pipeMin, pipeMin, BLOCK_MAX, pipeMax);
                ctx.renderPositiveYFacing(coverTexture);
                // Middle right panel
                renderBlocks.setRenderBounds(pipeMax, BLOCK_MAX, pipeMin, BLOCK_MAX, BLOCK_MAX, pipeMax);
            }
            ctx.renderPositiveYFacing(coverTexture);
        }

        if (hasCoverNorth) {
            final ITexture[] coverTexture = rte.getTexture(NORTH);
            renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, coverInnerMin);
            if (!hasCoverDown) ctx.renderNegativeYFacing(coverTexture);
            if (!hasCoverUp) ctx.renderPositiveYFacing(coverTexture);
            if (!hasCoverWest) ctx.renderNegativeXFacing(coverTexture);
            if (!hasCoverEast) ctx.renderPositiveXFacing(coverTexture);
            ctx.renderPositiveZFacing(coverTexture);
            if ((aConnections & CONNECTED_NORTH) != 0) {
                // Split outer face to leave hole for pipe
                // Lower panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, pipeMin, BLOCK_MIN);
                ctx.renderNegativeZFacing(coverTexture);
                // Upper panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMax, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, BLOCK_MIN);
                ctx.renderNegativeZFacing(coverTexture);
                // Middle left panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, BLOCK_MIN, pipeMin, pipeMax, BLOCK_MIN);
                ctx.renderNegativeZFacing(coverTexture);
                // Middle right panel
                renderBlocks.setRenderBounds(pipeMax, pipeMin, BLOCK_MIN, BLOCK_MAX, pipeMax, BLOCK_MIN);
            }
            ctx.renderNegativeZFacing(coverTexture);
        }

        if (hasCoverSouth) {
            final ITexture[] coverTexture = rte.getTexture(SOUTH);
            renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, coverInnerMax, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX);
            if (!hasCoverDown) ctx.renderNegativeYFacing(coverTexture);
            if (!hasCoverUp) ctx.renderPositiveYFacing(coverTexture);
            if (!hasCoverWest) ctx.renderNegativeXFacing(coverTexture);
            if (!hasCoverEast) ctx.renderPositiveXFacing(coverTexture);
            ctx.renderNegativeZFacing(coverTexture);
            if ((aConnections & CONNECTED_SOUTH) != 0) {
                // Split outer face to leave hole for pipe
                // Lower panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, pipeMin, BLOCK_MAX);
                ctx.renderPositiveZFacing(coverTexture);
                // Upper panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMax, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX);
                ctx.renderPositiveZFacing(coverTexture);
                // Middle left panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, BLOCK_MAX, pipeMin, pipeMax, BLOCK_MAX);
                ctx.renderPositiveZFacing(coverTexture);
                // Middle right panel
                renderBlocks.setRenderBounds(pipeMax, pipeMin, BLOCK_MAX, BLOCK_MAX, pipeMax, BLOCK_MAX);
            }
            ctx.renderPositiveZFacing(coverTexture);
        }

        if (hasCoverWest) {
            final ITexture[] coverTexture = rte.getTexture(WEST);
            renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, coverInnerMin, BLOCK_MAX, BLOCK_MAX);
            if (!hasCoverDown) ctx.renderNegativeYFacing(coverTexture);
            if (!hasCoverUp) ctx.renderPositiveYFacing(coverTexture);
            if (!hasCoverNorth) ctx.renderNegativeZFacing(coverTexture);
            if (!hasCoverSouth) ctx.renderPositiveZFacing(coverTexture);
            ctx.renderPositiveXFacing(coverTexture);
            if ((aConnections & CONNECTED_WEST) != 0) {
                // Split outer face to leave hole for pipe
                // Lower panel
                renderBlocks.setRenderBounds(BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, BLOCK_MIN, pipeMin, BLOCK_MAX);
                ctx.renderNegativeXFacing(coverTexture);
                // Upper panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMax, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX);
                ctx.renderNegativeXFacing(coverTexture);
                // Middle left panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, BLOCK_MIN, BLOCK_MIN, pipeMax, pipeMin);
                ctx.renderNegativeXFacing(coverTexture);
                // Middle right panel
                renderBlocks.setRenderBounds(BLOCK_MIN, pipeMin, pipeMax, BLOCK_MIN, pipeMax, BLOCK_MAX);
            }
            ctx.renderNegativeXFacing(coverTexture);
        }

        if (hasCoverEast) {
            final ITexture[] coverTexture = rte.getTexture(EAST);
            renderBlocks.setRenderBounds(coverInnerMax, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX);
            if (!hasCoverDown) ctx.renderNegativeYFacing(coverTexture);
            if (!hasCoverUp) ctx.renderPositiveYFacing(coverTexture);
            if (!hasCoverNorth) ctx.renderNegativeZFacing(coverTexture);
            if (!hasCoverSouth) ctx.renderPositiveZFacing(coverTexture);
            ctx.renderNegativeXFacing(coverTexture);

            if ((aConnections & CONNECTED_EAST) != 0) {
                // Split outer face to leave hole for pipe
                // Lower panel
                renderBlocks.setRenderBounds(BLOCK_MAX, BLOCK_MIN, BLOCK_MIN, BLOCK_MAX, pipeMin, BLOCK_MAX);
                ctx.renderPositiveXFacing(coverTexture);
                // Upper panel
                renderBlocks.setRenderBounds(BLOCK_MAX, pipeMax, BLOCK_MIN, BLOCK_MAX, BLOCK_MAX, BLOCK_MAX);
                ctx.renderPositiveXFacing(coverTexture);
                // Middle left panel
                renderBlocks.setRenderBounds(BLOCK_MAX, pipeMin, BLOCK_MIN, BLOCK_MAX, pipeMax, pipeMin);
                ctx.renderPositiveXFacing(coverTexture);
                // Middle right panel
                renderBlocks.setRenderBounds(BLOCK_MAX, pipeMin, pipeMax, BLOCK_MAX, pipeMax, BLOCK_MAX);
            }
            ctx.renderPositiveXFacing(coverTexture);
        }

        return true;
    }

    public float getSize() {
        if (GTMod.GT.isClientSide() && GTMod.clientProxy()
            .shouldHideThings()) return 0.0625F;
        return getCollisionThickness();
    }

    public abstract float getCollisionThickness();

    public abstract boolean renderInside(ForgeDirection side);

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel) {
        return Textures.BlockIcons.ERROR_RENDERING;
    }

    public ITexture[] getTexture(IGTE baseMetaTileEntity, ForgeDirection sideDirection, int connections, int colorIndex,
        boolean active, boolean redstoneLevel) {
        return Textures.BlockIcons.ERROR_RENDERING;
    }

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

    @Override
    public void receiveClientEvent(byte eventId, byte value) {
        super.receiveClientEvent(eventId, value);
        if (eventId == GregTechTileClientEvents.CHANGE_COMMON_DATA) {
            connections = value;
        }
    }

    @Override
    public ItemStack getStackForm(long amount) {
        return new ItemStack(GregTechAPI.sBlockMachines, (int) amount, getGTE().getMeta());
    }

    @Override
    public void initDefaultModes(NBTTagCompound nbt) {
        super.initDefaultModes(nbt);
        if (getGTE() instanceof BasePipeGTE pipe) {
            pipe.updateConnections();
        }
    }

    public boolean isCoverOnSide(BasePipeGTE pipe, EntityLivingBase entity) {
        ForgeDirection side = ForgeDirection.UNKNOWN;
        double difference = entity.posY - (double) pipe.yCoord;
        if (difference > 0.6 && difference < 0.99) {
            side = ForgeDirection.UP;
        }
        if (difference < -1.5 && difference > -1.99) {
            side = ForgeDirection.DOWN;
        }
        difference = entity.posZ - (double) pipe.zCoord;
        if (difference < -0.05 && difference > -0.4) {
            side = ForgeDirection.NORTH;
        }
        if (difference > 1.05 && difference < 1.4) {
            side = ForgeDirection.SOUTH;
        }
        difference = entity.posX - (double) pipe.xCoord;
        if (difference < -0.05 && difference > -0.4) {
            side = ForgeDirection.WEST;
        }
        if (difference > 1.05 && difference < 1.4) {
            side = ForgeDirection.EAST;
        }
        boolean tCovered = side != ForgeDirection.UNKNOWN && gte.hasCoverAtSide(side);
        if (isConnectedAtSide(side)) {
            tCovered = true;
        }
        return tCovered;
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z,
        ItemStack tool) {}

    @Override
    public boolean onWrenchRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player, float x,
        float y, float z, ItemStack tool) {
        return false;
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {
        return false;
    }

    @Override
    public boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player,
        float x, float y, float z, ItemStack tool) {
        return false;
    }

    @Override
    public void onExplosion() {}

    @Override
    public boolean isLiquidInput(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean isLiquidOutput(ForgeDirection side) {
        return false;
    }

    public int fill_default(ForgeDirection side, FluidStack aFluid, boolean doFill) {
        return fill(aFluid, doFill);
    }

    @Override
    public int fill(ForgeDirection side, FluidStack aFluid, boolean doFill) {
        return fill_default(side, aFluid, doFill);
    }

    @Override
    public float getExplosionResistance(ForgeDirection side) {
        return 5.0F;
    }

    @Override
    public void markDirty() {}

    @Override
    public void onColorChangeServer(byte color) {
        setCheckConnections();
    }

    public void setCheckConnections() {
        checkConnections = true;
    }

    public long injectEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        return 0;
    }

    @Override
    public void doExplosion(long explosionPower) {
        float tStrength = GTValues.getExplosionPowerForVoltage(explosionPower);
        int tX = getGTE().getXCord(), tY = getGTE().getYCord(), tZ = getGTE().getZCord();
        World tWorld = getGTE().getWorld();
        tWorld.setBlock(tX, tY, tZ, Blocks.air);
        if (GregTechAPI.sMachineExplosions) {
            new WorldSpawnedEventBuilder.ExplosionEffectEventBuilder().setStrength(tStrength)
                .setSmoking(true)
                .setPosition(tX + 0.5, tY + 0.5, tZ + 0.5)
                .setWorld(tWorld)
                .run();
        }
    }

    @Override
    public int getLightOpacity() {
        return 0;
    }

    protected boolean connectableColor(TileEntity tTileEntity) {
        if (tTileEntity instanceof IColoredTileEntity) {
            if (getGTE().getColorization() >= 0) {
                final byte tColor = ((IColoredTileEntity) tTileEntity).getColorization();
                return tColor < 0 || tColor == getGTE().getColorization();
            }
        }

        return true;
    }

    @Override
    public int connect(ForgeDirection side) {
        if (side == ForgeDirection.UNKNOWN) return 0;

        final ForgeDirection oppositeSide = side.getOpposite();
        final IGTE baseMetaTile = getGTE();
        if (baseMetaTile == null) return 0;

        final Cover cover = baseMetaTile.getCoverAtSide(side);

        final boolean alwaysLookConnected = cover.alwaysLookConnected();
        final boolean letsIn = letsIn(cover);
        final boolean letsOut = letsOut(cover);

        // Careful - tTileEntity might be null, and that's ok -- so handle it
        final TileEntity tTileEntity = baseMetaTile.getTileEntityAtSide(side);
        if (!connectableColor(tTileEntity)) return 0;

        if ((alwaysLookConnected || letsIn || letsOut)) {
            // Are we trying to connect to a pipe? let's do it!
            final IMTE tPipe = tTileEntity instanceof IGTE ? ((IGTE) tTileEntity).getMTE() : null;
            if (getClass().isInstance(tPipe) || (tPipe != null && tPipe.getClass()
                .isInstance(this))) {
                connectAtSide(side);
                if (!((IConnectable) tPipe).isConnectedAtSide(oppositeSide)) {
                    // Make sure pipes all get together -- connect back to us if we're connecting to a pipe
                    ((IConnectable) tPipe).connect(oppositeSide);
                }
                return 1;
            } else if ((baseMetaTile.getAirAtSide(side)) || canConnect(side, tTileEntity)) {
                // Allow open connections to Air, if the GT6 style pipe/cables are enabled, so that it'll connect to
                // the next block placed down next to it
                connectAtSide(side);
                return 1;
            }
            if (!baseMetaTile.getWorld()
                .getChunkProvider()
                .chunkExists(baseMetaTile.getOffsetX(side, 1) >> 4, baseMetaTile.getOffsetZ(side, 1) >> 4)) {
                // Target chunk unloaded
                return -1;
            }
        }
        return 0;
    }

    protected void checkConnections() {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if ((isConnectedAtSide(side)) && connect(side) == 0) {
                disconnect(side);
            }
        }
        checkConnections = false;
    }

    private void connectAtSide(ForgeDirection side) {
        connections |= side.flag;
        if (getGTE() instanceof BasePipeGTE pipe && pipe.isClientSide()) {
            pipe.mConnections = connections;
        }
    }

    @Override
    public void disconnect(ForgeDirection side) {
        if (side == ForgeDirection.UNKNOWN) return;
        connections &= ~side.flag;
        final ForgeDirection oppositeSide = side.getOpposite();
        IGTE tTileEntity = getGTE().getIGregTechTileEntityAtSide(side);
        IMTE tPipe = tTileEntity == null ? null : tTileEntity.getMTE();
        if ((this.getClass()
            .isInstance(tPipe)
            || (tPipe != null && tPipe.getClass()
                .isInstance(this)))
            && ((IConnectable) tPipe).isConnectedAtSide(oppositeSide)) {
            ((IConnectable) tPipe).disconnect(oppositeSide);
        }
    }

    @Override
    public boolean isConnectedAtSide(ForgeDirection sideDirection) {
        return (connections & sideDirection.flag) != 0;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB inputAABB,
        List<AxisAlignedBB> outputAABB, Entity collider) {
        if (boundingBoxShouldBeFullBlock()) {
            AxisAlignedBB fullSizeBoundingBox = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
            if (inputAABB.intersectsWith(fullSizeBoundingBox)) outputAABB.add(fullSizeBoundingBox);
        }
        // Even if we're holding a tool, we still want to check for collisions with the regular size
        // of the pipe in case the player is currently in contact with it.
        AxisAlignedBB physicalBoundingBox = getPhysicalCollisionBoundingBox(x, y, z);
        if (inputAABB.intersectsWith(physicalBoundingBox)) outputAABB.add(physicalBoundingBox);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        // This is all we need to return when drawing the interaction box around the pipe.
        if (boundingBoxShouldBeFullBlock()) {
            return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
        }
        // Otherwise, account for attached covers and connections
        return getPhysicalCollisionBoundingBox(x, y, z);
    }

    private boolean boundingBoxShouldBeFullBlock() {
        // While holding tool, make it full block.
        return (GTMod.GT.isClientSide() && GTMod.clientProxy()
            .forceFullBlockBB()) || getCollisionThickness() == 1;
    }

    private @NotNull AxisAlignedBB getPhysicalCollisionBoundingBox(int x, int y, int z) {
        final float space = (1f - getCollisionThickness()) / 2;
        float yStart = space;
        float yEnd = 1f - space;
        float zStart = space;
        float zEnd = 1f - space;
        float xStart = space;
        float xEnd = 1f - space;
        final BasePipeGTE baseTE = (BasePipeGTE) getGTE();

        if (baseTE.hasCoverAtSide(ForgeDirection.DOWN)) {
            yStart = zStart = xStart = 0;
            zEnd = xEnd = 1;
        }
        if (baseTE.hasCoverAtSide(ForgeDirection.UP)) {
            zStart = xStart = 0;
            yEnd = zEnd = xEnd = 1;
        }
        if (baseTE.hasCoverAtSide(ForgeDirection.NORTH)) {
            yStart = zStart = xStart = 0;
            yEnd = xEnd = 1;
        }
        if (baseTE.hasCoverAtSide(ForgeDirection.SOUTH)) {
            yStart = xStart = 0;
            yEnd = zEnd = xEnd = 1;
        }
        if (baseTE.hasCoverAtSide(ForgeDirection.WEST)) {
            yStart = zStart = xStart = 0;
            yEnd = zEnd = 1;
        }
        if (baseTE.hasCoverAtSide(ForgeDirection.EAST)) {
            yStart = zStart = 0;
            yEnd = zEnd = xEnd = 1;
        }

        // this.mConnections isn't synced, but BaseMetaPipeEntity.mConnections is for some reason
        final byte connections = baseTE.mConnections;
        if ((connections & ForgeDirection.DOWN.flag) != 0) {
            yStart = 0f;
        }
        if ((connections & ForgeDirection.UP.flag) != 0) {
            yEnd = 1f;
        }
        if ((connections & ForgeDirection.NORTH.flag) != 0) {
            zStart = 0f;
        }
        if ((connections & ForgeDirection.SOUTH.flag) != 0) {
            zEnd = 1f;
        }
        if ((connections & ForgeDirection.WEST.flag) != 0) {
            xStart = 0f;
        }
        if ((connections & ForgeDirection.EAST.flag) != 0) {
            xEnd = 1f;
        }

        return AxisAlignedBB.getBoundingBox(x + xStart, y + yStart, z + zStart, x + xEnd, y + yEnd, z + zEnd);
    }

    public boolean letsIn(Cover cover) {
        return false;
    }

    public boolean letsOut(Cover cover) {
        return false;
    }

    public boolean canConnect(ForgeDirection side, TileEntity tTileEntity) {
        return false;
    }

    @Override
    public boolean isMachineBlockUpdateRecursive() {
        return false;
    }

    public void reloadLocks() {}

    @Override
    public int getGUIColorization() {
        Dyes dye = Dyes.dyeWhite;
        if (GregTechAPI.sColoredGUI) {
            if (GregTechAPI.sMachineMetalGUI) {
                dye = Dyes.GUI_METAL;
            } else if (getGTE() != null) {
                dye = Dyes.getOrDefault(getGTE().getColorization(), Dyes.GUI_METAL);
            }
        }
        return dye.toInt();
    }
}
