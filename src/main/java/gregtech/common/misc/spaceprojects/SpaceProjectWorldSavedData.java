package gregtech.common.misc.spaceprojects;

import static gregtech.common.misc.spaceprojects.SpaceProjectManager.spaceTeams;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.world.WorldEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Deprecated
public class SpaceProjectWorldSavedData extends WorldSavedData {

    public static SpaceProjectWorldSavedData INSTANCE;

    private static final Gson GSON_TEAMS = new GsonBuilder().serializeNulls()
        .registerTypeAdapter(spaceTeams.getClass(), new SpaceTeamAdapter())
        .create();

    private static final String DATA_NAME = "GT_SpaceProjectData";

    private static final String SPACE_TEAMS_JSON = "spaceTeams.json";

    private static final String SPACE_TEAMS_NBT = "spaceTeams";

    private static File spaceTeamsFile;

    public SpaceProjectWorldSavedData() {
        super(DATA_NAME);
    }

    public SpaceProjectWorldSavedData(String aData) {
        super(aData);
    }

    @Override
    public void readFromNBT(NBTTagCompound aNBT) {
        if (!aNBT.hasKey(SPACE_TEAMS_NBT)) {
            try (JsonReader reader = new JsonReader(new FileReader(spaceTeamsFile))) {
                HashMap<UUID, UUID> jsonMap = GSON_TEAMS.fromJson(reader, spaceTeams.getClass());
                for (UUID member : jsonMap.keySet()) {
                    spaceTeams.put(member, jsonMap.get(member));
                }
            } catch (Exception e) {
                spaceTeams = null;
            }
        }

        if (spaceTeams == null) {
            spaceTeams = new HashMap<>();
        }

        if (aNBT.hasKey(SPACE_TEAMS_NBT)) {
            spaceTeams = GSON_TEAMS.fromJson(aNBT.getString(SPACE_TEAMS_NBT), spaceTeams.getClass());
        }

        if (spaceTeams == null) {
            spaceTeams = new HashMap<>();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound aNBT) {
        if (spaceTeams != null) {
            aNBT.setString(SPACE_TEAMS_NBT, GSON_TEAMS.toJson(spaceTeams));
        }
    }

    private static void loadInstance(World aWorld) {
        if (spaceTeams != null) {
            spaceTeams.clear();
        } else {
            spaceTeams = new HashMap<>();
        }
        spaceTeamsFile = new File(
            aWorld.getSaveHandler()
                .getWorldDirectory(),
            SPACE_TEAMS_JSON);
        MapStorage tStorage = aWorld.mapStorage;
        INSTANCE = (SpaceProjectWorldSavedData) tStorage.loadData(SpaceProjectWorldSavedData.class, DATA_NAME);
        if (INSTANCE == null) {
            INSTANCE = new SpaceProjectWorldSavedData();
            tStorage.setData(DATA_NAME, INSTANCE);
        }
        INSTANCE.markDirty();
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load aEvent) {
        if (!aEvent.world.isRemote && aEvent.world.provider.dimensionId == 0) {
            loadInstance(aEvent.world);
        }
    }

    private static class SpaceTeamAdapter
        implements JsonSerializer<HashMap<UUID, UUID>>, JsonDeserializer<HashMap<UUID, UUID>> {

        @Override
        public JsonElement serialize(HashMap<UUID, UUID> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray map = new JsonArray();
            for (Entry<UUID, UUID> entry : src.entrySet()) {
                if (entry.getKey() == null) continue;
                JsonObject teamMap = new JsonObject();

                teamMap.addProperty(
                    "MEMBER_UUID",
                    entry.getKey()
                        .toString());
                teamMap.addProperty(
                    "LEADER_UUID",
                    entry.getValue()
                        .toString());

                map.add(teamMap);
            }

            return map;
        }

        @Override
        public HashMap<UUID, UUID> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
            HashMap<UUID, UUID> map = new HashMap<>();
            JsonArray jsonArray = json.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                UUID memberUuid = UUID.fromString(
                    jsonObject.get("MEMBER_UUID")
                        .getAsString());
                UUID leaderUuid = UUID.fromString(
                    jsonObject.get("LEADER_UUID")
                        .getAsString());
                map.put(memberUuid, leaderUuid);
            }
            return map;
        }
    }
}
