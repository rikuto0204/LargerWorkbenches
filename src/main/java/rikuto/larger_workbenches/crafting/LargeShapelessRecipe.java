package rikuto.larger_workbenches.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class LargeShapelessRecipe implements IRecipe {

	private final ItemStack recipeOutput;
	public final List recipeItems;
	public int tier;

	public LargeShapelessRecipe(int tier, ItemStack result, List ingredients) {
		recipeOutput = result;
		recipeItems = ingredients;
		this.tier = tier;
	}

	public ItemStack getRecipeOutput() {
		return recipeOutput;
	}

	public boolean matches(InventoryCrafting matrix, World world) {
		if (tier != -1 && tier != LargeShapedRecipe.getTierFromSize(matrix.getSizeInventory()))
			return false;
		ArrayList list = new ArrayList(recipeItems);
		for (int i = 0; i < matrix.getSizeInventory(); i++) {
			ItemStack slot = matrix.getStackInSlot(i);
			if (slot != null) {
				boolean flag = false;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					ItemStack itemStack = (ItemStack)iterator.next();
					if (slot.getItem() == itemStack.getItem() && (itemStack.getItemDamage() == 32767 || slot.getItemDamage() == itemStack.getItemDamage())) {
						if (!itemStack.hasTagCompound() || ItemStack.areItemStackTagsEqual(itemStack, slot)) {
							flag = true;
							list.remove(itemStack);
							break;
						}
					}
				}
				if (!flag)
					return false;
			}
		}
		return list.isEmpty();
	}

	public ItemStack getCraftingResult(InventoryCrafting matrix) {
		return recipeOutput.copy();
	}

	public int getRecipeSize() {
		return recipeItems.size();
	}

	public ItemStack[] getRemainingItems(InventoryCrafting matrix) {
		return ForgeHooks.defaultRecipeGetRemainingItems(matrix);
	}
}