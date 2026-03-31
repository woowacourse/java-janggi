package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

import static util.ErrorMessage.IMPOSSIBLE_MOVE;

public class Cannon extends Piece {

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
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
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
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
        if (this.team == target.team()) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private void validateExactlyOneBridge(List<Piece> piecesOnPath) {
        if (piecesOnPath.size() != 1) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }

    private void validateBridgeIsNotCannon(List<Piece> piecesOnPath) {
        boolean hasCannon = piecesOnPath.stream()
                .anyMatch(p -> p instanceof Cannon);
        if (hasCannon) {
            throw new PieceException(IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
