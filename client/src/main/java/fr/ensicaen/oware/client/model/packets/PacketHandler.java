package fr.ensicaen.oware.client.model.packets;

import fr.ensicaen.oware.client.applications.Main;
import fr.ensicaen.oware.client.game.GameBoard;
import fr.ensicaen.oware.client.model.packets.datas.ActionData;
import fr.ensicaen.oware.client.model.packets.datas.GameBoardData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class PacketHandler extends Thread {

	private Socket socket;

	private Main main;

	public PacketHandler(Socket socket, Main main) {
		this.socket = socket;
		this.main = main;
	}

	@Override
	public void run() {
		while (!this.socket.isClosed()) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				this.handle(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handle a serialized packet from the server.
	 * @param serializedPacket The serialized packet to handle.
	 */
	private void handle(String serializedPacket) {
		Packet packet = Packet.GSON.fromJson(serializedPacket, Packet.class);
		if (packet.getId() == ActionData.ID) {
			ActionData actionData = packet.deserializeData(ActionData.class);
			if (actionData.getAction() == ActionData.Action.PLAY) {
				System.out.println("PLAY");
			}
		} else if (packet.getId() == GameBoardData.ID) {
			GameBoardData gameBoardData = packet.deserializeData(GameBoardData.class);
			GameBoard gameBoard = new GameBoard(gameBoardData.getCurrentPlayerHoles(), gameBoardData.getOpponentHoles());
			System.out.println("Player board : " + Arrays.toString(gameBoard.getPlayerHoles()));
			System.out.println("Opponent player board : " + Arrays.toString(gameBoard.getOpponentHoles()));
		}
	}
}
