package gregtech.loaders.postload.recipes;

import static goodgenerator.util.ItemRefer.Compassline_Casing_UMV;
import static goodgenerator.util.ItemRefer.Compassline_Casing_UXV;
import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.Avaritia;
import static gregtech.api.enums.Mods.GraviSuite;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.SuperSolarPanels;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.CondensateType;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.NaniteTier;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import tectech.thing.CustomItemList;

/// Recipes made in the BEC Condensate Assembler.
///
/// As a design constraint, recipes should fit into as narrow condensate families as possible. Presently, this is 5
/// 7-member families, given below.
///
/// | Family | Condensates |
/// |----------|------------------------------------------------------------------------------------------|
/// | Family 1 | SpaceTime, Space, Time, Neutronium, Cosmic Neutronium, Cosmic Solder, Bedrockium |
/// | Family 2 | Chromatic Glass, Celestial Tungsten, Infinity, Transcendent Metal, DSS, Cosmic Solder, Eternity |
/// | Family 3 | Phononic Medium, Universium, MHDCSM, QGP, MagMatter, Cosmic Solder, Eternity |
/// | Family 4 | Phononic Medium, Celestial Tungsten, Infinity, Neutronium, Cosmic Neutronium, Hypogen, Bedrockium |
/// | Family 5 | SpaceTime, Space, MHDCSM, QGP, DSS, Cosmic Solder, Eternity |
public class BECRecipes implements Runnable {

    private static final NaniteTier[] TIER_TO_NANITE = { NaniteTier.Carbon, NaniteTier.Silver, NaniteTier.Gold,
        NaniteTier.Transcendent, NaniteTier.SixPhasedCopper, NaniteTier.WhiteDwarf, NaniteTier.BlackDwarf,
        NaniteTier.Universium, NaniteTier.Eternity, NaniteTier.MagMatter };

    // The old EoH recipes were gated by the material of their bolt, following the tier of BOLT_TIER
    // This same tiering pattern is used through the 4-tier BEC Metamaterials.
    // "Bolt" is used for brevity.
    private static final int[] BOLT_TIER = { 1, 2, 2, 2, 3, 3, 3, 4, 4 };
    private static final int[] BOLT_POS = { 0, 0, 1, 2, 0, 1, 2, 0, 1 };
    // Materials used to differentiate each tier of EoH part
    private static final Materials[] TIER_MATS = { Materials.Netherite, Materials.ProtoHalkonite,
        Materials.SixPhasedCopper, Materials.TranscendentMetal, Materials.Mellion, Materials.Creon, Materials.SpaceTime,
        Materials.Hexanite, Materials.Eternity };
    private static final int[] EOH_TIMES = { 2_000_000, 3_000_000, 4_000_000, 5_000_000, 100_000, 120_000, 140_000,
        160_000, 180_000 };

    @Override
    public void run() {
        addBECCasingRecipes();
        if (NewHorizonsCoreMod.isModLoaded()) addGodforgeRecipes();
        if (Avaritia.isModLoaded() && AE2FluidCraft.isModLoaded()) {
            addEyeOfHarmonyRecipes();
        }

        // Transdimensional Alignment Matrix (Convergence)
        addBec(
            ItemList.Transdimensional_Alignment_Matrix.get(1),
            new ItemStack[] { CustomItemList.EOH_Infinite_Energy_Casing.get(1), ItemList.Sensor_UMV.get(16),
                ItemList.Field_Generator_UMV.get(4), ItemList.ZPM5.get(1), ItemList.EnergisedTesseract.get(32),
                GTOreDictUnificator.get(OrePrefixes.nanite, Materials.TranscendentMetal, 16),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Flerovium, 64),
                GGMaterial.metastableOganesson.get(OrePrefixes.plateDense, 32),
                ItemList.MetaMaterial_SensorArray1.get(32), ItemList.MetaMaterial_FieldManipulator1.get(32) },
            nanites(1, 2, 4, 4, 4, 3, 2, 4, 1, 1, 2, 3),
            new FluidStack[] { CondensateType.SpaceTime.getEntangled(192 * INGOTS),
                CondensateType.DimensionallyShiftedSuperfluid.getEntangled(10_000),
                CondensateType.Space.getEntangled(32 * INGOTS) },
            600 * SECONDS,
            TierEU.RECIPE_UMV);

