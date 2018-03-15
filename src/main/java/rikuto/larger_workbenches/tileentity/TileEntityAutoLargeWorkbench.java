package rikuto.larger_workbenches.tileentity;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.registry.GameData;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;
import rikuto.larger_workbenches.crafting.LargeCraftingManager;
import rikuto.larger_workbenches.gui.inventory.InventoryCraftingBase;

public class TileEntityAutoLargeWorkbench extends TileEntityLargeWorkbench implements IInventory, ISidedInventory {

	private int outputStackSize = 0;
	private InventoryCrafting craftingMatrix;
	private int[] slotsForAllSides;
	protected boolean recipeChanged = true;
	protected TIntIntMap patternMap = null;

	public TileEntityAutoLargeWorkbench(int meta) {
		super(meta);
		matrix = new ItemStack[inventory * 2];
		craftingMatrix = new LargeCraftingMatrix(meta + 4, meta + 4, this);
		slotsForAllSides = new int[inventory + 1];
		for (int i = 0; i < slotsForAllSides.length; i++)
			slotsForAllSides[i] = i;
	}

	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (recipeChanged) {
			recipeChanged = false;
			ItemStack output = LargeCraftingManager.instance.findMatchingRecipe(craftingMatrix, worldObj);
			ItemStack slotStack = getStackInSlot(0);
			if (output != null && slotStack != null && slotStack.getItem() == output.getItem() && (!output.getHasSubtypes() || output.getItemDamage() == slotStack.getItemDamage()))
				patternMap = getKeySizeMap(inventory, inventory * 2, matrix);
			else if (slotStack != null && slotStack.stackSize > 0)
				patternMap = null;
			else if (output == null && slotStack != null && slotStack.stackSize == 0) {
				result = null;
				patternMap = null;
				markDirty();
			} else if (output != null) {
				outputStackSize = output.stackSize;
				output.stackSize = 0;
				result = output;
				patternMap = getKeySizeMap(inventory, inventory * 2, matrix);
				markDirty();
			}
		}
		if (patternMap != null && result == null){
			ItemStack output = LargeCraftingManager.instance.findMatchingRecipe(craftingMatrix, worldObj);
			if (output == null) {
				patternMap = null;
				return;
			}
			outputStackSize = output.stackSize;
			output.stackSize = 0;
			result = output;
		}
		if (patternMap == null)
			return;
		ItemStack outputStack = result;
		if (outputStack == null || outputStack.stackSize + outputStackSize > outputStack.getMaxStackSize() || !matches(getSmartKeySizeMap(0, inventory, matrix), patternMap))
			return;
		cleanInput();
		outputStack.stackSize += outputStackSize;
		markDirty();
	}

	protected boolean matches(@Nonnull TIntIntMap inputMap, @Nonnull TIntIntMap patternMap) {
		if (inputMap.size() >= patternMap.size() && inputMap.keySet().containsAll(patternMap.keySet())) {
			for (int key : patternMap.keys())
				if (inputMap.get(key) < patternMap.get(key))
					return false;
			return true;
		} else
			return false;
	}

	protected void cleanInput() {
		TIntIntMap patternMap = new TIntIntHashMap(this.patternMap);
		for (int i = 0; i < inventory && !patternMap.isEmpty(); i++) {
			ItemStack itemStack = matrix[i];
			if (itemStack == null)
				continue;
			int key = get(itemStack);
			if (patternMap.containsKey(key)) {
				int total = patternMap.get(key);
				int dif = MathHelper.clamp_int(total, 1, itemStack.stackSize);
				if (!itemStack.getItem().hasContainerItem(itemStack))
					itemStack.stackSize -= dif;
				if (dif - total == 0)
					patternMap.remove(key);
				else
					patternMap.put(key, total - dif);
				if (itemStack.stackSize == 0)
					matrix[i] = null;
			}
		}
	}

	protected int get(ItemStack itemStack) {
		Item item;
		if (itemStack == null || (item = itemStack.getItem()) == null)
			return 0;
		int id = GameData.getItemRegistry().getId(item);
		return id > 0 ? item.getDamage(itemStack) == OreDictionary.WILDCARD_VALUE ? id : id | item.getDamage(itemStack) + 1 << 16 : 0;
	}

	protected TIntIntMap getKeySizeMap(int startIndex, int endIndex, @Nonnull ItemStack[] itemStacks) {
		TIntIntMap keySizeMap = new TIntIntHashMap();
		for (int i = startIndex; i < endIndex; i++) {
			if (itemStacks[i] == null)
				continue;
			int key = get(itemStacks[i]);
			if (keySizeMap.containsKey(key))
				keySizeMap.put(key, keySizeMap.get(key) + 1);
			else
				keySizeMap.put(key, 1);
		}
		return keySizeMap;
	}

	protected TIntIntMap getSmartKeySizeMap(int startIndex, int endIndex, @Nonnull ItemStack[] itemStacks) {
		TIntIntMap smartKeySizeMap = new TIntIntHashMap();
		for (int i = startIndex; i < endIndex; i++) {
			if (itemStacks[i] == null)
				continue;
			int key = get(itemStacks[i]);
			if (smartKeySizeMap.containsKey(key))
				smartKeySizeMap.put(key, smartKeySizeMap.get(key) + itemStacks[i].stackSize);
			else
				smartKeySizeMap.put(key, itemStacks[i].stackSize);
		}
		return smartKeySizeMap;
	}

	public int getSizeInventory() {
		return inventory * 2 + 1;
	}

	public ItemStack decrStackSize(int slot, int decrement) {
		ItemStack slotStack = slot == 0 ? result : matrix[slot - 1];
		if (slotStack != null) {
			int quantity = MathHelper.clamp_int(MathHelper.clamp_int(decrement, 1, slotStack.getMaxStackSize()), 1, slotStack.stackSize);
			ItemStack newStack = slotStack.copy();
			newStack.stackSize = quantity;
			if ((slotStack.stackSize -= quantity) == 0)
				if (slot == 0)
					result = null;
				else
					matrix[slot - 1] = null;
			markDirty();
			return newStack;
		} else
			return null;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		return slot == 0 ? result : matrix[slot - 1];
	}

	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		if (slot > inventory && slot <= inventory * 2)
			recipeChanged = true;
		super.setInventorySlotContents(slot, itemStack);
		markDirty();
	}

	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return slot > 0 && slot <= inventory;
	}

	public int[] getAccessibleSlotsFromSide(int slot) {
		return slotsForAllSides;
	}

	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return slot > 0 && slot <= inventory;
	}

	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot == 0 && result != null && result.stackSize > 0;
	}

	public class LargeCraftingMatrix extends InventoryCraftingBase {

		public LargeCraftingMatrix(int width, int height, TileEntityAutoLargeWorkbench tileEntity) {
			super(new Container() {

				public boolean canInteractWith(EntityPlayer entityPlayer) {
					return false;
				}
			}, width, height, tileEntity);
		}

		public ItemStack getStackInSlot(int slot) {
			return matrix[slot + inventory];
		}

		public void setInventorySlotContents(int slot, ItemStack itemStack) {
			recipeChanged = true;
			matrix[slot + inventory] = itemStack;
			markDirty();
		}

		public ItemStack decrStackSize(int slot, int decrement) {
			if (matrix[slot + inventory] == null)
				return null;
			ItemStack itemStack = matrix[slot].copy();
			if (matrix[slot].stackSize - decrement == 0)
				matrix[slot] = null;
			itemStack.stackSize = decrement;
			markDirty();
			return itemStack;
		}
	}
}