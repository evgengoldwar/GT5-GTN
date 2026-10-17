package gregtech.api.modpack.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Small inventory helpers used by machines and hatches.
 */
public final class InventoryUtil {

    private InventoryUtil() {}

    public static boolean canStack(ItemStack a, ItemStack b) {
        return a != null && b != null
            && a.getItem() == b.getItem()
            && a.getItemDamage() == b.getItemDamage()
            && ItemStack.areItemStackTagsEqual(a, b);
    }

    /**
     * Inserts a stack into {@code slots[from..to)} merging with existing stacks first.
     *
     * @return amount that did not fit
     */
    public static int insert(ItemStack[] slots, int from, int to, ItemStack stack, int slotLimit, boolean simulate) {
        if (stack == null || stack.stackSize <= 0) {
            return 0;
        }
        int remaining = stack.stackSize;
        int limit = Math.min(slotLimit, stack.getMaxStackSize());
        for (int i = from; i < to && remaining > 0; i++) {
            ItemStack slot = slots[i];
            if (canStack(slot, stack) && slot.stackSize < limit) {
                int moved = Math.min(remaining, limit - slot.stackSize);
                if (!simulate) {
                    slot.stackSize += moved;
                }
                remaining -= moved;
            }
        }
        for (int i = from; i < to && remaining > 0; i++) {
            if (slots[i] == null) {
                int moved = Math.min(remaining, limit);
                if (!simulate) {
                    slots[i] = stack.copy();
                    slots[i].stackSize = moved;
                }
                remaining -= moved;
            }
        }
        return remaining;
    }

    /**
     * Simulates inserting all stacks at once (stacks can share free slots).
     */
    public static boolean canFitAll(ItemStack[] slots, int from, int to, ItemStack[] stacks, int slotLimit) {
        if (stacks == null) {
            return true;
        }
        ItemStack[] copy = new ItemStack[slots.length];
        for (int i = from; i < to; i++) {
            copy[i] = slots[i] == null ? null : slots[i].copy();
        }
        for (ItemStack stack : stacks) {
            if (stack != null && insert(copy, from, to, stack, slotLimit, false) > 0) {
                return false;
            }
        }
        return true;
    }

    /** Sets empty stacks to null. */
    public static void cleanup(ItemStack[] slots) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].stackSize <= 0) {
                slots[i] = null;
            }
        }
    }

    /**
     * Pushes {@code slots[from..to)} into the inventory of a neighbour tile entity.
     */
    public static void pushToNeighbour(ItemStack[] slots, int from, int to, TileEntity target, ForgeDirection side) {
        if (!(target instanceof IInventory inventory)) {
            return;
        }
        int targetSide = side.getOpposite()
            .ordinal();
        int[] accessible;
        if (inventory instanceof ISidedInventory sided) {
            accessible = sided.getAccessibleSlotsFromSide(targetSide);
        } else {
            accessible = new int[inventory.getSizeInventory()];
            for (int i = 0; i < accessible.length; i++) {
                accessible[i] = i;
            }
        }
        for (int i = from; i < to; i++) {
            ItemStack stack = slots[i];
            if (stack == null) {
                continue;
            }
            for (int targetSlot : accessible) {
                if (stack.stackSize <= 0) {
                    break;
                }
                if (!inventory.isItemValidForSlot(targetSlot, stack)) {
                    continue;
                }
                if (inventory instanceof ISidedInventory sided && !sided.canInsertItem(targetSlot, stack, targetSide)) {
                    continue;
                }
                ItemStack existing = inventory.getStackInSlot(targetSlot);
                int limit = Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());
                if (existing == null) {
                    ItemStack moved = stack.copy();
                    moved.stackSize = Math.min(limit, stack.stackSize);
                    inventory.setInventorySlotContents(targetSlot, moved);
                    stack.stackSize -= moved.stackSize;
                } else if (canStack(existing, stack) && existing.stackSize < limit) {
                    int moved = Math.min(limit - existing.stackSize, stack.stackSize);
                    existing.stackSize += moved;
                    stack.stackSize -= moved;
                    inventory.setInventorySlotContents(targetSlot, existing);
                }
            }
            if (stack.stackSize <= 0) {
                slots[i] = null;
            }
        }
        inventory.markDirty();
    }

    /**
     * Pushes fluids into a neighbour fluid handler.
     */
    public static void pushFluids(FluidStack[] tanks, TileEntity target, ForgeDirection side) {
        if (!(target instanceof IFluidHandler handler)) {
            return;
        }
        ForgeDirection from = side.getOpposite();
        for (int i = 0; i < tanks.length; i++) {
            FluidStack fluid = tanks[i];
            if (fluid == null || fluid.amount <= 0) {
                continue;
            }
            int filled = handler.fill(from, fluid.copy(), true);
            fluid.amount -= filled;
            if (fluid.amount <= 0) {
                tanks[i] = null;
            }
        }
    }
}
