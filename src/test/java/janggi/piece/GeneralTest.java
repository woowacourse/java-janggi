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

class GeneralTest {

    @DisplayName("장이 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final General general = General.of(Team.HAN);
        // when
        // then
        assertThat(general.canMove(start, end, board)).isFalse();
    }

    @DisplayName("해당하는 규칙을 적용했을 때 도착 위치에 같은 팀의 기물이 있으면 false를 반환한다.")
    @Test
    void testPresentSameTeam() {
        // given
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.SIX, Column.THREE), Soldier.of(Team.CHO)
        );
        final Position start = new Position(Row.SEVEN, Column.THREE);
        final Position end = new Position(Row.SIX, Column.THREE);
        final General general = General.of(Team.CHO);
        // when
        final boolean actual = general.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("장이 해당 위치로 이동할 수 있는지 판단한다.")
    @Test
    void testCanMove() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.NINE, Column.THREE);
        final Position end = new Position(Row.EIGHT, Column.THREE);
        final General general = General.of(Team.CHO);
        // when
        // then
        assertThat(general.canMove(start, end, board)).isTrue();
    }

    @DisplayName("장은 궁 밖으로 움직일 수 없다.")
    @Test
    void testCannotEscapePalace() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        final Position start = new Position(Row.TWO, Column.THREE);
        final Position end = new Position(Row.THREE, Column.THREE);
        final General general = General.of(Team.CHO);
        // when
        // then
        assertThat(general.canMove(start, end, board)).isFalse();
    }

    @DisplayName("장이 궁안에서 대각선으로 움직이기 위해서는 가운데를 지나가야한다.")
    @Test
    void testDiagonalMoveThroughCenter() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        final Position start = new Position(Row.ZERO, Column.THREE);
        final Position end = new Position(Row.ONE, Column.FOUR);
        final General general = General.of(Team.CHO);
        // when
        // then
        assertThat(general.canMove(start, end, board)).isTrue();
    }

    @DisplayName("장이 궁안에서 대각선으로 움직일 때 가운데를 안지나치면 못움직인다.")
    @Test
    void testDiagonalMoveNotThroughCenter() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        final Position start = new Position(Row.ZERO, Column.FOUR);
        final Position end = new Position(Row.ONE, Column.FIVE);
        final General general = General.of(Team.CHO);
        // when
        // then
        assertThat(general.canMove(start, end, board)).isFalse();
    }
}
