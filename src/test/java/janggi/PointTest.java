package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Point;
import java.util.Set;
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
        assertThat(point.isHorizontallyAlignedWith(otherPoint))
                .isTrue();
    }

    @DisplayName("같은 수직선상에 있는지 확인한다.")
    @Test
    void verticalTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(0, 2);

        // when & then
        assertThat(point.isVerticallyAlignedWith(otherPoint))
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
        assertThat(point.isXWithin(0, 5))
                .isTrue();
        assertThat(point.isYWithin(0, 5))
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
        assertThat(point.isXWithin(0, 5))
                .isFalse();
        assertThat(point.isYWithin(0, 5))
                .isFalse();
    }

    @DisplayName("출발지와 목적지 사이 같은 수평 선상에 있는 모든 포인트를 찾는다.")
    @Test
    void findHorizontalPoints() {
        // given
        Point fromPoint = new Point(0, 5);
        Point toPoint = new Point(5, 5);

        // when
        Set<Point> horizontalPoints = fromPoint.findHorizontalPointsBetween(toPoint);

        // then
        assertThat(horizontalPoints)
                .containsExactlyInAnyOrder(
                        new Point(1, 5),
                        new Point(2, 5),
                        new Point(3, 5),
                        new Point(4, 5)
                );
    }

    @DisplayName("출발지와 목적지 사이 같은 수직 선상에 있는 모든 포인트를 찾는다.")
    @Test
    void findVerticalPoints() {
        // given
        Point fromPoint = new Point(5, 0);
        Point toPoint = new Point(5, 5);

        // when
        Set<Point> verticalPoints = fromPoint.findVerticalPointsBetween(toPoint);

        // then
        assertThat(verticalPoints)
                .containsExactlyInAnyOrder(
                        new Point(5, 1),
                        new Point(5, 2),
                        new Point(5, 3),
                        new Point(5, 4)
                );
    }

    @DisplayName("출발지와 목적지 사이 같은 수평 선상에 있는 가장 가까운 포인트를 찾는다.")
    @Test
    void findNextHorizontalPoint() {
        // given
        Point fromPoint = new Point(0, 0);
        Point toPoint = new Point(5, 0);

        // when
        Point horizontalPoint = fromPoint.getNextHorizontalPointToward(toPoint);

        // then
        assertThat(horizontalPoint)
                .isEqualTo(new Point(1, 0));
    }

    @DisplayName("출발지와 목적지 사이 같은 수직 선상에 있는 가장 가까운 포인트를 찾는다.")
    @Test
    void findNextVerticalPoint() {
        // given
        Point fromPoint = new Point(5, 0);
        Point toPoint = new Point(5, -2);

        // when
        Point verticalPoint = fromPoint.getNextVerticalPointToward(toPoint);

        // then
        assertThat(verticalPoint)
                .isEqualTo(new Point(5, -1));
    }

    @DisplayName("포인트를 찾을 때 좌표가 같으면 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenSameCoordinates() {
        // given
        Point fromPoint = new Point(0, 0);
        Point toPoint = new Point(0, 0);

        // when && then
        assertThatCode(() -> {
            fromPoint.getNextHorizontalPointToward(toPoint);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("x좌표가 같습니다.");
        assertThatCode(() -> {
            fromPoint.getNextVerticalPointToward(toPoint);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("y좌표가 같습니다.");
    }

    @DisplayName("두 좌표의 중간 좌표를 찾는다.")
    @Test
    void findCenterPointTest() {
        // given
        Point fromPoint = new Point(0, 0);
        Point toPoint = new Point(4, 4);

        // when
        Point centerPoint = fromPoint.getCenterPointWith(toPoint);

        // then
        assertThat(centerPoint)
                .isEqualTo(new Point(2, 2));

    }
}
