package janggi.domain.movestrategy.route;

import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {

    @ParameterizedTest
    @CsvSource({
            "NORTH, -1, 0",
            "SOUTH, 1, 0",
            "WEST, 0, -1",
            "EAST, 0, 1",
            "NORTH_WEST, -1, -1",
            "NORTH_EAST, -1, 1",
            "SOUTH_WEST, 1, -1",
            "SOUTH_EAST, 1, 1"
    })
    void 모든_방향에_대해_계산된_이동_거리가_정확해야_한다(Direction direction, int row, int column) {
        // give
        Position current = Position.of(Row.of(5), Column.of(5));
        // when
        Position next = direction.move(current);
        // then
        int expectedRow = 5 + row;
        int expectedCol = 5 + column;
        assertThat(next).isEqualTo(Position.of(Row.of(expectedRow), Column.of(expectedCol)));
    }
}
