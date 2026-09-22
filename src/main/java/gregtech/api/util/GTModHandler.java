package gregtech.api.util;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.B;
import static gregtech.api.enums.GTValues.D1;
import static gregtech.api.enums.GTValues.V;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.IDamagableItem;
import ic2.api.item.IC2Items;
import ic2.core.item.ItemToolbox;

public class GTModHandler {

    private static final Map<String, ItemStack> sIC2ItemMap = new HashMap<>();

    public static ItemStack getIC2Item(String aItem, long aAmount, ItemStack aReplacement) {
        if (GTUtility.isStringInvalid(aItem) || !GregTechAPI.sPreloadStarted) return null;
        // if (D1) GT_FML_LOGGER.debug("Requested the Item '" + aItem + "' from the IC2-API");
        if (!sIC2ItemMap.containsKey(aItem)) {
            ItemStack tStack = IC2Items.getItem(aItem);
            sIC2ItemMap.put(aItem, tStack);
            if (tStack == null && D1) GT_FML_LOGGER.error("{} is not found in the IC2 Items!", aItem);
        }
        return GTUtility.copyAmount(Math.toIntExact(aAmount), sIC2ItemMap.get(aItem));
    }

    public static ItemStack getIC2Item(String aItem, long aAmount) {
        return getIC2Item(aItem, aAmount, null);
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
        if (aStack == null) {
            return 0;
        }

        if (GTUtility.stackEqual(GTUtility.getContainerForFilledItem(aStack, true), ItemList.Cell_Empty.get(1))) {
            return 1;
        }

        return 0;
    }
}
