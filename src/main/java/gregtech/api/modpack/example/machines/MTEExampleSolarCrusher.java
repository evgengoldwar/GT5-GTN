package gregtech.api.modpack.example.machines;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;

/**
 * Inheriting from another machine: a crusher that works only during the day and runs twice as fast in deserts.
 */
public class MTEExampleSolarCrusher extends MTEExampleCrusher {

    public MTEExampleSolarCrusher(int id, String name, String localName, int tier) {
        super(id, name, localName, tier);
    }

    public MTEExampleSolarCrusher(String name, int tier, String[] description) {
        super(name, tier, description);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEExampleSolarCrusher(name, tier, description);
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
        if (getGTE().getBiome().biomeName.contains("Desert")) {
            progress++;
        }
    }
}