        // UMV Component Assembly Line Casing (UMV CoAL)
        addBec(
            Compassline_Casing_UMV.get(1),
            new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.SpaceTime, 1),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.SpaceTime, 1),
                ItemList.Robot_Arm_UMV.get(8), ItemList.Electric_Piston_UMV.get(10),
                ItemList.Electric_Motor_UMV.get(16),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.SpaceTime, 4),
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.SpaceTime, 16),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Quantium, 8),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UMV, 8),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UIV, 16),
                ItemList.MetaMaterial_Shielding1.get(64), ItemList.MetaMaterial_EnergyConduit1.get(64) },
            nanites(1, 1, 3, 3, 3, 2, 2, 1, 4, 4, 2, 2),
            new FluidStack[] { CondensateType.TranscendentMetal.getEntangled(32 * INGOTS),
                CondensateType.Space.getEntangled(24 * INGOTS), CondensateType.SpaceTime.getEntangled(12 * INGOTS),
                CondensateType.DimensionallyShiftedSuperfluid.getEntangled(10_000) },
            600 * SECONDS,
            TierEU.RECIPE_UMV);

        // UXV Component Assembly Line Casing (UXV CoAL)
        addBec(
            Compassline_Casing_UXV.get(1),
            new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.MHDCSM, 1),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.MHDCSM, 3),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.MagMatter, 3), ItemList.Robot_Arm_UXV.get(8),
                ItemList.Electric_Piston_UXV.get(10), ItemList.Electric_Motor_UXV.get(16),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.MHDCSM, 2),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.MagMatter, 2),
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.MHDCSM, 8),
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.MagMatter, 8),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.SpaceTime, 8),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UXV, 8),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UMV, 16),
                ItemList.MetaMaterial_Shielding3.get(64), ItemList.MetaMaterial_EnergyConduit3.get(64),
                ItemList.MetaMaterial_Waveguide3.get(64) },
            nanites(1, 3, 3, 7, 6, 6, 5, 5, 4, 4, 2, 5, 5, 6, 8, 9),
            new FluidStack[] { CondensateType.BoundlessCosmicSolder.getEntangled(5_000),
                CondensateType.MHDCSM.getEntangled(8 * INGOTS), CondensateType.Eternity.getEntangled(13 * INGOTS),
                CondensateType.DimensionallyShiftedSuperfluid.getEntangled(11_000) },
            600 * SECONDS,
            TierEU.RECIPE_UMV);
    }

    private void addGodforgeRecipes() {
        // Magnetic Confinement Casing

        // Graviton Modulator 2

        // Graviton Modulator 3

        // Phonon Transmission Conduit
    }

    public void runLateRecipes() {
        // Shielding Casing

    }

    private void addBECCasingRecipes() {
        // Electromagnetically-isolated Casing

        // Coherence-preserving Plasma Conduit

        // Fine-structure Constant Manipulator

        // Condensate Guidance Coil

        // Condensate Transformative Coil

        // Electromagnetic Waveguide

    }

    private void addEyeOfHarmonyRecipes() {
        addEyeOfHarmonyCasings();
        addTimeDilation();
        addSpacetimeCompression();
        addStabilisation();

        // Astral Array Fabricator
        addBec(
            CustomItemList.astralArrayFabricator.get(1),
            new ItemStack[] { CustomItemList.SpacetimeCompressionFieldGeneratorTier8.get(64),
                CustomItemList.SpacetimeCompressionFieldGeneratorTier8.get(64),
                CustomItemList.SpacetimeCompressionFieldGeneratorTier8.get(10),
                CustomItemList.StabilisationFieldGeneratorTier8.get(48),
                CustomItemList.TimeAccelerationFieldGeneratorTier8.get(64),
                CustomItemList.TimeAccelerationFieldGeneratorTier8.get(64),
                CustomItemList.TimeAccelerationFieldGeneratorTier8.get(40),
                CustomItemList.EOH_Infinite_Energy_Casing.get(32),
                CustomItemList.EOH_Reinforced_Temporal_Casing.get(64),
                CustomItemList.EOH_Reinforced_Spatial_Casing.get(64),
                GTOreDictUnificator.get(OrePrefixes.nanite, Materials.WhiteDwarfMatter, 2),
                GTOreDictUnificator.get(OrePrefixes.nanite, Materials.BlackDwarfMatter, 2),
                ItemList.Transdimensional_Alignment_Matrix.get(1), ItemList.MetaMaterial_Shielding3.get(16),
                ItemList.MetaMaterial_ElectrograviticValve3.get(16), ItemList.MetaMaterial_FieldManipulator4.get(32) },
            nanites(6, 6, 6, 8, 6, 6, 6, 3, 1, 1, 2, 9, 10, 4, 5, 7),
            new FluidStack[] { CondensateType.BoundlessCosmicSolder.getEntangled(1_024_000),
                CondensateType.Space.getEntangled(3_640 * INGOTS),
                CondensateType.SpaceTime.getEntangled(3_640 * INGOTS),
                CondensateType.Eternity.getEntangled(1_820 * INGOTS) },
            7200 * SECONDS,
            TierEU.RECIPE_UXV);
    }

    private void addEyeOfHarmonyCasings() {

        final int casingTime = 40_000 * SECONDS;
        // Reinforced Temporal Structure Casing
        addBec(
            CustomItemList.EOH_Reinforced_Temporal_Casing.get(4),
            new ItemStack[] { CustomItemList.Godforge_SingularityShieldingCasing.get(32),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.ProtoHalkonite, 32),
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Hexanite, 8),
                GTOreDictUnificator.get(OrePrefixes.nanite, Materials.Neutronium, 48),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Bedrockium, 2),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.CosmicNeutronium, 2),
                GGMaterial.shirabon.get(OrePrefixes.plateSuperdense, 2),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Infinity, 2),
                ItemList.Machine_UV_SolarPanel.get(1), ItemList.AcceleratorUV.get(4),
                getModItem(GraviSuite.ID, "itemSimpleItem", 64, 3), ItemList.EnergisedTesseract.get(1) },
            nanites(4, 1, 1, 2, 1, 1, 4, 1, 1, 1, 1, 2),
            new FluidStack[] { CondensateType.Neutronium.getEntangled(512 * INGOTS),
                CondensateType.CosmicNeutronium.getEntangled(512 * INGOTS),
                CondensateType.Bedrockium.getEntangled(256 * INGOTS), CondensateType.Time.getEntangled(10 * INGOTS) },
            casingTime,
            TierEU.RECIPE_UMV);

        // Reinforced Spatial Structure Casing
        addBec(
            CustomItemList.EOH_Reinforced_Spatial_Casing.get(4),
            new ItemStack[] { CustomItemList.Godforge_SingularityShieldingCasing.get(32),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.ProtoHalkonite, 32),
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Hexanite, 8),
                GTOreDictUnificator.get(OrePrefixes.nanite, Materials.Neutronium, 48),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Bedrockium, 2),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.CosmicNeutronium, 2),
                GGMaterial.shirabon.get(OrePrefixes.plateSuperdense, 2),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Infinity, 2),
                ItemList.Machine_UV_SolarPanel.get(1), ItemList.Quantum_Chest_IV.get(1),
                getModItem(GraviSuite.ID, "itemSimpleItem", 64, 3), ItemList.EnergisedTesseract.get(1) },
            nanites(4, 1, 1, 2, 1, 1, 4, 1, 1, 1, 1, 2),
            new FluidStack[] { CondensateType.Neutronium.getEntangled(512 * INGOTS),
                CondensateType.CosmicNeutronium.getEntangled(512 * INGOTS),
                CondensateType.Bedrockium.getEntangled(256 * INGOTS), CondensateType.Space.getEntangled(10 * INGOTS) },
            casingTime,
            TierEU.RECIPE_UMV);

        // Infinite Spacetime Boundary Casing
    }

    private void addTimeDilation() {
        ItemList[] waveFocus = { ItemList.MetaMaterial_WaveFocus1, ItemList.MetaMaterial_WaveFocus2,
            ItemList.MetaMaterial_WaveFocus3, ItemList.MetaMaterial_WaveFocus4 };
        CustomItemList[] outputs = { CustomItemList.TimeAccelerationFieldGeneratorTier0,
            CustomItemList.TimeAccelerationFieldGeneratorTier1, CustomItemList.TimeAccelerationFieldGeneratorTier2,
            CustomItemList.TimeAccelerationFieldGeneratorTier3, CustomItemList.TimeAccelerationFieldGeneratorTier4,
            CustomItemList.TimeAccelerationFieldGeneratorTier5, CustomItemList.TimeAccelerationFieldGeneratorTier6,
            CustomItemList.TimeAccelerationFieldGeneratorTier7, CustomItemList.TimeAccelerationFieldGeneratorTier8 };
        int[] focusQty = { 1, 4, 16 };

        for (int t = 0; t < 9; t++) {
            int tp1 = t + 1;
            ItemStack[] inputs = { CustomItemList.EOH_Reinforced_Temporal_Casing.get(1),
                CustomItemList.Godforge_GravitonFlowModulatorTier2.get(1), ItemList.Machine_UV_SolarPanel.get(tp1),
                ItemList.AcceleratorUV.get(4L * tp1), GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UXV, tp1),
                getModItem(SuperSolarPanels.ID, "redcomponent", 64),
                getModItem(SuperSolarPanels.ID, "greencomponent", 64),
                getModItem(SuperSolarPanels.ID, "bluecomponent", 64), ItemList.MetaMaterial_EnergyConduit1.get(tp1),
                waveFocus[BOLT_TIER[t] - 1].get(focusQty[BOLT_POS[t]]),
                ItemList.MetaMaterial_ElectrograviticValve1.get(2L * tp1), ItemList.MetaMaterial_Waveguide1.get(tp1),
                ItemList.Energy_Cluster.get(tp1),
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.Hexanite, 4L * tp1),
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Hexanite, 4 * tp1),
                GTOreDictUnificator.get(OrePrefixes.gearGt, TIER_MATS[t], tp1) };
            NaniteTier[] nanites = nanitesShifted(BOLT_TIER[t] - 1, 2, 2, 2, 2, 2, 1, 1, 1, 3, 4, 3, 3, 4, 1, 1, 1);
            FluidStack[] condensates = { cosmicSolder(t), CondensateType.Time.getEntangled(10 * tp1 * INGOTS),
                CondensateType.SpaceTime.getEntangled(10 * INGOTS),
                CondensateType.CosmicNeutronium.getEntangled(20 * tp1 * INGOTS) };
            addBec(outputs[t].get(1), inputs, nanites, condensates, EOH_TIMES[t], TierEU.RECIPE_UMV);
        }
    }

    private void addSpacetimeCompression() {
        ItemList[] resonanceChamber = { ItemList.MetaMaterial_ResonanceChamber1,
            ItemList.MetaMaterial_ResonanceChamber2, ItemList.MetaMaterial_ResonanceChamber3,
            ItemList.MetaMaterial_ResonanceChamber4 };
        CustomItemList[] outputs = { CustomItemList.SpacetimeCompressionFieldGeneratorTier0,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier1,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier2,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier3,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier4,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier5,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier6,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier7,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier8 };
        int[] resonanceQty = { 1, 4, 16 };

        for (int t = 0; t < 9; t++) {
            int tp1 = t + 1;
            ItemStack[] inputs = { CustomItemList.EOH_Reinforced_Spatial_Casing.get(1),
                ItemList.Machine_Multi_BlackHoleCompressor.get(tp1),
                getModItem(AppliedEnergistics2.ID, "item.ItemExtremeStorageCell.Singularity", 1),
                ItemList.MetaMaterial_Shielding1.get(4L * tp1),
                resonanceChamber[BOLT_TIER[t] - 1].get(resonanceQty[BOLT_POS[t]]),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UXV, tp1),
                CustomItemList.Godforge_SingularityShieldingCasing.get(4 * tp1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Hexanite, tp1),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Hexanite, 2L * tp1),
                GTOreDictUnificator.get(OrePrefixes.plateDense, TIER_MATS[t], tp1),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Hexanite, tp1),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.CosmicNeutronium, tp1),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Infinity, tp1),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.TranscendentMetal, tp1) };
            NaniteTier[] nanites = nanitesShifted(BOLT_TIER[t] - 1, 3, 3, 2, 4, 2, 2, 4, 3, 2, 2, 1, 1, 1, 1, 1, 1);
            FluidStack[] condensates = { cosmicSolder(t), CondensateType.Space.getEntangled(10 * tp1 * INGOTS),
                CondensateType.SpaceTime.getEntangled(10 * INGOTS),
                CondensateType.Neutronium.getEntangled(20 * tp1 * INGOTS) };
            addBec(outputs[t].get(1), inputs, nanites, condensates, EOH_TIMES[t], TierEU.RECIPE_UMV);
        }
    }

    private void addStabilisation() {
        ItemList[] sensorArray = { ItemList.MetaMaterial_SensorArray1, ItemList.MetaMaterial_SensorArray2,
            ItemList.MetaMaterial_SensorArray3, ItemList.MetaMaterial_SensorArray4 };
        ItemList[] fieldManipulator = { ItemList.MetaMaterial_FieldManipulator1,
            ItemList.MetaMaterial_FieldManipulator2, ItemList.MetaMaterial_FieldManipulator3,
            ItemList.MetaMaterial_FieldManipulator4 };
        CustomItemList[] timeGens = { CustomItemList.TimeAccelerationFieldGeneratorTier0,
            CustomItemList.TimeAccelerationFieldGeneratorTier1, CustomItemList.TimeAccelerationFieldGeneratorTier2,
            CustomItemList.TimeAccelerationFieldGeneratorTier3, CustomItemList.TimeAccelerationFieldGeneratorTier4,
            CustomItemList.TimeAccelerationFieldGeneratorTier5, CustomItemList.TimeAccelerationFieldGeneratorTier6,
            CustomItemList.TimeAccelerationFieldGeneratorTier7, CustomItemList.TimeAccelerationFieldGeneratorTier8 };
        CustomItemList[] spaceGens = { CustomItemList.SpacetimeCompressionFieldGeneratorTier0,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier1,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier2,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier3,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier4,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier5,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier6,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier7,
            CustomItemList.SpacetimeCompressionFieldGeneratorTier8 };
        CustomItemList[] outputs = { CustomItemList.StabilisationFieldGeneratorTier0,
            CustomItemList.StabilisationFieldGeneratorTier1, CustomItemList.StabilisationFieldGeneratorTier2,
            CustomItemList.StabilisationFieldGeneratorTier3, CustomItemList.StabilisationFieldGeneratorTier4,
            CustomItemList.StabilisationFieldGeneratorTier5, CustomItemList.StabilisationFieldGeneratorTier6,
            CustomItemList.StabilisationFieldGeneratorTier7, CustomItemList.StabilisationFieldGeneratorTier8 };
        // Sensor arrays follow the field-manipulator tiering, but one tier down, with their own quantity banding.
        // This is mainly for achieving cost parity with the pre-BEC recipes.
        int[] sensorTier = { 1, 1, 2, 2, 2, 3, 3, 3, 4 };
        int[] sensorQty = { 1, 1, 1, 1, 4, 1, 1, 4, 1 };
        int[] manipulatorQty = { 1, 2, 8 };

        for (int t = 0; t < 9; t++) {
            int tp1 = t + 1;
            ItemStack[] inputs = { timeGens[t].get(1), spaceGens[t].get(1),
                CustomItemList.EOH_Infinite_Energy_Casing.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UXV, tp1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.SuperconductorUHVBase, 4L * tp1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.SuperconductorUEVBase, 4L * tp1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.SuperconductorUIVBase, 4L * tp1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.SuperconductorUMVBase, 4L * tp1),
                ItemList.Superconducting_Magnet_Solenoid_UHV.get(2L * tp1),
                ItemList.Superconducting_Magnet_Solenoid_UEV.get(2L * tp1),
                ItemList.Superconducting_Magnet_Solenoid_UIV.get(2L * tp1),
                ItemList.Superconducting_Magnet_Solenoid_UMV.get(2L * tp1),
                sensorArray[sensorTier[t] - 1].get(sensorQty[t]),
                fieldManipulator[BOLT_TIER[t] - 1].get(manipulatorQty[BOLT_POS[t]]),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.Hexanite, 4L * tp1),
                GTOreDictUnificator.get(OrePrefixes.stickLong, TIER_MATS[t], 4L * tp1) };
            NaniteTier[] nanites = nanitesShifted(BOLT_TIER[t] - 1, 3, 3, 3, 2, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 1, 1);
            FluidStack[] condensates = { cosmicSolder(t), CondensateType.Time.getEntangled(10 * tp1 * INGOTS),
                CondensateType.Space.getEntangled(10 * tp1 * INGOTS),
                CondensateType.SpaceTime.getEntangled(10 * INGOTS) };
            addBec(outputs[t].get(1), inputs, nanites, condensates, EOH_TIMES[t], TierEU.RECIPE_UMV);
        }
    }

    private void addBec(ItemStack output, ItemStack[] inputs, NaniteTier[] nanites, FluidStack[] condensates,
        int duration, long eut) {}

    /// Cosmic solder condensate: transcendent metal at tier 0, boundless cosmic solder thereafter.
    private static FluidStack cosmicSolder(int tier) {
        return tier == 0 ? CondensateType.TranscendentMetal.getEntangled(69 * INGOTS)
            : CondensateType.BoundlessCosmicSolder.getEntangled(10_000 * tier);
    }

    private static NaniteTier[] nanites(int... tiers) {
        return nanitesShifted(0, tiers);
    }

    private static NaniteTier[] nanitesShifted(int shift, int... tiers) {
        NaniteTier[] result = new NaniteTier[tiers.length];
        for (int i = 0; i < tiers.length; i++) {
            result[i] = TIER_TO_NANITE[tiers[i] - 1 + shift];
        }
        return result;
    }
}
