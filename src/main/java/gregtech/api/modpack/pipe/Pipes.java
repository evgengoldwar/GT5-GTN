package gregtech.api.modpack.pipe;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.metatileentity.PipeMTE;
import gregtech.api.metatileentity.implementations.MTECable;
import gregtech.api.metatileentity.implementations.MTEFluidPipe;
import gregtech.api.metatileentity.implementations.MTEItemPipe;
import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.util.Lang;
import gregtech.common.MaterialSystem.Api.Material;

/**
 * Builders for GregTech style cables (wires), fluid pipes and item pipes. Every size of every material is a separate
 * MetaTileEntity in the {@link MTEArrays#CABLE} array, so each builder needs a start meta: the sizes take consecutive
 * metas starting from it. Metas are stored in the world - never change them after release.
 *
 * <pre>{@code
 * // 1x, 2x, 4x, 8x, 12x, 16x copper cable, 32 V, 1 A per wire, 2 EU loss per block -> metas 1000..1005
 * Pipes.cable(Materials.Copper, 1000).voltage(GTValues.V[1]).amperage(1).loss(2).insulated().register();
 *
 * // all sizes of bronze fluid pipes -> metas 2000..2004
 * Pipes.fluidPipe(Materials.Bronze, 2000).capacity(20).heatResistance(1696).register();
 *
 * // only small and normal tin item pipes -> metas 3000..3001
 * Pipes.itemPipe(Materials.Tin, 3000).sizes(ItemPipeSize.SMALL, ItemPipeSize.NORMAL).register();
 *
 * ItemStack copper4x = Pipes.getCable(Materials.Copper, CableSize.X4, 1);
 * }</pre>
 */
public final class Pipes {

    private static final Map<String, Map<CableSize, PipeMTE>> CABLES = new HashMap<>();
    private static final Map<String, Map<FluidPipeSize, PipeMTE>> FLUID_PIPES = new HashMap<>();
    private static final Map<String, Map<ItemPipeSize, PipeMTE>> ITEM_PIPES = new HashMap<>();

    private Pipes() {}

    // region Sizes

    public enum CableSize {

        X1(1, 0.125F),
        X2(2, 0.25F),
        X4(4, 0.375F),
        X8(8, 0.5F),
        X12(12, 0.75F),
        X16(16, 1.0F);

        public final int wires;
        public final float thickness;

        CableSize(int wires, float thickness) {
            this.wires = wires;
            this.thickness = thickness;
        }
    }

    public enum FluidPipeSize {

        TINY("Tiny", 1, 0.25F, 1),
        SMALL("Small", 2, 0.375F, 1),
        NORMAL("", 6, 0.5F, 1),
        LARGE("Large", 12, 0.75F, 1),
        HUGE("Huge", 24, 0.875F, 1),
        QUADRUPLE("Quadruple", 6, 1.0F, 4),
        NONUPLE("Nonuple", 6, 1.0F, 9);

        public final String prefix;
        /** capacity multiplier relative to the base capacity */
        public final int capacityMultiplier;
        public final float thickness;
        /** amount of different fluids the pipe can hold at once */
        public final int fluidTypes;

        FluidPipeSize(String prefix, int capacityMultiplier, float thickness, int fluidTypes) {
            this.prefix = prefix;
            this.capacityMultiplier = capacityMultiplier;
            this.thickness = thickness;
            this.fluidTypes = fluidTypes;
        }

        public static final FluidPipeSize[] DEFAULT = { TINY, SMALL, NORMAL, LARGE, HUGE };
    }

    public enum ItemPipeSize {

        SMALL("Small", 0.375F, 1),
        NORMAL("", 0.5F, 2),
        LARGE("Large", 0.75F, 4),
        HUGE("Huge", 0.875F, 8);

        public final String prefix;
        public final float thickness;
        /** item stacks moved per transfer */
        public final int slots;

        ItemPipeSize(String prefix, float thickness, int slots) {
            this.prefix = prefix;
            this.thickness = thickness;
            this.slots = slots;
        }
    }

    // endregion

    // region Entry points

    public static CableBuilder cable(Material material, int startMeta) {
        return new CableBuilder(material, startMeta);
    }

    public static FluidPipeBuilder fluidPipe(Material material, int startMeta) {
        return new FluidPipeBuilder(material, startMeta);
    }

