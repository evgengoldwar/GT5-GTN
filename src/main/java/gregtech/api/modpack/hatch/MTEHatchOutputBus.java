package gregtech.api.modpack.hatch;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;

/**
 * Output bus: receives the item outputs of the multiblock and pushes them into the inventory in front of it.
 */
public class MTEHatchOutputBus extends MTEHatchItems {

    public MTEHatchOutputBus(int id, String name, String localName, int tier) {
        super(id, name, localName, tier);
    }

    public MTEHatchOutputBus(String name, int tier) {
        super(name, tier);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchOutputBus(name, tier);
    }

    @Override
    public HatchType getType() {
        return HatchType.OUTPUT_BUS;
    }

    @Override
    public boolean isInput() {
        return false;
    }
}
