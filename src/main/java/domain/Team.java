package domain;

import domain.piece.PieceColor;

public enum Team {
    HAN("한", PieceColor.RED),
    CHO("초", PieceColor.BLUE),
    ;

    private final String name;
    private final PieceColor color;

    Team(String name, PieceColor color) {
        this.name = name;
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
