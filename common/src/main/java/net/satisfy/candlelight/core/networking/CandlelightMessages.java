package net.satisfy.candlelight.core.networking;

import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.core.networking.handler.SignNoteC2SPacketHandler;
import net.satisfy.candlelight.core.networking.handler.TypewriterDataC2SPacketHandler;
import net.satisfy.candlelight.core.networking.packet.SignNoteC2SPacket;
import net.satisfy.candlelight.core.networking.packet.SyncTypewriterDataC2SPacket;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;

public class CandlelightMessages {
    public static final ResourceLocation TYPEWRITER_SYNC = CandlelightIdentifier.identifier("typewriter_sync");
    public static final ResourceLocation SIGN_NOTE = CandlelightIdentifier.identifier("sign_note");

    public static void registerC2SPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SyncTypewriterDataC2SPacket.TYPE, SyncTypewriterDataC2SPacket.STREAM_CODEC, new TypewriterDataC2SPacketHandler());
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SignNoteC2SPacket.TYPE, SignNoteC2SPacket.STREAM_CODEC, new SignNoteC2SPacketHandler());
    }
}
