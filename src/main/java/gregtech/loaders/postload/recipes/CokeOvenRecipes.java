package gregtech.loaders.postload.recipes;

import static gregtech.api.recipe.RecipeMaps.cokeOvenRecipes;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.objects.OreDictItemStack;
import mods.railcraft.common.blocks.aesthetics.cube.EnumCube;
import mods.railcraft.common.items.RailcraftToolItems;

@SuppressWarnings("PointlessArithmeticExpression")
public class CokeOvenRecipes implements Runnable {

    @Override
    public void run() {
        if (!Mods.Railcraft.isModLoaded()) return;

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getGems(1))
            .itemOutputs(RailcraftToolItems.getCoalCoke(1))
            .fluidOutputs(Materials.Creosote.getFluid(500))
            .duration(1 * MINUTES + 30 * SECONDS)
            .eut(0)
            .addTo(cokeOvenRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getBlocks(1))
            .itemOutputs(EnumCube.COKE_BLOCK.getItem(1))
            .fluidOutputs(Materials.Creosote.getFluid(4_500))
            .duration(13 * MINUTES + 30 * SECONDS)
            .eut(0)
            .addTo(cokeOvenRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(new OreDictItemStack("logWood", 1))
            .itemOutputs(Materials.Charcoal.getGems(1))
            .fluidOutputs(Materials.Creosote.getFluid(250))
            .duration(1 * MINUTES + 30 * SECONDS)
            .eut(0)
            .addTo(cokeOvenRecipes);
    }
}
