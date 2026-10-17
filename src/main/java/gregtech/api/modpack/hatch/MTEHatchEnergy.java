package gregtech.api.modpack.hatch;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.enums.GTValues;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.modpack.machine.MachineTextures;

/**
 * Energy hatch (accepts EU from cables on its front) or dynamo hatch (emits EU from its front).
 */
public class MTEHatchEnergy extends MTEHatch {

    protected final long amperage;

    public MTEHatchEnergy(HatchType type, int tier, String id, String localName, long amperage) {
        super(type, tier, id, localName, 0);
        if (type != HatchType.ENERGY && type != HatchType.DYNAMO) {
            throw new IllegalArgumentException("Energy hatch must be ENERGY or DYNAMO");
        }
        this.amperage = amperage;
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchEnergy(type, tier, name, localName, amperage);
    }

    public boolean isDynamo() {
        return type == HatchType.DYNAMO;
    }

    public long getAmperage() {
        return amperage;
    }

    @Override
    public boolean isEnetInput() {
        return !isDynamo();
    }

    @Override
    public boolean isEnetOutput() {
        return isDynamo();
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        return !isDynamo() && side == getGTE().getFrontFacing();
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        return isDynamo() && side == getGTE().getFrontFacing();
    }

    @Override
    public long maxEUInput() {
        return isDynamo() ? 0 : getVoltage();
    }

    @Override
    public long maxEUOutput() {
        return isDynamo() ? getVoltage() : 0;
    }

    @Override
    public long maxAmperesIn() {
        return isDynamo() ? 0 : amperage;
    }

    @Override
    public long maxAmperesOut() {
        return isDynamo() ? amperage : 0;
    }

    @Override
    public long maxEUStore() {
        return 512 + GTValues.V[MachineTextures.clampTier(tier + 1)] * 2 * amperage;
    }

    @Override
    public long getMinimumStoredEU() {
        return isDynamo() ? getVoltage() : 512;
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
    public boolean onRightClick(IGTE gte, EntityPlayer player) {
        return false;
    }

    @Override
    protected void addDescription(List<String> lines) {
        super.addDescription(lines);
        lines.add(
            (isDynamo() ? "Output: " : "Input: ") + amperage
                + "A "
                + getVoltage()
                + " EU/t ("
                + GTValues.VN[MachineTextures.clampTier(tier)]
                + ")");
    }
}
