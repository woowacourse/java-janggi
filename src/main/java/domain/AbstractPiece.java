package domain;

public abstract class AbstractPiece implements Piece {

    protected final Team team;

    public AbstractPiece(final Team team) {
        this.team = team;
    }
}
