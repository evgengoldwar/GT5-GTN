package gregtech.api.enums;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import codechicken.nei.api.API;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GTUtility;

/**
 * Class containing all non-OreDict Items of GregTech.
 */
public enum ItemList implements IItemContainer {

    Display_Fluid,
    Cell_Universal_Fluid,
    Cell_Empty,
    Cell_Water,
    Cell_Lava,
    Cell_Air,
    Circuit_Integrated

    // semicolon after the comment to reduce merge conflicts
    ;

    private ItemStack itemStack;
    private boolean isSet;

    ItemList() {
        isSet = true;
    }

    @Override
    public IItemContainer set(Item item) {
        isSet = false;
        if (item == null) {
            return this;
        }
        ItemStack stack = new ItemStack(item, 1, 0);
        itemStack = GTUtility.copyAmount(1, stack);
        return this;
    }

    @Override
    public IItemContainer set(ItemStack itemStack) {
        isSet = false;
        this.itemStack = GTUtility.copyAmount(1, itemStack);
        return this;
    }

    @Override
    public IItemContainer hidden() {
        API.hideItem(get(1L));
        return this;
    }

    @Override
    public Item getItem() {
        sanityCheck();
        if (GTUtility.isStackInvalid(itemStack)) {
            return null;
        }
        return itemStack.getItem();
    }

    @Override
    public Block getBlock() {
        sanityCheck();
        return GTUtility.getBlockFromItem(getItem());
    }

    @Override
    public boolean isStackEqual(ItemStack itemStack, boolean ignoreMeta, boolean ignoreNBT) {
        if (GTUtility.isStackInvalid(itemStack)) {
            return false;
        }

        return GTUtility.stackEqual(this.itemStack, itemStack, ignoreMeta, ignoreNBT);
    }

    public boolean isStackEqual(ItemStack itemStack) {
        return isStackEqual(itemStack, false, false);
    }

    @Override
    public ItemStack get(long amount, ItemStack... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(itemStack)) {
            return GTUtility.copyAmount(amount, replacements);
        }
        return GTUtility.copyAmount(amount, itemStack);
    }

    @Override
    public ItemStack getWithDamage(long amount, long meta, ItemStack... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(itemStack)) return GTUtility.copyAmount(amount, replacements);
        return GTUtility.copyAmountAndMetaData(amount, meta, itemStack);
    }

    private void sanityCheck() {
        if (isSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
    }
}
