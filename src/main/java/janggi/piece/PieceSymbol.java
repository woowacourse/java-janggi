package janggi.piece;

public enum PieceSymbol {

    CANNON(7),
    CHARIOT(13),
    ELEPHANT(3),
    GENERAL(0),
    GUARD(3),
    HORSE(5),
    SOLDIER_JOL(2),
    SOLDIER_BYEONG(2),
    ;

    private final int point;

    PieceSymbol(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public Piece createPiece(Camp camp) {
        return switch (this) {
            case CANNON -> new Cannon(camp);
            case CHARIOT -> new Chariot(camp);
            case ELEPHANT -> new Elephant(camp);
            case GENERAL -> new General(camp);
            case GUARD -> new Guard(camp);
            case HORSE -> new Horse(camp);
            case SOLDIER_JOL -> new SoldierJol();
            case SOLDIER_BYEONG -> new SoldierByeong();
        };
    }
}
