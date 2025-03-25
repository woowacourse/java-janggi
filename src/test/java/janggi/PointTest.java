package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Point;
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

    @DisplayName("좌표가 시작값 이상, 끝값 미만이면 true를 리턴한다.")
    @Test
    void rangeTest() {
        // given
        Point point = new Point(0, 4);

        // when & then
        assertThat(point.isXInRange(0, 5))
                .isTrue();
        assertThat(point.isYInRange(0, 5))
                .isTrue();
    }

    @DisplayName("좌표가 시작값 이상, 끝값 미만 범위를 벗어나면 false를 리턴한다.")
    @ParameterizedTest
    @CsvSource({
            "6,-2",
            "5,5",
            "-2,6",
            "-1,5"
    })
    void outOfRangeTeset(int x, int y) {
        // given
        Point point = new Point(x, y);

        // when & then
        assertThat(point.isXInRange(0, 5))
                .isFalse();
        assertThat(point.isYInRange(0, 5))
                .isFalse();
    }
}
