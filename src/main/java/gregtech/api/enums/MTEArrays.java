package gregtech.api.enums;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.common.blocks.BlockCable;
import gregtech.common.blocks.BlockHatch;
import gregtech.common.blocks.BlockMachines;

public enum MTEArrays {

    MULTIBLOCK("multiblock_mte_array", createMTEArray(), BlockMachines.class),
    HATCH("hatch_mte_array", createMTEArray(), BlockHatch.class),
    CABLE("cable_mte_array", createMTEArray(), BlockCable.class);

    private final String name;
    private final IMTE[] mteArray;
    private final Class<? extends BlockMachines> blockClass;

    MTEArrays(String name, IMTE[] mteArray, Class<? extends BlockMachines> blockClass) {
        this.name = name;
        this.mteArray = mteArray;
        this.blockClass = blockClass;
    }

    public String getName() {
        return name;
    }

    public IMTE[] getMteArray() {
        return mteArray;
    }

    public Class<? extends BlockMachines> getBlockClass() {
        return blockClass;
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
