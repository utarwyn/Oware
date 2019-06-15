package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.stages.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
	private TextField hostnameTextField;

	@FXML
	private TextField portTextField;

	@FXML
	private Text errorMessage;

	@Override
	public void initialize() {
		// Nothing to initialize here
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
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Connection error!");
					alert.setHeaderText("Connection error!");
					alert.setContentText("We cannot connect you to the server.");
					alert.showAndWait();
				}
			} else {
				this.showErrorMessage("The port seems to be incorrect.");
			}
		} else {
			this.showErrorMessage("The hostname seems to be incorrect.");
		}
	}

	/**
	 * Try to connect to a specific server.
	 *
	 * @param hostname Server host to connect to
	 * @param port Server port to connect to
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
