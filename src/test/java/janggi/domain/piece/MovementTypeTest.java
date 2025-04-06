package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Movement;
import janggi.domain.piece.position.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovementTypeTest {

    @Nested
    class EdgeMovementTypeTest {

        @Test
        void 적절한_움직임을_찾는다() {
            // Given
            final MovementType edgeStrategy = MovementType.PALACE_CONSIDERATE;
            final Position currentPosition = new Position(9, 4);
            final Position arrivalPosition = new Position(9, 5);

            // When
            final Movement movement = edgeStrategy.findValidMovement(Piece.CANNON, currentPosition, arrivalPosition);

            // Then
            assertThat(movement).isEqualTo(new Movement(Direction.RIGHT));
        }
    }

    @Nested
    class RelativeMovementTypeTest {

        @Test
        void 적절한_움직임을_찾는다() {
            // Given
            final MovementType edgeStrategy = MovementType.BASIC;
            final Position from = new Position(2, 4);
            final Position to = new Position(4, 5);

            // When
            final Movement movement = edgeStrategy.findValidMovement(Piece.HORSE, from, to);

            // Then
            assertThat(movement).isEqualTo(new Movement(Direction.DOWN, Direction.DOWN_RIGHT));
        }
    }
}
