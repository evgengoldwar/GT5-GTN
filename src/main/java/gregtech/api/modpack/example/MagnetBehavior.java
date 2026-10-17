package gregtech.api.modpack.example;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import gregtech.api.modpack.item.ItemBehavior;
import gregtech.api.modpack.item.ItemData;

/**
 * Example of a complex item: right click toggles it, while enabled it pulls items around the player.
 */
public class MagnetBehavior implements ItemBehavior {

    private final double range;

    public MagnetBehavior(double range) {
        this.range = range;
    }

    @Override
    public ItemStack onRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            boolean enabled = ItemData.toggle(stack, "enabled");
            player.addChatComponentMessage(new ChatComponentText("Magnet " + (enabled ? "enabled" : "disabled")));
        }
        return stack;
    }

    @Override
    public void onTick(ItemStack stack, World world, Entity holder, int slot, boolean selected) {
        if (world.isRemote || !(holder instanceof EntityPlayer player) || !ItemData.getBoolean(stack, "enabled")) {
            return;
        }
        AxisAlignedBB box = player.boundingBox.expand(range, range, range);
        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, box);
        for (EntityItem item : items) {
            item.setPosition(player.posX, player.posY, player.posZ);
        }
    }

    @Override
    public void addTooltip(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add("Range: " + (int) range + " blocks");
        tooltip.add(ItemData.getBoolean(stack, "enabled") ? "Enabled" : "Disabled");
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return ItemData.getBoolean(stack, "enabled");
    }
}
