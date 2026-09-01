package gregtech.api.enums;

import static gregtech.api.enums.FluidState.GAS;
import static gregtech.api.enums.GTValues.M;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.util.GTUtility.formatStringSafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.TCAspects.TC_AspectStack;
import gregtech.api.fluid.GTFluidFactory;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.IMaterialHandler;
import gregtech.api.interfaces.IOreMaterial;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Client;
import gregtech.common.render.items.CosmicNeutroniumRenderer;
import gregtech.common.render.items.GaiaSpiritRenderer;
import gregtech.common.render.items.GeneratedMaterialRenderer;
import gregtech.common.render.items.GlitchEffectRenderer;
import gregtech.common.render.items.InfinityRenderer;
import gregtech.common.render.items.RainbowOverlayRenderer;
import gregtech.common.render.items.TranscendentMetalRenderer;
import gregtech.common.render.items.UniversiumRenderer;
import gregtech.loaders.materials.MaterialsInit;

@SuppressWarnings("unused") // API Legitimately has unused Members and Methods
public class Materials_OLD implements IColorModulationContainer, IOreMaterial {

    // region Elements
    public static Materials_OLD Aluminium;
    public static Materials_OLD Americium;
    public static Materials_OLD Antimony;
    public static Materials_OLD Argon;
    public static Materials_OLD Arsenic;
    public static Materials_OLD Barium;
    public static Materials_OLD Beryllium;
    public static Materials_OLD Bismuth;
    public static Materials_OLD Boron;
    public static Materials_OLD Caesium;
    public static Materials_OLD Calcium;
    public static Materials_OLD Carbon;
    public static Materials_OLD Cadmium;
    public static Materials_OLD Cerium;
    public static Materials_OLD Chlorine;
    public static Materials_OLD Chrome;
    public static Materials_OLD Cobalt;
    public static Materials_OLD Copper;
    public static Materials_OLD Desh; // Not a real element
    public static Materials_OLD Dysprosium;
    public static Materials_OLD Empty; // Not a real element
    public static Materials_OLD Erbium;
    public static Materials_OLD Europium;
    public static Materials_OLD Flerovium;
    public static Materials_OLD Fluorine;
    public static Materials_OLD Francium;
    public static Materials_OLD Gadolinium;
    public static Materials_OLD Gallium;
    public static Materials_OLD Gold;
    public static Materials_OLD Holmium;
    public static Materials_OLD Hydrogen;
    public static Materials_OLD Helium;
    public static Materials_OLD Indium;
    public static Materials_OLD Iridium;
    public static Materials_OLD Iron;
    public static Materials_OLD Lanthanum;
    public static Materials_OLD Lead;
    public static Materials_OLD Lithium;
    public static Materials_OLD Lutetium;
    public static Materials_OLD Magic; // Not a real element
    public static Materials_OLD Magnesium;
    public static Materials_OLD Manganese;
    public static Materials_OLD Mercury;
    public static Materials_OLD MeteoricIron; // Not a real element
    public static Materials_OLD Molybdenum;
    public static Materials_OLD Naquadah; // Not a real element
    public static Materials_OLD Neodymium;
    public static Materials_OLD Neutronium;
    public static Materials_OLD Nickel;
    public static Materials_OLD Niobium;
    public static Materials_OLD Nitrogen;
    public static Materials_OLD _NULL; // Not a real element
    public static Materials_OLD Oriharukon; // Not a real element
    public static Materials_OLD Osmium;
    public static Materials_OLD Oxygen;
    public static Materials_OLD Palladium;
    public static Materials_OLD Phosphorus;
    public static Materials_OLD Platinum;
    public static Materials_OLD Plutonium;
    public static Materials_OLD Potassium;
    public static Materials_OLD Praseodymium;
    public static Materials_OLD Promethium;
    public static Materials_OLD Radon;
    public static Materials_OLD Rubidium;
    public static Materials_OLD Samarium;
    public static Materials_OLD Scandium;
    public static Materials_OLD Silicon;
    public static Materials_OLD Silver;
    public static Materials_OLD Sodium;
    public static Materials_OLD Strontium;
    public static Materials_OLD Sulfur;
    public static Materials_OLD Tantalum;
    public static Materials_OLD Tellurium;
    public static Materials_OLD Terbium;
    public static Materials_OLD Thorium;
    public static Materials_OLD Thulium;
    public static Materials_OLD Tin;
    public static Materials_OLD Titanium;
    public static Materials_OLD Tritanium; // Not a real element
    public static Materials_OLD Tungsten;
    public static Materials_OLD Uranium;
    public static Materials_OLD Vanadium;
    public static Materials_OLD Ytterbium;
    public static Materials_OLD Yttrium;
    public static Materials_OLD Zinc;
    // endregion

    // region Isotopes
    public static Materials_OLD Deuterium;
    public static Materials_OLD Helium3;
    public static Materials_OLD Plutonium241;
    public static Materials_OLD Tritium;
    public static Materials_OLD Uranium235;
    // endregion

    // region Water Line
    public static Materials_OLD FlocculationWasteLiquid;
    public static Materials_OLD Grade1PurifiedWater;
    public static Materials_OLD Grade2PurifiedWater;
    public static Materials_OLD Grade3PurifiedWater;
    public static Materials_OLD Grade4PurifiedWater;
    public static Materials_OLD Grade5PurifiedWater;
    public static Materials_OLD Grade6PurifiedWater;
    public static Materials_OLD Grade7PurifiedWater;
    public static Materials_OLD Grade8PurifiedWater;
    // endregion

    // region Random
    public static Materials_OLD AnyBronze;
    public static Materials_OLD AnyCopper;
    public static Materials_OLD AnyCarbon;
    public static Materials_OLD AnyIron;
    public static Materials_OLD AnyRubber;
    public static Materials_OLD AnySyntheticRubber;
    public static Materials_OLD BrickNether;
    public static Materials_OLD Cobblestone;
    public static Materials_OLD Crystal;
    public static Materials_OLD Metal;
    public static Materials_OLD Organic;
    public static Materials_OLD Quartz;
    public static Materials_OLD Unknown;
    // endregion

    // region I don't care
    /*
     * The "I don't care" Section, everything I don't want to do anything with right now, is right here. Just to make
     * the Material Finder shut up about them. But I do see potential uses in some of these Materials.
     */
    public static Materials_OLD Alfium;
    public static Materials_OLD Aquamarine;
    public static Materials_OLD DarkThaumium;
    public static Materials_OLD Draconium;
    public static Materials_OLD DraconiumAwakened;
    public static Materials_OLD Ender;
    public static Materials_OLD Endium;
    public static Materials_OLD Fluix;
    public static Materials_OLD Flux;
    public static Materials_OLD InfusedTeslatite;
    public static Materials_OLD IridiumSodiumOxide;
    public static Materials_OLD Mutation;
    public static Materials_OLD OsmiumTetroxide;
    public static Materials_OLD PhasedGold;
    public static Materials_OLD PhasedIron;
    public static Materials_OLD PlatinumGroupSludge;
    public static Materials_OLD PurpleAlloy;
    public static Materials_OLD RubberTreeSap;
    public static Materials_OLD Serpentine;
    public static Materials_OLD SodiumPeroxide;
    public static Materials_OLD Teslatite;
    // endregion

    // region Unknown Material Components. Dead End Section.
    public static Materials_OLD Adamantium;
    public static Materials_OLD Adamite;
    public static Materials_OLD Adluorite;
    public static Materials_OLD Agate;
    public static Materials_OLD Alduorite;
    public static Materials_OLD Amber;
    public static Materials_OLD Ammonium;
    public static Materials_OLD Amordrine;
    public static Materials_OLD AncientDebris;
    public static Materials_OLD Andesite;
    public static Materials_OLD Ardite;
    public static Materials_OLD Aredrite;
    public static Materials_OLD Bitumen;
    public static Materials_OLD Black;
    public static Materials_OLD Blizz;
    public static Materials_OLD Bloodstone;
    public static Materials_OLD Bluestone;
    public static Materials_OLD Blutonium;
    public static Materials_OLD CertusQuartz;
    public static Materials_OLD CertusQuartzCharged;
    public static Materials_OLD Ceruclase;
    public static Materials_OLD Chimerite;
    public static Materials_OLD Chrysocolla;
    public static Materials_OLD Citrine;
    public static Materials_OLD CobaltHexahydrate;
    public static Materials_OLD ConstructionFoam;
    public static Materials_OLD Coral;
    public static Materials_OLD CrudeOil;
    public static Materials_OLD CrystalFlux;
    public static Materials_OLD Cyanite;
    public static Materials_OLD DarkIron;
    public static Materials_OLD DarkStone;
    public static Materials_OLD Demonite;
    public static Materials_OLD Desichalkos;
    public static Materials_OLD Dilithium;
    public static Materials_OLD Draconic;
    public static Materials_OLD Drulloy;
    public static Materials_OLD Duranium;
    public static Materials_OLD ElectrumFlux;
    public static Materials_OLD Emery;
    public static Materials_OLD EnderiumBase;
    public static Materials_OLD Energized;
    public static Materials_OLD FierySteel;
    public static Materials_OLD Firestone;
    public static Materials_OLD Fluorite;
    public static Materials_OLD Force;
    public static Materials_OLD Forcicium;
    public static Materials_OLD Forcillium;
    public static Materials_OLD Glowstone;
    public static Materials_OLD Graphene;
    public static Materials_OLD Graphite;
    public static Materials_OLD Greenstone;
    public static Materials_OLD Hematite;
    public static Materials_OLD HSLA;
    public static Materials_OLD Infernal;
    public static Materials_OLD InfusedAir;
    public static Materials_OLD InfusedDull;
    public static Materials_OLD InfusedEarth;
    public static Materials_OLD InfusedEntropy;
    public static Materials_OLD InfusedFire;
    public static Materials_OLD InfusedGold;
    public static Materials_OLD InfusedOrder;
    public static Materials_OLD InfusedVis;
    public static Materials_OLD InfusedWater;
    public static Materials_OLD Invisium;
    public static Materials_OLD Jade;
    public static Materials_OLD Lava;
    public static Materials_OLD Limestone;
    public static Materials_OLD Magma;
    public static Materials_OLD Mawsitsit;
    public static Materials_OLD Mercassium;
    public static Materials_OLD MeteoricSteel;
    public static Materials_OLD Meteorite;
    public static Materials_OLD Migmatite;
    public static Materials_OLD Mimichite;
    public static Materials_OLD Moonstone;
    public static Materials_OLD NaquadahAlloy;
    public static Materials_OLD NaquadahEnriched;
    public static Materials_OLD Naquadria;
    public static Materials_OLD Nether;
    public static Materials_OLD NetherBrick;
    public static Materials_OLD NetherQuartz;
    public static Materials_OLD NetherStar;
    public static Materials_OLD ObsidianFlux;
    public static Materials_OLD Oilsands;
    public static Materials_OLD Onyx;
    public static Materials_OLD Orichalcum;
    public static Materials_OLD Osmonium;
    public static Materials_OLD Painite;
    public static Materials_OLD Peanutwood;
    public static Materials_OLD Petroleum;
    public static Materials_OLD Pewter;
    public static Materials_OLD Phoenixite;
    public static Materials_OLD Quartzite;
    public static Materials_OLD Randomite;
    public static Materials_OLD Rubracium;
    public static Materials_OLD Sand;
    public static Materials_OLD Siltstone;
    public static Materials_OLD Spinel;
    public static Materials_OLD Sunstone;
    public static Materials_OLD Tar;
    public static Materials_OLD Tartarite;
    public static Materials_OLD UUAmplifier;
    public static Materials_OLD UUMatter;
    public static Materials_OLD Void;
    public static Materials_OLD Voidstone;
    public static Materials_OLD Vulcanite;
    public static Materials_OLD Vyroxeres;
    public static Materials_OLD Yellorium;
    public static Materials_OLD Zectium;
    // endregion

