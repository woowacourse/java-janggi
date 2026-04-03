package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;
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

    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    public void validateNotSameTeam(Piece piece) {
        if (!isEmptyPiece(piece) && piece.isSameTeam(this.team)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private boolean isEmptyPiece(Piece piece) {
        return piece.isEmpty();
    }

    public boolean isAliveGeneral() {
        return false;
    }

    public abstract void validateRule(Coordination from, Coordination to);

    public abstract List<Coordination> resolvePath(Coordination from, Coordination to);

    public abstract void validatePath(List<Piece> piecesOnPath);

    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        validateRule(from, to);
        List<Piece> piecesOnPath = resolvePath(from, to).stream()
                .map(board::get)
                .filter(piece -> !piece.isEmpty())
                .toList();
        validatePath(piecesOnPath);
        validateNotSameTeam(board.get(to));
    }
}
