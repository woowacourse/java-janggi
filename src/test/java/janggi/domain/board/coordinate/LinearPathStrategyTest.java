package janggi.domain.board.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LinearPathStrategyTest {

    private static final PathStrategy LINEAR_PATH_STRATEGY = new LinearPathStrategy();

    @Nested
    @DisplayName("calculate(): ")
    class Calculate {

        public static Stream<Arguments> calculate() {
            return Stream.of(
                    Arguments.of(
                            new Pattern(List.of(Direction.NORTH)),
                            Point.of(7, 0),
                            List.of(Point.of(8, 0), Point.of(9, 0))

                    ),
                    Arguments.of(
                            new Pattern(List.of(Direction.NORTH_EAST)),
                            Point.of(0, 0),
                            List.of(Point.of(1, 1),
                                    Point.of(2, 2),
                                    Point.of(3, 3),
                                    Point.of(4, 4),
                                    Point.of(5, 5),
                                    Point.of(6, 6),
                                    Point.of(7, 7),
                                    Point.of(8, 8))
                    )
            );
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("pattern에 따른 경로 계산")
        void calculate(Pattern pattern, Point from, List<Point> expected) {
            assertThat(LINEAR_PATH_STRATEGY.calculate(pattern, from))
                    .containsAll(expected);
        }

        @Test
        @DisplayName("[예외] 디렉션이 하나가 아니라면 예외를 반환한다.")
        void whenDirectionIsNotUnique() {
            assertThatThrownBy(
                    () -> LINEAR_PATH_STRATEGY.calculate(
                            new Pattern(List.of(Direction.NORTH_EAST, Direction.EAST)),
                            Point.of(0, 0)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
