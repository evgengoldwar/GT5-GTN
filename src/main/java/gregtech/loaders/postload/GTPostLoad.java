package gregtech.loaders.postload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.recipe.RecipeMaps.cannerRecipes;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTMod;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipeBuilder;
import gregtech.api.util.GTRecipeRegistrator;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Other;
import gregtech.common.items.MetaGeneratedItem01;

@SuppressWarnings("deprecation")
public class GTPostLoad {

    public static void registerFluidCannerRecipes() {
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
            .getRegisteredFluidContainerData()) {
            if (tData.fluid.amount <= 0) {
                continue;
            }
            // lava clay bucket is registered with empty container with 0 stack size
            ItemStack emptyContainer = tData.emptyContainer.copy();
            emptyContainer.stackSize = 1;
            GTValues.RA.stdBuilder()
                .itemInputs(emptyContainer)
                .itemOutputs(tData.filledContainer)
                .fluidInputs(tData.fluid)
                .duration((tData.fluid.amount / 62) * TICKS)
                .eut(1)
                .addTo(cannerRecipes);
            if (GTUtility.areFluidsEqual(Materials.Osmium.getMolten(1), tData.fluid)
                || GTUtility.areFluidsEqual(Materials.Iridium.getMolten(1), tData.fluid)
                || GTUtility.areFluidsEqual(Materials.Platinum.getMolten(1), tData.fluid)) {
                continue;
            }
            GTRecipeBuilder builder = GTValues.RA.stdBuilder()
                .itemInputs(tData.filledContainer);
            if (tData.emptyContainer.stackSize > 0) {
                builder.itemOutputs(tData.emptyContainer);
            }
            builder.fluidOutputs(tData.fluid)
                .duration((tData.fluid.amount / 62) * TICKS)
                .eut(1)
                .addTo(cannerRecipes);
        }
    }

    public static void changeWoodenVanillaTools() {
        if (!GTMod.proxy.mChangeWoodenVanillaTools) {
            return;
        }

        GT_FML_LOGGER.debug("GTMod: Updating Vanilla Wooden Tools");
        Items.wooden_sword.setMaxDamage(64);
        Items.wooden_pickaxe.setMaxDamage(64);
        Items.wooden_shovel.setMaxDamage(64);
        Items.wooden_axe.setMaxDamage(64);
        Items.wooden_hoe.setMaxDamage(64);
    }

    public static void doActualRegistration(Materials m) {
        String plateName = OrePrefixes.plate.get(m)
            .toString();
        boolean noSmash = !m.contains(SubTag.NO_SMASHING);
        if (m.hasMetalItems()) GTRecipeRegistrator.registerUsagesForMaterials(plateName, noSmash, m.getIngots(1));
        if (m.hasGemItems()) GTRecipeRegistrator.registerUsagesForMaterials(plateName, noSmash, m.getGems(1));
        if (m.getBlocks(1) != null) GTRecipeRegistrator.registerUsagesForMaterials(null, noSmash, m.getBlocks(1));
    }

    public static void addSolidFakeLargeBoilerFuels() {}

    public static void addCauldronRecipe() {
        OrePrefixes[] washablePrefixes = { OrePrefixes.dustImpure, OrePrefixes.dustPure, OrePrefixes.crushed,
            OrePrefixes.dust };

        for (Materials material : Materials.getAll()) {
            for (OrePrefixes prefix : washablePrefixes) {
                ItemStack input = GTOreDictUnificator.get(prefix, material, 1);
                ItemStack output = MetaGeneratedItem01.getCauldronWashingResult(prefix, material, 1);

                if (input == null || output == null) {
                    continue;
                }

                GTValues.RA.stdBuilder()
                    .itemInputs(input)
                    .fluidInputs(Materials.Water.getFluid(333))
                    .itemOutputs(output)
                    .duration(0)
                    .eut(0)
                    .fake()
                    .addTo(RecipeMaps.cauldronRecipe);
            }
        }
    }

    public static void identifyAnySteam() {
        final String[] steamCandidates = { "steam", "ic2steam" };
        final String[] superHeatedSteamCandidates = { "ic2superheatedsteam" };

        GTModHandler.sAnySteamFluidIDs = Arrays.stream(steamCandidates)
            .map(FluidRegistry::getFluid)
            .filter(Objects::nonNull)
            .map(FluidRegistry::getFluidID)
            .collect(Collectors.toList());
        GTModHandler.sSuperHeatedSteamFluidIDs = Arrays.stream(superHeatedSteamCandidates)
            .map(FluidRegistry::getFluid)
            .filter(Objects::nonNull)
            .map(FluidRegistry::getFluidID)
            .collect(Collectors.toList());
    }

    public static void processToolboxBans() {
        final ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

        for (final String name : Other.toolboxBans) {
            final String[] nameParts = name.split(":");

            if (nameParts.length == 2) {
                final Item item = GameRegistry.findItem(nameParts[0], nameParts[1]);
                if (item != null) {
                    builder.add(item);
                }
            }
        }

        GTMod.proxy.toolboxBans = builder.build();
    }
}
