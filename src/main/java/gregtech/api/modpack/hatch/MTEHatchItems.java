package gregtech.api.modpack.hatch;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modpack.util.InventoryUtil;
import gregtech.api.modularui2.GTGuis;

/**
 * Input or output bus. Slots: {@code (tier + 1)^2}, max 16.
 */
public class MTEHatchItems extends MTEHatch {

    public MTEHatchItems(HatchType type, int tier, String id, String localName) {
        super(type, tier, id, localName, slotsForTier(tier));
        if (type != HatchType.INPUT_BUS && type != HatchType.OUTPUT_BUS) {
            throw new IllegalArgumentException("Item hatch must be INPUT_BUS or OUTPUT_BUS");
        }
    }

    public static int slotsForTier(int tier) {
        int side = Math.min(4, Math.max(1, tier + 1));
        return side * side;
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchItems(type, tier, name, localName);
    }

    public boolean isInput() {
        return type == HatchType.INPUT_BUS;
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return isInput() && side == gte.getFrontFacing();
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return !isInput() && side == gte.getFrontFacing();
    }

    /** Output bus: inserts a stack, returns the amount that did not fit. */
    public int insert(ItemStack stack, boolean simulate) {
        int left = InventoryUtil.insert(inventory, 0, inventory.length, stack, getInventoryStackLimit(), simulate);
        if (!simulate) {
            markDirty();
        }
        return left;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (gte.isServerSide() && !isInput() && tick % 20 == 0) {
            ForgeDirection front = gte.getFrontFacing();
            InventoryUtil.pushToNeighbour(inventory, 0, inventory.length, gte.getTileEntityAtSide(front), front);
        }
    }

    @Override
    protected void addDescription(List<String> lines) {
        super.addDescription(lines);
        lines.add("Slots: " + inventory.length);
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(this, data, syncManager, uiSettings)
            .build();
        int columns = (int) Math.round(Math.sqrt(inventory.length));
        int startX = 88 - columns * 9;
        int startY = 8 + (4 - columns) * 9;
        for (int i = 0; i < inventory.length; i++) {
            ModularSlot slot = new ModularSlot(getInventoryHandler(), i);
            if (!isInput()) {
                slot.accessibility(false, true);
            }
            panel.child(
                new ItemSlot().slot(slot)
                    .pos(startX + (i % columns) * 18, startY + (i / columns) * 18));
        }
        return panel;
    }
}
