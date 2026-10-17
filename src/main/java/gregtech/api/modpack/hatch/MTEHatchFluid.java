package gregtech.api.modpack.hatch;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modpack.util.InventoryUtil;
import gregtech.api.modularui2.GTGuis;

/**
 * Input or output hatch with one tank of {@code 8000 * 2^tier} L.
 */
public class MTEHatchFluid extends MTEHatch {

    protected final FluidTank tank;

    public MTEHatchFluid(HatchType type, int tier, String id, String localName) {
        super(type, tier, id, localName, 0);
        if (type != HatchType.INPUT_HATCH && type != HatchType.OUTPUT_HATCH) {
            throw new IllegalArgumentException("Fluid hatch must be INPUT_HATCH or OUTPUT_HATCH");
        }
        this.tank = new FluidTank(capacityForTier(tier));
    }

    public static int capacityForTier(int tier) {
        return (int) Math.min(Integer.MAX_VALUE, 8000L << Math.max(0, Math.min(tier, 18)));
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchFluid(type, tier, name, localName);
    }

    public boolean isInput() {
        return type == HatchType.INPUT_HATCH;
    }

    public FluidTank getTank() {
        return tank;
    }

    /** Fluid stored, the multiblock consumes it in place. */
    @Override
    public FluidStack getFluid() {
        return tank.getFluid();
    }

    @Override
    public int getCapacity() {
        return tank.getCapacity();
    }

    @Override
    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    /** Output hatch: inserts from the multiblock. */
    public int fillFromMachine(FluidStack fluid, boolean doFill) {
        int filled = tank.fill(fluid, doFill);
        if (doFill && filled > 0) {
            markDirty();
        }
        return filled;
    }

    @Override
    public int fill(ForgeDirection side, FluidStack fluid, boolean doFill) {
        if (!isInput() || side != getGTE().getFrontFacing()) {
            return 0;
        }
        int filled = tank.fill(fluid, doFill);
        if (doFill && filled > 0) {
            markDirty();
        }
        return filled;
    }

    @Override
    public FluidStack drain(ForgeDirection side, int maxDrain, boolean doDrain) {
        if (isInput()) {
            return null;
        }
        FluidStack drained = tank.drain(maxDrain, doDrain);
        if (doDrain && drained != null) {
            markDirty();
        }
        return drained;
    }

    @Override
    public FluidStack drain(ForgeDirection side, FluidStack fluid, boolean doDrain) {
        if (fluid == null || tank.getFluid() == null || !tank.getFluid()
            .isFluidEqual(fluid)) {
            return null;
        }
        return drain(side, fluid.amount, doDrain);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection side) {
        return new FluidTankInfo[] { tank.getInfo() };
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (gte.isServerSide() && !isInput() && tick % 20 == 0 && tank.getFluid() != null) {
            ForgeDirection front = gte.getFrontFacing();
            FluidStack[] fluids = { tank.getFluid() };
            InventoryUtil.pushFluids(fluids, gte.getTileEntityAtSide(front), front);
            if (fluids[0] == null) {
                tank.setFluid(null);
            }
        }
    }

    @Override
    protected void addDescription(List<String> lines) {
        super.addDescription(lines);
        lines.add("Capacity: " + tank.getCapacity() + " L");
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(this, data, syncManager, uiSettings)
            .build();
        FluidSlotSyncHandler handler = new FluidSlotSyncHandler(tank);
        if (!isInput()) {
            handler.canFillSlot(false);
        }
        panel.child(
            new FluidSlot().syncHandler(handler)
                .pos(79, 34));
        return panel;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        tank.setFluid(null);
        tank.readFromNBT(nbt.getCompoundTag("tank"));
    }
}