    public static ItemPipeBuilder itemPipe(Material material, int startMeta) {
        return new ItemPipeBuilder(material, startMeta);
    }

    public static ItemStack getCable(Material material, CableSize size, int amount) {
        return get(CABLES, material, size, amount);
    }

    public static ItemStack getFluidPipe(Material material, FluidPipeSize size, int amount) {
        return get(FLUID_PIPES, material, size, amount);
    }

    public static ItemStack getItemPipe(Material material, ItemPipeSize size, int amount) {
        return get(ITEM_PIPES, material, size, amount);
    }

    private static <S> ItemStack get(Map<String, Map<S, PipeMTE>> map, Material material, S size, int amount) {
        Map<S, PipeMTE> sizes = map.get(material.getName());
        PipeMTE pipe = sizes == null ? null : sizes.get(size);
        if (pipe == null) {
            throw new IllegalArgumentException("No " + size + " pipe registered for " + material.getName());
        }
        return pipe.getStackForm(amount);
    }

    // endregion

    // region Builders

    private abstract static class Base<B extends Base<B>> {

        final Material material;
        final int startMeta;
        String sideTexture;
        String endTexture;

        Base(Material material, int startMeta) {
            ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "Pipe of " + material.getName());
            this.material = material;
            this.startMeta = startMeta;
        }

        @SuppressWarnings("unchecked")
        B self() {
            return (B) this;
        }

        /**
         * Overrides the grayscale textures (block texture paths), the end texture may be null.
         */
        public B textures(String side, String end) {
            this.sideTexture = side;
            this.endTexture = end;
            return self();
        }

        PipeAppearance appearance() {
            return PipeAppearance.of(sideTexture, endTexture, material.getRGBA());
        }

