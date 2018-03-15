package rikuto.larger_workbenches.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rikuto.larger_workbenches.tileentity.TileEntityLargeWorkbench4x4;

public class GuiLargeWorkbench4x4 extends GuiContainer {

	private static final ResourceLocation guiTextures = new ResourceLocation("largerworkbenches:textures/gui/workbench4x4_gui.png");

	public GuiLargeWorkbench4x4(InventoryPlayer inventoryPlayer, World world, int x, int y, int z, TileEntityLargeWorkbench4x4 tileEntity) {
		super(new ContainerLargeWorkbench4x4(inventoryPlayer, world, x, y, z, tileEntity));
		xSize = 176;
		ySize = 184;
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