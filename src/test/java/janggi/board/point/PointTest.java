package janggi.board.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PointTest {

    @DisplayName("X좌표와 Y좌표를 생성한다.")
    @Test
    void createTest() {
        // given
        int x = 1;
        int y = 2;

        // when & then
        assertThatCode(() -> new Point(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("같은 수평선상에 있는지 확인한다.")
    @Test
    void horizontalTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(2, 0);

        // when & then
        assertThat(point.isHorizontal(otherPoint))
                .isTrue();
    }

    @DisplayName("같은 수직선상에 있는지 확인한다.")
    @Test
    void verticalTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(0, 2);

        // when & then
        assertThat(point.isVertical(otherPoint))
                .isTrue();
    }

    @DisplayName("같은 좌표에 있는지 확인한다.")
    @Test
    void equalsTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(0, 0);

        // when & then
        assertThat(point.equals(otherPoint))
                .isTrue();
    }

    @DisplayName("뒤에 있는지 확인한다. (y가 더 작은지 확인)")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1, true",
            "0, 1, 0, 0, false",
    })
    void isBehindTest(int x, int y, int otherX, int otherY, boolean expected) {
        // given
        Point point = new Point(x, y);
        Point otherPoint = new Point(otherX, otherY);

        // when & then
        assertThat(point.isBehind(otherPoint))
                .isEqualTo(expected);
    }

    @DisplayName("한 칸만 떨어져 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1, true",
            "0, 0, 1, 0, true",
            "0, 0, 1, 1, false",
    })
    void isOneStepAwayTest(int fromX, int fromY, int toX, int toY, boolean expected) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);

        // when & then
        assertThat(point.isOneStepAway(otherPoint))
                .isEqualTo(expected);
    }
}
