package rikuto.larger_workbenches.gui.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench;

public class InventoryCraftingBase extends InventoryCrafting {

	private TileEntityLargeWorkbench workbench;
	private int inventoryWidth;
	private int inventoryHeight;
	private Container container;

	public InventoryCraftingBase(Container container, int width, int height, TileEntityLargeWorkbench tileEntity){
		super(container, width, height);
		workbench = tileEntity;
		inventoryWidth = width;
		inventoryHeight = height;
		this.container = container;
	}

	public ItemStack getStackInSlot(int slot) {
		return slot >= getSizeInventory() ? null : workbench.getStackInSlot(slot + 1);
	}

	public ItemStack getStackInRowAndColumn(int row, int column) {
		if (row >= 0 && row < inventoryWidth) {
			int x = row + column * inventoryWidth;
			return getStackInSlot(x);
		}
		else
			return null;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	public ItemStack decrStackSize(int slot, int decrement) {
		ItemStack itemStack = workbench.getStackInSlot(slot + 1);
		if (itemStack != null) {
			ItemStack newStack;
			if (itemStack.stackSize <= decrement) {
				newStack = itemStack.copy();
				itemStack = null;
				workbench.setInventorySlotContents(slot + 1, null);
				container.onCraftMatrixChanged(this);
				return newStack;
			}
			else {
				newStack = itemStack.splitStack(decrement);
				if (itemStack.stackSize == 0)
					itemStack = null;
				container.onCraftMatrixChanged(this);
				return newStack;
			}
		}
		else
			return null;
	}

	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		workbench.setInventorySlotContents(slot + 1, itemStack);
		container.onCraftMatrixChanged(this);
	}

	public int getWidth() {
		return inventoryWidth;
	}

	public int getHeight() {
		return inventoryHeight;
	}
}