package gregtech.common.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import gregtech.api.enums.Mods;

@Config(
    modid = Mods.ModIDs.GREG_TECH,
    category = Mods.ModIDs.GREG_TECH,
    configSubDirectory = "GregTech",
    filename = "GregTech")
@Config.LangKey("GT5U.gui.config.gregtech")
public class Gregtech {

    @Config.Comment("Debug section")
    public static final Debug debug = new Debug();

    @Config.Comment("Features section")
    public static final Features features = new Features();

    @Config.Comment("General section")
    public static final General general = new General();

    @Config.Comment("Machines section")
    public static final Machines machines = new Machines();

    @Config.LangKey("GT5U.gui.config.gregtech.debug")
    public static class Debug {

        @Config.Comment("enable D1 flag (a set of debug logs)")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean D1;

        @Config.Comment("enable D2 flag (another set of debug logs)")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean D2;

        @Config.Comment("This will prevent NEI from crashing but spams the Log.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean allowBrokenRecipeMap;

        @Config.Comment("Debug parameter for entity cramming reduction.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean debugEntityCramming;

        @Config.Comment("Debug parameter for gregtech.api.util.GT_ChunkAssociatedData")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean debugWorldData;

        @Config.Comment("Debug parameter for chunk loaders.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean debugChunkloaders;
    }

    @Config.LangKey("GT5U.gui.config.gregtech.features")
    public static class Features {

        @Config.Comment("Speed up machine update thread by skipping cables. Warning: this can cause weird side effects.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean speedupMachineUpdateThread;
    }

    @Config.LangKey("GT5U.gui.config.gregtech.general")
    public static class General {

        @Config.Comment("Control number of attempts to find a valid orevein. Generally this maximum limit isn't hit, selecting a vein is cheap")
        @Config.DefaultInt(64)
        @Config.RequiresMcRestart
        public int oreveinAttempts;

        @Config.Comment("Control number of attempts to place a valid ore vein. If a vein wasn't placed due to height restrictions, completely in the water, etc, another attempt is tried.")
        @Config.DefaultInt(8)
        @Config.RequiresMcRestart
        public int oreveinMaxPlacementAttempts;

        @Config.Comment("Whether to place small ores as placer ores for an orevein.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean oreveinPlacerOres;

        @Config.Comment("Multiplier to control how many placer ores get generated.")
        @Config.DefaultInt(2)
        @Config.RequiresMcRestart
        public int oreveinPlacerOresMultiplier;

        @Config.Comment("if true, shows all the metaitems in creative and in NEI.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean doShowAllItemsInCreative;

        @Config.Comment("if true, makes the GT5U sounds multi-threaded.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean multiThreadedSounds;

        @Config.Comment("Max entity amount in the same block for entity craming.")
        @Config.DefaultInt(6)
        @Config.RequiresMcRestart
        public int maxEqualEntitiesAtOneSpot;

        @Config.Comment("Entity despawn time.")
        @Config.DefaultInt(6000)
        @Config.RequiresMcRestart
        public int itemDespawnTime;

        @Config.Comment("Controls the amount of tick over the lag averaging is done with the scanner.")
        @Config.DefaultInt(25)
        @Config.RequiresMcRestart
        public int ticksForLagAveraging;

        @Config.Comment("Controls the threshold (in ms) above which a lag warning is issued in log for a specific tile entity.")
        @Config.DefaultInt(100)
        @Config.RequiresMcRestart
        public int millisecondThesholdUntilLagWarning;

        @Config.Comment("if true, enable placeholder for material names in lang file.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean i18nPlaceholder;

        @Config.Comment("if true, sets the hardness of the mobspawers to 500 and their blast resistance to 6 000 000.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean harderMobSpawner;

        @Config.Comment("This will set the blacklist of blocks for CTM blocks.")
        @Config.DefaultStringList({ "team.chisel.block.BlockRoadLine" })
        @Config.RequiresMcRestart
        public String[] CTMBlacklist;

        @Config.Comment("This will set the whitelist of blocks for CTM blocks.")
        @Config.DefaultStringList({ "team.chisel.block.BlockCarvable", "team.chisel.block.BlockCarvableGlass" })
        @Config.RequiresMcRestart
        public String[] CTMWhitelist;

        @Config.Comment("The maximum number of nanoseconds that the cooperative scheduler will run for each tick.")
        @Config.DefaultInt(10_000_000) // 10 ms
        @Config.Name("Scheduler Max Duration (ns)")
        public int schedulerDuration;

        @Config.Comment("The maximum number of tasks that the scheduler will try to run per tick (not a hard limit).")
        @Config.DefaultInt(5)
        @Config.Name("Scheduler Target Task Count")
        public int maxTaskCount;

        @Config.Comment("0 = No Profiling. 1 = Print the time taken by the scheduler. 2 = Print the time taken by each task.")
        @Config.DefaultInt(0)
        @Config.Name("Scheduler Profiling")
        public int schedulerProfileLevel;
    }

    @Config.LangKey("GT5U.gui.config.gregtech.machines")
    public static class Machines {

        @Config.Comment("Number of ticks between sending sound packets to clients for electric machines. Default is 1.5 seconds. Trying to mitigate lag and FPS drops.")
        @Config.DefaultInt(30)
        @Config.RequiresMcRestart
        public int ticksBetweenSounds;

        @Config.Comment("This will set the blacklist for the world accelerator in TE mode.")
        @Config.DefaultStringList({ "com.rwtema.extrautils.tileentity.enderquarry.TileEntityEnderQuarry",
            "advsolar.common.tiles.TileEntityUltimateSolarPanel", "advsolar.common.tiles.TileEntitySolarPanel",
            "advsolar.common.tiles.TileEntityQuantumSolarPanel", "advsolar.common.tiles.TileEntityHybridSolarPanel",
            "advsolar.common.tiles.TileEntityAdvancedSolarPanel", "com.supsolpans.tiles.TileAdminSolarPanel",
            "com.supsolpans.tiles.TilePhotonicSolarPanel", "com.supsolpans.tiles.TileSingularSolarPanel",
            "com.supsolpans.tiles.TileSpectralSolarPanel", "emt.tile.solar.air.TileEntityAirSolar",
            "emt.tile.solar.air.TileEntityDoubleAirSolar", "emt.tile.solar.air.TileEntityTripleAirSolar",
            "emt.tile.solar.air.TileEntityQuadrupleAirSolar", "emt.tile.solar.air.TileEntityQuintupleAirSolar",
            "emt.tile.solar.air.TileEntitySextupleAirSolar", "emt.tile.solar.air.TileEntitySeptupleAirSolar",
            "emt.tile.solar.air.TileEntityOctupleAirSolar", "emt.tile.solar.compressed.TileEntityCompressedSolar",
            "emt.tile.solar.compressed.TileEntityDoubleCompressedSolar",
            "emt.tile.solar.compressed.TileEntityTripleCompressedSolar",
            "emt.tile.solar.compressed.TileEntityQuadrupleAirSolar",
            "emt.tile.solar.compressed.TileEntityQuintupleAirSolar",
            "emt.tile.solar.compressed.TileEntitySextupleAirSolar",
            "emt.tile.solar.compressed.TileEntitySeptupleAirSolar",
            "emt.tile.solar.compressed.TileEntityOctupleAirSolar", "emt.tile.solar.dark.TileEntityDarkSolar",
            "emt.tile.solar.dark.TileEntityDoubleDarkSolar", "emt.tile.solar.dark.TileEntityTripleDarkSolar",
            "emt.tile.solar.dark.TileEntityQuadrupleAirSolar", "emt.tile.solar.dark.TileEntityQuintupleAirSolar",
            "emt.tile.solar.dark.TileEntitySextupleAirSolar", "emt.tile.solar.dark.TileEntitySeptupleAirSolar",
            "emt.tile.solar.dark.TileEntityOctupleAirSolar", "emt.tile.solar.earth.TileEntityDoubleEarthSolar",
            "emt.tile.solar.earth.TileEntityEarthSolar", "emt.tile.solar.earth.TileEntityTripleEarthSolar",
            "emt.tile.solar.earth.TileEntityQuadrupleAirSolar", "emt.tile.solar.earth.TileEntityQuintupleAirSolar",
            "emt.tile.solar.earth.TileEntitySextupleAirSolar", "emt.tile.solar.earth.TileEntitySeptupleAirSolar",
            "emt.tile.solar.earth.TileEntityOctupleAirSolar", "emt.tile.solar.fire.TileEntityDoubleFireSolar",
            "emt.tile.solar.fire.TileEntityFireSolar", "emt.tile.solar.fire.TileEntityTripleFireSolar",
            "emt.tile.solar.fire.TileEntityQuadrupleAirSolar", "emt.tile.solar.fire.TileEntityQuintupleAirSolar",
            "emt.tile.solar.fire.TileEntitySextupleAirSolar", "emt.tile.solar.fire.TileEntitySeptupleAirSolar",
            "emt.tile.solar.fire.TileEntityOctupleAirSolar", "emt.tile.solar.order.TileEntityDoubleOrderSolar",
            "emt.tile.solar.order.TileEntityOrderSolar", "emt.tile.solar.order.TileEntityTripleOrderSolar",
            "emt.tile.solar.order.TileEntityQuadrupleAirSolar", "emt.tile.solar.order.TileEntityQuintupleAirSolar",
            "emt.tile.solar.order.TileEntitySextupleAirSolar", "emt.tile.solar.order.TileEntitySeptupleAirSolar",
            "emt.tile.solar.order.TileEntityOctupleAirSolar", "emt.tile.solar.water.TileEntityDoubleWaterSolar",
            "emt.tile.solar.water.TileEntityTripleWaterSolar", "emt.tile.solar.water.TileEntityWaterSolar",
            "emt.tile.solar.water.TileEntityQuadrupleAirSolar", "emt.tile.solar.water.TileEntityQuintupleAirSolar",
            "emt.tile.solar.water.TileEntitySextupleAirSolar", "emt.tile.solar.water.TileEntitySeptupleAirSolar",
            "emt.tile.solar.water.TileEntityOctupleAirSolar", "com.lulan.compactkineticgenerators.tileentity.TileCkgE",
            "com.lulan.compactkineticgenerators.tileentity.TileCkgH",
            "com.lulan.compactkineticgenerators.tileentity.TileCkgL",
            "com.lulan.compactkineticgenerators.tileentity.TileCkgM",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwaE",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwaH",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwaL",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwaM",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwmE",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwmH",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwmL",
            "com.lulan.compactkineticgenerators.tileentity.TileCkwmM", "com.supsolpans.tiles.TileSpectralSolarPanel",
            "com.supsolpans.tiles.TileSingularSolarPanel", "com.supsolpans.tiles.TileAdminSolarPanel",
            "com.supsolpans.tiles.TilePhotonicSolarPanel", "gtPlusPlus.core.tileentities.general.TileEntityFishTrap",
            "gtPlusPlus.core.tileentities.general.TileEntityDecayablesChest",
            "net.bdew.gendustry.machines.apiary.TileApiary", "goodgenerator.blocks.tileEntity.EssentiaHatch",
            "magicbees.tileentity.TileEntityApimancersDrainerCommon",
            "magicbees.tileentity.TileEntityApimancersDrainerGT" })
        @Config.RequiresMcRestart
        public String[] blacklistedTileEntiyClassNamesForWA;

        @Config.Comment("This will set the percentage how much ReinforcedGlass is Allowed in Cleanroom Walls.")
        @Config.DefaultFloat(5.0f)
        @Config.RequiresMcRestart
        public float cleanroomGlass;

        @Config.Comment("This will let machines such as drills and pumps chunkload their work area.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean enableChunkloaders;

        @Config.Comment("This will make all chunkloading machines act as World Anchors (true) or Passive Anchors (false).")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean alwaysReloadChunkloaders;

        @Config.Comment("If true, then digital chest with AE2 storage bus will be accessible only through AE2")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean disableDigitalChestsExternalAccess;

        @Config.Comment("If true, machines can explode.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineExplosions;

        @Config.Comment("If true, machine can take fire.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineFlammable;

        @Config.Comment("If true, explodes if the machine is dismantled without a wrench.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineNonWrenchExplosions;

        @Config.Comment("If true, burn the wires on explosion. (by sending IV amps into the cables)")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineWireFire;

        @Config.Comment("If true, machine will randomly explode if there is fire on adjacent blocks.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineFireExplosions;

        @Config.Comment("If true, will randomly explode if it is raining.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineRainExplosions;

        @Config.Comment("If true, will randomly explode during thunderstorm if the machine can be exposed to rain.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean machineThunderExplosions;

        @Config.Comment("If true, show the color of the dye applied to the machine as a swatch next to the GUI title.")
        @Config.DefaultBoolean(true)
        @Config.RequiresMcRestart
        public boolean coloredGUI;

        @Config.Comment("If true and if coloredGUI is enabled, hide the color swatch and show a uniform metallic tint no matter what color is applied to the machines.")
        @Config.DefaultBoolean(false)
        @Config.RequiresMcRestart
        public boolean machineMetalGUI;

        @Config.Comment("If true, Crafting Input Bus/Buffer will drop items and fluids if they cannot be returned to the AE network.")
        @Config.DefaultBoolean(true)
        public boolean allowCribDropItems;

        @Config.Comment("Enables the powerfail notification system.")
        @Config.DefaultBoolean(true)
        @Config.Name("Enable Powerfail Notifications")
        public boolean enablePowerfailNotifications;

        @Config.Comment("Format template for the ghost circuit suffix in AE2 terminal display names. Use %s as placeholder for the circuit number. Applies to all GT machines and Crafting Input Bus/Buffer.")
        @Config.DefaultString(" [%s]")
        @Config.Name("Ghost Circuit Suffix Format")
        @Config.RequiresMcRestart
        public String ghostCircuitSuffixFormat = " [%s]";

        @Config.Comment("Format template for the item slots suffix in AE2 terminal display names. Use %s as placeholder for comma-separated item names. Applies to Crafting Input Bus/Buffer manual slots and non-consumed recipe inputs (e.g. molds).")
        @Config.DefaultString(" {%s}")
        @Config.Name("Item Slots Suffix Format")
        @Config.RequiresMcRestart
        public String itemSlotsSuffixFormat = " {%s}";
    }
}
