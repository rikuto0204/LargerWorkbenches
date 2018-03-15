package rikuto.larger_workbenches.block;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rikuto.larger_workbenches.LargerWorkbenches;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench4x4;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench5x5;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench6x6;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench7x7;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench8x8;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench9x9;

public class BlockAutoLargeWorkbench extends BlockLargeWorkbench {

	public BlockAutoLargeWorkbench() {
		super();
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		player.openGui(LargerWorkbenches.instance, ((Integer)state.getValue(TIER)).intValue() + 16, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileEntityAutoLargeWorkbench4x4();
		case 1:
			return new TileEntityAutoLargeWorkbench5x5();
		case 2:
			return new TileEntityAutoLargeWorkbench6x6();
		case 3:
			return new TileEntityAutoLargeWorkbench7x7();
		case 4:
			return new TileEntityAutoLargeWorkbench8x8();
		case 5:
			return new TileEntityAutoLargeWorkbench9x9();
		}
		return new TileEntityAutoLargeWorkbench4x4();
	}
}