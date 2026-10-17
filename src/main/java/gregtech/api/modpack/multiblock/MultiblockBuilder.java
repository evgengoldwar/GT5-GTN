package gregtech.api.modpack.multiblock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.IStructureElement;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureUtility;

import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.hatch.CasingTextures;
import gregtech.api.modpack.hatch.HatchType;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.modpack.util.Lang;
import gregtech.api.modpack.util.Prototypes;
import gregtech.api.recipe.RecipeMap;
import gregtech.common.MaterialSystem.Api.Material;
import gregtech.common.MaterialSystem.Api.MaterialType;

/**
 * Builds a multiblock without writing a class.
 * <p>
 * The shape is written layer by layer from the top, each layer as rows from the front (controller side) to the back,
 * as seen from above. Special characters: {@code '~'} controller, {@code '-'} air, {@code ' '} anything.
 *
 * <pre>{@code
 * MultiblockBuilder.create("big_furnace")
 *     .name("Big Furnace")
 *     .shape(new String[][] {
 *         { "CCC", "CCC", "CCC" },
 *         { "H~H", "C-C", "CCC" },
 *         { "CCC", "CCC", "CCC" } })
 *     .casing('C', Materials.Steel)
 *     .hatchOrCasing('H', Materials.Steel, HatchType.PROCESSING)
 *     .minCasings(12)
 *     .requireHatches(HatchType.ENERGY, HatchType.INPUT_BUS, HatchType.OUTPUT_BUS)
 *     .recipeMap(MyRecipeMaps.BIG_FURNACE)
 *     .parallel(8)
 *     .speed(2.0)                      // 2x faster
 *     .overlay("machines/big_furnace")
 *     .description("Smelts 8 items at once")
 *     .meta(600)
 *     .register();
 * }</pre>
 */
public final class MultiblockBuilder {

    private final String id;
    private String localName;
    private String[][] shape;
    private final Map<Character, IStructureElement<MTEMultiblockBase>> elements = new LinkedHashMap<>();
    private int casingTextureId = -1;
    private int minCasings;
    private final Set<HatchType> requiredHatches = EnumSet.noneOf(HatchType.class);
    private RecipeMap<?> recipeMap;
    private int maxParallel = 1;
    private double speedBonus = 1.0;
    private double euModifier = 1.0;
    private boolean perfectOverclock;
    private boolean generator;
    private MachineOverlay overlay = MachineOverlay.NONE;
    private MultiblockLogic logic = MultiblockLogic.NONE;
    private final List<String> description = new ArrayList<>();
    private MultiblockConfig.Factory factory = MTEConfigurableMultiblock::new;
    private int meta = -1;

    private MultiblockBuilder(String id) {
        this.id = id;
        this.localName = id;
    }

    public static MultiblockBuilder create(String id) {
        return new MultiblockBuilder(id);
    }

    public MultiblockBuilder name(String localName) {
        this.localName = localName;
        return this;
    }

    /** Layers from the top, rows from the front, see the class doc. Must contain exactly one {@code '~'}. */
    public MultiblockBuilder shape(String[][] layers) {
        this.shape = layers;
        return this;
    }

    public MultiblockBuilder element(char symbol, IStructureElement<MTEMultiblockBase> element) {
        elements.put(symbol, element);
        return this;
    }

    public MultiblockBuilder casing(char symbol, Block block, int meta) {
        if (casingTextureId < 0) {
            casingTextureId = CasingTextures.register(block, meta);
        }
        return element(symbol, MultiblockElements.casing(block, meta));
    }

    public MultiblockBuilder casing(char symbol, Material material) {
        return casing(symbol, material.getBlock(MaterialType.CASING), material.getMeta());
    }

    /** A casing position that also accepts the given hatch types (all types if none given). */
    public MultiblockBuilder hatchOrCasing(char symbol, Block block, int meta, HatchType... hatches) {
        if (casingTextureId < 0) {
            casingTextureId = CasingTextures.register(block, meta);
        }
        return element(symbol, MultiblockElements.hatchOrCasing(block, meta, hatches));
    }

    public MultiblockBuilder hatchOrCasing(char symbol, Material material, HatchType... hatches) {
        return hatchOrCasing(symbol, material.getBlock(MaterialType.CASING), material.getMeta(), hatches);
    }

