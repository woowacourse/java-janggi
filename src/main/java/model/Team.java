package model;

import java.util.List;

public enum Team {
    RED("한", 1.5, 7, 9),
    BLUE("초", 0, 0, 2);


    private final String team;
    private final double defaultScore;
    private final int yGoongMinimum;
    private final int yGoongMaximum;

    Team(String team, double defaultScore, int yGoongMinimum, int yGoongMaximum) {
        this.team = team;
        this.defaultScore = defaultScore;
        this.yGoongMinimum = yGoongMinimum;
        this.yGoongMaximum = yGoongMaximum;
    }

    public boolean isRed() {
        return this == RED;
    }

    public String getTeam() {
        return team;
    }

    public double getDefaultScore() {
        return defaultScore;
    }

    public int getyGoongMinimum() {
        return yGoongMinimum;
    }

    public int getyGoongMaximum() {
        return yGoongMaximum;
    }
}
