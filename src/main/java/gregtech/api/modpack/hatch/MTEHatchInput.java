package gregtech.api.modpack.hatch;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;

/**
 * Input hatch: fluid pumped into its front is available to the multiblock.
 */
public class MTEHatchInput extends MTEHatchFluid {

    public MTEHatchInput(int id, String name, String localName, int tier) {
        super(id, name, localName, tier);
    }

    public MTEHatchInput(String name, int tier) {
        super(name, tier);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchInput(name, tier);
    }

    @Override
    public HatchType getType() {
        return HatchType.INPUT_HATCH;
    }

    @Override
    public boolean isInput() {
        return true;
    }
}
