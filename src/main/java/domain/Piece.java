package domain;

public class Piece {

    private final Team team;
    private final Type type;

    private Piece(final Team team, final Type type) {
        this.team = team;
        this.type = type;
    }

    public static Piece of(final Team team, final Type type) {
        return new Piece(team, type);
    }
}
