package gregtech.api.modpack.util;

import gregtech.api.enums.MTEArrays;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.metatileentity.BaseGTE;
import gregtech.api.metatileentity.MTE;

/**
 * Registration of MetaTileEntity prototypes created with the {@code MTE(String name, int slots)} constructor, so one
 * constructor serves both the prototype and the placed instances.
 */
public final class Prototypes {

    private Prototypes() {}

    /**
     * Puts {@code mte} into the {@link MTEArrays#HATCH} array under {@code meta} (the GregTech machine block).
     */
    public static <T extends MTE> T register(T mte, int meta) {
        IMTE[] array = MTEArrays.HATCH.getMteArray();
        if (meta <= 0 || meta >= array.length) {
            throw new IllegalArgumentException("Machine meta out of range 1.." + (array.length - 1) + ": " + meta);
        }
        if (array[meta] != null) {
            throw new IllegalArgumentException(
                "MetaTileEntity meta " + meta + " is already occupied by " + array[meta].getMetaName());
        }
        array[meta] = mte;
        mte.setGTE(new BaseGTE());
        mte.getGTE()
            .setMeta((short) meta);
        return mte;
    }
}
