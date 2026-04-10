package janggi.domain.movestrategy;

import janggi.domain.board.Board;
import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.rule.MoveRule;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SlidingMoveStrategyTest {

    @Test
    void 주입받은_모든_룰을_통과하면_이동이_가능하다() {
        // give
        MoveRule alwaysTrue = (from, to, boardState) -> true;
        MoveRule conditionRule = (from, to, boardState) -> from.getColumn() != to.getColumn();

        SlidingMoveStrategy strategy = new SlidingMoveStrategy(List.of(alwaysTrue, conditionRule));
        BoardState boardState = new Board(new HashMap<>());

        Position from = Position.of(Row.of(0), Column.of(0));
        Position successTo = Position.of(Row.of(0), Column.of(1));
        // when & then
        assertThat(strategy.canMove(from, successTo, boardState)).isTrue();
    }

    @Test
    void 주입받은_룰_중_하나라도_실패하면_이동이_불가능하다() {
        // give
        MoveRule alwaysTrue = (from, to, boardState) -> true;
        MoveRule conditionRule = (from, to, boardState) -> from.getColumn() != to.getColumn();

        SlidingMoveStrategy strategy = new SlidingMoveStrategy(List.of(alwaysTrue, conditionRule));
        BoardState boardState = new Board(new HashMap<>());

        Position from = Position.of(Row.of(0), Column.of(0));
        Position failTo = Position.of(Row.of(1), Column.of(0));
        // when & then
        assertThat(strategy.canMove(from, failTo, boardState)).isFalse();
    }
}
