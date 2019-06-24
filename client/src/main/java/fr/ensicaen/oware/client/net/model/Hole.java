package fr.ensicaen.oware.client.net.model;

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