    // region Tier materials
    public static Materials_OLD ULV;
    public static Materials_OLD LV;
    public static Materials_OLD MV;
    public static Materials_OLD HV;
    public static Materials_OLD EV;
    public static Materials_OLD IV;
    public static Materials_OLD LuV;
    public static Materials_OLD ZPM;
    public static Materials_OLD UV;
    public static Materials_OLD UHV;
    public static Materials_OLD UEV;
    public static Materials_OLD UIV;
    public static Materials_OLD UMV;
    public static Materials_OLD UXV;
    public static Materials_OLD MAX;
    // endregion

    // region Circuitry
    public static Materials_OLD Resistor;
    public static Materials_OLD Diode;
    public static Materials_OLD Transistor;
    public static Materials_OLD Capacitor;
    public static Materials_OLD Inductor;
    public static Materials_OLD Nano;
    public static Materials_OLD Piko;
    // endregion

    // region Not possible to determine exact Components
    public static Materials_OLD Antimatter;
    public static Materials_OLD Biomass;
    public static Materials_OLD CharcoalByproducts;
    public static Materials_OLD Cheese;
    public static Materials_OLD Chili;
    public static Materials_OLD Chocolate;
    public static Materials_OLD Cluster;
    public static Materials_OLD CoalFuel;
    public static Materials_OLD Cocoa;
    public static Materials_OLD Coffee;
    public static Materials_OLD Creosote;
    public static Materials_OLD Diesel;
    public static Materials_OLD Ethanol;
    public static Materials_OLD FermentedBiomass;
    public static Materials_OLD FishOil;
    public static Materials_OLD FryingOilHot;
    public static Materials_OLD Glue;
    public static Materials_OLD GlueAdvanced;
    public static Materials_OLD Gunpowder;
    public static Materials_OLD Honey;
    public static Materials_OLD Leather;
    public static Materials_OLD Lubricant;
    public static Materials_OLD MeatCooked;
    public static Materials_OLD MeatRaw;
    public static Materials_OLD Milk;
    public static Materials_OLD Mud;
    public static Materials_OLD Oil;
    public static Materials_OLD Paper;
    public static Materials_OLD Peat;
    public static Materials_OLD RareEarth;
    public static Materials_OLD Red;
    public static Materials_OLD Reinforced;
    public static Materials_OLD SeedOil;
    public static Materials_OLD SeedOilHemp;
    public static Materials_OLD SeedOilLin;
    public static Materials_OLD Stone;
    public static Materials_OLD TNT;
    public static Materials_OLD Unstable;
    public static Materials_OLD UnstableIngot;
    public static Materials_OLD Vinegar;
    public static Materials_OLD WeedEX9000;
    public static Materials_OLD Wheat;
    public static Materials_OLD WoodGas;
    public static Materials_OLD WoodTar;
    public static Materials_OLD WoodVinegar;
    // endregion

    // region Bio Vat Bacterial Sludges
    public static Materials_OLD EnrichedBacterialSludge;
    public static Materials_OLD FermentedBacterialSludge;
    // endregion

    // region TODO: this
    public static Materials_OLD AluminiumBrass;
    public static Materials_OLD Endstone;
    public static Materials_OLD Netherrack;
    public static Materials_OLD Osmiridium;
    public static Materials_OLD SoulSand;
    public static Materials_OLD Sunnarium;
    // endregion

    // region Degree 1 Compounds
    public static Materials_OLD AceticAcid;
    public static Materials_OLD Acetone;
    public static Materials_OLD Air;
    public static Materials_OLD AllylChloride;
    public static Materials_OLD Almandine;
    public static Materials_OLD Ammonia;
    public static Materials_OLD Andradite;
    public static Materials_OLD AnnealedCopper;
    public static Materials_OLD AntimonyTrioxide;
    public static Materials_OLD ArsenicTrioxide;
    public static Materials_OLD Asbestos;
    public static Materials_OLD Ash;
    public static Materials_OLD AshDark;
    public static Materials_OLD BandedIron;
    public static Materials_OLD BatteryAlloy;
    public static Materials_OLD Benzene;
    public static Materials_OLD BlueTopaz;
    public static Materials_OLD Bone;
    public static Materials_OLD Brass;
    public static Materials_OLD Brick;
    public static Materials_OLD Bronze;
    public static Materials_OLD BrownLimonite;
    public static Materials_OLD Calcite;
    public static Materials_OLD CarbonDioxide;
    public static Materials_OLD Cassiterite;
    public static Materials_OLD CassiteriteSand;
    public static Materials_OLD Chalcopyrite;
    public static Materials_OLD Charcoal;
    public static Materials_OLD Chlorobenzene;
    public static Materials_OLD Chromite;
    public static Materials_OLD ChromiumDioxide;
    public static Materials_OLD Cinnabar;
    public static Materials_OLD Coal;
    public static Materials_OLD CobaltOxide;
    public static Materials_OLD Cobaltite;
    public static Materials_OLD Cooperite;
    public static Materials_OLD CupricOxide;
    public static Materials_OLD Cupronickel;
    public static Materials_OLD DeepIron;
    public static Materials_OLD Diamond;
    public static Materials_OLD DilutedHydrochloricAcid;
    public static Materials_OLD Electrum;
    public static Materials_OLD Emerald;
    public static Materials_OLD Epoxid;
    public static Materials_OLD FerriteMixture;
    public static Materials_OLD Ferrosilite;
    public static Materials_OLD FreshWater;
    public static Materials_OLD Galena;
    public static Materials_OLD Garnierite;
    public static Materials_OLD Glyceryl;
    public static Materials_OLD GreenSapphire;
    public static Materials_OLD Grossular;
    public static Materials_OLD HolyWater;
    public static Materials_OLD HydricSulfide;
    public static Materials_OLD Ice;
    public static Materials_OLD Ilmenite;
    public static Materials_OLD Invar;
    public static Materials_OLD Kanthal;
    public static Materials_OLD Lazurite;
    public static Materials_OLD LiquidNitrogen;
    public static Materials_OLD LiquidOxygen;
    public static Materials_OLD Magnalium;
    public static Materials_OLD Magnesia;
    public static Materials_OLD Magnesite;
    public static Materials_OLD Magnesiumchloride;
    public static Materials_OLD Magnetite;
    public static Materials_OLD Massicot;
    public static Materials_OLD Methane;
    public static Materials_OLD Molybdenite;
    public static Materials_OLD Nichrome;
    public static Materials_OLD NickelZincFerrite;
    public static Materials_OLD NiobiumNitride;
    public static Materials_OLD NiobiumTitanium;
    public static Materials_OLD NitroCarbon;
    public static Materials_OLD NitrogenDioxide;
    public static Materials_OLD NobleGases;
    public static Materials_OLD Obsidian;
    public static Materials_OLD Phosphate;
    public static Materials_OLD PigIron;
    public static Materials_OLD Polycaprolactam;
    public static Materials_OLD Polydimethylsiloxane;
    public static Materials_OLD Polyethylene;
    public static Materials_OLD Polytetrafluoroethylene;
    public static Materials_OLD Potash;
    public static Materials_OLD Powellite;
    public static Materials_OLD Pumice;
    public static Materials_OLD Pyrite;
    public static Materials_OLD Pyrochlore;
    public static Materials_OLD Pyrolusite;
    public static Materials_OLD Pyrope;
    public static Materials_OLD Quicklime;
    public static Materials_OLD RockSalt;
    public static Materials_OLD Rubber;
    public static Materials_OLD RubberRaw;
    public static Materials_OLD RubberSilicone;
    public static Materials_OLD Ruby;
    public static Materials_OLD Rutile;
    public static Materials_OLD Salt;
    public static Materials_OLD Saltpeter;
    public static Materials_OLD Sapphire;
    public static Materials_OLD Scheelite;
    public static Materials_OLD SiliconDioxide;
    public static Materials_OLD Snow;
    public static Materials_OLD SodaAsh;
    public static Materials_OLD Sodalite;
    public static Materials_OLD SodiumPersulfate;
    public static Materials_OLD SodiumPotassium;
    public static Materials_OLD SodiumSulfide;
    public static Materials_OLD Titaniumtetrachloride;
    public static Materials_OLD Water, Steam; // Steam.getGas(..) reads better than Water.getGas(..)
    public static Materials_OLD Zincite;
    // endregion

    // region Unclassified 01 materials
    public static Materials_OLD DenseSteam;
    public static Materials_OLD DenseSuperheatedSteam;
    public static Materials_OLD DenseSupercriticalSteam;
    public static Materials_OLD OilExtraHeavy;
    public static Materials_OLD OilHeavy;
    public static Materials_OLD OilLight;
    public static Materials_OLD OilMedium;
    public static Materials_OLD SuperCoolant;
    public static Materials_OLD EnrichedHolmium;
    public static Materials_OLD TengamPurified;
    public static Materials_OLD TengamAttuned;
    public static Materials_OLD TengamRaw;
    // endregion

    // region Unclassified 02 materials
    public static Materials_OLD Gas;
    public static Materials_OLD HeavyFuel;
    public static Materials_OLD LightFuel;
    public static Materials_OLD LPG;
    public static Materials_OLD Naphtha;
    public static Materials_OLD NaturalGas;
    public static Materials_OLD SulfuricGas;
    public static Materials_OLD SulfuricHeavyFuel;
    public static Materials_OLD SulfuricLightFuel;
    public static Materials_OLD SulfuricNaphtha;
    // endregion

    // region Unclassified 03 materials
    public static Materials_OLD BioMediumRaw;
    public static Materials_OLD BioMediumSterilized;
    public static Materials_OLD ReinforcedGlass;
    // endregion

    // region Unclassified 04 materials
    public static Materials_OLD GrowthMediumRaw;
    public static Materials_OLD GrowthMediumSterilized;
    // endregion

