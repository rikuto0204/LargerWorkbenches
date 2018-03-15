package rikuto.larger_workbenches.crafting;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LargeCraftingManager {

	public static LargeCraftingManager instance= new LargeCraftingManager();
	public ArrayList<IRecipe> recipes = new ArrayList<IRecipe>();

	private LargeCraftingManager() {}

	public LargeShapedRecipe addShaped(ItemStack result, Object... recipe) {
		return addShaped(-1, result, recipe);
	}

	public LargeShapedRecipe addShaped(int tier, ItemStack result, Object... recipe) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		if (recipe[i] instanceof String[]) {
			String[] strings = (String[])((String[])recipe[i++]);
			for (int l = 0; l < strings.length; l++) {
				String s1 = strings[l];
				k++;
				j = s1.length();
				s = s + s1;
			}
		}
		else {
			while (recipe[i] instanceof String) {
				String s2 = (String)recipe[i++];
				k++;
				j = s2.length();
				s = s + s2;
			}
		}
		HashMap hashmap;
		for (hashmap = new HashMap(); i < recipe.length; i += 2) {
			Character character = (Character)recipe[i];
			ItemStack itemStack = null;
			if (recipe[i + 1] instanceof Item)
				itemStack = new ItemStack((Item)recipe[i + 1]);
			else if (recipe[i + 1] instanceof Block)
				itemStack = new ItemStack((Block)recipe[i + 1], 1, 32767);
			else if (recipe[i + 1] instanceof ItemStack)
				itemStack = (ItemStack)recipe[i + 1];
			hashmap.put(character, itemStack);
		}
		ItemStack[] itemStacks = new ItemStack[j * k];
		for (int i1 = 0; i1 < j * k; i1++) {
			char c0 = s.charAt(i1);
			if (hashmap.containsKey(Character.valueOf(c0)))
				itemStacks[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
			else
				itemStacks[i1] = null;
		}
		LargeShapedRecipe craft = new LargeShapedRecipe(tier, j, k, itemStacks, result);
		recipes.add(craft);
		return craft;
	}

	public LargeShapedOreRecipe addShapedOreRecipe(ItemStack result, Object... recipe) {
		return addShapedOreRecipe(-1, result, recipe);
	}

	public LargeShapedOreRecipe addShapedOreRecipe(int tier, ItemStack result, Object... recipe){
		LargeShapedOreRecipe craft = new LargeShapedOreRecipe(tier, result, recipe);
		recipes.add(craft);
		return craft;
	}

	public LargeShapelessRecipe addShapeless(ItemStack result, Object... ingredients) {
		return addShapeless(-1, result, ingredients);
	}

	public LargeShapelessRecipe addShapeless(int tier, ItemStack result, Object... ingredients) {
		ArrayList list = new ArrayList();
		Object[] objects = ingredients;
		int i = ingredients.length;
		for (int j = 0; j < i; j++) {
			Object object = objects[j];
			if (object instanceof ItemStack)
				list.add(((ItemStack)object).copy());
			else if (object instanceof Item)
				list.add(new ItemStack((Item)object));
			else {
				if (!(object instanceof Block))
					throw new RuntimeException("Invalid shapeless recipy!");
				list.add(new ItemStack((Block)object));
			}
		}
		LargeShapelessRecipe recipe = new LargeShapelessRecipe(tier, result, list);
		recipes.add(recipe);
		return recipe;
	}

	public LargeShapelessOreRecipe addShapelessOreRecipe(ItemStack result, Object ... ingredients){
		return addShapelessOreRecipe(-1, result, ingredients);
	}

	public LargeShapelessOreRecipe addShapelessOreRecipe(int tier, ItemStack result, Object ... ingredients){
		LargeShapelessOreRecipe recipe = new LargeShapelessOreRecipe(tier, result, ingredients);
		recipes.add(recipe);
		return recipe;
	}

	public ItemStack findMatchingRecipe(InventoryCrafting matrix, World world) {
		int i = 0;
		ItemStack itemStack = null;
		ItemStack itemStack1 = null;
		for (int j = 0; j < matrix.getSizeInventory(); j++) {
			ItemStack slot = matrix.getStackInSlot(j);
			if (slot != null) {
				if (i == 0)
					itemStack = slot;
				if (i == 1)
					itemStack1 = slot;
				i++;
			}
		}
		if (i == 2 && itemStack.getItem() == itemStack1.getItem() && itemStack.stackSize == 1 && itemStack1.stackSize == 1 && itemStack.getItem().isRepairable()) {
			Item item = itemStack.getItem();
			int j1 = item.getMaxDamage() - itemStack.getItemDamage();
			int k = item.getMaxDamage() - itemStack1.getItemDamage();
			int l = j1 + k + item.getMaxDamage() * 5 / 100;
			int i1 = item.getMaxDamage() - l;
			if (i1 < 0)
				i1 = 0;
			return new ItemStack(itemStack.getItem(), 1, i1);
		}
		for (int j = 0; j < recipes.size(); j++) {
			IRecipe iRecipe = (IRecipe)recipes.get(j);
			if (iRecipe.matches(matrix, world))
				return iRecipe.getCraftingResult(matrix);
		}
		return null;
	}
}