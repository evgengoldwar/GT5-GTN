package gregtech.api.modpack.cover;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;

/**
 * Logic of a cover made with {@link CoverBuilder}. All methods are optional. Per cover data lives in
 * {@link SimpleCover#getData()} (saved with the machine).
 *
 * <pre>{@code
 * // emits a redstone signal while the machine is working
 * public class ActivityDetector implements CoverBehavior {
 *
 *     public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
 *         boolean active = cover.getTile() instanceof IGTE gte && gte.isActive();
 *         cover.setRedstoneOutput(active ? 15 : 0);
 *     }
 *
 *     public boolean emitsRedstone(SimpleCover cover) {
 *         return true;
 *     }
 * }
 * }</pre>
 */
public interface CoverBehavior {

    /** Ticks between {@link #onTick} calls, 0 = never ticks. */
    default int getTickRate() {
        return 20;
    }

    /** Server side work, called every {@link #getTickRate()} ticks. */
    default void onTick(SimpleCover cover, byte redstoneIn, long timer) {}

    /** Cover placed by a player. */
    default void onAttach(SimpleCover cover, EntityPlayer player) {}

    default void onRemove(SimpleCover cover) {}

    /** @return true if handled */
    default boolean onRightClick(SimpleCover cover, EntityPlayer player) {
        return false;
    }

    /** Screwdriver on the cover, usually cycles a mode. */
    default void onScrewdriver(SimpleCover cover, EntityPlayer player) {}

    default boolean letsEnergyIn(SimpleCover cover) {
        return true;
    }

    default boolean letsEnergyOut(SimpleCover cover) {
        return true;
    }

    default boolean letsItemsIn(SimpleCover cover, int slot) {
        return true;
    }

    default boolean letsItemsOut(SimpleCover cover, int slot) {
        return true;
    }

    default boolean letsFluidIn(SimpleCover cover, Fluid fluid) {
        return true;
    }

    default boolean letsFluidOut(SimpleCover cover, Fluid fluid) {
        return true;
    }

    default boolean letsRedstoneIn(SimpleCover cover) {
        return true;
    }

    /** Return true for covers that set a redstone output with {@link SimpleCover#setRedstoneOutput}. */
    default boolean emitsRedstone(SimpleCover cover) {
        return false;
    }

    /** Pipes and cables connect to this side even without a machine there (pumps, conveyors). */
    default boolean alwaysLookConnected(SimpleCover cover) {
        return false;
    }

    default void addTooltip(SimpleCover cover, List<String> tooltip) {}
}
