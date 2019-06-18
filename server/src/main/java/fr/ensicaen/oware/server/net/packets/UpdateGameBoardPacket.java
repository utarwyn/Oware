package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.game.Hole;
import fr.ensicaen.oware.server.net.Packet;

public class UpdateGameBoardPacket extends Packet {

    private Hole[] myHoles;

    private Hole[] opponentHoles;

    public UpdateGameBoardPacket(Hole[] myHoles, Hole[] opponentHoles) {
        this.myHoles = myHoles;
        this.opponentHoles = opponentHoles;
    }

}
