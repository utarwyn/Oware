package fr.ensicaen.oware.client.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.ensicaen.oware.client.applications.Main;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class creates a link between this client and the Oware server.
 * It is used to send and receive packet from the server.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@Getter
public class CapitalizeClient extends Thread {

	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Packet.class, new PacketTypeAdapter()).create();

	private Main main;

	private String host;

	private int port;

	private Socket socket;

	private PrintWriter outStream;

	public CapitalizeClient(Main main, String host, int port) {
		this.main = main;
		this.host = host;
		this.port = port;
	}

	/**
	 * Try to connect to the server and create the packet handler.
	 *
	 * @throws IOException Throwed if the client cannot connect to the server.
	 */
	public void connectToServer() throws IOException {
		this.socket = new Socket(this.host, this.port);
		this.outStream = new PrintWriter(this.socket.getOutputStream(), true);
		this.start();
	}

	/**
	 * Create a runnable to receive packets from the server.
	 */
	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String inputLine;

			while ((inputLine = reader.readLine()) != null) {
				this.handlePacket(inputLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.outStream.close();
	}

	/**
	 * Send a packet through network to the Oware server.
	 *
	 * @param packet Packet to send to the server.
	 */
	public void sendPacket(Packet packet) {
		this.outStream.println(GSON.toJson(packet, Packet.class));
	}

	/**
	 * Handle a specific packet from the server.
	 * @param serializedPacket Serialized packet received from the server.
	 */
	private void handlePacket(String serializedPacket) {
		Packet packet = GSON.fromJson(serializedPacket, Packet.class);
		if (packet != null) {
			packet.setMain(this.main);
			packet.onReceive();
		}
	}

}
