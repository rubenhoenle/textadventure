package de.dhbw.project.score;

import com.google.gson.annotations.SerializedName;

import java.util.concurrent.TimeUnit;

public class Score implements Comparable<Score> {
    @SerializedName("player")
    private String playerName;
    @SerializedName("points")
    private Integer points;
    @SerializedName("time")
    private Long time;

    public Score(String playerName, int points, long time) {
        this.playerName = playerName;
        this.points = points;
        this.time = time;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String timePlayed = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(getTime()),
                TimeUnit.MILLISECONDS.toMinutes(getTime())
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(getTime())),
                TimeUnit.MILLISECONDS.toSeconds(getTime())
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getTime())));

        return "player:" + playerName + ", points:" + points + ", time:" + timePlayed;
    }

    @Override
    public int compareTo(Score s) {
        Integer i1 = Integer.valueOf(getPoints().intValue() * -1);
        Integer i2 = Integer.valueOf(s.getPoints().intValue() * -1);

        if (!i1.equals(i2)) {
            return i1.compareTo(i2);
        } else if (getTime().equals(s.getTime())) {
            return getTime().compareTo(s.getTime());
        } else {
            return getPlayerName().compareTo(s.getPlayerName());
        }
    }
}
