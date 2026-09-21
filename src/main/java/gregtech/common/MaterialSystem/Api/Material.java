package gregtech.common.MaterialSystem.Api;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

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

    // region Item Types
    public ItemStack getIngot(int amount) {
        MaterialType materialType = MaterialType.INGOT;

        if (!hasType(materialType)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + materialType);
        }

        return MaterialRegistry.getInstance()
            .getItem(materialType, meta, amount);
    }

    public ItemStack getIngot() {
        return getIngot(1);
    }

    public ItemStack getPlate(int amount) {
        MaterialType materialType = MaterialType.PLATE;

        if (!hasType(materialType)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + materialType);
        }

        return MaterialRegistry.getInstance()
            .getItem(materialType, meta, amount);
    }

    public ItemStack getPlate() {
        return getPlate(1);
    }

    public ItemStack getBlockStack(int amount) {
        MaterialType materialType = MaterialType.BLOCK;

        if (!hasType(materialType)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + materialType);
        }

        return MaterialRegistry.getInstance()
            .getBlockStack(materialType, meta, amount);
    }

    public ItemStack getBlockStack() {
        return getBlockStack(1);
    }

    public ItemStack getCasingStack(int amount) {
        MaterialType materialType = MaterialType.CASING;

        if (!hasType(materialType)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + materialType);
        }

        return MaterialRegistry.getInstance()
            .getBlockStack(materialType, meta, amount);
    }

    public ItemStack getCasingStack() {
        return getCasingStack(1);
    }
    // endregion

    // region Block Types
    public Block getBlock() {
        MaterialType materialType = MaterialType.BLOCK;

        if (!hasType(materialType)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + materialType);
        }

        return MaterialRegistry.getInstance()
            .getBlock(materialType, meta);
    }

    public Block getCasing() {
        MaterialType materialType = MaterialType.CASING;

        if (!hasType(materialType)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + materialType);
        }

        return MaterialRegistry.getInstance()
            .getBlock(materialType, meta);
    }
    // endregion

    // region Fluid Types
    public Fluid getFluid() {
        if (!hasType(MaterialType.FLUID)) {
            throw new IllegalArgumentException("Material " + name + " does not have component " + MaterialType.FLUID);
        }

        return MaterialRegistry.getInstance()
            .getFluid(meta);
    }

    public FluidStack getFluidStack(int amount) {
        Fluid fluid = getFluid();

        return fluid == null ? null : new FluidStack(fluid, amount);
    }

    public FluidStack getFluidStack() {
        return getFluidStack(1);
    }
    // endregion
}
