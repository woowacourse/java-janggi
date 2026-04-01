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

    @ParameterizedTest
    @MethodSource("provideBound")
    void 궁성_안_범위_검사(Position pos, boolean expected) {
        Palace palace = new Palace();
        boolean actual = palace.isBound(pos);

        assertEquals(expected, actual);
    }

    @Test
    void 궁성_밖_범위_검사() {
        Palace palace = new Palace();
        Position pos = Position.of(6,6);
        boolean expected = false;
        boolean actual = palace.isBound(pos);

        assertEquals(expected, actual);
    }

    @Test
    void 대각선으로_이동_가능한가() {

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
}
