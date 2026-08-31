package gregtech.loaders.preload;

import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.items.ItemIntegratedCircuit;

public class LoaderGTBlockFluid implements Runnable {

    @Override
    public void run() {
        new ItemFluidDisplay();
        new ItemIntegratedCircuit();
        // new BlockMachines();

        // BaseMetaTileEntity tBaseMetaTileEntity = GregTechAPI.constructBaseMetaTileEntity();

        // GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity");
        // FMLInterModComms.sendMessage(
        // AppliedEnergistics2.ID,
        // "whitelist-spatial",
        // tBaseMetaTileEntity.getClass()
        // .getName());

        // GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
        // FMLInterModComms.sendMessage(AppliedEnergistics2.ID, "whitelist-spatial",
        // BaseMetaPipeEntity.class.getName());
    }
}
