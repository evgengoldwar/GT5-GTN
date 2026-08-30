package gregtech.common.items;

import static gregtech.api.enums.Textures.BlockIcons.MACHINE_CASINGS;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ADVANCED_WIRELESS_CONTROLLER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ARM;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_CONTROLLER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_CONVEYOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ENERGYDETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FLUIDDETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ITEMDETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_MAINTENANCE_DETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_PUMP;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_REDSTONE_RECEIVER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_REDSTONE_TRANSMITTER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_SHUTTER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_WIRELESS_CONTROLLER;
import static gregtech.client.GTTooltipHandler.Tier.ERV;
import static gregtech.client.GTTooltipHandler.Tier.EV;
import static gregtech.client.GTTooltipHandler.Tier.HV;
import static gregtech.client.GTTooltipHandler.Tier.IV;
import static gregtech.client.GTTooltipHandler.Tier.LV;
import static gregtech.client.GTTooltipHandler.Tier.LuV;
import static gregtech.client.GTTooltipHandler.Tier.MAX;
import static gregtech.client.GTTooltipHandler.Tier.MV;
import static gregtech.client.GTTooltipHandler.Tier.UEV;
import static gregtech.client.GTTooltipHandler.Tier.UHV;
import static gregtech.client.GTTooltipHandler.Tier.UIV;
import static gregtech.client.GTTooltipHandler.Tier.ULV;
import static gregtech.client.GTTooltipHandler.Tier.UMV;
import static gregtech.client.GTTooltipHandler.Tier.UV;
import static gregtech.client.GTTooltipHandler.Tier.UXV;
import static gregtech.client.GTTooltipHandler.Tier.ZPM;
import static gregtech.client.GTTooltipHandler.registerTieredTooltip;
import static gregtech.common.items.IDMetaItem01.BatteryHull_EV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_IV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_LuV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_UEV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_UHV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_UIV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_UMV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_UV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_UxV_Full;
import static gregtech.common.items.IDMetaItem01.BatteryHull_ZPM_Full;
import static gregtech.common.items.IDMetaItem01.Battery_RE_HV_Cadmium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_HV_Lithium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_HV_Sodium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_LV_Cadmium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_LV_Lithium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_LV_Sodium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_MV_Cadmium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_MV_Lithium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_MV_Sodium;
import static gregtech.common.items.IDMetaItem01.Battery_RE_ULV_Tantalum;
import static gregtech.common.items.IDMetaItem01.Battery_SU_HV_Mercury;
import static gregtech.common.items.IDMetaItem01.Battery_SU_HV_Sulfuric_Acid;
import static gregtech.common.items.IDMetaItem01.Battery_SU_LV_Mercury;
import static gregtech.common.items.IDMetaItem01.Battery_SU_LV_Sulfuric_Acid;
import static gregtech.common.items.IDMetaItem01.Battery_SU_MV_Mercury;
import static gregtech.common.items.IDMetaItem01.Battery_SU_MV_Sulfuric_Acid;
import static gregtech.common.items.IDMetaItem01.Electromagnet_Iron;
import static gregtech.common.items.IDMetaItem01.Electromagnet_Neodymium;
import static gregtech.common.items.IDMetaItem01.Electromagnet_Samarium;
import static gregtech.common.items.IDMetaItem01.Electromagnet_Steel;
import static gregtech.common.items.IDMetaItem01.Electromagnet_Tengam;
import static gregtech.common.items.IDMetaItem01.Energy_Cluster;
import static gregtech.common.items.IDMetaItem01.Energy_Lapotronic_Orb;
import static gregtech.common.items.IDMetaItem01.Energy_Lapotronic_orb_2;
import static gregtech.common.items.IDMetaItem01.Energy_Module;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Aluminium;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Chrome;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Iridium;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Neutronium;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Osmium;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_StainlessSteel;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Steel;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_Titanium;
import static gregtech.common.items.IDMetaItem01.Large_Fluid_Cell_TungstenSteel;
import static gregtech.common.items.IDMetaItem01.Spray_Color_0;
import static gregtech.common.items.IDMetaItem01.Spray_Color_1;
import static gregtech.common.items.IDMetaItem01.Spray_Color_10;
import static gregtech.common.items.IDMetaItem01.Spray_Color_11;
import static gregtech.common.items.IDMetaItem01.Spray_Color_12;
import static gregtech.common.items.IDMetaItem01.Spray_Color_13;
import static gregtech.common.items.IDMetaItem01.Spray_Color_14;
import static gregtech.common.items.IDMetaItem01.Spray_Color_15;
import static gregtech.common.items.IDMetaItem01.Spray_Color_2;
import static gregtech.common.items.IDMetaItem01.Spray_Color_3;
import static gregtech.common.items.IDMetaItem01.Spray_Color_4;
import static gregtech.common.items.IDMetaItem01.Spray_Color_5;
import static gregtech.common.items.IDMetaItem01.Spray_Color_6;
import static gregtech.common.items.IDMetaItem01.Spray_Color_7;
import static gregtech.common.items.IDMetaItem01.Spray_Color_8;
import static gregtech.common.items.IDMetaItem01.Spray_Color_9;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Infinite;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Remover;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_0;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_1;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_10;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_11;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_12;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_13;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_14;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_15;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_2;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_3;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_4;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_5;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_6;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_7;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_8;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_9;
import static gregtech.common.items.IDMetaItem01.Spray_Color_Used_Remover;
import static gregtech.common.items.IDMetaItem01.Tool_Cheat;
import static gregtech.common.items.IDMetaItem01.Tool_Cover_Copy_Paste;
import static gregtech.common.items.IDMetaItem01.Tool_Lighter_Invar_Full;
import static gregtech.common.items.IDMetaItem01.Tool_Lighter_Invar_Used;
import static gregtech.common.items.IDMetaItem01.Tool_Lighter_Platinum_Full;
import static gregtech.common.items.IDMetaItem01.Tool_Lighter_Platinum_Used;
import static gregtech.common.items.IDMetaItem01.Tool_MatchBox_Full;
import static gregtech.common.items.IDMetaItem01.Tool_MatchBox_Used;
import static gregtech.common.items.IDMetaItem01.Tool_Matches;
import static gregtech.common.items.IDMetaItem01.Tool_Scanner;
import static gregtech.common.items.IDMetaItem01.ZPM2;
import static gregtech.common.items.IDMetaItem01.ZPM3;
import static gregtech.common.items.IDMetaItem01.ZPM4;
import static gregtech.common.items.IDMetaItem01.ZPM5;
import static gregtech.common.items.IDMetaItem01.ZPM6;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import codechicken.enderstorage.api.EnderStorageDyeTool;
import cpw.mods.fml.common.Optional;
import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverPlacer;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.MetaBaseItem;
import gregtech.api.items.MetaGeneratedItemX32;
import gregtech.api.objects.ItemData;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.config.Client;
import gregtech.common.config.Other;
import gregtech.common.covers.CoverArm;
import gregtech.common.covers.CoverConveyor;
import gregtech.common.covers.CoverFluidRegulator;
import gregtech.common.covers.CoverPump;
import gregtech.common.covers.CoverShutter;
import gregtech.common.covers.redstone.CoverAdvancedWirelessController;
import gregtech.common.covers.redstone.CoverControlsWork;
import gregtech.common.covers.redstone.CoverDoesWork;
import gregtech.common.covers.redstone.CoverEUMeter;
import gregtech.common.covers.redstone.CoverItemMeter;
import gregtech.common.covers.redstone.CoverLiquidMeter;
import gregtech.common.covers.redstone.CoverNeedMaintainance;
import gregtech.common.covers.redstone.CoverRedstoneReceiverExternal;
import gregtech.common.covers.redstone.CoverRedstoneTransmitterExternal;
import gregtech.common.covers.redstone.CoverRedstoneTransmitterInternal;
import gregtech.common.covers.redstone.CoverWirelessController;
import gregtech.common.items.behaviors.BehaviourLighter;
import gregtech.common.items.behaviors.BehaviourSprayColor;
import gregtech.common.items.behaviors.BehaviourSprayColorInfinite;
import gregtech.common.items.behaviors.BehaviourSprayColorRemover;
import mods.railcraft.common.items.firestone.IItemFirestoneBurning;

