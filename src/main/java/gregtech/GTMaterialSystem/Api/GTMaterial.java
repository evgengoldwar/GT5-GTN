package gregtech.GTMaterialSystem.Api;

import java.util.Set;

import net.minecraft.item.ItemStack;

public class GTMaterial {

    private final String name;
    private final int meta;
    private final int color;
    private final Set<GTMaterialType> types;

    // spotless:off
    public GTMaterial(
        String name,
        int meta,
        int color,
        Set<GTMaterialType> types
    ) {
        this.name = name;
        this.meta = meta;
        this.color = color;
        this.types = types;
    }
    // spotless:on

    public boolean hasType(GTMaterialType type) {
        return types.contains(type);
    }

    public String getName() {
        return name;
    }

    public int getMeta() {
        return meta;
    }

    public int getColor() {
        return color;
    }

    public ItemStack get(GTMaterialType type, int amount) {
        if (!hasType(type)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + type);
        }

        GTMaterialRegistry registry = GTMaterialRegistry.getInstance();

        if (registry.getBlock(type) != null) {
            return registry.getBlock(type, meta, amount);
        }

        return registry.getItem(type, meta, amount);
    }
}
