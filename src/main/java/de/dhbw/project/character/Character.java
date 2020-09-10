package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;

public class Character {
    @SerializedName("name")
    private String name;
    @SerializedName("where")
    private String where;
    @SerializedName("health")
    private int health;
    @SerializedName("strength")
    private int strength;
    @SerializedName("startStatement")
    private String startStatement;
    @SerializedName("killStatement")
    private String killStatement;
    @SerializedName("killed")
    private boolean killed;
    @SerializedName("points")
    private int points;

    public Character() {
    }

    public Character(String name, String where, int health, int strength, String startStatement, String killStatement,
            boolean killed, int points) {
        this.name = name;
        this.where = where;
        this.health = health;
        this.strength = strength;
        this.startStatement = startStatement;
        this.killStatement = killStatement;
        this.killed = killed;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getStartStatement() {
        return startStatement;
    }

    public void setStartStatement(String startStatement) {
        this.startStatement = startStatement;
    }

    public String getKillStatement() {
        return killStatement;
    }

    public void setKillStatement(String killStatement) {
        this.killStatement = killStatement;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