@Optional.InterfaceList(
    value = {
        @Optional.Interface(
            iface = "mods.railcraft.common.items.firestone.IItemFirestoneBurning",
            modid = Mods.ModIDs.RAILCRAFT),
        @Optional.Interface(
            iface = "codechicken.enderstorage.api.EnderStorageDyeTool",
            modid = Mods.ModIDs.ENDER_STORAGE), })
public class MetaGeneratedItem01 extends MetaGeneratedItemX32 implements IItemFirestoneBurning, EnderStorageDyeTool {

    public static MetaGeneratedItem01 INSTANCE;
    private static final int[] Spray_Colors = new int[] { Spray_Color_0.ID, Spray_Color_1.ID, Spray_Color_2.ID,
        Spray_Color_3.ID, Spray_Color_4.ID, Spray_Color_5.ID, Spray_Color_6.ID, Spray_Color_7.ID, Spray_Color_8.ID,
        Spray_Color_9.ID, Spray_Color_10.ID, Spray_Color_11.ID, Spray_Color_12.ID, Spray_Color_13.ID, Spray_Color_14.ID,
        Spray_Color_15.ID };
    private static final int[] Spray_Colors_Used = new int[] { Spray_Color_Used_0.ID, Spray_Color_Used_1.ID,
        Spray_Color_Used_2.ID, Spray_Color_Used_3.ID, Spray_Color_Used_4.ID, Spray_Color_Used_5.ID,
        Spray_Color_Used_6.ID, Spray_Color_Used_7.ID, Spray_Color_Used_8.ID, Spray_Color_Used_9.ID,
        Spray_Color_Used_10.ID, Spray_Color_Used_11.ID, Spray_Color_Used_12.ID, Spray_Color_Used_13.ID,
        Spray_Color_Used_14.ID, Spray_Color_Used_15.ID };

