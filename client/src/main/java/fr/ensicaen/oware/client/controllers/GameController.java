package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.game.GameBoard;
import fr.ensicaen.oware.client.game.Hole;
import fr.ensicaen.oware.client.net.packets.GiveUpPacket;
import fr.ensicaen.oware.client.net.packets.HoleActionPacket;
import javafx.event.ActionEvent;
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
    private Text myTurnText;

    @FXML
    private Text collected;

    @FXML
    private Button giveupButton;

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

        // Load buttons to play
        for (Node node : this.myHolesPane.getChildren()) {
            ((Button) node).setOnAction(this::onChooseHole);
        }
    }

    /**
     * Update the stage with gameboard information. Temporary method.
     */
    public void updateGameBoard(GameBoard gameBoard) {
        // Create an iterator for each player
        Iterator<Node> myHoles = this.myHolesPane.getChildren().iterator();
        Iterator<Node> opHoles = this.opponentHolesPane.getChildren().iterator();

        // For now, we just replacing values in texts/buttons in stage panes
        for (int i = 0; i < gameBoard.getPlayerHoles().length; i++) {
            Button button = (Button) myHoles.next();
            Hole hole = gameBoard.getPlayerHoles()[i];

            button.setText(String.valueOf(hole.getSeeds()));
            button.setDisable(!hole.isPlayable() || hole.getSeeds() == 0);
        }
        for (int i = 0; i < gameBoard.getOpponentHoles().length; i++) {
            ((Text) opHoles.next()).setText(String.valueOf(gameBoard.getOpponentHoles()[i].getSeeds()));
        }

        // Update collected seeds
        this.collected.setText("Collected seeds: " + gameBoard.getCollectedSeeds());

        // Update give up button
        this.giveupButton.setVisible(gameBoard.isCanGiveUp());
        this.enableGiveUpButton(false);
    }

    /**
     * Display or not the text which indicates that its the player's turn.
     *
     * @param display Display the specific text
     */
    public void displayMyTurnText(boolean display) {
        this.myTurnText.setVisible(display);
    }

    /**
     * Enable or disable the give up button.
     *
     * @param enable Should the button have to be enabled?
     */
    public void enableGiveUpButton(boolean enable) {
        this.giveupButton.setDisable(!enable);
    }

    /**
     * Give up button clicked? Send the player state to the server.
     */
    public void onGiveUpButtonClicked() {
        GiveUpPacket.ActionType giveUpType;

        // If it's my turn, I can send a packet to propose to give up
        if (this.myTurnText.isVisible()) {
            giveUpType = GiveUpPacket.ActionType.PROPOSE;
        } else {
            giveUpType = GiveUpPacket.ActionType.ACCEPT;
        }

        this.application.getClient().sendPacket(new GiveUpPacket(giveUpType));
        this.enableGiveUpButton(false);
    }

    /**
     * Close the socket to communicate with the server.
     *
     * @throws IOException Throwed if the connection cannot be closed.
     */
    public void closeClientSocket() throws IOException {
        this.application.getClient().getSocket().close();
    }

    /**
     * Method called when a hole have to be played (represented by a button)
     *
     * @param event Event of the action.
     */
    private void onChooseHole(ActionEvent event) {
        if (this.myTurnText.isVisible()) {
            this.displayMyTurnText(false);

            int position = this.myHolesPane.getChildren().indexOf(event.getSource());
            this.application.getClient().sendPacket(new HoleActionPacket(position));
        }
    }

}
