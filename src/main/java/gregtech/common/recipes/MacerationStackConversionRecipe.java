package gregtech.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import gregtech.api.util.GTUtility;

// TODO: Remove in 2.10 when old macerator controller is removed
public class MacerationStackConversionRecipe implements IRecipe {

    public static final MacerationStackConversionRecipe INSTANCE = new MacerationStackConversionRecipe();

    public static void register() {
        CraftingManager.getInstance()
            .getRecipeList()
            .add(INSTANCE);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return getItemCount(inv) == getRecipeSize() && findController(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        if (getItemCount(inv) != getRecipeSize()) return null;

        ItemStack controller = findController(inv);

        if (controller == null) return null;

        return copyUpgradeChip(controller);
    }

    @Override
    public int getRecipeSize() {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    private static int getItemCount(InventoryCrafting inv) {
        int size = inv.getSizeInventory();
        int count = 0;

        for (int i = 0; i < size; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!GTUtility.isStackValid(stack)) continue;
            count++;
        }

        return count;
    }

    private static ItemStack findController(InventoryCrafting inv) {
        int size = inv.getSizeInventory();

        for (int i = 0; i < size; i++) {
            ItemStack stack = inv.getStackInSlot(i);

            return GTUtility.copyAmount(1, stack);
        }

        return null;
    }

    public static ItemStack copyUpgradeChip(@NotNull ItemStack original) {
        return new ItemStack(Blocks.stone);
    }
}
