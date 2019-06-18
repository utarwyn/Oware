package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.net.Capitalizer;
import fr.ensicaen.oware.server.net.packets.PlayPacket;
import fr.ensicaen.oware.server.net.packets.UpdateGameBoardPacket;
import lombok.Getter;

public class Player {

    static final int HOLES_PER_PLAYER = 6;

    private Capitalizer capitalizer;

    @Getter
    private Hole[] holes;

    Player(Capitalizer capitalizer) {
        this.capitalizer = capitalizer;
        this.holes = new Hole[HOLES_PER_PLAYER];

        this.capitalizer.setPlayer(this);

        for (int i = 0; i < this.holes.length; i++) {
            this.holes[i] = new Hole();
        }
    }

    void sendGameBoard(Hole[] opponentHoles) {
        this.capitalizer.sendPacket(new UpdateGameBoardPacket(this.holes, opponentHoles));
    }

    void sendPlayAction() {
        this.capitalizer.sendPacket(new PlayPacket());
    }

}
