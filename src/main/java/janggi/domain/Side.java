package janggi.domain;

public enum Side {
    HAN("\u001B[31m한나라\u001B[0m"),
    CHO("\u001B[32m초나라\u001B[0m");

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Side reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
