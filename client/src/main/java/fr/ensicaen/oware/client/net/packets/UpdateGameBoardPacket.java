package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.controllers.GameController;
import fr.ensicaen.oware.client.game.GameBoard;
import fr.ensicaen.oware.client.game.Hole;
import fr.ensicaen.oware.client.net.Packet;

import java.util.Arrays;

/**
 * Packet sended by the server to update the game board.
 * It will trigger the scene refreshing with the game board data.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class UpdateGameBoardPacket extends Packet {

	/**
     * List all of my holes
	 */
    private Hole[] myHoles;

    /**
     * List all of the opponent holes
     */
    private Hole[] opponentHoles;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReceive() {
        System.out.println("Gameboard updated from the server!");
        System.out.println("my holes      : " + Arrays.toString(this.myHoles));
        System.out.println("opponent holes: " + Arrays.toString(this.opponentHoles));

        GameController controller = this.application.getStage().getController();
        controller.updateGameBoard(new GameBoard(this.myHoles, this.opponentHoles));
	}

}
