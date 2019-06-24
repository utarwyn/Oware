package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.game.rank.RankPlayer;
import fr.ensicaen.oware.server.net.Packet;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GameEndedPacket extends Packet {

    private EndType type;

    private List<RankPlayer> topScores;

    public enum EndType {
        WIN, LOSE, DRAW
    }

}
