package rikuto.larger_workbenches.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLargeWorkbench extends TileEntity implements IInventory {

	protected int inventory;
	protected ItemStack result;
	protected ItemStack[] matrix;

	public TileEntityLargeWorkbench(int meta) {
		inventory = (meta + 4) * (meta + 4);
		matrix = new ItemStack[inventory];
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		result = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("Result"));
		for (int i = 0; i < matrix.length; i++)
			matrix[i] = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("Craft" + i));
	}

	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		if (result != null) {
			NBTTagCompound produce = new NBTTagCompound();
			result.writeToNBT(produce);
			nbtTagCompound.setTag("Result", produce);
		}
		else
			nbtTagCompound.removeTag("Result");
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i] != null) {
				NBTTagCompound craft = new NBTTagCompound();
				matrix[i].writeToNBT(craft);
				nbtTagCompound.setTag("Craft" + i, craft);
			}
			else
				nbtTagCompound.removeTag("Craft" + i);
		}
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtTagCompound);
	}

	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	public int getSizeInventory() {
		return inventory + 1;
	}

	public ItemStack getStackInSlot(int slot) {
		if (slot == 0)
			return result;
		if (slot <= matrix.length)
			return matrix[(slot - 1)];
		return null;
	}

	public ItemStack decrStackSize(int slot, int decrement) {
		if (slot == 0) {
			if (result != null) {
				for (int i = 1; i <= matrix.length; i++)
					decrStackSize(i, 1);
				if (result.stackSize <= decrement) {
					ItemStack craft = result;
					result = null;
					return craft;
				}
				ItemStack split = result.splitStack(decrement);
				if (result.stackSize <= 0)
					result = null;
				return split;
			}
			return null;
		}
		if (slot <= matrix.length && matrix[(slot - 1)] != null) {
			if (matrix[(slot - 1)].stackSize <= decrement) {
				ItemStack ingredient = matrix[(slot - 1)];
				matrix[(slot - 1)] = null;
				return ingredient;
			}
			ItemStack split = matrix[(slot - 1)].splitStack(decrement);
			if (matrix[(slot - 1)].stackSize <= 0)
				matrix[(slot - 1)] = null;
			return split;
		}
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		if (slot == 0)
			result = itemStack;
		else if (slot <= matrix.length)
			matrix[(slot - 1)] = itemStack;
	}

	public String getInventoryName() {
		return null;
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	public void openInventory() {}

	public void closeInventory() {}

	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return false;
	}
}