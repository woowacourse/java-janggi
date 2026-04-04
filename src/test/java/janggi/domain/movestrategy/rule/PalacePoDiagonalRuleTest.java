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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PalacePoDiagonalRuleTest {
    @Test
    void 대각선으로_2칸을_뛸_때_중간에_무언가_있고_그게_포가_아니면_성공한다() {
        // give
        PalacePoDiagonalRule rule = new PalacePoDiagonalRule();
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(1), Column.of(4)), new Piece(Team.HAN, PieceType.SA));
        BoardState boardState = new Board(pieces);

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(2), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 3, 1, 4",
            "1, 4, 2, 5"
    })
    void 포는_아무리_X자_대각선이어도_1칸만_이동하려고_하면_실패한다(int fromRow, int fromCol, int toRow, int toCol) {
        // give
        PalacePoDiagonalRule rule = new PalacePoDiagonalRule();
        BoardState board = new Board(new HashMap<>());
        Position from = Position.of(Row.of(fromRow), Column.of(fromCol));
        Position to = Position.of(Row.of(toRow), Column.of(toCol));
        // when & then
        assertThat(rule.isValid(from, to, board)).isFalse();
    }

    @Test
    void 대각선으로_2칸을_뛰려_하는데_중간에_밟을_게_없으면_실패한다() {
        // give
        PalacePoDiagonalRule rule = new PalacePoDiagonalRule();
        BoardState board = new Board(new HashMap<>());

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(2), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, board)).isFalse();
    }

    @Test
    void 중간에_받침대가_있긴_한데_포라서_넘을_수_없어_실패한다() {
        // give
        PalacePoDiagonalRule rule = new PalacePoDiagonalRule();
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(1), Column.of(4)), new Piece(Team.CHO, PieceType.PO));
        BoardState board = new Board(pieces);

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(2), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, board)).isFalse();
    }

    @Test
    void 도착지에_있는_기물이_포라서_잡을_수_없어_실패한다() {
        // give
        PalacePoDiagonalRule rule = new PalacePoDiagonalRule();
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(1), Column.of(4)), new Piece(Team.HAN, PieceType.SA));
        pieces.put(Position.of(Row.of(2), Column.of(5)), new Piece(Team.HAN, PieceType.PO));
        BoardState board = new Board(pieces);

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(2), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, board)).isFalse();
    }
}
