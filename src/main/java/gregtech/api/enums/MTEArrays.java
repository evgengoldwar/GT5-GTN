package gregtech.api.enums;

import gregtech.api.interfaces.metatileentity.IMTE;

public enum MTEArrays {

    MULTIBLOCK("multiblock_mte_array", createMTEArray()),
    HATCH("hatch_mte_array", createMTEArray()),
    CABLE("cable_mte_array", createMTEArray());

    private final String name;
    private final IMTE[] mteArray;

    MTEArrays(String name, IMTE[] mteArray) {
        this.name = name;
        this.mteArray = mteArray;
    }

    public String getName() {
        return name;
    }

    public IMTE[] getMteArray() {
        return mteArray;
    }

    private static IMTE[] createMTEArray() {
        return new IMTE[Short.MAX_VALUE - 1];
    }

    public static MTEArrays getByName(String name) {
        for (MTEArrays array : values()) {
            if (array.getName()
                .equals(name)) {
                return array;
            }
        }
        return null;
    }
}
