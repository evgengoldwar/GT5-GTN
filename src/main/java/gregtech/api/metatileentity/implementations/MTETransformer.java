package gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.GTValues.V;
import static mcp.mobius.waila.api.SpecialChars.BLUE;
import static mcp.mobius.waila.api.SpecialChars.GOLD;
import static mcp.mobius.waila.api.SpecialChars.GREEN;
import static mcp.mobius.waila.api.SpecialChars.RED;
import static mcp.mobius.waila.api.SpecialChars.RESET;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.GTMod;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.util.GTUtility;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor Extend this class to make a simple
 * Machine
 */
@IMTE.SkipGenerateDescription
public class MTETransformer extends MTETieredMachineBlock {

    public MTETransformer(int aID, String aName, String aNameRegional, int aTier, IMTE[] mteArray) {
        super(aID, aName, aNameRegional, aTier, 0, (String) null, mteArray);
    }

    public MTETransformer(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[12][17][];
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_4A[mTier + 1] };
            rTextures[1][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_4A[mTier + 1] };
            rTextures[2][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI_4A[mTier + 1] };
            rTextures[3][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN[mTier + 2] };
            rTextures[4][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN[mTier + 2] };
            rTextures[5][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN[mTier + 2] };
            rTextures[6][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_4A[mTier + 1] };
            rTextures[7][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_4A[mTier + 1] };
            rTextures[8][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_4A[mTier + 1] };
            rTextures[9][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier + 2] };
            rTextures[10][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier + 2] };
            rTextures[11][i + 1] = new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1],
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier + 2] };
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facingDirection, int colorIndex,
        boolean active, boolean redstoneLevel) {
        return mTextures[Math.min(2, side.ordinal()) + (side == facingDirection ? 3 : 0)
            + (gte.isAllowedToWork() ? 0 : 6)][colorIndex + 1];
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTETransformer(name, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isEnetOutput() {
        return true;
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        ForgeDirection blockFrontFacing = getGTE().getFrontFacing();

        if (getGTE().isAllowedToWork()) {
            return side == blockFrontFacing;
        } else {
            return side != blockFrontFacing;
        }
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        return !isInputFacing(side);
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public long getMinimumStoredEU() {
        return V[mTier + 1];
    }

    @Override
    public long maxEUStore() {
        return Math.max(512L, 1L << (mTier + 2)) + V[mTier + 1] * 4L;
    }

    @Override
    public long maxEUInput() {
        return V[getGTE().isAllowedToWork() ? mTier + 1 : mTier];
    }

    @Override
    public long maxEUOutput() {
        return V[getGTE().isAllowedToWork() ? mTier : mTier + 1];
    }

    @Override
    public long maxAmperesOut() {
        return getGTE().isAllowedToWork() ? 4 : 1;
    }

    @Override
    public long maxAmperesIn() {
        return getGTE().isAllowedToWork() ? 2 : 5;
    }

    public long displayedAmperesIn() {
        return getGTE().isAllowedToWork() ? 1 : 4;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        //
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        //
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean hasAlternativeModeText() {
        return true;
    }

    @Override
    public String getAlternativeModeText() {
        return (getGTE().isAllowedToWork() ? GTUtility.trans("145", "Step Down, In: ")
            : GTUtility.trans("146", "Step Up, In: ")) + maxEUInput()
            + GTUtility.trans("148", "V ")
            + maxAmperesIn()
            + GTUtility.trans("147", "A, Out: ")
            + maxEUOutput()
            + GTUtility.trans("148", "V ")
            + maxAmperesOut()
            + GTUtility.trans("149", "A");
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        final ForgeDirection facing = getGTE().getFrontFacing();
        final NBTTagCompound tag = accessor.getNBTData();
        final ForgeDirection side = accessor.getSide();
        final boolean allowedToWork = tag.getBoolean("isAllowedToWork");

        final byte inputTier = GTUtility.getTier(tag.getLong("maxEUInput"));
        final byte outputTier = GTUtility.getTier(tag.getLong("maxEUOutput"));

        list.add(
            String.format(
                "%s %s(%dA) -> %s(%dA)",
                (allowedToWork ? (GREEN + StatCollector.translateToLocal("GT5U.waila.transformer.step_down"))
                    : (RED + StatCollector.translateToLocal("GT5U.waila.transformer.step_up"))) + RESET,
                GTMod.proxy.mWailaTransformerVoltageTier ? GTUtility.getColoredTierNameFromTier(inputTier)
                    : tag.getLong("maxEUInput"),
                tag.getLong("displayedAmperesIn"),
                GTMod.proxy.mWailaTransformerVoltageTier ? GTUtility.getColoredTierNameFromTier(outputTier)
                    : tag.getLong("maxEUOutput"),
                tag.getLong("maxAmperesOut")));

        if ((side == facing && allowedToWork) || (side != facing && !allowedToWork)) {
            list.add(
                GOLD + StatCollector.translateToLocalFormatted(
                    "GT5U.waila.transformer.input",
                    GTMod.proxy.mWailaTransformerVoltageTier ? GTUtility.getColoredTierNameFromTier(inputTier)
                        : tag.getLong("maxEUInput"),
                    tag.getLong("maxAmperesIn")));
        } else {
            list.add(
                BLUE + StatCollector.translateToLocalFormatted(
                    "GT5U.waila.transformer.output",
                    GTMod.proxy.mWailaTransformerVoltageTier ? GTUtility.getColoredTierNameFromTier(outputTier)
                        : tag.getLong("maxEUOutput"),
                    tag.getLong("maxAmperesOut")));
        }

        super.getWailaBody(itemStack, list, accessor, config);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setBoolean("isAllowedToWork", getGTE().isAllowedToWork());
        tag.setLong("maxEUInput", maxEUInput());
        tag.setLong("maxAmperesIn", maxAmperesIn());
        tag.setLong("displayedAmperesIn", displayedAmperesIn());
        tag.setLong("maxEUOutput", maxEUOutput());
        tag.setLong("maxAmperesOut", maxAmperesOut());
    }

    @Override
    public String[] getDescription() {
        return new String[] { StatCollector.translateToLocalFormatted(
            "gt.blockmachines.transformer.desc",
            GTUtility.getColoredTierNameFromVoltage(maxEUInput()) + EnumChatFormatting.GRAY,
            GTUtility.getColoredTierNameFromVoltage(maxEUOutput()) + EnumChatFormatting.GRAY) };
    }
}
