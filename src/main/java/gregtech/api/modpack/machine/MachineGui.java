package gregtech.api.modpack.machine;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.LongSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuis;

/**
 * Default GUI of {@link MTEBasicMachine}: inputs on the left, progress arrow, outputs on the right, tanks below and an
 * energy readout. Override {@link MTEBasicMachine#buildUI} for a custom layout.
 */
public final class MachineGui {

    private static final int SLOT = 18;
    private static final int COLUMNS = 3;

    private MachineGui() {}

    public static ModularPanel build(MTEBasicMachine machine, PosGuiData data, PanelSyncManager syncManager,
        UISettings uiSettings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(machine, data, syncManager, uiSettings)
            .build();

        // inputs: right aligned grid ending left of the arrow
        int inputColumns = Math.min(COLUMNS, Math.max(1, machine.itemInputs));
        int inputX = 78 - 4 - inputColumns * SLOT;
        for (int i = 0; i < machine.itemInputs; i++) {
            panel.child(
                new ItemSlot().slot(new ModularSlot(machine.getInventoryHandler(), machine.getInputSlotStart() + i))
                    .pos(inputX + (i % COLUMNS) * SLOT, 16 + (i / COLUMNS) * SLOT));
        }

        // the setter only stores the synced value on the client
        DoubleSyncValue progress = new DoubleSyncValue(
            () -> machine.maxProgressTime() <= 0 ? 0 : machine.getProgressTime() / (double) machine.maxProgressTime(),
            value -> {});
        syncManager.syncValue("machine_progress", 0, progress);
        panel.child(
            new ProgressWidget().value(progress)
                .texture(GTGuiTextures.PROGRESSBAR_ARROW_STANDARD, 20)
                .size(20, 18)
                .pos(78, 25));

        int outputX = 78 + 20 + 4;
        for (int i = 0; i < machine.itemOutputs; i++) {
            panel.child(
                new ItemSlot()
                    .slot(
                        new ModularSlot(machine.getInventoryHandler(), machine.getOutputSlotStart() + i)
                            .accessibility(false, true))
                    .pos(outputX + (i % COLUMNS) * SLOT, 16 + (i / COLUMNS) * SLOT));
        }

        // tanks under the slots
        int tankY = 16 + 2 * SLOT + 4;
        for (int i = 0; i < machine.fluidInputs; i++) {
            panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(machine.getInputTanks()[i]))
                    .pos(inputX + i * SLOT, tankY));
        }
        for (int i = 0; i < machine.fluidOutputs; i++) {
            panel.child(
                new FluidSlot().syncHandler(
                    new FluidSlotSyncHandler(machine.getOutputTanks()[i]).canFillSlot(false))
                    .pos(outputX + i * SLOT, tankY));
        }

        LongSyncValue energy = new LongSyncValue(
            () -> machine.getGTE()
                .getStoredEU(),
            value -> {});
        syncManager.syncValue("machine_energy", 0, energy);
        panel.child(
            IKey.dynamic(() -> energy.getLongValue() + " / " + machine.maxEUStore() + " EU")
                .asWidget()
                .pos(8, 75));

        return panel;
    }
}
