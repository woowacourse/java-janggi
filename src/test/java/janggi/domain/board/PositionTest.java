package janggi.domain.board;

import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void 다른_포지션과의_row_차이를_구한다() {
        Position src = new Position(Row.FOUR, Column.FOUR);
        Position dst = new Position(Row.FIVE, Column.FOUR);

        assertThat(src.rowDifference(dst)).isEqualTo(1);
    }

    @Test
    void 다른_포지션과의_column_차이를_구한다() {
        Position src = new Position(Row.FOUR, Column.FOUR);
        Position dst = new Position(Row.FIVE, Column.FIVE);

        assertThat(src.columnDifference(dst)).isEqualTo(1);
    }

    @Test
    void 두_좌표_사이의_모든_좌표를_반환() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.ONE, Column.FIVE);

        List<Position> betweenPositions = src.getBetweenPositions(dst);

        assertAll(
                () -> assertThat(betweenPositions).hasSize(3),
                () -> assertThat(betweenPositions.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO)),
                () -> assertThat(betweenPositions.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE)),
                () -> assertThat(betweenPositions.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR))
        );
    }
}
