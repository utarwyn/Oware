package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.applications.Connection;
import fr.ensicaen.oware.client.applications.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
public class ConnectionController {

    @Setter
    private Connection connection;

    @FXML
    private TextField hostnameTextField;

    @FXML
    private TextField portTextField;

    public void onConnectButtonClick() {
        this.connection.getStage().close();
        Platform.runLater(() -> {
            try {
                new Main(this.hostnameTextField.getText(), Integer.parseInt(this.portTextField.getText())).start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
