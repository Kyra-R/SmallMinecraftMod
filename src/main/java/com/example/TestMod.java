package com.example;

import com.example.network.MessagePayload;
import com.example.protobuf.MessageOuterClass;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.example.database.DatabaseConnection.insertMessage;

public class TestMod implements ModInitializer {
	public static final String MOD_ID = "testmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(MessagePayload.TYPE, MessagePayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(
				MessagePayload.TYPE,
				(payload, context) -> {
					byte[] data = payload.data();
					UUID playerUuid = context.player().getGameProfile().getId();

					CompletableFuture.runAsync(() -> {
								try {
									Class.forName("org.postgresql.Driver");
									MessageOuterClass.Message message = MessageOuterClass.Message.parseFrom(data);
									String text = message.getText();

									insertMessage(playerUuid, text);

									System.out.println("Saving message from player " + playerUuid + ": " + text);

								} catch (Exception e) {
									e.printStackTrace();
								}
							});

							}
					);

		LOGGER.info("Hello Fabric world!");
	}

}