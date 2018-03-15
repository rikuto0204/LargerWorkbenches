package rikuto.larger_workbenches.plugin.nei;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;

public class LargeShapelessRecipeHandlerBase extends ShapelessRecipeHandler {

	public class CachedLargeShapelessRecipeBase extends CachedRecipe {

		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;

		public CachedLargeShapelessRecipeBase() {
			ingredients = new ArrayList<PositionedStack>();
		}

		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		public PositionedStack getResult() {
			return result;
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