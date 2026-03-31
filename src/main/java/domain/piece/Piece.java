package domain.piece;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.error.PieceException;
import util.ErrorMessage;

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

    public void validateSameTeam(Turn turn) {
        turn.validateSameTeam(team);
    }

    protected void validateNotSameTeam(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        Piece fromPiece = board.get(from);
        Piece toPiece = board.get(to);
        fromPiece.validateTarget(toPiece);
    }

    private void validateTarget(Piece piece) {
        if (!piece.isEmpty()) {
            validateNotSameTeam(piece);
        }
    }

    private void validateNotSameTeam(Piece piece) {
        if (this.team == piece.team) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }

    public abstract PieceType pieceType();

    public abstract void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board);

    public boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }
}
