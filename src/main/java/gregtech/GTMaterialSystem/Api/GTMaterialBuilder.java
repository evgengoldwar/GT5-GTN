package gregtech.GTMaterialSystem.Api;

import java.util.EnumSet;
import java.util.Set;

public class GTMaterialBuilder {

    private final String name;
    private int meta = -1;
    private int color = 0xFFFFFF;

    private final Set<GTMaterialType> types = EnumSet.noneOf(GTMaterialType.class);

    public GTMaterialBuilder(String name) {
        this.name = name;
    }

    public GTMaterialBuilder meta(int meta) {
        this.meta = meta;
        return this;
    }

    public GTMaterialBuilder color(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255");
        }

        this.color = (r << 16) | (g << 8) | b;

        return this;
    }

    public GTMaterialBuilder addIngot() {
        types.add(GTMaterialType.INGOT);
        return this;
    }

    public GTMaterialBuilder addPlate() {
        types.add(GTMaterialType.PLATE);
        return this;
    }

    public GTMaterialBuilder addBlock() {
        types.add(GTMaterialType.BLOCK);
        return this;
    }

    public GTMaterialBuilder addCasing() {
        types.add(GTMaterialType.CASING);
        return this;
    }

    public GTMaterial build() {
        if (meta < 0 || meta > GTMaterialUtils.MAX_MATERIAL_META) {
            throw new IllegalStateException("Invalid material meta: " + meta);
        }

        if (types.isEmpty()) {
            throw new IllegalStateException("Material " + name + " has no components");
        }

        GTMaterial material = new GTMaterial(name, meta, color, EnumSet.copyOf(types));

        GTMaterialRegistry.getInstance()
            .registerMaterial(material);

        return material;
    }
}
