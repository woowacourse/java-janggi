package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.movement.vo.Delta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Position 레코드 테스트")
class PositionTest {

    private Delta delta(int col, int row) {
        return new Delta(col, row);
    }

    @Test
    @DisplayName("동일한 col과 row를 가진 Position은 동등하다")
    void positionsWithSameColAndRowAreEqual() {
        Position pos1 = new Position(Column.E, Row.FOUR);
        Position pos2 = new Position(Column.E, Row.FOUR);

        assertThat(pos1).isEqualTo(pos2);
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode());
    }

    @Test
    @DisplayName("canShift: 보드 내 이동 가능한 delta는 true를 반환한다")
    void canShiftReturnsTrueForValidDelta() {
        Position center = new Position(Column.E, Row.FOUR);

        assertThat(center.canShift(delta(0, 1))).isTrue();
        assertThat(center.canShift(delta(0, -1))).isTrue();
        assertThat(center.canShift(delta(1, 0))).isTrue();
        assertThat(center.canShift(delta(-1, 0))).isTrue();
        assertThat(center.canShift(delta(3, 3))).isTrue();
        assertThat(center.canShift(delta(-3, -3))).isTrue();
    }

    @Test
    @DisplayName("canShift: 보드 밖으로 나가는 delta는 false를 반환한다")
    void canShiftReturnsFalseWhenOutOfBounds() {
        Position topLeft = new Position(Column.A, Row.ZERO);
        Position bottomRight = new Position(Column.I, Row.NINE);

        assertThat(topLeft.canShift(delta(-1, 0))).isFalse();
        assertThat(topLeft.canShift(delta(0, -1))).isFalse();
        assertThat(bottomRight.canShift(delta(1, 0))).isFalse();
        assertThat(bottomRight.canShift(delta(0, 1))).isFalse();
    }

    @Test
    @DisplayName("shift: delta만큼 이동한 새로운 Position을 반환한다")
    void shiftReturnsNewPositionWithAppliedDelta() {
        Position pos = new Position(Column.C, Row.THREE);

        assertThat(pos.shift(delta(1, 0))).isEqualTo(new Position(Column.D, Row.THREE));
        assertThat(pos.shift(delta(-1, 0))).isEqualTo(new Position(Column.B, Row.THREE));
        assertThat(pos.shift(delta(0, 1))).isEqualTo(new Position(Column.C, Row.FOUR));
        assertThat(pos.shift(delta(0, -1))).isEqualTo(new Position(Column.C, Row.TWO));
        assertThat(pos.shift(delta(2, 3))).isEqualTo(new Position(Column.E, Row.SIX));
    }
}
