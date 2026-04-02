package domain.piece;

public enum PieceType {

    GENERAL("楚", "漢"),
    GUARD("士", "士"),
    HORSE("馬", "馬"),
    ELEPHANT("象", "象"),
    SOLDIER("卒", "兵"),
    CANNON("砲", "炮"),
    CHARIOT("車", "車");

    PieceType(final String nameForCho, final String nameForHan) {
        this.nameForCho = nameForCho;
        this.nameForHan = nameForHan;
    }

    private final String nameForCho;
    private final String nameForHan;

    public String getNameForCho() {
        return nameForCho;
    }

    public String getNameForHan() {
        return nameForHan;
    }
}
