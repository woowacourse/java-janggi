package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @DisplayName("차가 가진 규칙을 적용할 수 없으면 예외가 발생한다.")
    @Test
    void testCannotMove() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ONE);
        final Chariot chariot = Chariot.of(Team.CHO);
        // when
        // then
        assertThatThrownBy(() -> chariot.validateMove(start, end, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물의 규칙에 맞지 않는 움직임입니다.");
    }

    @DisplayName("차의 중간 경로에 다른 기물이 있으면 예외가 발생한다.")
    @Test
    void testCannotMoveThrough() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Chariot chariot = Chariot.of(Team.CHO);
        final Map<Position, Piece> board = Map.of(new Position(Row.SEVEN, Column.ZERO), chariot);
        // when
        // then
        assertThatThrownBy(() -> chariot.validateMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물의 이동 경로에 다른 기물이 있습니다.");
    }
}
