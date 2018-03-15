package rikuto.larger_workbenches.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench6x6;

public class GuiLargeWorkbench6x6 extends GuiContainer {

	private static final ResourceLocation guiTextures = new ResourceLocation("largerworkbenches:textures/gui/workbench6x6_gui.png");

	public GuiLargeWorkbench6x6(InventoryPlayer inventoryPlayer, World world, BlockPos pos, TileEntityLargeWorkbench6x6 tileEntity) {
		super(new ContainerLargeWorkbench6x6(inventoryPlayer, world, pos, tileEntity));
		xSize = 194;
		ySize = 220;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}

	protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}