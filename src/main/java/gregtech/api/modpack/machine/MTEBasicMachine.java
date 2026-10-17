package gregtech.api.modpack.machine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.interfaces.IConfigurationCircuitSupport;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.MTE;
import gregtech.api.modpack.util.InventoryUtil;
import gregtech.api.modpack.util.Prototypes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.OverclockCalculator;

/**
 * GregTech style single block machine: tiered hull, energy input, item and fluid slots, recipe map processing,
 * auto output to the front face (toggle with a screwdriver) and a ModularUI 2 GUI.
 * <p>
 * Use it as is through {@link MachineBuilder}, give it custom hooks with {@link MachineLogic}, or extend it:
 *
 * <pre>{@code
 * public class MTESolarCrusher extends MTEBasicMachine {
 *
 *     public MTESolarCrusher(MachineConfig config) {
 *         super(config);
 *     }
 *
 *     // only works during the day
 *     protected CheckRecipeResult checkProcessing() {
 *         if (!getGTE().getWorld().isDaytime()) return SimpleCheckRecipeResult.ofFailure("night");
 *         return super.checkProcessing();
 *     }
 * }
 *
 * MachineBuilder.create("solar_crusher").factory(MTESolarCrusher::new)...
 * }</pre>
 *
 * Slot layout: {@code [inputs][outputs][circuit]}.
 */
@IMTE.SkipGenerateDescription
public class MTEBasicMachine extends MTE implements RecipeMapWorkable, IConfigurationCircuitSupport {

    protected final MachineConfig config;

    protected final FluidTank[] inputTanks;
    protected final FluidTank[] outputTanks;

    /** Ticks done of the current recipe. */
    protected int progress;
    /** Recipe duration in ticks, 0 = idle. */
    protected int maxProgress;
    /** EU/t used (or produced by generators) while working. */
    protected long eut;
    protected ItemStack[] pendingItemOutputs;
    protected FluidStack[] pendingFluidOutputs;

    protected boolean autoOutputItems = true;
    protected boolean autoOutputFluids = true;
    protected boolean inputsChanged = true;
    protected CheckRecipeResult lastResult = CheckRecipeResultRegistry.NONE;

    protected ProcessingLogic processingLogic;

    /**
     * The only constructor: used for the prototype (then {@link #asPrototype(int)} is called by the builder) and for
     * every placed machine (from {@link #newMTE(IGTE)}).
     */
    public MTEBasicMachine(MachineConfig config) {
        super(config.id, config.getSlotCount());
        this.mteArrays = MTEArrays.HATCH;
        this.config = config;
        this.inputTanks = createTanks(config.fluidInputs, config.tankCapacity);
        this.outputTanks = createTanks(config.fluidOutputs, config.tankCapacity);
    }

    /**
     * Registers this instance as the prototype for {@code meta} in the {@link MTEArrays#HATCH} array (what the
     * {@code MTE(MTEArrays, int, String, int)} constructor does for hand written machines).
     */
    public final MTEBasicMachine asPrototype(int meta) {
        Prototypes.register(this, meta);
        return this;
    }

    private static FluidTank[] createTanks(int count, int capacity) {
        FluidTank[] tanks = new FluidTank[count];
        for (int i = 0; i < count; i++) {
            tanks[i] = new FluidTank(capacity);
        }
        return tanks;
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return config.factory.create(config);
    }

    public MachineConfig getConfig() {
        return config;
    }

    public int getTier() {
        return config.tier;
    }

    // region Slots

    public int getInputSlotStart() {
        return 0;
    }

    public int getOutputSlotStart() {
        return config.itemInputs;
    }

    public int getOutputSlotEnd() {
        return config.itemInputs + config.itemOutputs;
    }

    public ItemStack getInputStack(int index) {
        return inventory[getInputSlotStart() + index];
    }

    public ItemStack getOutputStack(int index) {
        return inventory[getOutputSlotStart() + index];
    }

    public FluidTank[] getInputTanks() {
        return inputTanks;
    }

    public FluidTank[] getOutputTanks() {
        return outputTanks;
    }

    @Override
    public int getCircuitSlot() {
        return config.circuitSlot ? getOutputSlotEnd() : -1;
    }

