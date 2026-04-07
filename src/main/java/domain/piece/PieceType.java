package domain.piece;

import domain.player.Team;

public enum PieceType {

    GENERAL("楚", "漢"),
    GUARD("士", "士"),
    HORSE("馬", "馬"),
    ELEPHANT("象", "象"),
    SOLDIER("卒", "兵"),
    CANNON("砲", "炮"),
    CHARIOT("車", "車");

    private final String nameOfCho;
    private final String nameOfHan;

    PieceType(final String nameOfCho, final String nameOfHan) {
        this.nameOfCho = nameOfCho;
        this.nameOfHan = nameOfHan;
    }

    public String getNameOf(final Team team) {
        if (team == Team.CHO) {
            return nameOfCho;
        }
        return nameOfHan;
    }
}
