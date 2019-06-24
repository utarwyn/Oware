package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.controllers.GameController;
import fr.ensicaen.oware.client.net.Packet;
import javafx.application.Platform;

/**
 * Packet sent by the server to say to a client to play.
 * It does not contains any specific information.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class PlayPacket extends Packet {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceive() {
        System.out.println("It's my turn!");
        Platform.runLater(() -> {
            GameController controller = this.application.getStage().getController();
            controller.displayMyTurnText(true);
            controller.enableGiveUpButton(true);
        });
    }

}
