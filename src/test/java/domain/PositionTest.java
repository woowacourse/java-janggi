package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {

    @Nested
    class columnCoordinate {
        @Test
        @DisplayName("행 좌표가 0 미만일 경우 예외가 발생한다.")
        void throwException_When_RowCoordinateLessThanZero() {
            assertThatThrownBy(() -> new Position(-1, 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("행 좌표가 0인 경우 생성된 위치의 행 좌표는 0이다.")
        void generate_MinRowCoordinate() {
            Position position = new Position(0, 1);

            Assertions.assertThat(position.column()).isEqualTo(0);
        }

        @Test
        @DisplayName("행 좌표가 9 초과인 경우 예외가 발생한다.")
        void throwException_When_RowCoordinateMoreThanNine() {
            assertThatThrownBy(() -> new Position(10, 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("행 좌표가 9인 경우 생성된 위치의 행 좌표는 9이다.")
        void generate_MaxRowCoordinate() {
            Position position = new Position(9, 1);

            Assertions.assertThat(position.column()).isEqualTo(9);
        }
    }

    @Nested
    class RowCoordinate {
        @Test
        @DisplayName("열 좌표가 1 미만일 경우 예외가 발생한다.")
        void throwException_When_Row_CoordinateLessThanZero() {
            assertThatThrownBy(() -> new Position(1, 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("열 좌표가 1인 경우 생성된 위치의 열 좌표는 1이다.")
        void generate_Min_Row_Coordinate() {
            Position position = new Position(1, 1);

            Assertions.assertThat(position.row()).isEqualTo(1);
        }


        @Test
        @DisplayName("열 좌표가 9 초과인 경우 예외가 발생한다.")
        void throwException_When_Row_CoordinateMoreThanNine() {
            assertThatThrownBy(() -> new Position(1, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("열 좌표가 9인 경우 생성된 위치의 열 좌표는 9이다.")
        void generate_Max_Row_Coordinate() {
            Position position = new Position(1, 9);

            Assertions.assertThat(position.row()).isEqualTo(9);
        }
    }
}
