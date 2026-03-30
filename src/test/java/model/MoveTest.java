package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import model.move.Direction;
import model.move.Move;
import model.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MoveTest {

    @Test
    void 시작점과_끝점이_같으면_움직일_수_없다() {
        Position samePosition = Position.of(1, 1);
        assertThatThrownBy(() -> Move.of(samePosition, samePosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시작점과_끝점이_다르면_움직일_수_있다() {
        Position from = Position.of(1, 1);
        Position to = Position.of(2, 2);
        assertDoesNotThrow(() -> Move.of(from, to));
    }

    @ParameterizedTest
    @MethodSource("diffPosition")
    void 출발지_도착지_차이로_방향을_알_수_있다(Position from, Position to, Direction expectedDirection) {
        Move move = Move.of(from, to);
        Direction actual = move.direction();

        assertEquals(expectedDirection, actual);
    }

    @ParameterizedTest
    @MethodSource("diffPosition")
    void 출발지_도착지_차이로_거리를_알_수_있다(Position from, Position to, Direction expectedDirection, int expectedDistance) {
        Move move = Move.of(from, to);
        int actual = move.distance();

        assertEquals(expectedDistance, actual);
    }

    @ParameterizedTest
    @MethodSource("diffPosition")
    void 출발지_도착지는_직선인지_알_수_있다(Position from, Position to, Direction expectedDirection, int expectedDistance,
                              boolean expectedStraight) {
        Move move = Move.of(from, to);
        boolean actual = move.isStraight();

        assertEquals(expectedStraight, actual);
    }

    private static Stream<Arguments> diffPosition() {
        return Stream.of(
                Arguments.arguments(Position.of(4, 1), Position.of(1, 1), Direction.UP, 3, true),
                Arguments.arguments(Position.of(5, 5), Position.of(4, 4), Direction.UP_LEFT, 1, false),
                Arguments.arguments(Position.of(4, 5), Position.of(3, 7), Direction.UP_RIGHT, 2, false),
                Arguments.arguments(Position.of(4, 1), Position.of(4, 5), Direction.RIGHT, 4, true),
                Arguments.arguments(Position.of(4, 1), Position.of(5, 5), Direction.DOWN_RIGHT, 4, false),
                Arguments.arguments(Position.of(5, 5), Position.of(6, 5), Direction.DOWN, 1, true),
                Arguments.arguments(Position.of(5, 5), Position.of(6, 3), Direction.DOWN_LEFT, 2, false),
                Arguments.arguments(Position.of(3, 5), Position.of(3, 3), Direction.LEFT, 2, true)
        );
    }
}
