package fr.ensicaen.oware.client.model.packets;

import fr.ensicaen.oware.client.Main;

public class PacketHandler {

    private Main main;

    public PacketHandler(Main main) {
        this.main = main;
    }

    public void handle(String serializedPacket) {
        Packet packet = Packet.gson.fromJson(serializedPacket, Packet.class);
        System.out.println("Packet receive : " + packet);
    }

}
