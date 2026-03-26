package domain;

import domain.piece.move.Direction;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PointTest {
    @Test
    @DisplayName("좌표가 범위를 벗어나는 경우에 에러가 발생한다")
    void shouldThrowExceptionWhenCoordinateIsOutOfBounds() {
        int outOfIndexY = 10;
        int outOfIndexX = 9;

        Assertions.assertThatThrownBy(() -> {
            new Point(outOfIndexY, outOfIndexX);
        }).isInstanceOf(IllegalArgumentException.class);
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

    @Test
    @DisplayName("(0, 0)과 (0, 7)은 Y축이 같아야 한다")
    void returnTrueWhenFilesMatch() {
        int y = 0;
        int seven = 7;
        int zero = 0;

        Assertions.assertThat(new Point(y, seven).isSameFile(new Point(y, zero)))
                .isTrue();
    }

    @Test
    @DisplayName("(0, 0)과 (7, 0)은 X축이 같아야 한다")
    void returnTrueWhenRowsMatch() {
        int x = 0;
        int seven = 7;
        int zero = 0;

        Assertions.assertThat(new Point(seven, x).isSameRow(new Point(zero, x)))
                .isTrue();
    }

    @Test
    @DisplayName("(1,1)에서 DOWN시 (2,1)로 가야한다.")
    void increaseRowDirectionWhenMovingDown() {
        int y = 1;
        int x = 1;
        Direction down = Direction.DOWN;

        Point point = new Point(1, 1);

        Point actual = point.next(Direction.DOWN);
        Point expected = new Point(2, 1);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("(1,1)에서 UP시 (0,1)로 가야한다.")
    void increaseRowDirectionWhenMovingUp() {
        Point point = new Point(1, 1);

        Point actual = point.next(Direction.UP);
        Point expected = new Point(0, 1);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("(1,1)에서 LEFT시 (1,0)로 가야한다.")
    void decreaseFileDirectionWhenMovingLeft() {
        Point point = new Point(1, 1);

        Point actual = point.next(Direction.LEFT);
        Point expected = new Point(1, 0);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("(1,1)에서 DOWN시 (1,2)로 가야한다.")
    void increaseFileDirectionWhenMovingRight() {
        Point point = new Point(1, 1);

        Point actual = point.next(Direction.RIGHT);
        Point expected = new Point(1, 2);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
