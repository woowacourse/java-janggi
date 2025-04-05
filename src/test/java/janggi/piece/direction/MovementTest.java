package janggi.piece.direction;

import static janggi.move.Direction.UP;
import static janggi.move.Direction.UP_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.move.Movement;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    void 같은_위치인지_확인한다() {
        // Given
        final Movement movement = new Movement(UP, UP_RIGHT);

        // When & Then
        assertThat(movement.isSameMovement(-2, 1)).isTrue();
    }
}
