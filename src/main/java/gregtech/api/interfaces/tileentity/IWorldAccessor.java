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

    int getMetaIDOffset(int x, int y, int z);

    int getMetaIDAtSide(ForgeDirection side);

    int getMetaIDAtSideAndDistance(ForgeDirection side, int distance);

    byte getLightLevel(int x, int y, int z);

    byte getLightLevelOffset(int x, int y, int z);

    byte getLightLevelAtSide(ForgeDirection side);

    byte getLightLevelAtSideAndDistance(ForgeDirection side, int distance);

    boolean getOpacity(int x, int y, int z);

    boolean getOpacityOffset(int x, int y, int z);

    boolean getOpacityAtSide(ForgeDirection side);

    boolean getOpacityAtSideAndDistance(ForgeDirection side, int distance);

    boolean getSky(int x, int y, int z);

    boolean getSkyOffset(int x, int y, int z);

    boolean getSkyAtSide(ForgeDirection side);

    boolean getSkyAtSideAndDistance(ForgeDirection side, int distance);

    boolean getAir(int x, int y, int z);

    boolean getAirOffset(int x, int y, int z);

    boolean getAirAtSide(ForgeDirection side);

    boolean getAirAtSideAndDistance(ForgeDirection side, int distance);

    BiomeGenBase getBiome();

    BiomeGenBase getBiome(int x, int z);

    int getOffsetX(ForgeDirection side, int multiplier);

    short getOffsetY(ForgeDirection side, int multiplier);

    int getOffsetZ(ForgeDirection side, int multiplier);

    boolean isDead();

    void sendBlockEvent(byte aID, byte aValue);

    long getTimer();

    void setLightValue(byte aLightValue);

    void writeToNBT(NBTTagCompound aNBT);

    void readFromNBT(NBTTagCompound aNBT);

    boolean isInvalidTileEntity();
}
