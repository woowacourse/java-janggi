package janggi.direction.move;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.direction.Direction;
import janggi.direction.Movement;
import janggi.direction.PieceType;
import janggi.position.Position;
import org.junit.jupiter.api.Test;

class RelativeMoveStrategyTest {

    @Test
    void 적절한_움직임을_찾는다() {
        // Given
        final MoveStrategy edgeMoveStrategy = new RelativeMoveStrategy();
        final Position currentPosition = new Position(2, 4);
        final Position arrivalPosition = new Position(4, 5);

        // When
        final Movement movement = edgeMoveStrategy.move(currentPosition, arrivalPosition, PieceType.HORSE);

        // Then
        assertThat(movement).isEqualTo(new Movement(Direction.DOWN, Direction.DOWN_RIGHT));
    }
}
