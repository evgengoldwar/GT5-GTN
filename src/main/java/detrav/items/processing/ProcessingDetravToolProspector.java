package detrav.items.processing;

import net.minecraft.item.ItemStack;

import detrav.DetravScannerMod;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;

/**
 * Created by wital_000 on 18.03.2016.
 */
public class ProcessingDetravToolProspector implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingDetravToolProspector() {
        OrePrefixes.toolHeadDrill.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials material, String aOreDictName, String aModName,
        ItemStack aStack) {
        if (!aPrefix.doGenerateItem(material)) return;
        if (DetravScannerMod.DEBUG_ENABLED) return;

    }
}
