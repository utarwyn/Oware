package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.game.rank.RankPlayer;
import fr.ensicaen.oware.server.net.Capitalizer;
import fr.ensicaen.oware.server.net.packets.GameEndedPacket;
import fr.ensicaen.oware.server.net.packets.GiveUpPacket;
import fr.ensicaen.oware.server.net.packets.PlayPacket;
import fr.ensicaen.oware.server.net.packets.UpdateGameBoardPacket;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

public class Player {

    static final int HOLES_PER_PLAYER = 6;

    static final int MAX_COLLECTED_SEEDS = 25;

    static final int SEEDS_BEFORE_GIVEUP = 10;

    private Capitalizer capitalizer;

    @Getter
    private int index;

    @Getter
    private Hole[] holes;

    @Getter
    private int collectedSeeds;

    @Getter
    @Setter
    private boolean giveUp;

    @Getter
    @Setter
    private String name;

    public Player(int index, Capitalizer capitalizer) {
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

    void sendGameBoard(boolean canGiveUp, Hole[] opponentHoles) {
        this.capitalizer.sendPacket(new UpdateGameBoardPacket(this.holes, opponentHoles, this.collectedSeeds, canGiveUp));
    }

    void sendPlayAction() {
        this.capitalizer.sendPacket(new PlayPacket());
    }

    void sendGiveUpProposal() {
        this.capitalizer.sendPacket(new GiveUpPacket(GiveUpPacket.ActionType.PROPOSE));
    }

    void sendEndGame(GameEndedPacket.EndType endType, List<RankPlayer> topScores) {
        this.capitalizer.sendPacket(new GameEndedPacket(endType, topScores));
    }

}
