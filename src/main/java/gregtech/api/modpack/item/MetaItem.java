package gregtech.api.modpack.item;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.util.ResourceIcon;

/**
 * One registered item holding up to 32767 sub items (meta 0..32766). Each sub item is defined with
 * {@link #add(int, String)}.
 *
 * <pre>{@code
 * public static MetaItem COMPONENTS;
 * public static ItemEntry BASIC_CIRCUIT, STEEL_GEARBOX;
 *
 * COMPONENTS = MetaItem.create("components");
 * BASIC_CIRCUIT = COMPONENTS.add(1, "basic_circuit")
 *     .name("Basic Circuit")
 *     .tooltip("Tier: LV")
 *     .oreDict("circuitBasic")
 *     .build();
 * }</pre>
 */
public class MetaItem extends Item {

    private static final Map<String, MetaItem> ITEMS = new HashMap<>();
    private static final IFuelHandler FUEL_HANDLER = fuel -> {
        ItemDefinition definition = getDefinition(fuel);
        return definition == null ? 0 : definition.burnTime;
    };
    private static boolean fuelHandlerRegistered = false;

    public static final int MAX_META = Short.MAX_VALUE - 1;

    private final String id;
    private final Map<Integer, ItemDefinition> definitions = new TreeMap<>();

    @SideOnly(Side.CLIENT)
    private Map<Integer, IIcon> icons;

    protected MetaItem(String id) {
        this.id = id;
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName("gt.metaitem." + id);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    /**
     * Creates and registers a new meta item.
     *
     * @param id registry name, lower case. Never change it after release.
     */
    public static MetaItem create(String id) {
        ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "MetaItem " + id);
        if (!id.equals(id.toLowerCase(Locale.ENGLISH)) || id.contains(" ")) {
            throw new IllegalArgumentException("Item id must be lower case without spaces: " + id);
        }
        if (ITEMS.containsKey(id)) {
            throw new IllegalStateException("MetaItem " + id + " already exists");
        }
        MetaItem item = new MetaItem(id);
        GameRegistry.registerItem(item, "metaitem." + id);
        ITEMS.put(id, item);
        if (!fuelHandlerRegistered) {
            GameRegistry.registerFuelHandler(FUEL_HANDLER);
            fuelHandlerRegistered = true;
        }
        return item;
    }

    public static MetaItem get(String id) {
        return ITEMS.get(id);
    }

    /**
     * Definition of a stack if it is a MetaItem, otherwise null.
     */
    public static ItemDefinition getDefinition(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof MetaItem item)) {
            return null;
        }
        return item.definitions.get(stack.getItemDamage());
    }

    /**
     * Starts the definition of a sub item.
     *
     * @param meta stable id inside this item, 0..32766. Never change it after release.
     * @param id   lower case name, used for the unlocalized name and the default texture
     */
    public ItemBuilder add(int meta, String id) {
        if (meta < 0 || meta > MAX_META) {
            throw new IllegalArgumentException("Meta out of range 0.." + MAX_META + ": " + meta);
        }
        if (definitions.containsKey(meta)) {
            throw new IllegalStateException(
                "Meta " + meta + " of " + this.id + " is already used by " + definitions.get(meta).id);
        }
        return new ItemBuilder(this, new ItemDefinition(meta, id));
    }

    void register(ItemDefinition definition) {
        definitions.put(definition.meta, definition);
    }

    public String getId() {
        return id;
    }

    public ItemDefinition getDefinition(int meta) {
        return definitions.get(meta);
    }

    private ItemDefinition definition(ItemStack stack) {
        return definitions.get(stack.getItemDamage());
    }

    // region Naming and rendering

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        return definition == null ? super.getUnlocalizedName(stack) : getUnlocalizedName() + "." + definition.id;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new HashMap<>();
        for (ItemDefinition definition : definitions.values()) {
            icons.put(definition.meta, register.registerIcon(ResourceIcon.withDomain(definition.texture)));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        IIcon icon = icons == null ? null : icons.get(meta);
        return icon != null ? icon : super.getIconFromDamage(meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        ItemDefinition definition = definition(stack);
        return definition == null ? 0xFFFFFF : definition.color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (ItemDefinition definition : definitions.values()) {
            if (!definition.hidden) {
                list.add(new ItemStack(this, 1, definition.meta));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return;
        }
        tooltip.addAll(definition.tooltips);
        for (ItemBehavior behavior : definition.behaviors) {
            behavior.addTooltip(stack, player, tooltip, advanced);
        }
        if (definition.maxDurability > 0) {
            int left = definition.maxDurability - ItemData.getDamage(stack);
            tooltip.add("Durability: " + left + " / " + definition.maxDurability);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        return definition == null ? EnumRarity.common : definition.rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return false;
        }
        if (definition.glint) {
            return true;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            if (behavior.hasGlint(stack)) {
                return true;
            }
        }
        return false;
    }

    // endregion

    // region Stack size, durability, container

    @Override
    public int getItemStackLimit(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        return definition == null ? 64 : definition.maxStackSize;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        return definition != null && definition.maxDurability > 0 && ItemData.getDamage(stack) > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        if (definition == null || definition.maxDurability <= 0) {
            return 0;
        }
        return (double) ItemData.getDamage(stack) / definition.maxDurability;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        return definition != null && definition.containerItem != null;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        return definition == null || definition.containerItem == null ? null : definition.containerItem.copy();
    }

    // endregion

    // region Behaviors

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return stack;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            int duration = behavior.getUseDuration(stack);
            if (duration > 0) {
                player.setItemInUse(stack, duration);
                return stack;
            }
        }
        ItemStack result = stack;
        for (ItemBehavior behavior : definition.behaviors) {
            result = behavior.onRightClick(result, world, player);
            if (result == null || result.stackSize <= 0) {
                break;
            }
        }
        return result;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return false;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            if (behavior.onUseOnBlock(stack, player, world, x, y, z, side, hitX, hitY, hitZ)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return false;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            if (behavior.onUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return false;
        }
        boolean cancel = false;
        for (ItemBehavior behavior : definition.behaviors) {
            cancel |= behavior.onLeftClickEntity(stack, player, entity);
        }
        return cancel;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return false;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            if (behavior.onRightClickEntity(stack, player, target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return false;
        }
        boolean cancel = false;
        for (ItemBehavior behavior : definition.behaviors) {
            cancel |= behavior.onSwing(stack, entity);
        }
        return cancel;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean selected) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            behavior.onTick(stack, world, holder, slot, selected);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return 0;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            int duration = behavior.getUseDuration(stack);
            if (duration > 0) {
                return duration;
            }
        }
        return 0;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return EnumAction.none;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            if (behavior.getUseDuration(stack) > 0) {
                return behavior.getUseAction(stack);
            }
        }
        return EnumAction.none;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            behavior.onUsingTick(stack, player, count);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int remainingTicks) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return;
        }
        for (ItemBehavior behavior : definition.behaviors) {
            behavior.onStoppedUsing(stack, world, player, remainingTicks);
        }
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        ItemDefinition definition = definition(stack);
        if (definition == null) {
            return stack;
        }
        ItemStack result = stack;
        for (ItemBehavior behavior : definition.behaviors) {
            result = behavior.onUseFinished(result, world, player);
        }
        return result;
    }

    // endregion
}
