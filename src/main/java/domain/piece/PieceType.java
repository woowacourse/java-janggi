package domain.piece;

import domain.player.Team;

public enum PieceType {

    GENERAL("楚", "漢", 0),
    GUARD("士", "士", 3),
    HORSE("馬", "馬", 5),
    ELEPHANT("象", "象", 3),
    SOLDIER("卒", "兵", 2),
    CANNON("砲", "炮", 7),
    CHARIOT("車", "車", 13);

    private final String nameOfCho;
    private final String nameOfHan;
    private final int score;

    PieceType(final String nameOfCho, final String nameOfHan, final int score) {
        this.nameOfCho = nameOfCho;
        this.nameOfHan = nameOfHan;
        this.score = score;
    }

    public String getNameOf(final Team team) {
        if (team == Team.CHO) {
            return nameOfCho;
        }
        return nameOfHan;
    }

    public int getScore() {
        return score;
    }
}
