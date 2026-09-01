package gregtech.nei.formatter;

import static gregtech.api.util.GTUtility.trans;

import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import gregtech.GTMod;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DefaultSpecialValueFormatter implements INEISpecialInfoFormatter {

    public static DefaultSpecialValueFormatter INSTANCE = new DefaultSpecialValueFormatter();

    @Override
    public List<String> format(RecipeDisplayInfo recipeInfo) {
        return Collections.emptyList();
    }
}
