package janggi.movement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MovementTest {
    @DisplayName("기물 이름에 맞는 Movement 반환 확인")
    @Test
    void test() {
        assertThat(Movement.findPiece("K")).isEqualTo(Movement.KING);
    }
}
