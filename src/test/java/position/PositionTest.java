package position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void DELTA_만큼_이동한다() {
        // given
        Position before = new Position(0, 0);
        Delta delta = new Delta(1, 1);
        // when
        Position after = before.move(delta);
        // then
        Position expected = new Position(
            before.getRowIndex() + delta.rowDelta(),
            before.getColumnIndex() + delta.columnDelta()
        );
        assertThat(after).isEqualTo(expected);
    }
}
