package rikuto.larger_workbenches.plugin.nei;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import rikuto.larger_workbenches.crafting.LargeShapedRecipe;

public class LargeShapedRecipeHandlerBase extends ShapedRecipeHandler {

	public class CachedLargeShapedRecipeBase extends CachedRecipe {

		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;

		public CachedLargeShapedRecipeBase(int width, int height, Object[] items, ItemStack out) {
			ingredients = new ArrayList<PositionedStack>();
		}

		public CachedLargeShapedRecipeBase(LargeShapedRecipe recipe) {
			this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
		}

		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		public PositionedStack getResult() {
			return result;
		}

		public void computeVisuals() {
			for (PositionedStack p : ingredients)
				p.generatePermutations();
		}
	}

	public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
		List<Class<? extends GuiContainer>> classes = getGuiClasses();
		if (classes != null && !classes.isEmpty()) {
			LinkedList list = new LinkedList();
			for (Class<? extends GuiContainer> clazz : classes)
				list.add(clazz);
			return list;
		}
		return null;
	}

	public List<Class<? extends GuiContainer>> getGuiClasses() {
		return null;
	}

	public int recipiesPerPage() {
		return 1;
	}
}