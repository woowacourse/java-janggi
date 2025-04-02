package janggi.direction.move;

import janggi.direction.Movement;
import janggi.direction.Movements;
import janggi.direction.PieceType;
import janggi.piece.PalaceMovement;
import janggi.position.Position;
import java.util.Optional;

public class EdgeMoveStrategy implements MoveStrategy {

    @Override
    public Movement move(final Position currentPosition, final Position arrivalPosition, final PieceType pieceType) {
        final Movements movements = pieceType.getMovements();
        final Optional<Movements> optionalMovements = PalaceMovement.getMovements(currentPosition);

        optionalMovements.ifPresent(movements::add);
        return movements.findMovements(currentPosition, arrivalPosition, pieceType);
    }
}
