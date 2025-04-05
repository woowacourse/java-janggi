package janggi.domain.piece.direction;

import static janggi.domain.piece.direction.Direction.UP;
import static janggi.domain.piece.direction.Direction.UP_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

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
