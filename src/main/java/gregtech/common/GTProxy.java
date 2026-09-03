package gregtech.common;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.debugEntityCramming;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.ImmutableSet;
import com.gtnewhorizon.gtnhlib.keybind.SyncedKeybind;
import com.gtnewhorizon.gtnhlib.teams.TeamDataRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.IProjectileItem;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GTChunkManager;
import gregtech.api.objects.ItemData;
import gregtech.api.threads.RunnableCableUpdate;
import gregtech.api.threads.RunnableMachineUpdate;
import gregtech.api.util.GTBlockMap;
import gregtech.api.util.GTChunkAssociatedData;
import gregtech.api.util.GTClientPreference;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.api.util.WorldSpawnedEventBuilder;
import gregtech.client.renderer.waila.TTRenderGTProgressBar;
import gregtech.common.config.OPStuff;
import gregtech.common.data.GTPowerfailTracker;
import gregtech.common.data.WirelessEnergyHatchManager;
import gregtech.common.handlers.OffhandToolFunctionalityHandler;
import gregtech.common.misc.GlobalEnergyWorldSavedData;
import gregtech.common.misc.WirelessChargerManager;
import gregtech.common.misc.spaceprojects.SpaceProjectWorldSavedData;
import gregtech.common.networkanalyzer.events.NetworkAnalyzerPlayerTracker;
import gregtech.common.powergoggles.PowerGogglesWorldSavedData;
import gregtech.common.powergoggles.handlers.PowerGogglesEventHandler;
import gregtech.nei.GTNEIDefaultHandler;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class GTProxy implements IFuelHandler {

    private static final EnumSet<OreGenEvent.GenerateMinable.EventType> PREVENTED_ORES = EnumSet.of(
        OreGenEvent.GenerateMinable.EventType.COAL,
        OreGenEvent.GenerateMinable.EventType.IRON,
        OreGenEvent.GenerateMinable.EventType.GOLD,
        OreGenEvent.GenerateMinable.EventType.DIAMOND,
        OreGenEvent.GenerateMinable.EventType.REDSTONE,
        OreGenEvent.GenerateMinable.EventType.LAPIS,
        OreGenEvent.GenerateMinable.EventType.QUARTZ);
    public final GTBlockMap<Boolean> mCTMBlockCache = new GTBlockMap<>();
    public boolean mIgnoreTcon = true;
    public int mMaxEqualEntitiesAtOneSpot = 3;
    public int mItemDespawnTime = 6000;

    /** This enables ambient-occlusion smooth lighting on tiles */
    public boolean mRenderTileAmbientOcclusion = true;
    /** This enables rendering of glowing textures */
    public boolean mRenderGlowTextures = true;
    /** Render flipped textures */
    public boolean mRenderFlippedMachinesFlipped = true;
    /** This enables indicators on input/output hatches */
    public boolean mRenderIndicatorsOnHatch = true;
    /** This enables the rendering of dirt particles if pollution is enabled too */
    public boolean mRenderDirtParticles = true;
    /** This enables the rendering of the pollution fog if pollution is enabled too */
    public boolean mRenderPollutionFog = true;
    /** This enables BaseMetaTileEntity block updates handled by BlockUpdateHandler */
    public boolean mUseBlockUpdateHandler = false;
    /** This makes cover tabs visible on GregTech machines */
    public boolean mCoverTabsVisible = true;
    /** This controls whether cover tabs display on the left (default) or right side of the UI */
    public boolean mCoverTabsFlipped = false;
    /** How verbose should tooltips be? 0: disabled, 1: one-line, 2: normal, 3+: extended */
    public int mTooltipVerbosity = 2;
    /** How verbose should tooltips be when LSHIFT is held? 0: disabled, 1: one-line, 2: normal, 3+: extended */
    public int mTooltipShiftVerbosity = 3;
    /**
     * Which style to use for title tab on machine GUI? 0: text tab split-dark, 1: text tab unified, 2: item icon tab
     */
    public int mTitleTabStyle = 0;
    /// Which style should tooltip separator lines have? 0: empty line, 1: dashed line, 2+: continuous line
    /// Use [#getSeparatorStyle()] instead.
    public int separatorStyle = 2;

    public enum SeparatorStyle {
        Empty,
        Dashes,
        ContinuousLine
    }

    public SeparatorStyle getSeparatorStyle() {
        return switch (separatorStyle) {
            case 0 -> SeparatorStyle.Empty;
            case 1 -> SeparatorStyle.Dashes;
            default -> SeparatorStyle.ContinuousLine;
        };
    }

    /**
     * Which style should tooltip finisher separator lines have? 0: no line, 1: empty line, 2: dashed line, 3+:
     * continuous line
     */
    public int tooltipFinisherStyle = 1;
    /**
     * Enables scrolling up while hovering the ghost circuit of a machine UI to increment the circuit number instead of
     * decrement
     */
    public boolean invertCircuitScrollDirection = false;
    /** Whether to show seconds or ticks on NEI */
    public boolean mNEIRecipeSecondMode = true;
    /** This enables "Recipe by" display on NEI */
    public boolean mNEIRecipeOwner = false;
    /** This enables showing stack traces where the recipe was added. Reboot needed */
    public boolean mNEIRecipeOwnerStackTrace = false;
    /** This enables showing original voltage when overclocked */
    public boolean mNEIOriginalVoltage = false;
    /** This enables the green->red durability for an item's damage value */
    public boolean mRenderItemDurabilityBar = true;
    /** This enables the blue charge bar for an electric item's charge */
    public boolean mRenderItemChargeBar = true;
    /** This enables showing voltage tier of transformer for Waila, instead of raw voltage number */
    public boolean mWailaTransformerVoltageTier = true;
    /** This enables showing the cpu load in nanoseconds via waila. */
    public boolean wailaAverageNS = false;
    /** Color preset of the waila progress bar. */
    public TTRenderGTProgressBar.ProgressBarColor wailaProgressBarColorPreset = TTRenderGTProgressBar.ProgressBarColor.Green;
    /** First color of the waila progress bar. */
    public int wailaProgressBarColor1 = 0xFFDD5555;
    /** Second color of the waila progress bar. */
    public int wailaProgressBarColor2 = 0xFFDD2222;
    /** First color of the waila progress bar's border (top and left). */
    public int wailaProgressBorderColor1 = 0xFF505050;
    /** Second color of the waila progress bar's border (bottom and right). */
    public int wailaProgressBorderColor2 = 0xFF505050;

    public Set<Item> toolboxBans = ImmutableSet.of();

    private static final Map<String, Integer> oreDictBurnTimes = new Object2IntOpenHashMap<>();

    // Locking
    public final ReentrantLock TICK_LOCK = new ReentrantLock();

    private final ConcurrentMap<UUID, GTClientPreference> mClientPrefernces = new ConcurrentHashMap<>();
    /** A fast lookup for players. */
    private static Map<UUID, EntityPlayerMP> PLAYERS_BY_UUID;
    private static Map<String, UUID> UUID_BY_NAME;
    public WirelessChargerManager wirelessChargerManager;
    public GTPowerfailTracker powerfailTracker;
    public WirelessEnergyHatchManager wirelessEnergyHatchManager;

    public SyncedKeybind CTRL_KEYBIND;

    static {
        oreDictBurnTimes.put("dustTinyWood", 11);
        oreDictBurnTimes.put("dustTinySodium", 44);
        oreDictBurnTimes.put("dustSmallWood", 25);
        oreDictBurnTimes.put("dustSmallSodium", 100);
        oreDictBurnTimes.put("dustWood", 100);
        oreDictBurnTimes.put("dustTinyCoal", 177);
        oreDictBurnTimes.put("dustTinyCharcoal", 177);
        oreDictBurnTimes.put("dustTinyLignite", 166);
        oreDictBurnTimes.put("plateWood", 300);
        oreDictBurnTimes.put("dustSmallLignite", 375);
        oreDictBurnTimes.put("dustSodium", 400);
        oreDictBurnTimes.put("dustSmallCoal", 400);
        oreDictBurnTimes.put("dustSmallCharcoal", 400);
        oreDictBurnTimes.put("dustTinyLithium", 888);
        oreDictBurnTimes.put("dustTinyCaesium", 888);
        oreDictBurnTimes.put("gemLignite", 1200);
        oreDictBurnTimes.put("crushedLignite", 1200);
        oreDictBurnTimes.put("dustImpureLignite", 1200);
        oreDictBurnTimes.put("dustLignite", 1200);
        oreDictBurnTimes.put("dustSulfur", 1600);
        oreDictBurnTimes.put("gemCoal", 1600);
        oreDictBurnTimes.put("crushedCoal", 1600);
        oreDictBurnTimes.put("dustImpureCoal", 1600);
        oreDictBurnTimes.put("dustCoal", 1600);
        oreDictBurnTimes.put("gemCharcoal", 1600);
        oreDictBurnTimes.put("crushedCharcoal", 1600);
        oreDictBurnTimes.put("dustImpureCharcoal", 1600);
        oreDictBurnTimes.put("dustCharcoal", 1600);
        oreDictBurnTimes.put("dustSmallLithium", 2000);
        oreDictBurnTimes.put("dustSmallCaesium", 2000);
        oreDictBurnTimes.put("gemSodium", 4000);
        oreDictBurnTimes.put("crushedSodium", 4000);
        oreDictBurnTimes.put("dustImpureSodium", 4000);
        oreDictBurnTimes.put("gemLithium", 6000);
        oreDictBurnTimes.put("crushedLithium", 6000);
        oreDictBurnTimes.put("dustImpureLithium", 6000);
        oreDictBurnTimes.put("dustLithium", 6000);
        oreDictBurnTimes.put("gemCaesium", 6000);
        oreDictBurnTimes.put("crushedCaesium", 6000);
        oreDictBurnTimes.put("dustImpureCaesium", 6000);
        oreDictBurnTimes.put("dustCaesium", 6000);
        oreDictBurnTimes.put("blockLignite", 12000);
        oreDictBurnTimes.put("blockCharcoal", 16000);
        oreDictBurnTimes.put("gemDiamond", 102400);
        oreDictBurnTimes.put("blockDiamond", 1024000);
        oreDictBurnTimes.put("crushedDiamond", 102400);
        oreDictBurnTimes.put("dustImpureDiamond", 102400);
        oreDictBurnTimes.put("dustDiamond", 102400);
        oreDictBurnTimes.put("dustSmallDiamond", 25600);
        oreDictBurnTimes.put("dustTinyDiamond", 11378);
    }

    public GTProxy() {
        GameRegistry.registerFuelHandler(this);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.ORE_GEN_BUS.register(this);
        FMLCommonHandler.instance()
            .bus()
            .register(this);
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
            .getRegisteredFluidContainerData()) {
            onFluidContainerRegistration(new FluidContainerRegistry.FluidContainerRegisterEvent(tData));
        }
    }

    public boolean isClientSide() {
        return false;
    }

    public EntityPlayer getThePlayer() {
        return null;
    }

    public void onPreInitialization(FMLPreInitializationEvent event) {
        // spotless:off
        GT_FML_LOGGER.debug("GTMod: Preload-Phase started!");

        GregTechAPI.sPreloadStarted = true;
        this.mIgnoreTcon = OPStuff.ignoreTinkerConstruct;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GT_FML_LOGGER.debug("GTMod: Getting required Items of other Mods.");

        ItemList.Cell_Universal_Fluid.set(GTModHandler.getIC2Item("FluidCell", 1L));
        ItemList.Cell_Empty.set(GTModHandler.getIC2Item("cell", 1L, GTModHandler.getIC2Item("cellEmpty", 1L, GTModHandler.getIC2Item("emptyCell", 1L))));
        ItemList.Cell_Water.set(GTModHandler.getIC2Item("waterCell", 1L, GTModHandler.getIC2Item("cellWater", 1L)));
        ItemList.Cell_Lava.set(GTModHandler.getIC2Item("lavaCell", 1L, GTModHandler.getIC2Item("cellLava", 1L)));
        ItemList.Cell_Air.set(GTModHandler.getIC2Item("airCell", 1L, GTModHandler.getIC2Item("cellAir", 1L, GTModHandler.getIC2Item("cellOxygen", 1L))));

        ItemList.Dye_Bonemeal.set(new ItemStack(Items.dye, 1, 15));
        ItemList.Dye_SquidInk.set(new ItemStack(Items.dye, 1, 0));
        ItemList.Dye_Cocoa.set(new ItemStack(Items.dye, 1, 3));

        OrePrefixes.bucket.mContainerItem = new ItemStack(Items.bucket, 1);
        OrePrefixes.cellPlasma.mContainerItem = ItemList.Cell_Empty.get(1L);
        OrePrefixes.cellMolten.mContainerItem = ItemList.Cell_Empty.get(1L);
        OrePrefixes.cell.mContainerItem = ItemList.Cell_Empty.get(1L);

        // Register chunk manager with Forge
        GTChunkManager.init();
        // spotless:on

    }

    public void onInitialization(FMLInitializationEvent event) {
        GT_FML_LOGGER.debug("GTMod: Beginning Load-Phase.");

        MinecraftForge.EVENT_BUS.register(new SpaceProjectWorldSavedData());
        MinecraftForge.EVENT_BUS.register(new GlobalEnergyWorldSavedData(""));
        MinecraftForge.EVENT_BUS.register(new PowerGogglesWorldSavedData());
        MinecraftForge.EVENT_BUS.register(PowerGogglesEventHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(new OffhandToolFunctionalityHandler());
        NetworkAnalyzerPlayerTracker.init();
        CTRL_KEYBIND = SyncedKeybind.create(Keyboard.KEY_LCONTROL);

        GregTechAPI.sLoadStarted = true;
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
            .getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GTLanguageManager.writePlaceholderStrings();
    }

    public void onPostInitialization(FMLPostInitializationEvent event) {
        GT_FML_LOGGER.debug("GTMod: Beginning PostLoad-Phase.");
        GregTechAPI.sPostloadStarted = true;

        // This needs to happen late enough that all of the fluids we need have been registered.
        // onInitialization() seems to be too early, as the New Horizons Core Mod registers some fluids in post-load.
        // MetaGeneratedItem98.init();

        GTOreDictUnificator.addItemData(new ItemStack(Items.iron_door, 1), new ItemData(Materials_OLD.Iron, 21772800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.wooden_door, 1, 32767), new ItemData(Materials_OLD.Wood, 21772800L));
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry
            .getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        GT_FML_LOGGER.debug("GTMod: Adding Configs specific for MetaTileEntities");
        for (int i = 1; i < GregTechAPI.METATILEENTITIES.length; i++) {
            if (GregTechAPI.METATILEENTITIES[i] != null) {
                try {
                    GregTechAPI.METATILEENTITIES[i].onConfigLoad();
                } catch (Exception e) {
                    GT_FML_LOGGER.error("Could not load config for MTE {}", GregTechAPI.METATILEENTITIES[i], e);
                }
            }
        }
        GT_FML_LOGGER.debug("GTMod: Adding Tool Usage Crafting Recipes for OreDict Items.");
        // Cover GUI needs to know text color which can be configured with resource packs. In theory it's not needed on
        // server, but it's just convenient to be able to write GUI code without side check. This will be reworked with
        // MUI2, but for the time being it stays here. -- miozune
        CoverRegistry.reloadCoverColorOverrides();
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {}

    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        // spotless:off
        GT_FML_LOGGER.debug("GTMod: firing FMLServerAboutToStartEvent !");
        GTChunkAssociatedData.clearAll();
        PLAYERS_BY_UUID = new HashMap<>();
        UUID_BY_NAME = new HashMap<>();
        isFirstWorldTick = true;
        wirelessChargerManager = new WirelessChargerManager();
        powerfailTracker = new GTPowerfailTracker();
        wirelessEnergyHatchManager = new WirelessEnergyHatchManager();
        FMLCommonHandler.instance().bus().register(wirelessChargerManager);
        FMLCommonHandler.instance().bus().register(powerfailTracker);
        MinecraftForge.EVENT_BUS.register(powerfailTracker);
        TeamDataRegistry.register(GTPowerfailTracker.DATA_NAME, GTPowerfailTracker.PowerfailData::new);
        FMLCommonHandler.instance().bus().register(wirelessEnergyHatchManager);
        // spotless:off
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        GT_FML_LOGGER.debug("GTMod: firing FMLServerStartingEvent !");
        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if ((tData.filledContainer.getItem() == Items.potionitem) && (tData.filledContainer.getItemDamage() == 0)) {
                tData.fluid.amount = 0;
                break;
            }
        }
        for (int i = 1; i < GregTechAPI.METATILEENTITIES.length; i++) {
            if (GregTechAPI.METATILEENTITIES[i] != null) {
                try {
                    GregTechAPI.METATILEENTITIES[i].onServerStart();
                } catch (Exception e) {
                    throw new RuntimeException("Could not call onServerStart MTE " + GregTechAPI.METATILEENTITIES[i], e);
                }
            }
        }
    }

    public void onServerStarted(FMLServerStartedEvent event) {
        GT_FML_LOGGER.debug("GTMod: Cleaning up all OreDict Crafting Recipes, which have an empty List in them, since they are never meeting any Condition.");
        List<IRecipe> tList = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < tList.size(); i++) {
            if ((tList.get(i) instanceof ShapedOreRecipe)) {
                for (Object tObject : ((ShapedOreRecipe) tList.get(i)).getInput()) {
                    if (((tObject instanceof List)) && (((List<?>) tObject).isEmpty())) {
                        tList.remove(i--);
                        break;
                    }
                }
            } else if ((tList.get(i) instanceof ShapelessOreRecipe)) {
                for (Object tObject : ((ShapelessOreRecipe) tList.get(i)).getInput()) {
                    if (((tObject instanceof List)) && (((List<?>) tObject).isEmpty())) {
                        tList.remove(i--);
                        break;
                    }
                }
            }
        }
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        GregTechAPI.WIRELESS_REDSTONE.clear();
        GregTechAPI.ADVANCED_WIRELESS_REDSTONE.clear();
    }

    public void onServerStopped(FMLServerStoppedEvent event) {
        // spotless:off
        if (wirelessChargerManager != null) {
            FMLCommonHandler.instance().bus().unregister(wirelessChargerManager);
        }
        if (powerfailTracker != null) {
            FMLCommonHandler.instance().bus().unregister(powerfailTracker);
            MinecraftForge.EVENT_BUS.unregister(powerfailTracker);
        }
        if (wirelessEnergyHatchManager != null) {
            FMLCommonHandler.instance().bus().unregister(wirelessEnergyHatchManager);
        }
        wirelessChargerManager = null;
        powerfailTracker = null;
        wirelessEnergyHatchManager = null;
        PLAYERS_BY_UUID = null;
        UUID_BY_NAME = null;
        // spotless:on
        PowerGogglesEventHandler.getInstance()
            .onServerStopped(event);
        GTChunkManager.instance.onServerStopped();

    }

    /**
     * Tells {@link GTNEIDefaultHandler} to reload recipes.
     */
    public void reloadNEICache() {}

    /**
     * Logging in to server or {@link #reloadNEICache} being called increases the count.
     */
    public int getNEIReloadCount() {
        return 0;
    }

    @SubscribeEvent
    public void onArrowNockEvent(ArrowNockEvent aEvent) {
        if (GTUtility.isStackValid(aEvent.result)
            && (GTUtility.getProjectile(SubTag.PROJECTILE_ARROW, aEvent.entityPlayer.inventory) != null)) {
            aEvent.entityPlayer.setItemInUse(
                aEvent.result,
                aEvent.result.getItem()
                    .getMaxItemUseDuration(aEvent.result));
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onArrowLooseEvent(ArrowLooseEvent aEvent) {
        ItemStack aArrow = GTUtility.getProjectile(SubTag.PROJECTILE_ARROW, aEvent.entityPlayer.inventory);
        if (GTUtility.isStackValid(aEvent.bow) && (aArrow != null) && ((aEvent.bow.getItem() instanceof ItemBow))) {
            float tSpeed = aEvent.charge / 20.0F;
            tSpeed = (tSpeed * tSpeed + tSpeed * 2.0F) / 3.0F;
            if (tSpeed < 0.1D) {
                return;
            }
            if (tSpeed > 1.0D) {
                tSpeed = 1.0F;
            }
            EntityArrow tArrowEntity = ((IProjectileItem) aArrow.getItem()).getProjectile(
                SubTag.PROJECTILE_ARROW,
                aArrow,
                aEvent.entityPlayer.worldObj,
                aEvent.entityPlayer,
                tSpeed * 2.0F);
            if (tSpeed >= 1.0F) {
                tArrowEntity.setIsCritical(true);
            }
            int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, aEvent.bow);
            if (tLevel > 0) {
                tArrowEntity.setDamage(tArrowEntity.getDamage() + tLevel * 0.5D + 0.5D);
            }
            tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, aEvent.bow);
            if (tLevel > 0) {
                tArrowEntity.setKnockbackStrength(tLevel);
            }
            tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, aEvent.bow);
            if (tLevel > 0) {
                tArrowEntity.setFire(tLevel * 100);
            }
            aEvent.bow.damageItem(1, aEvent.entityPlayer);
            aEvent.bow.getItem();

            new WorldSpawnedEventBuilder.SoundAtEntityEventBuilder().setPitch(0.64893958288F + tSpeed * 0.5F)
                .setVolume(1f)
                .setIdentifier(SoundResource.RANDOM_BOW)
                .setEntity(aEvent.entityPlayer)
                .setWorld(aEvent.entityPlayer.worldObj)
                .run();

            tArrowEntity.canBePickedUp = 1;
            if (!aEvent.entityPlayer.capabilities.isCreativeMode) {
                aArrow.stackSize -= 1;
            }
            if (aArrow.stackSize == 0) {
                GTUtility.removeNullStacksFromInventory(aEvent.entityPlayer.inventory);
            }
            if (!aEvent.entityPlayer.worldObj.isRemote) {
                aEvent.entityPlayer.worldObj.spawnEntityInWorld(tArrowEntity);
            }
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEndermanTeleportEvent(EnderTeleportEvent aEvent) {
        if (((aEvent.entityLiving instanceof EntityEnderman))
            && (aEvent.entityLiving.getActivePotionEffect(Potion.weakness) != null)) {
            aEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntitySpawningEvent(EntityJoinWorldEvent aEvent) {
        if (aEvent.entity == null) {
            return;
        }

        if (aEvent.entity.worldObj.isRemote) {
            return;
        }

        if ((aEvent.entity instanceof EntityItem)) {
            ((EntityItem) aEvent.entity)
                .setEntityItemStack(GTOreDictUnificator.get(true, ((EntityItem) aEvent.entity).getEntityItem(), true));
        }
    }

    @SubscribeEvent
    public void onPlayerJoinEvent(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player != null) {
            event.player.isClientWorld();
        }
    }

    @SubscribeEvent
    public void onOreGenEvent(OreGenEvent.GenerateMinable generateMinable) {
        if (generateMinable.generator instanceof WorldGenMinable && PREVENTED_ORES.contains(generateMinable.type)) {
            generateMinable.setResult(Result.DENY);
        }
    }

    @SubscribeEvent
    public void onBlockBreakingEvent(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player == null) {
            return;
        }

        ItemStack item = event.getPlayer()
            .getCurrentEquippedItem();
        if (item == null) {
            return;
        }

        // if (item.getItem() instanceof MetaGeneratedTool tool) {
        // IToolStats stats = tool.getToolStats(item);
        // if (stats == null) return;
        //
        // TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
        // stats.onBreakBlock(player, event.x, event.y, event.z, event.block, event.blockMetadata, tile, event);
        // }
    }

    @SubscribeEvent
    public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent aEvent) {
        if (aEvent.harvester == null) return;

        ItemStack aStack = aEvent.harvester.getCurrentEquippedItem();
        if (aStack == null) {
            return;
        }

        // if ((aStack.getItem() instanceof MetaGeneratedTool tool)) {
        // tool.onHarvestBlockEvent(
        // aEvent.drops,
        // aStack,
        // aEvent.harvester,
        // aEvent.block,
        // aEvent.x,
        // aEvent.y,
        // aEvent.z,
        // aEvent.blockMetadata,
        // aEvent.fortuneLevel,
        // aEvent.isSilkTouching,
        // aEvent);
        // }
    }

    @SubscribeEvent
    public void onFluidContainerRegistration(FluidContainerRegistry.FluidContainerRegisterEvent aFluidEvent) {
        if ((aFluidEvent.data.filledContainer.getItem() == Items.potionitem)
            && (aFluidEvent.data.filledContainer.getItemDamage() == 0)) {
            aFluidEvent.data.fluid.amount = 0;
        }
        GTOreDictUnificator.addToBlacklist(aFluidEvent.data.emptyContainer);
        GTOreDictUnificator.addToBlacklist(aFluidEvent.data.filledContainer);
        GTUtility.insertInFluidToContainersMap(aFluidEvent.data);
    }

    @SubscribeEvent
    public void onServerTickEvent(TickEvent.ServerTickEvent aEvent) {
        if (aEvent.side.isServer()) {
            if (aEvent.phase == TickEvent.Phase.START) {
                TICK_LOCK.lock();
            } else {
                TICK_LOCK.unlock();
                RunnableMachineUpdate.endTick();
                RunnableCableUpdate.endTick();
            }
        }
    }

    private boolean isFirstWorldTick = true;

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent aEvent) {
        if (!aEvent.side.isServer()) return;
        if (isFirstWorldTick) {
            isFirstWorldTick = false;
            GTValues.worldTickHappened = true;
        }
        if ((aEvent.world.getTotalWorldTime() % 100L == 0L)
            && ((this.mItemDespawnTime != 6000) || (this.mMaxEqualEntitiesAtOneSpot > 0))) {
            long startTime = System.nanoTime();
            double oldX = 0, oldY = 0, oldZ = 0;
            if (debugEntityCramming && (!aEvent.world.loadedEntityList.isEmpty())) {
                GT_FML_LOGGER.debug("CRAM: Entity list size {}", aEvent.world.loadedEntityList.size());
            }
            for (int i = 0; i < aEvent.world.loadedEntityList.size(); i++) {
                if ((aEvent.world.loadedEntityList.get(i) instanceof Entity)) {
                    Entity tEntity = aEvent.world.loadedEntityList.get(i);
                    if (((tEntity instanceof EntityItem)) && (this.mItemDespawnTime != 6000)
                        && (((EntityItem) tEntity).lifespan == 6000)) {
                        ((EntityItem) tEntity).lifespan = this.mItemDespawnTime;
                    } else if (((tEntity instanceof EntityLivingBase)) && (this.mMaxEqualEntitiesAtOneSpot > 0)
                        && (!(tEntity instanceof EntityPlayer))
                        && (tEntity.canBePushed())
                        && (((EntityLivingBase) tEntity).getHealth() > 0.0F)) {
                            List<Entity> tList = tEntity.worldObj.getEntitiesWithinAABBExcludingEntity(
                                tEntity,
                                tEntity.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                            Class<? extends Entity> tClass = tEntity.getClass();
                            int tEntityCount = 1;
                            if (tList != null) {
                                for (Object o : tList) {
                                    if ((o != null) && (o.getClass() == tClass)) {
                                        tEntityCount++;
                                    }
                                }
                            }
                            if (tEntityCount > this.mMaxEqualEntitiesAtOneSpot) {
                                if (debugEntityCramming) {
                                    // Cheeseball way of not receiving a bunch of spam caused by 1 location
                                    // obviously fails if there are crammed entities in more than one spot.
                                    if (tEntity.posX != oldX && tEntity.posY != oldY && tEntity.posZ != oldZ) {
                                        GT_FML_LOGGER.debug(
                                            "CRAM: Excess entities: {} at X {} Y {} Z {}",
                                            tEntityCount,
                                            tEntity.posX,
                                            tEntity.posY,
                                            tEntity.posZ);
                                        oldX = tEntity.posX;
                                        oldY = tEntity.posY;
                                        oldZ = tEntity.posZ;
                                    }
                                }
                                tEntity.attackEntityFrom(
                                    DamageSource.inWall,
                                    tEntityCount - this.mMaxEqualEntitiesAtOneSpot);
                            }
                        }
                }
            }
            if (debugEntityCramming && (!aEvent.world.loadedEntityList.isEmpty())) {
                GT_FML_LOGGER
                    .debug("CRAM: Time spent checking {} microseconds", (System.nanoTime() - startTime) / 1000);
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        for (TileEntity tileEntity : event.world.loadedTileEntityList) {
            if (tileEntity instanceof IGregTechTileEntity) {
                tileEntity.onChunkUnload();
            }
        }
    }

    public GTClientPreference getClientPreference(UUID aPlayerID) {
        return mClientPrefernces.get(aPlayerID);
    }

    public void setClientPreference(UUID aPlayerID, GTClientPreference aPreference) {
        mClientPrefernces.put(aPlayerID, aPreference);
    }

    @Override
    public int getBurnTime(ItemStack stack) {
        if (stack == null || stack.getItem() == null) {
            return 0;
        }
        int burnTime = 0;
        final NBTTagCompound nbtTag = stack.getTagCompound();
        if (nbtTag != null) {
            // See if we have something defined by NBT
            final short gtBurnTime = nbtTag.getShort("GT.ItemFuelValue");
            burnTime = Math.max(burnTime, gtBurnTime);
        } else {
            // If not check the ore dict
            burnTime = Math.max(burnTime, burnTimeFromOreDict(stack));
        }

        // If we have something from the GT MetaGenerated_Item, ItemFuelValue, or OreDict return
        if (burnTime > 0) return burnTime;

        // Otherwise, a few more checks
        if (GTUtility.areStacksEqual(stack, new ItemStack(Blocks.wooden_button, 1))) return 150;
        else if (GTUtility.areStacksEqual(stack, new ItemStack(Blocks.ladder, 1))) return 100;
        else if (GTUtility.areStacksEqual(stack, new ItemStack(Items.sign, 1))) return 600;
        else if (GTUtility.areStacksEqual(stack, new ItemStack(Items.wooden_door, 1))) return 600;

        return 0;
    }

    private static int burnTimeFromOreDict(ItemStack stack) {
        List<String> list = new ArrayList<>();
        for (int i : OreDictionary.getOreIDs(stack)) {
            String oreName = OreDictionary.getOreName(i);
            list.add(oreName);
        }
        boolean seen = false;
        int best = 0;
        // noinspection ForLoopReplaceableByForEach
        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            int gtValue = oreDictBurnTimes.getOrDefault(list.get(i), 0);
            if (!seen || gtValue > best) {
                seen = true;
                best = gtValue;
            }
        }
        return seen ? best : 0;
    }

    @SubscribeEvent
    public void handleChunkLoadEvent(ChunkDataEvent.Load event) {}

    @SubscribeEvent
    public void onBlockBreakSpeedEvent(PlayerEvent.BreakSpeed aEvent) {
        if (aEvent.entityPlayer != null) {
            ItemStack aStack = aEvent.entityPlayer.getCurrentEquippedItem();
            // if ((aStack != null) && ((aStack.getItem() instanceof MetaGeneratedTool))) {
            // aEvent.newSpeed = ((MetaGeneratedTool) aStack.getItem()).onBlockBreakSpeedEvent(
            // aEvent.newSpeed,
            // aStack,
            // aEvent.entityPlayer,
            // aEvent.block,
            // aEvent.x,
            // aEvent.y,
            // aEvent.z,
            // aEvent.metadata,
            // aEvent);
            // }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerMap$onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP player)) {
            // this should never happen
            return;
        }
        final UUID UUID = player.getGameProfile()
            .getId();
        if (UUID != null) {
            PLAYERS_BY_UUID.put(UUID, player);
            UUID_BY_NAME.put(player.getCommandSenderName(), UUID);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void playerMap$onPlayerLeft(PlayerLoggedOutEvent event) {
        if (!(event.player instanceof EntityPlayerMP player)) {
            // this should never happen
            return;
        }
        final UUID UUID = player.getGameProfile()
            .getId();
        if (UUID != null) {
            PLAYERS_BY_UUID.remove(UUID);
            UUID_BY_NAME.remove(UUID);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerMap$onPlayerChangeDim(PlayerChangedDimensionEvent event) {
        if (!(event.player instanceof EntityPlayerMP player)) {
            // this should never happen
            return;
        }
        final UUID UUID = player.getGameProfile()
            .getId();
        if (UUID != null) {
            PLAYERS_BY_UUID.put(UUID, player);
            UUID_BY_NAME.put(player.getCommandSenderName(), UUID);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerMap$onPlayerRespawn(PlayerRespawnEvent event) {
        if (!(event.player instanceof EntityPlayerMP player)) {
            // this should never happen
            return;
        }
        final UUID UUID = player.getGameProfile()
            .getId();
        if (UUID != null) {
            PLAYERS_BY_UUID.put(UUID, player);
            UUID_BY_NAME.put(player.getCommandSenderName(), UUID);
        }
    }

    /**
     * This method allows fast lookup of EntityPlayerMp from UUID. It should only ever be called from the ServerThread
     * and while the Server is running.
     *
     * @param uuid - uuid of the EntityPlayerMP
     */
    @Nullable
    public EntityPlayerMP getPlayerMP(UUID uuid) {
        if (!FMLCommonHandler.instance()
            .getEffectiveSide()
            .isServer()) {
            throw new RuntimeException("Tried to retrieve an EntityPlayerMP from outside of the server thread!");
        }
        if (PLAYERS_BY_UUID != null) {
            return PLAYERS_BY_UUID.get(uuid);
        } else {
            throw new NullPointerException("PLAYERS_BY_ID is null because the server is not running!");
        }
    }

    /**
     * This method allows fast lookup of player UUID from their name. It should only ever be called from the
     * ServerThread and while the Server is running.
     *
     * @param playername - the name of the player as returned by getCommandSenderName()
     */
    public UUID getPlayersUUID(String playername) {
        if (!FMLCommonHandler.instance()
            .getEffectiveSide()
            .isServer()) {
            throw new RuntimeException("Tried to retrieve a player UUID from outside of the server thread!");
        }
        if (UUID_BY_NAME != null) {
            return UUID_BY_NAME.get(playername);
        } else {
            throw new NullPointerException("UUID_BY_NAME is null because the server is not running!");
        }
    }

}
