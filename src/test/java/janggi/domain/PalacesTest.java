package janggi.domain;

import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PalacesTest {

    private final Palaces palaces = Palaces.of();

    @ParameterizedTest
    @CsvSource(value = {
            "1, 4",
            "8, 4"
    })
    void 궁성_안_중앙이면_대각선_4방향을_반환한다(int row, int column) {
        List<Direction> directions = palaces.diagonalDirectionsAt(Position.of(row, column));

        assertThat(directions).containsExactlyInAnyOrderElementsOf(Direction.diagonal());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4, 4",
            "5, 5",
            "3, 3"
    })
    void 궁성_밖이면_빈_리스트를_반환한다(int row, int column) {
        List<Direction> directions = palaces.diagonalDirectionsAt(Position.of(row, column));

        assertThat(directions).isEmpty();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 4, true",
            "8, 4, true",
            "4, 4, false",
            "0, 0, false"
    })
    void 궁성_안에_포함되는지_확인한다(int row, int column, boolean expected) {
        boolean result = palaces.containsAny(Position.of(row, column));

        assertThat(result).isEqualTo(expected);
    }
}