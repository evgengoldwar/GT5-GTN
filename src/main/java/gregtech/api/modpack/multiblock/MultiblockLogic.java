package gregtech.api.modpack.multiblock;

import java.util.List;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;

/**
 * Custom logic hooks for multiblocks made with {@link MultiblockBuilder}. All methods are optional.
 *
 * <pre>{@code
 * // +10% speed for every casing above the minimum, max 2x
 * .logic(new MultiblockLogic() {
 *     public void modifyLogic(MTEConfigurableMultiblock machine, ProcessingLogic logic) {
 *         int extra = machine.getCasingCount() - 12;
 *         logic.setSpeedBonus(1.0 / Math.min(2.0, 1.0 + extra * 0.1));
 *     }
 * })
 * }</pre>
 */
public interface MultiblockLogic {

    MultiblockLogic NONE = new MultiblockLogic() {};

    /** Extra structure condition after the shape check (coil tiers, hatch counts...). */
    default boolean checkStructure(MTEConfigurableMultiblock machine) {
        return true;
    }

    default void modifyLogic(MTEConfigurableMultiblock machine, ProcessingLogic logic) {}

    default CheckRecipeResult validateRecipe(MTEConfigurableMultiblock machine, GTRecipe recipe) {
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    default void onRecipeStart(MTEConfigurableMultiblock machine, GTRecipe recipe) {}

    default void onRecipeFinish(MTEConfigurableMultiblock machine) {}

    default void onWorkTick(MTEConfigurableMultiblock machine) {}

    default void addInfo(MTEConfigurableMultiblock machine, List<String> info) {}
}
