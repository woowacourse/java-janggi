package janggi.domain.piece.movement.palace;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;
import java.util.List;

public class RookPalaceMovementStrategy extends PalaceMovementStrategy {

    public RookPalaceMovementStrategy(MovementStrategy defaultMovementStrategy) {
        super(defaultMovementStrategy);
    }

    @Override
    protected boolean isMovableInPalace(PiecesView map, Position origin, Side side, Position destination) {
        List<Position> diagonalPositions = Palace.fromPosition(origin).getDiagonalPositions();
        if (!isValidPath(map, diagonalPositions, destination)) {
            return false;
        }
        return map.findByPosition(destination)
            .map(view -> view.getSide() != side)
            .orElse(true);
    }

    private boolean isValidPath(PiecesView map, List<Position> diagonalPositions, Position destination) {
        if (diagonalPositions.size() > 2) {
            return true;
        }
        if (!diagonalPositions.contains(destination)) {
            return false;
        }

        return diagonalPositions.stream()
            .takeWhile(position -> !position.equals(destination))
            .noneMatch(position -> map.findByPosition(position).isPresent());
    }
}
