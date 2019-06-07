package fr.ensicaen.oware.client.model.sockets;

import fr.ensicaen.oware.client.applications.Main;
import fr.ensicaen.oware.client.model.packets.Packet;
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

    private Socket socket;

    private PacketHandler packetHandler;

    public CapitalizeClient(Main main, String host, int port) {
        this.main = main;
        this.host = host;
        this.port = port;
        this.packetHandler = new PacketHandler(this.main);
    }

    public void connectToServer() {
        try {
            this.socket = new Socket(this.host, this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            this.packetHandler.handle(reader.readLine());
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet) {
        try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)) {
            out.println(packet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
