package gregtech.api.modularui2;

import gregtech.api.enums.Dyes;

/**
 * Holds all the {@link GTGuiTheme}s registered.
 */
public final class GTGuiThemes {

    public static void init() {}

    public static final GTGuiTheme STANDARD = GTGuiTheme.builder("gregtech:standard")
        .panel(GTTextureIds.BACKGROUND_STANDARD)
        .itemSlot(GTTextureIds.SLOT_ITEM_STANDARD)
        .fluidSlot(GTTextureIds.SLOT_FLUID_STANDARD)
        .color(Dyes.GUI_METAL.toInt())
        .textColor(0x404040)
        .textField(Dyes.dyeWhite.toInt())
        .customTextColor(GTWidgetThemes.TEXT_TITLE.getFullName(), 0x404040)
        .customTextColor(GTWidgetThemes.DISPLAY_TEXT_WHITE.getFullName(), 0xFAFAFA)
        .button(GTTextureIds.BUTTON_STANDARD)
        .simpleToggleButton(GTTextureIds.BUTTON_STANDARD, GTTextureIds.BUTTON_STANDARD_PRESSED, Dyes.GUI_METAL.toInt())
        .themedTexture(GTWidgetThemes.BACKGROUND_POPUP.getFullName(), GTTextureIds.BACKGROUND_POPUP_STANDARD)
        .themedTexture(GTWidgetThemes.BACKGROUND_TITLE.getFullName(), GTTextureIds.BACKGROUND_TITLE_STANDARD)
        .themedTexture(GTWidgetThemes.BACKGROUND_COLOR_SWATCH.getFullName(), GTTextureIds.SLOT_ITEM_STANDARD)
        .themedOverlayItemSlot(
            GTWidgetThemes.OVERLAY_ITEM_SLOT_COAL.getFullName(),
            GTTextureIds.OVERLAY_SLOT_COAL_STANDARD)
        .themedOverlayItemSlot(
            GTWidgetThemes.OVERLAY_ITEM_SLOT_DUST.getFullName(),
            GTTextureIds.OVERLAY_SLOT_DUST_STANDARD)
        .themedOverlayItemSlot(GTWidgetThemes.OVERLAY_ITEM_SLOT_IN.getFullName(), GTTextureIds.OVERLAY_SLOT_IN_STANDARD)
        .themedOverlayItemSlot(
            GTWidgetThemes.OVERLAY_ITEM_SLOT_INGOT.getFullName(),
            GTTextureIds.OVERLAY_SLOT_INGOT_STANDARD)
        .themedOverlayItemSlot(
            GTWidgetThemes.OVERLAY_ITEM_SLOT_FURNACE.getFullName(),
            GTTextureIds.OVERLAY_SLOT_FURNACE_STANDARD)
        .themedOverlayItemSlot(
            GTWidgetThemes.OVERLAY_ITEM_SLOT_OUT.getFullName(),
            GTTextureIds.OVERLAY_SLOT_OUT_STANDARD)
        .themedOverlayItemSlot(
            GTWidgetThemes.OVERLAY_ITEM_SLOT_BLOCK.getFullName(),
            GTTextureIds.OVERLAY_SLOT_BLOCK_STANDARD)
        .themedOverlayFluidSlot(
            GTWidgetThemes.OVERLAY_FLUID_SLOT_IN.getFullName(),
            GTTextureIds.OVERLAY_SLOT_IN_STANDARD)
        .progressbar(
            GTWidgetThemes.PROGRESSBAR_BOILER_HEAT.getFullName(),
            GTTextureIds.SLOT_FLUID_STANDARD,
            GTTextureIds.PROGRESSBAR_BOILER_HEAT,
            54)
        .progressbar(GTWidgetThemes.PROGRESSBAR_FUEL.getFullName(), GTTextureIds.PROGRESSBAR_FUEL_STANDARD, 14)
        .themedButton(
            GTWidgetThemes.BUTTON_COVER_TAB_ENABLED.getFullName(),
            GTTextureIds.BUTTON_COVER_TAB_NORMAL_STANDARD,
            GTTextureIds.BUTTON_COVER_TAB_HIGHLIGHT_STANDARD)
        .themedButton(
            GTWidgetThemes.BUTTON_COVER_TAB_DISABLED.getFullName(),
            GTTextureIds.BUTTON_COVER_TAB_DISABLED_STANDARD)
        .themedTexture(GTWidgetThemes.PICTURE_CANISTER.getFullName(), GTTextureIds.OVERLAY_SLOT_CANISTER_DARK)
        .themedTexture(GTWidgetThemes.PICTURE_LOGO.getFullName(), GTTextureIds.PICTURE_GT_LOGO_STANDARD)
        .themedTexture(GTWidgetThemes.PICTURE_ERROR.getFullName(), GTTextureIds.PICTURE_ERROR)
        .build();

    public static final GTGuiTheme COVER = GTGuiTheme.builder("gregtech:cover")
        .parent(STANDARD)
        .textColor(0x555555)
        .customTextColor(GTWidgetThemes.TEXT_TITLE.getFullName(), 0x222222)
        .textField(Dyes.dyeWhite.toInt())
        .build();
}
