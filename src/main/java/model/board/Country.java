package model.board;

import model.move.Direction;

public enum Country {
    HAN("한", Direction.DOWN, "\u001B[31m", 1.5),
    CHO("초", Direction.UP, "\u001B[32m", 0.0);

    public static final String RESET = "\u001B[0m";
    private final String title;
    private final Direction forward;
    private final String color;
    private final double bonusScore;

    Country(String title, Direction forward, String color, double bonusScore) {
        this.title = title;
        this.forward = forward;
        this.color = color;
        this.bonusScore = bonusScore;
    }

    public String title() {
        return title;
    }

    public Direction forward() {
        return forward;
    }

    public String color() {
        return color;
    }

    public double bonusScore() {
        return bonusScore;
    }
}
