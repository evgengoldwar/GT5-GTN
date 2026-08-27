package gregtech.loaders.oreprocessing;

import static goodgenerator.util.NaquadahRecipeOutputs.convert;
import static gregtech.api.enums.GTValues.RA;
import static gregtech.api.recipe.RecipeMaps.chemicalBathRecipes;
import static gregtech.api.recipe.RecipeMaps.hammerRecipes;
import static gregtech.api.recipe.RecipeMaps.maceratorRecipes;
import static gregtech.api.recipe.RecipeMaps.oreWasherRecipes;
import static gregtech.api.recipe.RecipeMaps.thermalCentrifugeRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class ProcessingDirty implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingDirty() {
        OrePrefixes.clump.add(this);
        OrePrefixes.shard.add(this);
        OrePrefixes.crushed.add(this);
        OrePrefixes.dirtyGravel.add(this);
    }

    private boolean didPersulfate = false;
    private boolean didMercury = false;

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        net.minecraft.item.ItemStack aStack) {
        if (aMaterial.contains(SubTag.NO_ORE_PROCESSING)) {
            return;
        }

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(1, aStack))
            .itemOutputs(
                convert(aMaterial, GTOreDictUnificator.get(OrePrefixes.dustImpure, aMaterial.mMacerateInto, 1L)))
            .duration(10)
            .eut(TierEU.RECIPE_LV / 2)
            .addTo(hammerRecipes);

        OrePrefixes prefix = aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedCentrifuged : OrePrefixes.dust;

        didPersulfate = false;
        didMercury = false;

        addChemicalBathRecipes(aMaterial, aMaterial, aStack, aPrefix);

        for (Materials tMaterial : aMaterial.mOreByProducts) {
            addChemicalBathRecipes(aMaterial, tMaterial, aStack, aPrefix);
        }

    }

    private void addChemicalBathRecipes(Materials material, Materials byproduct, ItemStack stack, OrePrefixes prefix) {
        OrePrefixes chemicalBathPrefix = prefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified
            : OrePrefixes.dustPure;

        if (byproduct.contains(SubTag.WASHING_MERCURY) && !didMercury) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTUtility.copyAmount(1, stack))
                .itemOutputs(
                    convert(
                        material,
                        GTOreDictUnificator.get(chemicalBathPrefix, material, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, byproduct.mMacerateInto, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L)))
                .outputChances(10000, 7000, 4000)
                .fluidInputs(Materials.Mercury.getFluid(1_000))
                .duration(40 * SECONDS)
                .eut(TierEU.RECIPE_ULV)
                .addTo(chemicalBathRecipes);

            didMercury = true;
        }
        if (byproduct.contains(SubTag.WASHING_MERCURY_99_PERCENT) && !didMercury) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTUtility.copyAmount(1, stack))
                .itemOutputs(
                    convert(
                        material,
                        GTOreDictUnificator.get(chemicalBathPrefix, material, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, byproduct.mMacerateInto, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L)))
                .outputChances(10000, 9900, 4000)
                .fluidInputs(Materials.Mercury.getFluid(1_000))
                .duration(40 * SECONDS)
                .eut(TierEU.RECIPE_ULV)
                .addTo(chemicalBathRecipes);

            didMercury = true;
        }
        if (byproduct.contains(SubTag.WASHING_SODIUMPERSULFATE) && !didPersulfate) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTUtility.copyAmount(1, stack))
                .itemOutputs(
                    convert(
                        material,
                        GTOreDictUnificator.get(chemicalBathPrefix, material, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, byproduct.mMacerateInto, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L)))
                .outputChances(10000, 7000, 4000)
                .fluidInputs(Materials.SodiumPersulfate.getFluid(100L))
                .duration(40 * SECONDS)
                .eut(TierEU.RECIPE_ULV)
                .addTo(chemicalBathRecipes);

            didPersulfate = true;
        }
    }
}
