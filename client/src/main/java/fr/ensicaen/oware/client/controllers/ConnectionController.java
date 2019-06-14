package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.applications.Connection;
import fr.ensicaen.oware.client.applications.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.regex.Pattern;

@Getter
public class ConnectionController {

	/**
	 * Regex to validate if a string represents an hostname.
	 */
	private static final Pattern REGEX_HOSTNAME = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");

	/**
	 * Regex to validate if a string represents a port number.
	 */
	private static final Pattern REGEX_PORT = Pattern.compile("^[0-9]{1,5}$");

	@Setter
	private Connection connection;

	@FXML
	private TextField hostnameTextField;

	@FXML
	private TextField portTextField;

	@FXML
	private Text errorMessage;

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

	private void tryToConnect(String hostname, int port) throws IOException {
		Main main = new Main(hostname, port);
		main.getCapitalizeClient().connectToServer();

		// Now changing stage to display the board game!
		this.connection.getStage().close();
		main.start(new Stage());
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
