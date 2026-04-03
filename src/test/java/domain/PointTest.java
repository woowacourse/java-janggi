package domain;

import domain.piece.move.Vector;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    @DisplayName("좌표가 범위를 벗어나는 경우에 에러가 발생한다")
    void should_throw_exception_when_coordinate_is_out_of_bounds() {
        int outOfIndexY = 10;
        int outOfIndexX = 9;

        Assertions.assertThatThrownBy(() -> {
            new Point(outOfIndexY, outOfIndexX);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("좌표가 같으면 동등한 객체로 취급한다")
    void should_be_equal_when_points_are_same() {
        int y = 3;
        int x = 3;

        Point actual = new Point(y, x);
        Point expected = new Point(y, x);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("(0, 0)과 (0, 7)은 Y축이 같아야 한다")
    void should_return_true_when_files_match() {
        int y = 0;
        int seven = 7;
        int zero = 0;

        Assertions.assertThat(new Point(y, seven).isSameFile(new Point(y, zero)))
                .isTrue();
    }

    @Test
    @DisplayName("(0, 0)과 (7, 0)은 X축이 같아야 한다")
    void should_return_true_when_rows_match() {
        int x = 0;
        int seven = 7;
        int zero = 0;

        Assertions.assertThat(new Point(seven, x).isSameRow(new Point(zero, x)))
                .isTrue();
    }

    @Nested
    @DisplayName("Point 위치 이동 검증")
    class point_move_validation {

        @Test
        @DisplayName("DOWN시 Y축이 1증가해야한다.")
        void should_increase_row_when_moving_down() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.DOWN);
            Point expected = new Point(2, 1);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("UP시 Y축이 1감소해야한다.")
        void should_decrease_row_when_moving_up() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.UP);
            Point expected = new Point(0, 1);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("LEFT시 X축이 1감소해야한다.")
        void should_decrease_file_when_moving_left() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.LEFT);
            Point expected = new Point(1, 0);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("RIGHT시 X축이 1증가해야한다.")
        void should_increase_file_when_moving_right() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.RIGHT);
            Point expected = new Point(1, 2);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("LEFT_UP시 X축은 1감소하고 Y축도 1감소한다.")
        void should_decrease_row_and_file_when_moving_left_and_up() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.LEFT_UP);
            Point expected = new Point(0, 0);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("LEFT_DOWN시 X축은 1감소하고 Y축은 1증가한다.")
        void should_increase_row_and_decrease_file_when_moving_left_and_down() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.LEFT_DOWN);
            Point expected = new Point(2, 0);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }


        @Test
        @DisplayName("RIGHT_UP시 X축은 1증가하고 Y축은 1감소한다.")
        void should_decrease_row_and_increase_file_when_moving_right_up() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.RIGHT_UP);
            Point expected = new Point(0, 2);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }


        @Test
        @DisplayName("RIGHT_DOWN시 X축은 1증가하고 Y축은 1증가한다.")
        void should_increase_row_and_file_when_moving_right_down() {
            Point point = new Point(1, 1);

            Point actual = point.next(Vector.RIGHT_DOWN);
            Point expected = new Point(2, 2);

            Assertions.assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("이동 시, 장기판 범위를 이탈하면 예외가 발생한다.")
        void should_throw_exception_when_point_moving_is_out_of_bound() {
            Point point = new Point(0, 0);

            Assertions.assertThatThrownBy(() -> {
                point.next(Vector.LEFT_UP);
            }).isInstanceOf(IllegalArgumentException.class);

        }

    }

}
