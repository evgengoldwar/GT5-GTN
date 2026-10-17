package gregtech.api.modpack.item;

import net.minecraft.item.ItemStack;

/**
 * Handle to an item created by {@link ItemBuilder}. Store it in a static field and use it in recipes.
 *
 * <pre>{@code
 * GTValues.RA.stdBuilder().itemInputs(MyItems.CIRCUIT.get(2)) ...
 * }</pre>
 */
public final class ItemEntry {

    private final MetaItem item;
    private final ItemDefinition definition;

    ItemEntry(MetaItem item, ItemDefinition definition) {
        this.item = item;
        this.definition = definition;
    }

    public ItemStack get(int amount) {
        return new ItemStack(item, amount, definition.meta);
    }

    public ItemStack get() {
        return get(1);
    }

    /**
     * Wildcard free comparison: same item and same meta.
     */
    public boolean isSame(ItemStack stack) {
        return stack != null && stack.getItem() == item && stack.getItemDamage() == definition.meta;
    }

    public MetaItem getItem() {
        return item;
    }

    public ItemDefinition getDefinition() {
        return definition;
    }
}
