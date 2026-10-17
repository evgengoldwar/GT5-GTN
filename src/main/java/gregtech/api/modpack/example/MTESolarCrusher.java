package gregtech.api.modpack.example;

import gregtech.api.modpack.machine.MTEBasicMachine;
import gregtech.api.modpack.machine.MachineConfig;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;

/**
 * Example of a single block machine with custom logic in a subclass: a crusher that works only during the day and
 * finishes recipes twice as fast in the desert.
 */
public class MTESolarCrusher extends MTEBasicMachine {

    public MTESolarCrusher(MachineConfig config) {
        super(config);
    }

    @Override
    protected CheckRecipeResult checkProcessing() {
        if (!getGTE().getWorld()
            .isDaytime()) {
            return SimpleCheckRecipeResult.ofFailure("night");
        }
        return super.checkProcessing();
    }

    @Override
    protected void onWorkTick() {
        super.onWorkTick();
        if (getGTE().getBiome().biomeName.contains("Desert")) {
            progress++;
        }
    }
}
