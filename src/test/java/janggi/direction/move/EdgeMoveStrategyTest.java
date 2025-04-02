package janggi.direction.move;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.direction.Direction;
import janggi.direction.Movement;
import janggi.direction.PieceType;
import janggi.position.Position;
import org.junit.jupiter.api.Test;

class EdgeMoveStrategyTest {

    @Test
    void 적절한_움직임을_찾는다() {
        // Given
        final MoveStrategy edgeMoveStrategy = new EdgeMoveStrategy();
        final Position currentPosition = new Position(9, 4);
        final Position arrivalPosition = new Position(9, 5);

        // When
        final Movement movement = edgeMoveStrategy.move(currentPosition, arrivalPosition, PieceType.CANNON);

        // Then
        assertThat(movement).isEqualTo(new Movement(Direction.RIGHT));
    }
}
