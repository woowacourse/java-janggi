package domain.piece;

public abstract class AbstractPiece implements Piece {

    private final Team team;

    public AbstractPiece(Team team) {
        this.team = team;
    }

    public Team team() {
        return team;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
