package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.net.Packet;

public class GameEndedPacket extends Packet {

    private EndType type;

    public GameEndedPacket(EndType type) {
        this.type = type;
    }

    public enum EndType {
        WIN, LOSE, DRAW
    }

}
