package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.fixture.TestBoardGenerator;
import janggi.piece.Piece;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.SetupOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("이동 시작 위치에 기물이 없으면 예외가 발생한다.")
    @Test
    void testMoveWithWrongStartPosition() {
        // given
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SEVEN, Column.ZERO);
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        // when
        // then
        assertThatThrownBy(() -> board.move(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 80 위치에 기물이 없습니다.");
    }

    @DisplayName("이동할 수 없는 규칙인 경우 예외가 발생한다.")
    @Test
    void testCannotFindRule() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.SIX, Column.ZERO);
        final Position end = new Position(Row.SEVEN, Column.ZERO);
        // when
        // then
        assertThatThrownBy(() -> board.move(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 70 위치로 이동할 수 없습니다.");
    }

    @DisplayName("병 한 칸 전진시킨다.")
    @Test
    void testMoveSoldier() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.SIX, Column.ZERO);
        final Position end = new Position(Row.FIVE, Column.ZERO);
        // when
        board.move(start, end);
        final Piece actual = board.getPiece(end);
        // then
        assertThat(actual.type()).isEqualTo(Type.SOLDIER);
    }

    @DisplayName("병 이동된 위치에 상대 기물이 있으면 잡으면서 이동한다.")
    @Test
    void testMoveSoldierCatch() {
        // given
        final Board board = TestBoardGenerator.generateSoldierCatch();
        final Position start = new Position(Row.SIX, Column.SEVEN);
        final Position end = new Position(Row.FIVE, Column.SEVEN);
        // when
        board.move(start, end);
        final Piece actual = board.getPiece(end);
        // then
        assertThat(actual.type()).isEqualTo(Type.SOLDIER);
    }
}
