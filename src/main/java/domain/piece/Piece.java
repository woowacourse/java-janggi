package domain.piece;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.error.InvalidTargetException;
import domain.piece.error.PathBlockedException;

import java.util.List;

public abstract class Piece {

    private static final String BLOCKED_PATH_MESSAGE = "경로에 기물이 있어 이동할 수 없습니다.";
    private static final String SAME_TEAM_TARGET_MESSAGE = "아군 기물이 있는 위치로 이동할 수 없습니다.";
    protected static final String IMPOSSIBLE_MOVE_MESSAGE = "기물이 움직일 수 없는 위치입니다.";

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

    public void validateTarget(Piece target) {
        if (!target.isEmpty() && this.team == target.team) {
            throw new InvalidTargetException(SAME_TEAM_TARGET_MESSAGE);
        }
    }

    public void validatePath(List<Piece> piecesOnPath) {
        boolean hasBlocker = piecesOnPath.stream().anyMatch(p -> !p.isEmpty());
        if (hasBlocker) {
            throw new PathBlockedException(BLOCKED_PATH_MESSAGE);
        }
    }

    public abstract PieceType pieceType();

    public abstract void validateRule(Coordination from, Coordination to);

    public abstract List<Coordination> resolvePath(Coordination from, Coordination to);
}
