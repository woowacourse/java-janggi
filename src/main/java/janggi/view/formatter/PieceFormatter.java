package janggi.view.formatter;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

public final class PieceFormatter {

    private PieceFormatter() {
    }

    public static String format(Piece piece) {
        if (piece.isSameCamp(Camp.CHO)) {
            return choFormat(piece.pieceType());
        }
        return hanFormat(piece.pieceType());

    }

    private static String choFormat(PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> "楚";
            case CHARIOT -> "車";
            case HORSE -> "馬";
            case CANNON -> "包";
            case GUARD -> "士";
            case ELEPHANT -> "象";
            case SOLDIER -> "卒";
        };
    }

    private static String hanFormat(PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> "漢";
            case CHARIOT -> "車";
            case HORSE -> "馬";
            case CANNON -> "包";
            case GUARD -> "士";
            case ELEPHANT -> "象";
            case SOLDIER -> "兵";
        };
    }
}
