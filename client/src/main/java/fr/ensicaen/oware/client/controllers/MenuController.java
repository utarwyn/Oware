package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.stages.GameStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Represents the menu controller.
 * Used by the stage {@link fr.ensicaen.oware.client.stages.MenuStage}.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class MenuController extends Controller {

    /**
     * Regex to validate if a string represents an hostname.
     */
    private static final Pattern REGEX_HOSTNAME = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");

    /**
     * Regex to validate if a string represents a port number.
     */
    private static final Pattern REGEX_PORT = Pattern.compile("^[0-9]{1,5}$");

    @FXML
    private Pane mainPanel;

    @FXML
    private TextField hostnameTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Text errorMessage;

    @FXML
    private Text copyright;

    @Override
    public void load() {
        // Enable the moving system for the root panel
        this.enableMovingSystem(this.mainPanel);

        // Update version in the copyright component
        this.copyright.setText(this.copyright.getText().replace("${version}", this.application.getVersion()));

        // Send the form when key enter is pressed in hostname/port fields
        this.hostnameTextField.setOnKeyPressed(this::onFieldKeyPressed);
        this.portTextField.setOnKeyPressed(this::onFieldKeyPressed);
    }

    /**
     * Called when the button for connecting is clicked.
     */
    public void onConnectButtonClick() {
        String hostname = this.hostnameTextField.getText();
        String port = this.portTextField.getText();

        this.errorMessage.setVisible(false);

        if (REGEX_HOSTNAME.matcher(hostname).matches()) {
            if (REGEX_PORT.matcher(port).matches()) {
                try {
                    this.tryToConnect(hostname, Integer.parseInt(port));
                } catch (IOException e) {
                    this.showErrorMessage("Cannot connect to the server. Please retry.");
                }
            } else {
                this.showErrorMessage("The port seems to be incorrect.");
            }
        } else {
            this.showErrorMessage("The hostname seems to be incorrect.");
        }
    }

    /**
     * Called when the button for closing the menu is clicked.
     */
    public void onCloseButtonClick() {
        this.stage.close();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Method called when a key is pressed when focusing an input field.
     *
     * @param event Key Event fired by JavaFX
     */
    private void onFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.onConnectButtonClick();
        }
    }

    /**
     * Try to connect to a specific server.
     *
     * @param hostname Server host to connect to
     * @param port     Server port to connect to
     * @throws IOException Throwed if the client cannot connect to the server.
     */
    private void tryToConnect(String hostname, int port) throws IOException {
        this.application.getClient().connectToServer(hostname, port);
        this.application.displayStage(new GameStage());
    }

    /**
     * Show an error message in the error label.
     *
     * @param message Message to display
     */
    private void showErrorMessage(String message) {
        this.errorMessage.setText(message);
        this.errorMessage.setVisible(true);
    }

}
