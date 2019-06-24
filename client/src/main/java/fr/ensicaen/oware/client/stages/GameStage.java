package fr.ensicaen.oware.client.stages;

import fr.ensicaen.oware.client.controllers.GameController;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Represents the Game stage (main stage with all components).
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class GameStage extends OwareStage {

    /**
     * {@inheritDoc}
     */
    public GameStage() {
        super("game.fxml", "Oware | Game");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        this.initStyle(StageStyle.TRANSPARENT);
        this.setResizable(false);
        this.setOnCloseRequest(event -> {
            try {
                GameController controller = this.getController();
                controller.closeClientSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Platform.exit();
            System.exit(0);
        });

        // Load custom fonts for this stage.
        Font.loadFont(getClass().getResourceAsStream("/fonts/BreeSerif-Regular.otf"), 12);

        getScene().setFill(null);
    }

}
