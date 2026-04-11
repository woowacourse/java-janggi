package model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PalaceTest {

    Palace palace = new Palace();

    @ParameterizedTest
    @MethodSource("provideBound")
    void 궁성_안_범위_검사(Position pos, boolean expected) {
        boolean actual = palace.isPalaceBound(pos);

        assertEquals(expected, actual);
    }

    @Test
    void 궁성_밖_범위_검사() {
        Position pos = Position.of(6, 6);
        boolean expected = false;
        boolean actual = palace.isPalaceBound(pos);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideDiagonal")
    void 궁성_범위_안_대각선으로_이동_가능하다(Position from, Position to, boolean expected) {
        boolean actual = palace.canMove(from, to);

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideBound() {
        return Stream.of(Arguments.arguments(new Position(Row.from(1), Column.from(4)), true),
                Arguments.arguments(new Position(Row.from(1), Column.from(5)), true),
                Arguments.arguments(new Position(Row.from(1), Column.from(6)), true),
                Arguments.arguments(new Position(Row.from(2), Column.from(4)), true),
                Arguments.arguments(new Position(Row.from(2), Column.from(5)), true),
                Arguments.arguments(new Position(Row.from(2), Column.from(6)), true),
                Arguments.arguments(new Position(Row.from(3), Column.from(4)), true),
                Arguments.arguments(new Position(Row.from(3), Column.from(5)), true),
                Arguments.arguments(new Position(Row.from(3), Column.from(6)), true),
                Arguments.arguments(new Position(Row.from(8), Column.from(4)), true),
                Arguments.arguments(new Position(Row.from(8), Column.from(5)), true),
                Arguments.arguments(new Position(Row.from(8), Column.from(6)), true),
                Arguments.arguments(new Position(Row.from(9), Column.from(4)), true),
                Arguments.arguments(new Position(Row.from(9), Column.from(5)), true),
                Arguments.arguments(new Position(Row.from(9), Column.from(6)), true),
                Arguments.arguments(new Position(Row.from(10), Column.from(4)), true),
                Arguments.arguments(new Position(Row.from(10), Column.from(5)), true),
                Arguments.arguments(new Position(Row.from(10), Column.from(6)), true)
        );
    }

    private static Stream<Arguments> provideDiagonal() {
        return Stream.of(Arguments.arguments(Position.of(1, 4), Position.of(2, 5), true),
                Arguments.arguments(Position.of(1, 4), Position.of(3, 6), true),
                Arguments.arguments(Position.of(1, 6), Position.of(2, 5), true),
                Arguments.arguments(Position.of(1, 6), Position.of(3, 4), true),
                Arguments.arguments(Position.of(3, 4), Position.of(2, 5), true),
                Arguments.arguments(Position.of(3, 4), Position.of(1, 6), true),
                Arguments.arguments(Position.of(3, 6), Position.of(2, 5), true),
                Arguments.arguments(Position.of(3, 6), Position.of(1, 4), true),
                Arguments.arguments(Position.of(2, 5), Position.of(1, 4), true),
                Arguments.arguments(Position.of(2, 5), Position.of(1, 6), true),
                Arguments.arguments(Position.of(2, 5), Position.of(3, 4), true),
                Arguments.arguments(Position.of(2, 5), Position.of(3, 6), true),
                Arguments.arguments(Position.of(8, 4), Position.of(9, 5), true),
                Arguments.arguments(Position.of(8, 4), Position.of(10, 6), true),
                Arguments.arguments(Position.of(8, 6), Position.of(9, 5), true),
                Arguments.arguments(Position.of(8, 6), Position.of(10, 4), true),
                Arguments.arguments(Position.of(10, 4), Position.of(9, 5), true),
                Arguments.arguments(Position.of(10, 4), Position.of(8, 6), true),
                Arguments.arguments(Position.of(10, 6), Position.of(9, 5), true),
                Arguments.arguments(Position.of(10, 6), Position.of(8, 4), true),
                Arguments.arguments(Position.of(9, 5), Position.of(8, 4), true),
                Arguments.arguments(Position.of(9, 5), Position.of(8, 6), true),
                Arguments.arguments(Position.of(9, 5), Position.of(10, 4), true),
                Arguments.arguments(Position.of(9, 5), Position.of(10, 6), true)
        );
    }
}
