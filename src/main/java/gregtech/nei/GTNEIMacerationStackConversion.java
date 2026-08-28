package gregtech.nei;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;

import codechicken.nei.recipe.ShapelessRecipeHandler;

// TODO: Remove in 2.10
public class GTNEIMacerationStackConversion extends ShapelessRecipeHandler {

    @Override
    public String getOverlayIdentifier() {
        return "gt.recipe.macerator.conversion";
    }

    @Override
    public String getRecipeTabName() {
        return getRecipeName();
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal(getOverlayIdentifier());
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") || outputId.equals(getOverlayIdentifier())) {
            loadAllRecipes();
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (!isNewMacerationStack(result)) return;
        loadRecipeForTier(result);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (!isOldMacerationStack(ingredient)) return;
        loadRecipeForTier(ingredient);
    }

    private void loadRecipeForTier(ItemStack result) {
        loadRecipeT2();
    }

    private void loadAllRecipes() {
        loadRecipeT1();
        loadRecipeT2();
    }

    private void loadRecipeT1() {

    }

    private void loadRecipeT2() {
        ItemStack newController = new ItemStack(Blocks.stone);
        NBTTagCompound display = new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        lore.appendTag(new NBTTagString(StatCollector.translateToLocal("gt.macerator.upgraded")));
        display.setTag("Lore", lore);
        newController.setTagInfo("display", display);
    }

    private static boolean isOldMacerationStack(ItemStack stack) {
        return true;
    }

    private static boolean isNewMacerationStack(ItemStack stack) {
        return true;
    }
}
