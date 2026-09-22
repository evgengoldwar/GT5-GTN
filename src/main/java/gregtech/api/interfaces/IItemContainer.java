package gregtech.api.interfaces;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public interface IItemContainer {

    Item getItem();

    Block getBlock();

    boolean isStackEqual(Object itemStack, boolean wildCard, boolean ignoreNBT);

    ItemStack get(long amount, Object... replacements);

    ItemStack getWithDamage(long amount, long meta, Object... replacements);

    IItemContainer set(Item item);

    IItemContainer set(ItemStack itemStack);

    default IItemContainer hidden() {
        return this;
    }

    default IItemContainer setRender(IItemRenderer aRender) {
        return this;
    }

    default @Nonnull String getDisplayName() {
        ItemStack stack = get(1);

        if (stack == null) {
            return "null";
        }

        return stack.getDisplayName();
    }
}
