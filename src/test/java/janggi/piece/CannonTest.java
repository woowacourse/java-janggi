package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("포가 가진 규칙을 적용할 수 없으면 예외가 발생한다.")
    @Test
    void testCannotMove() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ONE);
        final Cannon cannon = Cannon.of(Team.CHO);
        // when
        // then
        assertThatThrownBy(() -> cannon.validateMove(start, end, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물의 규칙에 맞지 않는 움직임입니다.");
    }

    @DisplayName("포의 이동 경로에 포가 있으면 예외가 발생한다.")
    @Test
    void testCannotJumpCannon() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        final Map<Position, Piece> board = Map.of(new Position(Row.SEVEN, Column.ZERO), cannon);
        // when
        // then
        assertThatThrownBy(() -> cannon.validateMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어 넘을 수 없습니다.");
    }

    @DisplayName("포의 이동 경로에 기물이 없으면 예외가 발생한다.")
    @Test
    void testCannotMoveWithoutJump() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        final Map<Position, Piece> board = Map.of(new Position(Row.FIVE, Column.ZERO), cannon);
        // when
        // then
        assertThatThrownBy(() -> cannon.validateMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 적어도 하나의 기물을 뛰어 넘어야 합니다.");
    }

    @DisplayName("포의 이동 경로에 포를 제외한 기물이 하나만 있어야 한다.")
    @Test
    void testJumpOnlyOne() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.FIVE, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        final Map<Position, Piece> board = Map.of(new Position(Row.SEVEN, Column.ZERO), Soldier.of(Team.CHO),
                new Position(Row.SIX, Column.ZERO), Soldier.of(Team.CHO));
        // when
        // then
        assertThatThrownBy(() -> cannon.validateMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 단 하나의 기물만 뛰어 넘을 수 있습니다.");
    }
}
