package model.board;

import model.move.Direction;

public enum Country {
    HAN("한", Direction.DOWN, "\u001B[31m"),
    CHO("초", Direction.UP, "\u001B[32m");

    public static final String RESET = "\u001B[0m";
    private final String title;
    private final Direction forward;
    private final String color;

    Country(String title, Direction forward, String color) {
        this.title = title;
        this.forward = forward;
        this.color = color;
    }

    public String title() {
        return title;
    }

    public Direction forward(){
        return forward;
    }

    public String color() {
        return color;
    }
}
