package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovementTest {

    @DisplayName("대각선 방향인지 확인한다.")
    @Test
    void movementTest() {
        assertThat(Movement.UP.isDiagonal()).isFalse();
        assertThat(Movement.DOWN.isDiagonal()).isFalse();
        assertThat(Movement.RIGHT.isDiagonal()).isFalse();
        assertThat(Movement.LEFT.isDiagonal()).isFalse();
        assertThat(Movement.UP_RIGHT.isDiagonal()).isTrue();
        assertThat(Movement.DOWN_RIGHT.isDiagonal()).isTrue();
        assertThat(Movement.DOWN_LEFT.isDiagonal()).isTrue();
        assertThat(Movement.UP_LEFT.isDiagonal()).isTrue();
    }
}
