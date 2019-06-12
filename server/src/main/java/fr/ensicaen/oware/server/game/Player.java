package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.packets.datas.GameBoardData;
import fr.ensicaen.oware.server.sockets.Capitalizer;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class Player {

    private Capitalizer capitalizer;

    private Hole[] holes;

    public Player(Capitalizer capitalizer) {
        this.capitalizer = capitalizer;

        this.holes = new Hole[6];
        Arrays.fill(this.holes, new Hole());
    }

    public void sendGameBoard(Hole[] opponentHoles) {
        this.capitalizer.sendData(new GameBoardData(this.holes, opponentHoles));
    }

}
