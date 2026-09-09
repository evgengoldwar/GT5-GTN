package gregtech.api.util;

import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import com.gtnewhorizon.gtnhlib.item.ItemTransfer;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.IWorldAccessor;

public class GTItemTransfer extends ItemTransfer {

    public void push(IWorldAccessor self, ForgeDirection side) {
        push(self, side, self.getTileEntityAtSide(side));
    }

    public void pull(IWorldAccessor self, ForgeDirection side) {
        pull(self, side, self.getTileEntityAtSide(side));
    }

    public void outOfMachine(IGTE igte, ForgeDirection side) {
        outOfMachine(igte.getMetaTileEntity(), side);
    }

    public void outOfMachine(IMTE imte, ForgeDirection side) {
        source(imte, side);
        sink(
            imte.getGTE()
                .getTileEntityAtSide(side),
            side.getOpposite());
    }

    public void dropItems(IWorldAccessor tile, ForgeDirection output) {
        dropItems(
            tile.getWorld(),
            new BlockPos(
                tile.getXCord() + output.offsetX,
                tile.getYCord() + output.offsetY,
                tile.getZCord() + output.offsetZ));
    }
}
