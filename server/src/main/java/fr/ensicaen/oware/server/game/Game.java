package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.Main;
import fr.ensicaen.oware.server.net.packets.PlayPacket;
import fr.ensicaen.oware.server.net.packets.UpdateGameBoardPacket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

	private Main main;

	private Player firstPlayer;

	private Player secondPlayer;

	private Player currentPlayer;

	public Game(Main main) {
		this.main = main;
		System.out.println("Game started!");
		this.firstPlayer = new Player(this.main.getCapitalizeServer().getFirstClient());
		this.secondPlayer = new Player(this.main.getCapitalizeServer().getSecondClient());
		this.currentPlayer = new Random().nextInt() > 0.5 ? this.firstPlayer : this.secondPlayer;
	}

	public List<Hole> getGameBoard() {
		List<Hole> holes = new ArrayList<>(Arrays.asList(this.firstPlayer.getHoles()));
		holes.addAll(Arrays.asList(this.secondPlayer.getHoles()));
		return holes;
	}

	public void nextRound() {
		// Changing current player
		this.currentPlayer = this.currentPlayer == this.firstPlayer ? this.secondPlayer : this.firstPlayer;

		// Send the gameboard to players
		this.main.getCapitalizeServer().broadcastPacket(new UpdateGameBoardPacket(this.getGameBoard()));

		// And send the play packet to the current player
		this.currentPlayer.getCapitalizer().sendPacket(new PlayPacket());
	}

}
