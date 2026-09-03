package gregtech.api.util;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.GTLoggers.GT_RECIPE_REMOVAL_LOGGER;
import static gregtech.GTLoggers.GT_RECIPE_REMOVAL_LOGGER_ENABLED;
import static gregtech.api.enums.GTValues.B;
import static gregtech.api.enums.GTValues.D1;
import static gregtech.api.enums.GTValues.DW;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.util.GTRecipeBuilder.PANIC_MODE_NULL;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IDamagableItem;
import gregtech.api.interfaces.internal.IGTCraftingRecipe;
import gregtech.api.objects.GTItemStack;
import ic2.api.item.IC2Items;
import ic2.core.item.ItemToolbox;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

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

    static {
        sNativeRecipeClasses.add(ShapedRecipes.class.getName());
        sNativeRecipeClasses.add(ShapedOreRecipe.class.getName());
        sNativeRecipeClasses.add(ShapelessRecipes.class.getName());
        sNativeRecipeClasses.add(ShapelessOreRecipe.class.getName());
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
        return aFluid.isFluidEqual(Materials_OLD.Water.getFluid(1));
    }

    public static boolean isSteam(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(Materials_OLD.Steam.getGas(1));
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

    public static ItemStack getIC2Item(String aItem, long aAmount) {
        return getIC2Item(aItem, aAmount, null);
    }

    public static ItemStack getModItem(String aModID, String aItem) {
        return getModItem(aModID, aItem, 1, null);
    }

    public static ItemStack getModItem(String aModID, String aItem, long aAmount) {
        return getModItem(aModID, aItem, aAmount, null);
    }

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

    public static ItemStack getModItem(String aModID, String aItem, long aAmount, int aMeta) {
        ItemStack rStack = getModItem(aModID, aItem, aAmount);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    public static ItemStack getModItem(String aModID, String aItem, long aAmount, int aMeta, ItemStack aReplacement) {
        ItemStack rStack = getModItem(aModID, aItem, aAmount, aReplacement);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    public static void stopBufferingCraftingRecipes() {
        bulkRemoveRecipeByOutput();
        bulkRemoveByRecipe();
        sBufferRecipeList.forEach(GameRegistry::addRecipe);

        delayedRemovalByOutput.clear();
        delayedRemovalByOutputFlags.clear();
        delayedRemovalByRecipe.clear();
        delayedRemovalCallsites.clear();
        sBufferRecipeList.clear();
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
}
