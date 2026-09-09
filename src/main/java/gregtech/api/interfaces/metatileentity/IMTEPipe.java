package gregtech.api.interfaces.metatileentity;

import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGTE;

public interface IMTEPipe extends IMTE {

    ITexture[] getTexture(IGTE gte, ForgeDirection side, int facingBitMask, int colorIndex,
        boolean active, boolean redstoneLevel);
}
