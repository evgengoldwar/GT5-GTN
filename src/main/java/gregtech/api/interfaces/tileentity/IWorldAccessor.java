package gregtech.api.interfaces.tileentity;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.metatileentity.MetaTileEntity;

/**
 * This is a bunch of Functions my TileEntities provide, to make life much easier, and to get rid of internal TileEntity
 * stuff.
 * <p/>
 * This also makes access to adjacent TileEntities more Efficient.
 * <p/>
 * Note: It doesn't have to be a TileEntity in certain cases! And only certain cases, such as the Recipe checking of the
 * findRecipe Function.
 */
public interface IWorldAccessor {

    World getWorld();

    int getXCord();

    short getYCord();

    int getZCord();

    default ChunkCoordinates getCords() {
        return new ChunkCoordinates(getXCord(), getYCord(), getZCord());
    }

    boolean isServerSide();

    boolean isClientSide();

    int getRandomNumber(int range);

    TileEntity getTileEntity(int aX, int aY, int aZ);

    default TileEntity getTileEntity(BlockPos pos) {
        return getTileEntity(pos.getX(), pos.getY(), pos.getZ());
    }

    default <MTE extends MetaTileEntity> MTE getMetaTileEntity(BlockPos pos, Class<MTE> clazz) {
        if (!(getTileEntity(pos) instanceof IGTE igte)) return null;

        IMTE mte = igte.getMetaTileEntity();

        if (!clazz.isInstance(mte)) return null;

        // noinspection unchecked
        return (MTE) mte;
    }

    TileEntity getTileEntityOffset(int x, int y, int z);

    TileEntity getTileEntityAtSide(ForgeDirection side);

    TileEntity getTileEntityAtSideAndDistance(ForgeDirection side, int distance);

    IInventory getIInventory(int x, int y, int z);

    IInventory getIInventoryOffset(int x, int y, int z);

    IInventory getIInventoryAtSide(ForgeDirection side);

    IInventory getIInventoryAtSideAndDistance(ForgeDirection side, int distance);

    IFluidHandler getITankContainer(int x, int y, int z);

    IFluidHandler getITankContainerOffset(int x, int y, int z);

    IFluidHandler getITankContainerAtSide(ForgeDirection side);

    IFluidHandler getITankContainerAtSideAndDistance(ForgeDirection side, int distance);

    IGTE getIGregTechTileEntity(int x, int y, int z);

    IGTE getIGregTechTileEntityOffset(int x, int y, int z);

    IGTE getIGregTechTileEntityAtSide(ForgeDirection side);

    IGTE getIGregTechTileEntityAtSideAndDistance(ForgeDirection side, int distance);

    Block getBlock(int x, int y, int z);

    Block getBlockOffset(int x, int y, int z);

    Block getBlockAtSide(ForgeDirection side);

    Block getBlockAtSideAndDistance(ForgeDirection side, int distance);

    int getMetaID(int x, int y, int z);

    int getMetaIDOffset(int aX, int aY, int aZ);

    int getMetaIDAtSide(ForgeDirection side);

    int getMetaIDAtSideAndDistance(ForgeDirection side, int distance);

    byte getLightLevel(int aX, int aY, int aZ);

    byte getLightLevelOffset(int aX, int aY, int aZ);

    byte getLightLevelAtSide(ForgeDirection side);

    byte getLightLevelAtSideAndDistance(ForgeDirection side, int distance);

    boolean getOpacity(int aX, int aY, int aZ);

    boolean getOpacityOffset(int aX, int aY, int aZ);

    boolean getOpacityAtSide(ForgeDirection side);

    boolean getOpacityAtSideAndDistance(ForgeDirection side, int distance);

    boolean getSky(int aX, int aY, int aZ);

    boolean getSkyOffset(int aX, int aY, int aZ);

    boolean getSkyAtSide(ForgeDirection side);

    boolean getSkyAtSideAndDistance(ForgeDirection side, int distance);

    boolean getAir(int aX, int aY, int aZ);

    boolean getAirOffset(int aX, int aY, int aZ);

    boolean getAirAtSide(ForgeDirection side);

    boolean getAirAtSideAndDistance(ForgeDirection side, int distance);

    BiomeGenBase getBiome();

    BiomeGenBase getBiome(int aX, int aZ);

    int getOffsetX(ForgeDirection side, int aMultiplier);

    short getOffsetY(ForgeDirection side, int aMultiplier);

    int getOffsetZ(ForgeDirection side, int aMultiplier);

    /**
     * Checks if the TileEntity is Invalid or Unloaded. Stupid Minecraft cannot do that btw.
     */
    boolean isDead();

    /**
     * Sends a Block Event to the Client TileEntity.
     *
     * @param aValue value to sync
     */
    void sendBlockEvent(byte aID, byte aValue);

    /**
     * @return the Time this TileEntity has been loaded.
     */
    long getTimer();

    /**
     * Sets the Light Level of this Block on a Scale of 0 - 15 It could be that it doesn't work. This is just for
     * convenience.
     */
    void setLightValue(byte aLightValue);

    /**
     * Function of the regular TileEntity
     */
    void writeToNBT(NBTTagCompound aNBT);

    /**
     * Function of the regular TileEntity
     */
    void readFromNBT(NBTTagCompound aNBT);

    /**
     * Function of the regular TileEntity
     */
    boolean isInvalidTileEntity();
}
