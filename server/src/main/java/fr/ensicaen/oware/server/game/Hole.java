package fr.ensicaen.oware.server.game;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Hole {

    private static final int DEFAULT_SEEDS = 4;

    @Setter
    private int seeds;

    public Hole() {
        this.seeds = DEFAULT_SEEDS;
    }
}
