package janggi.domain.piece.movement.palace;

import janggi.domain.Palace;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;
import java.util.List;

public class CannonPalaceMovementStrategy extends PalaceMovementStrategy {

    public CannonPalaceMovementStrategy(MovementStrategy defaultMovementStrategy) {
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
        if (diagonalPositions.size() != 2) {
            return false;
        }
        if (!diagonalPositions.getLast().equals(destination)) {
            return false;
        }
        return diagonalPositions.stream()
            .flatMap(position -> map.findByPosition(position).stream())
            .noneMatch(pieceView -> pieceView.getPieceType() == PieceType.CANNON);
    }
}
