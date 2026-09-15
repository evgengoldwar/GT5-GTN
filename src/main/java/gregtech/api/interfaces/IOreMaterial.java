package gregtech.api.interfaces;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

import org.jetbrains.annotations.Nullable;

import gregtech.api.enums.Materials_OLD;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TextureSet;

/**
 * A temporary material interface to unify the three material systems.
 * Once the new material system is finished, this should be removed and all the code referencing this should be migrated
 * to the new system.
 */
public interface IOreMaterial extends ISubTagContainer {

    /**
     * Add tooltips(mainly chemical formula) for material items。
     *
     * @param list the list parameter in the {@link Item#addInformation} method (for tooltips).
     */
    void addTooltips(List<String> list);

    int getId();

    String getInternalName();

    String getDefaultLocalName();

    default String getLocalizedNameKey() {
        return "Material." + getInternalName().toLowerCase();
    }

    default String getLocalizedName() {
        return StatCollector.translateToLocal(getLocalizedNameKey());
    }

    short[] getRGBA();

    TextureSet getTextureSet();

    @Nullable
    Materials_OLD getGTMaterial();

    boolean generatesPrefix(OrePrefixes prefix);

}
