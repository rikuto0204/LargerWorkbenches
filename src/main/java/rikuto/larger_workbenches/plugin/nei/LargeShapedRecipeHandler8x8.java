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
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench8x8;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench8x8;

public class LargeShapedRecipeHandler8x8 extends LargeShapedRecipeHandlerBase {

	public class CachedLargeShapedRecipe8x8 extends CachedLargeShapedRecipeBase {

		public CachedLargeShapedRecipe8x8(int width, int height, Object[] items, ItemStack out) {
			super(width, height, items, out);
			result = new PositionedStack(out, 164, 64);
			setIngredients(width, height, items);
		}

		public void setIngredients(int width, int height, Object[] items) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items[(y * width + x)] != null) {
						PositionedStack positionedStack = new PositionedStack(items[(y * width + x)], -20 + x * 18, 1 + y * 18);
						positionedStack.setMaxSize(1);
						ingredients.add(positionedStack);
					}
				}
			}
		}

		public CachedLargeShapedRecipe8x8(LargeShapedRecipe recipe) {
			this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
		}
	}

	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(129, 63, 24, 18), "large_crafting.tier4"));
	}

	public List<Class<? extends GuiContainer>> getGuiClasses() {
		List<Class<? extends GuiContainer>> classes = new ArrayList();
		classes.add(GuiLargeWorkbench8x8.class);
		classes.add(GuiAutoLargeWorkbench8x8.class);
		return classes;
	}

	public String getRecipeName() {
		return StatCollector.translateToLocal("crafting.large.tier4");
	}

	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("large_crafting.tier4") && getClass() == LargeShapedRecipeHandler8x8.class) {
			for (IRecipe iRecipe : (List<IRecipe>)LargeCraftingManager.instance.recipes) {
				CachedLargeShapedRecipe8x8 recipe = null;
				if (iRecipe instanceof LargeShapedRecipe && (((LargeShapedRecipe)iRecipe).tier == -1 || ((LargeShapedRecipe)iRecipe).tier == 4) && ((LargeShapedRecipe)iRecipe).recipeWidth <= 8 && ((LargeShapedRecipe)iRecipe).recipeHeight <= 8)
					recipe = new CachedLargeShapedRecipe8x8((LargeShapedRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapedOreRecipe && (((LargeShapedOreRecipe)iRecipe).tier == -1 || ((LargeShapedOreRecipe)iRecipe).tier == 4) && ((LargeShapedOreRecipe)iRecipe).width <= 8 && ((LargeShapedOreRecipe)iRecipe).height <= 8)
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
				CachedLargeShapedRecipe8x8 recipe = null;
				if (iRecipe instanceof LargeShapedRecipe && (((LargeShapedRecipe)iRecipe).tier == -1 || ((LargeShapedRecipe)iRecipe).tier == 4) && ((LargeShapedRecipe)iRecipe).recipeWidth <= 8 && ((LargeShapedRecipe)iRecipe).recipeHeight <= 8)
					recipe = new CachedLargeShapedRecipe8x8((LargeShapedRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapedOreRecipe && (((LargeShapedOreRecipe)iRecipe).tier == -1 || ((LargeShapedOreRecipe)iRecipe).tier == 4) && ((LargeShapedOreRecipe)iRecipe).width <= 8 && ((LargeShapedOreRecipe)iRecipe).height <= 8)
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
			CachedLargeShapedRecipe8x8 recipe = null;
			if (iRecipe instanceof LargeShapedRecipe && (((LargeShapedRecipe)iRecipe).tier == -1 || ((LargeShapedRecipe)iRecipe).tier == 4) && ((LargeShapedRecipe)iRecipe).recipeWidth <= 8 && ((LargeShapedRecipe)iRecipe).recipeHeight <= 8)
				recipe = new CachedLargeShapedRecipe8x8((LargeShapedRecipe)iRecipe);
			else if (iRecipe instanceof LargeShapedOreRecipe && (((LargeShapedOreRecipe)iRecipe).tier == -1 || ((LargeShapedOreRecipe)iRecipe).tier == 4) && ((LargeShapedOreRecipe)iRecipe).width <= 8 && ((LargeShapedOreRecipe)iRecipe).height <= 8)
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

	public CachedLargeShapedRecipe8x8 forgeLargeShapedRecipe(LargeShapedOreRecipe recipe) {
		int width = recipe.width;
		int height = recipe.height;
		Object[] items = recipe.getInput();
		for (Object item : items)
			if (item instanceof List && ((List<?>)item).isEmpty())
				return null;
		return new CachedLargeShapedRecipe8x8(width, height, items, recipe.getRecipeOutput());
	}

	public String getOverlayIdentifier() {
		return "large_crafting.tier4";
	}

	public String getGuiTexture() {
		return "largerworkbenches:textures/gui/workbench8x8_gui_nei.png";
	}

	public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
		return RecipeInfo.hasDefaultOverlay(gui, "large_crafting.tier4");
	}

	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-32, -16, 0, 0, 230, 176);
	}
}