package janggi.domain.piece.movement.fixed;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DirectionTest {

    static Stream<Arguments> 시작점과_끝점을_통해_방향을_찾는다_테스트_케이스() {
        return Stream.of(
            Arguments.of(new Position(1, 1), new Position(1, 2), Direction.UP),
            Arguments.of(new Position(1, 1), new Position(1, 0), Direction.DOWN),
            Arguments.of(new Position(1, 1), new Position(0, 1), Direction.LEFT),
            Arguments.of(new Position(1, 1), new Position(2, 1), Direction.RIGHT),
            Arguments.of(new Position(1, 1), new Position(0, 2), Direction.UP_LEFT),
            Arguments.of(new Position(1, 1), new Position(2, 2), Direction.UP_RIGHT),
            Arguments.of(new Position(1, 1), new Position(0, 0), Direction.DOWN_LEFT),
            Arguments.of(new Position(1, 1), new Position(2, 0), Direction.DOWN_RIGHT),
            Arguments.of(new Position(1, 1), new Position(1, 3), Direction.UP),
            Arguments.of(new Position(1, 1), new Position(1, 4), Direction.UP),
            Arguments.of(new Position(1, 1), new Position(3, 1), Direction.RIGHT),
            Arguments.of(new Position(1, 1), new Position(1, 3), Direction.UP),
            Arguments.of(new Position(1, 1), new Position(3, 3), Direction.UP_RIGHT)
        );
    }

    @ParameterizedTest
    @MethodSource("시작점과_끝점을_통해_방향을_찾는다_테스트_케이스")
    void 시작점과_끝점을_통해_방향을_찾는다(Position origin, Position destination, Direction expected) {
        assertThat(Direction.get(origin, destination)).isEqualTo(expected);
    }
}
