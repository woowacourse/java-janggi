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
    @ParameterizedTest
    @CsvSource({
            "4, 5, true",
            "6, 5, true",
            "5, 6, false",
            "5, 4, false",
    })
    void horizontalTest(int otherX, int otherY, boolean expected) {
        // given
        Point point = new Point(5, 5);
        Point otherPoint = new Point(otherX, otherY);

        // when & then
        assertThat(point.isHorizontal(otherPoint))
                .isSameAs(expected);
    }

    @DisplayName("같은 수직선상에 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "5, 4, true",
            "5, 6, true",
            "4, 5, false",
            "6, 5, false",
    })
    void verticalTest(int otherX, int otherY, boolean expected) {
        // given
        Point point = new Point(5, 5);
        Point otherPoint = new Point(otherX, otherY);

        // when & then
        assertThat(point.isVertical(otherPoint))
                .isSameAs(expected);
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

    @DisplayName("수평으로 한 칸 이동한 좌표를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 0",
            "1, 0, 0, 0",
    })
    void getNextHorizontalStepTest(int fromX, int fromY, int toX, int toY) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);

        // when
        Point nextHorizontalStep = point.getNextHorizontalStep(otherPoint);

        // then
        assertThat(nextHorizontalStep)
                .isEqualTo(otherPoint);
    }

    @DisplayName("수직으로 한 칸 이동한 좌표를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1",
            "0, 1, 0, 0",
    })
    void getNextVerticalStepTest(int fromX, int fromY, int toX, int toY) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);

        // when
        Point nextVerticalStep = point.getNextVerticalStep(otherPoint);

        // then
        assertThat(nextVerticalStep)
                .isEqualTo(otherPoint);
    }

    @DisplayName("두 좌표의 중간 좌표를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 2, 2, 1, 1",
            "0, 0, 1, 1, 0, 0",
    })
    void middlePointTest(int fromX, int fromY, int toX, int toY, int middleX, int middleY) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);
        Point expected = new Point(middleX, middleY);

        // when
        Point middlePoint = point.middlePoint(otherPoint);

        // then
        assertThat(middlePoint)
                .isEqualTo(expected);
    }

    @DisplayName("두 좌표가 대각선상에 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 1, true",
            "0, 0, 2, 2, true",
            "0, 0, 1, 0, false",
            "0, 0, 0, 1, false",
    })
    void isDiagonalTest(int fromX, int fromY, int toX, int toY, boolean expected) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);

        // when & then
        assertThat(point.isDiagonal(otherPoint))
                .isEqualTo(expected);
    }

    @DisplayName("대각선으로 한 칸 이동한 좌표를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 1, 1, 1",
            "0, 0, 2, 2, 1, 1",
    })
    void getNextDiagonalStepTest(int fromX, int fromY, int toX, int toY, int nextX, int nextY) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);
        Point expected = new Point(nextX, nextY);

        // when
        Point nextDiagonalStep = point.getNextDiagonalStep(otherPoint);

        // then
        assertThat(nextDiagonalStep)
                .isEqualTo(expected);
    }

    @DisplayName("대각선 이동이 아닌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 0",
            "0, 0, 0, 1",
    })
    void shouldThrowException_WhenNotDiagonal(int fromX, int fromY, int toX, int toY) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> point.getNextDiagonalStep(otherPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("대각선 이동이 아닌 경우 다음 위치를 계산할 수 없습니다.");
    }

    @DisplayName("대각선으로 한 칸 떨어져 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "0, 0, 2, 2, false",
            "0, 0, 1, 1, true",
            "0, 0, 0, 1, false"
    })
    void isOneDiagonalStepAwayTest(int fromX, int fromY, int toX, int toY, boolean expected) {
        // given
        Point point = new Point(fromX, fromY);
        Point otherPoint = new Point(toX, toY);

        // when & then
        assertThat(point.isOneDiagonalStepAway(otherPoint))
                .isEqualTo(expected);
    }
}
