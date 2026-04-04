package janggi.domain.movestrategy.rule;

import janggi.domain.board.Board;
import janggi.domain.board.BoardState;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SingleJumpRuleTest {

    @Test
    void 경로에_정확히_하나의_기물이_있으면_true를_반환한다() {
        // give
        MoveRule rule = new SingleJumpRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position mid = Position.of(Row.of(0), Column.of(2));
        Position to = Position.of(Row.of(0), Column.of(5));

        Map<Position, Piece> initialPieces = new HashMap<>();
        initialPieces.put(mid, new Piece(Team.HAN, PieceType.HAN_JOL));
        BoardState boardState = new Board(initialPieces);
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isTrue();
    }

    @Test
    void 경로에_기물이_없으면_false를_반환한다() {
        // give
        MoveRule rule = new SingleJumpRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(5));
        
        BoardState boardState = new Board(new HashMap<>());
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isFalse();
    }

    @Test
    void 경로에_포가_있으면_false를_반환한다() {
        // give
        MoveRule rule = new SingleJumpRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position mid = Position.of(Row.of(0), Column.of(2));
        Position to = Position.of(Row.of(0), Column.of(5));

        Map<Position, Piece> initialPieces = new HashMap<>();
        initialPieces.put(mid, new Piece(Team.HAN, PieceType.PO));
        BoardState boardState = new Board(initialPieces);
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isFalse();
    }

    @Test
    void 도착지에_포가_있으면_false를_반환한다() {
        // give
        MoveRule rule = new SingleJumpRule();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position mid = Position.of(Row.of(0), Column.of(2));
        Position to = Position.of(Row.of(0), Column.of(5));

        Map<Position, Piece> initialPieces = new HashMap<>();
        initialPieces.put(mid, new Piece(Team.HAN, PieceType.HAN_JOL));
        initialPieces.put(to, new Piece(Team.CHO, PieceType.PO));
        BoardState boardState = new Board(initialPieces);
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isFalse();
    }
}
