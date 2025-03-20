package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.rule.MoveVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("두 개의 position의 차이를 계산한다.")
    @Test
    void testGetDiff() {
        // given
        final Position start = new Position(Row.SIX, Column.SIX);
        final Position end = new Position(Row.FIVE, Column.SEVEN);
        // when
        final MoveVector actual = end.getDiff(start);
        // then
        assertThat(actual).isEqualTo(new MoveVector(-1, 1));
    }
}
