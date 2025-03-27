package domain.board;

import static domain.movements.Direction.NORTH;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class BoardPointTest {

    @Nested
    @DisplayName("Point가 방향을 받았을 때")
    class TestMoveBoardPoint {
        @Test
        @DisplayName("정해진 방향으로 이동한다.")
        void test_move() {
            //given
            final BoardPoint boardPoint = new BoardPoint(0, 0);
            final BoardPoint expect = new BoardPoint(1, 0);
            //when
            final BoardPoint actual = boardPoint.move(NORTH);

            //then
            Assertions.assertThat(actual).isEqualTo(expect);
        }
    }
}
