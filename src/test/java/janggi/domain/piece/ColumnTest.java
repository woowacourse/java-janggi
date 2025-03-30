package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.position.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @DisplayName("원하는 값 만큼 열 값을 바꿀 수 있다.")
    @Test
    void testMoveColumn() {
        // given
        Column column = Column.ZERO;
        // when
        Column moveColumn = column.move(5);
        // then
        assertThat(moveColumn).isEqualTo(Column.FIVE);
    }

    @DisplayName("정해진 범위를 벗어나려고 하면 예외가 발생한다.")
    @Test
    void testIllegalMoveColumn() {
        // given
        Column column = Column.ZERO;
        // when
        // then
        assertThatThrownBy(() -> column.move(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보드를 벗어나는 위치입니다.");
    }

    @DisplayName("0번 열에서 +8만큼 이동할 수 있고, -1만큼은 이동할 수 없다.")
    @Test
    void testCanMoveColumn() {
        // given
        Column column = Column.ZERO;
        // when
        boolean canMove1 = column.canMove(8);
        boolean canMove2 = column.canMove(-1);
        // then
        assertAll(
                () -> assertThat(canMove1).isTrue(),
                () -> assertThat(canMove2).isFalse()
        );
    }
}
