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

class ChariotTest {

    @DisplayName("차가 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.INNER_SETUP);
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
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.SIX, Column.SEVEN), Soldier.of(Team.CHO)
        );
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
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.INNER_SETUP);
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
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.SIX, Column.SEVEN), Soldier.of(Team.CHO)
        );
        final Position start = new Position(Row.SEVEN, Column.SEVEN);
        final Position end = new Position(Row.FIVE, Column.SEVEN);
        final Chariot chariot = Chariot.of(Team.CHO);
        // when
        // then
        assertThat(chariot.canMove(start, end, board)).isFalse();
    }

    @DisplayName("궁성 내부에서 차는 대각선으로 움직일 수 있다.")
    @Test
    void testMoveInPalace() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        // when
        final Position start = new Position(Row.ZERO, Column.THREE);
        final Position end = new Position(Row.TWO, Column.FIVE);
        final Chariot chariot = Chariot.of(Team.CHO);
        // then
        assertThat(chariot.canMove(start, end, board)).isTrue();
    }

    @DisplayName("궁성 내부에서 외부로 나갈 때 차는 대각선으로 움직일 수 없다.")
    @Test
    void testMoveThroughPalace() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        // when
        final Position start = new Position(Row.ZERO, Column.THREE);
        final Position end = new Position(Row.THREE, Column.SIX);
        final Chariot chariot = Chariot.of(Team.CHO);
        // then
        assertThat(chariot.canMove(start, end, board)).isFalse();
    }

    @DisplayName("차가 궁안에서 대각선으로 움직일 때 가운데를 안지나치면 못움직인다.")
    @Test
    void testDiagonalMoveNotThroughCenter() {
        // given
        final Board board = TestBoardGenerator.generateEmpty();
        final Position start = new Position(Row.EIGHT, Column.FIVE);
        final Position end = new Position(Row.NINE, Column.FOUR);
        final Chariot chariot = Chariot.of(Team.HAN);
        // when
        // then
        assertThat(chariot.canMove(start, end, board)).isFalse();
    }
}

