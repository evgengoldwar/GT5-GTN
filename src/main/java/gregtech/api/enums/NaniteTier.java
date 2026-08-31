package gregtech.api.enums;

import java.text.MessageFormat;

import net.minecraft.item.ItemStack;

import gregtech.api.objects.ItemData;
import gregtech.api.util.GTOreDictUnificator;

public enum NaniteTier {

    Carbon(1),
    Glowstone(1),
    Silver(2),
    Neutronium(2),
    Gold(3),
    // intentionally above gold because transmetal nanites are harder to make
    Transcendent(4),
    SixPhasedCopper(5),
    // eoh t1-t3
    WhiteDwarf(6),
    // eoh t4-t6
    BlackDwarf(7),
    // eoh t7-t9
    Universium(8),
    Eternity(9),
    MagMatter(10);

    public final int tier;

    private ItemStack stack = null;

    NaniteTier(int tier) {
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    public Materials_OLD getMaterial() {
        return switch (this) {
            case Carbon -> Materials_OLD.Carbon;
            case Glowstone -> Materials_OLD.Glowstone;
            case Silver -> Materials_OLD.Silver;
            case Neutronium -> Materials_OLD.Neutronium;
            case Gold -> Materials_OLD.Gold;
            case Transcendent -> Materials_OLD.TranscendentMetal;
            case WhiteDwarf -> Materials_OLD.WhiteDwarfMatter;
            case BlackDwarf -> Materials_OLD.BlackDwarfMatter;
            case SixPhasedCopper -> Materials_OLD.SixPhasedCopper;
            case Universium -> Materials_OLD.Universium;
            case MagMatter -> Materials_OLD.MagMatter;
            case Eternity -> Materials_OLD.Eternity;
        };
    }

    public ItemStack getStack() {
        if (this.stack == null) {
            stack = GTOreDictUnificator.get(OrePrefixes.nanite, getMaterial(), 1);
        }

        return stack.copy();
    }

    public String describe() {
        return MessageFormat.format("{0} (Tier {1})", getMaterial().getLocalizedName(), tier);
    }

    public static NaniteTier fromMaterial(Materials_OLD mat) {
        if (mat == Materials_OLD.Carbon) return Carbon;
        if (mat == Materials_OLD.Glowstone) return Glowstone;
        if (mat == Materials_OLD.Silver) return Silver;
        if (mat == Materials_OLD.Neutronium) return Neutronium;
        if (mat == Materials_OLD.Gold) return Gold;
        if (mat == Materials_OLD.TranscendentMetal) return Transcendent;
        if (mat == Materials_OLD.WhiteDwarfMatter) return WhiteDwarf;
        if (mat == Materials_OLD.BlackDwarfMatter) return BlackDwarf;
        if (mat == Materials_OLD.SixPhasedCopper) return SixPhasedCopper;
        if (mat == Materials_OLD.Universium) return Universium;
        if (mat == Materials_OLD.MagMatter) return MagMatter;
        if (mat == Materials_OLD.Eternity) return Eternity;

        return null;
    }

    public static NaniteTier fromStack(ItemStack stack) {
        if (stack == null) return null;

        ItemData data = GTOreDictUnificator.getAssociation(stack);

        if (data == null) return null;
        if (data.mPrefix != OrePrefixes.nanite) return null;

        return fromMaterial(data.mMaterial.mMaterial);
    }
}
