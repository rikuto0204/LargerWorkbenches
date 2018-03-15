package rikuto.larger_workbenches.plugin.minetweaker;

import minetweaker.MineTweakerAPI;

public class TweakerPlugin {

	public static void register() {
		MineTweakerAPI.registerClass(LargeCrafting.class);
	}
}