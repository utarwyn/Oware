package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.net.Capitalizer;
import fr.ensicaen.oware.server.net.packets.PlayPacket;
import fr.ensicaen.oware.server.net.packets.UpdateGameBoardPacket;
import lombok.Getter;

import java.util.Arrays;

public class Player {

    private Capitalizer capitalizer;

    @Getter
    private Hole[] holes;

    Player(Capitalizer capitalizer) {
        this.capitalizer = capitalizer;
        this.capitalizer.setPlayer(this);

        this.holes = new Hole[6];
        Arrays.fill(this.holes, new Hole());
    }

    void sendGameBoard(Hole[] opponentHoles) {
        this.capitalizer.sendPacket(new UpdateGameBoardPacket(this.holes, opponentHoles));
    }

    void sendPlayAction() {
        this.capitalizer.sendPacket(new PlayPacket());
    }

}
