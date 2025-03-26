package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.fixture.TestBoardGenerator;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.SetupOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("포가 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.INNER_SETUP);
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
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.SEVEN, Column.ZERO), Cannon.of(Team.CHO)
        );
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
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.SEVEN, Column.ZERO), Soldier.of(Team.CHO)
        );
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Cannon cannon = Cannon.of(Team.CHO);
        // when
        final boolean actual = cannon.canMove(start, end, board);
        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("궁성 내부에서 포는 대각선으로 움직일 수 있다.")
    @Test
    void testMoveInPalace() {
        // given
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.ONE, Column.FOUR), General.of(Team.CHO)
        );
        // when
        final Position start = new Position(Row.ZERO, Column.THREE);
        final Position end = new Position(Row.TWO, Column.FIVE);
        final Cannon cannon = Cannon.of(Team.CHO);
        // then
        assertThat(cannon.canMove(start, end, board)).isTrue();
    }

    @DisplayName("궁성 내부에서 외부로 나갈 때 포는 대각선으로 움직일 수 없다.")
    @Test
    void testMoveThroughPalace() {
        // given
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.ONE, Column.FOUR), General.of(Team.CHO)
        );
        // when
        final Position start = new Position(Row.ZERO, Column.THREE);
        final Position end = new Position(Row.THREE, Column.SIX);
        final Cannon cannon = Cannon.of(Team.CHO);
        // then
        assertThat(cannon.canMove(start, end, board)).isFalse();
    }
}
