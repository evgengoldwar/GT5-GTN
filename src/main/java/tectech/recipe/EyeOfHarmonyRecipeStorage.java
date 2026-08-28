package tectech.recipe;

import static tectech.recipe.TecTechRecipeMaps.eyeOfHarmonyRecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.math.LongMath;

import bartworks.system.material.Werkstoff;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.StoneType;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.ores.BWOreAdapter;
import gregtech.common.ores.OreInfo;
import gtneioreplugin.plugin.block.BlockDimensionDisplay;
import gtneioreplugin.plugin.block.ModBlocks;
import tectech.util.FluidStackLong;
import tectech.util.ItemStackLong;

public class EyeOfHarmonyRecipeStorage {

    public static final long BILLION = LongMath.pow(10, 9);
    private static final double CHANCE_DECREASE_PER_DIMENSION = 0.05;

    // Map is unique so this is fine.
    HashMap<Block, String> blocksMapInverted = new HashMap<>() {

        private static final long serialVersionUID = -1634011860327553337L;

        {
            ModBlocks.blocks.forEach((dimString, dimBlock) -> put(dimBlock, dimString));
        }
    };

    private final HashMap<String, EyeOfHarmonyRecipe> recipeHashMap = new HashMap<>() {

        private static final long serialVersionUID = -3501819612517400500L;

        {}
    };

    public EyeOfHarmonyRecipe recipeLookUp(final ItemStack aStack) {
        String dimAbbreviation = blocksMapInverted.get(Block.getBlockFromItem(aStack.getItem()));
        return recipeHashMap.get(dimAbbreviation);
    }

    public EyeOfHarmonyRecipeStorage() {

        for (EyeOfHarmonyRecipe recipe : recipeHashMap.values()) {

            ArrayList<ItemStack> outputItems = new ArrayList<>();
            for (ItemStackLong itemStackLong : recipe.getOutputItems()) {
                outputItems.add(itemStackLong.itemStack);
            }

            ArrayList<FluidStack> outputFluids = new ArrayList<>();
            for (FluidStackLong fluidStackLong : recipe.getOutputFluids()) {
                outputFluids.add(fluidStackLong.fluidStack);
            }

            ItemStack planetItem = recipe.getRecipeTriggerItem()
                .copy();
            planetItem.stackSize = 0;

            GTValues.RA.stdBuilder()
                .itemInputs(planetItem)
                .itemOutputs(outputItems.toArray(new ItemStack[0]))
                .fluidInputs(
                    Materials.Hydrogen.getGas(0),
                    Materials.Helium.getGas(0),
                    Materials.RawStarMatter.getFluid(0))
                .fluidOutputs(outputFluids.toArray(new FluidStack[0]))
                .duration(recipe.getRecipeTimeInTicks())
                .eut(0)
                .special(recipe)
                .addTo(eyeOfHarmonyRecipes);
        }
    }

    private void specialDeepDarkRecipe(final HashMap<String, EyeOfHarmonyRecipe> hashMap,
        final BlockDimensionDisplay planetItem) {

        final HashSet<Materials> validMaterialSet = new HashSet<>();
        for (Materials material : Materials.values()) {

            ItemStack normalOre = GTOreDictUnificator.get(OrePrefixes.ore, material, 1);

            if ((normalOre != null)) {
                validMaterialSet.add(material);
            }

            ItemStack smallOre = GTOreDictUnificator.get(OrePrefixes.oreSmall, material, 1);

            if ((smallOre != null)) {
                validMaterialSet.add(material);
            }
        }

        OreInfo<Werkstoff> infoWerkstoff = OreInfo.getNewInfo();
        infoWerkstoff.stoneType = StoneType.Stone;
        for (Werkstoff mat : Werkstoff.werkstoffHashSet) {
            infoWerkstoff.material = mat;

            infoWerkstoff.isSmall = false;
            if (BWOreAdapter.INSTANCE.supports(infoWerkstoff)) validMaterialSet.add(mat.getBridgeMaterial());

            infoWerkstoff.isSmall = true;
            if (BWOreAdapter.INSTANCE.supports(infoWerkstoff)) validMaterialSet.add(mat.getBridgeMaterial());
        }
        infoWerkstoff.release();

        ArrayList<Materials> validMaterialList = new ArrayList<>(validMaterialSet);

        long rocketTier = 9;
    }

    private static long timeCalculator(final long rocketTier) {
        return (long) (18_000L * GTUtility.powInt(1.4, rocketTier));
    }
}
