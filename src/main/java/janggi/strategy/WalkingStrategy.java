package janggi.strategy;

import janggi.direction.Movement;
import janggi.direction.PieceMovement;
import janggi.piece.Pieces;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class WalkingStrategy implements MoveStrategy {

    private final PieceMovement pieceMovement;

    public WalkingStrategy(final PieceMovement pieceMovement) {
        this.pieceMovement = pieceMovement;
    }

    @Override
    public void validatePath(final Position currentPosition, final Position arrivalPosition, final Pieces pieces) {
        final Movement movement = pieceMovement.getMovements().findMovements(currentPosition, arrivalPosition);
        final Path path = movement.makePath(currentPosition, arrivalPosition);
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceMovement getPieceMovement() {
        return pieceMovement;
    }

    private boolean hasPieceInMiddle(final Path path, final Pieces pieces) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(pieces::hasPiece);
    }
}
