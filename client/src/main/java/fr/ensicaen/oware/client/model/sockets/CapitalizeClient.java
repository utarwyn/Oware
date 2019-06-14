package fr.ensicaen.oware.client.model.sockets;

import fr.ensicaen.oware.client.applications.Main;
import fr.ensicaen.oware.client.model.packets.Packet;
import fr.ensicaen.oware.client.model.packets.PacketHandler;
import lombok.Getter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class creates a link between this client and the Oware server.
 * It is used to send and receive packets from the server.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@Getter
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
    }

	/**
	 * Try to connect to the server and create the packet handler.
	 * @throws IOException Throwed if the client cannot connect to the server.
	 */
	public void connectToServer() throws IOException {
        this.socket = new Socket(this.host, this.port);
        this.packetHandler = new PacketHandler(this.socket, this.main);
        this.packetHandler.start();
    }

	/**
	 * Send a packet through network to the Oware server.
	 * @param packet Packet to send to the server.
	 * @throws IOException Throwed if the packet cannot be transmited to the server.
	 */
	public void sendPacket(Packet packet) throws IOException {
        try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)) {
            out.println(packet.toString());
        }
    }

}
