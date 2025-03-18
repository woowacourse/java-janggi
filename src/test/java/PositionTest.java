import org.junit.jupiter.api.Test;

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
}
