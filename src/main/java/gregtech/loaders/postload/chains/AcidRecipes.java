package gregtech.loaders.postload.chains;

import static gregtech.api.recipe.RecipeMaps.blastFurnaceRecipes;
import static gregtech.api.recipe.RecipeMaps.centrifugeNonCellRecipes;
import static gregtech.api.recipe.RecipeMaps.electrolyzerNonCellRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerNonCellRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.recipe.RecipeMaps.sifterRecipes;
import static gregtech.api.recipe.RecipeMaps.vacuumFreezerRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;
import static gregtech.api.util.GTRecipeConstants.UniversalChemical;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;

public class AcidRecipes {

    public static void run() {
        // Francium Line
        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.ThoriumElutionAdsorbent.getFluid(8000L))
            .itemOutputs(Materials.Chrome.getDust(4))
            .outputChances(4500)
            .fluidOutputs(Materials.ImpureFranciumSolution.getFluid(4500L), Materials.Ammonia.getGas(3500L))
            .eut(TierEU.RECIPE_LuV)
            .duration(30 * SECONDS)
            .addTo(electrolyzerNonCellRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SiliconDioxide.getDust(4), Materials.Barite.getDust(1))
            .fluidInputs(Materials.ImpureFranciumSolution.getFluid(5000L))
            .fluidOutputs(Materials.FranciumSlurry.getFluid(4000L))
            .eut(600)
            .duration(15 * SECONDS)
            .addTo(mixerRecipes);
        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.FranciumSlurry.getFluid(4000L))
            .itemOutputs(Materials.Francium.getDust(1), Materials.Francium.getDust(1), Materials.Lead.getDust(1))
            .outputChances(9000, 8000, 6500)
            .eut(TierEU.RECIPE_HV)
            .duration(5 * SECONDS)
            .addTo(sifterRecipes);
        // Fr + H2O = FrOH + H
        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Francium.getDust(1))
            .circuit(1)
            .itemOutputs(Materials.FranciumHydroxide.getDust(3))
            .fluidInputs(Materials.Water.getFluid(1000L))
            .fluidOutputs(Materials.Hydrogen.getGas(1000L))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);
        // Chlorosulfonic Acid Line
        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PhosphorousPentoxide.getDust(7))
            .fluidOutputs(Materials.PhosphorusChlorineMixture.getFluid(2000L), Materials.Water.getFluid(5000L))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);
        GTValues.RA.stdBuilder()
            .fluidOutputs(Materials.ActivatedWasteWater.getFluid(2000L), Materials.Chlorine.getGas(8000L))
            .circuit(1)// Necessary to exceed properties.minItemInputs threshold
            .eut(1440)
            .duration(10 * SECONDS)
            .addTo(mixerNonCellRecipes);
        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.ToxicAir.getGas(3000L), Materials.Ozone.getGas(10000L))
            .fluidOutputs(Materials.ToxicSlurry.getFluid(400L))
            .eut(TierEU.RECIPE_EV)
            .duration(5 * SECONDS)
            .addTo(vacuumFreezerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Quantium, 6))
            .fluidInputs(Materials.ToxicSlurry.getFluid(3000L))
            .fluidOutputs(Materials.DestabilizationSlurry.getFluid(2500L))
            .eut(TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(UniversalChemical);
        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.ToxicSlurry.getFluid(3000L))
            .fluidOutputs(
                Materials.TerephthalicAcid.getFluid(2500L),
                Materials.CarbonDioxide.getGas(2000L),
                Materials.Ammonia.getGas(1800L))
            .eut(TierEU.RECIPE_HV)
            .duration(15 * SECONDS)
            .addTo(centrifugeNonCellRecipes);
        GTValues.RA.stdBuilder()
            .fluidInputs(
                Materials.PhosphorusPentachloride.getFluid(5000L),
                Materials.SulfuricAcid.getFluid(5000L),
                Materials.UltraContaminatedGas.getGas(1000L),
                Materials.DestabilizationSlurry.getFluid(1000L))
            .fluidOutputs(
                Materials.ChlorosulfonicAcid.getFluid(5000L),
                Materials.HydrochloricAcid.getFluid(10000L),
                Materials.PhosphoricAcid.getFluid(5000L),
                Materials.AgitatingSlurry.getFluid(1000L))
            .eut(TierEU.RECIPE_IV)
            .duration(5 * SECONDS)
            .addTo(multiblockChemicalReactorRecipes);
        if (Mods.UniversalSingularities.isModLoaded()) {
            GTValues.RA.stdBuilder()
                .itemInputs(getModItem(Mods.UniversalSingularities.ID, "universal.toxic.singularity", 0))
                .fluidInputs(Materials.AgitatingSlurry.getFluid(10000L), Materials.Ozone.getGas(2000L))
                .fluidOutputs(Materials.DestabilizationSlurry.getFluid(7000L))
                .eut(TierEU.RECIPE_EV)
                .duration(10 * SECONDS)
                .addTo(multiblockChemicalReactorRecipes);
        }
        if (Mods.TwilightForest.isModLoaded()) {
            GTValues.RA.stdBuilder()
                .itemInputs(getModItem(Mods.TwilightForest.ID, "tile.TFSapling", 4))
                .fluidInputs(Materials.SulfuricAcid.getFluid(2000L))
                .fluidOutputs(Materials.PoisonousSlurry.getFluid(2500L))
                .eut(TierEU.RECIPE_EV)
                .duration(10 * SECONDS)
                .metadata(COIL_HEAT, 3050)
                .addTo(blastFurnaceRecipes);
        }

    }
}
