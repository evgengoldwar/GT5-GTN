package gregtech.api.modpack.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * Everything known about one meta value of a {@link MetaItem}. Created by {@link ItemBuilder}.
 */
public final class ItemDefinition {

    final int meta;
    final String id;
    String localName;
    String texture;
    int color = 0xFFFFFF;
    int maxStackSize = 64;
    int maxDurability = 0;
    int burnTime = 0;
    EnumRarity rarity = EnumRarity.common;
    boolean glint;
    boolean hidden;
    ItemStack containerItem;
    final List<String> tooltips = new ArrayList<>();
    final List<String> oreDicts = new ArrayList<>();
    final List<ItemBehavior> behaviors = new ArrayList<>();

    ItemDefinition(int meta, String id) {
        this.meta = meta;
        this.id = id;
    }

    public int getMeta() {
        return meta;
    }

    public String getId() {
        return id;
    }

    public String getLocalName() {
        return localName;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public List<ItemBehavior> getBehaviors() {
        return behaviors;
    }
}
