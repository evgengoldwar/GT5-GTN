package gregtech.api.modpack;

/**
 * One piece of modpack content. Implement the phases you need and register the module with
 * {@link ModpackAPI#registerModule(IModpackModule)} before GregTech pre-init (e.g. in {@code ModpackModules}).
 * <p>
 * Order of the calls (all modules run one phase before the next phase starts):
 * <ol>
 * <li>{@link #registerMaterials()} - pre-init, materials ({@code new MaterialBuilder(...)})</li>
 * <li>{@link #registerRecipeMaps()} - pre-init, recipe maps (machines need them)</li>
 * <li>{@link #registerBlocks()} - pre-init, simple blocks</li>
 * <li>{@link #registerItems()} - pre-init, simple and complex items</li>
 * <li>{@link #registerCovers()} - pre-init, covers (cover items must exist)</li>
 * <li>{@link #registerPipes()} - pre-init, cables, fluid and item pipes</li>
 * <li>{@link #registerMachines()} - pre-init, hatches, single block machines and multiblocks</li>
 * <li>{@link #init()} - init</li>
 * <li>{@link #registerRecipes()} - post-init, machine recipes and crafting recipes</li>
 * <li>{@link #postInit()} - post-init, after all recipes</li>
 * </ol>
 */
public interface IModpackModule {

    /**
     * Name used in logs and error messages.
     */
    String getName();

    default void registerMaterials() {}

    default void registerRecipeMaps() {}

    default void registerBlocks() {}

    default void registerItems() {}

    default void registerCovers() {}

    default void registerPipes() {}

    default void registerMachines() {}

    default void init() {}

    default void registerRecipes() {}

    default void postInit() {}
}
