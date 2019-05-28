package fr.ensicaen.oware.controllers;

import fr.ensicaen.oware.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MainController {

    @Setter
    private Main main;

    @FXML
    private Pane mainPanel;

    public void createWindowMover() {
        new WindowMover(this.main.getStage(), this.mainPanel);
    }

}
