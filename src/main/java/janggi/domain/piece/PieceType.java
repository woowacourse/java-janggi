package janggi.domain.piece;

import janggi.domain.side.Side;

public enum PieceType {
    CHARIOT("車", "車"),
    CANNON("包", "包"),
    HORSE("馬", "馬"),
    ELEPHANT("象", "象"),
    SOLDIER("卒", "兵"),
    ADVISOR("士", "士"),
    GENERAL("楚", "漢"),
    NONE("  ", "  ");

    private final String choName;
    private final String hanName;

    PieceType(String choName, String hanName) {
        this.choName = choName;
        this.hanName = hanName;

    }

    public String getNameFormat(Side side) {
        if (side == null) {
            throw new IllegalStateException("Side는 Han 또는 Cho를 넣어주세요");
        }
        if (Side.HAN.equals(side)) {
            return hanName;
        }
        return choName;
    }
}
