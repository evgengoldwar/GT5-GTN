package gregtech.loaders.oreprocessing;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GTOreDictUnificator;

public class ProcessingCrushedOre implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingCrushedOre() {
        OrePrefixes.crushedCentrifuged.add(this);
        OrePrefixes.crushedPurified.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        ItemStack aStack) {
        if (aMaterial.contains(SubTag.NO_ORE_PROCESSING)) {
            return;
        }

        switch (aPrefix.getName()) {
            case "crushedPurified" -> {

                ItemStack tGem = GTOreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
                if (tGem == null) {
                    break;
                }

                // Blacklist materials which are handled by Werkstoff loader and coal, which has an override
                if (aMaterial == Materials.Salt || aMaterial == Materials.RockSalt
                    || aMaterial == Materials.Spodumene
                    || aMaterial == Materials.Coal) return;

                switch (aMaterial.mName) {
                }

            }
            default -> {}
        }
    }
}
