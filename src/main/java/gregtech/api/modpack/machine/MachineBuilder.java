package gregtech.api.modpack.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.util.Lang;
import gregtech.api.recipe.RecipeMap;

/**
 * Builder for GregTech style single block machines ({@link MTEBasicMachine}).
 *
 * <pre>{@code
 * // LV..EV macerators, metas 100..103, names "LV Crusher" ... "EV Crusher"
 * List<MTEBasicMachine> crushers = MachineBuilder.create("crusher")
 *     .name("%s Crusher")
 *     .recipeMap(MyRecipeMaps.CRUSHER)
 *     .items(1, 2)                         // 1 input slot, 2 output slots
 *     .overlay("machines/crusher")         // textures/blocks/machines/crusher/overlay_front.png ...
 *     .description("Crushes ores into dust")
 *     .tiers(1, 4)
 *     .startMeta(100)
 *     .register();
 *
 * // one tier with fluids, programmed circuit slot and custom logic
 * MachineBuilder.create("mixer").name("Basic Mixer").recipeMap(MyRecipeMaps.MIXER)
 *     .items(4, 1).fluids(1, 1).tankCapacity(16_000).circuitSlot()
 *     .logic(new MyMixerLogic())
 *     .tier(1).startMeta(110).register();
 *
 * // a subclass with completely custom logic
 * MachineBuilder.create("solar_crusher").factory(MTESolarCrusher::new)...register();
 * }</pre>
 *
 * Metas live in the {@link gregtech.api.enums.MTEArrays#HATCH} array together with hatches and multiblock
 * controllers. Metas 32000+ are reserved for the built in hatches, see {@link gregtech.api.modpack.hatch.Hatches}.
 */
public final class MachineBuilder {

    private final String id;
    private String localName;
    private RecipeMap<?> recipeMap;
    private int itemInputs = 1;
    private int itemOutputs = 1;
    private int fluidInputs = 0;
    private int fluidOutputs = 0;
    private int tankCapacity = 16_000;
    private long amperage = 1;
    private long euStorage = 0;
    private boolean circuitSlot;
    private boolean generator;
    private boolean perfectOverclock;
    private MachineOverlay overlay = MachineOverlay.NONE;
    private MachineLogic logic = MachineLogic.NONE;
    private final List<String> description = new ArrayList<>();
    private MachineConfig.Factory factory = MTEBasicMachine::new;
    private int minTier = 1;
    private int maxTier = 1;
    private int startMeta = -1;

    private MachineBuilder(String id) {
        this.id = id;
        this.localName = id;
    }

    /**
     * @param id lower case name, used for the unlocalized name ({@code gt.blockmachines.<id>.<tier>.name})
     */
    public static MachineBuilder create(String id) {
        return new MachineBuilder(id);
    }

    /** English name, {@code %s} is replaced by the tier name (LV, MV, ...). */
    public MachineBuilder name(String localName) {
        this.localName = localName;
        return this;
    }

    public MachineBuilder recipeMap(RecipeMap<?> recipeMap) {
        this.recipeMap = recipeMap;
        return this;
    }

    /** Item input and output slot count. */
    public MachineBuilder items(int inputs, int outputs) {
        this.itemInputs = inputs;
        this.itemOutputs = outputs;
        return this;
    }

    /** Fluid input and output tank count. */
    public MachineBuilder fluids(int inputs, int outputs) {
        this.fluidInputs = inputs;
        this.fluidOutputs = outputs;
        return this;
    }

    public MachineBuilder tankCapacity(int liters) {
        this.tankCapacity = liters;
        return this;
    }

    /** Max amperes for the recipe (and +1 accepted from cables). */
    public MachineBuilder amperage(long amperage) {
        this.amperage = amperage;
        return this;
    }

    /** Internal EU buffer, default {@code 512 + V * 64}. */
    public MachineBuilder euStorage(long eu) {
        this.euStorage = eu;
        return this;
    }

    /** Adds a ghost programmed circuit slot (recipes with circuits). */
    public MachineBuilder circuitSlot() {
        this.circuitSlot = true;
        return this;
    }

    /**
     * Generator mode: recipes produce their EU/t instead of consuming it, energy is emitted from the front face.
     */
    public MachineBuilder generator() {
        this.generator = true;
        return this;
    }

    /** 4x speed per overclock instead of 2x. */
    public MachineBuilder perfectOverclock() {
        this.perfectOverclock = true;
        return this;
    }

    /** Overlay folder, see {@link MachineOverlay}. */
    public MachineBuilder overlay(String path) {
        this.overlay = MachineOverlay.of(path);
        return this;
    }

    public MachineBuilder description(String... lines) {
        Collections.addAll(description, lines);
        return this;
    }

    /** Custom logic hooks without subclassing. */
    public MachineBuilder logic(MachineLogic logic) {
        this.logic = logic;
        return this;
    }

    /** Use your own subclass: {@code .factory(MyMachine::new)}, the subclass needs a {@code (MachineConfig)} ctor. */
    public MachineBuilder factory(MachineConfig.Factory factory) {
        this.factory = factory;
        return this;
    }

    public MachineBuilder tier(int tier) {
        return tiers(tier, tier);
    }

    /** Creates one machine per tier, both inclusive (1 = LV ... 14 = MAX). */
    public MachineBuilder tiers(int minTier, int maxTier) {
        this.minTier = minTier;
        this.maxTier = maxTier;
        return this;
    }

    /** Meta of the lowest tier, the next tiers use the following metas. */
    public MachineBuilder startMeta(int meta) {
        this.startMeta = meta;
        return this;
    }

    public List<MTEBasicMachine> register() {
        ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "Machine " + id);
        if (startMeta <= 0) {
            throw new IllegalStateException("Machine " + id + " needs a startMeta > 0");
        }
        List<MTEBasicMachine> result = new ArrayList<>();
        boolean multipleTiers = maxTier > minTier;
        for (int tier = minTier; tier <= maxTier; tier++) {
            String tierId = multipleTiers ? id + "." + GTValues.VN[tier].toLowerCase() : id;
            String tierName = localName.contains("%s") ? String.format(localName, GTValues.VN[tier]) : localName;
            // spotless:off
            MachineConfig config = new MachineConfig(tierId, tierName, tier, recipeMap, itemInputs, itemOutputs,
                fluidInputs, fluidOutputs, tankCapacity, amperage, euStorage, circuitSlot, generator,
                perfectOverclock, overlay, logic, List.copyOf(description), factory);
            // spotless:on
            MTEBasicMachine prototype = factory.create(config)
                .asPrototype(startMeta + tier - minTier);
            Lang.add(prototype.getLocalNameKey(), tierName);
            result.add(prototype);
        }
        return result;
    }

    /** Stack of a registered machine. */
    public static ItemStack stack(MTEBasicMachine machine, int amount) {
        return machine.getStackForm(amount);
    }
}
