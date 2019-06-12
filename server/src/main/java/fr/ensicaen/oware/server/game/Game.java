package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.Main;
import fr.ensicaen.oware.server.packets.Packet;
import fr.ensicaen.oware.server.packets.datas.ActionData;

import java.util.Random;

public class Game {

    private Main main;

    private Player fisrtPlayer;

    private Player secondPlayer;

    private Player currentPlayer;

    public Game(Main main) {
        this.main = main;
        System.out.println("Game started!");
        this.fisrtPlayer = new Player(this.main.getCapitalizeServer().getFirstClient());
        this.secondPlayer = new Player(this.main.getCapitalizeServer().getSecondClient());
        this.currentPlayer = new Random().nextInt() > 0.5 ? this.fisrtPlayer : this.secondPlayer;
        this.nextRound();
    }

    public void nextRound() {
        this.currentPlayer.getCapitalizer().sendPacket(new Packet(new ActionData(ActionData.Action.PLAY)));
    }

}
