package janggi.domain;

import janggi.domain.vo.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {
    @ParameterizedTest
    @CsvSource({
            "1, 1, 0, 1, NORTH",
            "1, 1, 2, 1, SOUTH",
            "1, 1, 1, 2, EAST",
            "1, 1, 1, 0, WEST",
    })
    void 동서남북_방향_찾기_테스트(int fromRow, int fromCol, int toRow, int toCol, Direction direction) {
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        assertThat(Direction.between(from, to)).isEqualTo(direction);
    }
}
