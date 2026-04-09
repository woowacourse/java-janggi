package janggi.domain.common;

public enum Team {
    CHO("초나라"),
    HAN("한나라");

    private static final int MAX_Y = 11;
    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int calculateYPosition(int defaultY) {
        if (this == CHO) {
            return defaultY;
        }
        return MAX_Y - defaultY;
    }

    public Team next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
