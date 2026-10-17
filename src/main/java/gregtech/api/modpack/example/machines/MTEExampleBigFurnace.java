package gregtech.api.modpack.example.machines;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureUtility;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.modpack.example.ExampleMaterials;
import gregtech.api.modpack.example.ExampleModule;
import gregtech.api.modpack.hatch.CasingTextures;
import gregtech.api.modpack.hatch.HatchType;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.modpack.multiblock.MTEMultiblockBase;
import gregtech.api.modpack.multiblock.MultiblockElements;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.MultiblockTooltipBuilder;

/**
 * Multiblock example: 3x3x3 of iron casings with glass on the sides, 8 parallels, faster with more hatches.
 */
public class MTEExampleBigFurnace extends MTEMultiblockBase {

    // static fields are initialized when the class loads during pre-init, the same way on client and server
    private static final int CASING_TEXTURE = CasingTextures
        .register(ExampleMaterials.Iron.getCasing(), ExampleMaterials.Iron.getMeta());
    private static final MachineOverlay OVERLAY = MachineOverlay.of("machines/big_furnace");

    // spotless:off
    private static final IStructureDefinition<MTEMultiblockBase> STRUCTURE = StructureDefinition
        .<MTEMultiblockBase>builder()
        .addShape(MAIN_PIECE, StructureUtility.transpose(new String[][] {
            { "CCC", "CCC", "CCC" },
            { "H~H", "G-G", "CCC" },
            { "HHH", "HHH", "HHH" } }))
        .addElement('C', MultiblockElements.casing(ExampleMaterials.Iron))
        .addElement('H', MultiblockElements.hatchOrCasing(ExampleMaterials.Iron, HatchType.PROCESSING))
        .addElement('G', MultiblockElements.block(Blocks.glass, 0))
        .build();
    // spotless:on

    public MTEExampleBigFurnace(int id, String name, String localName) {
        super(id, name, localName);
    }

    public MTEExampleBigFurnace(String name) {
        super(name);
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return new MTEExampleBigFurnace(name);
    }

    @Override
    public IStructureDefinition<MTEMultiblockBase> getStructureDefinition() {
        return STRUCTURE;
    }

    @Override
    protected boolean checkMachine() {
        return checkPiece(MAIN_PIECE, 1, 1, 0) && getCasingCount() >= 8
            && !getEnergyHatches().isEmpty()
            && !getInputBuses().isEmpty()
            && !getOutputBuses().isEmpty();
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece(MAIN_PIECE, trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    protected int getCasingTextureId() {
        return CASING_TEXTURE;
    }

    @Override
    protected MachineOverlay getOverlay() {
        return OVERLAY;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return ExampleModule.BIG_FURNACE;
    }

    @Override
    protected int getMaxParallel() {
        return 8;
    }

    @Override
    protected void modifyLogic(ProcessingLogic logic) {
        // 2x speed, plus 10% for every input bus
        logic.setSpeedBonus(1.0 / (2.0 + 0.1 * getInputBuses().size()));
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder().addMachineType("Furnace")
            .addInfo("Smelts 8 items at once")
            .addInfo("+10% speed per input bus")
            .beginStructureBlock(3, 3, 3, true)
            .addController("Front center")
            .addCasingInfoMin("Iron Machine Casing", 8, false)
            .addOtherStructurePart("Glass", "Left and right of the center")
            .addEnergyHatch("Any casing", 1)
            .addInputBus("Any casing", 1)
            .addOutputBus("Any casing", 1)
            .toolTipFinisher();
    }
}
