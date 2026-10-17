package gregtech.api.modpack.example.machines;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modpack.example.ExampleModule;
import gregtech.api.modpack.machine.MTEBasicMachine;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.recipe.RecipeMap;

/**
 * Generator: burns fluids from its recipe map, every recipe gives its EU/t for its duration. Energy leaves through
 * the front face.
 */
public class MTEExampleCombustionGenerator extends MTEBasicMachine {

    private static final MachineOverlay OVERLAY = MachineOverlay.of("machines/combustion_generator");
    private static final String[] DESCRIPTION = { "Burns fuel" };

    public MTEExampleCombustionGenerator(int id, String name, String localName, int tier) {
        super(id, name, localName, tier, DESCRIPTION, 0, 0, 1, 0);
    }

    public MTEExampleCombustionGenerator(String name, int tier, String[] description) {
        super(name, tier, description, 0, 0, 1, 0);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEExampleCombustionGenerator(name, tier, description);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return ExampleModule.COMBUSTION_FUELS;
    }

    @Override
    public boolean isGenerator() {
        return true;
    }

    @Override
    public boolean allowSelectCircuit() {
        return false;
    }

    @Override
    protected MachineOverlay getOverlay() {
        return OVERLAY;
    }
}
