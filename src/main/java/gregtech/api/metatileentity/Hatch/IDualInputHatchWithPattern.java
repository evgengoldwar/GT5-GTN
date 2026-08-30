package gregtech.api.metatileentity.Hatch;

import java.util.Iterator;

public interface IDualInputHatchWithPattern extends IDualInputHatch {

    @Override
    Iterator<? extends IDualInputInventoryWithPattern> inventories();

}
