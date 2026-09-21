package gregtech.common.fluid;

import java.util.Arrays;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.IOreMaterial;
import gregtech.api.interfaces.fluid.IGTFluid;
import gregtech.api.interfaces.fluid.IGTRegisteredFluid;

public class GTFluid extends Fluid implements IGTFluid, IGTRegisteredFluid, Runnable {

    private final String defaultLocalName;
    private final ResourceLocation stillIconResourceLocation;
    private final ResourceLocation flowingIconResourceLocation;
    private final short[] colorRGBA;
    private final Fluid iconsFrom;
    private Fluid registeredFluid;
    private Supplier<String> localizedName;

    protected GTFluid(@Nonnull final GTFluidBuilder builder) {
        super(builder.fluidName);
        this.defaultLocalName = builder.defaultLocalName;
        this.stillIconResourceLocation = builder.stillIconResourceLocation;
        this.flowingIconResourceLocation = builder.flowingIconResourceLocation;
        this.iconsFrom = builder.iconsFrom;
        this.block = builder.fluidBlock;
        this.colorRGBA = builder.colorRGBA;
        this.temperature = builder.temperature;
        setGaseous(false).setViscosity(1000);
    }

    @Override
    public int getColor() {
        return (Math.clamp(colorRGBA[0], 0, 255) << 16) | (Math.clamp(colorRGBA[1], 0, 255) << 8)
            | Math.clamp(colorRGBA[2], 0, 255);
    }

    @Override
    public IGTRegisteredFluid addFluid() {
        if (FluidRegistry.registerFluid(GTFluid.this)) {
            registeredFluid = this;
        } else {
            registeredFluid = FluidRegistry.getFluid(GTFluid.this.fluidName);
            if (registeredFluid.getTemperature() == new Fluid("test").getTemperature()) {
                registeredFluid.setTemperature(GTFluid.this.temperature);
            }
        }
        GregTechAPI.sGTBlockIconload.add(this);
        return this;
    }

    @Override
    public IGTRegisteredFluid registerContainers(final ItemStack fullContainer, final ItemStack emptyContainer,
        final int containerSize) {
        if (fullContainer != null && emptyContainer != null) {
            final FluidStack fluidStack = new FluidStack(registeredFluid, containerSize);
            if (!FluidContainerRegistry.registerFluidContainer(fluidStack, fullContainer, emptyContainer)) {
                // GTValues.RA.stdBuilder()
                // .itemInputs(fullContainer)
                // .itemOutputs(GTUtility.getContainerItem(fullContainer, false))
                // .fluidOutputs(fluidStack)
                // .duration(fluidStack.amount / 62)
                // .eut(1)
                // .addTo(cannerRecipes);
            }
        }
        return this;
    }

    @Override
    public IGTRegisteredFluid registerBContainers(final ItemStack fullContainer, final ItemStack emptyContainer) {
        return registerContainers(fullContainer, emptyContainer, 1000);
    }

    @Override
    public IGTRegisteredFluid registerPContainers(final ItemStack fullContainer, final ItemStack emptyContainer) {
        return registerContainers(fullContainer, emptyContainer, 250);
    }

    @Override
    public IGTRegisteredFluid addLocalizedName() {
        return this;
    }

    private boolean addLocalizedNameHasOreprefix(IOreMaterial material, String[] oreprefixNames) {
        return Arrays.stream(oreprefixNames)
            .anyMatch(oreprefixName -> addLocalizedNameHasOreprefix(material, oreprefixName));
    }

    private boolean addLocalizedNameHasOreprefix(IOreMaterial material, String oreprefixName) {
        final String oreprefixNameRemovedFormat = oreprefixName.replace("%s", "");
        if (defaultLocalName.contains(oreprefixNameRemovedFormat)) {
            if (String.format(oreprefixName, material.getDefaultLocalName())
                .equals(defaultLocalName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IGTRegisteredFluid addLocalizedName(IOreMaterial material) {
        if (material == null) return addLocalizedName();
        if (!material.getDefaultLocalName()
            .equals(defaultLocalName)) {
            if (addLocalizedNameHasOreprefix(
                material,
                new String[] { "Molten %s", "%s Plasma", "Lightly Hydro-Cracked %s", "Lightly Steam-Cracked %s",
                    "Moderately Hydro-Cracked %s", "Moderately Steam-Cracked %s", "Severely Hydro-Cracked %s",
                    "Severely Steam-Cracked %s" })) {
                return this;
            }
            return addLocalizedName();
        }
        localizedName = material::getLocalizedName;
        return this;
    }

    @Override
    public Fluid asFluid() {
        return registeredFluid;
    }

    @Override
    public void run() {
        if (iconsFrom instanceof GTFluid) {
            // Needs the GTFluid to have registered its icons
            ((GTFluid) iconsFrom).run();
            stillIcon = iconsFrom.getStillIcon();
            flowingIcon = iconsFrom.getFlowingIcon();
        } else {
            if (stillIconResourceLocation != null) {
                stillIcon = GregTechAPI.sBlockIcons.registerIcon(stillIconResourceLocation.toString());
            }
            if (flowingIconResourceLocation != null) {
                flowingIcon = GregTechAPI.sBlockIcons.registerIcon(flowingIconResourceLocation.toString());
            }
        }
    }

    @Override
    public String toString() {
        return "GTFluid{" + "fluidName='" + fluidName + '\'' + '}';
    }

    @Override
    public String getLocalizedName() {
        if (localizedName == null) return super.getLocalizedName();
        return localizedName.get();
    }
}
