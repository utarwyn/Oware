package fr.ensicaen.oware.client.model.sockets;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@AllArgsConstructor
public class CapitalizeClient {

    private String host;

    private int port;

    public void connectToServer() {
        try {
            Socket socket = new Socket(this.host, this.port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
