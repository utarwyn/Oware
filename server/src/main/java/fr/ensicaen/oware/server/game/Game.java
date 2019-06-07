package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.Main;

public class Game {

    private Main main;

    private Player fisrtPlayer;

    private Player secondPlayer;

    public Game(Main main) {
        this.main = main;
        System.out.println("Game started!");
        this.fisrtPlayer = new Player(this.main.getCapitalizeServer().getFirstClient());
        this.secondPlayer = new Player(this.main.getCapitalizeServer().getSecondClient());
    }

}
