package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.controllers.GameController;
import fr.ensicaen.oware.client.net.Packet;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GiveUpPacket extends Packet {

    private ActionType actionType;

    @Override
    public void onReceive() {
        // Received if the opponent wants to give up
        if (this.actionType == ActionType.PROPOSE) {
            System.out.println("The opponent wants to give up. You too?");
            GameController controller = this.application.getStage().getController();
            controller.enableGiveUpButton(true);
        }
    }

    public enum ActionType {
        PROPOSE, ACCEPT
    }

}
