package gregtech.api.modpack.cover;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.modpack.util.InventoryUtil;

/**
 * Ready to use cover logics. Combine them with {@link CoverBuilder#behavior(CoverBehavior)} or use them as examples.
 */
public final class CoverBehaviors {

    private static final String[] DIRECTION_MODES = { "Export", "Import" };

    private CoverBehaviors() {}

    /**
     * Moves item stacks between the machine and the inventory in front of the cover. Screwdriver: export / import.
     *
     * @param stacksPerOperation stacks moved per operation
     * @param ticks              ticks between operations
     */
    public static CoverBehavior conveyor(int stacksPerOperation, int ticks) {
        return new CoverBehavior() {

            @Override
            public int getTickRate() {
                return ticks;
            }

            @Override
            public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
                ICoverable tile = cover.getTile();
                if (tile == null) {
                    return;
                }
                ForgeDirection side = cover.getSide();
                TileEntity other = tile.getTileEntityAtSide(side);
                if (!(other instanceof IInventory)) {
                    return;
                }
                if (cover.getMode() == 0) {
                    moveItems(tile, side, (IInventory) other, side.getOpposite(), stacksPerOperation);
                } else {
                    moveItems((IInventory) other, side.getOpposite(), tile, side, stacksPerOperation);
                }
            }

            @Override
            public void onScrewdriver(SimpleCover cover, EntityPlayer player) {
                cover.cycleMode(player, DIRECTION_MODES);
            }

            @Override
            public boolean alwaysLookConnected(SimpleCover cover) {
                return true;
            }

            @Override
            public void addTooltip(SimpleCover cover, List<String> tooltip) {
                tooltip.add(stacksPerOperation + " stack(s) every " + ticks / 20.0 + " s");
            }
        };
    }

    /**
     * Moves fluid between the machine and the tank in front of the cover. Screwdriver: export / import.
     *
     * @param litersPerOperation liters per operation
     * @param ticks              ticks between operations
     */
    public static CoverBehavior pump(int litersPerOperation, int ticks) {
        return new CoverBehavior() {

            @Override
            public int getTickRate() {
                return ticks;
            }

            @Override
            public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
                ICoverable tile = cover.getTile();
                if (!(tile instanceof IFluidHandler self)) {
                    return;
                }
                ForgeDirection side = cover.getSide();
                if (!(tile.getTileEntityAtSide(side) instanceof IFluidHandler other)) {
                    return;
                }
                if (cover.getMode() == 0) {
                    moveFluid(self, side, other, side.getOpposite(), litersPerOperation);
                } else {
                    moveFluid(other, side.getOpposite(), self, side, litersPerOperation);
                }
            }

            @Override
            public void onScrewdriver(SimpleCover cover, EntityPlayer player) {
                cover.cycleMode(player, DIRECTION_MODES);
            }

            @Override
            public boolean alwaysLookConnected(SimpleCover cover) {
                return true;
            }

            @Override
            public void addTooltip(SimpleCover cover, List<String> tooltip) {
                tooltip.add(litersPerOperation * 20 / ticks + " L/s");
            }
        };
    }

    /**
     * Produces EU during the day when the cover sees the sky.
     */
    public static CoverBehavior solarPanel(long euPerTick) {
        return new CoverBehavior() {

            @Override
            public int getTickRate() {
                return 1;
            }

            @Override
            public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
                ICoverable tile = cover.getTile();
                if (tile == null || cover.getSide() != ForgeDirection.UP) {
                    return;
                }
                World world = tile.getWorld();
                if (world.isDaytime() && !world.isRaining() && tile.getSkyAtSide(ForgeDirection.UP)) {
                    tile.increaseStoredEnergyUnits(euPerTick, false);
                }
            }

            @Override
            public void addTooltip(SimpleCover cover, List<String> tooltip) {
                tooltip.add(euPerTick + " EU/t in sunlight, top side only");
            }
        };
    }

    /**
     * Redstone output 15 while the machine is working. Screwdriver: normal / inverted.
     */
    public static CoverBehavior activityDetector() {
        return new CoverBehavior() {

            @Override
            public int getTickRate() {
                return 5;
            }

            @Override
            public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
                boolean active = cover.getTile() instanceof IMachineProgress machine && machine.isActive();
                boolean inverted = cover.getMode() == 1;
                cover.setRedstoneOutput(active != inverted ? 15 : 0);
            }

            @Override
            public void onScrewdriver(SimpleCover cover, EntityPlayer player) {
                cover.cycleMode(player, "Normal", "Inverted");
            }

            @Override
            public boolean emitsRedstone(SimpleCover cover) {
                return true;
            }
        };
    }

    /**
     * Machine works only with (mode 0) or without (mode 1) a redstone signal. Screwdriver cycles the mode.
     */
    public static CoverBehavior machineController() {
        return new CoverBehavior() {

            @Override
            public int getTickRate() {
                return 1;
            }

            @Override
            public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
                if (!(cover.getTile() instanceof IMachineProgress machine)) {
                    return;
                }
                boolean powered = redstoneIn > 0;
                boolean shouldWork = cover.getMode() == 0 ? powered : !powered;
                if (shouldWork && !machine.isAllowedToWork()) {
                    machine.enableWorking();
                } else if (!shouldWork && machine.isAllowedToWork()) {
                    machine.disableWorking();
                }
            }

            @Override
            public void onScrewdriver(SimpleCover cover, EntityPlayer player) {
                cover.cycleMode(player, "Work with redstone", "Work without redstone");
            }

            @Override
            public void onRemove(SimpleCover cover) {
                if (cover.getTile() instanceof IMachineProgress machine) {
                    machine.enableWorking();
                }
            }
        };
    }

    // region Helpers

    private static int[] slots(IInventory inventory, ForgeDirection side) {
        if (inventory instanceof ISidedInventory sided) {
            return sided.getAccessibleSlotsFromSide(side.ordinal());
        }
        int[] all = new int[inventory.getSizeInventory()];
        for (int i = 0; i < all.length; i++) {
            all[i] = i;
        }
        return all;
    }

    /** Moves up to {@code maxStacks} stacks from {@code from} to {@code to}. */
    public static void moveItems(IInventory from, ForgeDirection fromSide, IInventory to, ForgeDirection toSide,
        int maxStacks) {
        int moved = 0;
        for (int fromSlot : slots(from, fromSide)) {
            if (moved >= maxStacks) {
                break;
            }
            ItemStack stack = from.getStackInSlot(fromSlot);
            if (stack == null) {
                continue;
            }
            if (from instanceof ISidedInventory sided && !sided.canExtractItem(fromSlot, stack, fromSide.ordinal())) {
                continue;
            }
            int before = stack.stackSize;
            for (int toSlot : slots(to, toSide)) {
                if (stack.stackSize <= 0) {
                    break;
                }
                if (!to.isItemValidForSlot(toSlot, stack)) {
                    continue;
                }
                if (to instanceof ISidedInventory sided && !sided.canInsertItem(toSlot, stack, toSide.ordinal())) {
                    continue;
                }
                ItemStack existing = to.getStackInSlot(toSlot);
                int limit = Math.min(to.getInventoryStackLimit(), stack.getMaxStackSize());
                if (existing == null) {
                    ItemStack copy = stack.copy();
                    copy.stackSize = Math.min(limit, stack.stackSize);
                    to.setInventorySlotContents(toSlot, copy);
                    stack.stackSize -= copy.stackSize;
                } else if (InventoryUtil.canStack(existing, stack) && existing.stackSize < limit) {
                    int amount = Math.min(limit - existing.stackSize, stack.stackSize);
                    existing.stackSize += amount;
                    stack.stackSize -= amount;
                    to.setInventorySlotContents(toSlot, existing);
                }
            }
            if (stack.stackSize != before) {
                from.setInventorySlotContents(fromSlot, stack.stackSize <= 0 ? null : stack);
                moved++;
            }
        }
        if (moved > 0) {
            from.markDirty();
            to.markDirty();
        }
    }

    public static void moveFluid(IFluidHandler from, ForgeDirection fromSide, IFluidHandler to, ForgeDirection toSide,
        int amount) {
        FluidStack drained = from.drain(fromSide, amount, false);
        if (drained == null || drained.amount <= 0) {
            return;
        }
        int filled = to.fill(toSide, drained, true);
        if (filled > 0) {
            from.drain(fromSide, filled, true);
        }
    }

    // endregion
}
