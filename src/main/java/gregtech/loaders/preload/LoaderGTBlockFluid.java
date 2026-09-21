package gregtech.loaders.preload;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.MTEArrays;
import gregtech.common.blocks.BlockCable;
import gregtech.common.blocks.BlockHatch;
import gregtech.common.blocks.ItemBlockCable;
import gregtech.common.blocks.ItemBlockHatch;
import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.items.ItemIntegratedCircuit;

public class LoaderGTBlockFluid implements Runnable {

    @Override
    public void run() {
        new ItemFluidDisplay();
        new ItemIntegratedCircuit();
        GregTechAPI.sBlockMachines = new BlockHatch(ItemBlockHatch.class, MTEArrays.HATCH);
        GregTechAPI.sBlockCable = new BlockCable(ItemBlockCable.class, MTEArrays.CABLE);
    }
}
