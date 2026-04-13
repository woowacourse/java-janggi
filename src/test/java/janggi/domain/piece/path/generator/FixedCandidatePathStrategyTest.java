package janggi.domain.piece.path.generator;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.coordination.BoardCoordination;
import janggi.domain.coordination.PalaceCoordination;
import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.point.Point;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FixedCandidatePathStrategyTest {
    private static final PathStrategy FIXED_PATH_STRATEGY = new FixedPathStrategy();

    @Nested
    class Calculate {
        public static Stream<Arguments> calculate() {
            return Stream.of(
                    Arguments.of(
                            new Movement(List.of(Direction.NORTH, Direction.EAST, Direction.NORTH_EAST)),
                            new Point(7, 0),
                            List.of(new Point(8, 0), new Point(8, 1), new Point(9, 2))
                    ),
                    Arguments.of(
                            new Movement(List.of(Direction.NORTH)),
                            new Point(9, 0),
                            Collections.emptyList())
            );
        }

        public static Stream<Arguments> returnEmptyWhenInvalidRange() {
            return Stream.of(
                    Arguments.of(
                            new Movement(List.of(Direction.NORTH, Direction.EAST, Direction.NORTH_EAST)),
                            new Point(7, 0)
                    ),
                    Arguments.of(
                            new Movement(List.of(Direction.NORTH)),
                            new Point(9, 0)
                    )
            );
        }


        @ParameterizedTest
        @MethodSource
        @DisplayName("points를 movement 그대로 생성한다.")
        void calculate(Movement movement, Point from, List<Point> expected) {
            assertThat(FIXED_PATH_STRATEGY.calculate(movement, from, BoardCoordination::isInRange))
                    .containsAll(expected);
        }

        @ParameterizedTest
        @MethodSource
        @DisplayName("지정된 범위를 벗어나면 경로를 생성하지 않는다.")
        void returnEmptyWhenInvalidRange(Movement movement, Point from) {
            assertThat(FIXED_PATH_STRATEGY.calculate(movement, from, PalaceCoordination::isInRange))
                    .isEmpty();
        }
    }
}
