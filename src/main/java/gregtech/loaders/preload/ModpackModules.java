package gregtech.loaders.preload;

import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.example.ExampleModule;

/**
 * The list of modpack content modules. Add your modules here.
 * <p>
 * Run the game with {@code -Dgregtech.modpack.example=true} to load the example module that shows every part of the
 * API (materials, blocks, items, machines, multiblocks, pipes, covers and recipes).
 */
public final class ModpackModules {

    private ModpackModules() {}

    public static void register() {
        if (Boolean.getBoolean("gregtech.modpack.example")) {
            ModpackAPI.registerModule(new ExampleModule());
        }

        // ModpackAPI.registerModule(new MyModpackModule());
    }
}
