package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.game.Hole;
import fr.ensicaen.oware.server.net.Packet;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateGameBoardPacket extends Packet {

    private Hole[] myHoles;

    private Hole[] opponentHoles;

    private int collectedSeeds;

    private boolean canGiveUp;

}
