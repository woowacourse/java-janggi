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

class PalaceChaDiagonalRuleTest {
    @ParameterizedTest
    @CsvSource({
            "0, 3, 1, 4",
            "0, 5, 1, 4",
            "1, 4, 2, 3",
            "1, 4, 2, 5"
    })
    void 궁성_안에서_1칸_대각선_이동은_무조건_통과한다(int fromRow, int fromCol, int toRow, int toCol) {
        // give
        PalaceChaDiagonalRule rule = new PalaceChaDiagonalRule();
        BoardState boardState = new Board(new HashMap<>());
        Position from = Position.of(Row.of(fromRow), Column.of(fromCol));
        Position to = Position.of(Row.of(toRow), Column.of(toCol));
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 3, 2, 5",
            "0, 5, 2, 3",
            "7, 3, 9, 5",
            "9, 3, 7, 5"
    })
    void 중간에_장애물이_없다면_궁성_대각선_2칸_이동이_통과한다(int fromRow, int fromCol, int toRow, int toCol) {
        // give
        PalaceChaDiagonalRule rule = new PalaceChaDiagonalRule();
        BoardState board = new Board(new HashMap<>());
        Position from = Position.of(Row.of(fromRow), Column.of(fromCol));
        Position to = Position.of(Row.of(toRow), Column.of(toCol));
        // when & then
        assertThat(rule.isValid(from, to, board)).isTrue();
    }

    @Test
    void 중간에_장애물이_있으면_2칸_대각선_이동은_막혀서_실패한다() {
        // give
        PalaceChaDiagonalRule rule = new PalaceChaDiagonalRule();
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(1), Column.of(4)), new Piece(Team.HAN, PieceType.SA));
        BoardState boardState = new Board(pieces);

        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(2), Column.of(5));
        // when & then
        assertThat(rule.isValid(from, to, boardState)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 1, 3",
            "0, 4, 1, 5",
            "1, 3, 2, 4",
            "1, 5, 2, 4"
    })
    void 궁성_안이더라도_실제_선이_그어지지_않은_가짜_대각선은_실패한다(int fromRow, int fromCol, int toRow, int toCol) {
        // give
        PalaceChaDiagonalRule rule = new PalaceChaDiagonalRule();
        BoardState board = new Board(new HashMap<>());
        Position from = Position.of(Row.of(fromRow), Column.of(fromCol));
        Position to = Position.of(Row.of(toRow), Column.of(toCol));
        // when & then
        assertThat(rule.isValid(from, to, board)).isFalse();
    }
}
