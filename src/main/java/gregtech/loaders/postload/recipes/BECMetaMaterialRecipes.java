package gregtech.loaders.postload.recipes;

import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import bartworks.system.material.Werkstoff;
import gregtech.api.enums.CondensateType;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.NaniteTier;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;

/// BEC Meta Material recipes
/// </p>
/// There are two groups, four 3-tier items and four 4-tier items.
/// The 3-tier items have 5 ingredients: a base and 4 tiered materials
/// The 4-tier items have 4 ingredients: a base and 3 tiered materials
/// To ensure consistency among the items, they are autogenned. Thus, there are arrays for the tiered materials.
public class BECMetaMaterialRecipes implements Runnable {

    private static final NaniteTier[] TIER_TO_NANITE = { NaniteTier.Carbon, NaniteTier.Silver, NaniteTier.Gold,
        NaniteTier.Transcendent, NaniteTier.SixPhasedCopper, NaniteTier.WhiteDwarf, NaniteTier.BlackDwarf,
        NaniteTier.Universium, NaniteTier.Eternity, NaniteTier.MagMatter };

    private static final int[] baseAmounts = { 1, 2, 4, 8 };
    private static final int baseDuration = 300 * SECONDS;

    private static final Materials[] other3Tier = { Materials.ProtoHalkonite, Materials.Hexanite, Materials.MHDCSM };
    private static final Object[] black3Tier = { Materials.TranscendentMetal, Materials.BlackDwarfMatter };
    private static final Materials[] gray3Tier = { Materials.Churitsu, Materials.SpaceTime, Materials.Eternity };
    private static final Materials[] white3Tier = { Materials.Shijima, Materials.WhiteDwarfMatter,
        Materials.MagMatter };

    private static final Object[] black4Tier = { Materials.SpaceTime };
    private static final Materials[] gray4Tier = { Materials.Churitsu, Materials.WhiteDwarfMatter,
        Materials.BlackDwarfMatter, Materials.MHDCSM };
    private static final Materials[] white4Tier = { Materials.Shijima, Materials.ProtoHalkonite, Materials.Hexanite,
        Materials.Eternity };

    private static final int[] chromatic4TierAmts = { 8, 12, 16, 24 };
    private static final int[] infinity4TierAmts = { 4, 6, 8, 12 };
    private static final int[] solder4TierAmts = { 1, 2, 3, 4 };

    private static NaniteTier nanite(int tier) {
        return TIER_TO_NANITE[tier - 1];
    }

    @Override
    public void run() {

    }

    private void reg4Tier(ItemList[] outputs, ItemStack[] inputs, long[] euts, int t) {
        NaniteTier[] nanites = { nanite(4 + t), nanite(1 + t), nanite(2 + t), nanite(1 + t) };
        final CondensateType[] becCondensates;
        final int[] becAmounts;
        if (t == 0) {
            becCondensates = new CondensateType[] { CondensateType.Infinity, CondensateType.TranscendentMetal };
            final CondensateType[] becCondensates2 = new CondensateType[] { CondensateType.Infinity,
                CondensateType.BoundlessCosmicSolder };
            becAmounts = new int[] { chromatic4TierAmts[t], infinity4TierAmts[t], 2 };
            final int[] becAmounts2 = new int[] { chromatic4TierAmts[t], infinity4TierAmts[t], 1 };
            addBec(outputs[t].get(1), inputs, nanites, becCondensates, becAmounts, baseDuration, euts[t]);
            addBec(outputs[t].get(1), inputs, nanites, becCondensates2, becAmounts2, baseDuration, euts[t]);
        } else {
            becCondensates = new CondensateType[] { CondensateType.Infinity, CondensateType.BoundlessCosmicSolder };
            becAmounts = new int[] { chromatic4TierAmts[t], infinity4TierAmts[t], solder4TierAmts[t] };
            addBec(outputs[t].get(1), inputs, nanites, becCondensates, becAmounts, baseDuration, euts[t]);
        }
    }

    /// Builds and registers both the BEC Assembler variant (using entangled condensates + nanite-tier metadata)
    private void addBec(ItemStack output, ItemStack[] inputs, NaniteTier[] nanites, CondensateType[] condensates,
        int[] condensateIngots, int duration, long eut) {
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] == null) {
                throw new IllegalStateException(
                    "BEC Meta Material recipe for " + output.getDisplayName()
                        + " has null input at index "
                        + i
                        + " — an ore prefix is missing for the material in that slot.");
            }
        }
        FluidStack[] entangled = new FluidStack[condensates.length];
        for (int i = 0; i < condensates.length; i++) {
            CondensateType condensateType = condensates[i];
            entangled[i] = condensateType.getEntangled(condensateIngots[i] * condensateType.getUnit());
        }

    }

    // Material-type adapters: Werkstoff / Materials / gtPlusPlus Material

    private static ItemStack platePart(Object material, int amount) {
        if (material instanceof Werkstoff w) return w.get(OrePrefixes.plate, amount);
        if (material instanceof Materials m) return GTOreDictUnificator.get(OrePrefixes.plate, m, amount);
        throw new IllegalArgumentException("Unsupported material kind: " + material);
    }

    private static ItemStack foilPart(Object material, int amount) {
        if (material instanceof Werkstoff w) return w.get(OrePrefixes.foil, amount);
        if (material instanceof Materials m) return GTOreDictUnificator.get(OrePrefixes.foil, m, amount);
        throw new IllegalArgumentException("Unsupported material kind: " + material);
    }

    private static ItemStack fineWirePart(Object material, int amount) {
        if (material instanceof Werkstoff w) return w.get(OrePrefixes.wireFine, amount);
        if (material instanceof Materials m) return GTOreDictUnificator.get(OrePrefixes.wireFine, m, amount);
        throw new IllegalArgumentException("Unsupported material kind: " + material);
    }

    private static ItemStack ringPart(Object material, int amount) {
        if (material instanceof Werkstoff w) return w.get(OrePrefixes.ring, amount);
        if (material instanceof Materials m) return GTOreDictUnificator.get(OrePrefixes.ring, m, amount);
        throw new IllegalArgumentException("Unsupported material kind: " + material);
    }

    private static ItemStack rodPart(Object material, int amount) {
        if (material instanceof Werkstoff w) return w.get(OrePrefixes.stick, amount);
        if (material instanceof Materials m) return GTOreDictUnificator.get(OrePrefixes.stick, m, amount);
        throw new IllegalArgumentException("Unsupported material kind: " + material);
    }

    private static ItemStack longRodPart(Object material, int amount) {
        if (material instanceof Werkstoff w) return w.get(OrePrefixes.stickLong, amount);
        if (material instanceof Materials m) return GTOreDictUnificator.get(OrePrefixes.stickLong, m, amount);
        throw new IllegalArgumentException("Unsupported material kind: " + material);
    }
}
