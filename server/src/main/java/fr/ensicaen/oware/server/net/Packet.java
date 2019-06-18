package fr.ensicaen.oware.server.net;

import fr.ensicaen.oware.server.OwareServer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Packet {

    protected transient OwareServer server;

    protected transient Capitalizer capitalizer;

    public void onReceive() {
        // Not implemented here
    }

}
