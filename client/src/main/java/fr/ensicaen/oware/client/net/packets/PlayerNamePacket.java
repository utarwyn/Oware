package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.net.Packet;
import lombok.AllArgsConstructor;

/**
 * Packet sent by the client to send the player name.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@AllArgsConstructor
public class PlayerNamePacket extends Packet {

    private String playerName;

}
