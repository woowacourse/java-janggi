package domain.piece;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.error.ErrorMessage;
import domain.piece.error.PieceException;
import java.util.Map;

public abstract class Piece {

    protected final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public Team team() {
        return team;
    }

    public boolean isEmpty() {
        return false;
    }

    public void isSameTeam(Piece piece) {
        if (!isEmptyPiece(piece)) {
            validateSameTeam(piece);
        }
    }

    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    public void isSameTeam(Turn turn) {
        team.validateSameTeam(turn);
    }

    protected void validateSameTeam(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        Piece fromPiece = board.get(from);
        Piece toPiece = board.get(to);
        fromPiece.isSameTeam(toPiece);
    }

    private boolean isEmptyPiece(Piece piece) {
        return piece.isEmpty();
    }

    private void validateSameTeam(Piece piece) {
        if (piece.isSameTeam(this.team)) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }

    public boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }

    public abstract void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board);
}
