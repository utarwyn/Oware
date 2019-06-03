package fr.ensicaen.oware.server.sockets;

import fr.ensicaen.oware.server.Main;
import fr.ensicaen.oware.server.game.Game;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class CapitalizeServer {

    private Main main;

    private int port;

    private ServerSocket serverSocket;

    private Capitalizer firstClient;

    private Capitalizer secondClient;

    public CapitalizeServer(Main main, int port) {
        this.main = main;
        this.port = port;
    }

    public void listen() {
        System.out.println("Server is running!");
        try {
            this.serverSocket = new ServerSocket(this.port);
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                if (this.firstClient == null || this.secondClient == null) {
                    Capitalizer capitalizer = new Capitalizer(this.serverSocket.accept());
                    if (this.firstClient == null) {
                        this.firstClient = capitalizer;
                        System.out.println("First client connected!");
                    } else if (this.secondClient == null) {
                        this.secondClient = capitalizer;
                        System.out.println("Second client connected!");
                        this.main.setGame(new Game());
                    }
                    pool.execute(capitalizer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
