package gregtech.common.gui.modularui.cover.base;

import static net.minecraft.util.StatCollector.translateToLocal;

import com.cleanroommc.modularui.api.GuiAxis;
import com.cleanroommc.modularui.value.sync.EnumSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.layout.Flow;

import gregtech.api.modularui2.CoverGuiData;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.covers.Base.Cover;
import gregtech.common.modularui2.widget.builder.EnumSeriesBuilder;

public class CoverAdvancedRedstoneReceiverBaseGui
    extends CoverAdvancedWirelessRedstoneBaseGui<Cover.CoverAdvancedRedstoneReceiverBase> {

    public CoverAdvancedRedstoneReceiverBaseGui(Cover.CoverAdvancedRedstoneReceiverBase cover) {
        super(cover, true);
    }

    @Override
    public void addUIWidgets(PanelSyncManager syncManager, Flow column, CoverGuiData data) {
        EnumSyncValue<Cover.CoverAdvancedRedstoneReceiverBase.GateMode, ?> gateModeSync = new EnumSyncValue<>(
            Cover.CoverAdvancedRedstoneReceiverBase.GateMode.class,
            cover::getGateMode,
            cover::setMode).allowC2S();
        syncManager.syncValue("gateMode", gateModeSync);
        super.addUIWidgets(syncManager, column, data);
    }

    @Override
    protected Flow makeThirdFlow(PanelSyncManager syncManager, CoverGuiData data) {
        EnumSyncValue<Cover.CoverAdvancedRedstoneReceiverBase.GateMode, ?> gateMode = (EnumSyncValue<Cover.CoverAdvancedRedstoneReceiverBase.GateMode, ?>) syncManager
            .getSyncHandlerFromMapKey("gateMode:0");
        return Flow.row()
            .size(140, 18)

            .child(
                new EnumSeriesBuilder<>(Cover.CoverAdvancedRedstoneReceiverBase.GateMode.class).value(gateMode)
                    .overlay(
                        GTGuiTextures.OVERLAY_BUTTON_GATE_AND,
                        GTGuiTextures.OVERLAY_BUTTON_GATE_NAND,
                        GTGuiTextures.OVERLAY_BUTTON_GATE_OR,
                        GTGuiTextures.OVERLAY_BUTTON_GATE_NOR,
                        GTGuiTextures.OVERLAY_BUTTON_ANALOG)
                    .build(GuiAxis.X)
                    .width(120)
                    .marginRight(2))
            .child(new TextWidget(translateToLocal("gt.interact.desc.gatemode")));
    }

}
