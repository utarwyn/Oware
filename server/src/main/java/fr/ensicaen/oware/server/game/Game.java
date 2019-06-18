package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.OwareServer;

import java.util.Random;

public class Game {

    private Player firstPlayer;

    private Player secondPlayer;

    private Player currentPlayer;

    public Game(OwareServer server) {
        System.out.println("Game started!");
        this.firstPlayer = new Player(server.getCapitalizeServer().getFirstClient());
        this.secondPlayer = new Player(server.getCapitalizeServer().getSecondClient());
        this.currentPlayer = new Random().nextInt() > 0.5 ? this.firstPlayer : this.secondPlayer;
    }

    public void nextRound() {
        // Changing current player
        this.currentPlayer = this.currentPlayer == this.firstPlayer ? this.secondPlayer : this.firstPlayer;

        // Send the gameboard to players
        this.firstPlayer.sendGameBoard(this.secondPlayer.getHoles());
        this.secondPlayer.sendGameBoard(this.firstPlayer.getHoles());

        // And send the play packet to the current player
        this.currentPlayer.sendPlayAction();
    }

}
