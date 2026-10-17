package gregtech.api.modpack.example;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;

import gregtech.api.enums.GTValues;
import gregtech.api.modpack.IModpackModule;
import gregtech.api.modpack.block.BlockBuilder;
import gregtech.api.modpack.block.SimpleBlock;
import gregtech.api.modpack.cover.CoverBehaviors;
import gregtech.api.modpack.cover.CoverBuilder;
import gregtech.api.modpack.hatch.HatchType;
import gregtech.api.modpack.hatch.Hatches;
import gregtech.api.modpack.item.ItemData;
import gregtech.api.modpack.item.ItemEntry;
import gregtech.api.modpack.item.MetaItem;
import gregtech.api.modpack.machine.MTEBasicMachine;
import gregtech.api.modpack.machine.MachineBuilder;
import gregtech.api.modpack.machine.MachineLogic;
import gregtech.api.modpack.multiblock.MTEConfigurableMultiblock;
import gregtech.api.modpack.multiblock.MultiblockBuilder;
import gregtech.api.modpack.multiblock.MultiblockElements;
import gregtech.api.modpack.multiblock.MultiblockLogic;
import gregtech.api.modpack.pipe.Pipes;
import gregtech.api.modpack.recipe.CraftingRecipes;
import gregtech.api.modpack.recipe.RecipeMapFactory;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.common.MaterialSystem.Api.MaterialType;

/**
 * Shows every part of the modpack API. Enable with {@code -Dgregtech.modpack.example=true}.
 * <p>
 * Ids used here: materials 100-104, machines 1000-1100, pipes 2000-2040.
 */
public class ExampleModule implements IModpackModule {

    // recipe maps
    public static RecipeMap<RecipeMapBackend> CRUSHER;
    public static RecipeMap<RecipeMapBackend> MIXER;
    public static RecipeMap<RecipeMapBackend> COMBUSTION_FUELS;
    public static RecipeMap<RecipeMapBackend> BIG_FURNACE;

    // blocks
    public static SimpleBlock MARBLE;
    public static SimpleBlock REINFORCED_GLASS;
    public static SimpleBlock LAMP;

    // items
    public static MetaItem COMPONENTS;
    public static MetaItem TOOLS;
    public static MetaItem COVERS;
    public static ItemEntry BASIC_CIRCUIT, LV_MOTOR, QUANTUM_CHIP, COKE;
    public static ItemEntry MAGNET, HEALING_WAND, SCANNER;
    public static ItemEntry SOLAR_COVER, CONVEYOR_COVER, PUMP_COVER, ACTIVITY_COVER, CONTROLLER_COVER;

    // machines
    public static List<MTEBasicMachine> CRUSHERS;
    public static MTEBasicMachine MIXER_LV;
    public static MTEBasicMachine COMBUSTION_GENERATOR;
    public static MTEBasicMachine SOLAR_CRUSHER;
    public static MTEConfigurableMultiblock BIG_FURNACE_CONTROLLER;

    @Override
    public String getName() {
        return "example";
    }

    // region Materials

    @Override
    public void registerMaterials() {
        ExampleMaterials.register();
    }

    // endregion

    // region Recipe maps

    @Override
    public void registerRecipeMaps() {
        CRUSHER = RecipeMapFactory.simple("example.recipe.crusher", "Crusher", 1, 2, 0, 0);
        MIXER = RecipeMapFactory.simple("example.recipe.mixer", "Mixer", 4, 1, 2, 1);
        COMBUSTION_FUELS = RecipeMapFactory.simple("example.recipe.combustion", "Combustion Fuels", 0, 0, 1, 0);
        BIG_FURNACE = RecipeMapFactory.builder("example.recipe.big_furnace", "Big Furnace")
            .maxIO(6, 6, 0, 0)
            .minInputs(1, 0)
            .build();
    }

    // endregion

    // region Blocks

    @Override
    public void registerBlocks() {
        // textures: assets/gregtech/textures/blocks/blocks_custom/<id>.png by default
        MARBLE = BlockBuilder.create("marble")
            .name("Marble")
            .nameRu("Мрамор")
            .hardness(1.5F)
            .pickaxe(0)
            .oreDict("stoneMarble")
            .build();

        REINFORCED_GLASS = BlockBuilder.create("reinforced_glass")
            .name("Reinforced Glass")
            .material(Material.glass)
            .sound(Block.soundTypeGlass)
            .transparent()
            .resistance(100.0F)
            .tooltip("Blast proof")
            .build();

        LAMP = BlockBuilder.create("lamp")
            .name("Lamp")
            .light(1.0F)
            .material(Material.glass)
            .onRightClick((world, x, y, z, player, side) -> {
                if (!world.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText("It is a lamp."));
                }
                return true;
            })
            .build();

