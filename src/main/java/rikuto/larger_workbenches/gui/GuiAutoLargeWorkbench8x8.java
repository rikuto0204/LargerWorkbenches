package rikuto.larger_workbenches.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench8x8;

public class GuiAutoLargeWorkbench8x8 extends GuiContainer {

	private static final ResourceLocation guiTextures = new ResourceLocation("largerworkbenches:textures/gui/autoworkbench8x8_gui.png");

	public GuiAutoLargeWorkbench8x8(InventoryPlayer inventoryPlayer, World world, TileEntityAutoLargeWorkbench8x8 tileEntity) {
		super(new ContainerAutoLargeWorkbench8x8(inventoryPlayer, world, tileEntity));
		xSize = 366;
		ySize = 256;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}

	protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTextures);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0.0, 0.0);
		tessellator.addVertexWithUV(guiLeft, guiTop + xSize, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + xSize, guiTop + xSize, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(guiLeft + xSize, guiTop, 0, 1.0, 0.0);
		tessellator.draw();
	}
}