        static void name(PipeMTE pipe, String localName) {
            Lang.add(pipe.getLocalNameKey(), localName);
        }
    }

    public static final class CableBuilder extends Base<CableBuilder> {

        private long voltage = GTValues.V[1];
        private long amperage = 1;
        private long loss = 1;
        private boolean insulated;
        private CableSize[] sizes = CableSize.values();

        CableBuilder(Material material, int startMeta) {
            super(material, startMeta);
            sideTexture = "MaterialSystems/Pipes/wire";
            endTexture = null;
        }

        /** Max voltage (EU/p). Higher voltage burns the cable. */
        public CableBuilder voltage(long voltage) {
            this.voltage = voltage;
            return this;
        }

        /** Amperage of the 1x cable, multiplied by the wire count of bigger sizes. */
        public CableBuilder amperage(long amperage) {
            this.amperage = amperage;
            return this;
        }

        /** EU lost per block and ampere. */
        public CableBuilder loss(long lossPerBlock) {
            this.loss = lossPerBlock;
            return this;
        }

        /** Insulated cables do not shock and have half loss. */
        public CableBuilder insulated() {
            this.insulated = true;
            return this;
        }

        public CableBuilder sizes(CableSize... sizes) {
            this.sizes = sizes;
            return this;
        }

        public List<PipeMTE> register() {
            List<PipeMTE> result = new ArrayList<>();
            Map<CableSize, PipeMTE> map = CABLES.computeIfAbsent(material.getName(), k -> new EnumMap<>(CableSize.class));
            PipeAppearance appearance = appearance();
            if (insulated) {
                appearance = appearance
                    .withInsulation("MaterialSystems/Pipes/insulation", "MaterialSystems/Pipes/insulation_end");
            }
            int meta = startMeta;
            for (CableSize size : sizes) {
                String kind = insulated ? "cable" : "wire";
                String name = kind + "." + material.getUnlocalizedName() + "." + size.wires;
                // spotless:off
                MTECable cable = new MTECable(
                    MTEArrays.CABLE,
                    meta++,
                    name,
                    insulated ? Math.min(1.0F, size.thickness + 0.125F) : size.thickness,
                    insulated ? Math.max(1, loss / 2) : loss,
                    amperage * size.wires,
                    voltage,
                    insulated,
                    !insulated);
                // spotless:on
                cable.setAppearance(appearance);
                name(cable, size.wires + "x " + material.getLocalName() + (insulated ? " Cable" : " Wire"));
                map.put(size, cable);
                result.add(cable);
            }
            return result;
        }
    }

    public static final class FluidPipeBuilder extends Base<FluidPipeBuilder> {

        private int capacity = 20;
        private int heatResistance = 1000;
        private boolean gasProof = true;
        private FluidPipeSize[] sizes = FluidPipeSize.DEFAULT;

        FluidPipeBuilder(Material material, int startMeta) {
            super(material, startMeta);
            sideTexture = "MaterialSystems/Pipes/pipe_side";
            endTexture = "MaterialSystems/Pipes/pipe_end";
        }

        /** Base capacity in L per tick for the TINY pipe, other sizes multiply it. */
        public FluidPipeBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        /** Max fluid temperature in Kelvin before the pipe burns. */
        public FluidPipeBuilder heatResistance(int kelvin) {
            this.heatResistance = kelvin;
            return this;
        }

        public FluidPipeBuilder gasProof(boolean gasProof) {
            this.gasProof = gasProof;
            return this;
        }

        public FluidPipeBuilder sizes(FluidPipeSize... sizes) {
            this.sizes = sizes;
            return this;
        }

        public List<PipeMTE> register() {
            List<PipeMTE> result = new ArrayList<>();
            Map<FluidPipeSize, PipeMTE> map = FLUID_PIPES
                .computeIfAbsent(material.getName(), k -> new EnumMap<>(FluidPipeSize.class));
            PipeAppearance appearance = appearance();
            int meta = startMeta;
            for (FluidPipeSize size : sizes) {
                String name = "pipe.fluid." + material.getUnlocalizedName() + "." + size.name()
                    .toLowerCase();
                // spotless:off
                MTEFluidPipe pipe = new MTEFluidPipe(
                    MTEArrays.CABLE,
                    meta++,
                    name,
                    size.thickness,
                    capacity * size.capacityMultiplier,
                    heatResistance,
                    gasProof,
                    size.fluidTypes);
                // spotless:on
                pipe.setAppearance(appearance);
                String prefix = size.prefix.isEmpty() ? "" : size.prefix + " ";
                name(pipe, prefix + material.getLocalName() + " Fluid Pipe");
                map.put(size, pipe);
                result.add(pipe);
            }
            return result;
        }
    }

    public static final class ItemPipeBuilder extends Base<ItemPipeBuilder> {

        private int stepSize = 32768;
        private boolean restrictive;
        private int tickTime = 20;
        private ItemPipeSize[] sizes = ItemPipeSize.values();

        ItemPipeBuilder(Material material, int startMeta) {
            super(material, startMeta);
            sideTexture = "MaterialSystems/Pipes/item_pipe_side";
            endTexture = "MaterialSystems/Pipes/item_pipe_end";
        }

        /** Restrictive pipes have a huge routing cost, items avoid them when another path exists. */
        public ItemPipeBuilder restrictive() {
            this.restrictive = true;
            return this;
        }

        /** Routing cost of one pipe block, items prefer the cheapest path. */
        public ItemPipeBuilder stepSize(int stepSize) {
            this.stepSize = stepSize;
            return this;
        }

        /** Ticks between transfers. */
        public ItemPipeBuilder tickTime(int ticks) {
            this.tickTime = ticks;
            return this;
        }

        public ItemPipeBuilder sizes(ItemPipeSize... sizes) {
            this.sizes = sizes;
            return this;
        }

        public List<PipeMTE> register() {
            List<PipeMTE> result = new ArrayList<>();
            Map<ItemPipeSize, PipeMTE> map = ITEM_PIPES
                .computeIfAbsent(material.getName(), k -> new EnumMap<>(ItemPipeSize.class));
            PipeAppearance appearance = appearance();
            int meta = startMeta;
            for (ItemPipeSize size : sizes) {
                String name = "pipe.item." + material.getUnlocalizedName()
                    + "."
                    + size.name()
                        .toLowerCase()
                    + (restrictive ? ".restrictive" : "");
                // spotless:off
                MTEItemPipe pipe = new MTEItemPipe(
                    MTEArrays.CABLE,
                    meta++,
                    name,
                    size.thickness,
                    size.slots,
                    stepSize,
                    restrictive,
                    tickTime);
                // spotless:on
                pipe.setAppearance(appearance);
                String prefix = size.prefix.isEmpty() ? "" : size.prefix + " ";
                name(pipe, (restrictive ? "Restrictive " : "") + prefix + material.getLocalName() + " Item Pipe");
                map.put(size, pipe);
                result.add(pipe);
            }
            return result;
        }
    }

    // endregion
}
