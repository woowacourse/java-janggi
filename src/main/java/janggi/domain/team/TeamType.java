package janggi.domain.team;

public enum TeamType {
    RED("한나라"),
    BLUE("초나라");

    private final String name;

    TeamType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TeamType nextTeamType() {
        if (this == RED) {
            return BLUE;
        }
        return RED;
    }
}
