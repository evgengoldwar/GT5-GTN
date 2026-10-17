package gregtech.api.modpack.multiblock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.modpack.hatch.HatchType;
import gregtech.api.modpack.machine.MachineOverlay;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.util.GTRecipe;

/**
 * Multiblock whose structure, recipe map and bonuses come from a {@link MultiblockConfig} (see
 * {@link MultiblockBuilder}). Can be extended for custom logic: {@code .factory(MyMultiblock::new)}.
 */
public class MTEConfigurableMultiblock extends MTEMultiblockBase {

    protected final MultiblockConfig config;

    public MTEConfigurableMultiblock(MultiblockConfig config) {
        super(config.id);
        this.config = config;
        this.generator = config.generator;
    }

    @Override
    public IMTE newMTE(IGTE gte) {
        return config.factory.create(config);
    }

    public MultiblockConfig getConfig() {
        return config;
    }

    @Override
    public IStructureDefinition<MTEMultiblockBase> getStructureDefinition() {
        return config.structure;
    }

    @Override
    protected boolean checkStructure() {
        if (!checkPiece(MAIN_PIECE, config.offsetH, config.offsetV, config.offsetD)) {
            return false;
        }
        if (getCasingCount() < config.minCasings) {
            return false;
        }
        for (HatchType type : config.requiredHatches) {
            boolean present = switch (type) {
                case INPUT_BUS -> !inputBuses.isEmpty();
                case OUTPUT_BUS -> !outputBuses.isEmpty();
                case INPUT_HATCH -> !inputHatches.isEmpty();
                case OUTPUT_HATCH -> !outputHatches.isEmpty();
                case ENERGY -> !energyHatches.isEmpty();
                case DYNAMO -> !dynamoHatches.isEmpty();
            };
            if (!present) {
                return false;
            }
        }
        return config.logic.checkStructure(this);
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece(MAIN_PIECE, trigger, hintsOnly, config.offsetH, config.offsetV, config.offsetD);
    }

    @Override
    protected int getCasingTextureId() {
        return config.casingTextureId;
    }

    @Override
    protected MachineOverlay getOverlay() {
        return config.overlay;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return config.recipeMap;
    }

    @Override
    protected int getMaxParallel() {
        return config.maxParallel;
    }

    @Override
    protected void modifyLogic(ProcessingLogic logic) {
        logic.setSpeedBonus(config.speedBonus);
        logic.setEuModifier(config.euModifier);
        if (config.perfectOverclock) {
            logic.enablePerfectOverclock();
        }
        config.logic.modifyLogic(this, logic);
    }

    @Override
    protected CheckRecipeResult validateRecipe(GTRecipe recipe) {
        return config.logic.validateRecipe(this, recipe);
    }

    @Override
    protected void onRecipeStart(GTRecipe recipe) {
        config.logic.onRecipeStart(this, recipe);
    }

    @Override
    protected void onRecipeFinished() {
        config.logic.onRecipeFinish(this);
    }

    @Override
    protected void onWorkTick() {
        config.logic.onWorkTick(this);
    }

    @Override
    public String[] getInfoData() {
        List<String> info = new ArrayList<>(Arrays.asList(super.getInfoData()));
        config.logic.addInfo(this, info);
        return info.toArray(new String[0]);
    }

    @Override
    public String[] getDescription() {
        List<String> lines = new ArrayList<>(config.description);
        if (config.maxParallel > 1) {
            lines.add("Parallel: " + config.maxParallel);
        }
        if (config.minCasings > 0) {
            lines.add("Min casings: " + config.minCasings);
        }
        if (!config.requiredHatches.isEmpty()) {
            StringBuilder required = new StringBuilder("Requires: ");
            for (HatchType type : config.requiredHatches) {
                required.append(type.localName)
                    .append(", ");
            }
            lines.add(required.substring(0, required.length() - 2));
        }
        return lines.toArray(new String[0]);
    }
}
