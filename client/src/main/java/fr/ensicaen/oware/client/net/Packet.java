package fr.ensicaen.oware.client.net;

import fr.ensicaen.oware.client.OwareApp;
import fr.ensicaen.oware.client.stages.GameStage;
import lombok.Getter;
import lombok.Setter;

/**
 * A packet which can be received or transmitted to the server.
 * All of these attributes are serialized to be passed to the server.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public abstract class Packet {

    /**
     * The Oware application. Can be used by the onReceive method.
     * It has the transient modifier because we don't want it in the serialization.
     */
    @Getter
    @Setter
    protected transient OwareApp application;

    /**
     * Method which can be implemented (or not) by each packet.
     * Called when the packet is received from the server.
     */
    public void onReceive() {
        // Not implemented
    }

}
