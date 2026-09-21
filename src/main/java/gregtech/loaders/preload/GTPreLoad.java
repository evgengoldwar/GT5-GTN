package gregtech.loaders.preload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.Mods.CraftTweaker;
import static gregtech.api.enums.Mods.GregTech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Client;
import gregtech.common.config.Gregtech;

public class GTPreLoad {

    public static void sortToTheEnd() {
        try {
            GT_FML_LOGGER.info("GTMod: Sorting GregTech to the end of the Mod List for further processing.");
            LoadController tLoadController = (LoadController) GTUtility
                .getFieldContent(Loader.instance(), "modController", true, true);
            assert tLoadController != null;
            List<ModContainer> tModList = tLoadController.getActiveModList();
            List<ModContainer> tNewModsList = new ArrayList<>();
            ModContainer tGregTech = null;
            short tModList_sS = (short) tModList.size();
            for (short i = 0; i < tModList_sS; i = (short) (i + 1)) {
                ModContainer tMod = tModList.get(i);
                if (tMod.getModId()
                    .equalsIgnoreCase(GregTech.ID)) {
                    tGregTech = tMod;
                } else {
                    tNewModsList.add(tMod);
                }
            }
            if (tGregTech != null) {
                tNewModsList.add(tGregTech);
            }
            Objects.requireNonNull(GTUtility.getField(tLoadController, "activeModList", true, true))
                .set(tLoadController, tNewModsList);
        } catch (Exception e) {
            GTMod.logStackTrace(e);
        }
    }

    public static void initLocalization(File languageDir) {
        GT_FML_LOGGER.info("GTMod: Generating Lang-File");

        if (FMLCommonHandler.instance()
            .getEffectiveSide()
            .isClient()) {
            String userLang = Minecraft.getMinecraft()
                .getLanguageManager()
                .getCurrentLanguage()
                .getLanguageCode();
            GT_FML_LOGGER.info("User lang is {}", userLang);
            if (userLang.equals("en_US")) {
                GT_FML_LOGGER.info("Loading GregTech.lang");
            } else {
                String l10nFileName = "GregTech_" + userLang + ".lang";
                File l10nFile = new File(languageDir, l10nFileName);
                if (l10nFile.isFile()) {
                    GT_FML_LOGGER.info("Loading l10n file: {}", l10nFileName);
                } else {
                    GT_FML_LOGGER.info("Cannot find l10n file {}, fallback to GregTech.lang", l10nFileName);
                }
            }
        } else {}
    }

    public static void runMineTweakerCompat() {
        if (!CraftTweaker.isModLoaded()) return;

        GT_FML_LOGGER.info("preReader");
        final ArrayList<String> oreTags = new ArrayList<>();
        final File globalDir = new File("scripts");
        if (globalDir.exists()) {
            final List<String> scripts = new ArrayList<>();
            for (File file : Objects.requireNonNull(globalDir.listFiles())) {
                if (file.getName()
                    .endsWith(".zs")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            scripts.add(line);
                        }
                    } catch (Exception e) {
                        GT_FML_LOGGER.error(e);;
                    }
                }
            }