    // region Unclassified 05 materials
    public static Materials_OLD BioDiesel;
    public static Materials_OLD BisphenolA;
    public static Materials_OLD Butadiene;
    public static Materials_OLD Butane;
    public static Materials_OLD Butene;
    public static Materials_OLD CalciumAcetateSolution;
    public static Materials_OLD CaesiumHydroxide;
    public static Materials_OLD CarbonMonoxide;
    public static Materials_OLD Chloramine;
    public static Materials_OLD Chloroform;
    public static Materials_OLD Chloromethane;
    public static Materials_OLD Cumene;
    public static Materials_OLD Dichlorobenzene;
    public static Materials_OLD Dimethylamine;
    public static Materials_OLD Dimethyldichlorosilane;
    public static Materials_OLD Dimethylhydrazine;
    public static Materials_OLD DinitrogenTetroxide;
    public static Materials_OLD Epichlorohydrin;
    public static Materials_OLD Ethane;
    public static Materials_OLD Ethenone;
    public static Materials_OLD Ethylene;
    public static Materials_OLD Glycerol;
    public static Materials_OLD HydrochloricAcid;
    public static Materials_OLD HydrofluoricAcid;
    public static Materials_OLD HypochlorousAcid;
    public static Materials_OLD IronIIIChloride;
    public static Materials_OLD Isoprene;
    public static Materials_OLD LifeEssence;
    public static Materials_OLD MetalMixture;
    public static Materials_OLD Methanol;
    public static Materials_OLD MethylAcetate;
    public static Materials_OLD NitrationMixture;
    public static Materials_OLD NitricAcid;
    public static Materials_OLD NitricOxide;
    public static Materials_OLD Phenol;
    public static Materials_OLD PhosphoricAcid;
    public static Materials_OLD PhosphorousPentoxide;
    public static Materials_OLD PolyphenyleneSulfide;
    public static Materials_OLD Polystyrene;
    public static Materials_OLD PolyvinylAcetate;
    public static Materials_OLD PolyvinylChloride;
    public static Materials_OLD PotassiumHydroxide;
    public static Materials_OLD Propane;
    public static Materials_OLD Propene;
    public static Materials_OLD SaltWater;
    public static Materials_OLD SodiumBisulfate;
    public static Materials_OLD SodiumHydroxide;
    public static Materials_OLD SodiumOxide;
    public static Materials_OLD Styrene;
    public static Materials_OLD StyreneButadieneRubber;
    public static Materials_OLD SulfurDioxide;
    public static Materials_OLD SulfurTrioxide;
    public static Materials_OLD Tetrafluoroethylene;
    public static Materials_OLD Tetranitromethane;
    public static Materials_OLD Toluene;
    public static Materials_OLD VinylAcetate;
    public static Materials_OLD VinylChloride;
    // endregion

    // region Roasted Ores
    public static Materials_OLD RoastedAntimony;
    public static Materials_OLD RoastedArsenic;
    public static Materials_OLD RoastedCobalt;
    public static Materials_OLD RoastedCopper;
    public static Materials_OLD RoastedIron;
    public static Materials_OLD RoastedLead;
    public static Materials_OLD RoastedNickel;
    public static Materials_OLD RoastedZinc;
    // endregion

    // region Silicon Line
    public static Materials_OLD AluminiumFluoride;
    public static Materials_OLD CalciumDisilicide;
    public static Materials_OLD Calciumhydride;
    public static Materials_OLD Dichlorosilane;
    public static Materials_OLD Hexachlorodisilane;
    public static Materials_OLD Silane;
    public static Materials_OLD SiliconSG;
    public static Materials_OLD SiliconTetrachloride;
    public static Materials_OLD SiliconTetrafluoride;
    public static Materials_OLD Trichlorosilane;
    // endregion

    // region Unclassified 06 materials
    public static Materials_OLD GalliumArsenide;
    public static Materials_OLD IndiumGalliumPhosphide;
    public static Materials_OLD SolderingAlloy;
    public static Materials_OLD Spessartine;
    public static Materials_OLD Sphalerite;
    public static Materials_OLD StainlessSteel;
    public static Materials_OLD Steel;
    public static Materials_OLD Stibnite;
    public static Materials_OLD SulfuricAcid;
    public static Materials_OLD Tanzanite;
    public static Materials_OLD Tetrahedrite;
    public static Materials_OLD TinAlloy;
    public static Materials_OLD Topaz;
    public static Materials_OLD Tungstate;
    public static Materials_OLD Ultimet;
    public static Materials_OLD Uraninite;
    public static Materials_OLD Uvarovite;
    public static Materials_OLD VanadiumGallium;
    public static Materials_OLD Wood;
    public static Materials_OLD CastIron;
    @Deprecated
    public static Materials_OLD WroughtIron;
    public static Materials_OLD Wulfenite;
    public static Materials_OLD YellowLimonite;
    public static Materials_OLD YttriumBariumCuprate;
    // endregion

    // region Degree 2 Compounds
    public static Materials_OLD Aluminiumhydroxide;
    public static Materials_OLD Aluminiumoxide;
    public static Materials_OLD Alumite;
    public static Materials_OLD Alunite;
    public static Materials_OLD Amethyst;
    public static Materials_OLD Apatite;
    public static Materials_OLD Barite;
    public static Materials_OLD Bastnasite;
    public static Materials_OLD Bauxite;
    public static Materials_OLD Bentonite;
    public static Materials_OLD Biotite;
    public static Materials_OLD BismuthBronze;
    public static Materials_OLD BlackBronze;
    public static Materials_OLD BlackSteel;
    public static Materials_OLD Blaze;
    public static Materials_OLD Borax;
    public static Materials_OLD Chrysotile;
    public static Materials_OLD Clay;
    public static Materials_OLD CobaltBrass;
    public static Materials_OLD Concrete;
    public static Materials_OLD Cryolite;
    public static Materials_OLD DamascusSteel;
    public static Materials_OLD Diatomite;
    public static Materials_OLD DilutedSulfuricAcid;
    public static Materials_OLD Dolomite;
    public static Materials_OLD ElectricalSteel;
    public static Materials_OLD EnderPearl;
    public static Materials_OLD EpoxidFiberReinforced;
    public static Materials_OLD Flint;
    public static Materials_OLD FullersEarth;
    public static Materials_OLD GarnetRed;
    public static Materials_OLD GarnetYellow;
    public static Materials_OLD Glass;
    public static Materials_OLD Glauconite;
    public static Materials_OLD GlauconiteSand;
    public static Materials_OLD GraniteBlack;
    public static Materials_OLD GraniticMineralSand;
    public static Materials_OLD Gypsum;
    public static Materials_OLD HydratedCoal;
    public static Materials_OLD IronMagnetic;
    public static Materials_OLD Jasper;
    public static Materials_OLD Kaolinite;
    public static Materials_OLD Knightmetal;
    public static Materials_OLD Kyanite;
    public static Materials_OLD Lapis;
    public static Materials_OLD Lepidolite;
    public static Materials_OLD Lignite;
    public static Materials_OLD LiquidAir;
    public static Materials_OLD LiveRoot;
    public static Materials_OLD Malachite;
    public static Materials_OLD Manyullyn;
    public static Materials_OLD Marble;
    public static Materials_OLD Mica;
    public static Materials_OLD Mirabilite;
    public static Materials_OLD Monazite;
    public static Materials_OLD NeodymiumMagnetic;
    public static Materials_OLD Niter;
    public static Materials_OLD NitroCoalFuel;
    public static Materials_OLD NitroFuel;
    public static Materials_OLD Olivine;
    public static Materials_OLD Opal;
    public static Materials_OLD Pentlandite;
    public static Materials_OLD Perlite;
    public static Materials_OLD Pitchblende;
    public static Materials_OLD Pollucite;
    public static Materials_OLD Pollution;
    public static Materials_OLD PotassiumFeldspar;
    public static Materials_OLD QuartzSand;
    public static Materials_OLD RawStyreneButadieneRubber;
    public static Materials_OLD Realgar;
    public static Materials_OLD RedMud;
    public static Materials_OLD Redrock;
    public static Materials_OLD Redstone;
    public static Materials_OLD RoseGold;
    public static Materials_OLD SamariumMagnetic;
    public static Materials_OLD Soapstone;
    public static Materials_OLD SodiumAluminate;
    public static Materials_OLD SodiumCarbonate;
    public static Materials_OLD Spodumene;
    public static Materials_OLD SteelMagnetic;
    public static Materials_OLD Steeleaf;
    public static Materials_OLD SterlingSilver;
    public static Materials_OLD Sugar;
    public static Materials_OLD Talc;
    public static Materials_OLD Tantalite;
    public static Materials_OLD Thaumium;
    public static Materials_OLD TPV;
    public static Materials_OLD TricalciumPhosphate;
    public static Materials_OLD Trona;
    public static Materials_OLD TungstenCarbide;
    public static Materials_OLD TungstenSteel;
    public static Materials_OLD VanadiumMagnetite;
    public static Materials_OLD VanadiumSteel;
    public static Materials_OLD Vermiculite;
    public static Materials_OLD Vinteum;
    public static Materials_OLD Vis;
    public static Materials_OLD VolcanicAsh;
    public static Materials_OLD Wollastonite;
    public static Materials_OLD WoodSealed;
    public static Materials_OLD Zeolite;
    // endregion

    // region Degree 3 Compounds
    public static Materials_OLD Basalt;
    public static Materials_OLD BlueSteel;
    public static Materials_OLD BorosilicateGlass;
    public static Materials_OLD Cryotheum;
    public static Materials_OLD DarkSteel;
    public static Materials_OLD EnderEye;
    public static Materials_OLD Fireclay;
    public static Materials_OLD GarnetSand;
    public static Materials_OLD HSSG;
    public static Materials_OLD IronWood;
    public static Materials_OLD Pyrotheum;
    public static Materials_OLD GraniteRed;
    public static Materials_OLD RedAlloy;
    public static Materials_OLD RedSteel;
    public static Materials_OLD RedstoneAlloy;
    // endregion

    // region Degree 4 Compounds
    public static Materials_OLD BasalticMineralSand;
    public static Materials_OLD ConductiveIron;
    public static Materials_OLD EndSteel;
    public static Materials_OLD HSSE;
    public static Materials_OLD HSSS;
    public static Materials_OLD PulsatingIron;
    // endregion

    // region Degree 5 Compounds
    public static Materials_OLD CrystallineAlloy;
    public static Materials_OLD EnergeticAlloy;
    public static Materials_OLD EnergeticSilver;
    public static Materials_OLD MelodicAlloy;
    // endregion

    // region Degree 6 Compounds
    public static Materials_OLD CrystallinePinkSlime;
    public static Materials_OLD StellarAlloy;
    public static Materials_OLD VibrantAlloy;
    public static Materials_OLD VividAlloy;
    // endregion

    // region Polybenzimidazole Line
    public static Materials_OLD ChromiumTrioxide;
    public static Materials_OLD Diaminobenzidin;
    public static Materials_OLD Dichlorobenzidine;
    public static Materials_OLD Dimethylbenzene;
    public static Materials_OLD Diphenylisophthalate;
    public static Materials_OLD Nitrochlorobenzene;
    public static Materials_OLD PhthalicAcid;
    public static Materials_OLD Polybenzimidazole;
    public static Materials_OLD PotassiumNitrade;
    public static Materials_OLD Potassiumdichromate;
    // endregion

