package domain;

public abstract class Piece {

    private final Team team;

    public Piece(final Team team) {
        this.team = team;
    }
}
