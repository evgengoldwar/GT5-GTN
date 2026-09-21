package gregtech.common.MaterialSystem.Api;

import java.util.EnumSet;
import java.util.Set;

public class MaterialBuilder {

    private final String name;
    private int meta = -1;
    private int color = 0xFFFFFF;

    private final Set<MaterialType> types = EnumSet.noneOf(MaterialType.class);

    public MaterialBuilder(String name) {
        this.name = name;
    }

    public MaterialBuilder meta(int meta) {
        this.meta = meta;
        return this;
    }

    public MaterialBuilder color(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255");
        }

        this.color = (r << 16) | (g << 8) | b;

        return this;
    }

    public MaterialBuilder addIngot() {
        types.add(MaterialType.INGOT);
        return this;
    }

    public MaterialBuilder addPlate() {
        types.add(MaterialType.PLATE);
        return this;
    }

    public MaterialBuilder addBlock() {
        types.add(MaterialType.BLOCK);
        return this;
    }

    public MaterialBuilder addCasing() {
        types.add(MaterialType.CASING);
        return this;
    }

    public MaterialBuilder addFluid() {
        types.add(MaterialType.FLUID);
        return this;
    }

    public Material build() {
        if (meta < 0 || meta > MaterialUtils.MAX_MATERIAL_META) {
            throw new IllegalStateException("Invalid material meta: " + meta);
        }

        if (types.isEmpty()) {
            throw new IllegalStateException("Material " + name + " has no components");
        }

        Material material = new Material(name, meta, color, EnumSet.copyOf(types));

        MaterialRegistry.getInstance()
            .registerMaterial(material);

        return material;
    }
}
