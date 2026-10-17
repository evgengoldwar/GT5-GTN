package gregtech.api.modpack.multiblock;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gtnewhorizon.structurelib.StructureLibAPI;
import com.gtnewhorizon.structurelib.structure.IStructureElement;
import com.gtnewhorizon.structurelib.structure.StructureUtility;

import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modpack.hatch.CasingTextures;
import gregtech.api.modpack.hatch.HatchType;
import gregtech.api.modpack.hatch.MTEHatch;
import gregtech.common.MaterialSystem.Api.Material;
import gregtech.common.MaterialSystem.Api.MaterialType;

/**
 * Structure elements for {@link MTEMultiblockBase} shapes (thin wrappers around StructureLib).
 *
 * <pre>{@code
 * .addElement('C', MultiblockElements.casing(Materials.Steel))                         // casing only, counted
 * .addElement('H', MultiblockElements.hatchOrCasing(Materials.Steel, HatchType.PROCESSING))
 * .addElement('G', MultiblockElements.block(Blocks.glass, 0))                         // any fixed block
 * .addElement('A', MultiblockElements.air())                                          // must be air
 * }</pre>
 */
public final class MultiblockElements {

    private MultiblockElements() {}

    /** Exact block + meta, increases the casing counter of the controller. */
    public static IStructureElement<MTEMultiblockBase> casing(Block block, int meta) {
        return new IStructureElement<>() {

            @Override
            public boolean check(MTEMultiblockBase machine, World world, int x, int y, int z) {
                if (world.getBlock(x, y, z) == block && world.getBlockMetadata(x, y, z) == meta) {
                    machine.onCasingFound();
                    return true;
                }
                return false;
            }

            @Override
            public boolean spawnHint(MTEMultiblockBase machine, World world, int x, int y, int z, ItemStack trigger) {
                StructureLibAPI.hintParticle(world, x, y, z, block, meta);
                return true;
            }

            @Override
            public boolean placeBlock(MTEMultiblockBase machine, World world, int x, int y, int z, ItemStack trigger) {
                world.setBlock(x, y, z, block, meta, 2);
                return true;
            }
        };
    }

    /** Material casing block ({@link MaterialType#CASING}). */
    public static IStructureElement<MTEMultiblockBase> casing(Material material) {
        return casing(material.getBlock(MaterialType.CASING), material.getMeta());
    }

    /**
     * A hatch of one of the allowed types, or the casing block. Hatches get the casing texture.
     */
    public static IStructureElement<MTEMultiblockBase> hatchOrCasing(Block block, int meta, HatchType... allowed) {
        int textureId = CasingTextures.register(block, meta);
        Set<HatchType> types = allowed.length == 0 ? EnumSet.allOf(HatchType.class)
            : EnumSet.of(allowed[0], allowed);
        IStructureElement<MTEMultiblockBase> casing = casing(block, meta);
        return new IStructureElement<>() {

            @Override
            public boolean check(MTEMultiblockBase machine, World world, int x, int y, int z) {
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile instanceof IGTE gte && gte.getMTE() instanceof MTEHatch hatch) {
                    return types.contains(hatch.getType()) && machine.addHatch(hatch, textureId);
                }
                return casing.check(machine, world, x, y, z);
            }

            @Override
            public boolean spawnHint(MTEMultiblockBase machine, World world, int x, int y, int z, ItemStack trigger) {
                StructureLibAPI.hintParticle(world, x, y, z, block, meta);
                return true;
            }

            @Override
            public boolean placeBlock(MTEMultiblockBase machine, World world, int x, int y, int z, ItemStack trigger) {
                return casing.placeBlock(machine, world, x, y, z, trigger);
            }
        };
    }

    public static IStructureElement<MTEMultiblockBase> hatchOrCasing(Material material, HatchType... allowed) {
        return hatchOrCasing(material.getBlock(MaterialType.CASING), material.getMeta(), allowed);
    }

    /** Exact block + meta, not counted as casing. */
    public static IStructureElement<MTEMultiblockBase> block(Block block, int meta) {
        return StructureUtility.ofBlock(block, meta);
    }

    /** Must be air. */
    public static IStructureElement<MTEMultiblockBase> air() {
        return StructureUtility.isAir();
    }

    /** Anything (the position is not checked). */
    public static IStructureElement<MTEMultiblockBase> any() {
        return new IStructureElement<>() {

            @Override
            public boolean check(MTEMultiblockBase machine, World world, int x, int y, int z) {
                return true;
            }

            @Override
            public boolean spawnHint(MTEMultiblockBase machine, World world, int x, int y, int z, ItemStack trigger) {
                return true;
            }

            @Override
            public boolean placeBlock(MTEMultiblockBase machine, World world, int x, int y, int z, ItemStack trigger) {
                return true;
            }
        };
    }

    /** First element that matches wins. */
    @SafeVarargs
    public static IStructureElement<MTEMultiblockBase> anyOf(IStructureElement<MTEMultiblockBase>... elements) {
        return StructureUtility.ofChain(elements);
    }
}
