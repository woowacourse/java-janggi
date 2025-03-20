package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.SetupOption;
import janggi.fixture.TestBoardGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @DisplayName("차가 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ONE);
        final Chariot chariot = Chariot.of(Team.HAN);
        // when
        // then
        assertThat(chariot.canMove(start, end, board)).isFalse();
    }

    @DisplayName("해당하는 규칙을 적용했을 때 도착 위치에 같은 팀의 기물이 있으면 false를 반환한다.")
    @Test
    void testPresentSameTeam() {
        // given
        final Board board = TestBoardGenerator.generateSoldierCannotMove();
        final Position start = new Position(Row.SEVEN, Column.SEVEN);
        final Position end = new Position(Row.SIX, Column.SEVEN);
        final Chariot chariot = Chariot.of(Team.CHO);
        // when
        final boolean actual = chariot.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("차가 해당 위치로 이동할 수 있는지 판단한다.")
    @Test
    void testCanMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.NINE, Column.ZERO);
        final Position end = new Position(Row.EIGHT, Column.ZERO);
        final Chariot chariot = Chariot.of(Team.CHO);
        // when
        // then
        assertThat(chariot.canMove(start, end, board)).isTrue();
    }

    @DisplayName("차의 중간 경로에 다른 기물이 있으면 false를 반환한다.")
    @Test
    void testCannotMoveThrough() {
        // given
        final Board board = TestBoardGenerator.generateHorseCannotMove();
        final Position start = new Position(Row.SEVEN, Column.SEVEN);
        final Position end = new Position(Row.FIVE, Column.SEVEN);
        final Chariot chariot = Chariot.of(Team.CHO);
        // when
        // then
        assertThat(chariot.canMove(start, end, board)).isFalse();
    }
}
