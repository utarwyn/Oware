package fr.ensicaen.oware.client.net;

import fr.ensicaen.oware.client.applications.Main;
import lombok.Getter;
import lombok.Setter;

public abstract class Packet {

	@Getter
	@Setter
	protected transient Main main;

	public void onReceive() {

	}

}
