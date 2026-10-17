package gregtech.api.modpack.example.machines;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modpack.example.ExampleModule;
import gregtech.api.modpack.machine.MTEBasicMachine;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.recipe.RecipeMap;

/**
 * The simplest single block machine: a recipe map, slot counts and an overlay.
 */
public class MTEExampleCrusher extends MTEBasicMachine {

    private static final MachineOverlay OVERLAY = MachineOverlay.of("machines/crusher");
    private static final String[] DESCRIPTION = { "Crushes things into dust" };

    public MTEExampleCrusher(int id, String name, String localName, int tier) {
        super(id, name, localName, tier, DESCRIPTION, 1, 2, 0, 0);
    }

    public MTEExampleCrusher(String name, int tier, String[] description) {
        super(name, tier, description, 1, 2, 0, 0);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEExampleCrusher(name, tier, description);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return ExampleModule.CRUSHER;
    }

    @Override
    protected MachineOverlay getOverlay() {
        return OVERLAY;
    }
}
