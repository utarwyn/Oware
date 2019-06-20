package fr.ensicaen.oware.client.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameBoard {

    private Hole[] playerHoles;

    private Hole[] opponentHoles;

    private int collectedSeeds;

    private boolean canGiveUp;

}
