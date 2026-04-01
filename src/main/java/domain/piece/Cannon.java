package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

public class Cannon extends Piece {

    private static final String IMPOSSIBLE_MOVE_MESSAGE = "이동할 수 없는 위치입니다.";
    private static final String BLOCKED_PATH_MESSAGE = "경로에 기물이 있어 이동할 수 없습니다.";
    private static final String SAME_TEAM_TARGET_MESSAGE = "아군 기물이 있는 위치로 이동할 수 없습니다.";

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        boolean movable = from.isHorizontal(to) || from.isVertical(to);
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE_MESSAGE);
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (from.isVertical(to)) {
            return from.verticalPathTo(to);
        }
        return from.horizontalPathTo(to);
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        validateExactlyOneBridge(piecesOnPath);
        validateBridgeIsNotCannon(piecesOnPath);
    }

    @Override
    public void validateNotSameTeam(Piece target) {
        if (target.isEmpty()) {
            return;
        }
        if (target instanceof Cannon) {
            throw new PieceException(IMPOSSIBLE_MOVE_MESSAGE);
        }
        if (this.team == target.team()) {
            throw new PieceException(SAME_TEAM_TARGET_MESSAGE);
        }
    }

    private void validateExactlyOneBridge(List<Piece> piecesOnPath) {
        if (piecesOnPath.size() != 1) {
            throw new PieceException(BLOCKED_PATH_MESSAGE);
        }
    }

    private void validateBridgeIsNotCannon(List<Piece> piecesOnPath) {
        boolean hasCannon = piecesOnPath.stream()
                .anyMatch(p -> p instanceof Cannon);
        if (hasCannon) {
            throw new PieceException(BLOCKED_PATH_MESSAGE);
        }
    }
}
