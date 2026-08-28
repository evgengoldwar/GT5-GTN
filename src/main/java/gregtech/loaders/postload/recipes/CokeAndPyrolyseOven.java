package gregtech.loaders.postload.recipes;

import static gregtech.api.recipe.RecipeMaps.industrialCokeOvenRecipes;
import static gregtech.api.recipe.RecipeMaps.pyrolyseRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;

public class CokeAndPyrolyseOven {

    public static void postInit() {
        // Wood to Charcoal
        // Try use all woods found

        // Coal to Coke

        // Cactus and Sugar charcoal/coke, copied over from the ICO and adjusted for pyrolyse (*20 input and output,
        // duration*1.25 per item.)

        // Coke & Coal

    }

    private static void addCokingRecipes(ItemStack plant, ItemStack charcoal, ItemStack coke) {
        // Plant to Charcoal
        GTValues.RA.stdBuilder()
            .itemInputs(plant)
            .circuit(3)
            .itemOutputs(charcoal)
            .fluidOutputs(Materials.Creosote.getFluid(2_000))
            .eut(TierEU.RECIPE_LV / 2)
            .duration(25 * SECONDS)
            .addTo(pyrolyseRecipes, industrialCokeOvenRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(plant)
            .circuit(4)
            .itemOutputs(charcoal)
            .fluidInputs(Materials.Nitrogen.getGas(2_000))
            .fluidOutputs(Materials.CharcoalByproducts.getGas(4_000))
            .eut(TierEU.RECIPE_LV / 2)
            .duration(250 * TICKS)
            .addTo(pyrolyseRecipes, industrialCokeOvenRecipes);

        // Charcoal to Coke
        GTValues.RA.stdBuilder()
            .itemInputs(charcoal)
            .circuit(3)
            .itemOutputs(coke)
            .fluidOutputs(Materials.Creosote.getFluid(4_000))
            .eut(TierEU.RECIPE_LV / 2)
            .duration(50 * SECONDS)
            .addTo(pyrolyseRecipes, industrialCokeOvenRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(charcoal)
            .circuit(4)
            .itemOutputs(coke)
            .fluidInputs(Materials.Nitrogen.getGas(1_000))
            .fluidOutputs(Materials.CharcoalByproducts.getGas(2_000))
            .eut(TierEU.RECIPE_LV / 2)
            .duration(25 * SECONDS)
            .addTo(pyrolyseRecipes, industrialCokeOvenRecipes);

        // Coke to Wood tar/Wood gas
        GTValues.RA.stdBuilder()
            .itemInputs(coke)
            .circuit(5)
            .itemOutputs(Materials.Ash.getDust(5))
            .fluidInputs(Materials.Steam.getGas(2_000))
            .fluidOutputs(Materials.WoodTar.getFluid(4_000))
            .eut(TierEU.RECIPE_HV / 2)
            .duration(75 * SECONDS)
            .addTo(pyrolyseRecipes, industrialCokeOvenRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(coke)
            .circuit(6)
            .itemOutputs(Materials.Ash.getDust(5))
            .fluidInputs(Materials.Steam.getGas(2_000))
            .fluidOutputs(Materials.WoodGas.getGas(6_000))
            .eut(TierEU.RECIPE_HV / 2)
            .duration(75 * SECONDS)
            .addTo(pyrolyseRecipes, industrialCokeOvenRecipes);
    }
}
