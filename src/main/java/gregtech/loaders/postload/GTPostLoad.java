package gregtech.loaders.postload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.Mods.BetterLoadingScreen;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.GalacticraftMars;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.recipe.RecipeMaps.cannerRecipes;
import static gregtech.api.recipe.RecipeMaps.massFabFakeRecipes;
import static gregtech.api.recipe.RecipeMaps.scannerFakeRecipes;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTMod;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTCLSCompat;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipeBuilder;
import gregtech.api.util.GTRecipeRegistrator;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Other;
import gregtech.common.items.MetaGeneratedItem01;
import gregtech.common.tileentities.machines.basic.MTEMassfabricator;
import gregtech.common.tileentities.machines.basic.MTERockBreaker;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;

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

    public static void addFakeRecipes() {
        GT_FML_LOGGER.debug("GTMod: Adding Fake Recipes for NEI");

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.written_book, 1, 32767))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Book Data"))
            .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.filled_map, 1, 32767))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Map Data"))
            .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Tool_DataOrb.getWithName(1L, "Orb to overwrite"))
            .itemOutputs(ItemList.Tool_DataOrb.getWithName(1L, "Copy of the Orb"))
            .special(ItemList.Tool_DataOrb.getWithName(0L, "Orb to copy"))
            .duration(25 * SECONDS + 12 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Tool_DataStick.getWithName(1L, "Stick to overwrite"))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Copy of the Stick"))
            .special(ItemList.Tool_DataStick.getWithName(0L, "Stick to copy"))
            .duration(6 * SECONDS + 8 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Tool_DataStick.getWithName(1L, "Raw Prospection Data"))
            .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Analyzed Prospection Data"))
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .ignoreCollision()
            .fake()
            .addTo(scannerFakeRecipes);

        if (GalacticraftCore.isModLoaded()) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    Objects
                        .requireNonNull(
                            GTModHandler.getModItem(GalacticraftCore.ID, "item.schematic", 1, Short.MAX_VALUE))
                        .setStackDisplayName("Any Schematic"))
                .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Schematic"))
                .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
                .duration(30 * MINUTES)
                .eut(TierEU.RECIPE_HV)
                .ignoreCollision()
                .fake()
                .addTo(scannerFakeRecipes);

            if (GalacticraftMars.isModLoaded()) {
                GTValues.RA.stdBuilder()
                    .itemInputs(
                        Objects
                            .requireNonNull(
                                GTModHandler.getModItem(GalacticraftMars.ID, "item.schematic", 1, Short.MAX_VALUE))
                            .setStackDisplayName("Any Schematic"))
                    .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Schematic"))
                    .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
                    .duration(30 * MINUTES)
                    .eut(TierEU.RECIPE_MV)
                    .ignoreCollision()
                    .fake()
                    .addTo(scannerFakeRecipes);
            }
            if (GalaxySpace.isModLoaded()) {
                for (int i = 4; i < 9; i++) {
                    GTValues.RA.stdBuilder()
                        .itemInputs(
                            GTModHandler.getModItem(GalaxySpace.ID, "item.SchematicTier" + i, 1)
                                .setStackDisplayName("Any Schematic"))
                        .itemOutputs(ItemList.Tool_DataStick.getWithName(1L, "Scanned Schematic"))
                        .special(ItemList.Tool_DataStick.getWithName(1L, "Stick to save it to"))
                        .duration(30 * MINUTES)
                        .eut(TierEU.RECIPE_HV)
                        .ignoreCollision()
                        .fake()
                        .addTo(scannerFakeRecipes);
                }
            }
        }

        if (!MTEMassfabricator.sRequiresUUA) {

            MTEMassfabricator.nonUUARecipe = GTValues.RA.stdBuilder()
                .fluidOutputs(Materials.UUMatter.getFluid(1L))
                .duration(MTEMassfabricator.sDurationMultiplier)
                .eut(MTEMassfabricator.BASE_EUT)
                .ignoreCollision()
                .fake()
                .build()
                .get();

            massFabFakeRecipes.add(MTEMassfabricator.nonUUARecipe);

        }

        MTEMassfabricator.uuaRecipe = GTValues.RA.stdBuilder()
            .circuit(1)
            .fluidInputs(Materials.UUAmplifier.getFluid(MTEMassfabricator.sUUAperUUM))
            .fluidOutputs(Materials.UUMatter.getFluid(1L))
            .duration(MTEMassfabricator.sDurationMultiplier / MTEMassfabricator.sUUASpeedBonus)
            .eut(MTEMassfabricator.BASE_EUT)
            .ignoreCollision()
            .fake()
            .build()
            .get();

        massFabFakeRecipes.add(MTEMassfabricator.uuaRecipe);

        MTERockBreaker.addRockBreakerRecipe(
            b -> b.recipeDescription(StatCollector.translateToLocal("gt.recipe.rockbreaker.fakeitem.top"))
                .sideBlocks(Blocks.water)
                .topBlock(Blocks.lava)
                .outputItem(new ItemStack(Blocks.stone, 1))
                .duration(16 * TICKS));

        MTERockBreaker.addRockBreakerRecipe(
            b -> b.recipeDescription(StatCollector.translateToLocal("gt.recipe.rockbreaker.fakeitem.side"))
                .sideBlocks(Blocks.water, Blocks.lava)
                .outputItem(new ItemStack(Blocks.cobblestone, 1))
                .duration(16 * TICKS));

        MTERockBreaker.addRockBreakerRecipe(
            b -> b.sideBlocks(Blocks.water)
                .anywhereBlocks(Blocks.lava)
                .inputItem(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), true)
                .circuit(1)
                .outputItem(new ItemStack(Blocks.obsidian, 1))
                .duration(6 * SECONDS + 8 * TICKS));

        MTERockBreaker.addRockBreakerRecipe(
            b -> b.sideBlocks(Blocks.water)
                .anywhereBlocks(Blocks.lava)
                .inputItem(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L), true)
                .circuit(6)
                .outputItem(new ItemStack(Blocks.netherrack, 1))
                .duration(16 * TICKS));

        if (Mods.EtFuturumRequiem.isModLoaded()) {
            MTERockBreaker.addRockBreakerRecipe(
                b -> b.sideBlocks(Blocks.lava)
                    .bottomBlock(Blocks.soul_sand)
                    .inputItem(GTModHandler.getModItem(Mods.EtFuturumRequiem.ID, "blue_ice", 0, 0), false)
                    .circuit(1)
                    .outputItem(GTOreDictUnificator.get(OrePrefixes.stone, Materials.Basalt, 1L))
                    .duration(16 * TICKS));

            MTERockBreaker.addRockBreakerRecipe(
                b -> b.sideBlocks(Blocks.lava)
                    .bottomBlock(Blocks.soul_sand)
                    .inputItem(GTModHandler.getModItem(Mods.EtFuturumRequiem.ID, "magma", 0, 0), false)
                    .circuit(1)
                    .outputItem(GTModHandler.getModItem(Mods.EtFuturumRequiem.ID, "cobbled_deepslate", 1, 0))
                    .duration(16 * TICKS));
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
