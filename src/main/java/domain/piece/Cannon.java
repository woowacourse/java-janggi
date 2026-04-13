package domain.piece;

import domain.piece.error.InvalidTargetException;
import domain.piece.error.PathBlockedException;

import java.util.List;

public class Cannon extends DiagonalPalaceMovementPiece {

    private static final String NO_BRIDGE_MESSAGE = "포는 기물을 뛰어넘어야 이동할 수 있습니다.";
    private static final String TOO_MANY_BRIDGE_MESSAGE = "경로에 기물이 2개 이상 있어 이동할 수 없습니다.";
    private static final String CANNON_AS_BRIDGE_MESSAGE = "포는 포를 경유할 수 없습니다.";
    private static final String CANNON_AS_TARGET_MESSAGE = "포는 포를 잡을 수 없습니다.";
    private static final String SAME_TEAM_TARGET_MESSAGE = "아군 기물이 있는 위치로 이동할 수 없습니다.";

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        validateExactlyOneBridge(piecesOnPath);
        validateBridgeIsNotCannon(piecesOnPath);
    }

    @Override
    public void validateTarget(Piece target) {
        if (target.isEmpty()) {
            return;
        }
        if (target instanceof Cannon) {
            throw new InvalidTargetException(CANNON_AS_TARGET_MESSAGE);
        }
        if (this.team == target.team()) {
            throw new InvalidTargetException(SAME_TEAM_TARGET_MESSAGE);
        }
    }

    private void validateExactlyOneBridge(List<Piece> piecesOnPath) {
        if (piecesOnPath.isEmpty()) {
            throw new PathBlockedException(NO_BRIDGE_MESSAGE);
        }
        if (piecesOnPath.size() > 1) {
            throw new PathBlockedException(TOO_MANY_BRIDGE_MESSAGE);
        }
    }

    private void validateBridgeIsNotCannon(List<Piece> piecesOnPath) {
        boolean hasCannon = piecesOnPath.stream()
                .anyMatch(p -> p instanceof Cannon);
        if (hasCannon) {
            throw new PathBlockedException(CANNON_AS_BRIDGE_MESSAGE);
        }
    }
}
