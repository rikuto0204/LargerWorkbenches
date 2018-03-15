package rikuto.larger_workbenches.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench4x4;

public class GuiAutoLargeWorkbench4x4 extends GuiContainer {

	private static final ResourceLocation guiTextures = new ResourceLocation("largerworkbenches:textures/gui/autoworkbench4x4_gui.png");

	public GuiAutoLargeWorkbench4x4(InventoryPlayer inventoryPlayer, World world, TileEntityAutoLargeWorkbench4x4 tileEntity) {
		super(new ContainerAutoLargeWorkbench4x4(inventoryPlayer, world, tileEntity));
		xSize = 294;
		ySize = 184;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}

	protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTextures);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		vertexBuffer.pos(0.0D, guiTop, guiLeft).tex(0.0D, 0.0D).color(64, 64, 64, 255).endVertex();
		vertexBuffer.pos(0.0D, guiTop + xSize, guiLeft).tex(0.0D, 1.0D).color(64, 64, 64, 255).endVertex();
		vertexBuffer.pos(0.0D, guiTop + xSize, guiLeft + xSize).tex(1.0D, 1.0D).color(64, 64, 64, 255).endVertex();
		vertexBuffer.pos(0.0D, guiTop, guiLeft + xSize).tex(1.0D, 0.0D).color(64, 64, 64, 255).endVertex();
		tessellator.draw();
	}
}