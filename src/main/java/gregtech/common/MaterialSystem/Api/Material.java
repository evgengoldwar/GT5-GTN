package gregtech.common.MaterialSystem.Api;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * A material created with {@link MaterialBuilder}. Gives access to every generated item, block and fluid.
 *
 * <pre>{@code
 * ItemStack bolts = Materials.Iron.getStack(MaterialType.BOLT, 4);
 * FluidStack molten = Materials.Iron.getFluidStack(MaterialType.MOLTEN, 144);
 * }</pre>
 */
public class Material {

    private final String name;
    private final String localName;
    private final int meta;
    private final int color;
    private final Set<MaterialType> types;
    private final String iconSet;
    private final int meltingPoint;
    private final int gasTemperature;
    private final int plasmaTemperature;
    private final String tooltip;

    // spotless:off
    Material(
        String name,
        String localName,
        int meta,
        int color,
        Set<MaterialType> types,
        String iconSet,
        int meltingPoint,
        int gasTemperature,
        int plasmaTemperature,
        String tooltip
    ) {
        this.name = name;
        this.localName = localName;
        this.meta = meta;
        this.color = color;
        this.types = types;
        this.iconSet = iconSet;
        this.meltingPoint = meltingPoint;
        this.gasTemperature = gasTemperature;
        this.plasmaTemperature = plasmaTemperature;
        this.tooltip = tooltip;
    }
    // spotless:on

    public boolean hasType(MaterialType type) {
        return types.contains(type);
    }

    public Set<MaterialType> getTypes() {
        return Collections.unmodifiableSet(types);
    }

    /**
     * Internal name, used for the ore dictionary ({@code "ingot" + name}).
     */
    public String getName() {
        return name;
    }

    /**
     * Lower case name used for unlocalized names and fluid names.
     */
    public String getUnlocalizedName() {
        return name.toLowerCase(Locale.ENGLISH);
    }

    /**
     * English display name, e.g. "Stainless Steel".
     */
    public String getLocalName() {
        return localName;
    }

    public int getMeta() {
        return meta;
    }

    public int getColor() {
        return color;
    }

    public short[] getRGBA() {
        return MaterialUtils.getColorRGBA(color);
    }

    public String getIconSet() {
        return iconSet;
    }

    public int getMeltingPoint() {
        return meltingPoint;
    }

    /**
     * Fluid temperature in Kelvin for a fluid type.
     */
    public int getFluidTemperature(MaterialType type) {
        return switch (type) {
            case MOLTEN -> meltingPoint;
            case GAS -> gasTemperature;
            case PLASMA -> plasmaTemperature;
            default -> 295;
        };
    }

    /**
     * Extra tooltip line (chemical formula and similar), may be null.
     */
    public String getTooltip() {
        return tooltip;
    }

    private void checkType(MaterialType type) {
        if (!hasType(type)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + type);
        }
    }

    // region Generic access

    /**
     * Item or block stack of any non fluid type.
     */
    public ItemStack getStack(MaterialType type, int amount) {
        checkType(type);
        return switch (type.getKind()) {
            case ITEM -> MaterialRegistry.getInstance()
                .getItem(type, meta, amount);
            case BLOCK -> MaterialRegistry.getInstance()
                .getBlockStack(type, meta, amount);
            case FLUID -> throw new IllegalArgumentException(type + " is a fluid, use getFluidStack");
        };
    }

    public ItemStack getStack(MaterialType type) {
        return getStack(type, 1);
    }

    /**
     * Ore dictionary name of a type, e.g. {@code "boltSteel"}.
     */
    public String getOreDictName(MaterialType type) {
        return type.getOreDictPrefix() + name;
    }

    public Fluid getFluid(MaterialType type) {
        checkType(type);
        if (!type.isFluid()) {
            throw new IllegalArgumentException(type + " is not a fluid type");
        }
        return MaterialRegistry.getInstance()
            .getFluid(type, meta);
    }

    public FluidStack getFluidStack(MaterialType type, int amount) {
        Fluid fluid = getFluid(type);
        return fluid == null ? null : new FluidStack(fluid, amount);
    }

    // endregion

    // region Item Types
    public ItemStack getDust(int amount) {
        return getStack(MaterialType.DUST, amount);
    }

    public ItemStack getNugget(int amount) {
        return getStack(MaterialType.NUGGET, amount);
    }

    public ItemStack getIngot(int amount) {
        return getStack(MaterialType.INGOT, amount);
    }

    public ItemStack getIngot() {
        return getIngot(1);
    }

    public ItemStack getPlate(int amount) {
        return getStack(MaterialType.PLATE, amount);
    }

    public ItemStack getPlate() {
        return getPlate(1);
    }

    public ItemStack getRod(int amount) {
        return getStack(MaterialType.ROD, amount);
    }

    public ItemStack getBolt(int amount) {
        return getStack(MaterialType.BOLT, amount);
    }

    public ItemStack getScrew(int amount) {
        return getStack(MaterialType.SCREW, amount);
    }

    public ItemStack getRing(int amount) {
        return getStack(MaterialType.RING, amount);
    }

    public ItemStack getGear(int amount) {
        return getStack(MaterialType.GEAR, amount);
    }

    public ItemStack getBlockStack(int amount) {
        return getStack(MaterialType.BLOCK, amount);
    }

    public ItemStack getBlockStack() {
        return getBlockStack(1);
    }

    public ItemStack getCasingStack(int amount) {
        return getStack(MaterialType.CASING, amount);
    }

    public ItemStack getCasingStack() {
        return getCasingStack(1);
    }

    public ItemStack getFrameStack(int amount) {
        return getStack(MaterialType.FRAME, amount);
    }
    // endregion

    // region Block Types
    public Block getBlock(MaterialType type) {
        checkType(type);
        return MaterialRegistry.getInstance()
            .getBlock(type, meta);
    }

    public Block getBlock() {
        return getBlock(MaterialType.BLOCK);
    }

    public Block getCasing() {
        return getBlock(MaterialType.CASING);
    }

    public Block getFrame() {
        return getBlock(MaterialType.FRAME);
    }
    // endregion

    // region Fluid Types
    public Fluid getFluid() {
        return getFluid(MaterialType.FLUID);
    }

    public FluidStack getFluidStack(int amount) {
        return getFluidStack(MaterialType.FLUID, amount);
    }

    public FluidStack getFluidStack() {
        return getFluidStack(1);
    }

    public FluidStack getMolten(int amount) {
        return getFluidStack(MaterialType.MOLTEN, amount);
    }

    public FluidStack getGas(int amount) {
        return getFluidStack(MaterialType.GAS, amount);
    }

    public FluidStack getPlasma(int amount) {
        return getFluidStack(MaterialType.PLASMA, amount);
    }
    // endregion

    @Override
    public String toString() {
        return "Material{" + name + ", meta=" + meta + "}";
    }
}
