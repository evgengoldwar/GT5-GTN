package gregtech.loaders.preload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.FluidState.GAS;
import static gregtech.api.enums.FluidState.LIQUID;
import static gregtech.api.enums.FluidState.MOLTEN;
import static gregtech.api.enums.FluidState.PLASMA;
import static gregtech.api.enums.FluidState.SLURRY;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TwilightForest;
import static gregtech.api.recipe.RecipeMaps.maceratorRecipes;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.Locale;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import codechicken.nei.api.API;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.fluid.GTFluidFactory;
import gregtech.api.items.BlockLongDistancePipe;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GTDataUtils;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings10;
import gregtech.common.blocks.BlockCasings11;
import gregtech.common.blocks.BlockCasings12;
import gregtech.common.blocks.BlockCasings13;
import gregtech.common.blocks.BlockCasings14;
import gregtech.common.blocks.BlockCasings2;
import gregtech.common.blocks.BlockCasings3;
import gregtech.common.blocks.BlockCasings4;
import gregtech.common.blocks.BlockCasings5;
import gregtech.common.blocks.BlockCasings6;
import gregtech.common.blocks.BlockCasings8;
import gregtech.common.blocks.BlockCasings9;
import gregtech.common.blocks.BlockCasingsBEC;
import gregtech.common.blocks.BlockCasingsNH;
import gregtech.common.blocks.BlockCyclotronCoils;
import gregtech.common.blocks.BlockFrameBox;
import gregtech.common.blocks.BlockGlass1;
import gregtech.common.blocks.BlockMachines;
import gregtech.common.blocks.BlockMetal;
import gregtech.common.blocks.BlockReinforced;
import gregtech.common.blocks.BlockSheetMetal;
import gregtech.common.blocks.BlockTintedIndustrialGlass;
import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.items.ItemIntegratedCircuit;
import gregtech.common.items.MetaGeneratedItem01;
import gregtech.common.items.MetaGeneratedItem02;
import gregtech.common.items.MetaGeneratedItem03;
import gregtech.common.items.MetaGeneratedItem98;
import gregtech.common.items.MetaGeneratedItem99;
import gregtech.common.items.MetaGeneratedTool01;

public class LoaderGTBlockFluid implements Runnable {

