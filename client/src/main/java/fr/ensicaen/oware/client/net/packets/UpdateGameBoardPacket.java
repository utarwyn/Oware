package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.game.Hole;
import fr.ensicaen.oware.client.net.Packet;

import java.util.List;

public class UpdateGameBoardPacket extends Packet {

	private List<Hole> gameboard;

	@Override
	public void onReceive() {
		System.out.println("I've just received the gameboard from the server!");
		System.out.println(this.gameboard);
	}

}
