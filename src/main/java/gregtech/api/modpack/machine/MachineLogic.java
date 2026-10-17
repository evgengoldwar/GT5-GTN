package gregtech.api.modpack.machine;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;

/**
 * Custom logic for machines made with {@link MachineBuilder} without writing a subclass. All methods are optional.
 * <p>
 * For logic that does not fit these hooks extend {@link MTEBasicMachine} (single block) or
 * {@link gregtech.api.modpack.multiblock.MTEMultiblockBase} (multiblock) and override what you need.
 *
 * <pre>{@code
 * // a machine that only works in the Nether and runs twice as fast at night
 * .logic(new MachineLogic() {
 *     public CheckRecipeResult validateRecipe(MTEBasicMachine machine, GTRecipe recipe) {
 *         return machine.getGTE().getWorld().provider.dimensionId == -1 ? CheckRecipeResultRegistry.SUCCESSFUL
 *             : SimpleCheckRecipeResult.ofFailure("not_in_nether");
 *     }
 *     public void modifyLogic(MTEBasicMachine machine, ProcessingLogic logic) {
 *         if (!machine.getGTE().getWorld().isDaytime()) logic.setSpeedBonus(0.5);
 *     }
 * })
 * }</pre>
 */
public interface MachineLogic {

    MachineLogic NONE = new MachineLogic() {};

    /** Every server tick, before the machine works. */
    default void onTick(MTEBasicMachine machine, long tick) {}

    /** Every tick while a recipe runs (energy has already been used). */
    default void onWorkTick(MTEBasicMachine machine) {}

    /** Tweak overclocks, speed and EU modifiers before a recipe is searched. */
    default void modifyLogic(MTEBasicMachine machine, ProcessingLogic logic) {}

    /** Extra condition for a found recipe; return a failure to block it. */
    default CheckRecipeResult validateRecipe(MTEBasicMachine machine, GTRecipe recipe) {
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    /** Recipe started, inputs are consumed. */
    default void onRecipeStart(MTEBasicMachine machine, GTRecipe recipe) {}

    /** Recipe finished, outputs are in the output slots / tanks. */
    default void onRecipeFinish(MTEBasicMachine machine) {}

    /** @return true if handled (GUI will not open) */
    default boolean onRightClick(MTEBasicMachine machine, EntityPlayer player, ForgeDirection side) {
        return false;
    }

    /** Additional lines for the scanner / WAILA. */
    default void addInfo(MTEBasicMachine machine, List<String> info) {}
}
