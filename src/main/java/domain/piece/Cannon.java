package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

public class Cannon extends DiagonalPalaceMovementPiece {

    public static final String NO_BRIDGE_MESSAGE = "포는 기물을 뛰어넘어야 이동할 수 있습니다.";
    public static final String TOO_MANY_BRIDGE_MESSAGE = "경로에 기물이 2개 이상 있어 이동할 수 없습니다.";
    public static final String CANNON_AS_BRIDGE_MESSAGE = "포는 포를 경유할 수 없습니다.";
    public static final String CANNON_AS_TARGET_MESSAGE = "포는 포를 잡을 수 없습니다.";
    public static final String SAME_TEAM_TARGET_MESSAGE = "아군 기물이 있는 위치로 이동할 수 없습니다.";

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (palaceMovement.isPalace(from, to)) {
            palaceMovement.validateRule(from, to);
            return;
        }
        validateNormalRule(from, to);
    }

    private void validateNormalRule(Coordination from, Coordination to) {
        boolean movable = from.isHorizontal(to) || from.isVertical(to);
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE_MESSAGE);
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (palaceMovement.isPalace(from, to)) {
            if (from.isDiagonal(to)) {
                return from.diagonalPathTo(to);
            }
        }
        if (from.isVertical(to)) {
            return from.verticalPathTo(to);
        }
        if (from.isHorizontal(to)) {
            return from.horizontalPathTo(to);
        }
        throw new IllegalStateException(UNRESOLVABLE_PATH_MESSAGE);
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
            throw new PieceException(CANNON_AS_TARGET_MESSAGE);
        }
        if (this.team == target.team()) {
            throw new PieceException(SAME_TEAM_TARGET_MESSAGE);
        }
    }

    private void validateExactlyOneBridge(List<Piece> piecesOnPath) {
        if (piecesOnPath.isEmpty()) {
            throw new PieceException(NO_BRIDGE_MESSAGE);
        }
        if (piecesOnPath.size() > 1) {
            throw new PieceException(TOO_MANY_BRIDGE_MESSAGE);
        }
    }

    private void validateBridgeIsNotCannon(List<Piece> piecesOnPath) {
        boolean hasCannon = piecesOnPath.stream()
                .anyMatch(p -> p instanceof Cannon);
        if (hasCannon) {
            throw new PieceException(CANNON_AS_BRIDGE_MESSAGE);
        }
    }
}
