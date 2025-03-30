package janggi.game;

import janggi.movement.direction.Direction;

public enum Team {
    HAN("한나라", Direction.SOUTH, 0, "\u001B[31m"),
    CHO("초나라", Direction.NORTH, 9, "\u001B[32m");

    private final String text;
    private final Direction forward;
    private final int backwardRow;
    private final String colorCode;

    Team(String text, Direction forward, int backwardRow, String colorCode) {
        this.text = text;
        this.forward = forward;
        this.backwardRow = backwardRow;
        this.colorCode = colorCode;
    }

    public Team reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public Direction getForwardDirection() {
        return forward;
    }

    public String getText() {
        return text;
    }

    public String getColorCode() {
        return colorCode;
    }
}
