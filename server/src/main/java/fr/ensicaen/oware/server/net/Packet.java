package fr.ensicaen.oware.server.net;

import fr.ensicaen.oware.server.OwareServer;
import lombok.Getter;
import lombok.Setter;

public abstract class Packet {

    @Getter
    @Setter
    protected transient OwareServer server;

    void onReceive() {

    }

}
