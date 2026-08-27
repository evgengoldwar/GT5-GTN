package gregtech.api.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;

import gregtech.api.interfaces.IOreMaterial;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.OreMixBuilder;
import gregtech.common.WorldgenGTOreLayer;

public enum OreMixes {

    // spotless : off
    Naquadah(new OreMixBuilder().name("ore.mix.naquadah")
        .heightRange(10, 90)
        .weight(30)
        .density(4)
        .size(32)
        .primary(Materials.Naquadah)
        .secondary(Materials.Naquadah)
        .inBetween(Materials.Naquadah)
        .sporadic(Materials.NaquadahEnriched)),

    LigniteCoal(new OreMixBuilder().name("ore.mix.lignite")
        .heightRange(80, 210)
        .weight(160)
        .density(7)
        .size(32)
        .primary(Materials.Lignite)
        .secondary(Materials.Lignite)
        .inBetween(Materials.Lignite)
        .sporadic(Materials.Coal)),

    Coal(new OreMixBuilder().name("ore.mix.coal")
        .heightRange(30, 80)
        .weight(80)
        .density(5)
        .size(32)
        .primary(Materials.Coal)
        .secondary(Materials.Coal)
        .inBetween(Materials.Coal)
        .sporadic(Materials.Lignite)),

    Magnetite(new OreMixBuilder().name("ore.mix.magnetite")
        .heightRange(60, 180)
        .weight(160)
        .density(2)
        .size(32)
        .primary(Materials.Magnetite)
        .secondary(Materials.Magnetite)
        .inBetween(Materials.Iron)
        .sporadic(Materials.VanadiumMagnetite)),

    Gold(new OreMixBuilder().name("ore.mix.gold")
        .heightRange(30, 60)
        .weight(160)
        .density(2)
        .size(32)
        .primary(Materials.Magnetite)
        .secondary(Materials.Magnetite)
        .inBetween(Materials.VanadiumMagnetite)
        .sporadic(Materials.Gold)
        .setLocalizedName(Materials.Gold)),

    Iron(new OreMixBuilder().name("ore.mix.iron")
        .heightRange(10, 40)
        .weight(120)
        .density(3)
        .size(24)
        .primary(Materials.BrownLimonite)
        .secondary(Materials.YellowLimonite)
        .inBetween(Materials.BandedIron)
        .sporadic(Materials.Malachite)
        .setLocalizedName(Materials.Iron)),

    Cassiterite(new OreMixBuilder().name("ore.mix.cassiterite")
        .heightRange(60, 220)
        .weight(50)
        .density(4)
        .size(24)
        .primary(Materials.Tin)
        .secondary(Materials.Tin)
        .inBetween(Materials.Cassiterite)
        .sporadic(Materials.Tin)
        .setLocalizedName(Materials.Cassiterite)),

    Tetrahedrite(new OreMixBuilder().name("ore.mix.tetrahedrite")
        .heightRange(80, 120)
        .weight(70)
        .density(3)
        .size(24)
        .primary(Materials.Tetrahedrite)
        .secondary(Materials.Tetrahedrite)
        .inBetween(Materials.Copper)
        .sporadic(Materials.Stibnite)),

    NetherQuartz(new OreMixBuilder().name("ore.mix.netherquartz")
        .heightRange(40, 80)
        .weight(80)
        .density(4)
        .size(24)
        .primary(Materials.NetherQuartz)
        .secondary(Materials.NetherQuartz)
        .inBetween(Materials.NetherQuartz)
        .sporadic(Materials.Quartzite)),

    Sulfur(new OreMixBuilder().name("ore.mix.sulfur")
        .heightRange(5, 20)
        .weight(100)
        .density(4)
        .size(24)
        .primary(Materials.Sulfur)
        .secondary(Materials.Sulfur)
        .inBetween(Materials.Pyrite)
        .sporadic(Materials.Sphalerite)),

    Copper(new OreMixBuilder().name("ore.mix.copper")
        .heightRange(5, 60)
        .weight(80)
        .density(3)
        .size(24)
        .primary(Materials.Chalcopyrite)
        .secondary(Materials.Iron)
        .inBetween(Materials.Pyrite)
        .sporadic(Materials.Copper)
        .setLocalizedName(Materials.Copper)),