    /** Texture of the controller and of hatches without a casing element; default is the first casing. */
    public MultiblockBuilder controllerTexture(Block block, int meta) {
        this.casingTextureId = CasingTextures.register(block, meta);
        return this;
    }

    public MultiblockBuilder minCasings(int count) {
        this.minCasings = count;
        return this;
    }

    /** At least one hatch of every given type must be present. */
    public MultiblockBuilder requireHatches(HatchType... types) {
        Collections.addAll(requiredHatches, types);
        return this;
    }

    public MultiblockBuilder recipeMap(RecipeMap<?> recipeMap) {
        this.recipeMap = recipeMap;
        return this;
    }

    public MultiblockBuilder parallel(int maxParallel) {
        this.maxParallel = maxParallel;
        return this;
    }

    /** Speed multiplier: 2.0 = recipes take half the time. */
    public MultiblockBuilder speed(double multiplier) {
        this.speedBonus = 1.0 / multiplier;
        return this;
    }

    /** EU/t multiplier: 0.8 = 20% less energy. */
    public MultiblockBuilder euModifier(double modifier) {
        this.euModifier = modifier;
        return this;
    }

    public MultiblockBuilder perfectOverclock() {
        this.perfectOverclock = true;
        return this;
    }

    /** Recipes produce EU into dynamo hatches instead of consuming it. */
    public MultiblockBuilder generator() {
        this.generator = true;
        return this;
    }

    public MultiblockBuilder overlay(String path) {
        this.overlay = MachineOverlay.of(path);
        return this;
    }

    public MultiblockBuilder description(String... lines) {
        Collections.addAll(description, lines);
        return this;
    }

    public MultiblockBuilder logic(MultiblockLogic logic) {
        this.logic = logic;
        return this;
    }

    /** Own subclass of {@link MTEConfigurableMultiblock} with a {@code (MultiblockConfig)} constructor. */
    public MultiblockBuilder factory(MultiblockConfig.Factory factory) {
        this.factory = factory;
        return this;
    }

    public MultiblockBuilder meta(int meta) {
        this.meta = meta;
        return this;
    }

    public MTEConfigurableMultiblock register() {
        ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "Multiblock " + id);
        if (shape == null) {
            throw new IllegalStateException("Multiblock " + id + " has no shape");
        }
        if (meta <= 0) {
            throw new IllegalStateException("Multiblock " + id + " needs a meta > 0");
        }
        int[] offset = findController(shape);

        StructureDefinition.Builder<MTEMultiblockBase> definition = StructureDefinition.<MTEMultiblockBase>builder()
            .addShape(MTEMultiblockBase.MAIN_PIECE, StructureUtility.transpose(shape));
        for (Map.Entry<Character, IStructureElement<MTEMultiblockBase>> entry : elements.entrySet()) {
            definition.addElement(entry.getKey(), entry.getValue());
        }
        IStructureDefinition<MTEMultiblockBase> structure = definition.build();

        // spotless:off
        MultiblockConfig config = new MultiblockConfig(id, localName, structure, offset[0], offset[1], offset[2],
            Math.max(0, casingTextureId), minCasings, EnumSet.copyOf(requiredHatches), recipeMap, maxParallel,
            speedBonus, euModifier, perfectOverclock, generator, overlay, logic, List.copyOf(description), factory);
        // spotless:on
        MTEConfigurableMultiblock controller = Prototypes.register(factory.create(config), meta);
        Lang.add(controller.getLocalNameKey(), localName);
        return controller;
    }

    /** @return {column, layer, row} of the '~' */
    static int[] findController(String[][] layers) {
        int[] found = null;
        for (int layer = 0; layer < layers.length; layer++) {
            for (int row = 0; row < layers[layer].length; row++) {
                int column = layers[layer][row].indexOf('~');
                if (column >= 0) {
                    if (found != null) {
                        throw new IllegalArgumentException("Multiblock shape has more than one '~'");
                    }
                    found = new int[] { column, layer, row };
                }
            }
        }
        if (found == null) {
            throw new IllegalArgumentException("Multiblock shape has no controller '~'");
        }
        return found;
    }
}
