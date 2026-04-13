package domain.movement.palace;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Palace 클래스 테스트")
class PalaceTest {

    @Test
    @DisplayName("한궁과 초궁 내부 좌표는 true를 반환한다")
    void returnsTrueWhenPositionIsInsidePalace() {
        assertThat(Palace.isInside(new Position(Column.D, Row.ZERO))).isTrue();
        assertThat(Palace.isInside(new Position(Column.E, Row.ONE))).isTrue();
        assertThat(Palace.isInside(new Position(Column.F, Row.TWO))).isTrue();
        assertThat(Palace.isInside(new Position(Column.D, Row.SEVEN))).isTrue();
        assertThat(Palace.isInside(new Position(Column.E, Row.EIGHT))).isTrue();
        assertThat(Palace.isInside(new Position(Column.F, Row.NINE))).isTrue();
    }

    @Test
    @DisplayName("궁성 범위를 벗어난 좌표는 false를 반환한다")
    void returnsFalseWhenPositionIsOutsidePalace() {
        assertThat(Palace.isInside(new Position(Column.C, Row.ONE))).isFalse();
        assertThat(Palace.isInside(new Position(Column.G, Row.EIGHT))).isFalse();
        assertThat(Palace.isInside(new Position(Column.E, Row.THREE))).isFalse();
        assertThat(Palace.isInside(new Position(Column.E, Row.SIX))).isFalse();
    }
}
