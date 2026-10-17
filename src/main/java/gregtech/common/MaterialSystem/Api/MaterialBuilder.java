package gregtech.common.MaterialSystem.Api;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Builder for {@link Material}. The meta is the stable id of the material: it is used as item damage and block meta
 * for all generated parts, so never change it after a world has been created.
 *
 * <pre>{@code
 * Iron = new MaterialBuilder("Iron").meta(3)
 *     .color(0xC8C8C8)
 *     .addMetalParts()          // dust, nugget, ingot, plate, rod, bolt, screw, ring, gear, block, frame, molten
 *     .addPlasma()
 *     .meltingPoint(1811)
 *     .build();
 * }</pre>
 */
public class MaterialBuilder {

    private final String name;
    private String localName;
    private int meta = -1;
    private int color = 0xFFFFFF;
    private String iconSet = MaterialUtils.DEFAULT_ICON_SET;
    private int meltingPoint = 1000;
    private int gasTemperature = 295;
    private int plasmaTemperature = 10_000;
    private String tooltip;

    private final Set<MaterialType> types = EnumSet.noneOf(MaterialType.class);

    /**
     * @param name internal name without spaces, e.g. "StainlessSteel". Used for the ore dictionary.
     */
    public MaterialBuilder(String name) {
        if (name == null || name.isEmpty() || name.contains(" ")) {
            throw new IllegalArgumentException("Material name must be non empty and without spaces: '" + name + "'");
        }
        this.name = name;
        this.localName = splitCamelCase(name);
    }

    private static String splitCamelCase(String name) {
        return name.replaceAll("(?<=[a-z0-9])(?=[A-Z])", " ")
            .replace('_', ' ');
    }

    public MaterialBuilder meta(int meta) {
        this.meta = meta;
        return this;
    }

    /**
     * English display name, by default derived from the internal name ("StainlessSteel" -> "Stainless Steel").
     */
    public MaterialBuilder localName(String localName) {
        this.localName = localName;
        return this;
    }

    public MaterialBuilder color(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255");
        }

        this.color = (r << 16) | (g << 8) | b;

