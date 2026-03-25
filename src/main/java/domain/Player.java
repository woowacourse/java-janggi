package domain;

public final class Player {

    public final Name name;
    public final Team team;

    public Player(Name name, Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name.getValue();
    }
}
