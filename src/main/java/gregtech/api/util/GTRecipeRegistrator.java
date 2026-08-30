package gregtech.api.util;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.mixin.interfaces.accessors.ShapedOreRecipeAccessor;
import ic2.api.reactor.IReactorComponent;

public class GTRecipeRegistrator {

    public static final List<Materials> sRodMaterialList = new ArrayList<>();

    private static final ItemStack sMt1 = new ItemStack(Blocks.dirt, 1, 0), sMt2 = new ItemStack(Blocks.dirt, 1, 0);
    private static final String s_H = "h", s_F = "f", s_I = "I", s_P = "P", s_R = "R";
    private static final RecipeShape[] sShapes = new RecipeShape[] {
        new RecipeShape(sMt1, null, sMt1, sMt1, sMt1, sMt1, null, sMt1, null),
        new RecipeShape(sMt1, null, sMt1, sMt1, null, sMt1, sMt1, sMt1, sMt1),
        new RecipeShape(null, sMt1, null, sMt1, sMt1, sMt1, sMt1, null, sMt1),
        new RecipeShape(sMt1, sMt1, sMt1, sMt1, null, sMt1, null, null, null),
        new RecipeShape(sMt1, null, sMt1, sMt1, sMt1, sMt1, sMt1, sMt1, sMt1),
        new RecipeShape(sMt1, sMt1, sMt1, sMt1, null, sMt1, sMt1, null, sMt1),
        new RecipeShape(null, null, null, sMt1, null, sMt1, sMt1, null, sMt1),
        new RecipeShape(null, sMt1, null, null, sMt1, null, null, sMt2, null),
        new RecipeShape(sMt1, sMt1, sMt1, null, sMt2, null, null, sMt2, null),
        new RecipeShape(null, sMt1, null, null, sMt2, null, null, sMt2, null),
        new RecipeShape(sMt1, sMt1, null, sMt1, sMt2, null, null, sMt2, null),
        new RecipeShape(null, sMt1, sMt1, null, sMt2, sMt1, null, sMt2, null),
        new RecipeShape(sMt1, sMt1, null, null, sMt2, null, null, sMt2, null),
        new RecipeShape(null, sMt1, sMt1, null, sMt2, null, null, sMt2, null),
        new RecipeShape(null, sMt1, null, sMt1, null, null, null, sMt1, sMt2),
        new RecipeShape(null, sMt1, null, null, null, sMt1, sMt2, sMt1, null),
        new RecipeShape(null, sMt1, null, sMt1, null, sMt1, null, null, sMt2),
        new RecipeShape(null, sMt1, null, sMt1, null, sMt1, sMt2, null, null),
        new RecipeShape(null, sMt2, null, null, sMt1, null, null, sMt1, null),
        new RecipeShape(null, sMt2, null, null, sMt2, null, sMt1, sMt1, sMt1),
        new RecipeShape(null, sMt2, null, null, sMt2, null, null, sMt1, null),
        new RecipeShape(null, sMt2, null, sMt1, sMt2, null, sMt1, sMt1, null),
        new RecipeShape(null, sMt2, null, null, sMt2, sMt1, null, sMt1, sMt1),
        new RecipeShape(null, sMt2, null, null, sMt2, null, sMt1, sMt1, null),
        new RecipeShape(sMt1, null, null, null, sMt2, null, null, null, sMt2),
        new RecipeShape(null, null, sMt1, null, sMt2, null, sMt2, null, null),
        new RecipeShape(sMt1, null, null, null, sMt2, null, null, null, null),
        new RecipeShape(null, null, sMt1, null, sMt2, null, null, null, null),
        new RecipeShape(sMt1, sMt2, null, null, null, null, null, null, null),
        new RecipeShape(sMt2, sMt1, null, null, null, null, null, null, null),
        new RecipeShape(sMt1, null, null, sMt2, null, null, null, null, null),
        new RecipeShape(sMt2, null, null, sMt1, null, null, null, null, null),
        new RecipeShape(sMt1, sMt1, sMt1, sMt1, sMt1, sMt1, null, sMt2, null),
        new RecipeShape(sMt1, sMt1, null, sMt1, sMt1, sMt2, sMt1, sMt1, null),
        new RecipeShape(null, sMt1, sMt1, sMt2, sMt1, sMt1, null, sMt1, sMt1),
        new RecipeShape(null, sMt2, null, sMt1, sMt1, sMt1, sMt1, sMt1, sMt1),
        new RecipeShape(sMt1, sMt1, sMt1, sMt1, sMt2, sMt1, null, sMt2, null),
        new RecipeShape(sMt1, sMt1, null, sMt1, sMt2, sMt2, sMt1, sMt1, null),
        new RecipeShape(null, sMt1, sMt1, sMt2, sMt2, sMt1, null, sMt1, sMt1),
        new RecipeShape(null, sMt2, null, sMt1, sMt2, sMt1, sMt1, sMt1, sMt1),
        new RecipeShape(sMt1, null, null, null, sMt1, null, null, null, null),
        new RecipeShape(null, sMt1, null, sMt1, null, null, null, null, null),
        new RecipeShape(sMt1, sMt1, null, sMt2, null, sMt1, sMt2, null, null),
        new RecipeShape(null, sMt1, sMt1, sMt1, null, sMt2, null, null, sMt2) };
    private static volatile Map<RecipeShape, List<IRecipe>> indexedRecipeListCache;
    private static final String[][] sShapesA = new String[][] { null, null, null,
        { "Helmet", s_P + s_P + s_P, s_P + s_H + s_P },
        { "ChestPlate", s_P + s_H + s_P, s_P + s_P + s_P, s_P + s_P + s_P },
        { "Pants", s_P + s_P + s_P, s_P + s_H + s_P, s_P + " " + s_P }, { "Boots", s_P + " " + s_P, s_P + s_H + s_P },
        { "Sword", " " + s_P + " ", s_F + s_P + s_H, " " + s_R + " " },
        { "Pickaxe", s_P + s_I + s_I, s_F + s_R + s_H, " " + s_R + " " },
        { "Shovel", s_F + s_P + s_H, " " + s_R + " ", " " + s_R + " " },
        { "Axe", s_P + s_I + s_H, s_P + s_R + " ", s_F + s_R + " " },
        { "Axe", s_P + s_I + s_H, s_P + s_R + " ", s_F + s_R + " " },
        { "Hoe", s_P + s_I + s_H, s_F + s_R + " ", " " + s_R + " " },
        { "Hoe", s_P + s_I + s_H, s_F + s_R + " ", " " + s_R + " " },
        { "Sickle", " " + s_P + " ", s_P + s_F + " ", s_H + s_P + s_R },
        { "Sickle", " " + s_P + " ", s_P + s_F + " ", s_H + s_P + s_R },
        { "Sickle", " " + s_P + " ", s_P + s_F + " ", s_H + s_P + s_R },
        { "Sickle", " " + s_P + " ", s_P + s_F + " ", s_H + s_P + s_R },
        { "Sword", " " + s_R + " ", s_F + s_P + s_H, " " + s_P + " " },
        { "Pickaxe", " " + s_R + " ", s_F + s_R + s_H, s_P + s_I + s_I },
        { "Shovel", " " + s_R + " ", " " + s_R + " ", s_F + s_P + s_H },
        { "Axe", s_F + s_R + " ", s_P + s_R + " ", s_P + s_I + s_H },
        { "Axe", s_F + s_R + " ", s_P + s_R + " ", s_P + s_I + s_H },
        { "Hoe", " " + s_R + " ", s_F + s_R + " ", s_P + s_I + s_H },
        { "Hoe", " " + s_R + " ", s_F + s_R + " ", s_P + s_I + s_H },
        { "Spear", s_P + s_H + " ", s_F + s_R + " ", " " + " " + s_R },
        { "Spear", s_P + s_H + " ", s_F + s_R + " ", " " + " " + s_R }, { "Knive", s_H + s_P, s_R + s_F },
        { "Knive", s_F + s_H, s_P + s_R }, { "Knive", s_F + s_H, s_P + s_R }, { "Knive", s_P + s_F, s_R + s_H },
        { "Knive", s_P + s_F, s_R + s_H }, null, null, null, null,
        { "WarAxe", s_P + s_P + s_P, s_P + s_R + s_P, s_F + s_R + s_H }, null, null, null,
        { "Shears", s_H + s_P, s_P + s_F }, { "Shears", s_H + s_P, s_P + s_F },
        { "Scythe", s_I + s_P + s_H, s_R + s_F + s_P, s_R + " " + " " },
        { "Scythe", s_H + s_P + s_I, s_P + s_F + s_R, " " + " " + s_R } };

