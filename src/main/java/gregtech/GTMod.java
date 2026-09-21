package gregtech;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.GT_Version.VERSION_MAJOR;
import static gregtech.GT_Version.VERSION_MINOR;
import static gregtech.GT_Version.VERSION_PATCH;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.util.GTRecipe.setItemStacks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Logger;

import com.falsepattern.chunk.api.DataRegistry;
import com.google.common.base.Stopwatch;
import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Mods;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUIInfos;
import gregtech.api.metatileentity.BasePipeGTE;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.GTGuiTheme;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.recipe.RecipeLookupValidator;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.util.AssemblyLineServer;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTRecipe;
import gregtech.client.iconContainers.IconContainer;
import gregtech.common.GTClient;
import gregtech.common.GTDummyWorld;
import gregtech.common.GTNetwork;
import gregtech.common.GTProxy;
import gregtech.common.MaterialSystem.Materials;
import gregtech.common.RecipeAdder;
import gregtech.common.config.Client;
import gregtech.common.config.Gregtech;
import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.misc.GTMiscCommand;
import gregtech.common.misc.spaceprojects.commands.SPCommand;
import gregtech.common.misc.spaceprojects.commands.SpaceProjectCommand;
import gregtech.crossmod.ae2.AE2Compat;
import gregtech.crossmod.chunkapi.ClientMetaManager;
import gregtech.crossmod.holoinventory.HoloInventory;
import gregtech.crossmod.waila.Waila;
import gregtech.loaders.postload.GTPostLoad;
import gregtech.loaders.postload.MachineTooltipsLoader;
import gregtech.loaders.postload.PosteaTransformers;
import gregtech.loaders.preload.GTPreLoad;
import gregtech.loaders.preload.LoaderGTBlockFluid;
import gregtech.loaders.preload.LoaderMetaTileEntities;

@Mod(
    modid = Mods.ModIDs.GREG_TECH,
    name = "GregTech",
    version = GT_Version.VERSION,
    guiFactory = "gregtech.client.GTGuiFactory",
    dependencies = "required-after:IC2;" + "required-after:structurelib;"
        + "required-after:gtnhlib@[0.6.35,);"
        + "required-after:modularui@[1.1.12,);"
        + "required-after:appliedenergistics2@[rv3-beta-258,);"
        + "after:dreamcraft;"
        + "after:Forestry;"
        + "after:PFAAGeologica;"
        + "after:Thaumcraft;"
        + "after:Railcraft;"
        + "after:ThermalExpansion;"
        + "after:TwilightForest;"
        + "after:harvestcraft;"
        + "after:magicalcrops;"
        + "after:Botania;"
        + "after:BuildCraft|Transport;"
        + "after:BuildCraft|Silicon;"
        + "after:BuildCraft|Factory;"
        + "after:BuildCraft|Energy;"
        + "after:BuildCraft|Core;"
        + "after:BuildCraft|Builders;"
        + "after:GalacticraftCore;"
        + "after:GalacticraftMars;"
        + "after:GalacticraftPlanets;"
        + "after:ThermalExpansion|Transport;"
        + "after:ThermalExpansion|Energy;"
        + "after:ThermalExpansion|Factory;"
        + "after:RedPowerCore;"
        + "after:RedPowerBase;"
        + "after:RedPowerMachine;"
        + "after:RedPowerCompat;"
        + "after:RedPowerWiring;"
        + "after:RedPowerLogic;"
        + "after:RedPowerLighting;"
        + "after:RedPowerWorld;"
        + "after:RedPowerControl;"
        + "after:UndergroundBiomes;"
        + "after:TConstruct;"
        + "after:Translocator;"
        + "after:gendustry;"
        + "before:computronics;")
public class GTMod {

