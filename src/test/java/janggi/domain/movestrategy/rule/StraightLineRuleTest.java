package janggi.domain.movestrategy.rule;

import janggi.domain.board.Board;
import janggi.domain.board.BoardState;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class StraightLineRuleTest {

    private BoardState boardState;

    @BeforeEach
    void setUp() {
        boardState = new Board(new HashMap<>());
    }

    @Test
    void 가로_이동은_직선이다() {
        // give
        MoveRule rule = new StraightLineRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isTrue();
    }

    @Test
    void 세로_이동은_직선이다() {
        // give
        MoveRule rule = new StraightLineRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(5), Column.of(0));
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isTrue();
    }

    @Test
    void 대각선_이동은_직선이_아니다() {
        // give
        MoveRule rule = new StraightLineRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(5), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isFalse();
    }
}
