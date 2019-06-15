package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.game.Hole;
import fr.ensicaen.oware.client.net.Packet;

import java.util.List;

/**
 * Packet sended by the server to update the game board.
 * It will trigger the scene refreshing with the game board data.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class UpdateGameBoardPacket extends Packet {

	/**
	 * Gameboard sended by the server to be displayed
	 */
	private List<Hole> gameboard;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReceive() {
		System.out.println("I've just received the gameboard from the server!");
		System.out.println(this.gameboard);
	}

}
