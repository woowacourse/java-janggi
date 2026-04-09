package janggi.domain.piece;

import janggi.domain.side.Side;

public enum PieceType {
    CHARIOT("車", "車", 13),
    CANNON("包", "包", 7),
    HORSE("馬", "馬", 5),
    ELEPHANT("象", "象", 3),
    ADVISOR("士", "士", 3),
    SOLDIER("卒", "兵", 2),
    GENERAL("楚", "漢", 0),
    NONE("  ", "  ", 0);

    private final String hanName;
    private final String choName;
    private final double score;

    PieceType(String hanName, String choName, double score) {
        this.hanName = hanName;
        this.choName = choName;
        this.score = score;
    }

    public String getNameFormat(Side side) {
        if (Side.HAN.equals(side)) {
            return hanName;
        }
        if (Side.CHO.equals(side)) {
            return choName;
        }
        throw new IllegalStateException("Side는 Han 또는 Cho를 넣어주세요");
    }

    public double getScore() {
        return score;
    }

}