    public MetaGeneratedItem01() {
        super(
            "metaitem.01",
            OrePrefixes.dustTiny,
            OrePrefixes.dustSmall,
            OrePrefixes.dust,
            OrePrefixes.dustImpure,
            OrePrefixes.dustPure,
            OrePrefixes.crushed,
            OrePrefixes.crushedPurified,
            OrePrefixes.crushedCentrifuged,
            OrePrefixes.gem,
            OrePrefixes.nugget,
            null,
            OrePrefixes.ingot,
            OrePrefixes.ingotHot,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.plate,
            OrePrefixes.plateDouble,
            OrePrefixes.plateTriple,
            OrePrefixes.plateQuadruple,
            OrePrefixes.plateQuintuple,
            OrePrefixes.plateDense,
            OrePrefixes.stick,
            OrePrefixes.lens,
            OrePrefixes.round,
            OrePrefixes.bolt,
            OrePrefixes.screw,
            OrePrefixes.ring,
            OrePrefixes.foil,
            OrePrefixes.cell,
            OrePrefixes.cellPlasma);
        INSTANCE = this;

        // registerCovers();
        // registerBehaviors();
        // setAllFluidContainerStats();
        // setAllElectricStats();
        // registerTieredTooltips();
        // registerSubIcons();
    }

