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

class SoldierTest {

    @DisplayName("병이 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Soldier soldier = Soldier.of(Team.HAN);
        // when
        // then
        assertThat(soldier.canMove(start, end, board)).isFalse();
    }

    @DisplayName("해당하는 규칙을 적용했을 때 도착 위치에 같은 팀의 기물이 있으면 false를 반환한다.")
    @Test
    void testPresentSameTeam() {
        // given
        final Board board = TestBoardGenerator.generateSoldierCanNotMove();
        final Position start = new Position(Row.SEVEN, Column.SEVEN);
        final Position end = new Position(Row.SIX, Column.SEVEN);
        final Soldier soldier = Soldier.of(Team.CHO);
        // when
        final boolean actual = soldier.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("병이 해당 위치로 이동할 수 있는지 판단한다.")
    @Test
    void testCanMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.NINE, Column.ZERO);
        final Position end = new Position(Row.EIGHT, Column.ZERO);
        final Soldier soldier = Soldier.of(Team.CHO);
        // when
        // then
        assertThat(soldier.canMove(start, end, board)).isTrue();
    }
}
