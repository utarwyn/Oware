package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.net.Packet;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GameEndedPacket extends Packet {

    private EndType type;

    @Override
    public void onReceive() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End of the game!");
            alert.initOwner(this.application.getStage());

            if (this.type == EndType.WIN || this.type == EndType.LOSE) {
                alert.setHeaderText("You have " + (this.type == EndType.WIN ? "won" : "lose") + "!");
            } else {
                alert.setHeaderText("Nobody wins!");
            }

            alert.showAndWait();
        });
        System.out.println("end game! " + this.type);
    }

    public enum EndType {
        WIN, LOSE, DRAW
    }

}
