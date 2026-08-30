package gregtech.api.recipe;

import static gregtech.api.enums.Mods.GTNHIntergalactic;
import static gregtech.api.util.GTRecipeMapUtil.buildOrEmpty;

import java.util.Collections;
import java.util.Comparator;

import net.minecraft.item.ItemStack;

import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.recipe.maps.AssemblerBackend;
import gregtech.api.recipe.maps.AssemblyLineFrontend;
import gregtech.api.recipe.maps.FluidCannerBackend;
import gregtech.api.recipe.maps.FluidOnlyFrontend;
import gregtech.api.recipe.maps.LargeNEIFrontend;
import gregtech.api.recipe.maps.OilCrackerBackend;
import gregtech.api.recipe.maps.SpaceProjectFrontend;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.GTUtility;
import gregtech.nei.formatter.FusionSpecialValueFormatter;
import gregtech.nei.formatter.SimpleSpecialValueFormatter;

@SuppressWarnings("SimplifyOptionalCallChains")
public final class RecipeMaps {

    public static final RecipeMap<RecipeMapBackend> scannerFakeRecipes = RecipeMapBuilder.of("gt.recipe.scanner")
        .maxIO(1, 1, 1, 0)
        .minInputs(1, 0)
        .useSpecialSlot()
        .slotOverlays((index, isFluid, isOutput, isSpecial) -> {
            if (isSpecial) {
                return GTUITextures.OVERLAY_SLOT_DATA_ORB;
            }
            if (!isFluid && !isOutput) {
                return GTUITextures.OVERLAY_SLOT_MICROSCOPE;
            }
            return null;
        })
        .slotOverlaysMUI2((index, isFluid, isOutput, isSpecial) -> {
            if (isSpecial) {
                return GTGuiTextures.OVERLAY_SLOT_DATA_ORB;
            }
            if (!isFluid && !isOutput) {
                return GTGuiTextures.OVERLAY_SLOT_MICROSCOPE;
            }
            return null;
        })
        .build();

    public static final RecipeMap<RecipeMapBackend> assemblylineVisualRecipes = RecipeMapBuilder
        .of("gt.recipe.fakeAssemblylineProcess")
        .maxIO(16, 1, 4, 0)
        .minInputs(1, 0)
        .useSpecialSlot()
        .slotOverlays((index, isFluid, isOutput, isSpecial) -> isSpecial ? GTUITextures.OVERLAY_SLOT_DATA_ORB : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> isSpecial ? GTGuiTextures.OVERLAY_SLOT_DATA_ORB : null)
        .neiTransferRect(88, 8, 18, 72)
        .neiTransferRect(124, 8, 18, 72)
        .neiTransferRect(142, 26, 18, 18)
        .frontend(AssemblyLineFrontend::new)
        .build();

    public static final RecipeMap<RecipeMapBackend> arcFurnaceRecipes = RecipeMapBuilder.of("gt.recipe.arcfurnace")
        .maxIO(2, 9, 2, 1)
        .minInputs(1, 0)
        .build();