    static {
        GregTechAPI.sAfterGTPostload.add(() -> indexedRecipeListCache = null);
    }

    public static synchronized void registerUsagesForMaterials(String aPlate, boolean aRecipeReplacing,
        ItemStack... aMats) {
        for (ItemStack aMat : aMats) {
            aMat = GTUtility.copyOrNull(aMat);

            if (aMat == null) continue;

            ItemData aItemData = GTOreDictUnificator.getItemData(aMat);
            if (aItemData == null || aItemData.mPrefix != OrePrefixes.ingot) aPlate = null;
            if (aPlate != null && GTOreDictUnificator.getFirstOre(aPlate, 1) == null) aPlate = null;

            sMt1.func_150996_a(aMat.getItem());
            sMt1.stackSize = 1;
            Items.feather.setDamage(sMt1, Items.feather.getDamage(aMat));

            sMt2.func_150996_a(new ItemStack(Blocks.dirt).getItem());
            sMt2.stackSize = 1;
            Items.feather.setDamage(sMt2, 0);

            if (aItemData != null && aItemData.hasValidPrefixMaterialData()) {
                for (RecipeShape tRecipe : sShapes) {
                    for (ItemStack tCrafted : GTModHandler.getRecipeOutputsBuffered(tRecipe.shape)) {
                        GTOreDictUnificator.addItemData(
                            tCrafted,
                            new ItemData(aItemData.mMaterial.mMaterial, aItemData.mMaterial.mAmount * tRecipe.amount1));
                    }
                }
            }
            registerStickStuff(aPlate, aItemData, aRecipeReplacing);
        }
    }

