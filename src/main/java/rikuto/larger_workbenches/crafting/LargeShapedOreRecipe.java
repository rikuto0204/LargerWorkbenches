package rikuto.larger_workbenches.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class LargeShapedOreRecipe implements IRecipe {

	private ItemStack output = null;
	private Object[] input = null;
	public int width = 0;
	public int height = 0;
	private boolean mirrored = true;
	public int tier;

	public LargeShapedOreRecipe(int tier, ItemStack result, Object[] ingredients, int width, int height){
		this.tier = tier;
		this.width = width;
		this.height = height;
		output = result;
		input = ingredients;
	}

	public LargeShapedOreRecipe(int tier, ItemStack result, Object... recipe) {
		this.tier = tier;

		output = result.copy();
		String shape = "";
		int idx = 0;
		if (recipe[idx] instanceof Boolean) {
			mirrored = (Boolean)recipe[idx];
			if (recipe[idx + 1] instanceof Object[])
				recipe = (Object[])recipe[idx + 1];
			else
				idx = 1;
		}
		if (recipe[idx] instanceof String[]) {
			String[] parts = (String[])recipe[idx++];
			for (String s : parts) {
				width = s.length();
				shape += s;
			}
			height = parts.length;
		}
		else {
			while (recipe[idx] instanceof String) {
				String s = (String)recipe[idx++];
				shape += s;
				width = s.length();
				height++;
			}
		}
		if (width * height != shape.length()) {
			String ret = "Invalid shaped ore recipe: ";
			for (Object tmp :  recipe)
				ret += tmp + ", ";
			ret += output;
			throw new RuntimeException(ret);
		}
		HashMap<Character, Object> itemMap = new HashMap<Character, Object>();
		for (; idx < recipe.length; idx += 2) {
			Character character = (Character)recipe[idx];
			Object object = recipe[idx + 1];
			if (object instanceof ItemStack)
				itemMap.put(character, ((ItemStack)object).copy());
			else if (object instanceof Item)
				itemMap.put(character, new ItemStack((Item)object));
			else if (object instanceof Block)
				itemMap.put(character, new ItemStack((Block)object, 1, OreDictionary.WILDCARD_VALUE));
			else if (object instanceof String)
				itemMap.put(character, OreDictionary.getOres((String)object));
			else {
				String ret = "Invalid shaped ore recipe: ";
				for (Object tmp :  recipe)
					ret += tmp + ", ";
				ret += output;
				throw new RuntimeException(ret);
			}
		}
		input = new Object[width * height];
		int x = 0;
		for (char c : shape.toCharArray())
			input[x++] = itemMap.get(c);
	}

	public ItemStack getCraftingResult(InventoryCrafting var1){
		return output.copy();
	}

	public int getRecipeSize(){
		return input.length;
	}

	public ItemStack getRecipeOutput(){
		return output;
	}

	public boolean matches(InventoryCrafting matrix, World world) {
		if (tier != -1 && tier != LargeShapedRecipe.getTierFromSize(matrix.getSizeInventory()))
			return false;
		for (int x = 0; x <= matrix.getWidth() - width; x++) {
			for (int y = 0; y <= matrix.getHeight() - height; ++y) {
				if (checkMatch(matrix, x, y, false))
					return true;
				if (mirrored && checkMatch(matrix, x, y, true))
					return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean checkMatch(InventoryCrafting matrix, int startX, int startY, boolean mirror) {
		for (int x = 0; x < matrix.getWidth(); x++) {
			for (int y = 0; y < matrix.getHeight(); y++) {
				int subX = x - startX;
				int subY = y - startY;
				Object target = null;
				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					if (mirror)
						target = input[width - subX - 1 + subY * width];
					else
						target = input[subX + subY * width];
				}
				ItemStack slot = matrix.getStackInRowAndColumn(x, y);
				if (slot == null && target != null || slot != null && target == null)
					return false;
				if (target instanceof ItemStack) {
					ItemStack itemStack = (ItemStack)target;
					if (itemStack.getItem() != slot.getItem())
						return false;
					if (itemStack.getItemDamage() != 32767 && itemStack.getItemDamage() != slot.getItemDamage())
						return false;
					if (itemStack.hasTagCompound() &&  !ItemStack.areItemStackTagsEqual(itemStack, slot))
						return false;
					if (!OreDictionary.itemMatches((ItemStack)target, slot, false))
						return false;
				}
				else if (target instanceof ArrayList) {
					boolean matched = false;
					Iterator<ItemStack> iterator = ((ArrayList<ItemStack>)target).iterator();
					while (iterator.hasNext() && !matched)
						matched = OreDictionary.itemMatches(iterator.next(), slot, false);
					if (!matched)
						return false;
				}
			}
		}
		return true;
	}

	public LargeShapedOreRecipe setMirrored(boolean mirror) {
		mirrored = mirror;
		return this;
	}

	public Object[] getInput() {
		return input;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting matrix) {
		return ForgeHooks.defaultRecipeGetRemainingItems(matrix);
	}
}