package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.game.Hole;
import fr.ensicaen.oware.server.net.Packet;

public class UpdateGameBoardPacket extends Packet {

    private Hole[] myHoles;

    private Hole[] opponentHoles;

    private int collectedSeeds;

    public UpdateGameBoardPacket(Hole[] myHoles, Hole[] opponentHoles, int collectedSeeds) {
        this.myHoles = myHoles;
        this.opponentHoles = opponentHoles;
        this.collectedSeeds = collectedSeeds;
    }

}
