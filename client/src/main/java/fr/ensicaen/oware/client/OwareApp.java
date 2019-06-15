package fr.ensicaen.oware.client;

import fr.ensicaen.oware.client.net.CapitalizeClient;
import fr.ensicaen.oware.client.stages.MenuStage;
import fr.ensicaen.oware.client.stages.OwareStage;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

/**
 * Main object of the Oware client.
 * It instanciates the JavaFX application and store core objects.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@Getter
public class OwareApp extends Application {

	/**
	 * Current JavaFX stage to display
	 */
	private OwareStage stage;

	/**
	 * Object to communicate with the Oware server
	 */
	private CapitalizeClient client;

	/**
	 * Construct the Oware application.
	 */
	public OwareApp() {
		this.client = new CapitalizeClient(this);
	}

	/**
	 * Entry point of the application.
	 *
	 * @param args Arguments passed to the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Change the currently displayed stage of the application.
	 *
	 * @param stage Stage to display
	 * @throws IOException throwed if the linked Fxml view cannot be loaded
	 */
	public void displayStage(OwareStage stage) throws IOException {
		if (this.stage != null) {
			this.stage.hide();
		}

		this.stage = stage;
		this.stage.start(this);
	}

	/**
	 * This method is called by JavaFx when the application starts.
	 *
	 * @param primaryStage Default stage of the application, we don't use it here.
	 * @throws IOException throwed if the default Oware stage cannot be loaded from the view file.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.displayStage(new MenuStage());
	}

}
