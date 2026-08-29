package gregtech.common.items;

import static gregtech.common.items.IDMetaTool01.CROWBAR;
import static gregtech.common.items.IDMetaTool01.FILE;
import static gregtech.common.items.IDMetaTool01.HARDHAMMER;
import static gregtech.common.items.IDMetaTool01.KNIFE;
import static gregtech.common.items.IDMetaTool01.MORTAR;
import static gregtech.common.items.IDMetaTool01.PLUNGER;
import static gregtech.common.items.IDMetaTool01.ROLLING_PIN;
import static gregtech.common.items.IDMetaTool01.SAW;
import static gregtech.common.items.IDMetaTool01.SCOOP;
import static gregtech.common.items.IDMetaTool01.SCREWDRIVER;
import static gregtech.common.items.IDMetaTool01.SOFTMALLET;
import static gregtech.common.items.IDMetaTool01.SOLDERING_IRON_HV;
import static gregtech.common.items.IDMetaTool01.SOLDERING_IRON_LV;
import static gregtech.common.items.IDMetaTool01.SOLDERING_IRON_MV;
import static gregtech.common.items.IDMetaTool01.TROWEL;
import static gregtech.common.items.IDMetaTool01.TURBINE;
import static gregtech.common.items.IDMetaTool01.TURBINE_HUGE;
import static gregtech.common.items.IDMetaTool01.TURBINE_LARGE;
import static gregtech.common.items.IDMetaTool01.TURBINE_SMALL;
import static gregtech.common.items.IDMetaTool01.WIRECUTTER;
import static gregtech.common.items.IDMetaTool01.WRENCH;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TCAspects;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.items.MetaGeneratedTool;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.tools.ItemNetworkAnalyzer;
import gregtech.common.tools.ToolCrowbar;
import gregtech.common.tools.ToolFile;
import gregtech.common.tools.ToolHardHammer;
import gregtech.common.tools.ToolKnife;
import gregtech.common.tools.ToolMortar;
import gregtech.common.tools.ToolPlunger;
import gregtech.common.tools.ToolSaw;
import gregtech.common.tools.ToolScrewdriver;
import gregtech.common.tools.ToolSoftMallet;
import gregtech.common.tools.ToolSolderingIron;
import gregtech.common.tools.ToolVajra;
import gregtech.common.tools.ToolWireCutter;
import gregtech.common.tools.ToolWrench;

public class MetaGeneratedTool01 extends MetaGeneratedTool {

    public static MetaGeneratedTool01 INSTANCE;

