package gregtech.common.misc.spaceprojects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import net.minecraft.server.MinecraftServer;

import com.mojang.authlib.GameProfile;

@Deprecated
public class SpaceProjectManager {

    public static Map<UUID, UUID> spaceTeams = new HashMap<>();

    public static void putInTeam(UUID teamMember, UUID teamLeader) {
        if (teamMember.equals(teamLeader)) {
            spaceTeams.put(teamMember, teamLeader);
        } else if (!spaceTeams.get(teamLeader)
            .equals(teamLeader)) {
                putInTeam(teamMember, spaceTeams.get(teamLeader));
            } else {
                spaceTeams.put(teamMember, teamLeader);
            }

        if (SpaceProjectWorldSavedData.INSTANCE != null) {
            SpaceProjectWorldSavedData.INSTANCE.markDirty();
        }
    }

    public static UUID getLeader(UUID teamMember) {
        checkOrCreateTeam(teamMember);
        return spaceTeams.get(teamMember);
    }

    public static void checkOrCreateTeam(UUID teamMember) {
        if (spaceTeams.containsKey(teamMember)) {
            return;
        }

        spaceTeams.put(teamMember, teamMember);
        if (SpaceProjectWorldSavedData.INSTANCE != null) {
            SpaceProjectWorldSavedData.INSTANCE.markDirty();
        }
    }

    public static boolean isInTeam(UUID member) {
        return spaceTeams.containsKey(member);
    }

    public static HashSet<UUID> getTeamMembers(UUID leader) {
        HashSet<UUID> inTeam = new HashSet<>();

        SpaceProjectManager.spaceTeams.forEach((key, value) -> { if (Objects.equals(leader, value)) inTeam.add(key); });

        return inTeam;
    }

    public static UUID getPlayerUUIDFromName(String playerName) {
        return MinecraftServer.getServer()
            .func_152358_ax()
            .func_152655_a(playerName)
            .getId();
    }

    public static String getPlayerNameFromUUID(UUID playerUUID) {
        GameProfile profile = MinecraftServer.getServer()
            .func_152358_ax()
            .func_152652_a(playerUUID);
        return profile == null ? "ERROR_NULL_PROFILE" : profile.getName();
    }
}
