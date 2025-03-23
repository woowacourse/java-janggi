package janggi.domain;

public class Player {

    private final String name;
    private final Team team;

    public Player(final String name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }
}
