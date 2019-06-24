package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.OwareServer;
import fr.ensicaen.oware.server.game.rank.RankPlayer;
import fr.ensicaen.oware.server.game.rank.Ranking;
import fr.ensicaen.oware.server.net.CapitalizeServer;
import fr.ensicaen.oware.server.net.packets.GameEndedPacket;
import fr.ensicaen.oware.server.util.CyclicIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    private static final int MAX_SEEDS_INGAME = 6;

    private CapitalizeServer server;

    private Player firstPlayer;

    private Player secondPlayer;

    private Player currentPlayer;

    private Ranking ranking;

    public Game(OwareServer server) {
        System.out.println("Game started!");

        this.server = server.getCapitalizeServer();
        this.firstPlayer = this.server.getFirstClient().getPlayer();
        this.secondPlayer = this.server.getSecondClient().getPlayer();
        this.currentPlayer = new Random().nextInt() > 0.5 ? this.firstPlayer : this.secondPlayer;
        this.ranking = new Ranking();
    }

    public void nextRound() {
        // rule 9: reset players' give up state
        this.firstPlayer.setGiveUp(false);
        this.secondPlayer.setGiveUp(false);

        // Changing current player
        this.currentPlayer = this.currentPlayer == this.firstPlayer ? this.secondPlayer : this.firstPlayer;

        // Send the gameboard to players
        this.sendGameboard();

        // And send the play packet to the current player
        this.currentPlayer.sendPlayAction();
    }

    public void play(Player player, int position) {
        // Check if the action is valid before continuing...
        if (!this.checkActionValidity(player, position)) {
            return;
        }

        System.out.println("#" + (player.getIndex() + 1) + " playing at " + position + "...");

        // Generated a unique list with all holes to manage them
        // (and also a cyclic iterator to browse through them)
        List<Hole> holes = new ArrayList<>();
        Collections.addAll(holes, this.firstPlayer.getHoles());
        Collections.addAll(holes, this.secondPlayer.getHoles());

        int originPosition = player.getIndex() * Player.HOLES_PER_PLAYER + position;
        CyclicIterator<Hole> iterator = new CyclicIterator<>(holes, originPosition);

        // Rule 6: check if this hole can be played
        if (holes.get(originPosition).isPlayable()) {
            // Rule 3: move seeds from a hole position (from 0 to 2 * HOLES_PER_PLAYER - 1) to next ones
            this.moveSeedsFromHole(iterator);

            // Rule 4: collect seeds if holes got 2 or 3 of them
            this.collectSeeds(iterator);

            // Rule 6: update playable holes for the next player (if he need to feed the opponent)
            this.updatePlayableHolesOf(getOpponent(player));

            // Rule 6,8: check if the game has ended
            if (this.hasEnded()) {
                this.end(this.getWinner());
            } else {
                // Now going to the next round!
                this.nextRound();
            }
        }
    }

    public void handleGiveUpProposal(Player player) {
        if (this.canGiveUp() && !player.isGiveUp() && this.currentPlayer == player) {
            player.setGiveUp(true);
            getOpponent(player).sendGiveUpProposal();
        }
    }

    public void handleGiveUp(Player player) {
        if (this.canGiveUp() && !player.isGiveUp() && getOpponent(this.currentPlayer) == player) {
            player.setGiveUp(true);

            // In some cases, there is no winner.
            int seeds = this.getSeedsNb();

            if (seeds < 6 && this.firstPlayer.getCollectedSeeds() <= 24
                    && this.secondPlayer.getCollectedSeeds() <= 24) {
                this.end(null);
            }
            // Else, we need to distribute all seeds to players
            else {
                // player is one that accept the game ending
                getOpponent(player).collectSeeds(seeds / 2);
                player.collectSeeds((int) Math.ceil(seeds / 2f));

                // Empty the gameboard
                for (Hole hole : this.firstPlayer.getHoles()) {
                    hole.setSeeds(0);
                }
                for (Hole hole : this.secondPlayer.getHoles()) {
                    hole.setSeeds(0);
                }

                // End the game!
                this.end(this.getWinner());
            }
        }
    }

    private Player getOpponent(Player player) {
        return player == this.firstPlayer ? this.secondPlayer : this.firstPlayer;
    }

    private void sendGameboard() {
        boolean giveUp = this.canGiveUp();
        this.firstPlayer.sendGameBoard(giveUp, this.secondPlayer.getHoles());
        this.secondPlayer.sendGameBoard(giveUp, this.firstPlayer.getHoles());
    }

    private boolean checkActionValidity(Player player, int position) {
        return this.currentPlayer == player && position >= 0 && position < Player.HOLES_PER_PLAYER &&
                player.getHoles()[position].getSeeds() > 0;
    }

    private void moveSeedsFromHole(CyclicIterator<Hole> iterator) {
        Hole origin = iterator.next();
        int seeds = origin.getSeeds();

        origin.setSeeds(0);

        while (seeds > 0) {
            Hole hole = iterator.next();

            // rule 5: cannot fill the original hole
            if (hole != origin) {
                hole.setSeeds(hole.getSeeds() + 1);
                seeds--;
            }
        }
    }

    private void collectSeeds(CyclicIterator<Hole> iterator) {
        Hole hole = iterator.current();
        Player opponent = getOpponent(this.currentPlayer);
        int[] seedStore = opponent.getHoleSeeds();

        while (!this.currentPlayer.ownHole(hole) && (hole.getSeeds() == 2 || hole.getSeeds() == 3)) {
            this.currentPlayer.collectSeeds(hole.getSeeds());
            hole.setSeeds(0);

            hole = iterator.previous();
        }

        // rule 7: if all holes of the opponent are empty, we have to rollback the action
        if (opponent.hasEmptyHoles()) {
            for (int i = 0; i < Player.HOLES_PER_PLAYER; i++) {
                opponent.getHoles()[i].setSeeds(seedStore[i]);
            }
        }
    }

    private void updatePlayableHolesOf(Player player) {
        Player opponent = this.getOpponent(player);

        int i = 0;
        for (Hole hole : player.getHoles()) {
            // rule 6: we need to feed one of opponent holes if needed
            if (opponent.hasEmptyHoles()) {
                hole.setPlayable(hole.getSeeds() > Player.HOLES_PER_PLAYER - 1 - i);
            } else {
                hole.setPlayable(true);
            }

            i++;
        }
    }

    private void end(Player winner) {
        // Send the gameboard first!
        this.sendGameboard();

        if (winner != null) {
            RankPlayer       rankPlayer = new RankPlayer(winner.getName(), winner.getCollectedSeeds());
            List<RankPlayer> topScores  = this.ranking.handleNewScore(rankPlayer);
            winner.sendEndGame(GameEndedPacket.EndType.WIN, topScores);
            this.getOpponent(winner).sendEndGame(GameEndedPacket.EndType.LOSE, topScores);
        } else {
            List<RankPlayer> topScores = this.ranking.readTopScores();
            this.server.broadcastPacket(new GameEndedPacket(GameEndedPacket.EndType.DRAW, topScores));
        }
    }

    private boolean canGiveUp() {
        return getSeedsNb() <= Player.SEEDS_BEFORE_GIVEUP;
    }

    private boolean hasEnded() {
        // rule 6: the opponent must play in the next round (he has to be fed)
        // rule 8: game ending conditions
        return !this.getOpponent(this.currentPlayer).canPlay()
                || this.firstPlayer.getCollectedSeeds() >= Player.MAX_COLLECTED_SEEDS
                || this.secondPlayer.getCollectedSeeds() >= Player.MAX_COLLECTED_SEEDS
                || this.getSeedsNb() <= MAX_SEEDS_INGAME;
    }

    private int getSeedsNb() {
        return Arrays.stream(this.firstPlayer.getHoleSeeds()).sum() + Arrays.stream(this.secondPlayer.getHoleSeeds()).sum();
    }

    private Player getWinner() {
        // rule 8: end of the game
        if (this.firstPlayer.getCollectedSeeds() > this.secondPlayer.getCollectedSeeds()) {
            return this.firstPlayer;
        } else if (this.firstPlayer.getCollectedSeeds() < this.secondPlayer.getCollectedSeeds()) {
            return this.secondPlayer;
        } else {
            return null;
        }
    }

}