    private static List<IRecipe> getRecipeList(RecipeShape shape) {
        boolean force = !GregTechAPI.sPostloadStarted || GregTechAPI.sPostloadFinished;
        if (force || indexedRecipeListCache == null) {
            synchronized (GTRecipeRegistrator.class) {
                if (indexedRecipeListCache == null || force) {
                    indexedRecipeListCache = createIndexedRecipeListCache();
                }
            }
        }
        return indexedRecipeListCache.get(shape);
    }

    private static Map<RecipeShape, List<IRecipe>> createIndexedRecipeListCache() {
        Map<RecipeShape, List<IRecipe>> result = new IdentityHashMap<>();
        ArrayList<IRecipe> allRecipeList = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();
        // filter using the empty slots in the shape.
        // if the empty slots doesn't match, the recipe will definitely fail
        SetMultimap<List<Integer>, RecipeShape> filter = HashMultimap.create();
        for (RecipeShape shape : sShapes) {
            for (List<Integer> list : shape.getEmptySlotsAllVariants()) {
                filter.put(list, shape);
            }
        }
        List<Integer> buffer = new ArrayList<>(9);
        for (IRecipe tRecipe : allRecipeList) {
            if (tRecipe instanceof ShapelessRecipes || tRecipe instanceof ShapelessOreRecipe) {
                // we don't target shapeless recipes
                continue;
            }
            buffer.clear();
            ItemStack tStack = tRecipe.getRecipeOutput();
            if (GTUtility.isStackValid(tStack) && tStack.getMaxStackSize() == 1
                && tStack.getMaxDamage() > 0
                && !(tStack.getItem() instanceof ItemBlock)
                && !(tStack.getItem() instanceof IReactorComponent)
                && !GTModHandler.isElectricItem(tStack)
                && !GTUtility.isStackInList(tStack, GTModHandler.sNonReplaceableItems)) {
                if (tRecipe instanceof ShapedOreRecipe tShapedRecipe) {
                    if (checkRecipeShape(
                        buffer,
                        tShapedRecipe.getInput(),
                        getRecipeWidth(tShapedRecipe),
                        getRecipeHeight(tShapedRecipe))) {
                        for (RecipeShape s : filter.get(buffer)) {
                            result.computeIfAbsent(s, k -> new ArrayList<>())
                                .add(tRecipe);
                        }
                    }
                } else if (tRecipe instanceof ShapedRecipes tShapedRecipe) {
                    if (checkRecipeShape(
                        buffer,
                        tShapedRecipe.recipeItems,
                        getRecipeWidth(tShapedRecipe),
                        getRecipeHeight(tShapedRecipe))) {
                        for (RecipeShape s : filter.get(buffer)) {
                            result.computeIfAbsent(s, k -> new ArrayList<>())
                                .add(tRecipe);
                        }
                    }
                } else {
                    for (RecipeShape s : sShapes) {
                        // unknown recipe type. cannot determine empty slots. we choose to add to the recipe list for
                        // all shapes
                        result.computeIfAbsent(s, k -> new ArrayList<>())
                            .add(tRecipe);
                    }
                }
            }
        }
        return result;
    }

