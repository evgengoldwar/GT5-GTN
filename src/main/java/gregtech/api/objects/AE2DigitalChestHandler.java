package gregtech.api.objects;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IExternalStorageHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.common.tileentities.storage.MTEDigitalChestBase;

public class AE2DigitalChestHandler implements IExternalStorageHandler {

    @Override
    public boolean canHandle(final TileEntity te, final ForgeDirection d, final StorageChannel chan,
        final BaseActionSource mySrc) {
        return chan == StorageChannel.ITEMS && te instanceof BaseMetaTileEntity
            && ((BaseMetaTileEntity) te).getMetaTileEntity() instanceof MTEDigitalChestBase;
    }

    @Override
    public IMEInventory<?> getInventory(final TileEntity te, final ForgeDirection d, final StorageChannel chan,
        final BaseActionSource src) {
        if (chan == StorageChannel.ITEMS) {
            return ((MTEDigitalChestBase) (((BaseMetaTileEntity) te).getMetaTileEntity()));
        }
        return null;
    }
}
