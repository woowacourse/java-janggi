package view.message;

import domain.piece.*;

public enum PieceView {

    PAWN("졸"),
    CHARIOT("차"),
    HORSE("마"),
    ELEPHANT("상"),
    CANNON("포"),
    GUARD("사"),
    KING("장"),
    EMPTY("ㅁ");

    private final String name;

    PieceView(String name) {
        this.name = name;
    }

    public static String from(Piece piece) {
        if (piece instanceof Pawn) {
            return PAWN.name;
        }

        if (piece instanceof Chariot) {
            return CHARIOT.name;
        }

        if (piece instanceof Horse) {
            return HORSE.name;
        }

        if (piece instanceof Elephant) {
            return ELEPHANT.name;
        }

        if (piece instanceof Cannon) {
            return CANNON.name;
        }

        if (piece instanceof Guard) {
            return GUARD.name;
        }

        if (piece instanceof King) {
            return KING.name;
        }

        return EMPTY.name;
    }

    public String getName() {
        return name;
    }
}
