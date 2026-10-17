package gregtech.api.modpack.hatch;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;

/**
 * Output hatch: receives the fluid outputs of the multiblock and pushes them forward.
 */
public class MTEHatchOutput extends MTEHatchFluid {

    public MTEHatchOutput(int id, String name, String localName, int tier) {
        super(id, name, localName, tier);
    }

    public MTEHatchOutput(String name, int tier) {
        super(name, tier);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchOutput(name, tier);
    }

    @Override
    public HatchType getType() {
        return HatchType.OUTPUT_HATCH;
    }

    @Override
    public boolean isInput() {
        return false;
    }
}
