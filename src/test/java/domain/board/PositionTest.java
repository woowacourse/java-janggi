package domain.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {

    @Nested
    class XCoordinate {
        @Test
        @DisplayName("X 좌표가 1 미만일 경우 예외가 발생한다.")
        void throwException_When_XCoordinateLessThanZero() {
            assertThatThrownBy(() -> new Position(0, 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("X 좌표가 1인 경우 생성된 위치의 X 좌표는 1이다.")
        void generate_MinXCoordinate() {
            Position position = new Position(1, 1);

            Assertions.assertThat(position.x()).isEqualTo(1);
        }

        @Test
        @DisplayName("X 좌표가 9 초과인 경우 예외가 발생한다.")
        void throwException_When_XCoordinateMoreThanNine() {
            assertThatThrownBy(() -> new Position(10, 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("X 좌표가 9인 경우 생성된 위치의 X 좌표는 9이다.")
        void generate_MaxXCoordinate() {
            Position position = new Position(9, 1);

            Assertions.assertThat(position.x()).isEqualTo(9);
        }
    }

    @Nested
    class YCoordinate {
        @Test
        @DisplayName("Y 좌표가 1 미만일 경우 예외가 발생한다.")
        void throwException_When_Y_CoordinateLessThanZero() {
            assertThatThrownBy(() -> new Position(1, 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Y 좌표가 1인 경우 생성된 위치의 Y 좌표는 1이다.")
        void generate_Min_Y_Coordinate() {
            Position position = new Position(1, 1);

            Assertions.assertThat(position.y()).isEqualTo(1);
        }


        @Test
        @DisplayName("Y 좌표가 10 초과인 경우 예외가 발생한다.")
        void throwException_When_Y_CoordinateMoreThanNine() {
            assertThatThrownBy(() -> new Position(1, 11))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Y 좌표가 10인 경우 생성된 위치의 Y 좌표는 10이다.")
        void generate_Max_Y_Coordinate() {
            Position position = new Position(1, 10);

            Assertions.assertThat(position.y()).isEqualTo(10);
        }
    }
}
