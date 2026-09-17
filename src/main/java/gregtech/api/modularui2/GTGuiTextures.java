package gregtech.api.modularui2;

import static com.cleanroommc.modularui.drawable.UITexture.fullImage;
import static gregtech.api.enums.Mods.GregTech;

import com.cleanroommc.modularui.drawable.ColorType;
import com.cleanroommc.modularui.drawable.UITexture;

/**
 * Holds all the references to GUI textures used within GregTech.
 */
public final class GTGuiTextures {

    public static void init() {}

    // region background
    public static final UITexture BACKGROUND_TERMINAL = UITexture.builder()
        .location(GregTech.ID, "gui/background/terminal")
        .imageSize(142, 28)
        .adaptable(4)
        .build();
    public static final UITexture BACKGROUND_TEXT_FIELD = UITexture.builder()
        .location(GregTech.ID, "gui/background/text_field")
        .imageSize(142, 28)
        .adaptable(1)
        .build();

    public static final UITexture BACKGROUND_REDSTONE_SNIFFER = UITexture.builder()
        .location(GregTech.ID, "gui/background/redstone_sniffer")
        .imageSize(195, 136)
        .adaptable(1)
        .build();

    public static final UITexture BACKGROUND_CHAOS_LOCATOR = UITexture.builder()
        .location(GregTech.ID, "gui/background/chaos_locator")
        .imageSize(176, 166)
        .adaptable(1)
        .build();

    // endregion background

    // region slot

    public static final UITexture SLOT_ITEM_STANDARD = UITexture.builder()
        .location(GregTech.ID, "gui/slot/item_standard")
        .imageSize(18, 18)
        .adaptable(1)
        .canApplyTheme()
        .name(GTTextureIds.SLOT_ITEM_STANDARD)
        .build();
    public static final UITexture SLOT_ITEM_DARK = UITexture.builder()
        .location(GregTech.ID, "gui/slot/dark_gray")
        .imageSize(18, 18)
        .adaptable(1)
        .canApplyTheme()
        .build();

    // endregion slot

    // region slot overlay

    public static final UITexture OVERLAY_SLOT_INT_CIRCUIT = fullImageColorableGT("overlay_slot/int_circuit");
    public static final UITexture OVERLAY_BUTTON_REDSTONESNIFFERLOCATE = fullImage(
        GregTech.ID,
        "gui/overlay_button/redstoneSnifferLocate");

    public static final UITexture OVERLAY_EXPORT = fullImage(GregTech.ID, "gui/overlay_button/export");
    public static final UITexture OVERLAY_IMPORT = fullImage(GregTech.ID, "gui/overlay_button/import");

    // endregion slot overlay

    // region progressbar

    public static final UITexture PROGRESSBAR_ARROW_STANDARD = fullImageColorableGT("progressbar/arrow");
    public static final UITexture PROGRESSBAR_BOILER_HEAT = UITexture.builder()
        .location(GregTech.ID, "gui/progressbar/boiler_heat")
        .fullImage()
        .name(GTTextureIds.PROGRESSBAR_BOILER_HEAT)
        .build();
    public static final UITexture PROGRESSBAR_FUEL_STANDARD = UITexture.builder()
        .location(GregTech.ID, "gui/progressbar/fuel")
        .fullImage()
        .canApplyTheme()
        .name(GTTextureIds.PROGRESSBAR_FUEL_STANDARD)
        .build();

    public static final UITexture STEAM_GAUGE_BG = UITexture.builder()
        .fullImage()
        .location(GregTech.ID, "gui/background/steam_dial")
        .build();

    // endregion progressbar

    // region button

    public static final UITexture BUTTON_STANDARD_DISABLED = UITexture.builder()
        .location(GregTech.ID, "gui/button/standard_disabled")
        .imageSize(18, 18)
        .adaptable(1)
        .canApplyTheme()
        .build();
    public static final UITexture TOGGLE_BUTTON_STANDARD_DISABLED = UITexture.builder()
        .location(GregTech.ID, "gui/button/standard_toggle_disabled")
        .imageSize(18, 18)
        .adaptable(1)
        .canApplyTheme()
        .build();

    // endregion button

    // region button overlay

    public static final UITexture OVERLAY_BUTTON_ALLOW_INPUT = fullImageGT("overlay_button/allow_input");
    public static final UITexture OVERLAY_BUTTON_ALLOW_OUTPUT = fullImageGT("overlay_button/allow_output");
    public static final UITexture OVERLAY_BUTTON_BLOCK_INPUT = fullImageGT("overlay_button/block_input");
    public static final UITexture OVERLAY_BUTTON_BLOCK_OUTPUT = fullImageGT("overlay_button/block_output");
    public static final UITexture OVERLAY_BUTTON_CHECKMARK = fullImageGT("overlay_button/checkmark");
    public static final UITexture OVERLAY_BUTTON_CROSS = fullImageGT("overlay_button/cross");
    public static final UITexture OVERLAY_BUTTON_EXPORT = fullImageGT("overlay_button/export");
    public static final UITexture OVERLAY_BUTTON_HOURGLASS = fullImageGT("overlay_button/hourglass");
    public static final UITexture OVERLAY_BUTTON_IMPORT = fullImageGT("overlay_button/import");
    public static final UITexture OVERLAY_BUTTON_POWER_SWITCH_ON = fullImageGT("overlay_button/power_switch_on");
    public static final UITexture OVERLAY_BUTTON_PROGRESS = fullImageGT("overlay_button/progress");
    public static final UITexture OVERLAY_BUTTON_REDSTONE_OFF = fullImageGT("overlay_button/redstone_off");
    public static final UITexture OVERLAY_BUTTON_REDSTONE_ON = fullImageGT("overlay_button/redstone_on");
    public static final UITexture OVERLAY_BUTTON_USE_PROCESSING_STATE = fullImageGT(
        "overlay_button/use_processing_state");
    public static final UITexture OVERLAY_BUTTON_USE_INVERTED_PROCESSING_STATE = fullImageGT(
        "overlay_button/use_inverted_processing_state");
    public static final UITexture OVERLAY_BUTTON_GATE_AND = fullImageGT("overlay_button/gate_and");
    public static final UITexture OVERLAY_BUTTON_GATE_NAND = fullImageGT("overlay_button/gate_nand");
    public static final UITexture OVERLAY_BUTTON_GATE_OR = fullImageGT("overlay_button/gate_or");
    public static final UITexture OVERLAY_BUTTON_GATE_NOR = fullImageGT("overlay_button/gate_nor");
    public static final UITexture OVERLAY_BUTTON_ANALOG = fullImageGT("overlay_button/analog");

    public static final UITexture OVERLAY_BUTTON_CYCLIC = UITexture.builder()
        .location(GregTech.ID, "gui/overlay_button/cyclic")
        .fullImage()
        .canApplyTheme()
        .build();
    // endregion button overlay

    /**
     * Creates texture of full image. Theme of GUI does not affect how the texture is drawn.
     *
     * @param path Path to the image, excluding gregtech/textures/gui
     * @return Texture of full image
     */
    private static UITexture fullImageGT(String path) {
        return fullImage(GregTech.ID, "gui/" + path);
    }

    /**
     * Creates texture of full image. Theme color gets applied to the texture.
     *
     * @param path Path to the image, excluding gregtech/textures/gui
     * @return Texture of full image
     */
    private static UITexture fullImageColorableGT(String path) {
        return fullImage(GregTech.ID, "gui/" + path, ColorType.DEFAULT);
    }
}
