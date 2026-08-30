package gregtech.common.items;

import static gregtech.api.enums.Textures.BlockIcons.MACHINE_CASINGS;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ADVANCED_REDSTONE_RECEIVER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_ADVANCED_REDSTONE_TRANSMITTER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_WIRELESS_ACTIVITYDETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_WIRELESS_FLUID_DETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_WIRELESS_ITEM_DETECTOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_WIRELESS_MAINTENANCE_DETECTOR;
import static gregtech.common.items.IDMetaItem02.Cover_AdvancedRedstoneReceiver;
import static gregtech.common.items.IDMetaItem02.Cover_AdvancedRedstoneTransmitter;
import static gregtech.common.items.IDMetaItem02.Cover_WirelessActivityDetector;
import static gregtech.common.items.IDMetaItem02.Cover_WirelessFluidDetector;
import static gregtech.common.items.IDMetaItem02.Cover_WirelessItemDetector;
import static gregtech.common.items.IDMetaItem02.Cover_WirelessNeedsMaintainance;
import static gregtech.common.items.IDMetaItem02.Display_ITS_FREE;
import static gregtech.common.items.IDMetaItem02.Dye_Color_00;
import static gregtech.common.items.IDMetaItem02.Dye_Color_01;
import static gregtech.common.items.IDMetaItem02.Dye_Color_02;
import static gregtech.common.items.IDMetaItem02.Dye_Color_03;
import static gregtech.common.items.IDMetaItem02.Dye_Color_04;
import static gregtech.common.items.IDMetaItem02.Dye_Color_05;
import static gregtech.common.items.IDMetaItem02.Dye_Color_06;
import static gregtech.common.items.IDMetaItem02.Dye_Color_07;
import static gregtech.common.items.IDMetaItem02.Dye_Color_08;
import static gregtech.common.items.IDMetaItem02.Dye_Color_09;
import static gregtech.common.items.IDMetaItem02.Dye_Color_10;
import static gregtech.common.items.IDMetaItem02.Dye_Color_11;
import static gregtech.common.items.IDMetaItem02.Dye_Color_12;
import static gregtech.common.items.IDMetaItem02.Dye_Color_13;
import static gregtech.common.items.IDMetaItem02.Dye_Color_14;
import static gregtech.common.items.IDMetaItem02.Dye_Color_15;
import static gregtech.common.items.IDMetaItem02.Dye_Indigo;
import static gregtech.common.items.IDMetaItem02.Food_ChiliChips;
import static gregtech.common.items.IDMetaItem02.Food_Fries;
import static gregtech.common.items.IDMetaItem02.Food_Large_Sandwich_Bacon;
import static gregtech.common.items.IDMetaItem02.Food_Large_Sandwich_Cheese;
import static gregtech.common.items.IDMetaItem02.Food_Large_Sandwich_Steak;
import static gregtech.common.items.IDMetaItem02.Food_Large_Sandwich_Veggie;
import static gregtech.common.items.IDMetaItem02.Food_PotatoChips;
import static gregtech.common.items.IDMetaItem02.Food_Raw_Fries;
import static gregtech.common.items.IDMetaItem02.Food_Raw_PotatoChips;
import static gregtech.common.items.IDMetaItem02.Food_Sandwich_Bacon;
import static gregtech.common.items.IDMetaItem02.Food_Sandwich_Cheese;
import static gregtech.common.items.IDMetaItem02.Food_Sandwich_Steak;
import static gregtech.common.items.IDMetaItem02.Food_Sandwich_Veggie;
import static gregtech.common.items.IDMetaItem02.GelledToluene;
import static gregtech.common.items.IDMetaItem02.MSFMixture;
import static gregtech.common.items.IDMetaItem02.Magnetron;
import static gregtech.common.items.IDMetaItem02.Plank_Acacia;
import static gregtech.common.items.IDMetaItem02.Plank_Acacia_Green;
import static gregtech.common.items.IDMetaItem02.Plank_Balsa;
import static gregtech.common.items.IDMetaItem02.Plank_Baobab;
import static gregtech.common.items.IDMetaItem02.Plank_Birch;
import static gregtech.common.items.IDMetaItem02.Plank_Cherry;
import static gregtech.common.items.IDMetaItem02.Plank_Cherry_EFR;
import static gregtech.common.items.IDMetaItem02.Plank_Chestnut;
import static gregtech.common.items.IDMetaItem02.Plank_Citrus;
import static gregtech.common.items.IDMetaItem02.Plank_DarkOak;
import static gregtech.common.items.IDMetaItem02.Plank_Ebony;
import static gregtech.common.items.IDMetaItem02.Plank_Greenheart;
import static gregtech.common.items.IDMetaItem02.Plank_Jungle;
import static gregtech.common.items.IDMetaItem02.Plank_Kapok;
import static gregtech.common.items.IDMetaItem02.Plank_Larch;
import static gregtech.common.items.IDMetaItem02.Plank_Lime;
import static gregtech.common.items.IDMetaItem02.Plank_Mahagony;
import static gregtech.common.items.IDMetaItem02.Plank_Mahoe;
import static gregtech.common.items.IDMetaItem02.Plank_Maple;
import static gregtech.common.items.IDMetaItem02.Plank_Oak;
import static gregtech.common.items.IDMetaItem02.Plank_Palm;
import static gregtech.common.items.IDMetaItem02.Plank_Papaya;
import static gregtech.common.items.IDMetaItem02.Plank_Pine;
import static gregtech.common.items.IDMetaItem02.Plank_Plum;
import static gregtech.common.items.IDMetaItem02.Plank_Poplar;
import static gregtech.common.items.IDMetaItem02.Plank_Sequoia;
import static gregtech.common.items.IDMetaItem02.Plank_Spruce;
import static gregtech.common.items.IDMetaItem02.Plank_Teak;
import static gregtech.common.items.IDMetaItem02.Plank_Walnut;
import static gregtech.common.items.IDMetaItem02.Plank_Wenge;
import static gregtech.common.items.IDMetaItem02.Plank_Willow;
import static gregtech.common.items.IDMetaItem02.SFMixture;
import static gregtech.common.items.IDMetaItem02.Vajra_Core;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.gtnhlib.item.ItemStackNBT;