    @Override
    protected void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        super.addAdditionalToolTips(aList, aStack, aPlayer);
        int aDamage = aStack.getItemDamage();
        if (Materials.isMaterialItem(aDamage)) {
            Materials aMaterial = GregTechAPI.sGeneratedMaterials[(aDamage % 1000)];
            if ((aMaterial != null) && (aMaterial != Materials.Empty) && (aMaterial != Materials._NULL)) {
                OrePrefixes aPrefix = this.mGeneratedPrefixList[(aDamage / 1000)];
                if ((aPrefix == OrePrefixes.dustImpure) || (aPrefix == OrePrefixes.dustPure)) {
                    aList.add(StatCollector.translateToLocal("GT5U.tooltip.purify.1"));
                }
                if (aPrefix == OrePrefixes.dust && aMaterial == Materials.Wheat) {
                    aList.add(StatCollector.translateToLocal("GT5U.tooltip.flour.cauldron"));
                }
                if (aPrefix == OrePrefixes.ingotHot && Client.tooltip.showHotIngotText) {
                    aList.add(StatCollector.translateToLocal("gtpp.tooltip.ingot.very_hot"));
                }
            }
        }
        if (Client.tooltip.showRadioactiveText && GTUtility.getRadioactivityLevel(aStack) > 0) {
            aList.add(StatCollector.translateToLocal("GTPP.core.GT_Tooltip_Radioactive"));
            aList.add(StatCollector.translateToLocal("GTPP.core.GT_Tooltip_HazmatWarning"));
        }
    }

    public boolean isPlasmaCellUsed(OrePrefixes aPrefix, Materials aMaterial) {
        // Materials has a plasma fluid
        if (aPrefix == OrePrefixes.cellPlasma && aMaterial.getPlasma(1L) != null) {
            if (aMaterial.hasPlasma()) return true;
        }
        return false;
    }

    @Override
    public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
        return (aDoShowAllItems) || (((aPrefix != OrePrefixes.gem) || (!aMaterial.mName.startsWith("Infused")))
            && (aPrefix != OrePrefixes.dustTiny)
            && (aPrefix != OrePrefixes.dustSmall)
            && (aPrefix != OrePrefixes.dustImpure)
            && (aPrefix != OrePrefixes.dustPure)
            && (aPrefix != OrePrefixes.crushed)
            && (aPrefix != OrePrefixes.crushedPurified)
            && (aPrefix != OrePrefixes.crushedCentrifuged)
            && (aPrefix != OrePrefixes.ingotHot)
            && !(aPrefix == OrePrefixes.cellPlasma && !isPlasmaCellUsed(aPrefix, aMaterial)));
    }

    @Override
    public ItemStack getContainerItem(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        if (((aDamage >= 32430) && (aDamage <= 32461)) || (aDamage == 32465 || aDamage == 32466)) {
            return ItemList.Spray_Empty.get(1L);
        }
        if ((aDamage == 32479) || (aDamage == 32476)) {
            return new ItemStack(this, 1, aDamage - 2);
        }
        if (aDamage == 32401) {
            return new ItemStack(this, 1, aDamage - 1);
        }
        return super.getContainerItem(aStack);
    }

    private void setAllFluidContainerStats() {
        setFluidContainerStats(32000 + Large_Fluid_Cell_Steel.ID, 8_000L, 64L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_TungstenSteel.ID, 512_000L, 32L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_Aluminium.ID, 32_000L, 64L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_StainlessSteel.ID, 64_000L, 64L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_Titanium.ID, 128_000L, 64L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_Chrome.ID, 2_048_000L, 8L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_Iridium.ID, 8_192_000L, 2L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_Osmium.ID, 32_768_000L, 1L);
        setFluidContainerStats(32000 + Large_Fluid_Cell_Neutronium.ID, 131_072_000L, 1L);
        setFluidContainerStats(32000 + Electromagnet_Iron.ID, 0L, 1L);
        setFluidContainerStats(32000 + Electromagnet_Steel.ID, 0L, 1L);
        setFluidContainerStats(32000 + Electromagnet_Neodymium.ID, 0L, 1L);
        setFluidContainerStats(32000 + Electromagnet_Samarium.ID, 0L, 1L);
        setFluidContainerStats(32000 + Electromagnet_Tengam.ID, 0L, 1L);
    }

    private void registerCovers() {
        final ITexture doesWorkCoverTexture = TextureFactory.of(
            TextureFactory.of(OVERLAY_ACTIVITYDETECTOR),
            TextureFactory.builder()
                .addIcon(OVERLAY_ACTIVITYDETECTOR_GLOW)
                .glow()
                .build());

        CoverRegistry.registerCover(
            ItemList.Electric_Pump_LV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 32, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_HV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[3][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 512, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_EV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[4][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 2048, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_IV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[5][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 8192, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_LuV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[6][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 32768, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_ZPM.get(1L),
            TextureFactory.of(MACHINE_CASINGS[7][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 131072, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_UV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[8][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 524288, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_UHV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[9][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 8388608, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_UEV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[10][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 16777216, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_UIV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[11][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 33554432, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_UMV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[12][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 67108864, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_UXV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[13][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 134217728, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.Electric_Pump_MAX.get(1L),
            TextureFactory.of(MACHINE_CASINGS[14][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverPump(context, 268435456, TextureFactory.of(OVERLAY_PUMP)));

        CoverRegistry.registerCover(
            ItemList.FluidRegulator_LV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 32, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_MV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 128, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_HV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[3][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 512, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_EV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[4][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 2048, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_IV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[5][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 8192, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_LuV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[6][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 32768, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_ZPM.get(1L),
            TextureFactory.of(MACHINE_CASINGS[7][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 131072, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_UV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[8][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 524288, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_UHV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[9][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 8388608, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_UEV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[10][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 16777216, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_UIV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[11][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 33554432, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_UMV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[12][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 67108864, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_UXV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[12][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 134217728, TextureFactory.of(OVERLAY_PUMP)));
        CoverRegistry.registerCover(
            ItemList.FluidRegulator_MAX.get(1L),
            TextureFactory.of(MACHINE_CASINGS[13][0], TextureFactory.of(OVERLAY_PUMP)),
            context -> new CoverFluidRegulator(context, 268435456, TextureFactory.of(OVERLAY_PUMP)));

        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_LV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 100, 1, 16, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_MV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 100, 1, 64, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_HV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[3][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 20, 1, 64, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_EV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[4][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 4, 1, 64, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_IV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[5][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 1, 64, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_LuV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[6][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 4, 64, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_ZPM.get(1L),
            TextureFactory.of(MACHINE_CASINGS[7][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 64, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_UV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[8][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 256, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_UHV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[9][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 1024, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_UEV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[10][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 4096, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_UIV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[11][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 16384, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_UMV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[12][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 65536, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_UXV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[13][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, 262144, TextureFactory.of(OVERLAY_CONVEYOR)));
        CoverRegistry.registerCover(
            ItemList.Conveyor_Module_MAX.get(1L),
            TextureFactory.of(MACHINE_CASINGS[14][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            context -> new CoverConveyor(context, 1, 16, Integer.MAX_VALUE, TextureFactory.of(OVERLAY_CONVEYOR)));

        CoverRegistry.registerCover(
            ItemList.Robot_Arm_LV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_ARM)),
            context -> new CoverArm(context, 400, TextureFactory.of(OVERLAY_ARM)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Robot_Arm_MV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_ARM)),
            context -> new CoverArm(context, 100, TextureFactory.of(OVERLAY_ARM)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Robot_Arm_HV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[3][0], TextureFactory.of(OVERLAY_ARM)),
            context -> new CoverArm(context, 20, TextureFactory.of(OVERLAY_ARM)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Robot_Arm_EV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[4][0], TextureFactory.of(OVERLAY_ARM)),
            context -> new CoverArm(context, 4, TextureFactory.of(OVERLAY_ARM)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Robot_Arm_IV.get(1L),
            TextureFactory.of(MACHINE_CASINGS[5][0], TextureFactory.of(OVERLAY_ARM)),
            context -> new CoverArm(context, 1, TextureFactory.of(OVERLAY_ARM)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);

        CoverRegistry.registerCover(
            ItemList.Cover_Controller.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_CONTROLLER)),
            context -> new CoverControlsWork(context, TextureFactory.of(OVERLAY_CONTROLLER)),
            CoverPlacer.builder()
                .onlyPlaceIf(CoverControlsWork::isCoverPlaceable)
                .build());

        CoverRegistry.registerCover(
            ItemList.Cover_ActivityDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], doesWorkCoverTexture),
            context -> new CoverDoesWork(context, doesWorkCoverTexture));
        CoverRegistry.registerCover(
            ItemList.Cover_FluidDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_FLUIDDETECTOR)),
            context -> new CoverLiquidMeter(context, TextureFactory.of(OVERLAY_FLUIDDETECTOR)));
        CoverRegistry.registerCover(
            ItemList.Cover_ItemDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_ITEMDETECTOR)),
            context -> new CoverItemMeter(context, TextureFactory.of(OVERLAY_ITEMDETECTOR)));
        CoverRegistry.registerCover(
            ItemList.Cover_EnergyDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_ENERGYDETECTOR)),
            context -> new CoverEUMeter(context, TextureFactory.of(OVERLAY_ENERGYDETECTOR)));

        CoverRegistry.registerCover(
            ItemList.Cover_Shutter.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_SHUTTER)),
            context -> new CoverShutter(context, TextureFactory.of(OVERLAY_SHUTTER)));

        CoverRegistry.registerCover(
            ItemList.Cover_RedstoneTransmitter.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_REDSTONE_TRANSMITTER)),
            context -> new CoverRedstoneTransmitterExternal(context, TextureFactory.of(OVERLAY_REDSTONE_TRANSMITTER)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Cover_RedstoneTransmitterInternal.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_REDSTONE_TRANSMITTER)),
            context -> new CoverRedstoneTransmitterInternal(context, TextureFactory.of(OVERLAY_REDSTONE_TRANSMITTER)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Cover_RedstoneReceiver.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_REDSTONE_RECEIVER)),
            context -> new CoverRedstoneReceiverExternal(context, TextureFactory.of(OVERLAY_REDSTONE_RECEIVER)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Cover_WirelessController.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_WIRELESS_CONTROLLER)),
            context -> new CoverWirelessController(context, TextureFactory.of(OVERLAY_WIRELESS_CONTROLLER)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);
        CoverRegistry.registerCover(
            ItemList.Cover_AdvancedWirelessController.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_ADVANCED_WIRELESS_CONTROLLER)),
            context -> new CoverAdvancedWirelessController(
                context,
                TextureFactory.of(OVERLAY_ADVANCED_WIRELESS_CONTROLLER)),
            CoverRegistry.INTERCEPTS_RIGHT_CLICK_COVER_PLACER);

        CoverRegistry.registerCover(
            ItemList.Cover_NeedsMaintainance.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_MAINTENANCE_DETECTOR)),
            context -> new CoverNeedMaintainance(context, TextureFactory.of(OVERLAY_MAINTENANCE_DETECTOR)));

    }

    private void registerBehaviors() {
        IItemBehaviour<MetaBaseItem> behaviourSprayColorRemover = new BehaviourSprayColorRemover(
            ItemList.Spray_Empty.get(1L),
            ItemList.Spray_Color_Used_Remover.get(1L),
            ItemList.Spray_Color_Remover.get(1L),
            1024L);
        addItemBehavior(32000 + Spray_Color_Remover.ID, behaviourSprayColorRemover);
        addItemBehavior(32000 + Spray_Color_Used_Remover.ID, behaviourSprayColorRemover);

        addItemBehavior(
            32000 + Spray_Color_Infinite.ID,
            new BehaviourSprayColorInfinite(ItemList.Spray_Color_Infinite.get(1L)));

        IItemBehaviour<MetaBaseItem> behaviourMatches = new BehaviourLighter(
            null,
            ItemList.Tool_Matches.get(1L),
            ItemList.Tool_Matches.get(1L),
            1L);
        addItemBehavior(32000 + Tool_Matches.ID, behaviourMatches);
        IItemBehaviour<MetaBaseItem> behaviourMatchBox = new BehaviourLighter(
            null,
            ItemList.Tool_MatchBox_Used.get(1L),
            ItemList.Tool_MatchBox_Full.get(1L),
            16L);
        addItemBehavior(32000 + Tool_MatchBox_Used.ID, behaviourMatchBox);
        addItemBehavior(32000 + Tool_MatchBox_Full.ID, behaviourMatchBox);

        IItemBehaviour<MetaBaseItem> behaviourLighterInvar = new BehaviourLighter(
            ItemList.Tool_Lighter_Invar_Empty.get(1L),
            ItemList.Tool_Lighter_Invar_Used.get(1L),
            ItemList.Tool_Lighter_Invar_Full.get(1L),
            100L);
        addItemBehavior(32000 + Tool_Lighter_Invar_Used.ID, behaviourLighterInvar);
        addItemBehavior(32000 + Tool_Lighter_Invar_Full.ID, behaviourLighterInvar);

        IItemBehaviour<MetaBaseItem> behaviourLighterPlatinum = new BehaviourLighter(
            ItemList.Tool_Lighter_Platinum_Empty.get(1L),
            ItemList.Tool_Lighter_Platinum_Used.get(1L),
            ItemList.Tool_Lighter_Platinum_Full.get(1L),
            1000L);
        addItemBehavior(32000 + Tool_Lighter_Platinum_Used.ID, behaviourLighterPlatinum);
        addItemBehavior(32000 + Tool_Lighter_Platinum_Full.ID, behaviourLighterPlatinum);

        for (int i = 0; i < 16; i++) {
            IItemBehaviour<MetaBaseItem> behaviourSprayColor = new BehaviourSprayColor(
                ItemList.Spray_Empty.get(1L),
                ItemList.SPRAY_CAN_DYES_USED[i].get(1L),
                ItemList.SPRAY_CAN_DYES[i].get(1L),
                Other.sprayCanUses,
                i);
            addItemBehavior(32000 + Spray_Colors[i], behaviourSprayColor);
            addItemBehavior(32000 + Spray_Colors_Used[i], behaviourSprayColor);
        }
    }

    private void setAllElectricStats() {
        setElectricStats(32000 + Battery_RE_ULV_Tantalum.ID, 1000L, GTValues.V[0], 0L, -3L, false);
        setElectricStats(32000 + Battery_SU_LV_Sulfuric_Acid.ID, 18000L, GTValues.V[1], 1L, -2L, true);
        setElectricStats(32000 + Battery_SU_LV_Mercury.ID, 32000L, GTValues.V[1], 1L, -2L, true);
        setElectricStats(32000 + Battery_RE_LV_Cadmium.ID, 75000L, GTValues.V[1], 1L, -3L, true);
        setElectricStats(32000 + Battery_RE_LV_Lithium.ID, 100000L, GTValues.V[1], 1L, -3L, true);
        setElectricStats(32000 + Battery_RE_LV_Sodium.ID, 50000L, GTValues.V[1], 1L, -3L, true);
        setElectricStats(32000 + Battery_SU_MV_Sulfuric_Acid.ID, 72000L, GTValues.V[2], 2L, -2L, true);
        setElectricStats(32000 + Battery_SU_MV_Mercury.ID, 128000L, GTValues.V[2], 2L, -2L, true);
        setElectricStats(32000 + Battery_RE_MV_Cadmium.ID, 300000L, GTValues.V[2], 2L, -3L, true);
        setElectricStats(32000 + Battery_RE_MV_Lithium.ID, 400000L, GTValues.V[2], 2L, -3L, true);
        setElectricStats(32000 + Battery_RE_MV_Sodium.ID, 200000L, GTValues.V[2], 2L, -3L, true);
        setElectricStats(32000 + Battery_SU_HV_Sulfuric_Acid.ID, 288000L, GTValues.V[3], 3L, -2L, true);
        setElectricStats(32000 + Battery_SU_HV_Mercury.ID, 512000L, GTValues.V[3], 3L, -2L, true);
        setElectricStats(32000 + Battery_RE_HV_Cadmium.ID, 1200000L, GTValues.V[3], 3L, -3L, true);
        setElectricStats(32000 + Battery_RE_HV_Lithium.ID, 1600000L, GTValues.V[3], 3L, -3L, true);
        setElectricStats(32000 + Battery_RE_HV_Sodium.ID, 800000L, GTValues.V[3], 3L, -3L, true);
        setElectricStats(32000 + Energy_Lapotronic_Orb.ID, 100000000L, GTValues.V[5], 5L, -3L, true);
        setElectricStats(32000 + IDMetaItem01.ZPM.ID, 2000000000000L, GTValues.V[7], 7L, -2L, true);
        setElectricStats(32000 + Energy_Lapotronic_orb_2.ID, 1000000000L, GTValues.V[6], 6L, -3L, true);
        setElectricStats(32000 + ZPM2.ID, Long.MAX_VALUE, GTValues.V[8], 8L, -3L, true);
        setElectricStats(32000 + ZPM3.ID, Long.MAX_VALUE, GTValues.V[12], 12L, -3L, true);
        setElectricStats(32000 + ZPM4.ID, Long.MAX_VALUE, GTValues.V[13], 13L, -3L, true);
        setElectricStats(32000 + ZPM5.ID, Long.MAX_VALUE, GTValues.V[14], 14L, -3L, true);
        setElectricStats(32000 + ZPM6.ID, Long.MAX_VALUE, GTValues.V[15], 15L, -3L, true);
        setElectricStats(32000 + Energy_Module.ID, 10000000000L, GTValues.V[7], 7L, -3L, true);
        setElectricStats(32000 + Energy_Cluster.ID, 100000000000L, GTValues.V[8], 8L, -3L, true);
        setElectricStats(32000 + Tool_Cover_Copy_Paste.ID, 400000L, GTValues.V[2], 2L, -1L, false);
        setElectricStats(32000 + Tool_Cheat.ID, -2000000000L, 1000000000L, -1L, -3L, false);
        setElectricStats(32000 + Tool_Scanner.ID, 400000L, GTValues.V[2], 2L, -1L, false);
        setElectricStats(32000 + BatteryHull_EV_Full.ID, 6400000L, GTValues.V[4], 4L, -3L, true);
        setElectricStats(32000 + BatteryHull_IV_Full.ID, 25600000L, GTValues.V[5], 5L, -3L, true);
        setElectricStats(32000 + BatteryHull_LuV_Full.ID, 102400000L, GTValues.V[6], 6L, -3L, true);
        setElectricStats(32000 + BatteryHull_ZPM_Full.ID, 409600000L, GTValues.V[7], 7L, -3L, true);
        setElectricStats(32000 + BatteryHull_UV_Full.ID, 1638400000L, GTValues.V[8], 8L, -3L, true);
        setElectricStats(32000 + BatteryHull_UHV_Full.ID, 6553600000L, GTValues.V[9], 9L, -3L, true);
        setElectricStats(32000 + BatteryHull_UEV_Full.ID, 26214400000L, GTValues.V[10], 10L, -3L, true);
        setElectricStats(32000 + BatteryHull_UIV_Full.ID, 104857600000L, GTValues.V[11], 11L, -3L, true);
        setElectricStats(32000 + BatteryHull_UMV_Full.ID, 419430400000L, GTValues.V[12], 12L, -3L, true);
        setElectricStats(32000 + BatteryHull_UxV_Full.ID, 1677721600000L, GTValues.V[13], 13L, -3L, true);
    }

    private void registerSubIcons() {
        setSubIcons(Spray_Color_Infinite.ID, 3);
    }

    private void registerTieredTooltips() {
        registerTieredTooltip(ItemList.Battery_RE_ULV_Tantalum.get(1), ULV);
        registerTieredTooltip(ItemList.Battery_SU_LV_SulfuricAcid.get(1), LV);
        registerTieredTooltip(ItemList.Battery_SU_LV_Mercury.get(1), LV);
        registerTieredTooltip(ItemList.Battery_RE_LV_Cadmium.get(1), LV);
        registerTieredTooltip(ItemList.Battery_RE_LV_Lithium.get(1), LV);
        registerTieredTooltip(ItemList.Battery_RE_LV_Sodium.get(1), LV);
        registerTieredTooltip(ItemList.Battery_SU_MV_SulfuricAcid.get(1), MV);
        registerTieredTooltip(ItemList.Battery_SU_MV_Mercury.get(1), MV);
        registerTieredTooltip(ItemList.Battery_RE_MV_Cadmium.get(1), MV);
        registerTieredTooltip(ItemList.Battery_RE_MV_Lithium.get(1), MV);
        registerTieredTooltip(ItemList.Battery_RE_MV_Sodium.get(1), MV);
        registerTieredTooltip(ItemList.Battery_SU_HV_SulfuricAcid.get(1), HV);
        registerTieredTooltip(ItemList.Battery_SU_HV_Mercury.get(1), HV);
        registerTieredTooltip(ItemList.Battery_RE_HV_Cadmium.get(1), HV);
        registerTieredTooltip(ItemList.Battery_RE_HV_Lithium.get(1), HV);
        registerTieredTooltip(ItemList.Battery_RE_HV_Sodium.get(1), HV);
        registerTieredTooltip(ItemList.ZPM.get(1), ZPM);
        registerTieredTooltip(ItemList.Energy_LapotronicOrb2.get(1), LuV);
        registerTieredTooltip(ItemList.ZPM2.get(1), UV);
        registerTieredTooltip(ItemList.ZPM3.get(1), UMV);
        registerTieredTooltip(ItemList.ZPM4.get(1), UXV);
        registerTieredTooltip(ItemList.ZPM5.get(1), MAX);
        registerTieredTooltip(ItemList.ZPM6.get(1), ERV);
        registerTieredTooltip(ItemList.Energy_Module.get(1), ZPM);
        registerTieredTooltip(ItemList.Energy_Cluster.get(1), UV);
        registerTieredTooltip(ItemList.Circuit_Primitive.get(1), ULV);
        registerTieredTooltip(ItemList.Circuit_Good.get(1), MV);
        registerTieredTooltip(ItemList.Circuit_Data.get(1), EV);
        registerTieredTooltip(ItemList.Circuit_Elite.get(1), IV);
        registerTieredTooltip(ItemList.Circuit_Master.get(1), LuV);
        registerTieredTooltip(ItemList.BatteryHull_EV_Full.get(1), EV);
        registerTieredTooltip(ItemList.BatteryHull_IV_Full.get(1), IV);
        registerTieredTooltip(ItemList.BatteryHull_LuV_Full.get(1), LuV);
        registerTieredTooltip(ItemList.BatteryHull_ZPM_Full.get(1), ZPM);
        registerTieredTooltip(ItemList.BatteryHull_UV_Full.get(1), UV);
        registerTieredTooltip(ItemList.BatteryHull_UHV_Full.get(1), UHV);
        registerTieredTooltip(ItemList.BatteryHull_UEV_Full.get(1), UEV);
        registerTieredTooltip(ItemList.BatteryHull_UIV_Full.get(1), UIV);
        registerTieredTooltip(ItemList.BatteryHull_UMV_Full.get(1), UMV);
        registerTieredTooltip(ItemList.BatteryHull_UxV_Full.get(1), UXV);

    }

    @Override
    @Optional.Method(modid = Mods.ModIDs.RAILCRAFT)
    public boolean shouldBurn(ItemStack itemStack) {
        ItemData data = GTOreDictUnificator.getAssociation(itemStack);
        if (data == null || data.mMaterial == null
            || data.mMaterial.mMaterial != Materials.Firestone
            || data.mPrefix == null) {
            return false;
        }
        return data.mPrefix == OrePrefixes.dustTiny || data.mPrefix == OrePrefixes.dustSmall
            || data.mPrefix == OrePrefixes.dust
            || data.mPrefix == OrePrefixes.dustImpure
            || data.mPrefix == OrePrefixes.dustRefined
            || data.mPrefix == OrePrefixes.dustPure
            || data.mPrefix == OrePrefixes.crushed
            || data.mPrefix == OrePrefixes.crushedPurified
            || data.mPrefix == OrePrefixes.crushedCentrifuged
            || data.mPrefix == OrePrefixes.gem;
    }

    @Override
    public int getDye(final ItemStack itemStack) {
        if (Mods.EnderStorage.isModLoaded()) {
            final List<Integer> results = mapEachBehavior(itemStack, behavior -> {
                if (behavior instanceof final EnderStorageDyeTool dyeTool) {
                    return dyeTool.getDye(itemStack);
                }

                return null;
            });

            if (!results.isEmpty()) {
                return results.getFirst();
            }
        }

        return -1;
    }

    @Override
    public void expendToolUse(final ItemStack itemStack) {
        if (Mods.EnderStorage.isModLoaded()) {
            forEachBehavior(itemStack, behavior -> {
                if (behavior instanceof final EnderStorageDyeTool dyeTool) {
                    dyeTool.expendToolUse(itemStack);
                }
                return false;
            });
        }
    }
}