        // many blocks from one grayscale texture
        String[] colors = { "white", "red", "green", "blue", "yellow", "black" };
        int[] rgb = { 0xFFFFFF, 0xC03030, 0x30A030, 0x3050C0, 0xE0D030, 0x303030 };
        for (int i = 0; i < colors.length; i++) {
            BlockBuilder.create("concrete_" + colors[i])
                .name(Character.toUpperCase(colors[i].charAt(0)) + colors[i].substring(1) + " Concrete")
                .texture("blocks_custom/concrete")
                .color(rgb[i])
                .build();
        }
    }

    // endregion

    // region Items

    @Override
    public void registerItems() {
        COMPONENTS = MetaItem.create("components");
        // textures: assets/gregtech/textures/items/items_custom/components/<id>.png by default
        BASIC_CIRCUIT = COMPONENTS.add(1, "basic_circuit")
            .name("Basic Circuit")
            .nameRu("Простая схема")
            .tooltip("Tier: LV")
            .oreDict("circuitBasic")
            .build();
        LV_MOTOR = COMPONENTS.add(2, "lv_motor")
            .name("LV Electric Motor")
            .build();
        QUANTUM_CHIP = COMPONENTS.add(3, "quantum_chip")
            .name("Quantum Chip")
            .tooltip("Used in IV circuits", "Handle with care")
            .rarity(EnumRarity.epic)
            .glint()
            .build();
        COKE = COMPONENTS.add(4, "coke")
            .name("Coke")
            .burnTime(3200)
            .build();

        TOOLS = MetaItem.create("tools");
        MAGNET = TOOLS.add(1, "magnet")
            .name("Magnet")
            .maxStackSize(1)
            .behavior(new MagnetBehavior(8))
            .build();
        HEALING_WAND = TOOLS.add(2, "healing_wand")
            .name("Healing Wand")
            .durability(64)
            .onRightClick((stack, world, player) -> {
                if (!world.isRemote && player.getHealth() < player.getMaxHealth()) {
                    player.heal(4);
                    ItemData.damage(stack, player, 1);
                }
                return stack;
            })
            .onLeftClickEntity((stack, player, target) -> {
                if (target instanceof EntityLivingBase living) {
                    living.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 1));
                }
                return true; // no damage to the target
            })
            .build();
        SCANNER = TOOLS.add(3, "scanner")
            .name("Block Scanner")
            .maxStackSize(1)
            .onUseFirst((stack, player, world, x, y, z, side) -> {
                if (!world.isRemote) {
                    Block block = world.getBlock(x, y, z);
                    player.addChatComponentMessage(
                        new ChatComponentText(
                            block.getLocalizedName() + " meta " + world.getBlockMetadata(x, y, z) + " light "
                                + world.getBlockLightValue(x, y, z)));
                }
                return true;
            })
            .build();

        COVERS = MetaItem.create("covers");
        SOLAR_COVER = COVERS.add(1, "solar_panel")
            .name("Solar Panel Cover")
            .tooltip("8 EU/t in sunlight")
            .build();
        CONVEYOR_COVER = COVERS.add(2, "conveyor")
            .name("Conveyor Cover")
            .build();
        PUMP_COVER = COVERS.add(3, "pump")
            .name("Pump Cover")
            .build();
        ACTIVITY_COVER = COVERS.add(4, "activity_detector")
            .name("Activity Detector Cover")
            .build();
        CONTROLLER_COVER = COVERS.add(5, "machine_controller")
            .name("Machine Controller Cover")
            .build();
    }

    // endregion

    // region Covers

    @Override
    public void registerCovers() {
        // textures: assets/gregtech/textures/blocks/covers/<name>.png
        CoverBuilder.of(SOLAR_COVER)
            .texture("covers/solar_panel")
            .behavior(CoverBehaviors.solarPanel(8))
            .register();
        CoverBuilder.of(CONVEYOR_COVER)
            .texture("covers/conveyor")
            .behavior(CoverBehaviors.conveyor(1, 20))
            .register();
        CoverBuilder.of(PUMP_COVER)
            .texture("covers/pump")
            .behavior(CoverBehaviors.pump(1000, 20))
            .register();
        CoverBuilder.of(ACTIVITY_COVER)
            .texture("covers/activity_detector")
            .behavior(CoverBehaviors.activityDetector())
            .register();
        CoverBuilder.of(CONTROLLER_COVER)
            .texture("covers/machine_controller")
            .behavior(CoverBehaviors.machineController())
            .register();
        // plates as decorative covers
        CoverBuilder.of(ExampleMaterials.Iron.getPlate(1))
            .texture("covers/plate")
            .decorative()
            .register();
    }

    // endregion

    // region Pipes

    @Override
    public void registerPipes() {
        Pipes.cable(ExampleMaterials.Copper, 2000)
            .voltage(GTValues.V[1])
            .amperage(1)
            .loss(2)
            .register(); // 2000..2005 wires
        Pipes.cable(ExampleMaterials.Copper, 2010)
            .voltage(GTValues.V[1])
            .amperage(1)
            .loss(2)
            .insulated()
            .register(); // 2010..2015 cables
        Pipes.fluidPipe(ExampleMaterials.Iron, 2020)
            .capacity(20)
            .heatResistance(1800)
            .register(); // 2020..2024
        Pipes.itemPipe(ExampleMaterials.Tin, 2030)
            .register(); // 2030..2033
    }

    // endregion

    // region Machines

    @Override
    public void registerMachines() {
        // LV..EV crushers -> metas 1000..1003
        CRUSHERS = MachineBuilder.create("crusher")
            .name("%s Crusher")
            .recipeMap(CRUSHER)
            .items(1, 2)
            .overlay("machines/crusher")
            .description("Crushes things into dust")
            .tiers(1, 4)
            .startMeta(1000)
            .register();

        // fluids + circuit + custom logic hooks without a subclass
        MIXER_LV = MachineBuilder.create("mixer")
            .name("Basic Mixer")
            .recipeMap(MIXER)
            .items(4, 1)
            .fluids(2, 1)
            .circuitSlot()
            .overlay("machines/mixer")
            .logic(new MachineLogic() {

                @Override
                public void modifyLogic(MTEBasicMachine machine, ProcessingLogic logic) {
                    // twice as fast at night
                    if (!machine.getGTE()
                        .getWorld()
                        .isDaytime()) {
                        logic.setSpeedBonus(0.5);
                    }
                }
            })
            .tier(1)
            .startMeta(1010)
            .register()
            .get(0);

        // generator: recipes of COMBUSTION_FUELS produce EU
        COMBUSTION_GENERATOR = MachineBuilder.create("combustion_generator")
            .name("Basic Combustion Generator")
            .recipeMap(COMBUSTION_FUELS)
            .items(0, 0)
            .fluids(1, 0)
            .generator()
            .overlay("machines/combustion_generator")
            .tier(1)
            .startMeta(1020)
            .register()
            .get(0);

        // subclass with own logic
        SOLAR_CRUSHER = MachineBuilder.create("solar_crusher")
            .name("Solar Crusher")
            .recipeMap(CRUSHER)
            .items(1, 2)
            .factory(MTESolarCrusher::new)
            .description("Works only during the day")
            .tier(1)
            .startMeta(1030)
            .register()
            .get(0);

        // multiblock without a class
        BIG_FURNACE_CONTROLLER = MultiblockBuilder.create("big_furnace")
            .name("Big Furnace")
            .shape(
                new String[][] { { "CCC", "CCC", "CCC" }, { "H~H", "G-G", "CCC" }, { "HHH", "HHH", "HHH" } })
            .casing('C', ExampleMaterials.Iron)
            .hatchOrCasing('H', ExampleMaterials.Iron, HatchType.PROCESSING)
            .element('G', MultiblockElements.block(Blocks.glass, 0))
            .minCasings(8)
            .requireHatches(HatchType.ENERGY, HatchType.INPUT_BUS, HatchType.OUTPUT_BUS)
            .recipeMap(BIG_FURNACE)
            .parallel(8)
            .speed(2.0)
            .overlay("machines/big_furnace")
            .description("Smelts 8 items at once", "3x3x3, Iron Machine Casings, glass on the sides")
            .logic(new MultiblockLogic() {

                @Override
                public void addInfo(MTEConfigurableMultiblock machine, List<String> info) {
                    info.add("Casings: " + machine.getCasingCount());
                }
            })
            .meta(1100)
            .register();
    }

    // endregion

    // region Recipes

    @Override
    public void registerRecipes() {
        // machine recipes
        GTValues.RA.stdBuilder()
            .itemInputs(ExampleMaterials.Iron.getIngot(1))
            .itemOutputs(ExampleMaterials.Iron.getDust(1), ExampleMaterials.Iron.getStack(MaterialType.TINY_DUST, 2))
            .outputChances(10000, 5000)
            .duration(100)
            .eut(16)
            .addTo(CRUSHER);

        GTValues.RA.stdBuilder()
            .itemInputs(ExampleMaterials.Copper.getDust(3), ExampleMaterials.Tin.getIngot(1))
            .fluidInputs(ExampleMaterials.Oxygen.getGas(1000))
            .itemOutputs(ExampleMaterials.Copper.getIngot(4))
            .duration(200)
            .eut(30)
            .addTo(MIXER);

        GTValues.RA.stdBuilder()
            .fluidInputs(ExampleMaterials.Oxygen.getGas(100))
            .duration(200)
            .eut(32)
            .addTo(COMBUSTION_FUELS);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.cobblestone))
            .itemOutputs(new ItemStack(Blocks.stone))
            .duration(40)
            .eut(30)
            .addTo(BIG_FURNACE);

        // crafting table recipes
        CraftingRecipes.shaped(
            CRUSHERS.get(0)
                .getStackForm(1),
            "PMP",
            "CHC",
            "PMP",
            'P',
            "plateIron",
            'M',
            LV_MOTOR.get(),
            'C',
            "circuitBasic",
            'H',
            Hatches.get(HatchType.ENERGY, 1, 1));
        CraftingRecipes.shapeless(ExampleMaterials.Iron.getNugget(9), "ingotIron");
        CraftingRecipes.shaped(MARBLE.getStack(4), "SS", "SS", 'S', new ItemStack(Blocks.stone));
        CraftingRecipes.smelting(COKE.get(), new ItemStack(Items.coal), 0.1F);
    }

    // endregion
}
