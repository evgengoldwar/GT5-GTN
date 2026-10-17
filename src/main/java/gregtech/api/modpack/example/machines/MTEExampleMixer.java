package gregtech.api.modpack.example.machines;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.modpack.example.ExampleModule;
import gregtech.api.modpack.machine.MTEBasicMachine;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.recipe.RecipeMap;

/**
 * Machine with fluids, bigger tanks per tier and a speed bonus at night.
 */
public class MTEExampleMixer extends MTEBasicMachine {

    private static final MachineOverlay OVERLAY = MachineOverlay.of("machines/mixer");
    private static final String[] DESCRIPTION = { "Mixes things", "Twice as fast at night" };

    public MTEExampleMixer(int id, String name, String localName, int tier) {
        super(id, name, localName, tier, DESCRIPTION, 4, 1, 2, 1);
    }

    public MTEExampleMixer(String name, int tier, String[] description) {
        super(name, tier, description, 4, 1, 2, 1);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEExampleMixer(name, tier, description);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return ExampleModule.MIXER;
    }

    @Override
    protected MachineOverlay getOverlay() {
        return OVERLAY;
    }

    @Override
    protected int getTankCapacity() {
        return 8_000 * (tier + 1);
    }

    @Override
    protected void modifyLogic(ProcessingLogic logic) {
        if (!getGTE().getWorld()
            .isDaytime()) {
            logic.setSpeedBonus(0.5);
        }
    }
}
