package gregtech.api.modpack.util;

import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Code side localization. Builders call {@link #add(String, String)} with the english name, so content works without
 * any .lang file. A .lang file (assets/gregtech/lang/ru_RU.lang) still overrides these values for other languages.
 */
public final class Lang {

    public static final String EN = "en_US";
    public static final String RU = "ru_RU";

    private Lang() {}

    public static void add(String key, String english) {
        if (key == null || english == null) {
            return;
        }
        LanguageRegistry.instance()
            .addStringLocalization(key, EN, english);
    }

    public static void add(String key, String language, String value) {
        if (key == null || value == null) {
            return;
        }
        LanguageRegistry.instance()
            .addStringLocalization(key, language, value);
    }

    /**
     * Translates the key, or returns the key itself when there is no translation.
     */
    public static String translate(String key) {
        return StatCollector.translateToLocal(key);
    }

    public static String translate(String key, Object... args) {
        return StatCollector.translateToLocalFormatted(key, args);
    }
}
