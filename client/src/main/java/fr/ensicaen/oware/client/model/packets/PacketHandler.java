package fr.ensicaen.oware.client.model.packets;

import fr.ensicaen.oware.client.applications.Main;
import fr.ensicaen.oware.client.model.packets.datas.ActionData;

public class PacketHandler {

    private Main main;

    public PacketHandler(Main main) {
        this.main = main;
    }

    public void handle(String serializedPacket) {
        Packet packet = Packet.GSON.fromJson(serializedPacket, Packet.class);
        if (packet.getId() == ActionData.ID) {
            ActionData actionData = packet.deserializeData(ActionData.class);
            if (actionData.getAction() == ActionData.Action.PLAY) {
                System.out.println("PLAY");
            }
        }
    }

}
