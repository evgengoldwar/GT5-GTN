package gregtech.loaders.materials;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EnumChatFormatting;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Element;
import gregtech.api.enums.MaterialBuilder;
import gregtech.api.enums.MaterialIconRegistry;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.TierEU;
import gregtech.api.util.CustomGlyphs;

public class MaterialsInit {

    public static void load() {
        loadElements();
        loadIsotopes();
        loadWaterLine();
        loadRandom();
        loadDontCare();
        loadUnknownComponents();
        loadTiers();
        loadCircuitry();
        loadNotExact();
        loadBioVatBacterialSludges();
        loadTODOThis();
        loadDegree1Compounds();
        loadUnclassified01();
        loadUnclassified02();
        loadUnclassified03();
        loadUnclassified04();
        loadUnclassified05();
        loadRoastedOres();
        loadSiliconLine();
        loadUnclassified06();
        loadDegree2Compounds();
        loadDegree3Compounds();
        loadDegree4Compounds();
        loadDegree5Compounds();
        loadDegree6Compounds();
        loadPolybenzimidazoleLine();
        loadGasolineLine();
        loadAdded();
        loadGalaxySpace();
        loadUnclassified07();
        loadOverpoweredMaterials();
        loadSuperconductorBases();
        loadSuperconductors();
        loadWaterLineChemicals();
        loadRadoxLine();
        loadNetheriteLine();
        loadPrismaticAcidLine();
        loadAcidRecipes();
        loadMagicMaterials();
        loadBotaniaMaterials();
        loadKevlarLine();
        loadAluminiumOres();
        loadUEVPlusMaterials();
        loadGTNHMaterials();
        loadLabGrownGems();
        loadChimeraGems();
    }

    private static void loadElements() {
        Materials_OLD.Aluminium = loadAluminium();
        Materials_OLD.Americium = loadAmericium();
        Materials_OLD.Antimony = loadAntimony();
        Materials_OLD.Argon = loadArgon();
        Materials_OLD.Arsenic = loadArsenic();
        Materials_OLD.Barium = loadBarium();
        Materials_OLD.Beryllium = loadBeryllium();
        Materials_OLD.Bismuth = loadBismuth();
        Materials_OLD.Boron = loadBoron();
        Materials_OLD.Caesium = loadCaesium();
        Materials_OLD.Calcium = loadCalcium();
        Materials_OLD.Carbon = loadCarbon();
        Materials_OLD.Cadmium = loadCadmium();
        Materials_OLD.Cerium = loadCerium();
        Materials_OLD.Chlorine = loadChlorine();
        Materials_OLD.Chrome = loadChrome();
        Materials_OLD.Cobalt = loadCobalt();
        Materials_OLD.Copper = loadCopper();
        Materials_OLD.Desh = loadDesh(); // Not a real element
        Materials_OLD.Dysprosium = loadDysprosium();
        Materials_OLD.Empty = loadEmpty(); // Not a real element
        Materials_OLD.Erbium = loadErbium();
        Materials_OLD.Europium = loadEuropium();
        Materials_OLD.Flerovium = loadFlerovium();
        Materials_OLD.Fluorine = loadFluorine();
        Materials_OLD.Francium = loadFrancium();
        Materials_OLD.Gadolinium = loadGadolinium();
        Materials_OLD.Gallium = loadGallium();
        Materials_OLD.Gold = loadGold();
        Materials_OLD.Holmium = loadHolmium();
        Materials_OLD.Hydrogen = loadHydrogen();
        Materials_OLD.Helium = loadHelium();
        Materials_OLD.Indium = loadIndium();
        Materials_OLD.Iridium = loadIridium();
        Materials_OLD.Iron = loadIron();
        Materials_OLD.Lanthanum = loadLanthanum();
        Materials_OLD.Lead = loadLead();
        Materials_OLD.Lithium = loadLithium();
        Materials_OLD.Lutetium = loadLutetium();
        Materials_OLD.Magic = loadMagic(); // Not a real element
        Materials_OLD.Magnesium = loadMagnesium();
        Materials_OLD.Manganese = loadManganese();
        Materials_OLD.Mercury = loadMercury();
        Materials_OLD.MeteoricIron = loadMeteoricIron(); // Not a real element
        Materials_OLD.Molybdenum = loadMolybdenum();
        Materials_OLD.Naquadah = loadNaquadah(); // Not a real element
        Materials_OLD.Neodymium = loadNeodymium();
        Materials_OLD.Neutronium = loadNeutronium();
        Materials_OLD.Nickel = loadNickel();
        Materials_OLD.Niobium = loadNiobium();
        Materials_OLD.Nitrogen = loadNitrogen();
        Materials_OLD._NULL = loadNULL(); // Not a real element
        Materials_OLD.Oriharukon = loadOriharukon();
        Materials_OLD.Osmium = loadOsmium();
        Materials_OLD.Oxygen = loadOxygen();
        Materials_OLD.Palladium = loadPalladium();
        Materials_OLD.Phosphorus = loadPhosphorus();
        Materials_OLD.Platinum = loadPlatinum();
        Materials_OLD.Plutonium = loadPlutonium();
        Materials_OLD.Potassium = loadPotassium();
        Materials_OLD.Praseodymium = loadPraseodymium();
        Materials_OLD.Promethium = loadPromethium();
        Materials_OLD.Radon = loadRadon();
        Materials_OLD.Rubidium = loadRubidium();
        Materials_OLD.Samarium = loadSamarium();
        Materials_OLD.Scandium = loadScandium();
        Materials_OLD.Silicon = loadSilicon();
        Materials_OLD.Silver = loadSilver();
        Materials_OLD.Sodium = loadSodium();
        Materials_OLD.Strontium = loadStrontium();
        Materials_OLD.Sulfur = loadSulfur();
        Materials_OLD.Tantalum = loadTantalum();
        Materials_OLD.Tellurium = loadTellurium();
        Materials_OLD.Terbium = loadTerbium();
        Materials_OLD.Thorium = loadThorium();
        Materials_OLD.Thulium = loadThulium();
        Materials_OLD.Tin = loadTin();
        Materials_OLD.Titanium = loadTitanium();
        Materials_OLD.Tritanium = loadTritanium(); // Not a real element
        Materials_OLD.Tungsten = loadTungsten();
        Materials_OLD.Uranium = loadUranium();
        Materials_OLD.Vanadium = loadVanadium();
        Materials_OLD.Ytterbium = loadYtterbium();
        Materials_OLD.Yttrium = loadYttrium();
        Materials_OLD.Zinc = loadZinc();
    }

    private static Materials_OLD loadAluminium() {
        return new MaterialBuilder().setName("Aluminium")
            .setDefaultLocalName("Aluminium")
            .setElement(Element.Al)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x0080c8f0)
            .setTool(128, 2, 10.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(933)
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addOreByproduct(() -> Materials_OLD.Bauxite)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadAmericium() {
        return new MaterialBuilder().setName("Americium")
            .setDefaultLocalName("Americium")
            .setElement(Element.Am)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00c8c8c8)
            .setMiningLevel(3)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_449)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadAntimony() {
        return new MaterialBuilder().setName("Antimony")
            .setDefaultLocalName("Antimony")
            .setElement(Element.Sb)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00dcdcf0)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(903)
            .addOreByproduct(() -> Materials_OLD.Zinc)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadArgon() {
        return new MaterialBuilder().setName("Argon")
            .setDefaultLocalName("Argon")
            .setElement(Element.Ar)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0xf000ff00)
            .addCell()
            .addPlasma()
            .setMeltingPoint(83)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadArsenic() {
        return new MaterialBuilder().setName("Arsenic")
            .setDefaultLocalName("Arsenic")
            .setElement(Element.As)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeOrange)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addCell()
            .addPlasma()
            .setMeltingPoint(1_090)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadBarium() {
        return new MaterialBuilder().setName("Barium")
            .setDefaultLocalName("Barium")
            .setElement(Element.Ba)
            .setIconSet(TextureSet.SET_METALLIC)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_000)
            .constructMaterial();
    }

    private static Materials_OLD loadBeryllium() {
        return new MaterialBuilder().setName("Beryllium")
            .setDefaultLocalName("Beryllium")
            .setElement(Element.Be)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0064b464)
            .setTool(64, 2, 14.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(1_560)
            .addOreByproduct(() -> Materials_OLD.Emerald)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadBismuth() {
        return new MaterialBuilder().setName("Bismuth")
            .setDefaultLocalName("Bismuth")
            .setElement(Element.Bi)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x0064a0a0)
            .setTool(64, 1, 6.0f)
            .setToolEnchantment(() -> Enchantment.baneOfArthropods, 4)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(544)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadBoron() {
        return new MaterialBuilder().setName("Boron")
            .setDefaultLocalName("Boron")
            .setElement(Element.B)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00d2fad2)
            .addDustItems()
            .addPlasma()
            .setMeltingPoint(2_349)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .constructMaterial();
    }

    private static Materials_OLD loadCaesium() {
        return new MaterialBuilder().setName("Caesium")
            .setDefaultLocalName("Caesium")
            .setElement(Element.Cs)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00b0c4de)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(301)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadCalcium() {
        return new MaterialBuilder().setName("Calcium")
            .setDefaultLocalName("Calcium")
            .setElement(Element.Ca)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00fff5f5)
            .addDustItems()
            .addPlasma()
            .setMeltingPoint(1_115)
            .setBlastFurnaceTemp(1_115)
            .setBlastFurnaceRequired(true)
            .constructMaterial();
    }

    private static Materials_OLD loadCarbon() {
        return new MaterialBuilder().setName("Carbon")
            .setDefaultLocalName("Carbon")
            .setElement(Element.C)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00141414)
            .setTool(64, 2, 1.0f)
            .addDustItems()
            .addOreItems()
            .addMetalItems()
            .addCell()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_800)
            .addSubTag(SubTag.NO_SMELTING)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.sheetmetal)
            .constructMaterial();
    }

    private static Materials_OLD loadCadmium() {
        return new MaterialBuilder().setName("Cadmium")
            .setDefaultLocalName("Cadmium")
            .setElement(Element.Cd)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0x0032323c)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(594)
            .constructMaterial();
    }

    private static Materials_OLD loadCerium() {
        return new MaterialBuilder().setName("Cerium")
            .setDefaultLocalName("Cerium")
            .setElement(Element.Ce)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x007bd490)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_068)
            .setBlastFurnaceTemp(1_068)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadChlorine() {
        return new MaterialBuilder().setName("Chlorine")
            .setDefaultLocalName("Chlorine")
            .setElement(Element.Cl)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .addCell()
            .addPlasma()
            .setMeltingPoint(171)
            .constructMaterial();
    }

    private static Materials_OLD loadChrome() {
        return new MaterialBuilder().setName("Chrome")
            .setDefaultLocalName("Chrome")
            .setElement(Element.Cr)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ffe6e6)
            .setTool(256, 3, 11.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_180)
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadCobalt() {
        return new MaterialBuilder().setName("Cobalt")
            .setDefaultLocalName("Cobalt")
            .setElement(Element.Co)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x005050fa)
            .setTool(512, 3, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_768)
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addOreByproduct(() -> Materials_OLD.Cobaltite)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_SODIUMPERSULFATE)
            .constructMaterial();
    }

    private static Materials_OLD loadCopper() {
        return new MaterialBuilder().setName("Copper")
            .setDefaultLocalName("Copper")
            .setElement(Element.Cu)
            .setIconSet(
                TextureSet.SET_DULL.withCustomTextures(
                    "copper",
                    MaterialIconRegistry.IconType.ORE,
                    MaterialIconRegistry.IconType.ORE_SMALL,
                    MaterialIconRegistry.IconType.ORE_RAW))
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff6400)
            .setMiningLevel(1)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addGearItems()
            .setMeltingPoint(1_357)
            .addOreByproduct(() -> Materials_OLD.Cobalt)
            .addOreByproduct(() -> Materials_OLD.Gold)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .setArcSmeltingIntoWithGas(() -> Materials_OLD.Oxygen, () -> Materials_OLD.AnnealedCopper)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_SODIUMPERSULFATE)
            .addOrePrefix(OrePrefixes.nanite)
            .constructMaterial();
    }

    private static Materials_OLD loadDesh() {
        return new MaterialBuilder().setName("Desh")
            .setDefaultLocalName("Desh")
            .setElement(Element.De)
            .setChemicalFormula("De")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00282828)
            .setTool(1_280, 4, 20.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_500)
            .setBlastFurnaceTemp(2_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadDysprosium() {
        return new MaterialBuilder().setName("Dysprosium")
            .setDefaultLocalName("Dysprosium")
            .setElement(Element.Dy)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x0069d150)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_680)
            .setBlastFurnaceTemp(1_680)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEmpty() {
        return new MaterialBuilder().setName("Empty")
            .setDefaultLocalName("Empty")
            .setElement(Element._NULL)
            .setARGB(0xffffffff)
            .addEmpty()
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.cell)
            .removeOrePrefix(OrePrefixes.bucket)
            .removeOrePrefix(OrePrefixes.bucketClay)
            .constructMaterial();
    }

    private static Materials_OLD loadErbium() {
        return new MaterialBuilder().setName("Erbium")
            .setDefaultLocalName("Erbium")
            .setElement(Element.Er)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00b09851)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_802)
            .setBlastFurnaceTemp(1_802)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEuropium() {
        return new MaterialBuilder().setName("Europium")
            .setDefaultLocalName("Europium")
            .setElement(Element.Eu)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00f6b5ff)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_099)
            .setBlastFurnaceTemp(1_099)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadFlerovium() {
        return new MaterialBuilder().setName("Flerovium_GT5U")
            .setDefaultLocalName("Flerovium")
            .setElement(Element.Fl)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00f1edfa)
            .setColor(Dyes.dyeWhite)
            .setMiningLevel(0)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadFluorine() {
        return new MaterialBuilder().setName("Fluorine")
            .setDefaultLocalName("Fluorine")
            .setElement(Element.F)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x7fffffff)
            .addCell()
            .addPlasma()
            .setMeltingPoint(53)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadFrancium() {
        return new MaterialBuilder().setName("Francium_GT5U")
            .setDefaultLocalName("Francium")
            .setElement(Element.Fr)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff3900)
            .addDustItems()
            .addPlasma()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadGadolinium() {
        return new MaterialBuilder().setName("Gadolinium")
            .setDefaultLocalName("Gadolinium")
            .setElement(Element.Gd)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x003bba1c)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_585)
            .setBlastFurnaceTemp(1_585)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadGallium() {
        return new MaterialBuilder().setName("Gallium")
            .setDefaultLocalName("Gallium")
            .setElement(Element.Ga)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00dcdcff)
            .setTool(64, 2, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(302)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadGold() {
        return new MaterialBuilder().setName("Gold")
            .setDefaultLocalName("Gold")
            .setElement(Element.Au)
            .setIconSet(
                TextureSet.SET_SHINY.withCustomTextures(
                    "gold",
                    MaterialIconRegistry.IconType.ORE,
                    MaterialIconRegistry.IconType.ORE_SMALL,
                    MaterialIconRegistry.IconType.ORE_RAW))
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff1e)
            .setTool(64, 2, 12.0f)
            .setToolEnchantment(() -> Enchantment.smite, 3)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_337)
            .addOreByproduct(() -> Materials_OLD.Copper)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_MERCURY)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.nugget) // minecraft:gold_nugget
            .removeOrePrefix(OrePrefixes.block) // minecraft:gold_block
            .removeOrePrefix(OrePrefixes.ingot) // minecraft:gold_ingot
            .constructMaterial();
    }

    private static Materials_OLD loadHolmium() {
        return new MaterialBuilder().setName("Holmium")
            .setDefaultLocalName("Holmium")
            .setElement(Element.Ho)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x001608a6)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_734)
            .setBlastFurnaceTemp(1_734)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadHydrogen() {
        return new MaterialBuilder().setName("Hydrogen")
            .setDefaultLocalName("Hydrogen")
            .setElement(Element.H)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0xf00000ff)
            .setMiningLevel(2)
            .setFuel(MaterialBuilder.FuelType.Gas, 20)
            .setOreMultiplier(4)
            .addCell()
            .addPlasma()
            .setMeltingPoint(14)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadHelium() {
        return new MaterialBuilder().setName("Helium")
            .setDefaultLocalName("Helium")
            .setElement(Element.He)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0xf0ffff00)
            .setMiningLevel(2)
            .addCell()
            .addPlasma()
            .setMeltingPoint(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadIndium() {
        return new MaterialBuilder().setName("Indium")
            .setDefaultLocalName("Indium")
            .setElement(Element.In)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00400080)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(429)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadIridium() {
        return new MaterialBuilder().setName("Iridium")
            .setDefaultLocalName("Iridium")
            .setElement(Element.Ir)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00f0f0f5)
            .setTool(2_560, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_719)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Platinum)
            .addOreByproduct(() -> Materials_OLD.Osmium)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadIron() {
        return new MaterialBuilder().setName("Iron")
            .setDefaultLocalName("Iron")
            .setElement(Element.Fe)
            .setIconSet(
                TextureSet.SET_METALLIC.withCustomTextures(
                    "iron",
                    MaterialIconRegistry.IconType.ORE,
                    MaterialIconRegistry.IconType.ORE_SMALL,
                    MaterialIconRegistry.IconType.ORE_RAW))
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00c8c8c8)
            .setTool(256, 2, 6.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 1)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_811)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addOreByproduct(() -> Materials_OLD.Tin)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_TRIPLE)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.block) // minecraft:iron_block
            .removeOrePrefix(OrePrefixes.ingot) // minecraft:iron_ingot
            .constructMaterial();
    }

    private static Materials_OLD loadLanthanum() {
        return new MaterialBuilder().setName("Lanthanum")
            .setDefaultLocalName("Lanthanum")
            .setElement(Element.La)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x008a8a8a)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_193)
            .setBlastFurnaceTemp(1_193)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadLead() {
        return new MaterialBuilder().setName("Lead")
            .setDefaultLocalName("Lead")
            .setElement(Element.Pb)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x008c648c)
            .setTool(64, 1, 8.0f)
            .setToolEnchantment(() -> Enchantment.baneOfArthropods, 2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(600)
            .addOreByproduct(() -> Materials_OLD.Silver)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.SOLDERING_MATERIAL)
            .addSubTag(SubTag.SOLDERING_MATERIAL_BAD)
            .constructMaterial();
    }

    private static Materials_OLD loadLithium() {
        return new MaterialBuilder().setName("Lithium")
            .setDefaultLocalName("Lithium")
            .setElement(Element.Li)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00e1dcff)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(454)
            .addOreByproduct(() -> Materials_OLD.Lithium)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadLutetium() {
        return new MaterialBuilder().setName("Lutetium")
            .setDefaultLocalName("Lutetium")
            .setElement(Element.Lu)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00bc3ec7)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_925)
            .setBlastFurnaceTemp(1_925)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMagic() {
        return new MaterialBuilder().setName("Magic")
            .setDefaultLocalName("Magic")
            .setElement(Element.Ma)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyePurple)
            .setARGB(0x006400c8)
            .setTool(5_120, 5, 8.0f)
            .setFuel(MaterialBuilder.FuelType.Magic, 32)
            .addDustItems()
            .addMetalItems()
            .addGemItems()
            .addOreItems()
            .addCell()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_000)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadMagnesium() {
        return new MaterialBuilder().setName("Magnesium")
            .setDefaultLocalName("Magnesium")
            .setElement(Element.Mg)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ffc8c8)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(923)
            .addOreByproduct(() -> Materials_OLD.Olivine)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadManganese() {
        return new MaterialBuilder().setName("Manganese")
            .setDefaultLocalName("Manganese")
            .setElement(Element.Mn)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .setTool(512, 2, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(1_519)
            .addOreByproduct(() -> Materials_OLD.Chrome)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadMercury() {
        return new MaterialBuilder().setName("Mercury")
            .setDefaultLocalName("Mercury")
            .setElement(Element.Hg)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00ffdcdc)
            .setFuel(MaterialBuilder.FuelType.Magic, 32)
            .addCell()
            .addPlasma()
            .setMeltingPoint(234)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.SMELTING_TO_GEM)
            .constructMaterial();
    }

    private static Materials_OLD loadMeteoricIron() {
        return new MaterialBuilder().setName("MeteoricIron")
            .setDefaultLocalName("Meteoric Iron")
            .setElement(Element.SpFe)
            .setChemicalFormula("SpFe")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00643250)
            .setTool(384, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(1_811)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addOreByproduct(() -> Materials_OLD.Iridium)
            .addOreByproduct(() -> Materials_OLD.Platinum)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_TRIPLE)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadMolybdenum() {
        return new MaterialBuilder().setName("Molybdenum")
            .setDefaultLocalName("Molybdenum")
            .setElement(Element.Mo)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00b4b4dc)
            .setTool(512, 2, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(2_896)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadNaquadah() {
        return new MaterialBuilder().setName("Naquadah")
            .setDefaultLocalName("Naquadah")
            .setElement(Element.Nq)
            .setChemicalFormula("Nq")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .setMoltenARGB(0x0000ff00)
            .setTool(1_280, 4, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.NaquadahEnriched)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadNeodymium() {
        return new MaterialBuilder().setName("Neodymium")
            .setDefaultLocalName("Neodymium")
            .setElement(Element.Nd)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00646464)
            .setTool(512, 2, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_297)
            .setBlastFurnaceTemp(1_297)
            .setBlastFurnaceRequired(true)
            .addOreByproduct(() -> Materials_OLD.Monazite)
            .addOreByproduct(() -> Materials_OLD.RareEarth)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadNeutronium() {
        return new MaterialBuilder().setName("Neutronium")
            .setDefaultLocalName("Neutronium")
            .setElement(Element.Nt)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .setTool(655_360, 6, 24.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(10_000)
            .setBlastFurnaceTemp(10_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Neutronium)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addOrePrefix(OrePrefixes.nanite)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_LuV);
    }

    private static Materials_OLD loadNickel() {
        return new MaterialBuilder().setName("Nickel")
            .setDefaultLocalName("Nickel")
            .setElement(Element.Ni)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00c8c8fa)
            .setTool(64, 2, 6.0f)
            .setToolEnchantment(() -> Enchantment.baneOfArthropods, 2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_728)
            .addOreByproduct(() -> Materials_OLD.Cobalt)
            .addOreByproduct(() -> Materials_OLD.Platinum)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_SODIUMPERSULFATE)
            .constructMaterial();
    }

    private static Materials_OLD loadNiobium() {
        return new MaterialBuilder().setName("Niobium")
            .setDefaultLocalName("Niobium")
            .setElement(Element.Nb)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00beb4c8)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(2_750)
            .setBlastFurnaceTemp(2_750)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadNitrogen() {
        return new MaterialBuilder().setName("Nitrogen")
            .setDefaultLocalName("Nitrogen")
            .setElement(Element.N)
            .setIconSet(TextureSet.SET_GEM_HORIZONTAL)
            .setColor(Dyes.dyeCyan)
            .setARGB(0xf00096c8)
            .setMiningLevel(2)
            .setOreMultiplier(4)
            .addCell()
            .addPlasma()
            .setMeltingPoint(63)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadNULL() {
        return new MaterialBuilder().setName("NULL")
            .setDefaultLocalName("NULL")
            .setElement(Element._NULL)
            .constructMaterial();
    }

    private static Materials_OLD loadOriharukon() {
        return new MaterialBuilder().setName("Oriharukon")
            .setDefaultLocalName("Oriharukon")
            .setElement(Element.Oh)
            .setChemicalFormula("Oh")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00677d68)
            .setTool(10_240, 5, 32.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadOsmium() {
        return new MaterialBuilder().setName("Osmium")
            .setDefaultLocalName("Osmium")
            .setElement(Element.Os)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x003232ff)
            .setTool(1_280, 4, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_306)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Iridium)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_MERCURY)
            .constructMaterial();
    }

    private static Materials_OLD loadOxygen() {
        return new MaterialBuilder().setName("Oxygen")
            .setDefaultLocalName("Oxygen")
            .setElement(Element.O)
            .setIconSet(TextureSet.SET_GEM_VERTICAL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0xf00064c8)
            .setMiningLevel(2)
            .setOreMultiplier(4)
            .addCell()
            .addPlasma()
            .setMeltingPoint(54)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadPalladium() {
        return new MaterialBuilder().setName("Palladium")
            .setDefaultLocalName("Palladium")
            .setElement(Element.Pd)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0xB1B1B1)
            .setTool(512, 4, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_828)
            .setBlastFurnaceTemp(1_828)
            .setBlastFurnaceRequired(true)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphorus() {
        return new MaterialBuilder().setName("Phosphorus")
            .setDefaultLocalName("Phosphorus")
            .setElement(Element.P)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .addDustItems()
            .addPlasma()
            .setMeltingPoint(317)
            .addOreByproduct(() -> Materials_OLD.Phosphate)
            .constructMaterial();
    }

    private static Materials_OLD loadPlatinum() {
        return new MaterialBuilder().setName("Platinum")
            .setDefaultLocalName("Platinum")
            .setElement(Element.Pt)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ffffc8)
            .setTool(64, 4, 12.0f)
            .setToolEnchantment(() -> Enchantment.smite, 5)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_041)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addOreByproduct(() -> Materials_OLD.Iridium)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_MERCURY)
            .constructMaterial();
    }

    private static Materials_OLD loadPlutonium() {
        return new MaterialBuilder().setName("Plutonium")
            .setDefaultLocalName("Plutonium 239")
            .setElement(Element.Pu)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00f03232)
            .setTool(512, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .setMeltingPoint(912)
            .addOreByproduct(() -> Materials_OLD.Uranium)
            .addOreByproduct(() -> Materials_OLD.Lead)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadPotassium() {
        return new MaterialBuilder().setName("Potassium")
            .setDefaultLocalName("Potassium")
            .setElement(Element.K)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x009aacdf)
            .addDustItems()
            .addMetalItems()
            .addPlasma()
            .setMeltingPoint(336)
            .constructMaterial();
    }

    private static Materials_OLD loadPraseodymium() {
        return new MaterialBuilder().setName("Praseodymium")
            .setDefaultLocalName("Praseodymium")
            .setElement(Element.Pr)
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x0075d681)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_208)
            .setBlastFurnaceTemp(1_208)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadPromethium() {
        return new MaterialBuilder().setName("Promethium")
            .setDefaultLocalName("Promethium")
            .setElement(Element.Pm)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x0024b535)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_315)
            .setBlastFurnaceTemp(1_315)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadRadon() {
        return new MaterialBuilder().setName("Radon")
            .setDefaultLocalName("Radon")
            .setElement(Element.Rn)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePurple)
            .setARGB(0xf0ff00ff)
            .addCell()
            .addPlasma()
            .setMeltingPoint(202)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadRubidium() {
        return new MaterialBuilder().setName("Rubidium")
            .setDefaultLocalName("Rubidium")
            .setElement(Element.Rb)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00f01e1e)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(312)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadSamarium() {
        return new MaterialBuilder().setName("Samarium")
            .setDefaultLocalName("Samarium")
            .setElement(Element.Sm)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffffcc)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_345)
            .setBlastFurnaceTemp(1_345)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadScandium() {
        return new MaterialBuilder().setName("Scandium")
            .setDefaultLocalName("Scandium")
            .setElement(Element.Sc)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00cccccc)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .setMeltingPoint(1_814)
            .setBlastFurnaceTemp(1_814)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadSilicon() {
        return new MaterialBuilder().setName("Silicon")
            .setDefaultLocalName("Raw Silicon")
            .setElement(Element.Si)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x003c3c50)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(2_273)
            .setBlastFurnaceTemp(2_273)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSilver() {
        return new MaterialBuilder().setName("Silver")
            .setDefaultLocalName("Silver")
            .setElement(Element.Ag)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00dcdcff)
            .setTool(64, 2, 10.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_234)
            .addOreByproduct(() -> Materials_OLD.Lead)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_MERCURY_99_PERCENT)
            .addOrePrefix(OrePrefixes.nanite)
            .constructMaterial();
    }

    private static Materials_OLD loadSodium() {
        return new MaterialBuilder().setName("Sodium")
            .setDefaultLocalName("Sodium")
            .setElement(Element.Na)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00000096)
            .addDustItems()
            .addCell()
            .addPlasma()
            .setMeltingPoint(370)
            .constructMaterial();
    }

    private static Materials_OLD loadStrontium() {
        return new MaterialBuilder().setName("Strontium")
            .setDefaultLocalName("Strontium")
            .setElement(Element.Sr)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00c8c8c8)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addGearItems()
            .setMeltingPoint(1_050)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadSulfur() {
        return new MaterialBuilder().setName("Sulfur")
            .setDefaultLocalName("Sulfur")
            .setElement(Element.S)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00c8c800)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(388)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadTantalum() {
        return new MaterialBuilder().setName("Tantalum")
            .setDefaultLocalName("Tantalum")
            .setElement(Element.Ta)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x0069b7ff)
            .setTool(2_560, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(3_290)
            .setBlastFurnaceTemp(3_290)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadTellurium() {
        return new MaterialBuilder().setName("Tellurium")
            .setDefaultLocalName("Tellurium")
            .setElement(Element.Te)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00ceff56)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(722)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTerbium() {
        return new MaterialBuilder().setName("Terbium")
            .setDefaultLocalName("Terbium")
            .setElement(Element.Tb)
            .setIconSet(TextureSet.SET_METALLIC)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_629)
            .setBlastFurnaceTemp(1_629)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadThorium() {
        return new MaterialBuilder().setName("Thorium")
            .setDefaultLocalName("Thorium")
            .setElement(Element.Th)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00001e00)
            .setTool(512, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(2_115)
            .addOreByproduct(() -> Materials_OLD.Uranium)
            .addOreByproduct(() -> Materials_OLD.Lead)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadThulium() {
        return new MaterialBuilder().setName("Thulium")
            .setDefaultLocalName("Thulium")
            .setElement(Element.Tm)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00596bc2)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_818)
            .setBlastFurnaceTemp(1_818)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTin() {
        return new MaterialBuilder().setName("Tin")
            .setDefaultLocalName("Tin")
            .setElement(Element.Sn)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdcdc)
            .setMiningLevel(3)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addGearItems()
            .setMeltingPoint(505)
            .setBlastFurnaceTemp(505)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Zinc)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.SOLDERING_MATERIAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTitanium() {
        return new MaterialBuilder().setName("Titanium")
            .setDefaultLocalName("Titanium")
            .setElement(Element.Ti)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00dca0f0)
            .setTool(1_600, 3, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_941)
            .setBlastFurnaceTemp(1_940)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Almandine)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadTritanium() {
        return new MaterialBuilder().setName("Tritanium")
            .setDefaultLocalName("Tritanium")
            .setElement(Element.Tn)
            .setChemicalFormula("Tn")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00600000)
            .setTool(1_435_392, 6, 20.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(9_900)
            .setBlastFurnaceTemp(9_900)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTungsten() {
        return new MaterialBuilder().setName("Tungsten")
            .setDefaultLocalName("Tungsten")
            .setElement(Element.W)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .setTool(2_560, 3, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_695)
            .setBlastFurnaceTemp(3_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Manganese)
            .addOreByproduct(() -> Materials_OLD.Molybdenum)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadUranium() {
        return new MaterialBuilder().setName("Uranium")
            .setDefaultLocalName("Uranium 238")
            .setElement(Element.U)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0032f032)
            .setTool(512, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(1_405)
            .addOreByproduct(() -> Materials_OLD.Lead)
            .addOreByproduct(() -> Materials_OLD.Uranium235)
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addSubTag(SubTag.METAL)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadVanadium() {
        return new MaterialBuilder().setName("Vanadium")
            .setDefaultLocalName("Vanadium")
            .setElement(Element.V)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(2_183)
            .setBlastFurnaceTemp(2_183)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadYtterbium() {
        return new MaterialBuilder().setName("Ytterbium")
            .setDefaultLocalName("Ytterbium")
            .setElement(Element.Yb)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x002cc750)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_097)
            .setBlastFurnaceTemp(1_097)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadYttrium() {
        return new MaterialBuilder().setName("Yttrium")
            .setDefaultLocalName("Yttrium")
            .setElement(Element.Y)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00dcfadc)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(1_799)
            .setBlastFurnaceTemp(1_799)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadZinc() {
        return new MaterialBuilder().setName("Zinc")
            .setDefaultLocalName("Zinc")
            .setElement(Element.Zn)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00faf0f0)
            .setMiningLevel(1)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .setMeltingPoint(692)
            .addOreByproduct(() -> Materials_OLD.Tin)
            .addOreByproduct(() -> Materials_OLD.Gallium)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.WASHING_SODIUMPERSULFATE)
            .constructMaterial();
    }

    private static void loadIsotopes() {
        Materials_OLD.Deuterium = loadDeuterium();
        Materials_OLD.Helium3 = loadHelium3();
        Materials_OLD.Plutonium241 = loadPlutonium241();
        Materials_OLD.Tritium = loadTritium();
        Materials_OLD.Uranium235 = loadUranium235();
    }

    private static Materials_OLD loadDeuterium() {
        return new MaterialBuilder().setName("Deuterium")
            .setDefaultLocalName("Deuterium")
            .setElement(Element.D)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0xf0ffff00)
            .addCell()
            .addPlasma()
            .setMeltingPoint(14)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadHelium3() {
        return new MaterialBuilder().setName("Helium_3")
            .setDefaultLocalName("Helium-3")
            .setElement(Element.He_3)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0xf0ffff00)
            .addCell()
            .setMeltingPoint(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPlutonium241() {
        return new MaterialBuilder().setName("Plutonium241")
            .setDefaultLocalName("Plutonium 241")
            .setElement(Element.Pu_241)
            .setChemicalFormula(
                CustomGlyphs.SUPERSCRIPT2 + CustomGlyphs.SUPERSCRIPT4 + CustomGlyphs.SUPERSCRIPT1 + "Pu")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00fa4646)
            .setTool(512, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(912)
            .addSubTag(SubTag.METAL)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadTritium() {
        return new MaterialBuilder().setName("Tritium")
            .setDefaultLocalName("Tritium")
            .setElement(Element.T)
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0xf0ff0000)
            .addCell()
            .addPlasma()
            .setMeltingPoint(14)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadUranium235() {
        return new MaterialBuilder().setName("Uranium235")
            .setDefaultLocalName("Uranium 235")
            .setElement(Element.U_235)
            .setChemicalFormula(CustomGlyphs.SUPERSCRIPT2 + CustomGlyphs.SUPERSCRIPT3 + CustomGlyphs.SUPERSCRIPT5 + "U")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0046fa46)
            .setTool(512, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(1_405)
            .addSubTag(SubTag.METAL)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static void loadWaterLine() {
        Materials_OLD.FlocculationWasteLiquid = loadFlocculationWasteLiquid();
        Materials_OLD.Grade1PurifiedWater = loadGrade1PurifiedWater();
        Materials_OLD.Grade2PurifiedWater = loadGrade2PurifiedWater();
        Materials_OLD.Grade3PurifiedWater = loadGrade3PurifiedWater();
        Materials_OLD.Grade4PurifiedWater = loadGrade4PurifiedWater();
        Materials_OLD.Grade5PurifiedWater = loadGrade5PurifiedWater();
        Materials_OLD.Grade6PurifiedWater = loadGrade6PurifiedWater();
        Materials_OLD.Grade7PurifiedWater = loadGrade7PurifiedWater();
        Materials_OLD.Grade8PurifiedWater = loadGrade8PurifiedWater();
    }

    private static Materials_OLD loadFlocculationWasteLiquid() {
        return new MaterialBuilder().setName("FlocculationWasteLiquid")
            .setDefaultLocalName("Flocculation Waste Liquid")
            .setChemicalFormula("Al₂(OH)₃??Cl₃")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x003d3a52)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade1PurifiedWater() {
        return new MaterialBuilder().setName("Grade1PurifiedWater")
            .setDefaultLocalName("Filtered Water (Grade 1)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x003f4cfd)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade2PurifiedWater() {
        return new MaterialBuilder().setName("Grade2PurifiedWater")
            .setDefaultLocalName("Ozonated Water (Grade 2)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x005d5dfe)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade3PurifiedWater() {
        return new MaterialBuilder().setName("Grade3PurifiedWater")
            .setDefaultLocalName("Flocculated Water (Grade 3)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00736dfe)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade4PurifiedWater() {
        return new MaterialBuilder().setName("Grade4PurifiedWater")
            .setDefaultLocalName("pH Neutralized Water (Grade 4)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00877eff)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade5PurifiedWater() {
        return new MaterialBuilder().setName("Grade5PurifiedWater")
            .setDefaultLocalName("Extreme-Temperature Treated Water (Grade 5)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x009890ff)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade6PurifiedWater() {
        return new MaterialBuilder().setName("Grade6PurifiedWater")
            .setDefaultLocalName("Ultraviolet Treated Electrically Neutral Water (Grade 6)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00a8a1ff)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade7PurifiedWater() {
        return new MaterialBuilder().setName("Grade7PurifiedWater")
            .setDefaultLocalName("Degassed Decontaminant-Free Water (Grade 7)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00b7b3ff)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrade8PurifiedWater() {
        return new MaterialBuilder().setName("Grade8PurifiedWater")
            .setDefaultLocalName("Subatomically Perfect Water (Grade 8)")
            .setChemicalFormula("H₂O")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00c5c5ff)
            .addCell()
            .addFluid()
            .setMeltingPoint(273)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static void loadRandom() {
        Materials_OLD.AnyBronze = loadAnyBronze();
        Materials_OLD.AnyCopper = loadAnyCopper();
        Materials_OLD.AnyCarbon = loadAnyCarbon();
        Materials_OLD.AnyIron = loadAnyIron();
        Materials_OLD.AnyRubber = loadAnyRubber();
        Materials_OLD.AnySyntheticRubber = loadAnySyntheticRubber();
        Materials_OLD.BrickNether = loadBrickNether();
        Materials_OLD.Cobblestone = loadCobblestone();
        Materials_OLD.Crystal = loadCrystal();
        Materials_OLD.Metal = loadMetal();
        Materials_OLD.Organic = loadOrganic();
        Materials_OLD.Quartz = loadQuartz();
        Materials_OLD.Unknown = loadUnknown();
    }

    private static Materials_OLD loadAnyBronze() {
        return new MaterialBuilder().setName("AnyBronze")
            .setDefaultLocalName("AnyBronze")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_SHINY)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAnyCopper() {
        return new MaterialBuilder().setName("AnyCopper")
            .setDefaultLocalName("AnyCopper")
            .setChemicalFormula("Cu")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_SHINY)
            .setSmeltingInto(() -> Materials_OLD.Copper)
            .setMaceratingInto(() -> Materials_OLD.Copper)
            .setArcSmeltingIntoWithGas(() -> Materials_OLD.Oxygen, () -> Materials_OLD.AnnealedCopper)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAnyCarbon() {
        return new MaterialBuilder().setName("AnyCarbon")
            .setDefaultLocalName("AnyCarbon")
            .setChemicalFormula("C")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_DULL)
            .constructMaterial();
    }

    private static Materials_OLD loadAnyIron() {
        return new MaterialBuilder().setName("AnyIron")
            .setDefaultLocalName("AnyIron")
            .setChemicalFormula("Fe")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_SHINY)
            .setSmeltingInto(() -> Materials_OLD.Iron)
            .setMaceratingInto(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAnyRubber() {
        return new MaterialBuilder().setName("AnyRubber")
            .setDefaultLocalName("AnyRubber")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_SHINY)
            .setMaceratingInto(() -> Materials_OLD.Rubber)
            .constructMaterial();
    }

    private static Materials_OLD loadAnySyntheticRubber() {
        return new MaterialBuilder().setName("AnySyntheticRubber")
            .setDefaultLocalName("AnySyntheticRubber")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_SHINY)
            .constructMaterial();
    }

    private static Materials_OLD loadBrickNether() {
        return new MaterialBuilder().setName("BrickNether")
            .setDefaultLocalName("BrickNether")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_DULL)
            .removeOrePrefix(OrePrefixes.ingot) // minecraft:netherbrick
            .constructMaterial();
    }

    private static Materials_OLD loadCobblestone() {
        return new MaterialBuilder().setName("Cobblestone")
            .setDefaultLocalName("Cobblestone")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_DULL)
            .constructMaterial();
    }

    private static Materials_OLD loadCrystal() {
        return new MaterialBuilder().setName("Crystal")
            .setDefaultLocalName("Crystal")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_SHINY)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadMetal() {
        return new MaterialBuilder().setName("Metal")
            .setDefaultLocalName("Metal")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_METALLIC)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadOrganic() {
        return new MaterialBuilder().setName("Organic")
            .setDefaultLocalName("Organic")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_LEAF)
            .constructMaterial();
    }

    private static Materials_OLD loadQuartz() {
        return new MaterialBuilder().setName("Quartz")
            .setDefaultLocalName("Quartz")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_QUARTZ)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .constructMaterial();
    }

    private static Materials_OLD loadUnknown() {
        return new MaterialBuilder().setName("Unknown")
            .setDefaultLocalName("Unknown")
            .setUnifiable(false)
            .setIconSet(TextureSet.SET_DULL)
            .constructMaterial();
    }

    private static void loadDontCare() {
        Materials_OLD.Alfium = loadAlfium();
        Materials_OLD.Aquamarine = loadAquamarine();
        Materials_OLD.DarkThaumium = loadDarkThaumium();
        Materials_OLD.Draconium = loadDraconium();
        Materials_OLD.DraconiumAwakened = loadDraconiumAwakened();
        Materials_OLD.Ender = loadEnder();
        Materials_OLD.Endium = loadEndium();
        Materials_OLD.Fluix = loadFluix();
        Materials_OLD.Flux = loadFlux();
        Materials_OLD.InfusedTeslatite = loadInfusedTeslatite();
        Materials_OLD.IridiumSodiumOxide = loadIridiumSodiumOxide();
        Materials_OLD.Mutation = loadMutation();
        Materials_OLD.OsmiumTetroxide = loadOsmiumTetroxide();
        Materials_OLD.PhasedGold = loadPhasedGold();
        Materials_OLD.PhasedIron = loadPhasedIron();
        Materials_OLD.PlatinumGroupSludge = loadPlatinumGroupSludge();
        Materials_OLD.PurpleAlloy = loadPurpleAlloy();
        Materials_OLD.RubberTreeSap = loadRubberTreeSap();
        Materials_OLD.Serpentine = loadSerpentine();
        Materials_OLD.SodiumPeroxide = loadSodiumPeroxide();
        Materials_OLD.Teslatite = loadTeslatite();
    }

    private static Materials_OLD loadAlfium() {
        return new MaterialBuilder().setName("Alfium")
            .setDefaultLocalName("Alfium")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadAquamarine() {
        return new MaterialBuilder().setName("Aquamarine")
            .setDefaultLocalName("Aquamarine")
            .addDustItems()
            .addGemItems()
            .constructMaterial();
    }

    private static Materials_OLD loadDarkThaumium() {
        return new MaterialBuilder().setName("DarkThaumium")
            .setDefaultLocalName("Dark Thaumium")
            .addDustItems()
            .addMetalItems()
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadDraconium() {
        return new MaterialBuilder().setName("Draconium")
            .setDefaultLocalName("Draconium")
            .setChemicalFormula("D")
            .setIconSet(TextureSet.SET_DRACONIUM)
            .setColor(Dyes.dyePink)
            .setARGB(0x007a44b0)
            .setTool(32_768, 7, 20.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_000)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadDraconiumAwakened() {
        return new MaterialBuilder().setName("DraconiumAwakened")
            .setDefaultLocalName("Awakened Draconium")
            .setChemicalFormula("D*")
            .setIconSet(TextureSet.SET_AWOKEN_DRACONIUM)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00f44e00)
            .setTool(65_536, 9, 40.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_900)
            .setBlastFurnaceTemp(9_900)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .addSubTag(SubTag.METAL)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadEnder() {
        return new MaterialBuilder().setName("Ender")
            .setDefaultLocalName("Ender")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadEndium() {
        return new MaterialBuilder().setName("HeeEndium")
            .setDefaultLocalName("Endium")
            .setChemicalFormula("Em")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00a5dcfa)
            .setTool(1_024, 4, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadFluix() {
        return new MaterialBuilder().setName("Fluix")
            .setDefaultLocalName("Fluix")
            .addDustItems()
            .addGemItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .constructMaterial();
    }

    private static Materials_OLD loadFlux() {
        return new MaterialBuilder().setName("Flux")
            .setDefaultLocalName("Flux")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedTeslatite() {
        return new MaterialBuilder().setName("InfusedTeslatite")
            .setDefaultLocalName("Infused Teslatite")
            .setARGB(0x0064b4ff)
            .constructMaterial();
    }

    private static Materials_OLD loadIridiumSodiumOxide() {
        return new MaterialBuilder().setName("IridiumSodiumOxide")
            .setDefaultLocalName("Iridium Sodium Oxide")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadMutation() {
        return new MaterialBuilder().setName("Mutation")
            .setDefaultLocalName("Mutation")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadOsmiumTetroxide() {
        return new MaterialBuilder().setName("OsmiumTetroxide")
            .setDefaultLocalName("Osmium Tetroxide")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadPhasedGold() {
        return new MaterialBuilder().setName("PhasedGold")
            .setDefaultLocalName("Phased Gold")
            .addDustItems()
            .addMetalItems()
            .setBlastFurnaceTemp(1_800)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadPhasedIron() {
        return new MaterialBuilder().setName("PhasedIron")
            .setDefaultLocalName("Phased Iron")
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(3_300)
            .setBlastFurnaceTemp(3_300)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadPlatinumGroupSludge() {
        return new MaterialBuilder().setName("PlatinumGroupSludge")
            .setDefaultLocalName("Platinum Group Sludge")
            .setChemicalFormula(
                "(SiO₂)" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK
                    + "Au"
                    + CustomGlyphs.SUBSCRIPT_QUESTION_MARK
                    + "Pt"
                    + CustomGlyphs.SUBSCRIPT_QUESTION_MARK
                    + "Pd"
                    + CustomGlyphs.SUBSCRIPT_QUESTION_MARK
                    + "??")
            .setIconSet(TextureSet.SET_POWDER)
            .setARGB(0x00001e00)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadPurpleAlloy() {
        return new MaterialBuilder().setName("PurpleAlloy")
            .setDefaultLocalName("Purple Alloy")
            .setARGB(0x0064b4ff)
            .constructMaterial();
    }

    private static Materials_OLD loadRubberTreeSap() {
        return new MaterialBuilder().setName("RubberTreeSap")
            .setDefaultLocalName("Rubber Tree Sap")
            .constructMaterial();
    }

    private static Materials_OLD loadSerpentine() {
        return new MaterialBuilder().setName("Serpentine")
            .setDefaultLocalName("Serpentine")
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumPeroxide() {
        return new MaterialBuilder().setName("SodiumPeroxide")
            .setDefaultLocalName("Sodium Peroxide")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadTeslatite() {
        return new MaterialBuilder().setName("Teslatite")
            .setDefaultLocalName("Teslatite")
            .setARGB(0x003cb4c8)
            .setMiningLevel(2)
            .addDustItems()
            .setOreMultiplier(5) // No .addOreItems?
            .addOreByproduct(() -> Materials_OLD.Diamond)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.STONE)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static void loadUnknownComponents() {
        Materials_OLD.Adamantium = loadAdamantium();
        Materials_OLD.Adamite = loadAdamite();
        Materials_OLD.Adluorite = loadAdluorite();
        Materials_OLD.Agate = loadAgate();
        Materials_OLD.Alduorite = loadAlduorite();
        Materials_OLD.Amber = loadAmber();
        Materials_OLD.Ammonium = loadAmmonium();
        Materials_OLD.Amordrine = loadAmordrine();
        Materials_OLD.AncientDebris = loadAncientDebris();
        Materials_OLD.Andesite = loadAndesite();
        Materials_OLD.Ardite = loadArdite();
        Materials_OLD.Aredrite = loadAredrite();
        Materials_OLD.Bitumen = loadBitumen();
        Materials_OLD.Black = loadBlack();
        Materials_OLD.Blizz = loadBlizz();
        Materials_OLD.Bloodstone = loadBloodstone();
        Materials_OLD.Bluestone = loadBluestone();
        Materials_OLD.Blutonium = loadBlutonium();
        Materials_OLD.CertusQuartz = loadCertusQuartz();
        Materials_OLD.CertusQuartzCharged = loadCertusQuartzCharged();
        Materials_OLD.Ceruclase = loadCeruclase();
        Materials_OLD.Chimerite = loadChimerite();
        Materials_OLD.Chrysocolla = loadChrysocolla();
        Materials_OLD.Citrine = loadCitrine();
        Materials_OLD.CobaltHexahydrate = loadCobaltHexahydrate();
        Materials_OLD.ConstructionFoam = loadConstructionFoam();
        Materials_OLD.Coral = loadCoral();
        Materials_OLD.CrudeOil = loadCrudeOil();
        Materials_OLD.CrystalFlux = loadCrystalFlux();
        Materials_OLD.Cyanite = loadCyanite();
        Materials_OLD.DarkIron = loadDarkIron();
        Materials_OLD.DarkStone = loadDarkStone();
        Materials_OLD.Demonite = loadDemonite();
        Materials_OLD.Desichalkos = loadDesichalkos();
        Materials_OLD.Dilithium = loadDilithium();
        Materials_OLD.Draconic = loadDraconic();
        Materials_OLD.Drulloy = loadDrulloy();
        Materials_OLD.Duranium = loadDuranium();
        Materials_OLD.ElectrumFlux = loadElectrumFlux();
        Materials_OLD.Emery = loadEmery();
        Materials_OLD.EnderiumBase = loadEnderiumBase();
        Materials_OLD.Energized = loadEnergized();
        Materials_OLD.FierySteel = loadFierySteel();
        Materials_OLD.Firestone = loadFirestone();
        Materials_OLD.Fluorite = loadFluorite();
        Materials_OLD.Force = loadForce();
        Materials_OLD.Forcicium = loadForcicium();
        Materials_OLD.Forcillium = loadForcillium();
        Materials_OLD.Glowstone = loadGlowstone();
        Materials_OLD.Graphene = loadGraphene();
        Materials_OLD.Graphite = loadGraphite();
        Materials_OLD.Greenstone = loadGreenstone();
        Materials_OLD.Hematite = loadHematite();
        Materials_OLD.HSLA = loadHSLA();
        Materials_OLD.Infernal = loadInfernal();
        Materials_OLD.InfusedAir = loadInfusedAir();
        Materials_OLD.InfusedDull = loadInfusedDull();
        Materials_OLD.InfusedEarth = loadInfusedEarth();
        Materials_OLD.InfusedEntropy = loadInfusedEntropy();
        Materials_OLD.InfusedFire = loadInfusedFire();
        Materials_OLD.InfusedGold = loadInfusedGold();
        Materials_OLD.InfusedOrder = loadInfusedOrder();
        Materials_OLD.InfusedVis = loadInfusedVis();
        Materials_OLD.InfusedWater = loadInfusedWater();
        Materials_OLD.Invisium = loadInvisium();
        Materials_OLD.Jade = loadJade();
        Materials_OLD.Lava = loadLava();
        Materials_OLD.Limestone = loadLimestone();
        Materials_OLD.Magma = loadMagma();
        Materials_OLD.Mawsitsit = loadMawsitsit();
        Materials_OLD.Mercassium = loadMercassium();
        Materials_OLD.MeteoricSteel = loadMeteoricSteel();
        Materials_OLD.Meteorite = loadMeteorite();
        Materials_OLD.Migmatite = loadMigmatite();
        Materials_OLD.Mimichite = loadMimichite();
        Materials_OLD.Moonstone = loadMoonstone();
        Materials_OLD.NaquadahAlloy = loadNaquadahAlloy();
        Materials_OLD.NaquadahEnriched = loadNaquadahEnriched();
        Materials_OLD.Naquadria = loadNaquadria();
        Materials_OLD.Nether = loadNether();
        Materials_OLD.NetherBrick = loadNetherBrick();
        Materials_OLD.NetherQuartz = loadNetherQuartz();
        Materials_OLD.NetherStar = loadNetherStar();
        Materials_OLD.ObsidianFlux = loadObsidianFlux();
        Materials_OLD.Oilsands = loadOilsands();
        Materials_OLD.Onyx = loadOnyx();
        Materials_OLD.Orichalcum = loadOrichalcum();
        Materials_OLD.Osmonium = loadOsmonium();
        Materials_OLD.Painite = loadPainite();
        Materials_OLD.Peanutwood = loadPeanutwood();
        Materials_OLD.Petroleum = loadPetroleum();
        Materials_OLD.Pewter = loadPewter();
        Materials_OLD.Phoenixite = loadPhoenixite();
        Materials_OLD.Quartzite = loadQuartzite();
        Materials_OLD.Randomite = loadRandomite();
        Materials_OLD.Rubracium = loadRubracium();
        Materials_OLD.Sand = loadSand();
        Materials_OLD.Siltstone = loadSiltstone();
        Materials_OLD.Spinel = loadSpinel();
        Materials_OLD.Sunstone = loadSunstone();
        Materials_OLD.Tar = loadTar();
        Materials_OLD.Tartarite = loadTartarite();
        Materials_OLD.UUAmplifier = loadUUAmplifier();
        Materials_OLD.UUMatter = loadUUMatter();
        Materials_OLD.Void = loadVoid();
        Materials_OLD.Voidstone = loadVoidstone();
        Materials_OLD.Vulcanite = loadVulcanite();
        Materials_OLD.Vyroxeres = loadVyroxeres();
        Materials_OLD.Yellorium = loadYellorium();
        Materials_OLD.Zectium = loadZectium();
    }

    private static Materials_OLD loadAdamantium() {
        return new MaterialBuilder().setName("Adamantium")
            .setDefaultLocalName("Adamantium")
            .setChemicalFormula("Ad")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setTool(8_192, 10, 32.0f)
            .setTurbine(1.0f, 5.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(7_200)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadAdamite() {
        return new MaterialBuilder().setName("Adamite")
            .setDefaultLocalName("Adamite")
            .setMiningLevel(3)
            .setColor(Dyes.dyeLightGray)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadAdluorite() {
        return new MaterialBuilder().setName("Adluorite")
            .setDefaultLocalName("Adluorite")
            .setMiningLevel(2)
            .setColor(Dyes.dyeLightBlue)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAgate() {
        return new MaterialBuilder().setName("Agate")
            .setDefaultLocalName("Agate")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadAlduorite() {
        return new MaterialBuilder().setName("Alduorite")
            .setDefaultLocalName("Alduorite")
            .setChemicalFormula("SpAl")
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x009fb4b4)
            .setTool(8_192, 1, 32.0f)
            .setTurbine(6.0f, 1.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(6_600)
            .setBlastFurnaceTemp(6_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAmber() {
        return new MaterialBuilder().setName("Amber")
            .setDefaultLocalName("Amber")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x7fff8000)
            .setTool(128, 2, 4.0f)
            .setToolEnchantment(() -> Enchantment.silkTouch, 1)
            .setFuel(MaterialBuilder.FuelType.Magic, 32)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 10)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addMaterial(Materials_OLD.Oxygen, 16)
            .addOreByproduct(() -> Materials_OLD.Amber)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadAmmonium() {
        return new MaterialBuilder().setName("Ammonium")
            .setDefaultLocalName("Ammonium")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadAmordrine() {
        return new MaterialBuilder().setName("Amordrine")
            .setDefaultLocalName("Amordrine")
            .setTool(64, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAncientDebris() {
        return new MaterialBuilder().setName("Debris")
            .setDefaultLocalName("Ancient Debris")
            .setARGB(0x351a0b)
            .constructMaterial();
    }

    private static Materials_OLD loadAndesite() {
        return new MaterialBuilder().setName("Andesite")
            .setDefaultLocalName("Andesite")
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadArdite() {
        return new MaterialBuilder().setName("Ardite")
            .setDefaultLocalName("Ardite")
            .setChemicalFormula("Ai")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00fa8100)
            .setTool(1_024, 4, 18.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_600)
            .setBlastFurnaceTemp(1_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadAredrite() {
        return new MaterialBuilder().setName("Aredrite")
            .setDefaultLocalName("Aredrite")
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ff0000)
            .setTool(64, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadBitumen() {
        return new MaterialBuilder().setName("Bitumen")
            .setDefaultLocalName("Bitumen")
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadBlack() {
        return new MaterialBuilder().setName("Black")
            .setDefaultLocalName("Black")
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00000000)
            .constructMaterial();
    }

    private static Materials_OLD loadBlizz() {
        return new MaterialBuilder().setName("Blizz")
            .setDefaultLocalName("Blizz")
            .setChemicalFormula("❆Ma")
            .setIconSet(TextureSet.SET_BLIZZ)
            .setARGB(0x00dce9ff)
            .addDustItems()
            .setAutoGeneratedRecycleRecipes(false)
            .constructMaterial();
    }

    private static Materials_OLD loadBloodstone() {
        return new MaterialBuilder().setName("Bloodstone")
            .setDefaultLocalName("Bloodstone")
            .setColor(Dyes.dyeRed)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadBluestone() {
        return new MaterialBuilder().setName("Bluestone")
            .setDefaultLocalName("Bluestone")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlue)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadBlutonium() {
        return new MaterialBuilder().setName("Blutonium")
            .setDefaultLocalName("Blutonium")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000000ff)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadCertusQuartz() {
        return new MaterialBuilder().setName("CertusQuartz")
            .setDefaultLocalName("Certus Quartz")
            .setChemicalFormula("SiO₂")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00d2d2e6)
            .setTool(32, 1, 5.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addOreByproduct(() -> Materials_OLD.Quartzite)
            .addOreByproduct(() -> Materials_OLD.Barite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadCertusQuartzCharged() {
        return new MaterialBuilder().setName("ChargedCertusQuartz")
            .setDefaultLocalName("Charged Certus Quartz")
            .setChemicalFormula("SiO₂" + CustomGlyphs.HIGH_VOLTAGE)
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00ddddec)
            .setTool(32, 1, 5.0f)
            .addOreItems()
            .setOreMultiplier(2)
            .addOreByproduct(() -> Materials_OLD.CertusQuartz)
            .addOreByproduct(() -> Materials_OLD.Quartzite)
            .addOreByproduct(() -> Materials_OLD.Barite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .removeOrePrefix(OrePrefixes.dust) // dreamcraft:item.ChargedCertusQuartzDust
            .constructMaterial();
    }

    private static Materials_OLD loadCeruclase() {
        return new MaterialBuilder().setName("Ceruclase")
            .setDefaultLocalName("Ceruclase")
            .setChemicalFormula("SpAg")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x008cbdd0)
            .setTool(1_280, 2, 32.0f)
            .setTurbine(1.0f, 22.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(6_600)
            .setBlastFurnaceTemp(6_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadChimerite() {
        return new MaterialBuilder().setName("Chimerite")
            .setDefaultLocalName("Chimerite")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadChrysocolla() {
        return new MaterialBuilder().setName("Chrysocolla")
            .setDefaultLocalName("Chrysocolla")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadCitrine() {
        return new MaterialBuilder().setName("Citrine")
            .setDefaultLocalName("Citrine")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltHexahydrate() {
        return new MaterialBuilder().setName("CobaltHexahydrate")
            .setDefaultLocalName("Cobalt Hexahydrate")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x005050fa)
            .addDustItems()
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadConstructionFoam() {
        return new MaterialBuilder().setName("ConstructionFoam")
            .setDefaultLocalName("Construction Foam")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808080)
            .addDustItems()
            .addCell()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.STONE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.cell) // IC2:itemCellEmpty:4
            .constructMaterial();
    }

    private static Materials_OLD loadCoral() {
        return new MaterialBuilder().setName("Coral")
            .setDefaultLocalName("Coral")
            .setARGB(0x00ff80ff)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadCrudeOil() {
        return new MaterialBuilder().setName("CrudeOil")
            .setDefaultLocalName("Crude Oil")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadCrystalFlux() {
        return new MaterialBuilder().setName("CrystalFlux")
            .setDefaultLocalName("Flux Crystal")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setARGB(0x00643264)
            .addDustItems()
            .addGemItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadCyanite() {
        return new MaterialBuilder().setName("Cyanite")
            .setDefaultLocalName("Cyanite")
            .setColor(Dyes.dyeCyan)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadDarkIron() {
        return new MaterialBuilder().setName("DarkIron")
            .setDefaultLocalName("Deep Dark Iron")
            .setChemicalFormula("Sp₆Fe" + CustomGlyphs.PICKAXE)
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x0037283c)
            .setTool(384, 3, 7.0f)
            .setToolEnchantment(() -> Enchantment.fireAspect, 2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadDarkStone() {
        return new MaterialBuilder().setName("DarkStone")
            .setDefaultLocalName("Dark Stone")
            .setColor(Dyes.dyeBlack)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadDemonite() {
        return new MaterialBuilder().setName("Demonite")
            .setDefaultLocalName("Demonite")
            .setColor(Dyes.dyeRed)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadDesichalkos() {
        return new MaterialBuilder().setName("Desichalkos")
            .setDefaultLocalName("Desichalkos")
            .setTool(1_280, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadDilithium() {
        return new MaterialBuilder().setName("Dilithium")
            .setDefaultLocalName("Dilithium")
            .setChemicalFormula("∳Li∳Li∳")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x7ffffafa)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addCell()
            .addOreByproduct(() -> Materials_OLD.Dilithium)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadDraconic() {
        return new MaterialBuilder().setName("Draconic")
            .setDefaultLocalName("Draconic")
            .setColor(Dyes.dyeRed)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadDrulloy() {
        return new MaterialBuilder().setName("Drulloy")
            .setDefaultLocalName("Drulloy")
            .setColor(Dyes.dyeRed)
            .addDustItems()
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadDuranium() {
        return new MaterialBuilder().setName("Duranium")
            .setDefaultLocalName("Duranium")
            .setChemicalFormula("Du")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightGray)
            .setTool(40_960, 11, 32.0f)
            .setTurbine(16.0f, 16.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadElectrumFlux() {
        return new MaterialBuilder().setName("ElectrumFlux")
            .setDefaultLocalName("Fluxed Electrum")
            .setChemicalFormula("The formula is too long...", true)
            .setIconSet(TextureSet.SET_FLUXED)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffffff)
            .setTool(512, 3, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_000)
            .setBlastFurnaceTemp(9_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadEmery() {
        return new MaterialBuilder().setName("Emery")
            .setDefaultLocalName("Emery")
            .setIconSet(TextureSet.SET_DULL)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadEnderiumBase() {
        return new MaterialBuilder().setName("EnderiumBase")
            .setDefaultLocalName("Enderium Base")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00487799)
            .setTool(768, 4, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_600)
            .setBlastFurnaceTemp(3_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Tin, 2)
            .addMaterial(Materials_OLD.Silver, 2)
            .addMaterial(Materials_OLD.Platinum, 2)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEnergized() {
        return new MaterialBuilder().setName("Energized")
            .setDefaultLocalName("Energized")
            .constructMaterial();
    }

    private static Materials_OLD loadFierySteel() {
        return new MaterialBuilder().setName("FierySteel")
            .setDefaultLocalName("Fiery Steel")
            .setChemicalFormula(CustomGlyphs.BRIMSTONE + "Fe₅" + CustomGlyphs.SUBSCRIPT0 + "C")
            .setIconSet(TextureSet.SET_FIERY)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00400000)
            .setTool(256, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.fireAspect, 3)
            .setFuel(MaterialBuilder.FuelType.Magic, 2_048)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_811)
            .setBlastFurnaceTemp(1_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.BURNING)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadFirestone() {
        return new MaterialBuilder().setName("Firestone")
            .setDefaultLocalName("Firestone")
            .setChemicalFormula("⽕")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c81400)
            .setTool(1_280, 3, 6.0f)
            .setToolEnchantment(() -> Enchantment.fireAspect, 3)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .setHeatDamage(5.0f)
            .addSubTag(SubTag.BURNING)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadFluorite() {
        return new MaterialBuilder().setName("Fluorite")
            .setDefaultLocalName("Fluorite")
            .setColor(Dyes.dyeGreen)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadForce() {
        return new MaterialBuilder().setName("Force")
            .setDefaultLocalName("Force")
            .setChemicalFormula("Fc⚙")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setTool(128, 3, 10.0f)
            .setToolEnchantment(() -> Enchantment.silkTouch, 1)
            .addDustItems()
            .addMetalItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addOreByproduct(() -> Materials_OLD.Force)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadForcicium() {
        return new MaterialBuilder().setName("Forcicium")
            .setDefaultLocalName("Forcicium")
            .setChemicalFormula("◃◁◀")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00323246)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addCell()
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addOreByproduct(() -> Materials_OLD.Neodymium)
            .addOreByproduct(() -> Materials_OLD.RareEarth)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadForcillium() {
        return new MaterialBuilder().setName("Forcillium")
            .setDefaultLocalName("Forcillium")
            .setChemicalFormula("▶▷▹")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00323246)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addCell()
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addOreByproduct(() -> Materials_OLD.Neodymium)
            .addOreByproduct(() -> Materials_OLD.RareEarth)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadGlowstone() {
        return new MaterialBuilder().setName("Glowstone")
            .setDefaultLocalName("Glowstone")
            .setIconSet(TextureSet.SET_GLOWSTONE)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setMiningLevel(1)
            .addDustItems()
            .setOreMultiplier(5) // No .addOreItems?
            .addCell()
            .addOreByproduct(() -> Materials_OLD.Redstone)
            .addOreByproduct(() -> Materials_OLD.Gold)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.STONE)
            .addSubTag(SubTag.UNBURNABLE)
            .addOrePrefix(OrePrefixes.nanite)
            .addOrePrefix(OrePrefixes.plate)
            .removeOrePrefix(OrePrefixes.dust) // minecraft:glowstone_dust
            .constructMaterial();
    }

    private static Materials_OLD loadGraphene() {
        return new MaterialBuilder().setName("Graphene")
            .setDefaultLocalName("Graphene")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808080)
            .setTool(32, 1, 6.0f)
            .addDustItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Carbon, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadGraphite() {
        return new MaterialBuilder().setName("Graphite")
            .setDefaultLocalName("Graphite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808080)
            .setTool(32, 2, 5.0f)
            .addDustItems()
            .addOreItems()
            .addCell()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addOreByproduct(() -> Materials_OLD.Carbon)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadGreenstone() {
        return new MaterialBuilder().setName("Greenstone")
            .setDefaultLocalName("Greenstone")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGreen)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadHematite() {
        return new MaterialBuilder().setName("Hematite")
            .setDefaultLocalName("Hematite")
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadHSLA() {
        return new MaterialBuilder().setName("HSLA")
            .setDefaultLocalName("HSLA Steel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00808080)
            .setTool(500, 3, 6.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_811)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadInfernal() {
        return new MaterialBuilder().setName("Infernal")
            .setDefaultLocalName("Infernal")
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedAir() {
        return new MaterialBuilder().setName("InfusedAir")
            .setDefaultLocalName("Aer")
            .setChemicalFormula(CustomGlyphs.AIR)
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff7e)
            .setTool(64, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.knockback, 2)
            .setArmorEnchantment(() -> Enchantment.respiration, 3)
            .setFuel(MaterialBuilder.FuelType.Magic, 160)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedDull() {
        return new MaterialBuilder().setName("InfusedDull")
            .setDefaultLocalName("Vacuus")
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00646464)
            .setTool(64, 3, 32.0f)
            .setArmorEnchantment(() -> Enchantment.blastProtection, 4)
            .setFuel(MaterialBuilder.FuelType.Magic, 160)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedEarth() {
        return new MaterialBuilder().setName("InfusedEarth")
            .setDefaultLocalName("Terra")
            .setChemicalFormula(CustomGlyphs.EARTH)
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0000a000)
            .setTool(256, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.baneOfArthropods, 5)
            .setArmorEnchantment(() -> Enchantment.protection, 4)
            .setFuel(MaterialBuilder.FuelType.Magic, 160)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedEntropy() {
        return new MaterialBuilder().setName("InfusedEntropy")
            .setDefaultLocalName("Perditio")
            .setChemicalFormula(CustomGlyphs.CHAOS)
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00555577)
            .setTool(64, 4, 32.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 5)
            .setArmorEnchantment(() -> Enchantment.thorns, 3)
            .setFuel(MaterialBuilder.FuelType.Magic, 320)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedFire() {
        return new MaterialBuilder().setName("InfusedFire")
            .setDefaultLocalName("Ignis")
            .setChemicalFormula(CustomGlyphs.FIRE)
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff3c01)
            .setTool(64, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.fireAspect, 3)
            .setArmorEnchantment(() -> Enchantment.featherFalling, 4)
            .setFuel(MaterialBuilder.FuelType.Magic, 320)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedGold() {
        return new MaterialBuilder().setName("InfusedGold")
            .setDefaultLocalName("Infused Gold")
            .setChemicalFormula("AuMa*")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffc83c)
            .setTool(64, 3, 12.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addOreByproduct(() -> Materials_OLD.Gold)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedOrder() {
        return new MaterialBuilder().setName("InfusedOrder")
            .setDefaultLocalName("Ordo")
            .setChemicalFormula(CustomGlyphs.ORDER)
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00edcbfe)
            .setTool(64, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.silkTouch, 1)
            .setArmorEnchantment(() -> Enchantment.projectileProtection, 4)
            .setFuel(MaterialBuilder.FuelType.Magic, 240)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedVis() {
        return new MaterialBuilder().setName("InfusedVis")
            .setDefaultLocalName("Auram")
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00ff00ff)
            .setTool(64, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.smite, 5)
            .setArmorEnchantment(() -> Enchantment.protection, 4)
            .setFuel(MaterialBuilder.FuelType.Magic, 240)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadInfusedWater() {
        return new MaterialBuilder().setName("InfusedWater")
            .setDefaultLocalName("Aqua")
            .setChemicalFormula(CustomGlyphs.WATER)
            .setIconSet(TextureSet.SET_SHARDS)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000090ff)
            .setTool(64, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.fortune, 3)
            .setArmorEnchantment(() -> Enchantment.aquaAffinity, 1)
            .setFuel(MaterialBuilder.FuelType.Magic, 160)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.UNBURNABLE)
            .hasGlowingOre()
            .constructMaterial();
    }

    private static Materials_OLD loadInvisium() {
        return new MaterialBuilder().setName("Invisium")
            .setDefaultLocalName("Invisium")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadJade() {
        return new MaterialBuilder().setName("Jade")
            .setDefaultLocalName("Jade")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00006400)
            .setTool(16, 2, 1.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadLava() {
        return new MaterialBuilder().setName("Lava")
            .setDefaultLocalName("Lava")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff4000)
            .addCell()
            .setHeatDamage(3.0f)
            .removeOrePrefix(OrePrefixes.cell) // IC2:itemCellEmpty:2
            .removeOrePrefix(OrePrefixes.bucket) // minecraft:lava_bucket
            .removeOrePrefix(OrePrefixes.bucketClay) // IguanaTweaksTConstruct:clayBucketLava
            .constructMaterial();
    }

    private static Materials_OLD loadLimestone() {
        return new MaterialBuilder().setName("Limestone")
            .setDefaultLocalName("Limestone")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadMagma() {
        return new MaterialBuilder().setName("Magma")
            .setDefaultLocalName("Magma")
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff4000)
            .constructMaterial();
    }

    private static Materials_OLD loadMawsitsit() {
        return new MaterialBuilder().setName("Mawsitsit")
            .setDefaultLocalName("Mawsitsit")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadMercassium() {
        return new MaterialBuilder().setName("Mercassium")
            .setDefaultLocalName("Mercassium")
            .setTool(64, 1, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMeteoricSteel() {
        return new MaterialBuilder().setName("MeteoricSteel")
            .setDefaultLocalName("Meteoric Steel")
            .setChemicalFormula("SpFe₅" + CustomGlyphs.SUBSCRIPT0 + "C")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00321928)
            .setTool(768, 4, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .setMeltingPoint(1_811)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .setDensity(51, 50)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.MeteoricIron, 50)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMeteorite() {
        return new MaterialBuilder().setName("Meteorite")
            .setDefaultLocalName("Meteorite")
            .setColor(Dyes.dyePurple)
            .setARGB(0x0050233c)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadMigmatite() {
        return new MaterialBuilder().setName("Migmatite")
            .setDefaultLocalName("Migmatite")
            .setIconSet(TextureSet.SET_DULL)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadMimichite() {
        return new MaterialBuilder().setName("Mimichite")
            .setDefaultLocalName("Mimichite")
            .setIconSet(TextureSet.SET_GEM_VERTICAL)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadMoonstone() {
        return new MaterialBuilder().setName("Moonstone")
            .setDefaultLocalName("Moonstone")
            .setColor(Dyes.dyeWhite)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadNaquadahAlloy() {
        return new MaterialBuilder().setName("NaquadahAlloy")
            .setDefaultLocalName("Naquadah Alloy")
            .setChemicalFormula("Nq₂KeC")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00282828)
            .setTool(5_120, 5, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(7_200)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadNaquadahEnriched() {
        return new MaterialBuilder().setName("NaquadahEnriched")
            .setDefaultLocalName("Enriched Naquadah")
            .setChemicalFormula("Nq+")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .setMoltenARGB(0x0040ff40)
            .setTool(1_280, 4, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Naquadah)
            .addOreByproduct(() -> Materials_OLD.Naquadria)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadNaquadria() {
        return new MaterialBuilder().setName("Naquadria")
            .setDefaultLocalName("Naquadria")
            .setChemicalFormula("Nq*")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x001e1e1e)
            .setMoltenARGB(0x0080ff80)
            .setTool(512, 4, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .setMeltingPoint(9_000)
            .setBlastFurnaceTemp(9_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOreByproduct(() -> Materials_OLD.Naquadria)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadNether() {
        return new MaterialBuilder().setName("Nether")
            .setDefaultLocalName("Nether")
            .constructMaterial();
    }

    private static Materials_OLD loadNetherBrick() {
        return new MaterialBuilder().setName("NetherBrick")
            .setDefaultLocalName("Nether Brick")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00640000)
            .addDustItems()
            .setMaceratingInto(() -> Materials_OLD.Netherrack)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .constructMaterial();
    }

    private static Materials_OLD loadNetherQuartz() {
        return new MaterialBuilder().setName("NetherQuartz")
            .setDefaultLocalName("Nether Quartz")
            .setChemicalFormula("SiO₂")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e6d2d2)
            .setTool(32, 1, 1.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addToolHeadItems()
            .addOreByproduct(() -> Materials_OLD.Netherrack)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .removeOrePrefix(OrePrefixes.gem) // minecraft:quartz
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadNetherStar() {
        return new MaterialBuilder().setName("NetherStar")
            .setDefaultLocalName("Nether Star")
            .setChemicalFormula("(Nh₂Ma)₃" + CustomGlyphs.CIRCLE_CROSS + "C₆")
            .setIconSet(TextureSet.SET_NETHERSTAR)
            .setColor(Dyes.dyeWhite)
            .setTool(5_120, 4, 6.0f)
            .setToolEnchantment(() -> Enchantment.silkTouch, 1)
            .setFuel(MaterialBuilder.FuelType.Magic, 50_000)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.UNBURNABLE)
            .removeOrePrefix(OrePrefixes.gem) // minecraft:nether_star
            .constructMaterial();
    }

    private static Materials_OLD loadObsidianFlux() {
        return new MaterialBuilder().setName("ObsidianFlux")
            .setDefaultLocalName("Fluxed Obsidian")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00503264)
            .addDustItems()
            .addMetalItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadOilsands() {
        return new MaterialBuilder().setName("Oilsands")
            .setDefaultLocalName("Oilsands")
            .setARGB(0x000a0a0a)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadOnyx() {
        return new MaterialBuilder().setName("Onyx")
            .setDefaultLocalName("Onyx")
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadOrichalcum() {
        return new MaterialBuilder().setName("Orichalcum")
            .setDefaultLocalName("Orichalcum")
            .setChemicalFormula("SpBi")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00547a38)
            .setTool(20_480, 1, 32.0f)
            .setTurbine(1.0f, 6.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(6_000)
            .setBlastFurnaceTemp(6_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadOsmonium() {
        return new MaterialBuilder().setName("Osmonium")
            .setDefaultLocalName("Osmonium")
            .setColor(Dyes.dyeBlue)
            .setTool(64, 1, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadPainite() {
        return new MaterialBuilder().setName("Painite")
            .setDefaultLocalName("Painite")
            .constructMaterial();
    }

    private static Materials_OLD loadPeanutwood() {
        return new MaterialBuilder().setName("Peanutwood")
            .setDefaultLocalName("Peanut Wood")
            .setMaceratingInto(() -> Materials_OLD.Wood)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.WOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadPetroleum() {
        return new MaterialBuilder().setName("Petroleum")
            .setDefaultLocalName("Petroleum")
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadPewter() {
        return new MaterialBuilder().setName("Pewter")
            .setDefaultLocalName("Pewter")
            .constructMaterial();
    }

    private static Materials_OLD loadPhoenixite() {
        return new MaterialBuilder().setName("Phoenixite")
            .setDefaultLocalName("Phoenixite")
            .setTool(64, 1, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadQuartzite() {
        return new MaterialBuilder().setName("Quartzite")
            .setDefaultLocalName("Quartzite")
            .setChemicalFormula("SiO₂")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00d2e6d2)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addOreByproduct(() -> Materials_OLD.CertusQuartz)
            .addOreByproduct(() -> Materials_OLD.Barite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .addOrePrefix(OrePrefixes.dustImpure)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadRandomite() {
        return new MaterialBuilder().setName("Randomite")
            .setDefaultLocalName("Randomite")
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadRubracium() {
        return new MaterialBuilder().setName("Rubracium")
            .setDefaultLocalName("Rubracium")
            .setChemicalFormula("SpRb")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00972d2d)
            .setTool(128, 1, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addOreByproduct(() -> Materials_OLD.Samarium)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSand() {
        return new MaterialBuilder().setName("Sand")
            .setDefaultLocalName("Sand")
            .setColor(Dyes.dyeYellow)
            .setSmeltingInto(() -> Materials_OLD.Glass)
            .addSubTag(SubTag.NO_RECYCLING)
            .constructMaterial();
    }

    private static Materials_OLD loadSiltstone() {
        return new MaterialBuilder().setName("Siltstone")
            .setDefaultLocalName("Siltstone")
            .setIconSet(TextureSet.SET_DULL)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadSpinel() {
        return new MaterialBuilder().setName("FoolsRuby")
            .setDefaultLocalName("Spinel")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeRed)
            .setARGB(0xcc740c48)
            .setMiningLevel(2)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addOreByproduct(() -> Materials_OLD.Jasper)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadSunstone() {
        return new MaterialBuilder().setName("Sunstone")
            .setDefaultLocalName("Sunstone")
            .setMiningLevel(1)
            .setColor(Dyes.dyeYellow)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadTar() {
        return new MaterialBuilder().setName("Tar")
            .setDefaultLocalName("Tar")
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .constructMaterial();
    }

    private static Materials_OLD loadTartarite() {
        return new MaterialBuilder().setName("Tartarite")
            .setDefaultLocalName("Tartarite")
            .setChemicalFormula("Tt")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00ff763c)
            .setTool(20_480, 3, 32.0f)
            .setTurbine(1_120.0f, 1_120.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .setMeltingPoint(10_400)
            .setBlastFurnaceTemp(10_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadUUAmplifier() {
        return new MaterialBuilder().setName("UUAmplifier")
            .setDefaultLocalName("UU-Amplifier")
            .setChemicalFormula("Accelerates the Mass Fabricator", true)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePink)
            .setARGB(0x00600080)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadUUMatter() {
        return new MaterialBuilder().setName("UUMatter")
            .setDefaultLocalName("UU-Matter")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePink)
            .setARGB(0x008000c4)
            .addCell()
            .removeOrePrefix(OrePrefixes.cell) // IC2:itemCellEmpty:3
            .constructMaterial();
    }

    private static Materials_OLD loadVoid() {
        return new MaterialBuilder().setName("Void")
            .setDefaultLocalName("Void Metal")
            .setChemicalFormula("ShFeMa₃")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x001c0639)
            .setTool(512, 4, 32.0f)
            .setFuel(MaterialBuilder.FuelType.Magic, 1_500)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setDensity(2, 1)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadVoidstone() {
        return new MaterialBuilder().setName("Voidstone")
            .setDefaultLocalName("Voidstone")
            .setARGB(0xc8ffffff)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadVulcanite() {
        return new MaterialBuilder().setName("Vulcanite")
            .setDefaultLocalName("Vulcanite")
            .setChemicalFormula("SpCu")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00ff8448)
            .setTool(20_480, 2, 32.0f)
            .setTurbine(40.0f, 1.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(8_400)
            .setBlastFurnaceTemp(8_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadVyroxeres() {
        return new MaterialBuilder().setName("Vyroxeres")
            .setDefaultLocalName("Vyroxeres")
            .setChemicalFormula("SpBe")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x0055e001)
            .setTool(7_680, 1, 32.0f)
            .setTurbine(1.0f, 3.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadYellorium() {
        return new MaterialBuilder().setName("Yellorium")
            .setDefaultLocalName("Yellorium")
            .setColor(Dyes.dyeYellow)
            .addDustItems()
            .addMetalItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadZectium() {
        return new MaterialBuilder().setName("Zectium")
            .setDefaultLocalName("Zectium")
            .setColor(Dyes.dyeBlack)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static void loadTiers() {
        Materials_OLD.ULV = loadULV();
        Materials_OLD.LV = loadLV();
        Materials_OLD.MV = loadMV();
        Materials_OLD.HV = loadHV();
        Materials_OLD.EV = loadEV();
        Materials_OLD.IV = loadIV();
        Materials_OLD.LuV = loadLuV();
        Materials_OLD.ZPM = loadZPM();
        Materials_OLD.UV = loadUV();
        Materials_OLD.UHV = loadUHV();
        Materials_OLD.UEV = loadUEV();
        Materials_OLD.UIV = loadUIV();
        Materials_OLD.UMV = loadUMV();
        Materials_OLD.UXV = loadUXV();
        Materials_OLD.MAX = loadMAX();
    }

    private static Materials_OLD loadULV() {
        return new MaterialBuilder().setName("Primitive")
            .setDefaultLocalName("Primitive")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadLV() {
        return new MaterialBuilder().setName("Basic")
            .setDefaultLocalName("Basic")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadMV() {
        return new MaterialBuilder().setName("Good")
            .setDefaultLocalName("Good")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadHV() {
        return new MaterialBuilder().setName("Advanced")
            .setDefaultLocalName("Advanced")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadEV() {
        return new MaterialBuilder().setName("Data")
            .setDefaultLocalName("Data")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadIV() {
        return new MaterialBuilder().setName("Elite")
            .setDefaultLocalName("Elite")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadLuV() {
        return new MaterialBuilder().setName("Master")
            .setDefaultLocalName("Master")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadZPM() {
        return new MaterialBuilder().setName("Ultimate")
            .setDefaultLocalName("Ultimate")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadUV() {
        return new MaterialBuilder().setName("Superconductor")
            .setDefaultLocalName("Superconductor")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadUHV() {
        return new MaterialBuilder().setName("Infinite")
            .setDefaultLocalName("Infinite")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadUEV() {
        return new MaterialBuilder().setName("Bio")
            .setDefaultLocalName("Bio")
            .setColor(Dyes.dyeLightGray)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadUIV() {
        return new MaterialBuilder().setName("Optical")
            .setDefaultLocalName("Optical")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadUMV() {
        return new MaterialBuilder().setName("Exotic")
            .setDefaultLocalName("Exotic")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadUXV() {
        return new MaterialBuilder().setName("Cosmic")
            .setDefaultLocalName("Cosmic")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadMAX() {
        return new MaterialBuilder().setName("Transcendent")
            .setDefaultLocalName("Transcendent")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static void loadCircuitry() {
        Materials_OLD.Resistor = loadResistor();
        Materials_OLD.Diode = loadDiode();
        Materials_OLD.Transistor = loadTransistor();
        Materials_OLD.Capacitor = loadCapacitor();
        Materials_OLD.Inductor = loadInductor();
        Materials_OLD.Nano = loadNano();
        Materials_OLD.Piko = loadPiko();
    }

    private static Materials_OLD loadResistor() {
        return new MaterialBuilder().setName("Resistor")
            .setDefaultLocalName("Resistor")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadDiode() {
        return new MaterialBuilder().setName("Diode")
            .setDefaultLocalName("Diode")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadTransistor() {
        return new MaterialBuilder().setName("Transistor")
            .setDefaultLocalName("Transistor")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadCapacitor() {
        return new MaterialBuilder().setName("Capacitor")
            .setDefaultLocalName("Capacitor")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadInductor() {
        return new MaterialBuilder().setName("Inductor")
            .setDefaultLocalName("Inductor")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadNano() {
        return new MaterialBuilder().setName("Nano")
            .setDefaultLocalName("Bio")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static Materials_OLD loadPiko() {
        return new MaterialBuilder().setName("Piko")
            .setDefaultLocalName("Bio")
            .setColor(Dyes.dyeLightGray)
            .constructMaterial();
    }

    private static void loadNotExact() {
        Materials_OLD.Biomass = loadBiomass();
        Materials_OLD.CharcoalByproducts = loadCharcoalByproducts();
        Materials_OLD.Cheese = loadCheese();
        Materials_OLD.Chili = loadChili();
        Materials_OLD.Chocolate = loadChocolate();
        Materials_OLD.Cluster = loadCluster();
        Materials_OLD.CoalFuel = loadCoalFuel();
        Materials_OLD.Cocoa = loadCocoa();
        Materials_OLD.Coffee = loadCoffee();
        Materials_OLD.Creosote = loadCreosote();
        Materials_OLD.Diesel = loadDiesel();
        Materials_OLD.Ethanol = loadEthanol();
        Materials_OLD.FermentedBiomass = loadFermentedBiomass();
        Materials_OLD.FishOil = loadFishOil();
        Materials_OLD.FryingOilHot = loadFryingOilHot();
        Materials_OLD.Glue = loadGlue();
        Materials_OLD.GlueAdvanced = loadGlueAdvanced();
        Materials_OLD.Gunpowder = loadGunpowder();
        Materials_OLD.Honey = loadHoney();
        Materials_OLD.Leather = loadLeather();
        Materials_OLD.Lubricant = loadLubricant();
        Materials_OLD.MeatCooked = loadMeatCooked();
        Materials_OLD.MeatRaw = loadMeatRaw();
        Materials_OLD.Milk = loadMilk();
        Materials_OLD.Mud = loadMud();
        Materials_OLD.Oil = loadOil();
        Materials_OLD.Paper = loadPaper();
        Materials_OLD.Peat = loadPeat();
        Materials_OLD.RareEarth = loadRareEarth();
        Materials_OLD.Red = loadRed();
        Materials_OLD.Reinforced = loadReinforced();
        Materials_OLD.SeedOil = loadSeedOil();
        Materials_OLD.SeedOilHemp = loadSeedOilHemp();
        Materials_OLD.SeedOilLin = loadSeedOilLin();
        Materials_OLD.Stone = loadStone();
        Materials_OLD.TNT = loadTNT();
        Materials_OLD.Unstable = loadUnstable();
        Materials_OLD.UnstableIngot = loadUnstableIngot();
        Materials_OLD.Vinegar = loadVinegar();
        Materials_OLD.WeedEX9000 = loadWeedEX9000();
        Materials_OLD.Wheat = loadWheat();
        Materials_OLD.WoodGas = loadWoodGas();
        Materials_OLD.WoodTar = loadWoodTar();
        Materials_OLD.WoodVinegar = loadWoodVinegar();
    }

    private static Materials_OLD loadBiomass() {
        return new MaterialBuilder().setName("Biomass")
            .setDefaultLocalName("Forestry Biomass")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0000ff00)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 8)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadCharcoalByproducts() {
        return new MaterialBuilder().setName("CharcoalByproducts")
            .setDefaultLocalName("Charcoal Byproducts")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00784421)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadCheese() {
        return new MaterialBuilder().setName("Cheese")
            .setDefaultLocalName("Cheese")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setMiningLevel(0)
            .addDustItems()
            .addOreItems()
            .setMeltingPoint(320)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .constructMaterial();
    }

    private static Materials_OLD loadChili() {
        return new MaterialBuilder().setName("Chili")
            .setDefaultLocalName("Chili")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c80000)
            .addDustItems()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadChocolate() {
        return new MaterialBuilder().setName("Chocolate")
            .setDefaultLocalName("Chocolate")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00be5f00)
            .addDustItems()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadCluster() {
        return new MaterialBuilder().setName("Cluster")
            .setDefaultLocalName("Cluster")
            .setColor(Dyes.dyeWhite)
            .setARGB(0x7fffffff)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadCoalFuel() {
        return new MaterialBuilder().setName("CoalFuel")
            .setDefaultLocalName("Coalfuel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323246)
            .setFuel(MaterialBuilder.FuelType.Diesel, 16)
            .addCell()
            .removeOrePrefix(OrePrefixes.cell)
            .constructMaterial();
    }

    private static Materials_OLD loadCocoa() {
        return new MaterialBuilder().setName("Cocoa")
            .setDefaultLocalName("Cocoa")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00be5f00)
            .addDustItems()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadCoffee() {
        return new MaterialBuilder().setName("Coffee")
            .setDefaultLocalName("Coffee")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00964b00)
            .addDustItems()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadCreosote() {
        return new MaterialBuilder().setName("Creosote")
            .setDefaultLocalName("Creosote")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00804000)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 8)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadDiesel() {
        return new MaterialBuilder().setName("Fuel")
            .setDefaultLocalName("Diesel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.Diesel, 480)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadEthanol() {
        return new MaterialBuilder().setName("Ethanol")
            .setDefaultLocalName("Ethanol")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8000)
            .setFuel(MaterialBuilder.FuelType.Diesel, 192)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadFermentedBiomass() {
        return new MaterialBuilder().setName("FermentedBiomass")
            .setDefaultLocalName("Fermented Biomass")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00445500)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadFishOil() {
        return new MaterialBuilder().setName("FishOil")
            .setDefaultLocalName("Fish Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffc400)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 2)
            .addCell()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadFryingOilHot() {
        return new MaterialBuilder().setName("FryingOilHot")
            .setDefaultLocalName("Hot Frying Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00c8c400)
            .addCell()
            .setHeatDamage(1.0f)
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadGlue() {
        return new MaterialBuilder().setName("Glue")
            .setDefaultLocalName("Refined Glue")
            .setChemicalFormula("No Horses were harmed in the making of this substance", true)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00c8c400)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadGlueAdvanced() {
        return new MaterialBuilder().setName("AdvancedGlue")
            .setDefaultLocalName("Advanced Glue")
            .setChemicalFormula("A chemically approved glue!", true)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffffb9)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadGunpowder() {
        return new MaterialBuilder().setName("Gunpowder")
            .setDefaultLocalName("Gunpowder")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808080)
            .addDustItems()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_SMASHING)
            .removeOrePrefix(OrePrefixes.dust) // minecraft:gunpowder
            .constructMaterial();
    }

    private static Materials_OLD loadHoney() {
        return new MaterialBuilder().setName("Honey")
            .setDefaultLocalName("Honey")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00d2c800)
            .addCell()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadLeather() {
        return new MaterialBuilder().setName("Leather")
            .setDefaultLocalName("Leather")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x7f969650)
            .addDustItems()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadLubricant() {
        return new MaterialBuilder().setName("Lubricant")
            .setDefaultLocalName("Lubricant")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ffc400)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadMeatCooked() {
        return new MaterialBuilder().setName("MeatCooked")
            .setDefaultLocalName("Cooked Meat")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyePink)
            .setARGB(0x00963c14)
            .addDustItems()
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadMeatRaw() {
        return new MaterialBuilder().setName("MeatRaw")
            .setDefaultLocalName("Raw Meat")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ff6464)
            .addDustItems()
            .setSmeltingInto(() -> Materials_OLD.MeatCooked)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static Materials_OLD loadMilk() {
        return new MaterialBuilder().setName("Milk")
            .setDefaultLocalName("Milk")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fefefe)
            .addDustItems()
            .addCell()
            .addSubTag(SubTag.FOOD)
            .removeOrePrefix(OrePrefixes.bucket) // minecraft:milk_bucket
            .removeOrePrefix(OrePrefixes.bucketClay) // IguanaTweaksTConstruct:clayBucketMilk
            .constructMaterial();
    }

    private static Materials_OLD loadMud() {
        return new MaterialBuilder().setName("Mud")
            .setDefaultLocalName("Mud")
            .setColor(Dyes.dyeBrown)
            .constructMaterial();
    }

    private static Materials_OLD loadOil() {
        return new MaterialBuilder().setName("Oil")
            .setDefaultLocalName("Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 20)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadPaper() {
        return new MaterialBuilder().setName("Paper")
            .setDefaultLocalName("Paper")
            .setIconSet(TextureSet.SET_PAPER)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .addDustItems()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.PAPER)
            .addOrePrefix(OrePrefixes.plateDouble)
            .addOrePrefix(OrePrefixes.plateTriple)
            .addOrePrefix(OrePrefixes.plateQuadruple)
            .addOrePrefix(OrePrefixes.plateQuintuple)
            .addOrePrefix(OrePrefixes.ring)
            .constructMaterial();
    }

    private static Materials_OLD loadPeat() {
        return new MaterialBuilder().setName("Peat")
            .setDefaultLocalName("Peat")
            .setColor(Dyes.dyeBrown)
            .constructMaterial();
    }

    private static Materials_OLD loadRareEarth() {
        return new MaterialBuilder().setName("RareEarth")
            .setDefaultLocalName("Rare Earth")
            .setChemicalFormula("??????")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808064)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadRed() {
        return new MaterialBuilder().setName("Red")
            .setDefaultLocalName("Red")
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff0000)
            .constructMaterial();
    }

    private static Materials_OLD loadReinforced() {
        return new MaterialBuilder().setName("Reinforced")
            .setDefaultLocalName("Reinforced")
            .setChemicalFormula("Fe₂(C(MgFeSi₂O₈)₈)")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00698da5)
            .setTool(480, 4, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .constructMaterial();
    }

    private static Materials_OLD loadSeedOil() {
        return new MaterialBuilder().setName("SeedOil")
            .setDefaultLocalName("Seed Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00c4ff00)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 2)
            .addCell()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadSeedOilHemp() {
        return new MaterialBuilder().setName("SeedOilHemp")
            .setDefaultLocalName("Hemp Seed Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00c4ff00)
            .addCell()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadSeedOilLin() {
        return new MaterialBuilder().setName("SeedOilLin")
            .setDefaultLocalName("Lin Seed Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00c4ff00)
            .addCell()
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadStone() {
        return new MaterialBuilder().setName("Stone")
            .setDefaultLocalName("Stone")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00cdcdcd)
            .setTool(32, 1, 4.0f)
            .addDustItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.NO_RECYCLING)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .constructMaterial();
    }

    private static Materials_OLD loadTNT() {
        return new MaterialBuilder().setName("TNT")
            .setDefaultLocalName("TNT")
            .setColor(Dyes.dyeRed)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static Materials_OLD loadUnstable() {
        return new MaterialBuilder().setName("Unstable")
            .setDefaultLocalName("Unstable")
            .setChemicalFormula(CustomGlyphs.FIXED_JAPANESE_OPENING_QUOTE + "Fe/C⌋")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x7fdcdcdc)
            .addDustItems()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadUnstableIngot() {
        return new MaterialBuilder().setName("Unstableingot")
            .setDefaultLocalName("Unstable")
            .setColor(Dyes.dyeWhite)
            .setARGB(0x7fffffff)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadVinegar() {
        return new MaterialBuilder().setName("Vinegar")
            .setDefaultLocalName("Vinegar")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .constructMaterial();
    }

    private static Materials_OLD loadWeedEX9000() {
        return new MaterialBuilder().setName("WeedEX9000")
            .setDefaultLocalName("Weed-EX 9000")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0040e056)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadWheat() {
        return new MaterialBuilder().setName("Wheat")
            .setDefaultLocalName("Wheat")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffffc4)
            .addDustItems()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadWoodGas() {
        return new MaterialBuilder().setName("WoodGas")
            .setDefaultLocalName("Wood Gas")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00decd87)
            .setFuel(MaterialBuilder.FuelType.Gas, 24)
            .addCell()
            .addGas()
            .constructMaterial();
    }

    private static Materials_OLD loadWoodTar() {
        return new MaterialBuilder().setName("WoodTar")
            .setDefaultLocalName("Wood Tar")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x0028170b)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadWoodVinegar() {
        return new MaterialBuilder().setName("WoodVinegar")
            .setDefaultLocalName("Wood Vinegar")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00d45500)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static void loadBioVatBacterialSludges() {
        Materials_OLD.EnrichedBacterialSludge = loadEnrichedBacterialSludge();
        Materials_OLD.FermentedBacterialSludge = loadFermentedBacterialSludge();
    }

    private static Materials_OLD loadEnrichedBacterialSludge() {
        return new MaterialBuilder().setName("EnrichedBacterialSludge")
            .setDefaultLocalName("Enriched Bacterial Sludge")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0036f60e)
            .constructMaterial();
    }

    private static Materials_OLD loadFermentedBacterialSludge() {
        return new MaterialBuilder().setName("FermentedBacterialSludge")
            .setDefaultLocalName("Fermented Bacterial Sludge")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00008c10)
            .constructMaterial();
    }

    private static void loadTODOThis() {
        Materials_OLD.AluminiumBrass = loadAluminiumBrass();
        Materials_OLD.Endstone = loadEndstone();
        Materials_OLD.Netherrack = loadNetherrack();
        Materials_OLD.Osmiridium = loadOsmiridium();
        Materials_OLD.SoulSand = loadSoulSand();
        Materials_OLD.Sunnarium = loadSunnarium();
    }

    private static Materials_OLD loadAluminiumBrass() {
        return new MaterialBuilder().setName("AluminiumBrass")
            .setDefaultLocalName("Aluminium Brass")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setTool(64, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEndstone() {
        return new MaterialBuilder().setName("Endstone")
            .setDefaultLocalName("Endstone")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .addDustItems()
            .addOreByproduct(() -> Materials_OLD.Helium3)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .constructMaterial();
    }

    private static Materials_OLD loadNetherrack() {
        return new MaterialBuilder().setName("Netherrack")
            .setDefaultLocalName("Netherrack")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c80000)
            .addDustItems()
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .setSmeltingInto(() -> Materials_OLD.NetherBrick)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addSubTag(SubTag.UNBURNABLE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .constructMaterial();
    }

    private static Materials_OLD loadOsmiridium() {
        return new MaterialBuilder().setName("Osmiridium")
            .setDefaultLocalName("Osmiridium")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x006464ff)
            .setTool(1_600, 3, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iridium, 3)
            .addMaterial(Materials_OLD.Osmium, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadSoulSand() {
        return new MaterialBuilder().setName("SoulSand")
            .setDefaultLocalName("Soulsand")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadSunnarium() {
        return new MaterialBuilder().setName("Sunnarium")
            .setDefaultLocalName("Sunnarium")
            .setChemicalFormula("Su")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(4_200)
            .setBlastFurnaceTemp(4_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static void loadDegree1Compounds() {
        Materials_OLD.AceticAcid = loadAceticAcid();
        Materials_OLD.Acetone = loadAcetone();
        Materials_OLD.AllylChloride = loadAllylChloride();
        Materials_OLD.Almandine = loadAlmandine();
        Materials_OLD.Ammonia = loadAmmonia();
        Materials_OLD.Andradite = loadAndradite();
        Materials_OLD.AnnealedCopper = loadAnnealedCopper();
        Materials_OLD.AntimonyTrioxide = loadAntimonyTrioxide();
        Materials_OLD.ArsenicTrioxide = loadArsenicTrioxide();
        Materials_OLD.Asbestos = loadAsbestos();
        Materials_OLD.Ash = loadAsh();
        Materials_OLD.AshDark = loadAshDark();
        Materials_OLD.BandedIron = loadBandedIron();
        Materials_OLD.BatteryAlloy = loadBatteryAlloy();
        Materials_OLD.Benzene = loadBenzene();
        Materials_OLD.BlueTopaz = loadBlueTopaz();
        Materials_OLD.Bone = loadBone();
        Materials_OLD.Brass = loadBrass();
        Materials_OLD.Brick = loadBrick();
        Materials_OLD.Bronze = loadBronze();
        Materials_OLD.BrownLimonite = loadBrownLimonite();
        Materials_OLD.Calcite = loadCalcite();
        Materials_OLD.CarbonDioxide = loadCarbonDioxide();
        Materials_OLD.Cassiterite = loadCassiterite();
        Materials_OLD.CassiteriteSand = loadCassiteriteSand();
        Materials_OLD.Chalcopyrite = loadChalcopyrite();
        Materials_OLD.Charcoal = loadCharcoal();
        Materials_OLD.Chlorobenzene = loadChlorobenzene();
        Materials_OLD.Chromite = loadChromite();
        Materials_OLD.ChromiumDioxide = loadChromiumDioxide();
        Materials_OLD.Cinnabar = loadCinnabar();
        Materials_OLD.Coal = loadCoal();
        Materials_OLD.CobaltOxide = loadCobaltOxide();
        Materials_OLD.Cobaltite = loadCobaltite();
        Materials_OLD.Cooperite = loadCooperite();
        Materials_OLD.CupricOxide = loadCupricOxide();
        Materials_OLD.Cupronickel = loadCupronickel();
        Materials_OLD.DeepIron = loadDeepIron();
        Materials_OLD.Diamond = loadDiamond();
        Materials_OLD.DilutedHydrochloricAcid = loadDilutedHydrochloricAcid();
        Materials_OLD.Electrum = loadElectrum();
        Materials_OLD.Emerald = loadEmerald();
        Materials_OLD.Epoxid = loadEpoxid();
        Materials_OLD.FerriteMixture = loadFerriteMixture();
        Materials_OLD.Ferrosilite = loadFerrosilite();
        Materials_OLD.FreshWater = loadFreshWater();
        Materials_OLD.Galena = loadGalena();
        Materials_OLD.Garnierite = loadGarnierite();
        Materials_OLD.Glyceryl = loadGlyceryl();
        Materials_OLD.GreenSapphire = loadGreenSapphire();
        Materials_OLD.Grossular = loadGrossular();
        Materials_OLD.HolyWater = loadHolyWater();
        Materials_OLD.HydricSulfide = loadHydricSulfide();
        Materials_OLD.Ice = loadIce();
        Materials_OLD.Ilmenite = loadIlmenite();
        Materials_OLD.Invar = loadInvar();
        Materials_OLD.Kanthal = loadKanthal();
        Materials_OLD.Lazurite = loadLazurite();
        Materials_OLD.LiquidNitrogen = loadLiquidNitrogen();
        Materials_OLD.LiquidOxygen = loadLiquidOxygen();
        Materials_OLD.Magnalium = loadMagnalium();
        Materials_OLD.Magnesia = loadMagnesia();
        Materials_OLD.Magnesite = loadMagnesite();
        Materials_OLD.Magnesiumchloride = loadMagnesiumchloride();
        Materials_OLD.Magnetite = loadMagnetite();
        Materials_OLD.Massicot = loadMassicot();
        Materials_OLD.Methane = loadMethane();
        Materials_OLD.Molybdenite = loadMolybdenite();
        Materials_OLD.Nichrome = loadNichrome();
        Materials_OLD.NickelZincFerrite = loadNickelZincFerrite();
        Materials_OLD.NiobiumNitride = loadNiobiumNitride();
        Materials_OLD.NiobiumTitanium = loadNiobiumTitanium();
        Materials_OLD.NitroCarbon = loadNitroCarbon();
        Materials_OLD.NitrogenDioxide = loadNitrogenDioxide();
        Materials_OLD.NobleGases = loadNobleGases();
        Materials_OLD.Obsidian = loadObsidian();
        Materials_OLD.Phosphate = loadPhosphate();
        Materials_OLD.PigIron = loadPigIron();
        Materials_OLD.Polycaprolactam = loadPolycaprolactam();
        Materials_OLD.Polydimethylsiloxane = loadPolydimethylsiloxane();
        Materials_OLD.Polyethylene = loadPolyethylene();
        Materials_OLD.Polytetrafluoroethylene = loadPolytetrafluoroethylene();
        Materials_OLD.Potash = loadPotash();
        Materials_OLD.Powellite = loadPowellite();
        Materials_OLD.Pumice = loadPumice();
        Materials_OLD.Pyrite = loadPyrite();
        Materials_OLD.Pyrochlore = loadPyrochlore();
        Materials_OLD.Pyrolusite = loadPyrolusite();
        Materials_OLD.Pyrope = loadPyrope();
        Materials_OLD.Quicklime = loadQuicklime();
        Materials_OLD.RockSalt = loadRockSalt();
        Materials_OLD.Rubber = loadRubber();
        Materials_OLD.RubberRaw = loadRubberRaw();
        Materials_OLD.RubberSilicone = loadRubberSilicone();
        Materials_OLD.Ruby = loadRuby();
        Materials_OLD.Rutile = loadRutile();
        Materials_OLD.Salt = loadSalt();
        Materials_OLD.Saltpeter = loadSaltpeter();
        Materials_OLD.Sapphire = loadSapphire();
        Materials_OLD.Scheelite = loadScheelite();
        Materials_OLD.SiliconDioxide = loadSiliconDioxide();
        Materials_OLD.Snow = loadSnow();
        Materials_OLD.SodaAsh = loadSodaAsh();
        Materials_OLD.Sodalite = loadSodalite();
        Materials_OLD.SodiumPersulfate = loadSodiumPersulfate();
        Materials_OLD.SodiumPotassium = loadSodiumPotassium();
        Materials_OLD.SodiumSulfide = loadSodiumSulfide();
        Materials_OLD.Titaniumtetrachloride = loadTitaniumtetrachloride();
        Materials_OLD.Water = Materials_OLD.Steam = loadWater();
        Materials_OLD.Zincite = loadZincite();
    }

    private static Materials_OLD loadAceticAcid() {
        return new MaterialBuilder().setName("AceticAcid")
            .setDefaultLocalName("Acetic Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c8b4a0)
            .setMeltingPoint(289)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadAcetone() {
        return new MaterialBuilder().setName("Acetone")
            .setDefaultLocalName("Acetone")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00afafaf)
            .setMeltingPoint(178)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadAir() {
        return new MaterialBuilder().setName("Air")
            .setDefaultLocalName("Air")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0xf0a9d0f5)
            .addCell()
            .addMaterial(Materials_OLD.Nitrogen, 40)
            .addMaterial(Materials_OLD.Oxygen, 11)
            .addMaterial(Materials_OLD.Argon, 1)
            .addMaterial(Materials_OLD.NobleGases, 1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadAllylChloride() {
        return new MaterialBuilder().setName("AllylChloride")
            .setDefaultLocalName("Allyl Chloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x0087deaa)
            .setMeltingPoint(138)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 5)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadAlmandine() {
        return new MaterialBuilder().setName("Almandine")
            .setDefaultLocalName("Almandine")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff0000)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Iron, 3)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.GarnetRed)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .constructMaterial();
    }

    private static Materials_OLD loadAmmonia() {
        return new MaterialBuilder().setName("Ammonia")
            .setDefaultLocalName("Ammonia")
            .setIconSet(TextureSet.SET_EMERALD)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x003f3480)
            .setOreMultiplier(4)
            .addCell()
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .addElectrolyzerRecipe()
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadAndradite() {
        return new MaterialBuilder().setName("Andradite")
            .setDefaultLocalName("Andradite")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00967800)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Calcium, 3)
            .addMaterial(Materials_OLD.Iron, 2)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.GarnetYellow)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .constructMaterial();
    }

    private static Materials_OLD loadAnnealedCopper() {
        return new MaterialBuilder().setName("AnnealedCopper")
            .setDefaultLocalName("Annealed Copper")
            .setChemicalFormula("Cu*")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff7814)
            .setMeltingPoint(1_357)
            .addDustItems()
            .addMetalItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Copper, 1)
            .setArcSmeltingInto(() -> Materials_OLD.AnnealedCopper)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadAntimonyTrioxide() {
        return new MaterialBuilder().setName("AntimonyTrioxide")
            .setDefaultLocalName("Antimony Trioxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e6e6f0)
            .addDustItems()
            .addMaterial(Materials_OLD.Antimony, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadArsenicTrioxide() {
        return new MaterialBuilder().setName("ArsenicTrioxide")
            .setDefaultLocalName("Arsenic Trioxide")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGreen)
            .addDustItems()
            .addMaterial(Materials_OLD.Arsenic, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadAsbestos() {
        return new MaterialBuilder().setName("Asbestos")
            .setDefaultLocalName("Asbestos")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e6e6e6)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // Mg3Si2O5(OH)4
            .addMaterial(Materials_OLD.Magnesium, 3)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 9)
            .addOreByproduct(() -> Materials_OLD.Asbestos)
            .addOreByproduct(() -> Materials_OLD.SiliconDioxide)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .constructMaterial();
    }

    private static Materials_OLD loadAsh() {
        return new MaterialBuilder().setName("Ash")
            .setDefaultLocalName("Ashes")
            .setChemicalFormula("??")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00969696)
            .addDustItems()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addOreByproduct(() -> Materials_OLD.Carbon)
            .constructMaterial();
    }

    private static Materials_OLD loadAshDark() {
        return new MaterialBuilder().setName("DarkAsh")
            .setDefaultLocalName("Dark Ashes")
            .setChemicalFormula("C" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK + "??")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00323232)
            .addDustItems()
            .setDensity(2, 1)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addOreByproduct(() -> Materials_OLD.Carbon)
            .constructMaterial();
    }

    private static Materials_OLD loadBandedIron() {
        return new MaterialBuilder().setName("BandedIron")
            .setDefaultLocalName("Banded Iron")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00915a5a)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadBatteryAlloy() {
        return new MaterialBuilder().setName("BatteryAlloy")
            .setDefaultLocalName("Battery Alloy")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x009c7ca0)
            .addDustItems()
            .addMetalItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Lead, 4)
            .addMaterial(Materials_OLD.Antimony, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadBenzene() {
        return new MaterialBuilder().setName("Benzene")
            .setDefaultLocalName("Benzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x001a1a1a)
            .setMeltingPoint(279)
            .setFuel(MaterialBuilder.FuelType.Gas, 360)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadBlueTopaz() {
        return new MaterialBuilder().setName("BlueTopaz")
            .setDefaultLocalName("Blue Topaz")
            .setIconSet(TextureSet.SET_GEM_HORIZONTAL)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x7f0000ff)
            .setTool(256, 3, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Fluorine, 2)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .addOreByproduct(() -> Materials_OLD.Topaz)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadBone() {
        return new MaterialBuilder().setName("Bone")
            .setDefaultLocalName("Bone")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .addDustItems()
            .addMaterial(Materials_OLD.Calcium, 1)
            .removeOrePrefix(OrePrefixes.dust) // minecraft:dye:15
            .removeOrePrefix(OrePrefixes.stick)
            .constructMaterial();
    }

    private static Materials_OLD loadBrass() {
        return new MaterialBuilder().setName("Brass")
            .setDefaultLocalName("Brass")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffb400)
            .setTool(96, 1, 7.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Zinc, 1)
            .addMaterial(Materials_OLD.Copper, 3)
            .addOreByproduct(() -> Materials_OLD.Copper)
            .addOreByproduct(() -> Materials_OLD.Zinc)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadBrick() {
        return new MaterialBuilder().setName("Brick")
            .setDefaultLocalName("Brick")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x009b5643)
            .addDustItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 4)
            .addMaterial(Materials_OLD.Oxygen, 11)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.STONE)
            .removeOrePrefix(OrePrefixes.ingot) // minecraft:brick
            .constructMaterial();
    }

    private static Materials_OLD loadBronze() {
        return new MaterialBuilder().setName("Bronze")
            .setDefaultLocalName("Bronze")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8000)
            .setTool(192, 2, 6.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 1)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Tin, 1)
            .addMaterial(Materials_OLD.Copper, 3)
            .addOreByproduct(() -> Materials_OLD.Copper)
            .addOreByproduct(() -> Materials_OLD.Tin)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadBrownLimonite() {
        return new MaterialBuilder().setName("BrownLimonite")
            .setDefaultLocalName("Brown Limonite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00c86400)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            // FeO(OH)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.Malachite)
            .addOreByproduct(() -> Materials_OLD.YellowLimonite)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadCalcite() {
        return new MaterialBuilder().setName("Calcite")
            .setDefaultLocalName("Calcite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00fae6dc)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Andradite)
            .addOreByproduct(() -> Materials_OLD.Malachite)
            .constructMaterial();
    }

    private static Materials_OLD loadCarbonDioxide() {
        return new MaterialBuilder().setName("CarbonDioxide")
            .setDefaultLocalName("Carbon Dioxide")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0xf0a9d0f5)
            .setOreMultiplier(4)
            .addCell()
            .setMeltingPoint(25)
            .setBlastFurnaceTemp(1)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addSubTag(SubTag.TRANSPARENT)
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadCassiterite() {
        return new MaterialBuilder().setName("Cassiterite")
            .setDefaultLocalName("Cassiterite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdcdc)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Tin, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.Tin)
            .setDirectSmelting(() -> Materials_OLD.Tin)
            .constructMaterial();
    }

    private static Materials_OLD loadCassiteriteSand() {
        return new MaterialBuilder().setName("CassiteriteSand")
            .setDefaultLocalName("Cassiterite Sand")
            .setIconSet(TextureSet.SET_SAND)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdcdc)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Tin, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.Tin)
            .setDirectSmelting(() -> Materials_OLD.Tin)
            .constructMaterial();
    }

    private static Materials_OLD loadChalcopyrite() {
        return new MaterialBuilder().setName("Chalcopyrite")
            .setDefaultLocalName("Chalcopyrite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00a07828)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Sulfur, 2)
            .addOreByproduct(() -> Materials_OLD.Pyrite)
            .addOreByproduct(() -> Materials_OLD.Cobalt)
            .addOreByproduct(() -> Materials_OLD.Cadmium)
            .addOreByproduct(() -> Materials_OLD.Gold)
            .setDirectSmelting(() -> Materials_OLD.Copper)
            .addSubTag(SubTag.DONT_ADD_DEFAULT_BBF_RECIPE)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadCharcoal() {
        return new MaterialBuilder().setName("Charcoal")
            .setDefaultLocalName("Charcoal")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00644646)
            .addDustItems()
            .addGemItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.gem) // minecraft:coal:1
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadChlorobenzene() {
        return new MaterialBuilder().setName("Chlorobenzene")
            .setDefaultLocalName("Chlorobenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00003241)
            .setMeltingPoint(228)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 5)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadChromite() {
        return new MaterialBuilder().setName("Chromite")
            .setDefaultLocalName("Chromite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x0023140f)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .setMeltingPoint(1_700)
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Chrome, 2)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .setDirectSmelting(() -> Materials_OLD.Chrome)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadChromiumDioxide() {
        return new MaterialBuilder().setName("ChromiumDioxide")
            .setDefaultLocalName("Chromium Dioxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePink)
            .setARGB(0x00e6c8c8)
            .setTool(256, 3, 11.0f)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(650)
            .setBlastFurnaceTemp(650)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Chrome, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadCinnabar() {
        return new MaterialBuilder().setName("Cinnabar")
            .setDefaultLocalName("Cinnabar")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00960000)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Mercury, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addOreByproduct(() -> Materials_OLD.Redstone)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addOreByproduct(() -> Materials_OLD.Glowstone)
            .setDirectSmelting(() -> Materials_OLD.Mercury)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .addSubTag(SubTag.SMELTING_TO_GEM)
            .constructMaterial();
    }

    private static Materials_OLD loadCoal() {
        return new MaterialBuilder().setName("Coal")
            .setDefaultLocalName("Coal")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00464646)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(2)
            .setDensity(2, 1)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addOreByproduct(() -> Materials_OLD.Lignite)
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.block) // minecraft:coal_block
            .removeOrePrefix(OrePrefixes.gem) // minecraft:coal:0
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltOxide() {
        return new MaterialBuilder().setName("CobaltOxide")
            .setDefaultLocalName("Cobalt Oxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00668000)
            .addDustItems()
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltite() {
        return new MaterialBuilder().setName("Cobaltite")
            .setDefaultLocalName("Cobaltite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x005050fa)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Arsenic, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addOreByproduct(() -> Materials_OLD.Cobalt)
            .setDirectSmelting(() -> Materials_OLD.Cobalt)
            .addSubTag(SubTag.WASHING_SODIUMPERSULFATE)
            .constructMaterial();
    }

    private static Materials_OLD loadCooperite() {
        return new MaterialBuilder().setName("Cooperite")
            .setDefaultLocalName("Sheldonite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffffc8)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Platinum, 3)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Palladium, 1)
            .addOreByproduct(() -> Materials_OLD.Palladium)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addOreByproduct(() -> Materials_OLD.Iridium)
            .setDirectSmelting(() -> Materials_OLD.Platinum)
            .addSubTag(SubTag.DONT_ADD_DEFAULT_BBF_RECIPE)
            .addSubTag(SubTag.WASHING_MERCURY)
            .constructMaterial();
    }

    private static Materials_OLD loadCupricOxide() {
        return new MaterialBuilder().setName("CupricOxide")
            .setDefaultLocalName("Cupric Oxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000f0f0f)
            .addDustItems()
            .setMeltingPoint(1_599)
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadCupronickel() {
        return new MaterialBuilder().setName("Cupronickel")
            .setDefaultLocalName("Cupronickel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00e39680)
            .setTool(64, 1, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadDeepIron() {
        return new MaterialBuilder().setName("DeepIron")
            .setDefaultLocalName("Deep Iron")
            .setChemicalFormula("Sp₂Fe")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00968c8c)
            .setTool(384, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .setMeltingPoint(7_500)
            .setBlastFurnaceTemp(7_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addMaterial(Materials_OLD.Iron, 1)
            .addOreByproduct(() -> Materials_OLD.Trinium)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_TRIPLE)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadDiamond() {
        return new MaterialBuilder().setName("Diamond")
            .setDefaultLocalName("Diamond")
            .setIconSet(
                TextureSet.SET_DIAMOND.withCustomTextures(
                    "diamond",
                    MaterialIconRegistry.IconType.ORE,
                    MaterialIconRegistry.IconType.ORE_SMALL))
            .setColor(Dyes.dyeWhite)
            .setARGB(0x7fc8ffff)
            .setTool(1_280, 4, 8.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setDensity(64, 1)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addOreByproduct(() -> Materials_OLD.Graphite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.block) // minecraft:diamond_block
            .removeOrePrefix(OrePrefixes.gem) // minecraft:diamond
            .constructMaterial();
    }

    private static Materials_OLD loadDilutedHydrochloricAcid() {
        return new MaterialBuilder().setName("DilutedHydrochloricAcid_GT5U")
            .setDefaultLocalName("Diluted Hydrochloric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x0099a7a3)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadElectrum() {
        return new MaterialBuilder().setName("Electrum")
            .setDefaultLocalName("Electrum")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff64)
            .setTool(64, 2, 12.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Silver, 1)
            .addMaterial(Materials_OLD.Gold, 1)
            .addOreByproduct(() -> Materials_OLD.Gold)
            .addOreByproduct(() -> Materials_OLD.Silver)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadEmerald() {
        return new MaterialBuilder().setName("Emerald")
            .setDefaultLocalName("Emerald")
            .setIconSet(
                TextureSet.SET_EMERALD.withCustomTextures(
                    "emerald",
                    MaterialIconRegistry.IconType.ORE,
                    MaterialIconRegistry.IconType.ORE_SMALL))
            .setColor(Dyes.dyeGreen)
            .setARGB(0x7f50ff50)
            .setTool(256, 4, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Beryllium, 3)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 6)
            .addMaterial(Materials_OLD.Oxygen, 18)
            .addOreByproduct(() -> Materials_OLD.Beryllium)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.block) // minecraft:emerald_block
            .removeOrePrefix(OrePrefixes.gem) // minecraft:emerald
            .constructMaterial();
    }

    private static Materials_OLD loadEpoxid() {
        return new MaterialBuilder().setName("Epoxid")
            .setDefaultLocalName("Epoxid")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c88c14)
            .setTool(32, 1, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .removeOrePrefix(OrePrefixes.sheetmetal)
            .setMeltingPoint(400)
            .addMaterial(Materials_OLD.Carbon, 21)
            .addMaterial(Materials_OLD.Hydrogen, 24)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadFerriteMixture() {
        return new MaterialBuilder().setName("FerriteMixture")
            .setDefaultLocalName("Ferrite Mixture")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00b4b4b4)
            .addDustItems()
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Zinc, 1)
            .addMaterial(Materials_OLD.Iron, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadFerrosilite() {
        return new MaterialBuilder().setName("Ferrosilite")
            .setDefaultLocalName("Ferrosilite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x0097632a)
            .addDustItems()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadFreshWater() {
        return new MaterialBuilder().setName("FreshWater")
            .setDefaultLocalName("Fresh Water")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000000ff)
            .addCell()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addSubTag(SubTag.FOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadGalena() {
        return new MaterialBuilder().setName("Galena")
            .setDefaultLocalName("Galena")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00643c64)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Lead, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addOreByproduct(() -> Materials_OLD.Silver)
            .addOreByproduct(() -> Materials_OLD.Lead)
            .setDirectSmelting(() -> Materials_OLD.Lead)
            .constructMaterial();
    }

    private static Materials_OLD loadGarnierite() {
        return new MaterialBuilder().setName("Garnierite")
            .setDefaultLocalName("Garnierite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x0032c846)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .setDirectSmelting(() -> Materials_OLD.Nickel)
            .constructMaterial();
    }

    private static Materials_OLD loadGlyceryl() {
        return new MaterialBuilder().setName("Glyceryl")
            .setDefaultLocalName("Glyceryl Trinitrate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x00009696)
            .setMeltingPoint(291)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 5)
            .addMaterial(Materials_OLD.Nitrogen, 3)
            .addMaterial(Materials_OLD.Oxygen, 9)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static Materials_OLD loadGreenSapphire() {
        return new MaterialBuilder().setName("GreenSapphire")
            .setDefaultLocalName("Green Sapphire")
            .setIconSet(TextureSet.SET_GEM_HORIZONTAL)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x7f64c882)
            .setTool(256, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Sapphire)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGrossular() {
        return new MaterialBuilder().setName("Grossular")
            .setDefaultLocalName("Grossular")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00c86400)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addMaterial(Materials_OLD.Calcium, 3)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.GarnetYellow)
            .addOreByproduct(() -> Materials_OLD.Calcium)
            .constructMaterial();
    }

    private static Materials_OLD loadHolyWater() {
        return new MaterialBuilder().setName("HolyWater")
            .setDefaultLocalName("Holy Water")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000000ff)
            .addCell()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadHydricSulfide() {
        return new MaterialBuilder().setName("HydricSulfide")
            .setDefaultLocalName("Hydrogen Sulfide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .addCell()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadIce() {
        return new MaterialBuilder().setName("Ice")
            .setDefaultLocalName("Ice")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00c8c8ff)
            .addDustItems()
            .addCell()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .setSmeltingInto(() -> Materials_OLD.Water)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.NO_RECYCLING)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadIlmenite() {
        return new MaterialBuilder().setName("Ilmenite")
            .setDefaultLocalName("Ilmenite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00463732)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .setDensity(2, 1)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Titanium, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Rutile)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadInvar() {
        return new MaterialBuilder().setName("Invar")
            .setDefaultLocalName("Invar")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00b4b478)
            .setTool(256, 2, 6.0f)
            .setToolEnchantment(() -> Enchantment.baneOfArthropods, 3)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Iron, 2)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadKanthal() {
        return new MaterialBuilder().setName("Kanthal")
            .setDefaultLocalName("Kanthal")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00c2d2df)
            .setTool(64, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .setMeltingPoint(1_800)
            .setBlastFurnaceTemp(1_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadLazurite() {
        return new MaterialBuilder().setName("Lazurite")
            .setDefaultLocalName("Lazurite")
            .setIconSet(TextureSet.SET_LAPIS)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x006478ff)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(6)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Aluminium, 6)
            .addMaterial(Materials_OLD.Silicon, 6)
            .addMaterial(Materials_OLD.Calcium, 8)
            .addMaterial(Materials_OLD.Sodium, 8)
            .addOreByproduct(() -> Materials_OLD.Sodalite)
            .addOreByproduct(() -> Materials_OLD.Lapis)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadLiquidNitrogen() {
        return new MaterialBuilder().setName("LiquidNitrogen")
            .setDefaultLocalName("Liquid Nitrogen")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0xf0a9d0f5)
            .addCell()
            .setMeltingPoint(4)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadLiquidOxygen() {
        return new MaterialBuilder().setName("LiquidOxygen")
            .setDefaultLocalName("Liquid Oxygen")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0xf0a9d0f5)
            .addCell()
            .setMeltingPoint(4)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadMagnalium() {
        return new MaterialBuilder().setName("Magnalium")
            .setDefaultLocalName("Magnalium")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00c8beff)
            .setTool(256, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMagnesia() {
        return new MaterialBuilder().setName("Magnesia")
            .setDefaultLocalName("Magnesia")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffe1e1)
            .addDustItems()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadMagnesite() {
        return new MaterialBuilder().setName("Magnesite")
            .setDefaultLocalName("Magnesite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00fafab4)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .constructMaterial();
    }

    private static Materials_OLD loadMagnesiumchloride() {
        return new MaterialBuilder().setName("Magnesiumchloride")
            .setDefaultLocalName("Magnesiumchloride")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00d40d5c)
            .addDustItems()
            .addCell()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Chlorine, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadMagnetite() {
        return new MaterialBuilder().setName("Magnetite")
            .setDefaultLocalName("Magnetite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x001e1e1e)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .setMiningLevel(2)
            .addMaterial(Materials_OLD.Iron, 3)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Gold)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD)
            .constructMaterial();
    }

    private static Materials_OLD loadMassicot() {
        return new MaterialBuilder().setName("Massicot")
            .setDefaultLocalName("Massicot")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffdd55)
            .addDustItems()
            .addMaterial(Materials_OLD.Lead, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadMethane() {
        return new MaterialBuilder().setName("Methane")
            .setDefaultLocalName("Methane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeMagenta)
            .setFuel(MaterialBuilder.FuelType.Gas, 104)
            .setOreMultiplier(4)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadMolybdenite() {
        return new MaterialBuilder().setName("Molybdenite")
            .setDefaultLocalName("Molybdenite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00191919)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // MoS2 (also source of Re)
            .addMaterial(Materials_OLD.Molybdenum, 1)
            .addMaterial(Materials_OLD.Sulfur, 2)
            .addOreByproduct(() -> Materials_OLD.Molybdenum)
            .setDirectSmelting(() -> Materials_OLD.Molybdenum)
            .addSubTag(SubTag.DONT_ADD_DEFAULT_BBF_RECIPE)
            .constructMaterial();
    }

    private static Materials_OLD loadNichrome() {
        return new MaterialBuilder().setName("Nichrome")
            .setDefaultLocalName("Nichrome")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00cdcef6)
            .setTool(64, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .setMeltingPoint(2_700)
            .setBlastFurnaceTemp(2_700)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Nickel, 4)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadNickelZincFerrite() {
        return new MaterialBuilder().setName("NickelZincFerrite")
            .setDefaultLocalName("Nickel-Zinc Ferrite")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x003c3c3c)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setTool(32, 0, 3.0f)
            .setBlastFurnaceRequired(true)
            .setBlastFurnaceTemp(1_500)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Zinc, 1)
            .addMaterial(Materials_OLD.Iron, 4)
            .addMaterial(Materials_OLD.Oxygen, 8)
            .constructMaterial();
    }

    private static Materials_OLD loadNiobiumNitride() {
        // Anti-Reflective Material
        return new MaterialBuilder().setName("NiobiumNitride")
            .setDefaultLocalName("Niobium Nitride")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x001d291d)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(2_573)
            .setBlastFurnaceTemp(2_573)
            .setBlastFurnaceRequired(true)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Niobium, 1)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadNiobiumTitanium() {
        return new MaterialBuilder().setName("NiobiumTitanium")
            .setDefaultLocalName("Niobium-Titanium")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x001d1d29)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Niobium, 1)
            .addMaterial(Materials_OLD.Titanium, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadNitroCarbon() {
        return new MaterialBuilder().setName("NitroCarbon")
            .setDefaultLocalName("Nitro-Carbon")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x00004b64)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static Materials_OLD loadNitrogenDioxide() {
        return new MaterialBuilder().setName("NitrogenDioxide")
            .setDefaultLocalName("Nitrogen Dioxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x0064afff)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadNobleGases() {
        return new MaterialBuilder().setName("NobleGases")
            .setDefaultLocalName("Noble Gases")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0xf0a9d0f5)
            .addCell()
            .addGas()
            .setMeltingPoint(4)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.CarbonDioxide, 21)
            .addMaterial(Materials_OLD.Helium, 9)
            .addMaterial(Materials_OLD.Methane, 3)
            .addMaterial(Materials_OLD.Deuterium, 1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadObsidian() {
        return new MaterialBuilder().setName("Obsidian")
            .setDefaultLocalName("Obsidian")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00503264)
            .addDustItems()
            .addMetalItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Oxygen, 8)
            .addOreByproduct(() -> Materials_OLD.Olivine)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.plate)
            .removeOrePrefix(OrePrefixes.stickLong) // dreamcraft:item.LongObsidianRod
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphate() {
        return new MaterialBuilder().setName("Phosphate")
            .setDefaultLocalName("Phosphate")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Phosphorus, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addOreByproduct(() -> Materials_OLD.Phosphorus)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadPigIron() {
        return new MaterialBuilder().setName("PigIron")
            .setDefaultLocalName("Pig Iron")
            .setChemicalFormula("¿Fe?")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00c8b4b4)
            .setTool(384, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .setSmeltingInto(() -> Materials_OLD.Iron)
            .setMaceratingInto(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_TRIPLE)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadPolycaprolactam() {
        return new MaterialBuilder().setName("Polycaprolactam")
            .setDefaultLocalName("Polycaprolactam (PCL)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00323232)
            .setTool(32, 1, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .removeOrePrefix(OrePrefixes.sheetmetal)
            .setMeltingPoint(500)
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 11)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadPolydimethylsiloxane() {
        return new MaterialBuilder().setName("Polydimethylsiloxane")
            .setDefaultLocalName("Polydimethylsiloxane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00f5f5f5)
            .addDustItems()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadPolyethylene() {
        return new MaterialBuilder().setName("Plastic")
            .setDefaultLocalName("Polyethylene (PE)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c8c8c8)
            .setTool(32, 1, 3.0f)
            .setToolEnchantment(() -> Enchantment.knockback, 1)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(400)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addSubTag(SubTag.BOUNCY)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadPolytetrafluoroethylene() {
        return new MaterialBuilder().setName("Polytetrafluoroethylene")
            .setDefaultLocalName("Polytetrafluoroethylene (PTFE)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00646464)
            .setTool(32, 1, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(600)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Fluorine, 4)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadPotash() {
        return new MaterialBuilder().setName("Potash")
            .setDefaultLocalName("Potash")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00784237)
            .addDustItems()
            .addMaterial(Materials_OLD.Potassium, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadPowellite() {
        return new MaterialBuilder().setName("Powellite")
            .setDefaultLocalName("Powellite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Molybdenum, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadPumice() {
        return new MaterialBuilder().setName("Pumice")
            .setDefaultLocalName("Pumice")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00e6b9b9)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Stone, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPyrite() {
        return new MaterialBuilder().setName("Pyrite")
            .setDefaultLocalName("Pyrite")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00967828)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Sulfur, 2)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addOreByproduct(() -> Materials_OLD.TricalciumPhosphate)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadPyrochlore() {
        return new MaterialBuilder().setName("Pyrochlore")
            .setDefaultLocalName("Pyrochlore")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x002b1100)
            .setMiningLevel(0)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Calcium, 2)
            .addMaterial(Materials_OLD.Niobium, 2)
            .addMaterial(Materials_OLD.Oxygen, 7)
            .addOreByproduct(() -> Materials_OLD.Apatite)
            .addOreByproduct(() -> Materials_OLD.Calcite)
            .addOreByproduct(() -> Materials_OLD.Niobium)
            .constructMaterial();
    }

    private static Materials_OLD loadPyrolusite() {
        return new MaterialBuilder().setName("Pyrolusite")
            .setDefaultLocalName("Pyrolusite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x009696aa)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Manganese, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.Manganese)
            .addOreByproduct(() -> Materials_OLD.Tantalite)
            .addOreByproduct(() -> Materials_OLD.Niobium)
            .addSubTag(SubTag.DONT_ADD_DEFAULT_BBF_RECIPE)
            .constructMaterial();
    }

    private static Materials_OLD loadPyrope() {
        return new MaterialBuilder().setName("Pyrope")
            .setDefaultLocalName("Pyrope")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00783264)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Magnesium, 3)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.GarnetRed)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .constructMaterial();
    }

    private static Materials_OLD loadQuicklime() {
        return new MaterialBuilder().setName("Quicklime")
            .setDefaultLocalName("Quicklime")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00f0f0f0)
            .addDustItems()
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadRockSalt() {
        return new MaterialBuilder().setName("RockSalt")
            .setDefaultLocalName("Rock Salt")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00f0c8c8)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addOreByproduct(() -> Materials_OLD.Salt)
            .addOreByproduct(() -> Materials_OLD.Borax)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .constructMaterial();
    }

    private static Materials_OLD loadRubber() {
        return new MaterialBuilder().setName("Rubber")
            .setDefaultLocalName("Rubber")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00000000)
            .setTool(32, 0, 1.5f)
            .setToolEnchantment(() -> Enchantment.knockback, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(400)
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addSubTag(SubTag.BOUNCY)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadRubberRaw() {
        return new MaterialBuilder().setName("RawRubber")
            .setDefaultLocalName("Raw Rubber")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ccc789)
            .addDustItems()
            .setMeltingPoint(400)
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .constructMaterial();
    }

    private static Materials_OLD loadRubberSilicone() {
        return new MaterialBuilder().setName("Silicone")
            .setDefaultLocalName("Silicone Rubber")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdcdc)
            .setTool(128, 1, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(900)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addSubTag(SubTag.BOUNCY)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadRuby() {
        return new MaterialBuilder().setName("Ruby")
            .setDefaultLocalName("Ruby")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeRed)
            .setARGB(0x7fff6464)
            .setTool(256, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Chrome, 1)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Chrome)
            .addOreByproduct(() -> Materials_OLD.GarnetRed)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadRutile() {
        return new MaterialBuilder().setName("Rutile")
            .setDefaultLocalName("Rutile")
            .setIconSet(TextureSet.SET_GEM_HORIZONTAL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00d40d5c)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .setDensity(2, 1)
            .addMaterial(Materials_OLD.Titanium, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadSalt() {
        return new MaterialBuilder().setName("Salt")
            .setDefaultLocalName("Salt")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(2)
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addOreByproduct(() -> Materials_OLD.RockSalt)
            .addOreByproduct(() -> Materials_OLD.Borax)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .constructMaterial();
    }

    private static Materials_OLD loadSaltpeter() {
        return new MaterialBuilder().setName("Saltpeter")
            .setDefaultLocalName("Saltpeter")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e6e6e6)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(4)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Saltpeter)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSapphire() {
        return new MaterialBuilder().setName("Sapphire")
            .setDefaultLocalName("Sapphire")
            .setIconSet(TextureSet.SET_GEM_VERTICAL)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x7f6464c8)
            .setTool(256, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.GreenSapphire)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadScheelite() {
        return new MaterialBuilder().setName("Scheelite")
            .setDefaultLocalName("Scheelite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00c88c14)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(2)
            .setMeltingPoint(2_500)
            .setBlastFurnaceTemp(2_500)
            .addMaterial(Materials_OLD.Tungsten, 1)
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addOreByproduct(() -> Materials_OLD.Manganese)
            .addOreByproduct(() -> Materials_OLD.Molybdenum)
            .addOreByproduct(() -> Materials_OLD.Calcium)
            .constructMaterial();
    }

    private static Materials_OLD loadSiliconDioxide() {
        return new MaterialBuilder().setName("SiliconDioxide")
            .setDefaultLocalName("Silicon Dioxide")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeLightGray)
            .addDustItems()
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.SiliconDioxide)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.QUARTZ)
            .constructMaterial();
    }

    private static Materials_OLD loadSnow() {
        return new MaterialBuilder().setName("Snow")
            .setDefaultLocalName("Snow")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .addDustItems()
            .addCell()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .setSmeltingInto(() -> Materials_OLD.Water)
            .addSubTag(SubTag.NO_RECYCLING)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSodaAsh() {
        return new MaterialBuilder().setName("SodaAsh")
            .setDefaultLocalName("Soda Ash")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdcff)
            .addDustItems()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadSodalite() {
        return new MaterialBuilder().setName("Sodalite")
            .setDefaultLocalName("Sodalite")
            .setIconSet(TextureSet.SET_LAPIS)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x001414ff)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(6)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Aluminium, 3)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Sodium, 4)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addOreByproduct(() -> Materials_OLD.Lazurite)
            .addOreByproduct(() -> Materials_OLD.Lapis)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumPersulfate() {
        return new MaterialBuilder().setName("SodiumPersulfate")
            .setDefaultLocalName("Sodium Persulfate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Sulfur, 2)
            .addMaterial(Materials_OLD.Oxygen, 8)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumPotassium() {
        return new MaterialBuilder().setName("SodiumPotassium")
            .setDefaultLocalName("Sodium Potassium")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0016ff24)
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Potassium, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumSulfide() {
        return new MaterialBuilder().setName("SodiumSulfide")
            .setDefaultLocalName("Sodium Sulfide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ffe680)
            .addDustItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadTitaniumtetrachloride() {
        return new MaterialBuilder().setName("Titaniumtetrachloride")
            .setDefaultLocalName("Titaniumtetrachloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00d40d5c)
            .setMeltingPoint(249)
            .addCell()
            .addMaterial(Materials_OLD.Titanium, 1)
            .addMaterial(Materials_OLD.Chlorine, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadWater() {
        return new MaterialBuilder().setName("Water")
            .setDefaultLocalName("Water")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000000ff)
            .addCell()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.NO_RECYCLING)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.cell) // IC2:itemCellEmpty:1
            .removeOrePrefix(OrePrefixes.bucket) // minecraft:water_bucket
            .removeOrePrefix(OrePrefixes.bucketClay) // IguanaTweaksTConstruct:clayBucketWater
            .constructMaterial();
    }

    private static Materials_OLD loadZincite() {
        return new MaterialBuilder().setName("Zincite")
            .setDefaultLocalName("Zincite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fffff5)
            .addDustItems()
            .addMaterial(Materials_OLD.Zinc, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static void loadUnclassified01() {
        Materials_OLD.DenseSteam = loadDenseSteam();
        Materials_OLD.DenseSuperheatedSteam = loadDenseSuperheatedSteam();
        Materials_OLD.DenseSupercriticalSteam = loadDenseSupercriticalSteam();
        Materials_OLD.OilExtraHeavy = loadOilExtraHeavy();
        Materials_OLD.OilHeavy = loadOilHeavy();
        Materials_OLD.OilLight = loadOilLight();
        Materials_OLD.OilMedium = loadOilMedium();
        Materials_OLD.SuperCoolant = loadSuperCoolant();
        Materials_OLD.EnrichedHolmium = loadEnrichedHolmium();
        Materials_OLD.TengamPurified = loadTengamPurified();
        Materials_OLD.TengamAttuned = loadTengamAttuned();
        Materials_OLD.TengamRaw = loadTengamRaw();
    }

    private static Materials_OLD loadDenseSteam() {
        return new MaterialBuilder().setName("DenseSteam")
            .setDefaultLocalName("Dense Steam")
            .addCell()
            .addGas()
            .setColor(Dyes.dyeWhite)
            .constructMaterial()
            .setGasTemperature(420);
    }

    private static Materials_OLD loadDenseSuperheatedSteam() {
        return new MaterialBuilder().setName("DenseSuperheatedSteam")
            .setDefaultLocalName("Dense Superheated Steam")
            .addCell()
            .addGas()
            .setColor(Dyes.dyeWhite)
            .constructMaterial()
            .setGasTemperature(600);
    }

    private static Materials_OLD loadDenseSupercriticalSteam() {
        return new MaterialBuilder().setName("DenseSupercriticalSteam")
            .setDefaultLocalName("Dense Supercritical Steam")
            .addCell()
            .addGas()
            .setColor(Dyes.dyeWhite)
            .constructMaterial()
            .setGasTemperature(600);
    }

    private static Materials_OLD loadOilExtraHeavy() {
        return new MaterialBuilder().setName("OilExtraHeavy")
            .setDefaultLocalName("Very Heavy Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 45)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadOilHeavy() {
        return new MaterialBuilder().setName("OilHeavy")
            .setDefaultLocalName("Heavy Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 40)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadOilLight() {
        return new MaterialBuilder().setName("OilLight")
            .setDefaultLocalName("Light Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 20)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadOilMedium() {
        return new MaterialBuilder().setName("OilMedium")
            .setDefaultLocalName("Raw Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 30)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadSuperCoolant() {
        return new MaterialBuilder().setName("SuperCoolant")
            .setDefaultLocalName("Super Coolant")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00025b6f)
            .addCell()
            .addFluid()
            .constructMaterial()
            .setLiquidTemperature(1);
    }

    private static Materials_OLD loadEnrichedHolmium() {
        return new MaterialBuilder().setName("EnrichedHolmium")
            .setDefaultLocalName("Enriched Holmium")
            .setChemicalFormula("Nq+₄Ho₁")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePurple)
            .setARGB(0x001264ff)
            .addMetalItems()
            .setMeltingPoint(3_000)
            .setBlastFurnaceTemp(3_000)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadTengamPurified() {
        return new MaterialBuilder().setName("TengamPurified")
            .setDefaultLocalName("Purified Tengam")
            .setChemicalFormula("M")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00badf70)
            .addDustItems()
            .addGearItems()
            .addMetalItems()
            .addToolHeadItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UV);
    }

    private static Materials_OLD loadTengamAttuned() {
        return new MaterialBuilder().setName("TengamAttuned")
            .setDefaultLocalName("Attuned Tengam")
            .setChemicalFormula(CustomGlyphs.HIGH_VOLTAGE + "M" + CustomGlyphs.MAGNET)
            .setIconSet(TextureSet.SET_MAGNETIC)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00d5ff80)
            .addDustItems()
            .addGearItems()
            .addMetalItems()
            .addToolHeadItems()
            .setSmeltingInto(() -> Materials_OLD.TengamPurified)
            .setMaceratingInto(() -> Materials_OLD.TengamPurified)
            .setArcSmeltingInto(() -> Materials_OLD.TengamPurified)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UV);
    }

    private static Materials_OLD loadTengamRaw() {
        return new MaterialBuilder().setName("TengamRaw")
            .setDefaultLocalName("Raw Tengam")
            .setChemicalFormula("M" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00a0bf60)
            .setMiningLevel(0)
            .addOreItems()
            .addOreByproduct(() -> Materials_OLD.NeodymiumMagnetic)
            .addOreByproduct(() -> Materials_OLD.SamariumMagnetic)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UV);
    }

    private static void loadUnclassified02() {
        Materials_OLD.Gas = loadGas();
        Materials_OLD.HeavyFuel = loadHeavyFuel();
        Materials_OLD.LightFuel = loadLightFuel();
        Materials_OLD.LPG = loadLPG();
        Materials_OLD.Naphtha = loadNaphtha();
        Materials_OLD.NaturalGas = loadNaturalGas();
        Materials_OLD.SulfuricGas = loadSulfuricGas();
        Materials_OLD.SulfuricHeavyFuel = loadSulfuricHeavyFuel();
        Materials_OLD.SulfuricLightFuel = loadSulfuricLightFuel();
        Materials_OLD.SulfuricNaphtha = loadSulfuricNaphtha();
    }

    private static Materials_OLD loadGas() {
        return new MaterialBuilder().setName("Gas")
            .setDefaultLocalName("Refinery Gas")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setFuel(MaterialBuilder.FuelType.Gas, 160)
            .addCell()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadHeavyFuel() {
        return new MaterialBuilder().setName("HeavyFuel")
            .setDefaultLocalName("Heavy Fuel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 240)
            .addCell()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadLightFuel() {
        return new MaterialBuilder().setName("LightFuel")
            .setDefaultLocalName("Light Fuel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.Diesel, 305)
            .addCell()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadLPG() {
        return new MaterialBuilder().setName("LPG")
            .setDefaultLocalName("LPG")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.Gas, 320)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadNaphtha() {
        return new MaterialBuilder().setName("Naphtha")
            .setDefaultLocalName("Naphtha")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.Gas, 220)
            .addCell()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadNaturalGas() {
        return new MaterialBuilder().setName("NatruralGas")
            .setDefaultLocalName("Natural Gas")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setFuel(MaterialBuilder.FuelType.Gas, 20)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadSulfuricGas() {
        return new MaterialBuilder().setName("SulfuricGas")
            .setDefaultLocalName("Sulfuric Gas")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setFuel(MaterialBuilder.FuelType.Gas, 25)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadSulfuricHeavyFuel() {
        return new MaterialBuilder().setName("SulfuricHeavyFuel")
            .setDefaultLocalName("Sulfuric Heavy Fuel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 40)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadSulfuricLightFuel() {
        return new MaterialBuilder().setName("SulfuricLightFuel")
            .setDefaultLocalName("Sulfuric Light Fuel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.Diesel, 40)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadSulfuricNaphtha() {
        return new MaterialBuilder().setName("SulfuricNaphtha")
            .setDefaultLocalName("Sulfuric Naphtha")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setFuel(MaterialBuilder.FuelType.Gas, 40)
            .addCell()
            .constructMaterial();
    }

    private static void loadUnclassified03() {
        Materials_OLD.BioMediumRaw = loadBioMediumRaw();
        Materials_OLD.BioMediumSterilized = loadBioMediumSterilized();
        Materials_OLD.ReinforcedGlass = loadReinforcedGlass();
    }

    private static Materials_OLD loadBioMediumRaw() {
        return new MaterialBuilder().setName("BioMediumRaw")
            .setDefaultLocalName("Raw Bio Catalyst Medium")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0061932e)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadBioMediumSterilized() {
        return new MaterialBuilder().setName("BiohMediumSterilized")
            .setDefaultLocalName("Sterilized Bio Catalyst Medium")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00a2fd35)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadReinforcedGlass() {
        return new MaterialBuilder().setName("ReinforcedGlass")
            .setDefaultLocalName("Reinforced Glass")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c0f5fe)
            .setMeltingPoint(2_000)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static void loadUnclassified04() {
        Materials_OLD.GrowthMediumRaw = loadGrowthMediumRaw();
        Materials_OLD.GrowthMediumSterilized = loadGrowthMediumSterilized();
    }

    private static Materials_OLD loadGrowthMediumRaw() {
        return new MaterialBuilder().setName("GrowthMediumRaw")
            .setDefaultLocalName("Raw Growth Catalyst Medium")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00d38d5f)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadGrowthMediumSterilized() {
        return new MaterialBuilder().setName("GrowthMediumSterilized")
            .setDefaultLocalName("Growth Catalyst Medium")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00deaa87)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static void loadUnclassified05() {
        Materials_OLD.BioDiesel = loadBioDiesel();
        Materials_OLD.BisphenolA = loadBisphenolA();
        Materials_OLD.Butadiene = loadButadiene();
        Materials_OLD.Butane = loadButane();
        Materials_OLD.Butene = loadButene();
        Materials_OLD.CalciumAcetateSolution = loadCalciumAcetateSolution();
        Materials_OLD.CarbonMonoxide = loadCarbonMonoxide();
        Materials_OLD.CaesiumHydroxide = loadCaesiumHydroxide();
        Materials_OLD.Chloramine = loadChloramine();
        Materials_OLD.Chloroform = loadChloroform();
        Materials_OLD.Chloromethane = loadChloromethane();
        Materials_OLD.Cumene = loadCumene();
        Materials_OLD.Dichlorobenzene = loadDichlorobenzene();
        Materials_OLD.Dimethylamine = loadDimethylamine();
        Materials_OLD.Dimethyldichlorosilane = loadDimethyldichlorosilane();
        Materials_OLD.Dimethylhydrazine = loadDimethylhydrazine();
        Materials_OLD.DinitrogenTetroxide = loadDinitrogenTetroxide();
        Materials_OLD.Epichlorohydrin = loadEpichlorohydrin();
        Materials_OLD.Ethane = loadEthane();
        Materials_OLD.Ethenone = loadEthenone();
        Materials_OLD.Ethylene = loadEthylene();
        Materials_OLD.Glycerol = loadGlycerol();
        Materials_OLD.HydrochloricAcid = loadHydrochloricAcid();
        Materials_OLD.HydrofluoricAcid = loadHydrofluoricAcid();
        Materials_OLD.HypochlorousAcid = loadHypochlorousAcid();
        Materials_OLD.IronIIIChloride = loadIronIIIChloride();
        Materials_OLD.Isoprene = loadIsoprene();
        Materials_OLD.LifeEssence = loadLifeEssence();
        Materials_OLD.MetalMixture = loadMetalMixture();
        Materials_OLD.Methanol = loadMethanol();
        Materials_OLD.MethylAcetate = loadMethylAcetate();
        Materials_OLD.NitrationMixture = loadNitrationMixture();
        Materials_OLD.NitricAcid = loadNitricAcid();
        Materials_OLD.NitricOxide = loadNitricOxide();
        Materials_OLD.Phenol = loadPhenol();
        Materials_OLD.PhosphoricAcid = loadPhosphoricAcid();
        Materials_OLD.PhosphorousPentoxide = loadPhosphorousPentoxide();
        Materials_OLD.PolyphenyleneSulfide = loadPolyphenyleneSulfide();
        Materials_OLD.Polystyrene = loadPolystyrene();
        Materials_OLD.PolyvinylAcetate = loadPolyvinylAcetate();
        Materials_OLD.PolyvinylChloride = loadPolyvinylChloride();
        Materials_OLD.PotassiumHydroxide = loadPotassiumHydroxide();
        Materials_OLD.Propane = loadPropane();
        Materials_OLD.Propene = loadPropene();
        Materials_OLD.SaltWater = loadSaltWater();
        Materials_OLD.SodiumBisulfate = loadSodiumBisulfate();
        Materials_OLD.SodiumHydroxide = loadSodiumHydroxide();
        Materials_OLD.SodiumOxide = loadSodiumOxide();
        Materials_OLD.Styrene = loadStyrene();
        Materials_OLD.StyreneButadieneRubber = loadStyreneButadieneRubber();
        Materials_OLD.SulfurDioxide = loadSulfurDioxide();
        Materials_OLD.SulfurTrioxide = loadSulfurTrioxide();
        Materials_OLD.Tetrafluoroethylene = loadTetrafluoroethylene();
        Materials_OLD.Tetranitromethane = loadTetranitromethane();
        Materials_OLD.Toluene = loadToluene();
        Materials_OLD.VinylAcetate = loadVinylAcetate();
        Materials_OLD.VinylChloride = loadVinylChloride();
    }

    private static Materials_OLD loadBioDiesel() {
        return new MaterialBuilder().setName("BioDiesel")
            .setDefaultLocalName("Bio Diesel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8000)
            .setFuel(MaterialBuilder.FuelType.Diesel, 320)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadBisphenolA() {
        return new MaterialBuilder().setName("BisphenolA")
            .setDefaultLocalName("Bisphenol A")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00d4aa00)
            .setMeltingPoint(428)
            .addCell()
            .addMaterial(Materials_OLD.Carbon, 15)
            .addMaterial(Materials_OLD.Hydrogen, 16)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadButadiene() {
        return new MaterialBuilder().setName("Butadiene")
            .setDefaultLocalName("Butadiene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00e86900)
            .setFuel(MaterialBuilder.FuelType.Gas, 206)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadButane() {
        return new MaterialBuilder().setName("Butane")
            .setDefaultLocalName("Butane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00b6371e)
            .setFuel(MaterialBuilder.FuelType.Gas, 296)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadButene() {
        return new MaterialBuilder().setName("Butene")
            .setDefaultLocalName("Butene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00cf5005)
            .setFuel(MaterialBuilder.FuelType.Gas, 256)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadCaesiumHydroxide() {
        return new MaterialBuilder().setName("CaesiumHydroxide_GT5U")
            .setDefaultLocalName("Caesium Hydroxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00becaee)
            .addDustItems()
            .addMaterial(Materials_OLD.Caesium, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadCalciumAcetateSolution() {
        return new MaterialBuilder().setName("CalciumAcetateSolution")
            .setDefaultLocalName("Calcium Acetate Solution")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x00dcc8b4)
            .setMeltingPoint(433)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadCarbonMonoxide() {
        return new MaterialBuilder().setName("CarbonMonoxide")
            .setDefaultLocalName("Carbon Monoxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x000e4880)
            .setFuel(MaterialBuilder.FuelType.Gas, 24)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadChloramine() {
        return new MaterialBuilder().setName("Chloramine")
            .setDefaultLocalName("Chloramine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x003f9f80)
            .setMeltingPoint(207)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadChloroform() {
        return new MaterialBuilder().setName("Chloroform")
            .setDefaultLocalName("Chloroform")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00892ca0)
            .setMeltingPoint(210)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Chlorine, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadChloromethane() {
        return new MaterialBuilder().setName("Chloromethane")
            .setDefaultLocalName("Chloromethane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x00c82ca0)
            .setMeltingPoint(176)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadCumene() {
        return new MaterialBuilder().setName("Isopropylbenzene")
            .setDefaultLocalName("Isopropylbenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00552200)
            .setMeltingPoint(177)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 9)
            .addMaterial(Materials_OLD.Hydrogen, 12)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDichlorobenzene() {
        return new MaterialBuilder().setName("Dichlorobenzene")
            .setDefaultLocalName("Dichlorobenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00004455)
            .setMeltingPoint(327)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Chlorine, 2)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDimethylamine() {
        return new MaterialBuilder().setName("Dimethylamine")
            .setDefaultLocalName("Dimethylamine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00554469)
            .setMeltingPoint(180)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 7)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDimethyldichlorosilane() {
        return new MaterialBuilder().setName("Dimethyldichlorosilane")
            .setDefaultLocalName("Dimethyldichlorosilane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00441650)
            .setMeltingPoint(197)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Chlorine, 2)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDimethylhydrazine() {
        return new MaterialBuilder().setName("1,1Dimethylhydrazine")
            .setDefaultLocalName("1,1-Dimethylhydrazine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00000055)
            .setMeltingPoint(216)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDinitrogenTetroxide() {
        return new MaterialBuilder().setName("DinitrogenTetroxide")
            .setDefaultLocalName("Dinitrogen Tetroxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00004184)
            .setMeltingPoint(173)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadEpichlorohydrin() {
        return new MaterialBuilder().setName("Epichlorohydrin")
            .setDefaultLocalName("Epichlorohydrin")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00501d05)
            .setMeltingPoint(248)
            .addCell()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 5)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadEthane() {
        return new MaterialBuilder().setName("Ethane")
            .setDefaultLocalName("Ethane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00c8c8ff)
            .setFuel(MaterialBuilder.FuelType.Gas, 168)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadEthenone() {
        return new MaterialBuilder().setName("Ethenone")
            .setDefaultLocalName("Ethenone")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00141446)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadEthylene() {
        return new MaterialBuilder().setName("Ethylene")
            .setDefaultLocalName("Ethylene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e1e1e1)
            .setFuel(MaterialBuilder.FuelType.Gas, 128)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadGlycerol() {
        return new MaterialBuilder().setName("Glycerol")
            .setDefaultLocalName("Glycerol")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0087de87)
            .setMeltingPoint(291)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 164)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadHydrochloricAcid() {
        return new MaterialBuilder().setName("HydrochloricAcid_GT5U")
            .setDefaultLocalName("Hydrochloric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00b7c8c4)
            .setMeltingPoint(247)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadHydrofluoricAcid() {
        return new MaterialBuilder().setName("HydrofluoricAcid_GT5U")
            .setDefaultLocalName("Hydrofluoric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x000088aa)
            .setMeltingPoint(190)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Fluorine, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadHypochlorousAcid() {
        return new MaterialBuilder().setName("HypochlorousAcid")
            .setDefaultLocalName("Hypochlorous Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x006f8a91)
            .setMeltingPoint(161)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadIronIIIChloride() {
        return new MaterialBuilder().setName("IronIIIChloride")
            .setDefaultLocalName("Iron III Chloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x0016150e)
            .setMeltingPoint(273)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Chlorine, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadIsoprene() {
        return new MaterialBuilder().setName("Isoprene")
            .setDefaultLocalName("Isoprene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00141414)
            .setMeltingPoint(129)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadLifeEssence() {
        return new MaterialBuilder().setName("lifeessence")
            .setDefaultLocalName("Life")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x006e0303)
            .setFuel(MaterialBuilder.FuelType.Magic, 100)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadMetalMixture() {
        return new MaterialBuilder().setName("MetalMixture")
            .setDefaultLocalName("Metal Mixture")
            .setChemicalFormula(
                "Fe" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK + "O" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK + "??")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00502d16)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadMethanol() {
        return new MaterialBuilder().setName("Methanol")
            .setDefaultLocalName("Methanol")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00aa8800)
            .setMeltingPoint(176)
            .setFuel(MaterialBuilder.FuelType.Diesel, 84)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadMethylAcetate() {
        return new MaterialBuilder().setName("MethylAcetate")
            .setDefaultLocalName("Methyl Acetate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00eec6af)
            .setMeltingPoint(190)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadNitrationMixture() {
        return new MaterialBuilder().setName("NitrationMixture")
            .setDefaultLocalName("Nitration Mixture")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00e6e2ab)
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadNitricAcid() {
        return new MaterialBuilder().setName("NitricAcid")
            .setDefaultLocalName("Nitric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00e6e2ab)
            .setMeltingPoint(231)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadNitricOxide() {
        return new MaterialBuilder().setName("NitricOxide")
            .setDefaultLocalName("Nitric Oxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x007dc8f0)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadPhenol() {
        return new MaterialBuilder().setName("Phenol")
            .setDefaultLocalName("Phenol")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00784421)
            .setMeltingPoint(314)
            .setFuel(MaterialBuilder.FuelType.Gas, 288)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphoricAcid() {
        return new MaterialBuilder().setName("PhosphoricAcid_GT5U")
            .setDefaultLocalName("Phosphoric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00dcdc00)
            .setMeltingPoint(316)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .addMaterial(Materials_OLD.Phosphorus, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphorousPentoxide() {
        return new MaterialBuilder().setName("PhosphorousPentoxide")
            .setDefaultLocalName("Phosphorus Pentoxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00dcdc00)
            .addCell()
            .addDustItems()
            .addMaterial(Materials_OLD.Phosphorus, 4)
            .addMaterial(Materials_OLD.Oxygen, 10)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadPolyphenyleneSulfide() {
        return new MaterialBuilder().setName("PolyphenyleneSulfide")
            .setDefaultLocalName("Polyphenylene Sulfide (PPS)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00aa8800)
            .setMeltingPoint(554)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setTool(32, 1, 3.0f)
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadPolystyrene() {
        return new MaterialBuilder().setName("Polystyrene")
            .setDefaultLocalName("Polystyrene (PS)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00beb4aa)
            .setMeltingPoint(513)
            .setTool(32, 1, 3.0f)
            .setToolEnchantment(() -> Enchantment.knockback, 1)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addSubTag(SubTag.BOUNCY)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadPolyvinylAcetate() {
        return new MaterialBuilder().setName("PolyvinylAcetate")
            .setDefaultLocalName("Polyvinyl Acetate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff9955)
            .setMeltingPoint(333)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadPolyvinylChloride() {
        return new MaterialBuilder().setName("PolyvinylChloride")
            .setDefaultLocalName("Polyvinyl Chloride (PVC)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00d7e6e6)
            .setMeltingPoint(433)
            .setTool(32, 1, 3.0f)
            .setToolEnchantment(() -> Enchantment.knockback, 1)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addSubTag(SubTag.BOUNCY)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadPotassiumHydroxide() {
        return new MaterialBuilder().setName("PotassiumHydroxide_GT5U")
            .setDefaultLocalName("Potassium Hydroxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x005e4a9e)
            .addDustItems()
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPropane() {
        return new MaterialBuilder().setName("Propane")
            .setDefaultLocalName("Propane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00fae250)
            .setFuel(MaterialBuilder.FuelType.Gas, 232)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadPropene() {
        return new MaterialBuilder().setName("Propene")
            .setDefaultLocalName("Propene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffdd55)
            .setFuel(MaterialBuilder.FuelType.Gas, 192)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addElectrolyzerRecipe()
            .addCrackingRecipes()
            .constructMaterial();
    }

    private static Materials_OLD loadSaltWater() {
        return new MaterialBuilder().setName("SaltWater")
            .setDefaultLocalName("Salt Water")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000000c8)
            .setMeltingPoint(271)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumBisulfate() {
        return new MaterialBuilder().setName("SodiumBisulfate")
            .setDefaultLocalName("Sodium Bisulfate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00004455)
            .addDustItems()
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumHydroxide() {
        return new MaterialBuilder().setName("SodiumHydroxide_GT5U")
            .setDefaultLocalName("Sodium Hydroxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00003380)
            .addDustItems()
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumOxide() {
        return new MaterialBuilder().setName("SodiumOxide")
            .setDefaultLocalName("Sodium Oxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffffeb)
            .addDustItems()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadStyrene() {
        return new MaterialBuilder().setName("Styrene")
            .setDefaultLocalName("Styrene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00d2c8be)
            .setMeltingPoint(243)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadStyreneButadieneRubber() {
        return new MaterialBuilder().setName("StyreneButadieneRubber")
            .setDefaultLocalName("Styrene-Butadiene Rubber (SBR)")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00211a18)
            .setMeltingPoint(526)
            .setTool(128, 1, 3.0f)
            .setToolEnchantment(() -> Enchantment.knockback, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Styrene, 1)
            .addMaterial(Materials_OLD.Butadiene, 3)
            .addSubTag(SubTag.BOUNCY)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadSulfurDioxide() {
        return new MaterialBuilder().setName("SulfurDioxide")
            .setDefaultLocalName("Sulfur Dioxide")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00c8c819)
            .setOreMultiplier(4)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addSubTag(SubTag.ICE_ORE)
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .addOrePrefix(OrePrefixes.ore)
            .addOrePrefix(OrePrefixes.rawOre)
            .constructMaterial();
    }

    private static Materials_OLD loadSulfurTrioxide() {
        return new MaterialBuilder().setName("SulfurTrioxide")
            .setDefaultLocalName("Sulfur Trioxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00a0a014)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadTetrafluoroethylene() {
        return new MaterialBuilder().setName("Tetrafluoroethylene")
            .setDefaultLocalName("Tetrafluoroethylene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x007d7d7d)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Fluorine, 4)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadTetranitromethane() {
        return new MaterialBuilder().setName("Tetranitromethane")
            .setDefaultLocalName("Tetranitromethane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000f2828)
            .setMeltingPoint(287)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Nitrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 8)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadToluene() {
        return new MaterialBuilder().setName("Toluene")
            .setDefaultLocalName("Toluene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00501d05)
            .setFuel(MaterialBuilder.FuelType.Gas, 328)
            .addCell()
            .addMaterial(Materials_OLD.Carbon, 7)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadVinylAcetate() {
        return new MaterialBuilder().setName("VinylAcetate")
            .setDefaultLocalName("Vinyl Acetate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ffb380)
            .setMeltingPoint(180)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadVinylChloride() {
        return new MaterialBuilder().setName("VinylChloride")
            .setDefaultLocalName("Vinyl Chloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00e1f0f0)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static void loadRoastedOres() {
        Materials_OLD.RoastedAntimony = loadRoastedAntimony();
        Materials_OLD.RoastedArsenic = loadRoastedArsenic();
        Materials_OLD.RoastedCobalt = loadRoastedCobalt();
        Materials_OLD.RoastedCopper = loadRoastedCopper();
        Materials_OLD.RoastedIron = loadRoastedIron();
        Materials_OLD.RoastedLead = loadRoastedLead();
        Materials_OLD.RoastedNickel = loadRoastedNickel();
        Materials_OLD.RoastedZinc = loadRoastedZinc();
    }

    private static Materials_OLD loadRoastedAntimony() {
        return new MaterialBuilder().setName("RoastedAntimony")
            .setDefaultLocalName("Roasted Antimony")
            .setChemicalFormula("Sb" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00c4b2c2)
            .addDustItems()
            .setDirectSmelting(() -> Materials_OLD.Antimony)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedArsenic() {
        return new MaterialBuilder().setName("RoastedArsenic")
            .setDefaultLocalName("Roasted Arsenic")
            .setChemicalFormula("As" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00f0f0f0)
            .addDustItems()
            .setDirectSmelting(() -> Materials_OLD.Arsenic)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedCobalt() {
        return new MaterialBuilder().setName("RoastedCobalt")
            .setDefaultLocalName("Roasted Cobalt")
            .setChemicalFormula("Co" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00084009)
            .addDustItems()
            .setDirectSmelting(() -> Materials_OLD.Cobalt)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedCopper() {
        return new MaterialBuilder().setName("RoastedCopper")
            .setDefaultLocalName("Roasted Copper")
            .setChemicalFormula("Cu" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x004d1212)
            .addDustItems()
            .setDirectSmelting(() -> Materials_OLD.Copper)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedIron() {
        return new MaterialBuilder().setName("RoastedIron")
            .setDefaultLocalName("Roasted Iron")
            .setChemicalFormula("Fe" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00946262)
            .setMiningLevel(0)
            .addDustItems()
            .addOreItems()
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedLead() {
        return new MaterialBuilder().setName("RoastedLead")
            .setDefaultLocalName("Roasted Lead")
            .setChemicalFormula("Pb" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00a8952b)
            .addDustItems()
            .setDirectSmelting(() -> Materials_OLD.Lead)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedNickel() {
        return new MaterialBuilder().setName("RoastedNickel")
            .setDefaultLocalName("Roasted Nickel")
            .setChemicalFormula("Ni" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00468c2d)
            .setMiningLevel(0)
            .addDustItems()
            .addOreItems()
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .setDirectSmelting(() -> Materials_OLD.Nickel)
            .constructMaterial();
    }

    private static Materials_OLD loadRoastedZinc() {
        return new MaterialBuilder().setName("RoastedZinc")
            .setDefaultLocalName("Roasted Zinc")
            .setChemicalFormula("Zn" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK)
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00d1d1d1)
            .setMiningLevel(0)
            .addDustItems()
            .setDirectSmelting(() -> Materials_OLD.Zinc)
            .constructMaterial();
    }

    private static void loadSiliconLine() {
        Materials_OLD.AluminiumFluoride = loadAluminiumFluoride();
        Materials_OLD.CalciumDisilicide = loadCalciumDisilicide();
        Materials_OLD.Calciumhydride = loadCalciumhydride();
        Materials_OLD.Dichlorosilane = loadDichlorosilane();
        Materials_OLD.Hexachlorodisilane = loadHexachlorodisilane();
        Materials_OLD.Silane = loadSilane();
        Materials_OLD.SiliconSG = loadSiliconSG();
        Materials_OLD.SiliconTetrachloride = loadSiliconTetrachloride();
        Materials_OLD.SiliconTetrafluoride = loadSiliconTetrafluoride();
        Materials_OLD.Trichlorosilane = loadTrichlorosilane();
    }

    private static Materials_OLD loadAluminiumFluoride() {
        return new MaterialBuilder().setName("Aluminiumfluoride")
            .setDefaultLocalName("Aluminium Fluoride")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .setMeltingPoint(1_533)
            .addElectrolyzerRecipe()
            // ALF3
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Fluorine, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadCalciumDisilicide() {
        return new MaterialBuilder().setName("CalciumDisilicide")
            .setDefaultLocalName("Calcium Disilicide")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00b4b4b4)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .setMeltingPoint(1_313)
            .addElectrolyzerRecipe()
            // CaSi2
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Silicon, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadCalciumhydride() {
        return new MaterialBuilder().setName("CalciumHydride")
            .setDefaultLocalName("Calcium Hydride")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00dcdcdc)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .setMeltingPoint(1_089)
            .addElectrolyzerRecipe()
            // CaH2
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadDichlorosilane() {
        return new MaterialBuilder().setName("Dichlorosilane")
            .setDefaultLocalName("Dichlorosilane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffffff)
            .addCell()
            .addGas()
            .setMeltingPoint(151)
            .addElectrolyzerRecipe()
            // SIH2CL2
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Chlorine, 2)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadHexachlorodisilane() {
        return new MaterialBuilder().setName("Hexachlorodisilane")
            .setDefaultLocalName("Hexachlorodisilane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(272)
            .addElectrolyzerRecipe()
            // SI2CL6
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Chlorine, 6)
            .constructMaterial();
    }

    private static Materials_OLD loadSilane() {
        return new MaterialBuilder().setName("Silane")
            .setDefaultLocalName("Silane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addGas()
            .setMeltingPoint(88)
            // SIH4
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadSiliconSG() {
        return new MaterialBuilder().setName("SiliconSolarGrade")
            .setDefaultLocalName("Silicon Solar Grade (Poly SI)")
            .setChemicalFormula("Si*")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00505064)
            .setMiningLevel(2)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .setMeltingPoint(2_273)
            .setBlastFurnaceTemp(2_273)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadSiliconTetrachloride() {
        return new MaterialBuilder().setName("SiliconTetrachloride")
            .setDefaultLocalName("Silicon Tetrachloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdcdc)
            .addCell()
            .addFluid()
            .setMeltingPoint(204)
            // SICL4
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Chlorine, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadSiliconTetrafluoride() {
        return new MaterialBuilder().setName("SiliconTetrafluoride")
            .setDefaultLocalName("Silicon Tetrafluoride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c8c8c8)
            .addCell()
            .addGas()
            .setMeltingPoint(178)
            // SIF4
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Fluorine, 4)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadTrichlorosilane() {
        return new MaterialBuilder().setName("Trichlorosilane")
            .setDefaultLocalName("Trichlorosilane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(139)
            // HSICL3
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Chlorine, 3)
            .constructMaterial();
    }

    private static void loadUnclassified06() {
        Materials_OLD.GalliumArsenide = loadGalliumArsenide();
        Materials_OLD.IndiumGalliumPhosphide = loadIndiumGalliumPhosphide();
        Materials_OLD.SolderingAlloy = loadSolderingAlloy();
        Materials_OLD.Spessartine = loadSpessartine();
        Materials_OLD.Sphalerite = loadSphalerite();
        Materials_OLD.StainlessSteel = loadStainlessSteel();
        Materials_OLD.Steel = loadSteel();
        Materials_OLD.Stibnite = loadStibnite();
        Materials_OLD.SulfuricAcid = loadSulfuricAcid();
        Materials_OLD.Tanzanite = loadTanzanite();
        Materials_OLD.Tetrahedrite = loadTetrahedrite();
        Materials_OLD.TinAlloy = loadTinAlloy();
        Materials_OLD.Topaz = loadTopaz();
        Materials_OLD.Tungstate = loadTungstate();
        Materials_OLD.Ultimet = loadUltimet();
        Materials_OLD.Uraninite = loadUraninite();
        Materials_OLD.Uvarovite = loadUvarovite();
        Materials_OLD.VanadiumGallium = loadVanadiumGallium();
        Materials_OLD.Wood = loadWood();
        Materials_OLD.WroughtIron = Materials_OLD.CastIron = loadCastIron();
        Materials_OLD.Wulfenite = loadWulfenite();
        Materials_OLD.YellowLimonite = loadYellowLimonite();
        Materials_OLD.YttriumBariumCuprate = loadYttriumBariumCuprate();
    }

    private static Materials_OLD loadGalliumArsenide() {
        return new MaterialBuilder().setName("GalliumArsenide")
            .setDefaultLocalName("Gallium Arsenide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00a0a0a0)
            .addDustItems()
            .addMetalItems()
            .setBlastFurnaceTemp(1_200)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Arsenic, 1)
            .addMaterial(Materials_OLD.Gallium, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadIndiumGalliumPhosphide() {
        return new MaterialBuilder().setName("IndiumGalliumPhosphide")
            .setDefaultLocalName("Indium Gallium Phosphide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00a08cbe)
            .addDustItems()
            .addMetalItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Indium, 1)
            .addMaterial(Materials_OLD.Gallium, 1)
            .addMaterial(Materials_OLD.Phosphorus, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadSolderingAlloy() {
        return new MaterialBuilder().setName("SolderingAlloy")
            .setDefaultLocalName("Soldering Alloy")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00dcdce6)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(400)
            .setBlastFurnaceTemp(400)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Tin, 9)
            .addMaterial(Materials_OLD.Antimony, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.SOLDERING_MATERIAL)
            .addSubTag(SubTag.SOLDERING_MATERIAL_GOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadSpessartine() {
        return new MaterialBuilder().setName("Spessartine")
            .setDefaultLocalName("Spessartine")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff6464)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Manganese, 3)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.GarnetRed)
            .addOreByproduct(() -> Materials_OLD.Manganese)
            .constructMaterial();
    }

    private static Materials_OLD loadSphalerite() {
        return new MaterialBuilder().setName("Sphalerite")
            .setDefaultLocalName("Sphalerite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addMaterial(Materials_OLD.Zinc, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addOreByproduct(() -> Materials_OLD.GarnetYellow)
            .addOreByproduct(() -> Materials_OLD.Cadmium)
            .addOreByproduct(() -> Materials_OLD.Gallium)
            .addOreByproduct(() -> Materials_OLD.Zinc)
            .setDirectSmelting(() -> Materials_OLD.Zinc)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadStainlessSteel() {
        return new MaterialBuilder().setName("StainlessSteel")
            .setDefaultLocalName("Stainless Steel")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c8c8dc)
            .setTool(480, 4, 7.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 3)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 6)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addMaterial(Materials_OLD.Manganese, 1)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadSteel() {
        return new MaterialBuilder().setName("Steel")
            .setDefaultLocalName("Steel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808080)
            .setTool(512, 3, 6.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_811)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .setDensity(51, 50)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 50)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadStibnite() {
        return new MaterialBuilder().setName("Stibnite")
            .setDefaultLocalName("Stibnite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00464646)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Antimony, 2)
            .addMaterial(Materials_OLD.Sulfur, 3)
            .addOreByproduct(() -> Materials_OLD.Antimony)
            .setDirectSmelting(() -> Materials_OLD.Antimony)
            .constructMaterial();
    }

    private static Materials_OLD loadSulfuricAcid() {
        return new MaterialBuilder().setName("SulfuricAcid")
            .setDefaultLocalName("Sulfuric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8000)
            .addCell()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadTanzanite() {
        return new MaterialBuilder().setName("Tanzanite")
            .setDefaultLocalName("Tanzanite")
            .setIconSet(TextureSet.SET_GEM_VERTICAL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x7f4000c8)
            .setTool(256, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Calcium, 2)
            .addMaterial(Materials_OLD.Aluminium, 3)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 13)
            .addOreByproduct(() -> Materials_OLD.Opal)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadTetrahedrite() {
        return new MaterialBuilder().setName("Tetrahedrite")
            .setDefaultLocalName("Tetrahedrite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c82000)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            // Cu3SbS3 + x(Fe,Zn)6Sb2S9
            .addMaterial(Materials_OLD.Copper, 3)
            .addMaterial(Materials_OLD.Antimony, 1)
            .addMaterial(Materials_OLD.Sulfur, 3)
            .addMaterial(Materials_OLD.Iron, 1)
            .addOreByproduct(() -> Materials_OLD.Antimony)
            .addOreByproduct(() -> Materials_OLD.Zinc)
            .setDirectSmelting(() -> Materials_OLD.Copper)
            .addSubTag(SubTag.DONT_ADD_DEFAULT_BBF_RECIPE)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .addSubTag(SubTag.WASHING_SODIUMPERSULFATE)
            .constructMaterial();
    }

    private static Materials_OLD loadTinAlloy() {
        return new MaterialBuilder().setName("TinAlloy")
            .setDefaultLocalName("Tin Alloy")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c8c8c8)
            .setTool(96, 2, 6.5f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Tin, 1)
            .addMaterial(Materials_OLD.Iron, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTopaz() {
        return new MaterialBuilder().setName("Topaz")
            .setDefaultLocalName("Topaz")
            .setIconSet(TextureSet.SET_GEM_HORIZONTAL)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x7fff8000)
            .setTool(256, 3, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Fluorine, 2)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .addOreByproduct(() -> Materials_OLD.BlueTopaz)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadTungstate() {
        return new MaterialBuilder().setName("Tungstate")
            .setDefaultLocalName("Tungstate")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00373223)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(2)
            .setMeltingPoint(2_500)
            .setBlastFurnaceTemp(2_500)
            .setBlastFurnaceRequired(true)
            .addMaterial(Materials_OLD.Tungsten, 1)
            .addMaterial(Materials_OLD.Lithium, 2)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addOreByproduct(() -> Materials_OLD.Manganese)
            .addOreByproduct(() -> Materials_OLD.Silver)
            .addOreByproduct(() -> Materials_OLD.Lithium)
            .constructMaterial();
    }

    private static Materials_OLD loadUltimet() {
        return new MaterialBuilder().setName("Ultimet")
            .setDefaultLocalName("Ultimet")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00b4b4e6)
            .setTool(2_048, 4, 9.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_700)
            .setBlastFurnaceTemp(2_700)
            .setBlastFurnaceRequired(true)
            .addElectrolyzerRecipe()
            // 54% Cobalt, 26% Chromium, 9% Nickel, 5% Molybdenum, 3% Iron, 2% Tungsten, 0.8% Manganese, 0.3% Silicon,
            // 0.08% Nitrogen and 0.06% Carbon
            .addMaterial(Materials_OLD.Cobalt, 5)
            .addMaterial(Materials_OLD.Chrome, 2)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Molybdenum, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadUraninite() {
        return new MaterialBuilder().setName("Uraninite")
            .setDefaultLocalName("Uraninite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00232323)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Uranium, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.Uranium)
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addOreByproduct(() -> Materials_OLD.Uranium235)
            .constructMaterial();
    }

    private static Materials_OLD loadUvarovite() {
        return new MaterialBuilder().setName("Uvarovite")
            .setDefaultLocalName("Uvarovite")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00b4ffb4)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Calcium, 3)
            .addMaterial(Materials_OLD.Chrome, 2)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.GarnetYellow)
            .addOreByproduct(() -> Materials_OLD.Chrome)
            .constructMaterial();
    }

    private static Materials_OLD loadVanadiumGallium() {
        return new MaterialBuilder().setName("VanadiumGallium")
            .setDefaultLocalName("Vanadium-Gallium")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0x0080808c)
            .addDustItems()
            .addMetalItems()
            .addGearItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Vanadium, 3)
            .addMaterial(Materials_OLD.Gallium, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadWood() {
        return new MaterialBuilder().setName("Wood")
            .setDefaultLocalName("Wood")
            .setChemicalFormula("")
            .setIconSet(TextureSet.SET_WOOD)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00643200)
            .setTool(16, 0, 2.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.WOOD)
            .removeOrePrefix(OrePrefixes.stick) // minecraft:stick
            .removeOrePrefix(OrePrefixes.ingot)
            .constructMaterial();
    }

    private static Materials_OLD loadCastIron() {
        return new MaterialBuilder().setName("CastIron")
            .setDefaultLocalName("Cast Iron")
            .setChemicalFormula("Fe*")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00c8b4b4)
            .setTool(384, 2, 6.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_811)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_TRIPLE)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadWulfenite() {
        return new MaterialBuilder().setName("Wulfenite")
            .setDefaultLocalName("Wulfenite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8000)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Lead, 1)
            .addMaterial(Materials_OLD.Molybdenum, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadYellowLimonite() {
        return new MaterialBuilder().setName("YellowLimonite")
            .setDefaultLocalName("Yellow Limonite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00c8c800)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            // FeO(OH) + a bit of Ni and Co
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .addOreByproduct(() -> Materials_OLD.Nickel)
            .addOreByproduct(() -> Materials_OLD.BrownLimonite)
            .addOreByproduct(() -> Materials_OLD.Cobalt)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadYttriumBariumCuprate() {
        return new MaterialBuilder().setName("YttriumBariumCuprate")
            .setDefaultLocalName("Yttrium Barium Cuprate")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00504046)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addMaterial(Materials_OLD.Yttrium, 1)
            .addMaterial(Materials_OLD.Barium, 2)
            .addMaterial(Materials_OLD.Copper, 3)
            .addMaterial(Materials_OLD.Oxygen, 7)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static void loadDegree2Compounds() {
        Materials_OLD.Air = loadAir();
        Materials_OLD.Aluminiumhydroxide = loadAluminiumHydroxide();
        Materials_OLD.Aluminiumoxide = loadAluminiumoxide();
        Materials_OLD.Alumite = loadAlumite();
        Materials_OLD.Alunite = loadAlunite();
        Materials_OLD.Amethyst = loadAmethyst();
        Materials_OLD.Apatite = loadApatite();
        Materials_OLD.Barite = loadBarite();
        Materials_OLD.Bastnasite = loadBastnasite();
        Materials_OLD.Bauxite = loadBauxite();
        Materials_OLD.Bentonite = loadBentonite();
        Materials_OLD.Biotite = loadBiotite();
        Materials_OLD.BismuthBronze = loadBismuthBronze();
        Materials_OLD.BlackBronze = loadBlackBronze();
        Materials_OLD.BlackSteel = loadBlackSteel();
        Materials_OLD.Blaze = loadBlaze();
        Materials_OLD.Borax = loadBorax();
        Materials_OLD.Chrysotile = loadChrysotile();
        Materials_OLD.Clay = loadClay();
        Materials_OLD.CobaltBrass = loadCobaltBrass();
        Materials_OLD.Concrete = loadConcrete();
        Materials_OLD.Cryolite = loadCryolite();
        Materials_OLD.DamascusSteel = loadDamascusSteel();
        Materials_OLD.DilutedSulfuricAcid = loadDilutedSulfuricAcid();
        Materials_OLD.Dolomite = loadDolomite();
        Materials_OLD.ElectricalSteel = loadElectricalSteel();
        Materials_OLD.EnderPearl = loadEnderPearl();
        Materials_OLD.EpoxidFiberReinforced = loadEpoxidFiberReinforced();
        Materials_OLD.Flint = loadFlint();
        Materials_OLD.FullersEarth = loadFullersEarth();
        Materials_OLD.GarnetRed = loadGarnetRed();
        Materials_OLD.GarnetYellow = loadGarnetYellow();
        Materials_OLD.Glass = loadGlass();
        Materials_OLD.Glauconite = loadGlauconite();
        Materials_OLD.GlauconiteSand = loadGlauconiteSand();
        Materials_OLD.GraniteBlack = loadGraniteBlack();
        Materials_OLD.GraniticMineralSand = loadGraniticMineralSand();
        Materials_OLD.Gypsum = loadGypsum();
        Materials_OLD.HydratedCoal = loadHydratedCoal();
        Materials_OLD.IronMagnetic = loadIronMagnetic();
        Materials_OLD.Jasper = loadJasper();
        Materials_OLD.Kaolinite = loadKaolinite();
        Materials_OLD.Knightmetal = loadKnightmetal();
        Materials_OLD.Kyanite = loadKyanite();
        Materials_OLD.Lapis = loadLapis();
        Materials_OLD.Lepidolite = loadLepidolite();
        Materials_OLD.Lignite = loadLignite();
        Materials_OLD.LiquidAir = loadLiquidAir();
        Materials_OLD.LiveRoot = loadLiveRoot();
        Materials_OLD.Malachite = loadMalachite();
        Materials_OLD.Manyullyn = loadManyullyn();
        Materials_OLD.Marble = loadMarble();
        Materials_OLD.Mica = loadMica();
        Materials_OLD.Mirabilite = loadMirabilite();
        Materials_OLD.Monazite = loadMonazite();
        Materials_OLD.NeodymiumMagnetic = loadNeodymiumMagnetic();
        Materials_OLD.Niter = loadNiter();
        Materials_OLD.NitroCoalFuel = loadNitroCoalFuel();
        Materials_OLD.NitroFuel = loadNitroFuel();
        Materials_OLD.Olivine = loadOlivine();
        Materials_OLD.Opal = loadOpal();
        Materials_OLD.Pentlandite = loadPentlandite();
        Materials_OLD.Perlite = loadPerlite();
        Materials_OLD.Pitchblende = loadPitchblende();
        Materials_OLD.Pollucite = loadPollucite();
        Materials_OLD.Pollution = loadPollution();
        Materials_OLD.PotassiumFeldspar = loadPotassiumFeldspar();
        Materials_OLD.QuartzSand = loadQuartzSand();
        Materials_OLD.RawStyreneButadieneRubber = loadRawStyreneButadieneRubber();
        Materials_OLD.Realgar = loadRealgar();
        Materials_OLD.RedMud = loadRedMud();
        Materials_OLD.Redrock = loadRedrock();
        Materials_OLD.Redstone = loadRedstone();
        Materials_OLD.RoseGold = loadRoseGold();
        Materials_OLD.SamariumMagnetic = loadSamariumMagnetic();
        Materials_OLD.Soapstone = loadSoapstone();
        Materials_OLD.SodiumAluminate = loadSodiumAluminate();
        Materials_OLD.SodiumCarbonate = loadSodiumCarbonate();
        Materials_OLD.Spodumene = loadSpodumene();
        Materials_OLD.SteelMagnetic = loadSteelMagnetic();
        Materials_OLD.Steeleaf = loadSteeleaf();
        Materials_OLD.SterlingSilver = loadSterlingSilver();
        Materials_OLD.Sugar = loadSugar();
        Materials_OLD.Talc = loadTalc();
        Materials_OLD.Tantalite = loadTantalite();
        Materials_OLD.Thaumium = loadThaumium();
        Materials_OLD.TPV = loadTPV();
        Materials_OLD.TricalciumPhosphate = loadTricalciumPhosphate();
        Materials_OLD.Trona = loadTrona();
        Materials_OLD.TungstenCarbide = loadTungstenCarbide();
        Materials_OLD.TungstenSteel = loadTungstenSteel();
        Materials_OLD.VanadiumMagnetite = loadVanadiumMagnetite();
        Materials_OLD.VanadiumSteel = loadVanadiumSteel();
        Materials_OLD.Vermiculite = loadVermiculite();
        Materials_OLD.Vinteum = loadVinteum();
        Materials_OLD.Vis = loadVis();
        Materials_OLD.VolcanicAsh = loadVolcanicAsh();
        Materials_OLD.Wollastonite = loadWollastonite();
        Materials_OLD.WoodSealed = loadWoodSealed();
        Materials_OLD.Zeolite = loadZeolite();
    }

    private static Materials_OLD loadAluminiumHydroxide() {
        return new MaterialBuilder().setName("AluminiumHydroxide")
            .setDefaultLocalName("Aluminium Hydroxide")
            .setChemicalFormula("Al(OH)₃")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ebebff)
            .setTool(64, 1, 1.0f)
            .addDustItems()
            .setBlastFurnaceTemp(1_200)
            .setMeltingPoint(1_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadAluminiumoxide() {
        return new MaterialBuilder().setName("Alumina")
            .setDefaultLocalName("Alumina")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ebffff)
            .setTool(64, 1, 1.0f)
            .addDustItems()
            .setBlastFurnaceTemp(2_054)
            .setMeltingPoint(2_054)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadAlumite() {
        return new MaterialBuilder().setName("Alumite")
            .setDefaultLocalName("Obzinite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ff69b4)
            .setTool(768, 2, 5.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Zinc, 5)
            .addMaterial(Materials_OLD.Steel, 2)
            .addMaterial(Materials_OLD.Obsidian, 2)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadAlunite() {
        return new MaterialBuilder().setName("Alunite")
            .setDefaultLocalName("Alunite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00e1b441)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // KAl3(SO4)2(OH)6
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Aluminium, 3)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 14)
            .constructMaterial();
    }

    private static Materials_OLD loadAmethyst() {
        return new MaterialBuilder().setName("Amethyst")
            .setDefaultLocalName("Amethyst")
            .setIconSet(TextureSet.SET_FLINT)
            .setColor(Dyes.dyePink)
            .setARGB(0x7fd232d2)
            .setTool(256, 3, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.SiliconDioxide, 4)
            .addMaterial(Materials_OLD.Iron, 1)
            .addOreByproduct(() -> Materials_OLD.Amethyst)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadApatite() {
        return new MaterialBuilder().setName("Apatite")
            .setDefaultLocalName("Apatite")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x00c8c8ff)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(4)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Calcium, 5)
            .addMaterial(Materials_OLD.Phosphate, 3)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addOreByproduct(() -> Materials_OLD.TricalciumPhosphate)
            .addOreByproduct(() -> Materials_OLD.Phosphate)
            .addOreByproduct(() -> Materials_OLD.Pyrochlore)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadBarite() {
        return new MaterialBuilder().setName("Barite")
            .setDefaultLocalName("Barite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00e6ebff)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Barium, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadBastnasite() {
        return new MaterialBuilder().setName("Bastnasite")
            .setDefaultLocalName("Bastnasite")
            .setIconSet(TextureSet.SET_FINE)
            .setARGB(0x00c86e2d)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // (Ce, La, Y)CO3F
            .addMaterial(Materials_OLD.Cerium, 1)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Fluorine, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.Neodymium)
            .addOreByproduct(() -> Materials_OLD.RareEarth)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)
            .constructMaterial();
    }

    private static Materials_OLD loadBauxite() {
        return new MaterialBuilder().setName("Bauxite")
            .setDefaultLocalName("Bauxite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00c86400)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Rutile, 2)
            .addMaterial(Materials_OLD.Aluminium, 16)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addMaterial(Materials_OLD.Oxygen, 11)
            .addOreByproduct(() -> Materials_OLD.Grossular)
            .addOreByproduct(() -> Materials_OLD.Rutile)
            .addOreByproduct(() -> Materials_OLD.Gallium)
            .constructMaterial();
    }

    private static Materials_OLD loadBentonite() {
        return new MaterialBuilder().setName("Bentonite")
            .setDefaultLocalName("Bentonite")
            .setIconSet(TextureSet.SET_ROUGH)
            .setARGB(0x00f5d7d2)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // (Na,Ca)0.33(Al,Mg)2(Si4O10)(OH)2 nH2O
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Magnesium, 6)
            .addMaterial(Materials_OLD.Silicon, 12)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Water, 5)
            .addMaterial(Materials_OLD.Oxygen, 36)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Calcium)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .constructMaterial();
    }

    private static Materials_OLD loadBiotite() {
        return new MaterialBuilder().setName("Biotite")
            .setDefaultLocalName("Biotite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00141e14)
            .addDustItems()
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Magnesium, 3)
            .addMaterial(Materials_OLD.Aluminium, 3)
            .addMaterial(Materials_OLD.Fluorine, 2)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 10)
            .constructMaterial();
    }

    private static Materials_OLD loadBismuthBronze() {
        return new MaterialBuilder().setName("BismuthBronze")
            .setDefaultLocalName("Bismuth Bronze")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x00647d7d)
            .setTool(256, 2, 8.0f)
            .setToolEnchantment(() -> Enchantment.baneOfArthropods, 5)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_100)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Bismuth, 1)
            .addMaterial(Materials_OLD.Zinc, 1)
            .addMaterial(Materials_OLD.Copper, 3)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadBlackBronze() {
        return new MaterialBuilder().setName("BlackBronze")
            .setDefaultLocalName("Black Bronze")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x0064327d)
            .setTool(256, 2, 12.0f)
            .setToolEnchantment(() -> Enchantment.smite, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(2_000)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Gold, 1)
            .addMaterial(Materials_OLD.Silver, 1)
            .addMaterial(Materials_OLD.Copper, 3)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadBlackSteel() {
        return new MaterialBuilder().setName("BlackSteel")
            .setDefaultLocalName("Black Steel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00646464)
            .setTool(768, 3, 6.5f)
            .setToolEnchantment(() -> Enchantment.sharpness, 4)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.BlackBronze, 1)
            .addMaterial(Materials_OLD.Steel, 3)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadBlaze() {
        return new MaterialBuilder().setName("Blaze")
            .setDefaultLocalName("Blaze")
            .setIconSet(TextureSet.SET_BLAZE)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffc800)
            .addDustItems()
            .setMeltingPoint(6_400)
            .setDensity(3, 2)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.AshDark, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Magic, 1)
            .addSubTag(SubTag.BURNING)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.UNBURNABLE)
            .addOrePrefix(OrePrefixes.stickLong)
            .removeOrePrefix(OrePrefixes.dust) // minecraft:blaze_powder
            .removeOrePrefix(OrePrefixes.stick) // minecraft:blaze_rod
            .constructMaterial();
    }

    private static Materials_OLD loadBorax() {
        return new MaterialBuilder().setName("Borax")
            .setDefaultLocalName("Borax")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Boron, 4)
            .addMaterial(Materials_OLD.Oxygen, 7)
            .addMaterial(Materials_OLD.Water, 10)
            .constructMaterial();
    }

    private static Materials_OLD loadChrysotile() {
        return new MaterialBuilder().setName("Chrysotile")
            .setDefaultLocalName("Chrysotile")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x006e8c6e)
            .setTool(10_240, 3, 32.0f)
            .setTurbine(280.0f, 280.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_400)
            .setBlastFurnaceTemp(9_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Asbestos, 1)
            .addOreByproduct(() -> Materials_OLD.Asbestos)
            .addOreByproduct(() -> Materials_OLD.SiliconDioxide)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadClay() {
        return new MaterialBuilder().setName("Clay")
            .setDefaultLocalName("Clay")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00c8c8dc)
            .addDustItems()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Lithium, 1)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Oxygen, 7)
            .addMaterial(Materials_OLD.Water, 2)
            .addOreByproduct(() -> Materials_OLD.Clay)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltBrass() {
        return new MaterialBuilder().setName("CobaltBrass")
            .setDefaultLocalName("Cobalt Brass")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00b4b4a0)
            .setTool(256, 2, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Brass, 7)
            .addMaterial(Materials_OLD.Tin, 1)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadConcrete() {
        return new MaterialBuilder().setName("Concrete")
            .setDefaultLocalName("Concrete")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00646464)
            .addDustItems()
            .setMeltingPoint(300)
            .addMaterial(Materials_OLD.Stone, 1)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.plate)
            .constructMaterial();
    }

    private static Materials_OLD loadCryolite() {
        return new MaterialBuilder().setName("Cryolite")
            .setDefaultLocalName("Cryolite")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00bfefff)
            .setTool(64, 1, 1.0f)
            .addOreItems()
            .setOreMultiplier(4)
            .setMeltingPoint(1_012)
            .setBlastFurnaceTemp(1_012)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addMaterial(Materials_OLD.Sodium, 3)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Fluorine, 6)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Sodium)
            .constructMaterial();
    }

    private static Materials_OLD loadDamascusSteel() {
        return new MaterialBuilder().setName("DamascusSteel")
            .setDefaultLocalName("Damascus Steel")
            .setChemicalFormula("(Fe₅" + CustomGlyphs.SUBSCRIPT0 + "C)₉Mn₄Cr₄CSiV")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x006e6e6e)
            .setTool(1_280, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 5)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_000)
            .setBlastFurnaceTemp(1_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Steel, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadDilutedSulfuricAcid() {
        return new MaterialBuilder().setName("DilutedSulfuricAcid")
            .setDefaultLocalName("Diluted Sulfuric Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00c07820)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.SulfuricAcid, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadDolomite() {
        return new MaterialBuilder().setName("Dolomite")
            .setDefaultLocalName("Dolomite")
            .setIconSet(TextureSet.SET_FLINT)
            .setARGB(0x00e1cdcd)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // CaMg(CO3)2
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .constructMaterial();
    }

    private static Materials_OLD loadElectricalSteel() {
        return new MaterialBuilder().setName("ElectricalSteel")
            .setDefaultLocalName("Electrical Steel")
            .setIconSet(TextureSet.SET_DARKSTEEL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00d8d8d8)
            .setTool(512, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_811)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Steel, 1)
            .addMaterial(Materials_OLD.Coal, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadEnderPearl() {
        return new MaterialBuilder().setName("EnderPearl")
            .setDefaultLocalName("Enderpearl")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x006cdcc8)
            .setTool(16, 1, 1.0f)
            .setToolEnchantment(() -> Enchantment.silkTouch, 1)
            .addDustItems()
            .addGemItems()
            .setDensity(16, 10)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Beryllium, 1)
            .addMaterial(Materials_OLD.Potassium, 4)
            .addMaterial(Materials_OLD.Nitrogen, 5)
            .addMaterial(Materials_OLD.Magic, 6)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.PEARL)
            .removeOrePrefix(OrePrefixes.gem) // minecraft:ender_pearl
            .constructMaterial();
    }

    private static Materials_OLD loadEpoxidFiberReinforced() {
        return new MaterialBuilder().setName("EpoxidFiberReinforced")
            .setDefaultLocalName("Fiber-Reinforced Epoxy Resin")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00a07010)
            .setTool(64, 1, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .removeOrePrefix(OrePrefixes.sheetmetal)
            .setMeltingPoint(400)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Epoxid, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadFlint() {
        return new MaterialBuilder().setName("Flint")
            .setDefaultLocalName("Flint")
            .setIconSet(TextureSet.SET_FLINT)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00002040)
            .setTool(128, 1, 2.5f)
            .setToolEnchantment(() -> Enchantment.fireAspect, 1)
            .addDustItems()
            .addToolHeadItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.SiliconDioxide, 1)
            .addOreByproduct(() -> Materials_OLD.Obsidian)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .removeOrePrefix(OrePrefixes.gem) // minecraft:flint
            .constructMaterial();
    }

    private static Materials_OLD loadFullersEarth() {
        return new MaterialBuilder().setName("FullersEarth")
            .setDefaultLocalName("Fullers Earth")
            .setIconSet(TextureSet.SET_FINE)
            .setARGB(0x00a0a078)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // (Mg,Al)2Si4O10(OH) 4(H2O)
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Silicon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Water, 4)
            .addMaterial(Materials_OLD.Oxygen, 11)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.SiliconDioxide)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .constructMaterial();
    }

    private static Materials_OLD loadGarnetRed() {
        return new MaterialBuilder().setName("GarnetRed")
            .setDefaultLocalName("Red Garnet")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeRed)
            .setARGB(0x7fc85050)
            .setTool(128, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Pyrope, 3)
            .addMaterial(Materials_OLD.Almandine, 5)
            .addMaterial(Materials_OLD.Spessartine, 8)
            .addOreByproduct(() -> Materials_OLD.Spessartine)
            .addOreByproduct(() -> Materials_OLD.Pyrope)
            .addOreByproduct(() -> Materials_OLD.Almandine)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGarnetYellow() {
        return new MaterialBuilder().setName("GarnetYellow")
            .setDefaultLocalName("Yellow Garnet")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x7fc8c850)
            .setTool(128, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Andradite, 5)
            .addMaterial(Materials_OLD.Grossular, 8)
            .addMaterial(Materials_OLD.Uvarovite, 3)
            .addOreByproduct(() -> Materials_OLD.Andradite)
            .addOreByproduct(() -> Materials_OLD.Grossular)
            .addOreByproduct(() -> Materials_OLD.Uvarovite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGlass() {
        return new MaterialBuilder().setName("Glass")
            .setDefaultLocalName("Glass")
            .setIconSet(TextureSet.SET_GLASS)
            .setColor(Dyes.dyeWhite)
            .setARGB(0xdcfafafa)
            .setTool(4, 0, 1.0f)
            .addDustItems()
            .addGemItems()
            .setMeltingPoint(1_500)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.SiliconDioxide, 1)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_RECYCLING)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadGlauconite() {
        return new MaterialBuilder().setName("Glauconite")
            .setDefaultLocalName("Glauconite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x0082b43c)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Magnesium, 2)
            .addMaterial(Materials_OLD.Aluminium, 4)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.Sodium)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadGlauconiteSand() {
        return new MaterialBuilder().setName("GlauconiteSand")
            .setDefaultLocalName("Glauconite Sand")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x0082b43c)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Magnesium, 2)
            .addMaterial(Materials_OLD.Aluminium, 4)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.Sodium)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadGraniteBlack() {
        return new MaterialBuilder().setName("GraniteBlack")
            .setDefaultLocalName("Black Granite")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .setTool(64, 3, 4.0f)
            .addDustItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.SiliconDioxide, 4)
            .addMaterial(Materials_OLD.Biotite, 1)
            .addOreByproduct(() -> Materials_OLD.Biotite)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .addOrePrefix(OrePrefixes.plate)
            .constructMaterial();
    }

    private static Materials_OLD loadGraniticMineralSand() {
        return new MaterialBuilder().setName("GraniticMineralSand")
            .setDefaultLocalName("Granitic Mineral Sand")
            .setIconSet(TextureSet.SET_SAND)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00283c3c)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Magnetite, 1)
            .addMaterial(Materials_OLD.GraniteBlack, 1)
            .addOreByproduct(() -> Materials_OLD.GraniteBlack)
            .addOreByproduct(() -> Materials_OLD.Magnetite)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadGypsum() {
        return new MaterialBuilder().setName("Gypsum")
            .setDefaultLocalName("Gypsum")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00e6e6fa)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // CaSO4 2H2O
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .addMaterial(Materials_OLD.Water, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadHydratedCoal() {
        return new MaterialBuilder().setName("HydratedCoal")
            .setDefaultLocalName("Hydrated Coal")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00464664)
            .addDustItems()
            .setDensity(9, 8)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Coal, 8)
            .addMaterial(Materials_OLD.Water, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadIronMagnetic() {
        return new MaterialBuilder().setName("IronMagnetic")
            .setDefaultLocalName("Magnetic Iron")
            .setChemicalFormula("Fe" + CustomGlyphs.MAGNET)
            .setIconSet(TextureSet.SET_MAGNETIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00c8c8c8)
            .setTool(256, 2, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .setSmeltingInto(() -> Materials_OLD.Iron)
            .setMaceratingInto(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .constructMaterial();
    }

    private static Materials_OLD loadJasper() {
        return new MaterialBuilder().setName("Jasper")
            .setDefaultLocalName("Jasper")
            .setIconSet(TextureSet.SET_EMERALD)
            .setColor(Dyes.dyeRed)
            .setARGB(0x64c85050)
            .setMiningLevel(2)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.SiliconDioxide, 1)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadKaolinite() {
        return new MaterialBuilder().setName("Kaolinite")
            .setDefaultLocalName("Kaolinite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00f5ebeb)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // Al2Si2O5(OH)4
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 9)
            .constructMaterial();
    }

    private static Materials_OLD loadKnightmetal() {
        return new MaterialBuilder().setName("Knightmetal")
            .setDefaultLocalName("Knightmetal")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00d2f0c8)
            .setTool(1_024, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 3)
            .setArmorEnchantment(() -> Enchantment.protection, 1)
            .setFuel(MaterialBuilder.FuelType.Magic, 24)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Steel, 2)
            .addMaterial(Materials_OLD.Magic, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadKyanite() {
        return new MaterialBuilder().setName("Kyanite")
            .setDefaultLocalName("Kyanite")
            .setIconSet(TextureSet.SET_FLINT)
            .setARGB(0x006e6efa)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // Al2SiO5
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Oxygen, 5)
            .constructMaterial();
    }

    private static Materials_OLD loadLapis() {
        return new MaterialBuilder().setName("Lapis")
            .setDefaultLocalName("Lapis")
            .setIconSet(TextureSet.SET_LAPIS)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x004646dc)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(6)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Lazurite, 12)
            .addMaterial(Materials_OLD.Sodalite, 2)
            .addMaterial(Materials_OLD.Pyrite, 1)
            .addMaterial(Materials_OLD.Calcite, 1)
            .addOreByproduct(() -> Materials_OLD.Lazurite)
            .addOreByproduct(() -> Materials_OLD.Sodalite)
            .addOreByproduct(() -> Materials_OLD.Pyrite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.block) // minecraft:lapis_block
            .removeOrePrefix(OrePrefixes.gem) // minecraft:dye:4
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadLepidolite() {
        return new MaterialBuilder().setName("Lepidolite")
            .setDefaultLocalName("Lepidolite")
            .setIconSet(TextureSet.SET_FINE)
            .setARGB(0x00f0328c)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // K(Li,Al,Rb)3(Al,Si)4O10(F,OH)2
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Lithium, 3)
            .addMaterial(Materials_OLD.Aluminium, 4)
            .addMaterial(Materials_OLD.Fluorine, 2)
            .addMaterial(Materials_OLD.Oxygen, 10)
            .addOreByproduct(() -> Materials_OLD.Lithium)
            .addOreByproduct(() -> Materials_OLD.Caesium)
            .constructMaterial();
    }

    private static Materials_OLD loadLignite() {
        return new MaterialBuilder().setName("Lignite")
            .setDefaultLocalName("Lignite Coal")
            .setIconSet(TextureSet.SET_LIGNITE)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00644646)
            .setMiningLevel(0)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Water, 1)
            .addOreByproduct(() -> Materials_OLD.Coal)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadLiquidAir() {
        return new MaterialBuilder().setName("LiquidAir")
            .setDefaultLocalName("Liquid Air")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0xf0a9d0f5)
            .addCell()
            .setMeltingPoint(4)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Nitrogen, 40)
            .addMaterial(Materials_OLD.Oxygen, 11)
            .addMaterial(Materials_OLD.Argon, 1)
            .addMaterial(Materials_OLD.NobleGases, 1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadLiveRoot() {
        return new MaterialBuilder().setName("LiveRoot")
            .setDefaultLocalName("Liveroot")
            .setChemicalFormula("(COH₃)Ma")
            .setIconSet(TextureSet.SET_WOOD)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00dcc800)
            .setFuel(MaterialBuilder.FuelType.Magic, 16)
            .addDustItems()
            .setDensity(4, 3)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Wood, 3)
            .addMaterial(Materials_OLD.Magic, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.WOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadMalachite() {
        return new MaterialBuilder().setName("Malachite")
            .setDefaultLocalName("Malachite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00055f05)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // Cu2CO3(OH)2
            .addMaterial(Materials_OLD.Copper, 2)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 5)
            .addOreByproduct(() -> Materials_OLD.Copper)
            .addOreByproduct(() -> Materials_OLD.BrownLimonite)
            .addOreByproduct(() -> Materials_OLD.Calcite)
            .setDirectSmelting(() -> Materials_OLD.Copper)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadManyullyn() {
        return new MaterialBuilder().setName("Manyullyn")
            .setDefaultLocalName("Manyullyn")
            .setChemicalFormula("AiCo")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyePurple)
            .setARGB(0x009a4cb9)
            .setTool(2_048, 5, 25.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_600)
            .setBlastFurnaceTemp(3_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Ardite, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMarble() {
        return new MaterialBuilder().setName("Marble")
            .setDefaultLocalName("Marble")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c8c8c8)
            .setTool(16, 1, 1.0f)
            .addDustItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Calcite, 7)
            .addOreByproduct(() -> Materials_OLD.Calcite)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .addOrePrefix(OrePrefixes.plate)
            .constructMaterial();
    }

    private static Materials_OLD loadMica() {
        return new MaterialBuilder().setName("Mica")
            .setDefaultLocalName("Mica")
            .setIconSet(TextureSet.SET_FINE)
            .setARGB(0x00c3c3cd)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            // KAl2(AlSi3O10)(F,OH)2
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Aluminium, 3)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Fluorine, 2)
            .addMaterial(Materials_OLD.Oxygen, 10)
            .constructMaterial();
    }

    private static Materials_OLD loadMirabilite() {
        return new MaterialBuilder().setName("Mirabilite")
            .setDefaultLocalName("Mirabilite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00f0fad2)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // Na2SO4 10H2O
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Water, 10)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadMonazite() {
        return new MaterialBuilder().setName("Monazite")
            .setDefaultLocalName("Monazite")
            .setIconSet(TextureSet.SET_DIAMOND)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00324632)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(8)
            .addElectrolyzerRecipe()
            // Wikipedia: (Ce, La, Nd, Th, Sm, Gd)PO4 Monazite also smelt-extract to Helium, it is brown like the rare
            // earth Item Monazite sand deposits are inevitably of the monazite-(Ce) composition. Typically, the
            // lanthanides in such monazites contain about 45.8% cerium, about 24% lanthanum, about 17% neodymium, about
            // 5% praseodymium, and minor quantities of samarium, gadolinium, and yttrium. Europium concentrations tend
            // to be low, about 0.05% Thorium content of monazite is variable.
            .addMaterial(Materials_OLD.RareEarth, 1)
            .addMaterial(Materials_OLD.Phosphate, 1)
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addOreByproduct(() -> Materials_OLD.Neodymium)
            .addOreByproduct(() -> Materials_OLD.RareEarth)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadNeodymiumMagnetic() {
        return new MaterialBuilder().setName("NeodymiumMagnetic")
            .setDefaultLocalName("Magnetic Neodymium")
            .setChemicalFormula("Nd" + CustomGlyphs.MAGNET)
            .setIconSet(TextureSet.SET_MAGNETIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00646464)
            .setTool(512, 2, 7.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_297)
            .setBlastFurnaceTemp(1_297)
            .setBlastFurnaceRequired(true)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Neodymium, 1)
            .setSmeltingInto(() -> Materials_OLD.Neodymium)
            .setMaceratingInto(() -> Materials_OLD.Neodymium)
            .setArcSmeltingInto(() -> Materials_OLD.Neodymium)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .constructMaterial();
    }

    private static Materials_OLD loadNiter() {
        return new MaterialBuilder().setName("Niter")
            .setDefaultLocalName("Niter")
            .setIconSet(TextureSet.SET_FLINT)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ffc8c8)
            .setMiningLevel(1)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Saltpeter, 1)
            .addOreByproduct(() -> Materials_OLD.Saltpeter)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadNitroCoalFuel() {
        return new MaterialBuilder().setName("NitroCoalFuel")
            .setDefaultLocalName("Nitro-Coalfuel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00324632)
            .setFuel(MaterialBuilder.FuelType.Diesel, 48)
            .addCell()
            .addMaterial(Materials_OLD.Glyceryl, 1)
            .addMaterial(Materials_OLD.CoalFuel, 4)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static Materials_OLD loadNitroFuel() {
        return new MaterialBuilder().setName("NitroFuel")
            .setDefaultLocalName("Cetane-Boosted Diesel")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00c8ff00)
            .setFuel(MaterialBuilder.FuelType.Diesel, 1_000)
            .addCell()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_SMASHING)
            .constructMaterial();
    }

    private static Materials_OLD loadOlivine() {
        return new MaterialBuilder().setName("Olivine")
            .setDefaultLocalName("Olivine")
            .setIconSet(TextureSet.SET_RUBY)
            .setColor(Dyes.dyeLime)
            .setARGB(0x7f96ff96)
            .setTool(256, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Magnesium, 2)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.SiliconDioxide, 2)
            .addOreByproduct(() -> Materials_OLD.Pyrope)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadOpal() {
        return new MaterialBuilder().setName("Opal")
            .setDefaultLocalName("Opal")
            .setIconSet(TextureSet.SET_OPAL)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000000ff)
            .setTool(256, 2, 7.0f)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.SiliconDioxide, 1)
            .addOreByproduct(() -> Materials_OLD.Tanzanite)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPentlandite() {
        return new MaterialBuilder().setName("Pentlandite")
            .setDefaultLocalName("Pentlandite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00a59605)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // (Fe,Ni)9S8
            .addMaterial(Materials_OLD.Nickel, 9)
            .addMaterial(Materials_OLD.Sulfur, 8)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Sulfur)
            .addOreByproduct(() -> Materials_OLD.Cobalt)
            .setDirectSmelting(() -> Materials_OLD.Nickel)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadPerlite() {
        return new MaterialBuilder().setName("Perlite")
            .setDefaultLocalName("Perlite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x001e141e)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Obsidian, 2)
            .addMaterial(Materials_OLD.Water, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPitchblende() {
        return new MaterialBuilder().setName("Pitchblende")
            .setDefaultLocalName("Pitchblende")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00c8d200)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Uraninite, 3)
            .addMaterial(Materials_OLD.Thorium, 1)
            .addMaterial(Materials_OLD.Lead, 1)
            .addOreByproduct(() -> Materials_OLD.Thorium)
            .addOreByproduct(() -> Materials_OLD.Uranium)
            .addOreByproduct(() -> Materials_OLD.Lead)
            .constructMaterial();
    }

    private static Materials_OLD loadPollucite() {
        return new MaterialBuilder().setName("Pollucite")
            .setDefaultLocalName("Pollucite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00f0d2d2)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // (Cs,Na)2Al2Si4O12 2H2O (also a source of Rb)
            .addMaterial(Materials_OLD.Caesium, 2)
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.Silicon, 4)
            .addMaterial(Materials_OLD.Water, 2)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.Caesium)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Rubidium)
            .constructMaterial();
    }

    private static Materials_OLD loadPollution() {
        return new MaterialBuilder().setName("Pollution")
            .setDefaultLocalName("Pollution")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00d0b361)
            .constructMaterial();
    }

    private static Materials_OLD loadPotassiumFeldspar() {
        return new MaterialBuilder().setName("PotassiumFeldspar")
            .setDefaultLocalName("Potassium Feldspar")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyePink)
            .setARGB(0x00782828)
            .addDustItems()
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 8)
            .constructMaterial();
    }

    private static Materials_OLD loadQuartzSand() {
        return new MaterialBuilder().setName("QuartzSand")
            .setDefaultLocalName("Quartz Sand")
            .setChemicalFormula("(SiO₂)" + CustomGlyphs.SUBSCRIPT_QUESTION_MARK + "??")
            .setIconSet(TextureSet.SET_SAND)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c2b280)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addMaterial(Materials_OLD.CertusQuartz, 1)
            .addMaterial(Materials_OLD.Quartzite, 1)
            .addOreByproduct(() -> Materials_OLD.CertusQuartz)
            .addOreByproduct(() -> Materials_OLD.Quartzite)
            .addOreByproduct(() -> Materials_OLD.Barite)
            .constructMaterial();
    }

    private static Materials_OLD loadRawStyreneButadieneRubber() {
        return new MaterialBuilder().setName("RawStyreneButadieneRubber")
            .setDefaultLocalName("Raw Styrene-Butadiene Rubber")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0x0054403d)
            .addDustItems()
            .addMaterial(Materials_OLD.Styrene, 1)
            .addMaterial(Materials_OLD.Butadiene, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadRealgar() {
        return new MaterialBuilder().setName("Realgar")
            .setDefaultLocalName("Realgar")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x008c6464)
            .setTool(32, 1, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Arsenic, 4)
            .addMaterial(Materials_OLD.Sulfur, 4)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadRedMud() {
        return new MaterialBuilder().setName("RedMud")
            .setDefaultLocalName("Red Mud")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x008c1616)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadRedrock() {
        return new MaterialBuilder().setName("Redrock")
            .setDefaultLocalName("Redrock")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff5032)
            .addDustItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Calcite, 2)
            .addMaterial(Materials_OLD.Flint, 1)
            .addMaterial(Materials_OLD.Clay, 1)
            .addOreByproduct(() -> Materials_OLD.Clay)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .constructMaterial();
    }

    private static Materials_OLD loadRedstone() {
        return new MaterialBuilder().setName("Redstone")
            .setDefaultLocalName("Redstone")
            .setIconSet(TextureSet.SET_REDSTONE)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c80000)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(5)
            .setMeltingPoint(500)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Pyrite, 5)
            .addMaterial(Materials_OLD.Ruby, 1)
            .addMaterial(Materials_OLD.Mercury, 3)
            .addOreByproduct(() -> Materials_OLD.Cinnabar)
            .addOreByproduct(() -> Materials_OLD.RareEarth)
            .addOreByproduct(() -> Materials_OLD.Glowstone)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.PULVERIZING_CINNABAR)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.STONE)
            .addSubTag(SubTag.UNBURNABLE)
            .addOrePrefix(OrePrefixes.plate)
            .removeOrePrefix(OrePrefixes.block) // minecraft:redstone_block
            .removeOrePrefix(OrePrefixes.dust) // minecraft:redstone
            .addPlasma()
            .constructMaterial();
    }

    private static Materials_OLD loadRoseGold() {
        return new MaterialBuilder().setName("RoseGold")
            .setDefaultLocalName("Rose Gold")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyePink)
            .setARGB(0x00dea193)
            .setTool(128, 2, 14.0f)
            .setToolEnchantment(() -> Enchantment.smite, 4)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_600)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Gold, 4)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSamariumMagnetic() {
        return new MaterialBuilder().setName("SamariumMagnetic")
            .setDefaultLocalName("Magnetic Samarium")
            .setChemicalFormula("Sm" + CustomGlyphs.MAGNET)
            .setIconSet(TextureSet.SET_MAGNETIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffffcc)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_345)
            .setBlastFurnaceTemp(1_345)
            .setBlastFurnaceRequired(true)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Samarium, 1)
            .setSmeltingInto(() -> Materials_OLD.Samarium)
            .setMaceratingInto(() -> Materials_OLD.Samarium)
            .setArcSmeltingInto(() -> Materials_OLD.Samarium)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .constructMaterial();
    }

    private static Materials_OLD loadSoapstone() {
        return new MaterialBuilder().setName("Soapstone")
            .setDefaultLocalName("Soapstone")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x005f915f)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // H2Mg3(SiO3)4
            .addMaterial(Materials_OLD.Magnesium, 3)
            .addMaterial(Materials_OLD.Silicon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumAluminate() {
        return new MaterialBuilder().setName("SodiumAluminate")
            .setDefaultLocalName("Sodium Aluminate")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffebff)
            .setTool(64, 1, 1.0f)
            .addDustItems()
            .setBlastFurnaceTemp(1_800)
            .setMeltingPoint(1_800)
            .setBlastFurnaceRequired(false)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumCarbonate() {
        return new MaterialBuilder().setName("SodiumCarbonate")
            .setDefaultLocalName("Sodium Carbonate")
            .setIconSet(TextureSet.SET_QUARTZ)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ffffeb)
            .setTool(64, 1, 1.0f)
            .addDustItems()
            .setBlastFurnaceTemp(851)
            .setMeltingPoint(851)
            .setBlastFurnaceRequired(false)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Sodium, 2)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadSpodumene() {
        return new MaterialBuilder().setName("Spodumene")
            .setDefaultLocalName("Spodumene")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00beaaaa)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // LiAl(SiO3)2
            .addMaterial(Materials_OLD.Lithium, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Silicon, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Lithium)
            .constructMaterial();
    }

    private static Materials_OLD loadSteelMagnetic() {
        return new MaterialBuilder().setName("SteelMagnetic")
            .setDefaultLocalName("Magnetic Steel")
            .setChemicalFormula("Fe₅" + CustomGlyphs.SUBSCRIPT0 + "C" + CustomGlyphs.MAGNET)
            .setIconSet(TextureSet.SET_MAGNETIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00808080)
            .setTool(512, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_000)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Steel, 1)
            .setSmeltingInto(() -> Materials_OLD.Steel)
            .setMaceratingInto(() -> Materials_OLD.Steel)
            .setArcSmeltingInto(() -> Materials_OLD.Steel)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .constructMaterial();
    }

    private static Materials_OLD loadSteeleaf() {
        return new MaterialBuilder().setName("Steeleaf")
            .setDefaultLocalName("Steeleaf")
            .setIconSet(TextureSet.SET_LEAF)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00327f32)
            .setTool(768, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.fortune, 2)
            .setArmorEnchantment(() -> Enchantment.protection, 2)
            .setFuel(MaterialBuilder.FuelType.Magic, 24)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Steel, 1)
            .addMaterial(Materials_OLD.Magic, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.WOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadSterlingSilver() {
        return new MaterialBuilder().setName("SterlingSilver")
            .setDefaultLocalName("Sterling Silver")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fadce1)
            .setTool(128, 2, 13.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Silver, 4)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSugar() {
        return new MaterialBuilder().setName("Sugar")
            .setDefaultLocalName("Sugar")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .setMeltingPoint(462)
            .addDustItems()
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Water, 5)
            .addMaterial(Materials_OLD.Oxygen, 25)
            .addSubTag(SubTag.FOOD)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .removeOrePrefix(OrePrefixes.dust) // minecraft:sugar
            .constructMaterial();
    }

    private static Materials_OLD loadTalc() {
        return new MaterialBuilder().setName("Talc")
            .setDefaultLocalName("Talc")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x005ab45a)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // H2Mg3(SiO3)4
            .addMaterial(Materials_OLD.Magnesium, 3)
            .addMaterial(Materials_OLD.Silicon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .constructMaterial();
    }

    private static Materials_OLD loadTantalite() {
        return new MaterialBuilder().setName("Tantalite")
            .setDefaultLocalName("Tantalite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00915028)
            .setMiningLevel(3)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // (Fe, Mn)Ta2O6 (also source of Nb)
            .addMaterial(Materials_OLD.Manganese, 1)
            .addMaterial(Materials_OLD.Tantalum, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .addOreByproduct(() -> Materials_OLD.Manganese)
            .addOreByproduct(() -> Materials_OLD.Niobium)
            .addOreByproduct(() -> Materials_OLD.Tantalum)
            .constructMaterial();
    }

    private static Materials_OLD loadThaumium() {
        return new MaterialBuilder().setName("Thaumium")
            .setDefaultLocalName("Thaumium")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePurple)
            .setARGB(0x009664c8)
            .setTool(256, 3, 12.0f)
            .setToolEnchantment(() -> Enchantment.fortune, 2)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setDensity(2, 1)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Magic, 1)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadTPV() {
        return new MaterialBuilder().setName("TPVAlloy")
            .setDefaultLocalName("TPV-Alloy")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00faaafa)
            .setTool(4_000, 5, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_000)
            .setBlastFurnaceTemp(3_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Titanium, 3)
            .addMaterial(Materials_OLD.Platinum, 3)
            .addMaterial(Materials_OLD.Vanadium, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTricalciumPhosphate() {
        return new MaterialBuilder().setName("TricalciumPhosphate")
            .setDefaultLocalName("Tricalcium Phosphate")
            .setIconSet(TextureSet.SET_FLINT)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff00)
            .setMiningLevel(2)
            .addDustItems()
            .addGemItems()
            .addOreItems()
            .setOreMultiplier(3)
            .addCell()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Calcium, 3)
            .addMaterial(Materials_OLD.Phosphate, 2)
            .addOreByproduct(() -> Materials_OLD.Apatite)
            .addOreByproduct(() -> Materials_OLD.Phosphate)
            .addOreByproduct(() -> Materials_OLD.Pyrochlore)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.EXPLOSIVE)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadTrona() {
        return new MaterialBuilder().setName("Trona")
            .setDefaultLocalName("Trona")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x0087875f)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // Na3(CO3)(HCO3) 2H2O
            .addMaterial(Materials_OLD.Sodium, 3)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .addMaterial(Materials_OLD.Water, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .constructMaterial();
    }

    private static Materials_OLD loadTungstenCarbide() {
        return new MaterialBuilder().setName("TungstenCarbide")
            .setDefaultLocalName("Tungstencarbide")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00330066)
            .setTool(1_280, 4, 14.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 5)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_460)
            .setBlastFurnaceTemp(2_460)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Tungsten, 1)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadTungstenSteel() {
        return new MaterialBuilder().setName("TungstenSteel")
            .setDefaultLocalName("Tungstensteel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x006464a0)
            .setTool(2_560, 4, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(4_000)
            .setBlastFurnaceTemp(4_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Steel, 1)
            .addMaterial(Materials_OLD.Tungsten, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadVanadiumMagnetite() {
        return new MaterialBuilder().setName("VanadiumMagnetite")
            .setDefaultLocalName("Vanadium Magnetite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x0023233c)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            // Mixture of Fe3O4 and V2O5
            .addMaterial(Materials_OLD.Magnetite, 1)
            .addMaterial(Materials_OLD.Vanadium, 1)
            .addOreByproduct(() -> Materials_OLD.Magnetite)
            .addOreByproduct(() -> Materials_OLD.Vanadium)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD)
            .constructMaterial();
    }

    private static Materials_OLD loadVanadiumSteel() {
        return new MaterialBuilder().setName("VanadiumSteel")
            .setDefaultLocalName("Vanadiumsteel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c0c0c0)
            .setTool(1_920, 3, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_453)
            .setBlastFurnaceTemp(1_453)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Vanadium, 1)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addMaterial(Materials_OLD.Steel, 7)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadVermiculite() {
        return new MaterialBuilder().setName("Vermiculite")
            .setDefaultLocalName("Vermiculite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00c8b40f)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // (Mg+2, Fe+2, Fe+3)3 [(AlSi)4O10] (OH)2 4H2O)
            .addMaterial(Materials_OLD.Iron, 3)
            .addMaterial(Materials_OLD.Aluminium, 4)
            .addMaterial(Materials_OLD.Silicon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Water, 4)
            .addMaterial(Materials_OLD.Oxygen, 12)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addOreByproduct(() -> Materials_OLD.Aluminiumoxide)
            .addOreByproduct(() -> Materials_OLD.Magnesium)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)
            .constructMaterial();
    }

    private static Materials_OLD loadVinteum() {
        return new MaterialBuilder().setName("Vinteum")
            .setDefaultLocalName("Vinteum")
            .setChemicalFormula("FeMa*")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x0064c8ff)
            .setTool(128, 3, 10.0f)
            .setToolEnchantment(() -> Enchantment.fortune, 1)
            .setFuel(MaterialBuilder.FuelType.Magic, 32)
            .addDustItems()
            .addMetalItems()
            .addGemItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Thaumium, 1)
            .addOreByproduct(() -> Materials_OLD.Vinteum)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.lens)
            .constructMaterial();
    }

    private static Materials_OLD loadVis() {
        return new MaterialBuilder().setName("Vis")
            .setDefaultLocalName("Vis")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyePurple)
            .setARGB(0x008000ff)
            .setFuel(MaterialBuilder.FuelType.Magic, 32)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Magic, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadVolcanicAsh() {
        return new MaterialBuilder().setName("VolcanicAsh")
            .setDefaultLocalName("Volcanic Ashes")
            .setIconSet(TextureSet.SET_FLINT)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x003c3232)
            .addDustItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Flint, 6)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Magnesium, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadWollastonite() {
        return new MaterialBuilder().setName("Wollastonite")
            .setDefaultLocalName("Wollastonite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00f0f0f0)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            .addElectrolyzerRecipe()
            // CaSiO3
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadWoodSealed() {
        return new MaterialBuilder().setName("WoodSealed")
            .setDefaultLocalName("Sealed Wood")
            .setChemicalFormula("")
            .setIconSet(TextureSet.SET_WOOD)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00502800)
            .setTool(24, 0, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Wood, 1)
            .setMaceratingInto(() -> Materials_OLD.Wood)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_WORKING)
            .addSubTag(SubTag.WOOD)
            .removeOrePrefix(OrePrefixes.ingot)
            .constructMaterial();
    }

    private static Materials_OLD loadZeolite() {
        return new MaterialBuilder().setName("Zeolite")
            .setDefaultLocalName("Zeolite")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00f0e6e6)
            .setMiningLevel(2)
            .addDustItems()
            .addOreItems()
            // NaCa4(Si27Al9)O72
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Calcium, 4)
            .addMaterial(Materials_OLD.Silicon, 27)
            .addMaterial(Materials_OLD.Aluminium, 9)
            .addMaterial(Materials_OLD.Oxygen, 72)
            .constructMaterial();
    }

    private static void loadDegree3Compounds() {
        Materials_OLD.Basalt = loadBasalt();
        Materials_OLD.BlueSteel = loadBlueSteel();
        Materials_OLD.BorosilicateGlass = loadBorosilicateGlass();
        Materials_OLD.Cryotheum = loadCryotheum();
        Materials_OLD.DarkSteel = loadDarkSteel();
        Materials_OLD.Diatomite = loadDiatomite();
        Materials_OLD.EnderEye = loadEnderEye();
        Materials_OLD.Fireclay = loadFireclay();
        Materials_OLD.GarnetSand = loadGarnetSand();
        Materials_OLD.HSSG = loadHSSG();
        Materials_OLD.IronWood = loadIronWood();
        Materials_OLD.Pyrotheum = loadPyrotheum();
        Materials_OLD.GraniteRed = loadGraniteRed();
        Materials_OLD.RedAlloy = loadRedAlloy();
        Materials_OLD.RedSteel = loadRedSteel();
        Materials_OLD.RedstoneAlloy = loadRedstoneAlloy();
    }

    private static Materials_OLD loadBasalt() {
        return new MaterialBuilder().setName("Basalt")
            .setDefaultLocalName("Basalt")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x001e1414)
            .setTool(64, 1, 1.0f)
            .addDustItems()
            .addToolHeadItems()
            .addGearItems()
            .addCentrifugeRecipe()
            .setAutoGeneratedRecycleRecipes(false)
            .addMaterial(Materials_OLD.Olivine, 1)
            .addMaterial(Materials_OLD.Calcite, 3)
            .addMaterial(Materials_OLD.Flint, 8)
            .addMaterial(Materials_OLD.AshDark, 4)
            .addOreByproduct(() -> Materials_OLD.Olivine)
            .addOreByproduct(() -> Materials_OLD.AshDark)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .addOrePrefix(OrePrefixes.plate)
            .constructMaterial();
    }

    private static Materials_OLD loadBlueSteel() {
        return new MaterialBuilder().setName("BlueSteel")
            .setDefaultLocalName("Blue Steel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x0064648c)
            .setTool(1_024, 4, 7.5f)
            .setToolEnchantment(() -> Enchantment.sharpness, 5)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.RoseGold, 1)
            .addMaterial(Materials_OLD.Brass, 1)
            .addMaterial(Materials_OLD.Steel, 2)
            .addMaterial(Materials_OLD.BlackSteel, 4)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadBorosilicateGlass() {
        return new MaterialBuilder().setName("BorosilicateGlass")
            .setDefaultLocalName("Borosilicate Glass")
            .setIconSet(TextureSet.SET_GLASS)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e6f3e6)
            .addDustItems()
            .addMetalItems()
            .addMaterial(Materials_OLD.Boron, 1)
            .addMaterial(Materials_OLD.Glass, 7)
            .addCentrifugeRecipe()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_RECYCLING)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .constructMaterial();
    }

    private static Materials_OLD loadCryotheum() {
        return new MaterialBuilder().setName("Cryotheum")
            .setDefaultLocalName("Cryotheum")
            .setChemicalFormula(
                "(KNO₃)(" + Materials_OLD.Lapis.getChemicalFormula()
                    + ")(H₂O)("
                    + Materials_OLD.Blizz.getChemicalFormula()
                    + ")")
            .setIconSet(TextureSet.SET_CRYOTHEUM)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x000094cb)
            .addDustItems()
            .setDensity(4, 1)
            .setAutoGeneratedRecycleRecipes(false)
            .addMaterial(Materials_OLD.Saltpeter, 1)
            .addMaterial(Materials_OLD.Lapis, 1)
            .addMaterial(Materials_OLD.Snow, 1)
            .addMaterial(Materials_OLD.Blizz, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadDarkSteel() {
        return new MaterialBuilder().setName("DarkSteel")
            .setDefaultLocalName("Dark Steel")
            .setIconSet(TextureSet.SET_DARKSTEEL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00504650)
            .setTool(512, 3, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.ElectricalSteel, 1)
            .addMaterial(Materials_OLD.Coal, 1)
            .addMaterial(Materials_OLD.Obsidian, 1)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadDiatomite() {
        return new MaterialBuilder().setName("Diatomite")
            .setDefaultLocalName("Diatomite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00e1e1e1)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Flint, 8)
            .addMaterial(Materials_OLD.BandedIron, 1)
            .addMaterial(Materials_OLD.Sapphire, 1)
            .addOreByproduct(() -> Materials_OLD.BandedIron)
            .addOreByproduct(() -> Materials_OLD.Sapphire)
            .constructMaterial();
    }

    private static Materials_OLD loadEnderEye() {
        return new MaterialBuilder().setName("EnderEye")
            .setDefaultLocalName("Endereye")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00a0fae6)
            .setTool(16, 1, 1.0f)
            .setFuel(MaterialBuilder.FuelType.Magic, 10)
            .addDustItems()
            .addGemItems()
            .setDensity(2, 1)
            .addMaterial(Materials_OLD.EnderPearl, 1)
            .addMaterial(Materials_OLD.Blaze, 1)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.PEARL)
            .removeOrePrefix(OrePrefixes.gem) // minecraft:ender_eye
            .constructMaterial();
    }

    private static Materials_OLD loadFireclay() {
        return new MaterialBuilder().setName("Fireclay")
            .setDefaultLocalName("Fireclay")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00ada09b)
            .addDustItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Brick, 1)
            .addMaterial(Materials_OLD.Clay, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadGarnetSand() {
        return new MaterialBuilder().setName("GarnetSand")
            .setDefaultLocalName("Garnet Sand")
            .setIconSet(TextureSet.SET_SAND)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00c86400)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.GarnetRed, 1)
            .addMaterial(Materials_OLD.GarnetYellow, 1)
            .addOreByproduct(() -> Materials_OLD.GarnetRed)
            .addOreByproduct(() -> Materials_OLD.GarnetYellow)
            .constructMaterial();
    }

    private static Materials_OLD loadHSSG() {
        return new MaterialBuilder().setName("HSSG")
            .setDefaultLocalName("HSS-G")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00999900)
            .setTool(4_000, 3, 10.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 4)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.TungstenSteel, 5)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addMaterial(Materials_OLD.Molybdenum, 2)
            .addMaterial(Materials_OLD.Vanadium, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadIronWood() {
        return new MaterialBuilder().setName("IronWood")
            .setDefaultLocalName("Ironwood")
            .setIconSet(TextureSet.SET_WOOD)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00968c6e)
            .setTool(384, 2, 6.0f)
            .setToolEnchantment(() -> Enchantment.fortune, 1)
            .setArmorEnchantment(() -> Enchantment.aquaAffinity, 1)
            .setFuel(MaterialBuilder.FuelType.Magic, 8)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setDensity(19, 18)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Iron, 9)
            .addMaterial(Materials_OLD.LiveRoot, 9)
            .addMaterial(Materials_OLD.Gold, 1)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MORTAR_GRINDABLE)
            .addSubTag(SubTag.WOOD)
            .constructMaterial();
    }

    private static Materials_OLD loadPyrotheum() {
        return new MaterialBuilder().setName("Pyrotheum")
            .setDefaultLocalName("Pyrotheum")
            .setIconSet(TextureSet.SET_PYROTHEUM)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ff8000)
            .addDustItems()
            .setDensity(4, 1)
            .setHeatDamage(5.0f)
            .setAutoGeneratedRecycleRecipes(false)
            .addMaterial(Materials_OLD.Coal, 1)
            .addMaterial(Materials_OLD.Redstone, 1)
            .addMaterial(Materials_OLD.Blaze, 1)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadGraniteRed() {
        return new MaterialBuilder().setName("GraniteRed")
            .setDefaultLocalName("Red Granite")
            .setIconSet(TextureSet.SET_ROUGH)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x00ff0080)
            .setTool(64, 3, 4.0f)
            .addDustItems()
            .addToolHeadItems()
            .addGearItems()
            .addMaterial(Materials_OLD.Aluminium, 2)
            .addMaterial(Materials_OLD.PotassiumFeldspar, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addOreByproduct(() -> Materials_OLD.PotassiumFeldspar)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STONE)
            .addOrePrefix(OrePrefixes.dustImpure)
            .addOrePrefix(OrePrefixes.plate)
            .constructMaterial();
    }

    private static Materials_OLD loadRedAlloy() {
        return new MaterialBuilder().setName("RedAlloy")
            .setDefaultLocalName("Red Alloy")
            .setChemicalFormula("Cu(" + Materials_OLD.Redstone.getChemicalFormula() + ")₄")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c80000)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(500)
            .setDensity(5, 1)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Redstone, 4)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadRedSteel() {
        return new MaterialBuilder().setName("RedSteel")
            .setDefaultLocalName("Red Steel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x008c6464)
            .setTool(896, 4, 7.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 4)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_300)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.SterlingSilver, 1)
            .addMaterial(Materials_OLD.BismuthBronze, 1)
            .addMaterial(Materials_OLD.Steel, 2)
            .addMaterial(Materials_OLD.BlackSteel, 4)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadRedstoneAlloy() {
        return new MaterialBuilder().setName("RedstoneAlloy")
            .setDefaultLocalName("Redstone Alloy")
            .setIconSet(TextureSet.SET_DARKSTEEL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff4332)
            .setTool(128, 2, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(671)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Redstone, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addMaterial(Materials_OLD.Coal, 1)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static void loadDegree4Compounds() {
        Materials_OLD.BasalticMineralSand = loadBasalticMineralSand();
        Materials_OLD.ConductiveIron = loadConductiveIron();
        Materials_OLD.EndSteel = loadEndSteel();
        Materials_OLD.HSSE = loadHSSE();
        Materials_OLD.HSSS = loadHSSS();
        Materials_OLD.PulsatingIron = loadPulsatingIron();
    }

    private static Materials_OLD loadBasalticMineralSand() {
        return new MaterialBuilder().setName("BasalticMineralSand")
            .setDefaultLocalName("Basaltic Mineral Sand")
            .setIconSet(TextureSet.SET_SAND)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00283228)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Magnetite, 1)
            .addMaterial(Materials_OLD.Basalt, 1)
            .addOreByproduct(() -> Materials_OLD.Basalt)
            .addOreByproduct(() -> Materials_OLD.Magnetite)
            .setDirectSmelting(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_DOUBLE)
            .addSubTag(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD)
            .addSubTag(SubTag.INDUCTIONSMELTING_LOW_OUTPUT)
            .constructMaterial();
    }

    private static Materials_OLD loadConductiveIron() {
        return new MaterialBuilder().setName("ConductiveIron")
            .setDefaultLocalName("Conductive Iron")
            .setIconSet(TextureSet.SET_DARKSTEEL)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ffbfc3)
            .setTool(256, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.RedstoneAlloy, 1)
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Silver, 1)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadEndSteel() {
        return new MaterialBuilder().setName("EndSteel")
            .setDefaultLocalName("End Steel")
            .setIconSet(TextureSet.SET_END_STEEL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00dbce7d)
            .setTool(2_000, 4, 12.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(940)
            .setBlastFurnaceTemp(3_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.DarkSteel, 1)
            .addMaterial(Materials_OLD.Tungsten, 1)
            .addMaterial(Materials_OLD.Endstone, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadHSSE() {
        return new MaterialBuilder().setName("HSSE")
            .setDefaultLocalName("HSS-E")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00336600)
            .setTool(10_240, 7, 32.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 5)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.HSSG, 6)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Manganese, 1)
            .addMaterial(Materials_OLD.Silicon, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadHSSS() {
        return new MaterialBuilder().setName("HSSS")
            .setDefaultLocalName("HSS-S")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00660033)
            .setTool(10_240, 8, 32.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 5)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.HSSG, 6)
            .addMaterial(Materials_OLD.Iridium, 2)
            .addMaterial(Materials_OLD.Osmium, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadPulsatingIron() {
        return new MaterialBuilder().setName("PulsatingIron")
            .setDefaultLocalName("Pulsating Iron")
            .setIconSet(TextureSet.SET_DARKSTEEL)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0080f69b)
            .setTool(256, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.EnderPearl, 1)
            .addMaterial(Materials_OLD.RedstoneAlloy, 1)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static void loadDegree5Compounds() {
        Materials_OLD.CrystallineAlloy = loadCrystallineAlloy();
        Materials_OLD.EnergeticAlloy = loadEnergeticAlloy();
        Materials_OLD.EnergeticSilver = loadEnergeticSilver();
        Materials_OLD.MelodicAlloy = loadMelodicAlloy();
    }

    private static Materials_OLD loadCrystallineAlloy() {
        return new MaterialBuilder().setName("CrystallineAlloy")
            .setDefaultLocalName("Crystalline Alloy")
            .setIconSet(TextureSet.SET_CRYSTALLINE)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x004adbdb)
            .setTool(768, 4, 18.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Gold, 1)
            .addMaterial(Materials_OLD.Diamond, 1)
            .addMaterial(Materials_OLD.PulsatingIron, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEnergeticAlloy() {
        return new MaterialBuilder().setName("EnergeticAlloy")
            .setDefaultLocalName("Energetic Alloy")
            .setIconSet(TextureSet.SET_ENERGETIC)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8c19)
            .setTool(1_024, 3, 12.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(2_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.ConductiveIron, 1)
            .addMaterial(Materials_OLD.Gold, 1)
            .addMaterial(Materials_OLD.BlackSteel, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEnergeticSilver() {
        return new MaterialBuilder().setName("EnergeticSilver")
            .setDefaultLocalName("Energetic Silver")
            .setIconSet(TextureSet.SET_ENERGETIC_SILVER)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x0063a2c7)
            .setTool(512, 3, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(2_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Silver, 1)
            .addMaterial(Materials_OLD.ConductiveIron, 1)
            .addMaterial(Materials_OLD.BlackSteel, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMelodicAlloy() {
        return new MaterialBuilder().setName("MelodicAlloy")
            .setDefaultLocalName("Melodic Alloy")
            .setIconSet(TextureSet.SET_MELODIC)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x00c155c1)
            .setTool(1_024, 5, 24.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.EndSteel, 1)
            .addMaterial(Materials_OLD.EnderEye, 1)
            .addMaterial(Materials_OLD.Oriharukon, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static void loadDegree6Compounds() {
        Materials_OLD.CrystallinePinkSlime = loadCrystallinePinkSlime();
        Materials_OLD.StellarAlloy = loadStellarAlloy();
        Materials_OLD.VibrantAlloy = loadVibrantAlloy();
        Materials_OLD.VividAlloy = loadVividAlloy();
    }

    private static Materials_OLD loadCrystallinePinkSlime() {
        return new MaterialBuilder().setName("CrystallinePinkSlime")
            .setDefaultLocalName("Crystalline Pink Slime")
            .setIconSet(TextureSet.SET_CRYSTALLINE_PINK_SLIME)
            .setColor(Dyes.dyePink)
            .setARGB(0x00E56BDB)
            .setTool(128, 3, 6.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(5_000)
            .setBlastFurnaceTemp(5_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.CrystallineAlloy, 1)
            .addMaterial(Materials_OLD.Diamond, 1)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadStellarAlloy() {
        return new MaterialBuilder().setName("StellarAlloy")
            .setDefaultLocalName("Stellar Alloy")
            .setIconSet(TextureSet.SET_STELLAR)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00d3ffff)
            .setTool(10_240, 7, 96.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(7_200)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.NetherStar, 1)
            .addMaterial(Materials_OLD.MelodicAlloy, 1)
            .addMaterial(Materials_OLD.Naquadah, 1)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadVibrantAlloy() {
        return new MaterialBuilder().setName("VibrantAlloy")
            .setDefaultLocalName("Vibrant Alloy")
            .setIconSet(TextureSet.SET_VIBRANT)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0095e011)
            .setTool(4_048, 4, 18.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_300)
            .setBlastFurnaceTemp(3_300)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.EnergeticAlloy, 1)
            .addMaterial(Materials_OLD.EnderEye, 1)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadVividAlloy() {
        return new MaterialBuilder().setName("VividAlloy")
            .setDefaultLocalName("Vivid Alloy")
            .setIconSet(TextureSet.SET_VIVID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x0046bcdb)
            .setTool(768, 4, 12.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_300)
            .setBlastFurnaceTemp(3_300)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.EnergeticSilver, 1)
            .addMaterial(Materials_OLD.EnderEye, 1)
            .addMaterial(Materials_OLD.Chrome, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static void loadPolybenzimidazoleLine() {
        Materials_OLD.ChromiumTrioxide = loadChromiumTrioxide();
        Materials_OLD.Diaminobenzidin = loadDiaminobenzidin();
        Materials_OLD.Dichlorobenzidine = loadDichlorobenzidine();
        Materials_OLD.Dimethylbenzene = loadDimethylbenzene();
        Materials_OLD.Diphenylisophthalate = loadDiphenylisophthalate();
        Materials_OLD.Nitrochlorobenzene = loadNitrochlorobenzene();
        Materials_OLD.PhthalicAcid = loadPhthalicAcid();
        Materials_OLD.Polybenzimidazole = loadPolybenzimidazole();
        Materials_OLD.PotassiumNitrade = loadPotassiumNitrade();
        Materials_OLD.Potassiumdichromate = loadPotassiumdichromate();
    }

    private static Materials_OLD loadChromiumTrioxide() {
        return new MaterialBuilder().setName("Chromiumtrioxide")
            .setDefaultLocalName("Chromium Trioxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ffe4e1)
            .addDustItems()
            .addMaterial(Materials_OLD.Chrome, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDiaminobenzidin() {
        return new MaterialBuilder().setName("3,3Diaminobenzidine")
            .setDefaultLocalName("3,3-Diaminobenzidine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00337d59)
            .setMeltingPoint(448)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 12)
            .addMaterial(Materials_OLD.Hydrogen, 14)
            .addMaterial(Materials_OLD.Nitrogen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadDichlorobenzidine() {
        return new MaterialBuilder().setName("3,3Dichlorobenzidine")
            .setDefaultLocalName("3,3-Dichlorobenzidine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00a1dea6)
            .setMeltingPoint(405)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 12)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Chlorine, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadDimethylbenzene() {
        return new MaterialBuilder().setName("Dimethylbenzene")
            .setDefaultLocalName("1,2-Dimethylbenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00669c40)
            .setMeltingPoint(248)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadDiphenylisophthalate() {
        return new MaterialBuilder().setName("DiphenylIsophtalate")
            .setDefaultLocalName("Diphenyl Isophthalate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00246e57)
            .setMeltingPoint(409)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 20)
            .addMaterial(Materials_OLD.Hydrogen, 14)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadNitrochlorobenzene() {
        return new MaterialBuilder().setName("2Nitrochlorobenzene")
            .setDefaultLocalName("2-Nitrochlorobenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x008fb51a)
            .setMeltingPoint(306)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadPhthalicAcid() {
        return new MaterialBuilder().setName("phtalicacid")
            .setDefaultLocalName("Phthalic Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00368547)
            .setMeltingPoint(207)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadPolybenzimidazole() {
        return new MaterialBuilder().setName("Polybenzimidazole")
            .setDefaultLocalName("Polybenzimidazole (PBI)")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x002d2d2d)
            .setTool(64, 1, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_450)
            .addMaterial(Materials_OLD.Carbon, 20)
            .addMaterial(Materials_OLD.Nitrogen, 4)
            .addMaterial(Materials_OLD.Hydrogen, 12)
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadPotassiumNitrade() {
        return new MaterialBuilder().setName("PotassiumNitrate")
            .setDefaultLocalName("Potassium Nitrate")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x0081228d)
            .addDustItems()
            .addMaterial(Materials_OLD.Potassium, 1)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadPotassiumdichromate() {
        return new MaterialBuilder().setName("PotassiumDichromate")
            .setDefaultLocalName("Potassium Dichromate")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePink)
            .setARGB(0x00ff087f)
            .addDustItems()
            .addMaterial(Materials_OLD.Potassium, 2)
            .addMaterial(Materials_OLD.Chrome, 2)
            .addMaterial(Materials_OLD.Oxygen, 7)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static void loadGasolineLine() {
        Materials_OLD.AntiKnock = loadAntiKnock();
        Materials_OLD.GasolinePremium = loadGasolinePremium();
        Materials_OLD.GasolineRaw = loadGasolineRaw();
        Materials_OLD.GasolineRegular = loadGasolineRegular();
        Materials_OLD.MTBEMixture = loadMTBEMixture();
        Materials_OLD.MTBEMixtureAlt = loadMTBEMixtureAlt();
        Materials_OLD.NitrousOxide = loadNitrousOxide();
        Materials_OLD.Octane = loadOctane();
    }

    private static Materials_OLD loadAntiKnock() {
        return new MaterialBuilder().setName("EthylTertButylEther")
            .setDefaultLocalName("Anti-Knock Agent")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setMeltingPoint(179)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 14)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadGasolinePremium() {
        return new MaterialBuilder().setName("HighOctaneGasoline")
            .setDefaultLocalName("High Octane Gasoline")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ffa500)
            .setFuel(MaterialBuilder.FuelType.Diesel, 2_500)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadGasolineRaw() {
        return new MaterialBuilder().setName("RawGasoline")
            .setDefaultLocalName("Raw Gasoline")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff6400)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadGasolineRegular() {
        return new MaterialBuilder().setName("Gasoline")
            .setDefaultLocalName("Gasoline")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ffa500)
            .setFuel(MaterialBuilder.FuelType.Diesel, 576)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadMTBEMixture() {
        return new MaterialBuilder().setName("MTBEReactionMixture(Butene)")
            .setDefaultLocalName("MTBE Reaction Mixture (Butene)")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 12)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadMTBEMixtureAlt() {
        return new MaterialBuilder().setName("MTBEReactionMixture(Butane)")
            .setDefaultLocalName("MTBE Reaction Mixture (Butane)")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 14)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadNitrousOxide() {
        return new MaterialBuilder().setName("NitrousOxide")
            .setDefaultLocalName("Nitrous Oxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x007dc8ff)
            .addCell()
            .addGas()
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadOctane() {
        return new MaterialBuilder().setName("Octane")
            .setDefaultLocalName("Octane")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setMeltingPoint(216)
            .setFuel(MaterialBuilder.FuelType.Diesel, 80)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 18)
            .constructMaterial();
    }

    private static void loadAdded() {
        Materials_OLD.BloodInfusedIron = loadBloodInfusedIron();
        Materials_OLD.Electrotine = loadElectrotine();
        Materials_OLD.Galgadorian = loadGalgadorian();
        Materials_OLD.GalgadorianEnhanced = loadGalgadorianEnhanced();
        Materials_OLD.Shadow = loadShadow();
    }

    private static Materials_OLD loadBloodInfusedIron() {
        return new MaterialBuilder().setName("BloodInfusedIron")
            .setDefaultLocalName("Blood Infused Iron")
            .setChemicalFormula(CustomGlyphs.BRIMSTONE + "Fe")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeRed)
            .setARGB(0x0045090a)
            .setTool(384, 2, 10.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(2_400)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadElectrotine() {
        return new MaterialBuilder().setName("Electrotine")
            .setDefaultLocalName("Electrotine")
            .setChemicalFormula("Rp")
            .setIconSet(TextureSet.SET_ELECTROTINE)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x003cb4c8)
            .setMiningLevel(1)
            .addDustItems()
            .addOreItems()
            .setOreMultiplier(5)
            .addMaterial(Materials_OLD.Redstone, 1)
            .addMaterial(Materials_OLD.Electrum, 1)
            .addOreByproduct(() -> Materials_OLD.Redstone)
            .addOreByproduct(() -> Materials_OLD.Electrum)
            .addOreByproduct(() -> Materials_OLD.Diamond)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addSubTag(SubTag.STONE)
            .addSubTag(SubTag.UNBURNABLE)
            .addOrePrefix(OrePrefixes.plate)
            .constructMaterial();
    }

    private static Materials_OLD loadGalgadorian() {
        return new MaterialBuilder().setName("Galgadorian")
            .setDefaultLocalName("Galgadorian")
            .setChemicalFormula("???C₉Nh₃Fe₂(C(MgFeSi₂O₈)₈)")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x009a6977)
            .setTool(3_600, 3, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_000)
            .setBlastFurnaceTemp(3_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .constructMaterial();
    }

    private static Materials_OLD loadGalgadorianEnhanced() {
        return new MaterialBuilder().setName("EnhancedGalgadorian")
            .setDefaultLocalName("Enhanced Galgadorian")
            .setChemicalFormula("???C₉Nh₃Fe₂(C(MgFeSi₂O₈)₈)")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePink)
            .setARGB(0x00985d85)
            .setTool(7_200, 5, 32.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .constructMaterial();
    }

    private static Materials_OLD loadShadow() {
        return new MaterialBuilder().setName("Shadow")
            .setDefaultLocalName("Shadow Metal")
            .setChemicalFormula("Sh₆(FeMa₃)₂")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00100342)
            .setTool(8_192, 4, 32.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_800)
            .setBlastFurnaceTemp(1_800)
            .setBlastFurnaceRequired(true)
            .setDensity(4, 3)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static void loadGalaxySpace() {
        Materials_OLD.BlackPlutonium = loadBlackPlutonium();
        Materials_OLD.CallistoIce = loadCallistoIce();
        Materials_OLD.Duralumin = loadDuralumin();
        Materials_OLD.Ledox = loadLedox();
        Materials_OLD.MysteriousCrystal = loadMysteriousCrystal();
        Materials_OLD.Mytryl = loadMytryl();
        Materials_OLD.Quantium = loadQuantium();
    }

    private static Materials_OLD loadBlackPlutonium() {
        return new MaterialBuilder().setName("BlackPlutonium")
            .setDefaultLocalName("Black Plutonium")
            .setChemicalFormula("SpPu")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .setTool(8_192, 8, 36.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_000)
            .setBlastFurnaceTemp(9_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadCallistoIce() {
        return new MaterialBuilder().setName("CallistoIce")
            .setDefaultLocalName("Callisto Ice")
            .setChemicalFormula("SpH₂O")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x001eb1ff)
            .setTool(1_024, 4, 9.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadDuralumin() {
        return new MaterialBuilder().setName("Duralumin")
            .setDefaultLocalName("Duralumin")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ebd1a0)
            .setTool(512, 3, 16.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_600)
            .setBlastFurnaceTemp(1_600)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Aluminium, 6)
            .addMaterial(Materials_OLD.Copper, 1)
            .addMaterial(Materials_OLD.Manganese, 1)
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadLedox() {
        return new MaterialBuilder().setName("Ledox")
            .setDefaultLocalName("Ledox")
            .setChemicalFormula("SpPb")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000074ff)
            .setTool(1_024, 4, 15.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadMysteriousCrystal() {
        return new MaterialBuilder().setName("MysteriousCrystal")
            .setDefaultLocalName("Mysterious Crystal")
            .setChemicalFormula(CustomGlyphs.CIRCLE_STAR)
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x0016856c)
            .setTool(256, 6, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(7_200)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static Materials_OLD loadMytryl() {
        return new MaterialBuilder().setName("Mytryl")
            .setDefaultLocalName("Mytryl")
            .setChemicalFormula("SpPt₂FeMa")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00f26404)
            .setTool(512, 4, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(3_600)
            .setBlastFurnaceTemp(3_600)
            .setBlastFurnaceRequired(true)
            .addOreByproduct(() -> Materials_OLD.Samarium)
            .addOreByproduct(() -> Materials_OLD.Zinc)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadQuantium() {
        return new MaterialBuilder().setName("Quantium")
            .setDefaultLocalName("Quantium")
            .setChemicalFormula("Qt")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0000d10b)
            .setTool(2_048, 4, 18.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_900)
            .setBlastFurnaceTemp(9_900)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static void loadUnclassified07() {
        Materials_OLD.AstralSilver = loadAstralSilver();
        Materials_OLD.BlueAlloy = loadBlueAlloy();
        Materials_OLD.ClayCompound = loadClayCompound();
        Materials_OLD.Enderium = loadEnderium();
        Materials_OLD.Mithril = loadMithril();
        Materials_OLD.ShadowIron = loadShadowIron();
        Materials_OLD.ShadowSteel = loadShadowSteel();
        Materials_OLD.Soularium = loadSoularium();
    }

    private static Materials_OLD loadAstralSilver() {
        return new MaterialBuilder().setName("AstralSilver")
            .setDefaultLocalName("Astral Silver")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e6e6ff)
            .setTool(64, 2, 10.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Silver, 2)
            .addMaterial(Materials_OLD.Thaumium, 1)
            .addOreByproduct(() -> Materials_OLD.Silver)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.WASHING_MERCURY)
            .constructMaterial();
    }

    private static Materials_OLD loadBlueAlloy() {
        return new MaterialBuilder().setName("BlueAlloy")
            .setDefaultLocalName("Blue Alloy")
            .setChemicalFormula("AgRp₄")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x0064b4ff)
            .addDustItems()
            .addMetalItems()
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Silver, 1)
            .addMaterial(Materials_OLD.Electrotine, 4)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadClayCompound() {
        return new MaterialBuilder().setName("CrudeSteel")
            .setDefaultLocalName("Clay Compound")
            .setIconSet(TextureSet.SET_CRUDE_STEEL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x009e9087)
            .setTool(64, 2, 2.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(1_000)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Stone, 1)
            .addMaterial(Materials_OLD.Clay, 1)
            .addMaterial(Materials_OLD.Flint, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadEnderium() {
        return new MaterialBuilder().setName("Enderium")
            .setDefaultLocalName("Enderium")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00599187)
            .setTool(1_500, 3, 8.0f)
            .setToolEnchantment(() -> Enchantment.silkTouch, 1)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(4_500)
            .setBlastFurnaceTemp(4_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.EnderiumBase, 2)
            .addMaterial(Materials_OLD.Thaumium, 1)
            .addMaterial(Materials_OLD.EnderPearl, 1)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadMithril() {
        return new MaterialBuilder().setName("Mithril")
            .setDefaultLocalName("Mithril")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightBlue)
            .setARGB(0x00ffffd2)
            .setTool(64, 2, 32.0f)
            .setToolEnchantment(() -> Enchantment.fortune, 3)
            .setTurbine(22.0f, 1.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .setMeltingPoint(6_600)
            .setBlastFurnaceTemp(6_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Platinum, 2)
            .addMaterial(Materials_OLD.Thaumium, 1)
            .addOreByproduct(() -> Materials_OLD.Platinum)
            .addSubTag(SubTag.MAGICAL)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.WASHING_MERCURY)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadShadowIron() {
        return new MaterialBuilder().setName("ShadowIron")
            .setDefaultLocalName("Shadow Iron")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00787878)
            .setTool(10_240, 2, 32.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 3)
            .setTurbine(1.0f, 76.0f, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .setMeltingPoint(8_400)
            .setBlastFurnaceTemp(8_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Thaumium, 3)
            .addOreByproduct(() -> Materials_OLD.Iron)
            .addSubTag(SubTag.BLASTFURNACE_CALCITE_TRIPLE)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .constructMaterial();
    }

    private static Materials_OLD loadShadowSteel() {
        return new MaterialBuilder().setName("ShadowSteel")
            .setDefaultLocalName("Shadow Steel")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x005a5a5a)
            .setTool(768, 4, 6.0f)
            .setToolEnchantment(() -> Enchantment.sharpness, 4)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .setBlastFurnaceTemp(1_700)
            .setBlastFurnaceRequired(true)
            .addCentrifugeRecipe()
            .addMaterial(Materials_OLD.Steel, 1)
            .addMaterial(Materials_OLD.Thaumium, 3)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSoularium() {
        return new MaterialBuilder().setName("Soularium")
            .setDefaultLocalName("Soularium")
            .setIconSet(TextureSet.SET_DARKSTEEL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00916d3e)
            .setTool(256, 2, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(800)
            .setBlastFurnaceTemp(1_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.SoulSand, 1)
            .addMaterial(Materials_OLD.Gold, 1)
            .addMaterial(Materials_OLD.Ash, 1)
            .constructMaterial();
    }

    private static void loadOverpoweredMaterials() {
        Materials_OLD.Bedrockium = loadBedrockium();
        Materials_OLD.CosmicNeutronium = loadCosmicNeutronium();
        Materials_OLD.Ichorium = loadIchorium();
        Materials_OLD.Infinity = loadInfinity();
        Materials_OLD.InfinityCatalyst = loadInfinityCatalyst();
        Materials_OLD.Trinium = loadTrinium();
    }

    private static Materials_OLD loadBedrockium() {
        return new MaterialBuilder().setName("Bedrockium")
            .setDefaultLocalName("Bedrockium")
            .setIconSet(TextureSet.SET_BEDROCKIUM)
            .setColor(Dyes.dyeBlack)
            .addOreItems()
            .addDustItems()
            .addPlasma()
            .addMetalItems()
            .setTool(327_680, 9, 8f)
            .setBlastFurnaceRequired(true)
            .setBlastFurnaceTemp(9_900)
            .setMeltingPoint(9_900)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addMaterial(Materials_OLD.SiliconDioxide, 26_244)
            .addMaterial(Materials_OLD.Diamond, 9)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSMUTABLE_NUGGETS)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_EV);
    }

    private static Materials_OLD loadCosmicNeutronium() {
        return new MaterialBuilder().setName("CosmicNeutronium")
            .setDefaultLocalName("Cosmic Neutronium")
            .setChemicalFormula("SpNt")
            .setIconSet(TextureSet.SET_COSMIC_NEUTRONIUM)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323237)
            .setTool(163_840, 12, 96.0f)
            .setTurbine(6.0f, 6.0f, 3.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_900)
            .setBlastFurnaceTemp(9_900)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .hasGlowingOre()
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_ZPM);
    }

    private static Materials_OLD loadIchorium() {
        return new MaterialBuilder().setName("Ichorium")
            .setDefaultLocalName("Ichorium")
            .setChemicalFormula("IcMa")
            .setFlavorText("Fabric of planar coalescence")
            .setIconSet(TextureSet.SET_ICHORIUM)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00d37806)
            .setTool(850_000, 12, 32.0f)
            .setToolEnchantment(() -> Enchantment.smite, 8)
            .setTurbine(6.0f, 6.0f, 3.0f)
            .setFuel(MaterialBuilder.FuelType.Magic, 250_000)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(9_000)
            .setBlastFurnaceTemp(9_000)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadInfinity() {
        return new MaterialBuilder().setName("Infinity")
            .setDefaultLocalName("Infinity")
            .setChemicalFormula("If*")
            .setFlavorText("The fury of the universe in the palm of your hand")
            .setIconSet(TextureSet.SET_INFINITY)
            .setColor(Dyes.dyeLightGray)
            .setTool(2_621_440, 17, 256.0f)
            .setFuel(MaterialBuilder.FuelType.Magic, 5_000_000)
            .addDustItems()
            .addMetalItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(10_800)
            .setBlastFurnaceTemp(10_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UV);
    }

    private static Materials_OLD loadInfinityCatalyst() {
        return new MaterialBuilder().setName("InfinityCatalyst")
            .setDefaultLocalName("Infinity Catalyst")
            .setChemicalFormula("If")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setTool(1_310_720, 10, 64.0f)
            .setFuel(MaterialBuilder.FuelType.Magic, 500_000)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(10_800)
            .setBlastFurnaceTemp(10_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UV);
    }

    private static Materials_OLD loadTrinium() {
        return new MaterialBuilder().setName("Trinium")
            .setDefaultLocalName("Trinium")
            .setChemicalFormula("Ke")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00c8c8d2)
            .setTool(51_200, 8, 128.0f)
            .addDustItems()
            .addMetalItems()
            .addOreItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(7_200)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial();
    }

    private static void loadSuperconductorBases() {
        Materials_OLD.SuperconductorMVBase = loadSuperconductorMVBase();
        Materials_OLD.SuperconductorHVBase = loadSuperconductorHVBase();
        Materials_OLD.SuperconductorEVBase = loadSuperconductorEVBase();
        Materials_OLD.SuperconductorIVBase = loadSuperconductorIVBase();
        Materials_OLD.SuperconductorLuVBase = loadSuperconductorLuVBase();
        Materials_OLD.SuperconductorZPMBase = loadSuperconductorZPMBase();
        Materials_OLD.SuperconductorUVBase = loadSuperconductorUVBase();
        Materials_OLD.SuperconductorUHVBase = loadSuperconductorUHVBase();
        Materials_OLD.SuperconductorUEVBase = loadSuperconductorUEVBase();
        Materials_OLD.SuperconductorUIVBase = loadSuperconductorUIVBase();
        Materials_OLD.SuperconductorUMVBase = loadSuperconductorUMVBase();
    }

    private static Materials_OLD loadSuperconductorMVBase() {
        return new MaterialBuilder().setName("Pentacadmiummagnesiumhexaoxid")
            .setDefaultLocalName("Superconductor Base MV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00555555)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(2_500)
            .setBlastFurnaceTemp(2_500)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Cadmium, 5)
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorHVBase() {
        return new MaterialBuilder().setName("Titaniumonabariumdecacoppereikosaoxid")
            .setDefaultLocalName("Superconductor Base HV")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00331900)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(3_300)
            .setBlastFurnaceTemp(3_300)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Titanium, 1)
            .addMaterial(Materials_OLD.Barium, 9)
            .addMaterial(Materials_OLD.Copper, 10)
            .addMaterial(Materials_OLD.Oxygen, 20)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorEVBase() {
        return new MaterialBuilder().setName("Uraniumtriplatinid")
            .setDefaultLocalName("Superconductor Base EV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00008700)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(4_400)
            .setBlastFurnaceTemp(4_400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Uranium, 1)
            .addMaterial(Materials_OLD.Platinum, 3)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorIVBase() {
        return new MaterialBuilder().setName("Vanadiumtriindinid")
            .setDefaultLocalName("Superconductor Base IV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x00330033)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(5_200)
            .setBlastFurnaceTemp(5_200)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Vanadium, 1)
            .addMaterial(Materials_OLD.Indium, 3)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorLuVBase() {
        return new MaterialBuilder().setName("Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid")
            .setDefaultLocalName("Superconductor Base LuV")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00994c00)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(6_000)
            .setBlastFurnaceTemp(6_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Indium, 4)
            .addMaterial(Materials_OLD.Tin, 2)
            .addMaterial(Materials_OLD.Barium, 2)
            .addMaterial(Materials_OLD.Titanium, 1)
            .addMaterial(Materials_OLD.Copper, 7)
            .addMaterial(Materials_OLD.Oxygen, 14)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorZPMBase() {
        return new MaterialBuilder().setName("Tetranaquadahdiindiumhexaplatiumosminid")
            .setDefaultLocalName("Superconductor Base ZPM")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(8_100)
            .setBlastFurnaceTemp(8_100)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Naquadah, 4)
            .addMaterial(Materials_OLD.Indium, 2)
            .addMaterial(Materials_OLD.Palladium, 6)
            .addMaterial(Materials_OLD.Osmium, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorUVBase() {
        return new MaterialBuilder().setName("Longasssuperconductornameforuvwire")
            .setDefaultLocalName("Superconductor Base UV")
            .setChemicalFormula("Nq*₄(Ir₃Os)₃EuSm")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00e0d207)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(9_900)
            .setBlastFurnaceTemp(9_900)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Naquadria, 4)
            .addMaterial(Materials_OLD.Osmiridium, 3)
            .addMaterial(Materials_OLD.Europium, 1)
            .addMaterial(Materials_OLD.Samarium, 1)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_LuV);
    }

    private static Materials_OLD loadSuperconductorUHVBase() {
        return new MaterialBuilder().setName("Longasssuperconductornameforuhvwire")
            .setDefaultLocalName("Superconductor Base UHV")
            .setChemicalFormula("D₆(SpNt)₇Tn₅Am₆")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x002681bd)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(10_800)
            .setBlastFurnaceTemp(10_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Draconium, 6)
            .addMaterial(Materials_OLD.CosmicNeutronium, 7)
            .addMaterial(Materials_OLD.Tritanium, 5)
            .addMaterial(Materials_OLD.Americium, 6)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_ZPM);
    }

    private static Materials_OLD loadSuperconductorUEVBase() {
        return new MaterialBuilder().setName("SuperconductorUEVBase")
            .setDefaultLocalName("Superconductor Base UEV")
            .setChemicalFormula("D*₅If*₅(✦◆✦)(⚷⚙⚷Ni4Ti6)")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ae0808)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(11_700)
            .setBlastFurnaceTemp(11_800)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UV);
    }

    private static Materials_OLD loadSuperconductorUIVBase() {
        return new MaterialBuilder().setName("SuperconductorUIVBase")
            .setDefaultLocalName("Superconductor Base UIV")
            .setChemicalFormula("(C₁₄Os₁₁O₇Ag₃SpH₂O)₄?₁" + CustomGlyphs.SUBSCRIPT0 + "(Fs⚶)₆(⌘☯☯⌘)₅")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e558b1)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(12_700)
            .setBlastFurnaceTemp(12_700)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UHV);
    }

    private static Materials_OLD loadSuperconductorUMVBase() {
        return new MaterialBuilder().setName("SuperconductorUMVBase")
            .setDefaultLocalName("Superconductor Base UMV")
            .setChemicalFormula(
                "?₆Or₃(Hy⚶)₁₁(((CW)₇Ti₃)₃" + CustomGlyphs.FIRE + CustomGlyphs.EARTH + CustomGlyphs.CHAOS + ")₅۞₂")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00b526cd)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(13_600)
            .setBlastFurnaceTemp(13_600)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static void loadSuperconductors() {
        Materials_OLD.SuperconductorMV = loadSuperconductorMV();
        Materials_OLD.SuperconductorHV = loadSuperconductorHV();
        Materials_OLD.SuperconductorEV = loadSuperconductorEV();
        Materials_OLD.SuperconductorIV = loadSuperconductorIV();
        Materials_OLD.SuperconductorLuV = loadSuperconductorLuV();
        Materials_OLD.SuperconductorZPM = loadSuperconductorZPM();
        Materials_OLD.SuperconductorUV = loadSuperconductorUV();
        Materials_OLD.SuperconductorUHV = loadSuperconductorUHV();
        Materials_OLD.SuperconductorUEV = loadSuperconductorUEV();
        Materials_OLD.SuperconductorUIV = loadSuperconductorUIV();
        Materials_OLD.SuperconductorUMV = loadSuperconductorUMV();
    }

    private static Materials_OLD loadSuperconductorMV() {
        return new MaterialBuilder().setName("SuperconductorMV")
            .setDefaultLocalName("Superconductor MV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00555555)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorHV() {
        return new MaterialBuilder().setName("SuperconductorHV")
            .setDefaultLocalName("Superconductor HV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00331900)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorEV() {
        return new MaterialBuilder().setName("SuperconductorEV")
            .setDefaultLocalName("Superconductor EV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLime)
            .setARGB(0x00008700)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorIV() {
        return new MaterialBuilder().setName("SuperconductorIV")
            .setDefaultLocalName("Superconductor IV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x00330033)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorLuV() {
        return new MaterialBuilder().setName("SuperconductorLuV")
            .setDefaultLocalName("Superconductor LuV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00994c00)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorZPM() {
        return new MaterialBuilder().setName("SuperconductorZPM")
            .setDefaultLocalName("Superconductor ZPM")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x000a0a0a)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorUV() {
        return new MaterialBuilder().setName("SuperconductorUV")
            .setDefaultLocalName("Superconductor UV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00e0d207)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorUHV() {
        return new MaterialBuilder().setName("Superconductor")
            .setDefaultLocalName("Superconductor UHV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x002681bd)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorUEV() {
        return new MaterialBuilder().setName("SuperconductorUEV")
            .setDefaultLocalName("Superconductor UEV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ae0808)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorUIV() {
        return new MaterialBuilder().setName("SuperconductorUIV")
            .setDefaultLocalName("Superconductor UIV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00e558b1)
            .constructMaterial();
    }

    private static Materials_OLD loadSuperconductorUMV() {
        return new MaterialBuilder().setName("SuperconductorUMV")
            .setDefaultLocalName("Superconductor UMV")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00b526cd)
            .constructMaterial();
    }

    private static void loadWaterLineChemicals() {
        Materials_OLD.ActivatedCarbon = loadActivatedCarbon();
        Materials_OLD.PreActivatedCarbon = loadPreActivatedCarbon();
        Materials_OLD.DirtyActivatedCarbon = loadDirtyActivatedCarbon();
        Materials_OLD.PolyAluminiumChloride = loadPolyAluminiumChloride();
        Materials_OLD.Ozone = loadOzone();
        Materials_OLD.StableBaryonicMatter = loadStableBaryonicMatter();
    }

    private static Materials_OLD loadActivatedCarbon() {
        return new MaterialBuilder().setName("ActivatedCarbon")
            .setDefaultLocalName("Activated Carbon")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00141414)
            .addDustItems()
            .setAutoGeneratedRecycleRecipes(false)
            .addMaterial(Materials_OLD.Carbon, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPreActivatedCarbon() {
        return new MaterialBuilder().setName("PreActivatedCarbon")
            .setDefaultLocalName("Pre-Activated Carbon")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x000f3341)
            .addDustItems()
            .setAutoGeneratedRecycleRecipes(false)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.PhosphoricAcid, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadDirtyActivatedCarbon() {
        return new MaterialBuilder()
            // don't change this to the more sensible name or a centrifuge recipe appears
            .setName("carbonactivateddirty")
            .setDefaultLocalName("Dirty Activated Carbon")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x006e6e6e)
            .addDustItems()
            .setAutoGeneratedRecycleRecipes(false)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.PhosphoricAcid, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPolyAluminiumChloride() {
        return new MaterialBuilder().setName("PolyaluminiumChloride")
            .setDefaultLocalName("Polyaluminium Chloride")
            .setChemicalFormula("Al₂(OH)₃Cl₃")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00fcec05)
            .addFluid()
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadOzone() {
        return new MaterialBuilder().setName("Ozone")
            .setDefaultLocalName("Ozone")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00bef4fa)
            .addGas()
            .addCell()
            .addMaterial(Materials_OLD.Oxygen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadStableBaryonicMatter() {
        return new MaterialBuilder().setName("stablebaryonicmatter")
            .setDefaultLocalName("Stabilised Baryonic Matter")
            .setIconSet(TextureSet.SET_SBM)
            .setARGB(0x00ffffff)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static void loadRadoxLine() {
        Materials_OLD.RadoxCracked = loadRadoxCracked();
        Materials_OLD.RadoxGas = loadRadoxGas();
        Materials_OLD.RadoxHeavy = loadRadoxHeavy();
        Materials_OLD.RadoxLight = loadRadoxLight();
        Materials_OLD.RadoxPolymer = loadRadoxPolymer();
        Materials_OLD.RadoxRaw = loadRadoxRaw();
        Materials_OLD.RadoxSuperHeavy = loadRadoxSuperHeavy();
        Materials_OLD.RadoxSuperLight = loadRadoxSuperLight();
        Materials_OLD.Xenoxene = loadXenoxene();
        Materials_OLD.XenoxeneDiluted = loadXenoxeneDiluted();
    }

    private static Materials_OLD loadRadoxCracked() {
        return new MaterialBuilder().setName("CrackedRadox")
            .setDefaultLocalName("Cracked Radox")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00b482b4)
            .addGas()
            .constructMaterial();
    }

    private static Materials_OLD loadRadoxGas() {
        return new MaterialBuilder().setName("RadoxGas")
            .setDefaultLocalName("Radox Gas")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00ff82ff)
            .addGas()
            .constructMaterial();
    }

    private static Materials_OLD loadRadoxHeavy() {
        return new MaterialBuilder().setName("HeavyRadox")
            .setDefaultLocalName("Heavy Radox")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00730073)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadRadoxLight() {
        return new MaterialBuilder().setName("LightRadox")
            .setDefaultLocalName("Light Radox")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x008c008c)
            .addGas()
            .constructMaterial();
    }

    private static Materials_OLD loadRadoxPolymer() {
        return new MaterialBuilder().setName("RadoxPoly")
            .setDefaultLocalName("Radox Polymer")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00850080)
            .setTool(600000, 3, 8.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addCell()
            .addGas()
            .setMeltingPoint(6_203)
            .addMaterial(Materials_OLD.Carbon, 14)
            .addMaterial(Materials_OLD.Osmium, 11)
            .addMaterial(Materials_OLD.Oxygen, 7)
            .addMaterial(Materials_OLD.Silver, 3)
            .addMaterial(Materials_OLD.CallistoIce, 1)
            .addSubTag(SubTag.SOFT)
            .removeOrePrefix(OrePrefixes.cell) // non molten cell is useless
            .constructMaterial()
            .setGasTemperature(12_406);
    }

    private static Materials_OLD loadRadoxRaw() {
        return new MaterialBuilder().setName("RawRadox")
            .setDefaultLocalName("Raw Radox")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00501e50)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadRadoxSuperHeavy() {
        return new MaterialBuilder().setName("SuperHeavyRadox")
            .setDefaultLocalName("Super Heavy Radox")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00640064)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadRadoxSuperLight() {
        return new MaterialBuilder().setName("SuperLightRadox")
            .setDefaultLocalName("Super Light Radox")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x009b009b)
            .addGas()
            .constructMaterial();
    }

    private static Materials_OLD loadXenoxene() {
        return new MaterialBuilder().setName("Xenoxene")
            .setDefaultLocalName("Xenoxene")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00858280)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadXenoxeneDiluted() {
        return new MaterialBuilder().setName("DilutedXenoxene")
            .setDefaultLocalName("Diluted Xenoxene")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00cec8c4)
            .addFluid()
            .constructMaterial();
    }

    private static void loadNetheriteLine() {
        Materials_OLD.NetherAir = loadNetherAir();
        Materials_OLD.NetherSemiFluid = loadNethersemifluid();
        Materials_OLD.NefariousGas = loadNefariousGas();
        Materials_OLD.NefariousOil = loadNefariousOil();
        Materials_OLD.PoorNetherWaste = loadPoorNetherWaste();
        Materials_OLD.RichNetherWaste = loadRichNetherWaste();
        Materials_OLD.HellishMetal = loadHellishMetal();
        Materials_OLD.Netherite = loadNetherite();
        Materials_OLD.Netherite.remove(SubTag.SMELTING_TO_FLUID);
        Materials_OLD.ActivatedNetherite = loadActivatedNetherite();
    }

    private static Materials_OLD loadNetherAir() {
        return new MaterialBuilder().setName("netherair")
            .setDefaultLocalName("Nether Air")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00eea39a)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadNethersemifluid() {
        return new MaterialBuilder().setName("nethersemifluid")
            .setDefaultLocalName("Nether Semifluid")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00dac172)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadNefariousGas() {
        return new MaterialBuilder().setName("nefariousgas")
            .setDefaultLocalName("Nefarious Gas")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00300a05)
            .setFuel(MaterialBuilder.FuelType.Gas, 1200)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadNefariousOil() {
        return new MaterialBuilder().setName("nefariousoil")
            .setDefaultLocalName("Nefarious Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00391616)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 256)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadPoorNetherWaste() {
        return new MaterialBuilder().setName("poornetherwaste")
            .setDefaultLocalName("Poor Nether Waste")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00a0827e)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadRichNetherWaste() {
        return new MaterialBuilder().setName("richnetherwaste")
            .setDefaultLocalName("Rich Nether Waste")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00f9827e)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadHellishMetal() {
        return new MaterialBuilder().setName("HellishMetal")
            .setDefaultLocalName("Hellish Metal")
            .setChemicalFormula("RhMa")
            .setIconSet(TextureSet.SET_FIERY)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0xffaaaaaa)
            .addMetalItems()
            .setMeltingPoint(1_200)
            .setBlastFurnaceTemp(1_900)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.itemCasing)
            .removeOrePrefix(OrePrefixes.nugget)
            .constructMaterial();
    }

    private static Materials_OLD loadNetherite() {
        return new MaterialBuilder().setName("Netherite")
            .setDefaultLocalName("Netherite")
            .setChemicalFormula("NrAuMa*")
            .setIconSet(TextureSet.SET_NETHERITE)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0xffffffff)
            .addMetalItems()
            .addGearItems()
            .setMeltingPoint(1_200)
            .setBlastFurnaceTemp(1_900)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.nugget)
            .removeOrePrefix(OrePrefixes.spring)
            .removeOrePrefix(OrePrefixes.springSmall)
            .removeOrePrefix(OrePrefixes.sheetmetal)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_ZPM);
    }

    private static Materials_OLD loadActivatedNetherite() {
        // The corresponding fluid is registered manually in LoaderGTBlockFluid so it can use a molten texture and a
        // custom temperature, so addFluid() is intentionally omitted here to avoid a duplicate registration.
        return new MaterialBuilder().setName("activatednetherite")
            .setDefaultLocalName("Activated Netherite")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x009c575a)
            .addCell()
            .constructMaterial();
    }

    private static void loadPrismaticAcidLine() {
        Materials_OLD.PrismarineSolution = loadPrismarineSolution();
        Materials_OLD.PrismarineContaminatedHydrogenPeroxide = loadPrismarinecontaminatedhydrogenperoxide();
        Materials_OLD.PrismarineRichNitrobenzeneSolution = loadPrismarinerichnitrobenzenesolution();
        Materials_OLD.PrismarineContaminatedNitrobenzeSolution = loadPrismarinecontaminatednitrobenzenesolution();
        Materials_OLD.PrismaticGas = loadPrismaticGas();
        Materials_OLD.PrismaticAcid = loadPrismaticAcid();
        Materials_OLD.PrismaticNaquadah = loadPrismaticNaquadah();
        Materials_OLD.PrismaticNaquadahCompositeSlurry = loadPrismaticNaquadahCompositeSlurry();
    }

    private static Materials_OLD loadPrismarineSolution() {
        return new MaterialBuilder().setName("prismarinesolution")
            .setDefaultLocalName("Prismarine Solution")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00559a8a)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadPrismarinecontaminatedhydrogenperoxide() {
        return new MaterialBuilder().setName("prismarinecontaminatedhydrogenperoxide")
            .setDefaultLocalName("Prismarine-Contaminated Hydrogen Peroxide")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00445f59)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadPrismarinerichnitrobenzenesolution() {
        return new MaterialBuilder().setName("prismarinerichnitrobenzenesolution")
            .setDefaultLocalName("Prismarine-Rich Nitrobenzene Solution")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x005d763f)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadPrismarinecontaminatednitrobenzenesolution() {
        return new MaterialBuilder().setName("prismarinecontaminatednitrobenzenesolution")
            .setDefaultLocalName("Prismarine-Contaminated Nitrobenzene Solution")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x002f331e)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadPrismaticGas() {
        return new MaterialBuilder().setName("prismaticgas")
            .setDefaultLocalName("Prismatic Gas")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x0076babd)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadPrismaticAcid() {
        return new MaterialBuilder().setName("prismaticacid")
            .setDefaultLocalName("Prismatic Acid")
            .setIconSet(TextureSet.SET_PRISMATIC_ACID)
            .setARGB(0x00ffffff)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPrismaticNaquadah() {
        return new MaterialBuilder().setName("prismaticnaquadah")
            .setDefaultLocalName("Prismatic Naquadah")
            .setChemicalFormula(Materials_OLD.Naquadah.getChemicalFormula() + "\u0394")
            .setFlavorText("Absorbs all radiation")
            .setIconSet(TextureSet.SET_METALLIC)
            .setARGB(0x00373737)
            .addDustItems()
            .addMetalItems()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_ZPM);
    }

    private static Materials_OLD loadPrismaticNaquadahCompositeSlurry() {
        return new MaterialBuilder().setName("prismaticnaquadahcompositeslurry")
            .setDefaultLocalName("Prismatic Naquadah Composite Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x004b4b4b)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static void loadAcidRecipes() {
        Materials_OLD.ImpureFranciumSolution = loadImpureFranciumSolution();
        Materials_OLD.FranciumHydroxide = loadFranciumHydroxide();
        Materials_OLD.FranciumSlurry = loadFranciumSlurry();
        Materials_OLD.ThoriumElutionAdsorbent = loadThoriumElutionAdsorbent();
        Materials_OLD.PhosphorusChlorineMixture = loadPhosphorusChlorineMixture();
        Materials_OLD.PhosphorusPentachloride = loadPhosphorusPentachloride();
        Materials_OLD.ToxicAir = loadToxicAir();
        Materials_OLD.ToxicSlurry = loadToxicSlurry();
        Materials_OLD.DestabilizationSlurry = loadDestabilizationSlurry();
        Materials_OLD.AgitatingSlurry = loadAgitatingSlurry();
        Materials_OLD.UltraContaminatedGas = loadUltraContaminatedGas();
        Materials_OLD.StagnantWasteWater = loadStagnantWasteWater();
        Materials_OLD.ActivatedWasteWater = loadActivatedWasteWater();
        Materials_OLD.ChlorosulfonicAcid = loadChlorosulfonicAcid();
        Materials_OLD.PoisonousSlurry = loadPoisonousSlurry();
    }

    private static Materials_OLD loadImpureFranciumSolution() {
        return new MaterialBuilder().setName("ImpureFranciumSolution")
            .setDefaultLocalName("Impure Francium Solution")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x004a180a)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadFranciumSlurry() {
        return new MaterialBuilder().setName("FranciumSlurry")
            .setDefaultLocalName("Francium Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00b02000)
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadFranciumHydroxide() {
        return new MaterialBuilder().setName("FranciumHydroxide")
            .setDefaultLocalName("Francium Hydroxide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyePink)
            .setARGB(0x00f2653d)
            .addDustItems()
            .addMaterial(Materials_OLD.Francium, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadThoriumElutionAdsorbent() {
        return new MaterialBuilder().setName("ThoriumElutionAdsorbent")
            .setDefaultLocalName("Thorium Elution Adsorbent")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0007835a)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphorusChlorineMixture() {
        return new MaterialBuilder().setName("PhosphorusChlorineMixture")
            .setDefaultLocalName("Phosphorus-Chlorine Mixture")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00f7f3b8)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphorusPentachloride() {
        return new MaterialBuilder().setName("PhosphorusPentachloride")
            .setDefaultLocalName("Phosphorus Pentachloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00ebf2b3)
            .setMeltingPoint(434)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Phosphorus, 1)
            .addMaterial(Materials_OLD.Chlorine, 5)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadToxicAir() {
        return new MaterialBuilder().setName("ToxicAir")
            .setDefaultLocalName("Toxic Air")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x004d5f3e)
            .addCell()
            .addGas()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadToxicSlurry() {
        return new MaterialBuilder().setName("ToxicSlurry")
            .setDefaultLocalName("Toxic Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00425f1f)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadDestabilizationSlurry() {
        return new MaterialBuilder().setName("DestabilizationSlurry")
            .setDefaultLocalName("Destabilization Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00284309)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadAgitatingSlurry() {
        return new MaterialBuilder().setName("AgitatingSlurry")
            .setDefaultLocalName("Agitating Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0081a558)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadUltraContaminatedGas() {
        return new MaterialBuilder().setName("UltraContaminatedGas")
            .setDefaultLocalName("Ultra Contaminated Gas")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00343434)
            .addCell()
            .addGas()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadStagnantWasteWater() {
        return new MaterialBuilder().setName("StagnantWasteWater")
            .setDefaultLocalName("Stagnant Waste Water")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x00206332)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadActivatedWasteWater() {
        return new MaterialBuilder().setName("ActivatedWasteWater")
            .setDefaultLocalName("Activated Waste Water")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x000f37a4)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadChlorosulfonicAcid() {
        return new MaterialBuilder().setName("ChlorosulfonicAcid")
            .setDefaultLocalName("Chlorosulfonic Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0xabff00)
            .setMeltingPoint(193)
            .addCell()
            .addFluid()
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Sulfur, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addMaterial(Materials_OLD.Chlorine, 1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPoisonousSlurry() {
        return new MaterialBuilder().setName("PoisonousSlurry")
            .setDefaultLocalName("Poisonous Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x237745)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static void loadMagicMaterials() {
        Materials_OLD.ComplexityCatalyst = loadComplexityCatalyst();
        Materials_OLD.EntropicCatalyst = loadEntropicCatalyst();
        Materials_OLD.SoulInfusedMedium = loadSoulInfusedMedium();
    }

    private static Materials_OLD loadComplexityCatalyst() {
        return new MaterialBuilder().setName("ComplexityCatalyst")
            .setDefaultLocalName("Complexity Catalyst")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x008b93a9)
            .addFluid()
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadEntropicCatalyst() {
        return new MaterialBuilder().setName("EntropicCatalyst")
            .setDefaultLocalName("Entropic Catalyst")
            .setIconSet(TextureSet.SET_DULL)
            .setARGB(0x00a99da5)
            .addFluid()
            .addCell()
            .constructMaterial();
    }

    private static Materials_OLD loadSoulInfusedMedium() {
        return new MaterialBuilder().setName("SoulInfusedMedium")
            .setDefaultLocalName("Soul Infused Medium")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xff32cd32)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static void loadBotaniaMaterials() {
        Materials_OLD.Manasteel = loadManasteel();
        Materials_OLD.Terrasteel = loadTerrasteel();
        Materials_OLD.ElvenElementium = loadElvenElementium();
        Materials_OLD.Livingrock = loadLivingrock();
        Materials_OLD.GaiaSpirit = loadGaiaSpirit();
        Materials_OLD.Livingwood = loadLivingwood();
        Materials_OLD.Dreamwood = loadDreamwood();
        Materials_OLD.ManaDiamond = loadManaDiamond();
        Materials_OLD.Dragonstone = loadDragonstone();
    }

    private static Materials_OLD loadManasteel() {
        return new MaterialBuilder().setName("Manasteel")
            .setDefaultLocalName("Manasteel")
            .setChemicalFormula("Ms")
            .setIconSet(TextureSet.SET_MANASTEEL)
            .setARGB(0xff46aae6)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setTool(5_120, 4, 8.0f)
            .setMeltingPoint(1_500)
            .setBlastFurnaceTemp(1_500)
            .setBlastFurnaceRequired(true)
            .removeOrePrefix(OrePrefixes.ingot) // Botania:manaResource:0
            .removeOrePrefix(OrePrefixes.nugget) // Botania:manaResource:17
            .constructMaterial();
    }

    private static Materials_OLD loadTerrasteel() {
        return new MaterialBuilder().setName("Terrasteel")
            .setDefaultLocalName("Terrasteel")
            .setChemicalFormula("Tr")
            .setIconSet(TextureSet.SET_MANASTEEL)
            .setARGB(0xff46c800)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setTool(10_240, 5, 32.0f)
            .setMeltingPoint(5_400)
            .setBlastFurnaceTemp(5_400)
            .setBlastFurnaceRequired(true)
            .removeOrePrefix(OrePrefixes.ingot) // Botania:manaResource:4
            .removeOrePrefix(OrePrefixes.nugget) // Botania:manaResource:18
            .constructMaterial();
    }

    private static Materials_OLD loadElvenElementium() {
        return new MaterialBuilder().setName("ElvenElementium")
            .setDefaultLocalName("Elven Elementium")
            .setChemicalFormula("Ef")
            .setIconSet(TextureSet.SET_MANASTEEL)
            .setARGB(0xffff2df0)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setTool(32_768, 7, 20.0f)
            .setMeltingPoint(7_200)
            .setBlastFurnaceTemp(7_200)
            .setBlastFurnaceRequired(true)
            .removeOrePrefix(OrePrefixes.ingot) // Botania:manaResource:7
            .removeOrePrefix(OrePrefixes.nugget) // Botania:manaResource:19
            .constructMaterial();
    }

    private static Materials_OLD loadLivingrock() {
        return new MaterialBuilder().setName("Livingrock")
            .setDefaultLocalName("Livingrock")
            .setChemicalFormula("Lv")
            .setIconSet(TextureSet.SET_LIVINGROCK)
            .addDustItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addOrePrefix(OrePrefixes.plate)
            .addOrePrefix(OrePrefixes.rod) // this is not working
            .removeOrePrefix(OrePrefixes.rotor)
            .constructMaterial();
    }

    private static Materials_OLD loadGaiaSpirit() {
        return new MaterialBuilder().setName("GaiaSpirit")
            .setDefaultLocalName("Gaia Spirit")
            .setChemicalFormula("Gs")
            .setIconSet(TextureSet.SET_GAIA_SPIRIT)
            .setTool(850_000, 12, 32.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.SOFT)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.ingot) // Botania::manaResource:14
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadLivingwood() {
        return new MaterialBuilder().setName("Livingwood")
            .setDefaultLocalName("Livingwood")
            .setChemicalFormula("Lw")
            .setIconSet(TextureSet.SET_LIVINGWOOD)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.WOOD)
            .removeOrePrefix(OrePrefixes.ingot)
            .removeOrePrefix(OrePrefixes.nugget)
            .constructMaterial();
    }

    private static Materials_OLD loadDreamwood() {
        return new MaterialBuilder().setName("Dreamwood")
            .setDefaultLocalName("Dreamwood")
            .setChemicalFormula("Dw")
            .setIconSet(TextureSet.SET_DREAMWOOD)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.WOOD)
            .removeOrePrefix(OrePrefixes.ingot)
            .removeOrePrefix(OrePrefixes.nugget)
            .constructMaterial();
    }

    private static Materials_OLD loadManaDiamond() {
        return new MaterialBuilder().setName("ManaDiamond")
            .setDefaultLocalName("Mana Diamond")
            .setChemicalFormula("Ma₄C")
            .setIconSet(TextureSet.SET_MANA_DIAMOND)
            .setTool(2_560, 8, 16.0f)
            .addDustItems()
            .addGemItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.gem) // Botania:manaResource:2
            .constructMaterial();
    }

    private static Materials_OLD loadDragonstone() {
        return new MaterialBuilder().setName("BotaniaDragonstone")
            .setDefaultLocalName("Dragonstone")
            .setChemicalFormula("Dg")
            .setIconSet(TextureSet.SET_DRAGONSTONE)
            .setTool(3_840, 12, 24.0f)
            .addDustItems()
            .addGemItems()
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.gem) // Botania:manaResource:9
            .constructMaterial();
    }

    private static void loadKevlarLine() {
        Materials_OLD.DiphenylmethaneDiisocyanate = loadDiphenylmethaneDiisocyanate();
        Materials_OLD.DiaminodiphenylmethanMixture = loadDiaminodiphenylmethanMixture();
        Materials_OLD.DiphenylmethaneDiisocyanateMixture = loadDiphenylmethaneDiisocyanateMixture();
        Materials_OLD.Butyraldehyde = loadButyraldehyde();
        Materials_OLD.Isobutyraldehyde = loadIsobutyraldehyde();
        Materials_OLD.NickelTetracarbonyl = loadNickelTetracarbonyl();
        Materials_OLD.KevlarCatalyst = loadKevlarCatalyst();
        Materials_OLD.EthyleneOxide = loadEthyleneOxide();
        Materials_OLD.SiliconOil = loadSiliconOil();
        Materials_OLD.Ethyleneglycol = loadEthyleneglycol();
        Materials_OLD.Acetaldehyde = loadAcetaldehyde();
        Materials_OLD.Pentaerythritol = loadPentaerythritol();
        Materials_OLD.PolyurethaneResin = loadPolyurethaneResin();
        Materials_OLD.NMethylIIPyrrolidone = loadNMethylIIPyrrolidone();
        Materials_OLD.TerephthaloylChloride = loadTerephthaloylChloride();
        Materials_OLD.Acetylene = loadAcetylene();
        Materials_OLD.IVNitroaniline = loadIVNitroaniline();
        Materials_OLD.ParaPhenylenediamine = loadParaPhenylenediamine();
        Materials_OLD.Methylamine = loadMethylamine();
        Materials_OLD.Trimethylamine = loadTrimethylamine();
        Materials_OLD.GammaButyrolactone = loadGammaButyrolactone();
        Materials_OLD.CalciumCarbide = loadCalciumCarbide();
        Materials_OLD.LiquidCrystalKevlar = loadLiquidCrystalKevlar();
        Materials_OLD.IIButinIIVdiol = loadIIButinIIVdiol();
        Materials_OLD.NickelAluminide = loadNickelAluminide();
        Materials_OLD.RaneyNickelActivated = loadRaneyNickelActivated();
        Materials_OLD.BismuthIIIOxide = loadBismuthIIIOxide();
        Materials_OLD.ThionylChloride = loadThionylChloride();
        Materials_OLD.SulfurDichloride = loadSulfurDichloride();
        Materials_OLD.DimethylTerephthalate = loadDimethylTerephthalate();
        Materials_OLD.Kevlar = loadKevlar();
        Materials_OLD.TerephthalicAcid = loadTerephthalicAcid();
        Materials_OLD.IIIDimethylbenzene = loadIIIDimethylbenzene();
        Materials_OLD.IVDimethylbenzene = loadIVDimethylbenzene();
        Materials_OLD.CobaltIINaphthenate = loadCobaltIINaphthenate();
        Materials_OLD.NaphthenicAcid = loadNaphthenicAcid();
        Materials_OLD.CobaltIIHydroxide = loadCobaltIIHydroxide();
        Materials_OLD.CobaltIIAcetate = loadCobaltIIAcetate();
        Materials_OLD.CobaltIINitrate = loadCobaltIINitrate();
        Materials_OLD.OrganorhodiumCatalyst = loadOrganorhodiumCatalyst();
        Materials_OLD.SodiumBorohydride = loadSodiumBorohydride();
        Materials_OLD.RhodiumChloride = loadRhodiumChloride();
        Materials_OLD.Triphenylphosphene = loadTriphenylphosphene();
        Materials_OLD.PhosphorusTrichloride = loadPhosphorusTrichloride();
        Materials_OLD.SodiumHydride = loadSodiumHydride();
        Materials_OLD.TrimethylBorate = loadTrimethylBorate();
        Materials_OLD.SodiumMethoxide = loadSodiumMethoxide();
    }

    private static Materials_OLD loadDiphenylmethaneDiisocyanate() {
        return new MaterialBuilder().setName("DiphenylmethaneDiisocyanate")
            .setDefaultLocalName("4,4'-Diphenylmethane Diisocyanate")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffe632)
            .addDustItems()
            .setMeltingPoint(310)
            .addMaterial(Materials_OLD.Carbon, 15)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadDiaminodiphenylmethanMixture() {
        return new MaterialBuilder().setName("DiaminodiphenylmethanMixture")
            .setDefaultLocalName("Diaminodiphenylmethane Mixture")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00fff37a)
            .addCell()
            .addFluid()
            .setMeltingPoint(365)
            .addMaterial(Materials_OLD.Carbon, 13)
            .addMaterial(Materials_OLD.Hydrogen, 14)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadDiphenylmethaneDiisocyanateMixture() {
        return new MaterialBuilder().setName("DiphenylmethaneDiisocyanateMixture")
            .setDefaultLocalName("Diphenylmethane Diisocyanate Mixture")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffe632)
            .addCell()
            .addFluid()
            .setMeltingPoint(310)
            .addMaterial(Materials_OLD.Carbon, 15)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadButyraldehyde() {
        return new MaterialBuilder().setName("Butyraldehyde")
            .setDefaultLocalName("Butyraldehyde")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(176)
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadIsobutyraldehyde() {
        return new MaterialBuilder().setName("Isobutyraldehyde")
            .setDefaultLocalName("Isobutyraldehyde")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(208)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadNickelTetracarbonyl() {
        return new MaterialBuilder().setName("NickelTetracarbonyl")
            .setDefaultLocalName("Nickel Tetracarbonyl")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(256)
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadKevlarCatalyst() {
        return new MaterialBuilder().setName("PolyurethaneCatalystADust")
            .setDefaultLocalName("Polyurethane Catalyst A")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .addDustItems()
            .setMeltingPoint(300)
            .constructMaterial();
    }

    private static Materials_OLD loadEthyleneOxide() {
        return new MaterialBuilder().setName("EthyleneOxide")
            .setDefaultLocalName("Ethylene Oxide")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addGas()
            .setMeltingPoint(160)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadSiliconOil() {
        return new MaterialBuilder().setName("SiliconOil")
            .setDefaultLocalName("Silicon Oil")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(473)
            .constructMaterial();
    }

    private static Materials_OLD loadEthyleneglycol() {
        return new MaterialBuilder().setName("EthyleneGlycol")
            .setDefaultLocalName("Ethylene Glycol")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(260)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadAcetaldehyde() {
        return new MaterialBuilder().setName("Acetaldehyde")
            .setDefaultLocalName("Acetaldehyde")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addGas()
            .setMeltingPoint(150)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPentaerythritol() {
        return new MaterialBuilder().setName("Pentaerythritol")
            .setDefaultLocalName("Pentaerythritol")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeWhite)
            .addDustItems()
            .setMeltingPoint(533)
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 12)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadPolyurethaneResin() {
        return new MaterialBuilder().setName("PolyurethaneResin")
            .setDefaultLocalName("Polyurethane Resin")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00e6e678)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadNMethylIIPyrrolidone() {
        return new MaterialBuilder().setName("NMethylpyrolidone")
            .setDefaultLocalName("N-Methyl-2-pyrrolidone")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(249)
            .addMaterial(Materials_OLD.Carbon, 5)
            .addMaterial(Materials_OLD.Hydrogen, 9)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadTerephthaloylChloride() {
        return new MaterialBuilder().setName("TerephthaloylChloride")
            .setDefaultLocalName("Terephthaloyl Chloride")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0000ff0c)
            .addDustItems()
            .setMeltingPoint(355)
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .addMaterial(Materials_OLD.Chlorine, 2)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadAcetylene() {
        // TODO: Add to JUPITER Athmosphere and Enceladus and to moon of Saturn
        return new MaterialBuilder().setName("Acetylene")
            .setDefaultLocalName("Acetylene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addGas()
            .setMeltingPoint(192)
            .addMaterial(Materials_OLD.Carbon, 2)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadIVNitroaniline() {
        return new MaterialBuilder().setName("4Nitroaniline")
            .setDefaultLocalName("4-Nitroaniline")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x00ff8733)
            .addCell()
            .addFluid()
            .setMeltingPoint(420)
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadParaPhenylenediamine() {
        return new MaterialBuilder().setName("pPhenylenediamine")
            .setDefaultLocalName("para-Phenylenediamine")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00fbec5d)
            .addDustItems()
            .setMeltingPoint(293)
            .addMaterial(Materials_OLD.Carbon, 6)
            .addMaterial(Materials_OLD.Hydrogen, 8)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadMethylamine() {
        return new MaterialBuilder().setName("Methylamine")
            .setDefaultLocalName("Methylamine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00414469)
            .addCell()
            .addGas()
            .setMeltingPoint(180)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 5)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadTrimethylamine() {
        return new MaterialBuilder().setName("Trimethylamine")
            .setDefaultLocalName("Trimethylamine")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00694469)
            .addCell()
            .addGas()
            .setMeltingPoint(156)
            .addElectrolyzerRecipe()
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 9)
            .addMaterial(Materials_OLD.Nitrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadGammaButyrolactone() {
        return new MaterialBuilder().setName("GammaButyrolactone")
            .setDefaultLocalName("gamma-Butyrolactone")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00ffff97)
            .addCell()
            .addFluid()
            .setMeltingPoint(229)
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadCalciumCarbide() {
        return new MaterialBuilder().setName("CacliumCarbide")
            .setDefaultLocalName("Calcium Carbide")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00ebebeb)
            .addDustItems()
            .setMeltingPoint(2430)
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Carbon, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadLiquidCrystalKevlar() {
        return new MaterialBuilder().setName("LiquidCrystalKevlar")
            .setDefaultLocalName("Liquid Crystal Kevlar")
            .setChemicalFormula("[-CO-C₆H₄-CO-NH-C₆H₄-NH-]n")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00f0f078)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadIIButinIIVdiol() {
        return new MaterialBuilder().setName("2Butin14diol")
            .setDefaultLocalName("2-Butin-1,4-diol")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00f7f7b4)
            .addDustItems()
            .setMeltingPoint(331)
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadNickelAluminide() {
        return new MaterialBuilder().setName("NickelAluminide")
            .setDefaultLocalName("Nickel Aluminide")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00e6e6e6)
            .addDustItems()
            .addMetalItems()
            .setMeltingPoint(1_668)
            .setBlastFurnaceTemp(1_668)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Aluminium, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadRaneyNickelActivated() {
        return new MaterialBuilder().setName("RaneyNickelActivated")
            .setDefaultLocalName("Raney Nickel")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00e6e6e6)
            .addDustItems()
            .setMeltingPoint(1_955)
            .addMaterial(Materials_OLD.Nickel, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadBismuthIIIOxide() {
        return new MaterialBuilder().setName("BismuthIIIOxide")
            .setDefaultLocalName("Bismuth Oxide")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x00323232)
            .addDustItems()
            .setMeltingPoint(1090)
            .addMaterial(Materials_OLD.Bismuth, 2)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadThionylChloride() {
        // SOCl2
        return new MaterialBuilder().setName("ThionylChloride")
            .setDefaultLocalName("Thionyl Chloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadSulfurDichloride() {
        // SCl2
        return new MaterialBuilder().setName("SulfurDichloride")
            .setDefaultLocalName("Sulfur Dichloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00c80000)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadDimethylTerephthalate() {
        return new MaterialBuilder().setName("DimethylTerephthalate")
            .setDefaultLocalName("Dimethyl Terephthalate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(415)
            .addMaterial(Materials_OLD.Carbon, 10)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadKevlar() {
        return new MaterialBuilder().setName("Kevlar")
            .setDefaultLocalName("Kevlar")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeYellow)
            .setARGB(0x00f0f078)
            .addDustItems()
            .addMetalItems()
            .addGearItems()
            .addSubTag(SubTag.FLAMMABLE)
            .addSubTag(SubTag.MULTI_PLATE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.STRETCHY)
            .constructMaterial();
    }

    private static Materials_OLD loadTerephthalicAcid() {
        return new MaterialBuilder().setName("TerephthalicAcid")
            .setDefaultLocalName("Terephthalic Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(480)
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadIIIDimethylbenzene() {
        return new MaterialBuilder().setName("1,3Dimethylbenzene")
            .setDefaultLocalName("1,3-Dimethylbenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x0070924a)
            .addCell()
            .addFluid()
            .setMeltingPoint(225)
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadIVDimethylbenzene() {
        return new MaterialBuilder().setName("1,4Dimethylbenzene")
            .setDefaultLocalName("1,4-Dimethylbenzene")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x007a8854)
            .addCell()
            .addFluid()
            .setMeltingPoint(286)
            .addMaterial(Materials_OLD.Carbon, 8)
            .addMaterial(Materials_OLD.Hydrogen, 10)
            .addElectrolyzerRecipe()
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltIINaphthenate() {
        return new MaterialBuilder().setName("Cobalt(II)Naphthenate")
            .setDefaultLocalName("Cobalt II Naphthenate")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x008f5f27)
            .addDustItems()
            .setMeltingPoint(413)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Carbon, 22)
            .addMaterial(Materials_OLD.Hydrogen, 14)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadNaphthenicAcid() {
        return new MaterialBuilder().setName("NaphthenicAcid")
            .setDefaultLocalName("Naphthenic Acid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .setFuel(MaterialBuilder.FuelType.SemiFluid, 80)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltIIHydroxide() {
        return new MaterialBuilder().setName("CobaltIIHydroxide")
            .setDefaultLocalName("Cobalt II Hydroxide")
            .setChemicalFormula("Co(OH)₂")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00e58cef)
            .addDustItems()
            .setMeltingPoint(441)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Hydrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltIIAcetate() {
        return new MaterialBuilder().setName("Cobalt(II)Acetate")
            .setDefaultLocalName("Cobalt II Acetate")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00dba2e5)
            .addDustItems()
            .setMeltingPoint(413)
            .addMaterial(Materials_OLD.Carbon, 4)
            .addMaterial(Materials_OLD.Hydrogen, 6)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Oxygen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadCobaltIINitrate() {
        return new MaterialBuilder().setName("Cobalt(II)Nitrate")
            .setDefaultLocalName("Cobalt II Nitrate")
            .setChemicalFormula("Co(NO₃)₂")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00aa0000)
            .addDustItems()
            .setMeltingPoint(373)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.Nitrogen, 2)
            .addMaterial(Materials_OLD.Oxygen, 6)
            .constructMaterial();
    }

    private static Materials_OLD loadOrganorhodiumCatalyst() {
        return new MaterialBuilder().setName("OrganorhodiumCatalyst")
            .setDefaultLocalName("Organorhodium Catalyst")
            .setChemicalFormula("RhHCO(P(C₆H₅)₃)₃")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00aa0000)
            .addDustItems()
            .setMeltingPoint(373)
            .addMaterial(Materials_OLD.Cobalt, 1)
            .addMaterial(Materials_OLD.NitricAcid, 2)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumBorohydride() {
        return new MaterialBuilder().setName("SodiumBorohydride")
            .setDefaultLocalName("Sodium Borohydride")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeWhite)
            .addDustItems()
            .setMeltingPoint(673)
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Boron, 1)
            .addMaterial(Materials_OLD.Hydrogen, 4)
            .constructMaterial();
    }

    private static Materials_OLD loadRhodiumChloride() {
        return new MaterialBuilder().setName("RhodiumChloride")
            .setDefaultLocalName("Rhodium Chloride")
            .setChemicalFormula("RhCl₃")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00800000)
            .addDustItems()
            .setMeltingPoint(723)
            .constructMaterial();
    }

    private static Materials_OLD loadTriphenylphosphene() {
        return new MaterialBuilder().setName("Triphenylphosphene")
            .setDefaultLocalName("Triphenylphosphine")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeWhite)
            .addDustItems()
            .setMeltingPoint(353)
            .addMaterial(Materials_OLD.Carbon, 18)
            .addMaterial(Materials_OLD.Hydrogen, 15)
            .addMaterial(Materials_OLD.Phosphorus, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadPhosphorusTrichloride() {
        return new MaterialBuilder().setName("PhosphorusTrichloride")
            .setDefaultLocalName("Phosphorus Trichloride")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .addDustItems()
            .setMeltingPoint(179)
            .addMaterial(Materials_OLD.Phosphorus, 1)
            .addMaterial(Materials_OLD.Chlorine, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumHydride() {
        return new MaterialBuilder().setName("SodiumHydride")
            .setDefaultLocalName("Sodium Hydride")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeLightGray)
            .setARGB(0x00c0c0c0)
            .addDustItems()
            .setMeltingPoint(911)
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadTrimethylBorate() {
        return new MaterialBuilder().setName("TrimethylBorate")
            .setDefaultLocalName("Trimethyl Borate")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeWhite)
            .addCell()
            .addFluid()
            .setMeltingPoint(239)
            .addMaterial(Materials_OLD.Carbon, 3)
            .addMaterial(Materials_OLD.Hydrogen, 9)
            .addMaterial(Materials_OLD.Boron, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .constructMaterial();
    }

    private static Materials_OLD loadSodiumMethoxide() {
        return new MaterialBuilder().setName("SodiumMethoxide")
            .setDefaultLocalName("Sodium Methoxide")
            .setIconSet(TextureSet.SET_POWDER)
            .setColor(Dyes.dyeWhite)
            .addDustItems()
            .setMeltingPoint(400)
            .addMaterial(Materials_OLD.Carbon, 1)
            .addMaterial(Materials_OLD.Hydrogen, 3)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Sodium, 1)
            .constructMaterial();
    }

    private static void loadAluminiumOres() {
        Materials_OLD.BauxiteSlurry = loadBauxiteSlurry();
        Materials_OLD.HeatedBauxiteSlurry = loadHeatedBauxiteSlurry();
        Materials_OLD.SluiceJuice = loadSluiceJuice();
        Materials_OLD.SluiceSand = loadSluiceSand();
        Materials_OLD.BauxiteSlag = loadBauxiteSlag();
        Materials_OLD.IlmeniteSlag = loadIlmeniteSlag();
        Materials_OLD.GreenSapphireJuice = loadGreenSapphireJuice();
        Materials_OLD.SapphireJuice = loadSapphireJuice();
        Materials_OLD.RubyJuice = loadRubyJuice();
    }

    private static Materials_OLD loadBauxiteSlurry() {
        return new MaterialBuilder().setName("BauxiteSlurry")
            .setDefaultLocalName("Bauxite Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x002543a8)
            .addCell()
            .addFluid()
            .setMeltingPoint(295)
            .constructMaterial();
    }

    private static Materials_OLD loadHeatedBauxiteSlurry() {
        return new MaterialBuilder().setName("HeadedBauxiteSlurry")
            .setDefaultLocalName("Heated Bauxite Slurry")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00375cd4)
            .addCell()
            .addFluid()
            .setMeltingPoint(295)
            .constructMaterial();
    }

    private static Materials_OLD loadSluiceJuice() {
        return new MaterialBuilder().setName("SluiceJuice")
            .setDefaultLocalName("Sluice Juice")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGray)
            .setARGB(0x005c3c24)
            .addCell()
            .addFluid()
            .setMeltingPoint(295)
            .constructMaterial();
    }

    private static Materials_OLD loadSluiceSand() {
        return new MaterialBuilder().setName("SluiceSand")
            .setDefaultLocalName("Sluice Sand")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00a5a578)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadBauxiteSlag() {
        return new MaterialBuilder().setName("BauxiteSlag")
            .setDefaultLocalName("Bauxite Slag")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeRed)
            .setARGB(0x006e1f1f)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadIlmeniteSlag() {
        return new MaterialBuilder().setName("IlmeniteSlag")
            .setDefaultLocalName("Ilmenite Slag")
            .setIconSet(TextureSet.SET_FINE)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00a32626)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadGreenSapphireJuice() {
        return new MaterialBuilder().setName("GreenSapphireJuice")
            .setDefaultLocalName("Green Sapphire Juice")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x0064c882)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadSapphireJuice() {
        return new MaterialBuilder().setName("SapphireJuice")
            .setDefaultLocalName("Sapphire Juice")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x006464c8)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static Materials_OLD loadRubyJuice() {
        return new MaterialBuilder().setName("RubyJuice")
            .setDefaultLocalName("Ruby Juice")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeRed)
            .setARGB(0x00ff6464)
            .addCell()
            .addFluid()
            .constructMaterial();
    }

    private static void loadUEVPlusMaterials() {
        Materials_OLD.DTCC = loadDTCC();
        Materials_OLD.DTPC = loadDTPC();
        Materials_OLD.DTRC = loadDTRC();
        Materials_OLD.DTEC = loadDTEC();
        Materials_OLD.DTSC = loadDTSC();
        Materials_OLD.ExcitedDTCC = loadExcitedDTCC();
        Materials_OLD.ExcitedDTPC = loadExcitedDTPC();
        Materials_OLD.ExcitedDTRC = loadExcitedDTRC();
        Materials_OLD.ExcitedDTEC = loadExcitedDTEC();
        Materials_OLD.ExcitedDTSC = loadExcitedDTSC();
        Materials_OLD.DTR = loadDTR();
        Materials_OLD.SpaceTime = loadSpaceTime();
        Materials_OLD.TranscendentMetal = loadTranscendentMetal();
        Materials_OLD.MHDCSM = loadMHDCSM();
        Materials_OLD.RawStarMatter = loadRawStarMatter();
        Materials_OLD.WhiteDwarfMatter = loadWhiteDwarfMatter();
        Materials_OLD.BlackDwarfMatter = loadBlackDwarfMatter();
        Materials_OLD.Time = loadTime();
        Materials_OLD.Space = loadSpace();
        Materials_OLD.Universium = loadUniversium();
        Materials_OLD.Eternity = loadEternity();
        Materials_OLD.PrimordialMatter = loadPrimordialMatter();
        Materials_OLD.MagMatter = loadMagMatter();
        Materials_OLD.QuarkGluonPlasma = loadQuarkGluonPlasma();
        Materials_OLD.PhononMedium = loadPhononMedium();
        Materials_OLD.PhononCrystalSolution = loadPhononCrystalSolution();
        Materials_OLD.SixPhasedCopper = loadSixPhasedCopper();
        Materials_OLD.Mellion = loadMellion();
        Materials_OLD.Creon = loadCreon();
        Materials_OLD.GravitonShard = loadGravitonShard();
        Materials_OLD.DimensionallyShiftedSuperfluid = loadDimensionallyShiftedSuperfluid();
        Materials_OLD.MoltenProtoHalkoniteBase = loadMoltenProtoHalkoniteBase();
        Materials_OLD.HotProtoHalkonite = loadHotProtoHalkonite();
        Materials_OLD.ProtoHalkonite = loadProtoHalkonite();
        Materials_OLD.MoltenExoHalkoniteBase = loadMoltenExoHalkoniteBase();
        Materials_OLD.HotExoHalkonite = loadHotExoHalkonite();
        Materials_OLD.ExoHalkonite = loadExoHalkonite();
        Materials_OLD.Antimatter = loadAntimatter();
        Materials_OLD.Protomatter = loadProtomatter();
        Materials_OLD.StargateCrystalSlurry = loadStargateCrystalSlurry();
        Materials_OLD.LumipodExtract = loadLumipodExtract();
        Materials_OLD.InactiveCosmicSolder = loadInactiveCosmicSolder();
        Materials_OLD.BoundlessCosmicSolder = loadBoundlessCosmicSolder();
        Materials_OLD.BiocatalyzedPropulsionFluid = loadBiocatalyzedPropulsionFluid();
        Materials_OLD.Shijima = loadShijima();
        Materials_OLD.Churitsu = loadChuritsu();
        Materials_OLD.ComputationBase = loadComputationBase();
    }

    private static Materials_OLD loadDTCC() {
        return new MaterialBuilder().setName("DimensionallyTranscendentCrudeCatalyst")
            .setDefaultLocalName("Dimensionally Transcendent Crude Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x010a1414)
            .addCell()
            .addFluid()
            .setMeltingPoint(25_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadDTPC() {
        return new MaterialBuilder().setName("DimensionallyTranscendentProsaicCatalyst")
            .setDefaultLocalName("Dimensionally Transcendent Prosaic Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x010a1414)
            .addCell()
            .addFluid()
            .setMeltingPoint(50_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadDTRC() {
        return new MaterialBuilder().setName("DimensionallyTranscendentResplendentCatalyst")
            .setDefaultLocalName("Dimensionally Transcendent Resplendent Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x010a1414)
            .addCell()
            .addFluid()
            .setMeltingPoint(75_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadDTEC() {
        return new MaterialBuilder().setName("DimensionallyTranscendentExoticCatalyst")
            .setDefaultLocalName("Dimensionally Transcendent Exotic Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x010a1414)
            .addCell()
            .addFluid()
            .setMeltingPoint(100_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadDTSC() {
        return new MaterialBuilder().setName("DimensionallyTranscendentStellarCatalyst")
            .setDefaultLocalName("Dimensionally Transcendent Stellar Catalyst")
            .setChemicalFormula("Stellar", true)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x010a1414)
            .addCell()
            .addFluid()
            .setMeltingPoint(100_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadExcitedDTCC() {
        return new MaterialBuilder().setName("ExcitedDTCC")
            .setDefaultLocalName("Excited Dimensionally Transcendent Crude Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x010a1414)
            .addCell()
            .setMeltingPoint(500_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadExcitedDTPC() {
        return new MaterialBuilder().setName("ExcitedDTPC")
            .setDefaultLocalName("Excited Dimensionally Transcendent Prosaic Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeGreen)
            .setARGB(0x01233b29)
            .addCell()
            .setMeltingPoint(500_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadExcitedDTRC() {
        return new MaterialBuilder().setName("ExcitedDTRC")
            .setDefaultLocalName("Excited Dimensionally Transcendent Resplendent Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeLime)
            .setARGB(0x01261438)
            .addCell()
            .setMeltingPoint(500_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadExcitedDTEC() {
        return new MaterialBuilder().setName("ExcitedDTEC")
            .setDefaultLocalName("Excited Dimensionally Transcendent Exotic Catalyst")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeMagenta)
            .setARGB(0x01f0f029)
            .addCell()
            .setMeltingPoint(500_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadExcitedDTSC() {
        return new MaterialBuilder().setName("ExcitedDTSC")
            .setDefaultLocalName("Excited Dimensionally Transcendent Stellar Catalyst")
            .setChemicalFormula("[-Stellar-Stellar-]", true)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeOrange)
            .setARGB(0x017e4b0b)
            .addCell()
            .setMeltingPoint(500_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadDTR() {
        return new MaterialBuilder().setName("DimensionallyTranscendentResidue")
            .setDefaultLocalName("Dimensionally Transcendent Residue")
            .setChemicalFormula(CustomGlyphs.SPARKLES + "-" + CustomGlyphs.EMPTY_SET)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x01000000)
            .addCell()
            .setMeltingPoint(25)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadSpaceTime() {
        return new MaterialBuilder().setName("SpaceTime")
            .setDefaultLocalName("SpaceTime")
            .setChemicalFormula("\u03A6")
            .setFlavorText("Reality itself distilled into physical form")
            .setIconSet(TextureSet.SET_SPACETIME)
            .setARGB(0x00ffffff)
            .setMeltingPoint(0)
            .setTool(10_485_760, 25, 320.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadTranscendentMetal() {
        return new MaterialBuilder().setName("TranscendentMetal")
            .setDefaultLocalName("Transcendent Metal")
            .setChemicalFormula("TsЖ")
            .setFlavorText("Spatially incomprehensible")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyeBlack)
            .setARGB(0x323232)
            .setMeltingPoint(11_700)
            .setTool(7864320, 22, 290.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(3_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setDensity(1_000, 1_000)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.BLACK_HOLE)
            .addSubTag(SubTag.TRANSPARENT)
            .addOrePrefix(OrePrefixes.ingotHot)
            .addOrePrefix(OrePrefixes.nanite)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UHV);
    }

    private static Materials_OLD loadMHDCSM() {
        return new MaterialBuilder().setName("MagnetohydrodynamicallyConstrainedStarMatter")
            .setDefaultLocalName("Magnetohydrodynamically Constrained Star Matter")
            .setChemicalFormula(
                "⇲" + CustomGlyphs.ARROW_CORNER_SOUTH_EAST
                    + CustomGlyphs.GALAXY
                    + CustomGlyphs.ARROW_CORNER_NORTH_WEST
                    + "⇱")
            .setFlavorText("Stabilised core of a dead star")
            .setIconSet(TextureSet.SET_MHDCSM)
            .setARGB(0x00ffffff)
            .setMeltingPoint(0)
            .setTool(10485760, 25, 320.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addSubTag(SubTag.TRANSPARENT)
            .addOrePrefix(OrePrefixes.gear)
            .addOrePrefix(OrePrefixes.ingot)
            .addOrePrefix(OrePrefixes.toolHeadHammer)
            .addOrePrefix(OrePrefixes.frame)
            .addOrePrefix(OrePrefixes.frameGt)
            .removeOrePrefix(OrePrefixes.cell)
            .removeOrePrefix(OrePrefixes.turbineBlade)
            .removeOrePrefix(OrePrefixes.dust)
            .removeOrePrefix(OrePrefixes.dustSmall)
            .removeOrePrefix(OrePrefixes.dustTiny)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UIV);
    }

    private static Materials_OLD loadRawStarMatter() {
        return new MaterialBuilder().setName("RawStarMatter")
            .setDefaultLocalName("Condensed Raw Stellar Plasma Mixture")
            .setChemicalFormula(CustomGlyphs.GALAXY)
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePurple)
            .setARGB(0xff6401ff)
            .addCell()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadWhiteDwarfMatter() {
        return new MaterialBuilder().setName("WhiteDwarfMatter")
            .setDefaultLocalName("White Dwarf Matter")
            .setChemicalFormula("∅")
            .setIconSet(TextureSet.SET_WHITE_DWARF_MATTER)
            .setColor(Dyes.dyePurple)
            .setMeltingPoint(0)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addFluid()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UXV);
    }

    private static Materials_OLD loadBlackDwarfMatter() {
        return new MaterialBuilder().setName("BlackDwarfMatter")
            .setDefaultLocalName("Black Dwarf Matter")
            .setChemicalFormula(">>∅<<")
            .setIconSet(TextureSet.SET_METALLIC)
            .setColor(Dyes.dyePurple)
            .setARGB(0xff000000)
            .setMeltingPoint(0)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addFluid()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSPARENT)
            .addOrePrefix(OrePrefixes.nanite)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UXV);
    }

    private static Materials_OLD loadTime() {
        return new MaterialBuilder().setName("temporalFluid")
            .setDefaultLocalName("Tachyon Rich Temporal Fluid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePurple)
            .setARGB(0xff6401ff)
            .addCell()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setLiquidTemperature(0);
    }

    private static Materials_OLD loadSpace() {
        return new MaterialBuilder().setName("spatialFluid")
            .setDefaultLocalName("Spatially Enlarged Fluid")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyePurple)
            .setARGB(0xff6401ff)
            .addCell()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setLiquidTemperature(0);
    }

    private static Materials_OLD loadUniversium() {
        return new MaterialBuilder().setName("Universium")
            .setDefaultLocalName("Universium")
            .setChemicalFormula("\u03A3" + EnumChatFormatting.OBFUSCATED + "X")
            .setFlavorText("A tear into the space beyond space")
            .setIconSet(TextureSet.SET_UNIVERSIUM)
            .setARGB(0xff263145)
            .setMeltingPoint(0)
            .setTool(10_485_760, 30, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.TRANSPARENT)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.frame)
            .removeOrePrefix(OrePrefixes.frameGt) // disabled but shows up and is used in game
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UXV);
    }

    private static Materials_OLD loadEternity() {
        return new MaterialBuilder().setName("Eternity")
            .setDefaultLocalName("Eternity")
            .setChemicalFormula("En⦼")
            .setIconSet(TextureSet.SET_ETERNITY)
            .setTool(20_971_520, 26, 1.0f)
            .setMeltingPoint(0)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(14_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UMV);
    }

    private static Materials_OLD loadPrimordialMatter() {
        return new MaterialBuilder().setName("PrimordialMatter")
            .setDefaultLocalName("Liquid Primordial Matter")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBlue)
            .setARGB(0x00ffffff)
            .addCell()
            .setMeltingPoint(2_000_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadMagMatter() {
        return new MaterialBuilder().setName("Magmatter")
            .setDefaultLocalName("Magmatter")
            .setChemicalFormula("M⎋")
            .setIconSet(TextureSet.SET_MAGMATTER)
            .setTool(167_772_160, 26, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setBlastFurnaceTemp(25_000)
            .setBlastFurnaceRequired(true)
            .addSubTag(SubTag.METAL)
            .addOrePrefix(OrePrefixes.nanite)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UMV);
    }

    private static Materials_OLD loadQuarkGluonPlasma() {
        return new MaterialBuilder().setName("QuarkGluonPlasma")
            .setDefaultLocalName("Degenerate Quark Gluon Plasma")
            .setChemicalFormula(
                EnumChatFormatting.OBFUSCATED + "X"
                    + EnumChatFormatting.RESET
                    + EnumChatFormatting.GRAY
                    + "g"
                    + EnumChatFormatting.OBFUSCATED
                    + "X")
            .setFlavorText("Matter beyond structure")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setMeltingPoint(2_000_000_000)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPhononMedium() {
        return new MaterialBuilder().setName("PhononMedium")
            .setDefaultLocalName("Lossless Phonon Transfer Medium")
            .setChemicalFormula(
                "((Si₅O₁" + CustomGlyphs.SUBSCRIPT0
                    + "Fe)₃(Bi₂Te₃)₄ZrO₂Fe₅"
                    + CustomGlyphs.SUBSCRIPT0
                    + "C)₅Og*Pr₁₅((C₁₄Os₁₁O₇Ag₃SpH₂O)₄?₁"
                    + CustomGlyphs.SUBSCRIPT0
                    + "(Fs⚶)₆(⌘☯☯⌘)₅)₆〄₄")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setMeltingPoint(500)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadPhononCrystalSolution() {
        return new MaterialBuilder().setName("PhononCrystalSolution")
            .setDefaultLocalName("Saturated Phononic Crystal Solution")
            .setChemicalFormula("〄")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setMeltingPoint(500)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadSixPhasedCopper() {
        return new MaterialBuilder().setName("SixPhasedCopper")
            .setDefaultLocalName("Six-Phased Copper")
            .setChemicalFormula("✢")
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00ff7814)
            .setTool(20_971_520, 26, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_000)
            .setBlastFurnaceTemp(14_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOrePrefix(OrePrefixes.nanite)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadMellion() {
        return new MaterialBuilder().setName("Mellion")
            .setDefaultLocalName("Mellion")
            .setChemicalFormula(
                "Tn₁₁Or₈Rb₁₁(" + Materials_OLD.FierySteel.getChemicalFormula()
                    + ")₇"
                    + Materials_OLD.Firestone.getChemicalFormula()
                    + "₁₃?₁₃")
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x003c0505)
            .setTool(20_971_520, 26, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_000)
            .setBlastFurnaceTemp(14_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadCreon() {
        return new MaterialBuilder().setName("Creon")
            .setDefaultLocalName("Creon")
            .setChemicalFormula("⸎")
            .setIconSet(TextureSet.SET_SHINY)
            .setARGB(0x00460046)
            .setTool(20_971_520, 26, 1.0f)
            .addDustItems()
            .addMetalItems()
            .addPlasma()
            .addToolHeadItems()
            .addGearItems()
            .setMeltingPoint(1_000)
            .setBlastFurnaceTemp(14_000)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addSubTag(SubTag.METAL)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadGravitonShard() {
        return new MaterialBuilder().setName("GravitonShard")
            .setDefaultLocalName("Graviton Shard")
            .setIconSet(TextureSet.SET_GRAVITON_SHARD)
            .setTool(20_971_520, 26, 1.0f)
            .setMeltingPoint(100_000)
            .setBlastFurnaceTemp(100_000)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .addOrePrefix(OrePrefixes.gem)
            .constructMaterial();
    }

    private static Materials_OLD loadDimensionallyShiftedSuperfluid() {
        return new MaterialBuilder().setName("dimensionallyshiftedsuperfluid")
            .setDefaultLocalName("Dimensionally Shifted Superfluid")
            .setIconSet(TextureSet.SET_DIMENSIONALLY_SHIFTED_SUPER_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .addFluid()
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadMoltenProtoHalkoniteBase() {
        return new MaterialBuilder().setName("protohalkonitebase")
            .setDefaultLocalName("Molten Proto-Halkonite Steel Base")
            .setChemicalFormula(
                "(" + Materials_OLD.TranscendentMetal.getChemicalFormula()
                    + ")₂"
                    + "(W₈Nq*₇("
                    + Materials_OLD.Bedrockium.getChemicalFormula()
                    + ")₄C₄V₃SpPu)₂"
                    + Materials_OLD.Tartarite.getChemicalFormula()
                    + "₂"
                    + "((CW)₇Ti₃)₃"
                    + CustomGlyphs.FIRE
                    + CustomGlyphs.EARTH
                    + CustomGlyphs.CHAOS
                    + "If*")
            .setIconSet(TextureSet.SET_PROTOHALKONITE_BASE)
            .setARGB(0x00ffffff)
            .addFluid()
            .addCell()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.TRANSPARENT)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadHotProtoHalkonite() {
        return new MaterialBuilder().setName("hotprotohalkonite")
            .setDefaultLocalName("Hot Proto-Halkonite Steel")
            .setChemicalFormula(Materials_OLD.MoltenProtoHalkoniteBase.getChemicalFormula())
            .setIconSet(TextureSet.SET_HOT_PROTOHALKONITE)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addOrePrefix(OrePrefixes.gear)
            .addOrePrefix(OrePrefixes.ingot)
            .addOrePrefix(OrePrefixes.toolHeadHammer)
            .addOrePrefix(OrePrefixes.frame)
            .addOrePrefix(OrePrefixes.frameGt)
            .removeOrePrefix(OrePrefixes.cell)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .removeOrePrefix(OrePrefixes.turbineBlade)
            .removeOrePrefix(OrePrefixes.nugget)
            .removeOrePrefix(OrePrefixes.dust)
            .removeOrePrefix(OrePrefixes.dustSmall)
            .removeOrePrefix(OrePrefixes.dustTiny)
            .removeOrePrefix(OrePrefixes.spring)
            .removeOrePrefix(OrePrefixes.springSmall)
            .removeOrePrefix(OrePrefixes.itemCasing)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadProtoHalkonite() {
        return new MaterialBuilder().setName("protohalkonite")
            .setDefaultLocalName("Proto-Halkonite Steel")
            .setChemicalFormula(Materials_OLD.MoltenProtoHalkoniteBase.getChemicalFormula())
            .setFlavorText("Forged to be indestructible, probably")
            .setIconSet(TextureSet.SET_PROTOHALKONITE)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addOrePrefix(OrePrefixes.gear)
            .addOrePrefix(OrePrefixes.ingot)
            .addOrePrefix(OrePrefixes.toolHeadHammer)
            .addOrePrefix(OrePrefixes.frame)
            .addOrePrefix(OrePrefixes.frameGt)
            .removeOrePrefix(OrePrefixes.cell)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .removeOrePrefix(OrePrefixes.turbineBlade)
            .removeOrePrefix(OrePrefixes.nugget)
            .removeOrePrefix(OrePrefixes.dust)
            .removeOrePrefix(OrePrefixes.dustSmall)
            .removeOrePrefix(OrePrefixes.dustTiny)
            .removeOrePrefix(OrePrefixes.spring)
            .removeOrePrefix(OrePrefixes.springSmall)
            .removeOrePrefix(OrePrefixes.itemCasing)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadMoltenExoHalkoniteBase() {
        return new MaterialBuilder().setName("moltenexohalkonitebase")
            .setDefaultLocalName("Molten Exo-Halkonite Steel Preparation Base")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x001e1e1e)
            .addFluid()
            .addCell()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .constructMaterial();
    }

    private static Materials_OLD loadHotExoHalkonite() {
        return new MaterialBuilder().setName("hotexohalkonite")
            .setDefaultLocalName("Hot Exo-Halkonite Steel")
            .setIconSet(TextureSet.SET_HOT_EXOHALKONITE)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addOrePrefix(OrePrefixes.gear)
            .addOrePrefix(OrePrefixes.ingot)
            .addOrePrefix(OrePrefixes.toolHeadHammer)
            .addOrePrefix(OrePrefixes.frame)
            .addOrePrefix(OrePrefixes.frameGt)
            .removeOrePrefix(OrePrefixes.cell)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .removeOrePrefix(OrePrefixes.turbineBlade)
            .removeOrePrefix(OrePrefixes.nugget)
            .removeOrePrefix(OrePrefixes.dust)
            .removeOrePrefix(OrePrefixes.dustSmall)
            .removeOrePrefix(OrePrefixes.dustTiny)
            .removeOrePrefix(OrePrefixes.spring)
            .removeOrePrefix(OrePrefixes.springSmall)
            .removeOrePrefix(OrePrefixes.itemCasing)
            .removeOrePrefix(OrePrefixes.plateSuperdense) // when Exo Halkonite is added, remove this.
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadExoHalkonite() {
        return new MaterialBuilder().setName("exohalkonite")
            .setDefaultLocalName("Exo-Halkonite Steel")
            .setIconSet(TextureSet.SET_EXOHALKONITE)
            .addDustItems()
            .addMetalItems()
            .addToolHeadItems()
            .addGearItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addSubTag(SubTag.SMELTING_TO_FLUID)
            .addOrePrefix(OrePrefixes.gear)
            .addOrePrefix(OrePrefixes.ingot)
            .addOrePrefix(OrePrefixes.toolHeadHammer)
            .addOrePrefix(OrePrefixes.frame)
            .addOrePrefix(OrePrefixes.frameGt)
            .removeOrePrefix(OrePrefixes.cell)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .removeOrePrefix(OrePrefixes.turbineBlade)
            .removeOrePrefix(OrePrefixes.nugget)
            .removeOrePrefix(OrePrefixes.dust)
            .removeOrePrefix(OrePrefixes.dustSmall)
            .removeOrePrefix(OrePrefixes.dustTiny)
            .removeOrePrefix(OrePrefixes.spring)
            .removeOrePrefix(OrePrefixes.springSmall)
            .removeOrePrefix(OrePrefixes.itemCasing)
            .removeOrePrefix(OrePrefixes.plateSuperdense) // when Exo Halkonite is added, remove this.
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UEV);
    }

    private static Materials_OLD loadAntimatter() {
        return new MaterialBuilder().setName("Antimatter")
            .setDefaultLocalName("Semi-Stable Antimatter")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadProtomatter() {
        return new MaterialBuilder().setName("Protomatter")
            .setDefaultLocalName("Protomatter")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial();
    }

    private static Materials_OLD loadStargateCrystalSlurry() {
        return new MaterialBuilder().setName("sgcrystalslurry")
            .setDefaultLocalName("Stargate Crystal Slurry")
            .setIconSet(TextureSet.SET_SG_CRYSTAL_SLURRY)
            .addFluid()
            .addCell()
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_MAX);
    }

    private static Materials_OLD loadLumipodExtract() {
        return new MaterialBuilder().setName("BrightLumipodExtract")
            .setDefaultLocalName("Bright Lumipod Extract")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00d7e6bb)
            .addFluid()
            .addCell()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setLiquidTemperature(298);
    }

    private static Materials_OLD loadInactiveCosmicSolder() {
        return new MaterialBuilder().setName("InactiveCosmicSolder")
            .setDefaultLocalName("Inactive Cosmic Solder")
            .setChemicalFormula("✦✦✦✦✦")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setMeltingPoint(500)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UMV);
    }

    private static Materials_OLD loadBoundlessCosmicSolder() {
        return new MaterialBuilder().setName("BoundlessCosmicSolder")
            .setDefaultLocalName("Boundless Cosmic Solder")
            .setChemicalFormula("✧✦✧✦✧")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x00ffffff)
            .addCell()
            .setMeltingPoint(500)
            .setBlastFurnaceTemp(1)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UMV);
    }

    private static Materials_OLD loadBiocatalyzedPropulsionFluid() {
        return new MaterialBuilder().setName("BiocatalyzedPropulsionFluid")
            .setDefaultLocalName("Biocatalyzed Propulsion Fluid")
            .setChemicalFormula("ඞ")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x002d1f4d)
            .addFluid()
            .addCell()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.TRANSPARENT)
            .constructMaterial()
            .setLiquidTemperature(303);
    }

    private static void loadGTNHMaterials() {
        Materials_OLD.Signalum = loadSignalum();
        Materials_OLD.Lumium = loadLumium();
        Materials_OLD.Prismarine = loadPrismarine();
        Materials_OLD.AquaRegia = loadAquaRegia();
        Materials_OLD.SolutionBlueVitriol = loadSolutionBlueVitriol();
        Materials_OLD.SolutionNickelSulfate = loadSolutionNickelSulfate();
        Materials_OLD.Lodestone = loadLodestone();
        Materials_OLD.Luminite = loadLuminite();
        Materials_OLD.Chlorite = loadChlorite();
        Materials_OLD.Staurolite = loadStaurolite();
        Materials_OLD.Cordierite = loadCordierite();
        Materials_OLD.Datolite = loadDatolite();
        Materials_OLD.MetamorphicMineralMixture = loadMetamorphicMineralMixture();
        Materials_OLD.Plagioclase = loadPlagioclase();
        Materials_OLD.Epidote = loadEpidote();
        Materials_OLD.UnformedHexanite = loadUnformedHexanite();
        Materials_OLD.Hexanite = loadHexanite();
    }

    private static Materials_OLD loadSignalum() {
        return new MaterialBuilder().setName("Signalum")
            .setDefaultLocalName("Signalum")
            .addDustItems()
            .addMetalItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadLumium() {
        return new MaterialBuilder().setName("Lumium")
            .setDefaultLocalName("Lumium")
            .addDustItems()
            .addMetalItems()
            .addSubTag(SubTag.METAL)
            .constructMaterial();
    }

    private static Materials_OLD loadPrismarine() {
        return new MaterialBuilder().setName("Prismarine")
            .setDefaultLocalName("Prismarine")
            .addSubTag(SubTag.NO_ORE_PROCESSING)
            .constructMaterial();
    }

    private static Materials_OLD loadAquaRegia() {
        return new MaterialBuilder().setName("AquaRegia")
            .setDefaultLocalName("Aqua Regia")
            .constructMaterial();
    }

    private static Materials_OLD loadSolutionBlueVitriol() {
        return new MaterialBuilder().setName("SolutionBlueVitriol")
            .setDefaultLocalName("Blue Vitriol Solution")
            .constructMaterial();
    }

    private static Materials_OLD loadSolutionNickelSulfate() {
        return new MaterialBuilder().setName("SolutionNickelSulfate")
            .setDefaultLocalName("Nickel Sulfate Solution")
            .constructMaterial();
    }

    private static Materials_OLD loadLodestone() {
        return new MaterialBuilder().setName("Lodestone")
            .setDefaultLocalName("Lodestone")
            .setMiningLevel(0)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadLuminite() {
        return new MaterialBuilder().setName("Luminite")
            .setDefaultLocalName("Luminite")
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00fafafa)
            .setMiningLevel(0)
            .addDustItems()
            .addOreItems()
            .constructMaterial();
    }

    private static Materials_OLD loadChlorite() {
        return new MaterialBuilder().setName("Chlorite")
            .setDefaultLocalName("Chlorite")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeLime)
            .addDustItems()
            .setARGB(0x00607d6c)
            .addMaterial(Materials_OLD.Iron, 3)
            .addMaterial(Materials_OLD.Magnesium, 2)
            .addMaterial(Materials_OLD.Aluminiumoxide, 1)
            .addMaterial(Materials_OLD.SiliconDioxide, 3)
            .addMaterial(Materials_OLD.Water, 4)
            .addMaterial(Materials_OLD.Oxygen, 5)
            .constructMaterial();
    }

    private static Materials_OLD loadStaurolite() {
        return new MaterialBuilder().setName("Staurolite")
            .setDefaultLocalName("Staurolite")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeBrown)
            .addDustItems()
            .setARGB(0x003f2816)
            .addMaterial(Materials_OLD.Iron, 2)
            .addMaterial(Materials_OLD.Aluminium, 9)
            .addMaterial(Materials_OLD.SiliconDioxide, 4)
            .addMaterial(Materials_OLD.Oxygen, 16)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadCordierite() {
        return new MaterialBuilder().setName("Cordierite")
            .setDefaultLocalName("Cordierite")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyePurple)
            .setARGB(0x00434b82)
            .addDustItems()
            .addMaterial(Materials_OLD.Iron, 1)
            .addMaterial(Materials_OLD.Magnesium, 1)
            .addMaterial(Materials_OLD.SiliconDioxide, 5)
            .addMaterial(Materials_OLD.Aluminiumoxide, 1)
            .addMaterial(Materials_OLD.Oxygen, 5)
            .constructMaterial();
    }

    private static Materials_OLD loadDatolite() {
        return new MaterialBuilder().setName("Datolite")
            .setDefaultLocalName("Datolite")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00eac4ce)
            .addDustItems()
            .addMaterial(Materials_OLD.Calcium, 1)
            .addMaterial(Materials_OLD.Boron, 1)
            .addMaterial(Materials_OLD.SiliconDioxide, 1)
            .addMaterial(Materials_OLD.Oxygen, 3)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadMetamorphicMineralMixture() {
        return new MaterialBuilder().setName("MetamorphicMineralMixture")
            .setDefaultLocalName("Metamorphic Mineral Mixture")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeCyan)
            .setARGB(0x006c8294)
            .addDustItems()
            .constructMaterial();
    }

    private static Materials_OLD loadPlagioclase() {
        return new MaterialBuilder().setName("Plagioclase")
            .setDefaultLocalName("Plagioclase")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00c1bab2)
            .addDustItems()
            .addMaterial(Materials_OLD.Sodium, 1)
            .addMaterial(Materials_OLD.Aluminium, 1)
            .addMaterial(Materials_OLD.Silicon, 3)
            .addMaterial(Materials_OLD.Oxygen, 8)
            .constructMaterial();
    }

    private static Materials_OLD loadEpidote() {
        return new MaterialBuilder().setName("Epidote")
            .setDefaultLocalName("Epidote")
            .setIconSet(TextureSet.SET_DULL)
            .setColor(Dyes.dyeLime)
            .setARGB(0x008cb121)
            .addDustItems()
            .addMaterial(Materials_OLD.Calcium, 2)
            .addMaterial(Materials_OLD.Aluminium, 3)
            .addMaterial(Materials_OLD.SiliconDioxide, 3)
            .addMaterial(Materials_OLD.Oxygen, 1)
            .addMaterial(Materials_OLD.Hydrogen, 1)
            .constructMaterial();
    }

    private static Materials_OLD loadUnformedHexanite() {
        return new MaterialBuilder().setName("Unformed Hexanite")
            .setDefaultLocalName("Unformed Hexanite")
            .setChemicalFormula("Hx*")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0x003fb094)
            .addFluid()
            .addCell()
            .setMeltingPoint(9_000)
            .setBlastFurnaceTemp(9_000)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial();
    }

    private static Materials_OLD loadHexanite() {
        return new MaterialBuilder().setName("Hexanite")
            .setDefaultLocalName("Hexanite")
            .setChemicalFormula("Hx⚶")
            .setFlavorText("Insanity and willpower, given form")
            .setIconSet(TextureSet.SET_NUCLEAR)
            .setARGB(0x003fb094)
            .addMetalItems()
            .addGearItems()
            .setAutoGenerateBlastFurnaceRecipes(false)
            .setAutoGeneratedVacuumFreezerRecipe(false)
            .setAutoGeneratedRecycleRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addOrePrefix(OrePrefixes.itemCasing)
            .addOrePrefix(OrePrefixes.plateSuperdense)
            .removeOrePrefix(OrePrefixes.dust)
            .removeOrePrefix(OrePrefixes.dustSmall)
            .removeOrePrefix(OrePrefixes.dustTiny)
            .removeOrePrefix(OrePrefixes.nugget)
            .removeOrePrefix(OrePrefixes.spring)
            .removeOrePrefix(OrePrefixes.springSmall)
            .removeOrePrefix(OrePrefixes.wireFine)
            .removeOrePrefix(OrePrefixes.cellMolten)
            .removeOrePrefix(OrePrefixes.sheetmetal) // no custom texture set for this. remove when implemented.
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UMV);
    }

    private static Materials_OLD loadShijima() {
        return new MaterialBuilder().setName("Shijima")
            .setDefaultLocalName("Shijima")
            .setChemicalFormula(
                "(" + Materials_OLD.NetherStar.getChemicalFormula()
                    + ")₈Tb₇Tc₄("
                    + CustomGlyphs.FIXED_JAPANESE_OPENING_QUOTE
                    + "Fe/C⌋"
                    + ")₄Fl₃If")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0x00f0f0f0)
            .addDustItems()
            .addMetalItems()
            .addGearItems()
            .setBlastFurnaceTemp(7400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_ZPM);
    }

    private static Materials_OLD loadChuritsu() {
        return new MaterialBuilder().setName("Churitsu")
            .setDefaultLocalName("Churitsu")
            .setChemicalFormula(
                "(SnFe)₈(Ru₂Ir)₇(Kn₅Nq₉)₄(Ad₅Nq₂La₃)₄Cf₃" + "(Co₇Cr₇Mn₄Ti₂)₃("
                    + CustomGlyphs.AIR
                    + CustomGlyphs.EARTH
                    + CustomGlyphs.FIRE
                    + CustomGlyphs.WATER
                    + ")(SiC)GaAmPdBiGe")
            .setIconSet(TextureSet.SET_SHINY)
            .setColor(Dyes.dyeGray)
            .setARGB(0x00828282)
            .addDustItems()
            .addMetalItems()
            .addGearItems()
            .setBlastFurnaceTemp(7400)
            .setBlastFurnaceRequired(true)
            .setAutoGenerateBlastFurnaceRecipes(false)
            .addSubTag(SubTag.METAL)
            .addSubTag(SubTag.MULTI_PLATE)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_ZPM);
    }

    private static Materials_OLD loadComputationBase() {
        return new MaterialBuilder().setName("Computation Base")
            .setDefaultLocalName("Computation Base")
            .setChemicalFormula("01010011 01000111")
            .setFlavorText("01000011 01101111 01101101 01110000 01110101 01110100 01100101")
            .setIconSet(TextureSet.SET_FLUID)
            .setColor(Dyes.dyeBrown)
            .setARGB(0x00523a51)
            .addCell()
            .setMeltingPoint(100_000_000)
            .setBlastFurnaceTemp(100_000_000)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_UXV);
    }

    private static void loadLabGrownGems() {

    }

    private static void loadChimeraGems() {
        Materials_OLD.Amalgatite = loadAmalgatite();
    }

    private static Materials_OLD loadAmalgatite() {
        return new MaterialBuilder().setName("Amalgatite")
            .setDefaultLocalName("Amalgatite")
            .setChemicalFormula(EnumChatFormatting.OBFUSCATED + "?????????")
            .setFlavorText("It could have been great!")
            .setIconSet(TextureSet.SET_SG_CRYSTAL_SLURRY)
            .setColor(Dyes.dyeWhite)
            .setARGB(0xffffffff)
            .addSubTag(SubTag.CRYSTAL)
            .addSubTag(SubTag.CRYSTALLISABLE)
            .addSubTag(SubTag.NO_SMASHING)
            .addSubTag(SubTag.NO_SMELTING)
            .addSubTag(SubTag.NO_RECYCLING_RECIPES)
            .addSubTag(SubTag.NO_RECIPES)
            .addSubTag(SubTag.TRANSPARENT)
            .addOrePrefix(OrePrefixes.gemChipped)
            .addOrePrefix(OrePrefixes.gemFlawed)
            .addOrePrefix(OrePrefixes.gem)
            .addOrePrefix(OrePrefixes.gemFlawless)
            .addOrePrefix(OrePrefixes.gemExquisite)
            .constructMaterial()
            .setProcessingMaterialTierEU(TierEU.RECIPE_MAX);
    }
}
