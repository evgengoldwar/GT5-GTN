package gregtech.common.blocks;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.gtnewhorizon.gtnhlib.item.ItemStackNBT;

import appeng.me.helpers.AENetworkProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Mods;
import gregtech.api.interfaces.IHideTooltipEnergyInfo;
import gregtech.api.interfaces.ISecondaryDescribable;
import gregtech.api.interfaces.metatileentity.IConnectable;
import gregtech.api.interfaces.metatileentity.IFluidContainerItemMetaTile;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.ILocalizedMetaPipeEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.CoverableTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEFluidPipe;
import gregtech.api.util.GTItsNotMyFaultException;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTSplit;
import gregtech.api.util.GTUtility;
import gregtech.api.util.tooltip.TooltipHelper;
import gregtech.common.config.Client;
import gregtech.crossmod.backhand.Backhand;

public class ItemMachines extends ItemBlock implements IFluidContainerItem {

    public ItemMachines(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    public static IMetaTileEntity getMetaTileEntity(ItemStack itemStack) {
        if (GTUtility.isStackInvalid(itemStack)) {
            return null;
        }
        if (!(itemStack.getItem() instanceof ItemMachines)) {
            return null;
        }
        if (itemStack.getItemDamage() < 0
            || itemStack.getItemDamage() > GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length) {
            return null;
        }
        return GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[itemStack.getItemDamage()];
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean F3_H) {
        try {
            final int meta = getDamage(itemStack);
            if ((meta <= 0) || (meta >= GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length)) {
                return;
            }

            if (GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta] != null) {
                final IGregTechTileEntity gte = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta]
                    .getBaseMetaTileEntity();
                final IMetaTileEntity mte = gte.getMetaTileEntity();
                if (mte instanceof ILocalizedMetaPipeEntity localizedMetaPipeEntity) {
                    localizedMetaPipeEntity.addMaterialTooltip(list);
                }
                addDescription(list, mte, meta);
                mte.addAdditionalTooltipInformation(itemStack, list);
                if (gte.getEUCapacity() > 0L) {
                    if (!(mte instanceof IHideTooltipEnergyInfo)) {
                        if (gte.getInputVoltage() > 0L) {
                            list.add(
                                StatCollector.translateToLocalFormatted(
                                    "gt.tileentity.eup_in",
                                    TooltipHelper.voltageText(gte.getInputVoltage())));
                        }
                        if (gte.getOutputVoltage() > 0L) {
                            list.add(
                                StatCollector.translateToLocalFormatted(
                                    "gt.tileentity.eup_out",
                                    TooltipHelper.voltageText(gte.getOutputVoltage())));
                        }
                        if (gte.getOutputAmperage() > 1L) {
                            list.add(
                                StatCollector.translateToLocalFormatted(
                                    "gt.tileentity.amperage",
                                    TooltipHelper.ampText(gte.getOutputAmperage())));
                        }
                    }
                    if (Client.tooltip.showEnergyCapacity) {
                        list.add(
                            translateToLocalFormatted(
                                "gt.tileentity.eup_store",
                                TooltipHelper.euCapacityText(gte.getEUCapacity())));
                    }
                }
            }
            final NBTTagCompound nbt = itemStack.getTagCompound();
            if (nbt != null) {
                if (nbt.getBoolean("mMuffler")) {
                    list.add(translateToLocal("gt.tileentity.has_muffler"));
                }
                if (nbt.getBoolean("mSteamConverter")) {
                    list.add(translateToLocal("gt.tileentity.has_steam_upgrade"));
                }

                CoverableTileEntity.addInstalledCoversInformation(nbt, list);
                if (nbt.hasKey("mColor") && nbt.getByte("mColor") != -1) {
                    list.add(
                        translateToLocalFormatted(
                            "gt.tileentity.colored",
                            Dyes.get(nbt.getByte("mColor") - 1).formatting,
                            Dyes.get(nbt.getByte("mColor") - 1).mName));
                }
            }
        } catch (Exception e) {
            list.add("§cAn exception was thrown while getting this item's info.§r");
            list.add(e.getLocalizedMessage());
            GT_FML_LOGGER.error("addInformation", e);
        }
    }

    private void addDescription(List<String> list, IMetaTileEntity mte, int meta) {
        final String[] description = mte.getDescription();
        if (description == null) {
            return;
        }
        if (isSkipGenerateDescription(mte)) {
            Collections.addAll(list, description);
            return;
        }

        final String suffix;

        if (mte instanceof ISecondaryDescribable secondaryDescribable) {
            if (secondaryDescribable.isDisplaySecondaryDescription()) {
                suffix = "_secondary";
            } else {
                suffix = "";
            }
        } else {
            suffix = "";
        }

        String key = "gt.blockmachines." + mte.getMetaName() + ".tooltip" + suffix;
        if (StatCollector.canTranslate(key + "." + meta)) {
            key = key + "." + meta;
        }
        final String translated = StatCollector.translateToLocal(key);
        if (translated.contains("%s")) {
            final String collected = Arrays.stream(description)
                .filter(GTUtility::isStringValid)
                .collect(Collectors.joining(""));
            List<String> parameters = new ArrayList<>();
            final Matcher matcher = Pattern.compile("%%%(.*?)%%%")
                .matcher(collected);
            while (matcher.find()) {
                parameters.add(matcher.group(1));
            }
            Collections.addAll(list, GTSplit.splitFormatted(translated, parameters.toArray()));
        } else {
            Collections.addAll(list, GTSplit.split(translated));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerDescription(int meta) {
        if (meta >= GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length) {
            return;
        }
        if (GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta] != null) {
            final IMetaTileEntity mte = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta].getBaseMetaTileEntity()
                .getMetaTileEntity();
            if (isSkipGenerateDescription(mte)) return;
            String key = "gt.blockmachines." + mte.getMetaName() + ".tooltip";
            if (mte instanceof ISecondaryDescribable secondaryDescribable) {
                final String[] secondaryDescription = secondaryDescribable.getSecondaryDescription();
                registerDescription(secondaryDescription, key + "_secondary", meta);
            }
            registerDescription(mte.getDescription(), key, meta);
        }
    }

    @SideOnly(Side.CLIENT)
    private void registerDescription(@Nullable String[] description, String key, int meta) {
        if (description == null) {
            return;
        }

        String collected = Arrays.stream(description)
            .filter(GTUtility::isStringValid)
            .collect(Collectors.joining(GTSplit.LB));
        if (collected.contains("%%%")) {
            collected = collected.replaceAll("%%%.*?%%%", "%s");
        }
        if (GTLanguageManager.hasGTLocalizationKey(key)) {
            GTLanguageManager.addStringLocalization(key + "." + meta, collected);
            return;
        }
        GTLanguageManager.addStringLocalization(key, collected);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        final short meta = (short) getDamage(itemStack);
        if ((meta < 0) || (meta >= GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length)) {
            return "";
        }
        if (GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta] != null) {
            return getUnlocalizedName() + "." + GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta].getMetaName();
        }
        return "";
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        String displayName = super.getItemStackDisplayName(itemStack);
        final short meta = (short) getDamage(itemStack);
        final IMetaTileEntity mte = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta];
        if (meta < GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length && mte != null) {
            if (mte instanceof ILocalizedMetaPipeEntity localMetaTE) {
                return localMetaTE.getLocalizedName();
            }
        }
        return displayName;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        super.onCreated(itemStack, world, player);
        final short meta = (short) getDamage(itemStack);
        if ((meta < 0) || ((meta >= GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length)
            && (GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta] != null))) {
            GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta].onCreated(itemStack, world, player);
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z,
        int ordinalSide, float hitX, float hitY, float hitZ, int unusedMeta) {
        final ForgeDirection side = ForgeDirection.getOrientation(ordinalSide);
        final short meta = (short) getDamage(itemStack);
        if (meta > 0) {
            if (GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta] == null) {
                return false;
            }

            final int newMeta = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta].getTileEntityBaseType();
            if (!world.setBlock(x, y, z, this.field_150939_a, newMeta, 3)) {
                return false;
            }

            if (world.getBlock(x, y, z) != this.field_150939_a) {
                throw new GTItsNotMyFaultException(
                    "Failed to place Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
            }

            if (world.getBlockMetadata(x, y, z) != newMeta) {
                throw new GTItsNotMyFaultException(
                    "Failed to set the MetaValue of the Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
            }
            final TileEntity te = world.getTileEntity(x, y, z);
            final IGregTechTileEntity gte = (IGregTechTileEntity) te;
            if (gte != null) {
                gte.setInitialValuesAsNBT(gte.isServerSide() ? itemStack.getTagCompound() : null, meta);

                if (player != null) {
                    gte.setOwnerName(player.getDisplayName());
                    gte.setOwnerUuid(player.getUniqueID());
                }

                if (gte.getMetaTileEntity() instanceof MetaTileEntity mte) {
                    AENetworkProxy proxy = mte.getProxy();
                    if (proxy != null) {
                        proxy.setOwner(player);
                    }
                }
                gte.setFrontFacing(
                    BaseTileEntity.getSideForPlayerPlacing(player, ForgeDirection.UP, gte.getValidFacings()));
                final ForgeDirection oppositeSide = side.getOpposite();
                if (gte.getMetaTileEntity() instanceof IConnectable connectable) {
                    connectable.connect(oppositeSide);
                    if (player != null && Mods.Backhand.isModLoaded() && connectable instanceof MTEFluidPipe pipe) {
                        pipeDirectionOffhand(player, pipe, side);
                    }
                } else if (player != null && player.isSneaking()) {
                    final IGregTechTileEntity newGte = gte.getIGregTechTileEntityAtSide(oppositeSide);
                    if (newGte != null && newGte.getMetaTileEntity() instanceof IConnectable connectable) {
                        connectable.connect(side);
                    }
                }
                gte.getMetaTileEntity()
                    .initDefaultModes(itemStack.getTagCompound());

                if (player instanceof EntityPlayerMP playerMP && playerMP.playerNetServerHandler != null) {
                    playerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
                    Packet packet = te.getDescriptionPacket();
                    if (packet != null) {
                        playerMP.playerNetServerHandler.sendPacket(packet);
                    }
                }
            }
        } else if (!world.setBlock(x, y, z, this.field_150939_a, meta, 3)) {
            return false;
        }
        if (world.getBlock(x, y, z) == this.field_150939_a) {
            this.field_150939_a.onBlockPlacedBy(world, x, y, z, player, itemStack);
            this.field_150939_a.onPostBlockPlaced(world, x, y, z, meta);
        }
        return true;
    }

    private void pipeDirectionOffhand(EntityPlayer player, MTEFluidPipe pipe, ForgeDirection side) {
        ItemStack offHand = Backhand.getOffhandItem(player);
        if ((GTUtility.isStackInList(offHand, GregTechAPI.sWrenchList))) {
            ForgeDirection oppositeSide = side.getOpposite();
            if (player.isSneaking()) {
                TileEntity adjTile = pipe.getBaseMetaTileEntity()
                    .getTileEntityAtSide(oppositeSide);
                if (adjTile instanceof IGregTechTileEntity gte
                    && gte.getMetaTileEntity() instanceof MTEFluidPipe adjPipe) {
                    adjPipe.mDisableInput |= (byte) side.flag;
                }
            } else {
                (pipe).mDisableInput |= (byte) oppositeSide.flag;
            }
            GTModHandler.damageOrDechargeItem(offHand, 1, 1000, player);
        }
    }

    @Nullable
    private static IFluidContainerItemMetaTile getFluidContainerItemMeta(@Nonnull ItemStack container) {
        final int meta = container.getItemDamage();
        if (meta < 0 || meta >= GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length) {
            return null;
        }
        final IMetaTileEntity mte = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta];
        return mte instanceof IFluidContainerItemMetaTile fluidMeta ? fluidMeta : null;
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        if (container != null) {
            final IFluidContainerItemMetaTile fluidMeta = getFluidContainerItemMeta(container);
            if (fluidMeta == null) {
                return null;
            }
            final String fluidKey = fluidMeta.getFluidNbtKey();
            if (ItemStackNBT.hasKey(container, fluidKey, Constants.NBT.TAG_COMPOUND)) {
                return FluidStack.loadFluidStackFromNBT(ItemStackNBT.getCompoundTag(container, fluidKey));
            }
        }
        return null;
    }

    @Override
    public int getCapacity(ItemStack container) {
        if (container != null) {
            final int meta = container.getItemDamage();
            final IMetaTileEntity mte = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[meta];
            if (mte != null) {
                return mte.getCapacity();
            }
        }
        return 0;
    }

    @Nullable
    private Fluid getLockedFluid(@Nonnull ItemStack container) {
        final String lockedName = ItemStackNBT.getString(container, "lockedFluidName");
        if (GTUtility.isStringInvalid(lockedName)) {
            return null;
        }
        return FluidRegistry.getFluid(lockedName);
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        if (container != null && resource != null) {
            final IFluidContainerItemMetaTile fluidMeta = getFluidContainerItemMeta(container);
            if (fluidMeta == null) {
                return 0;
            }

            final String fluidKey = fluidMeta.getFluidNbtKey();
            if (container.stackSize > 1) {
                return 0;
            }

            if (container.stackTagCompound == null) {
                container.stackTagCompound = new NBTTagCompound();
            }

            final FluidStack tStoredFluid = getFluid(container);
            final int tCapacity = getCapacity(container);
            if (tCapacity <= 0) {
                return 0;
            }

            final Fluid lockedFluid = getLockedFluid(container);
            if (lockedFluid != null && resource.getFluid() != lockedFluid) {
                return 0;
            }

            if (tStoredFluid != null && tStoredFluid.isFluidEqual(resource)) {
                final int tAmount = Math.min(tCapacity - tStoredFluid.amount, resource.amount);
                if (doFill) {
                    final FluidStack tNewFluid = new FluidStack(tStoredFluid, tAmount + tStoredFluid.amount);
                    container.stackTagCompound.setTag(fluidKey, tNewFluid.writeToNBT(new NBTTagCompound()));
                }
                return tAmount;
            }

            if (tStoredFluid == null) {
                final int tAmount = Math.min(tCapacity, resource.amount);
                if (doFill) {
                    final FluidStack tNewFluid = new FluidStack(resource, tAmount);
                    container.stackTagCompound.setTag(fluidKey, tNewFluid.writeToNBT(new NBTTagCompound()));
                }
                return tAmount;
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        if (container != null && container.hasTagCompound()) {
            final IFluidContainerItemMetaTile fluidMeta = getFluidContainerItemMeta(container);
            if (fluidMeta == null) {
                return null;
            }

            final String fluidKey = fluidMeta.getFluidNbtKey();
            final FluidStack fluid = getFluid(container);
            if (fluid != null) {
                final int amount = Math.min(maxDrain, fluid.amount);
                final FluidStack newFluid = new FluidStack(fluid, fluid.amount - amount);
                final FluidStack outputFluid = new FluidStack(fluid, amount);
                if (doDrain) {
                    if (newFluid.amount <= 0) {
                        ItemStackNBT.removeTag(container, fluidKey);
                    } else {
                        ItemStackNBT.setTag(container, fluidKey, newFluid.writeToNBT(new NBTTagCompound()));
                    }
                }
                return outputFluid;
            }
        }
        return null;
    }

    private static boolean isSkipGenerateDescription(IMetaTileEntity metaTE) {
        return metaTE.getClass()
            .getAnnotation(IMetaTileEntity.SkipGenerateDescription.class) != null;
    }
}
