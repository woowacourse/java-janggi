package janggi.domain.piece.movement.palace;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;
import java.util.List;

public class GuardPalaceMovementStrategy extends PalaceMovementStrategy {

    public GuardPalaceMovementStrategy(MovementStrategy defaultMovementStrategy) {
        super(defaultMovementStrategy);
    }

    @Override
    protected boolean isMovableInPalace(PiecesView map, Position origin, Side side, Position destination) {
        if (isOneDiagonalMove(origin, destination)) {
            return isValidDiagonalMove(map, origin, side, destination);
        }
        if (isOneVerticalMove(origin, destination) || isOneHorizontalMove(origin, destination)) {
            return isValidStraightMove(map, side, destination);
        }
        return false;
    }

    private boolean isValidDiagonalMove(PiecesView map, Position origin, Side side, Position destination) {
        List<Position> diagonalPositions = Palace.fromPosition(origin).getDiagonalPositions();
        if (diagonalPositions.isEmpty() || !diagonalPositions.contains(destination)) {
            return false;
        }
        return map.findByPosition(destination)
            .map(view -> view.getSide() != side)
            .orElse(true);
    }

    private boolean isValidStraightMove(PiecesView map, Side side, Position destination) {
        return map.findByPosition(destination)
            .map(view -> view.getSide() != side)
            .orElse(true);
    }

    private boolean isOneDiagonalMove(Position origin, Position destination) {
        return origin.hasXDistance(destination, 1) && origin.hasYDistance(destination, 1);
    }

    private boolean isOneVerticalMove(Position origin, Position destination) {
        return origin.hasXDistance(destination, 0) && origin.hasYDistance(destination, 1);
    }

    private boolean isOneHorizontalMove(Position origin, Position destination) {
        return origin.hasXDistance(destination, 1) && origin.hasYDistance(destination, 0);
    }
}
