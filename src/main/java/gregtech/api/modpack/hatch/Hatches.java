package gregtech.api.modpack.hatch;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.metatileentity.MTE;
import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.util.Lang;
import gregtech.api.modpack.util.Prototypes;

/**
 * Built in hatches: every {@link HatchType} for tiers ULV..UV (0..8). They use the reserved metas
 * {@code 32000 + type.ordinal() * 20 + tier} of the machine block, so keep your own machines below 32000.
 *
 * <pre>{@code
 * ItemStack lvInputBus = Hatches.get(HatchType.INPUT_BUS, 1, 1);
 *
 * // a custom 4A energy hatch
 * Hatches.register(5000, new MTEHatchEnergy(HatchType.ENERGY, 3, "hatch.energy4a.hv", "HV 4A Energy Hatch", 4));
 * }</pre>
 */
public final class Hatches {

    public static final int RESERVED_START = 32000;
    public static final int MAX_DEFAULT_TIER = 8;

    private static final Map<HatchType, MTEHatch[]> DEFAULTS = new EnumMap<>(HatchType.class);
    private static boolean registered;

    private Hatches() {}

    /** Called by {@link ModpackAPI} before modules register their machines. */
    public static void registerDefaults() {
        if (registered) {
            return;
        }
        registered = true;
        for (HatchType type : HatchType.values()) {
            MTEHatch[] tiers = new MTEHatch[MAX_DEFAULT_TIER + 1];
            for (int tier = 0; tier <= MAX_DEFAULT_TIER; tier++) {
                String id = "hatch." + type.textureName + "." + GTValues.VN[tier].toLowerCase();
                String localName = GTValues.VN[tier] + " " + type.localName;
                MTEHatch hatch = switch (type) {
                    case INPUT_BUS, OUTPUT_BUS -> new MTEHatchItems(type, tier, id, localName);
                    case INPUT_HATCH, OUTPUT_HATCH -> new MTEHatchFluid(type, tier, id, localName);
                    case ENERGY, DYNAMO -> new MTEHatchEnergy(type, tier, id, localName, 2);
                };
                tiers[tier] = register(RESERVED_START + type.ordinal() * 20 + tier, hatch);
            }
            DEFAULTS.put(type, tiers);
        }
    }

    /**
     * Registers any hatch (or other MTE built with the {@code (name, slots)} constructor) under a meta.
     */
    public static <T extends MTE> T register(int meta, T hatch) {
        ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "Hatch " + hatch.getMetaName());
        Prototypes.register(hatch, meta);
        if (hatch instanceof MTEHatch h) {
            Lang.add(hatch.getLocalNameKey(), h.localName);
        }
        return hatch;
    }

    public static MTEHatch getPrototype(HatchType type, int tier) {
        MTEHatch[] tiers = DEFAULTS.get(type);
        if (tiers == null || tier < 0 || tier >= tiers.length) {
            throw new IllegalArgumentException("No default " + type + " for tier " + tier);
        }
        return tiers[tier];
    }

    public static ItemStack get(HatchType type, int tier, int amount) {
        return getPrototype(type, tier).getStackForm(amount);
    }
}
