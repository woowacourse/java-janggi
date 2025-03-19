package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.Board;
import janggi.BoardGenerator;
import janggi.Column;
import janggi.Position;
import janggi.Row;
import janggi.SetupOption;
import janggi.fixture.TestBoardGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("포가 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ONE);
        final Cannon cannon = Cannon.of(Team.CHO);
        // when
        // then
        assertThat(cannon.canMove(start, end, board)).isFalse();
    }

    @DisplayName("포의 이동 경로에 포가 있으면 false를 반환한다.")
    @Test
    void testCannotJumpCannon() {
        // given
        final Board board = TestBoardGenerator.generateCannon();
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        // when
        final boolean actual = cannon.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("포의 이동 경로에 기물이 없으면 false를 반환한다.")
    @Test
    void testCannotMoveWithoutJump() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        // when
        final boolean actual = cannon.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("포의 이동 경로에 포를 제외한 기물이 하나만 있어야 한다.")
    @Test
    void testJumpOnlyOne() {
        // given
        final Board board = TestBoardGenerator.generateNotCannon();
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        // when
        final boolean actual = cannon.canMove(start, end, board);
        // then
        assertThat(actual).isTrue();
    }
}
