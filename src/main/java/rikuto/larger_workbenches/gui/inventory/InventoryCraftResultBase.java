package rikuto.larger_workbenches.gui.inventory;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench;

public class InventoryCraftResultBase extends InventoryCraftResult {

	private TileEntityLargeWorkbench workbench;

	public InventoryCraftResultBase(TileEntityLargeWorkbench tileEntity) {
		workbench = tileEntity;
	}

	public ItemStack getStackInSlot(int slot) {
		return workbench.getStackInSlot(0);
	}

	public ItemStack decrStackSize(int slot, int decrement) {
		ItemStack itemStack = workbench.getStackInSlot(0);
		if (itemStack != null) {
			ItemStack newStack = itemStack;
			workbench.setInventorySlotContents(0, null);
			return newStack;
		}
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		workbench.setInventorySlotContents(0, itemStack);
	}
}