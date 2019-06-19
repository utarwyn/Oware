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
        this.firstPlayer = new Player(server.getCapitalizeServer().getFirstClient());
        this.secondPlayer = new Player(server.getCapitalizeServer().getSecondClient());
        this.currentPlayer = new Random().nextInt() > 0.5 ? this.firstPlayer : this.secondPlayer;
    }

    public void nextRound() {
        // Changing current player
        this.currentPlayer = this.currentPlayer == this.firstPlayer ? this.secondPlayer : this.firstPlayer;

        // Send the gameboard to players
        this.firstPlayer.sendGameBoard(this.secondPlayer.getHoles());
        this.secondPlayer.sendGameBoard(this.firstPlayer.getHoles());

        // And send the play packet to the current player
        this.currentPlayer.sendPlayAction();
    }

    public void play(Player player, int position) {
        // Check if the action is valid before continuing...
        if (!this.checkActionValidity(player, position)) {
            return;
        }

        int playerIndex = this.firstPlayer == player ? 0 : 1;
        System.out.println("#" + playerIndex + " playing at " + position + "...");

        // Generated a unique list with all holes to manage them
        // (and also a cyclic iterator to browse through them)
        List<Hole> holes = new ArrayList<>();
        Collections.addAll(holes, this.firstPlayer.getHoles());
        Collections.addAll(holes, this.secondPlayer.getHoles());

        int originPosition = playerIndex * Player.HOLES_PER_PLAYER + position;
        CyclicIterator<Hole> iterator = new CyclicIterator<>(holes, originPosition);

        // Rule 3: move seeds from a hole position (from 0 to 2 * HOLES_PER_PLAYER - 1) to next ones
        this.moveSeedsFromHole(iterator);

        // Rule 4: collect seeds if holes got 2 or 3 of them
        this.collectSeeds(iterator);

        // Now going to the next round!
        this.nextRound();
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

            // rule 5: cannot fill the origin hole
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

}
