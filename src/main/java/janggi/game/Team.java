package janggi.game;

import janggi.point.Direction;

public enum Team {
    HAN("한나라", Direction.SOUTH, "\u001B[31m"),
    CHO("초나라", Direction.NORTH, "\u001B[32m");

    private final String text;
    private final Direction front;
    private final String colorCode;


    Team(String text, Direction front, String colorCode) {
        this.text = text;
        this.front = front;
        this.colorCode = colorCode;
    }

    public Team reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public boolean headsBack(Direction direction) {
        return direction == this.front.reverse();
    }

    public String getText() {
        return text;
    }

    public String getColorCode() {
        return colorCode;
    }
}
