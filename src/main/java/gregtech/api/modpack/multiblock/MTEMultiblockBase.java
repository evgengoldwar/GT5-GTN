package gregtech.api.modpack.multiblock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.MTEArrays;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.MTE;
import gregtech.api.modpack.hatch.CasingTextures;
import gregtech.api.modpack.hatch.MTEHatch;
import gregtech.api.modpack.hatch.MTEHatchEnergy;
import gregtech.api.modpack.hatch.MTEHatchFluid;
import gregtech.api.modpack.hatch.MTEHatchItems;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.modpack.util.InventoryUtil;
import gregtech.api.modpack.util.Lang;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;

/**
 * Base class of GregTech style multiblocks (like {@code MTEExtendedPowerMultiBlockBase} in GTNH), built with
 * StructureLib. Every multiblock is its own class:
 *
 * <pre>{@code
 * public class MTEBigFurnace extends MTEMultiblockBase {
 *
 *     // static fields are created when the class loads (pre-init, same order on client and server)
 *     private static final int CASING = CasingTextures.register(Materials.Steel.getCasing(), Materials.Steel.getMeta());
 *     private static final IStructureDefinition<MTEMultiblockBase> STRUCTURE = StructureDefinition
 *         .<MTEMultiblockBase>builder()
 *         .addShape(MAIN_PIECE, StructureUtility.transpose(new String[][] {
 *             { "CCC", "CCC", "CCC" },  // top layer, rows go from the front to the back
 *             { "H~H", "C-C", "CCC" },  // '~' is the controller, '-' is air
 *             { "CCC", "CCC", "CCC" } }))
 *         .addElement('C', MultiblockElements.casing(Materials.Steel))
 *         .addElement('H', MultiblockElements.hatchOrCasing(Materials.Steel, HatchType.PROCESSING))
 *         .build();
 *
 *     public MTEBigFurnace(int id, String name, String localName) {  // prototype
 *         super(id, name, localName);
 *     }
 *
 *     public MTEBigFurnace(String name) {                              // copy
 *         super(name);
 *     }
 *
 *     public IMTE newMTE(IGTE gte) {
 *         return new MTEBigFurnace(name);
 *     }
 *
 *     public IStructureDefinition<MTEMultiblockBase> getStructureDefinition() {
 *         return STRUCTURE;
 *     }
 *
 *     protected boolean checkMachine() {
 *         // controller offset: column 1, layer 1, row 0 of the '~'
 *         return checkPiece(MAIN_PIECE, 1, 1, 0) && getCasingCount() >= 12 && !getEnergyHatches().isEmpty();
 *     }
 *
 *     public void construct(ItemStack trigger, boolean hintsOnly) {
 *         buildPiece(MAIN_PIECE, trigger, hintsOnly, 1, 1, 0);
 *     }
 *
 *     protected int getCasingTextureId() {
 *         return CASING;
 *     }
 *
 *     public RecipeMap<?> getRecipeMap() {
 *         return MyRecipeMaps.BIG_FURNACE;
 *     }
 *
 *     protected MultiblockTooltipBuilder createTooltip() {
 *         return new MultiblockTooltipBuilder().addMachineType("Furnace")
 *             .addInfo("Smelts a lot")
 *             .beginStructureBlock(3, 3, 3, true)
 *             .addController("Front center")
 *             .addCasingInfoMin("Steel Machine Casing", 12, false)
 *             .addEnergyHatch("Any casing", 1)
 *             .addInputBus("Any casing", 1)
 *             .addOutputBus("Any casing", 1)
 *             .toolTipFinisher();
 *     }
 * }
 *
 * // registration (IModpackModule.registerMachines):
 * BIG_FURNACE = new MTEBigFurnace(10000, "multimachine.big_furnace", "Big Furnace").getStackForm(1);
 * }</pre>
 */
@IMTE.SkipGenerateDescription
public abstract class MTEMultiblockBase extends MTE implements RecipeMapWorkable, IConstructable {

    public static final String MAIN_PIECE = "main";

    protected final List<MTEHatchItems> inputBuses = new ArrayList<>();
    protected final List<MTEHatchItems> outputBuses = new ArrayList<>();
    protected final List<MTEHatchFluid> inputHatches = new ArrayList<>();
    protected final List<MTEHatchFluid> outputHatches = new ArrayList<>();
    protected final List<MTEHatchEnergy> energyHatches = new ArrayList<>();
    protected final List<MTEHatchEnergy> dynamoHatches = new ArrayList<>();

