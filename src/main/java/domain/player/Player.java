package domain.player;

public class Player {

    private final String name;
    private final Team team;

    private Player(final String name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public static Player cho(final String name) {
        return new Player(name, Team.CHO);
    }

    public static Player han(final String name) {
        return new Player(name, Team.HAN);
    }
}
