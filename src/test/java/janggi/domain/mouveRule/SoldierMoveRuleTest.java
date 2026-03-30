package janggi.domain.mouveRule;

import janggi.domain.FakeBoard;
import janggi.domain.board.Board;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierMoveRuleTest {
    private final FakeBoard board = new FakeBoard();

    @ParameterizedTest
    @CsvSource({
            "CHO, 6, 4, 5, 4",
            "HAN, 3, 4, 4, 4"
    })
    void 각_진영_전진_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        // given
        MoveRule rule = new SoldierMoveRule(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(rule.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 6, 4, 7, 4",
            "HAN, 3, 4, 2, 4"
    })
    void 각_진영_후진_불가_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        // given
        MoveRule rule = new SoldierMoveRule(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(rule.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 5, 4, 5, 3", // 초 서쪽 이동
            "CHO, 5, 4, 5, 5", // 초 동쪽 이동
            "HAN, 4, 4, 4, 3", // 한 서쪽 이동
            "HAN, 4, 4, 4, 5"  // 한 동쪽 이동
    })
    void 각_진영_좌우_이동_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        MoveRule soldierMoveRule = new SoldierMoveRule(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        assertThat(soldierMoveRule.canMove(from, to, board)).isTrue();
    }
}
