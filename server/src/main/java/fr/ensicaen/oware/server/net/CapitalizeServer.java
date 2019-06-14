package fr.ensicaen.oware.server.net;

import fr.ensicaen.oware.server.Main;
import fr.ensicaen.oware.server.game.Game;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;

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

			while (!this.serverSocket.isClosed()) {
				if (this.firstClient == null || this.secondClient == null) {
					Capitalizer capitalizer = new Capitalizer(this.main, this.serverSocket.accept());

					capitalizer.initialize();

					if (this.firstClient == null) {
						this.firstClient = capitalizer;
						System.out.println("First client connected!");
					} else if (this.secondClient == null) {
						this.secondClient = capitalizer;
						System.out.println("Second client connected!");

						this.main.setGame(new Game(this.main));
						this.main.getGame().nextRound();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastPacket(Packet packet) {
		this.firstClient.sendPacket(packet);
		this.secondClient.sendPacket(packet);
	}

}
