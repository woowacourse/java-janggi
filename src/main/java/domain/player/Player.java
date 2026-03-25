package domain.player;

public class Player {

    private final Name name;
    private final Team team;

    private Player(final Name name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public static Player cho(final String name) {
        return new Player(new Name(name), Team.CHO);
    }

    public static Player han(final String name) {
        return new Player(new Name(name), Team.HAN);
    }
}