    protected int casingCount;
    protected boolean structureFormed;
    protected boolean structureChanged = true;

    protected int progress;
    protected int maxProgress;
    /** EU/t used (or produced for generators) while working */
    protected long eut;
    protected ItemStack[] pendingItemOutputs;
    protected FluidStack[] pendingFluidOutputs;
    protected CheckRecipeResult lastResult = CheckRecipeResultRegistry.NONE;
    protected ProcessingLogic processingLogic;

    private MultiblockTooltipBuilder tooltip;

    /** Prototype constructor: registers the controller under {@code id} and its english name. */
    protected MTEMultiblockBase(int id, String name, String localName) {
        super(MTEArrays.HATCH, id, name, 0);
        Lang.add(getLocalNameKey(), localName);
    }

    /** Copy constructor, used in {@link #newMTE(IGTE)}. */
    protected MTEMultiblockBase(String name) {
        super(name, 0);
        this.mteArrays = MTEArrays.HATCH;
    }

    /** Generator multiblock: recipes produce their EU/t into the dynamo hatches. */
    public boolean isGenerator() {
        return false;
    }

    // region Tooltip

    /** Item tooltip and hologram projector description, like {@code createTooltip()} in GTNH. */
    protected abstract MultiblockTooltipBuilder createTooltip();

    protected MultiblockTooltipBuilder getTooltip() {
        if (tooltip == null) {
            tooltip = createTooltip();
        }
        return tooltip;
    }

    @Override
    public String[] getDescription() {
        return getTooltip().getInformation();
    }

    @Override
    public String[] getStructureDescription(ItemStack trigger) {
        return getTooltip().getStructureInformation();
    }

    // endregion

    // region Structure

    /** StructureLib definition, create it once (static) and return it. */
    public abstract IStructureDefinition<MTEMultiblockBase> getStructureDefinition();

    /**
     * Checks the structure. Hatch lists and the casing counter are cleared before. Use {@link #checkPiece} and add
     * your own conditions (casing count, required hatches, coil tier...).
     */
    protected abstract boolean checkMachine();

    /** Texture id of the casing for the controller faces, see {@link CasingTextures}. */
    protected abstract int getCasingTextureId();

    /** Front overlay of the controller. */
    protected MachineOverlay getOverlay() {
        return MachineOverlay.NONE;
    }

    protected final ExtendedFacing getExtendedFacing() {
        return ExtendedFacing.of(getGTE().getFrontFacing());
    }

    /**
     * @param horizontalOffset column of the controller ('~') in its row
     * @param verticalOffset   layer of the controller, 0 = top layer
     * @param depthOffset      row of the controller inside its layer, 0 = front row
     */
    protected final boolean checkPiece(String piece, int horizontalOffset, int verticalOffset, int depthOffset) {
        IGTE gte = getGTE();
        // spotless:off
        return getStructureDefinition().check(this, piece, gte.getWorld(), getExtendedFacing(),
            gte.getXCord(), gte.getYCord(), gte.getZCord(), horizontalOffset, verticalOffset, depthOffset,
            !structureFormed);
        // spotless:on
    }

    protected final boolean buildPiece(String piece, ItemStack trigger, boolean hintsOnly, int horizontalOffset,
        int verticalOffset, int depthOffset) {
        IGTE gte = getGTE();
        // spotless:off
        return getStructureDefinition().buildOrHints(this, trigger, piece, gte.getWorld(), getExtendedFacing(),
            gte.getXCord(), gte.getYCord(), gte.getZCord(), horizontalOffset, verticalOffset, depthOffset, hintsOnly);
        // spotless:on
    }

    /**
     * Builds the structure with the StructureLib hologram projector. Call {@link #buildPiece} with the same offsets as
     * {@link #checkMachine()}.
     */
    @Override
    public abstract void construct(ItemStack trigger, boolean hintsOnly);

    /** Called by casing elements. */
    public void onCasingFound() {
        casingCount++;
    }

    public int getCasingCount() {
        return casingCount;
    }