    public MetaGeneratedTool01() {
        super("metatool.01");
        INSTANCE = this;
        addTool(
            SAW.ID,
            "Saw",
            "Can also harvest Ice",
            new ToolSaw(),
            ToolDictNames.craftingToolSaw,
            new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
            new TCAspects.TC_AspectStack(TCAspects.METO, 2L),
            new TCAspects.TC_AspectStack(TCAspects.ARBOR, 2L));
        GregTechAPI.registerTool(
            addTool(
                HARDHAMMER.ID,
                "Hammer",
                "Crushes Ores instead of harvesting them",
                new ToolHardHammer(),
                ToolDictNames.craftingToolHardHammer,
                new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sHardHammerList);
        GregTechAPI.registerTool(
            addTool(
                SOFTMALLET.ID,
                "Soft Mallet",
                "",
                new ToolSoftMallet(),
                ToolDictNames.craftingToolSoftMallet,
                new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
                new TCAspects.TC_AspectStack(TCAspects.LIMUS, 4L)),
            GregTechAPI.sSoftMalletList);
        GregTechAPI.registerTool(
            addTool(
                WRENCH.ID,
                "Wrench",
                "Hold Leftclick to dismantle Machines",
                new ToolWrench(),
                ToolDictNames.craftingToolWrench,
                new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sWrenchList);
        addTool(
            FILE.ID,
            "File",
            "",
            new ToolFile(),
            ToolDictNames.craftingToolFile,
            new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
            new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
            new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L));
        GregTechAPI.registerTool(
            addTool(
                CROWBAR.ID,
                "Crowbar",
                "Dismounts Covers and Rotates Rails",
                new ToolCrowbar(),
                ToolDictNames.craftingToolCrowbar,
                new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.TELUM, 2L)),
            GregTechAPI.sCrowbarList);
        GregTechAPI.registerTool(
            addTool(
                SCREWDRIVER.ID,
                "Screwdriver",
                "Adjusts Covers and Machines",
                new ToolScrewdriver(),
                ToolDictNames.craftingToolScrewdriver,
                new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sScrewdriverList);
        addTool(
            MORTAR.ID,
            "Mortar",
            "",
            new ToolMortar(),
            ToolDictNames.craftingToolMortar,
            new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
            new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
            new TCAspects.TC_AspectStack(TCAspects.PERDITIO, 2L));
        GregTechAPI.registerTool(
            addTool(
                WIRECUTTER.ID,
                "Wire Cutter",
                "",
                new ToolWireCutter(),
                ToolDictNames.craftingToolWireCutter,
                new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sWireCutterList);

        addTool(
            KNIFE.ID,
            "Knife",
            "",
            new ToolKnife(),
            ToolDictNames.craftingToolBlade,
            ToolDictNames.craftingToolKnife,
            new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
            new TCAspects.TC_AspectStack(TCAspects.TELUM, 2L),
            new TCAspects.TC_AspectStack(TCAspects.CORPUS, 2L));

        addTool(
            PLUNGER.ID,
            "Plunger",
            "",
            new ToolPlunger(),
            ToolDictNames.craftingToolPlunger,
            new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 2L),
            new TCAspects.TC_AspectStack(TCAspects.VACUOS, 2L),
            new TCAspects.TC_AspectStack(TCAspects.ITER, 2L));

        GregTechAPI.registerTool(
            addTool(
                SOLDERING_IRON_LV.ID,
                "Soldering Iron (LV)",
                "Fixes burned out Circuits. Needs soldering materials in inventory.",
                new ToolSolderingIron(),
                ToolDictNames.craftingToolSolderingIron,
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sSolderingToolList);
        GregTechAPI.registerTool(
            addTool(
                SOLDERING_IRON_MV.ID,
                "Soldering Iron (MV)",
                "Fixes burned out Circuits. Needs soldering materials in inventory.",
                new ToolSolderingIron(),
                ToolDictNames.craftingToolSolderingIron,
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sSolderingToolList);
        GregTechAPI.registerTool(
            addTool(
                SOLDERING_IRON_HV.ID,
                "Soldering Iron (HV)",
                "Fixes burned out Circuits. Needs soldering materials in inventory.",
                new ToolSolderingIron(),
                ToolDictNames.craftingToolSolderingIron,
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.FABRICO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L)),
            GregTechAPI.sSolderingToolList);

        // Register Electric Wirecutters

        // Register Electric File

        ItemList.Tool_Vajra.set(new ToolVajra("Tool_Vajra", "Vajra", "", 0, 20, true));
        ItemList.NetworkAnalyzer.set(new ItemNetworkAnalyzer("Network Analyzer", "", 0, 0, true));

        initCraftingShapedRecipes();
        initCraftingShapelessRecipes();
    }

    private void initCraftingShapelessRecipes() {
        GTModHandler.addShapelessCraftingRecipe(
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Items.coal, 1) });
        GTModHandler.addShapelessCraftingRecipe(
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Clay, 1L),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Blocks.clay, 1) });
        GTModHandler.addShapelessCraftingRecipe(
            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Items.wheat, 1) });
        GTModHandler.addShapelessCraftingRecipe(
            new ItemStack(Items.flint, 1),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Blocks.gravel, 1) });
        GTModHandler.addShapelessCraftingRecipe(
            new ItemStack(Items.blaze_powder, 2),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Items.blaze_rod, 1) });
    }

    private void initCraftingShapedRecipes() {
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.Flint, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', new ItemStack(Items.flint, 1), 'S', OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.Bronze, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.Bronze), 'S', OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.Iron, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.Iron), 'S', OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.Steel, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.Steel), 'S', OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.CastIron, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.CastIron), 'S',
                OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.RedSteel, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.RedSteel), 'S',
                OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.BlueSteel, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.BlueSteel), 'S',
                OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.BlackSteel, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.BlackSteel), 'S',
                OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.DamascusSteel, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.DamascusSteel), 'S',
                OrePrefixes.stone });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(MORTAR.ID, 1, Materials.Thaumium, Materials.Stone, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { " I ", "SIS", "SSS", 'I', OrePrefixes.ingot.get(Materials.Thaumium), 'S',
                OrePrefixes.stone });

        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(ROLLING_PIN.ID, 1, Materials.Wood, Materials.Wood, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { "  S", " I ", "S f", 'I', OrePrefixes.plank.get(Materials.Wood), 'S',
                OrePrefixes.stick.get(Materials.Wood) });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(ROLLING_PIN.ID, 1, Materials.Polyethylene, Materials.Polyethylene, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { "  S", " I ", "S f", 'I', OrePrefixes.ingot.get(Materials.Polyethylene), 'S',
                OrePrefixes.stick.get(Materials.Polyethylene) });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(ROLLING_PIN.ID, 1, Materials.Aluminium, Materials.Aluminium, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { "  S", " I ", "S f", 'I', OrePrefixes.ingot.get(Materials.Aluminium), 'S',
                OrePrefixes.stick.get(Materials.Aluminium) });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(ROLLING_PIN.ID, 1, Materials.StainlessSteel, Materials.StainlessSteel, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { "  S", " I ", "S f", 'I', OrePrefixes.ingot.get(Materials.StainlessSteel), 'S',
                OrePrefixes.stick.get(Materials.StainlessSteel) });
        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(ROLLING_PIN.ID, 1, Materials.IronWood, Materials.IronWood, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { "  S", " I ", "S f", 'I', OrePrefixes.ingot.get(Materials.IronWood), 'S',
                OrePrefixes.stick.get(Materials.IronWood) });

        GTModHandler.addCraftingRecipe(
            INSTANCE.getToolWithStats(KNIFE.ID, 1, Materials.Flint, Materials.Wood, null),
            GTModHandler.RecipeBits.NOT_REMOVABLE,
            new Object[] { "F", "S", 'S', OrePrefixes.stick.get(Materials.Wood), 'F', new ItemStack(Items.flint, 1) });
    }
}
