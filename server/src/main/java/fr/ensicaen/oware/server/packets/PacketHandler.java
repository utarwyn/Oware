package fr.ensicaen.oware.server.packets;

public class PacketHandler {

    public void handle(String serializedPacket) {
        Packet packet = Packet.GSON.fromJson(serializedPacket, Packet.class);
    }

}
