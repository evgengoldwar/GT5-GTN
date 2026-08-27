package gregtech.loaders.postload;

import static gregtech.GTLoggers.GT_FML_LOGGER;

import gregtech.api.enums.GTStones;
import gregtech.api.enums.OreMixes;
import gregtech.common.GTWorldgenerator;

public class GTWorldgenloader implements Runnable {

    @Override
    public void run() {
        new GTWorldgenerator();

        // GT Stones
        for (GTStones stone : GTStones.VALUES) {
            stone.addGTStone();
        }

        // GT Default Small Ores

        // GT Veins registration
        for (OreMixes oreMix : OreMixes.VALUES) {
            oreMix.addGTOreLayer();
        }

        GT_FML_LOGGER.debug("Started Galactic Greg ore gen code");
    }
}
