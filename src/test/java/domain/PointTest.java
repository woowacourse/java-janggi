package domain;

import static domain.piece.Direction.NORTH;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PointTest {

    @Nested
    @DisplayName("Point가 방향을 받았을 때")
    class TestMovePoint {
        @Test
        @DisplayName("정해진 방향으로 이동한다.")
        void test_move() {
            //given
            final Point point = new Point(0, 0);
            final Point expect = new Point(1, 0);
            //when
            final Point actual = point.move(NORTH);

            //then
            Assertions.assertThat(actual).isEqualTo(expect);
        }
    }
}
