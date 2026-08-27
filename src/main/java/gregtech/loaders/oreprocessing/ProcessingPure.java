package gregtech.loaders.oreprocessing;

import static goodgenerator.util.NaquadahRecipeOutputs.convert;
import static gregtech.api.recipe.RecipeMaps.hammerRecipes;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class ProcessingPure implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingPure() {
        OrePrefixes.crushedPurified.add(this);
        OrePrefixes.cleanGravel.add(this);
        OrePrefixes.reduced.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        ItemStack aStack) {
        if (aMaterial.contains(SubTag.NO_ORE_PROCESSING)) {
            return;
        }

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(1, aStack))
            .itemOutputs(convert(aMaterial, GTOreDictUnificator.get(OrePrefixes.dustPure, aMaterial.mMacerateInto, 1L)))
            .duration(10)
            .eut(TierEU.RECIPE_LV / 2)
            .addTo(hammerRecipes);
    }
}