    Bauxite(new OreMixBuilder().name("ore.mix.bauxite")
        .heightRange(10, 80)
        .weight(80)
        .density(3)
        .size(24)
        .primary(Materials.Bauxite)
        .secondary(Materials.Ilmenite)
        .inBetween(Materials.Aluminium)
        .sporadic(Materials.Ilmenite)),

    Salts(new OreMixBuilder().name("ore.mix.salts")
        .heightRange(50, 70)
        .weight(50)
        .density(2)
        .size(24)
        .primary(Materials.RockSalt)
        .secondary(Materials.Salt)
        .inBetween(Materials.Lepidolite)
        .sporadic(Materials.Spodumene)
        .setLocalizedName(Materials.Salt)),

    Redstone(new OreMixBuilder().name("ore.mix.redstone")
        .heightRange(5, 40)
        .weight(60)
        .density(2)
        .size(24)
        .primary(Materials.Redstone)
        .secondary(Materials.Redstone)
        .inBetween(Materials.Ruby)
        .sporadic(Materials.Cinnabar)),

    Soapstone(new OreMixBuilder().name("ore.mix.soapstone")
        .heightRange(20, 50)
        .weight(40)
        .density(2)
        .size(16)
        .primary(Materials.Soapstone)
        .secondary(Materials.Talc)
        .inBetween(Materials.Glauconite)
        .sporadic(Materials.Pentlandite)),

    Nickel(new OreMixBuilder().name("ore.mix.nickel")
        .heightRange(10, 40)
        .weight(40)
        .density(2)
        .size(16)
        .primary(Materials.Garnierite)
        .secondary(Materials.Nickel)
        .inBetween(Materials.Cobaltite)
        .sporadic(Materials.Pentlandite)
        .setLocalizedName(Materials.Nickel)),

    Platinum(new OreMixBuilder().name("ore.mix.platinum")
        .heightRange(40, 50)
        .weight(5)
        .density(2)
        .size(16)
        .primary(Materials.Cooperite)
        .secondary(Materials.Palladium)
        .inBetween(Materials.Platinum)
        .sporadic(Materials.Iridium)
        .setLocalizedName(Materials.Platinum)),

    Pitchblende(new OreMixBuilder().name("ore.mix.pitchblende")
        .heightRange(30, 60)
        .weight(40)
        .density(2)
        .size(16)
        .primary(Materials.Pitchblende)
        .secondary(Materials.Pitchblende)
        .inBetween(Materials.Uraninite)
        .sporadic(Materials.Uraninite)),

    Monazite(new OreMixBuilder().name("ore.mix.monazite")
        .heightRange(20, 40)
        .weight(30)
        .density(2)
        .size(16)
        .primary(Materials.Bastnasite)
        .secondary(Materials.Bastnasite)
        .inBetween(Materials.Monazite)
        .sporadic(Materials.Neodymium)
        .setLocalizedName(Materials.Monazite)),

    Molybdenum(new OreMixBuilder().name("ore.mix.molybdenum")
        .heightRange(20, 50)
        .weight(5)
        .density(2)
        .size(16)
        .primary(Materials.Wulfenite)
        .secondary(Materials.Molybdenite)
        .inBetween(Materials.Molybdenum)
        .sporadic(Materials.Powellite)
        .setLocalizedName(Materials.Molybdenum)),

    Tungstate(new OreMixBuilder().name("ore.mix.tungstate")
        .heightRange(20, 60)
        .weight(10)
        .density(2)
        .size(16)
        .primary(Materials.Scheelite)
        .secondary(Materials.Scheelite)
        .inBetween(Materials.Tungstate)
        .sporadic(Materials.Lithium)
        .setLocalizedName(Materials.Tungstate)),

    Sapphire(new OreMixBuilder().name("ore.mix.sapphire")
        .heightRange(10, 25)
        .weight(60)
        .density(2)
        .size(16)
        .primary(Materials.Almandine)
        .secondary(Materials.Pyrope)
        .inBetween(Materials.Sapphire)
        .sporadic(Materials.GreenSapphire)
        .setLocalizedName(Materials.Sapphire)),

    Manganese(new OreMixBuilder().name("ore.mix.manganese")
        .heightRange(20, 30)
        .weight(20)
        .density(2)
        .size(16)
        .primary(Materials.Grossular)
        .secondary(Materials.Spessartine)
        .inBetween(Materials.Pyrolusite)
        .sporadic(Materials.Tantalite)
        .setLocalizedName(Materials.Manganese)),