            final Pattern p = Pattern.compile(Pattern.quote("<") + "(.*?)" + Pattern.quote(">"));
            final String[] prefixes1 = { "dustTiny", "dustSmall", "dust", "dustImpure", "dustPure", "crushed",
                "crushedPurified", "crushedCentrifuged", "gem", "nugget", null, "ingot", "ingotHot", null, null, null,
                null, "plate", "plateDouble", "plateTriple", "plateQuadruple", "plateQuintuple", "plateDense", "stick",
                "lens", "round", "bolt", "screw", "ring", "foil", "cell", "cellPlasma", "cellMolten", "rawOre",
                "plateSuperdense" };
            final String[] prefixes2 = { "toolHeadSword", "toolHeadPickaxe", "toolHeadShovel", "toolHeadAxe",
                "toolHeadHoe", "toolHeadHammer", "toolHeadFile", "toolHeadSaw", "toolHeadDrill", "toolHeadChainsaw",
                "toolHeadWrench", "toolHeadUniversalSpade", "toolHeadSense", "toolHeadPlow", "toolHeadArrow",
                "toolHeadBuzzSaw", "turbineBlade", null, "itemCasing", "wireFine", "gearGtSmall", "rotor", "stickLong",
                "springSmall", "spring", "arrowGtWood", "arrowGtPlastic", "gemChipped", "gemFlawed", "gemFlawless",
                "gemExquisite", "gearGt" };
            final String[] prefixes3 = { "rawOre", "nanite", "plateSuperdense" };
            for (String text : scripts) {
                Matcher m = p.matcher(text);
                while (m.find()) {
                    String hit = m.group(1);
                    if (hit.startsWith("ore:")) {
                        hit = hit.substring(4);
                        if (!oreTags.contains(hit)) oreTags.add(hit);
                    } else if (hit.startsWith("gregtech:gt.metaitem.0")) {
                        hit = hit.substring(22);
                        int mIt = Integer.parseInt(hit.substring(0, 1));
                        if (mIt > 0) {
                            int meta = 0;
                            try {
                                hit = hit.substring(2);
                                meta = Integer.parseInt(hit);
                            } catch (Exception e) {
                                GT_FML_LOGGER.info("parseError: {}", hit);
                            }
                            if (meta > 0 && meta < 32000) {
                                int prefix = meta / 1000;
                                int material = meta % 1000;
                                String[] tags = null;
                                if (mIt == 1) tags = prefixes1;
                                if (mIt == 2) tags = prefixes2;
                                if (mIt == 3) tags = prefixes3;
                                if (tags == null) tags = GTValues.emptyStringArray;
                            }
                        }
                    }
                }
            }
        }

        if (oreTags.isEmpty()) return;

        final String[] preS = { "dustTiny", "dustSmall", "dust", "dustImpure", "dustPure", "crushed", "crushedPurified",
            "crushedCentrifuged", "gem", "nugget", "ingot", "ingotHot", "plate", "plateDouble", "plateTriple",
            "plateQuadruple", "plateQuintuple", "plateDense", "stick", "lens", "round", "bolt", "screw", "ring", "foil",
            "cell", "cellPlasma", "toolHeadSword", "toolHeadPickaxe", "toolHeadShovel", "toolHeadAxe", "toolHeadHoe",
            "toolHeadHammer", "toolHeadFile", "toolHeadSaw", "toolHeadDrill", "toolHeadChainsaw", "toolHeadWrench",
            "toolHeadUniversalSpade", "toolHeadSense", "toolHeadPlow", "toolHeadArrow", "toolHeadBuzzSaw",
            "turbineBlade", "wireFine", "gearGtSmall", "rotor", "stickLong", "springSmall", "spring", "arrowGtWood",
            "arrowGtPlastic", "gemChipped", "gemFlawed", "gemFlawless", "gemExquisite", "gearGt", "nanite",
            "cellMolten", "rawOre", "plateSuperdense" };

        final ArrayList<String> mMTTags = new ArrayList<>();
        // noinspection ForLoopReplaceableByForEach
        for (int i = 0, size = oreTags.size(); i < size; i++) {
            final String str = oreTags.get(i);
            if (StringUtils.startsWithAny(str, preS)) {
                mMTTags.add(str);
                if (GTValues.D1) GT_FML_LOGGER.info("oretag: {}", str);
            }
        }

        GT_FML_LOGGER.info("reenableMetaItems");

        // noinspection ForLoopReplaceableByForEach
        for (int i = 0, size = mMTTags.size(); i < size; i++) {
            final String reEnable = mMTTags.get(i);
        }
    }

    public static void adjustScrap() {
        GT_FML_LOGGER.info("GTMod: Removing all original Scrapbox Drops.");
        try {
            Objects.requireNonNull(GTUtility.getField("ic2.core.item.ItemScrapbox$Drop", "topChance", true, true))
                .set(null, 0);
            ((List<?>) Objects
                .requireNonNull(GTUtility.getFieldContent(ic2.api.recipe.Recipes.scrapboxDrops, "drops", true, true)))
                    .clear();
        } catch (Exception e) {
            if (GTValues.D1) {
                GT_FML_LOGGER.error(e);
            }
        }
        GT_FML_LOGGER.debug("GTMod: Adding Scrap with a Weight of 200.0F to the Scrapbox Drops.");
    }

    public static void loadConfig() {
        // general
        GTValues.D1 = Gregtech.debug.D1;
        GTValues.D2 = Gregtech.debug.D2;
        GTValues.allow_broken_recipemap = Gregtech.debug.allowBrokenRecipeMap;
        GTValues.debugEntityCramming = Gregtech.debug.debugEntityCramming;
        GTValues.debugWorldData = Gregtech.debug.debugWorldData;
        GTValues.oreveinAttempts = Gregtech.general.oreveinAttempts;
        GTValues.oreveinMaxPlacementAttempts = Gregtech.general.oreveinMaxPlacementAttempts;
        GTValues.oreveinPlacerOres = Gregtech.general.oreveinPlacerOres;
        GTValues.oreveinPlacerOresMultiplier = Gregtech.general.oreveinPlacerOresMultiplier;
        GregTechAPI.TICKS_FOR_LAG_AVERAGING = Gregtech.general.ticksForLagAveraging;
        GregTechAPI.MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING = Gregtech.general.millisecondThesholdUntilLagWarning;
        GregTechAPI.sMultiThreadedSounds = Gregtech.general.multiThreadedSounds;
        GTMod.proxy.mMaxEqualEntitiesAtOneSpot = Gregtech.general.maxEqualEntitiesAtOneSpot;
        GTMod.proxy.mItemDespawnTime = Gregtech.general.itemDespawnTime;
        GTValues.mCTMEnabledBlock.addAll(Arrays.asList(Gregtech.general.CTMWhitelist));
        GTValues.mCTMDisabledBlock.addAll(Arrays.asList(Gregtech.general.CTMBlacklist));
        if (Gregtech.general.harderMobSpawner) {
            Blocks.mob_spawner.setHardness(500.0F)
                .setResistance(6000000.0F);
        }

        // machines
        GTValues.ticksBetweenSounds = Gregtech.machines.ticksBetweenSounds;
        GTValues.blacklistedTileEntiyClassNamesForWA = Gregtech.machines.blacklistedTileEntiyClassNamesForWA;
        GTValues.cleanroomGlass = Gregtech.machines.cleanroomGlass;
        GTValues.enableChunkloaders = Gregtech.machines.enableChunkloaders;
        GTValues.alwaysReloadChunkloaders = Gregtech.machines.alwaysReloadChunkloaders;
        GTValues.debugChunkloaders = Gregtech.debug.debugChunkloaders;
        GTValues.disableDigitalChestsExternalAccess = Gregtech.machines.disableDigitalChestsExternalAccess;
        GregTechAPI.sMachineExplosions = Gregtech.machines.machineExplosions;
        GregTechAPI.sMachineFlammable = Gregtech.machines.machineFlammable;
        GregTechAPI.sMachineNonWrenchExplosions = Gregtech.machines.machineNonWrenchExplosions;
        GregTechAPI.sMachineWireFire = Gregtech.machines.machineWireFire;
        GregTechAPI.sMachineFireExplosions = Gregtech.machines.machineFireExplosions;
        GregTechAPI.sMachineRainExplosions = Gregtech.machines.machineRainExplosions;
        GregTechAPI.sMachineThunderExplosions = Gregtech.machines.machineThunderExplosions;
        GregTechAPI.sColoredGUI = Gregtech.machines.coloredGUI;
        GregTechAPI.sMachineMetalGUI = Gregtech.machines.machineMetalGUI;

        // client
        loadClientConfig();
    }

    public static void loadClientConfig() {
        GTMod.proxy.mRenderTileAmbientOcclusion = Client.render.renderTileAmbientOcclusion;
        GTMod.proxy.mRenderGlowTextures = Client.render.renderGlowTextures;
        GTMod.proxy.mRenderFlippedMachinesFlipped = Client.render.renderFlippedMachinesFlipped;
        GTMod.proxy.mRenderIndicatorsOnHatch = Client.render.renderIndicatorsOnHatch;
        GTMod.proxy.mRenderDirtParticles = Client.render.renderDirtParticles;
        GTMod.proxy.mRenderPollutionFog = Client.render.renderPollutionFog;
        GTMod.proxy.mRenderItemDurabilityBar = Client.render.renderItemDurabilityBar;
        GTMod.proxy.mRenderItemChargeBar = Client.render.renderItemChargeBar;
        GTMod.proxy.mUseBlockUpdateHandler = Client.render.useBlockUpdateHandler;

        GTMod.proxy.mCoverTabsVisible = Client.iface.coverTabsVisible;
        GTMod.proxy.mCoverTabsFlipped = Client.iface.coverTabsFlipped;
        GTMod.proxy.mTooltipVerbosity = Client.iface.tooltipVerbosity;
        GTMod.proxy.mTooltipShiftVerbosity = Client.iface.tooltipShiftVerbosity;
        GTMod.proxy.mTitleTabStyle = Client.iface.titleTabStyle;
        GTMod.proxy.separatorStyle = Client.iface.separatorStyle;
        GTMod.proxy.tooltipFinisherStyle = Client.iface.tooltipFinisherStyle;

        GTMod.proxy.invertCircuitScrollDirection = Client.preference.invertCircuitScrollDirection;

        GTMod.proxy.mNEIRecipeOwner = Client.nei.NEIRecipeOwner;
        GTMod.proxy.mNEIRecipeOwnerStackTrace = Client.nei.NEIRecipeOwnerStackTrace;
        GTMod.proxy.mNEIOriginalVoltage = Client.nei.NEIOriginalVoltage;

        GTMod.proxy.mWailaTransformerVoltageTier = Client.waila.wailaTransformerVoltageTier;
        GTMod.proxy.wailaAverageNS = Client.waila.wailaAverageNS;

        GTMod.proxy.reloadNEICache();
    }
}
