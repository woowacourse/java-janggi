package domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionDeltaTest {
    @Test
    @DisplayName("(1,1)의 좌표를 (10,9)로 판단할 수 있다.")
    void rotate180form_좌표를_180도_돌리기_테스트() {
        Position rotatedPosition = Position.rotate180from(Position.of(1, 1));

        assertThat(rotatedPosition.getRow()).isEqualTo(10);
        assertThat(rotatedPosition.getColumn()).isEqualTo(9);
    }
}
