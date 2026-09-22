package gregtech.api.interfaces.fluid;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IGTRegisteredFluid {

    IGTRegisteredFluid registerContainers(final ItemStack fullContainer, final ItemStack emptyContainer,
        final int containerSize);

    IGTRegisteredFluid addLocalizedName();

    Fluid asFluid();
}