    @Override
    public void run() {
        Materials.Water.mFluid = FluidRegistry.getFluid("water");
        Materials.Ice.mFluid = FluidRegistry.getFluid("water");
        Materials.Lava.mFluid = FluidRegistry.getFluid("lava");

        GT_FML_LOGGER.debug("GTMod: Register Items.");

        new ItemIntegratedCircuit();

        new MetaGeneratedItem01();
        new MetaGeneratedItem02();
        new MetaGeneratedItem03();
        // GT_MetaGenerated_Item_98 is initialized in GTProxy.onPostInitialization()
        // because we need to wait for fluids to be registered.
        // Pre-initialization needs to happen before then, though, because the cell icons get deleted at some point
        // between load and post-load.
        MetaGeneratedItem98.preInit();
        new MetaGeneratedItem99();
        new MetaGeneratedTool01();
        new ItemFluidDisplay();

        // Tiered recipe materials actually appear to be set in MTEBasicMachineWithRecipe, making these
        // unused
        ItemList.Rotor_LV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1L));
        ItemList.Rotor_MV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L));
        ItemList.Rotor_HV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L));
        ItemList.Rotor_EV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1L));
        ItemList.Rotor_IV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L));

        GT_FML_LOGGER.debug("GTMod: Adding Blocks.");
        GregTechAPI.sBlockMachines = new BlockMachines();
        GregTechAPI.sBlockCasings1 = new BlockCasings1();
        GregTechAPI.sBlockCasings2 = new BlockCasings2();
        GregTechAPI.sBlockCasings3 = new BlockCasings3();
        GregTechAPI.sBlockCasings4 = new BlockCasings4();
        GregTechAPI.sBlockCasings5 = new BlockCasings5();
        GregTechAPI.sBlockCasings6 = new BlockCasings6();
        GregTechAPI.sBlockCasings8 = new BlockCasings8();
        GregTechAPI.sBlockCasings9 = new BlockCasings9();
        GregTechAPI.sBlockCasings10 = new BlockCasings10();
        GregTechAPI.sBlockCasings11 = new BlockCasings11();
        GregTechAPI.sBlockCasings12 = new BlockCasings12();
        GregTechAPI.sBlockCasings13 = new BlockCasings13();
        GregTechAPI.sBlockCasings14 = new BlockCasings14();
        GregTechAPI.sBlockCasingsNH = new BlockCasingsNH();
        GregTechAPI.sBlockCasingsBEC = new BlockCasingsBEC();
        GregTechAPI.sBlockLongDistancePipes = new BlockLongDistancePipe();

        GregTechAPI.sBlockFrames = new BlockFrameBox();
        GregTechAPI.sBlockGlass1 = new BlockGlass1();
        GregTechAPI.sBlockTintedGlass = new BlockTintedIndustrialGlass();
        // meta ID order, DO NOT CHANGE ORDER

        GregTechAPI.sBlockMetal1 = new BlockMetal(
            "gt.blockmetal1",
            new Materials[] { Materials.Adamantium, Materials.Aluminium, Materials.Americium, Materials.AnnealedCopper,
                Materials.Antimony, Materials.Arsenic, Materials.AstralSilver, Materials.BatteryAlloy,
                Materials.Beryllium, Materials.Bismuth, Materials.BismuthBronze, Materials.BlackBronze,
                Materials.BlackSteel, Materials.BlueAlloy, Materials.BlueSteel, Materials.Brass },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS1);

        GregTechAPI.sBlockMetal2 = new BlockMetal(
            "gt.blockmetal2",
            new Materials[] { Materials.Bronze, Materials.Caesium, Materials.Cerium, Materials.Chrome,
                Materials.ChromiumDioxide, Materials.Cobalt, Materials.CobaltBrass, Materials.Copper,
                Materials.Cupronickel, Materials.DamascusSteel, Materials.DarkIron, Materials.DeepIron, Materials.Desh,
                Materials.Duranium, Materials.Dysprosium, Materials.Electrum },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS2);

        GregTechAPI.sBlockMetal3 = new BlockMetal(
            "gt.blockmetal3",
            new Materials[] { Materials.ElectrumFlux, Materials.Enderium, Materials.Erbium, Materials.Europium,
                Materials.FierySteel, Materials.Gadolinium, Materials.Gallium, Materials.Holmium, Materials.HSLA,
                Materials.Indium, Materials.InfusedGold, Materials.Invar, Materials.Iridium, Materials.IronMagnetic,
                Materials.IronWood, Materials.Kanthal },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS3);

        GregTechAPI.sBlockMetal4 = new BlockMetal(
            "gt.blockmetal4",
            new Materials[] { Materials.Knightmetal, Materials.Lanthanum, Materials.Lead, Materials.Lutetium,
                Materials.Magnalium, Materials.Magnesium, Materials.Manganese, Materials.MeteoricIron,
                Materials.MeteoricSteel, Materials.Trinium, Materials.Mithril, Materials.Molybdenum, Materials.Naquadah,
                Materials.NaquadahAlloy, Materials.NaquadahEnriched, Materials.Naquadria },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS4);

        GregTechAPI.sBlockMetal5 = new BlockMetal(
            "gt.blockmetal5",
            new Materials[] { Materials.Neodymium, Materials.NeodymiumMagnetic, Materials.Neutronium,
                Materials.Nichrome, Materials.Nickel, Materials.Niobium, Materials.NiobiumNitride,
                Materials.NiobiumTitanium, Materials.Osmiridium, Materials.Osmium, Materials.Palladium,
                Materials.PigIron, Materials.Platinum, Materials.Plutonium, Materials.Plutonium241,
                Materials.Praseodymium },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS5);

        GregTechAPI.sBlockMetal6 = new BlockMetal(
            "gt.blockmetal6",
            new Materials[] { Materials.Promethium, Materials.RedAlloy, Materials.RedSteel, Materials.RoseGold,
                Materials.Rubidium, Materials.Samarium, Materials.Scandium, Materials.ShadowIron, Materials.ShadowSteel,
                Materials.Silicon, Materials.Silver, Materials.SolderingAlloy, Materials.StainlessSteel,
                Materials.Steel, Materials.SteelMagnetic, Materials.SterlingSilver },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS6);

        GregTechAPI.sBlockMetal7 = new BlockMetal(
            "gt.blockmetal7",
            new Materials[] { Materials.Sunnarium, Materials.Tantalum, Materials.Tellurium, Materials.Terbium,
                Materials.Thaumium, Materials.Thorium, Materials.Thulium, Materials.Tin, Materials.TinAlloy,
                Materials.Titanium, Materials.Tritanium, Materials.Tungsten, Materials.TungstenSteel, Materials.Ultimet,
                Materials.Uranium, Materials.Uranium235 },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS7);

        GregTechAPI.sBlockMetal8 = new BlockMetal(
            "gt.blockmetal8",
            new Materials[] { Materials.Vanadium, Materials.VanadiumGallium, Materials.CastIron, Materials.Ytterbium,
                Materials.Yttrium, Materials.YttriumBariumCuprate, Materials.Zinc, Materials.TungstenCarbide,
                Materials.VanadiumSteel, Materials.HSSG, Materials.HSSE, Materials.HSSS, Materials.Steeleaf,
                Materials.Ichorium, Materials.Firestone, Materials.Shadow },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS8);

        GregTechAPI.sBlockGem1 = new BlockMetal(
            "gt.blockgem1",
            new Materials[] { Materials.InfusedAir, Materials.Amber, Materials.Amethyst, Materials.InfusedWater,
                Materials.BlueTopaz, Materials.CertusQuartz, Materials.Dilithium, Materials.EnderEye,
                Materials.EnderPearl, Materials.Spinel, Materials.Force, Materials.Forcicium, Materials.Forcillium,
                Materials.GreenSapphire, Materials.InfusedFire, Materials.Jasper },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS9);

        GregTechAPI.sBlockGem2 = new BlockMetal(
            "gt.blockgem2",
            new Materials[] { Materials.Lazurite, Materials.Lignite, Materials.Monazite, Materials.Niter,
                Materials.Olivine, Materials.Opal, Materials.InfusedOrder, Materials.InfusedEntropy,
                Materials.TricalciumPhosphate, Materials.Quartzite, Materials.GarnetRed, Materials.Ruby,
                Materials.Sapphire, Materials.Sodalite, Materials.Tanzanite, Materials.InfusedEarth },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS10);

        GregTechAPI.sBlockGem3 = new BlockMetal(
            "gt.blockgem3",
            new Materials[] { Materials.Topaz, Materials.Vinteum, Materials.GarnetYellow, Materials.NetherStar,
                Materials.Charcoal, Materials.Blaze },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS11);

        GregTechAPI.sBlockMetal9 = new BlockMetal(
            "gt.blockmetal9",
            new Materials[] { Materials.Cryolite, Materials.SiliconSG, Materials.NickelAluminide, Materials.SpaceTime,
                Materials.TranscendentMetal, Materials.Oriharukon, Materials.WhiteDwarfMatter,
                Materials.BlackDwarfMatter, Materials.Universium, Materials.Eternity, Materials.MagMatter,
                Materials.SixPhasedCopper, Materials.HellishMetal, Materials.MHDCSM, Materials.Hexanite },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS12);

        GregTechAPI.sBlockMetal10 = new BlockMetal(
            "gt.blockmetal10",
            new Materials[] { Materials.Shijima, Materials.Churitsu },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS13);

        GregTechAPI.sBlockReinforced = new BlockReinforced("gt.blockreinforced");

        GregTechAPI.sBlockSheetmetalGT = new BlockSheetMetal(
            "gt.sheetmetal",
            meta -> GTDataUtils.getIndexSafe(GregTechAPI.sGeneratedMaterials, meta),
            1000);

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

        GT_FML_LOGGER.debug("GTMod: Registering the Ore TileEntity.");

        GT_FML_LOGGER.debug("GTMod: Registering Fluids.");
        Materials.ConstructionFoam.mFluid = GTUtility.getFluidForFilledItem(GTModHandler.getIC2Item("CFCell", 1L), true)
            .getFluid();
        Materials.UUMatter.mFluid = GTUtility.getFluidForFilledItem(GTModHandler.getIC2Item("uuMatterCell", 1L), true)
            .getFluid();

        GTFluidFactory.builder("Air")
            .withDefaultLocalName("Air")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Air)
            .addLocalizedName(Materials.Air)
            .registerContainers(ItemList.Cell_Air.get(1L), ItemList.Cell_Empty.get(1L), 2000);
        GTFluidFactory.builder("LiquidOxygen")
            .withDefaultLocalName("Liquid Oxygen")
            .withStateAndTemperature(GAS, 60)
            .buildAndRegister()
            .configureMaterials(Materials.LiquidOxygen)
            .addLocalizedName(Materials.LiquidOxygen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LiquidOxygen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("LiquidNitrogen")
            .withDefaultLocalName("Liquid Nitrogen")
            .withStateAndTemperature(GAS, 77)
            .buildAndRegister()
            .configureMaterials(Materials.LiquidNitrogen)
            .addLocalizedName(Materials.LiquidNitrogen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LiquidNitrogen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("LiquidAir")
            .withDefaultLocalName("Liquid Air")
            .withStateAndTemperature(LIQUID, 77)
            .buildAndRegister()
            .configureMaterials(Materials.LiquidAir)
            .addLocalizedName(Materials.LiquidAir)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LiquidAir, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Oxygen")
            .withDefaultLocalName("Oxygen")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Oxygen)
            .addLocalizedName(Materials.Oxygen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Hydrogen")
            .withDefaultLocalName("Hydrogen")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Hydrogen)
            .addLocalizedName(Materials.Hydrogen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Deuterium")
            .withDefaultLocalName("Deuterium")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Deuterium)
            .addLocalizedName(Materials.Deuterium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Deuterium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Tritium")
            .withDefaultLocalName("Tritium")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Tritium)
            .addLocalizedName(Materials.Tritium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Tritium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Helium")
            .withDefaultLocalName("Helium")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Helium)
            .addLocalizedName(Materials.Helium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Helium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Argon")
            .withDefaultLocalName("Argon")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Argon)
            .addLocalizedName(Materials.Argon)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Argon, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Radon")
            .withDefaultLocalName("Radon")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Radon)
            .addLocalizedName(Materials.Radon)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Radon, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("Fluorine")
            .withDefaultLocalName("Fluorine")
            .withStateAndTemperature(GAS, 53)
            .buildAndRegister()
            .configureMaterials(Materials.Fluorine)
            .addLocalizedName(Materials.Fluorine)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Fluorine, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Titaniumtetrachloride")
            .withDefaultLocalName("Titaniumtetrachloride")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Titaniumtetrachloride)
            .addLocalizedName(Materials.Titaniumtetrachloride)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Titaniumtetrachloride, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Helium-3")
            .withDefaultLocalName("Helium-3")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Helium3)
            .addLocalizedName(Materials.Helium3)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Helium3, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Methane")
            .withTextureName("methane")
            .withDefaultLocalName("Methane")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Methane)
            .addLocalizedName(Materials.Methane)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Methane, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Nitrogen")
            .withDefaultLocalName("Nitrogen")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Nitrogen)
            .addLocalizedName(Materials.Nitrogen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Nitrogen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("NitrogenDioxide")
            .withDefaultLocalName("Nitrogen Dioxide")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NitrogenDioxide)
            .addLocalizedName(Materials.NitrogenDioxide)
            .addLocalizedName(Materials.NitrogenDioxide)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NitrogenDioxide, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Steam")
            .withDefaultLocalName("Steam")
            .withStateAndTemperature(GAS, 375)
            .buildAndRegister()
            .configureMaterials(Materials.Water)
            .addLocalizedName(Materials.Water)
            .registerBContainers(GTModHandler.getIC2Item("steamCell", 1), Materials.Empty.getCells(1));

        Materials.Ice.mGas = Materials.Water.mGas;
        Materials.Water.mGas.setTemperature(375)
            .setGaseous(true);

        ItemList.sOilExtraHeavy = GTFluidFactory.builder("liquid_extra_heavy_oil")
            .withDefaultLocalName("Very Heavy Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilExtraHeavy)
            .addLocalizedName(Materials.OilExtraHeavy)
            .registerBContainers(Materials.OilExtraHeavy.getCells(1), Materials.Empty.getCells(1))
            .asFluid();
        ItemList.sEpichlorhydrin = GTFluidFactory.builder("liquid_epichlorhydrin")
            .withDefaultLocalName("Epichlorohydrin")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Epichlorohydrin)
            .addLocalizedName(Materials.Epichlorohydrin)
            .registerBContainers(Materials.Epichlorohydrin.getCells(1), Materials.Empty.getCells(1))
            .asFluid();
        ItemList.sDrillingFluid = GTFluidFactory.of("liquid_drillingfluid", "Drilling Fluid", LIQUID, 295);
        ItemList.sToluene = GTFluidFactory.builder("liquid_toluene")
            .withDefaultLocalName("Toluene")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Toluene)
            .addLocalizedName(Materials.Toluene)
            .registerBContainers(Materials.Toluene.getCells(1), Materials.Empty.getCells(1))
            .asFluid();
        ItemList.sNitrationMixture = GTFluidFactory.builder("liquid_nitrationmixture")
            .withDefaultLocalName("Nitration Mixture")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NitrationMixture)
            .addLocalizedName(Materials.NitrationMixture)
            .registerBContainers(Materials.NitrationMixture.getCells(1), Materials.Empty.getCells(1))
            .asFluid();

        GTFluidFactory.builder("liquid_heavy_oil")
            .withDefaultLocalName("Heavy Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilHeavy)
            .addLocalizedName(Materials.OilHeavy)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.OilHeavy, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_medium_oil")
            .withDefaultLocalName("Raw Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilMedium)
            .addLocalizedName(Materials.OilMedium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.OilMedium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_light_oil")
            .withDefaultLocalName("Light Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilLight)
            .addLocalizedName(Materials.OilLight)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.OilLight, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("gas_natural_gas")
            .withDefaultLocalName("Natural Gas")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NaturalGas)
            .addLocalizedName(Materials.NaturalGas)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NaturalGas, 1L),
                ItemList.Cell_Empty.get(1L));
        ItemList.sHydricSulfur = GTFluidFactory.builder("liquid_hydricsulfur")
            .withDefaultLocalName("Hydrogen Sulfide")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.HydricSulfide)
            .addLocalizedName(Materials.HydricSulfide)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1L),
                ItemList.Cell_Empty.get(1L))
            .asFluid();
        GTFluidFactory.builder("gas_sulfuricgas")
            .withDefaultLocalName("Sulfuric Gas")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricGas)
            .addLocalizedName(Materials.SulfuricGas)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricGas, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("gas_gas")
            .withDefaultLocalName("Refinery Gas")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Gas)
            .addLocalizedName(Materials.Gas)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Gas, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_sulfuricnaphtha")
            .withDefaultLocalName("Sulfuric Naphtha")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricNaphtha)
            .addLocalizedName(Materials.SulfuricNaphtha)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricNaphtha, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_sufluriclight_fuel")
            .withDefaultLocalName("Sulfuric Light Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricLightFuel)
            .addLocalizedName(Materials.SulfuricLightFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricLightFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_sulfuricheavy_fuel")
            .withDefaultLocalName("Sulfuric Heavy Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricHeavyFuel)
            .addLocalizedName(Materials.SulfuricHeavyFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricHeavyFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_naphtha")
            .withDefaultLocalName("Naphtha")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Naphtha)
            .addLocalizedName(Materials.Naphtha)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Naphtha, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_light_fuel")
            .withDefaultLocalName("Light Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.LightFuel)
            .addLocalizedName(Materials.LightFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LightFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_heavy_fuel")
            .withDefaultLocalName("Heavy Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.HeavyFuel)
            .addLocalizedName(Materials.HeavyFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HeavyFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_lpg")
            .withDefaultLocalName("LPG")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.LPG)
            .addLocalizedName(Materials.LPG)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LPG, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("charcoal_byproducts")
            .withTextureName("molten.autogenerated")
            .withDefaultLocalName("Charcoal Byproducts")
            .withColorRGBA(Materials.CharcoalByproducts.mRGBa)
            .withStateAndTemperature(GAS, 775)
            .buildAndRegister()
            .configureMaterials(Materials.CharcoalByproducts)
            .addLocalizedName(Materials.CharcoalByproducts)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.CharcoalByproducts, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("molten.bisphenol_a")
            .withTextureName("molten.autogenerated")
            .withDefaultLocalName("Molten Bisphenol A")
            .withColorRGBA(Materials.BisphenolA.mRGBa)
            .withStateAndTemperature(LIQUID, 432)
            .buildAndRegister()
            .configureMaterials(Materials.BisphenolA)
            .addLocalizedName(Materials.BisphenolA)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.BisphenolA, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("UUAmplifier")
            .withDefaultLocalName("UU Amplifier")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.UUAmplifier)
            .addLocalizedName(Materials.UUAmplifier)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.UUAmplifier, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Chlorine")
            .withDefaultLocalName("Chlorine")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Chlorine)
            .addLocalizedName(Materials.Chlorine)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Chlorine, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Mercury")
            .withDefaultLocalName("Mercury")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Mercury)
            .addLocalizedName(Materials.Mercury)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Mercury, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("NitroFuel")
            .withDefaultLocalName("Cetane-Boosted Diesel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NitroFuel)
            .addLocalizedName(Materials.NitroFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NitroFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("SodiumPersulfate")
            .withDefaultLocalName("Sodium Persulfate")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SodiumPersulfate)
            .addLocalizedName(Materials.SodiumPersulfate)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SodiumPersulfate, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Glyceryl")
            .withDefaultLocalName("Glyceryl Trinitrate")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Glyceryl)
            .addLocalizedName(Materials.Glyceryl)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Glyceryl, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("lubricant")
            .withDefaultLocalName("Lubricant")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Lubricant)
            .addLocalizedName(Materials.Lubricant)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Lubricant, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("creosote")
            .withDefaultLocalName("Creosote Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Creosote)
            .addLocalizedName(Materials.Creosote)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Creosote, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("seedoil")
            .withDefaultLocalName("Seed Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SeedOil)
            .addLocalizedName(Materials.SeedOil)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SeedOil, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("fishoil")
            .withDefaultLocalName("Fish Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.FishOil)
            .addLocalizedName(Materials.FishOil)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.FishOil, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("oil")
            .withDefaultLocalName("Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Oil)
            .addLocalizedName(Materials.Oil)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oil, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("fuel")
            .withDefaultLocalName("Diesel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Diesel)
            .addLocalizedName(Materials.Diesel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Diesel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("for.honey")
            .withDefaultLocalName("Honey")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Honey)
            .addLocalizedName(Materials.Honey)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Honey, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("biomass")
            .withDefaultLocalName("Biomass")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Biomass)
            .addLocalizedName(Materials.Biomass)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Biomass, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("bioethanol")
            .withDefaultLocalName("Ethanol")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Ethanol)
            .addLocalizedName(Materials.Ethanol)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Ethanol, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("sulfuricacid")
            .withDefaultLocalName("Sulfuric Acid")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricAcid)
            .addLocalizedName(Materials.SulfuricAcid)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricAcid, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("milk")
            .withDefaultLocalName("Milk")
            .withStateAndTemperature(LIQUID, 290)
            .buildAndRegister()
            .configureMaterials(Materials.Milk)
            .addLocalizedName(Materials.Milk)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Milk, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("refinedGlue")
            .withDefaultLocalName("Refined Glue")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Glue)
            .addLocalizedName(Materials.Glue)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Glue, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("hotfryingoil")
            .withDefaultLocalName("Hot Frying Oil")
            .withStateAndTemperature(LIQUID, 400)
            .buildAndRegister()
            .configureMaterials(Materials.FryingOilHot)
            .addLocalizedName(Materials.FryingOilHot)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.FryingOilHot, 1L),
                ItemList.Cell_Empty.get(1L));

        // region NewHorizonsCoreMod fluids
        // These fluids have to get registered manually so they use the MetaGeneratedItem98 cells instead of generating
        // new MetaGeneratedItem01 fluid cells.
        GTFluidFactory.builder("SodiumPotassium")
            .withDefaultLocalName("Sodium Potassium")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SodiumPotassium)
            .addLocalizedName(Materials.SodiumPotassium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SodiumPotassium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Pollution")
            .withDefaultLocalName("Pollution")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Pollution)
            .addLocalizedName(Materials.Pollution)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Pollution, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("EnrichedBacterialSludge")
            .withDefaultLocalName("Enriched Bacterial Sludge")
            .withStateAndTemperature(LIQUID, 300)
            .buildAndRegister()
            .configureMaterials(Materials.EnrichedBacterialSludge)
            .addLocalizedName(Materials.EnrichedBacterialSludge)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.EnrichedBacterialSludge, 1L),
                ItemList.Cell_Empty.get(1L))
            .asFluid()
            .setLuminosity(15);
        GTFluidFactory.builder("FermentedBacterialSludge")
            .withDefaultLocalName("Fermented Bacterial Sludge")
            .withStateAndTemperature(LIQUID, 300)
            .buildAndRegister()
            .configureMaterials(Materials.FermentedBacterialSludge)
            .addLocalizedName(Materials.FermentedBacterialSludge)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.FermentedBacterialSludge, 1L),
                ItemList.Cell_Empty.get(1L))
            .asFluid()
            .setLuminosity(8);
        // endregion

        GTFluidFactory.builder("DimensionallyTranscendentResidue")
            .withDefaultLocalName("Dimensionally Transcendent Residue")
            .withStateAndTemperature(LIQUID, 2000000000)
            .buildAndRegister()
            .configureMaterials(Materials.DTR)
            .addLocalizedName(Materials.DTR)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.DTR, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTCC")
            .withDefaultLocalName("Excited Dimensionally Transcendent Crude Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(Materials.ExcitedDTCC)
            .addLocalizedName(Materials.ExcitedDTCC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ExcitedDTCC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTPC")
            .withDefaultLocalName("Excited Dimensionally Transcendent Prosaic Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(Materials.ExcitedDTPC)
            .addLocalizedName(Materials.ExcitedDTPC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ExcitedDTPC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTRC")
            .withDefaultLocalName("Excited Dimensionally Transcendent Resplendent Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(Materials.ExcitedDTRC)
            .addLocalizedName(Materials.ExcitedDTRC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ExcitedDTRC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTEC")
            .withDefaultLocalName("Excited Dimensionally Transcendent Exotic Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(Materials.ExcitedDTEC)
            .addLocalizedName(Materials.ExcitedDTEC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ExcitedDTEC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTSC")
            .withDefaultLocalName("Excited Dimensionally Transcendent Stellar Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(Materials.ExcitedDTSC)
            .addLocalizedName(Materials.ExcitedDTSC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ExcitedDTSC, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(Materials.RawStarMatter.mName)
            .withDefaultLocalName(Materials.RawStarMatter.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 10_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.RawStarMatter)
            .addLocalizedName(Materials.RawStarMatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.RawStarMatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(Materials.Space.mName)
            .withDefaultLocalName(Materials.Space.mDefaultLocalName)
            .withStateAndTemperature(MOLTEN, 0)
            .buildAndRegister()
            .configureMaterials(Materials.Space)
            .addLocalizedName(Materials.Space)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Space, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(Materials.Time.mName)
            .withDefaultLocalName(Materials.Time.mDefaultLocalName)
            .withStateAndTemperature(MOLTEN, 0)
            .buildAndRegister()
            .configureMaterials(Materials.Time)
            .addLocalizedName(Materials.Time)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Time, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("PrimordialMatter")
            .withDefaultLocalName(Materials.PrimordialMatter.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 2_000_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.PrimordialMatter)
            .addLocalizedName(Materials.PrimordialMatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.PrimordialMatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("QuarkGluonPlasma")
            .withDefaultLocalName(Materials.QuarkGluonPlasma.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 2_000_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.QuarkGluonPlasma)
            .addLocalizedName(Materials.QuarkGluonPlasma)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.QuarkGluonPlasma, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("PhononMedium")
            .withDefaultLocalName(Materials.PhononMedium.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 500)
            .buildAndRegister()
            .configureMaterials(Materials.PhononMedium)
            .addLocalizedName(Materials.PhononMedium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.PhononMedium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("PhononCrystalSolution")
            .withDefaultLocalName(Materials.PhononCrystalSolution.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 500)
            .buildAndRegister()
            .configureMaterials(Materials.PhononCrystalSolution)
            .addLocalizedName(Materials.PhononCrystalSolution)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.PhononCrystalSolution, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("antimatter")
            .withDefaultLocalName(Materials.Antimatter.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 1000000)
            .buildAndRegister()
            .configureMaterials(Materials.Antimatter)
            .addLocalizedName(Materials.Antimatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Antimatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("protomatter")
            .withDefaultLocalName(Materials.Protomatter.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 1)
            .buildAndRegister()
            .configureMaterials(Materials.Protomatter)
            .addLocalizedName(Materials.Protomatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Protomatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.infinity")
            .withDefaultLocalName("Infinity Plasma")
            .withStateAndTemperature(PLASMA, 10000)
            .buildAndRegister()
            .configureMaterials(Materials.Infinity)
            .addLocalizedName(Materials.Infinity)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Infinity, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.bedrockium")
            .withDefaultLocalName("Bedrockium Plasma")
            .withStateAndTemperature(PLASMA, 10000)
            .buildAndRegister()
            .configureMaterials(Materials.Bedrockium)
            .addLocalizedName(Materials.Bedrockium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Bedrockium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.cosmicneutronium")
            .withDefaultLocalName("Cosmic Neutronium Plasma")
            .withStateAndTemperature(PLASMA, 10000)
            .buildAndRegister()
            .configureMaterials(Materials.CosmicNeutronium)
            .addLocalizedName(Materials.CosmicNeutronium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.CosmicNeutronium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("molten.cosmicneutronium")
            .withDefaultLocalName("Molten Cosmic Neutronium")
            .withStateAndTemperature(MOLTEN, 9900)
            .buildAndRegister()
            .configureMaterials(Materials.CosmicNeutronium)
            .addLocalizedName(Materials.CosmicNeutronium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.CosmicNeutronium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.ichorium")
            .withDefaultLocalName("Ichorium Plasma")
            .withStateAndTemperature(PLASMA, 9000)
            .buildAndRegister()
            .configureMaterials(Materials.Ichorium)
            .addLocalizedName(Materials.Ichorium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Ichorium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("molten.ichorium")
            .withDefaultLocalName("Molten Ichorium")
            .withStateAndTemperature(MOLTEN, 9000)
            .buildAndRegister()
            .configureMaterials(Materials.Ichorium)
            .addLocalizedName(Materials.Ichorium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Ichorium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("InactiveCosmicSolder")
            .withDefaultLocalName(Materials.InactiveCosmicSolder.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 1_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.InactiveCosmicSolder)
            .addLocalizedName(Materials.InactiveCosmicSolder)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.InactiveCosmicSolder, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("BoundlessCosmicSolder")
            .withDefaultLocalName(Materials.BoundlessCosmicSolder.mDefaultLocalName)
            .withStateAndTemperature(LIQUID, 1_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.BoundlessCosmicSolder)
            .addLocalizedName(Materials.BoundlessCosmicSolder)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.BoundlessCosmicSolder, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("ComputationBase")
            .withDefaultLocalName(Materials.ComputationBase.mDefaultLocalName)
            .withStateAndTemperature(MOLTEN, 100_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.ComputationBase)
            .addLocalizedName(Materials.ComputationBase)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ComputationBase, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("fieryblood")
            .withDefaultLocalName("Fiery Blood")
            .withStateAndTemperature(LIQUID, 6400)
            .buildAndRegister()
            .configureMaterials(Materials.FierySteel)
            .addLocalizedName(Materials.FierySteel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.FierySteel, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("holywater")
            .withDefaultLocalName("Holy Water")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.HolyWater)
            .addLocalizedName(Materials.HolyWater)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HolyWater, 1L),
                ItemList.Cell_Empty.get(1L));

        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.Diesel.getFluid(1_000),
                ItemList.Tool_Lighter_Platinum_Full.get(1L),
                ItemList.Tool_Lighter_Platinum_Empty.get(1L)));

        Dyes.dyeBlack.addFluidDye(GTFluidFactory.of("squidink", "Squid Ink", LIQUID, 295));
        Dyes.dyeBlue.addFluidDye(GTFluidFactory.of("indigo", "Indigo Dye", LIQUID, 295));
        for (byte i = 0; i < Dyes.VALUES.length; i = (byte) (i + 1)) {
            Dyes tDye = Dyes.VALUES[i];
            tDye.addFluidDye(
                GTFluidFactory.builder(
                    "dye.watermixed." + tDye.name()
                        .toLowerCase(Locale.ENGLISH))
                    .withTextureName("dyes")
                    .withDefaultLocalName("Water Mixed " + tDye.mName + " Dye")
                    .withColorRGBA(tDye.getRGBA())
                    .withStateAndTemperature(LIQUID, 295)
                    .buildAndRegister()
                    .addLocalizedName()
                    .asFluid());
            tDye.addFluidDye(
                GTFluidFactory.builder(
                    "dye.chemical." + tDye.name()
                        .toLowerCase(Locale.ENGLISH))
                    .withTextureName("dyes")
                    .withDefaultLocalName("Chemical " + tDye.mName + " Dye")
                    .withColorRGBA(tDye.getRGBA())
                    .withStateAndTemperature(LIQUID, 295)
                    .buildAndRegister()
                    .addLocalizedName()
                    .registerContainers(ItemList.SPRAY_CAN_DYES[i].get(1L), ItemList.Spray_Empty.get(1L), 16 * INGOTS)
                    .asFluid());
        }
        GTFluidFactory.builder("ice")
            .withDefaultLocalName("Crushed Ice")
            .withStateAndTemperature(SLURRY, 270)
            .buildAndRegister()
            .configureMaterials(Materials.Ice)
            .addLocalizedName(Materials.Ice)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Ice, 1L),
                ItemList.Cell_Empty.get(1L));
        Materials.Water.mSolid = Materials.Ice.mSolid;

        GTFluidFactory.builder("molten.glass")
            .withDefaultLocalName("Molten Glass")
            .withStateAndTemperature(MOLTEN, 1500)
            .buildAndRegister()
            .configureMaterials(Materials.Glass)
            .addLocalizedName(Materials.Glass)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Glass, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("molten.redstone")
            .withDefaultLocalName("Molten Redstone")
            .withStateAndTemperature(MOLTEN, 500)
            .buildAndRegister()
            .configureMaterials(Materials.Redstone)
            .addLocalizedName(Materials.Redstone)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Redstone, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("molten.blaze")
            .withDefaultLocalName("Molten Blaze")
            .withStateAndTemperature(MOLTEN, 6400)
            .buildAndRegister()
            .configureMaterials(Materials.Blaze)
            .addLocalizedName(Materials.Blaze)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Blaze, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("wet.concrete")
            .withDefaultLocalName("Wet Concrete")
            .withStateAndTemperature(MOLTEN, 300)
            .buildAndRegister()
            .configureMaterials(Materials.Concrete)
            .addLocalizedName(Materials.Concrete)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Concrete, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("molten.granitered")
            .withDefaultLocalName("Molten Red Granite")
            .withTextureName("molten.autogenerated")
            .withColorRGBA(Materials.GraniteRed.mRGBa)
            .withStateAndTemperature(MOLTEN, 1520)
            .buildAndRegister()
            .configureMaterials(Materials.GraniteRed)
            .addLocalizedName(Materials.GraniteRed)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.GraniteRed, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("molten.graniteblack")
            .withTextureName("molten.autogenerated")
            .withDefaultLocalName("Molten Black Granite")
            .withColorRGBA(Materials.GraniteBlack.mRGBa)
            .withStateAndTemperature(MOLTEN, 1520)
            .buildAndRegister()
            .configureMaterials(Materials.GraniteBlack)
            .addLocalizedName(Materials.GraniteBlack)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.GraniteBlack, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);

        GTFluidFactory.builder("activatednetherite")
            .withDefaultLocalName(Materials.ActivatedNetherite.mDefaultLocalName)
            .withTextureName("molten.autogenerated")
            .withColorRGBA(Materials.ActivatedNetherite.mRGBa)
            .withStateAndTemperature(MOLTEN, 50_000_000)
            .buildAndRegister()
            .configureMaterials(Materials.ActivatedNetherite)
            .addLocalizedName(Materials.ActivatedNetherite)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.ActivatedNetherite, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(Materials.CarbonDioxide.mName.toLowerCase(Locale.ENGLISH))
            .withDefaultLocalName(Materials.CarbonDioxide.mDefaultLocalName)
            .withTextureName("carbondioxide")
            .withStateAndTemperature(GAS, Materials.CarbonDioxide.getGasTemperature())
            .buildAndRegister()
            .configureMaterials(Materials.CarbonDioxide)
            .addLocalizedName(Materials.CarbonDioxide)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.CarbonDioxide, 1L),
                ItemList.Cell_Empty.get(1L));

        ResourceLocation stillTexture = new ResourceLocation(GregTech.ID, "fluids/fluid.ammonia.still");
        ResourceLocation flowTexture = new ResourceLocation(GregTech.ID, "fluids/fluid.ammonia.flow");

        GTFluidFactory.builder(Materials.Ammonia.mName.toLowerCase(Locale.ENGLISH))
            .withDefaultLocalName(Materials.Ammonia.mDefaultLocalName)
            .withTextures(stillTexture, flowTexture)
            .withStateAndTemperature(GAS, Materials.Ammonia.getGasTemperature())
            .buildAndRegister()
            .configureMaterials(Materials.Ammonia)
            .addLocalizedName(Materials.Ammonia)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Ammonia, 1L),
                ItemList.Cell_Empty.get(1L));

        for (Materials tMaterial : Materials.values()) {
            if ((tMaterial.mStandardMoltenFluid == null) && (tMaterial.contains(SubTag.SMELTING_TO_FLUID))
                && (!tMaterial.contains(SubTag.NO_SMELTING))) {
                if (!OrePrefixes.cellMolten.mNotGeneratedItems.contains(tMaterial)) {
                    GTMod.proxy.addAutogeneratedMoltenFluid(tMaterial);
                }
                if ((tMaterial.mSmeltInto != tMaterial) && (tMaterial.mSmeltInto.mStandardMoltenFluid == null)) {
                    GTMod.proxy.addAutogeneratedMoltenFluid(tMaterial.mSmeltInto);
                }
            }
            if (tMaterial.mElement != null || (tMaterial.hasPlasma() && !tMaterial.mIconSet.is_custom)) {
                GTMod.proxy.addAutogeneratedPlasmaFluid(tMaterial);
            }
            if (tMaterial.hasCorrespondingFluid()) {
                GTMod.proxy.addAutoGeneratedCorrespondingFluid(tMaterial);
            }
            if (tMaterial.hasCorrespondingGas()) {
                GTMod.proxy.addAutoGeneratedCorrespondingGas(tMaterial);
            }
            if (tMaterial.canBeCracked()) {
                GTMod.proxy.addAutoGeneratedHydroCrackedFluids(tMaterial);
                GTMod.proxy.addAutoGeneratedSteamCrackedFluids(tMaterial);
            }
        }

        if (!GTValues.D1) {
            if (Mods.NotEnoughItems.isModLoaded()) {
                GT_FML_LOGGER.debug("GTMod: Hiding certain Items from NEI.");
                API.hideItem(ItemList.Display_Fluid.getWildcard(1L));
            }
        }

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.gravel, 1, WILDCARD))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new ItemStack(Items.flint, 1))
            .outputChances(10000, 1000)
            .duration(20 * SECONDS)
            .eut(2)
            .addTo(maceratorRecipes);

        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.FierySteel,
            GTModHandler.getModItem(TwilightForest.ID, "item.fieryIngot", 1L, 0));
        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.Knightmetal,
            GTModHandler.getModItem(TwilightForest.ID, "item.knightMetal", 1L, 0));
        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.Steeleaf,
            GTModHandler.getModItem(TwilightForest.ID, "item.steeleafIngot", 1L, 0));
        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.IronWood,
            GTModHandler.getModItem(TwilightForest.ID, "item.ironwoodIngot", 1L, 0));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedAir, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 0));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedFire, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 1));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedWater, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 2));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedEarth, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 3));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedOrder, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 4));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedEntropy, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 5));
        GTOreDictUnificator
            .set(OrePrefixes.nugget, Materials.Mercury, GTModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 5));
        GTOreDictUnificator
            .set(OrePrefixes.nugget, Materials.Thaumium, GTModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 6));
        GTOreDictUnificator
            .set(OrePrefixes.ingot, Materials.Thaumium, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 2));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.Mercury, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 3));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.Amber, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 6));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.Firestone, GTModHandler.getModItem(Railcraft.ID, "firestone.raw", 1L));

        GTOreDictUnificator
            .set(OrePrefixes.nugget, Materials.Void, GTModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 7));
        GTOreDictUnificator
            .set(OrePrefixes.ingot, Materials.Void, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 16));

        // Special secondary oredict for Sodium Hydroxide
        GTOreDictUnificator.registerOre("dustSodiumHydroxide", Materials.SodiumHydroxide.getDust(1));
        GTOreDictUnificator.registerOre("dustSmallSodiumHydroxide", Materials.SodiumHydroxide.getDustSmall(1));
        GTOreDictUnificator.registerOre("dustTinySodiumHydroxide", Materials.SodiumHydroxide.getDustTiny(1));

        GTOreDictUnificator.set(
            OrePrefixes.dust,
            Materials.Cocoa,
            GTModHandler.getModItem(PamsHarvestCraft.ID, "cocoapowderItem", 1L, 0));

        GregTechAPI.sSolenoidCoilCasings = new BlockCyclotronCoils();
    }
}
