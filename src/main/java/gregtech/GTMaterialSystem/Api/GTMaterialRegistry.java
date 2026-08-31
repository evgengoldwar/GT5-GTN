package gregtech.GTMaterialSystem.Api;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTMaterialSystem.Blocks.GTMaterialBlockGT;
import gregtech.GTMaterialSystem.Items.GTMaterialIngot;
import gregtech.GTMaterialSystem.Items.GTMaterialPlate;

public final class GTMaterialRegistry {

    private static final GTMaterialRegistry INSTANCE = new GTMaterialRegistry();

    private final Map<GTMaterialType, ItemMetaGTMaterial> items = new EnumMap<>(GTMaterialType.class);

    private final Map<GTMaterialType, BlockMetaGTMaterial> blocks = new EnumMap<>(GTMaterialType.class);

    private final Map<Integer, GTMaterial> materials = new HashMap<>();

    private GTMaterialRegistry() {}

    public static GTMaterialRegistry getInstance() {
        return INSTANCE;
    }

    public void init() {
        registerItem(GTMaterialType.INGOT, new GTMaterialIngot());
        registerItem(GTMaterialType.PLATE, new GTMaterialPlate());

        registerBlock(GTMaterialType.BLOCK, new GTMaterialBlockGT());
    }

    private void registerItem(GTMaterialType type, ItemMetaGTMaterial item) {
        items.put(type, item);
        GameRegistry.registerItem(item, "meta_" + type.getTextureName());
    }

    private void registerBlock(GTMaterialType type, BlockMetaGTMaterial block) {
        blocks.put(type, block);
        GameRegistry.registerBlock(block, ItemBlockMetaGTMaterial.class, "meta_" + type.getTextureName());
    }

    public void registerMaterial(GTMaterial material) {
        if (material == null) {
            return;
        }

        int meta = material.getMeta();

        if (meta < 0 || meta > 32_000) {
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

        for (GTMaterialType type : GTMaterialType.values()) {

            if (!material.hasType(type)) {
                continue;
            }

            ItemMetaGTMaterial item = items.get(type);

            if (item != null) {
                item.registerMaterial(material);
            }

            BlockMetaGTMaterial block = blocks.get(type);

            if (block != null) {
                block.registerMaterial(material);
            }
        }
    }

    public ItemMetaGTMaterial getItem(GTMaterialType type) {
        return items.get(type);
    }

    public BlockMetaGTMaterial getBlock(GTMaterialType type) {
        return blocks.get(type);
    }

    public ItemStack getItem(GTMaterialType type, int meta, int amount) {
        ItemMetaGTMaterial item = items.get(type);

        if (item == null || !item.hasMaterial(meta)) {
            return null;
        }

        return new ItemStack(item, amount, meta);
    }

    public ItemStack getBlock(GTMaterialType type, int meta, int amount) {
        BlockMetaGTMaterial block = blocks.get(type);

        if (block == null || block.getMaterial(meta) == null) {
            return null;
        }

        return new ItemStack(block, amount, meta);
    }

    public GTMaterial getMaterial(int meta) {
        return materials.get(meta);
    }
}
