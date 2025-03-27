package janggi.domain.piece.movement.fixed;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class VectorTest {

    private static Stream<Arguments> Position에_벡터를_적용한다_테스트_케이스() {
        return Stream.of(
            Arguments.of(new Vector(1, 1), new Position(1, 1), new Position(3, 3), new Position(2, 2)),
            Arguments.of(new Vector(1, 0), new Position(1, 1), new Position(3, 1), new Position(2, 1)),
            Arguments.of(new Vector(0, 1), new Position(1, 1), new Position(1, 3), new Position(1, 2))
        );
    }

    private static Stream<Arguments> 두_포지션_사이의_상대적인_거리를_비교한다_테스트_케이스() {
        return Stream.of(
            Arguments.of(new Vector(3, 2), new Position(5, 5), new Position(8, 7), true),
            Arguments.of(new Vector(3, 2), new Position(5, 5), new Position(2, 3), true),
            Arguments.of(new Vector(3, 2), new Position(5, 5), new Position(8, 8), false),
            Arguments.of(new Vector(3, 2), new Position(5, 5), new Position(2, 4), false)
        );
    }

    @ParameterizedTest
    @MethodSource("Position에_벡터를_적용한다_테스트_케이스")
    void Position에_벡터를_적용한다(Vector vector, Position position, Position destination, Position expected) {

        assertThat(vector.apply(position, destination)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("두_포지션_사이의_상대적인_거리를_비교한다_테스트_케이스")
    void 두_포지션_사이의_상대적인_거리를_비교한다(Vector vector, Position origin, Position destination, boolean expected) {
        assertThat(vector.hasRelativeOffsetFrom(origin, destination)).isEqualTo(expected);
    }
}
