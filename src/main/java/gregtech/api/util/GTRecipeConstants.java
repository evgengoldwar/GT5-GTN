package gregtech.api.util;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.recipe.metadata.SimpleRecipeMetadataKey;
import gregtech.api.util.recipe.QuantumComputerRecipeData;

public class GTRecipeConstants {

    /**
     * Set to true to signal the recipe require low gravity. do nothing if recipe set specialValue explicitly. Can
     * coexist with CLEANROOM just fine
     */
    public static final RecipeMetadataKey<Boolean> LOW_GRAVITY = SimpleRecipeMetadataKey
        .create(Boolean.class, "low_gravity");
    /**
     * Set to true to signal the recipe require cleanroom. do nothing if recipe set specialValue explicitly. Can coexist
     * with LOW_GRAVITY just fine
     */
    public static final RecipeMetadataKey<Boolean> CLEANROOM = SimpleRecipeMetadataKey
        .create(Boolean.class, "cleanroom");
    /**
     * Used for fusion reactor. Denotes ignition threshold.
     */
    public static final RecipeMetadataKey<Long> FUSION_THRESHOLD = SimpleRecipeMetadataKey
        .create(Long.class, "fusion_threshold");

    /**
     * Fuel value.
     */
    public static final RecipeMetadataKey<Integer> FUEL_VALUE = SimpleRecipeMetadataKey
        .create(Integer.class, "fuel_value");
    /**
     * Required heat for heating coil (Kelvin).
     */
    public static final RecipeMetadataKey<Integer> COIL_HEAT = SimpleRecipeMetadataKey
        .create(Integer.class, "coil_heat");

    /**
     * Replicator output material.
     */
    public static final RecipeMetadataKey<Materials> MATERIAL = SimpleRecipeMetadataKey
        .create(Materials.class, "material");
    /**
     * For Microwave.
     */
    public static final RecipeMetadataKey<Boolean> EXPLODE = SimpleRecipeMetadataKey.create(Boolean.class, "explode");
    /**
     * For Microwave.
     */
    public static final RecipeMetadataKey<Boolean> ON_FIRE = SimpleRecipeMetadataKey.create(Boolean.class, "on_fire");

    /**
     * Values of items used in quantum computer, used to show NEI recipes
     */
    public static final RecipeMetadataKey<QuantumComputerRecipeData> QUANTUM_COMPUTER_DATA = SimpleRecipeMetadataKey
        .create(QuantumComputerRecipeData.class, "quantum_computer_data");

