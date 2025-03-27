package janggi.piece;

public enum PieceType {

    SOLDIER("병"),
    CHARIOT("차"),
    KING("왕"),
    PAWN("졸"),
    HORSE("마"),
    CANNON("포"),
    GUARD("사"),
    ELEPHANT("상"),
    ;

    private final String name;

    PieceType(final String name) {
        this.name = name;
    }

    public static boolean isKing(final PieceType pieceType) {
        return KING.equals(pieceType);
    }

    public static boolean isCannon(final PieceType pieceType) {
        return CANNON.equals(pieceType);
    }

    public String getValue() {
        return name;
    }
}