    // region Gasoline Line
    public static Materials_OLD AntiKnock;
    public static Materials_OLD GasolinePremium;
    public static Materials_OLD GasolineRaw;
    public static Materials_OLD GasolineRegular;
    public static Materials_OLD MTBEMixture;
    public static Materials_OLD MTBEMixtureAlt;
    public static Materials_OLD NitrousOxide;
    public static Materials_OLD Octane;
    // endregion

    // region Added
    public static Materials_OLD BloodInfusedIron;
    public static Materials_OLD Electrotine;
    public static Materials_OLD Galgadorian;
    public static Materials_OLD GalgadorianEnhanced;
    public static Materials_OLD Shadow;
    // endregion

    // region Galaxy Space 1.10 compat from Version 2.6
    public static Materials_OLD BlackPlutonium;
    public static Materials_OLD CallistoIce;
    public static Materials_OLD Duralumin;
    public static Materials_OLD Ledox;
    public static Materials_OLD MysteriousCrystal;
    public static Materials_OLD Mytryl;
    public static Materials_OLD Quantium;
    // endregion

    // region Unclassified 07 materials
    public static Materials_OLD AstralSilver;
    public static Materials_OLD BlueAlloy;
    public static Materials_OLD ClayCompound;
    public static Materials_OLD Enderium;
    public static Materials_OLD Mithril;
    public static Materials_OLD ShadowIron;
    public static Materials_OLD ShadowSteel;
    public static Materials_OLD Soularium;
    // endregion

    // region Overpowered Materials (Draconic Evolution & above)
    public static Materials_OLD Bedrockium;
    public static Materials_OLD CosmicNeutronium;
    public static Materials_OLD Ichorium;
    public static Materials_OLD Infinity;
    public static Materials_OLD InfinityCatalyst;
    public static Materials_OLD Trinium;
    // endregion

    // region Superconductor Bases
    public static Materials_OLD SuperconductorMVBase;
    public static Materials_OLD SuperconductorHVBase;
    public static Materials_OLD SuperconductorEVBase;
    public static Materials_OLD SuperconductorIVBase;
    public static Materials_OLD SuperconductorLuVBase;
    public static Materials_OLD SuperconductorZPMBase;
    public static Materials_OLD SuperconductorUVBase;
    public static Materials_OLD SuperconductorUHVBase;
    public static Materials_OLD SuperconductorUEVBase;
    public static Materials_OLD SuperconductorUIVBase;
    public static Materials_OLD SuperconductorUMVBase;
    // endregion

    // region Superconductors
    public static Materials_OLD SuperconductorMV;
    public static Materials_OLD SuperconductorHV;
    public static Materials_OLD SuperconductorEV;
    public static Materials_OLD SuperconductorIV;
    public static Materials_OLD SuperconductorLuV;
    public static Materials_OLD SuperconductorZPM;
    public static Materials_OLD SuperconductorUV;
    public static Materials_OLD SuperconductorUHV;
    public static Materials_OLD SuperconductorUEV;
    public static Materials_OLD SuperconductorUIV;
    public static Materials_OLD SuperconductorUMV;
    // endregion

    // region Waterline Chemicals
    public static Materials_OLD ActivatedCarbon;
    public static Materials_OLD PreActivatedCarbon;
    public static Materials_OLD DirtyActivatedCarbon;
    public static Materials_OLD PolyAluminiumChloride;
    public static Materials_OLD Ozone;
    public static Materials_OLD StableBaryonicMatter;
    // endregion

    // region Radox Line
    public static Materials_OLD RadoxCracked;
    public static Materials_OLD RadoxGas;
    public static Materials_OLD RadoxHeavy;
    public static Materials_OLD RadoxLight;
    public static Materials_OLD RadoxPolymer;
    public static Materials_OLD RadoxRaw;
    public static Materials_OLD RadoxSuperHeavy;
    public static Materials_OLD RadoxSuperLight;
    public static Materials_OLD Xenoxene;
    public static Materials_OLD XenoxeneDiluted;
    // endregion

    // region Netherite Line
    public static Materials_OLD NetherAir;
    public static Materials_OLD NetherSemiFluid;
    public static Materials_OLD NefariousGas;
    public static Materials_OLD NefariousOil;
    public static Materials_OLD PoorNetherWaste;
    public static Materials_OLD RichNetherWaste;
    public static Materials_OLD HellishMetal;
    public static Materials_OLD Netherite;
    // endregion

    // region Prismatic Acid Line
    public static Materials_OLD ActivatedNetherite;
    public static Materials_OLD PrismarineSolution;
    public static Materials_OLD PrismarineContaminatedHydrogenPeroxide;
    public static Materials_OLD PrismarineRichNitrobenzeneSolution;
    public static Materials_OLD PrismarineContaminatedNitrobenzeSolution;
    public static Materials_OLD PrismaticGas;
    public static Materials_OLD PrismaticAcid;
    public static Materials_OLD PrismaticNaquadah;
    public static Materials_OLD PrismaticNaquadahCompositeSlurry;
    // endregion

    // region Francium Line
    public static Materials_OLD ImpureFranciumSolution;
    public static Materials_OLD FranciumHydroxide;
    public static Materials_OLD FranciumSlurry;
    public static Materials_OLD ThoriumElutionAdsorbent;
    // endregion

    // region Chlorosulfonic Acid Line
    public static Materials_OLD PhosphorusChlorineMixture;
    public static Materials_OLD PhosphorusPentachloride;
    public static Materials_OLD ToxicAir;
    public static Materials_OLD ToxicSlurry;
    public static Materials_OLD DestabilizationSlurry;
    public static Materials_OLD AgitatingSlurry;
    public static Materials_OLD UltraContaminatedGas;
    public static Materials_OLD StagnantWasteWater;
    public static Materials_OLD ActivatedWasteWater;
    public static Materials_OLD ChlorosulfonicAcid;
    public static Materials_OLD PoisonousSlurry;
    // endregion

    // region Magic Materials
    public static Materials_OLD ComplexityCatalyst;
    public static Materials_OLD EntropicCatalyst;
    public static Materials_OLD SoulInfusedMedium;
    // endregion

    // region Botania Materials
    public static Materials_OLD Manasteel;
    public static Materials_OLD Terrasteel;
    public static Materials_OLD ElvenElementium;
    public static Materials_OLD Livingrock;
    public static Materials_OLD GaiaSpirit;
    public static Materials_OLD Livingwood;
    public static Materials_OLD Dreamwood;
    public static Materials_OLD ManaDiamond;
    public static Materials_OLD Dragonstone;
    // endregion

    // region Kevlar Line
    public static Materials_OLD DiphenylmethaneDiisocyanate;
    public static Materials_OLD DiaminodiphenylmethanMixture;
    public static Materials_OLD DiphenylmethaneDiisocyanateMixture;
    public static Materials_OLD Butyraldehyde;
    public static Materials_OLD Isobutyraldehyde;
    public static Materials_OLD NickelTetracarbonyl;
    public static Materials_OLD KevlarCatalyst;
    public static Materials_OLD EthyleneOxide;
    public static Materials_OLD SiliconOil;
    public static Materials_OLD Ethyleneglycol;
    public static Materials_OLD Acetaldehyde;
    public static Materials_OLD Pentaerythritol;
    public static Materials_OLD PolyurethaneResin;
    public static Materials_OLD NMethylIIPyrrolidone;
    public static Materials_OLD TerephthaloylChloride;
    public static Materials_OLD Acetylene;
    public static Materials_OLD IVNitroaniline;
    public static Materials_OLD ParaPhenylenediamine;
    public static Materials_OLD Methylamine;
    public static Materials_OLD Trimethylamine;
    public static Materials_OLD GammaButyrolactone;
    public static Materials_OLD CalciumCarbide;
    public static Materials_OLD LiquidCrystalKevlar;
    public static Materials_OLD IIButinIIVdiol;
    public static Materials_OLD NickelAluminide;
    public static Materials_OLD RaneyNickelActivated;
    public static Materials_OLD BismuthIIIOxide;
    public static Materials_OLD ThionylChloride;
    public static Materials_OLD SulfurDichloride;
    public static Materials_OLD DimethylTerephthalate;
    public static Materials_OLD Kevlar;
    public static Materials_OLD TerephthalicAcid;
    public static Materials_OLD IIIDimethylbenzene;
    public static Materials_OLD IVDimethylbenzene;
    public static Materials_OLD CobaltIINaphthenate;
    public static Materials_OLD NaphthenicAcid;
    public static Materials_OLD CobaltIIHydroxide;
    public static Materials_OLD CobaltIIAcetate;
    public static Materials_OLD CobaltIINitrate;
    public static Materials_OLD OrganorhodiumCatalyst;
    public static Materials_OLD SodiumBorohydride;
    public static Materials_OLD RhodiumChloride;
    public static Materials_OLD Triphenylphosphene;
    public static Materials_OLD PhosphorusTrichloride;
    public static Materials_OLD SodiumHydride;
    public static Materials_OLD TrimethylBorate;
    public static Materials_OLD SodiumMethoxide;
    // endregion

    // region Aluminium Ores
    public static Materials_OLD BauxiteSlurry;
    public static Materials_OLD HeatedBauxiteSlurry;
    public static Materials_OLD SluiceJuice;
    public static Materials_OLD SluiceSand;
    public static Materials_OLD BauxiteSlag;
    public static Materials_OLD IlmeniteSlag;
    public static Materials_OLD GreenSapphireJuice;
    public static Materials_OLD SapphireJuice;
    public static Materials_OLD RubyJuice;
    // endregion

    // region UEV+ Materials
    public static Materials_OLD DTCC;
    public static Materials_OLD DTPC;
    public static Materials_OLD DTRC;
    public static Materials_OLD DTEC;
    public static Materials_OLD DTSC;
    public static Materials_OLD ExcitedDTCC;
    public static Materials_OLD ExcitedDTPC;
    public static Materials_OLD ExcitedDTRC;
    public static Materials_OLD ExcitedDTEC;
    public static Materials_OLD ExcitedDTSC;
    public static Materials_OLD DTR;
    public static Materials_OLD SpaceTime;
    public static Materials_OLD TranscendentMetal;
    public static Materials_OLD MHDCSM;
    public static Materials_OLD RawStarMatter;
    public static Materials_OLD WhiteDwarfMatter;
    public static Materials_OLD BlackDwarfMatter;
    public static Materials_OLD Time;
    public static Materials_OLD Space;
    public static Materials_OLD Universium;
    public static Materials_OLD Eternity;
    public static Materials_OLD PrimordialMatter;
    public static Materials_OLD MagMatter;
    public static Materials_OLD QuarkGluonPlasma;
    public static Materials_OLD PhononMedium;
    public static Materials_OLD PhononCrystalSolution;
    public static Materials_OLD SixPhasedCopper;
    public static Materials_OLD Mellion;
    public static Materials_OLD Creon;
    public static Materials_OLD GravitonShard;
    public static Materials_OLD DimensionallyShiftedSuperfluid;
    public static Materials_OLD MoltenProtoHalkoniteBase;
    public static Materials_OLD HotProtoHalkonite;
    public static Materials_OLD ProtoHalkonite;
    public static Materials_OLD MoltenExoHalkoniteBase;
    public static Materials_OLD HotExoHalkonite;
    public static Materials_OLD ExoHalkonite;
    public static Materials_OLD Protomatter;
    public static Materials_OLD StargateCrystalSlurry;
    public static Materials_OLD LumipodExtract;
    public static Materials_OLD BiocatalyzedPropulsionFluid;
    public static Materials_OLD Shijima;
    public static Materials_OLD Churitsu;
    public static Materials_OLD InactiveCosmicSolder;
    public static Materials_OLD BoundlessCosmicSolder;
    public static Materials_OLD ComputationBase;
    // endregion

