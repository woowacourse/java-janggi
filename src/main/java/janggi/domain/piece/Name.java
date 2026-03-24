package janggi.domain.piece;

public enum Name {
    GENERAL("궁"),
    CHARIOT("차"),
    CANNON("포"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    SOLDIER("병");


    private String name;

    Name(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
