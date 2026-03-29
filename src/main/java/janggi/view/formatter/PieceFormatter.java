package janggi.view.formatter;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;

public final class PieceFormatter {

    private PieceFormatter() {
    }

    public static String format(Piece piece) {
        if (piece.isSameCamp(Camp.CHO)) {
            return choFormat(piece.pieceRule());
        }
        return hanFormat(piece.pieceRule());

    }

    private static String choFormat(PieceRule pieceRule) {
        return switch (pieceRule) {
            case GENERAL -> "楚";
            case CHARIOT -> "車";
            case HORSE -> "馬";
            case CANNON -> "包";
            case GUARD -> "士";
            case ELEPHANT -> "象";
            case SOLDIER -> "卒";
        };
    }

    private static String hanFormat(PieceRule pieceRule) {
        return switch (pieceRule) {
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
