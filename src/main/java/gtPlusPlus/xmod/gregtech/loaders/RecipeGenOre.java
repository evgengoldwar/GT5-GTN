package gtPlusPlus.xmod.gregtech.loaders;

import static bartworks.system.material.gtenhancement.PlatinumSludgeOutputs.convert;
import static gregtech.api.enums.GTValues.RA;
import static gregtech.api.recipe.RecipeMaps.centrifugeRecipes;
import static gregtech.api.recipe.RecipeMaps.chemicalBathRecipes;
import static gregtech.api.recipe.RecipeMaps.chemicalDehydratorRecipes;
import static gregtech.api.recipe.RecipeMaps.electrolyzerRecipes;
import static gregtech.api.recipe.RecipeMaps.hammerRecipes;
import static gregtech.api.recipe.RecipeMaps.maceratorRecipes;
import static gregtech.api.recipe.RecipeMaps.oreWasherRecipes;
import static gregtech.api.recipe.RecipeMaps.thermalCentrifugeRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.material.MaterialGenerator;
import gtPlusPlus.core.material.MaterialStack;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.material.MaterialsOres;
import gtPlusPlus.core.material.nuclear.MaterialsFluorides;
import gtPlusPlus.core.material.state.MaterialState;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;

public class RecipeGenOre extends RecipeGenBase {

    public static final Set<Runnable> mRecipeGenMap = new HashSet<>();

    static {
        MaterialGenerator.mRecipeMapsToGenerate.add(mRecipeGenMap);
    }

    public RecipeGenOre(final Material M) {
        this(M, false);
    }

    public RecipeGenOre(final Material M, final boolean O) {
        this.toGenerate = M;
        this.disableOptional = O;
        mRecipeGenMap.add(this);
    }

    @Override
    public void run() {
        generateRecipes(this.toGenerate, this.disableOptional);
    }

    private static Material mStone;

