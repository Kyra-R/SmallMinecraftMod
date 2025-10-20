package com.example.network;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MessagePayload(byte[] data) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.tryBuild("testmod", "send_message");

    public static final CustomPacketPayload.Type<MessagePayload> TYPE =
            new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MessagePayload> CODEC = new StreamCodec<>() {
        @Override
        public void encode(FriendlyByteBuf object, MessagePayload object2) {
            object.writeByteArray(object2.data());
        }

        @Override
        public MessagePayload decode(FriendlyByteBuf input) {
            return new MessagePayload(input.readByteArray());
        }

    };


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
