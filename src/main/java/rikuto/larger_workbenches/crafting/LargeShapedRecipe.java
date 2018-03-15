package rikuto.larger_workbenches.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class LargeShapedRecipe implements IRecipe {

	public final int recipeWidth;
	public final int recipeHeight;
	public final ItemStack[] recipeItems;
	private ItemStack recipeOutput;
	public int tier;

	public LargeShapedRecipe(int tier, int width, int height, ItemStack[] ingredients, ItemStack result) {
		recipeWidth = width;
		recipeHeight = height;
		recipeItems = ingredients;
		recipeOutput = result;
		this.tier = tier;
	}

	public ItemStack getRecipeOutput() {
		return recipeOutput;
	}

	public boolean matches(InventoryCrafting matrix, World world) {
		if (tier != -1 && tier != getTierFromSize(matrix.getSizeInventory()))
			return false;
		for (int i = 0; i <= matrix.getWidth() - recipeWidth; ++i) {
			for (int j = 0; j <= matrix.getHeight() - recipeHeight; ++j) {
				if (checkMatch(matrix, i, j, true)) {
					return true;
				}
				if (checkMatch(matrix, i, j, false)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean mirrored) {
		for (int k = 0; k < matrix.getWidth(); k++) {
			for (int l = 0; l < matrix.getHeight(); l++) {
				int i1 = k - x;
				int j1 = l - y;
				ItemStack itemStack = null;
				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight) {
					if (mirrored)
						itemStack = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					else
						itemStack = recipeItems[i1 + j1 * recipeWidth];
				}
				ItemStack slot = matrix.getStackInRowAndColumn(k, l);
				if (slot != null || itemStack != null) {
					if (slot == null && itemStack != null || slot != null && itemStack == null)
						return false;
					if (itemStack.getItem() != slot.getItem())
						return false;
					if (itemStack.getItemDamage() != 32767 && itemStack.getItemDamage() != slot.getItemDamage())
						return false;
					if (itemStack.hasTagCompound() &&  !ItemStack.areItemStackTagsEqual(itemStack, slot))
						return false;
				}
			}
		}
		return true;
	}

	public ItemStack getCraftingResult(InventoryCrafting matrix) {
		return getRecipeOutput().copy();

	}

	public int getRecipeSize() {
		return recipeWidth * recipeHeight;
	}

	public static int getTierFromSize(int size) {
		int tier = size < 65 && size > 49 ? 4 : size < 50 && size > 36 ? 3 : size < 37 && size > 25 ? 2 : size < 26 && size > 16 ? 1 : size < 17 && size > 9 ? 0 : 5;
		return tier;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting matrix) {
		return ForgeHooks.defaultRecipeGetRemainingItems(matrix);
	}
}