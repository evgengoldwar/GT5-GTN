package gregtech.api.modpack;

import static gregtech.GTLoggers.GT_FML_LOGGER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import gregtech.common.MaterialSystem.Api.MaterialRegistry;
import gregtech.common.MaterialSystem.Materials;

/**
 * Entry point of the modpack API.
 *
 * <pre>{@code
 * public class MyModule implements IModpackModule {
 *     public String getName() { return "my_pack"; }
 *     public void registerMaterials() { ... }
 *     public void registerMachines() { ... }
 *     public void registerRecipes() { ... }
 * }
 *
 * // in gregtech.loaders.preload.ModpackModules#register() or in your own mod constructor:
 * ModpackAPI.registerModule(new MyModule());
 * }</pre>
 *
 * @see IModpackModule for the order of the phases
 */
public final class ModpackAPI {

    private static final List<IModpackModule> MODULES = new ArrayList<>();

    private static Phase phase = Phase.NOT_STARTED;

    public enum Phase {
        NOT_STARTED,
        MATERIALS,
        CONTENT,
        INIT,
        POST_INIT,
        DONE
    }

    private ModpackAPI() {}

    /**
     * Registers a module. Must be called before GregTech pre-init starts.
     */
    public static void registerModule(IModpackModule module) {
        if (phase != Phase.NOT_STARTED) {
            throw new IllegalStateException(
                "Module " + module.getName() + " registered too late, register modules before GregTech pre-init");
        }
        MODULES.add(module);
    }

    public static List<IModpackModule> getModules() {
        return Collections.unmodifiableList(MODULES);
    }

    public static Phase getPhase() {
        return phase;
    }

    /**
     * Throws if called outside of the expected loading phase. Builders use this to give a clear error message.
     */
    public static void checkPhase(Phase expected, String what) {
        if (phase != expected) {
            throw new IllegalStateException(what + " must be registered during " + expected + " phase, now: " + phase);
        }
    }

    // region Called by GTMod

    /**
     * Pre-init, first thing GregTech does: create material items/blocks and all materials.
     */
    public static void onPreInitMaterials() {
        phase = Phase.MATERIALS;
        MaterialRegistry.getInstance()
            .init();
        Materials.init();
        forEach("registerMaterials", IModpackModule::registerMaterials);
    }

    /**
     * Pre-init, after the GregTech machine blocks exist.
     */
    public static void onPreInitContent() {
        phase = Phase.CONTENT;
        forEach("registerRecipeMaps", IModpackModule::registerRecipeMaps);
        forEach("registerBlocks", IModpackModule::registerBlocks);
        forEach("registerItems", IModpackModule::registerItems);
        forEach("registerCovers", IModpackModule::registerCovers);
        forEach("registerPipes", IModpackModule::registerPipes);
        forEach("registerMachines", IModpackModule::registerMachines);
    }

    public static void onInit() {
        phase = Phase.INIT;
        forEach("init", IModpackModule::init);
    }

    public static void onPostInit() {
        phase = Phase.POST_INIT;
        forEach("registerRecipes", IModpackModule::registerRecipes);
        forEach("postInit", IModpackModule::postInit);
        phase = Phase.DONE;
    }

    // endregion

    private static void forEach(String step, Consumer<IModpackModule> action) {
        for (IModpackModule module : MODULES) {
            try {
                action.accept(module);
            } catch (RuntimeException e) {
                GT_FML_LOGGER.error("Modpack module {} failed in {}", module.getName(), step, e);
                throw e;
            }
        }
    }
}
