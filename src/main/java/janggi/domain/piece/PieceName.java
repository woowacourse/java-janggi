package janggi.domain.piece;

import janggi.domain.side.Side;

public enum PieceName {
    CHARIOT("車", "車"),
    CANNON("包", "包"),
    HORSE("馬", "馬"),
    ELEPHANT("象", "象"),
    SOLDIER("卒", "兵"),
    ADVISOR("士", "士"),
    GENERAL("楚", "漢");

    private final String hanName;
    private final String choName;

    PieceName(String hanName, String choName) {
        this.hanName = hanName;
        this.choName = choName;
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

}
