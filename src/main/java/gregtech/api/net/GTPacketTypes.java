package gregtech.api.net;

import java.util.Arrays;

import gregtech.common.networkanalyzer.net.GTPacketNetworkAnalyzer;
import gregtech.common.networkanalyzer.net.GTPacketNetworkAnalyzerMode;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * Centralized place to keep all the GT packet ID constants
 */
public enum GTPacketTypes {

    // ID 0 unused
    SOUND(1, new GTPacketSound()),
    BLOCK_EVENT(2, new GTPacketBlockEvent()),
    CLIENT_PREFERENCE(9, new GTPacketClientPreference()),
    SET_CONFIGURATION_CIRCUIT(12, new GTPacketSetConfigurationCircuit()),
    UPDATE_ITEM(13, new GTPacketUpdateItem()),
    SEND_COVER_DATA(16, new GTPacketSendCoverData()),
    REQUEST_COVER_DATA(17, new GTPacketRequestCoverData()),
    // ID 20 unused
    // ID 28 unused
    NODE_INFO(29, new GTPacketNodeInfo()),
    COIL_STATUS(30, new GTCoilStatus()),
    PLAYER_TELEPORT(31, new PacketTeleportPlayer()),
    SEND_WIRELESS_EU(33, new GTPacketUpdatePowerGoggles()),
    OVERWRITE_POWER_GOGGLES_MEASUREMENTS(34, new GTPacketOverwritePowerGogglesMeasurements()),
    UPDATE_POWERFAILS(36, new GTPacketUpdatePowerfails()),
    ON_POWERFAIL(37, new GTPacketOnPowerfail()),
    CLEAR_POWERFAIL(38, new GTPacketClearPowerfail()),
    CHAT(39, new GTPacketChat()),
    NETWORK_ANALYZER(44, new GTPacketNetworkAnalyzer()),
    NETWORK_ANALYZER_MODE(45, new GTPacketNetworkAnalyzerMode()),
    SYNC_TILE_RENDER_DATA_TO_CLIENT(46, new GTPacketClientMTERendererData()),

    // merge conflict prevention comment, keep a trailing comma above
    ;

    static {
        // Validate no duplicate IDs
        final GTPacketTypes[] types = values();
        final Int2ObjectOpenHashMap<GTPacket> foundIds = new Int2ObjectOpenHashMap<>(types.length);
        for (GTPacketTypes type : types) {
            final GTPacket previous = foundIds.get(type.id);
            if (previous != null) {
                throw new IllegalStateException(
                    "Duplicate packet IDs defined: " + type.id
                        + " for "
                        + type.getClass()
                        + " and "
                        + previous.getClass());
            }
            foundIds.put(type.id, type.referencePacket);
        }
    }

    public final byte id;
    public final GTPacket referencePacket;

    GTPacketTypes(int id, GTPacket referencePacket) {
        if (((int) (byte) id) != id) {
            throw new IllegalArgumentException("Value outside of byte normal range: " + id);
        }
        this.id = (byte) id;
        this.referencePacket = referencePacket;
    }

    public static GTPacket[] referencePackets() {
        return Arrays.stream(values())
            .map(p -> p.referencePacket)
            .toArray(GTPacket[]::new);
    }
}
