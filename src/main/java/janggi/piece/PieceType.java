package janggi.piece;

import janggi.player.Score;

public enum PieceType {
    GENERAL("장"),
    GUARD("사"),
    SOLDIER("병/졸"),
    HORSE("마"),
    ELEPHANT("상"),
    CHARIOT("차"),
    CANNON("포"),
    ;

    private final String description;

    PieceType(final String description) {
        this.description = description;
    }

    public static PieceType from(final String pieceType) {
        return PieceType.valueOf(pieceType);
    }

    public Score score() {
        return switch (this) {
            case SOLDIER -> Score.soldier();
            case GUARD -> Score.guard();
            case ELEPHANT -> Score.elephant();
            case HORSE -> Score.horse();
            case CANNON -> Score.cannon();
            case CHARIOT -> Score.chariot();
            case GENERAL -> Score.general();
        };
    }

    public boolean isCannon() {
        return this == CANNON;
    }

    public String getDescription() {
        return description;
    }
}
