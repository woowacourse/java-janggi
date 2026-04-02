package janggi.domain.team;

public enum TeamType {
    CHU("초나라"),
    HAN("한나라"),
    ;

    private final String name;

    TeamType(String name) {
        this.name = name;
    }

    public TeamType findOpponent() {
        if (this == CHU) {
            return HAN;
        }
        return CHU;
    }

    public String getName() {
        return name;
    }
}
