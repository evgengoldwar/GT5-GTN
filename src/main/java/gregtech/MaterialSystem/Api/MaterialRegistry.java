package gregtech.MaterialSystem.Api;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.MaterialSystem.Items.ItemIngot;
import gregtech.MaterialSystem.Items.ItemPlate;

public final class MaterialRegistry {

    private static final MaterialRegistry INSTANCE = new MaterialRegistry();

    private final Map<MaterialType, ItemMetaMaterial> items = new EnumMap<>(MaterialType.class);

    private final Map<Integer, Material> materials = new HashMap<>();

    private MaterialRegistry() {}

    public static MaterialRegistry getInstance() {
        return INSTANCE;
    }

    public void init() {
        register(MaterialType.INGOT, new ItemIngot());
        register(MaterialType.PLATE, new ItemPlate());
    }

    private void register(MaterialType type, ItemMetaMaterial item) {

        items.put(type, item);

        GameRegistry.registerItem(item, "meta_" + type.getTextureName());
    }

    public void registerMaterial(Material material) {

        if (material == null) {
            return;
        }

        int meta = material.getMeta();

        if (materials.containsKey(meta)) {
            throw new IllegalStateException(
                "Material meta " + meta
                    + " is already registered by "
                    + materials.get(meta)
                        .getName());
        }

        materials.put(meta, material);

        for (MaterialType type : MaterialType.values()) {

            if (material.hasType(type)) {

                ItemMetaMaterial item = items.get(type);

                if (item != null) {
                    item.registerMaterial(material);
                }
            }
        }
    }

    public Material getMaterial(int meta) {
        return materials.get(meta);
    }

    public ItemMetaMaterial getItem(MaterialType type) {
        return items.get(type);
    }

    public ItemStack getItem(MaterialType type, int meta, int amount) {

        ItemMetaMaterial item = items.get(type);

        if (item == null) {
            return null;
        }

        if (!item.hasMaterial(meta)) {
            return null;
        }

        return new ItemStack(item, amount, meta);
    }

    public ItemStack getIngot(int meta, int amount) {
        return getItem(MaterialType.INGOT, meta, amount);
    }

    public ItemStack getPlate(int meta, int amount) {
        return getItem(MaterialType.PLATE, meta, amount);
    }
}
