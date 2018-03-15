package rikuto.larger_workbenches.plugin.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
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

public class NEIConfig  implements IConfigureNEI {

	public String getName() {
		return null;
	}

	public String getVersion() {
		return null;
	}

	public void loadConfig() {
		API.registerRecipeHandler(new LargeShapedRecipeHandler4x4());
		API.registerRecipeHandler(new LargeShapedRecipeHandler5x5());
		API.registerRecipeHandler(new LargeShapedRecipeHandler6x6());
		API.registerRecipeHandler(new LargeShapedRecipeHandler7x7());
		API.registerRecipeHandler(new LargeShapedRecipeHandler8x8());
		API.registerRecipeHandler(new LargeShapedRecipeHandler9x9());
		API.registerRecipeHandler(new LargeShapelessRecipeHandler4x4());
		API.registerRecipeHandler(new LargeShapelessRecipeHandler5x5());
		API.registerRecipeHandler(new LargeShapelessRecipeHandler6x6());
		API.registerRecipeHandler(new LargeShapelessRecipeHandler7x7());
		API.registerRecipeHandler(new LargeShapelessRecipeHandler8x8());
		API.registerRecipeHandler(new LargeShapelessRecipeHandler9x9());

		API.registerUsageHandler(new LargeShapedRecipeHandler4x4());
		API.registerUsageHandler(new LargeShapedRecipeHandler5x5());
		API.registerUsageHandler(new LargeShapedRecipeHandler6x6());
		API.registerUsageHandler(new LargeShapedRecipeHandler7x7());
		API.registerUsageHandler(new LargeShapedRecipeHandler8x8());
		API.registerUsageHandler(new LargeShapedRecipeHandler9x9());
		API.registerUsageHandler(new LargeShapelessRecipeHandler4x4());
		API.registerUsageHandler(new LargeShapelessRecipeHandler5x5());
		API.registerUsageHandler(new LargeShapelessRecipeHandler6x6());
		API.registerUsageHandler(new LargeShapelessRecipeHandler7x7());
		API.registerUsageHandler(new LargeShapelessRecipeHandler8x8());
		API.registerUsageHandler(new LargeShapelessRecipeHandler9x9());

		API.registerGuiOverlay(GuiLargeWorkbench4x4.class, "large_crafting.tier0");
		API.registerGuiOverlay(GuiLargeWorkbench5x5.class, "large_crafting.tier1");
		API.registerGuiOverlay(GuiLargeWorkbench6x6.class, "large_crafting.tier2", 14, 3);
		API.registerGuiOverlay(GuiLargeWorkbench7x7.class, "large_crafting.tier3", 23, 12);
		API.registerGuiOverlay(GuiLargeWorkbench8x8.class, "large_crafting.tier4", 32, 16);
		API.registerGuiOverlay(GuiLargeWorkbench9x9.class, "large_crafting.tier5", 37, 7);
		API.registerGuiOverlay(GuiAutoLargeWorkbench4x4.class, "large_crafting.tier0", 41, 11);
		API.registerGuiOverlay(GuiAutoLargeWorkbench5x5.class, "large_crafting.tier1", 41, 11);
		API.registerGuiOverlay(GuiAutoLargeWorkbench6x6.class, "large_crafting.tier2", 41, 3);
		API.registerGuiOverlay(GuiAutoLargeWorkbench7x7.class, "large_crafting.tier3", 41, 12);
		API.registerGuiOverlay(GuiAutoLargeWorkbench8x8.class, "large_crafting.tier4", 41, 16);
		API.registerGuiOverlay(GuiAutoLargeWorkbench9x9.class, "large_crafting.tier5", 37, 7);

		API.registerGuiOverlayHandler(GuiLargeWorkbench4x4.class, new DefaultOverlayHandler(), "large_crafting.tier0");
		API.registerGuiOverlayHandler(GuiLargeWorkbench5x5.class, new DefaultOverlayHandler(), "large_crafting.tier1");
		API.registerGuiOverlayHandler(GuiLargeWorkbench6x6.class, new DefaultOverlayHandler(14, 3), "large_crafting.tier2");
		API.registerGuiOverlayHandler(GuiLargeWorkbench7x7.class, new DefaultOverlayHandler(23, 12), "large_crafting.tier3");
		API.registerGuiOverlayHandler(GuiLargeWorkbench8x8.class, new DefaultOverlayHandler(32, 16), "large_crafting.tier4");
		API.registerGuiOverlayHandler(GuiLargeWorkbench9x9.class, new DefaultOverlayHandler(37, 7), "large_crafting.tier5");
		API.registerGuiOverlayHandler(GuiAutoLargeWorkbench4x4.class, new DefaultOverlayHandler(185, 11), "large_crafting.tier0");
		API.registerGuiOverlayHandler(GuiAutoLargeWorkbench5x5.class, new DefaultOverlayHandler(194, 11), "large_crafting.tier1");
		API.registerGuiOverlayHandler(GuiAutoLargeWorkbench6x6.class, new DefaultOverlayHandler(203, 3), "large_crafting.tier2");
		API.registerGuiOverlayHandler(GuiAutoLargeWorkbench7x7.class, new DefaultOverlayHandler(212, 12), "large_crafting.tier3");
		API.registerGuiOverlayHandler(GuiAutoLargeWorkbench8x8.class, new DefaultOverlayHandler(221, 16), "large_crafting.tier4");
		API.registerGuiOverlayHandler(GuiAutoLargeWorkbench9x9.class, new DefaultOverlayHandler(209, 7), "large_crafting.tier5");
	}
}