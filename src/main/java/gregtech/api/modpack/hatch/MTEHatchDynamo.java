package gregtech.api.modpack.hatch;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;

/**
 * Dynamo hatch: generator multiblocks put their EU here, it is emitted from the front into cables.
 */
public class MTEHatchDynamo extends MTEHatchEnergy {

    public MTEHatchDynamo(int id, String name, String localName, int tier) {
        super(id, name, localName, tier, 2);
    }

    public MTEHatchDynamo(int id, String name, String localName, int tier, long amperage) {
        super(id, name, localName, tier, amperage);
    }

    public MTEHatchDynamo(String name, int tier, long amperage) {
        super(name, tier, amperage);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEHatchDynamo(name, tier, amperage);
    }

    @Override
    public HatchType getType() {
        return HatchType.DYNAMO;
    }

    @Override
    public boolean isDynamo() {
        return true;
    }
}
