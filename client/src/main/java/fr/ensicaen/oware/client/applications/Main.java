package fr.ensicaen.oware.client.applications;

import fr.ensicaen.oware.client.controllers.MainController;
import fr.ensicaen.oware.client.model.sockets.CapitalizeClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import java.io.IOException;

@Getter
public class Main extends Application {

    private Parent root;

    private Stage stage;

    private Scene scene;

    private MainController mainController;

    private String hostname;

    private int port;

    private CapitalizeClient capitalizeClient;

    public Main(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        this.buildRoot();
        primaryStage.setTitle("Oware");
        this.buildStage();
        this.buildScene();
        this.stage.show();

        (new Thread(() -> {
            this.capitalizeClient = new CapitalizeClient(this, this.hostname, this.port);
            capitalizeClient.connectToServer();
        })).start();
    }

    private void buildRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/main.fxml"));
        this.root = loader.load();
        this.mainController = loader.getController();
        this.mainController.setMain(this);
        this.mainController.createWindowMover();
    }

    private void buildScene() {
        this.scene = new Scene(root, 490, 490);
        this.scene.setFill(null);
        this.stage.setResizable(false);
        this.stage.setScene(this.scene);
    }

    private void buildStage() {
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.setResizable(false);
    }

}
