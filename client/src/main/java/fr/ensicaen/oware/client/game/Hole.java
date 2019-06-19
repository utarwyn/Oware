package fr.ensicaen.oware.client.game;

import lombok.Getter;

@Getter
public class Hole {

    private int seeds;

    private boolean playable;

    @Override
    public String toString() {
        return String.valueOf(this.seeds);
    }

}
