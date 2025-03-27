package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.movement.Movement;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class CoordinateTest {

    @Nested
    class CoordinateMoveTest {

        @DisplayName("장기판을 벗어났는지 확인한다. "
                + "row의 범위: 1 ~ 10, col의 범위: 1 ~ 9")
        @ParameterizedTest
        @MethodSource("isOutOfBoundaryTestCases")
        void validateBoardBoundary(Coordinate coordinate, boolean expected) {
            assertThat(coordinate.isInBoundary()).isEqualTo(expected);
        }

        static Stream<Arguments> isOutOfBoundaryTestCases() {
            return Stream.of(
                    Arguments.of(new Coordinate(0, 1), false),
                    Arguments.of(new Coordinate(1, 0), false),
                    Arguments.of(new Coordinate(1, 1), true),
                    Arguments.of(new Coordinate(10, 9), true),
                    Arguments.of(new Coordinate(10, 10), false),
                    Arguments.of(new Coordinate(11, 9), false)
            );
        }

        @DisplayName("좌표를 원하는 만큼 움직인다")
        @Test
        void moveTest() {
            int row = 5;
            int col = 5;
            Coordinate currPosition = new Coordinate(row, col);

            Coordinate next = currPosition.move(1, 1);

            Assertions.assertThat(next).isEqualTo(new Coordinate(row + 1, col + 1));
        }

        @DisplayName("좌표를 움직인다")
        @Test
        void moveTest2() {
            Coordinate currPosition = new Coordinate(5, 5);

            Coordinate next = currPosition.move(Movement.UP_RIGHT);

            Assertions.assertThat(next).isEqualTo(new Coordinate(4, 6));
        }
    }
}
