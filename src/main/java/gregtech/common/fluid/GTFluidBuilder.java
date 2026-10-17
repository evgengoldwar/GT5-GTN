package gregtech.common.fluid;

import static gregtech.api.enums.Mods.GregTech;

import java.util.Locale;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.fluid.IGTFluid;
import gregtech.api.interfaces.fluid.IGTFluidBuilder;
import gregtech.api.interfaces.fluid.IGTRegisteredFluid;

public class GTFluidBuilder implements IGTFluidBuilder {

    final String fluidName;
    String defaultLocalName;
    ResourceLocation stillIconResourceLocation = null, flowingIconResourceLocation = null;
    short[] colorRGBA = Dyes._NULL.getRGBA();
    Block fluidBlock = null;
    int temperature = 295;
    boolean gaseous = false;
    Fluid iconsFrom;

    public GTFluidBuilder(final String fluidName) {
        this.fluidName = fluidName.toLowerCase(Locale.ENGLISH);
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withColorRGBA(final short[] colorRGBA) {
        this.colorRGBA = colorRGBA;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withDefaultLocalName(final String defaultLocalName) {
        this.defaultLocalName = defaultLocalName;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withStillIconResourceLocation(final ResourceLocation stillIconResourceLocation) {
        this.stillIconResourceLocation = stillIconResourceLocation;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withFlowingIconResourceLocation(final ResourceLocation flowingIconResourceLocation) {
        this.flowingIconResourceLocation = flowingIconResourceLocation;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withTextureName(final String textureName) {
        this.stillIconResourceLocation = new ResourceLocation(GregTech.ID, "fluids/fluid." + textureName);
        this.flowingIconResourceLocation = null;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withIconsFrom(@Nonnull final Fluid fromFluid) {
        this.iconsFrom = fromFluid;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withFluidBlock(final Block fluidBlock) {
        this.fluidBlock = fluidBlock;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluidBuilder withTextures(final ResourceLocation stillIconResourceLocation,
        final ResourceLocation flowingIconResourceLocation) {
        this.stillIconResourceLocation = stillIconResourceLocation;
        this.flowingIconResourceLocation = flowingIconResourceLocation;
        return this;
    }

    @Override
    public IGTFluidBuilder withTemperature(final int temperature) {
        this.temperature = temperature;
        return this;
    }

    @Override
    public IGTFluidBuilder withGaseous(final boolean gaseous) {
        this.gaseous = gaseous;
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTFluid build() {
        if (colorRGBA == null) {
            colorRGBA = Dyes._NULL.getRGBA();
        }
        if (stillIconResourceLocation == null) {
            withTextureName(fluidName.toLowerCase(Locale.ENGLISH));
        }
        if (defaultLocalName == null) {
            defaultLocalName = fluidName;
        }
        return new GTFluid(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGTRegisteredFluid buildAndRegister() {
        return build().addFluid();
    }
}
