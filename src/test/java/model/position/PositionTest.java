package model.position;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @Test
    void 포지션_유효성_검사() {
        Position pos = Position.inValid();
        boolean expected = true;
        boolean actual = pos.isInValid();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("providePosition")
    void 같은_포지션인지_검사(Position pos, boolean expectedPosition) {
        Position from = Position.of(5, 5);
        boolean actual = from.isSamePosition(pos);

        assertEquals(expectedPosition, actual);
    }

    @ParameterizedTest
    @MethodSource("providePosition")
    void 같은_행인지_검사(Position pos, boolean expectedPosition, boolean expectedRow) {
        Position from = Position.of(5, 5);
        boolean actual = from.isSameRow(pos);

        assertEquals(expectedRow, actual);
    }

    @ParameterizedTest
    @MethodSource("providePosition")
    void 같은_열인지_검사(Position pos, boolean expectedPosition, boolean expectedRow, boolean expectedColumn) {
        Position from = Position.of(5, 5);
        boolean actual = from.isSameColumn(pos);

        assertEquals(expectedColumn, actual);
    }

    private static Stream<Arguments> providePosition() {
        return Stream.of(
                Arguments.arguments(Position.of(4,5), false, false, true),
                Arguments.arguments(Position.of(4,4), false, false, false),
                Arguments.arguments(Position.of(5,5), true, true, true),
                Arguments.arguments(Position.of(5,4), false, true, false),
                Arguments.arguments(Position.of(6,4), false, false, false),
                Arguments.arguments(Position.of(6,5), false, false, true),
                Arguments.arguments(Position.of(5,6), false, true, false),
                Arguments.arguments(Position.of(4,6), false, false, false)
        );
    }
}