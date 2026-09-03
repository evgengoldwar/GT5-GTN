package gregtech.api.util;

import static gregtech.GTLoggers.GT_ORE_DICT_LOGGER;
import static gregtech.api.enums.GTValues.E;
import static gregtech.api.enums.GTValues.M;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.GTItemStack;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;

public class GTOreDictUnificator {

    private static final Map<String, ItemStack> sName2StackMap = new HashMap<>();
    private static final Map<ItemStack, ItemData> sItemStack2DataMap = new Object2ObjectOpenCustomHashMap<>(
        GTItemStack.ITEMSTACK_HASH_STRATEGY2);
    private static final Map<ItemStack, List<ItemStack>> sUnificationTable = new Object2ObjectOpenCustomHashMap<>(
        GTItemStack.ITEMSTACK_HASH_STRATEGY2);
    private static final Set<ItemStack> sNoUnificationList = new ObjectOpenCustomHashSet<>(
        GTItemStack.ITEMSTACK_HASH_STRATEGY2);
    private static boolean mRunThroughTheList = true;

    public static void addToBlacklist(ItemStack aStack) {
        if (GTUtility.isStackValid(aStack) && !GTUtility.isStackInStackSet(aStack, sNoUnificationList))
            sNoUnificationList.add(aStack);
    }

    public static boolean isBlacklisted(ItemStack aStack) {
        return GTUtility.isStackInStackSet(aStack, sNoUnificationList);
    }

    public static void add(OrePrefixes aPrefix, Materials_OLD aMaterial, ItemStack aStack) {
        set(aPrefix, aMaterial, aStack, false, false);
    }

    public static void set(OrePrefixes aPrefix, Materials_OLD aMaterial, ItemStack aStack) {
        set(aPrefix, aMaterial, aStack, true, false);
    }

    public static void set(OrePrefixes aPrefix, Materials_OLD aMaterial, ItemStack aStack, boolean aOverwrite,
        boolean aAlreadyRegistered) {
        if (aMaterial == null || aPrefix == null
            || GTUtility.isStackInvalid(aStack)
            || Items.feather.getDamage(aStack) == WILDCARD) return;
        aStack = GTUtility.copyAmount(1, aStack);
        if (!aAlreadyRegistered) registerOre(aPrefix.get(aMaterial), aStack);
        addAssociation(aPrefix, aMaterial, aStack, isBlacklisted(aStack));
        if (aOverwrite || GTUtility.isStackInvalid(
            sName2StackMap.get(
                aPrefix.get(aMaterial)
                    .toString())))
            sName2StackMap.put(
                aPrefix.get(aMaterial)
                    .toString(),
                aStack);
    }

    public static ItemStack getFirstOre(Object aName, long aAmount) {
        if (GTUtility.isStringInvalid(aName)) return null;
        ItemStack tStack = sName2StackMap.get(aName.toString());
        if (GTUtility.isStackValid(tStack)) return GTUtility.copyAmount(aAmount, tStack);
        return GTUtility.copyAmount(aAmount, getOresImmutable(aName).toArray());
    }

    public static ItemStack get(Object aName, long aAmount) {
        return get(aName, null, aAmount, true, true);
    }

    public static ItemStack get(Object aName, ItemStack aReplacement, long aAmount) {
        return get(aName, aReplacement, aAmount, true, true);
    }

    public static ItemStack get(OrePrefixes aPrefix, Object aMaterial, long aAmount) {
        return get(aPrefix, aMaterial, null, aAmount);
    }

    public static ItemStack get(OrePrefixes aPrefix, Object aMaterial, ItemStack aReplacement, long aAmount) {
        if (OrePrefixes.mPreventableComponents.contains(aPrefix) && aPrefix.mDisabledItems.contains(aMaterial))
            return aReplacement;
        return get(aPrefix.get(aMaterial), aReplacement, aAmount, false, true);
    }

    public static ItemStack get(OrePrefixes aPrefix, Object aMaterial, long aAmount, boolean aNoInvalidAmounts) {
        if (OrePrefixes.mPreventableComponents.contains(aPrefix) && aPrefix.mDisabledItems.contains(aMaterial))
            return null;
        return get(aPrefix.get(aMaterial), null, aAmount, false, aNoInvalidAmounts);
    }

    public static ItemStack get(Object aName, ItemStack aReplacement, long aAmount, boolean aMentionPossibleTypos,
        boolean aNoInvalidAmounts) {
        if (aNoInvalidAmounts && aAmount < 1) return null;
        final ItemStack stackFromName = sName2StackMap.get(aName.toString());
        if (stackFromName != null) return GTUtility.copyAmount(aAmount, stackFromName);
        if (aMentionPossibleTypos) {
            GT_ORE_DICT_LOGGER.error("Unknown Key for Unification, Typo? {}", aName);
        }
        final ItemStack stackFirstOre = getFirstOre(aName, aAmount);
        if (stackFirstOre != null) return GTUtility.copyAmount(aAmount, stackFirstOre);
        return GTUtility.copyAmount(aAmount, aReplacement);
    }

