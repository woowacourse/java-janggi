package janggi.domain.piece;

import janggi.domain.piece.direction.Movement;
import janggi.domain.piece.direction.Movements;
import janggi.domain.piece.direction.PalaceMovement;
import janggi.domain.piece.position.Position;
import java.util.Optional;

public enum MovementType {

    PALACE_CONSIDERATE {
        @Override
        public Movement findValidMovement(final Piece piece, final Position from, final Position to) {
            final Movements movements = piece.getPossibleMovements();
            includePalaceMovementsIfInPalace(from, movements);
            return movements.findMovements(from, to, piece.canMoveMultipleSteps());
        }

        private void includePalaceMovementsIfInPalace(final Position from, final Movements movements) {
            final Optional<Movements> optionalMovements = PalaceMovement.getMovements(from);
            optionalMovements.ifPresent(movements::add);
        }
    },

    BASIC {
        @Override
        public Movement findValidMovement(final Piece piece, final Position from, final Position to) {
            final Movements movements = piece.getPossibleMovements();
            return movements.findMovements(from, to, piece.canMoveMultipleSteps());
        }
    };

    public abstract Movement findValidMovement(Piece piece, Position from, Position to);
}
