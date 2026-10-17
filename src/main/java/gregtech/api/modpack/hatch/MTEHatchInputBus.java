package gregtech.api.modpack.hatch;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;

/**
 * Input bus: items put into its front are available to the multiblock.
 */
public class MTEHatchInputBus extends MTEHatchItems {

    public MTEHatchInputBus(int id, String name, String localName, int tier) {
        super(id, name, localName, tier);
    }

    public MTEHatchInputBus(String name, int tier) {
        super(name, tier);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchInputBus(name, tier);
    }

    @Override
    public HatchType getType() {
        return HatchType.INPUT_BUS;
    }

    @Override
    public boolean isInput() {
        return true;
    }
}
