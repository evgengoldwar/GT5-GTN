package gregtech.api.modpack.item;

import java.util.Collections;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.modpack.util.Lang;

/**
 * Builder of one sub item of a {@link MetaItem}. Obtain it with {@link MetaItem#add(int, String)}.
 *
 * <pre>{@code
 * // simple crafting component
 * PLATE_BENDER_HEAD = COMPONENTS.add(10, "plate_bender_head").name("Plate Bender Head").build();
 *
 * // component with tooltip
 * QUANTUM_CHIP = COMPONENTS.add(11, "quantum_chip").name("Quantum Chip")
 *     .tooltip("Used in IV circuits", "Handle with care").rarity(EnumRarity.epic).glint().build();
 *
 * // complex item with lambdas
 * HEALING_WAND = TOOLS.add(1, "healing_wand").name("Healing Wand").maxStackSize(1).durability(64)
 *     .onRightClick((stack, world, player) -> {
 *         if (!world.isRemote) {
 *             player.heal(4);
 *             ItemData.damage(stack, player, 1);
 *         }
 *         return stack;
 *     })
 *     .build();
 *
 * // complex item with a behavior class
 * MAGNET = TOOLS.add(2, "magnet").name("Magnet").maxStackSize(1).behavior(new MagnetBehavior(8)).build();
 * }</pre>
 */
public final class ItemBuilder {

    @FunctionalInterface
    public interface RightClick {

        ItemStack onRightClick(ItemStack stack, World world, EntityPlayer player);
    }

    @FunctionalInterface
    public interface UseOnBlock {

        boolean onUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side);
    }

    @FunctionalInterface
    public interface LeftClickEntity {

        boolean onLeftClick(ItemStack stack, EntityPlayer player, Entity target);
    }

    @FunctionalInterface
    public interface RightClickEntity {

        boolean onRightClick(ItemStack stack, EntityPlayer player, EntityLivingBase target);
    }

    @FunctionalInterface
    public interface Swing {

        boolean onSwing(ItemStack stack, EntityLivingBase entity);
    }

    @FunctionalInterface
    public interface Tick {

        void onTick(ItemStack stack, World world, Entity holder, int slot, boolean selected);
    }

    private final MetaItem item;
    private final ItemDefinition definition;

    ItemBuilder(MetaItem item, ItemDefinition definition) {
        this.item = item;
        this.definition = definition;
        definition.localName = definition.id;
        definition.texture = "items_custom/" + item.getId() + "/" + definition.id;
    }

    /** English display name. */
    public ItemBuilder name(String localName) {
        definition.localName = localName;
        return this;
    }

    /** Russian display name. */
    public ItemBuilder nameRu(String localName) {
        Lang.add(unlocalizedKey(), Lang.RU, localName);
        return this;
    }

    /**
     * Texture path relative to {@code assets/gregtech/textures/items/}, or {@code "mod:path"}.
     * Default: {@code items_custom/<metaitem id>/<item id>}.
     */
    public ItemBuilder texture(String texture) {
        definition.texture = texture;
        return this;
    }

    /** Color multiplier 0xRRGGBB applied to the texture. */
    public ItemBuilder color(int rgb) {
        definition.color = rgb;
        return this;
    }

    public ItemBuilder tooltip(String... lines) {
        Collections.addAll(definition.tooltips, lines);
        return this;
    }

    public ItemBuilder maxStackSize(int size) {
        definition.maxStackSize = size;
        return this;
    }

    /** Durability stored in NBT, see {@link ItemData#damage}. Also sets max stack size to 1. */
    public ItemBuilder durability(int maxDurability) {
        definition.maxDurability = maxDurability;
        definition.maxStackSize = 1;
        return this;
    }

    public ItemBuilder rarity(EnumRarity rarity) {
        definition.rarity = rarity;
        return this;
    }

    public ItemBuilder glint() {
        definition.glint = true;
        return this;
    }

    /** Not shown in creative tab / NEI item list. */
    public ItemBuilder hidden() {
        definition.hidden = true;
        return this;
    }

    /** Furnace fuel in ticks (coal = 1600). */
    public ItemBuilder burnTime(int ticks) {
        definition.burnTime = ticks;
        return this;
    }

    /** Item left in the crafting grid after crafting (like a bucket). */
    public ItemBuilder containerItem(ItemStack container) {
        definition.containerItem = container;
        return this;
    }

    public ItemBuilder oreDict(String... names) {
        Collections.addAll(definition.oreDicts, names);
        return this;
    }

    // region Logic

    public ItemBuilder behavior(ItemBehavior behavior) {
        definition.behaviors.add(behavior);
        return this;
    }

    public ItemBuilder onRightClick(RightClick handler) {
        return behavior(new ItemBehavior() {

            @Override
            public ItemStack onRightClick(ItemStack stack, World world, EntityPlayer player) {
                return handler.onRightClick(stack, world, player);
            }
        });
    }

    public ItemBuilder onUseOnBlock(UseOnBlock handler) {
        return behavior(new ItemBehavior() {

            @Override
            public boolean onUseOnBlock(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                int side, float hitX, float hitY, float hitZ) {
                return handler.onUse(stack, player, world, x, y, z, side);
            }
        });
    }

    /** Like {@link #onUseOnBlock} but runs before the block is activated (works on machines too). */
    public ItemBuilder onUseFirst(UseOnBlock handler) {
        return behavior(new ItemBehavior() {

            @Override
            public boolean onUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
                int side, float hitX, float hitY, float hitZ) {
                return handler.onUse(stack, player, world, x, y, z, side);
            }
        });
    }

    public ItemBuilder onLeftClickEntity(LeftClickEntity handler) {
        return behavior(new ItemBehavior() {

            @Override
            public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
                return handler.onLeftClick(stack, player, target);
            }
        });
    }

    public ItemBuilder onRightClickEntity(RightClickEntity handler) {
        return behavior(new ItemBehavior() {

            @Override
            public boolean onRightClickEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
                return handler.onRightClick(stack, player, target);
            }
        });
    }

    /** Any left click (air or block). */
    public ItemBuilder onSwing(Swing handler) {
        return behavior(new ItemBehavior() {

            @Override
            public boolean onSwing(ItemStack stack, EntityLivingBase entity) {
                return handler.onSwing(stack, entity);
            }
        });
    }

    public ItemBuilder onTick(Tick handler) {
        return behavior(new ItemBehavior() {

            @Override
            public void onTick(ItemStack stack, World world, Entity holder, int slot, boolean selected) {
                handler.onTick(stack, world, holder, slot, selected);
            }
        });
    }

    // endregion

    private String unlocalizedKey() {
        return item.getUnlocalizedName() + "." + definition.id + ".name";
    }

    public ItemEntry build() {
        item.register(definition);
        Lang.add(unlocalizedKey(), definition.localName);
        ItemEntry entry = new ItemEntry(item, definition);
        for (String oreDict : definition.oreDicts) {
            OreDictionary.registerOre(oreDict, entry.get());
        }
        return entry;
    }
}
