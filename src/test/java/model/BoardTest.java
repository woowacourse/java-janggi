package model;

import model.coordinate.Position;
import model.piece.Chariot;
import model.piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void 아군_위치로_이동하면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Chariot(Team.HAN));
        pieces.put(new Position(0, 5), new Chariot(Team.HAN));
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> board.movePiece(new Position(0, 0), new Position(0, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 빈_위치를_선택하면_예외가_발생한다() {
        // given
        Board board = new Board(new HashMap<>());

        // when & then
        assertThatThrownBy(() -> board.pickPiece(new Position(0, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 적군_위치로_이동하면_기물이_교체된다() {
        // given
        Piece han = new Chariot(Team.HAN);
        Piece cho = new Chariot(Team.CHO);
        Position cur = new Position(0, 0);
        Position next = new Position(0, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(cur, han);
        pieces.put(next, cho);

        Board board = new Board(pieces);

        // when
        board.movePiece(cur, next);

        // then
        assertThat(board.pickPiece(next)).isEqualTo(han);
    }
}