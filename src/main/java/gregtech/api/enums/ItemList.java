package gregtech.api.enums;

import static gregtech.GTLoggers.GT_FML_LOGGER;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

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

    private ItemStack mStack;
    private boolean mHasNotBeenSet;
    private boolean mWarned;

    ItemList() {
        mHasNotBeenSet = true;
    }

    @Override
    public IItemContainer set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null) return this;
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = GTUtility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        mHasNotBeenSet = false;
        mStack = GTUtility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer hidden() {
        API.hideItem(get(1L));
        return this;
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        return null;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        return null;
    }

    @Override
    public ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements) {
        return null;
    }

    @Override
    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        return null;
    }

    @Override
    public Item getItem() {
        sanityCheck();
        if (GTUtility.isStackInvalid(mStack)) return null;
        return mStack.getItem();
    }

    @Override
    public Block getBlock() {
        sanityCheck();
        return GTUtility.getBlockFromItem(getItem());
    }

    @Override
    public final boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    @Override
    public boolean isStackEqual(Object aStack) {
        return isStackEqual(aStack, false, false);
    }

    @Override
    public boolean isStackEqual(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
        if (!mWarned) {
            GT_FML_LOGGER.error(new Exception(this + " is now deprecated"));
            // warn only once
            mWarned = true;
        }
        if (GTUtility.isStackInvalid(aStack)) return false;

        return true;
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(mStack)) {
            GT_FML_LOGGER.debug("Object in the ItemList is null at:");
            GT_FML_LOGGER.debug(new NullPointerException());
            return GTUtility.copyAmount(aAmount, aReplacements);
        }
        return GTUtility.copyAmount(aAmount, mStack);
    }

    @Override
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        return null;
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        return null;
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        return null;
    }

    @Override
    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(mStack)) return GTUtility.copyAmount(aAmount, aReplacements);
        return GTUtility.copyAmountAndMetaData(aAmount, aMetaValue, mStack);
    }

    @Override
    public IItemContainer setRender(IItemRenderer aRenderer) {
        return this;
    }

    private void sanityCheck() {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (!mWarned) {
            GT_FML_LOGGER.error(new Exception(this + " is now deprecated"));
            // warn only once
            mWarned = true;
        }
    }
}
