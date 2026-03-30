package janggi.view.format;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

public enum PieceFormat {

    GENERAL_CHO("楚"),
    GENERAL_HAN("漢"),
    SOLDIER_CHO("卒"),
    SOLDIER_HAN("兵"),
    CHARIOT_CHO("車"),
    CHARIOT_HAN("車"),
    HORSE_CHO("馬"),
    HORSE_HAN("馬"),
    CANNON_CHO("包"),
    CANNON_HAN("包"),
    GUARD_CHO("士"),
    GUARD_HAN("士"),
    ELEPHANT_CHO("象"),
    ELEPHANT_HAN("象"),
    ;

    private final String symbol;

    PieceFormat(String symbol) {
        this.symbol = symbol;
    }

    public static PieceFormat from(Piece piece) {
        return switch (piece.camp()) {
            case CHO -> choOf(piece.pieceType());
            case HAN -> hanOf(piece.pieceType());
        };
    }

    private static PieceFormat choOf(PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> GENERAL_CHO;
            case CHARIOT -> CHARIOT_CHO;
            case HORSE -> HORSE_CHO;
            case CANNON -> CANNON_CHO;
            case GUARD -> GUARD_CHO;
            case ELEPHANT -> ELEPHANT_CHO;
            case SOLDIER -> SOLDIER_CHO;
        };
    }

    private static PieceFormat hanOf(PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> GENERAL_HAN;
            case CHARIOT -> CHARIOT_HAN;
            case HORSE -> HORSE_HAN;
            case CANNON -> CANNON_HAN;
            case GUARD -> GUARD_HAN;
            case ELEPHANT -> ELEPHANT_HAN;
            case SOLDIER -> SOLDIER_HAN;
        };
    }

    public String symbol() {
        return symbol;
    }
}
