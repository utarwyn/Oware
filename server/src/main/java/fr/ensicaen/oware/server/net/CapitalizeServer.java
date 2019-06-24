package fr.ensicaen.oware.server.net;

import fr.ensicaen.oware.server.OwareServer;
import fr.ensicaen.oware.server.game.Game;
import fr.ensicaen.oware.server.game.Player;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;

@Getter
public class CapitalizeServer {

    private OwareServer server;

    private int port;

    private ServerSocket serverSocket;

    private Capitalizer firstClient;

    private Capitalizer secondClient;

    public CapitalizeServer(OwareServer server, int port) {
        this.server = server;
        this.port = port;
    }

    public void listen() {
        System.out.println("Server is running!");
        try {
            this.serverSocket = new ServerSocket(this.port);

            while (!this.serverSocket.isClosed()) {
                if (this.firstClient == null || this.secondClient == null) {
                    Capitalizer capitalizer = new Capitalizer(this.server, this.serverSocket.accept());

                    capitalizer.initialize();

                    if (this.firstClient == null) {
                        this.firstClient = capitalizer;
                        capitalizer.setPlayer(new Player(0, capitalizer));
                        System.out.println("First client connected!");
                    } else if (this.secondClient == null) {
                        this.secondClient = capitalizer;
                        capitalizer.setPlayer(new Player(1, capitalizer));
                        System.out.println("Second client connected!");

                        this.server.setGame(new Game(this.server));
                        this.server.getGame().nextRound();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastPacket(Packet packet) {
        this.firstClient.sendPacket(packet);
        this.secondClient.sendPacket(packet);
    }

}