    @Override
    public boolean allowSelectCircuit() {
        return config.circuitSlot;
    }

    @Override
    public int getCircuitSlotX() {
        return 153;
    }

    @Override
    public int getCircuitSlotY() {
        return 63;
    }

    @Override
    public boolean isValidSlot(int index) {
        return index != getCircuitSlot();
    }

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return index >= getInputSlotStart() && index < getOutputSlotStart();
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return index >= getOutputSlotStart() && index < getOutputSlotEnd();
    }

    @Override
    public void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        inputsChanged = true;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        super.setInventorySlotContents(index, itemStack);
        inputsChanged = true;
    }

    // endregion

    // region Fluids

    @Override
    public int fill(ForgeDirection side, FluidStack fluid, boolean doFill) {
        if (fluid == null || inputTanks.length == 0) {
            return 0;
        }
        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() != null && tank.getFluid()
                .isFluidEqual(fluid)) {
                int filled = tank.fill(fluid, doFill);
                if (filled > 0 && doFill) {
                    inputsChanged = true;
                    markDirty();
                }
                return filled;
            }
        }
        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() == null) {
                int filled = tank.fill(fluid, doFill);
                if (filled > 0 && doFill) {
                    inputsChanged = true;
                    markDirty();
                }
                return filled;
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection side, int maxDrain, boolean doDrain) {
        for (FluidTank tank : outputTanks) {
            if (tank.getFluidAmount() > 0) {
                FluidStack drained = tank.drain(maxDrain, doDrain);
                if (doDrain) {
                    markDirty();
                }
                return drained;
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection side, FluidStack fluid, boolean doDrain) {
        if (fluid == null) {
            return null;
        }
        for (FluidTank tank : outputTanks) {
            if (tank.getFluid() != null && tank.getFluid()
                .isFluidEqual(fluid)) {
                FluidStack drained = tank.drain(fluid.amount, doDrain);
                if (doDrain) {
                    markDirty();
                }
                return drained;
            }
        }
        return null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection side) {
        FluidTankInfo[] info = new FluidTankInfo[inputTanks.length + outputTanks.length];
        for (int i = 0; i < inputTanks.length; i++) {
            info[i] = inputTanks[i].getInfo();
        }
        for (int i = 0; i < outputTanks.length; i++) {
            info[inputTanks.length + i] = outputTanks[i].getInfo();
        }
        return info;
    }

    // endregion

    // region Energy

    @Override
    public boolean isEnetInput() {
        return !config.generator;
    }

    @Override
    public boolean isEnetOutput() {
        return config.generator;
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        return !config.generator && side != getGTE().getFrontFacing();
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        return config.generator && side == getGTE().getFrontFacing();
    }

    @Override
    public long maxEUInput() {
        return config.generator ? 0 : config.getVoltage();
    }

    @Override
    public long maxEUOutput() {
        return config.generator ? config.getVoltage() : 0;
    }

    @Override
    public long maxAmperesIn() {
        return config.amperage + 1;
    }

    @Override
    public long maxAmperesOut() {
        return config.amperage;
    }

    @Override
    public long maxEUStore() {
        return config.euStorage > 0 ? config.euStorage : 512 + config.getVoltage() * 64;
    }

    @Override
    public long getMinimumStoredEU() {
        return config.generator ? config.getVoltage() : 512;
    }

    // endregion

    // region Processing

    @Override
    public RecipeMap<?> getRecipeMap() {
        return config.recipeMap;
    }

    /**
     * Creates the recipe search logic. Override to change overclocks, parallels or recipe checks globally.
     */
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (!canOutputItems(recipe)) {
                    return CheckRecipeResultRegistry.ITEM_OUTPUT_FULL;
                }
                if (!canOutputFluids(recipe)) {
                    return CheckRecipeResultRegistry.FLUID_OUTPUT_FULL;
                }
                return config.logic.validateRecipe(MTEBasicMachine.this, recipe);
            }

            @Override
            protected @Nonnull OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                // generators produce the recipe EU/t as is, they never overclock
                return super.createOverclockCalculator(recipe)
                    .setNoOverclock(config.generator);
            }

            @Override
            protected @Nonnull CheckRecipeResult onRecipeStart(@Nonnull GTRecipe recipe) {
                config.logic.onRecipeStart(MTEBasicMachine.this, recipe);
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
        };
    }

    public ProcessingLogic getProcessingLogic() {
        if (processingLogic == null) {
            processingLogic = createProcessingLogic();
        }
        return processingLogic;
    }

    /** Whether all item outputs of a recipe fit into the output slots. */
    protected boolean canOutputItems(GTRecipe recipe) {
        return InventoryUtil
            .canFitAll(inventory, getOutputSlotStart(), getOutputSlotEnd(), recipe.mOutputs, getInventoryStackLimit());
    }

    /** Whether all fluid outputs of a recipe fit into the output tanks. */
    protected boolean canOutputFluids(GTRecipe recipe) {
        if (recipe.mFluidOutputs != null) {
            for (FluidStack fluid : recipe.mFluidOutputs) {
                if (fluid != null && !canFitFluid(fluid)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean canFitFluid(FluidStack fluid) {
        for (FluidTank tank : outputTanks) {
            if (tank.getFluid() == null || tank.getFluid()
                .isFluidEqual(fluid)) {
                if (tank.getCapacity() - tank.getFluidAmount() >= fluid.amount) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Input stacks passed to the recipe search (the real stacks, they get consumed in place). */
    protected ItemStack[] getRecipeInputItems() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = getInputSlotStart(); i < getOutputSlotStart(); i++) {
            if (inventory[i] != null) {
                stacks.add(inventory[i]);
            }
        }
        if (config.circuitSlot && inventory[getCircuitSlot()] != null) {
            stacks.add(inventory[getCircuitSlot()]);
        }
        return stacks.toArray(new ItemStack[0]);
    }

    protected FluidStack[] getRecipeInputFluids() {
        List<FluidStack> fluids = new ArrayList<>();
        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() != null) {
                fluids.add(tank.getFluid());
            }
        }
        return fluids.toArray(new FluidStack[0]);
    }

    /**
     * Searches and starts a recipe. On success it must set {@link #maxProgress}, {@link #eut},
     * {@link #pendingItemOutputs} and {@link #pendingFluidOutputs}.
     * <p>
     * Override for machines without recipe map (generators with custom fuel logic, pumps, miners...).
     */
    protected CheckRecipeResult checkProcessing() {
        if (config.recipeMap == null) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        ProcessingLogic logic = getProcessingLogic();
        logic.clear();
        logic.setRecipeMap(config.recipeMap)
            .setInputItems(getRecipeInputItems())
            .setInputFluids(getRecipeInputFluids())
            .setAvailableVoltage(config.getVoltage())
            .setAvailableAmperage(config.amperage);
        if (config.perfectOverclock) {
            logic.enablePerfectOverclock();
        }
        config.logic.modifyLogic(this, logic);

        CheckRecipeResult result = logic.process();
        if (!result.wasSuccessful()) {
            return result;
        }
        maxProgress = Math.max(1, logic.getDuration());
        eut = Math.abs(logic.getCalculatedEut());
        pendingItemOutputs = logic.getOutputItems();
        pendingFluidOutputs = logic.getOutputFluids();
        return result;
    }

    private void cleanupInputs() {
        for (int i = getInputSlotStart(); i < getOutputSlotStart(); i++) {
            if (inventory[i] != null && inventory[i].stackSize <= 0) {
                inventory[i] = null;
            }
        }
        for (FluidTank tank : inputTanks) {
            if (tank.getFluid() != null && tank.getFluid().amount <= 0) {
                tank.setFluid(null);
            }
        }
    }

    /** Puts {@link #pendingItemOutputs} and {@link #pendingFluidOutputs} into the outputs. */
    protected void outputProducts() {
        if (pendingItemOutputs != null) {
            for (ItemStack stack : pendingItemOutputs) {
                InventoryUtil
                    .insert(inventory, getOutputSlotStart(), getOutputSlotEnd(), stack, getInventoryStackLimit(), false);
            }
        }
        if (pendingFluidOutputs != null) {
            for (FluidStack fluid : pendingFluidOutputs) {
                if (fluid == null) {
                    continue;
                }
                for (FluidTank tank : outputTanks) {
                    if (tank.getFluid() == null || tank.getFluid()
                        .isFluidEqual(fluid)) {
                        tank.fill(fluid, true);
                        break;
                    }
                }
            }
        }
        pendingItemOutputs = null;
        pendingFluidOutputs = null;
    }

    /** Called every tick while a recipe runs, energy is already used. */
    protected void onWorkTick() {
        config.logic.onWorkTick(this);
    }

    protected void onRecipeFinished() {
        config.logic.onRecipeFinish(this);
    }

    /** Uses (or for generators produces) the energy of one tick. */
    protected boolean drainEnergyForTick() {
        IGTE gte = getGTE();
        if (config.generator) {
            gte.increaseStoredEnergyUnits(eut, true);
            return true;
        }
        return eut <= 0 || gte.decreaseStoredEnergyUnits(eut, false);
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (!gte.isServerSide()) {
            return;
        }
        config.logic.onTick(this, tick);

        if (maxProgress > 0) {
            if (drainEnergyForTick()) {
                progress++;
                gte.setActive(true);
                onWorkTick();
                if (progress >= maxProgress) {
                    outputProducts();
                    progress = 0;
                    maxProgress = 0;
                    eut = 0;
                    inputsChanged = true;
                    onRecipeFinished();
                    markDirty();
                }
            } else {
                // not enough energy: GregTech machines lose their progress
                progress = 0;
                gte.setActive(false);
            }
        }

        if (maxProgress <= 0) {
            if (gte.isAllowedToWork() && (inputsChanged || tick % 100 == 0)) {
                inputsChanged = false;
                lastResult = checkProcessing();
                cleanupInputs();
                if (lastResult.wasSuccessful() && maxProgress > 0) {
                    progress = 0;
                    gte.setActive(true);
                    markDirty();
                } else {
                    maxProgress = 0;
                    gte.setActive(false);
                }
            } else if (!gte.isAllowedToWork()) {
                gte.setActive(false);
            }
        }

        if (tick % 20 == 0) {
            ForgeDirection front = gte.getFrontFacing();
            if (autoOutputItems && config.itemOutputs > 0) {
                InventoryUtil.pushToNeighbour(
                    inventory,
                    getOutputSlotStart(),
                    getOutputSlotEnd(),
                    gte.getTileEntityAtSide(front),
                    front);
            }
            if (autoOutputFluids && outputTanks.length > 0) {
                FluidStack[] fluids = new FluidStack[outputTanks.length];
                for (int i = 0; i < outputTanks.length; i++) {
                    fluids[i] = outputTanks[i].getFluid();
                }
                InventoryUtil.pushFluids(fluids, gte.getTileEntityAtSide(front), front);
                for (int i = 0; i < outputTanks.length; i++) {
                    if (fluids[i] == null) {
                        outputTanks[i].setFluid(null);
                    }
                }
            }
        }
    }

    @Override
    public int getProgressTime() {
        return progress;
    }

    @Override
    public int maxProgressTime() {
        return maxProgress;
    }

    public long getEUt() {
        return eut;
    }

    public CheckRecipeResult getLastResult() {
        return lastResult;
    }

    // endregion

    // region Interaction

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player, ForgeDirection side, float x, float y, float z) {
        if (config.logic.onRightClick(this, player, side)) {
            return true;
        }
        return super.onRightClick(gte, player, side, x, y, z);
    }

    @Override
    public boolean onRightClick(IGTE gte, EntityPlayer player) {
        if (gte.isClientSide()) {
            return true;
        }
        openGui(player);
        return true;
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z,
        ItemStack tool) {
        if (player.isSneaking()) {
            autoOutputFluids = !autoOutputFluids;
            player.addChatComponentMessage(
                new ChatComponentText("Auto output fluids: " + (autoOutputFluids ? "enabled" : "disabled")));
        } else {
            autoOutputItems = !autoOutputItems;
            player.addChatComponentMessage(
                new ChatComponentText("Auto output items: " + (autoOutputItems ? "enabled" : "disabled")));
        }
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        List<String> info = new ArrayList<>();
        info.add(config.localName);
        info.add("Progress: " + progress / 20 + " / " + maxProgress / 20 + " s");
        info.add("Energy: " + getGTE().getStoredEU() + " / " + maxEUStore() + " EU");
        if (maxProgress > 0) {
            info.add((config.generator ? "Producing: " : "Using: ") + eut + " EU/t");
        }
        if (lastResult != null && !lastResult.wasSuccessful() && maxProgress <= 0) {
            info.add(lastResult.getDisplayString());
        }
        config.logic.addInfo(this, info);
        return info.toArray(new String[0]);
    }

    @Override
    public String[] getDescription() {
        List<String> lines = new ArrayList<>(config.description);
        lines.add((config.generator ? "Output: " : "Input: ") + GTValues.V[MachineTextures.clampTier(config.tier)]
            + " EU/t ("
            + GTValues.VN[MachineTextures.clampTier(config.tier)]
            + ")");
        return lines.toArray(new String[0]);
    }

    // endregion

    // region Rendering

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel) {
        ITexture hull = MachineTextures.hull(config.tier, side);
        ITexture overlay = config.overlay.get(side, facing, active);
        return overlay == null ? new ITexture[] { hull } : new ITexture[] { hull, overlay };
    }

    // endregion

    // region NBT

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt.setInteger("progress", progress);
        nbt.setInteger("maxProgress", maxProgress);
        nbt.setLong("eut", eut);
        nbt.setBoolean("autoOutputItems", autoOutputItems);
        nbt.setBoolean("autoOutputFluids", autoOutputFluids);
        nbt.setTag("inputTanks", writeTanks(inputTanks));
        nbt.setTag("outputTanks", writeTanks(outputTanks));
        if (pendingItemOutputs != null) {
            NBTTagList list = new NBTTagList();
            for (ItemStack stack : pendingItemOutputs) {
                if (stack != null) {
                    list.appendTag(stack.writeToNBT(new NBTTagCompound()));
                }
            }
            nbt.setTag("pendingItems", list);
        }
        if (pendingFluidOutputs != null) {
            NBTTagList list = new NBTTagList();
            for (FluidStack fluid : pendingFluidOutputs) {
                if (fluid != null) {
                    list.appendTag(fluid.writeToNBT(new NBTTagCompound()));
                }
            }
            nbt.setTag("pendingFluids", list);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        progress = nbt.getInteger("progress");
        maxProgress = nbt.getInteger("maxProgress");
        eut = nbt.getLong("eut");
        if (nbt.hasKey("autoOutputItems")) {
            autoOutputItems = nbt.getBoolean("autoOutputItems");
            autoOutputFluids = nbt.getBoolean("autoOutputFluids");
        }
        readTanks(nbt.getTagList("inputTanks", 10), inputTanks);
        readTanks(nbt.getTagList("outputTanks", 10), outputTanks);
        if (nbt.hasKey("pendingItems")) {
            NBTTagList list = nbt.getTagList("pendingItems", 10);
            pendingItemOutputs = new ItemStack[list.tagCount()];
            for (int i = 0; i < list.tagCount(); i++) {
                pendingItemOutputs[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
            }
        }
        if (nbt.hasKey("pendingFluids")) {
            NBTTagList list = nbt.getTagList("pendingFluids", 10);
            pendingFluidOutputs = new FluidStack[list.tagCount()];
            for (int i = 0; i < list.tagCount(); i++) {
                pendingFluidOutputs[i] = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
            }
        }
    }

    protected static NBTTagList writeTanks(FluidTank[] tanks) {
        NBTTagList list = new NBTTagList();
        for (FluidTank tank : tanks) {
            list.appendTag(tank.writeToNBT(new NBTTagCompound()));
        }
        return list;
    }

    protected static void readTanks(NBTTagList list, FluidTank[] tanks) {
        for (int i = 0; i < tanks.length && i < list.tagCount(); i++) {
            tanks[i].setFluid(null);
            tanks[i].readFromNBT(list.getCompoundTagAt(i));
        }
    }

    // endregion

    // region GUI

    @Override
    protected boolean useMui2() {
        return true;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        return MachineGui.build(this, data, syncManager, uiSettings);
    }

    // endregion
}