    // region GTNH Materials
    public static Materials_OLD Signalum;
    public static Materials_OLD Lumium;
    public static Materials_OLD Prismarine;
    public static Materials_OLD AquaRegia;
    public static Materials_OLD SolutionBlueVitriol;
    public static Materials_OLD SolutionNickelSulfate;
    public static Materials_OLD Lodestone;
    public static Materials_OLD Luminite;
    public static Materials_OLD Chlorite;
    public static Materials_OLD Staurolite;
    public static Materials_OLD Cordierite;
    public static Materials_OLD Datolite;
    public static Materials_OLD MetamorphicMineralMixture;
    public static Materials_OLD Plagioclase;
    public static Materials_OLD Epidote;
    public static Materials_OLD UnformedHexanite;
    public static Materials_OLD Hexanite;
    // endregion

    // region Lab Grown Gems (cgc)
    // endregion

    // region Chimera Gems (cgc)
    public static Materials_OLD Amalgatite;
    // endregion

    public static final List<IMaterialHandler> mMaterialHandlers = new ArrayList<>();
    public static final Map<Fluid, Materials_OLD> FLUID_MAP = new LinkedHashMap<>();
    /** @deprecated This is for keeping compatibility with addons mods (Such as TinkersGregworks etc.) */
    @Deprecated
    public static Collection<Materials_OLD> VALUES = new LinkedHashSet<>();

    private static final Map<String, Materials_OLD> MATERIALS_MAP = new LinkedHashMap<>();
    private static Materials_OLD[] materialsOLDArray = new Materials_OLD[] {};

    static {
        MaterialsInit.load();

        setOreByproducts();
        setSmeltingInto();
        setMaceratingInto();
        setArcSmeltingInto();
        setDirectSmelting();
        setReRegistration();
        setMultipliers();
    }

    public final short[] mRGBa = new short[] { 255, 255, 255, 0 };
    public final short[] mMoltenRGBa = new short[] { 255, 255, 255, 0 };
    public TextureSet mIconSet;
    public GeneratedMaterialRenderer renderer;
    public List<MaterialStack> mMaterialList = new ArrayList<>();
    public List<Materials_OLD> mOreReRegistrations = new ArrayList<>();
    public List<TCAspects.TC_AspectStack> mAspects = new ArrayList<>();
    public ArrayList<ItemStack> mMaterialItems = new ArrayList<>();
    public LinkedHashSet<SubTag> mSubTags = new LinkedHashSet<>();
    public List<Supplier<Materials_OLD>> mPendingOreByproducts = new ArrayList<>();
    public List<Materials_OLD> mOreByProducts = new ArrayList<>();
    public int mDurability = 0;
    public byte mToolQuality = 0;
    public float mToolSpeed = 1.0F;
    private Supplier<Enchantment> mPendingToolEnchantment;
    public Enchantment mToolEnchantment;
    public byte mToolEnchantmentLevel = 0;
    private Supplier<Enchantment> mPendingArmorEnchantment;
    public Enchantment mArmorEnchantment;
    public byte mArmorEnchantmentLevel = 0;
    public boolean mUnifiable;
    public boolean mBlastFurnaceRequired = false;
    public boolean mAutoGenerateBlastFurnaceRecipes = true;
    public boolean mAutoGenerateVacuumFreezerRecipes = true;
    public boolean mAutoGenerateRecycleRecipes = true;
    public boolean mHasParentMod = true;
    private boolean mGenerateDustItems = false;
    private boolean mGenerateMetalItems = false;
    private boolean mGenerateGemItems = false;
    private boolean mGenerateOreItems = false;
    private boolean mGenerateCell = false;
    private boolean mGeneratePlasma = false;
    private boolean mGenerateToolHeadItems = false;
    private boolean mGenerateGearItems = false;
    private boolean mGenerateEmpty = false;
    public boolean mHasGas = false;
    public boolean mCustomOre = false;
    public short mBlastFurnaceTemp = 0;
    public int mMeltingPoint = 0;
    public int mGasTemp = 0;
    public int mMetaItemSubID = -1;
    public int mFuelPower = 0;
    public int mFuelType = 0;
    public int mExtraData = 0;
    public int mOreMultiplier = 1;
    public int mByProductMultiplier = 1;
    public int mSmeltingMultiplier = 1;
    public int mDensityMultiplier = 1;
    public int mDensityDivider = 1;
    public int processingMaterialTierEU = 0;
    public long mDensity = M;
    public float mHeatDamage = 0.0F;
    public float mSteamMultiplier = 1.0F;
    public float mGasMultiplier = 1.0F;
    public float mPlasmaMultiplier = 1.0F;
    private String mChemicalFormula = "?";
    private boolean isFormulaNeededLocalized = false;
    public String mName;
    public String mDefaultLocalName;
    public String mCustomID = "null";
    public String mConfigSection = "null";
    public Dyes mColor = Dyes._NULL;
    public Element mElement = null;
    public Materials_OLD mOreReplacement = this;
    private Supplier<Materials_OLD> mPendingSmeltingInto;
    public Materials_OLD mSmeltInto = this;
    private Supplier<Materials_OLD> mPendingMaceratingInto;
    public Materials_OLD mMacerateInto = this;
    private Supplier<Materials_OLD> mPendingArcSmeltingInto;
    public Materials_OLD mArcSmeltInto = this;
    private Map<Supplier<Materials_OLD>, Supplier<Materials_OLD>> mPendingArcSmeltingIntoWithGas;
    public final Map<Materials_OLD, Materials_OLD> mArcSmeltIntoWithGas = new LinkedHashMap<>();
    private Supplier<Materials_OLD> mPendingDirectSmelting;
    public Materials_OLD mDirectSmelting = this;
    public Materials_OLD mHandleMaterial = this;
    public Materials_OLD mMaterialInto;
    public Fluid mSolid = null;
    public Fluid mFluid = null;
    public Fluid mGas = null;
    public Fluid mPlasma = null;
    /**
     * This Fluid is used as standard Unit for Molten Materials. 1296 is a Molten Block, that means 144 is one Material
     * Unit worth of fluid.
     */
    public Fluid mStandardMoltenFluid = null;
    private boolean hasCorrespondingFluid = false;
    private boolean hasCorrespondingGas = false;
    private boolean mCanBeCracked = false;
    private Fluid[] hydroCrackedFluids = new Fluid[3];
    private Fluid[] steamCrackedFluids = new Fluid[3];
    private boolean hasGlowingOre = false;

