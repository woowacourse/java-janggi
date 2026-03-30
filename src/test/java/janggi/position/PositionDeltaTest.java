package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.position.PositionDelta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionDeltaTest {

    @DisplayName("대각선을 포함해서 한 칸을 이동하면 false를 반환한다.")
    @Test
    void isMultiStep() {
        assertThat(new PositionDelta(1, 0).isMultiStep())
                .isFalse();

        assertThat(new PositionDelta(0, 1).isMultiStep())
                .isFalse();

        assertThat(new PositionDelta(1, 1).isMultiStep())
                .isFalse();
    }

    @DisplayName("이미 움직인 수평 거리만큼 columnDistance에서 뺀다.")
    @Test
    void movedHorizontally() {
        //given
        PositionDelta connection = new PositionDelta(1, 3);

        //when
        PositionDelta moved = connection.movedHorizontally(2);

        //then
        assertThat(moved.columnDistance()).isEqualTo(1);
    }

    @DisplayName("이미 움직인 수직 거리만큼 rowDistance에서 뺀다.")
    @Test
    void movedVertically() {
        //given
        PositionDelta connection = new PositionDelta(3, 1);

        //when
        PositionDelta moved = connection.movedVertically(2);

        //then
        assertThat(moved.rowDistance()).isEqualTo(1);
    }
}
