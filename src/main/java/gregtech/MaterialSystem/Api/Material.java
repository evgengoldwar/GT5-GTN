package gregtech.MaterialSystem.Api;

import net.minecraft.item.ItemStack;

import java.util.Set;

public class Material {

    private final String name;
    private final int meta;
    private final int color;
    private final Set<MaterialType> types;

    // spotless:off
    public Material(
        String name,
        int meta,
        int color,
        Set<MaterialType> types
    ) {
        this.name = name;
        this.meta = meta;
        this.color = color;
        this.types = types;
    }
    // spotless:on

    public boolean hasType(MaterialType type) {
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

    public ItemStack get(MaterialType type, int amount) {
        if (!hasType(type)) {
            throw new IllegalArgumentException(
                "Material " + name +
                    " does not have component " + type
            );
        }

        return MaterialRegistry.getInstance()
            .getItem(type, meta, amount);
    }
}
