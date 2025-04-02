package janggi.direction.move;

import janggi.direction.Movement;
import janggi.direction.Movements;
import janggi.direction.PieceType;
import janggi.position.Position;

public class RelativeMoveStrategy implements MoveStrategy {

    @Override
    public Movement move(final Position currentPosition, final Position arrivalPosition, final PieceType pieceType) {
        final Movements movements = pieceType.getMovements();
        return movements.findMovements(currentPosition, arrivalPosition, pieceType);
    }
}