    public static final RecipeMap<RecipeMapBackend> laserEngraverRecipes = RecipeMapBuilder
        .of("gt.recipe.laserengraver")
        .maxIO(4, 4, 2, 2)
        .slotOverlays(
            (index, isFluid, isOutput,
                isSpecial) -> !isFluid && !isOutput && index != 0 ? GTUITextures.OVERLAY_SLOT_LENS : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput,
                isSpecial) -> !isFluid && !isOutput && index != 0 ? GTGuiTextures.OVERLAY_SLOT_LENS : null)
        // Add a simple ordering so lower tier purified water is displayed first, otherwise it gets really confusing
        // NEI Catalyst search requires recipes to be sorted by voltage tier. Therefore, we first sort by voltage
        // tier,
        // then by water tier, then the default comparator.
        .neiRecipeComparator(
            (a, b) -> Comparator.<GTRecipe, Integer>comparing(recipe -> recipe.mEUt)
                .compare(a, b))
        .build();

    public static final RecipeMap<RecipeMapBackend> maceratorRecipes = RecipeMapBuilder.of("gt.recipe.macerator")
        .maxIO(1, 4, 0, 0)
        .minInputs(1, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> isOutput ? GTUITextures.OVERLAY_SLOT_DUST
                : GTUITextures.OVERLAY_SLOT_CRUSHED_ORE)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> isOutput ? GTGuiTextures.OVERLAY_SLOT_DUST
                : GTGuiTextures.OVERLAY_SLOT_CRUSHED_ORE)
        .slotOverlaysSteam(
            (index, isFluid, isOutput, isSpecial) -> isOutput ? GTUITextures.OVERLAY_SLOT_DUST_STEAM
                : GTUITextures.OVERLAY_SLOT_CRUSHED_ORE_STEAM)
        .slotOverlaysSteamMUI2(
            (_, _, isOutput, _) -> isOutput ? GTGuiTextures.OVERLAY_SLOT_DUST_STEAM
                : GTGuiTextures.OVERLAY_SLOT_CRUSHED_ORE_STEAM)
        .progressBar(GTUITextures.PROGRESSBAR_MACERATE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_MACERATE)
        .progressBarSteam(GTUITextures.PROGRESSBAR_MACERATE_STEAM)
        .progressBarTextureSteamMUI2(GTGuiTextures.PROGRESSBAR_MACERATE_STEAM)
        // Avoid steam machine being used as handler icon
        .build();

    public static final RecipeMap<RecipeMapBackend> fluidSolidifierRecipes = RecipeMapBuilder
        .of("gt.recipe.fluidsolidifier")
        .maxIO(1, 1, 1, 0)
        .minInputs(1, 1)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_MOLD : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_MOLD : null)
        .build();
    public static final RecipeMap<RecipeMapBackend> fluidExtractionRecipes = RecipeMapBuilder
        .of("gt.recipe.fluidextractor")
        .maxIO(1, 1, 0, 1)
        .minInputs(1, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_CENTRIFUGE
                : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_CENTRIFUGE
                : null)
        .progressBar(GTUITextures.PROGRESSBAR_EXTRACT)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_EXTRACT)
        .build();

    public static final RecipeMap<RecipeMapBackend> fusionRecipes = RecipeMapBuilder.of("gt.recipe.fusionreactor")
        .maxIO(0, 0, 2, 1)
        .minInputs(0, 2)
        .useCustomFilterForNEI()
        .neiSpecialInfoFormatter(FusionSpecialValueFormatter.INSTANCE)
        .neiRecipeComparator(
            Comparator.<GTRecipe, Integer>comparing(
                recipe -> FusionSpecialValueFormatter
                    .getFusionTier(recipe.getMetadataOrDefault(GTRecipeConstants.FUSION_THRESHOLD, 0L), recipe.mEUt))
                .thenComparing(GTRecipe::compareTo))
        .frontend(FluidOnlyFrontend::new)
        .build();

    public static final RecipeMap<RecipeMapBackend> spaceProjectFakeRecipes = RecipeMapBuilder
        .of("gt.recipe.fakespaceprojects")
        .maxIO(12, 0, 4, 0)
        .neiSpecialInfoFormatter(new SimpleSpecialValueFormatter("GT5U.nei.stages"))
        .neiRecipeBackgroundOffset(3, 23)
        .logo(UITexture.fullImage(GTNHIntergalactic.ID, "gui/picture/space_elevator_logo.png"))
        .logoSize(18, 18)
        .logoPos(152, 83)
        .neiTransferRect(70, 28, 18, 72)
        .neiTransferRect(106, 28, 18, 72)
        .frontend(SpaceProjectFrontend::new)
        .neiHandlerInfo(builder -> builder.setHeight(140))
        .disableRenderRealStackSizes()
        .build();

    public static final RecipeMap<RecipeMapBackend> chemicalReactorRecipes = RecipeMapBuilder
        .of("gt.recipe.chemicalreactor")
        .maxIO(2, 2, 1, 1)
        .slotOverlays((index, isFluid, isOutput, isSpecial) -> {
            if (isFluid) {
                if (isOutput) {
                    return GTUITextures.OVERLAY_SLOT_VIAL_2;
                }
                return GTUITextures.OVERLAY_SLOT_MOLECULAR_3;
            } else {
                if (isOutput) {
                    return GTUITextures.OVERLAY_SLOT_VIAL_1;
                }
                if (index == 0) {
                    return GTUITextures.OVERLAY_SLOT_MOLECULAR_1;
                }
                return GTUITextures.OVERLAY_SLOT_MOLECULAR_2;
            }
        })
        .slotOverlaysMUI2((index, isFluid, isOutput, isSpecial) -> {
            if (isFluid) {
                if (isOutput) {
                    return GTGuiTextures.OVERLAY_SLOT_VIAL_2;
                }
                return GTGuiTextures.OVERLAY_SLOT_MOLECULAR_3;
            } else {
                if (isOutput) {
                    return GTGuiTextures.OVERLAY_SLOT_VIAL_1;
                }
                if (index == 0) {
                    return GTGuiTextures.OVERLAY_SLOT_MOLECULAR_1;
                }
                return GTGuiTextures.OVERLAY_SLOT_MOLECULAR_2;
            }
        })
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_ARROW_MULTIPLE)
        .build();

    public static final RecipeMap<RecipeMapBackend> multiblockChemicalReactorRecipes = RecipeMapBuilder
        .of("gt.recipe.largechemicalreactor")
        .maxIO(6, 6, 6, 6)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(LargeNEIFrontend::new)
        .build();

    public static final RecipeMap<OilCrackerBackend> crackingRecipes = RecipeMapBuilder
        .of("gt.recipe.craker", OilCrackerBackend::new)
        .maxIO(1, 1, 2, 1)
        .minInputs(1, 2)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_ARROW_MULTIPLE)
        .build();

    public static final RecipeMap<RecipeMapBackend> wiremillRecipes = RecipeMapBuilder.of("gt.recipe.wiremill")
        .maxIO(2, 1, 0, 0)
        .minInputs(1, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_WIREMILL : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_WIREMILL : null)
        .progressBar(GTUITextures.PROGRESSBAR_WIREMILL)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_WIREMILL)
        .build();

    public static final RecipeMap<RecipeMapBackend> benderRecipes = RecipeMapBuilder.of("gt.recipe.metalbender")
        .maxIO(2, 1, 0, 0)
        .minInputs(2, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_BENDER : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_BENDER : null)
        .progressBar(GTUITextures.PROGRESSBAR_BENDING)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_BENDING)
        .build();

    public static final RecipeMap<RecipeMapBackend> alloySmelterRecipes = RecipeMapBuilder.of("gt.recipe.alloysmelter")
        .maxIO(2, 1, 0, 0)
        .minInputs(2, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_FURNACE : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_FURNACE : null)
        .slotOverlaysSteam((index, isFluid, isOutput, isSpecial) -> GTUITextures.OVERLAY_SLOT_FURNACE_STEAM)
        .slotOverlaysSteamMUI2((_, _, _, _) -> GTGuiTextures.OVERLAY_SLOT_FURNACE_STEAM)
        .progressBarSteam(GTUITextures.PROGRESSBAR_ARROW_STEAM)
        .progressBarTextureSteamMUI2(GTGuiTextures.PROGRESSBAR_ARROW_STEAM)
        .recipeEmitter(b -> {
            if (Materials.Graphite.contains(b.getItemInputBasic(0))) return Collections.emptyList();
            if (GTUtility.isArrayOfLength(b.getItemInputsBasic(), 1)) {
                ItemStack aInput1 = b.getItemInputBasic(0);
                if (((OrePrefixes.ingot.contains(aInput1)) || (OrePrefixes.dust.contains(aInput1))
                    || (OrePrefixes.gem.contains(aInput1)))) return Collections.emptyList();
            }
            return buildOrEmpty(
                b.validateNoInputFluid()
                    .validateNoOutputFluid()
                    .validateInputCount(1, 2)
                    .validateOutputCount(1, 1));
        })
        // Avoid steam machine being used as handler icon
        .build();

    public static final RecipeMap<AssemblerBackend> assemblerRecipes = RecipeMapBuilder
        .of("gt.recipe.assembler", AssemblerBackend::new)
        .maxIO(9, 1, 1, 0)
        .minInputs(1, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_CIRCUIT : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_CIRCUIT : null)
        .progressBar(GTUITextures.PROGRESSBAR_ASSEMBLE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_ASSEMBLE)
        .build();

    public static final RecipeMap<FluidCannerBackend> cannerRecipes = RecipeMapBuilder
        .of("gt.recipe.canner", FluidCannerBackend::new)
        .maxIO(2, 2, 1, 1)
        .minInputs(1, 0)
        .slotOverlays((index, isFluid, isOutput, isSpecial) -> {
            if (isOutput) {
                return null;
            }
            if (index == 0) {
                return GTUITextures.OVERLAY_SLOT_CANNER;
            }
            return GTUITextures.OVERLAY_SLOT_CANISTER;
        })
        .slotOverlaysMUI2((index, isFluid, isOutput, isSpecial) -> {
            if (isOutput) {
                return null;
            }
            if (index == 0) {
                return GTGuiTextures.OVERLAY_SLOT_CANNER;
            }
            return GTGuiTextures.OVERLAY_SLOT_CANISTER;
        })
        .progressBar(GTUITextures.PROGRESSBAR_CANNER)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_CANNER)
        .build();

    public static final RecipeMap<RecipeMapBackend> extruderRecipes = RecipeMapBuilder.of("gt.recipe.extruder")
        .maxIO(2, 1, 0, 0)
        .minInputs(2, 0)
        .slotOverlays(
            (index, isFluid, isOutput,
                isSpecial) -> !isFluid && !isOutput && index != 0 ? GTUITextures.OVERLAY_SLOT_EXTRUDER_SHAPE : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput,
                isSpecial) -> !isFluid && !isOutput && index != 0 ? GTGuiTextures.OVERLAY_SLOT_EXTRUDER_SHAPE : null)
        .progressBar(GTUITextures.PROGRESSBAR_EXTRUDE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_EXTRUDE)
        .build();

    public static final RecipeMap<RecipeMapBackend> hammerRecipes = RecipeMapBuilder.of("gt.recipe.hammer")
        .maxIO(2, 2, 2, 2)
        .minInputs(1, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_HAMMER : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_HAMMER : null)
        .progressBar(GTUITextures.PROGRESSBAR_HAMMER, ProgressBar.Direction.DOWN)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_HAMMER, ProgressWidget.Direction.DOWN)
        .addSpecialTexture(78, 42, 20, 6, GTUITextures.PROGRESSBAR_HAMMER_BASE)
        .slotOverlaysSteam(
            (index, isFluid, isOutput, isSpecial) -> !isOutput ? GTUITextures.OVERLAY_SLOT_HAMMER_STEAM : null)
        .slotOverlaysSteamMUI2((_, _, isOutput, _) -> !isOutput ? GTGuiTextures.OVERLAY_SLOT_HAMMER_STEAM : null)
        .progressBarSteam(GTUITextures.PROGRESSBAR_HAMMER_STEAM)
        .progressBarTextureSteamMUI2(GTGuiTextures.PROGRESSBAR_HAMMER_STEAM)
        .addSpecialTextureSteam(78, 42, 20, 6, GTUITextures.PROGRESSBAR_HAMMER_BASE_STEAM)
        // Avoid steam machine being used as handler icon
        .build();

    public static final RecipeMap<RecipeMapBackend> alloyBlastSmelterRecipes = RecipeMapBuilder
        .of("gt.recipe.alloyblastsmelter")
        .maxIO(9, 9, 3, 3)
        .minInputs(1, 0)
        .frontend(LargeNEIFrontend::new)
        .build();

}
