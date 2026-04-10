package janggi.domain.piece.path.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.coordination.BoardCoordination;
import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.point.Point;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LinearCandidatePathStrategyTest {
    private static final PathStrategy LINEAR_PATH_STRATEGY = new LinearPathStrategy();

    @Nested
    @DisplayName("calculate(): ")
    class Calculate {
        public static Stream<Arguments> calculate() {
            return Stream.of(
                    Arguments.of(
                            new Movement(List.of(Direction.NORTH)),
                            new Point(7, 0),
                            List.of(new Point(8, 0), new Point(9, 0))

                    ),
                    Arguments.of(
                            new Movement(List.of(Direction.NORTH_EAST)),
                            new Point(0, 0),
                            List.of(new Point(1, 1),
                                    new Point(2, 2),
                                    new Point(3, 3),
                                    new Point(4, 4),
                                    new Point(5, 5),
                                    new Point(6, 6),
                                    new Point(7, 7),
                                    new Point(8, 8))
                    )
            );
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("movement에 따른 경로 계산")
        void calculate(Movement movement, Point from, List<Point> expected) {
            assertThat(LINEAR_PATH_STRATEGY.calculate(movement, from, BoardCoordination::isInRange))
                    .containsAll(expected);
        }

        @Test
        @DisplayName("[예외] 디렉션이 하나가 아니라면 예외를 반환한다.")
        void whenDirectionIsNotUnique() {
            Movement movement = new Movement(List.of(Direction.NORTH_EAST, Direction.EAST));
            Point point = new Point(0, 0);
            assertThatThrownBy(() -> LINEAR_PATH_STRATEGY.calculate(movement, point, BoardCoordination::isInRange))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
