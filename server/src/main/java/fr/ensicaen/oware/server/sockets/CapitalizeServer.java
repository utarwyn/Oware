package fr.ensicaen.oware.server.sockets;

import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class CapitalizeServer {

    private int port;

    private ServerSocket serverSocket;

    public CapitalizeServer(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(this.serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
