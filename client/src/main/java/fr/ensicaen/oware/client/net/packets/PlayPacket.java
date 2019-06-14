package fr.ensicaen.oware.client.net.packets;

import fr.ensicaen.oware.client.net.Packet;

public class PlayPacket extends Packet {

	@Override
	public void onReceive() {
		System.out.println("It's my turn to play.");
	}

}
