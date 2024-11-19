package folk.sisby.tinkerers_smithing;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

import java.util.Map;

public class TinkerersSmithingKubeJSPlugin extends KubeJSPlugin {

	public void injectRuntimeRecipes(RecipesEventJS event, RecipeManager manager, Map<Identifier, Recipe<?>> recipesByName) {
		TinkerersSmithing.generateSmithingData(recipesByName);
		int manualRecipes = 0;
		for (Recipe<?> recipe : TinkerersSmithingLoader.INSTANCE.RECIPES) {
			if (!recipesByName.containsKey(recipe.getId())) {
				recipesByName.put(recipe.getId(), recipe);
			} else {
				manualRecipes++;
			}
		}
		TinkerersSmithing.LOGGER.info("[Tinkerer's Smithing] Added {} runtime recipes with {} data overrides!", TinkerersSmithingLoader.INSTANCE.RECIPES.size(), manualRecipes);
	}
}
