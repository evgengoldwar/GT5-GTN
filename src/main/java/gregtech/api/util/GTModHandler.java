package gregtech.api.util;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.GTLoggers.GT_RECIPE_REMOVAL_LOGGER;
import static gregtech.GTLoggers.GT_RECIPE_REMOVAL_LOGGER_ENABLED;
import static gregtech.api.enums.GTValues.B;
import static gregtech.api.enums.GTValues.D1;
import static gregtech.api.enums.GTValues.DW;
import static gregtech.api.enums.GTValues.E;
import static gregtech.api.enums.GTValues.M;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.enums.GTValues.VN;
import static gregtech.api.util.GTRecipeBuilder.PANIC_MODE_NULL;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.jetbrains.annotations.Nullable;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OreDictNames;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.interfaces.IDamagableItem;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.interfaces.internal.IGTCraftingRecipe;
import gregtech.api.metatileentity.implementations.MTEBasicMachineWithRecipe;
import gregtech.api.objects.GTItemStack;
import gregtech.api.objects.ItemData;
import gregtech.common.items.ItemGTToolbox;
import gregtech.common.items.toolbox.ToolboxDelegateInventory;
import gregtech.common.items.toolbox.ToolboxUtil;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import ic2.core.item.ItemToolbox;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the Interface I use for interacting with other Mods.
 * <p/>
 * Due to the many imports, this File can cause compile Problems if not all the APIs are installed
 */
public class GTModHandler {

    public static final List<IRecipe> sSingleNonBlockDamagableRecipeList = new ArrayList<>(1000);
    private static final Map<String, ItemStack> sIC2ItemMap = new HashMap<>();

    public static final List<IRecipe> sBufferRecipeList = new ArrayList<>(1000);

    private static final ObjectOpenCustomHashSet<ItemStack> delayedRemovalByOutput = new ObjectOpenCustomHashSet<>(
        1024,
        GTItemStack.ITEMSTACK_HASH_STRATEGY2);
    private static final Object2IntOpenCustomHashMap<ItemStack> delayedRemovalByOutputFlags = new Object2IntOpenCustomHashMap<>(
        512,
        GTItemStack.ITEMSTACK_HASH_STRATEGY2);
    private static final int DELAYED_REMOVAL_KEEP_SHAPELESS = 1;
    private static final int DELAYED_REMOVAL_ONLY_REMOVE_NATIVE = 2;

    private static final List<InventoryCrafting> delayedRemovalByRecipe = new ArrayList<>();
    private static final List<Exception> delayedRemovalCallsites = new ArrayList<>();

    public static Collection<String> sNativeRecipeClasses = new HashSet<>();
    public static Collection<String> sSpecialRecipeClasses = new HashSet<>();
    public static Collection<GTItemStack> sBoxableItems = new ArrayList<>();

    private static boolean sBufferCraftingRecipes = true;

