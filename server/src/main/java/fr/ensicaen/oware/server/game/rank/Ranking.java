package fr.ensicaen.oware.server.game.rank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ranking {

    private static final Type RANK_PLAYERS_TYPE = new TypeToken<ArrayList<RankPlayer>>() {
    }.getType();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final int MAX_SCORES = 10;

    private static final File SCORES_FILE = new File("./scores.json");

    public Ranking() {
        if (!SCORES_FILE.exists()) {
            SCORES_FILE.getParentFile().mkdirs();
        }
    }

    private List<RankPlayer> readTopScores() {
        try (FileReader fileReader = new FileReader(SCORES_FILE)) {
            return GSON.fromJson(fileReader, RANK_PLAYERS_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void writeTopScores(List<RankPlayer> rankPlayers) {
        try (FileWriter fileWriter = new FileWriter(this.SCORES_FILE)) {
            GSON.toJson(rankPlayers, RANK_PLAYERS_TYPE, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<RankPlayer> handleNewScore(RankPlayer rankPlayer) {
        List<RankPlayer> rankPlayers = this.readTopScores();
        rankPlayers.add(rankPlayer);
        rankPlayers = rankPlayers.stream().sorted().limit(MAX_SCORES).collect(Collectors.toList());
        this.writeTopScores(rankPlayers);
        return rankPlayers;
    }

}
