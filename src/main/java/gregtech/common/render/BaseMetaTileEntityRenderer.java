package gregtech.common.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import gregtech.api.metatileentity.BaseGTE;

public class BaseMetaTileEntityRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float timeSinceLastTick) {
        if (!(te instanceof BaseGTE baseTE)) return;
        if (baseTE.getMTE() instanceof IMTERenderer renderer) {
            renderer.renderTESR(x, y, z, timeSinceLastTick);
        }
    }
}
