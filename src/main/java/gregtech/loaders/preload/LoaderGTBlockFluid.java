package gregtech.loaders.preload;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.MTEArrays;
import gregtech.api.metatileentity.BaseGTE;
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

        // new MTECable(100, "CAL", "CAL", 0.01F, Materials.Steam, 1, 1, 32, false, false);

        BaseGTE baseGTE = GregTechAPI.constructBaseMetaTileEntity();

        GameRegistry.registerTileEntity(baseGTE.getClass(), "BaseMetaTileEntity");
        // FMLInterModComms.sendMessage(
        // AppliedEnergistics2.ID,
        // "whitelist-spatial",
        // tBaseMetaTileEntity.getClass()
        // .getName());

        // GT_FML_LOGGER.debug("GTMod: Registering the BaseMetaPipeEntity.");
        // GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
        // FMLInterModComms.sendMessage(AppliedEnergistics2.ID, "whitelist-spatial",
        // BaseMetaPipeEntity.class.getName());
    }
}
