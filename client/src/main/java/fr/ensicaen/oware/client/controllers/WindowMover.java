package fr.ensicaen.oware.controllers;

import javafx.scene.Node;
import javafx.stage.Stage;

public class WindowMover {

    private double xOffset;
    private double yOffset;

    public WindowMover(final Stage primaryStage, Node node) {
        this.xOffset = 0;
        this.yOffset = 0;
        node.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        node.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

}
