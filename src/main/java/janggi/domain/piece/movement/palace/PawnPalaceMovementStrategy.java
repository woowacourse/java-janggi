package janggi.domain.piece.movement.palace;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;
import java.util.List;

public class PawnPalaceMovementStrategy extends PalaceMovementStrategy {

    public PawnPalaceMovementStrategy(MovementStrategy defaultMovementStrategy) {
        super(defaultMovementStrategy);
    }

    @Override
    protected boolean isMovableInPalace(PiecesView map, Position origin, Side side, Position destination) {
        Palace palace = Palace.fromPosition(origin);
        List<Position> diagonalPositions = getOneDistanceDiagonalPositions(palace, origin);

        if (isInvalidMove(diagonalPositions, side, origin, destination)) {
            return false;
        }
        return isEnemyOrEmptyDestination(map, side, destination);
    }

    private boolean isInvalidMove(List<Position> diagonalPositions, Side side, Position origin, Position destination) {
        if (diagonalPositions.isEmpty()) {
            return true;
        }
        if (side == Side.HAN && destination.y() < origin.y()) {
            return true;
        }
        return side == Side.CHO && destination.y() > origin.y();
    }

    private boolean isEnemyOrEmptyDestination(PiecesView map, Side side, Position destination) {
        if (map.isEnemyOnDestination(side, destination)) {
            return true;
        }
        return !map.isAllyOnDestination(side, destination);
    }

    private List<Position> getOneDistanceDiagonalPositions(Palace palace, Position destination) {
        return palace.getDiagonalPositions().stream()
            .filter(position -> position.hasXDistance(destination, 1) && position.hasYDistance(destination, 1))
            .toList();
    }
}
