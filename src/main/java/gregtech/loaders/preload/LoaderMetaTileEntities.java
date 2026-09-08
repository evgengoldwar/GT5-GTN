package gregtech.loaders.preload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.MetaTileEntityIDs.*;

// TODO Some GT MetaTileEntity registrations are done in load/GT_Loader_MetaTileEntities_Recipes.java due to joint
// registration+recipe methods, they should be split and brought here to register all in preload.

public class LoaderMetaTileEntities implements Runnable { // TODO CHECK CIRCUIT RECIPES AND USAGES

    // private static void registerMachineHull() {
    // ItemList.Hull_LV.set(new MTEBasicHull(HULL_LV.ID, "hull.tier.01", "LV Machine Hull", 1).getStackForm(1L));
    // ItemList.Hull_MV.set(new MTEBasicHull(HULL_MV.ID, "hull.tier.02", "MV Machine Hull", 2).getStackForm(1L));
    // ItemList.Hull_HV.set(new MTEBasicHull(HULL_HV.ID, "hull.tier.03", "HV Machine Hull", 3).getStackForm(1L));
    // ItemList.Hull_EV.set(new MTEBasicHull(HULL_EV.ID, "hull.tier.04", "EV Machine Hull", 4).getStackForm(1L));
    // ItemList.Hull_IV.set(new MTEBasicHull(HULL_IV.ID, "hull.tier.05", "IV Machine Hull", 5).getStackForm(1L));
    // ItemList.Hull_LuV.set(new MTEBasicHull(HULL_LuV.ID, "hull.tier.06", "LuV Machine Hull", 6).getStackForm(1L));
    // ItemList.Hull_ZPM.set(new MTEBasicHull(HULL_ZPM.ID, "hull.tier.07", "ZPM Machine Hull", 7).getStackForm(1L));
    // ItemList.Hull_UV.set(new MTEBasicHull(HULL_UV.ID, "hull.tier.08", "UV Machine Hull", 8).getStackForm(1L));
    // ItemList.Hull_UHV.set(new MTEBasicHull(HULL_UHV.ID, "hull.tier.09", "UHV Machine Hull", 9).getStackForm(1L));
    // ItemList.Hull_UEV.set(new MTEBasicHull(HULL_UEV.ID, "hull.tier.10", "UEV Machine Hull", 10).getStackForm(1L));
    // ItemList.Hull_UIV.set(new MTEBasicHull(HULL_UIV.ID, "hull.tier.11", "UIV Machine Hull", 11).getStackForm(1L));
    // ItemList.Hull_UMV.set(new MTEBasicHull(HULL_UMV.ID, "hull.tier.12", "UMV Machine Hull", 12).getStackForm(1L));
    // ItemList.Hull_UXV.set(new MTEBasicHull(HULL_UXV.ID, "hull.tier.13", "UXV Machine Hull", 13).getStackForm(1L));
    // ItemList.Hull_MAX.set(new MTEBasicHull(HULL_MAX.ID, "hull.tier.14", "MAX Machine Hull", 14).getStackForm(1L));
    // }
    //
    // private static void registerTransformer() {
    // ItemList.Transformer_LV_ULV.set(
    // new MTETransformer(transformer_LV_ULV.ID, "transformer.tier.00", "Ultra Low Voltage Transformer", 0)
    // .getStackForm(1L));
    // ItemList.Transformer_MV_LV.set(
    // new MTETransformer(transformer_MV_LV.ID, "transformer.tier.01", "Low Voltage Transformer", 1)
    // .getStackForm(1L));
    // ItemList.Transformer_HV_MV.set(
    // new MTETransformer(transformer_HV_MV.ID, "transformer.tier.02", "Medium Voltage Transformer", 2)
    // .getStackForm(1L));
    // ItemList.Transformer_EV_HV.set(
    // new MTETransformer(transformer_EV_HV.ID, "transformer.tier.03", "High Voltage Transformer", 3)
    // .getStackForm(1L));
    // ItemList.Transformer_IV_EV.set(
    // new MTETransformer(transformer_IV_EV.ID, "transformer.tier.04", "Extreme Transformer", 4).getStackForm(1L));
    // ItemList.Transformer_LuV_IV.set(
    // new MTETransformer(transformer_LuV_IV.ID, "transformer.tier.05", "Insane Transformer", 5).getStackForm(1L));
    // ItemList.Transformer_ZPM_LuV.set(
    // new MTETransformer(transformer_ZPM_LuV.ID, "transformer.tier.06", "Ludicrous Transformer", 6)
    // .getStackForm(1L));
    // ItemList.Transformer_UV_ZPM.set(
    // new MTETransformer(transformer_UV_ZPM.ID, "transformer.tier.07", "ZPM Voltage Transformer", 7)
    // .getStackForm(1L));
    // ItemList.Transformer_MAX_UV.set(
    // new MTETransformer(transformer_UHV_UV.ID, "transformer.tier.08", "Ultimate Transformer", 8)
    // .getStackForm(1L));
    // ItemList.Transformer_UEV_UHV.set(
    // new MTETransformer(TRANSFORMER_UEV_UHV.ID, "transformer.tier.09", "Highly Ultimate Transformer", 9)
    // .getStackForm(1L));
    //
    // ItemList.Transformer_UIV_UEV.set(
    // new MTETransformer(TRANSFORMER_UIV_UEV.ID, "transformer.tier.10", "Extremely Ultimate Transformer", 10)
    // .getStackForm(1L));
    //
    // ItemList.Transformer_UMV_UIV.set(
    // new MTETransformer(TRANSFORMER_UMV_UIV.ID, "transformer.tier.11", "Insanely Ultimate Transformer", 11)
    // .getStackForm(1L));
    //
    // ItemList.Transformer_UXV_UMV.set(
    // new MTETransformer(TRANSFORMER_UXV_UMV.ID, "transformer.tier.12", "Mega Ultimate Transformer", 12)
    // .getStackForm(1L));
    //
    // ItemList.Transformer_MAX_UXV.set(
    // new MTETransformer(TRANSFORMER_MAX_UXV.ID, "transformer.tier.13", "Extended Mega Ultimate Transformer", 13)
    // .getStackForm(1L));
    // }
    //
    // private static void registerDynamoHatch() {
    // ItemList.Hatch_Dynamo_ULV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_ULV.ID, "hatch.dynamo.tier.00", "ULV Dynamo Hatch", 0).getStackForm(1L));
    // ItemList.Hatch_Dynamo_LV
    // .set(new MTEHatchDynamo(DYNAMO_HATCH_LV.ID, "hatch.dynamo.tier.01", "LV Dynamo Hatch", 1).getStackForm(1L));
    // ItemList.Hatch_Dynamo_MV
    // .set(new MTEHatchDynamo(DYNAMO_HATCH_MV.ID, "hatch.dynamo.tier.02", "MV Dynamo Hatch", 2).getStackForm(1L));
    // ItemList.Hatch_Dynamo_HV
    // .set(new MTEHatchDynamo(DYNAMO_HATCH_HV.ID, "hatch.dynamo.tier.03", "HV Dynamo Hatch", 3).getStackForm(1L));
    // ItemList.Hatch_Dynamo_EV
    // .set(new MTEHatchDynamo(DYNAMO_HATCH_EV.ID, "hatch.dynamo.tier.04", "EV Dynamo Hatch", 4).getStackForm(1L));
    // ItemList.Hatch_Dynamo_IV
    // .set(new MTEHatchDynamo(DYNAMO_HATCH_IV.ID, "hatch.dynamo.tier.05", "IV Dynamo Hatch", 5).getStackForm(1L));
    // ItemList.Hatch_Dynamo_LuV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_LuV.ID, "hatch.dynamo.tier.06", "LuV Dynamo Hatch", 6).getStackForm(1L));
    // ItemList.Hatch_Dynamo_ZPM.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_ZPM.ID, "hatch.dynamo.tier.07", "ZPM Dynamo Hatch", 7).getStackForm(1L));
    // ItemList.Hatch_Dynamo_UV
    // .set(new MTEHatchDynamo(DYNAMO_HATCH_UV.ID, "hatch.dynamo.tier.08", "UV Dynamo Hatch", 8).getStackForm(1L));
    // ItemList.Hatch_Dynamo_UHV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_UHV.ID, "hatch.dynamo.tier.09", "UHV Dynamo Hatch", 9).getStackForm(1L));
    // ItemList.Hatch_Dynamo_UEV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_UEV.ID, "hatch.dynamo.tier.10", "UEV Dynamo Hatch", 10).getStackForm(1L));
    //
    // ItemList.Hatch_Dynamo_UIV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_UIV.ID, "hatch.dynamo.tier.11", "UIV Dynamo Hatch", 11).getStackForm(1L));
    //
    // ItemList.Hatch_Dynamo_UMV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_UMV.ID, "hatch.dynamo.tier.12", "UMV Dynamo Hatch", 12).getStackForm(1L));
    //
    // ItemList.Hatch_Dynamo_UXV.set(
    // new MTEHatchDynamo(DYNAMO_HATCH_UXV.ID, "hatch.dynamo.tier.13", "UXV Dynamo Hatch", 13).getStackForm(1L));
    // }
    //
    // private static void registerEnergyHatch() {
    // ItemList.Hatch_Energy_ULV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_ULV.ID, "hatch.energy.tier.00", "ULV Energy Hatch", 0).getStackForm(1L));
    // ItemList.Hatch_Energy_LV
    // .set(new MTEHatchEnergy(ENERGY_HATCH_LV.ID, "hatch.energy.tier.01", "LV Energy Hatch", 1).getStackForm(1L));
    // ItemList.Hatch_Energy_MV
    // .set(new MTEHatchEnergy(ENERGY_HATCH_MV.ID, "hatch.energy.tier.02", "MV Energy Hatch", 2).getStackForm(1L));
    // ItemList.Hatch_Energy_HV
    // .set(new MTEHatchEnergy(ENERGY_HATCH_HV.ID, "hatch.energy.tier.03", "HV Energy Hatch", 3).getStackForm(1L));
    // ItemList.Hatch_Energy_EV
    // .set(new MTEHatchEnergy(ENERGY_HATCH_EV.ID, "hatch.energy.tier.04", "EV Energy Hatch", 4).getStackForm(1L));
    // ItemList.Hatch_Energy_IV
    // .set(new MTEHatchEnergy(ENERGY_HATCH_IV.ID, "hatch.energy.tier.05", "IV Energy Hatch", 5).getStackForm(1L));
    // ItemList.Hatch_Energy_LuV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_LuV.ID, "hatch.energy.tier.06", "LuV Energy Hatch", 6).getStackForm(1L));
    // ItemList.Hatch_Energy_ZPM.set(
    // new MTEHatchEnergy(ENERGY_HATCH_ZPM.ID, "hatch.energy.tier.07", "ZPM Energy Hatch", 7).getStackForm(1L));
    // ItemList.Hatch_Energy_UV
    // .set(new MTEHatchEnergy(ENERGY_HATCH_UV.ID, "hatch.energy.tier.08", "UV Energy Hatch", 8).getStackForm(1L));
    // ItemList.Hatch_Energy_UHV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_UHV.ID, "hatch.energy.tier.09", "UHV Energy Hatch", 9).getStackForm(1L));
    // ItemList.Hatch_Energy_UEV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_UEV.ID, "hatch.energy.tier.10", "UEV Energy Hatch", 10).getStackForm(1L));
    //
    // ItemList.Hatch_Energy_UIV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_UIV.ID, "hatch.energy.tier.11", "UIV Energy Hatch", 11).getStackForm(1L));
    //
    // ItemList.Hatch_Energy_UMV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_UMV.ID, "hatch.energy.tier.12", "UMV Energy Hatch", 12).getStackForm(1L));
    //
    // ItemList.Hatch_Energy_UXV.set(
    // new MTEHatchEnergy(ENERGY_HATCH_UXV.ID, "hatch.energy.tier.13", "UXV Energy Hatch", 13).getStackForm(1L));
    //
    // ItemList.DebugEnergyHatch.set(
    // new MTEHatchEnergyDebug(ENERGY_HATCH_DEBUG.ID, "hatch.energy.debug", "Debug Energy Hatch", 14)
    // .getStackForm(1L));
    // }
    //
    // private static void registerInputHatch() {
    // ItemList.Hatch_Input_ULV
    // .set(new MTEHatchInput(INPUT_HATCH_ULV.ID, "hatch.input.tier.00", "Input Hatch (ULV)", 0).getStackForm(1L));
    // ItemList.Hatch_Input_LV
    // .set(new MTEHatchInput(INPUT_HATCH_LV.ID, "hatch.input.tier.01", "Input Hatch (LV)", 1).getStackForm(1L));
    // ItemList.Hatch_Input_MV
    // .set(new MTEHatchInput(INPUT_HATCH_MV.ID, "hatch.input.tier.02", "Input Hatch (MV)", 2).getStackForm(1L));
    // ItemList.Hatch_Input_HV
    // .set(new MTEHatchInput(INPUT_HATCH_HV.ID, "hatch.input.tier.03", "Input Hatch (HV)", 3).getStackForm(1L));
    // ItemList.Hatch_Input_EV
    // .set(new MTEHatchInput(INPUT_HATCH_EV.ID, "hatch.input.tier.04", "Input Hatch (EV)", 4).getStackForm(1L));
    // ItemList.Hatch_Input_IV
    // .set(new MTEHatchInput(INPUT_HATCH_IV.ID, "hatch.input.tier.05", "Input Hatch (IV)", 5).getStackForm(1L));
    // ItemList.Hatch_Input_LuV
    // .set(new MTEHatchInput(INPUT_HATCH_LuV.ID, "hatch.input.tier.06", "Input Hatch (LuV)", 6).getStackForm(1L));
    // ItemList.Hatch_Input_ZPM
    // .set(new MTEHatchInput(INPUT_HATCH_ZPM.ID, "hatch.input.tier.07", "Input Hatch (ZPM)", 7).getStackForm(1L));
    // ItemList.Hatch_Input_UV
    // .set(new MTEHatchInput(INPUT_HATCH_UV.ID, "hatch.input.tier.08", "Input Hatch (UV)", 8).getStackForm(1L));
    // ItemList.Hatch_Input_UHV
    // .set(new MTEHatchInput(INPUT_HATCH_UHV.ID, "hatch.input.tier.09", "Input Hatch (UHV)", 9).getStackForm(1L));
    // ItemList.Hatch_Input_UEV.set(
    // new MTEHatchInput(INPUT_HATCH_UEV.ID, "hatch.input.tier.10", "Input Hatch (UEV)", 10).getStackForm(1L));
    // ItemList.Hatch_Input_UIV.set(
    // new MTEHatchInput(INPUT_HATCH_UIV.ID, "hatch.input.tier.11", "Input Hatch (UIV)", 11).getStackForm(1L));
    // ItemList.Hatch_Input_UMV.set(
    // new MTEHatchInput(INPUT_HATCH_UMV.ID, "hatch.input.tier.12", "Input Hatch (UMV)", 12).getStackForm(1L));
    // ItemList.Hatch_Input_UXV.set(
    // new MTEHatchInput(INPUT_HATCH_UXV.ID, "hatch.input.tier.13", "Input Hatch (UXV)", 13).getStackForm(1L));
    // ItemList.Hatch_Input_MAX.set(
    // new MTEHatchInput(INPUT_HATCH_MAX.ID, "hatch.input.tier.14", "Input Hatch (MAX)", 14).getStackForm(1L));
    // ItemList.Hatch_Input_Debug.set(
    // new MTEHatchInputDebug(INPUT_HATCH_DEBUG.ID, "hatch.input.debug", "Debug Input Hatch", 14)
    // .getStackForm(1L));
    // }
    //
    // private static void registerQuadrupleInputHatch() {
    // ItemList.Hatch_Input_Multi_2x2_EV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_EV.ID,
    // 4,
    // "hatch.multi.input.tier.01",
    // "Quadruple Input Hatch (EV)",
    // 4).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_IV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_IV.ID,
    // 4,
    // "hatch.multi.input.tier.02",
    // "Quadruple Input Hatch (IV)",
    // 5).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_LuV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_LuV.ID,
    // 4,
    // "hatch.multi.input.tier.03",
    // "Quadruple Input Hatch (LuV)",
    // 6).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_ZPM.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_ZPM.ID,
    // 4,
    // "hatch.multi.input.tier.04",
    // "Quadruple Input Hatch (ZPM)",
    // 7).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_UV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_UV.ID,
    // 4,
    // "hatch.multi.input.tier.05",
    // "Quadruple Input Hatch (UV)",
    // 8).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_UHV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_UHV.ID,
    // 4,
    // "hatch.multi.input.tier.06",
    // "Quadruple Input Hatch (UHV)",
    // 9).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_UEV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_UEV.ID,
    // 4,
    // "hatch.multi.input.tier.07",
    // "Quadruple Input Hatch (UEV)",
    // 10).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_UIV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_UIV.ID,
    // 4,
    // "hatch.multi.input.tier.08",
    // "Quadruple Input Hatch (UIV)",
    // 11).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_UMV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_UMV.ID,
    // 4,
    // "hatch.multi.input.tier.09",
    // "Quadruple Input Hatch (UMV)",
    // 12).getStackForm(1L));
    // ItemList.Hatch_Input_Multi_2x2_UXV.set(
    // new MTEHatchMultiInput(
    // QUADRUPLE_INPUT_HATCHES_UXV.ID,
    // 4,
    // "hatch.multi.input.tier.10",
    // "Quadruple Input Hatch (UXV)",
    // 13).getStackForm(1L));
    // }
    //
    // private static void registerOutputHatch() {
    // ItemList.Hatch_Output_ULV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_ULV.ID, "hatch.output.tier.00", "Output Hatch (ULV)", 0).getStackForm(1L));
    // ItemList.Hatch_Output_LV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_LV.ID, "hatch.output.tier.01", "Output Hatch (LV)", 1).getStackForm(1L));
    // ItemList.Hatch_Output_MV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_MV.ID, "hatch.output.tier.02", "Output Hatch (MV)", 2).getStackForm(1L));
    // ItemList.Hatch_Output_HV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_HV.ID, "hatch.output.tier.03", "Output Hatch (HV)", 3).getStackForm(1L));
    // ItemList.Hatch_Output_EV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_EV.ID, "hatch.output.tier.04", "Output Hatch (EV)", 4).getStackForm(1L));
    // ItemList.Hatch_Output_IV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_IV.ID, "hatch.output.tier.05", "Output Hatch (IV)", 5).getStackForm(1L));
    // ItemList.Hatch_Output_LuV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_LuV.ID, "hatch.output.tier.06", "Output Hatch (LuV)", 6).getStackForm(1L));
    // ItemList.Hatch_Output_ZPM.set(
    // new MTEHatchOutput(OUTPUT_HATCH_ZPM.ID, "hatch.output.tier.07", "Output Hatch (ZPM)", 7).getStackForm(1L));
    // ItemList.Hatch_Output_UV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_UV.ID, "hatch.output.tier.08", "Output Hatch (UV)", 8).getStackForm(1L));
    // ItemList.Hatch_Output_UHV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_UHV.ID, "hatch.output.tier.09", "Output Hatch (UHV)", 9).getStackForm(1L));
    // ItemList.Hatch_Output_UEV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_UEV.ID, "hatch.output.tier.10", "Output Hatch (UEV)", 10).getStackForm(1L));
    // ItemList.Hatch_Output_UIV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_UIV.ID, "hatch.output.tier.11", "Output Hatch (UIV)", 11).getStackForm(1L));
    // ItemList.Hatch_Output_UMV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_UMV.ID, "hatch.output.tier.12", "Output Hatch (UMV)", 12).getStackForm(1L));
    // ItemList.Hatch_Output_UXV.set(
    // new MTEHatchOutput(OUTPUT_HATCH_UXV.ID, "hatch.output.tier.13", "Output Hatch (UXV)", 13).getStackForm(1L));
    // ItemList.Hatch_Output_MAX.set(
    // new MTEHatchOutput(OUTPUT_HATCH_MAX.ID, "hatch.output.tier.14", "Output Hatch (MAX)", 14).getStackForm(1L));
    // }
    //
    // private static void registerVoidHatch() {
    // ItemList.Hatch_Void.set(new MTEHatchVoid(VOID_HATCH.ID, "hatch.void.tier.00", "Void Hatch").getStackForm(1L));
    // }
    //
    // private static void registerVoidBus() {
    // ItemList.Hatch_Void_Bus
    // .set(new MTEHatchVoidBus(VOID_BUS.ID, "hatch.void_bus.tier.00", "Void Bus").getStackForm(1L));
    // }
    //
    // private static void registerQuantumTank() {
    // ItemList.Quantum_Tank_LV
    // .set(new MTEQuantumTank(QUANTUM_TANK_LV.ID, "quantum.tank.tier.06", "Quantum Tank I", 6).getStackForm(1L));
    // ItemList.Quantum_Tank_MV
    // .set(new MTEQuantumTank(QUANTUM_TANK_MV.ID, "quantum.tank.tier.07", "Quantum Tank II", 7).getStackForm(1L));
    // ItemList.Quantum_Tank_HV.set(
    // new MTEQuantumTank(QUANTUM_TANK_HV.ID, "quantum.tank.tier.08", "Quantum Tank III", 8).getStackForm(1L));
    // ItemList.Quantum_Tank_EV
    // .set(new MTEQuantumTank(QUANTUM_TANK_EV.ID, "quantum.tank.tier.09", "Quantum Tank IV", 9).getStackForm(1L));
    // ItemList.Quantum_Tank_IV
    // .set(new MTEQuantumTank(QUANTUM_TANK_IV.ID, "quantum.tank.tier.10", "Quantum Tank V", 10).getStackForm(1L));
    // ItemList.Debug_Fluid_Tank
    // .set(new MTEDebugTank(DEBUG_FLUID_TANK.ID, "quantum.tank.debug", "Debug Fluid Tank", 10).getStackForm(1L));
    // }
    //
    // private static void registerQuantumChest() {
    // ItemList.Quantum_Chest_LV.set(
    // new MTEQuantumChest(QUANTUM_CHEST_LV.ID, "quantum.chest.tier.06", "Quantum Chest I", 6).getStackForm(1L));
    // ItemList.Quantum_Chest_MV.set(
    // new MTEQuantumChest(QUANTUM_CHEST_MV.ID, "quantum.chest.tier.07", "Quantum Chest II", 7).getStackForm(1L));
    // ItemList.Quantum_Chest_HV.set(
    // new MTEQuantumChest(QUANTUM_CHEST_HV.ID, "quantum.chest.tier.08", "Quantum Chest III", 8).getStackForm(1L));
    // ItemList.Quantum_Chest_EV.set(
    // new MTEQuantumChest(QUANTUM_CHEST_EV.ID, "quantum.chest.tier.09", "Quantum Chest IV", 9).getStackForm(1L));
    // ItemList.Quantum_Chest_IV.set(
    // new MTEQuantumChest(QUANTUM_CHEST_IV.ID, "quantum.chest.tier.10", "Quantum Chest V", 10).getStackForm(1L));
    // }
    //
    // private static void registerSuperTank() {
    // ItemList.Super_Tank_LV
    // .set(new MTESuperTank(SUPER_TANK_LV.ID, "super.tank.tier.01", "Super Tank I", 1).getStackForm(1L));
    // ItemList.Super_Tank_MV
    // .set(new MTESuperTank(SUPER_TANK_MV.ID, "super.tank.tier.02", "Super Tank II", 2).getStackForm(1L));
    // ItemList.Super_Tank_HV
    // .set(new MTESuperTank(SUPER_TANK_HV.ID, "super.tank.tier.03", "Super Tank III", 3).getStackForm(1L));
    // ItemList.Super_Tank_EV
    // .set(new MTESuperTank(SUPER_TANK_EV.ID, "super.tank.tier.04", "Super Tank IV", 4).getStackForm(1L));
    // ItemList.Super_Tank_IV
    // .set(new MTESuperTank(SUPER_TANK_IV.ID, "super.tank.tier.05", "Super Tank V", 5).getStackForm(1L));
    // }
    //
    // private static void registerSuperChest() {
    // ItemList.Super_Chest_LV
    // .set(new MTESuperChest(SUPER_CHEST_LV.ID, "super.chest.tier.01", "Super Chest I", 1).getStackForm(1L));
    // ItemList.Super_Chest_MV
    // .set(new MTESuperChest(SUPER_CHEST_MV.ID, "super.chest.tier.02", "Super Chest II", 2).getStackForm(1L));
    // ItemList.Super_Chest_HV
    // .set(new MTESuperChest(SUPER_CHEST_HV.ID, "super.chest.tier.03", "Super Chest III", 3).getStackForm(1L));
    // ItemList.Super_Chest_EV
    // .set(new MTESuperChest(SUPER_CHEST_EV.ID, "super.chest.tier.04", "Super Chest IV", 4).getStackForm(1L));
    // ItemList.Super_Chest_IV
    // .set(new MTESuperChest(SUPER_CHEST_IV.ID, "super.chest.tier.05", "Super Chest V", 5).getStackForm(1L));
    // }
    //
    // private static void registerAE2Hatches() {
    // ItemList.Hatch_Output_Bus_ME
    // .set(new MTEHatchOutputBusME(OUTPUT_BUS_ME.ID, "hatch.output_bus.me", "Output Bus (ME)").getStackForm(1L));
    // ItemList.Hatch_Input_Bus_ME.set(
    // new MTEHatchInputBusME(INPUT_BUS_ME.ID, false, "hatch.input_bus.me.basic", "Stocking Input Bus (ME)")
    // .getStackForm(1L));
    // ItemList.Hatch_Input_Bus_ME_Advanced.set(
    // new MTEHatchInputBusME(
    // INPUT_BUS_ME_ADVANCED.ID,
    // true,
    // "hatch.input_bus.me",
    // "Advanced Stocking Input Bus (ME)").getStackForm(1L));
    // ItemList.Hatch_Input_ME.set(
    // new MTEHatchInputME(INPUT_HATCH_ME.ID, false, "hatch.input.me.basic", "Stocking Input Hatch (ME)")
    // .getStackForm(1L));
    // ItemList.Hatch_Input_ME_Advanced.set(
    // new MTEHatchInputME(
    // INPUT_HATCH_ME_ADVANCED.ID,
    // true,
    // "hatch.input.me",
    // "Advanced Stocking Input Hatch (ME)").getStackForm(1L));
    // ItemList.Hatch_Output_ME
    // .set(new MTEHatchOutputME(OUTPUT_HATCH_ME.ID, "hatch.output.me", "Output Hatch (ME)").getStackForm(1L));
    // ItemList.Hatch_CraftingInput_Bus_ME.set(
    // new MTEHatchCraftingInputME(
    // CRAFTING_INPUT_ME.ID,
    // "hatch.crafting_input.me",
    // "Crafting Input Buffer (ME)",
    // true).getStackForm(1L));
    // ItemList.Hatch_CraftingInput_Bus_ME_ItemOnly.set(
    // new MTEHatchCraftingInputME(
    // CRAFTING_INPUT_ME_BUS.ID,
    // "hatch.crafting_input.me.item_only",
    // "Crafting Input Bus (ME)",
    // false).getStackForm(1L));
    // ItemList.Hatch_CraftingInput_Bus_Slave.set(
    // new MTEHatchCraftingInputSlave(
    // CRAFTING_INPUT_SLAVE.ID,
    // "hatch.crafting_input.proxy",
    // "Crafting Input Proxy").getStackForm(1L));
    // }
    //
    // private static void registerInputBus() {
    // ItemList.Hatch_Input_Bus_ULV.set(
    // new MTEHatchInputBus(INPUT_BUS_ULV.ID, "hatch.input_bus.tier.00", "Input Bus (ULV)", 0).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_LV.set(
    // new MTEHatchInputBus(INPUT_BUS_LV.ID, "hatch.input_bus.tier.01", "Input Bus (LV)", 1).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_MV.set(
    // new MTEHatchInputBus(INPUT_BUS_MV.ID, "hatch.input_bus.tier.02", "Input Bus (MV)", 2).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_HV.set(
    // new MTEHatchInputBus(INPUT_BUS_HV.ID, "hatch.input_bus.tier.03", "Input Bus (HV)", 3).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_EV.set(
    // new MTEHatchInputBus(INPUT_BUS_EV.ID, "hatch.input_bus.tier.04", "Input Bus (EV)", 4).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_IV.set(
    // new MTEHatchInputBus(INPUT_BUS_IV.ID, "hatch.input_bus.tier.05", "Input Bus (IV)", 5).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_LuV.set(
    // new MTEHatchInputBus(INPUT_BUS_LuV.ID, "hatch.input_bus.tier.06", "Input Bus (LuV)", 6).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_ZPM.set(
    // new MTEHatchInputBus(INPUT_BUS_ZPM.ID, "hatch.input_bus.tier.07", "Input Bus (ZPM)", 7).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_UV.set(
    // new MTEHatchInputBus(INPUT_BUS_UV.ID, "hatch.input_bus.tier.08", "Input Bus (UV)", 8).getStackForm(1L));
    // ItemList.Hatch_Input_Bus_MAX.set(
    // new MTEHatchInputBus(INPUT_BUS_UHV.ID, "hatch.input_bus.tier.09", "Input Bus (UHV)", 9).getStackForm(1L));
    //
    // ItemList.Hatch_Input_Bus_Debug.set(
    // new MTEHatchInputBusDebug(INPUT_BUS_DEBUG.ID, "hatch.input_bus.debug", "Debug Input Bus", 9)
    // .getStackForm(1L));
    // }
    //
    // private static void registerOutputBus() {
    // ItemList.Hatch_Output_Bus_ULV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_ULV.ID, "hatch.output_bus.tier.00", "Output Bus (ULV)", 0)
    // .getStackForm(1L));
    // ItemList.Hatch_Output_Bus_LV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_LV.ID, "hatch.output_bus.tier.01", "Output Bus (LV)", 1).getStackForm(1L));
    // ItemList.Hatch_Output_Bus_MV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_MV.ID, "hatch.output_bus.tier.02", "Output Bus (MV)", 2).getStackForm(1L));
    // ItemList.Hatch_Output_Bus_HV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_HV.ID, "hatch.output_bus.tier.03", "Output Bus (HV)", 3).getStackForm(1L));
    // ItemList.Hatch_Output_Bus_EV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_EV.ID, "hatch.output_bus.tier.04", "Output Bus (EV)", 4).getStackForm(1L));
    // ItemList.Hatch_Output_Bus_IV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_IV.ID, "hatch.output_bus.tier.05", "Output Bus (IV)", 5).getStackForm(1L));
    // ItemList.Hatch_Output_Bus_LuV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_LuV.ID, "hatch.output_bus.tier.06", "Output Bus (LuV)", 6)
    // .getStackForm(1L));
    // ItemList.Hatch_Output_Bus_ZPM.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_ZPM.ID, "hatch.output_bus.tier.07", "Output Bus (ZPM)", 7)
    // .getStackForm(1L));
    // ItemList.Hatch_Output_Bus_UV.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_UV.ID, "hatch.output_bus.tier.08", "Output Bus (UV)", 8).getStackForm(1L));
    // ItemList.Hatch_Output_Bus_MAX.set(
    // new MTEHatchOutputBus(OUTPUT_BUS_UHV.ID, "hatch.output_bus.tier.09", "Output Bus (UHV)", 9)
    // .getStackForm(1L));
    // }
    //
    // private static void registerMufflerHatch() {
    // ItemList.Hatch_Muffler_LV.set(
    // new MTEHatchMuffler(MUFFLER_HATCH_LV.ID, "hatch.muffler.tier.01", "Muffler Hatch (LV)", 1)
    // .getStackForm(1L));
    // }
    //
    // private static void registerBatteryBuffer4x4() {
    // ItemList.Battery_Buffer_4by4_LV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_LV.ID,
    // "batterybuffer.16.tier.01",
    // "Low Voltage Battery Buffer",
    // 1,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_MV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_MV.ID,
    // "batterybuffer.16.tier.02",
    // "Medium Voltage Battery Buffer",
    // 2,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_HV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_HV.ID,
    // "batterybuffer.16.tier.03",
    // "High Voltage Battery Buffer",
    // 3,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_EV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_EV.ID,
    // "batterybuffer.16.tier.04",
    // "Extreme Voltage Battery Buffer",
    // 4,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_IV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_IV.ID,
    // "batterybuffer.16.tier.05",
    // "Insane Voltage Battery Buffer",
    // 5,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_LuV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_LuV.ID,
    // "batterybuffer.16.tier.06",
    // "Ludicrous Voltage Battery Buffer",
    // 6,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_ZPM.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_ZPM.ID,
    // "batterybuffer.16.tier.07",
    // "ZPM Voltage Battery Buffer",
    // 7,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_UV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_UV.ID,
    // "batterybuffer.16.tier.08",
    // "Ultimate Voltage Battery Buffer",
    // 8,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_UHV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_UHV.ID,
    // "batterybuffer.16.tier.09",
    // "Highly Ultimate Voltage Battery Buffer",
    // 9,
    // "",
    // 16).getStackForm(1L));
    // ItemList.Battery_Buffer_4by4_UEV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_UEV.ID,
    // "batterybuffer.16.tier.10",
    // "Extremely Ultimate Battery Buffer",
    // 10,
    // "",
    // 16).getStackForm(1L));
    //
    // ItemList.Battery_Buffer_4by4_UIV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_UIV.ID,
    // "batterybuffer.16.tier.11",
    // "Insanely Ultimate Battery Buffer",
    // 11,
    // "",
    // 16).getStackForm(1L));
    //
    // ItemList.Battery_Buffer_4by4_UMV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_UMV.ID,
    // "batterybuffer.16.tier.12",
    // "Mega Ultimate Battery Buffer",
    // 12,
    // "",
    // 16).getStackForm(1L));
    //
    // ItemList.Battery_Buffer_4by4_UXV.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_UXV.ID,
    // "batterybuffer.16.tier.13",
    // "Extended Mega Ultimate Battery Buffer",
    // 13,
    // "",
    // 16).getStackForm(1L));
    //
    // ItemList.Battery_Buffer_4by4_MAX.set(
    // new MTEBasicBatteryBuffer(
    // BATTERY_BUFFER_4_BY_4_MAX.ID,
    // "batterybuffer.16.tier.14",
    // "Maximum Battery Buffer",
    // 14,
    // "",
    // 16).getStackForm(1L));
    // }
    //
    // private void registerWetTransformer() {
    // ItemList.WetTransformer_LV_ULV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_LV_ULV.ID,
    // "wettransformer.tier.00",
    // "Ultra Low Voltage Power Transformer",
    // 0).getStackForm(1L));
    //
    // ItemList.WetTransformer_MV_LV.set(
    // new MTEWetTransformer(WET_TRANSFORMER_MV_LV.ID, "wetransformer.tier.01", "Low Voltage Power Transformer", 1)
    // .getStackForm(1L));
    //
    // ItemList.WetTransformer_HV_MV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_HV_MV.ID,
    // "wettransformer.tier.02",
    // "Medium Voltage Power Transformer",
    // 2).getStackForm(1L));
    //
    // ItemList.WetTransformer_EV_HV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_EV_HV.ID,
    // "wettransformer.tier.03",
    // "High Voltage Power Transformer",
    // 3).getStackForm(1L));
    //
    // ItemList.WetTransformer_IV_EV.set(
    // new MTEWetTransformer(WET_TRANSFORMER_IV_EV.ID, "wettransformer.tier.04", "Extreme Power Transformer", 4)
    // .getStackForm(1L));
    //
    // ItemList.WetTransformer_LuV_IV.set(
    // new MTEWetTransformer(WET_TRANSFORMER_LuV_IV.ID, "wettransformer.tier.05", "Insane Power Transformer", 5)
    // .getStackForm(1L));
    //
    // ItemList.WetTransformer_ZPM_LuV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_ZPM_LuV.ID,
    // "wettransformer.tier.06",
    // "Ludicrous Power Transformer",
    // 6).getStackForm(1L));
    //
    // ItemList.WetTransformer_UV_ZPM.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_UV_ZPM.ID,
    // "wettransformer.tier.07",
    // "ZPM Voltage Power Transformer",
    // 7).getStackForm(1L));
    //
    // ItemList.WetTransformer_UHV_UV.set(
    // new MTEWetTransformer(WET_TRANSFORMER_UHV_UV.ID, "wettransformer.tier.08", "Ultimate Power Transformer", 8)
    // .getStackForm(1L));
    //
    // ItemList.WetTransformer_UEV_UHV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_UEV_UHV.ID,
    // "wettransformer.tier.09",
    // "Highly Ultimate Power Transformer",
    // 9).getStackForm(1L));
    //
    // ItemList.WetTransformer_UIV_UEV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_UIV_UEV.ID,
    // "wettransformer.tier.10",
    // "Extremely Ultimate Power Transformer",
    // 10).getStackForm(1L));
    //
    // ItemList.WetTransformer_UMV_UIV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_UMV_UIV.ID,
    // "wettransformer.tier.11",
    // "Insanely Ultimate Power Transformer",
    // 11).getStackForm(1L));
    //
    // ItemList.WetTransformer_UXV_UMV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_UXV_UMV.ID,
    // "wettransformer.tier.12",
    // "Mega Ultimate Power Transformer",
    // 12).getStackForm(1L));
    //
    // ItemList.WetTransformer_MAX_UXV.set(
    // new MTEWetTransformer(
    // WET_TRANSFORMER_MAX_UXV.ID,
    // "wettransformer.tier.13",
    // "Extended Mega Ultimate Power Transformer",
    // 13).getStackForm(1L));
    // }
    //
    // private static void registerWirelessEnergyHatch() {
    // ItemList.Wireless_Hatch_Energy_ULV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_ULV.ID,
    // "hatch.wireless.receiver.tier.00",
    // "ULV Wireless Energy Hatch",
    // 0).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_LV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_LV.ID,
    // "hatch.wireless.receiver.tier.01",
    // "LV Wireless Energy Hatch",
    // 1).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_MV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_MV.ID,
    // "hatch.wireless.receiver.tier.02",
    // "MV Wireless Energy Hatch",
    // 2).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_HV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_HV.ID,
    // "hatch.wireless.receiver.tier.03",
    // "HV Wireless Energy Hatch",
    // 3).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_EV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_EV.ID,
    // "hatch.wireless.receiver.tier.04",
    // "EV Wireless Energy Hatch",
    // 4).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_IV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_IV.ID,
    // "hatch.wireless.receiver.tier.05",
    // "IV Wireless Energy Hatch",
    // 5).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_LuV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_LuV.ID,
    // "hatch.wireless.receiver.tier.06",
    // "LuV Wireless Energy Hatch",
    // 6).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_ZPM.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_ZPM.ID,
    // "hatch.wireless.receiver.tier.07",
    // "ZPM Wireless Energy Hatch",
    // 7).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_UV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_UV.ID,
    // "hatch.wireless.receiver.tier.08",
    // "UV Wireless Energy Hatch",
    // 8).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_UHV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_UHV.ID,
    // "hatch.wireless.receiver.tier.09",
    // "UHV Wireless Energy Hatch",
    // 9).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_UEV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_UEV.ID,
    // "hatch.wireless.receiver.tier.10",
    // "UEV Wireless Energy Hatch",
    // 10).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_UIV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_UIV.ID,
    // "hatch.wireless.receiver.tier.11",
    // "UIV Wireless Energy Hatch",
    // 11).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_UMV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_UMV.ID,
    // "hatch.wireless.receiver.tier.12",
    // "UMV Wireless Energy Hatch",
    // 12).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_UXV.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_UXV.ID,
    // "hatch.wireless.receiver.tier.13",
    // "UXV Wireless Energy Hatch",
    // 13).getStackForm(1L));
    // ItemList.Wireless_Hatch_Energy_MAX.set(
    // new MTEWirelessEnergy(
    // WIRELESS_HATCH_ENERGY_MAX.ID,
    // "hatch.wireless.receiver.tier.14",
    // "MAX Wireless Energy Hatch",
    // 14).getStackForm(1L));
    // }

