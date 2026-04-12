package janggi.domain;

import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    static Stream<Arguments> palaceTestCases() {
        return Stream.of(
                // 초나라 궁성
                Arguments.of(Palace.cho(), Position.of(0, 3), List.of(Direction.UP_RIGHT)),
                Arguments.of(Palace.cho(), Position.of(0, 5), List.of(Direction.UP_LEFT)),
                Arguments.of(Palace.cho(), Position.of(2, 3), List.of(Direction.DOWN_RIGHT)),
                Arguments.of(Palace.cho(), Position.of(2, 5), List.of(Direction.DOWN_LEFT)),
                Arguments.of(Palace.cho(), Position.of(0, 4), List.of()),
                Arguments.of(Palace.cho(), Position.of(1, 3), List.of()),
                Arguments.of(Palace.cho(), Position.of(1, 5), List.of()),
                Arguments.of(Palace.cho(), Position.of(2, 4), List.of()),
                Arguments.of(Palace.cho(), Position.of(1, 4), Direction.diagonal()),

                // 한나라 궁성
                Arguments.of(Palace.han(), Position.of(7, 3), List.of(Direction.UP_RIGHT)),
                Arguments.of(Palace.han(), Position.of(7, 5), List.of(Direction.UP_LEFT)),
                Arguments.of(Palace.han(), Position.of(9, 3), List.of(Direction.DOWN_RIGHT)),
                Arguments.of(Palace.han(), Position.of(9, 5), List.of(Direction.DOWN_LEFT)),
                Arguments.of(Palace.han(), Position.of(9, 4), List.of()),
                Arguments.of(Palace.han(), Position.of(8, 3), List.of()),
                Arguments.of(Palace.han(), Position.of(8, 5), List.of()),
                Arguments.of(Palace.han(), Position.of(7, 4), List.of()),
                Arguments.of(Palace.han(), Position.of(8, 4), Direction.diagonal())
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 3", "0, 4", "0, 5",
            "1, 3", "1, 4", "1, 5",
            "2, 3", "2, 4", "2, 5"
    })
    void 포지션이_초나라_궁성에_존재하면_true를_반환한다(int row, int column) {

        Palace cho = Palace.cho();

        boolean result = cho.contains(Position.of(row, column));

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "7, 3", "7, 4", "7, 5",
            "8, 3", "8, 4", "8, 5",
            "9, 3", "9, 4", "9, 5"
    })
    void 포지션이_한나라_궁성에_존재하면_true를_반환한다(int row, int column) {
        Palace han = Palace.han();

        boolean result = han.contains(Position.of(row, column));

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 2", "3, 3", "0, 6",
            "6, 4", "7, 2", "9, 6"
    })
    void 포지션이_궁성_밖에_존재하면_false를_반환한다(int row, int column) {
        boolean isChoContains = Palace.cho().contains(Position.of(row, column));
        boolean isHanContains = Palace.han().contains(Position.of(row, column));

        assertThat(isChoContains).isFalse();
        assertThat(isHanContains).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 2", "3, 3", "0, 6",
            "1, 2", "3, 4", "1, 6",
            "2, 2", "3, 5", "2, 6"
    })
    void 포지션이_궁성_안에_없다면_빈_리스트를_반환한다(int row, int column) {
        Palace cho = Palace.cho();

        List<Direction> directions = cho.diagonalDirectionsAt(Position.of(row, column));

        assertThat(directions).isEqualTo(List.of());
    }

    @ParameterizedTest
    @MethodSource("palaceTestCases")
    void 궁성_위치에_따라_올바른_대각선을_반환한다(Palace palace, Position position, List<Direction> expected) {
        List<Direction> directions = palace.diagonalDirectionsAt(position);

        assertThat(directions).containsExactlyInAnyOrderElementsOf(expected);
    }
}