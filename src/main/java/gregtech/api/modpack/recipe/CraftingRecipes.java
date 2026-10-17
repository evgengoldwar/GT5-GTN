package gregtech.api.modpack.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Crafting table recipes with ore dictionary support. Ingredients can be ItemStack, Item, Block or an ore dictionary
 * name ({@code "plateSteel"}).
 *
 * <pre>{@code
 * CraftingRecipes.shaped(MyMachines.CRUSHER_LV.getStackForm(1),
 *     "PGP",
 *     "MHM",
 *     "CWC",
 *     'P', "plateSteel", 'G', "gearGtSteel", 'H', Hatches.get(HatchType.ENERGY, 1, 1),
 *     'M', MyItems.MOTOR_LV.get(), 'C', "circuitBasic", 'W', Pipes.getCable(Materials.Copper, CableSize.X1, 1));
 *
 * CraftingRecipes.shapeless(Materials.Iron.getNugget(9), "ingotIron");
 * }</pre>
 */
public final class CraftingRecipes {

    private CraftingRecipes() {}

    public static void shaped(ItemStack output, Object... recipe) {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
    }

    public static void shapeless(ItemStack output, Object... ingredients) {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, ingredients));
    }

    public static void smelting(ItemStack input, ItemStack output, float xp) {
        GameRegistry.addSmelting(input, output, xp);
    }
}
