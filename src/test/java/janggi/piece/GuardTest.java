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

class GuardTest {

    @DisplayName("사가 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Guard guard = Guard.of(Team.HAN);
        // when
        // then
        assertThat(guard.canMove(start, end, board)).isFalse();
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
        final Guard guard = Guard.of(Team.CHO);
        // when
        final boolean actual = guard.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("사가 해당 위치로 이동할 수 있는지 판단한다.")
    @Test
    void testCanMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.NINE, Column.THREE);
        final Position end = new Position(Row.EIGHT, Column.THREE);
        final Guard guard = Guard.of(Team.CHO);
        // when
        // then
        assertThat(guard.canMove(start, end, board)).isTrue();
    }
}