    protected Materials_OLD(
        // spotless:off
        String name,
        String defaultLocalName,
        @Nullable Element element,
        @Nullable String chemicalFormula,
        boolean unifiable,
        TextureSet iconSet,
        Dyes color,
        int argb, int argbMolten,
        int toolDurability, int toolQuality, float toolSpeed,
        @Nullable Supplier<Enchantment> pendingToolEnchantment, int toolEnchantmentLevel,
        @Nullable Supplier<Enchantment> pendingArmorEnchantment, int armorEnchantmentLevel,
        float steamMultiplier, float gasMultiplier, float plasmaMultiplier,
        int fuelType, int fuelPower,
        boolean generateDustItems,
        boolean generateMetalItems,
        boolean generateGemItems,
        boolean generateOreItems,
        boolean generateCell,
        boolean generatePlasma,
        boolean generateToolHeadItems,
        boolean generateGearItems,
        boolean generateEmpty,
        boolean generateFluid,
        boolean generateGas,
        int extraData,
        boolean canBeCracked,
        float heatDamage,
        int meltingPoint,
        int blastFurnaceTemp,
        boolean blastFurnaceRequired,
        boolean autoGenerateBlastFurnaceRecipes,
        boolean autoGenerateVacuumFreezerRecipes,
        boolean autoGeneratedRecycleRecipes,
        int densityMultiplier, int densityDivider,
        int oreMultiplier,
        List<MaterialStack> materialList,
        List<TCAspects.TC_AspectStack> aspects,
        List<Supplier<Materials_OLD>> pendingOreByproducts,
        Supplier<Materials_OLD> pendingSmeltingInto,
        Supplier<Materials_OLD> pendingMaceratingInto,
        Supplier<Materials_OLD> pendingArcSmeltingInto,
        Map<Supplier<Materials_OLD>, Supplier<Materials_OLD>> pendingArcSmeltingIntoWithGas,
        Supplier<Materials_OLD> pendingDirectSmelting,
        LinkedHashSet<SubTag> subTags,
        boolean hasGlowingOre
        // spotless:on
    ) {

        // Set names
        mName = name;
        mDefaultLocalName = defaultLocalName;
        MATERIALS_MAP.put(mName, this);

        // Set element
        if (element != null) {
            mElement = element;
            mElement.mLinkedMaterials.add(this);
        }

        // Set Chemical formula
        if (chemicalFormula != null) {
            mChemicalFormula = chemicalFormula;
        } else if (element != null) {
            mChemicalFormula = element.toString();
        } else if (materialList.size() == 1) {
            mChemicalFormula = materialList.get(0)
                .toString(true);
        } else if (materialList.size() > 1) {
            mChemicalFormula = materialList.stream()
                .map(MaterialStack::toString)
                .collect(Collectors.joining())
                .replace("_", "-");
        }

        // Set texture and colors
        mIconSet = iconSet;
        mColor = color;
        mRGBa[0] = (short) ((argb >>> 16) & 0xFF);
        mRGBa[1] = (short) ((argb >>> 8) & 0xFF);
        mRGBa[2] = (short) (argb & 0xFF);
        mRGBa[3] = (short) ((argb >>> 24) & 0xFF);
        mMoltenRGBa[0] = (short) ((argbMolten >>> 16) & 0xFF);
        mMoltenRGBa[1] = (short) ((argbMolten >>> 8) & 0xFF);
        mMoltenRGBa[2] = (short) (argbMolten & 0xFF);
        mMoltenRGBa[3] = (short) ((argbMolten >>> 24) & 0xFF);

        // Set tool properties
        mDurability = toolDurability;
        mToolSpeed = toolSpeed;
        mToolQuality = (byte) toolQuality;
        mPendingToolEnchantment = pendingToolEnchantment;
        mToolEnchantmentLevel = (byte) toolEnchantmentLevel;
        mPendingArmorEnchantment = pendingArmorEnchantment;
        mArmorEnchantmentLevel = (byte) armorEnchantmentLevel;

        // Set turbine properties
        mSteamMultiplier = steamMultiplier;
        mGasMultiplier = gasMultiplier;
        mPlasmaMultiplier = plasmaMultiplier;

        // Set fuel properties
        mFuelPower = fuelPower;
        mFuelType = fuelType;

        // Set auto-generated parts and recipes
        mExtraData = extraData;
        mCanBeCracked = canBeCracked;
        mGenerateDustItems = generateDustItems;
        mGenerateMetalItems = generateMetalItems;
        mGenerateGemItems = generateGemItems;
        mGenerateOreItems = generateOreItems;
        mGenerateCell = generateCell;
        mGeneratePlasma = generatePlasma;
        mGenerateToolHeadItems = generateToolHeadItems;
        mGenerateGearItems = generateGearItems;
        mGenerateEmpty = generateEmpty;
        hasCorrespondingFluid = generateFluid;
        hasCorrespondingGas = generateGas;
        mBlastFurnaceRequired = blastFurnaceRequired;
        mAutoGenerateBlastFurnaceRecipes = autoGenerateBlastFurnaceRecipes;
        mAutoGenerateVacuumFreezerRecipes = autoGenerateVacuumFreezerRecipes;
        mAutoGenerateRecycleRecipes = autoGeneratedRecycleRecipes;

        // Set what materials this material is composed of
        mMaterialList = materialList;
        mPendingOreByproducts = pendingOreByproducts;
        mPendingSmeltingInto = pendingSmeltingInto;
        mPendingMaceratingInto = pendingMaceratingInto;
        mPendingArcSmeltingInto = pendingArcSmeltingInto;
        mPendingArcSmeltingIntoWithGas = pendingArcSmeltingIntoWithGas;
        mPendingDirectSmelting = pendingDirectSmelting;

        // Set material density
        mDensityMultiplier = densityMultiplier;
        mDensityDivider = densityDivider;
        mDensity = (M * densityMultiplier) / densityDivider;

        // Set material temperatures
        mBlastFurnaceTemp = (short) blastFurnaceTemp;
        mMeltingPoint = meltingPoint;

        // Constant fields
        mCustomOre = false;
        mCustomID = "null";
        mConfigSection = "ore";
        mMaterialInto = this;

        // Set material SubTags
        mSubTags = subTags;
        if (mColor != null) mSubTags.add(SubTag.HAS_COLOR);
        if (generateMetalItems) mSubTags.add(SubTag.SMELTING_TO_FLUID);

        // Uncategorized fields
        mHeatDamage = heatDamage;
        mOreMultiplier = oreMultiplier;
        mUnifiable = unifiable;

        int numberOfComponents = 0;
        int tMeltingPoint = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            numberOfComponents += tMaterial.mAmount;
            if (tMaterial.mMaterial.mMeltingPoint > 0) {
                tMeltingPoint += tMaterial.mMaterial.mMeltingPoint * tMaterial.mAmount;
            }
            if (aspects == null) {
                for (TC_AspectStack tAspect : tMaterial.mMaterial.mAspects) {
                    tAspect.addToAspectList(mAspects);
                }
            }
        }

        if (mMeltingPoint < 0 && numberOfComponents > 1) mMeltingPoint = tMeltingPoint / numberOfComponents;

