package piece;

public enum PieceType {
    GENERAL("장"),
    CHARIOT("차"),
    CANON("포"),
    HORSE("마"),
    ELEPHANT("상"),
    COUNSELOR("사"),
    PAWN("병");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }
}

