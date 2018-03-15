package rikuto.larger_workbenches;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rikuto.larger_workbenches.block.BlockAutoLargeWorkbench;
import rikuto.larger_workbenches.block.BlockLargeWorkbench;
import rikuto.larger_workbenches.block.ItemBlockLargeWorkbench;
import rikuto.larger_workbenches.gui.ContainerAutoLargeWorkbench4x4;
import rikuto.larger_workbenches.gui.ContainerAutoLargeWorkbench5x5;
import rikuto.larger_workbenches.gui.ContainerAutoLargeWorkbench6x6;
import rikuto.larger_workbenches.gui.ContainerAutoLargeWorkbench7x7;
import rikuto.larger_workbenches.gui.ContainerAutoLargeWorkbench8x8;
import rikuto.larger_workbenches.gui.ContainerAutoLargeWorkbench9x9;
import rikuto.larger_workbenches.gui.ContainerLargeWorkbench4x4;
import rikuto.larger_workbenches.gui.ContainerLargeWorkbench5x5;
import rikuto.larger_workbenches.gui.ContainerLargeWorkbench6x6;
import rikuto.larger_workbenches.gui.ContainerLargeWorkbench7x7;
import rikuto.larger_workbenches.gui.ContainerLargeWorkbench8x8;
import rikuto.larger_workbenches.gui.ContainerLargeWorkbench9x9;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench4x4;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench5x5;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench6x6;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench7x7;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench8x8;
import rikuto.larger_workbenches.gui.GuiAutoLargeWorkbench9x9;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench4x4;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench5x5;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench6x6;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench7x7;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench8x8;
import rikuto.larger_workbenches.gui.GuiLargeWorkbench9x9;
import rikuto.larger_workbenches.plugin.minetweaker.TweakerPlugin;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench4x4;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench5x5;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench6x6;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench7x7;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench8x8;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench9x9;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench4x4;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench5x5;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench6x6;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench7x7;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench8x8;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench9x9;

@Mod(modid = LargerWorkbenches.MODID, name = LargerWorkbenches.MODNAME, version = LargerWorkbenches.VERSION)
public class LargerWorkbenches {


	/**
	 * @author Rikuto
	 */

	public static final String MODID = "LargerWorkbenches";
	public static final String MODNAME = "Larger Workbenches";
	public static final String VERSION = "1.0.0";
	@Metadata(MODID)
	public static ModMetadata meta;
	@Mod.Instance(MODID)
	public static LargerWorkbenches instance;

	public static Block workbench;
	public static Block autoWorkbench;

