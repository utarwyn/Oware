package fr.ensicaen.oware.client.model.sockets;

import fr.ensicaen.oware.client.applications.Main;
import fr.ensicaen.oware.client.model.packets.PacketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CapitalizeClient {

    private Main main;

    private String host;

    private int port;

    private PacketHandler packetHandler;

    public CapitalizeClient(Main main, String host, int port) {
        this.main = main;
        this.host = host;
        this.port = port;
        this.packetHandler = new PacketHandler(this.main);
    }

    public void connectToServer() {
        try (Socket socket = new Socket(this.host, this.port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            // out.println(new Packet().serialize());
            this.packetHandler.handle(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
