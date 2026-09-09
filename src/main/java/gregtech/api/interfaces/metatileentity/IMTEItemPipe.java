package gregtech.api.interfaces.metatileentity;

import java.util.Map;

import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.BaseMetaPipeEntity;

public interface IMTEItemPipe extends IMTEPipe {

    boolean pipeCapacityCheck();

    boolean incrementTransferCounter(int increment);

    boolean sendItemStack(Object sender);

    boolean insertItemStackIntoTileEntity(Object sender, ForgeDirection side);

    int getStepSize();

    class Util {

        public static Map<IMTEItemPipe, Long> scanPipes(IMTEItemPipe pipe, Map<IMTEItemPipe, Long> map,
            long step, boolean suckItem, boolean ignoreCapacity) {
            step += pipe.getStepSize();
            if (ignoreCapacity || pipe.pipeCapacityCheck())
                if (map.get(pipe) == null || map.get(pipe) > step) {
                    final IGTE aBaseMetaTileEntity = pipe.getGTE();
                    map.put(pipe, step);
                    for (final ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                        if (pipe instanceof IConnectable
                            && !((IConnectable) pipe).isConnectedAtSide(side)) continue;
                        final ForgeDirection oppositeSide = side.getOpposite();
                        if (suckItem) {
                            if (aBaseMetaTileEntity.getCoverAtSide(side)
                                .letsItemsIn(-2)) {
                                final IGTE tItemPipe = aBaseMetaTileEntity.getIGregTechTileEntityAtSide(side);
                                if (aBaseMetaTileEntity.getColorization() >= 0) {
                                    final byte tColor = tItemPipe.getColorization();
                                    if (tColor >= 0 && tColor != aBaseMetaTileEntity.getColorization()) {
                                        continue;
                                    }
                                }
                                if (tItemPipe instanceof BaseMetaPipeEntity) {
                                    final IMTE tMetaTileEntity = tItemPipe.getMTE();
                                    if (tMetaTileEntity instanceof IMTEItemPipe
                                        && tItemPipe.getCoverAtSide(oppositeSide)
                                            .letsItemsOut(-2)) {
                                        scanPipes(
                                            (IMTEItemPipe) tMetaTileEntity,
                                            map,
                                            step,
                                            suckItem,
                                            ignoreCapacity);
                                    }
                                }
                            }
                        } else {
                            if (aBaseMetaTileEntity.getCoverAtSide(side)
                                .letsItemsOut(-2)) {
                                final IGTE tItemPipe = aBaseMetaTileEntity.getIGregTechTileEntityAtSide(side);
                                if (tItemPipe != null) {
                                    if (aBaseMetaTileEntity.getColorization() >= 0) {
                                        final byte tColor = tItemPipe.getColorization();
                                        if (tColor >= 0 && tColor != aBaseMetaTileEntity.getColorization()) {
                                            continue;
                                        }
                                    }
                                    if (tItemPipe instanceof BaseMetaPipeEntity) {
                                        final IMTE tMetaTileEntity = tItemPipe.getMTE();
                                        if (tMetaTileEntity instanceof IMTEItemPipe
                                            && tItemPipe.getCoverAtSide(oppositeSide)
                                                .letsItemsIn(-2)) {
                                            scanPipes(
                                                (IMTEItemPipe) tMetaTileEntity,
                                                map,
                                                step,
                                                suckItem,
                                                ignoreCapacity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            return map;
        }
    }
}
