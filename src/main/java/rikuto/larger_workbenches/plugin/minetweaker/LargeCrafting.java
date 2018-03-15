package rikuto.larger_workbenches.plugin.minetweaker;

import java.util.ArrayList;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import rikuto.larger_workbenches.crafting.LargeCraftingManager;
import rikuto.larger_workbenches.crafting.LargeShapedOreRecipe;
import rikuto.larger_workbenches.crafting.LargeShapelessOreRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.larger_workbenches.LargeCrafting")
public class LargeCrafting {

	@ZenMethod
	public static void addShaped(IItemStack output, IIngredient[][] ingredients) {
		addShaped(-1, output, ingredients);
	}

	@ZenMethod
	public static void addShaped(int tier, IItemStack output, IIngredient[][] ingredients) {
		int width = 0;
		int height = ingredients.length;
		for (IIngredient[] row : ingredients) {
			if (row != null && row.length > width)
				width = row.length;
		}
		Object[] input = new Object[width * height];
		int x = 0;
		for (IIngredient[] row : ingredients)
			for (IIngredient ingredient : row)
				input[x++] = toActualObject(ingredient);
		MineTweakerAPI.apply(new Add(new LargeShapedOreRecipe(tier, toStack(output), input, width, height)));
	}

	@ZenMethod
	public static void addShapeless(IItemStack output, IIngredient[] ingredients) {
		addShapeless(-1, output, ingredients);;
	}

	@ZenMethod
	public static void addShapeless(int tier, IItemStack output, IIngredient[] ingredients) {
		MineTweakerAPI.apply(new Add(new LargeShapelessOreRecipe(tier, toStack(output), toObjects(ingredients))));
	}

	private static class Add implements IUndoableAction {

		IRecipe recipe;

		public Add(IRecipe add){
			recipe = add;
		}

		public void apply(){
			LargeCraftingManager.instance.recipes.add(recipe);
		}

		public boolean canUndo(){
			return true;
		}

		public void undo(){
			LargeCraftingManager.instance.recipes.remove(recipe);
		}

		public String describe(){
			return "Adding Large Crafting Recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		public String describeUndo(){
			return "Removing Large Crafting Recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		public Object getOverrideKey() {
			return null;
		}
	}

	@ZenMethod
	public static void remove(IItemStack output){
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		for (Object obj : LargeCraftingManager.instance.recipes) {
			if (obj instanceof IRecipe) {
				IRecipe recipe = (IRecipe)obj;
				if (recipe.getRecipeOutput().isItemEqual(toStack(output)))
					recipes.add(recipe);
			}
		}
		if (!recipes.isEmpty())
			MineTweakerAPI.apply(new Remove(recipes));
	}

	private static class Remove implements IUndoableAction {

		List<IRecipe> recipes = new ArrayList<IRecipe>();

		public Remove(List<IRecipe> remove){
			recipes = remove;
		}

		public void apply(){
			if (!this.recipes.isEmpty())
				for (IRecipe recipe : recipes)
					if (recipe != null)
						LargeCraftingManager.instance.recipes.remove(recipe);
		}

		public boolean canUndo(){
			return !recipes.isEmpty();
		}

		public void undo(){
			if (!this.recipes.isEmpty())
				for (IRecipe recipe : recipes)
					if (recipe != null)
						LargeCraftingManager.instance.recipes.add(recipe);
		}

		public String describe(){
			return "Removing Large Crafting Recipe for " + recipes.get(0).getRecipeOutput().getDisplayName();
		}

		public String describeUndo(){
			return "Restoring Large Crafting Recipe for " + recipes.get(0).getRecipeOutput().getDisplayName();
		}

		public Object getOverrideKey() {
			return null;
		}
	}

	private static ItemStack toStack(IItemStack iStack){
		if (iStack == null)
			return null;
		Object internal = iStack.getInternal();
		if (internal == null || !(internal instanceof ItemStack)) {
			MineTweakerAPI.getLogger().logError("Not a valid item stack: " + iStack);
		}
		return (ItemStack)internal;
	}

	private static Object toObject(IIngredient iIngredient){
		if (iIngredient == null)
			return null;
		if (iIngredient instanceof IOreDictEntry)
			return toString((IOreDictEntry)iIngredient);
		if (iIngredient instanceof IItemStack)
			return toStack((IItemStack)iIngredient);
		return null;
	}

	private static Object[] toObjects(IIngredient[] iIngredients){
		if (iIngredients == null)
			return null;
		Object[] ingredients = new Object[iIngredients.length];
		for (int i = 0; i < iIngredients.length; i++)
			if (iIngredients[i] != null)
				ingredients[i] = toObject(iIngredients[i]);
			else
				ingredients[i] = "";
		return ingredients;
	}

	private static Object toActualObject(IIngredient ingredient){
		if (ingredient == null)
			return null;
		if (ingredient instanceof IOreDictEntry)
			return OreDictionary.getOres(toString((IOreDictEntry) ingredient));
		if (ingredient instanceof IItemStack)
			return toStack((IItemStack)ingredient);
		return null;
	}

	private static String toString(IOreDictEntry entry) {
		return ((IOreDictEntry)entry).getName();
	}
}