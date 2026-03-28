package janggi.view;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

public class PieceViewResover {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private PieceViewResover() {
    }

    public static String toDisplayName(Piece piece) {
        String name = switch (piece.getType()) {
            case CHA -> "차";
            case GUNG -> "궁";
            case JOLBYEOUNG -> "졸";
            case MA -> "마";
            case PO -> "포";
            case SA -> "사";
            case SANG -> "상";
            case NONE -> "ㆍ";
        };

        if (piece.isEmpty()) {
            return name;
        }

        if (piece.isSameSide(Side.HAN)) {
            return ANSI_RED + name + ANSI_RESET;
        }
        return ANSI_BLUE + name + ANSI_RESET;
    }
}
