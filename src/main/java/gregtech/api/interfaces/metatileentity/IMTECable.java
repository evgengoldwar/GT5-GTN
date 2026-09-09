package gregtech.api.interfaces.metatileentity;

import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface IMTECable extends IMTEPipe {

    long transferElectricity(ForgeDirection side, long voltage, long amperage, HashSet<TileEntity> alreadyPassedSet);
}
