package gregtech.api.modpack.machine;

import java.util.ArrayList;
import java.util.Arrays;
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
import gregtech.api.modpack.util.Lang;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.OverclockCalculator;

/**
 * Base class of GregTech style single block machines, like {@code MTEBasicMachineWithRecipe} in GTNH: tiered hull,
 * energy input, item slots, fluid tanks, programmed circuit slot, recipe map processing with overclocks, auto output
 * to the front face (screwdriver toggles items, shift + screwdriver fluids) and a ModularUI 2 GUI.
 * <p>
 * Every machine is its own class with two constructors, registration is one line:
 *
 * <pre>{@code
 * public class MTECrusher extends MTEBasicMachine {
 *
 *     private static final MachineOverlay OVERLAY = MachineOverlay.of("machines/crusher");
 *
 *     // prototype: registers the machine under its id
 *     public MTECrusher(int id, String name, String localName, int tier) {
 *         super(id, name, localName, tier, new String[] { "Crushes ores into dust" }, 1, 2, 0, 0);
 *     }
 *
 *     // copy for every placed block
 *     public MTECrusher(String name, int tier, String[] description) {
 *         super(name, tier, description, 1, 2, 0, 0);
 *     }
 *
 *     public IMTE newMTE(IGTE gte) {
 *         return new MTECrusher(name, tier, description);
 *     }
 *
 *     public RecipeMap<?> getRecipeMap() {
 *         return MyRecipeMaps.CRUSHER;
 *     }
 *
 *     protected MachineOverlay getOverlay() {
 *         return OVERLAY;
 *     }
 * }
 *
 * // registration (IModpackModule.registerMachines):
 * CRUSHER_LV = new MTECrusher(1000, "basicmachine.crusher.tier.01", "Basic Crusher", 1).getStackForm(1);
 * CRUSHER_MV = new MTECrusher(1001, "basicmachine.crusher.tier.02", "Advanced Crusher", 2).getStackForm(1);
 * }</pre>
 *
 * Things to override: {@link #getRecipeMap()}, {@link #getOverlay()}, {@link #isGenerator()},
 * {@link #getAmperage()}, {@link #modifyLogic}, {@link #validateRecipe}, {@link #checkProcessing()} (machines
 * without recipes), {@link #onWorkTick()}, {@link #onRecipeStart}, {@link #onRecipeFinished()}, {@link #buildUI}.
 * <p>
 * Slot layout: {@code [inputs][outputs][circuit]}.
 */
@IMTE.SkipGenerateDescription
public abstract class MTEBasicMachine extends MTE implements RecipeMapWorkable, IConfigurationCircuitSupport {

    public final int tier;
    public final String[] description;
    public final int itemInputs;
    public final int itemOutputs;
    public final int fluidInputs;
    public final int fluidOutputs;

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
     * Prototype constructor: registers the machine under {@code id} in the GregTech machine block and its english
     * name.
     *
     * @param name      unique lower case name, e.g. {@code "basicmachine.crusher.tier.01"}
     * @param localName english name
     */
    protected MTEBasicMachine(int id, String name, String localName, int tier, String[] description, int itemInputs,
        int itemOutputs, int fluidInputs, int fluidOutputs) {
        super(MTEArrays.HATCH, id, name, itemInputs + itemOutputs + 1);
        this.tier = tier;
        this.description = description;
        this.itemInputs = itemInputs;
        this.itemOutputs = itemOutputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.inputTanks = createTanks(fluidInputs, getTankCapacity());
        this.outputTanks = createTanks(fluidOutputs, getTankCapacity());
        Lang.add(getLocalNameKey(), localName);
    }

    /** Copy constructor, call it from your copy constructor used in {@link #newMTE(IGTE)}. */
    protected MTEBasicMachine(String name, int tier, String[] description, int itemInputs, int itemOutputs,
        int fluidInputs, int fluidOutputs) {
        super(name, itemInputs + itemOutputs + 1);
        this.mteArrays = MTEArrays.HATCH;
        this.tier = tier;
        this.description = description;
        this.itemInputs = itemInputs;
        this.itemOutputs = itemOutputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.inputTanks = createTanks(fluidInputs, getTankCapacity());
        this.outputTanks = createTanks(fluidOutputs, getTankCapacity());
    }

    private static FluidTank[] createTanks(int count, int capacity) {
        FluidTank[] tanks = new FluidTank[count];
        for (int i = 0; i < count; i++) {
            tanks[i] = new FluidTank(capacity);
        }
        return tanks;
    }

    // region Settings to override

    /**
     * Capacity of every tank. Called from the constructor: return a constant or compute it from the tier only.
     */
    protected int getTankCapacity() {
        return 16_000;
    }

    /** Front overlay textures. Create it once in a static field: {@code MachineOverlay.of("machines/crusher")}. */
    protected MachineOverlay getOverlay() {
        return MachineOverlay.NONE;
    }