    /**
     * Nano Forge Tier.
     */
    public static final RecipeMetadataKey<Integer> NANO_FORGE_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "nano_forge_tier");

    /**
     * FOG Exotic recipe tier.
     */
    public static final RecipeMetadataKey<Integer> FOG_EXOTIC_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "fog_exotic_tier");

    /**
     * FOG Plasma recipe tier.
     */
    public static final RecipeMetadataKey<Integer> FOG_PLASMA_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "fog_plasma_tier");

    /**
     * DEFC Casing tier.
     */
    public static final RecipeMetadataKey<Integer> DEFC_CASING_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "defc_casing_tier");

    /**
     * Chemplant Casing tier. Beware, codewise index starts at 0, but it is tier 1.
     */
    public static final RecipeMetadataKey<Integer> CHEMPLANT_CASING_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "chemplant_casing_tier");

    /**
     * Algae Pond tier.
     */
    public static final RecipeMetadataKey<Integer> ALGAE_POND_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "algae_pond_tier");

    /**
     * QFT Focus tier.
     */
    public static final RecipeMetadataKey<Integer> QFT_FOCUS_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "qft_focus_tier");

    /**
     * QFT catalyst meta.
     */
    public static final RecipeMetadataKey<ItemStack> QFT_CATALYST = SimpleRecipeMetadataKey
        .create(ItemStack.class, "qft_catalyst");

    /**
     * Dissolution Tank Ratio.
     */
    public static final RecipeMetadataKey<Integer> DISSOLUTION_TANK_RATIO = SimpleRecipeMetadataKey
        .create(Integer.class, "dissolution_tank_ratio");

    /**
     * Duration in days for the RTG.
     */
    public static final RecipeMetadataKey<Integer> RTG_DURATION_IN_DAYS = SimpleRecipeMetadataKey
        .create(Integer.class, "rtg_duration_in_days");

    /**
     * Basic output for the Large Naquadah Generator.
     */
    public static final RecipeMetadataKey<Integer> LNG_BASIC_OUTPUT = SimpleRecipeMetadataKey
        .create(Integer.class, "lng_basic_output");

    /**
     * Coil tier for the Naquadah Fuel Refinery.
     */
    public static final RecipeMetadataKey<Integer> NFR_COIL_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "nfr_coil_tier");

    /**
     * NKE range for the neutron activator.
     */
    public static final RecipeMetadataKey<Integer> NKE_RANGE = SimpleRecipeMetadataKey
        .create(Integer.class, "nke_range");
    /**
     * Precise Assembler casing tier.
     */
    public static final RecipeMetadataKey<Integer> PRECISE_ASSEMBLER_CASING_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "precise_assembler_casing_tier");
    /**
     * CoAL casing tier.
     */
    public static final RecipeMetadataKey<Integer> COAL_CASING_TIER = SimpleRecipeMetadataKey
        .create(Integer.class, "coal_casing_tier");

    /**
     * Sparge Tower maximum byproduct outputs.
     */
    public static final RecipeMetadataKey<Integer> SPARGE_MAX_BYPRODUCT = SimpleRecipeMetadataKey
        .create(Integer.class, "sparge_max_byproduct");

    /**
     * Research Station data, upper 48 bits are for minimum computation, lower 16 bit for research amps
     */
    public static final RecipeMetadataKey<Long> RESEARCH_STATION_DATA = SimpleRecipeMetadataKey
        .create(Long.class, "research_station_data");

    public static final RecipeMetadataKey<Integer> EU_MULTIPLIER = SimpleRecipeMetadataKey
        .create(Integer.class, "eu_multiplier");

    public static final RecipeMetadataKey<Double> HALF_LIFE = SimpleRecipeMetadataKey.create(Double.class, "half-life");

    /**
     * Just some trivia to show in the decay recipes, since they don't have a lot of relevant info. Maybe this will come
     * in handy some day.
     */
    public enum DecayType {
        Unknown,
        /** Nucleus emits 2 protons and 2 neutrons, to form a single new nucleus. */
        Alpha,
        /** Nucleus splits into two smaller nuclei, often emitting several alpha particles. */
        SpontaneousFission,
        /** Nucleus emits a small cluster of protons/neutrons instead of individual protons or neutrons. */
        Cluster,
        /** Nucleus emits an alpha particle, often emitting or absorbing other particles in the process. */
        AlphaTransfer,
        /**
         * Nucleus emits a positron, which typically converts a neutron into a proton, emitting a neutrino in the
         * process.
         */
        BetaMinus,
        /** A proton in the nucleus captures an electron to form a neutron, emitting a neutrino in the process. */
        BetaPlus,
    }

    public static final RecipeMetadataKey<DecayType> DECAY_TYPE = SimpleRecipeMetadataKey
        .create(DecayType.class, "decay-type");

    static {
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(COIL_HEAT);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(FUEL_VALUE);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(NANO_FORGE_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(FOG_EXOTIC_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(FOG_PLASMA_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(DEFC_CASING_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(CHEMPLANT_CASING_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(ALGAE_POND_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(QFT_FOCUS_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(DISSOLUTION_TANK_RATIO);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(RTG_DURATION_IN_DAYS);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(LNG_BASIC_OUTPUT);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(NFR_COIL_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(NKE_RANGE);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(PRECISE_ASSEMBLER_CASING_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(COAL_CASING_TIER);
        GTRecipeMapUtil.SPECIAL_VALUE_ALIASES.add(RESEARCH_STATION_DATA);
    }
}
