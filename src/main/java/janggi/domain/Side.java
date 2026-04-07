package janggi.domain;

public enum Side {
    CHO("초"),
    HAN("한"),
    EMPTY("없음");

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Side reverse() {
        if (this == CHO) {
            return HAN;
        }
        if (this == HAN) {
            return CHO;
        }
        return EMPTY;
    }
}
