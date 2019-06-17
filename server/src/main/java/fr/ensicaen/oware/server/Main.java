package fr.ensicaen.oware.server;

import fr.ensicaen.oware.server.game.Game;
import fr.ensicaen.oware.server.net.CapitalizeServer;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Main {

    @Setter
    private Game game;

    private CapitalizeServer capitalizeServer;

    public Main() {
        this.capitalizeServer = new CapitalizeServer(this, 59898);
        this.capitalizeServer.listen();
    }

    public static void main(String[] args) {
        new Main();
    }

}
