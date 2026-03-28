package janggi.domain;

public enum Side {

    HAN("한"),
    CHO("초");

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Side switchTurn() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
