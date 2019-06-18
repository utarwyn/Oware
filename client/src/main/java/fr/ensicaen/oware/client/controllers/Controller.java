package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.OwareApp;
import fr.ensicaen.oware.client.stages.OwareStage;
import javafx.scene.Node;
import lombok.Setter;

/**
 * Represents an abstract controller for the Oware application.
 * This class is used to control a stage and link it with the main application.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@Setter
public abstract class Controller {

    /**
     * The custom stage which uses this controller
     */
    OwareStage stage;

    /**
     * The Oware application
     */
    OwareApp application;

    /**
     * Offset X of the window for the moving system if enabled
     */
    private double xOffset;

    /**
     * Offset Y of the window for the moving system if enabled
     */
    private double yOffset;

    /**
     * Called when the controller is initializing from the stage.
     */
    public abstract void load();

    /**
     * Allow a controller to enable moving system on the affected stage.
     */
    void enableMovingSystem(Node node) {
        this.xOffset = 0;
        this.yOffset = 0;

        node.setOnMousePressed(event -> {
            this.xOffset = this.stage.getX() - event.getScreenX();
            this.yOffset = this.stage.getY() - event.getScreenY();
        });
        node.setOnMouseDragged(event -> {
            this.stage.setX(event.getScreenX() + this.xOffset);
            this.stage.setY(event.getScreenY() + this.yOffset);
        });
    }

}
