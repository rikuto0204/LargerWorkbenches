package rikuto.larger_workbenches.plugin.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
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
import rikuto.larger_workbenches.crafting.LargeShapedOreRecipe;
import rikuto.larger_workbenches.crafting.LargeShapedRecipe;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench7x7;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench7x7;

public class LargeShapedRecipeHandler7x7 extends LargeShapedRecipeHandlerBase {

	public class CachedLargeShapedRecipe7x7 extends CachedLargeShapedRecipeBase {

		public CachedLargeShapedRecipe7x7(int width, int height, Object[] items, ItemStack out) {
			super(width, height, items, out);
			result = new PositionedStack(out, 155, 59);
			setIngredients(width, height, items);
		}

		public void setIngredients(int width, int height, Object[] items) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items[(y * width + x)] != null) {
						PositionedStack positionedStack = new PositionedStack(items[(y * width + x)], -11 + x * 18, 5 + y * 18);
						positionedStack.setMaxSize(1);
						ingredients.add(positionedStack);
					}
				}
			}
		}

		public CachedLargeShapedRecipe7x7(LargeShapedRecipe recipe) {
			this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
		}
	}

	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(120, 58, 24, 18), "large_crafting.tier3"));
	}

	public List<Class<? extends GuiContainer>> getGuiClasses() {
		List<Class<? extends GuiContainer>> classes = new ArrayList();
		classes.add(GuiLargeWorkbench7x7.class);
		classes.add(GuiAutoLargeWorkbench7x7.class);
		return classes;
	}

	public String getRecipeName() {
		return StatCollector.translateToLocal("crafting.large.tier3");
	}

	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("large_crafting.tier3") && getClass() == LargeShapedRecipeHandler7x7.class) {
			for (IRecipe iRecipe : (List<IRecipe>)LargeCraftingManager.instance.recipes) {
				CachedLargeShapedRecipe7x7 recipe = null;
				if (iRecipe instanceof LargeShapedRecipe && (((LargeShapedRecipe)iRecipe).tier == -1 || ((LargeShapedRecipe)iRecipe).tier == 3) && ((LargeShapedRecipe)iRecipe).recipeWidth <= 7 && ((LargeShapedRecipe)iRecipe).recipeHeight <= 7)
					recipe = new CachedLargeShapedRecipe7x7((LargeShapedRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapedOreRecipe && (((LargeShapedOreRecipe)iRecipe).tier == -1 || ((LargeShapedOreRecipe)iRecipe).tier == 3) && ((LargeShapedOreRecipe)iRecipe).width <= 7 && ((LargeShapedOreRecipe)iRecipe).height <= 7)
					recipe = forgeLargeShapedRecipe((LargeShapedOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				recipe.computeVisuals();
				arecipes.add(recipe);
			}
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	public void loadCraftingRecipes(ItemStack result) {
		for (IRecipe iRecipe : (List<IRecipe>)LargeCraftingManager.instance.recipes) {
			if (NEIServerUtils.areStacksSameTypeCrafting(iRecipe.getRecipeOutput(), result)) {
				CachedLargeShapedRecipe7x7 recipe = null;
				if (iRecipe instanceof LargeShapedRecipe && (((LargeShapedRecipe)iRecipe).tier == -1 || ((LargeShapedRecipe)iRecipe).tier == 3) && ((LargeShapedRecipe)iRecipe).recipeWidth <= 7 && ((LargeShapedRecipe)iRecipe).recipeHeight <= 7)
					recipe = new CachedLargeShapedRecipe7x7((LargeShapedRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapedOreRecipe && (((LargeShapedOreRecipe)iRecipe).tier == -1 || ((LargeShapedOreRecipe)iRecipe).tier == 3) && ((LargeShapedOreRecipe)iRecipe).width <= 7 && ((LargeShapedOreRecipe)iRecipe).height <= 7)
					recipe = forgeLargeShapedRecipe((LargeShapedOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				recipe.computeVisuals();
				arecipes.add(recipe);
			}
		}
	}

	public void loadUsageRecipes(ItemStack ingredient) {
		for (IRecipe iRecipe : (List<IRecipe>)LargeCraftingManager.instance.recipes) {
			CachedLargeShapedRecipe7x7 recipe = null;
			if (iRecipe instanceof LargeShapedRecipe && (((LargeShapedRecipe)iRecipe).tier == -1 || ((LargeShapedRecipe)iRecipe).tier == 3) && ((LargeShapedRecipe)iRecipe).recipeWidth <= 7 && ((LargeShapedRecipe)iRecipe).recipeHeight <= 7)
				recipe = new CachedLargeShapedRecipe7x7((LargeShapedRecipe)iRecipe);
			else if (iRecipe instanceof LargeShapedOreRecipe && (((LargeShapedOreRecipe)iRecipe).tier == -1 || ((LargeShapedOreRecipe)iRecipe).tier == 3) && ((LargeShapedOreRecipe)iRecipe).width <= 7 && ((LargeShapedOreRecipe)iRecipe).height <= 7)
				recipe = forgeLargeShapedRecipe((LargeShapedOreRecipe)iRecipe);
			if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem()))
				continue;
			recipe.computeVisuals();
			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	public CachedLargeShapedRecipe7x7 forgeLargeShapedRecipe(LargeShapedOreRecipe recipe) {
		int width = recipe.width;
		int height = recipe.height;
		Object[] items = recipe.getInput();
		for (Object item : items)
			if (item instanceof List && ((List<?>)item).isEmpty())
				return null;
		return new CachedLargeShapedRecipe7x7(width, height, items, recipe.getRecipeOutput());
	}

	public String getOverlayIdentifier() {
		return "large_crafting.tier3";
	}

	public String getGuiTexture() {
		return "largerworkbenches:textures/gui/workbench7x7_gui_nei.png";
	}

	public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
		return RecipeInfo.hasDefaultOverlay(gui, "large_crafting.tier3");
	}

	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-23, -16, 0, 0, 212, 166);
	}
}