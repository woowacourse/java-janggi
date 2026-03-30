package domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinateTest {
    @Test
    @DisplayName("(1,1)의 좌표를 (10,9)로 판단할 수 있다.")
    void 좌표를_180도_회전_테스트() {
        Coordinate rotatedCoordinate = Coordinate.rotate180from(1, 1);

        assertThat(rotatedCoordinate.row()).isEqualTo(10);
        assertThat(rotatedCoordinate.column()).isEqualTo(9);
    }
}
