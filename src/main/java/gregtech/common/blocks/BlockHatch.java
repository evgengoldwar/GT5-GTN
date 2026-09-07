package gregtech.common.blocks;

import net.minecraft.item.ItemBlock;

import gregtech.api.enums.MTEArrays;

public class BlockHatch extends BlockMachines {

    public BlockHatch(Class<? extends ItemBlock> itemClass, MTEArrays mteArrays) {
        super(itemClass, "gregtech." + mteArrays.getName(), mteArrays);
    }
}
