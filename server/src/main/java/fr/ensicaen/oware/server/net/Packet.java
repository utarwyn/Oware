package fr.ensicaen.oware.server.net;

import fr.ensicaen.oware.server.Main;
import lombok.Getter;
import lombok.Setter;

public abstract class Packet {

    @Getter
    @Setter
    protected transient Main main;

    public void onReceive() {

    }

}
