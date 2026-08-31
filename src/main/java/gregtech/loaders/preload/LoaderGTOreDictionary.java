package gregtech.loaders.preload;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.enums.Mods.EtFuturumRequiem;
import static gregtech.api.enums.Mods.GalacticraftMars;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TwilightForest;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OreDictNames;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;

public class LoaderGTOreDictionary implements Runnable {

    @Override
    public void run() {
        GT_FML_LOGGER.debug("GTMod: Register OreDict Entries of Non-GT-Items.");
        GTOreDictUnificator.set(OrePrefixes.cell, Materials_OLD.Empty, ItemList.Cell_Empty.get(1L));
        // GTOreDictUnificator.set(OrePrefixes.cell, Materials.Lava, ItemList.Cell_Lava.get(1L));
        // GTOreDictUnificator.set(OrePrefixes.cell, Materials.Lava, GTModHandler.getIC2Item("lavaCell", 1L));
        GTOreDictUnificator.set(OrePrefixes.cell, Materials_OLD.Water, ItemList.Cell_Water.get(1L));
        GTOreDictUnificator.set(OrePrefixes.cell, Materials_OLD.Water, GTModHandler.getIC2Item("waterCell", 1L));
        GTOreDictUnificator.set(
            OrePrefixes.cell,
            Materials_OLD.Creosote,
            GTModHandler.getModItem(Railcraft.ID, "fluid.creosote.cell", 1L));

        GTOreDictUnificator.set(OrePrefixes.cell, Materials_OLD.UUMatter, GTModHandler.getIC2Item("uuMatterCell", 1L));
        GTOreDictUnificator
            .set(OrePrefixes.cell, Materials_OLD.ConstructionFoam, GTModHandler.getIC2Item("CFCell", 1L));

        GTOreDictUnificator.set(OrePrefixes.bucket, Materials_OLD.Empty, new ItemStack(Items.bucket, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.bucket, Materials_OLD.Water, new ItemStack(Items.water_bucket, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.bucket, Materials_OLD.Lava, new ItemStack(Items.lava_bucket, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.bucket, Materials_OLD.Milk, new ItemStack(Items.milk_bucket, 1, 0));
        // Clay buckets handled in gregtech.common.GTProxy.onInitialization()
        // as they aren't registered until Iguana Tweaks pre-init.

        GTOreDictUnificator
            .set(OrePrefixes.plateAlloy, Materials_OLD.Iridium, GTModHandler.getIC2Item("iridiumPlate", 1L));
        GTOreDictUnificator.set(OrePrefixes.plateAlloy, Materials_OLD.HV, GTModHandler.getIC2Item("advancedAlloy", 1L));
        GTOreDictUnificator
            .set(OrePrefixes.plateAlloy, Materials_OLD.Carbon, GTModHandler.getIC2Item("carbonPlate", 1L));

        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Coal, new ItemStack(Blocks.coal_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Iron, new ItemStack(Blocks.iron_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Lapis, new ItemStack(Blocks.lapis_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Redstone, new ItemStack(Blocks.redstone_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Redstone, new ItemStack(Blocks.lit_redstone_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Gold, new ItemStack(Blocks.gold_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Diamond, new ItemStack(Blocks.diamond_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.Emerald, new ItemStack(Blocks.emerald_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ore, Materials_OLD.NetherQuartz, new ItemStack(Blocks.quartz_ore, 1));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Copper, GTModHandler.getIC2Item("copperIngot", 1L));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Tin, GTModHandler.getIC2Item("tinIngot", 1L));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Lead, GTModHandler.getIC2Item("leadIngot", 1L));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Bronze, GTModHandler.getIC2Item("bronzeIngot", 1L));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Silver, GTModHandler.getIC2Item("silverIngot", 1L));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.Lapis, new ItemStack(Items.dye, 1, 4));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.EnderEye, new ItemStack(Items.ender_eye, 1));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.EnderPearl, new ItemStack(Items.ender_pearl, 1));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.Diamond, new ItemStack(Items.diamond, 1));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.Emerald, new ItemStack(Items.emerald, 1));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.Coal, new ItemStack(Items.coal, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.Charcoal, new ItemStack(Items.coal, 1, 1));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.NetherQuartz, new ItemStack(Items.quartz, 1));
        GTOreDictUnificator.set(OrePrefixes.gem, Materials_OLD.NetherStar, new ItemStack(Items.nether_star, 1));
        GTOreDictUnificator.set(OrePrefixes.nugget, Materials_OLD.Gold, new ItemStack(Items.gold_nugget, 1));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Gold, new ItemStack(Items.gold_ingot, 1));
        GTOreDictUnificator.set(OrePrefixes.ingot, Materials_OLD.Iron, new ItemStack(Items.iron_ingot, 1));
        GTOreDictUnificator.set(OrePrefixes.plate, Materials_OLD.Paper, new ItemStack(Items.paper, 1));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials_OLD.Sugar, new ItemStack(Items.sugar, 1));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials_OLD.Bone, ItemList.Dye_Bonemeal.get(1L));
        GTOreDictUnificator.set(OrePrefixes.stick, Materials_OLD.Wood, new ItemStack(Items.stick, 1));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials_OLD.Redstone, new ItemStack(Items.redstone, 1));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials_OLD.Gunpowder, new ItemStack(Items.gunpowder, 1));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials_OLD.Glowstone, new ItemStack(Items.glowstone_dust, 1));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials_OLD.Blaze, new ItemStack(Items.blaze_powder, 1));
        GTOreDictUnificator.set(OrePrefixes.stick, Materials_OLD.Blaze, new ItemStack(Items.blaze_rod, 1));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Iron, new ItemStack(Blocks.iron_block, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Gold, new ItemStack(Blocks.gold_block, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Diamond, new ItemStack(Blocks.diamond_block, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Emerald, new ItemStack(Blocks.emerald_block, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Lapis, new ItemStack(Blocks.lapis_block, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Coal, new ItemStack(Blocks.coal_block, 1, 0));
        GTOreDictUnificator.set(OrePrefixes.block, Materials_OLD.Redstone, new ItemStack(Blocks.redstone_block, 1, 0));
        GTOreDictUnificator
            .set(OrePrefixes.block, Materials_OLD.NetherQuartz, new ItemStack(Blocks.quartz_block, 1, 0));

        if (Blocks.ender_chest != null) {
            GTOreDictUnificator.registerOre(OreDictNames.enderChest, new ItemStack(Blocks.ender_chest, 1));
        }
        GTOreDictUnificator.registerOre(OreDictNames.craftingAnvil, new ItemStack(Blocks.anvil, 1));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingAnvil, GTModHandler.getModItem(Railcraft.ID, "anvil", 1L, 0));

        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Basalt, GTModHandler.getModItem(Railcraft.ID, "cube", 1L, 6));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Marble, GTModHandler.getModItem(Railcraft.ID, "cube", 1L, 7));
        GTOreDictUnificator.registerOre(
            OrePrefixes.stone,
            Materials_OLD.Basalt,
            GTModHandler.getModItem(Railcraft.ID, "brick.abyssal", 1L, 32767));
        GTOreDictUnificator.registerOre(
            OrePrefixes.stone,
            Materials_OLD.Marble,
            GTModHandler.getModItem(Railcraft.ID, "brick.quarried", 1L, 32767));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Obsidian, new ItemStack(Blocks.obsidian, 1, 32767));
        GTOreDictUnificator.registerOre(OrePrefixes.stone, Materials_OLD.Stone, new ItemStack(Blocks.stone, 1, 0));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneMossy, new ItemStack(Blocks.mossy_cobblestone, 1, 32767));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneCobble, new ItemStack(Blocks.mossy_cobblestone, 1, 32767));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneCobble, new ItemStack(Blocks.cobblestone, 1, 32767));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneSmooth, new ItemStack(Blocks.stone, 1, 32767));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneBricks, new ItemStack(Blocks.stonebrick, 1, 32767));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneMossy, new ItemStack(Blocks.stonebrick, 1, 1));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneCracked, new ItemStack(Blocks.stonebrick, 1, 2));
        GTOreDictUnificator.registerOre(OrePrefixes.stoneChiseled, new ItemStack(Blocks.stonebrick, 1, 3));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Sand, new ItemStack(Blocks.sandstone, 1, 32767));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Netherrack, new ItemStack(Blocks.netherrack, 1, 32767));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.NetherBrick, new ItemStack(Blocks.nether_brick, 1, 32767));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Endstone, new ItemStack(Blocks.end_stone, 1, 32767));
        GTOreDictUnificator
            .registerOre(OrePrefixes.stone, Materials_OLD.Glowstone, new ItemStack(Blocks.glowstone, 1, 32767));

        GTOreDictUnificator
            .registerOre("paperResearchFragment", GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 9));
        GTOreDictUnificator.registerOre(
            "itemCertusQuartz",
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 1));
        GTOreDictUnificator.registerOre(
            "itemCertusQuartz",
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 10));
        GTOreDictUnificator.registerOre(
            "itemNetherQuartz",
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 11));
        GTOreDictUnificator.registerOre(
            OreDictNames.craftingQuartz,
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 1));
        GTOreDictUnificator.registerOre(
            OreDictNames.craftingQuartz,
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 10));
        GTOreDictUnificator.registerOre(
            OreDictNames.craftingQuartz,
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 11));
        GTOreDictUnificator
            .registerOre(OrePrefixes.battery, Materials_OLD.LV, GTModHandler.getIC2Item("chargedReBattery", 1L, 32767));

        GTOreDictUnificator
            .registerOre(OreDictNames.craftingWireCopper, GTModHandler.getIC2Item("insulatedCopperCableItem", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingWireGold, GTModHandler.getIC2Item("insulatedGoldCableItem", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingWireIron, GTModHandler.getIC2Item("insulatedIronCableItem", 1L));
        GTOreDictUnificator.registerOre(
            OreDictNames.craftingWireTin,
            GTModHandler
                .getIC2Item("insulatedTinCableItem", 1L, GTModHandler.getIC2Item("insulatedCopperCableItem", 1L)));

        GTOreDictUnificator
            .registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.redstone_torch, 1, 32767));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.unlit_redstone_torch, 1, 32767));

        GTOreDictUnificator.registerOre(OreDictNames.craftingWorkBench, new ItemStack(Blocks.crafting_table, 1));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingWorkBench, new ItemStack(GregTechAPI.sBlockMachines, 1, 16));

        GTOreDictUnificator.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.piston, 1, 32767));
        GTOreDictUnificator.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.sticky_piston, 1, 32767));

        GTOreDictUnificator.registerOre(OreDictNames.craftingSafe, new ItemStack(GregTechAPI.sBlockMachines, 1, 45));
        GTOreDictUnificator.registerOre(OreDictNames.craftingSafe, GTModHandler.getIC2Item("personalSafe", 1L));

        GTOreDictUnificator.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.chest, 1, 32767));
        GTOreDictUnificator.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.trapped_chest, 1, 32767));

        GTOreDictUnificator.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.furnace, 1, 32767));

        GTOreDictUnificator.registerOre(OreDictNames.craftingPump, GTModHandler.getIC2Item("pump", 1L));
        GTOreDictUnificator.registerOre(OreDictNames.craftingElectromagnet, GTModHandler.getIC2Item("magnetizer", 1L));
        GTOreDictUnificator.registerOre(OreDictNames.craftingTeleporter, GTModHandler.getIC2Item("teleporter", 1L));

        GTOreDictUnificator.registerOre(OreDictNames.craftingMacerator, GTModHandler.getIC2Item("macerator", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingMacerator, new ItemStack(GregTechAPI.sBlockMachines, 1, 50));

        GTOreDictUnificator.registerOre(OreDictNames.craftingExtractor, GTModHandler.getIC2Item("extractor", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingExtractor, new ItemStack(GregTechAPI.sBlockMachines, 1, 51));

        GTOreDictUnificator.registerOre(OreDictNames.craftingCompressor, GTModHandler.getIC2Item("compressor", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingCompressor, new ItemStack(GregTechAPI.sBlockMachines, 1, 52));

        GTOreDictUnificator.registerOre(OreDictNames.craftingRecycler, GTModHandler.getIC2Item("recycler", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingRecycler, new ItemStack(GregTechAPI.sBlockMachines, 1, 53));

        GTOreDictUnificator.registerOre(
            OreDictNames.craftingBlastFurnace,
            GTModHandler.getModItem(EtFuturumRequiem.ID, "blast_furnace", 1));

        GTOreDictUnificator
            .registerOre(OreDictNames.craftingCentrifuge, new ItemStack(GregTechAPI.sBlockMachines, 1, 62));

        GTOreDictUnificator
            .registerOre(OreDictNames.craftingInductionFurnace, GTModHandler.getIC2Item("inductionFurnace", 1L));

        GTOreDictUnificator
            .registerOre(OreDictNames.craftingElectricFurnace, GTModHandler.getIC2Item("electroFurnace", 1L));
        GTOreDictUnificator
            .registerOre(OreDictNames.craftingElectricFurnace, new ItemStack(GregTechAPI.sBlockMachines, 1, 54));

        GTOreDictUnificator.registerOre(OreDictNames.craftingGenerator, GTModHandler.getIC2Item("generator", 1L));

        GTOreDictUnificator
            .registerOre(OreDictNames.craftingGeothermalGenerator, GTModHandler.getIC2Item("geothermalGenerator", 1L));

        GTOreDictUnificator.registerOre(OreDictNames.craftingFeather, new ItemStack(Items.feather, 1, 32767));
        GTOreDictUnificator.registerOre(
            OreDictNames.craftingFeather,
            GTModHandler.getModItem(TwilightForest.ID, "item.tfFeather", 1L, 32767));

        GTOreDictUnificator.registerOre("itemWheat", new ItemStack(Items.wheat, 1, 32767));
        GTOreDictUnificator.registerOre("paperEmpty", new ItemStack(Items.paper, 1, 32767));
        GTOreDictUnificator.registerOre("paperMap", new ItemStack(Items.map, 1, 32767));
        GTOreDictUnificator.registerOre("paperMap", new ItemStack(Items.filled_map, 1, 32767));
        GTOreDictUnificator.registerOre("bookEmpty", new ItemStack(Items.book, 1, 32767));
        GTOreDictUnificator.registerOre("bookWritable", new ItemStack(Items.writable_book, 1, 32767));
        GTOreDictUnificator.registerOre("bookWritten", new ItemStack(Items.written_book, 1, 32767));
        GTOreDictUnificator.registerOre("bookEnchanted", new ItemStack(Items.enchanted_book, 1, 32767));
        GTOreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.book, 1, 32767));
        GTOreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.writable_book, 1, 32767));
        GTOreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.written_book, 1, 32767));
        GTOreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.enchanted_book, 1, 32767));

        GTOreDictUnificator.addToBlacklist(GTModHandler.getIC2Item("electronicCircuit", 1L));
        GTOreDictUnificator.addToBlacklist(GTModHandler.getIC2Item("advancedCircuit", 1L));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit, Materials_OLD.LV, GTModHandler.getIC2Item("electronicCircuit", 1L));
        GTOreDictUnificator
            .registerOre(OrePrefixes.circuit, Materials_OLD.HV, GTModHandler.getIC2Item("advancedCircuit", 1L));

        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Copper,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 0));
        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Tin,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 1));
        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Bronze,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 2));
        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Gold,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 3));
        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Iron,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 4));
        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Steel,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 5));
        GTOreDictUnificator.registerOre(
            OrePrefixes.itemCasing,
            Materials_OLD.Lead,
            GTModHandler.getModItem(IndustrialCraft2.ID, "itemCasing", 1L, 6));

        // Fake Circuits
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.ULV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitULV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.LV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitLV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.MV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitMV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.HV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitHV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.EV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitEV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.IV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitIV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.LuV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitLuV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.ZPM,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitZPM", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.UV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitUV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.UHV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitUHV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.UEV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitUEV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.UIV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitUIV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.UMV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitUMV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.UXV,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitUXV", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.circuit,
            Materials_OLD.MAX,
            GTModHandler.getModItem(NewHorizonsCoreMod.ID, "CircuitMAX", 1L));

        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.Manasteel,
            GTModHandler.getModItem(Botania.ID, "storage", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.Terrasteel,
            GTModHandler.getModItem(Botania.ID, "storage", 1L, 1));
        GTOreDictUnificator.registerOre(
            OrePrefixes.ingot,
            Materials_OLD.ElvenElementium,
            GTModHandler.getModItem(Botania.ID, "manaResource", 1L, 7));
        GTOreDictUnificator.registerOre(
            OrePrefixes.nugget,
            Materials_OLD.ElvenElementium,
            GTModHandler.getModItem(Botania.ID, "manaResource", 1L, 19));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.ElvenElementium,
            GTModHandler.getModItem(Botania.ID, "storage", 1L, 2));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.Livingrock,
            GTModHandler.getModItem(Botania.ID, "livingrock", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.ingot,
            Materials_OLD.GaiaSpirit,
            GTModHandler.getModItem(Botania.ID, "manaResource", 1L, 14));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.Livingwood,
            GTModHandler.getModItem(Botania.ID, "livingwood", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.Dreamwood,
            GTModHandler.getModItem(Botania.ID, "dreamwood", 1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.gem,
            Materials_OLD.ManaDiamond,
            GTModHandler.getModItem(Botania.ID, "manaResource", 1L, 2));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.ManaDiamond,
            GTModHandler.getModItem(Botania.ID, "storage", 1L, 3));
        GTOreDictUnificator.registerOre(
            OrePrefixes.gem,
            Materials_OLD.Dragonstone,
            GTModHandler.getModItem(Botania.ID, "manaResource", 1L, 9));
        GTOreDictUnificator.registerOre(
            OrePrefixes.block,
            Materials_OLD.Dragonstone,
            GTModHandler.getModItem(Botania.ID, "storage", 1L, 4));

        GTOreDictUnificator.registerOre(
            OrePrefixes.ore,
            Materials_OLD.Desh,
            GTModHandler.getModItem(GalacticraftMars.ID, "item.null", 1L, 0));
    }
}
