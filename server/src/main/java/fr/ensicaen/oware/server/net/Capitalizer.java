package fr.ensicaen.oware.server.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.ensicaen.oware.server.OwareServer;
import fr.ensicaen.oware.server.game.Player;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Capitalizer extends Thread {

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Packet.class, new PacketTypeAdapter()).create();

    private OwareServer server;

    @Getter
    @Setter
    private Player player;

    private Socket socket;

    private PrintWriter outStream;

    Capitalizer(OwareServer server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }

    void initialize() throws IOException {
        this.outStream = new PrintWriter(this.socket.getOutputStream(), true);
        this.start();
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                this.handlePacket(inputLine);
            }
        } catch (Exception e) {
            System.out.println("Client disconnected!");
        }

        this.outStream.close();
    }

    public void sendPacket(Packet packet) {
        this.outStream.println(GSON.toJson(packet, Packet.class));
    }

    /**
     * Handle a specific packet from a client.
     *
     * @param seralizedPacket Serialized packet received from a client.
     */
    private void handlePacket(String seralizedPacket) {
        Packet packet = GSON.fromJson(seralizedPacket, Packet.class);
        if (packet != null) {
            packet.setCapitalizer(this);
            packet.setServer(this.server);
            packet.onReceive();
        }
    }

}