    @Override
    public void run() {
        GT_FML_LOGGER.debug("GTMod: Registering MetaTileEntities.");
        // registerMachineHull();
        // registerTransformer();
        // registerDynamoHatch();
        // registerEnergyHatch();
        // registerInputHatch();
        // registerQuadrupleInputHatch();
        // registerOutputHatch();
        // registerVoidHatch();
        // registerQuantumTank();
        // registerQuantumChest();
        // registerSuperTank();
        // registerSuperChest();
        // registerAE2Hatches();
        // registerInputBus();
        // registerOutputBus();
        // registerVoidBus();
        // registerMufflerHatch();
        // registerBatteryBuffer4x4();
        // registerWirelessEnergyHatch();
        // registerWetTransformer();

        // for (int i = 0; i < 30_000; i++) {
        // new MTEHatchMultiInput(
        // i,
        // 4,
        // "hatch.multi.input.tier.05",
        // "Quadruple Input Hatch (UV)",
        // 8,
        // MTEArrays.HATCH).getStackForm(1L);
        //
        // new MTEQuantumChest(i, "quantum.chest.tier.09", "Quantum Chest IV", 9, MTEArrays.CABLE).getStackForm(1L);
        // }

        // new MTEHatchMultiInput(
        // QUADRUPLE_INPUT_HATCHES_UV.ID,
        // 4,
        // "hatch.multi.input.tier.05",
        // "Quadruple Input Hatch (UV)",
        // 8,
        // MTEArrays.CABLE).getStackForm(1L);

        //
        // ItemList.DebugStructureWriter.set(
        // new MTEDebugStructureWriter(DEBUG_STRUCTURE_WRITER.ID, "debugstructurewriter", "Debug Structure Writer", 5)
        // .getStackForm(1L));
        // ItemList.Hatch_Maintenance.set(
        // new MTEHatchMaintenance(MAINTENANCE_HATCH.ID, "hatch.maintenance", "Maintenance Hatch", 1)
        // .getStackForm(1L));
        // ItemList.Hatch_AutoMaintenance.set(
        // new MTEHatchMaintenance(
        // AUTO_MAINTENANCE_HATCH.ID,
        // "hatch.maintenance.auto",
        // "Auto Maintenance Hatch",
        // 6,
        // true).getStackForm(1L));
    }
}
