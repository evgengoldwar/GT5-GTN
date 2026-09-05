package gregtech.nei;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import codechicken.lib.config.ConfigTagParent;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.SearchField;
import codechicken.nei.SearchField.SearchParserProvider;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.config.OptionCycled;
import codechicken.nei.event.NEIRegisterHandlerInfosEvent;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.HandlerInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.recipe.RecipeCategory;
import gregtech.api.recipe.RecipeMap;
import gregtech.nei.searchprovider.ChemicalFormulaFilter;

public class NEIGTConfig implements IConfigureNEI {

    private static final ImmutableMap<RecipeMap<?>, Integer> RECIPE_MAP_ORDERING = ImmutableMap
        .<RecipeMap<?>, Integer>builder()
        .build();

    private static final Comparator<GTNEIDefaultHandler> RECIPE_MAP_HANDLER_COMPARATOR = Comparator
        .comparingInt(handler -> RECIPE_MAP_ORDERING.getOrDefault(handler.getRecipeMap(), 0));

    private static ListMultimap<RecipeCategory, RecipeMapWorkable> RECIPE_CATALYST_INDEX;

    private static final GTNEIMacerationStackConversion MACERATOR_UPGRADE_HANDLER = new GTNEIMacerationStackConversion();

    public static boolean sIsAdded = true;

    private static void addHandler(TemplateRecipeHandler handler) {
        FMLInterModComms.sendRuntimeMessage(
            GTMod.GT,
            "NEIPlugins",
            "register-crafting-handler",
            "gregtech@" + handler.getRecipeName() + "@" + handler.getOverlayIdentifier());
        GuiCraftingRecipe.craftinghandlers.add(handler);
        GuiUsageRecipe.usagehandlers.add(handler);
    }

    @Override
    public void loadConfig() {
        sIsAdded = false;
        registerHandlers();
        registerCatalysts();
        registerItemEntries();
        registerDumpers();
        registerFilters();
        sIsAdded = true;
    }

    private void registerHandlers() {
        RecipeCategory.ALL_RECIPE_CATEGORIES.values()
            .stream()
            .filter(
                recipeCategory -> recipeCategory.recipeMap.getFrontend()
                    .getNEIProperties().registerNEI)
            .map(GTNEIDefaultHandler::new)
            .sorted(RECIPE_MAP_HANDLER_COMPARATOR)
            .forEach(NEIGTConfig::addHandler);

        GuiCraftingRecipe.craftinghandlers.add(MACERATOR_UPGRADE_HANDLER);
        GuiUsageRecipe.usagehandlers.add(MACERATOR_UPGRADE_HANDLER);
    }

    private void registerCatalysts() {
        for (Map.Entry<RecipeCategory, Collection<RecipeMapWorkable>> entry : RECIPE_CATALYST_INDEX.asMap()
            .entrySet()) {
            entry.getValue()
                .forEach(
                    recipeMapWorkable -> API.addRecipeCatalyst(
                        recipeMapWorkable.getStackForm(1),
                        entry.getKey().unlocalizedName,
                        recipeMapWorkable.getRecipeCatalystPriority()));
        }
    }

    private void registerItemEntries() {}

    private void registerDumpers() {}

    private void registerFilters() {
        API.addSearchProvider(
            new SearchParserProvider('=', "chemicalFormula", EnumChatFormatting.GREEN, ChemicalFormulaFilter::new));

        ConfigTagParent tag = NEIClientConfig.global.config;
        tag.getTag("inventory.search.chemicalFormulaSearchMode")
            .setComment("Search mode for chemical formulas (prefix: =)")
            .getIntValue(1);
        API.addOption(new OptionCycled("inventory.search.chemicalFormulaSearchMode", 3, true) {

            @Override
            public boolean onClick(int button) {
                if (!super.onClick(button)) {
                    return false;
                }
                SearchField.searchParser.clearCache();
                return true;
            }

            @Override
            public String getButtonText() {
                return translateN(
                    name + "." + value(),
                    EnumChatFormatting.GREEN + String.valueOf(SearchField.searchParser.getRedefinedPrefix('=')));
            }

            @Override
            public boolean isEnabled() {
                return !tag.getTag("inventory.search.format")
                    .getBooleanValue();
            }

        });
    }

    @SubscribeEvent
    public void registerHandlerInfo(NEIRegisterHandlerInfosEvent event) {
        if (RECIPE_CATALYST_INDEX == null) {
            // This method will be called earlier than #loadConfig
            generateRecipeCatalystIndex();
        }
        RecipeCategory.ALL_RECIPE_CATEGORIES.values()
            .forEach(recipeCategory -> {
                HandlerInfo.Builder builder = createHandlerInfoBuilderTemplate(recipeCategory);

                if (recipeCategory.handlerInfoCreator != null) {
                    builder = recipeCategory.handlerInfoCreator.apply(builder);
                }

                event.registerHandlerInfo(builder.build());
            });

    }

    private HandlerInfo.Builder createHandlerInfoBuilderTemplate(RecipeCategory recipeCategory) {
        // Infer icon from recipe catalysts
        final RecipeMapWorkable catalyst = RECIPE_CATALYST_INDEX.get(recipeCategory)
            .stream()
            .findFirst()
            .orElse(null);

        return new HandlerInfo.Builder(
            recipeCategory.unlocalizedName,
            recipeCategory.ownerMod.getName(),
            recipeCategory.ownerMod.getModId()).setShiftY(8)
                .setHeight(135)
                .setShowBadge(true)
                .setDisplayStack(catalyst != null ? catalyst.getStackForm(1) : null)
                .setMultipleWidgetsAllowed(true);
    }

    private static void generateRecipeCatalystIndex() {
        ImmutableListMultimap.Builder<RecipeCategory, RecipeMapWorkable> builder = new ImmutableListMultimap.Builder<>();
        builder
            .orderValuesBy(Comparator.comparing(recipeMapWorkable -> -recipeMapWorkable.getRecipeCatalystPriority()));
        for (int i = 1; i < GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES.length; i++) {
            IMetaTileEntity mte = GregTechAPI.MULTIBLOCK_HATCH_META_TILE_ENTITIES[i];
            if (!(mte instanceof RecipeMapWorkable recipeMapWorkable)) continue;
            for (RecipeMap<?> recipeMap : recipeMapWorkable.getAvailableRecipeMaps()) {
                for (RecipeCategory recipeCategory : recipeMap.getAssociatedCategories()) {
                    builder.put(recipeCategory, recipeMapWorkable);
                }
            }
        }
        RECIPE_CATALYST_INDEX = builder.build();
    }

    @Override
    public String getName() {
        return "GregTech NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "(5.03a)";
    }
}
