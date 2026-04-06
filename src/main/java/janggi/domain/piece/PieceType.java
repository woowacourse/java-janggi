package janggi.domain.piece;

public enum PieceType {
    GENERAL("장", 0.0),
    CHARIOT("차", 13.0),
    CANNON("포", 7.0),
    HORSE("마", 5.0),
    ELEPHANT("상", 3.0),
    GUARD("사", 3.0),
    SOLDIER("졸", 2.0),
    EMPTY("빈", 0.0);

    private final String name;
    private final double score;

    PieceType(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
