package view.message;

import domain.piece.*;

public enum PieceView {

    PAWN("卒"),
    HORSE("馬"),
    ELEPHANT("象"),
    CHARIOT("車"),
    CANNON("包"),
    GUARD("士"),
    KING("將"),
    EMPTY("ㅁ");

    private final String name;

    PieceView(String name) {
        this.name = name;
    }

    public static String from(Piece piece) {
        if (piece.getType() == PieceType.PAWN) {
            return PAWN.name;
        }

        if (piece.getType() == PieceType.HORSE) {
            return HORSE.name;
        }

        if (piece.getType() == PieceType.ELEPHANT) {
            return ELEPHANT.name;
        }

        if (piece.getType() == PieceType.CHARIOT) {
            return CHARIOT.name;
        }

        if (piece.getType() == PieceType.CANNON) {
            return CANNON.name;
        }

        if (piece.getType() == PieceType.GUARD) {
            return GUARD.name;
        }

        if (piece.getType() == PieceType.KING) {
            return KING.name;
        }

        return EMPTY.name;
    }
}
