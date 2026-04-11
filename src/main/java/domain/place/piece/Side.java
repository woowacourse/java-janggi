package domain.place.piece;

import domain.player.Player;

public enum Side {
    CHO("C", 10, -1, "\u001B[34m"),
    HAN("H", 1, 1, "\u001B[31m");

    private static final String RESET = "\u001B[0m";
    private final String name;
    private final int startLine;
    private final int direction;
    private final String color;

    Side(String name, int startLine, int direction, String color) {
        this.name = name;
        this.startLine = startLine;
        this.direction = direction;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String colorize(String text) {
        return color + text + RESET;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getDirection() {
        return direction;
    }

    public Player resolve(Player cho, Player han) {
        if (this == CHO) {
            return cho;
        }
        return han;
    }
}
