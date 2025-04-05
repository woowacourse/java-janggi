package janggi.move;

import janggi.piece.PalaceMovement;
import janggi.position.Position;
import java.util.Optional;

public enum MoveStrategy {

    EDGE {
        @Override
        public Movement move(final Position currentPosition, final Position arrivalPosition,
                             final Piece piece) {
            final Movements movements = piece.getMovements();
            final Optional<Movements> optionalMovements = PalaceMovement.getMovements(currentPosition);

            optionalMovements.ifPresent(movements::add);
            return movements.findMovements(currentPosition, arrivalPosition, piece);
        }
    },
    RELATIVE {
        @Override
        public Movement move(final Position currentPosition, final Position arrivalPosition,
                             final Piece piece) {
            final Movements movements = piece.getMovements();
            return movements.findMovements(currentPosition, arrivalPosition, piece);
        }
    };

    public abstract Movement move(Position currentPosition, Position arrivalPosition, Piece piece);
}
