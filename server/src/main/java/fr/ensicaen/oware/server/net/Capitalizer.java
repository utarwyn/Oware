package fr.ensicaen.oware.server.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.ensicaen.oware.server.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Capitalizer extends Thread {

	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Packet.class, new PacketTypeAdapter()).create();

	private Main main;

	private Socket socket;

	private PrintWriter outStream;

	Capitalizer(Main main, Socket socket) {
		this.socket = socket;
		this.main = main;
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
			e.printStackTrace();
		}

		this.outStream.close();
	}

	public void sendPacket(Packet packet) {
		this.outStream.println(GSON.toJson(packet, Packet.class));
	}

	/**
	 * Handle a specific packet from a client.
	 * @param seralizedPacket Serialized packet received from a client.
	 */
	private void handlePacket(String seralizedPacket) {
		Packet packet = GSON.fromJson(seralizedPacket, Packet.class);
		if (packet != null) {
			packet.setMain(this.main);
			packet.onReceive();
		}
	}

}
