package janggi.domain.piece;

import janggi.domain.piece.direction.Movement;
import janggi.domain.piece.direction.Movements;
import janggi.domain.piece.position.Position;
import janggi.domain.piece.direction.PalaceMovement;
import java.util.Optional;

public enum MovementType {

    PALACE_AWARE {
        @Override
        public Movement determineMovement(final Piece piece, final Position from, final Position to) {
            final Movements movements = piece.getMovements();
            addPalaceMovementIfApplicable(from, movements);
            return movements.findMovements(from, to, piece.canMoveIterable());
        }

        private void addPalaceMovementIfApplicable(final Position from, final Movements movements) {
            final Optional<Movements> optionalMovements = PalaceMovement.getMovements(from);
            optionalMovements.ifPresent(movements::add);
        }
    },
    STANDARD {
        @Override
        public Movement determineMovement(final Piece piece, final Position from, final Position to) {
            final Movements movements = piece.getMovements();
            return movements.findMovements(from, to, piece.canMoveIterable());
        }
    };

    public abstract Movement determineMovement(Piece piece, Position from, Position to);
}
