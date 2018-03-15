package rikuto.larger_workbenches.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rikuto.larger_workbenches.crafting.LargeCraftingManager;
import rikuto.larger_workbenches.gui.inventory.InventoryCraftResultBase;
import rikuto.larger_workbenches.gui.inventory.InventoryCraftingBase;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench6x6;

public class ContainerLargeWorkbench6x6  extends Container {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerLargeWorkbench6x6(InventoryPlayer inventoryPlayer, World world, int x, int y, int z, TileEntityLargeWorkbench6x6 tileEntity) {
		worldObj = world;
		posX = x;
		posY = y;
		posZ = z;
		craftMatrix = new InventoryCraftingBase(this, 6, 6, tileEntity);
		craftResult = new InventoryCraftResultBase(tileEntity);
		addSlotToContainer(new SlotCrafting(inventoryPlayer.player, craftMatrix, craftResult, 0, 160, 62));
		for (int l = 0; l < 6; l++) {
			for (int i1 = 0; i1 < 6; i1++) {
				addSlotToContainer(new Slot(craftMatrix, i1 + l * 6, 12 + i1 * 18, 17 + l * 18));
			}
		}
		for (int l = 0; l < 3; l++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 17 + i1 * 18, 138 + l * 18));
			}
		}
		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(inventoryPlayer, l, 17 + l * 18, 196));
		}
		onCraftMatrixChanged(craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory inventory) {
		ItemStack itemStack = LargeCraftingManager.instance.findMatchingRecipe(craftMatrix, worldObj);
		craftResult.setInventorySlotContents(0, itemStack);
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNumber);
		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (slotNumber == 0) {
				if (!mergeItemStack(itemStack1, 37, 73, true))
					return null;
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if ((slotNumber >= 37) && (slotNumber < 64)) {
				if (!mergeItemStack(itemStack1, 64, 73, false))
					return null;
			}
			else if ((slotNumber >= 64) && (slotNumber < 73)) {
				if (!mergeItemStack(itemStack1, 37, 64, false))
					return null;
			}
			else if (!mergeItemStack(itemStack1, 37, 73, false))
				return null;
			if (itemStack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
			if (itemStack1.stackSize == itemStack.stackSize)
				return null;
			slot.onPickupFromSlot(player, itemStack1);
		}
		return itemStack;
	}
}