    Quartz(new OreMixBuilder().name("ore.mix.quartz")
        .heightRange(80, 120)
        .weight(20)
        .density(2)
        .size(16)
        .primary(Materials.Quartzite)
        .secondary(Materials.Barite)
        .inBetween(Materials.CertusQuartz)
        .sporadic(Materials.CertusQuartz)),

    Diamond(new OreMixBuilder().name("ore.mix.diamond")
        .heightRange(5, 20)
        .weight(40)
        .density(1)
        .size(16)
        .primary(Materials.Graphite)
        .secondary(Materials.Graphite)
        .inBetween(Materials.Diamond)
        .sporadic(Materials.Coal)
        .setLocalizedName(Materials.Diamond)),

    Olivine(new OreMixBuilder().name("ore.mix.olivine")
        .heightRange(10, 25)
        .weight(60)
        .density(2)
        .size(16)
        .primary(Materials.Bentonite)
        .secondary(Materials.Magnesite)
        .inBetween(Materials.Olivine)
        .sporadic(Materials.Glauconite)
        .setLocalizedName(Materials.Olivine)),

    Apatite(new OreMixBuilder().name("ore.mix.apatite")
        .heightRange(40, 60)
        .weight(60)
        .density(2)
        .size(16)
        .primary(Materials.Apatite)
        .secondary(Materials.Apatite)
        .inBetween(Materials.TricalciumPhosphate)
        .sporadic(Materials.Pyrochlore)),

    Galena(new OreMixBuilder().name("ore.mix.galena")
        .heightRange(5, 45)
        .weight(40)
        .density(4)
        .size(16)
        .primary(Materials.Galena)
        .secondary(Materials.Galena)
        .inBetween(Materials.Silver)
        .sporadic(Materials.Lead)),

    Lapis(new OreMixBuilder().name("ore.mix.lapis")
        .heightRange(20, 50)
        .weight(40)
        .density(4)
        .size(16)
        .primary(Materials.Lazurite)
        .secondary(Materials.Sodalite)
        .inBetween(Materials.Lapis)
        .sporadic(Materials.Calcite)
        .setLocalizedName(Materials.Lapis)),

    Beryllium(new OreMixBuilder().name("ore.mix.beryllium")
        .heightRange(5, 30)
        .weight(30)
        .density(2)
        .size(16)
        .primary(Materials.Beryllium)
        .secondary(Materials.Beryllium)
        .inBetween(Materials.Emerald)
        .sporadic(Materials.Thorium)),

    Uranium(new OreMixBuilder().name("ore.mix.uranium")
        .heightRange(20, 30)
        .weight(20)
        .density(2)
        .size(16)
        .primary(Materials.Uraninite)
        .secondary(Materials.Uraninite)
        .inBetween(Materials.Uranium)
        .sporadic(Materials.Uranium)
        .setLocalizedName(Materials.Uranium)),
    OilSand(new OreMixBuilder().name("ore.mix.oilsand")
        .heightRange(50, 80)
        .weight(40)
        .density(5)
        .size(16)
        .primary(Materials.Oilsands)
        .secondary(Materials.Oilsands)
        .inBetween(Materials.Oilsands)
        .sporadic(Materials.Oilsands)),

    Neutronium(new OreMixBuilder().name("ore.mix.neutronium")
        .heightRange(5, 30)
        .weight(10)
        .density(2)
        .size(16)
        .primary(Materials.Neutronium)
        .secondary(Materials.Adamantium)
        .inBetween(Materials.Naquadah)
        .sporadic(Materials.Titanium)),

    AquaIgnis(new OreMixBuilder().name("ore.mix.aquaignis")
        .heightRange(5, 20)
        .weight(16)
        .density(2)
        .size(16)
        .primary(Materials.InfusedWater)
        .secondary(Materials.InfusedFire)
        .inBetween(Materials.Amber)
        .sporadic(Materials.Cinnabar)
        .setLocalizedName(Materials.InfusedFire, Materials.Amber)),

    TerraAer(new OreMixBuilder().name("ore.mix.terraaer")
        .heightRange(5, 20)
        .weight(16)
        .density(2)
        .size(16)
        .primary(Materials.InfusedEarth)
        .secondary(Materials.InfusedAir)
        .inBetween(Materials.Amber)
        .sporadic(Materials.Cinnabar)
        .setLocalizedName(Materials.InfusedAir)),

