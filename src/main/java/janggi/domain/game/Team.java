package janggi.domain.game;

public enum Team {

    CHO("초"), HAN("한");

    private final String name;

    Team(final String name) {
        this.name = name;
    }

    public Team opposite() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public String getName() {
        return name;
    }
}
