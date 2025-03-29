package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @CsvSource(value = {"1,1", "1,10", "9,1", "9,10"})
    @ParameterizedTest
    void 위치는_장기판_내부에_존재해야_한다(int row, int column) {
        assertDoesNotThrow(() -> new Position(row, column));
    }

    @MethodSource
    @ParameterizedTest
    void 위치가_궁성_영역인지_알려준다(Position position, boolean expected) {
        assertThat(position.isPalacePosition()).isEqualTo(expected);
    }

    private static Stream<Arguments> 위치가_궁성_영역인지_알려준다() {
        return Stream.of(
                Arguments.of(new Position(1, 4), true),
                Arguments.of(new Position(1, 6), true),
                Arguments.of(new Position(3, 4), true),
                Arguments.of(new Position(3, 6), true),
                Arguments.of(new Position(8, 4), true),
                Arguments.of(new Position(8, 6), true),
                Arguments.of(new Position(10, 4), true),
                Arguments.of(new Position(10, 6), true),
                Arguments.of(new Position(1, 3), false),
                Arguments.of(new Position(1, 7), false),
                Arguments.of(new Position(4, 4), false),
                Arguments.of(new Position(4, 6), false),
                Arguments.of(new Position(7, 4), false),
                Arguments.of(new Position(7, 6), false),
                Arguments.of(new Position(10, 3), false),
                Arguments.of(new Position(10, 7), false)
        );
    }

}
