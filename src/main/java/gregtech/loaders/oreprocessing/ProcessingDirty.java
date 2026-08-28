package gregtech.loaders.oreprocessing;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;

public class ProcessingDirty implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingDirty() {
        OrePrefixes.clump.add(this);
        OrePrefixes.shard.add(this);
        OrePrefixes.crushed.add(this);
        OrePrefixes.dirtyGravel.add(this);
    }

    private boolean didPersulfate = false;
    private boolean didMercury = false;

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        net.minecraft.item.ItemStack aStack) {
        if (aMaterial.contains(SubTag.NO_ORE_PROCESSING)) {
            return;
        }

        OrePrefixes prefix = aPrefix == OrePrefixes.crushed ? OrePrefixes.crushedCentrifuged : OrePrefixes.dust;

        didPersulfate = false;
        didMercury = false;

        addChemicalBathRecipes(aMaterial, aMaterial, aStack, aPrefix);

        for (Materials tMaterial : aMaterial.mOreByProducts) {
            addChemicalBathRecipes(aMaterial, tMaterial, aStack, aPrefix);
        }

    }

    private void addChemicalBathRecipes(Materials material, Materials byproduct, ItemStack stack, OrePrefixes prefix) {
        OrePrefixes chemicalBathPrefix = prefix == OrePrefixes.crushed ? OrePrefixes.crushedPurified
            : OrePrefixes.dustPure;

        if (byproduct.contains(SubTag.WASHING_MERCURY) && !didMercury) {

            didMercury = true;
        }
        if (byproduct.contains(SubTag.WASHING_MERCURY_99_PERCENT) && !didMercury) {

            didMercury = true;
        }
        if (byproduct.contains(SubTag.WASHING_SODIUMPERSULFATE) && !didPersulfate) {

            didPersulfate = true;
        }
    }
}
