package gregtech.api.enums;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.AQUA;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.BLUE;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.BOLD;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.DARK_BLUE;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.DARK_GRAY;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.DARK_GREEN;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.DARK_PURPLE;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.DARK_RED;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.GOLD;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.GRAY;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.GREEN;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.LIGHT_PURPLE;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.OBFUSCATED;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.RED;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.UNDERLINE;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.YELLOW;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.addItemTooltip;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.animatedText;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.chain;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.text;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

public enum Tier {

    NULL(0, 0, 0, text("")),
    STONE(0, 0, 0, text("")),
    STEAM(0, 0, 0, text("")),
    LV(32, 30, 1, text(GRAY + "LV-Tier")),
    MV(128, 120, 2, text(GOLD + "MV-Tier")),
    HV(512, 480, 3, text(YELLOW + "HV-Tier")),
    EV(2_048, 1_920, 4, text(DARK_GRAY + "EV-Tier")),
    IV(8_192, 7_680, 5, text(GREEN + "IV-Tier")),
    LuV(32_768, 30_720, 6, text(LIGHT_PURPLE + "LuV-Tier")),
    ZPM(131_072, 122_880, 7, text(AQUA + "ZPM-Tier")),
    UV(524_288, 491_520, 8, text(DARK_GREEN + "UV-Tier")),
    UHV(2_097_152, 1_966_080, 9, text(DARK_RED + "UHV-Tier")),
    UEV(8_388_608, 7_864_320, 10, text(DARK_PURPLE + "UEV-Tier")),
    UIV(33_554_432, 31_457_280, 11, text(DARK_BLUE + BOLD + "UIV-Tier")),
    UMV(134_217_728, 125_829_120, 12, text(RED + BOLD + UNDERLINE + "UMV-Tier")),
    UXV(536_870_912, 503_316_480, 13,
        animatedText("UXV-Tier", 1, 100, DARK_PURPLE + BOLD + UNDERLINE, DARK_RED + UNDERLINE + BOLD)),
    MAX(2_147_483_640, 2_013_265_920, 14,
        chain(
            animatedText(
                "X",
                1,
                100,
                LIGHT_PURPLE + BOLD + OBFUSCATED + UNDERLINE,
                RED + BOLD + OBFUSCATED + UNDERLINE,
                GOLD + OBFUSCATED + BOLD + UNDERLINE,
                YELLOW + OBFUSCATED + BOLD + UNDERLINE,
                GREEN + OBFUSCATED + BOLD + UNDERLINE,
                AQUA + OBFUSCATED + BOLD + UNDERLINE,
                BLUE + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "MAX-Tier",
                1,
                100,
                RED + BOLD + UNDERLINE,
                GOLD + BOLD + UNDERLINE,
                YELLOW + BOLD + UNDERLINE,
                GREEN + BOLD + UNDERLINE,
                AQUA + BOLD + UNDERLINE,
                BLUE + BOLD + UNDERLINE,
                LIGHT_PURPLE + BOLD + UNDERLINE),
            animatedText(
                "X",
                1,
                100,
                GOLD + OBFUSCATED + BOLD + UNDERLINE,
                YELLOW + OBFUSCATED + BOLD + UNDERLINE,
                GREEN + OBFUSCATED + BOLD + UNDERLINE,
                AQUA + OBFUSCATED + BOLD + UNDERLINE,
                BLUE + OBFUSCATED + BOLD + UNDERLINE,
                LIGHT_PURPLE + OBFUSCATED + BOLD + UNDERLINE,
                RED + OBFUSCATED + BOLD + UNDERLINE))),
    ERV(8_589_934_592L, 8_053_063_680L, 15,
        chain(
            animatedText(
                "E",
                1,
                100,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "R",
                1,
                100,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "R",
                1,
                200,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "O",
                1,
                150,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "R",
                1,
                150,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "-",
                1,
                150,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "T",
                1,
                200,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "i",
                1,
                100,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "e",
                1,
                150,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE),
            animatedText(
                "r",
                1,
                100,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_RED + BOLD + UNDERLINE,
                DARK_GRAY + OBFUSCATED + BOLD + UNDERLINE)));

    private final long voltage;
    private final long recipeVoltage;
    private final int index;
    private final Supplier<String> tooltip;

    Tier(long voltage, long recipeVoltage, int index, Supplier<String> tooltip) {
        this.voltage = voltage;
        this.recipeVoltage = recipeVoltage;
        this.index = index;
        this.tooltip = tooltip;
    }

    public long getVoltage() {
        return voltage;
    }

    public long getRecipeVoltage() {
        return recipeVoltage;
    }

    public int getIndex() {
        return index;
    }

    public static void registerTieredTooltip(ItemStack item, Tier tier) {
        addItemTooltip(item, tier.tooltip);
    }
}
