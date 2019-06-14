package fr.ensicaen.oware.client.applications;

import fr.ensicaen.oware.client.controllers.ConnectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

@Getter
public class Connection extends Application {

	private Stage stage;

	private ConnectionController connectionController;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
		this.stage.setTitle("Oware - Connection");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/views/connection.fxml"));
		Parent root = loader.load();

		this.connectionController = loader.getController();
		this.connectionController.setConnection(this);

		Scene scene = new Scene(root, 400, 400);
		this.stage.setScene(scene);
		this.stage.show();
	}
}
