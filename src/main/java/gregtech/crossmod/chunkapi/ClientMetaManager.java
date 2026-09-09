package gregtech.crossmod.chunkapi;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.falsepattern.chunk.api.DataManager;
import com.gtnewhorizon.gtnhlib.util.CoordinatePacker;

import gregtech.api.enums.Mods;
import gregtech.api.net.ClientMetaTrackerRegistry;
import gregtech.api.net.IClientMetaTracker;
import it.unimi.dsi.fastutil.longs.LongSet;

public class ClientMetaManager implements DataManager.PacketDataManager, DataManager.BlockPacketDataManager {

    @Override
    public int maxPacketSize() {
        return 16 * 16 * 256 * 10;
    }

    @Override
    public void writeToBuffer(Chunk chunk, int subChunkMask, boolean forceUpdate, ByteBuffer buffer) {
        for (IClientMetaTracker tracker : ClientMetaTrackerRegistry.getAllTrackers()) {
            World theWorld = chunk.worldObj;
            LongSet blockList = tracker.getTrackedBlocksByChunk(chunk);
            if (blockList == null) {
                buffer.putInt(0);
                continue;
            } else {
                buffer.putInt(blockList.size());
            }
            for (long packedBlockPos : blockList) {
                buffer.putLong(packedBlockPos);
                int x = CoordinatePacker.unpackX(packedBlockPos);
                int y = CoordinatePacker.unpackY(packedBlockPos);
                int z = CoordinatePacker.unpackZ(packedBlockPos);
                buffer.putShort((short) theWorld.getBlockMetadata(x, y, z));
            }
        }
    }

    @Override
    public void readFromBuffer(Chunk chunk, int subChunkMask, boolean forceUpdate, ByteBuffer buffer) {}

    @Override
    public void writeBlockToPacket(Chunk chunk, int x, int y, int z, S23PacketBlockChange packet) {}

    @Override
    public void readBlockFromPacket(Chunk chunk, int x, int y, int z, S23PacketBlockChange packet) {}

    @Override
    public void writeBlockPacketToBuffer(S23PacketBlockChange packet, PacketBuffer buffer) throws IOException {}

    @Override
    public void readBlockPacketFromBuffer(S23PacketBlockChange packet, PacketBuffer buffer) throws IOException {}

    @Override
    public String domain() {
        return Mods.ModIDs.GREG_TECH;
    }

    @Override
    public String id() {
        return "clientmeta";
    }
}
