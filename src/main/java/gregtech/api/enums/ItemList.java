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
    Circuit_Integrated,

    // Multiblock hatches, registered in LoaderMetaTileEntities
    Hatch_Input_Bus_ULV,
    Hatch_Input_Bus_LV,
    Hatch_Input_Bus_MV,
    Hatch_Input_Bus_HV,
    Hatch_Input_Bus_EV,
    Hatch_Input_Bus_IV,
    Hatch_Input_Bus_LuV,
    Hatch_Input_Bus_ZPM,
    Hatch_Input_Bus_UV,
    Hatch_Output_Bus_ULV,
    Hatch_Output_Bus_LV,
    Hatch_Output_Bus_MV,
    Hatch_Output_Bus_HV,
    Hatch_Output_Bus_EV,
    Hatch_Output_Bus_IV,
    Hatch_Output_Bus_LuV,
    Hatch_Output_Bus_ZPM,
    Hatch_Output_Bus_UV,
    Hatch_Input_ULV,
    Hatch_Input_LV,
    Hatch_Input_MV,
    Hatch_Input_HV,
    Hatch_Input_EV,
    Hatch_Input_IV,
    Hatch_Input_LuV,
    Hatch_Input_ZPM,
    Hatch_Input_UV,
    Hatch_Output_ULV,
    Hatch_Output_LV,
    Hatch_Output_MV,
    Hatch_Output_HV,
    Hatch_Output_EV,
    Hatch_Output_IV,
    Hatch_Output_LuV,
    Hatch_Output_ZPM,
    Hatch_Output_UV,
    Hatch_Energy_ULV,
    Hatch_Energy_LV,
    Hatch_Energy_MV,
    Hatch_Energy_HV,
    Hatch_Energy_EV,
    Hatch_Energy_IV,
    Hatch_Energy_LuV,
    Hatch_Energy_ZPM,
    Hatch_Energy_UV,
    Hatch_Dynamo_ULV,
    Hatch_Dynamo_LV,
    Hatch_Dynamo_MV,
    Hatch_Dynamo_HV,
    Hatch_Dynamo_EV,
    Hatch_Dynamo_IV,
    Hatch_Dynamo_LuV,
    Hatch_Dynamo_ZPM,
    Hatch_Dynamo_UV

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
        API.hideItem(get());
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
    public ItemStack get(int amount, ItemStack replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(itemStack)) {
            return GTUtility.copyAmount(amount, replacements);
        }
        return GTUtility.copyAmount(amount, itemStack);
    }

    public ItemStack get(int amount) {
        return get(amount, null);
    }

    public ItemStack get() {
        return get(1);
    }

    @Override
    public ItemStack getWithDamage(int amount, int meta, ItemStack replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(itemStack)) {
            return GTUtility.copyAmount(amount, replacements);
        }
        return GTUtility.copyAmountAndMetaData(amount, meta, itemStack);
    }

    public ItemStack getWithDamage(int amount, int meta) {
        return getWithDamage(amount, meta, null);
    }

    public ItemStack getWithDamage(int meta) {
        return getWithDamage(1, meta);
    }

    private void sanityCheck() {
        if (isSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
    }
}