    /**
     * Called by hatch elements: remembers the hatch and gives it the casing texture.
     *
     * @return false to reject the hatch
     */
    public boolean addHatch(MTEHatch hatch, int casingTextureId) {
        hatch.setCasingTexture(casingTextureId);
        switch (hatch.getType()) {
            case INPUT_BUS -> inputBuses.add((MTEHatchItems) hatch);
            case OUTPUT_BUS -> outputBuses.add((MTEHatchItems) hatch);
            case INPUT_HATCH -> inputHatches.add((MTEHatchFluid) hatch);
            case OUTPUT_HATCH -> outputHatches.add((MTEHatchFluid) hatch);
            case ENERGY -> energyHatches.add((MTEHatchEnergy) hatch);
            case DYNAMO -> dynamoHatches.add((MTEHatchEnergy) hatch);
            // own hatch kinds: override addHatch, handle them and call super for the rest
            case OTHER -> {
                return false;
            }
        }
        return true;
    }

    private void clearStructure() {
        inputBuses.clear();
        outputBuses.clear();
        inputHatches.clear();
        outputHatches.clear();
        energyHatches.clear();
        dynamoHatches.clear();
        casingCount = 0;
    }

    public boolean isStructureFormed() {
        return structureFormed;
    }

    public List<MTEHatchItems> getInputBuses() {
        return inputBuses;
    }

    public List<MTEHatchItems> getOutputBuses() {
        return outputBuses;
    }

    public List<MTEHatchFluid> getInputHatches() {
        return inputHatches;
    }

    public List<MTEHatchFluid> getOutputHatches() {
        return outputHatches;
    }

    public List<MTEHatchEnergy> getEnergyHatches() {
        return energyHatches;
    }

    public List<MTEHatchEnergy> getDynamoHatches() {
        return dynamoHatches;
    }

    @Override
    public void onMachineBlockUpdate() {
        structureChanged = true;
    }

    // endregion

    // region Energy

    /** Highest voltage of the energy hatches. */
    public long getMaxInputVoltage() {
        long voltage = 0;
        for (MTEHatchEnergy hatch : energyHatches) {
            if (hatch.isValid()) {
                voltage = Math.max(voltage, hatch.getVoltage());
            }
        }
        return voltage;
    }

    /** Amperes available for recipes: one per energy hatch (like GregTech). */
    public long getMaxInputAmperage() {
        return energyHatches.size();
    }

    public long getStoredEnergy() {
        long stored = 0;
        for (MTEHatchEnergy hatch : energyHatches) {
            if (hatch.isValid()) {
                stored += hatch.getGTE()
                    .getStoredEU();
            }
        }
        return stored;
    }

    /** Takes EU from the energy hatches. */
    protected boolean drainEnergy(long amount) {
        if (getStoredEnergy() < amount) {
            return false;
        }
        long left = amount;
        for (MTEHatchEnergy hatch : energyHatches) {
            if (left <= 0) {
                break;
            }
            if (!hatch.isValid()) {
                continue;
            }
            long take = Math.min(
                left,
                hatch.getGTE()
                    .getStoredEU());
            if (take > 0 && hatch.getGTE()
                .decreaseStoredEnergyUnits(take, false)) {
                left -= take;
            }
        }
        return left <= 0;
    }

    /** Puts EU into the dynamo hatches. */
    protected void addEnergyToDynamos(long amount) {
        long left = amount;
        for (MTEHatchEnergy hatch : dynamoHatches) {
            if (left <= 0) {
                break;
            }
            if (!hatch.isValid()) {
                continue;
            }
            IGTE gte = hatch.getGTE();
            long space = gte.getEUCapacity() - gte.getStoredEU();
            long put = Math.min(left, space);
            if (put > 0) {
                gte.increaseStoredEnergyUnits(put, false);
                left -= put;
            }
        }
    }

    // endregion

    // region Processing

    @Override
    public RecipeMap<?> getRecipeMap() {
        return null;
    }

