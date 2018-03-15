package rikuto.larger_workbenches.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import rikuto.larger_workbenches.LargerWorkbenches;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench4x4;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench5x5;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench6x6;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench7x7;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench8x8;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench9x9;

public class BlockLargeWorkbench extends BlockContainer {

	protected IIcon[] topIcons = new IIcon[6];
	protected IIcon[] sideIcons = new IIcon[6];
	protected IIcon[] bottomIcons = new IIcon[6];

	public BlockLargeWorkbench() {
		super(Material.wood);
	}

	public int damageDropped(int meta) {
		return meta;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		player.openGui(LargerWorkbenches.instance, world.getBlockMetadata(x, y, z), world, x, y, z);
		return true;
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityLargeWorkbench tileEntity = (TileEntityLargeWorkbench)world.getTileEntity(x, y, z);
		if (tileEntity != null) {
			for (int i = tileEntity instanceof TileEntityAutoLargeWorkbench ? 0 : 1; i < tileEntity.getSizeInventory(); i++) {
				ItemStack itemStack = tileEntity.getStackInSlot(i);
				if (itemStack != null) {
					Random random = new Random();
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;
					while (itemStack.stackSize > 0) {
						int j1 = random.nextInt(21) + 10;
						if (j1 > itemStack.stackSize)
							j1 = itemStack.stackSize;
						itemStack.stackSize -= j1;
						EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemStack.getItem(), j1, itemStack.getItemDamage()));
						if (itemStack.hasTagCompound())
							entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
						float f3 = 0.05F;
						entityItem.motionX = (float)random.nextGaussian() * f3;
						entityItem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
						entityItem.motionZ = (float)random.nextGaussian() * f3;
						world.spawnEntityInWorld(entityItem);
					}
				}
				world.func_147453_f(x, y, z, block);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileEntityLargeWorkbench4x4();
		case 1:
			return new TileEntityLargeWorkbench5x5();
		case 2:
			return new TileEntityLargeWorkbench6x6();
		case 3:
			return new TileEntityLargeWorkbench7x7();
		case 4:
			return new TileEntityLargeWorkbench8x8();
		case 5:
			return new TileEntityLargeWorkbench9x9();
		}
		return new TileEntityLargeWorkbench4x4();
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < topIcons.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		for (int i = 0; i < topIcons.length; i++) {
			topIcons[i] = register.registerIcon(getTextureName() + "_" + i + "_top");
			sideIcons[i] = register.registerIcon(getTextureName() + "_" + i + "_side");
			bottomIcons[i] = register.registerIcon(getTextureName() + "_" + i + "_bottom");
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 0)
			return bottomIcons[meta];
		if (side == 1)
			return topIcons[meta];
		return sideIcons[meta];
	}
}