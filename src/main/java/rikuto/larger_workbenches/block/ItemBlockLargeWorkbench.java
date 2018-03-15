package rikuto.larger_workbenches.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockLargeWorkbench  extends ItemBlockWithMetadata {

	public ItemBlockLargeWorkbench(Block block) {
		super(block, block);
	}

	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}
}