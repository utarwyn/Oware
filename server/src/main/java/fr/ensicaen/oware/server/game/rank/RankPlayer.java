package fr.ensicaen.oware.server.game.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RankPlayer implements Comparable<RankPlayer> {

    private String playerName;

    private int score;

    @Override
    public int compareTo(RankPlayer rankPlayer) {
        return this.score - rankPlayer.getScore();
    }
}
