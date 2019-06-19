package fr.ensicaen.oware.server.game;

import fr.ensicaen.oware.server.OwareServer;
import fr.ensicaen.oware.server.util.CyclicIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    private Player firstPlayer;

    private Player secondPlayer;

    private Player currentPlayer;

    public Game(OwareServer server) {
        System.out.println("Game started!");
        this.firstPlayer = new Player(0, server.getCapitalizeServer().getFirstClient());
        this.secondPlayer = new Player(1, server.getCapitalizeServer().getSecondClient());
        this.currentPlayer = new Random().nextInt() > 0.5 ? this.firstPlayer : this.secondPlayer;
    }

    public void nextRound() {
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
                System.out.println("Game ended.");
                // Send the gameboard, TODO end-game packets
                this.sendGameboard();
            } else {
                // Now going to the next round!
                this.nextRound();
            }
        }
    }

    private Player getOpponent(Player player) {
        return player == this.firstPlayer ? this.secondPlayer : this.firstPlayer;
    }

    private void sendGameboard() {
        this.firstPlayer.sendGameBoard(this.secondPlayer.getHoles());
        this.secondPlayer.sendGameBoard(this.firstPlayer.getHoles());
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

        while (!this.currentPlayer.ownHole(hole) && (hole.getSeeds() == 2 || hole.getSeeds() == 3)) {
            this.currentPlayer.collectSeeds(hole.getSeeds());
            hole.setSeeds(0);

            hole = iterator.previous();
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

    private boolean hasEnded() {
        // rule 6: the opponent must play in the next round (he has to be fed)
        return !this.getOpponent(this.currentPlayer).canPlay();
    }

}
