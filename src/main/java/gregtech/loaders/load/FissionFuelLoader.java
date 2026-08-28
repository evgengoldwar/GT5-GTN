package gregtech.loaders.load;

import static gregtech.api.enums.OrePrefixes.stick;
import static gregtech.api.enums.OrePrefixes.stickLong;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.cannerRecipes;
import static gregtech.api.recipe.RecipeMaps.fluidExtractionRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerRecipes;
import static gregtech.api.recipe.RecipeMaps.thermalCentrifugeRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;

public class FissionFuelLoader implements Runnable {

    @Override
    public void run() {
        // Remove IC2 recipes
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_Uranium_238.get(1));
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_Uranium_235.get(1));
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_Uranium_235_Small.get(1));
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_Plutonium.get(1));
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_Plutonium_Small.get(1));
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_Uranium_Fuel.get(1));
        GTModHandler.removeRecipeByOutputDelayed(ItemList.IC2_MOX_Fuel.get(1));

        // Lithium
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.IC2_Fuel_Rod_Empty.get(1), Materials.Lithium.getDustTiny(1))
            .itemOutputs(ItemList.RodLithium.get(1))
            .duration(16 * TICKS)
            .eut(TierEU.RECIPE_MV / 2)
            .addTo(cannerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.DepletedRodLithium.get(1))
            .itemOutputs(ItemList.IC2_Fuel_Rod_Empty.get(1))
            .fluidOutputs(Materials.Tritium.getGas(32))
            .duration(16 * TICKS)
            .eut(TierEU.RECIPE_MV / 2)
            .addTo(fluidExtractionRecipes);

        // Glowstone
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.IC2_Fuel_Rod_Empty.get(1), Materials.Glowstone.getDust(9))
            .itemOutputs(ItemList.RodGlowstone.get(1))
            .fluidInputs(Materials.Helium.getGas(250))
            .duration(1 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_LV / 2)
            .addTo(mixerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.DepletedRodGlowstone.get(1))
            .itemOutputs(Materials.Sunnarium.getDust(1), Materials.Glowstone.getDust(2), Materials.Iron.getDust(1))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(thermalCentrifugeRecipes);

        if (!Mods.NuclearHorizons.isModLoaded()) {
            // Thorium
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.IC2_Fuel_Rod_Empty.get(1), Materials.Thorium.getDust(3))
                .itemOutputs(ItemList.RodThorium.get(1L))
                .duration(1 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_LV / 2)
                .addTo(cannerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodThorium.get(2), GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4))
                .circuit(2)
                .itemOutputs(ItemList.RodThorium2.get(1))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.RodThorium.get(4),
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 6))
                .circuit(4)
                .itemOutputs(ItemList.RodThorium4.get(1))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodThorium2.get(2), GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4))
                .circuit(5)
                .itemOutputs(ItemList.RodThorium4.get(1))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodThorium.get(1))
                .itemOutputs(
                    Materials.Lutetium.getDustSmall(2),
                    Materials.Thorium.getDust(1),
                    Materials.Iron.getDust(1))
                .duration(25 * SECONDS)
                .eut(48)
                .addTo(thermalCentrifugeRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodThorium2.get(1))
                .itemOutputs(Materials.Lutetium.getDust(1), Materials.Thorium.getDust(2), Materials.Iron.getDust(3))
                .duration(25 * SECONDS)
                .eut(48)
                .addTo(thermalCentrifugeRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodThorium4.get(1))
                .itemOutputs(Materials.Lutetium.getDust(2), Materials.Thorium.getDust(4), Materials.Iron.getDust(6))
                .duration(25 * SECONDS)
                .eut(48)
                .addTo(thermalCentrifugeRecipes);

            // Uranium
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.IC2_Uranium_238.get(6), ItemList.IC2_Uranium_235_Small.get(3))
                .itemOutputs(ItemList.IC2_Uranium_Fuel.get(1))
                .fluidInputs(GTModHandler.getIC2Coolant(1_000))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.IC2_Fuel_Rod_Empty.get(1), ItemList.IC2_Uranium_Fuel.get(1))
                .itemOutputs(ItemList.RodUranium.get(1))
                .duration(1 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_LV / 2)
                .addTo(cannerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodUranium.get(2), GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4))
                .circuit(2)
                .itemOutputs(ItemList.RodUranium2.get(1))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.RodUranium.get(4),
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 6))
                .circuit(4)
                .itemOutputs(ItemList.RodUranium4.get(1))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodUranium2.get(2), GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4))
                .circuit(5)
                .itemOutputs(ItemList.RodUranium4.get(1))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodUranium.get(1))
                .itemOutputs(
                    ItemList.IC2_Plutonium_Small.get(1),
                    ItemList.IC2_Uranium_238.get(4),
                    Materials.Iron.getDust(1))
                .duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(thermalCentrifugeRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodUranium2.get(1))
                .itemOutputs(
                    ItemList.IC2_Plutonium_Small.get(2),
                    ItemList.IC2_Uranium_238.get(8),
                    Materials.Iron.getDust(3))
                .duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(thermalCentrifugeRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodUranium4.get(1))
                .itemOutputs(
                    ItemList.IC2_Plutonium_Small.get(4),
                    ItemList.IC2_Uranium_238.get(16),
                    Materials.Iron.getDust(6))
                .duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(thermalCentrifugeRecipes);

            // MOX
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.IC2_Uranium_238.get(6), ItemList.IC2_Plutonium.get(3))
                .itemOutputs(ItemList.IC2_MOX_Fuel.get(1))
                .fluidInputs(GTModHandler.getIC2Coolant(1_000))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_HV / 2)
                .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.IC2_Fuel_Rod_Empty.get(1), ItemList.IC2_MOX_Fuel.get(1))
                .itemOutputs(ItemList.RodMOX.get(1))
                .duration(1 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_LV / 2)
                .addTo(cannerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodMOX.get(2), GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4))
                .circuit(2)
                .itemOutputs(ItemList.RodMOX2.get(1))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodMOX.get(4), GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 6))
                .circuit(4)
                .itemOutputs(ItemList.RodMOX4.get(1))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.RodMOX2.get(2), GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4))
                .circuit(5)
                .itemOutputs(ItemList.RodMOX4.get(1))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodMOX.get(1))
                .itemOutputs(
                    ItemList.IC2_Plutonium_Small.get(1),
                    ItemList.IC2_Plutonium.get(3),
                    Materials.Iron.getDust(1))
                .duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(thermalCentrifugeRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodMOX2.get(1))
                .itemOutputs(
                    ItemList.IC2_Plutonium_Small.get(2),
                    ItemList.IC2_Plutonium.get(6),
                    Materials.Iron.getDust(3))
                .duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(thermalCentrifugeRecipes);
            GTValues.RA.stdBuilder()
                .itemInputs(ItemList.DepletedRodMOX4.get(1))
                .itemOutputs(
                    ItemList.IC2_Plutonium_Small.get(4),
                    ItemList.IC2_Plutonium.get(12),
                    Materials.Iron.getDust(6))
                .duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(thermalCentrifugeRecipes);

        }

        // High Density Uranium

        // Naquadah
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Large_Fluid_Cell_TungstenSteel.get(1L), Materials.NaquadahEnriched.getDust(3))
            .itemOutputs(ItemList.RodNaquadah.get(1L))
            .duration(1 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_LV / 2)
            .addTo(cannerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadah.get(2),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 4))
            .circuit(2)
            .itemOutputs(ItemList.RodNaquadah2.get(1))
            .duration(5 * SECONDS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadah.get(4),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 6))
            .circuit(4)
            .itemOutputs(ItemList.RodNaquadah4.get(1))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadah2.get(2),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 4))
            .circuit(5)
            .itemOutputs(ItemList.RodNaquadah4.get(1))
            .duration(5 * SECONDS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadah.get(32L),
                GTOreDictUnificator.get(stickLong, Materials.TungstenSteel, 64L),
                GTOreDictUnificator.get(stickLong, Materials.TungstenSteel, 64L),
                GTOreDictUnificator.get(stickLong, Materials.TungstenSteel, 64L))
            .itemOutputs(ItemList.RodNaquadah32.get(1))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

        // Naquadria
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Large_Fluid_Cell_TungstenSteel.get(1L), Materials.Naquadria.getDust(3))
            .itemOutputs(ItemList.RodNaquadria.get(1L))
            .duration(1 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_LV / 2)
            .addTo(cannerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadria.get(2),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 4))
            .circuit(2)
            .itemOutputs(ItemList.RodNaquadria2.get(1))
            .duration(5 * SECONDS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadria.get(4),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 6))
            .circuit(4)
            .itemOutputs(ItemList.RodNaquadria4.get(1))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.RodNaquadria2.get(2),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 4))
            .circuit(5)
            .itemOutputs(ItemList.RodNaquadria4.get(1))
            .duration(5 * SECONDS)
            .eut(400)
            .addTo(assemblerRecipes);

        // Tiberium
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Large_Fluid_Cell_TungstenSteel.get(1L))
            .itemOutputs(ItemList.RodTiberium.get(1))
            .duration(1 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_LV / 2)
            .addTo(cannerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.RodTiberium.get(2), GTOreDictUnificator.get(stick, Materials.TungstenSteel, 4L))
            .itemOutputs(ItemList.RodTiberium2.get(1))
            .duration(5 * SECONDS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.RodTiberium.get(4), GTOreDictUnificator.get(stickLong, Materials.TungstenSteel, 6L))
            .itemOutputs(ItemList.RodTiberium4.get(1))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(400)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.RodTiberium2.get(2), GTOreDictUnificator.get(stick, Materials.TungstenSteel, 4L))
            .itemOutputs(ItemList.RodTiberium4.get(1))
            .duration(5 * SECONDS)
            .eut(400)
            .addTo(assemblerRecipes);

    }
}
