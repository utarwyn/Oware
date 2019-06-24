package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.net.Packet;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameEndedPacket extends Packet {

    private EndType type;

    public enum EndType {
        WIN, LOSE, DRAW
    }

}