    static {
        try {
            // Client
            ConfigurationManager.registerConfig(Client.class);

            // GregTech.cfg
            ConfigurationManager.registerConfig(Gregtech.class);

        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }

    public static final int NBT_VERSION = calculateTotalGTVersion(VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH);

    @Mod.Instance(Mods.ModIDs.GREG_TECH)
    public static GTMod GT;

    @SidedProxy(
        modId = Mods.ModIDs.GREG_TECH,
        clientSide = "gregtech.common.GTClient",
        serverSide = "gregtech.common.GTProxy")
    public static GTProxy proxy;
    public static final boolean DEBUG = Boolean.getBoolean("gt.debug");

    public GTMod() {
        GTValues.DW = new GTDummyWorld();
        GTValues.NW = new GTNetwork();
        GTValues.RA = new RecipeAdder();

        // TEs that can be wrenched.
        for (int i = 0; i < 4; i++) {
            GregTechAPI.registerTileEntityConstructor(i, i2 -> GregTechAPI.constructBaseMetaTileEntity());
        }

        // TEs that can be mined.
        for (int i = 12; i < 16; i++) {
            GregTechAPI.registerTileEntityConstructor(i, i2 -> GregTechAPI.constructBaseMetaTileEntity());
        }

        for (int i = 4; i < 12; i++) {
            GregTechAPI.registerTileEntityConstructor(i, i2 -> new BasePipeGTE());
        }
    }

    public static GTClient clientProxy() {
        if (proxy.isClientSide()) {
            return (GTClient) proxy;
        } else {
            throw new RuntimeException("Client Proxy accessed from the dedicated server!");
        }
    }

    public boolean isClientSide() {
        return proxy.isClientSide();
    }

    public EntityPlayer getThePlayer() {
        return proxy.getThePlayer();
    }

    public static int calculateTotalGTVersion(int majorVersion, int minorVersion) {
        return calculateTotalGTVersion(majorVersion, minorVersion, 0);
    }

    public static int calculateTotalGTVersion(int majorVersion, int minorVersion, int patchVersion) {
        return majorVersion * 1000000 + minorVersion * 1000 + patchVersion;
    }

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        Materials.init();

        Locale.setDefault(Locale.ENGLISH);
        if (GregTechAPI.sPreloadStarted) {
            return;
        }

        proxy.onPreInitialization(event);

        GT_FML_LOGGER.debug("GTMod: Setting Configs");

        GTPreLoad.loadConfig();

        // ModularUI
        GTGuis.registerFactories();
        GTGuiTextures.init();
        GTGuiTheme.registerThemes();

        GTPreLoad.initLocalization(
            event.getModConfigurationDirectory()
                .getParentFile());
        GTPreLoad.adjustScrap();

        AE2Compat.onPreInit();

        GTPreLoad.runMineTweakerCompat();

        new LoaderGTBlockFluid().run();
        new LoaderMetaTileEntities().run();

        // populate itemstack instance for NBT check in GTRecipe
        setItemStacks();

        GTPreLoad.sortToTheEnd();
        GregTechAPI.sPreloadFinished = true;
        GT_FML_LOGGER.debug("GTMod: Preload-Phase finished!");

        GTUIInfos.init();

        if (FMLCommonHandler.instance()
            .getEffectiveSide()
            .isServer()) AssemblyLineServer.fillMap(event);
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        IconContainer.init();
        Textures.init();

        DataRegistry.registerDataManager(new ClientMetaManager(), 1000);

        if (GregTechAPI.sLoadStarted) {
            return;
        }

        proxy.onInitialization(event);

        if (Mods.Waila.isModLoaded()) {
            Waila.init();
        }
        if (Mods.HoloInventory.isModLoaded()) {
            HoloInventory.init();
        }

        GregTechAPI.sLoadFinished = true;
    }

