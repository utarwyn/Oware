package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.game.GameBoard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Iterator;

/**
 * Represents the game controller.
 * Used by the stage {@link fr.ensicaen.oware.client.stages.GameStage}.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class GameController extends Controller {

    @FXML
    private Pane mainPanel;

    @FXML
    private Pane opponentHolesPane;

    @FXML
    private Pane myHolesPane;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        this.enableMovingSystem(this.mainPanel);
    }

    /**
     * Update the stage with gameboard information
     */
    public void updateGameBoard(GameBoard gameBoard) {
        Platform.runLater(() -> {
            Iterator<Node> myHoles = this.myHolesPane.getChildren().iterator();
            Iterator<Node> opHoles = this.opponentHolesPane.getChildren().iterator();

            for (int i = 0; i < gameBoard.getPlayerHoles().length; i++) {
                ((Button) myHoles.next()).setText(String.valueOf(gameBoard.getPlayerHoles()[i].getSeeds()));
            }
            for (int i = 0; i < gameBoard.getOpponentHoles().length; i++) {
                ((Text) opHoles.next()).setText(String.valueOf(gameBoard.getOpponentHoles()[i].getSeeds()));
            }
        });
    }

    /**
     * Close the socket to communicate with the server.
     *
     * @throws IOException Throwed if the connection cannot be closed.
     */
    public void closeClientSocket() throws IOException {
        this.application.getClient().getSocket().close();
    }

}
