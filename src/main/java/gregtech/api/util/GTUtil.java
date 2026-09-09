package gregtech.api.util;

import static gregtech.GTLoggers.GT_FML_LOGGER;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.gtnhlib.item.ItemStackNBT;

public class GTUtil {

    public static Tuple tuple(String key, Object value) {
        return new Tuple(key, value);
    }

    public static NBTTagCompound fuseNBT(NBTTagCompound nbt1, NBTTagCompound nbt2) {
        if (nbt1 == null) return nbt2 == null ? new NBTTagCompound() : (NBTTagCompound) nbt2.copy();
        final NBTTagCompound rNBT = (NBTTagCompound) nbt1.copy();
        if (nbt2 == null) return rNBT;
        for (Object tKey : nbt2.func_150296_c /* getKeySet */())
            if (!rNBT.hasKey(tKey.toString())) rNBT.setTag(tKey.toString(), nbt2.getTag(tKey.toString()));
        return rNBT;
    }

    /**
     * Construct a NBTTagCompound from a series of key, value pairs. Inspired from GT6.
     */
    public static NBTTagCompound makeNBT(Tuple... tags) {
        final NBTTagCompound nbt = new NBTTagCompound();
        for (Tuple t : tags) {
            switch (t.getSecond()) {
                case null -> {
                    continue;
                }
                case Boolean b -> nbt.setBoolean(
                    t.getFirst()
                        .toString(),
                    b);
                case Byte b -> nbt.setByte(
                    t.getFirst()
                        .toString(),
                    b);
                case Short i -> nbt.setShort(
                    t.getFirst()
                        .toString(),
                    i);
                case Integer i -> nbt.setInteger(
                    t.getFirst()
                        .toString(),
                    i);
                case Long l -> nbt.setLong(
                    t.getFirst()
                        .toString(),
                    l);
                case Float v -> nbt.setFloat(
                    t.getFirst()
                        .toString(),
                    v);
                case Double v -> nbt.setDouble(
                    t.getFirst()
                        .toString(),
                    v);
                case String s -> nbt.setString(
                    t.getFirst()
                        .toString(),
                    s);
                case NBTBase nbtBase -> nbt.setTag(
                    t.getFirst()
                        .toString(),
                    nbtBase);
                default -> nbt.setString(
                    t.getFirst()
                        .toString(),
                    t.getSecond()
                        .toString());
            }

        }

        return nbt;
    }

    /**
     * Get a TileEntity
     */
    public static TileEntity getTileEntity(World world, int x, int y, int z, boolean aLoadUnloadedChunks) {
        if (aLoadUnloadedChunks || world.blockExists(x, y, z)) {
            return world.getTileEntity(x, y, z);
        }
        return null;
    }

    /**
     * Sets the TileEntity at the passed position, with the option of turning adjacent TileEntity updates off.
     */
    public static TileEntity setTileEntity(World world, int x, int y, int z, TileEntity aTileEntity,
        boolean aCauseTileEntityUpdates) {
        if (aCauseTileEntityUpdates) world.setTileEntity(x, y, z, aTileEntity);
        else {
            final Chunk tChunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
            if (tChunk != null) {
                world.addTileEntity(aTileEntity);
                tChunk.func_150812_a /* setBlockTileEntityInChunk */(x & 15, y, z & 15, aTileEntity);
                tChunk.setChunkModified();
            }
        }
        return aTileEntity;
    }

    public static boolean setBlock(World world, int x, int y, int z, Block block, short aMeta, long aFlags,
        boolean aRemoveGrassBelow) {
        if (aRemoveGrassBelow) {
            final Block blockBelow = world.getBlock(x, y - 1, z);
            if (blockBelow == Blocks.grass || blockBelow == Blocks.mycelium)
                world.setBlock(x, y - 1, z, Blocks.dirt, 0, (byte) aFlags);
        }
        return world.setBlock(x, y, z, block, aMeta, (byte) aFlags);
    }

    public static TileEntity getTileEntity(World world, ChunkCoordinates coords, boolean loadUnloadedChunks) {
        return getTileEntity(world, coords.posX, coords.posY, coords.posZ, loadUnloadedChunks);
    }

    /**
     * Marks a Chunk dirty so it is saved
     */
    public static boolean markChunkDirty(World world, int x, int z) {
        if (world == null || world.isRemote) return false;
        Chunk aChunk = world.getChunkFromBlockCoords(x, z);
        if (aChunk == null) {
            world.getBlockMetadata(x, 0, z);
            aChunk = world.getChunkFromBlockCoords(x, z);
            if (aChunk == null) {
                GT_FML_LOGGER
                    .error("Some important Chunk does not exist for some reason at Coordinates X: {} and Z: {}", x, z);
                return false;
            }
        }
        aChunk.setChunkModified();
        return true;
    }

    /**
     * Marks a Chunk dirty so it is saved
     */
    public static boolean markChunkDirty(Object maybeTile) {
        return maybeTile instanceof TileEntity tileEntity
            && markChunkDirty(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.zCoord);
    }

    public static String toHexString(short[] aColors) {
        return aColors == null ? "FFFFFF" : Integer.toHexString((aColors[0] << 16) | (aColors[1] << 8) | aColors[2]);
    }

    public static short[] getRGBaArray(int aColors) {
        return new short[] { (short) ((aColors >>> 16) & 255), (short) ((aColors >>> 8) & 255), (short) (aColors & 255),
            (short) ((aColors >>> 24) & 255) };
    }

    public static boolean hasMultiblockInputConfiguration(ItemStack dataOrb) {
        return !GTUtility.isStackInvalid(dataOrb)
            && "MultiblockConfiguration".equals(ItemStackNBT.getString(dataOrb, "type"));
    }

    public static boolean setVanillaChestDirection(World world, int x, int y, int z, int newSide, Block currentBlock,
        boolean dryRun) {
        ForgeDirection newDirection = ForgeDirection.getOrientation(newSide);
        if (newDirection.offsetY != 0) return false; // cannot face up/down
        if (currentBlock == null) currentBlock = world.getBlock(x, y, z);
        if (world.getBlock(x + newDirection.offsetX, y + newDirection.offsetY, z + newDirection.offsetZ) == currentBlock
            || world.getBlock(x - newDirection.offsetX, y - newDirection.offsetY, z - newDirection.offsetZ)
                == currentBlock) {
            // new direction would face towards/away from another chest. not good
            return false;
        }
        if (dryRun) return true;
        ForgeDirection sideway = ForgeDirection
            .getOrientation(ForgeDirection.ROTATION_MATRIX[ForgeDirection.UP.ordinal()][newSide]);
        boolean result = true;
        if (world.getBlock(x + sideway.offsetX, y + sideway.offsetY, z + sideway.offsetZ) == currentBlock) {
            result &= world
                .setBlockMetadataWithNotify(x + sideway.offsetX, y + sideway.offsetY, z + sideway.offsetZ, newSide, 3);
        } else if (world.getBlock(x - sideway.offsetX, y - sideway.offsetY, z - sideway.offsetZ) == currentBlock) {
            result &= world
                .setBlockMetadataWithNotify(x - sideway.offsetX, y - sideway.offsetY, z - sideway.offsetZ, newSide, 3);
        }
        if (!result) {
            return false;
        }
        return world.setBlockMetadataWithNotify(x, y, z, newSide, 3);
    }
}
