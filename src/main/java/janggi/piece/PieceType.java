package janggi.piece;

public enum PieceType {
    CHARIOT("차", 13),
    CANNON("포", 7),
    HORSE("마", 5),
    ELEPHANT("상", 3),
    GUARD("사", 3),
    SOLDIER("병", 2),
    GENERAL("장", 0);

    private final String name;
    private final int point;

    PieceType(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}
