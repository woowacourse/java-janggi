package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @DisplayName("원하는 값 만큼 행 값을 바꿀 수 있다.")
    @Test
    void testMoveRow() {
        // given
        Row row = Row.ZERO;
        // when
        Row moveRow = row.move(5);
        // then
        assertThat(moveRow).isEqualTo(Row.FIVE);
    }

    @DisplayName("정해진 범위를 벗어나려고 하면 예외가 발생한다.")
    @Test
    void testIllegalMoveRow() {
        // given
        Row row = Row.ZERO;
        // when
        // then
        assertThatThrownBy(() -> row.move(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보드를 벗어나는 위치입니다.");
    }

    @DisplayName("0번 행에서 +9만큼 이동할 수 있고, -1만큼은 이동할 수 없다.")
    @Test
    void testCanMoveRow() {
        // given
        Row row = Row.ZERO;
        // when
        boolean canMove1 = row.canMove(9);
        boolean canMove2 = row.canMove(-1);
        // then
        assertAll(
                () -> assertThat(canMove1).isTrue(),
                () -> assertThat(canMove2).isFalse()
        );
    }
}
