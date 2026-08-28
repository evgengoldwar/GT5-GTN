package gregtech.loaders.postload.recipes;

import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.Avaritia;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.CondensateType;
import gregtech.api.enums.Materials;
import gregtech.api.enums.NaniteTier;

/// Recipes made in the BEC Condensate Assembler.
///
/// As a design constraint, recipes should fit into as narrow condensate families as possible. Presently, this is 5
/// 7-member families, given below.
///
/// | Family | Condensates |
/// |----------|------------------------------------------------------------------------------------------|
/// | Family 1 | SpaceTime, Space, Time, Neutronium, Cosmic Neutronium, Cosmic Solder, Bedrockium |
/// | Family 2 | Chromatic Glass, Celestial Tungsten, Infinity, Transcendent Metal, DSS, Cosmic Solder, Eternity |
/// | Family 3 | Phononic Medium, Universium, MHDCSM, QGP, MagMatter, Cosmic Solder, Eternity |
/// | Family 4 | Phononic Medium, Celestial Tungsten, Infinity, Neutronium, Cosmic Neutronium, Hypogen, Bedrockium |
/// | Family 5 | SpaceTime, Space, MHDCSM, QGP, DSS, Cosmic Solder, Eternity |
public class BECRecipes implements Runnable {

    private static final NaniteTier[] TIER_TO_NANITE = { NaniteTier.Carbon, NaniteTier.Silver, NaniteTier.Gold,
        NaniteTier.Transcendent, NaniteTier.SixPhasedCopper, NaniteTier.WhiteDwarf, NaniteTier.BlackDwarf,
        NaniteTier.Universium, NaniteTier.Eternity, NaniteTier.MagMatter };

    // The old EoH recipes were gated by the material of their bolt, following the tier of BOLT_TIER
    // This same tiering pattern is used through the 4-tier BEC Metamaterials.
    // "Bolt" is used for brevity.
    private static final int[] BOLT_TIER = { 1, 2, 2, 2, 3, 3, 3, 4, 4 };
    private static final int[] BOLT_POS = { 0, 0, 1, 2, 0, 1, 2, 0, 1 };
    // Materials used to differentiate each tier of EoH part
    private static final Materials[] TIER_MATS = { Materials.Netherite, Materials.ProtoHalkonite,
        Materials.SixPhasedCopper, Materials.TranscendentMetal, Materials.Mellion, Materials.Creon, Materials.SpaceTime,
        Materials.Hexanite, Materials.Eternity };
    private static final int[] EOH_TIMES = { 2_000_000, 3_000_000, 4_000_000, 5_000_000, 100_000, 120_000, 140_000,
        160_000, 180_000 };

    @Override
    public void run() {
        addBECCasingRecipes();
        if (NewHorizonsCoreMod.isModLoaded()) addGodforgeRecipes();
        if (Avaritia.isModLoaded() && AE2FluidCraft.isModLoaded()) {
            addEyeOfHarmonyRecipes();
        }

        // Transdimensional Alignment Matrix (Convergence)

        // UMV Component Assembly Line Casing (UMV CoAL)

        // UXV Component Assembly Line Casing (UXV CoAL)

    }

    private void addGodforgeRecipes() {
        // Magnetic Confinement Casing

        // Graviton Modulator 2

        // Graviton Modulator 3

        // Phonon Transmission Conduit
    }

    public void runLateRecipes() {
        // Shielding Casing

    }

    private void addBECCasingRecipes() {
        // Electromagnetically-isolated Casing

        // Coherence-preserving Plasma Conduit

        // Fine-structure Constant Manipulator

        // Condensate Guidance Coil

        // Condensate Transformative Coil

        // Electromagnetic Waveguide

    }

    private void addEyeOfHarmonyRecipes() {
        addEyeOfHarmonyCasings();

        // Astral Array Fabricator

    }

    private void addEyeOfHarmonyCasings() {

        final int casingTime = 40_000 * SECONDS;
        // Reinforced Temporal Structure Casing

        // Reinforced Spatial Structure Casing

        // Infinite Spacetime Boundary Casing
    }

    private void addBec(ItemStack output, ItemStack[] inputs, NaniteTier[] nanites, FluidStack[] condensates,
        int duration, long eut) {}

    /// Cosmic solder condensate: transcendent metal at tier 0, boundless cosmic solder thereafter.
    private static FluidStack cosmicSolder(int tier) {
        return tier == 0 ? CondensateType.TranscendentMetal.getEntangled(69 * INGOTS)
            : CondensateType.BoundlessCosmicSolder.getEntangled(10_000 * tier);
    }

    private static NaniteTier[] nanites(int... tiers) {
        return nanitesShifted(0, tiers);
    }

    private static NaniteTier[] nanitesShifted(int shift, int... tiers) {
        NaniteTier[] result = new NaniteTier[tiers.length];
        for (int i = 0; i < tiers.length; i++) {
            result[i] = TIER_TO_NANITE[tiers[i] - 1 + shift];
        }
        return result;
    }
}