	public static final CreativeTabs tabLargerWorkbenches = new CreativeTabs("LargerWorkbenchesTab") {

		@SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
			if (workbench != null) {
				return new ItemStack(workbench, 1, 0);
			}
			return null;
		}

		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return null;
		}
	};

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registerInfo(meta);

		workbench = new BlockLargeWorkbench()
				.setBlockName("workbench")
				.setBlockTextureName("largerworkbenches:workbench")
				.setCreativeTab(tabLargerWorkbenches);
		GameRegistry.registerBlock(workbench, ItemBlockLargeWorkbench.class, "workbench");

		autoWorkbench = new BlockAutoLargeWorkbench()
				.setBlockName("autoWorkbench")
				.setBlockTextureName("largerworkbenches:autoWorkbench")
				.setCreativeTab(tabLargerWorkbenches);
		GameRegistry.registerBlock(autoWorkbench, ItemBlockLargeWorkbench.class, "autoWorkbench");

		GameRegistry.registerTileEntity(TileEntityLargeWorkbench4x4.class, "TileEntityLargeWorkbench4x4");
		GameRegistry.registerTileEntity(TileEntityLargeWorkbench5x5.class, "TileEntityLargeWorkbench5x5");
		GameRegistry.registerTileEntity(TileEntityLargeWorkbench6x6.class, "TileEntityLargeWorkbench6x6");
		GameRegistry.registerTileEntity(TileEntityLargeWorkbench7x7.class, "TileEntityLargeWorkbench7x7");
		GameRegistry.registerTileEntity(TileEntityLargeWorkbench8x8.class, "TileEntityLargeWorkbench8x8");
		GameRegistry.registerTileEntity(TileEntityLargeWorkbench9x9.class, "TileEntityLargeWorkbench9x9");
		GameRegistry.registerTileEntity(TileEntityAutoLargeWorkbench4x4.class, "TileEntityAutoLargeWorkbench4x4");
		GameRegistry.registerTileEntity(TileEntityAutoLargeWorkbench5x5.class, "TileEntityAutoLargeWorkbench5x5");
		GameRegistry.registerTileEntity(TileEntityAutoLargeWorkbench6x6.class, "TileEntityAutoLargeWorkbench6x6");
		GameRegistry.registerTileEntity(TileEntityAutoLargeWorkbench7x7.class, "TileEntityAutoLargeWorkbench7x7");
		GameRegistry.registerTileEntity(TileEntityAutoLargeWorkbench8x8.class, "TileEntityAutoLargeWorkbench8x8");
		GameRegistry.registerTileEntity(TileEntityAutoLargeWorkbench9x9.class, "TileEntityAutoLargeWorkbench9x9");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("MineTweaker3"))
			TweakerPlugin.register();
	}

	private static void registerInfo(ModMetadata meta) {
		meta.modId = LargerWorkbenches.MODID;
		meta.name = LargerWorkbenches.MODNAME;
		meta.description = "Larger than Workbench";
		meta.version = LargerWorkbenches.VERSION;
		meta.url = "https://github.com/rikuto0204/LargerWorkbenches";
		meta.authorList.add("Rikuto");
		meta.autogenerated = false;
	}

	public static class GuiHandler implements IGuiHandler {

		public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
			switch (ID) {
			case 0:
				return new ContainerLargeWorkbench4x4(player.inventory, world, x, y, z, (TileEntityLargeWorkbench4x4)world.getTileEntity(x, y, z));
			case 1:
				return new ContainerLargeWorkbench5x5(player.inventory, world, x, y, z, (TileEntityLargeWorkbench5x5)world.getTileEntity(x, y, z));
			case 2:
				return new ContainerLargeWorkbench6x6(player.inventory, world, x, y, z, (TileEntityLargeWorkbench6x6)world.getTileEntity(x, y, z));
			case 3:
				return new ContainerLargeWorkbench7x7(player.inventory, world, x, y, z, (TileEntityLargeWorkbench7x7)world.getTileEntity(x, y, z));
			case 4:
				return new ContainerLargeWorkbench8x8(player.inventory, world, x, y, z, (TileEntityLargeWorkbench8x8)world.getTileEntity(x, y, z));
			case 5:
				return new ContainerLargeWorkbench9x9(player.inventory, world, x, y, z, (TileEntityLargeWorkbench9x9)world.getTileEntity(x, y, z));
			case 16:
				return new ContainerAutoLargeWorkbench4x4(player.inventory, world, (TileEntityAutoLargeWorkbench4x4)world.getTileEntity(x, y, z));
			case 17:
				return new ContainerAutoLargeWorkbench5x5(player.inventory, world, (TileEntityAutoLargeWorkbench5x5)world.getTileEntity(x, y, z));
			case 18:
				return new ContainerAutoLargeWorkbench6x6(player.inventory, world, (TileEntityAutoLargeWorkbench6x6)world.getTileEntity(x, y, z));
			case 19:
				return new ContainerAutoLargeWorkbench7x7(player.inventory, world, (TileEntityAutoLargeWorkbench7x7)world.getTileEntity(x, y, z));
			case 20:
				return new ContainerAutoLargeWorkbench8x8(player.inventory, world, (TileEntityAutoLargeWorkbench8x8)world.getTileEntity(x, y, z));
			case 21:
				return new ContainerAutoLargeWorkbench9x9(player.inventory, world, (TileEntityAutoLargeWorkbench9x9)world.getTileEntity(x, y, z));
			}
			return null;
		}

		public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
			switch (ID) {
			case 0:
				return new GuiLargeWorkbench4x4(player.inventory, world, x, y, z, (TileEntityLargeWorkbench4x4)world.getTileEntity(x, y, z));
			case 1:
				return new GuiLargeWorkbench5x5(player.inventory, world, x, y, z, (TileEntityLargeWorkbench5x5)world.getTileEntity(x, y, z));
			case 2:
				return new GuiLargeWorkbench6x6(player.inventory, world, x, y, z, (TileEntityLargeWorkbench6x6)world.getTileEntity(x, y, z));
			case 3:
				return new GuiLargeWorkbench7x7(player.inventory, world, x, y, z, (TileEntityLargeWorkbench7x7)world.getTileEntity(x, y, z));
			case 4:
				return new GuiLargeWorkbench8x8(player.inventory, world, x, y, z, (TileEntityLargeWorkbench8x8)world.getTileEntity(x, y, z));
			case 5:
				return new GuiLargeWorkbench9x9(player.inventory, world, x, y, z, (TileEntityLargeWorkbench9x9)world.getTileEntity(x, y, z));
			case 16:
				return new GuiAutoLargeWorkbench4x4(player.inventory, world, (TileEntityAutoLargeWorkbench4x4)world.getTileEntity(x, y, z));
			case 17:
				return new GuiAutoLargeWorkbench5x5(player.inventory, world, (TileEntityAutoLargeWorkbench5x5)world.getTileEntity(x, y, z));
			case 18:
				return new GuiAutoLargeWorkbench6x6(player.inventory, world, (TileEntityAutoLargeWorkbench6x6)world.getTileEntity(x, y, z));
			case 19:
				return new GuiAutoLargeWorkbench7x7(player.inventory, world, (TileEntityAutoLargeWorkbench7x7)world.getTileEntity(x, y, z));
			case 20:
				return new GuiAutoLargeWorkbench8x8(player.inventory, world, (TileEntityAutoLargeWorkbench8x8)world.getTileEntity(x, y, z));
			case 21:
				return new GuiAutoLargeWorkbench9x9(player.inventory, world, (TileEntityAutoLargeWorkbench9x9)world.getTileEntity(x, y, z));
			}
			return null;
		}
	}
}