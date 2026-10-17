package gregtech.common.MaterialSystem.Api;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.fluid.GTFluidFactory;

public final class MaterialRegistry {

    private static final MaterialRegistry INSTANCE = new MaterialRegistry();

    private final Map<MaterialType, ItemMetaMaterial> items = new EnumMap<>(MaterialType.class);

    private final Map<MaterialType, BlockMetaMaterial> blocks = new EnumMap<>(MaterialType.class);

    private final Map<MaterialType, Map<Integer, Fluid>> fluids = new EnumMap<>(MaterialType.class);

    private final Map<Integer, Material> materials = new TreeMap<>();

    private final Map<String, Material> materialsByName = new HashMap<>();

    private boolean initialized = false;

    private MaterialRegistry() {}

    public static MaterialRegistry getInstance() {
        return INSTANCE;
    }

    public void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        for (MaterialType type : MaterialType.values()) {
            switch (type.getKind()) {
                case ITEM -> registerItem(type, new ItemMetaMaterial(type));
                case BLOCK -> registerBlock(type, new BlockMetaMaterial(type));
                case FLUID -> fluids.put(type, new HashMap<>());
            }
        }
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

        if (!initialized) {
            throw new IllegalStateException(
                "MaterialRegistry is not initialized yet, create materials in IModpackModule.registerMaterials()");
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

        if (materialsByName.containsKey(material.getName())) {
            throw new IllegalStateException("Material name " + material.getName() + " is already registered");
        }

        materials.put(meta, material);
        materialsByName.put(material.getName(), material);

        for (MaterialType type : MaterialType.values()) {

            if (!material.hasType(type)) {
                continue;
            }

            switch (type.getKind()) {
                case ITEM -> items.get(type)
                    .registerMaterial(material);
                case BLOCK -> blocks.get(type)
                    .registerMaterial(material);
                case FLUID -> registerFluid(material, type);
            }

            if (!type.isFluid() && !type.getOreDictPrefix()
                .isEmpty()) {
                OreDictionary.registerOre(material.getOreDictName(type), material.getStack(type, 1));
            }
        }
    }

    private void registerFluid(Material material, MaterialType type) {
        Fluid fluid = GTFluidFactory.builder(type.getFluidName(material))
            .withDefaultLocalName(type.formatLocalName(material))
            .withColorRGBA(material.getRGBA())
            .withStillIconResourceLocation(new ResourceLocation(MaterialUtils.getTexturePath(type)))
            .withTemperature(material.getFluidTemperature(type))
            .withGaseous(type == MaterialType.GAS || type == MaterialType.PLASMA)
            .buildAndRegister()
            .addLocalizedName()
            .asFluid();

        fluids.get(type)
            .put(material.getMeta(), fluid);
    }

    public Fluid getFluid(MaterialType type, int meta) {
        Map<Integer, Fluid> map = fluids.get(type);
        return map == null ? null : map.get(meta);
    }

    public Fluid getFluid(int meta) {
        return getFluid(MaterialType.FLUID, meta);
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

    public ItemStack getBlockStack(MaterialType type, int meta, int amount) {
        BlockMetaMaterial block = blocks.get(type);

        if (block == null || block.getMaterial(meta) == null) {
            return null;
        }

        return new ItemStack(block, amount, meta);
    }

    public Block getBlock(MaterialType type, int meta) {
        BlockMetaMaterial block = blocks.get(type);

        if (block == null || block.getMaterial(meta) == null) {
            return null;
        }

        return block;
    }

    public Material getMaterial(int meta) {
        return materials.get(meta);
    }

    public Material getMaterial(String name) {
        return materialsByName.get(name);
    }

    /**
     * All materials sorted by meta.
     */
    public Collection<Material> getMaterials() {
        return Collections.unmodifiableCollection(materials.values());
    }
}