    private void generateRecipes(final Material material, final boolean disableOptional) {

        if (mStone == null) {
            mStone = MaterialUtils.generateMaterialFromGtENUM(Materials.Stone);
        }

        int tVoltageMultiplier = MaterialUtils.getVoltageForTier(material.tier);

        final ItemStack dustStone = ItemUtils.getItemStackOfAmountFromOreDict("dustStone", 1);
        Material bonusA = null; // Ni
        Material bonusB = null; // Tin

        // Setup Bonuses
        ArrayList<Material> aMatComp = new ArrayList<>(MaterialUtils.getCompoundMaterialsRecursively(material));

        if (aMatComp.size() < 3) {
            while (aMatComp.size() < 3) {
                aMatComp.add(material);
            }
        }

        final ArrayList<Material> amJ = new ArrayList<>();
        for (Material g : aMatComp) {
            if (g.hasSolidForm()) {
                amJ.add(g);
                if (amJ.size() >= 2) break;
            }
        }

        boolean allFailed = false;
        final ArrayList<MaterialStack> composites = material.getComposites();
        if (amJ.size() < 2) {
            allFailed = true;
            if (!composites.isEmpty() && composites.get(0) != null) {
                bonusA = composites.get(0)
                    .getStackMaterial();
            } else {
                bonusA = material;
            }

            // If Secondary Output has no solid output, try the third (If it exists), then the fourth/fifth
            for (byte i = 1; i < Math.min(composites.size(), 5); i++) {
                if (composites.get(i) == null) break;
                bonusB = composites.get(i)
                    .getStackMaterial();
                if (bonusB != null && bonusB.hasSolidForm()) {
                    allFailed = false;
                    break;
                }
            }
            // If Fifth Output has no solid output, default {see if(allFailed...)}
        } else {
            bonusA = amJ.get(0);
            bonusB = amJ.get(1);
        }

        // Default out if it's made of fluids or some stuff.
        if (bonusA == null) {
            bonusA = tVoltageMultiplier > 100 ? material : mStone;
        }
        // Default out if it's made of fluids or some stuff.
        if (allFailed || bonusB == null) {
            bonusB = tVoltageMultiplier > 100 ? material : mStone;
        }

        ArrayList<Pair<Integer, Material>> componentMap = new ArrayList<>();
        for (MaterialStack r : composites) {
            if (r != null) {
                componentMap.add(Pair.of(r.getPartsPerOneHundred(), r.getStackMaterial()));
            }
        }

        // Need two valid outputs
        if (bonusA == null || !bonusA.hasSolidForm()) {
            bonusA = mStone;
        }
        if (bonusB == null || !bonusB.hasSolidForm()) {
            bonusB = mStone;
        }

        ItemStack matDust = getDust(material);
        ItemStack matDustA = getDust(bonusA);
        ItemStack matDustB = getDust(bonusB);

        /**
         * Macerate
         */


        // Fluorite Hydrogen Chemical Bath
        if (material == MaterialsFluorides.FLUORITE) {
            GTValues.RA.stdBuilder()
                .itemInputs(material.getCrushed(1))
                .itemOutputs(material.getCrushedPurified(4), material.getDustImpure(2), material.getDustPurified(1))
                .outputChances(100_00, 50_00, 10_00)
                .fluidInputs(Materials.Hydrogen.getGas(1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_HV / 2)
                .addTo(chemicalBathRecipes);
        }



        // Forge Hammer

        GTValues.RA.stdBuilder()
            .itemInputs(material.getCrushedCentrifuged(1))
            .itemOutputs(matDust)
            .duration(10 * TICKS)
            .eut(tVoltageMultiplier / 4)
            .addTo(hammerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(material.getCrushed(1))
            .itemOutputs(material.getDustImpure(1))
            .duration(10 * TICKS)
            .eut(tVoltageMultiplier / 4)
            .addTo(hammerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(material.getCrushedPurified(1))
            .itemOutputs(material.getDustPurified(1))
            .duration(10 * TICKS)
            .eut(tVoltageMultiplier / 4)
            .addTo(hammerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(material.getOre(1))
            .itemOutputs(material.getCrushed(1))
            .duration(10 * TICKS)
            .eut(tVoltageMultiplier / 4)
            .addTo(hammerRecipes);

        // Centrifuge

        // Electrolyzer

        if (!disableOptional && material != MaterialsFluorides.FLUORITE) {
            // Process Dust
            if (!componentMap.isEmpty() && componentMap.size() <= 6) {

                ItemStack[] mInternalOutputs = new ItemStack[6];
                int[] mChances = new int[6];
                int mCellCount = 0;

                int mTotalCount = 0;

                int mCounter = 0;
                for (Pair<Integer, Material> f : componentMap) {
                    if (f.getValue()
                        .getState() != MaterialState.SOLID) {
                        mInternalOutputs[mCounter++] = f.getValue()
                            .getCell(f.getKey());
                        mCellCount += f.getKey();
                    } else {
                        mInternalOutputs[mCounter++] = getDust(f.getValue(), f.getKey());
                    }
                    mTotalCount += f.getKey();
                }

                // Build Output Array
                for (int g = 0; g < mInternalOutputs.length; g++) {
                    mChances[g] = (mInternalOutputs[g] != null ? 10000 : 0);
                }

                ItemStack emptyCell = null;
                if (mCellCount > 0) {
                    emptyCell = ItemList.Cell_Empty.get(mCellCount);
                }

                ItemStack mainDust = material.getDust(material.smallestStackSizeWhenProcessing);
                if (mainDust == null) {
                    mainDust = material.getDust(mTotalCount);
                    if (mainDust == null) {
                        return;
                    }
                }

                for (int j = 0; j < mInternalOutputs.length; j++) {
                    if (mInternalOutputs[j] == null) {
                        mInternalOutputs[j] = GTValues.NI;
                    }
                }

                // i don't understand the mess above, so let's just strip nulls and assume the chances are in correct
                // order
                List<ItemStack> internalOutputs = new ArrayList<>(Arrays.asList(mInternalOutputs));
                internalOutputs.removeIf(Objects::isNull);
                int[] chances = new int[internalOutputs.size()];
                System.arraycopy(mChances, 0, chances, 0, internalOutputs.size());
                ItemStack[] outputs = internalOutputs.toArray(new ItemStack[0]);
                if (usesLanthanidesElectrolyzerOutputs(material)) {
                } else {
                    outputs = convert(outputs);
                }
                ItemStack[] inputs;
                if (emptyCell == null) {
                    inputs = new ItemStack[] { mainDust };
                } else {
                    inputs = new ItemStack[] { mainDust, emptyCell };
                }
                GTValues.RA.stdBuilder()
                    .itemInputs(inputs)
                    .itemOutputs(outputs)
                    .outputChances(chances)
                    .duration(Math.max(material.getMass() * 3L * 1, 1))
                    .eut(tVoltageMultiplier)
                    .addTo(electrolyzerRecipes);

            } else if (componentMap.size() > 6 && componentMap.size() <= 9) {

                ItemStack[] mInternalOutputs = new ItemStack[9];
                int[] mChances = new int[9];
                int mCellCount = 0;

                int mTotalCount = 0;

                int mCounter = 0;
                for (Pair<Integer, Material> f : componentMap) {
                    if (f.getValue()
                        .getState() != MaterialState.SOLID
                        && f.getValue()
                            .getState() != MaterialState.ORE) {
                        mInternalOutputs[mCounter++] = f.getValue()
                            .getCell(f.getKey());
                        mCellCount += f.getKey();
                    } else {
                        mInternalOutputs[mCounter++] = getDust(f.getValue(), f.getKey());
                    }
                    mTotalCount += f.getKey();
                }

                // Build Output Array
                for (int g = 0; g < mInternalOutputs.length; g++) {
                    mChances[g] = (mInternalOutputs[g] != null ? 10000 : 0);
                }

                ItemStack emptyCell = null;
                if (mCellCount > 0) {
                    emptyCell = ItemList.Cell_Empty.get(mCellCount);
                }

                ItemStack mainDust = material.getDust(material.smallestStackSizeWhenProcessing);
                if (mainDust == null) {
                    mainDust = material.getDust(mTotalCount);
                    if (mainDust == null) {
                        return;
                    }
                }

                for (int j = 0; j < mInternalOutputs.length; j++) {
                    if (mInternalOutputs[j] == null) {
                        mInternalOutputs[j] = GTValues.NI;
                    }
                }

                // i don't understand the mess above, so let's just strip nulls and assume the chances are in correct
                // order
                List<ItemStack> internalOutputs = new ArrayList<>(Arrays.asList(mInternalOutputs));
                internalOutputs.removeIf(Objects::isNull);
                int[] chances = new int[internalOutputs.size()];
                System.arraycopy(mChances, 0, chances, 0, internalOutputs.size());
                ItemStack[] outputs = internalOutputs.toArray(new ItemStack[0]);
                if (usesLanthanidesDehydratorOutputs(material)) {
                } else {
                    outputs = convert(outputs);
                }

                ItemStack[] inputs;
                if (emptyCell == null) {
                    inputs = new ItemStack[] { mainDust };
                } else {
                    inputs = new ItemStack[] { mainDust, emptyCell };
                }

                GTValues.RA.stdBuilder()
                    .itemInputs(inputs)
                    .itemOutputs(outputs)
                    .outputChances(chances)
                    .eut(tVoltageMultiplier)
                    .duration((int) Math.max(material.getMass() * 4L * 1, 1))
                    .addTo(chemicalDehydratorRecipes);

            }
        }

        // Shaped Crafting

        GTModHandler.addCraftingRecipe(
            material.getDustPurified(1),
            GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            new Object[] { "h  ", "P  ", "   ", 'P', material.getCrushedPurified(1) });

        GTModHandler.addCraftingRecipe(
            material.getDustImpure(1),
            GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            new Object[] { "h  ", "C  ", "   ", 'C', material.getCrushed(1) });

        GTModHandler.addCraftingRecipe(
            matDust,
            GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            new Object[] { "h  ", "C  ", "   ", 'C', material.getCrushedCentrifuged(1) });

        final ItemStack smallDust = material.getSmallDust(1);
        final ItemStack tinyDust = material.getTinyDust(1);

        if (tinyDust != null) {
            GTModHandler.addCraftingRecipe(
                matDust,
                GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
                new Object[] { "TTT", "TTT", "TTT", 'T', tinyDust });
            GTModHandler.addCraftingRecipe(
                material.getTinyDust(9),
                GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
                new Object[] { "D  ", "   ", "   ", 'D', matDust });
        }

        if (smallDust != null) {
            GTModHandler.addCraftingRecipe(
                matDust,
                GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
                new Object[] { "SS ", "SS ", "   ", 'S', smallDust });
            GTModHandler.addCraftingRecipe(
                material.getSmallDust(4),
                GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
                new Object[] { " D ", "   ", "   ", 'D', matDust });
        }
    }

    private static boolean usesLanthanidesElectrolyzerOutputs(Material material) {
        return material == MaterialsOres.FLORENCITE || material == MaterialsOres.HIBONITE
            || material == MaterialsOres.LANTHANITE_CE
            || material == MaterialsOres.YTTROCERITE
            || material == MaterialsOres.ZIRCONILITE;
    }

    private static boolean usesLanthanidesDehydratorOutputs(Material material) {
        return material == MaterialsOres.CERITE || material == MaterialsOres.FLUORCAPHITE
            || material == MaterialsOres.GADOLINITE_CE
            || material == MaterialsOres.GADOLINITE_Y
            || material == MaterialsOres.POLYCRASE
            || material == MaterialsOres.ZIRKELITE;
    }

    public static ItemStack getTinyDust(Material m) {
        ItemStack x = m.getTinyDust(1);
        if (x == null) {
            x = mStone.getDust(1);
        }
        return x;
    }

    public static ItemStack getDust(Material m) {
        ItemStack x = getDust(m, 1);
        if (x == null) {
            x = mStone.getDust(1);
        }
        return x;
    }

    public static ItemStack getDust(Material material, int amount) {
        return material.getDust(amount);
    }
}
