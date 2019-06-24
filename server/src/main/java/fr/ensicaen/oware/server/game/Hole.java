package fr.ensicaen.oware.server.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hole {

    private static final int DEFAULT_SEEDS = 4;

    private int seeds;

    private boolean playable;

    Hole() {
        this.seeds = DEFAULT_SEEDS;
        this.playable = true;
    }

    @Override
    public String toString() {
        return "Hole #" + this.hashCode() +
                " {seeds=" + seeds +
                ", playable=" + playable +
                '}';
    }

}
