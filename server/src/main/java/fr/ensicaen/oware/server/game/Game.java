package fr.ensicaen.oware.server.game;

import java.util.Arrays;

public class Game {

    private Hole[] firstPlayerHoles;
    private Hole[] secondPlayerHoles;

    public Game() {
        System.out.println("Game started!");
        this.firstPlayerHoles = new Hole[6];
        this.secondPlayerHoles = new Hole[6];
        Arrays.fill(this.firstPlayerHoles, new Hole());
        Arrays.fill(this.secondPlayerHoles, new Hole());
    }

}
