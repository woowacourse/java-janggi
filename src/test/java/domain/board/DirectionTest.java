package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DirectionTest {
    @Test
    @DisplayName("방향이 대각선과 직선이 있을 떄 직선 먼저 반환한다.")
    void directionOrderTest() {
        List<Direction> expectedDirection = List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN);
        assertThat(Direction.findDirections(2, -3)).isEqualTo(expectedDirection);
    }
}
