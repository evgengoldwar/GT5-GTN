package gregtech.common.blocks;

import net.minecraft.item.ItemBlock;

import gregtech.api.enums.MTEArrays;

public class BlockCable extends BlockMachines {

    public BlockCable(Class<? extends ItemBlock> itemClass, MTEArrays mteArrays) {
        super(itemClass, "gregtech." + mteArrays.getName(), mteArrays);
    }
}