    private static boolean checkRecipeShape(List<Integer> emptySlotIndexesBuffer, Object[] input, int tRecipeWidth,
        int tRecipeHeight) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (x >= tRecipeWidth || y >= tRecipeHeight) {
                    emptySlotIndexesBuffer.add(x + y * 3);
                    continue;
                }
                Object tObject = input[x + y * tRecipeWidth];
                if (tObject == null) {
                    emptySlotIndexesBuffer.add(x + y * 3);
                    continue;
                }
                if (tObject instanceof ItemStack
                    && (((ItemStack) tObject).getItem() == null || ((ItemStack) tObject).getMaxStackSize() < 2
                        || ((ItemStack) tObject).getMaxDamage() > 0
                        || ((ItemStack) tObject).getItem() instanceof ItemBlock)) {
                    return false;
                }
                if (tObject instanceof List && ((List<?>) tObject).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static synchronized void registerStickStuff(String aPlate, ItemData aItemData, boolean aRecipeReplacing) {
        IdentityHashMap<IRecipe, Boolean> tKnownMatches = aRecipeReplacing && aPlate != null ? new IdentityHashMap<>()
            : null;
        for (Materials tMaterial : sRodMaterialList) {
            ItemStack tMt2 = GTOreDictUnificator.get(OrePrefixes.stick, tMaterial, 1);
            if (tMt2 == null) {
                continue;
            }

            sMt2.func_150996_a(tMt2.getItem());
            sMt2.stackSize = 1;
            Items.feather.setDamage(sMt2, Items.feather.getDamage(tMt2));

            for (int i = 0; i < sShapes.length; i++) {
                RecipeShape tRecipe = sShapes[i];
                boolean tCanReplace = aRecipeReplacing && aPlate != null
                    && sShapesA[i] != null
                    && sShapesA[i].length > 1;
                if (tCanReplace) tKnownMatches.clear();
                boolean tRemovalAttempted = false;

                for (ItemStack tCrafted : GTModHandler.getRecipeOutputs(
                    getRecipeList(tRecipe),
                    true,
                    tRecipe.shape,
                    tCanReplace ? tKnownMatches : null)) {
                    if (aItemData != null && aItemData.hasValidPrefixMaterialData()) {
                        GTOreDictUnificator.addItemData(
                            tCrafted,
                            new ItemData(
                                aItemData.mMaterial.mMaterial,
                                aItemData.mMaterial.mAmount * tRecipe.amount1,
                                new MaterialStack(tMaterial, OrePrefixes.stick.getMaterialAmount() * tRecipe.amount2)));
                    }

                    if (tCanReplace && !tRemovalAttempted) {
                        tRemovalAttempted = true;
                        assert aItemData != null;

                        ItemStack tStack = GTModHandler.removeRecipe(tRecipe.shape, tKnownMatches);
                        if (tStack == null) {
                            continue;
                        }

                        switch (sShapesA[i].length) {
                            case 2 -> GTModHandler.addCraftingRecipe(
                                tStack,
                                GTModHandler.RecipeBits.BUFFERED,
                                new Object[] { sShapesA[i][1], s_P.charAt(0), aPlate, s_R.charAt(0),
                                    OrePrefixes.stick.get(tMaterial), s_I.charAt(0), aItemData });
                            case 3 -> GTModHandler.addCraftingRecipe(
                                tStack,
                                GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
                                new Object[] { sShapesA[i][1], sShapesA[i][2], s_P.charAt(0), aPlate, s_R.charAt(0),
                                    OrePrefixes.stick.get(tMaterial), s_I.charAt(0), aItemData });
                            default -> GTModHandler.addCraftingRecipe(
                                tStack,
                                GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
                                new Object[] { sShapesA[i][1], sShapesA[i][2], sShapesA[i][3], s_P.charAt(0), aPlate,
                                    s_R.charAt(0), OrePrefixes.stick.get(tMaterial), s_I.charAt(0), aItemData });
                        }
                    }
                }
            }
        }
    }

    private static int getRecipeWidth(ShapedOreRecipe r) {
        return ((ShapedOreRecipeAccessor) r).gt5u$getWidth();
    }

    private static int getRecipeHeight(ShapedOreRecipe r) {
        return ((ShapedOreRecipeAccessor) r).gt5u$getHeight();
    }

    private static int getRecipeHeight(ShapedRecipes r) {
        return r.recipeHeight;
    }

    private static int getRecipeWidth(ShapedRecipes r) {
        return r.recipeWidth;
    }

    private static class RecipeShape {

        private final ItemStack[] shape;
        private int amount1;
        private int amount2;

        public RecipeShape(ItemStack... shape) {
            this.shape = shape;

            for (ItemStack stack : shape) {
                if (stack == sMt1) this.amount1++;
                if (stack == sMt2) this.amount2++;
            }
        }

        public List<List<Integer>> getEmptySlotsAllVariants() {
            // "shake" the grid in 8 direction and see if the recipe shape is still valid
            // also include the "no movement" case
            ImmutableList.Builder<List<Integer>> b = ImmutableList.builder();
            for (int i = -1; i < 2; i++) {
                if (i != 0 && !isColClear(i + 1)) continue;
                for (int j = -1; j < 2; j++) {
                    if (j != 0 && !isRowClear(j + 1)) continue;
                    b.add(getEmptySlots(i, j));
                }
            }
            return b.build();
        }

        private boolean isRowClear(int row) {
            for (int i = 0; i < 3; i++) {
                if (shape[i + row * 3] != null) return false;
            }
            return true;
        }

        private boolean isColClear(int col) {
            for (int i = 0; i < 3; i++) {
                if (shape[col + i * 3] != null) return false;
            }
            return true;
        }

        private List<Integer> getEmptySlots(int offsetX, int offsetY) {
            ImmutableList.Builder<Integer> b = ImmutableList.builder();
            for (int i = 0; i < shape.length; i++) {
                int mappedIndex = i - offsetX - offsetY * 3;
                // empty slot if it either
                // 1) map to a slot outside the original shape
                // 2) map to an empty slot in original shape
                if (mappedIndex < 0 || mappedIndex > 8 || shape[mappedIndex] == null) b.add(i);
            }
            return b.build();
        }
    }
}
