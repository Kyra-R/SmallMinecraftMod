package com.example;

import com.example.network.MessagePayload;
import com.example.protobuf.MessageOuterClass;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;

public class MessageScreen extends Screen {
    private EditBox messageField;

    public MessageScreen() {
        super(Component.literal("Send!"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int fieldWidth = 200;
        int buttonWidth = 80;
        int fieldHeight = 20;
        int buttonHeight = 20;


        this.messageField = new EditBox(this.font, centerX - fieldWidth / 2, centerY - 10, fieldWidth, fieldHeight, Component.literal("Message"));
        this.messageField.setMaxLength(256);
        this.messageField.setValue("");

        this.addRenderableWidget(this.messageField);
        this.setInitialFocus(this.messageField);


        this.addRenderableWidget(
                Button.builder(Component.literal("Send"), button -> onSend())
                        .pos(centerX - 40, centerY + 20)
                        .size(buttonWidth, buttonHeight)
                        .build());
    }

    private void onSend() {
        String text = messageField.getValue();
        Minecraft mc = Minecraft.getInstance();

        /*if (mc.player != null && !text.isEmpty()) { //for testing
            mc.player.displayClientMessage(Component.literal(text), false);
        }*/

        System.out.println("Message sent: " + text); //for testing

        mc.setScreen(null);

        MessageOuterClass.Message message = MessageOuterClass.Message.newBuilder() //to protobuf
                .setText(text)
                .build();

        byte[] data = message.toByteArray();

        MessagePayload payload = new MessagePayload(data);
        ClientPlayNetworking.send(payload);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            Minecraft.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
