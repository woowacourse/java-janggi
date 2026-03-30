package janggi.domain.piece;

public enum PieceType {
    GENERAL("將"),
    CHARIOT("車"),
    CANNON("包"),
    HORSE("馬"),
    ELEPHANT("象"),
    GUARD("士"),
    SOLDIER("兵");

    private final String type;

    PieceType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
