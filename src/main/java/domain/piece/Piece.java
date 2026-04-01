package domain.piece;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.error.PieceException;
import java.util.Map;

public abstract class Piece {

    protected static final String IMPOSSIBLE_MOVE = "기물이 움직일 수 없는 위치입니다.";

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
            throw new PieceException(IMPOSSIBLE_MOVE);
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
