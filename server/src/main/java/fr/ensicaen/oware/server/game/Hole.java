package fr.ensicaen.oware.server.game;

import lombok.Getter;

@Getter
public class Hole {

	private static final int DEFAULT_SEEDS = 4;

	private int seeds;

	public Hole() {
		this.seeds = DEFAULT_SEEDS;
	}
}
