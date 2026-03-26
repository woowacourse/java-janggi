package domain.piece;

import domain.piece.error.PieceException;
import util.ErrorMessage;

public abstract class AbstractPiece implements Piece {

    protected final Team team;

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

    @Override
    public void isSameTeam(Piece piece) {
        if (!isEmptyPiece(piece)) {
            validateSameTeam(piece);
        }
    }

    private boolean isEmptyPiece(Piece piece) {
        return piece.isEmpty();
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    private void validateSameTeam(Piece piece) {
        if (piece.isSameTeam(this.team)) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
