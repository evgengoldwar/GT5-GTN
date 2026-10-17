package gregtech.api.modpack.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Logic of a complex item. Implement only what you need, every method has a "do nothing" default.
 * <p>
 * Several behaviors can be attached to one item, they are called in the order they were added.
 *
 * <pre>{@code
 * public class TeleportBehavior implements ItemBehavior {
 *
 *     public ItemStack onRightClick(ItemStack stack, World world, EntityPlayer player) {
 *         if (!world.isRemote) {
 *             player.setPositionAndUpdate(player.posX, player.posY + 10, player.posZ);
 *             ItemData.damage(stack, player, 1);
 *         }
 *         return stack;
 *     }
 * }
 * }</pre>
 */
public interface ItemBehavior {

    /**
     * Right click in the air (and on blocks if {@link #onUseOnBlock} returned false).
     *
     * @return the stack left in the hand (usually {@code stack})
     */
    default ItemStack onRightClick(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }

    /**
     * Right click on a block, after the block itself had a chance to react.
     *
     * @return true if the click was consumed
     */
    default boolean onUseOnBlock(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        return false;
    }

    /**
     * Right click on a block, before the block is activated (use for wrenches, scanners...).
     *
     * @return true to cancel the block activation
     */
    default boolean onUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        return false;
    }

    /**
     * Left click on an entity.
     *
     * @return true to cancel the normal attack
     */
    default boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
        return false;
    }

    /**
     * Right click on a living entity.
     *
     * @return true if handled
     */
    default boolean onRightClickEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
        return false;
    }

    /**
     * Any left click (air or block), called on both sides when the arm swings.
     *
     * @return true to cancel the swing animation
     */
    default boolean onSwing(ItemStack stack, EntityLivingBase entity) {
        return false;
    }

    /**
     * Called every tick while the item is in an inventory.
     */
    default void onTick(ItemStack stack, World world, Entity holder, int slot, boolean selected) {}

    /**
     * Extra tooltip lines, can depend on NBT.
     */
    default void addTooltip(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {}

    // region "Hold right click" items (bows, food like items, chargeable tools)

    /**
     * Ticks the right button must be held. 0 = not a "hold" item.
     */
    default int getUseDuration(ItemStack stack) {
        return 0;
    }

    default EnumAction getUseAction(ItemStack stack) {
        return EnumAction.none;
    }

    /**
     * Called every tick while the right button is held.
     */
    default void onUsingTick(ItemStack stack, EntityPlayer player, int remainingTicks) {}

    /**
     * Right button released before the duration ended.
     */
    default void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int remainingTicks) {}

    /**
     * Right button held for the whole duration.
     *
     * @return the stack left in the hand
     */
    default ItemStack onUseFinished(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }

    // endregion

    /**
     * Enchantment glint.
     */
    default boolean hasGlint(ItemStack stack) {
        return false;
    }
}
