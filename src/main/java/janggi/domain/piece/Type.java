package janggi.domain.piece;

public enum Type {
    GENERAL("將"),
    CHARIOT("車"),
    HORSE("馬"),
    CANNON("包"),
    GUARD("士"),
    ELEPHANT("象"),
    SOLDIER("卒");

    private final String hanja;

    Type(String hanja) {
        this.hanja = hanja;
    }
}
