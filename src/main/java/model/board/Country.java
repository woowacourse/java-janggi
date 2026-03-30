package model.board;

import model.move.Direction;

public enum Country {
    HAN("한", Direction.UP, "\u001B[31m"),
    CHO("초", Direction.DOWN, "\u001B[32m");

    public static final String RESET = "\u001B[0m";
    private final String title;
    private final Direction forbidden;
    private final String color;

    Country(String title, Direction forbidden, String color) {
        this.title = title;
        this.forbidden = forbidden;
        this.color = color;
    }

    public String title() {
        return title;
    }

    public String color() {
        return color;
    }
}