    PerditioOrdo(new OreMixBuilder().name("ore.mix.perditioordo")
        .heightRange(5, 20)
        .weight(16)
        .density(2)
        .size(16)
        .primary(Materials.InfusedEntropy)
        .secondary(Materials.InfusedOrder)
        .inBetween(Materials.Amber)
        .sporadic(Materials.Cinnabar)
        .setLocalizedName(Materials.InfusedOrder));
    // spotless : on

    public static final OreMixes[] VALUES = values();

    public final OreMixBuilder oreMixBuilder;

    OreMixes(OreMixBuilder oreMixBuilder) {
        this.oreMixBuilder = oreMixBuilder;
    }

    public WorldgenGTOreLayer addGTOreLayer() {
        return new WorldgenGTOreLayer(this.oreMixBuilder);
    }

    // used in the coremod, for the shuttle schematic recipe
    /**
     * Give all the stone variants of an ore, with the specified stack size.
     *
     * @param material  The material of the ore
     * @param stackSize The stacksize of the variants
     * @return The array containing all the stone variants of the given ore, with the given stack size
     */
    public static ItemStack[] getOreVariants(IOreMaterial material, int stackSize) {
        List<ItemStack> variants = new ArrayList<>();
        Set<StoneType> stoneTypes = getStoneTypesFromMixes(material);
        for (StoneType stoneType : stoneTypes) {
            OrePrefixes prefix = stoneType.getPrefix();
            ItemStack ore = GTOreDictUnificator.get(prefix, material, 1L);
            if (!GTUtility.isStackValid(ore)) {
                continue;
            }
            for (ItemStack variant : GTOreDictUnificator.getNonUnifiedStacks(ore)) {
                ItemStack sizedVariant = GTUtility.copyAmount(stackSize, variant);
                if (!GTUtility.isStackValid(sizedVariant) || containsStack(variants, sizedVariant)) {
                    continue;
                }
                variants.add(sizedVariant);
            }
        }

        return variants.toArray(new ItemStack[0]);
    }

    public static boolean containsStack(List<ItemStack> stacks, ItemStack candidate) {
        for (ItemStack stack : stacks) {
            if (GTUtility.areStacksEqual(stack, candidate, true)) {
                return true;
            }
        }
        return false;
    }

    public static List<OreMixes> getOreMixes(IOreMaterial material) {
        List<OreMixes> mixes = new ArrayList<>();
        for (OreMixes mix : OreMixes.VALUES) {
            if (mix.containMaterial(material)) {
                mixes.add(mix);
            }
        }

        return mixes;
    }

    public static Set<StoneType> getStoneTypesFromMixes(IOreMaterial material) {
        List<OreMixes> mixes = getOreMixes(material);
        Set<StoneType> stoneTypes = new HashSet<>();
        for (OreMixes mix : mixes) {
            for (String dim : mix.oreMixBuilder.dimsEnabled) {
                for (StoneType stoneType : getStoneTypesForDim(dim)) {
                    if (mix.oreMixBuilder.stoneCategories.contains(stoneType.getCategory())) {
                        stoneTypes.add(stoneType);
                    }
                }
            }
        }

        return stoneTypes;
    }

    private static Set<StoneType> getStoneTypesForDim(String dim) {
        return null;
    }

    private static void addGTStoneTypes(Set<StoneType> stoneTypes, String dim) {
        // To account for all the GTStones entries, which apply only either in OW or Nether
        Integer dimensionId = switch (dim) {
            default -> null;
        };

        if (dimensionId == null) return;

        for (GTStones gtStone : GTStones.VALUES) {
            if (gtStone.stone.enabledByDefault && gtStone.stone.dimension == dimensionId) {
                StoneType stoneType = StoneType.findStoneType(gtStone.stone.block, gtStone.stone.blockMeta);
                if (stoneType != null) stoneTypes.add(stoneType);
            }
        }
    }

    private static void addAsteroidStoneTypes(Set<StoneType> stoneTypes, String dim) {}

    public boolean containMaterial(IOreMaterial material) {
        return oreMixBuilder.primary == material || oreMixBuilder.secondary == material
            || oreMixBuilder.sporadic == material
            || oreMixBuilder.between == material;
    }
}
