package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.game.Game;
import fr.ensicaen.oware.server.game.Player;
import fr.ensicaen.oware.server.net.Packet;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GiveUpPacket extends Packet {

    private ActionType actionType;

    @Override
    public void onReceive() {
        Game game = this.server.getGame();
        Player player = this.capitalizer.getPlayer();

        if (actionType == ActionType.PROPOSE) {
            game.handleGiveUpProposal(player);
        } else if (actionType == ActionType.ACCEPT) {
            game.handleGiveUp(player);
        }
    }

    public enum ActionType {
        PROPOSE, ACCEPT
    }

}
