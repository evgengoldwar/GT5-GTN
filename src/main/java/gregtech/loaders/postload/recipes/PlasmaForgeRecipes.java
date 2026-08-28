package gregtech.loaders.postload.recipes;

import static gregtech.api.recipe.RecipeMaps.plasmaForgeRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.STACKS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

@SuppressWarnings({ "PointlessArithmeticExpression" })
public class PlasmaForgeRecipes implements Runnable {

    @Override
    public void run() {
        // Dimensionally transcendent plasma forge recipes.
        // Ordered so that recipes using higher tier catalysts are prioritized.

        {
            // Dimensionally Shifted Superfluid

            // Tier 5
            // Best recipe, unlocks with Stellar Catalyst.
            // Quadruples the cost of everything except for Metastable and Celestial, which are only doubled,
            // but gives 4x the output.

            // Tier 4
            // Better recipe, unlocks with Eternal coil.
            // Doubles the cost across the board, but outputs 3x more.
            // Switches to Heavy Radox, which can be mass-produced with the QFT.

            // Tier 3
            // Better recipe, unlocks with Hypogen coil.
            // This recipe takes UMV power but processes 4x input and output as the original recipe, making it a free
            // POC
            // over the original recipe. Only increases the cost of baryonic and water, making the ratio much cheaper.

            // Tier 2
            // First recipe using AwDr coil and super heavy radox

        }

        // Giga chad trophy.
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Field_Generator_UEV.get(64),
                ItemList.Field_Generator_UIV.get(64),
                ItemList.Field_Generator_UMV.get(64))
            .fluidInputs(Materials.ExcitedDTEC.getFluid(100_000_000), Materials.SpaceTime.getMolten(18 * STACKS))
            .itemOutputs(ItemList.GigaChad.get(1))
            .duration(86400 * 20 * 2)
            .eut(2_000_000_000)
            .metadata(COIL_HEAT, 13500)
            .addTo(plasmaForgeRecipes);

        // Quantum anomaly recipe bypass for UEV+. Avoids RNG.

        // Chipped Amalgatite
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.nanite, Materials.MagMatter, 1)),
                GTOreDictUnificator.get(OrePrefixes.round, Materials.Eternity, 64),
                GTOreDictUnificator.get(OrePrefixes.round, Materials.MagMatter, 64),
                GTOreDictUnificator.get(OrePrefixes.round, Materials.Hexanite, 64),
                GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Ruby, 64),
                GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Jasper, 64),
                GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Sapphire, 64),
                GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Opal, 64))
            .fluidInputs(Materials.StargateCrystalSlurry.getFluid(5_000), Materials.ExcitedDTSC.getFluid(72_000))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gemChipped, Materials.Amalgatite, 64))
            .fluidOutputs(Materials.DTR.getFluid(144_000))
            .duration(420 * SECONDS)
            .eut((int) TierEU.RECIPE_MAX)
            .metadata(COIL_HEAT, 13500)
            .addTo(plasmaForgeRecipes);

    }
}
