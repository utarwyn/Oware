package fr.ensicaen.oware.client.stages;

import com.google.gson.internal.GsonBuildConfig;
import fr.ensicaen.oware.client.OwareApp;
import fr.ensicaen.oware.client.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents an abstract stage for the Oware application.
 * This class is used to link stages with a view and a controller easily.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public abstract class OwareStage extends Stage {

    /**
     * The application favicon image
     */
    private static final Image FAVICON = new Image(OwareStage.class.getResourceAsStream("/images/favicon.png"));

    /**
     * Linked FXML view file
     */
    private String fxmlFile;

    /**
     * Controller linked with the stage and the view ("fx:controller" attribute)
     */
    private Controller controller;

    /**
     * Construct a custom stage for the Oware application.
     *
     * @param fxmlFile FXML view file to load
     * @param title    Title to display in the window bar
     */
    OwareStage(String fxmlFile, String title) {
        this.fxmlFile = fxmlFile;
        this.setTitle(title);
    }

    /**
     * Return the stored controller with a generic type.
     *
     * @param <T> Generic type of the controller. Must extends the Oware controller class.
     * @return The controller with the choosen type.
     */
    <T extends Controller> T getController() {
        return (T) this.controller;
    }

    /**
     * Start the stage and display it for the user.
     *
     * @param application The Oware application. Used to load the controller.
     * @throws IOException throwed if the stage cannot be loaded from the view file.
     */
    public void start(OwareApp application) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GameStage.class.getResource("/views/" + this.fxmlFile));
        Parent root = loader.load();

        this.controller = loader.getController();
        this.controller.setApplication(application);
        this.controller.setStage(this);
        this.controller.load();

        // Apply the Oware favicon if needed
        if (this.getIcons().isEmpty()) {
            this.getIcons().add(FAVICON);
        }

        this.setScene(new Scene(root));
        this.setup();
        this.show();
    }

    /**
     * Called when the stage is setting up (before the display).
     */
    public void setup() {
        // Not implemented
    }

}
