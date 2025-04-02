package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @CsvSource(value = {
            "1,1,false", "1,9,false", "10,1,false", "10,9,false",
            "1,0,true", "0,1,true", "1,10,true", "0,9,true",
            "11,1,true", "10,0,true", "11,9,true", "10,10,true"
    })
    @ParameterizedTest
    void 위치가_장기판_내부에_존재할_수_없는지_알려준다(int row, int column, boolean expected) {
        Position position = new Position(row, column);

        assertThat(position.isInValidPosition()).isEqualTo(expected);
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

    @MethodSource
    @ParameterizedTest
    void 대각선으로_움직일_수_있는_궁성_영역인지_알려준다(Position position, boolean expected) {
        assertThat(position.hasDiagonalDirectionInPosition()).isEqualTo(expected);
    }

    private static Stream<Arguments> 대각선으로_움직일_수_있는_궁성_영역인지_알려준다() {
        return Stream.of(
                Arguments.of(new Position(1, 4), true),
                Arguments.of(new Position(1, 6), true),
                Arguments.of(new Position(2, 5), true),
                Arguments.of(new Position(3, 4), true),
                Arguments.of(new Position(3, 6), true),
                Arguments.of(new Position(8, 4), true),
                Arguments.of(new Position(8, 6), true),
                Arguments.of(new Position(9, 5), true),
                Arguments.of(new Position(10, 4), true),
                Arguments.of(new Position(10, 6), true),
                Arguments.of(new Position(1, 5), false),
                Arguments.of(new Position(2, 4), false),
                Arguments.of(new Position(2, 6), false),
                Arguments.of(new Position(3, 5), false),
                Arguments.of(new Position(8, 5), false),
                Arguments.of(new Position(9, 4), false),
                Arguments.of(new Position(9, 6), false),
                Arguments.of(new Position(10, 5), false)
        );
    }

}
