package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.game.Hole;
import fr.ensicaen.oware.server.net.Packet;

import java.util.List;

public class UpdateGameBoardPacket extends Packet {

    private List<Hole> gameboard;

    public UpdateGameBoardPacket(List<Hole> gameboard) {
        this.gameboard = gameboard;
    }

}
