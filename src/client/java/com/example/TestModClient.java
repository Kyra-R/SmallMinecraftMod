package com.example;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;


import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class TestModClient implements ClientModInitializer {


	private KeyMapping openGuiKey;

	@Override
	public void onInitializeClient() {
		openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.mymod.open_gui",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_M,
				"key.categories.misc"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openGuiKey.consumeClick() && Minecraft.getInstance().screen == null) {
				Minecraft.getInstance().setScreen(new MessageScreen());
			}
		});
	}
}