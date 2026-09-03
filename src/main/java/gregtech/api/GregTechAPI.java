package gregtech.api;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.B;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.objects.GTHashSet;
import gregtech.api.threads.RunnableCableUpdate;
import gregtech.api.threads.RunnableMachineUpdate;
import gregtech.api.util.GTCreativeTab;
import gregtech.api.util.GTUtility;
import gregtech.common.GTDummyWorld;
import gregtech.common.covers.Base.CoverPosition;

public class GregTechAPI {

    public static final CreativeTabs TAB_GREGTECH = new GTCreativeTab("Main", "Main");
    public static final CreativeTabs TAB_GREGTECH_MATERIALS = new GTCreativeTab("Materials", "Materials");

    public static final IMetaTileEntity[] METATILEENTITIES = new IMetaTileEntity[Short.MAX_VALUE - 1];

    public static final Map<Block, Integer> MACHINE_BLOCKS = new ConcurrentHashMap<>();

    public static final Map<Integer, Byte> WIRELESS_REDSTONE = new ConcurrentHashMap<>();

    public static final Map<String, Map<String, Map<CoverPosition, Byte>>> ADVANCED_WIRELESS_REDSTONE = new ConcurrentHashMap<>();

    public static final GTHashSet sCrowbarList = new GTHashSet();
    public static final GTHashSet sScrewdriverList = new GTHashSet();
    public static final GTHashSet sWrenchList = new GTHashSet();
    public static final GTHashSet sSoftMalletList = new GTHashSet();
    public static final GTHashSet sHardHammerList = new GTHashSet();
    public static final GTHashSet sWireCutterList = new GTHashSet();
    public static final GTHashSet sSolderingToolList = new GTHashSet();
    public static final GTHashSet sSolderingMetalList = new GTHashSet();
    public static final GTHashSet sJackhammerList = new GTHashSet();

    public static final Materials_OLD[] S_GENERATED_MATERIALS = new Materials_OLD[1000];

    public static List<Runnable> sGTBlockIconload = new ArrayList<>();
    public static List<Runnable> sGTItemIconload = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    public static IIconRegister sBlockIcons;

    @SideOnly(Side.CLIENT)
    public static IIconRegister sItemIcons;

    public static int TICKS_FOR_LAG_AVERAGING = 25;
    public static int MILLISECOND_THRESHOLD_UNTIL_LAG_WARNING = 100;

    public static Block sBlockMachines;

    public static Block sBlockReinforced;
    public static Block sBlockSheetmetalGT;
    public static Block sBlockCasings1;
    public static Block sBlockCasings2;
    public static Block sBlockCasings3;
    public static Block sBlockCasings4;
    public static Block sBlockCasings5;
    public static Block sBlockCasings6;
    public static Block sBlockCasings8;
    public static Block sBlockCasings9;
    public static Block sBlockCasings10;
    public static Block sBlockCasings11;
    public static Block sBlockCasings12;
    public static Block sBlockCasings13;
    public static Block sBlockCasings14;
    public static Block sSolenoidCoilCasings;
    public static Block sBlockCasingsNH;
    public static Block sBlockCasingsFoundry;
    public static Block sBlockCasingsBEC;
    public static Block sBlockGlass1;
    public static Block sBlockTintedGlass;

    public static boolean sDrinksAlwaysDrinkable = false;
    public static boolean sMultiThreadedSounds = false;
    public static boolean sDoShowAllItemsInCreative = false;
    public static boolean sColoredGUI = true;
    public static boolean sMachineMetalGUI = false;
    public static boolean sMachineExplosions = true;
    public static boolean sMachineFlammable = true;
    public static boolean sMachineNonWrenchExplosions = true;
    public static boolean sMachineRainExplosions = true;
    public static boolean sMachineThunderExplosions = true;
    public static boolean sMachineFireExplosions = true;
    public static boolean sMachineWireFire = true;
    public static boolean mOutputRF = false;
    public static boolean mInputRF = false;
    public static boolean mRFExplosions = false;
    public static boolean mServerStarted = false;

    public static int mEUtoRF = 360;
    public static int mRFtoEU = 20;

    public static boolean mUseOnlyGoodSolderingMaterials = false;

    public static boolean sUnificationEntriesRegistered = false;
    public static boolean sPreloadStarted = false;
    public static boolean sPreloadFinished = false;
    public static boolean sLoadStarted = false;
    public static boolean sLoadFinished = false;
    public static boolean sPostloadStarted = false;
    public static boolean sPostloadFinished = false;
    public static boolean sFullLoadFinished = false;

    @SuppressWarnings("unchecked")
    private static final IntFunction<TileEntity>[] teCreators = new IntFunction[16];

    private static final Set<Class<?>> dummyWorlds = new HashSet<>();

    static {
        dummyWorlds.add(GTDummyWorld.class);
    }

    public static void addDummyWorld(Class<?> clazz) {
        dummyWorlds.add(clazz);
    }

    public static boolean isDummyWorld(@Nonnull World world) {
        return dummyWorlds.contains(world.getClass());
    }

    public static void causeMachineUpdate(World world, int x, int y, int z) {
        if (world != null && !world.isRemote && !isDummyWorld(world)) {
            RunnableMachineUpdate.setMachineUpdateValues(world, x, y, z);
        }
    }

    public static void causeCableUpdate(World world, int x, int y, int z) {
        if (world == null || world.isRemote || isDummyWorld(world)) {
            return;
        }
        RunnableCableUpdate.setCableUpdateValues(world, x, y, z);
    }

    /**
     * Adds a Multi-Machine Block, like my Machine Casings for example. You should call @causeMachineUpdate
     * in @Block.breakBlock and in {@link Block#onBlockAdded} of your registered Block. You don't need to register
     * TileEntities which implement {@link IMachineBlockUpdateable}
     *
     * @param block the Block
     * @param meta  the Metadata of the Blocks as Bitmask! -1 or ~0 for all Meta-values
     */
    public static void registerMachineBlock(Block block, int meta) {
        if (block == null) {
            return;
        }
        MACHINE_BLOCKS.put(block, meta);
    }

    public static boolean isMachineBlock(Block block, int meta) {
        if (block != null) {
            Integer id = MACHINE_BLOCKS.get(block);
            if (id != null) {
                if (id == -1) {
                    return true;
                }
                return (id & B[meta]) != 0;
            }
        }
        return false;
    }

    public static BaseMetaTileEntity constructBaseMetaTileEntity() {
        try {
            return new BaseMetaTileEntity();
        } catch (Exception e) {
            GT_FML_LOGGER.error("GTMod: Fatal Error occurred while initializing TileEntities, crashing Minecraft.");
            GT_FML_LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void setBlockIconRegister(IIconRegister iconRegister) {
        sBlockIcons = iconRegister;
    }

    public static void registerTileEntityConstructor(int meta, IntFunction<TileEntity> constructor) {
        if (meta < 0 || meta > 15) {
            throw new IllegalArgumentException("Invalid meta: " + meta + " (must be " + 0 + "-" + 15 + ")");
        }

        if (constructor == null) {
            throw new IllegalArgumentException("Constructor cannot be null");
        }

        IntFunction<TileEntity> existing = teCreators[meta];

        if (existing != null) {
            throw new IllegalStateException("Constructor already registered for meta " + meta);
        }

        teCreators[meta] = constructor;
    }

    public static TileEntity createTileEntity(int meta) {
        meta = GTUtility.clamp(meta, 0, 15);
        if (teCreators[meta] == null) {
            return null;
        }
        return teCreators[meta].apply(meta);
    }
}
