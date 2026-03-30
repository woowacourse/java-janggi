package domain.point;

import domain.move.directions.Vector;
import domain.point.exception.PointException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static domain.point.exception.PointError.POINT_RANGE_IS_OVER;

public class PointTest {
    @Test
    @DisplayName("좌표가 범위를 벗어나는 경우에 에러가 발생한다")
    void shouldThrowExceptionWhenCoordinateIsOutOfBounds() {
        int outOfIndexY = 10;
        int outOfIndexX = 9;

        Assertions.assertThatThrownBy(() -> {
                    new Point(outOfIndexY, outOfIndexX);
                }).isInstanceOf(PointException.class)
                .hasMessage(POINT_RANGE_IS_OVER.getMessage());
    }

    @Test
    @DisplayName("좌표가 같으면 동등한 객체로 취급한다")
    void shouldBeEqualWhenPointsAreSame() {
        int y = 3;
        int x = 3;

        Point actual = new Point(y, x);
        Point expected = new Point(y, x);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Nested
    @DisplayName("Point 위치 이동 검증")
    class shouldUpdateDirectionWhenPointMove {

        @Test
        @DisplayName("DOWN시 Y축이 1증가해야한다.")
        void increaseRowWhenMovingDown() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.DOWN);
            Point expected = new Point(2, 1);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("UP시 Y축이 1감소해야한다.")
        void increaseRowWhenMovingUp() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.UP);
            Point expected = new Point(0, 1);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("LEFT시 X축이 1감소해야한다.")
        void decreaseFileWhenMovingLeft() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.LEFT);
            Point expected = new Point(1, 0);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("RIGHT시 X축이 1증가해야한다.")
        void increaseFileWhenMovingRight() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.RIGHT);
            Point expected = new Point(1, 2);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("LEFT_UP시 X축은 1감소하고 Y축도 1감소한다.")
        void decreaseRowAndFileWhenMovingLeftAndUp() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.LEFT_UP);
            Point expected = new Point(0, 0);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("LEFT_DOWN시 X축은 1감소하고 Y축은 1증가한다.")
        void increaseRowAndDecreaseFileWhenMovingLeftAndDown() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.LEFT_DOWN);
            Point expected = new Point(2, 0);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }


        @Test
        @DisplayName("RIGHT_UP시 X축은 1증가하고 Y축은 1감소한다.")
        void decreaseRowAndIncreaseFileWhenMovingRightUp() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.RIGHT_UP);
            Point expected = new Point(0, 2);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }


        @Test
        @DisplayName("RIGHT_DOWN시 X축은 1증가하고 Y축은 1증가한다.")
        void increaseRowAndFileWhenMovingRightDown() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.RIGHT_DOWN);
            Point expected = new Point(2, 2);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("이동 시, 장기판 범위를 이탈하면 예외가 발생한다.")
        void shouldThrowException_WhenPointMovingIsOutOfBound() {
            Point point = new Point(0, 0);

            Assertions.assertThatThrownBy(() -> {
                        point.next(Vector.LEFT_UP);
                    }).isInstanceOf(PointException.class)
                    .hasMessage(POINT_RANGE_IS_OVER.getMessage());
        }

    }

}
