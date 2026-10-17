package gregtech.api.modpack.multiblock;

import java.util.List;
import java.util.Set;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;

import gregtech.api.modpack.hatch.HatchType;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.recipe.RecipeMap;

/**
 * Immutable settings of a {@link MTEConfigurableMultiblock}, created by {@link MultiblockBuilder}.
 */
public final class MultiblockConfig {

    @FunctionalInterface
    public interface Factory {

        MTEConfigurableMultiblock create(MultiblockConfig config);
    }

    public final String id;
    public final String localName;
    public final IStructureDefinition<MTEMultiblockBase> structure;
    /** controller position inside the shape: column, layer, row */
    public final int offsetH, offsetV, offsetD;
    public final int casingTextureId;
    public final int minCasings;
    public final Set<HatchType> requiredHatches;
    public final RecipeMap<?> recipeMap;
    public final int maxParallel;
    public final double speedBonus;
    public final double euModifier;
    public final boolean perfectOverclock;
    public final boolean generator;
    public final MachineOverlay overlay;
    public final MultiblockLogic logic;
    public final List<String> description;
    public final Factory factory;

    // spotless:off
    MultiblockConfig(String id, String localName, IStructureDefinition<MTEMultiblockBase> structure, int offsetH,
        int offsetV, int offsetD, int casingTextureId, int minCasings, Set<HatchType> requiredHatches,
        RecipeMap<?> recipeMap, int maxParallel, double speedBonus, double euModifier, boolean perfectOverclock,
        boolean generator, MachineOverlay overlay, MultiblockLogic logic, List<String> description,
        Factory factory) {
        this.id = id;
        this.localName = localName;
        this.structure = structure;
        this.offsetH = offsetH;
        this.offsetV = offsetV;
        this.offsetD = offsetD;
        this.casingTextureId = casingTextureId;
        this.minCasings = minCasings;
        this.requiredHatches = requiredHatches;
        this.recipeMap = recipeMap;
        this.maxParallel = maxParallel;
        this.speedBonus = speedBonus;
        this.euModifier = euModifier;
        this.perfectOverclock = perfectOverclock;
        this.generator = generator;
        this.overlay = overlay;
        this.logic = logic;
        this.description = description;
        this.factory = factory;
    }
    // spotless:on
}
