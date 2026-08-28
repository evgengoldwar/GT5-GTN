package gregtech.loaders.postload.recipes;

import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.NotEnoughItems;

public class NEIHiding implements Runnable {

    @Override
    public void run() {
        if (!NotEnoughItems.isModLoaded() || !Forestry.isModLoaded()) {
            return;
        }

    }
}
