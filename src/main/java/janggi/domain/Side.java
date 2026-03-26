package janggi.domain;

public enum Side {
    HAN("한"),
    CHO("초"),
    NONE("없음");

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
