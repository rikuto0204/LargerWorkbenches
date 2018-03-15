package rikuto.larger_workbenches.plugin.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import rikuto.larger_workbenches.crafting.LargeCraftingManager;
import rikuto.larger_workbenches.crafting.LargeShapelessOreRecipe;
import rikuto.larger_workbenches.crafting.LargeShapelessRecipe;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench6x6;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench6x6;

public class LargeShapelessRecipeHandler6x6 extends LargeShapelessRecipeHandlerBase {

	public class CachedLargeShapelessRecipe6x6 extends CachedLargeShapelessRecipeBase {

		public CachedLargeShapelessRecipe6x6(ItemStack output) {
			super();
			result = new PositionedStack(output, 146, 59);
		}

		public CachedLargeShapelessRecipe6x6(Object[] input, ItemStack output) {
			this(Arrays.asList(input), output);
		}

		public CachedLargeShapelessRecipe6x6(List<?> input, ItemStack output) {
			this(output);
			setIngredients(input);
		}

		public void setIngredients(List<?> items) {
			ingredients.clear();
			for (int i = 0; i < items.size(); i++) {
				PositionedStack positionedStack = new PositionedStack(items.get(i), -2 + (i % 6) * 18, 14 + (i / 6) * 18);
				positionedStack.setMaxSize(1);
				ingredients.add(positionedStack);
			}
		}
	}

	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(111, 58, 24, 18), "large_crafting.tier2"));
	}

	public List<Class<? extends GuiContainer>> getGuiClasses() {
		List<Class<? extends GuiContainer>> classes = new ArrayList();
		classes.add(GuiLargeWorkbench6x6.class);
		classes.add(GuiAutoLargeWorkbench6x6.class);
		return classes;
	}

	public String getRecipeName() {
		return StatCollector.translateToLocal("crafting.large.shapeless.tier2");
	}

	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("large_crafting.tier2") && getClass() == LargeShapelessRecipeHandler6x6.class) {
			for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
				CachedLargeShapelessRecipe6x6 recipe = null;
				if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 2) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 36)
					recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 2) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 36)
					recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				arecipes.add(recipe);
			}
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	public void loadCraftingRecipes(ItemStack result) {
		for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
			if (NEIServerUtils.areStacksSameTypeCrafting(iRecipe.getRecipeOutput(), result)) {
				CachedLargeShapelessRecipe6x6 recipe = null;
				if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 2) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 36)
					recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 2) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 36)
					recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				arecipes.add(recipe);
			}
		}
	}

	public void loadUsageRecipes(ItemStack ingredient) {
		for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
			CachedLargeShapelessRecipe6x6 recipe = null;
			if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 2) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 36)
				recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
			else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 2) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 36)
				recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
			if (recipe == null)
				continue;
			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	private CachedLargeShapelessRecipe6x6 largeShapelessRecipe(LargeShapelessRecipe recipe) {
		if(recipe.recipeItems == null)
			return null;
		return new CachedLargeShapelessRecipe6x6(recipe.recipeItems, recipe.getRecipeOutput());
	}

	public CachedLargeShapelessRecipe6x6 forgeLargeShapelessRecipe(LargeShapelessOreRecipe recipe) {
		ArrayList<Object> items = recipe.getInput();
		for (Object item : items)
			if (item instanceof List && ((List<?>)item).isEmpty())
				return null;
		return new CachedLargeShapelessRecipe6x6(items, recipe.getRecipeOutput());
	}

	public String getOverlayIdentifier() {
		return "large_crafting.tier2";
	}

	public String getGuiTexture() {
		return "largerworkbenches:textures/gui/workbench6x6_gui_nei.png";
	}

	public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
		return RecipeInfo.hasDefaultOverlay(gui, "large_crafting.tier2");
	}

	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-14, -16, 0, 0, 194, 166);
	}
}