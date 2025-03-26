package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {

    @Nested
    class linearTest {

        @DisplayName("geDirection은 방향을 검사한다")
        @ParameterizedTest
        @MethodSource("directionArguments")
        void getDirectionTest(JanggiCoordinate from, JanggiCoordinate to, Direction direction) {
            Direction calcDirection = Direction.getDirection(from, to);

            assertThat(calcDirection).isEqualTo(direction);
        }

        private static Stream<Arguments> directionArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(7, 2), new JanggiCoordinate(5, 2), Direction.UP),
                    Arguments.arguments(new JanggiCoordinate(5, 3), new JanggiCoordinate(3, 3), Direction.UP),
                    Arguments.arguments(new JanggiCoordinate(1, 2), new JanggiCoordinate(1, 9), Direction.RIGHT),
                    Arguments.arguments(new JanggiCoordinate(5, 4), new JanggiCoordinate(5, 6), Direction.RIGHT),
                    Arguments.arguments(new JanggiCoordinate(3, 7), new JanggiCoordinate(6, 7), Direction.DOWN),
                    Arguments.arguments(new JanggiCoordinate(8, 5), new JanggiCoordinate(10, 5), Direction.DOWN),
                    Arguments.arguments(new JanggiCoordinate(7, 4), new JanggiCoordinate(7, 1), Direction.LEFT)
            );
        }

        @DisplayName("geDirection(distance)는 거리만큼 떨어진 곳을 기준으로 방향을 검사한다.")
        @ParameterizedTest
        @MethodSource("directionDistanceArguments")
        void getDirectionDistanceTest(JanggiCoordinate to, Direction direction) {
            JanggiCoordinate from = new JanggiCoordinate(7, 5);
            int distance = 3;
            Direction calcDirection = Direction.getDirection(from, to, distance);

            assertThat(calcDirection).isEqualTo(direction);
        }

        private static Stream<Arguments> directionDistanceArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(4, 7), Direction.UP),
                    Arguments.arguments(new JanggiCoordinate(4, 3), Direction.UP),
                    Arguments.arguments(new JanggiCoordinate(5, 8), Direction.RIGHT),
                    Arguments.arguments(new JanggiCoordinate(9, 8), Direction.RIGHT),
                    Arguments.arguments(new JanggiCoordinate(10, 7), Direction.DOWN),
                    Arguments.arguments(new JanggiCoordinate(10, 3), Direction.DOWN),
                    Arguments.arguments(new JanggiCoordinate(9, 2), Direction.LEFT),
                    Arguments.arguments(new JanggiCoordinate(5, 2), Direction.LEFT)
            );
        }

        @DisplayName("from으로 부터 to로 대각선 방향을 검사한다.")
        @ParameterizedTest
        @MethodSource("diagonalDirectionArguments")
        void getDirectionDiagonalTest(JanggiCoordinate to, Direction direction) {
            JanggiCoordinate from = new JanggiCoordinate(7, 5);

            Direction calcDirection = Direction.getDiagonalDirection(from, to);

            assertThat(calcDirection).isEqualTo(direction);
        }

        private static Stream<Arguments> diagonalDirectionArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(4, 7), Direction.RIGHT_UP),
                    Arguments.arguments(new JanggiCoordinate(5, 8), Direction.RIGHT_UP),
                    Arguments.arguments(new JanggiCoordinate(9, 8), Direction.RIGHT_DOWN),
                    Arguments.arguments(new JanggiCoordinate(10, 7), Direction.RIGHT_DOWN),
                    Arguments.arguments(new JanggiCoordinate(10, 3), Direction.LEFT_DOWN),
                    Arguments.arguments(new JanggiCoordinate(9, 2), Direction.LEFT_DOWN),
                    Arguments.arguments(new JanggiCoordinate(4, 3), Direction.LEFT_UP),
                    Arguments.arguments(new JanggiCoordinate(5, 2), Direction.LEFT_UP)
            );
        }
    }
}