    static {
        sNativeRecipeClasses.add(ShapedRecipes.class.getName());
        sNativeRecipeClasses.add(ShapedOreRecipe.class.getName());
        sNativeRecipeClasses.add(GTShapedRecipe.class.getName());
        sNativeRecipeClasses.add(ShapelessRecipes.class.getName());
        sNativeRecipeClasses.add(ShapelessOreRecipe.class.getName());
        sNativeRecipeClasses.add(GTShapelessRecipe.class.getName());
        sNativeRecipeClasses.add(ic2.core.AdvRecipe.class.getName());
        sNativeRecipeClasses.add(ic2.core.AdvShapelessRecipe.class.getName());
        sNativeRecipeClasses.add("appeng.recipes.game.ShapedRecipe");
        sNativeRecipeClasses.add("appeng.recipes.game.ShapelessRecipe");
        sNativeRecipeClasses.add("forestry.core.utils.ShapedRecipeCustom");

        // Recipe Classes, which should never be removed.
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipeFireworks.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipesArmorDyes.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipeBookCloning.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipesMapCloning.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipesMapExtending.class.getName());
        sSpecialRecipeClasses.add("jds.bibliocraft.BiblioSpecialRecipes");
        sSpecialRecipeClasses.add("dan200.qcraft.shared.EntangledQBlockRecipe");
        sSpecialRecipeClasses.add("dan200.qcraft.shared.EntangledQuantumComputerRecipe");
        sSpecialRecipeClasses.add("dan200.qcraft.shared.QBlockRecipe");
        sSpecialRecipeClasses.add("appeng.recipes.game.FacadeRecipe");
        sSpecialRecipeClasses.add("appeng.recipes.game.DisassembleRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.carts.LocomotivePaintingRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.RotorRepairRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.RoutingTableCopyRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.RoutingTicketCopyRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.TankCartFilterRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.emblems.LocomotiveEmblemRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.emblems.EmblemPostColorRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.emblems.EmblemPostEmblemRecipe");
        sSpecialRecipeClasses.add("mods.immibis.redlogic.interaction.RecipeDyeLumarButton");
        sSpecialRecipeClasses.add("thaumcraft.common.items.armor.RecipesRobeArmorDyes");
        sSpecialRecipeClasses.add("thaumcraft.common.items.armor.RecipesVoidRobeArmorDyes");
        sSpecialRecipeClasses.add("thaumcraft.common.lib.crafting.ShapelessNBTOreRecipe");
        sSpecialRecipeClasses.add("twilightforest.item.TFMapCloningRecipe");
        sSpecialRecipeClasses.add("forestry.lepidopterology.MatingRecipe");
        sSpecialRecipeClasses.add("micdoodle8.mods.galacticraft.planets.asteroids.recipe.CanisterRecipes");
        sSpecialRecipeClasses.add("shedar.mods.ic2.nuclearcontrol.StorageArrayRecipe");
    }

    public static boolean isWater(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(Materials.Water.getFluid(1));
    }

    public static boolean isSteam(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(Materials.Steam.getGas(1));
    }

    public static ItemStack getIC2Item(String aItem, long aAmount, ItemStack aReplacement) {
        if (GTUtility.isStringInvalid(aItem) || !GregTechAPI.sPreloadStarted) return null;
        // if (D1) GT_FML_LOGGER.debug("Requested the Item '" + aItem + "' from the IC2-API");
        if (!sIC2ItemMap.containsKey(aItem)) {
            ItemStack tStack = IC2Items.getItem(aItem);
            sIC2ItemMap.put(aItem, tStack);
            if (tStack == null && D1) GT_FML_LOGGER.error("{} is not found in the IC2 Items!", aItem);
        }
        return GTUtility.copyAmount(aAmount, sIC2ItemMap.get(aItem), aReplacement);
    }

    /**
     * Gets an Item from IndustrialCraft, but the Damage Value can be specified, and returns a Replacement Item with the
     * same Damage if not possible
     */
    public static ItemStack getIC2Item(String aItem, long aAmount, int aMeta, ItemStack aReplacement) {
        ItemStack rStack = getIC2Item(aItem, aAmount, aReplacement);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    /**
     * Gets an Item from IndustrialCraft, but the Damage Value can be specified
     */
    public static ItemStack getIC2Item(String aItem, long aAmount, int aMeta) {
        return getIC2Item(aItem, aAmount, aMeta, null);
    }

    /**
     * Gets an Item from IndustrialCraft
     */
    public static ItemStack getIC2Item(String aItem, long aAmount) {
        return getIC2Item(aItem, aAmount, null);
    }

    /**
     * Gets an Item from the specified mod
     */
    public static ItemStack getModItem(String aModID, String aItem) {
        return getModItem(aModID, aItem, 1, null);
    }

    /**
     * Gets an Item from the specified mod
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount) {
        return getModItem(aModID, aItem, aAmount, null);
    }

    /**
     * Gets an Item from the specified mod, and returns a Replacement Item if not possible
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount, ItemStack aReplacement) {
        ItemStack result;
        if (GTUtility.isStringInvalid(aItem) || !GregTechAPI.sPreloadStarted) {
            result = null;
        } else {
            result = GTUtility
                .copyAmount(aAmount, GameRegistry.findItemStack(aModID, aItem, (int) aAmount), aReplacement);
        }

        if (result == null) {
            String reason;
            if (GTUtility.isStringInvalid(aItem)) {
                reason = "the name of the item is an invalid string";
            } else if (!GregTechAPI.sPreloadStarted) {
                reason = "the GT5U preloading phase has not yet started";
            } else {
                reason = "the item was not found in the game registry";
            }
            String log_message = "getModItem call: object \"" + aItem
                + "\" with mod id \""
                + aModID
                + "\" has returned null because "
                + reason;
            if (PANIC_MODE_NULL) {
                GT_FML_LOGGER.fatal(log_message, new Exception());
            } else {
                GT_FML_LOGGER.info(log_message, new Exception());
            }
        }
        return result;
    }

    /**
     * Gets an Item from the specified mod, but the Damage Value can be specified
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount, int aMeta) {
        ItemStack rStack = getModItem(aModID, aItem, aAmount);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    /**
     * Gets an Item from the specified mod, but the Damage Value can be specified, and returns a Replacement Item with
     * the same Damage if not possible
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount, int aMeta, ItemStack aReplacement) {
        ItemStack rStack = getModItem(aModID, aItem, aAmount, aReplacement);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    /**
     * Adds a Scrapbox Drop. Fails at April first for the "suddenly Hoes"-Feature of IC2
     */
    public static boolean addScrapboxDrop(float aChance, ItemStack aOutput) {
        aOutput = GTOreDictUnificator.get(true, aOutput);
        if (aOutput == null || aChance <= 0) return false;
        aOutput.stackSize = 1;
        if (GTConfig.troll && !GTUtility.areStacksEqual(aOutput, new ItemStack(Items.wooden_hoe, 1, 0))) return false;
        Recipes.scrapboxDrops.addDrop(GTUtility.copyOrNull(aOutput), aChance);
        return true;
    }

    // temporary buffer list to fix NPE if you try to access the recyclerBlacklist too early
    private static List<RecipeInputItemStack> tempRecyclerBlackList = new ArrayList<>();

    /**
     * Adds an Item to the Recycler Blacklist
     */
    public static boolean addToRecyclerBlackList(ItemStack aRecycledStack) {
        if (aRecycledStack == null) return false;
        final RecipeInputItemStack iRecipeInput = new RecipeInputItemStack(aRecycledStack);
        if (Recipes.recyclerBlacklist == null) {
            tempRecyclerBlackList.add(iRecipeInput);
            return true;
        }
        if (tempRecyclerBlackList != null) {
            for (RecipeInputItemStack recipe : tempRecyclerBlackList) {
                Recipes.recyclerBlacklist.add(recipe);
            }
            tempRecyclerBlackList = null;
        }
        Recipes.recyclerBlacklist.add(iRecipeInput);
        return true;
    }

    /**
     * Just simple Furnace smelting. Unbelievable how Minecraft fails at making a simple ItemStack->ItemStack mapping...
     */
    public static boolean addSmeltingRecipe(ItemStack input, ItemStack output) {
        return addSmeltingRecipe(input, output, 0.0F);
    }

    /**
     * Just simple Furnace smelting. Unbelievable how Minecraft fails at making a simple ItemStack->ItemStack mapping...
     */
    public static boolean addSmeltingRecipe(ItemStack input, ItemStack output, float xp) {
        output = GTOreDictUnificator.get(true, output);
        if (input == null || output == null) return false;
        FurnaceRecipes.smelting()
            .func_151394_a(input, GTUtility.copyOrNull(output), xp);
        return true;
    }

    /**
     * This was supposed to be called once, but since now there is a second buffering pass, this will now be called too
     * at the end of GT5U's load complete if NHCore isn't present, otherwise NHCore will call it in its own load
     * complete phase, as it runs after GT5U and has delayed removals.
     */
    public static void stopBufferingCraftingRecipes() {
        sBufferCraftingRecipes = false;

        bulkRemoveRecipeByOutput();
        bulkRemoveByRecipe();
        sBufferRecipeList.forEach(GameRegistry::addRecipe);

        delayedRemovalByOutput.clear();
        delayedRemovalByOutputFlags.clear();
        delayedRemovalByRecipe.clear();
        delayedRemovalCallsites.clear();
        sBufferRecipeList.clear();
    }

    /**
     * This is simply allowed because who knows what side effects there are if we just switch the
     * {@link #stopBufferingCraftingRecipes()} from end of postinit to end of load complete. So to
     * play safe, we restart the buffer to catch all the calls to delayed removals happening after
     * GT5U's post init. If later it is proven that it is safe to move the {@link #stopBufferingCraftingRecipes()}
     * to load complete, then feel free to remove this dirty hack.
     */
    public static void restartBufferingCraftingRecipe() {
        sBufferCraftingRecipes = true;
    }

    /**
     * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
     */
    public static boolean addCraftingRecipe(ItemStack aResult, Enchantment[] aEnchantmentsAdded,
        int[] aEnchantmentLevelsAdded, Object[] aRecipe) {
        return addCraftingRecipe(
            aResult,
            aEnchantmentsAdded,
            aEnchantmentLevelsAdded,
            false,
            true,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            true,
            true,
            aRecipe);
    }

    /**
     * Regular Crafting Recipes. Deletes conflicting Recipes too.
     * <p/>
     * You can insert instances of IItemContainer into the Recipe Input Array directly without having to call "get(1)"
     * on them.
     * <p/>
     * Enums are automatically getting their "name()"-Method called in order to deliver an OreDict String.
     * <p/>
     * Lowercase Letters are reserved for Tools. They are as follows:
     * <p/>
     * 'b' ToolDictNames.craftingToolBlade 'c' ToolDictNames.craftingToolCrowbar, 'd'
     * ToolDictNames.craftingToolScrewdriver, 'f' ToolDictNames.craftingToolFile, 'h'
     * ToolDictNames.craftingToolHardHammer, 'i' ToolDictNames.craftingToolSolderingIron, 'j'
     * ToolDictNames.craftingToolSolderingMetal, 'k' ToolDictNames.craftingToolKnive 'm'
     * ToolDictNames.craftingToolMortar, 'p' ToolDictNames.craftingToolDrawplate, 'r'
     * ToolDictNames.craftingToolSoftMallet, 's' ToolDictNames.craftingToolSaw, 'w' ToolDictNames.craftingToolWrench,
     * 'x' ToolDictNames.craftingToolWireCutter,
     */
    public static boolean addCraftingRecipe(ItemStack aResult, Object[] aRecipe) {
        return addCraftingRecipe(aResult, 0, aRecipe);
    }

    /**
     * Regular Crafting Recipes. Deletes conflicting Recipes too.
     * <p/>
     * You can insert instances of IItemContainer into the Recipe Input Array directly without having to call "get(1)"
     * on them.
     * <p/>
     * Enums are automatically getting their "name()"-Method called in order to deliver an OreDict String.
     * <p/>
     * Lowercase Letters are reserved for Tools. They are as follows:
     * <p/>
     * 'b' ToolDictNames.craftingToolBlade 'c' ToolDictNames.craftingToolCrowbar, 'd'
     * ToolDictNames.craftingToolScrewdriver, 'f' ToolDictNames.craftingToolFile, 'h'
     * ToolDictNames.craftingToolHardHammer, 'i' ToolDictNames.craftingToolSolderingIron, 'j'
     * ToolDictNames.craftingToolSolderingMetal, 'k' ToolDictNames.craftingToolKnive 'm'
     * ToolDictNames.craftingToolMortar, 'p' ToolDictNames.craftingToolDrawplate, 'r'
     * ToolDictNames.craftingToolSoftMallet, 's' ToolDictNames.craftingToolSaw, 'w' ToolDictNames.craftingToolWrench,
     * 'x' ToolDictNames.craftingToolWireCutter,
     */
    public static boolean addCraftingRecipe(ItemStack aResult, long aBitMask, Object[] aRecipe) {
        return addCraftingRecipe(
            aResult,
            null,
            null,
            (aBitMask & RecipeBits.MIRRORED) != 0,
            (aBitMask & RecipeBits.BUFFERED) != 0,
            (aBitMask & RecipeBits.KEEPNBT) != 0,
            (aBitMask & RecipeBits.NOT_REMOVABLE) == 0,
            (aBitMask & RecipeBits.REVERSIBLE) != 0,
            (aBitMask & RecipeBits.DELETE_ALL_OTHER_RECIPES) != 0,
            (aBitMask & RecipeBits.DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT) != 0,
            (aBitMask & RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES) != 0,
            (aBitMask & RecipeBits.DELETE_ALL_OTHER_NATIVE_RECIPES) != 0,
            (aBitMask & RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS) == 0,
            (aBitMask & RecipeBits.ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT) != 0,
            (aBitMask & RecipeBits.ONLY_ADD_IF_RESULT_IS_NOT_NULL) != 0,
            (aBitMask & RecipeBits.DONT_UNIFY_OUTPUT) == 0,
            aRecipe);
    }

    public static void addMachineCraftingRecipe(ItemStack aResult, Object @Nullable [] aRecipe, int machineTier) {
        addMachineCraftingRecipe(
            aResult,
            RecipeBits.BITS | RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            aRecipe,
            machineTier);
    }

    public static void addMachineCraftingRecipe(ItemStack aResult, long aBitMask, Object @Nullable [] aRecipe,
        int machineTier) {
        if (aRecipe == null) return;

        for (int i = 3; i < aRecipe.length; i++) {
            if (!(aRecipe[i] instanceof MTEBasicMachineWithRecipe.X)) continue;

            // spotless:off
            aRecipe[i] = switch ((MTEBasicMachineWithRecipe.X) aRecipe[i]) {
                case CIRCUIT -> switch (machineTier) {
                    case  0 -> OrePrefixes.circuit.get(Materials.ULV);
                    case  1 -> OrePrefixes.circuit.get(Materials.LV);
                    case  2 -> OrePrefixes.circuit.get(Materials.MV);
                    case  3 -> OrePrefixes.circuit.get(Materials.HV);
                    case  4 -> OrePrefixes.circuit.get(Materials.EV);
                    case  5 -> OrePrefixes.circuit.get(Materials.IV);
                    case  6 -> OrePrefixes.circuit.get(Materials.LuV);
                    case  7 -> OrePrefixes.circuit.get(Materials.ZPM);
                    case  8 -> OrePrefixes.circuit.get(Materials.UV);
                    default -> OrePrefixes.circuit.get(Materials.UHV);
                };

                case BETTER_CIRCUIT -> switch (machineTier) {
                    case  0 -> OrePrefixes.circuit.get(Materials.LV);
                    case  1 -> OrePrefixes.circuit.get(Materials.MV);
                    case  2 -> OrePrefixes.circuit.get(Materials.HV);
                    case  3 -> OrePrefixes.circuit.get(Materials.EV);
                    case  4 -> OrePrefixes.circuit.get(Materials.IV);
                    case  5 -> OrePrefixes.circuit.get(Materials.LuV);
                    case  6 -> OrePrefixes.circuit.get(Materials.ZPM);
                    case  7 -> OrePrefixes.circuit.get(Materials.UV);
                    default -> OrePrefixes.circuit.get(Materials.UHV);
                };

                case HULL -> switch (machineTier) {
                    case  0 -> ItemList.Hull_ULV;
                    case  1 -> ItemList.Hull_LV;
                    case  2 -> ItemList.Hull_MV;
                    case  3 -> ItemList.Hull_HV;
                    case  4 -> ItemList.Hull_EV;
                    case  5 -> ItemList.Hull_IV;
                    case  6 -> ItemList.Hull_LuV;
                    case  7 -> ItemList.Hull_ZPM;
                    case  8 -> ItemList.Hull_UV;
                    default -> ItemList.Hull_MAX;
                };

                case WIRE -> switch (machineTier) {
                    case  0 -> OrePrefixes.cableGt01.get(Materials.Lead);
                    case  1 -> OrePrefixes.cableGt01.get(Materials.Tin);
                    case  2 -> OrePrefixes.cableGt01.get(Materials.AnyCopper);
                    case  3 -> OrePrefixes.cableGt01.get(Materials.Gold);
                    case  4 -> OrePrefixes.cableGt01.get(Materials.Aluminium);
                    case  5 -> OrePrefixes.cableGt01.get(Materials.Platinum);
                    case  6 -> OrePrefixes.cableGt01.get(Materials.NiobiumTitanium);
                    case  7 -> OrePrefixes.cableGt01.get(Materials.Naquadah);
                    case  8 -> OrePrefixes.cableGt04.get(Materials.NaquadahAlloy);
                    default -> OrePrefixes.wireGt01.get(Materials.SuperconductorUHV);
                };

                case WIRE4 -> switch (machineTier) {
                    case  0 -> OrePrefixes.cableGt04.get(Materials.Lead);
                    case  1 -> OrePrefixes.cableGt04.get(Materials.Tin);
                    case  2 -> OrePrefixes.cableGt04.get(Materials.AnyCopper);
                    case  3 -> OrePrefixes.cableGt04.get(Materials.Gold);
                    case  4 -> OrePrefixes.cableGt04.get(Materials.Aluminium);
                    case  5 -> OrePrefixes.cableGt04.get(Materials.Platinum);
                    case  6 -> OrePrefixes.cableGt04.get(Materials.NiobiumTitanium);
                    case  7 -> OrePrefixes.cableGt04.get(Materials.Naquadah);
                    case  8 -> OrePrefixes.wireGt01.get(Materials.SuperconductorUHV);
                    default -> OrePrefixes.wireGt04.get(Materials.SuperconductorUHV);
                };

                case STICK_DISTILLATION -> OrePrefixes.stick.get(Materials.Blaze);

                case GLASS -> switch (machineTier) {
                    case 0, 1, 2, 3    -> new ItemStack(Blocks.glass, 1, WILDCARD);
                    case 4, 5, 6, 7, 8 -> "blockGlass" + VN[machineTier];
                    default            -> "blockGlass" + VN[8];
                };

                case PLATE -> switch (machineTier) {
                    case 0, 1 -> OrePrefixes.plate.get(Materials.Steel);
                    case 2    -> OrePrefixes.plate.get(Materials.Aluminium);
                    case 3    -> OrePrefixes.plate.get(Materials.StainlessSteel);
                    case 4    -> OrePrefixes.plate.get(Materials.Titanium);
                    case 5    -> OrePrefixes.plate.get(Materials.TungstenSteel);
                    case 6    -> OrePrefixes.plate.get(Materials.HSSG);
                    case 7    -> OrePrefixes.plate.get(Materials.HSSE);
                    default   -> OrePrefixes.plate.get(Materials.Neutronium);
                };

                case PIPE -> switch (machineTier) {
                    case 0, 1 -> OrePrefixes.pipeMedium.get(Materials.Bronze);
                    case 2    -> OrePrefixes.pipeMedium.get(Materials.Steel);
                    case 3    -> OrePrefixes.pipeMedium.get(Materials.StainlessSteel);
                    case 4    -> OrePrefixes.pipeMedium.get(Materials.Titanium);
                    case 5    -> OrePrefixes.pipeMedium.get(Materials.TungstenSteel);
                    case 6    -> OrePrefixes.pipeSmall.get(Materials.ZPM);
                    case 7    -> OrePrefixes.pipeMedium.get(Materials.ZPM);
                    case 8    -> OrePrefixes.pipeLarge.get(Materials.ZPM);
                    default   -> OrePrefixes.pipeHuge.get(Materials.ZPM);
                };

                case COIL_HEATING -> switch (machineTier) {
                    case 0, 1 -> OrePrefixes.wireGt02.get(Materials.AnyCopper);
                    case 2    -> OrePrefixes.wireGt02.get(Materials.Cupronickel);
                    case 3    -> OrePrefixes.wireGt02.get(Materials.Kanthal);
                    case 4    -> OrePrefixes.wireGt02.get(Materials.Nichrome);
                    case 5    -> OrePrefixes.wireGt02.get(Materials.TPV);
                    case 6    -> OrePrefixes.wireGt02.get(Materials.HSSG);
                    case 7    -> OrePrefixes.wireGt02.get(Materials.Naquadah);
                    case 8    -> OrePrefixes.wireGt02.get(Materials.NaquadahAlloy);
                    case 9    -> OrePrefixes.wireGt04.get(Materials.NaquadahAlloy);
                    default   -> OrePrefixes.wireGt08.get(Materials.NaquadahAlloy);
                };

                case COIL_HEATING_DOUBLE -> switch (machineTier) {
                    case 0, 1 -> OrePrefixes.wireGt04.get(Materials.AnyCopper);
                    case 2    -> OrePrefixes.wireGt04.get(Materials.Cupronickel);
                    case 3    -> OrePrefixes.wireGt04.get(Materials.Kanthal);
                    case 4    -> OrePrefixes.wireGt04.get(Materials.Nichrome);
                    case 5    -> OrePrefixes.wireGt04.get(Materials.TPV);
                    case 6    -> OrePrefixes.wireGt04.get(Materials.HSSG);
                    case 7    -> OrePrefixes.wireGt04.get(Materials.Naquadah);
                    case 8    -> OrePrefixes.wireGt04.get(Materials.NaquadahAlloy);
                    case 9    -> OrePrefixes.wireGt08.get(Materials.NaquadahAlloy);
                    default   -> OrePrefixes.wireGt16.get(Materials.NaquadahAlloy);
                };

                case STICK_MAGNETIC -> switch (machineTier) {
                    case 0, 1       -> OrePrefixes.stick.get(Materials.IronMagnetic);
                    case 2, 3       -> OrePrefixes.stick.get(Materials.SteelMagnetic);
                    case 4, 5       -> OrePrefixes.stick.get(Materials.NeodymiumMagnetic);
                    case 6, 7, 8, 9 -> OrePrefixes.stick.get(Materials.SamariumMagnetic);
                    default         -> OrePrefixes.stick.get(Materials.TengamAttuned);
                };

                case STICK_ELECTROMAGNETIC -> switch (machineTier) {
                    case 0, 1 -> OrePrefixes.stick.get(Materials.AnyIron);
                    case 2, 3 -> OrePrefixes.stick.get(Materials.Steel);
                    case 4    -> OrePrefixes.stick.get(Materials.Neodymium);
                    default   -> OrePrefixes.stick.get(Materials.VanadiumGallium);
                };

                case COIL_ELECTRIC -> switch (machineTier) {
                    case 0  -> OrePrefixes.wireGt01.get(Materials.Lead);
                    case 1  -> OrePrefixes.wireGt02.get(Materials.Tin);
                    case 2  -> OrePrefixes.wireGt02.get(Materials.AnyCopper);
                    case 3  -> OrePrefixes.wireGt04.get(Materials.AnyCopper);
                    case 4  -> OrePrefixes.wireGt08.get(Materials.AnnealedCopper);
                    case 5  -> OrePrefixes.wireGt16.get(Materials.AnnealedCopper);
                    case 6  -> OrePrefixes.wireGt04.get(Materials.YttriumBariumCuprate);
                    case 7  -> OrePrefixes.wireGt08.get(Materials.Iridium);
                    default -> OrePrefixes.wireGt16.get(Materials.Osmium);
                };

                case ROBOT_ARM -> switch (machineTier) {
                    case 0, 1 -> ItemList.Robot_Arm_LV;
                    case 2    -> ItemList.Robot_Arm_MV;
                    case 3    -> ItemList.Robot_Arm_HV;
                    case 4    -> ItemList.Robot_Arm_EV;
                    case 5    -> ItemList.Robot_Arm_IV;
                    case 6    -> ItemList.Robot_Arm_LuV;
                    case 7    -> ItemList.Robot_Arm_ZPM;
                    case 8    -> ItemList.Robot_Arm_UV;
                    case 9    -> ItemList.Robot_Arm_UHV;
                    case 10   -> ItemList.Robot_Arm_UEV;
                    case 11   -> ItemList.Robot_Arm_UIV;
                    case 12   -> ItemList.Robot_Arm_UMV;
                    case 13   -> ItemList.Robot_Arm_UXV;
                    default   ->  ItemList.Robot_Arm_MAX;
                };

                case PUMP -> switch (machineTier) {
                    case 0, 1 -> ItemList.Electric_Pump_LV;
                    case 2    -> ItemList.Electric_Pump_MV;
                    case 3    -> ItemList.Electric_Pump_HV;
                    case 4    -> ItemList.Electric_Pump_EV;
                    case 5    -> ItemList.Electric_Pump_IV;
                    case 6    -> ItemList.Electric_Pump_LuV;
                    case 7    -> ItemList.Electric_Pump_ZPM;
                    case 8    -> ItemList.Electric_Pump_UV;
                    case 9    -> ItemList.Electric_Pump_UHV;
                    case 10   -> ItemList.Electric_Pump_UEV;
                    case 11   -> ItemList.Electric_Pump_UIV;
                    case 12   -> ItemList.Electric_Pump_UMV;
                    case 13   -> ItemList.Electric_Pump_UXV;
                    default   -> ItemList.Electric_Pump_MAX;
                };

                case MOTOR -> switch (machineTier) {
                    case 0, 1 -> ItemList.Electric_Motor_LV;
                    case 2    -> ItemList.Electric_Motor_MV;
                    case 3    -> ItemList.Electric_Motor_HV;
                    case 4    -> ItemList.Electric_Motor_EV;
                    case 5    -> ItemList.Electric_Motor_IV;
                    case 6    -> ItemList.Electric_Motor_LuV;
                    case 7    -> ItemList.Electric_Motor_ZPM;
                    case 8    -> ItemList.Electric_Motor_UV;
                    case 9    -> ItemList.Electric_Motor_UHV;
                    case 10   -> ItemList.Electric_Motor_UEV;
                    case 11   -> ItemList.Electric_Motor_UIV;
                    case 12   -> ItemList.Electric_Motor_UMV;
                    case 13   -> ItemList.Electric_Motor_UXV;
                    default   -> ItemList.Electric_Motor_MAX;
                };

                case PISTON -> switch (machineTier) {
                    case 0, 1 -> ItemList.Electric_Piston_LV;
                    case 2    -> ItemList.Electric_Piston_MV;
                    case 3    -> ItemList.Electric_Piston_HV;
                    case 4    -> ItemList.Electric_Piston_EV;
                    case 5    -> ItemList.Electric_Piston_IV;
                    case 6    -> ItemList.Electric_Piston_LuV;
                    case 7    -> ItemList.Electric_Piston_ZPM;
                    case 8    -> ItemList.Electric_Piston_UV;
                    case 9    -> ItemList.Electric_Piston_UHV;
                    case 10   -> ItemList.Electric_Piston_UEV;
                    case 11   -> ItemList.Electric_Piston_UIV;
                    case 12   -> ItemList.Electric_Piston_UMV;
                    case 13   -> ItemList.Electric_Piston_UXV;
                    default   -> ItemList.Electric_Piston_MAX;
                };

                case CONVEYOR -> switch (machineTier) {
                    case 0, 1 -> ItemList.Conveyor_Module_LV;
                    case 2    -> ItemList.Conveyor_Module_MV;
                    case 3    -> ItemList.Conveyor_Module_HV;
                    case 4    -> ItemList.Conveyor_Module_EV;
                    case 5    -> ItemList.Conveyor_Module_IV;
                    case 6    -> ItemList.Conveyor_Module_LuV;
                    case 7    -> ItemList.Conveyor_Module_ZPM;
                    case 8    -> ItemList.Conveyor_Module_UV;
                    case 9    -> ItemList.Conveyor_Module_UHV;
                    case 10   -> ItemList.Conveyor_Module_UEV;
                    case 11   -> ItemList.Conveyor_Module_UIV;
                    case 12   -> ItemList.Conveyor_Module_UMV;
                    case 13   -> ItemList.Conveyor_Module_UXV;
                    default   -> ItemList.Conveyor_Module_MAX;
                };

                case EMITTER -> switch (machineTier) {
                    case 0, 1 -> ItemList.Emitter_LV;
                    case 2    -> ItemList.Emitter_MV;
                    case 3    -> ItemList.Emitter_HV;
                    case 4    -> ItemList.Emitter_EV;
                    case 5    -> ItemList.Emitter_IV;
                    case 6    -> ItemList.Emitter_LuV;
                    case 7    -> ItemList.Emitter_ZPM;
                    case 8    -> ItemList.Emitter_UV;
                    case 9    -> ItemList.Emitter_UHV;
                    case 10   -> ItemList.Emitter_UEV;
                    case 11   -> ItemList.Emitter_UIV;
                    case 12   -> ItemList.Emitter_UMV;
                    case 13   -> ItemList.Emitter_UXV;
                    default   -> ItemList.Emitter_MAX;
                };

                case SENSOR -> switch (machineTier) {
                    case 0, 1 -> ItemList.Sensor_LV;
                    case 2    -> ItemList.Sensor_MV;
                    case 3    -> ItemList.Sensor_HV;
                    case 4    -> ItemList.Sensor_EV;
                    case 5    -> ItemList.Sensor_IV;
                    case 6    -> ItemList.Sensor_LuV;
                    case 7    -> ItemList.Sensor_ZPM;
                    case 8    -> ItemList.Sensor_UV;
                    case 9    -> ItemList.Sensor_UHV;
                    case 10   -> ItemList.Sensor_UEV;
                    case 11   -> ItemList.Sensor_UIV;
                    case 12   -> ItemList.Sensor_UMV;
                    case 13   -> ItemList.Sensor_UXV;
                    default   -> ItemList.Sensor_MAX;
                };

                case FIELD_GENERATOR -> switch (machineTier) {
                    case 0, 1 -> ItemList.Field_Generator_LV;
                    case 2    -> ItemList.Field_Generator_MV;
                    case 3    -> ItemList.Field_Generator_HV;
                    case 4    -> ItemList.Field_Generator_EV;
                    case 5    -> ItemList.Field_Generator_IV;
                    case 6    -> ItemList.Field_Generator_LuV;
                    case 7    -> ItemList.Field_Generator_ZPM;
                    case 8    -> ItemList.Field_Generator_UV;
                    case 9    -> ItemList.Field_Generator_UHV;
                    case 10   -> ItemList.Field_Generator_UEV;
                    case 11   -> ItemList.Field_Generator_UIV;
                    case 12   -> ItemList.Field_Generator_UMV;
                    case 13   -> ItemList.Field_Generator_UXV;
                    default   -> ItemList.Field_Generator_MAX;
                };

                case ROTOR -> switch (machineTier) {
                    case 0, 1 -> OrePrefixes.rotor.get(Materials.Tin);
                    case 2    -> OrePrefixes.rotor.get(Materials.Bronze);
                    case 3    -> OrePrefixes.rotor.get(Materials.Steel);
                    case 4    -> OrePrefixes.rotor.get(Materials.StainlessSteel);
                    case 5    -> OrePrefixes.rotor.get(Materials.TungstenSteel);
                    case 7    -> OrePrefixes.rotor.get(Materials.Iridium);
                    default   -> OrePrefixes.rotor.get(Materials.Osmium);
                };

                default -> throw new IllegalArgumentException("MISSING TIER MAPPING FOR: " + aRecipe[i] + " AT TIER " + machineTier);
            };
            // spotless:on
        }

        if (!GTModHandler.addCraftingRecipe(aResult, aBitMask, aRecipe)) {
            throw new IllegalArgumentException("INVALID CRAFTING RECIPE FOR: " + aResult.getDisplayName());
        }
    }

    /**
     * Internal realisation of the Crafting Recipe adding Process.
     */
    private static boolean addCraftingRecipe(ItemStack aResult, Enchantment[] aEnchantmentsAdded,
        int[] aEnchantmentLevelsAdded, boolean aMirrored, boolean aBuffered, boolean aKeepNBT, boolean aRemovable,
        boolean aReversible, boolean aRemoveAllOthersWithSameOutput,
        boolean aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT, boolean aRemoveAllOtherShapedsWithSameOutput,
        boolean aRemoveAllOtherNativeRecipes, boolean aCheckForCollisions,
        boolean aOnlyAddIfThereIsAnyRecipeOutputtingThis, boolean aOnlyAddIfResultIsNotNull, boolean unifyOutput,
        Object[] aRecipe) {

        if (unifyOutput) aResult = GTOreDictUnificator.get(true, aResult);
        if (aOnlyAddIfResultIsNotNull && aResult == null) return false;
        if (aResult != null && Items.feather.getDamage(aResult) == WILDCARD) Items.feather.setDamage(aResult, 0);
        if (aRecipe == null || aRecipe.length == 0) return false;

        // The renamed variable clarifies what's happening
        // noinspection UnnecessaryLocalVariable
        boolean tDoWeCareIfThereWasARecipe = aOnlyAddIfThereIsAnyRecipeOutputtingThis;
        boolean tThereWasARecipe = false;

        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] instanceof IItemContainer) aRecipe[i] = ((IItemContainer) aRecipe[i]).get(1);
            else if (aRecipe[i] instanceof Enum) aRecipe[i] = ((Enum<?>) aRecipe[i]).name();
            else if (!(aRecipe[i] == null || aRecipe[i] instanceof ItemStack
                || aRecipe[i] instanceof ItemData
                || aRecipe[i] instanceof String
                || aRecipe[i] instanceof Character)) aRecipe[i] = aRecipe[i].toString();
        }

        StringBuilder shape = new StringBuilder(E);
        int idx = 0;
        if (aRecipe[idx] instanceof Boolean) {
            throw new IllegalArgumentException();
        }

        ArrayList<Object> tRecipeList = new ArrayList<>(Arrays.asList(aRecipe));

        while (aRecipe[idx] instanceof String) {
            StringBuilder s = new StringBuilder((String) aRecipe[idx++]);
            shape.append(s);
            while (s.length() < 3) s.append(" ");
            if (s.length() > 3) throw new IllegalArgumentException();

            for (char c : s.toString()
                .toCharArray()) {
                switch (c) {
                    case 'b' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolBlade.name());
                    }
                    case 'c' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolCrowbar.name());
                    }
                    case 'd' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolScrewdriver.name());
                    }
                    case 'f' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolFile.name());
                    }
                    case 'h' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolHardHammer.name());
                    }
                    case 'i' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolSolderingIron.name());
                    }
                    case 'j' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolSolderingMetal.name());
                    }
                    case 'k' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolKnife.name());
                    }
                    case 'm' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolMortar.name());
                    }
                    case 'p' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolDrawplate.name());
                    }
                    case 'r' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolSoftMallet.name());
                    }
                    case 's' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolSaw.name());
                    }
                    case 'w' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolWrench.name());
                    }
                    case 'x' -> {
                        tRecipeList.add(c);
                        tRecipeList.add(ToolDictNames.craftingToolWireCutter.name());
                    }
                }
            }
        }

        aRecipe = tRecipeList.toArray();

        if (aRecipe[idx] instanceof Boolean) {
            idx++;
        }
        Map<Character, ItemStack> tItemStackMap = new HashMap<>();
        Map<Character, ItemData> tItemDataMap = new HashMap<>();
        tItemStackMap.put(' ', null);

        boolean tRemoveRecipe = true;

        for (; idx < aRecipe.length; idx += 2) {
            if (aRecipe[idx] == null || aRecipe[idx + 1] == null) {
                if (D1) {
                    GT_FML_LOGGER.error("WARNING: Missing Item for shaped Recipe: {}", aResult == null ? "null" : aResult.getDisplayName());
                    for (Object tContent : aRecipe) GT_FML_LOGGER.error(tContent);
                }
                return false;
            }
            Character chr = (Character) aRecipe[idx];
            Object in = aRecipe[idx + 1];
            switch (in) {
                case ItemStack is -> {
                    tItemStackMap.put(chr, GTUtility.copyOrNull(is));
                    tItemDataMap.put(chr, GTOreDictUnificator.getItemData(is));
                }
                case ItemData itemData -> {
                    String tString = in.toString();
                    switch (tString) {
                        case "plankWood" -> tItemDataMap.put(chr, new ItemData(Materials.Wood, M));
                        case "stoneNetherrack" -> tItemDataMap.put(chr, new ItemData(Materials.Netherrack, M));
                        case "stoneObsidian" -> tItemDataMap.put(chr, new ItemData(Materials.Obsidian, M));
                        case "stoneEndstone" -> tItemDataMap.put(chr, new ItemData(Materials.Endstone, M));
                        default -> tItemDataMap.put(chr, itemData);
                    }
                    ItemStack tStack = GTOreDictUnificator.getFirstOre(in, 1);
                    if (tStack == null) tRemoveRecipe = false;
                    else tItemStackMap.put(chr, tStack);
                    in = aRecipe[idx + 1] = in.toString();
                }
                case String s -> {
                    if (in.equals(OreDictNames.craftingChest.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Wood, M * 8));
                    else if (in.equals(OreDictNames.craftingBook.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Paper, M * 3));
                    else if (in.equals(OreDictNames.craftingPiston.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Stone, M * 4, Materials.Wood, M * 3));
                    else if (in.equals(OreDictNames.craftingFurnace.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Stone, M * 8));
                    else if (in.equals(OreDictNames.craftingIndustrialDiamond.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Diamond, M));
                    else if (in.equals(OreDictNames.craftingAnvil.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Iron, M * 10));
                    ItemStack tStack = GTOreDictUnificator.getFirstOre(in, 1);
                    if (tStack == null) tRemoveRecipe = false;
                    else tItemStackMap.put(chr, tStack);
                }
                case null, default -> throw new IllegalArgumentException();
            }
        }

        if (aReversible && aResult != null) {
            ItemData[] tData = new ItemData[9];
            int x = -1;
            for (char chr : shape.toString()
                .toCharArray()) tData[++x] = tItemDataMap.get(chr);
            if (GTUtility.arrayContainsNonNull(tData)) GTOreDictUnificator.addItemData(aResult, new ItemData(tData));
        }

        if (aCheckForCollisions && tRemoveRecipe) {
            ItemStack[] tRecipe = new ItemStack[9];
            int x = -1;
            for (char chr : shape.toString()
                .toCharArray()) {
                tRecipe[++x] = tItemStackMap.get(chr);
                if (tRecipe[x] != null && Items.feather.getDamage(tRecipe[x]) == WILDCARD)
                    Items.feather.setDamage(tRecipe[x], 0);
            }
            if (tDoWeCareIfThereWasARecipe || !aBuffered) tThereWasARecipe = removeRecipe(tRecipe) != null;
            else removeRecipeDelayed(tRecipe);
        }

        if (aResult == null || aResult.stackSize <= 0) return false;

        if (aRemoveAllOthersWithSameOutput || aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT
            || aRemoveAllOtherShapedsWithSameOutput
            || aRemoveAllOtherNativeRecipes) {
            if (tDoWeCareIfThereWasARecipe || !aBuffered) tThereWasARecipe = removeRecipeByOutput(
                aResult,
                !aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT,
                aRemoveAllOtherShapedsWithSameOutput,
                aRemoveAllOtherNativeRecipes) || tThereWasARecipe;
            else removeRecipeByOutputDelayed(
                aResult,
                !aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT,
                aRemoveAllOtherShapedsWithSameOutput,
                aRemoveAllOtherNativeRecipes);
        }

        if (aOnlyAddIfThereIsAnyRecipeOutputtingThis && !tDoWeCareIfThereWasARecipe && !tThereWasARecipe) {
            ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance()
                .getRecipeList();
            int tList_sS = tList.size();
            for (int i = 0; i < tList_sS && !tThereWasARecipe; i++) {
                IRecipe tRecipe = tList.get(i);
                if (sSpecialRecipeClasses.contains(
                    tRecipe.getClass()
                        .getName()))
                    continue;
                if (GTUtility.areStacksEqual(GTOreDictUnificator.get(tRecipe.getRecipeOutput()), aResult, true)) {
                    tList.remove(i--);
                    tList_sS = tList.size();
                    tThereWasARecipe = true;
                }
            }
        }

        if (Items.feather.getDamage(aResult) == WILDCARD || Items.feather.getDamage(aResult) < 0)
            Items.feather.setDamage(aResult, 0);

        GTUtility.updateItemStack(aResult);

        if (tThereWasARecipe || !aOnlyAddIfThereIsAnyRecipeOutputtingThis) {
            if (sBufferCraftingRecipes && aBuffered) sBufferRecipeList.add(
                new GTShapedRecipe(
                    GTUtility.copyOrNull(aResult),
                    aRemovable,
                    aKeepNBT,
                    aEnchantmentsAdded,
                    aEnchantmentLevelsAdded,
                    aRecipe).setMirrored(aMirrored));
            else GameRegistry.addRecipe(
                new GTShapedRecipe(
                    GTUtility.copyOrNull(aResult),
                    aRemovable,
                    aKeepNBT,
                    aEnchantmentsAdded,
                    aEnchantmentLevelsAdded,
                    aRecipe).setMirrored(aMirrored));
        }
        return true;
    }

    public static ItemStack removeRecipe(ItemStack... shape) {
        return removeRecipe(shape, null);
    }

    static ItemStack removeRecipe(ItemStack[] shape, IdentityHashMap<IRecipe, Boolean> knownMatches) {
        if (shape == null || isAllNulls(shape)) {
            if (GT_RECIPE_REMOVAL_LOGGER_ENABLED) {
                GT_RECIPE_REMOVAL_LOGGER.error(
                    "removeRecipe rejected empty or null-only crafting inputs; call site follows",
                    new Exception("Rejected crafting inputs: " + Arrays.toString(shape)));
            }
            return null;
        }

        InventoryCrafting craftMatrix = new InventoryCrafting(new Container() {

            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < shape.length && i < 9; i++) {
            craftMatrix.setInventorySlotContents(i, shape[i]);
        }

        ArrayList<IRecipe> allRecipes = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();
        ItemStack rReturn = removeMatchingRecipes(allRecipes, craftMatrix, knownMatches);

        if (rReturn == null) {
            GT_RECIPE_REMOVAL_LOGGER.warn(
                "No existing removable crafting recipe matched these inputs; removal call site follows",
                new Exception("Direct crafting inputs (not an existing recipe): " + Arrays.toString(shape)));
        }
        return rReturn;
    }

    static ItemStack removeMatchingRecipes(List<IRecipe> allRecipes, InventoryCrafting craftMatrix,
        IdentityHashMap<IRecipe, Boolean> knownMatches) {
        ItemStack result = null;
        for (int i = 0; i < allRecipes.size(); i++) {
            final IRecipe recipe = allRecipes.get(i);

            if (recipe instanceof IGTCraftingRecipe && !((IGTCraftingRecipe) recipe).isRemovable()) {
                continue;
            }

            Boolean knownMatch = knownMatches == null ? null : knownMatches.get(recipe);
            if (knownMatch != null ? knownMatch : recipe.matches(craftMatrix, DW)) {
                result = recipe.getCraftingResult(craftMatrix);
                allRecipes.remove(i--);
            }
        }
        return result;
    }

    public static void removeRecipeDelayed(ItemStack... shape) {
        if (shape == null || isAllNulls(shape)) {
            if (GT_RECIPE_REMOVAL_LOGGER_ENABLED) {
                GT_RECIPE_REMOVAL_LOGGER.error(
                    "removeRecipeDelayed rejected empty or null-only crafting inputs; call site follows",
                    new Exception("Rejected crafting inputs: " + Arrays.toString(shape)));
            }
            return;
        }

        if (!sBufferCraftingRecipes) {
            removeRecipe(shape);
            return;
        }

        InventoryCrafting craftMatrix = new InventoryCrafting(new Container() {

            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < shape.length && i < 9; i++) {
            craftMatrix.setInventorySlotContents(i, shape[i]);
        }

        delayedRemovalByRecipe.add(craftMatrix);
        if (GT_RECIPE_REMOVAL_LOGGER_ENABLED) {
            delayedRemovalCallsites
                .add(new Exception("Queued crafting inputs (not an existing recipe): " + Arrays.toString(shape)));
        }
    }

    private static void bulkRemoveByRecipe() {
        ArrayList<IRecipe> allRecipes = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();
        GT_FML_LOGGER
            .info("BulkRemoveByRecipe: allRecipes: {}; toRemove: {}", allRecipes.size(), delayedRemovalByRecipe.size());
        long start = System.currentTimeMillis();
        AtomicIntegerArray matchedDelayedRemovals = GT_RECIPE_REMOVAL_LOGGER_ENABLED
            ? new AtomicIntegerArray(delayedRemovalByRecipe.size())
            : null;
        Set<IRecipe> listToRemove = allRecipes.parallelStream()
            .filter(recipe -> {
                if (recipe instanceof IGTCraftingRecipe craftingRecipe && !craftingRecipe.isRemovable()) {
                    return false;
                }
                for (int i = 0; i < delayedRemovalByRecipe.size(); i++) {
                    if (recipe.matches(delayedRemovalByRecipe.get(i), DW)) {
                        if (matchedDelayedRemovals != null) matchedDelayedRemovals.set(i, 1);
                        return true;
                    }
                }
                return false;
            })
            .collect(Collectors.toSet());

        allRecipes.removeIf(listToRemove::contains);
        GT_FML_LOGGER.info("BulkRemoveByRecipe processed in {} ms!", System.currentTimeMillis() - start);
        if (matchedDelayedRemovals != null) {
            for (int i = 0; i < matchedDelayedRemovals.length(); i++) {
                if (matchedDelayedRemovals.get(i) == 0) {
                    GT_RECIPE_REMOVAL_LOGGER.warn(
                        "No existing removable crafting recipe matched these queued inputs; removal call site follows",
                        delayedRemovalCallsites.get(i));
                }
            }
        }
    }

    public static boolean removeRecipeByOutputDelayed(ItemStack output, boolean ignoreNBT, boolean keepShapelessRecipes,
        boolean onlyRemoveNativeHandlers) {
        // Handling NBT-sensitive stacks would be a nightmare, because we wouldn't be able to use HashMaps anymore.
        // Luckily, ignoreNBT is almost never false, so we're not missing any performance gain by not handling it.
        if (sBufferCraftingRecipes && ignoreNBT) {
            ItemStack unifiedOutput = GTOreDictUnificator.get_nocopy(output);

            int removalFlags = 0;
            if (keepShapelessRecipes) removalFlags |= DELAYED_REMOVAL_KEEP_SHAPELESS;
            if (onlyRemoveNativeHandlers) removalFlags |= DELAYED_REMOVAL_ONLY_REMOVE_NATIVE;

            if (removalFlags != 0) {
                delayedRemovalByOutputFlags.put(unifiedOutput, removalFlags);
            }
            return delayedRemovalByOutput.add(unifiedOutput);
        }

        return removeRecipeByOutput(output, ignoreNBT, keepShapelessRecipes, onlyRemoveNativeHandlers);
    }

    public static boolean removeRecipeByOutput(ItemStack output, boolean ignoreNBT, boolean keepShapelessRecipes,
        boolean onlyRemoveNativeHandlers) {

        output = GTOreDictUnificator.get_nocopy(output);
        if (output == null) return false;

        final ArrayList<IRecipe> allRecipes = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();
        int size = allRecipes.size();
        boolean removed = false;

        for (int i = 0; i < size; i++) {
            final IRecipe recipe = allRecipes.get(i);

            if (recipe instanceof IGTCraftingRecipe craftingRecipe && !craftingRecipe.isRemovable()) {
                continue;
            }

            if (keepShapelessRecipes) {
                if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe) {
                    continue;
                }
            }

            String recipeClassName = recipe.getClass()
                .getName();
            if (onlyRemoveNativeHandlers) {
                if (!sNativeRecipeClasses.contains(recipeClassName)) continue;
            } else {
                if (sSpecialRecipeClasses.contains(recipeClassName)) continue;
            }

            ItemStack recipeOutput = GTOreDictUnificator.get_nocopy(recipe.getRecipeOutput());
            if (GTUtility.areStacksEqual(recipeOutput, output, ignoreNBT)) {
                allRecipes.remove(i--);
                size = allRecipes.size();
                removed = true;
            }
        }

        return removed;
    }

    private static void bulkRemoveRecipeByOutput() {
        ArrayList<IRecipe> allRecipes = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();
        ItemStack wildcardItem = new ItemStack((Item) null, 0, WILDCARD);

        GT_FML_LOGGER.info(
            "BulkRemoveRecipeByOutput: allRecipes: {}; toRemove: {}",
            allRecipes.size(),
            delayedRemovalByOutput.size());

        allRecipes.removeIf(recipe -> {
            if (recipe instanceof IGTCraftingRecipe craftingRecipe && !craftingRecipe.isRemovable()) {
                return false;
            }

            ItemStack output = GTOreDictUnificator.get_nocopy(recipe.getRecipeOutput());
            if (output == null) return false;

            wildcardItem.func_150996_a(output.getItem());
            if (delayedRemovalByOutput.contains(wildcardItem)) {
                output = wildcardItem;
            }

            if (!delayedRemovalByOutput.contains(output)) {
                return false;
            }

            final int flags = delayedRemovalByOutputFlags.getOrDefault(output, 0);
            if ((flags & DELAYED_REMOVAL_KEEP_SHAPELESS) != 0) {
                if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe) {
                    return false;
                }
            }

            String recipeClassName = recipe.getClass()
                .getName();
            if ((flags & DELAYED_REMOVAL_ONLY_REMOVE_NATIVE) != 0) {
                return sNativeRecipeClasses.contains(recipeClassName);
            } else {
                return !sSpecialRecipeClasses.contains(recipeClassName);
            }
        });
    }

    private static boolean isAllNulls(ItemStack[] aRecipe) {
        for (ItemStack stack : aRecipe) {
            if (stack != null) {
                return false;
            }
        }
        return true;
    }

    public static int chargeElectricItem(ItemStack aStack, int aCharge, int aTier, boolean aIgnoreLimit,
        boolean aSimulate) {
        if (isElectricItem(aStack)) {
            int tTier = ((ic2.api.item.IElectricItem) aStack.getItem()).getTier(aStack);
            if (tTier < 0 || tTier == aTier || aTier == Integer.MAX_VALUE) {
                if (!aIgnoreLimit && tTier >= 0)
                    aCharge = (int) Math.min(aCharge, V[Math.max(0, Math.min(V.length - 1, tTier))]);
                if (aCharge > 0) {
                    int rCharge = (int) Math
                        .max(0.0, ic2.api.item.ElectricItem.manager.charge(aStack, aCharge, tTier, true, aSimulate));
                    return rCharge + (rCharge * 4 > aTier ? aTier : 0);
                }
            }
        }
        return 0;
    }

    public static int dischargeElectricItem(ItemStack aStack, int aCharge, int aTier, boolean aIgnoreLimit,
        boolean aSimulate, boolean aIgnoreDischargability) {
        if (isElectricItem(aStack)) {
            int tTier = ((ic2.api.item.IElectricItem) aStack.getItem()).getTier(aStack);
            if (tTier < 0 || tTier == aTier || aTier == Integer.MAX_VALUE) {
                if (!aIgnoreLimit && tTier >= 0) {
                    int tier = Math.max(0, Math.min(V.length - 1, tTier));
                    aCharge = (int) Math.min(aCharge, V[tier] + B[tier]);
                }
                if (aCharge > 0) {
                    int rCharge = (int) Math.max(
                        0,
                        ic2.api.item.ElectricItem.manager.discharge(
                            aStack,
                            aCharge + (aCharge * 4 > aTier ? aTier : 0),
                            tTier,
                            true,
                            !aIgnoreDischargability,
                            aSimulate));
                    return rCharge - (rCharge * 4 > aTier ? aTier : 0);
                }
            }
        }
        return 0;
    }

    public static boolean canUseElectricItem(ItemStack aStack, int aCharge) {
        if (isElectricItem(aStack)) {
            return ic2.api.item.ElectricItem.manager.canUse(aStack, aCharge);
        }
        return false;
    }

    public static boolean useElectricItem(ItemStack aStack, int aCharge, EntityPlayer aPlayer) {
        if (isElectricItem(aStack)) {
            ic2.api.item.ElectricItem.manager.use(aStack, 0, aPlayer);
            if (ic2.api.item.ElectricItem.manager.canUse(aStack, aCharge)) {
                return ic2.api.item.ElectricItem.manager.use(aStack, aCharge, aPlayer);
            }
        }
        return false;
    }

    public static boolean damageOrDechargeItem(ItemStack aStack, int aDamage, int aDecharge, EntityLivingBase aPlayer) {
        if (GTUtility.isStackInvalid(aStack) || (aStack.getMaxStackSize() <= 1 && aStack.stackSize > 1)) return false;
        if (aPlayer instanceof EntityPlayer && ((EntityPlayer) aPlayer).capabilities.isCreativeMode) return true;
        if (aStack.getItem() instanceof IDamagableItem) {
            return ((IDamagableItem) aStack.getItem()).doDamageToItem(aStack, aDamage);
        } else if (GTModHandler.isElectricItem(aStack)) {
            if (canUseElectricItem(aStack, aDecharge)) {
                if (aPlayer instanceof EntityPlayer) {
                    return GTModHandler.useElectricItem(aStack, aDecharge, (EntityPlayer) aPlayer);
                }
                return GTModHandler.dischargeElectricItem(aStack, aDecharge, Integer.MAX_VALUE, true, false, true)
                    >= aDecharge;
            }
        } else if (aStack.getItem()
            .isDamageable()) {
                if (aPlayer == null) {
                    aStack.setItemDamage(aStack.getItemDamage() + aDamage);
                } else {
                    aStack.damageItem(aDamage, aPlayer);
                }
                if (aStack.getItemDamage() >= aStack.getMaxDamage()) {
                    aStack.setItemDamage(aStack.getMaxDamage() + 1);
                    ItemStack tStack = GTUtility.getContainerItem(aStack, true);
                    if (tStack != null) {
                        aStack.func_150996_a(tStack.getItem());
                        aStack.setItemDamage(tStack.getItemDamage());
                        aStack.stackSize = tStack.stackSize;
                        aStack.setTagCompound(tStack.getTagCompound());
                    } else if (aStack.stackSize > 0) {
                        aStack.stackSize--;
                    }
                }
                return true;
            }
        return false;
    }

    public static boolean useSolderingIron(ItemStack aStack, EntityLivingBase aPlayer, IInventory aExternalInventory) {
        if (aPlayer == null || aStack == null) return false;
        if (GTUtility.isStackInList(aStack, GregTechAPI.sSolderingToolList)) {
            if (aPlayer instanceof EntityPlayer tPlayer) {
                if (tPlayer.capabilities.isCreativeMode) return true;
                if (isElectricItem(aStack) && ic2.api.item.ElectricItem.manager.getCharge(aStack) > 1000.0d) {
                    if ((aExternalInventory != null && consumeSolderingMaterial(aExternalInventory))
                        || consumeSolderingMaterial(tPlayer)) {
                        if (canUseElectricItem(aStack, 10000)) {
                            return GTModHandler.useElectricItem(aStack, 10000, (EntityPlayer) aPlayer);
                        }
                        GTModHandler.useElectricItem(
                            aStack,
                            (int) ic2.api.item.ElectricItem.manager.getCharge(aStack),
                            (EntityPlayer) aPlayer);
                        return false;
                    } else {
                        GTUtility.sendChatTrans((EntityPlayer) aPlayer, "GT5U.chat.soldering_iron.not_enough");
                    }
                }
            } else {
                damageOrDechargeItem(aStack, 1, 1000, aPlayer);
                return true;
            }
        }
        return false;
    }

    public static boolean useSolderingIron(ItemStack aStack, EntityLivingBase aPlayer) {
        if (aStack != null && aStack.getItem() instanceof ItemGTToolbox) {
            final ToolboxDelegateInventory delegateInventory = new ToolboxDelegateInventory(aStack);
            final boolean result = useSolderingIron(aStack, aPlayer, delegateInventory);

            ToolboxUtil.saveToolbox(aStack, delegateInventory.getHandler());
            return result;
        }
        return useSolderingIron(aStack, aPlayer, null);
    }

    public static boolean consumeSolderingMaterial(EntityPlayer aPlayer) {
        if (aPlayer.capabilities.isCreativeMode) return true;
        IInventory tInventory = aPlayer.inventory;
        if (consumeSolderingMaterial(tInventory)) {
            if (aPlayer.inventoryContainer != null) {
                aPlayer.inventoryContainer.detectAndSendChanges();
            }
            return true;
        }
        for (int i = 0; i < tInventory.getSizeInventory(); i++) {
            ItemStack tStack = tInventory.getStackInSlot(i);
            if (tStack != null && tStack.getItem() instanceof ItemToolbox) {
                IInventory tToolboxInventory = ((ItemToolbox) tStack.getItem()).getInventory(aPlayer, tStack);
                if (consumeSolderingMaterial(tToolboxInventory)) {
                    tInventory.markDirty();
                    if (aPlayer.inventoryContainer != null) {
                        aPlayer.inventoryContainer.detectAndSendChanges();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean consumeSolderingMaterial(IInventory aInventory) {
        for (int i = 0; i < aInventory.getSizeInventory(); i++) {
            ItemStack tStack = aInventory.getStackInSlot(i);
            if (GTUtility.isStackInList(tStack, GregTechAPI.sSolderingMetalList)) {
                if (tStack.stackSize < 1) return false;
                if (tStack.stackSize == 1) {
                    tStack = null;
                } else {
                    tStack.stackSize--;
                }
                aInventory.setInventorySlotContents(i, tStack);
                aInventory.markDirty();
                return true;
            }
        }
        return false;
    }

    public static boolean isChargerItem(ItemStack aStack) {
        if (isElectricItem(aStack)) {
            return ((ic2.api.item.IElectricItem) aStack.getItem()).canProvideEnergy(aStack);
        }
        return false;
    }

    public static boolean isElectricItem(ItemStack aStack) {
        return aStack != null && aStack.getItem() instanceof ic2.api.item.IElectricItem electricItem
            && electricItem.getTier(aStack) < Integer.MAX_VALUE;
    }

    public static boolean isElectricItem(ItemStack aStack, byte aTier) {
        return aStack != null && aStack.getItem() instanceof ic2.api.item.IElectricItem electricItem
            && electricItem.getTier(aStack) == aTier;
    }

    public static int getCapsuleCellContainerCountMultipliedWithStackSize(ItemStack... aStacks) {
        int rAmount = 0;
        for (ItemStack tStack : aStacks)
            if (tStack != null) rAmount += getCapsuleCellContainerCount(tStack) * tStack.stackSize;
        return rAmount;
    }

    public static int getCapsuleCellContainerCount(ItemStack aStack) {
        if (aStack == null) return 0;

        if (GTUtility.areStacksEqual(GTUtility.getContainerForFilledItem(aStack, true), ItemList.Cell_Empty.get(1))) {
            return 1;
        }

        if (GTUtility.areStacksEqual(aStack, getIC2Item("waterCell", 1, WILDCARD))) {
            return 1;
        }

        for (OrePrefixes cellType : OrePrefixes.CELL_TYPES) {
            if (cellType.contains(aStack)) {
                return 1;
            }
        }

        return 0;
    }

    public static class RecipeBits {

        /**
         * Mirrors the Recipe
         */
        public static final long MIRRORED = B[0];
        /**
         * Buffers the Recipe for later addition. This makes things more efficient.
         */
        public static final long BUFFERED = B[1];
        /**
         * This is a special Tag I used for crafting Coins up and down. If all the input items have the same NBT, keep
         * it in the output item.
         */
        public static final long KEEPNBT = B[2];
        /**
         * Makes the Recipe Reverse Craftable in the Disassembler.
         *
         * @deprecated Disassembler was removed from the mod, this flag is no-op.
         */
        @Deprecated
        public static final long DISMANTLEABLE = B[3];
        /**
         * Prevents the Recipe from accidentally getting removed by my own Handlers.
         */
        public static final long NOT_REMOVABLE = B[4];
        /**
         * Reverses the Output of the Recipe for smelting and pulverising.
         */
        public static final long REVERSIBLE = B[5];
        /**
         * Removes all Recipes with the same Output Item regardless of NBT, unless another Recipe Deletion Bit is added
         * too.
         */
        public static final long DELETE_ALL_OTHER_RECIPES = B[6];
        /**
         * Removes all Recipes with the same Output Item limited to the same NBT.
         */
        public static final long DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT = B[7];
        /**
         * Removes all Recipes with the same Output Item limited to Shaped Recipes.
         */
        public static final long DELETE_ALL_OTHER_SHAPED_RECIPES = B[8];
        /**
         * Removes all Recipes with the same Output Item limited to native Recipe Handlers.
         */
        public static final long DELETE_ALL_OTHER_NATIVE_RECIPES = B[9];
        /**
         * Disables the check for colliding Recipes.
         */
        public static final long DO_NOT_CHECK_FOR_COLLISIONS = B[10];
        /**
         * Only adds the Recipe if there is another Recipe having that Output
         */
        public static final long ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT = B[11];
        /**
         * Only adds the Recipe if it has an Output
         */
        public static final long ONLY_ADD_IF_RESULT_IS_NOT_NULL = B[12];
        /**
         * Keep input item's NBT if the input item is the same as output item, and try to overwrite input item's NBT
         * tags with output item's NBT tags if exists
         */
        public static final long OVERWRITE_NBT = B[14];
        /**
         * Keeps the supplied crafting recipe output instead of ore-dictionary-unifying it.
         */
        public static final long DONT_UNIFY_OUTPUT = B[15];
        /**
         * Combination of common bits. NOT_REMOVABLE, REVERSIBLE, and BUFFERED
         */
        public static final long BITS = NOT_REMOVABLE | REVERSIBLE | BUFFERED;

    }
}
