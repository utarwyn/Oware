package fr.ensicaen.oware.server.packets;

public class PacketHandler {

    public void handle(String serializedPacket) {
        Packet packet = Packet.gson.fromJson(serializedPacket, Packet.class);
    }

}