        return this;
    }

    /**
     * @param rgb color as 0xRRGGBB
     */
    public MaterialBuilder color(int rgb) {
        this.color = rgb & 0xFFFFFF;
        return this;
    }

    /**
     * Icon set (like GregTech TextureSet). Textures are searched in
     * {@code MaterialSystems/<Items|Blocks|Fluids>/<iconSet>/<type>.png} and fall back to the default ones.
     */
    public MaterialBuilder iconSet(String iconSet) {
        this.iconSet = iconSet;
        return this;
    }

    /**
     * Melting point in Kelvin, used as temperature of the {@link MaterialType#MOLTEN} fluid.
     */
    public MaterialBuilder meltingPoint(int kelvin) {
        this.meltingPoint = kelvin;
        return this;
    }

    public MaterialBuilder gasTemperature(int kelvin) {
        this.gasTemperature = kelvin;
        return this;
    }

    public MaterialBuilder plasmaTemperature(int kelvin) {
        this.plasmaTemperature = kelvin;
        return this;
    }

    /**
     * Extra tooltip line for every item of the material, e.g. a chemical formula.
     */
    public MaterialBuilder tooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    // region Components

    public MaterialBuilder add(MaterialType... types) {
        Collections.addAll(this.types, types);
        return this;
    }

    public MaterialBuilder remove(MaterialType... types) {
        for (MaterialType type : types) {
            this.types.remove(type);
        }
        return this;
    }

    /**
     * Typical GregTech metal: dust, nugget, ingot, plate, rod, bolt, screw, ring, gear, block, frame and molten metal.
     */
    public MaterialBuilder addMetalParts() {
        return add(
            MaterialType.DUST,
            MaterialType.SMALL_DUST,
            MaterialType.TINY_DUST,
            MaterialType.NUGGET,
            MaterialType.INGOT,
            MaterialType.PLATE,
            MaterialType.ROD,
            MaterialType.BOLT,
            MaterialType.SCREW,
            MaterialType.RING,
            MaterialType.GEAR,
            MaterialType.BLOCK,
            MaterialType.FRAME,
            MaterialType.MOLTEN);
    }

    /**
     * Gem material: dust, gem, plate, lens, rod, block.
     */
    public MaterialBuilder addGemParts() {
        return add(
            MaterialType.DUST,
            MaterialType.SMALL_DUST,
            MaterialType.TINY_DUST,
            MaterialType.GEM,
            MaterialType.PLATE,
            MaterialType.LENS,
            MaterialType.ROD,
            MaterialType.BLOCK);
    }

    public MaterialBuilder addDust() {
        return add(MaterialType.DUST, MaterialType.SMALL_DUST, MaterialType.TINY_DUST);
    }

    public MaterialBuilder addNugget() {
        return add(MaterialType.NUGGET);
    }

    public MaterialBuilder addIngot() {
        return add(MaterialType.INGOT);
    }

    public MaterialBuilder addHotIngot() {
        return add(MaterialType.HOT_INGOT);
    }

    public MaterialBuilder addGem() {
        return add(MaterialType.GEM);
    }

    public MaterialBuilder addPlate() {
        return add(MaterialType.PLATE);
    }

    public MaterialBuilder addDoublePlate() {
        return add(MaterialType.DOUBLE_PLATE);
    }

    public MaterialBuilder addDensePlate() {
        return add(MaterialType.DENSE_PLATE);
    }

    public MaterialBuilder addFoil() {
        return add(MaterialType.FOIL);
    }

    public MaterialBuilder addRod() {
        return add(MaterialType.ROD);
    }

    public MaterialBuilder addLongRod() {
        return add(MaterialType.LONG_ROD);
    }

    public MaterialBuilder addBolt() {
        return add(MaterialType.BOLT);
    }

    public MaterialBuilder addScrew() {
        return add(MaterialType.SCREW);
    }

    public MaterialBuilder addRing() {
        return add(MaterialType.RING);
    }

    public MaterialBuilder addSpring() {
        return add(MaterialType.SPRING);
    }

    public MaterialBuilder addSmallSpring() {
        return add(MaterialType.SMALL_SPRING);
    }

    public MaterialBuilder addGear() {
        return add(MaterialType.GEAR);
    }

    public MaterialBuilder addSmallGear() {
        return add(MaterialType.SMALL_GEAR);
    }

    public MaterialBuilder addRotor() {
        return add(MaterialType.ROTOR);
    }

    public MaterialBuilder addFineWire() {
        return add(MaterialType.FINE_WIRE);
    }

    public MaterialBuilder addLens() {
        return add(MaterialType.LENS);
    }

    public MaterialBuilder addBlock() {
        return add(MaterialType.BLOCK);
    }

    public MaterialBuilder addCasing() {
        return add(MaterialType.CASING);
    }

    public MaterialBuilder addFrame() {
        return add(MaterialType.FRAME);
    }

    /**
     * Plain liquid (water like), fluid name is the lower case material name.
     */
    public MaterialBuilder addFluid() {
        return add(MaterialType.FLUID);
    }

    /**
     * Molten metal ("molten.iron"), temperature = {@link #meltingPoint(int)}.
     */
    public MaterialBuilder addMolten() {
        return add(MaterialType.MOLTEN);
    }

    public MaterialBuilder addGas() {
        return add(MaterialType.GAS);
    }

    public MaterialBuilder addPlasma() {
        return add(MaterialType.PLASMA);
    }

    // endregion

    public Material build() {
        if (meta < 0 || meta > MaterialUtils.MAX_MATERIAL_META) {
            throw new IllegalStateException("Invalid material meta: " + meta);
        }

        if (types.isEmpty()) {
            throw new IllegalStateException("Material " + name + " has no components");
        }

        // spotless:off
        Material material = new Material(
            name,
            localName,
            meta,
            color,
            EnumSet.copyOf(types),
            iconSet,
            meltingPoint,
            gasTemperature,
            plasmaTemperature,
            tooltip);
        // spotless:on

        MaterialRegistry.getInstance()
            .registerMaterial(material);

        return material;
    }
}
