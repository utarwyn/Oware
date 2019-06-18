package fr.ensicaen.oware.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        this.enableMovingSystem(this.mainPanel);
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
