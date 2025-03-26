package janggi.domain.piece;

public enum Team {
    RED("홍"),
    BLUE("청"),
    NONE("없음");

    private final String name;

    Team(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