import gregtech.api.GregTechAPI;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TCAspects;
import gregtech.api.items.MetaGeneratedItemX32;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.common.covers.redstone.CoverAdvancedRedstoneReceiverExternal;
import gregtech.common.covers.redstone.CoverAdvancedRedstoneTransmitterExternal;
import gregtech.common.covers.redstone.CoverWirelessDoesWorkDetector;
import gregtech.common.covers.redstone.CoverWirelessFluidDetector;
import gregtech.common.covers.redstone.CoverWirelessItemDetector;
import gregtech.common.covers.redstone.CoverWirelessMaintenanceDetector;

public class MetaGeneratedItem02 extends MetaGeneratedItemX32 {

    public static MetaGeneratedItem02 INSTANCE;
    private static final String aTextCover = "Usable as Cover";

    public MetaGeneratedItem02() {
        super(
            "metaitem.02",
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.toolHeadHammer,
            OrePrefixes.toolHeadFile,
            OrePrefixes.toolHeadSaw,
            OrePrefixes.toolHeadDrill,
            OrePrefixes.toolHeadChainsaw,
            OrePrefixes.toolHeadWrench,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.toolHeadBuzzSaw,
            OrePrefixes.turbineBlade,
            null,
            OrePrefixes.itemCasing,
            OrePrefixes.wireFine,
            OrePrefixes.gearGtSmall,
            OrePrefixes.rotor,
            OrePrefixes.stickLong,
            OrePrefixes.springSmall,
            OrePrefixes.spring,
            OrePrefixes.___placeholder___,
            OrePrefixes.___placeholder___,
            OrePrefixes.gemChipped,
            OrePrefixes.gemFlawed,
            OrePrefixes.gemFlawless,
            OrePrefixes.gemExquisite,
            OrePrefixes.gearGt);
        INSTANCE = this;

        ItemList.GelledToluene.set(
            addItemWithLocalizationKeys(
                GelledToluene.ID,
                "gt.item.gelled_toluene.name",
                "gt.item.gelled_toluene.tooltip"));

        ItemList.Dye_Indigo.set(
            addItemWithLocalizationKeys(
                Dye_Indigo.ID,
                "gt.item.dye.indigo.name",
                "gt.item.dye.indigo.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.SENSUS, 1L),
                Dyes.dyeBlue));

        int[] Dye_Colors = new int[] { Dye_Color_00.ID, Dye_Color_01.ID, Dye_Color_02.ID, Dye_Color_03.ID,
            Dye_Color_04.ID, Dye_Color_05.ID, Dye_Color_06.ID, Dye_Color_07.ID, Dye_Color_08.ID, Dye_Color_09.ID,
            Dye_Color_10.ID, Dye_Color_11.ID, Dye_Color_12.ID, Dye_Color_13.ID, Dye_Color_14.ID, Dye_Color_15.ID };
        for (int i = 0; i < 16; i = i + 1) {
            String dyeKey = "gt.item.dye." + Dyes.get(i).mName.toLowerCase()
                .replace(" ", "_") + ".name";
            // ItemList.DYE_ONLY_ITEMS[i].set(
            // addItemWithLocalizationKeys(
            // Dye_Colors[i],
            // dyeKey,
            // "",
            // Dyes.get(i)
            // .name(),
            // new TCAspects.TC_AspectStack(TCAspects.SENSUS, 1L)));
        }

        ItemList.SFMixture
            .set(addItemWithLocalizationKeys(SFMixture.ID, "gt.item.sf_mixture.name", "gt.item.sf_mixture.tooltip"));
        ItemList.MSFMixture
            .set(addItemWithLocalizationKeys(MSFMixture.ID, "gt.item.msf_mixture.name", "gt.item.msf_mixture.tooltip"));

        ItemList.Cover_AdvancedRedstoneTransmitter.set(
            addItemWithLocalizationKeys(
                Cover_AdvancedRedstoneTransmitter.ID,
                "gt.item.cover.advanced_redstone_transmitter.name",
                "gt.item.cover.advanced_redstone_transmitter.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L)));

        ItemList.Cover_AdvancedRedstoneReceiver.set(
            addItemWithLocalizationKeys(
                Cover_AdvancedRedstoneReceiver.ID,
                "gt.item.cover.advanced_redstone_receiver.name",
                "gt.item.cover.advanced_redstone_receiver.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L)));

        ItemList.Cover_WirelessFluidDetector.set(
            addItemWithLocalizationKeys(
                Cover_WirelessFluidDetector.ID,
                "gt.item.cover.wireless_fluid_detector.name",
                "gt.item.cover.wireless_fluid_detector.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.SENSUS, 2L),
                new TCAspects.TC_AspectStack(TCAspects.AQUA, 1L)));
        ItemList.Cover_WirelessItemDetector.set(
            addItemWithLocalizationKeys(
                Cover_WirelessItemDetector.ID,
                "gt.item.cover.wireless_item_detector.name",
                "gt.item.cover.wireless_item_detector.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.SENSUS, 2L),
                new TCAspects.TC_AspectStack(TCAspects.TERRA, 1L)));

        ItemList.Cover_WirelessNeedsMaintainance.set(
            addItemWithLocalizationKeys(
                Cover_WirelessNeedsMaintainance.ID,
                "gt.item.cover.wireless_needs_maintenance.name",
                "gt.item.cover.wireless_needs_maintenance.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 4L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 3L)));

        ItemList.Cover_WirelessActivityDetector.set(
            addItemWithLocalizationKeys(
                Cover_WirelessActivityDetector.ID,
                "gt.item.cover.wireless_activity_detector.name",
                "gt.item.cover.wireless_activity_detector.tooltip",
                new TCAspects.TC_AspectStack(TCAspects.ORDO, 2L),
                new TCAspects.TC_AspectStack(TCAspects.MACHINA, 2L),
                new TCAspects.TC_AspectStack(TCAspects.SENSUS, 2L),
                new TCAspects.TC_AspectStack(TCAspects.TERRA, 1L)));

        ItemList.Display_ITS_FREE.set(
            addItemWithLocalizationKeys(
                Display_ITS_FREE.ID,
                "gt.item.display.its_free.name",
                "gt.item.display.its_free.tooltip",
                SubTag.INVISIBLE,
                new TCAspects.TC_AspectStack(TCAspects.LUCRUM, 1L)));
        ItemList.Item_Redstone_Sniffer.set(
            new ItemRedstoneSniffer(
                "Item_Redstone_Sniffer",
                "gt.item.redstone_sniffer.name",
                "gt.item.redstone_sniffer.tooltip"));
        ItemList.Vajra_Core.set(addItemWithLocalizationKeys(Vajra_Core.ID, "gt.item.vajra_core.name", ""));
        ItemList.Magnetron.set(addItemWithLocalizationKeys(Magnetron.ID, "gt.item.magnetron.name", ""));

        final ItemGTToolbox itemToolbox = new ItemGTToolbox(
            "Item_Toolbox",
            "Field Engineer's Toolbox",
            "A container to hold your tools!");
        ItemList.ToolBox.set(itemToolbox);

        if (GregTechAPI.sThaumcraftCompat != null) {
            GregTechAPI.sThaumcraftCompat.registerThaumcraftAspectsToItem(
                new ItemStack(itemToolbox),
                ImmutableList.of(
                    new TCAspects.TC_AspectStack(TCAspects.INSTRUMENTUM, 4),
                    new TCAspects.TC_AspectStack(TCAspects.VACUOS, 4),
                    new TCAspects.TC_AspectStack(TCAspects.COGNITIO, 2)),
                false);
        }

        setAllFluidContainerStats();
        setBurnValues();
        registerCovers();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        super.onLeftClickEntity(aStack, aPlayer, aEntity);
        return false;
    }

    @Override
    public boolean hasProjectile(SubTag aProjectileType, ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        return ((aDamage >= 25000) && (aDamage < 27000)) || (super.hasProjectile(aProjectileType, aStack));
    }

    @Override
    public boolean isItemStackUsable(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        Materials aMaterial = GregTechAPI.sGeneratedMaterials[(aDamage % 1000)];
        if ((aDamage >= 25000) && (aDamage < 27000) && (aMaterial != null) && (aMaterial.mToolEnchantment != null)) {
            Enchantment tEnchant = aMaterial.mToolEnchantment == Enchantment.fortune ? Enchantment.looting
                : aMaterial.mToolEnchantment;
            if (tEnchant.type == EnumEnchantmentType.weapon) {
                if (!ItemStackNBT.getBoolean(aStack, "GT.HasBeenUpdated")) {
                    ItemStackNBT.setBoolean(aStack, "GT.HasBeenUpdated", true);
                    GTUtility.ItemNBT.addEnchantment(aStack, tEnchant, aMaterial.mToolEnchantmentLevel);
                }
            }
        }
        return super.isItemStackUsable(aStack);
    }

    private void setAllFluidContainerStats() {
        setFluidContainerStats(32000 + Food_Raw_Fries.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_Fries.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_Raw_PotatoChips.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_PotatoChips.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_ChiliChips.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_Sandwich_Veggie.ID, 0L, 32L);
        setFluidContainerStats(32000 + Food_Sandwich_Cheese.ID, 0L, 32L);
        setFluidContainerStats(32000 + Food_Sandwich_Bacon.ID, 0L, 32L);
        setFluidContainerStats(32000 + Food_Sandwich_Steak.ID, 0L, 32L);
        setFluidContainerStats(32000 + Food_Large_Sandwich_Veggie.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_Large_Sandwich_Cheese.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_Large_Sandwich_Bacon.ID, 0L, 16L);
        setFluidContainerStats(32000 + Food_Large_Sandwich_Steak.ID, 0L, 16L);
    }

    private void setBurnValues() {
        setBurnValue(32000 + Plank_Oak.ID, 75);
        setBurnValue(32000 + Plank_Spruce.ID, 75);
        setBurnValue(32000 + Plank_Birch.ID, 75);
        setBurnValue(32000 + Plank_Jungle.ID, 75);
        setBurnValue(32000 + Plank_Acacia.ID, 75);
        setBurnValue(32000 + Plank_DarkOak.ID, 75);
        setBurnValue(32000 + Plank_Larch.ID, 75);
        setBurnValue(32000 + Plank_Teak.ID, 75);
        setBurnValue(32000 + Plank_Acacia_Green.ID, 75);
        setBurnValue(32000 + Plank_Lime.ID, 75);
        setBurnValue(32000 + Plank_Chestnut.ID, 75);
        setBurnValue(32000 + Plank_Wenge.ID, 75);
        setBurnValue(32000 + Plank_Baobab.ID, 75);
        setBurnValue(32000 + Plank_Sequoia.ID, 75);
        setBurnValue(32000 + Plank_Kapok.ID, 75);
        setBurnValue(32000 + Plank_Ebony.ID, 75);
        setBurnValue(32000 + Plank_Mahagony.ID, 75);
        setBurnValue(32000 + Plank_Balsa.ID, 75);
        setBurnValue(32000 + Plank_Willow.ID, 75);
        setBurnValue(32000 + Plank_Walnut.ID, 75);
        setBurnValue(32000 + Plank_Greenheart.ID, 75);
        setBurnValue(32000 + Plank_Cherry.ID, 75);
        setBurnValue(32000 + Plank_Mahoe.ID, 75);
        setBurnValue(32000 + Plank_Poplar.ID, 75);
        setBurnValue(32000 + Plank_Palm.ID, 75);
        setBurnValue(32000 + Plank_Papaya.ID, 75);
        setBurnValue(32000 + Plank_Pine.ID, 75);
        setBurnValue(32000 + Plank_Plum.ID, 75);
        setBurnValue(32000 + Plank_Maple.ID, 75);
        setBurnValue(32000 + Plank_Citrus.ID, 75);
        setBurnValue(32000 + Plank_Cherry_EFR.ID, 75);
    }

    public void registerCovers() {
        CoverRegistry.registerCover(
            ItemList.Cover_AdvancedRedstoneTransmitter.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_ADVANCED_REDSTONE_TRANSMITTER)),
            context -> new CoverAdvancedRedstoneTransmitterExternal(
                context,
                TextureFactory.of(OVERLAY_ADVANCED_REDSTONE_TRANSMITTER)));

        CoverRegistry.registerCover(
            ItemList.Cover_AdvancedRedstoneReceiver.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_ADVANCED_REDSTONE_RECEIVER)),
            context -> new CoverAdvancedRedstoneReceiverExternal(
                context,
                TextureFactory.of(OVERLAY_ADVANCED_REDSTONE_RECEIVER)));

        CoverRegistry.registerCover(
            ItemList.Cover_WirelessFluidDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_WIRELESS_FLUID_DETECTOR)),
            context -> new CoverWirelessFluidDetector(context, TextureFactory.of(OVERLAY_WIRELESS_FLUID_DETECTOR)));

        CoverRegistry.registerCover(
            ItemList.Cover_WirelessItemDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_WIRELESS_ITEM_DETECTOR)),
            context -> new CoverWirelessItemDetector(context, TextureFactory.of(OVERLAY_WIRELESS_ITEM_DETECTOR)));

        CoverRegistry.registerCover(
            ItemList.Cover_WirelessActivityDetector.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_WIRELESS_ACTIVITYDETECTOR)),
            context -> new CoverWirelessDoesWorkDetector(
                context,
                TextureFactory.of(OVERLAY_WIRELESS_ACTIVITYDETECTOR)));

        CoverRegistry.registerCover(
            ItemList.Cover_WirelessNeedsMaintainance.get(1L),
            TextureFactory.of(MACHINE_CASINGS[2][0], TextureFactory.of(OVERLAY_WIRELESS_MAINTENANCE_DETECTOR)),
            context -> new CoverWirelessMaintenanceDetector(
                context,
                TextureFactory.of(OVERLAY_WIRELESS_MAINTENANCE_DETECTOR)));
    }

    @Override
    public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
        return (aDoShowAllItems) || (!aPrefix.getName()
            .startsWith("toolHead"));
    }

    @Override
    public final ItemStack getContainerItem(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        if (aDamage < 32000) {
            return null;
        }
        return null;
    }
}
