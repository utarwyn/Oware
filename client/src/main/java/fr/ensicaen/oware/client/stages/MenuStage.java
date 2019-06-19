package fr.ensicaen.oware.client.stages;

import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import lombok.Getter;

/**
 * Represents the Menu stage.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class MenuStage extends OwareStage {

    @Getter
    private boolean connectionError;

    /**
     * {@inheritDoc}
     */
    public MenuStage() {
        this(false);
    }

    /**
     * {@inheritDoc}
     */
    public MenuStage(boolean connectionError) {
        super("menu.fxml", "Oware | Connect to a server");
        this.connectionError = connectionError;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        // Load custom fonts for this stage.
        Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Bold.ttf"), 12);

        this.initStyle(StageStyle.TRANSPARENT);
        this.setResizable(false);
        this.getScene().setFill(null);
    }

}
