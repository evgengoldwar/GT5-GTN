package gregtech.common.gui.modularui.util;

import java.util.function.BiFunction;

import com.cleanroommc.modularui.utils.item.IItemHandler;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import gregtech.api.interfaces.tileentity.IGTE;

/**
 * Calls {@link IGTE#markInventoryBeenModified()} when the contents of this slot changes.
 * Useful for singleblock machine IO slots.
 */
public class MachineModularSlot extends ModularSlot {

    public MachineModularSlot(IItemHandler itemHandler, int index, IGTE baseMetaTileEntity) {
        super(itemHandler, index);

        changeListener(
            (_, _, client, init) -> { if (!client && !init) baseMetaTileEntity.markInventoryBeenModified(); });
    }

    public static BiFunction<IItemHandler, Integer, MachineModularSlot> supplier(
        IGTE baseMetaTileEntity) {
        return (itemHandler, index) -> new MachineModularSlot(itemHandler, index, baseMetaTileEntity);
    }
}