    public static ItemStack[] setStackArray(boolean aUseBlackList, boolean aUnsafe, ItemStack... aStacks) {
        for (int i = 0; i < aStacks.length; i++)
            aStacks[i] = get(aUseBlackList, GTUtility.copyOrNull(aStacks[i]), aUnsafe);
        return aStacks;
    }

    public static ItemStack[] getStackArray(boolean aUseBlackList, Object... aStacks) {
        ItemStack[] rStacks = new ItemStack[aStacks.length];
        for (int i = 0; i < aStacks.length; i++) {
            rStacks[i] = get(aUseBlackList, GTUtility.copy(aStacks[i]), true);
        }
        return rStacks;
    }

    public static ItemStack setStack(boolean aUseBlackList, ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack)) return aStack;
        ItemStack tStack = get(aUseBlackList, aStack);
        if (GTUtility.areStacksEqual(aStack, tStack)) return aStack;
        aStack.func_150996_a(tStack.getItem());
        Items.feather.setDamage(aStack, Items.feather.getDamage(tStack));
        return aStack;
    }

    public static ItemStack get(ItemStack stack) {
        return get(true, stack);
    }

    public static ItemStack get(boolean useBlackList, ItemStack stack) {
        return get(useBlackList, stack, false);
    }

    public static ItemStack get(boolean useBlackList, ItemStack stack, boolean unsafe) {
        if (GTUtility.isStackInvalid(stack)) return null;
        ItemData itemData = getAssociation(stack);
        if (itemData == null || !itemData.hasValidPrefixMaterialData() || (useBlackList && itemData.mBlackListed)) {
            return GTUtility.copyOrNull(stack);
        }
        if (useBlackList && !GregTechAPI.sUnificationEntriesRegistered && isBlacklisted(stack)) {
            itemData.mBlackListed = true;
            return GTUtility.copyOrNull(stack);
        }
        if (itemData.mUnificationTarget == null) {
            itemData.mUnificationTarget = sName2StackMap.get(itemData.toString());
        }
        final ItemStack rStack = itemData.mUnificationTarget;
        if (GTUtility.isStackInvalid(rStack)) {
            return GTUtility.copyOrNull(stack);
        }
        final ItemStack newStack;
        if (unsafe) {
            newStack = GTUtility.copyAmountUnsafe(stack.stackSize, rStack);
        } else {
            newStack = GTUtility.copyAmount(stack.stackSize, rStack);
        }
        if (newStack != null) {
            newStack.setTagCompound(stack.getTagCompound());
        }
        return newStack;
    }

    public static ItemStack get_nocopy(ItemStack aStack) {
        return get_nocopy(true, aStack);
    }

    public static ItemStack get_nocopy(boolean useBlackList, ItemStack stack) {
        if (GTUtility.isStackInvalid(stack)) return null;
        ItemData itemData = getAssociation(stack);
        if (itemData == null || !itemData.hasValidPrefixMaterialData() || (useBlackList && itemData.mBlackListed)) {
            return stack;
        }
        if (useBlackList && !GregTechAPI.sUnificationEntriesRegistered && isBlacklisted(stack)) {
            itemData.mBlackListed = true;
            return stack;
        }
        if (itemData.mUnificationTarget == null) {
            itemData.mUnificationTarget = sName2StackMap.get(itemData.toString());
        }
        ItemStack rStack = itemData.mUnificationTarget;
        if (GTUtility.isStackInvalid(rStack)) {
            return stack;
        }

        if (stack.getTagCompound() == rStack.getTagCompound()) {
            return rStack;
        }

        ItemStack newStack = new ItemStack(rStack.getItem(), stack.stackSize, Items.feather.getDamage(rStack));
        newStack.setTagCompound(stack.getTagCompound());
        return newStack;
    }

    public static boolean isInputStackEqual(ItemStack aStack, ItemData aStackPrefixData, ItemStack unified_tStack) {
        boolean alreadyCompared = false;
        if (GTUtility.isStackInvalid(aStack)) return false;
        ItemStack rStack = null;
        if (aStackPrefixData == null || !aStackPrefixData.hasValidPrefixMaterialData())
            return GTUtility.areStacksEqual(aStack, unified_tStack, true);
        else if (aStackPrefixData.mBlackListed) {
            if (GTUtility.areStacksEqual(aStack, unified_tStack, true)) return true;
            else alreadyCompared = true;
        }
        if (!alreadyCompared && !GregTechAPI.sUnificationEntriesRegistered && isBlacklisted(aStack)) {
            aStackPrefixData.mBlackListed = true;
            if (GTUtility.areStacksEqual(aStack, unified_tStack, true)) return true;
            else alreadyCompared = true;
        }
        if (aStackPrefixData.mUnificationTarget == null)
            aStackPrefixData.mUnificationTarget = sName2StackMap.get(aStackPrefixData.toString());
        rStack = aStackPrefixData.mUnificationTarget;
        if (GTUtility.isStackInvalid(rStack))
            return !alreadyCompared && GTUtility.areStacksEqual(aStack, unified_tStack, true);
        return GTUtility.areStacksEqual(rStack, unified_tStack, true);
    }

    public static List<ItemStack> getNonUnifiedStacks(Object obj) {
        if (sUnificationTable.isEmpty() && !sItemStack2DataMap.isEmpty()) {
            // use something akin to double check lock. this synchronization overhead is causing lag whenever my
            // 5900x tries to do NEI lookup
            synchronized (sUnificationTable) {
                if (sUnificationTable.isEmpty() && !sItemStack2DataMap.isEmpty()) {
                    for (ItemStack tGTStack0 : sItemStack2DataMap.keySet()) {
                        ItemStack tStack0 = GTItemStack.internalCopyStack(tGTStack0);
                        ItemStack tStack1 = get_nocopy(false, tStack0);
                        if (!GTUtility.areStacksEqual(tStack0, tStack1)) {
                            List<ItemStack> list = sUnificationTable.computeIfAbsent(tStack1, k -> new ArrayList<>());
                            // greg's original code tries to dedupe the list using List#contains, which won't work
                            // on vanilla ItemStack. I removed it since it never worked and can be slow.
                            list.add(tStack0);
                        }
                    }
                }
            }
        }
        ItemStack[] aStacks = GTValues.emptyItemStackArray;
        if (obj instanceof ItemStack) aStacks = new ItemStack[] { (ItemStack) obj };
        else if (obj instanceof ItemStack[]) aStacks = (ItemStack[]) obj;
        else if (obj instanceof List) aStacks = ((List<?>) obj).toArray(new ItemStack[0]);
        List<ItemStack> rList = new ArrayList<>();
        for (ItemStack aStack : aStacks) {
            if (aStack == null) continue;
            rList.add(aStack);
            List<ItemStack> tList = sUnificationTable.get(aStack);
            if (tList != null) {
                for (ItemStack tStack : tList) {
                    ItemStack tStack1 = GTUtility.copyAmountUnsafe(aStack.stackSize, tStack);
                    rList.add(tStack1);
                }
            }
        }
        return rList;
    }

    public static void addItemData(ItemStack aStack, ItemData aData) {
        if (GTUtility.isStackValid(aStack) && getItemData(aStack) == null && aData != null) setItemData(aStack, aData);
    }

    public static void setItemData(ItemStack aStack, ItemData aData) {
        if (GTUtility.isStackInvalid(aStack) || aData == null) return;
        ItemData tData = getItemData(aStack);
        if (tData == null || !tData.hasValidPrefixMaterialData()) {
            if (tData != null) for (Object tObject : tData.mExtraData)
                if (!aData.mExtraData.contains(tObject)) aData.mExtraData.add(tObject);
            if (aStack.stackSize > 1) {
                if (aData.mMaterial != null) aData.mMaterial.mAmount /= aStack.stackSize;
                for (MaterialStack tMaterial : aData.mByProducts) tMaterial.mAmount /= aStack.stackSize;
                aStack = GTUtility.copyAmount(1, aStack);
            }
            sItemStack2DataMap.put(aStack, aData);
            if (aData.hasValidMaterialData()) {}
            if (mRunThroughTheList) {
                if (GregTechAPI.sLoadStarted) {
                    mRunThroughTheList = false;
                }
            } else {}
        } else {
            for (Object tObject : aData.mExtraData)
                if (!tData.mExtraData.contains(tObject)) tData.mExtraData.add(tObject);
        }
    }

    public static void addAssociation(OrePrefixes aPrefix, Materials_OLD aMaterial, ItemStack aStack,
        boolean aBlackListed) {
        if (aPrefix == null || aMaterial == null || GTUtility.isStackInvalid(aStack)) return;
        if (Items.feather.getDamage(aStack) == WILDCARD) for (byte i = 0; i < 16; i++)
            setItemData(GTUtility.copyAmountAndMetaData(1, i, aStack), new ItemData(aPrefix, aMaterial, aBlackListed));
        setItemData(aStack, new ItemData(aPrefix, aMaterial, aBlackListed));
    }

    @Nullable
    public static ItemData getItemData(ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack)) return null;
        ItemData rData = sItemStack2DataMap.get(aStack);
        if (rData == null) { // Try the lookup again but with wildcard damage value
            rData = sItemStack2DataMap.get(GTItemStack.internalCopyStack(aStack, true));
        }
        return rData;
    }

    @Nullable
    public static ItemData getAssociation(ItemStack aStack) {
        ItemData rData = getItemData(aStack);
        return rData != null && rData.hasValidPrefixMaterialData() ? rData : null;
    }

    public static boolean isItemStackInstanceOf(ItemStack aStack, Object aName) {
        if (GTUtility.isStringInvalid(aName) || GTUtility.isStackInvalid(aStack)) return false;
        for (ItemStack tOreStack : getOresImmutable(aName.toString()))
            if (GTUtility.areStacksEqual(tOreStack, aStack, true)) return true;
        return false;
    }

    public static boolean registerOre(OrePrefixes aPrefix, Object aMaterial, ItemStack aStack) {
        return registerOre(aPrefix.get(aMaterial), aStack);
    }

    public static boolean registerOre(Object aName, ItemStack aStack) {
        if (aName == null || GTUtility.isStackInvalid(aStack)) return false;

        String tName = aName.toString();

        if (GTUtility.isStringInvalid(tName)) return false;

        for (ItemStack itemStack : getOresImmutable(tName))
            if (GTUtility.areStacksEqual(itemStack, aStack, true)) return false;

        OreDictionary.registerOre(tName, GTUtility.copyAmount(1, aStack));
        return true;
    }

    public static ItemStack getDust(MaterialStack aMaterial) {
        return aMaterial == null ? null : getDust(aMaterial.mMaterial, aMaterial.mAmount);
    }

    public static ItemStack getDust(Materials_OLD aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = null;
        if (((aMaterialAmount % M == 0) || aMaterialAmount >= M * 16))
            rStack = get(OrePrefixes.dust, aMaterial, aMaterialAmount / M);
        if (rStack == null && (((aMaterialAmount * 4) % M == 0) || aMaterialAmount >= M * 8))
            rStack = get(OrePrefixes.dustSmall, aMaterial, (aMaterialAmount * 4) / M);
        if (rStack == null && (((aMaterialAmount * 9) >= M)))
            rStack = get(OrePrefixes.dustTiny, aMaterial, (aMaterialAmount * 9) / M);
        return rStack;
    }

    public static ItemStack getIngot(MaterialStack aMaterial) {
        return aMaterial == null ? null : getIngot(aMaterial.mMaterial, aMaterial.mAmount);
    }

    public static ItemStack getIngot(Materials_OLD aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = null;
        if (((aMaterialAmount % (M * 9) == 0 && aMaterialAmount / (M * 9) > 1) || aMaterialAmount >= M * 72))
            rStack = get(OrePrefixes.block, aMaterial, aMaterialAmount / (M * 9));
        if (rStack == null && ((aMaterialAmount % M == 0) || aMaterialAmount >= M * 8))
            rStack = get(OrePrefixes.ingot, aMaterial, aMaterialAmount / M);
        if (rStack == null && (((aMaterialAmount * 9) >= M)))
            rStack = get(OrePrefixes.nugget, aMaterial, (aMaterialAmount * 9) / M);
        return rStack;
    }

    public static ItemStack getIngotOrDust(Materials_OLD aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = getIngot(aMaterial, aMaterialAmount);
        if (rStack == null) rStack = getDust(aMaterial, aMaterialAmount);
        return rStack;
    }

    public static ItemStack getIngotOrDust(MaterialStack aMaterial) {
        ItemStack rStack = getIngot(aMaterial);
        if (rStack == null) rStack = getDust(aMaterial);
        return rStack;
    }

    public static ItemStack getDustOrIngot(Materials_OLD aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = getDust(aMaterial, aMaterialAmount);
        if (rStack == null) rStack = getIngot(aMaterial, aMaterialAmount);
        return rStack;
    }

    public static ArrayList<ItemStack> getOres(Object aOreName) {
        String aName = aOreName == null ? E : aOreName.toString();
        ArrayList<ItemStack> rList = new ArrayList<>();
        if (GTUtility.isStringValid(aName)) rList.addAll(OreDictionary.getOres(aName));
        return rList;
    }

    public static List<ItemStack> getOresImmutable(@Nullable Object aOreName) {
        String aName = aOreName == null ? E : aOreName.toString();

        return GTUtility.isStringValid(aName) ? Collections.unmodifiableList(OreDictionary.getOres(aName))
            : Collections.emptyList();
    }
}
