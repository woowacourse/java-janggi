package janggi.move;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.position.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MoveStrategyTest {

    @Nested
    class EdgeMoveStrategyTest {

        @Test
        void 적절한_움직임을_찾는다() {
            // Given
            final MoveStrategy edgeStrategy = MoveStrategy.EDGE;
            final Position currentPosition = new Position(9, 4);
            final Position arrivalPosition = new Position(9, 5);

            // When
            final Movement movement = edgeStrategy.move(currentPosition, arrivalPosition, Piece.CANNON);

            // Then
            assertThat(movement).isEqualTo(new Movement(Direction.RIGHT));
        }
    }

    @Nested
    class RelativeMoveStrategyTest {

        @Test
        void 적절한_움직임을_찾는다() {
            // Given
            final MoveStrategy edgeStrategy = MoveStrategy.RELATIVE;
            final Position from = new Position(2, 4);
            final Position to = new Position(4, 5);

            // When
            final Movement movement = edgeStrategy.move(from, to, Piece.HORSE);

            // Then
            assertThat(movement).isEqualTo(new Movement(Direction.DOWN, Direction.DOWN_RIGHT));
        }
    }
}
