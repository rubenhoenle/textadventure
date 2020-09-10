package de.dhbw.project.score;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {
    @SerializedName("scores")
    private List<Score> scores;

    public ScoreBoard() {
        this.scores = new ArrayList<>();
    }

    public ScoreBoard(List<Score> scores) {
        this.scores = scores;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void printScoreBoard() {
        if (scores.size() == 0) {
            System.out.println("There are no scores to show!");
        } else {
            Collections.sort(scores);
            for (int i = 0; i < scores.size(); i++) {
                System.out.println((i + 1) + ". " + scores.get(i).toString());
            }
        }
    }

}
