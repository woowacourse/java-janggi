package domain;

public enum PieceType {

    GENERAL("將"),
    GUARD("士"),
    SOLDIER("卒"),
    HORSE("馬"),
    ELEPHANT("象"),
    CHARIOT("車"),
    CANNON("包"),

    EMPTY_VALUE("＋")
    ;

    private final String description;

    PieceType(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
