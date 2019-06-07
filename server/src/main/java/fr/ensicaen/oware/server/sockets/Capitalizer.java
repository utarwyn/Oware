package fr.ensicaen.oware.server.sockets;

import fr.ensicaen.oware.server.packets.Packet;
import fr.ensicaen.oware.server.packets.PacketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Capitalizer implements Runnable {

    private Socket socket;

    private PacketHandler packetHandler;

    public Capitalizer(Socket socket) {
        this.socket = socket;
        this.packetHandler = new PacketHandler();
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String serializedPacket = reader.readLine();
            this.packetHandler.handle(serializedPacket);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + this.socket);
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Closed: " + this.socket);
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
