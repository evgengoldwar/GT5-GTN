package gregtech.api.modpack.machine;

import java.util.List;

import gregtech.api.enums.GTValues;
import gregtech.api.recipe.RecipeMap;

/**
 * Immutable settings of a single block machine, shared by the prototype and every placed instance. Created by
 * {@link MachineBuilder}; custom subclasses of {@link MTEBasicMachine} receive it in their constructor.
 */
public final class MachineConfig {

    /** Creates the machine instance for a config. Use a constructor reference: {@code MyMachine::new}. */
    @FunctionalInterface
    public interface Factory {

        MTEBasicMachine create(MachineConfig config);
    }

    public final String id;
    public final String localName;
    public final int tier;
    public final RecipeMap<?> recipeMap;
    public final int itemInputs;
    public final int itemOutputs;
    public final int fluidInputs;
    public final int fluidOutputs;
    public final int tankCapacity;
    public final long amperage;
    public final long euStorage;
    public final boolean circuitSlot;
    public final boolean generator;
    public final boolean perfectOverclock;
    public final MachineOverlay overlay;
    public final MachineLogic logic;
    public final List<String> description;
    public final Factory factory;

    // spotless:off
    MachineConfig(String id, String localName, int tier, RecipeMap<?> recipeMap, int itemInputs, int itemOutputs,
        int fluidInputs, int fluidOutputs, int tankCapacity, long amperage, long euStorage, boolean circuitSlot,
        boolean generator, boolean perfectOverclock, MachineOverlay overlay, MachineLogic logic,
        List<String> description, Factory factory) {
        this.id = id;
        this.localName = localName;
        this.tier = tier;
        this.recipeMap = recipeMap;
        this.itemInputs = itemInputs;
        this.itemOutputs = itemOutputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.tankCapacity = tankCapacity;
        this.amperage = amperage;
        this.euStorage = euStorage;
        this.circuitSlot = circuitSlot;
        this.generator = generator;
        this.perfectOverclock = perfectOverclock;
        this.overlay = overlay;
        this.logic = logic;
        this.description = description;
        this.factory = factory;
    }
    // spotless:on

    public long getVoltage() {
        return GTValues.V[MachineTextures.clampTier(tier)];
    }

    public int getSlotCount() {
        return itemInputs + itemOutputs + (circuitSlot ? 1 : 0);
    }
}
