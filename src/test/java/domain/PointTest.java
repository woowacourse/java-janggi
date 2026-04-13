package domain;

import domain.move.Vector;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PointTest {
    @Test
    @DisplayName("좌표가 같으면 동등한 객체로 취급한다")
    void should_be_equal_when_points_are_same() {
        Point actual = new Point(3, 3);
        Point expected = new Point(3, 3);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("(0, 0)과 (7, 0)은 열(X / File)이 같아야 한다")
    void should_return_true_when_files_match() {
        Assertions.assertThat(new Point(7, 0).isSameFile(new Point(0, 0)))
                .isTrue();
    }

    @Test
    @DisplayName("(0, 0)과 (0, 7)은 행(Y / Row)이 같아야 한다")
    void should_return_true_when_rows_match() {
        Assertions.assertThat(new Point(0, 7).isSameRow(new Point(0, 0)))
                .isTrue();
    }

    @Nested
    @DisplayName("Point 생성 경계값 실패 검증")
    class PointCreationFailureValidation {
        @Test
        @DisplayName("Y좌표(Row)가 0보다 작으면 예외가 발생한다")
        void should_throw_exception_when_y_is_negative() {
            Assertions.assertThatThrownBy(() -> new Point(-1, 5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Y좌표(Row)가 9보다 크면 예외가 발생한다")
        void should_throw_exception_when_y_is_over_max() {
            Assertions.assertThatThrownBy(() -> new Point(10, 5))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("X좌표(File)가 0보다 작으면 예외가 발생한다")
        void should_throw_exception_when_x_is_negative() {
            Assertions.assertThatThrownBy(() -> new Point(5, -1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("X좌표(File)가 8보다 크면 예외가 발생한다")
        void should_throw_exception_when_x_is_over_max() {
            Assertions.assertThatThrownBy(() -> new Point(5, 9))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("canMake 메서드 검증")
    class CanMakeValidation {
        @Test
        @DisplayName("현재 위치에서 정상 범위 내의 위치 증감이 주어지면 true를 반환한다")
        void should_return_true_when_valid_offset() {
            Point point = new Point(5, 5);
            Assertions.assertThat(point.canMake(1, -1)).isTrue();
        }

        @Test
        @DisplayName("현재 위치에서 X(열) 범위 밖으로 나가는 증감이면 false를 반환한다")
        void should_return_false_when_invalid_x_offset() {
            Point point = new Point(5, 8);
            Assertions.assertThat(point.canMake(1, 1)).isFalse();
        }

        @Test
        @DisplayName("현재 위치에서 Y(행) 범위 밖으로 나가는 증감이면 false를 반환한다")
        void should_return_false_when_invalid_y_offset() {
            Point point = new Point(0, 5);
            Assertions.assertThat(point.canMake(-1, 1)).isFalse();
        }
    }

    @Nested
    @DisplayName("movePoint 메서드 검증")
    class MovePointValidation {
        @Test
        @DisplayName("주어진 y, x 값만큼 정상적으로 이동된 새로운 Point를 반환한다")
        void should_return_new_point_moved() {
            Point point = new Point(4, 4);
            Point expected = new Point(6, 3);

            Assertions.assertThat(point.movePoint(2, -1)).isEqualTo(expected);
        }

        @Test
        @DisplayName("범위를 넘어서게 이동을 시도하면 예외가 발생한다")
        void should_throw_exception_when_moved_out_of_bounds() {
            Point point = new Point(4, 4);

            Assertions.assertThatThrownBy(() -> point.movePoint(-5, 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Point 위치 이동 검증")
    class PointMoveValidation {
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
