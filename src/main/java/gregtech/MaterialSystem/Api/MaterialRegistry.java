package gregtech.MaterialSystem.Api;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.MaterialSystem.Blocks.MaterialBlock;
import gregtech.MaterialSystem.Blocks.MaterialCasing;
import gregtech.MaterialSystem.Items.MaterialIngot;
import gregtech.MaterialSystem.Items.MaterialPlate;

public final class MaterialRegistry {

    private static final MaterialRegistry INSTANCE = new MaterialRegistry();

    private final Map<MaterialType, ItemMetaMaterial> items = new EnumMap<>(MaterialType.class);

    private final Map<MaterialType, BlockMetaMaterial> blocks = new EnumMap<>(MaterialType.class);

    private final Map<Integer, Material> materials = new HashMap<>();

    private MaterialRegistry() {}

    public static MaterialRegistry getInstance() {
        return INSTANCE;
    }

    public void init() {
        registerItem(MaterialType.INGOT, new MaterialIngot());
        registerItem(MaterialType.PLATE, new MaterialPlate());

        registerBlock(MaterialType.BLOCK, new MaterialBlock());
        registerBlock(MaterialType.CASING, new MaterialCasing());
    }

    private void registerItem(MaterialType type, ItemMetaMaterial item) {
        items.put(type, item);
        GameRegistry.registerItem(item, "meta_" + type.getTextureName());
    }

    private void registerBlock(MaterialType type, BlockMetaMaterial block) {
        blocks.put(type, block);
        GameRegistry.registerBlock(block, ItemBlockMetaMaterial.class, "meta_" + type.getTextureName());
    }

    public void registerMaterial(Material material) {
        if (material == null) {
            return;
        }

        int meta = material.getMeta();

        if (meta < 0 || meta > MaterialUtils.MAX_MATERIAL_META) {
            throw new IllegalArgumentException("Material meta out of range: " + meta);
        }

        if (materials.containsKey(meta)) {
            throw new IllegalStateException(
                "Material meta " + meta
                    + " is already registered by "
                    + materials.get(meta)
                        .getName());
        }

        materials.put(meta, material);

        for (MaterialType type : MaterialType.values()) {

            if (!material.hasType(type)) {
                continue;
            }

            ItemMetaMaterial item = items.get(type);

            if (item != null) {
                item.registerMaterial(material);
            }

            BlockMetaMaterial block = blocks.get(type);

            if (block != null) {
                block.registerMaterial(material);
            }
        }
    }

    public ItemMetaMaterial getItem(MaterialType type) {
        return items.get(type);
    }

    public BlockMetaMaterial getBlock(MaterialType type) {
        return blocks.get(type);
    }

    public ItemStack getItem(MaterialType type, int meta, int amount) {
        ItemMetaMaterial item = items.get(type);

        if (item == null || !item.hasMaterial(meta)) {
            return null;
        }

        return new ItemStack(item, amount, meta);
    }

    public ItemStack getBlock(MaterialType type, int meta, int amount) {
        BlockMetaMaterial block = blocks.get(type);

        if (block == null || block.getMaterial(meta) == null) {
            return null;
        }

        return new ItemStack(block, amount, meta);
    }

    public Material getMaterial(int meta) {
        return materials.get(meta);
    }
}
