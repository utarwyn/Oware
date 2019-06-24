package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.net.model.GameBoard;
import fr.ensicaen.oware.client.net.model.Hole;
import fr.ensicaen.oware.client.net.packets.GiveUpPacket;
import fr.ensicaen.oware.client.net.packets.HoleActionPacket;
import fr.ensicaen.oware.client.render.HoleNode;
import fr.ensicaen.oware.client.stages.MenuStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    private List<HoleNode> myHoles;

    private List<HoleNode> opponentHoles;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        this.myHoles = new ArrayList<>();
        this.opponentHoles = new ArrayList<>();

        this.addHoleNode(this.myHoles, new HoleNode(132, 302));
        this.addHoleNode(this.myHoles, new HoleNode(193, 363));
        this.addHoleNode(this.myHoles, new HoleNode(273, 410));
        this.addHoleNode(this.myHoles, new HoleNode(365, 410));
        this.addHoleNode(this.myHoles, new HoleNode(437, 363));
        this.addHoleNode(this.myHoles, new HoleNode(498, 302));
        this.addHoleNode(this.opponentHoles, new HoleNode(498, 214, true));
        this.addHoleNode(this.opponentHoles, new HoleNode(437, 153, true));
        this.addHoleNode(this.opponentHoles, new HoleNode(357, 106, true));
        this.addHoleNode(this.opponentHoles, new HoleNode(265, 106, true));
        this.addHoleNode(this.opponentHoles, new HoleNode(193, 153, true));
        this.addHoleNode(this.opponentHoles, new HoleNode(132, 214, true));

        for (HoleNode holeNode : this.myHoles) {
            holeNode.getGroup().setOnMouseClicked(this::onChooseHole);
        }

        this.enableMovingSystem(this.mainPanel);
    }

    /**
     * Update the stage with gameboard information. Temporary method.
     */
    public void updateGameBoard(GameBoard gameBoard) {
        // Create an iterator for each player
        Iterator<HoleNode> myHoles = this.myHoles.iterator();
        Iterator<HoleNode> opHoles = this.opponentHoles.iterator();

        // For now, we just replacing values in texts/buttons in stage panes
        for (int i = 0; i < gameBoard.getPlayerHoles().length; i++) {
            Hole hole = gameBoard.getPlayerHoles()[i];
            myHoles.next().update(hole.getSeeds(), !hole.isPlayable() || hole.getSeeds() == 0);
        }
        for (int i = 0; i < gameBoard.getOpponentHoles().length; i++) {
            Hole hole = gameBoard.getOpponentHoles()[i];
            opHoles.next().update(hole.getSeeds(), true);
        }

        // Update collected seeds
        this.collected.setText(gameBoard.getCollectedSeeds() + " SEEDS COLLECTED");

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
     * Called when the button for quitting the current game is clicked.
     */
    public void onCloseButtonClick() {
        try {
            this.closeClientSocket();
            this.stage.close();
            Platform.exit();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void onChooseHole(MouseEvent event) {
        Optional<HoleNode> holeNode = this.myHoles.stream()
                .filter(hole -> hole.getGroup() == event.getSource())
                .findFirst();

        if (this.myTurnText.isVisible() && holeNode.isPresent() && !holeNode.get().isDisabled()) {
            this.displayMyTurnText(false);

            int position = this.myHoles.indexOf(holeNode.get());
            this.application.getClient().sendPacket(new HoleActionPacket(position));
        }
    }

    /**
     * Add a new hole node to the stage.
     *
     * @param list List where do the node has to be added
     * @param node Node to add to the list and the stage
     */
    private void addHoleNode(List<HoleNode> list, HoleNode node) {
        list.add(node);
        this.mainPanel.getChildren().add(node.getGroup());
    }

}
