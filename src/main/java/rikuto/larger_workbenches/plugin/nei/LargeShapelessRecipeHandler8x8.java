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
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench8x8;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench8x8;

public class LargeShapelessRecipeHandler8x8 extends LargeShapelessRecipeHandlerBase {

	public class CachedLargeShapelessRecipe8x8 extends CachedLargeShapelessRecipeBase {

		public CachedLargeShapelessRecipe8x8(ItemStack output) {
			super();
			result = new PositionedStack(output, 164, 64);
		}

		public CachedLargeShapelessRecipe8x8(Object[] input, ItemStack output) {
			this(Arrays.asList(input), output);
		}

		public CachedLargeShapelessRecipe8x8(List<?> input, ItemStack output) {
			this(output);
			setIngredients(input);
		}

		public void setIngredients(List<?> items) {
			ingredients.clear();
			for (int i = 0; i < items.size(); i++) {
				PositionedStack positionedStack = new PositionedStack(items.get(i), -20 + (i % 8) * 18, 1 + (i / 8) * 18);
				positionedStack.setMaxSize(1);
				ingredients.add(positionedStack);
			}
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
		return StatCollector.translateToLocal("crafting.large.shapeless.tier4");
	}

	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("large_crafting.tier4") && getClass() == LargeShapelessRecipeHandler8x8.class) {
			for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
				CachedLargeShapelessRecipe8x8 recipe = null;
				if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 4) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 64)
					recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 4) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 64)
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
				CachedLargeShapelessRecipe8x8 recipe = null;
				if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 4) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 64)
					recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 4) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 64)
					recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				arecipes.add(recipe);
			}
		}
	}

	public void loadUsageRecipes(ItemStack ingredient) {
		for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
			CachedLargeShapelessRecipe8x8 recipe = null;
			if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 4) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 64)
				recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
			else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 4) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 64)
				recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
			if (recipe == null)
				continue;
			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	private CachedLargeShapelessRecipe8x8 largeShapelessRecipe(LargeShapelessRecipe recipe) {
		if(recipe.recipeItems == null)
			return null;
		return new CachedLargeShapelessRecipe8x8(recipe.recipeItems, recipe.getRecipeOutput());
	}

	public CachedLargeShapelessRecipe8x8 forgeLargeShapelessRecipe(LargeShapelessOreRecipe recipe) {
		ArrayList<Object> items = recipe.getInput();
		for (Object item : items)
			if (item instanceof List && ((List<?>)item).isEmpty())
				return null;
		return new CachedLargeShapelessRecipe8x8(items, recipe.getRecipeOutput());
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