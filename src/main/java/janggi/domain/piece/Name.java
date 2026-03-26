package janggi.domain.piece;

public enum Name {
    GENERAL("將"),
    CHARIOT("車"),
    CANNON("包"),
    HORSE("馬"),
    ELEPHANT("象"),
    GUARD("士"),
    SOLDIER("兵");

    private String name;

    Name(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
