package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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
        assertThatCode(() -> new Point(x, y)).doesNotThrowAnyException();
    }

    @DisplayName("같은 수평선상에 있는지 확인한다.")
    @Test
    void horizontalTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(2, 0);

        // when & then
        assertThat(point.isHorizontallyAlignedWith(otherPoint)).isTrue();
    }

    @DisplayName("같은 수직선상에 있는지 확인한다.")
    @Test
    void verticalTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(0, 2);

        // when & then
        assertThat(point.isVerticallyAlignedWith(otherPoint)).isTrue();
    }

    @DisplayName("같은 좌표에 있는지 확인한다.")
    @Test
    void equalsTest() {
        // given
        Point point = new Point(0, 0);
        Point otherPoint = new Point(0, 0);

        // when & then
        assertThat(point.equals(otherPoint)).isTrue();
    }

    @DisplayName("좌표가 시작값 이상, 끝값 미만이면 true를 리턴한다.")
    @Test
    void rangeTest() {
        // given
        Point point = new Point(0, 4);

        // when & then
        assertThat(point.isXBetween(0, 5)).isTrue();
        assertThat(point.isYBetween(0, 5)).isTrue();
    }

    @DisplayName("좌표가 시작값 이상, 끝값 미만 범위를 벗어나면 false를 리턴한다.")
    @ParameterizedTest
    @CsvSource({"6,-2", "5,5", "-2,6", "-1,5"})
    void outOfRangeTest(int x, int y) {
        // given
        Point point = new Point(x, y);

        // when & then
        assertThat(point.isXBetween(0, 5)).isFalse();
        assertThat(point.isYBetween(0, 5)).isFalse();
    }

    @DisplayName("출발지와 목적지 사이 같은 수평 선상에 있는 모든 포인트를 찾는다.")
    @Test
    void findHorizontalPoints() {
        // given
        Point from = new Point(0, 5);
        Point to = new Point(5, 5);

        // when
        Set<Point> horizontalPoints = from.findHorizontalPointsBetween(to);

        // then
        assertThat(horizontalPoints)
                .containsExactlyInAnyOrder(new Point(1, 5), new Point(2, 5), new Point(3, 5), new Point(4, 5));
    }

    @DisplayName("출발지와 목적지 사이 같은 수직 선상에 있는 모든 포인트를 찾는다.")
    @Test
    void findVerticalPoints() {
        // given
        Point from = new Point(5, 0);
        Point to = new Point(5, 5);

        // when
        Set<Point> verticalPoints = from.findVerticalPointsBetween(to);

        // then
        assertThat(verticalPoints)
                .containsExactlyInAnyOrder(new Point(5, 1), new Point(5, 2), new Point(5, 3), new Point(5, 4));
    }

    @DisplayName("출발지와 목적지 사이 같은 수평 선상에 있는 가장 가까운 포인트를 찾는다.")
    @Test
    void findNextHorizontalPoint() {
        // given
        Point from = new Point(0, 0);
        Point to = new Point(5, 0);

        // when
        Point horizontalPoint = from.nextHorizontalPointTo(to);

        // then
        assertThat(horizontalPoint).isEqualTo(new Point(1, 0));
    }

    @DisplayName("출발지와 목적지 사이 같은 수직 선상에 있는 가장 가까운 포인트를 찾는다.")
    @Test
    void findNextVerticalPoint() {
        // given
        Point from = new Point(5, 0);
        Point to = new Point(5, -2);

        // when
        Point verticalPoint = from.nextVerticalPointTo(to);

        // then
        assertThat(verticalPoint).isEqualTo(new Point(5, -1));
    }

    @DisplayName("포인트를 찾을 때 좌표가 같으면 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenSameCoordinates() {
        // given
        Point from = new Point(0, 0);
        Point to = new Point(0, 0);

        // when && then
        assertThatCode(() -> from.nextHorizontalPointTo(to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("x좌표가 같습니다.");
        assertThatCode(() -> from.nextVerticalPointTo(to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("y좌표가 같습니다.");
    }

    @DisplayName("짝수 좌표에 대한 중간 좌표를 찾는다.")
    @Test
    void findCenterPointWithEvenCoordinatesTest() {
        // given
        Point from = new Point(0, 0);
        Point to = new Point(4, 4);

        // when
        Point centerPoint = from.midPointBetween(to);

        // then
        assertThat(centerPoint).isEqualTo(new Point(2, 2));
    }

    @DisplayName("홀수 좌표에 대한 중간 좌표를 찾는다.")
    @Test
    void findCenterPointWithOddCoordinatesTest() {
        // given
        Point from = new Point(1, 1);
        Point to = new Point(4, 6);

        // when
        Point centerPoint = from.midPointBetween(to);

        // then
        assertThat(centerPoint).isEqualTo(new Point(2, 3));
    }
}
