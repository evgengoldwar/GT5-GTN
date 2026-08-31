package gregtech.loaders.preload;

import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.items.ItemIntegratedCircuit;

public class LoaderGTBlockFluid implements Runnable {

    @Override
    public void run() {
        new ItemFluidDisplay();
        new ItemIntegratedCircuit();
        // GregTechAPI.sBlockMachines = new BlockMachines();
        //
        // new MTECable(100, "CAL", "CAL", 0.01F, Materials.Steam, 1, 1, 32, false, false);
        //
        // BaseMetaTileEntity tBaseMetaTileEntity = GregTechAPI.constructBaseMetaTileEntity();
        //
        // GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity");
        // FMLInterModComms.sendMessage(
        // AppliedEnergistics2.ID,
        // "whitelist-spatial",
        // tBaseMetaTileEntity.getClass()
        // .getName());
        //
        // GT_FML_LOGGER.debug("GTMod: Registering the BaseMetaPipeEntity.");
        // GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
        // FMLInterModComms.sendMessage(AppliedEnergistics2.ID, "whitelist-spatial",
        // BaseMetaPipeEntity.class.getName());
    }
}
