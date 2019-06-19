package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.net.Capitalizer;
import fr.ensicaen.oware.server.net.packets.GameEndedPacket;
import fr.ensicaen.oware.server.net.packets.PlayPacket;
import fr.ensicaen.oware.server.net.packets.UpdateGameBoardPacket;
import lombok.Getter;

import java.util.Arrays;

public class Player {

    static final int HOLES_PER_PLAYER = 6;

    static final int MAX_COLLECTED_SEEDS = 25;

    private Capitalizer capitalizer;

    @Getter
    private int index;

    @Getter
    private Hole[] holes;

    @Getter
    private int collectedSeeds;

    Player(int index, Capitalizer capitalizer) {
        this.index = index;
        this.capitalizer = capitalizer;
        this.holes = new Hole[HOLES_PER_PLAYER];

        this.capitalizer.setPlayer(this);

        for (int i = 0; i < this.holes.length; i++) {
            this.holes[i] = new Hole();
        }
    }

    boolean ownHole(Hole hole) {
        return Arrays.asList(this.holes).contains(hole);
    }

    boolean hasEmptyHoles() {
        return Arrays.stream(this.holes).allMatch(hole -> hole.getSeeds() == 0);
    }

    boolean canPlay() {
        return Arrays.stream(this.holes).anyMatch(Hole::isPlayable);
    }

    int[] getHoleSeeds() {
        return Arrays.stream(this.holes).mapToInt(Hole::getSeeds).toArray();
    }

    void collectSeeds(int n) {
        this.collectedSeeds += n;
    }

    void sendGameBoard(Hole[] opponentHoles) {
        this.capitalizer.sendPacket(new UpdateGameBoardPacket(this.holes, opponentHoles, this.collectedSeeds));
    }

    void sendPlayAction() {
        this.capitalizer.sendPacket(new PlayPacket());
    }

    void sendEndGame(GameEndedPacket.EndType endType) {
        this.capitalizer.sendPacket(new GameEndedPacket(endType));
    }

}
