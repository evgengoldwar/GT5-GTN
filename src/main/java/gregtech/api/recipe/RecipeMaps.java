package gregtech.api.recipe;

import static gregtech.api.enums.Mods.GTNHIntergalactic;

import java.util.Comparator;

import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;

import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.recipe.maps.FluidCannerBackend;
import gregtech.api.recipe.maps.FluidOnlyFrontend;
import gregtech.api.recipe.maps.LargeNEIFrontend;
import gregtech.api.recipe.maps.OilCrackerBackend;
import gregtech.api.recipe.maps.SpaceProjectFrontend;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.nei.formatter.FusionSpecialValueFormatter;
import gregtech.nei.formatter.SimpleSpecialValueFormatter;

@SuppressWarnings("SimplifyOptionalCallChains")
public final class RecipeMaps {

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
}
