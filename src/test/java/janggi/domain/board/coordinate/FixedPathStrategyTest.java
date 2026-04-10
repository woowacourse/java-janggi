package janggi.domain.board.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FixedPathStrategyTest {

    private static final PathStrategy FIXED_PATH_STRATEGY = new FixedPathStrategy();

    public static Stream<Arguments> calculate() {
        return Stream.of(
                Arguments.of(
                        new Pattern(List.of(Direction.NORTH, Direction.EAST, Direction.NORTH_EAST)),
                        Point.of(7, 0),
                        List.of(Point.of(8, 0), Point.of(8, 1), Point.of(9, 2))
                ),
                Arguments.of(
                        new Pattern(List.of(Direction.NORTH)),
                        Point.of(9, 0),
                        Collections.emptyList())
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("calculate(): pattern에 따른 경로 계산")
    void calculate(Pattern pattern, Point from, List<Point> expected) {
        assertThat(FIXED_PATH_STRATEGY.calculate(pattern, from))
                .containsAll(expected);
    }
}
