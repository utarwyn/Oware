package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.net.Capitalizer;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class Player {

    private Capitalizer capitalizer;

    private Hole[] holes;

    public Player(Capitalizer capitalizer) {
        this.capitalizer = capitalizer;

        this.holes = new Hole[6];
        Arrays.fill(this.holes, new Hole());
    }

}
