package janggi.domain.piece;

public abstract class Piece {
    private final Team team;
    private final Name name;

    public Piece(Name name, Team team) {
        this.name = name;
        this.team = team;
    }
}