        numberOfComponents *= densityMultiplier;
        numberOfComponents /= densityDivider;
        if (aspects == null) {
            for (TC_AspectStack tAspect : mAspects) {
                tAspect.mAmount = Math.max(1, tAspect.mAmount / Math.max(1, numberOfComponents));
            }
        } else {
            mAspects.addAll(aspects);
        }
        this.hasGlowingOre = hasGlowingOre;
    }

    private static void setOreByproducts() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingOreByproducts == null) continue;

            material.mOreByProducts = material.mPendingOreByproducts.stream()
                .map(Supplier::get)
                .map(byproduct -> byproduct.mMaterialInto)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

            material.mPendingOreByproducts = null;
        }
    }

    private static void setSmeltingInto() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingSmeltingInto == null) continue;
            material.mSmeltInto = material.mPendingSmeltingInto.get().mMaterialInto.mSmeltInto;
            material.mPendingSmeltingInto = null;
        }
    }

    private static void setMaceratingInto() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingMaceratingInto == null) continue;
            material.mMacerateInto = material.mPendingMaceratingInto.get().mMaterialInto.mMacerateInto;
            material.mPendingMaceratingInto = null;
        }
    }

    private static void setArcSmeltingInto() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingArcSmeltingInto != null) {
                material.mArcSmeltInto = material.mPendingArcSmeltingInto.get().mMaterialInto.mArcSmeltInto;
                material.mPendingArcSmeltingInto = null;
            }
            if (material.mPendingArcSmeltingIntoWithGas != null) {
                for (Map.Entry<Supplier<Materials_OLD>, Supplier<Materials_OLD>> entry : material.mPendingArcSmeltingIntoWithGas
                    .entrySet()) {
                    material.mArcSmeltIntoWithGas.put(
                        entry.getKey()
                            .get().mMaterialInto,
                        entry.getValue()
                            .get().mMaterialInto.mArcSmeltInto);
                }
                material.mPendingArcSmeltingIntoWithGas = null;
            }
        }
    }

    private static void setDirectSmelting() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingDirectSmelting == null) continue;
            material.mDirectSmelting = material.mPendingDirectSmelting.get().mMaterialInto.mDirectSmelting;
            material.mPendingDirectSmelting = null;
        }
    }

    private static void setMultipliers() {
        Amber.setSmeltingMultiplier(2);
        InfusedAir.setSmeltingMultiplier(2);
        InfusedFire.setSmeltingMultiplier(2);
        InfusedEarth.setSmeltingMultiplier(2);
        InfusedWater.setSmeltingMultiplier(2);
        InfusedEntropy.setSmeltingMultiplier(2);
        InfusedOrder.setSmeltingMultiplier(2);
        InfusedVis.setSmeltingMultiplier(2);
        InfusedDull.setSmeltingMultiplier(2);
        Salt.setSmeltingMultiplier(2);
        RockSalt.setSmeltingMultiplier(2);
        Scheelite.setSmeltingMultiplier(2);
        Tungstate.setSmeltingMultiplier(2);
        Cassiterite.setSmeltingMultiplier(2);
        CassiteriteSand.setSmeltingMultiplier(2);
        NetherQuartz.setSmeltingMultiplier(2);
        CertusQuartz.setSmeltingMultiplier(2);
        CertusQuartzCharged.setSmeltingMultiplier(2);
        TricalciumPhosphate.setSmeltingMultiplier(3);
        Saltpeter.setSmeltingMultiplier(4);
        Apatite.setSmeltingMultiplier(4)
            .setByProductMultiplier(2);
        Electrotine.setSmeltingMultiplier(5);
        Teslatite.setSmeltingMultiplier(5);
        Redstone.setSmeltingMultiplier(5);
        Glowstone.setSmeltingMultiplier(5);
        Lapis.setSmeltingMultiplier(6)
            .setByProductMultiplier(4);
        Sodalite.setSmeltingMultiplier(6)
            .setByProductMultiplier(4);
        Lazurite.setSmeltingMultiplier(6)
            .setByProductMultiplier(4);
        Monazite.setSmeltingMultiplier(8)
            .setByProductMultiplier(2);
        Cryolite.setByProductMultiplier(4);
        Coal.setByProductMultiplier(2);
    }

    private static void setReRegistration() {
        Iron.mOreReRegistrations.add(AnyIron);
        PigIron.mOreReRegistrations.add(AnyIron);
        CastIron.mOreReRegistrations.add(AnyIron);
        Copper.mOreReRegistrations.add(AnyCopper);
        AnnealedCopper.mOreReRegistrations.add(AnyCopper);
        Bronze.mOreReRegistrations.add(AnyBronze);
        Rubber.mOreReRegistrations.add(AnyRubber);
        StyreneButadieneRubber.mOreReRegistrations.add(AnyRubber);
        RubberSilicone.mOreReRegistrations.add(AnyRubber);
        StyreneButadieneRubber.mOreReRegistrations.add(AnySyntheticRubber);
        RubberSilicone.mOreReRegistrations.add(AnySyntheticRubber);
        Carbon.mOreReRegistrations.add(AnyCarbon);
        Coal.mOreReRegistrations.add(AnyCarbon);
        Charcoal.mOreReRegistrations.add(AnyCarbon);
        Lignite.mOreReRegistrations.add(AnyCarbon);
    }

    private static void setToolEnchantments() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingToolEnchantment == null) continue;
            material.mToolEnchantment = material.mPendingToolEnchantment.get();
            material.mPendingToolEnchantment = null;
        }
    }

    private static void setArmorEnchantments() {
        for (Materials_OLD material : MATERIALS_MAP.values()) {
            if (material.mPendingArmorEnchantment == null) continue;
            material.mArmorEnchantment = material.mPendingArmorEnchantment.get();
            material.mPendingArmorEnchantment = null;
        }
    }

    public static void init() {
        new MaterialsIDMap().register();

        setToolEnchantments();
        setArmorEnchantments();

        // This is where addon mods can add/manipulate materials
        mMaterialHandlers.forEach(IMaterialHandler::onMaterialsInit);

        initMaterialProperties();
        // No more material addition or manipulation should be done past this point!

        // Generate standard object array. This is a lot faster to loop over.
        materialsOLDArray = MATERIALS_MAP.values()
            .toArray(new Materials_OLD[0]);
        VALUES = Arrays.asList(materialsOLDArray);

        disableUnusedHotIngots();
        fillGeneratedMaterialsMap();
    }

    private static void disableUnusedHotIngots() {
        OrePrefixes.ingotHot.mDisabledItems.addAll(
            Arrays.stream(Materials_OLD.values())
                .parallel()
                .filter(OrePrefixes.ingotHot::doGenerateItem)
                .filter(m -> m.mBlastFurnaceTemp < 1750 && m.mAutoGenerateBlastFurnaceRecipes)
                .collect(Collectors.toSet()));
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Reinforced);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.ConductiveIron);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.FierySteel);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.ElectricalSteel);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.EndSteel);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Soularium);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.EnergeticSilver);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Cheese);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Calcium);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Flerovium);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Cobalt);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.RedstoneAlloy);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Ardite);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.DarkSteel);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.BlackSteel);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.EnergeticAlloy);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.PulsatingIron);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.ClayCompound);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.Netherite);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.HotProtoHalkonite);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.ProtoHalkonite);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.HotExoHalkonite);
        OrePrefixes.ingotHot.disableComponent(Materials_OLD.ExoHalkonite);
    }

    /**
     * Init rendering properties. Will be called at pre init by GT client proxy.
     */
    public static void initClient() {
        Materials_OLD.TranscendentMetal.renderer = new TranscendentMetalRenderer();
        Materials_OLD.GaiaSpirit.renderer = new GaiaSpiritRenderer();
        Materials_OLD.Infinity.renderer = new InfinityRenderer();
        Materials_OLD.CosmicNeutronium.renderer = new CosmicNeutroniumRenderer();
        Materials_OLD.Universium.renderer = new UniversiumRenderer();
        Materials_OLD.Eternity.renderer = new InfinityRenderer();
        Materials_OLD.MagMatter.renderer = new InfinityRenderer();
        Materials_OLD.SixPhasedCopper.renderer = new GlitchEffectRenderer();
        Materials_OLD.GravitonShard.renderer = new InfinityRenderer();
        Materials_OLD.ExoHalkonite.renderer = new InfinityRenderer();
        Materials_OLD.HotExoHalkonite.renderer = new InfinityRenderer();
        Materials_OLD.PrismaticNaquadah.renderer = new RainbowOverlayRenderer(
            Materials_OLD.PrismaticNaquadah.getRGBA());
        Materials_OLD.Amalgatite.renderer = new InfinityRenderer();
    }

    private static void fillGeneratedMaterialsMap() {
        for (Materials_OLD aMaterial : materialsOLDArray) {
            if (aMaterial.mMetaItemSubID >= 0) {
                if (aMaterial.mMetaItemSubID < 1000) {
                    if (aMaterial.mHasParentMod) {
                        if (GregTechAPI.S_GENERATED_MATERIALS[aMaterial.mMetaItemSubID] == null) {
                            GregTechAPI.S_GENERATED_MATERIALS[aMaterial.mMetaItemSubID] = aMaterial;
                        } else throw new IllegalArgumentException(
                            "The Material Index " + aMaterial.mMetaItemSubID
                                + " for "
                                + aMaterial.mName
                                + " is already used!");
                    }
                } else throw new IllegalArgumentException(
                    "The Material Index " + aMaterial.mMetaItemSubID
                        + " for "
                        + aMaterial.mName
                        + " is/over the maximum of 1000");
            }
        }
    }

    private static void addToolValues(Materials_OLD aMaterial) {
        // Moved from GTProxy? (Not sure)
        aMaterial.mHandleMaterial = (aMaterial == Desh ? aMaterial.mHandleMaterial
            : aMaterial == Diamond || aMaterial == Thaumium ? Wood
                : aMaterial.contains(SubTag.BURNING) ? Blaze
                    : aMaterial.contains(SubTag.MAGICAL) && aMaterial.contains(SubTag.CRYSTAL)
                        && Thaumcraft.isModLoaded() ? Thaumium
                            : aMaterial.getMass() > Element.Tc.getMass() * 2 ? TungstenSteel
                                : aMaterial.getMass() > Element.Tc.getMass() ? Steel : Wood);

        if (aMaterial == Materials_OLD.SpaceTime) {
            aMaterial.mHandleMaterial = Materials_OLD.Infinity;
        }

        if (aMaterial == Materials_OLD.TranscendentMetal) {
            aMaterial.mHandleMaterial = Materials_OLD.DraconiumAwakened;
        }

        if (aMaterial == Materials_OLD.Eternity) {
            aMaterial.mHandleMaterial = Materials_OLD.SpaceTime;
        }

        if (aMaterial == Materials_OLD.MagMatter) {
            aMaterial.mHandleMaterial = Materials_OLD.MHDCSM;
        }
    }

    private static void addEnchantmentValues(Materials_OLD aMaterial) {
        String aEnchantmentName = aMaterial.mToolEnchantment != null ? aMaterial.mToolEnchantment.getName() : "";
        if (aMaterial.mToolEnchantment != null && !aEnchantmentName.equals(aMaterial.mToolEnchantment.getName()))
            IntStream.range(0, Enchantment.enchantmentsList.length)
                .filter(i -> aEnchantmentName.equals(Enchantment.enchantmentsList[i].getName()))
                .forEach(i -> aMaterial.mToolEnchantment = Enchantment.enchantmentsList[i]);
    }

    private static void addHasGasFluid(Materials_OLD aMaterial) {
        if (aMaterial.mIconSet.is_custom) {
            return;
        }

        if (aMaterial.mHasGas) {
            GTFluidFactory
                .of(aMaterial.mName.toLowerCase(), aMaterial.mDefaultLocalName, aMaterial, GAS, aMaterial.mGasTemp);
        }
    }

    private static String getConfigPath(Materials_OLD aMaterial) {
        String cOre = aMaterial.mCustomOre ? aMaterial.mCustomID : aMaterial.mName;
        return "materials." + aMaterial.mConfigSection + "." + cOre;
    }

    public static void initMaterialProperties() {
        for (Materials_OLD aMaterial : MATERIALS_MAP.values()) {
            if (aMaterial == null || aMaterial == Materials_OLD._NULL || aMaterial == Materials_OLD.Empty) {
                continue;
            }

            addToolValues(aMaterial);
            addEnchantmentValues(aMaterial);
            addHasGasFluid(aMaterial);
        }
    }

    /**
     * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc.) that looped over the old
     * materials enum
     */
    public static Materials_OLD[] values() {
        return materialsOLDArray;
    }

    /**
     * This should only be used for getting a Material by its name as a String. Do not loop over this map, use values().
     */
    public static Map<String, Materials_OLD> getMaterialsMap() {
        return MATERIALS_MAP;
    }

    public static @NotNull Materials_OLD get(String aMaterialName) {
        return getWithFallback(aMaterialName, Materials_OLD._NULL);
    }

    public static @NotNull Materials_OLD getWithFallback(String name, @NotNull Materials_OLD fallback) {
        Materials_OLD material = getMaterialsMap().get(name);
        if (material != null) {
            return material;
        }
        return fallback;
    }

    public static Materials_OLD getRealMaterial(String aMaterialName) {
        return get(aMaterialName).mMaterialInto;
    }

    /**
     * Adds a Class implementing IMaterialRegistrator to the master list
     */
    public static boolean add(IMaterialHandler aRegistrator) {
        if (aRegistrator == null) return false;
        return mMaterialHandlers.add(aRegistrator);
    }

    public static String getLocalizedNameForItem(String aFormat, int aMaterialID) {
        if (aMaterialID >= 0 && aMaterialID < 1000) {
            Materials_OLD aMaterial = GregTechAPI.S_GENERATED_MATERIALS[aMaterialID];
            if (aMaterial != null) return aMaterial.getLocalizedNameForItem(aFormat);
        }
        return aFormat;
    }

    public static Collection<Materials_OLD> getAll() {
        return MATERIALS_MAP.values();
    }

    public int getProcessingMaterialTierEU() {
        return processingMaterialTierEU;
    }

    public Materials_OLD setProcessingMaterialTierEU(final long processingMaterialTierEU) {
        this.processingMaterialTierEU = (int) processingMaterialTierEU;
        return this;
    }

    /**
     * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc.) that looped over the old
     * materials enum
     *
     * @deprecated Use {@link Materials_OLD#getName()} instead, but realistically you should never even need the name.
     */
    @Deprecated
    public String name() {
        return mName;
    }

    /**
     * Gets the 'mName' field of the Material.
     *
     * @return mName
     */
    public String getName() {
        return mName;
    }

    public long getProtons() {
        if (mElement != null) return mElement.getProtons();
        if (mMaterialList.isEmpty()) return Element.Tc.getProtons();
        long rAmount = 0, tAmount = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getProtons();
        }
        return (getDensity() * rAmount) / (tAmount * M);
    }

    public long getNeutrons() {
        if (mElement != null) return mElement.getNeutrons();
        if (mMaterialList.isEmpty()) return Element.Tc.getNeutrons();
        long rAmount = 0, tAmount = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getNeutrons();
        }
        return (getDensity() * rAmount) / (tAmount * M);
    }

    public long getMass() {
        if (mElement != null) return mElement.getMass();
        if (mMaterialList.isEmpty()) return Element.Tc.getMass();
        long rAmount = 0, tAmount = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getMass();
        }
        return (getDensity() * rAmount) / (tAmount * M);
    }

    public long getDensity() {
        return mDensity;
    }

    /**
     * Set material's chemical formula.
     *
     * @param aChemicalFormula     the Chemical Formula want to set
     * @param isNeededLocalization if it's true, will generate a localized key.
     */
    public void setChemicalFormula(String aChemicalFormula, boolean isNeededLocalization) {
        this.mChemicalFormula = aChemicalFormula;
        if (isNeededLocalization) {
            this.isFormulaNeededLocalized = true;
            GTLanguageManager.addStringLocalization(getLocalizedNameKey() + ".ChemicalFormula", aChemicalFormula);
        }
    }

    public void setChemicalFormula(String aChemicalFormula) {
        setChemicalFormula(aChemicalFormula, false);
    }

    public String getChemicalFormula() {
        return isFormulaNeededLocalized ? StatCollector.translateToLocal(getLocalizedNameKey() + ".ChemicalFormula")
            : mChemicalFormula;
    }

    public String getChemicalTooltip() {
        return getChemicalTooltip(1, false);
    }

    public String getChemicalTooltip(boolean aShowQuestionMarks) {
        return getChemicalTooltip(1, aShowQuestionMarks);
    }

    public String getChemicalTooltip(long aMultiplier) {
        return getChemicalTooltip(aMultiplier, false);
    }

    public String getChemicalTooltip(long aMultiplier, boolean aShowQuestionMarks) {
        final String aChemicalFormula = getChemicalFormula();
        if (!aShowQuestionMarks && aChemicalFormula.equals("?")) return "";
        if (aMultiplier >= M * 2 && !mMaterialList.isEmpty()) {
            return ((mElement != null || (mMaterialList.size() < 2 && mMaterialList.get(0).mAmount == 1))
                ? aChemicalFormula
                : "(" + aChemicalFormula + ")") + aMultiplier;
        }
        return aChemicalFormula;
    }

    @Nullable
    public String getFlavorText() {
        final String key = getLocalizedNameKey() + ".flavorText";
        return StatCollector.canTranslate(key) ? StatCollector.translateToLocal(key) : null;
    }

    public void addTooltips(List<String> list, long aMultiplier) {
        if (Client.tooltip.showFormula) {
            final String chemicalTooltip = getChemicalTooltip(aMultiplier);
            if (GTUtility.isStringValid(chemicalTooltip)) list.add(chemicalTooltip);
        }
        if (Client.tooltip.showFlavorText) {
            final String flavorTooltip = getFlavorText() != null ? "§8§o" + getFlavorText() : null;
            if (flavorTooltip != null) list.add(flavorTooltip);
        }
    }

    @Override
    public void addTooltips(List<String> list) {
        addTooltips(list, 1);
    }

    /**
     * Adds an ItemStack to this Material.
     */
    public Materials_OLD add(ItemStack aStack) {
        if (aStack != null && !contains(aStack)) mMaterialItems.add(aStack);
        return this;
    }

    /**
     * This is used to determine if any of the ItemStacks belongs to this Material.
     */
    public boolean contains(ItemStack... aStacks) {
        if (aStacks == null || aStacks.length == 0) return false;
        for (int i = 0, size = mMaterialItems.size(); i < size; i++) {
            ItemStack materialItem = mMaterialItems.get(i);
            boolean ignoreNBT = !materialItem.hasTagCompound();
            for (int j = 0; j < aStacks.length; j++) {
                if (GTUtility.areStacksEqual(aStacks[j], materialItem, ignoreNBT)) return true;
            }
        }
        return false;
    }

    /**
     * This is used to determine if an ItemStack belongs to this Material.
     */
    public boolean remove(ItemStack aStack) {
        if (aStack == null) return false;
        boolean temp = false;
        int mMaterialItems_sS = mMaterialItems.size();
        for (int i = 0; i < mMaterialItems_sS; i++) if (GTUtility.areStacksEqual(aStack, mMaterialItems.get(i))) {
            mMaterialItems.remove(i--);
            temp = true;
        }
        return temp;
    }

    /**
     * Adds a SubTag to this Material
     */
    @Override
    @Deprecated
    public ISubTagContainer add(SubTag... aTags) {
        if (aTags != null) for (SubTag aTag : aTags) if (aTag != null && !contains(aTag)) {
            aTag.addContainerToList(this);
            mSubTags.add(aTag);
        }
        return this;
    }

    /**
     * If this Material has this exact SubTag
     */
    @Override
    public boolean contains(SubTag aTag) {
        return mSubTags.contains(aTag);
    }

    /**
     * Removes a SubTag from this Material
     */
    @Override
    public boolean remove(SubTag aTag) {
        return mSubTags.remove(aTag);
    }

    /**
     * If this Ore gives multiple drops of its Byproduct Material.
     */
    @SuppressWarnings("UnusedReturnValue") // Maintains signature
    public Materials_OLD setByProductMultiplier(int aByProductMultiplier) {
        if (aByProductMultiplier > 0) mByProductMultiplier = aByProductMultiplier;
        return this;
    }

    /**
     * If this Ore gives multiple drops of its Main Material. Lapis Ore for example gives about 6 drops.
     */
    public Materials_OLD setSmeltingMultiplier(int aSmeltingMultiplier) {
        if (aSmeltingMultiplier > 0) mSmeltingMultiplier = aSmeltingMultiplier;
        return this;
    }

    /**
     * This Material should be the Main Material this Ore gets ground into. Example, Chromite giving Chrome or Tungstate
     * giving Tungsten.
     */
    @SuppressWarnings("UnusedReturnValue") // Maintains signature
    public Materials_OLD setOreReplacement(Materials_OLD aMaterial) {
        if (aMaterial != null) mOreReplacement = aMaterial.mMaterialInto.mOreReplacement;
        return this;
    }

    /** @deprecated Use {@link MaterialBuilder#setToolEnchantment(Supplier, int)} instead. */
    @Deprecated
    public Materials_OLD setEnchantmentForTools(Enchantment aEnchantment, int aEnchantmentLevel) {
        mToolEnchantment = aEnchantment;
        mToolEnchantmentLevel = (byte) aEnchantmentLevel;
        return this;
    }

    /** @deprecated Use {@link MaterialBuilder#setArmorEnchantment(Supplier, int)} instead. */
    @Deprecated
    public Materials_OLD setEnchantmentForArmors(Enchantment aEnchantment, int aEnchantmentLevel) {
        mArmorEnchantment = aEnchantment;
        mArmorEnchantmentLevel = (byte) aEnchantmentLevel;
        return this;
    }

    public FluidStack getSolid(long aAmount) {
        if (mSolid == null) return null;
        return new FluidStack(mSolid, (int) aAmount);
    }

    public FluidStack getFluid(long aAmount) {
        if (mFluid == null) return null;
        return new FluidStack(mFluid, (int) aAmount);
    }

    public FluidStack getGas(long aAmount) {
        if (mGas == null) return null;
        return new FluidStack(mGas, (int) aAmount);
    }

    public FluidStack getPlasma(long aAmount) {
        if (mPlasma == null) return null;
        return new FluidStack(mPlasma, (int) aAmount);
    }

    public FluidStack getMolten(long aAmount) {
        if (mStandardMoltenFluid == null) return null;
        return new FluidStack(mStandardMoltenFluid, (int) aAmount);
    }

    @Override
    public short[] getRGBA() {
        return mRGBa;
    }

    @Override
    public TextureSet getTextureSet() {
        return mIconSet;
    }

    @Override
    public String toString() {
        return this.mName;
    }

    @Override
    public int getId() {
        return mMetaItemSubID;
    }

    @Override
    public @Nullable Materials_OLD getGTMaterial() {
        return this;
    }

    @Override
    public String getInternalName() {
        return mName;
    }

    @Override
    public String getDefaultLocalName() {
        return mDefaultLocalName;
    }

    public String getDefaultLocalizedNameForItem(String aFormat) {
        return formatStringSafe(
            aFormat.replace("%s", "%temp")
                .replace("%material", "%s"),
            this.mDefaultLocalName).replace("%temp", "%s");
    }

    public String getLocalizedNameForItem(String aFormat) {
        return formatStringSafe(
            aFormat.replace("%s", "%temp")
                .replace("%material", "%s"),
            this.getLocalizedName()).replace("%temp", "%s");
    }

    public static boolean isMaterialItem(int meta) {
        return meta >= 0 && meta < 32000;
    }

    public static boolean isMaterialItem(@NotNull ItemStack stack) {
        return isMaterialItem(stack.getItemDamage());
    }

    @Override
    public boolean generatesPrefix(OrePrefixes prefix) {
        return prefix.doGenerateItem(this);
    }

    public boolean hasDustItems() {
        return mGenerateDustItems;
    }

    public boolean hasMetalItems() {
        return mGenerateMetalItems;
    }

    public boolean hasGemItems() {
        return mGenerateGemItems;
    }

    public boolean hasOresItems() {
        return mGenerateOreItems;
    }

    public boolean hasCell() {
        return mGenerateCell;
    }

    public boolean hasPlasma() {
        return mGeneratePlasma;
    }

    public boolean hasToolHeadItems() {
        return mGenerateToolHeadItems;
    }

    public boolean hasGearItems() {
        return mGenerateGearItems;
    }

    public boolean hasEmpty() {
        return mGenerateEmpty;
    }

    public boolean hasCorrespondingFluid() {
        return hasCorrespondingFluid;
    }

    /** @deprecated Use {@link MaterialBuilder#addFluid()} on {@link MaterialBuilder} instead. */
    @Deprecated
    public Materials_OLD setHasCorrespondingFluid(boolean hasCorrespondingFluid) {
        this.hasCorrespondingFluid = hasCorrespondingFluid;
        return this;
    }

    public boolean hasCorrespondingGas() {
        return hasCorrespondingGas;
    }

    /** @deprecated Use {@link MaterialBuilder#addGas()} on {@link MaterialBuilder} instead. */
    @Deprecated
    public Materials_OLD setHasCorrespondingGas(boolean hasCorrespondingGas) {
        this.hasCorrespondingGas = hasCorrespondingGas;
        return this;
    }

    public boolean canBeCracked() {
        return mCanBeCracked;
    }

    public int getLiquidTemperature() {
        return mMeltingPoint == -1 ? 295 : mMeltingPoint;
    }

    public Materials_OLD setLiquidTemperature(int liquidTemperature) {
        this.mMeltingPoint = liquidTemperature;
        return this;
    }

    public int getGasTemperature() {
        return mGasTemp == 0 ? 295 : mMeltingPoint;
    }

    public Materials_OLD setGasTemperature(int gasTemperature) {
        this.mGasTemp = gasTemperature;
        return this;
    }

    public Materials_OLD setHydroCrackedFluids(Fluid[] hydroCrackedFluids) {
        this.hydroCrackedFluids = hydroCrackedFluids;
        return this;
    }

    public FluidStack getLightlyHydroCracked(int amount) {
        if (hydroCrackedFluids[0] == null) {
            return null;
        }
        return new FluidStack(hydroCrackedFluids[0], amount);
    }

    public FluidStack getModeratelyHydroCracked(int amount) {
        if (hydroCrackedFluids[0] == null) {
            return null;
        }
        return new FluidStack(hydroCrackedFluids[1], amount);
    }

    public FluidStack getSeverelyHydroCracked(int amount) {
        if (hydroCrackedFluids[0] == null) {
            return null;
        }
        return new FluidStack(hydroCrackedFluids[2], amount);
    }

    public Materials_OLD setSteamCrackedFluids(Fluid[] steamCrackedFluids) {
        this.steamCrackedFluids = steamCrackedFluids;
        return this;
    }

    public FluidStack getLightlySteamCracked(int amount) {
        if (hydroCrackedFluids[0] == null) {
            return null;
        }
        return new FluidStack(steamCrackedFluids[0], amount);
    }

    public FluidStack getModeratelySteamCracked(int amount) {
        if (hydroCrackedFluids[0] == null) {
            return null;
        }
        return new FluidStack(steamCrackedFluids[1], amount);
    }

    public FluidStack getSeverelySteamCracked(int amount) {
        if (hydroCrackedFluids[0] == null) {
            return null;
        }
        return new FluidStack(steamCrackedFluids[2], amount);
    }

    /**
     * Check that the material is a proper soldering fluid
     **
     * @return true if Materials is a proper soldering fluid
     */
    public boolean isProperSolderingFluid() {
        return mStandardMoltenFluid != null && contains(SubTag.SOLDERING_MATERIAL)
            && !(GregTechAPI.mUseOnlyGoodSolderingMaterials && !contains(SubTag.SOLDERING_MATERIAL_GOOD));
    }

    public ItemStack getCells(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.cell, this, amount);
    }

    public ItemStack getDust(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.dust, this, amount);
    }

    public ItemStack getDustSmall(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.dustSmall, this, amount);
    }

    public ItemStack getDustTiny(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.dustTiny, this, amount);
    }

    public ItemStack getGems(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.gem, this, amount);
    }

    public ItemStack getIngots(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.ingot, this, amount);
    }

    public ItemStack getNuggets(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.nugget, this, amount);
    }

    public ItemStack getBlocks(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.block, this, amount);
    }

    public ItemStack getPlates(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.plate, this, amount);
    }

    public static Materials_OLD getGtMaterialFromFluid(Fluid fluid) {
        return FLUID_MAP.get(fluid);
    }

    public ItemStack getNanite(int amount) {
        return GTOreDictUnificator.get(OrePrefixes.nanite, this, amount);
    }

    public boolean hasGlowingOre() {
        return hasGlowingOre;
    }
}
