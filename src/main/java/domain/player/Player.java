package domain.player;

public class Player {

    private final Name name;
    private final Team team;

    private Player(final Name name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public static Player of(final String name, final Team team) {
        return new Player(new Name(name), team);
    }
}
