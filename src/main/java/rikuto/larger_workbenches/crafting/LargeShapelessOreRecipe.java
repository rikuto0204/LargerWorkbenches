package rikuto.larger_workbenches.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LargeShapelessOreRecipe extends ShapelessOreRecipe {

	public int tier;

	public LargeShapelessOreRecipe(int tier, ItemStack result, Object[] recipe) {
		super(result, recipe);
		this.tier = tier;
	}

	public boolean matches(InventoryCrafting matrix, World world) {
		if (tier != -1 && tier != LargeShapedRecipe.getTierFromSize(matrix.getSizeInventory()))
			return false;
		return super.matches(matrix, world);
	}
}