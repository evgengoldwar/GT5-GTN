package gregtech.api.modpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NBT helpers for complex items. All values are stored inside the {@code "gt.data"} compound of the stack.
 */
public final class ItemData {

    private static final String ROOT = "gt.data";
    private static final String DAMAGE = "gt.damage";

    private ItemData() {}

    public static NBTTagCompound get(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        if (!stack.stackTagCompound.hasKey(ROOT)) {
            stack.stackTagCompound.setTag(ROOT, new NBTTagCompound());
        }
        return stack.stackTagCompound.getCompoundTag(ROOT);
    }

    /** Read only view, does not create NBT on the stack. */
    private static NBTTagCompound peek(ItemStack stack) {
        return stack.stackTagCompound == null ? new NBTTagCompound() : stack.stackTagCompound.getCompoundTag(ROOT);
    }

    public static boolean has(ItemStack stack, String key) {
        return stack.stackTagCompound != null && stack.stackTagCompound.getCompoundTag(ROOT)
            .hasKey(key);
    }

    public static int getInt(ItemStack stack, String key) {
        return peek(stack).getInteger(key);
    }

    public static void setInt(ItemStack stack, String key, int value) {
        get(stack).setInteger(key, value);
    }

    public static long getLong(ItemStack stack, String key) {
        return peek(stack).getLong(key);
    }

    public static void setLong(ItemStack stack, String key, long value) {
        get(stack).setLong(key, value);
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return peek(stack).getBoolean(key);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        get(stack).setBoolean(key, value);
    }

    /**
     * Flips a boolean and returns the new value (handy for "mode" toggles).
     */
    public static boolean toggle(ItemStack stack, String key) {
        boolean value = !getBoolean(stack, key);
        setBoolean(stack, key, value);
        return value;
    }

    public static String getString(ItemStack stack, String key) {
        return peek(stack).getString(key);
    }

    public static void setString(ItemStack stack, String key, String value) {
        get(stack).setString(key, value);
    }

    // region Durability (items built with ItemBuilder#durability)

    public static int getDamage(ItemStack stack) {
        return stack.stackTagCompound == null ? 0 : stack.stackTagCompound.getInteger(DAMAGE);
    }

    public static void setDamage(ItemStack stack, int damage) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        stack.stackTagCompound.setInteger(DAMAGE, Math.max(0, damage));
    }

    /**
     * Damages an item that has a durability. Creative players do not damage items.
     *
     * @return true if the item broke (its stack size was reduced)
     */
    public static boolean damage(ItemStack stack, EntityPlayer player, int amount) {
        if (player != null && player.capabilities.isCreativeMode) {
            return false;
        }
        ItemDefinition definition = MetaItem.getDefinition(stack);
        if (definition == null || definition.maxDurability <= 0) {
            return false;
        }
        int damage = getDamage(stack) + amount;
        if (damage >= definition.maxDurability) {
            stack.stackSize--;
            setDamage(stack, 0);
            if (player != null) {
                player.renderBrokenItemStack(stack);
            }
            return true;
        }
        setDamage(stack, damage);
        return false;
    }

    // endregion
}
