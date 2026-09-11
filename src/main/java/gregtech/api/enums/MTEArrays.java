package gregtech.api.enums;

import net.minecraft.tileentity.TileEntity;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.metatileentity.BaseGTE;
import gregtech.api.metatileentity.BasePipeGTE;
import gregtech.common.blocks.BlockCable;
import gregtech.common.blocks.BlockHatch;
import gregtech.common.blocks.BlockMachines;

public enum MTEArrays {

    MULTIBLOCK("multiblock_mte_array", createMTEArray(), BlockMachines.class, BaseGTE.class),
    HATCH("hatch_mte_array", createMTEArray(), BlockHatch.class, BaseGTE.class),
    CABLE("cable_mte_array", createMTEArray(), BlockCable.class, BasePipeGTE.class);

    private final String name;
    private final IMTE[] mteArray;
    private final Class<? extends BlockMachines> blockClass;
    private final Class<? extends TileEntity> teClass;

    MTEArrays(String name, IMTE[] mteArray, Class<? extends BlockMachines> blockClass,
        Class<? extends TileEntity> teClass) {
        this.name = name;
        this.mteArray = mteArray;
        this.blockClass = blockClass;
        this.teClass = teClass;
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

    public TileEntity getTileEntityInstance() {
        try {
            return teClass.getDeclaredConstructor()
                .newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate " + teClass.getName());
        }
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
