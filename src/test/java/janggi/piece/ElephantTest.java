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

class ElephantTest {

    @DisplayName("상이 가진 규칙을 적용할 수 없으면 false를 반환한다.")
    @Test
    void testCannotMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        final Position start = new Position(Row.EIGHT, Column.ZERO);
        final Position end = new Position(Row.SIX, Column.ZERO);
        final Elephant elephant = Elephant.of(Team.CHO);
        // when
        // then
        assertThat(elephant.canMove(start, end, board)).isFalse();
    }

    @DisplayName("해당하는 규칙을 적용했을 때 도착 위치에 같은 팀의 기물이 있으면 false를 반환한다.")
    @Test
    void testPresentSameTeam() {
        // given
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.FIVE, Column.SIX), Soldier.of(Team.CHO)
        );
        final Position start = new Position(Row.EIGHT, Column.EIGHT);
        final Position end = new Position(Row.FIVE, Column.SIX);
        final Elephant elephant = Elephant.of(Team.CHO);
        // when
        final boolean actual = elephant.canMove(start, end, board);
        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("상이 해당 위치로 이동할 수 있는지 판단한다.")
    @Test
    void testCanMove() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.OUTER_SETUP);
        final Position start = new Position(Row.NINE, Column.ONE);
        final Position end = new Position(Row.SIX, Column.THREE);
        final Elephant elephant = Elephant.of(Team.CHO);
        // when
        // then
        assertThat(elephant.canMove(start, end, board)).isTrue();
    }

    @DisplayName("상의 중간 경로에 다른 기물이 있으면 false를 반환한다.")
    @Test
    void testCannotMoveThrough() {
        // given
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(
                new Position(Row.FIVE, Column.SIX), Soldier.of(Team.CHO)
        );
        final Position start = new Position(Row.SEVEN, Column.FIVE); // 5,6을 지나감
        final Position end = new Position(Row.FOUR, Column.SEVEN);
        final Elephant elephant = Elephant.of(Team.CHO);
        // when
        // then
        assertThat(elephant.canMove(start, end, board)).isFalse();
    }

}
