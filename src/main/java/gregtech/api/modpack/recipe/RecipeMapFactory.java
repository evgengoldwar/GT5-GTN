package gregtech.api.modpack.recipe;

import gregtech.api.modpack.util.Lang;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;

/**
 * Short cuts for recipe maps. A recipe map is the list of recipes of one machine type, it also creates the NEI tab.
 *
 * <pre>{@code
 * public static RecipeMap<RecipeMapBackend> CRUSHER;
 *
 * // in registerRecipeMaps(): 1 item in, 2 items out, no fluids
 * CRUSHER = RecipeMapFactory.simple("modpack.recipe.crusher", "Crusher", 1, 2, 0, 0);
 *
 * // full control with the GregTech builder
 * MIXER = RecipeMapFactory.builder("modpack.recipe.mixer", "Mixer")
 *     .maxIO(6, 1, 2, 1)
 *     .minInputs(1, 0)
 *     .progressBar(GTUITextures.PROGRESSBAR_ARROW)
 *     .build();
 *
 * // recipes (in registerRecipes)
 * GTValues.RA.stdBuilder()
 *     .itemInputs(Materials.Iron.getIngot(1))
 *     .itemOutputs(Materials.Iron.getDust(1), Materials.Iron.getStack(MaterialType.TINY_DUST, 2))
 *     .outputChances(10000, 5000)            // 100%, 50%
 *     .duration(200)                          // ticks
 *     .eut(30)                                // EU/t
 *     .addTo(CRUSHER);
 * }</pre>
 */
public final class RecipeMapFactory {

    private RecipeMapFactory() {}

    /**
     * @param id        unique id, also the NEI translation key
     * @param localName NEI tab title
     */
    public static RecipeMapBuilder<RecipeMapBackend> builder(String id, String localName) {
        Lang.add(id, localName);
        return RecipeMapBuilder.of(id);
    }

    public static RecipeMap<RecipeMapBackend> simple(String id, String localName, int itemInputs, int itemOutputs,
        int fluidInputs, int fluidOutputs) {
        return builder(id, localName).maxIO(itemInputs, itemOutputs, fluidInputs, fluidOutputs)
            .build();
    }
}
