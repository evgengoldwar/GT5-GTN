package gregtech.loaders.preload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.Mods.AppliedEnergistics2;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.items.ItemIntegratedCircuit;
import gregtech.common.items.MetaGeneratedTool01;

public class LoaderGTBlockFluid implements Runnable {

    @Override
    public void run() {
        GT_FML_LOGGER.debug("GTMod: Register Items.");
        new ItemFluidDisplay();
        new ItemIntegratedCircuit();
        new MetaGeneratedTool01();
        GT_FML_LOGGER.debug("GTMod: Register TileEntities.");

        BaseMetaTileEntity tBaseMetaTileEntity = GregTechAPI.constructBaseMetaTileEntity();

        GT_FML_LOGGER.debug("GTMod: Registering the BaseMetaTileEntity.");
        GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity");
        FMLInterModComms.sendMessage(
            AppliedEnergistics2.ID,
            "whitelist-spatial",
            tBaseMetaTileEntity.getClass()
                .getName());

        GT_FML_LOGGER.debug("GTMod: Registering the BaseMetaPipeEntity.");
        GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
        FMLInterModComms.sendMessage(AppliedEnergistics2.ID, "whitelist-spatial", BaseMetaPipeEntity.class.getName());
    }
}