    /** Recipes processed in parallel. */
    protected int getMaxParallel() {
        return 1;
    }

    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (!canOutputItems(recipe.mOutputs)) {
                    return CheckRecipeResultRegistry.ITEM_OUTPUT_FULL;
                }
                if (!canOutputFluids(recipe.mFluidOutputs)) {
                    return CheckRecipeResultRegistry.FLUID_OUTPUT_FULL;
                }
                return MTEMultiblockBase.this.validateRecipe(recipe);
            }

            @Override
            protected @Nonnull OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                // generators produce the recipe EU/t as is, they never overclock
                return super.createOverclockCalculator(recipe)
                    .setNoOverclock(isGenerator());
            }

            @Override
            protected @Nonnull CheckRecipeResult onRecipeStart(@Nonnull GTRecipe recipe) {
                MTEMultiblockBase.this.onRecipeStart(recipe);
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

    /** Extra recipe condition. */
    protected CheckRecipeResult validateRecipe(GTRecipe recipe) {
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    protected void onRecipeStart(GTRecipe recipe) {}

    protected void onRecipeFinished() {}

    /** Every tick while running, after the energy was used. */
    protected void onWorkTick() {}

    /** Tweak overclocks / bonuses before every recipe search. */
    protected void modifyLogic(ProcessingLogic logic) {}

    protected boolean canOutputItems(ItemStack[] outputs) {
        if (outputs == null || outputs.length == 0) {
            return true;
        }
        List<ItemStack[]> copies = new ArrayList<>();
        for (MTEHatchItems bus : outputBuses) {
            ItemStack[] copy = new ItemStack[bus.inventory.length];
            for (int i = 0; i < copy.length; i++) {
                copy[i] = bus.inventory[i] == null ? null : bus.inventory[i].copy();
            }
            copies.add(copy);
        }
        for (ItemStack output : outputs) {
            if (output == null) {
                continue;
            }
            ItemStack left = output.copy();
            for (ItemStack[] copy : copies) {
                left.stackSize = InventoryUtil.insert(copy, 0, copy.length, left, 64, false);
                if (left.stackSize <= 0) {
                    break;
                }
            }
            if (left.stackSize > 0) {
                return false;
            }
        }
        return true;
    }

    protected boolean canOutputFluids(FluidStack[] outputs) {
        if (outputs == null) {
            return true;
        }
        for (FluidStack fluid : outputs) {
            if (fluid == null) {
                continue;
            }
            boolean fits = false;
            for (MTEHatchFluid hatch : outputHatches) {
                if (hatch.getTank()
                    .fill(fluid, false) >= fluid.amount) {
                    fits = true;
                    break;
                }
            }
            if (!fits) {
                return false;
            }
        }
        return true;
    }

    protected ItemStack[] getRecipeInputItems() {
        List<ItemStack> stacks = new ArrayList<>();
        for (MTEHatchItems bus : inputBuses) {
            if (!bus.isValid()) {
                continue;
            }
            for (ItemStack stack : bus.inventory) {
                if (stack != null) {
                    stacks.add(stack);
                }
            }
        }
        return stacks.toArray(new ItemStack[0]);
    }

    protected FluidStack[] getRecipeInputFluids() {
        List<FluidStack> fluids = new ArrayList<>();
        for (MTEHatchFluid hatch : inputHatches) {
            if (hatch.isValid() && hatch.getFluid() != null) {
                fluids.add(hatch.getFluid());
            }
        }
        return fluids.toArray(new FluidStack[0]);
    }

    /**
     * Searches a recipe and starts it. Must set {@link #maxProgress}, {@link #eut} and the pending outputs on
     * success. Override for multiblocks with custom logic (no recipe map).
     */
    protected CheckRecipeResult checkProcessing() {
        RecipeMap<?> map = getRecipeMap();
        if (map == null) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        long voltage = isGenerator() ? GTValues.V[GTValues.V.length - 1] : getMaxInputVoltage();
        if (!isGenerator() && voltage <= 0) {
            return SimpleCheckRecipeResult.ofFailure("no_energy_hatch");
        }
        ProcessingLogic logic = getProcessingLogic();
        logic.clear();
        logic.setRecipeMap(map)
            .setInputItems(getRecipeInputItems())
            .setInputFluids(getRecipeInputFluids())
            .setAvailableVoltage(voltage)
            .setAvailableAmperage(Math.max(1, getMaxInputAmperage()))
            .setMaxParallel(getMaxParallel());
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
        for (MTEHatchItems bus : inputBuses) {
            InventoryUtil.cleanup(bus.inventory);
        }
        for (MTEHatchFluid hatch : inputHatches) {
            if (hatch.getFluid() != null && hatch.getFluid().amount <= 0) {
                hatch.getTank()
                    .setFluid(null);
            }
        }
    }

    protected void outputProducts() {
        if (pendingItemOutputs != null) {
            for (ItemStack stack : pendingItemOutputs) {
                if (stack == null) {
                    continue;
                }
                ItemStack left = stack.copy();
                for (MTEHatchItems bus : outputBuses) {
                    left.stackSize = bus.insert(left, false);
                    if (left.stackSize <= 0) {
                        break;
                    }
                }
            }
        }
        if (pendingFluidOutputs != null) {
            for (FluidStack fluid : pendingFluidOutputs) {
                if (fluid == null) {
                    continue;
                }
                FluidStack left = fluid.copy();
                for (MTEHatchFluid hatch : outputHatches) {
                    left.amount -= hatch.fillFromMachine(left, true);
                    if (left.amount <= 0) {
                        break;
                    }
                }
            }
        }
        pendingItemOutputs = null;
        pendingFluidOutputs = null;
    }

    @Override
    public void onPostTick(IGTE gte, long tick) {
        super.onPostTick(gte, tick);
        if (!gte.isServerSide()) {
            return;
        }

        if (structureChanged || tick % 100 == 0) {
            structureChanged = false;
            clearStructure();
            boolean formed = checkMachine();
            if (formed != structureFormed) {
                structureFormed = formed;
                markDirty();
            }
        }

        if (!structureFormed) {
            if (maxProgress > 0) {
                progress = 0;
            }
            gte.setActive(false);
            return;
        }

        if (maxProgress > 0) {
            boolean powered;
            if (isGenerator()) {
                addEnergyToDynamos(eut);
                powered = true;
            } else {
                powered = eut <= 0 || drainEnergy(eut);
            }
            if (powered) {
                progress++;
                gte.setActive(true);
                onWorkTick();
                if (progress >= maxProgress) {
                    outputProducts();
                    progress = 0;
                    maxProgress = 0;
                    eut = 0;
                    onRecipeFinished();
                    markDirty();
                }
            } else {
                progress = 0;
                gte.setActive(false);
            }
        }

        if (maxProgress <= 0) {
            if (gte.isAllowedToWork() && tick % 20 == 0) {
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
    }

    @Override
    public int getProgressTime() {
        return progress;
    }

    @Override
    public int maxProgressTime() {
        return maxProgress;
    }

    public CheckRecipeResult getLastResult() {
        return lastResult;
    }

    // endregion

    // region Misc MTE

    @Override
    public boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return facing.offsetY == 0;
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
    public ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel) {
        ITexture casing = CasingTextures.get(getCasingTextureId());
        if (casing == null) {
            casing = TextureFactory.of(Textures.MISSING);
        }
        ITexture overlay = side == facing ? getOverlay().get(side, facing, active) : null;
        return overlay == null ? new ITexture[] { casing } : new ITexture[] { casing, overlay };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        List<String> info = new ArrayList<>();
        info.add(structureFormed ? "Structure: formed" : "Structure: incomplete");
        info.add("Progress: " + progress / 20 + " / " + maxProgress / 20 + " s");
        info.add((isGenerator() ? "Producing: " : "Using: ") + eut + " EU/t");
        info.add("Energy: " + getStoredEnergy() + " EU");
        if (!lastResult.wasSuccessful() && maxProgress <= 0) {
            info.add(lastResult.getDisplayString());
        }
        return info.toArray(new String[0]);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt.setInteger("progress", progress);
        nbt.setInteger("maxProgress", maxProgress);
        nbt.setLong("eut", eut);
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

    // endregion

    // region GUI

    @Override
    protected boolean useMui2() {
        return true;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = GTGuis.mteTemplatePanelBuilder(this, data, syncManager, uiSettings)
            .build();
        StringSyncValue status = new StringSyncValue(() -> String.join("\n", getInfoData()), value -> {});
        syncManager.syncValue("multiblock_status", 0, status);
        panel.child(
            IKey.dynamic(status::getStringValue)
                .asWidget()
                .pos(8, 8)
                .size(160, 70));
        return panel;
    }

    // endregion

    /** Formats a voltage like "512 EU/t (HV)". */
    protected static String voltageText(long voltage) {
        return voltage + " EU/t (" + GTValues.VN[GTUtility.getTier(voltage)] + ")";
    }
}