    @Mod.EventHandler
    public void onPostInitialization(FMLPostInitializationEvent event) {
        if (GregTechAPI.sPostloadStarted) {
            return;
        }

        proxy.onPostInitialization(event);

        AE2Compat.onPostInit();

        // Register postea transformers
        new PosteaTransformers().run();

        /*
         * Until this point most crafting recipe additions, and removals, have been buffered. Go through, execute the
         * removals in bulk, and then any deferred additions. The bulk removals in particular significantly speed up the
         * recipe list modifications.
         */

        @SuppressWarnings("UnstableApiUsage") // Stable enough for this project
        Stopwatch stopwatch = Stopwatch.createStarted();
        GT_FML_LOGGER.debug("GTMod: Adding buffered Recipes.");
        // noinspection UnstableApiUsage// Stable enough for this project
        GT_FML_LOGGER.info("Executed delayed Crafting Recipes ({}). Have a Cake.", stopwatch.stop());

        GT_FML_LOGGER.debug("restarting recipe removal buffering for NHCore...");

        GT_FML_LOGGER.debug("GTMod: Saving Lang File.");
        new MachineTooltipsLoader().run();
        GTLanguageManager.sEnglishFile.save();
        GregTechAPI.sPostloadFinished = true;
        GT_FML_LOGGER.debug("GTMod: PostLoad-Phase finished!");

        new GTPostLoad().run();

        GT_FML_LOGGER.debug("GTMod: Loading finished, de-allocating temporary Init Variables.");
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        ItemFluidDisplay.setupFluids();
        proxy.onLoadComplete(event);

        if (!NewHorizonsCoreMod.isModLoaded()) {
            GT_FML_LOGGER.debug("stopping second buffering pass, likely a dev env.");
            @SuppressWarnings("UnstableApiUsage") // Stable enough for this project
            Stopwatch stopwatch = Stopwatch.createStarted();
            GT_FML_LOGGER.debug("GTMod: Adding 2nd pass of buffered Recipes.");
            // noinspection UnstableApiUsage// Stable enough for this project
            GT_FML_LOGGER
                .info("Executed 2nd pass of delayed Crafting Recipes ({}). Have another Cake.", stopwatch.stop());
        }
        GregTechAPI.sFullLoadFinished = true;
    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        proxy.onServerAboutToStart(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);
        GregTechAPI.mServerStarted = true;

        event.registerServerCommand(new GTMiscCommand());
        event.registerServerCommand(new SPCommand());
        event.registerServerCommand(new SpaceProjectCommand());
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        proxy.onServerStarted(event);
        if (RecipeMapBackend.shouldValidateLookup()) {
            GT_FML_LOGGER.info("GTRecipeLookupValidator: enabled; waiting for first server tick after server start.");
            FMLCommonHandler.instance()
                .bus()
                .register(new RecipeLookupValidationServerTickHandler());
        }
    }

    public static final class RecipeLookupValidationServerTickHandler {

        @SubscribeEvent
        public void onServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }
            FMLCommonHandler.instance()
                .bus()
                .unregister(this);
            GT_FML_LOGGER.info("GTRecipeLookupValidator: first server tick reached; starting validation.");
            RecipeLookupValidator.validateLookup();
            GT_FML_LOGGER.info("GTRecipeLookupValidator: validation completed without mismatches.");
            throw new IllegalStateException("GT recipe lookup validation found 0 issue(s); run completed.");
        }
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        proxy.onServerStopping(event);
    }

    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event) {
        proxy.onServerStopped(event);
    }

    @Mod.EventHandler
    public void onIDChangingEvent(FMLModIdMappingEvent event) {
        if (event.remappedIds.isEmpty()) return;

        GTRecipe.reInit();
    }

    @Mod.EventHandler
    public void onMissingMappings(FMLMissingMappingsEvent event) {}

    public static void logStackTrace(Throwable t) {
        final StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        GT_FML_LOGGER.error(sw);
    }

    private static String[] generateGTErr01Message(ItemStack stack) {
        // The message is presented on a per-line basis to make possible formatting in the future easier.
        return new String[] { "GT-ERR-01 at " + stack.getUnlocalizedName() + "   " + stack.getDisplayName(),
            "A recipe used an OreDict item as output directly, without copying the item before that. This is a typical CallByReference/CallByValue error.",
            "The said item will be renamed to make the invalid recipe visible.",
            "Please check all recipes that output this item, and report them to the mod that introduced the recipes.", };
    }

    @SuppressWarnings("SameParameterValue") // The method is used with one logger, but that might change in the future.
    private static void logMultilineError(Logger logger, String[] errorMessageLines) {
        for (String errorMessage : errorMessageLines) {
            logger.error(errorMessage);
        }
    }
}
