package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.net.Packet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Packet sent by the client to send the player name.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@NoArgsConstructor
@AllArgsConstructor
public class PlayerNamePacket extends Packet {

    private String playerName;

    @Override
    public void onReceive() {
        this.getCapitalizer().getPlayer().setName(this.playerName);
    }

}
