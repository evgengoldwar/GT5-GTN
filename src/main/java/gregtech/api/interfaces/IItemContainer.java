package gregtech.api.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public interface IItemContainer {

    Item getItem();

    Block getBlock();

    boolean isStackEqual(ItemStack itemStack, boolean ignoreMeta, boolean ignoreNBT);

    ItemStack get(int amount, ItemStack replacements);

    ItemStack getWithDamage(int amount, int meta, ItemStack replacements);

    IItemContainer set(Item item);

    IItemContainer set(ItemStack itemStack);

    default IItemContainer hidden() {
        return this;
    }

    default IItemContainer setRender(IItemRenderer aRender) {
        return this;
    }

    default @Nonnull String getDisplayName() {
        ItemStack stack = get(1, null);

        if (stack == null) {
            return "null";
        }

        return stack.getDisplayName();
    }
}
