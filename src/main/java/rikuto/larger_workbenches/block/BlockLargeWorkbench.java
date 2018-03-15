package rikuto.larger_workbenches.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

	public static final PropertyInteger TIER = PropertyInteger.create("tier", 0, 5);

	public BlockLargeWorkbench() {
		super(Material.WOOD);
		setDefaultState(blockState.getBaseState().withProperty(TIER, 0));
	}

	public int damageDropped(IBlockState state) {
		return ((Integer)state.getValue(TIER)).intValue();
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		player.openGui(LargerWorkbenches.instance, ((Integer)state.getValue(TIER)).intValue(), world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityLargeWorkbench tileEntity = (TileEntityLargeWorkbench)world.getTileEntity(pos);
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
						EntityItem entityItem = new EntityItem(world);
						entityItem.setPosition(pos.getX() + f, pos.getY() + f1, pos.getZ() + f2);
						entityItem.setEntityItemStack(new ItemStack(itemStack.getItem(), j1, itemStack.getItemDamage()));
						if (itemStack.hasTagCompound())
							entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
						float f3 = 0.05F;
						entityItem.motionX = (float)random.nextGaussian() * f3;
						entityItem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
						entityItem.motionZ = (float)random.nextGaussian() * f3;
						world.spawnEntityInWorld(entityItem);
					}
				}
				world.updateComparatorOutputLevel(pos, state.getBlock());
			}
		}
		super.breakBlock(world, pos, state);
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

	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIER, meta);
	}

	public int getMetaFromState(IBlockState state) {
		return ((Integer)state.getValue(TIER)).intValue();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TIER });
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < 6; i++)
			list.add(new ItemStack(item, 1, i));
	}
}