    /** Generator: recipes produce their EU/t instead of consuming it, EU is emitted from the front face. */
    public boolean isGenerator() {
        return false;
    }

    /** Amperes usable by a recipe (one more is accepted from cables). */
    public long getAmperage() {
        return 1;
    }

    public long getVoltage() {
        return GTValues.V[MachineTextures.clampTier(tier)];
    }

    // endregion

    public int getTier() {
        return tier;
    }

    // region Slots

    public int getInputSlotStart() {
        return 0;
    }

    public int getOutputSlotStart() {
        return itemInputs;
    }

    public int getOutputSlotEnd() {
        return itemInputs + itemOutputs;
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
        return getOutputSlotEnd();
    }

    @Override
    public boolean allowSelectCircuit() {
        return getRecipeMap() != null;
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
        return !isGenerator();
    }

    @Override
    public boolean isEnetOutput() {
        return isGenerator();
    }

    @Override
    public boolean isInputFacing(ForgeDirection side) {
        return !isGenerator() && side != getGTE().getFrontFacing();
    }

    @Override
    public boolean isOutputFacing(ForgeDirection side) {
        return isGenerator() && side == getGTE().getFrontFacing();
    }

    @Override
    public long maxEUInput() {
        return isGenerator() ? 0 : getVoltage();
    }

    @Override
    public long maxEUOutput() {
        return isGenerator() ? getVoltage() : 0;
    }

    @Override
    public long maxAmperesIn() {
        return getAmperage() + 1;
    }

    @Override
    public long maxAmperesOut() {
        return getAmperage();
    }

    @Override
    public long maxEUStore() {
        return 512 + getVoltage() * 64;
    }

    @Override
    public long getMinimumStoredEU() {
        return isGenerator() ? getVoltage() : 512;
    }

    // endregion

    // region Processing

    @Override
    public RecipeMap<?> getRecipeMap() {
        return null;
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
                return MTEBasicMachine.this.validateRecipe(recipe);
            }

            @Override
            protected @Nonnull OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                // generators produce the recipe EU/t as is, they never overclock
                return super.createOverclockCalculator(recipe)
                    .setNoOverclock(isGenerator());
            }

            @Override
            protected @Nonnull CheckRecipeResult onRecipeStart(@Nonnull GTRecipe recipe) {
                MTEBasicMachine.this.onRecipeStart(recipe);
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
        if (allowSelectCircuit() && inventory[getCircuitSlot()] != null) {
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
        RecipeMap<?> recipeMap = getRecipeMap();
        if (recipeMap == null) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        ProcessingLogic logic = getProcessingLogic();
        logic.clear();
        logic.setRecipeMap(recipeMap)
            .setInputItems(getRecipeInputItems())
            .setInputFluids(getRecipeInputFluids())
            .setAvailableVoltage(getVoltage())
            .setAvailableAmperage(getAmperage());
        modifyLogic(logic);

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
    protected void onWorkTick() {}

    protected void onRecipeFinished() {}

    /** Tweak the logic before every recipe search: overclocks, speed, EU discount, parallels. */
    protected void modifyLogic(ProcessingLogic logic) {}

    /** Extra condition for a found recipe, return a failure to block it. */
    protected CheckRecipeResult validateRecipe(GTRecipe recipe) {
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    /** Recipe started, inputs are already consumed. */
    protected void onRecipeStart(GTRecipe recipe) {}

    /** Uses (or for generators produces) the energy of one tick. */
    protected boolean drainEnergyForTick() {
        IGTE gte = getGTE();
        if (isGenerator()) {
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
            if (autoOutputItems && itemOutputs > 0) {
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
        info.add(getLocalName());
        info.add("Progress: " + progress / 20 + " / " + maxProgress / 20 + " s");
        info.add("Energy: " + getGTE().getStoredEU() + " / " + maxEUStore() + " EU");
        if (maxProgress > 0) {
            info.add((isGenerator() ? "Producing: " : "Using: ") + eut + " EU/t");
        }
        if (lastResult != null && !lastResult.wasSuccessful() && maxProgress <= 0) {
            info.add(lastResult.getDisplayString());
        }
        return info.toArray(new String[0]);
    }

    @Override
    public String[] getDescription() {
        List<String> lines = new ArrayList<>(Arrays.asList(description));
        lines.add((isGenerator() ? "Output: " : "Input: ") + GTValues.V[MachineTextures.clampTier(tier)]
            + " EU/t ("
            + GTValues.VN[MachineTextures.clampTier(tier)]
            + ")");
        return lines.toArray(new String[0]);
    }

    // endregion

    // region Rendering

    @Override
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel) {
        ITexture hull = MachineTextures.hull(tier, side);
        ITexture overlay = getOverlay().get(side, facing, active);
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
