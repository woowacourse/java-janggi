package janggi.domain.piece;

public enum PieceType {
    TANK,
    HORSE,
    ELEPHANT,
    ADVISOR,
    KING,
    CANNON,
    SOLDIER;

    public static PieceType from(Piece piece) {
        if (piece instanceof Tank) {
            return TANK;
        }
        if (piece instanceof Horse) {
            return HORSE;
        }
        if (piece instanceof Elephant) {
            return ELEPHANT;
        }
        if (piece instanceof Advisor) {
            return ADVISOR;
        }
        if (piece instanceof King) {
            return KING;
        }
        if (piece instanceof Cannon) {
            return CANNON;
        }
        if (piece instanceof Soldier) {
            return SOLDIER;
        }
        throw new IllegalArgumentException("알 수 없는 기물 타입입니다.");
    }

    public Piece createPiece(Team team) {
        if (this == TANK) {
            return new Tank(team);
        }
        if (this == HORSE) {
            return new Horse(team);
        }
        if (this == ELEPHANT) {
            return new Elephant(team);
        }
        if (this == ADVISOR) {
            return new Advisor(team);
        }
        if (this == KING) {
            return new King(team);
        }
        if (this == CANNON) {
            return new Cannon(team);
        }
        if (this == SOLDIER) {
            return new Soldier(team);
        }
        throw new IllegalArgumentException("알 수 없는 기물 타입입니다.");
    }
}
