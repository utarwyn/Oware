package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.net.Packet;

/**
 * Packet sent to the server when the player wants to move seeds of a hole.
 * It contains the position of the hole where seeds have to be taken.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class HoleActionPacket extends Packet {

    private int position;

    public HoleActionPacket(int position) {
        this.position = position;
    }

}
