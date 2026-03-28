package janggi.view;

import janggi.domain.piece.PieceType;

public class PieceViewResover {

    private PieceViewResover() {
    }

    public static String toDisplayName(PieceType pieceType) {
        return switch (pieceType) {
            case CHA -> "차";
            case GUNG -> "궁";
            case JOLBYEOUNG -> "졸";
            case MA -> "마";
            case PO -> "포";
            case SA -> "사";
            case SANG -> "상";
            case NONE -> "ㆍ";
        };
    }
}
