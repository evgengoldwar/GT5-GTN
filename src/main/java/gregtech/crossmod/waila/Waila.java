package gregtech.crossmod.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.Mods;
import gregtech.api.metatileentity.BaseGTE;
import gregtech.api.metatileentity.BasePipeGTE;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class Waila {

    public static void callbackRegister(IWailaRegistrar register) {
        final IWailaDataProvider multiBlockProvider = new GregtechTEWailaDataProvider();

        register.registerBodyProvider(multiBlockProvider, BaseGTE.class);
        register.registerBodyProvider(multiBlockProvider, BasePipeGTE.class);

        register.registerNBTProvider(multiBlockProvider, BaseGTE.class);
        register.registerNBTProvider(multiBlockProvider, BasePipeGTE.class);

        register.registerTailProvider(multiBlockProvider, BaseGTE.class);
        register.registerTailProvider(multiBlockProvider, BasePipeGTE.class);
    }

    public static void init() {
        FMLInterModComms.sendMessage(Mods.Waila.ID, "register", Waila.class.getName() + ".callbackRegister");
    }
}
