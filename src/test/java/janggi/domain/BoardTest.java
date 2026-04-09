package janggi.domain;

import janggi.domain.movestorage.JolMoveStorage;
import janggi.exception.move.EmptyPositionException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {
    @Test
    void 보드는_특정_위치에_기물이_존재하는지_확인할_수_있다() {
        // give
        Position position = Position.of(Row.of(0), Column.of(7));
        Piece piece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");
        Board board = new Board(Map.of(position, piece));

        // when & then
        assertThat(board.hasPieceAt(position)).isTrue();
    }

    @Test
    void 보드는_지정된_좌표의_기물_정보를_알려준다() {
        // give
        Position position = Position.of(Row.of(0), Column.of(7));
        Piece piece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");
        Board board = new Board(Map.of(position, piece));

        // when & then
        assertThat(board.getPieceAt(position)).isEqualTo(piece);
    }

    @Test
    void 기물은_허용된_규칙에_따라_새로운_위치로_이동한다() {
        // give
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(1));

        Position position = Position.of(Row.of(0), Column.of(0));
        Piece piece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Board board = new Board(Map.of(position, piece));

        // when
        board.move(from, to);

        // then
        assertThat(board.getPieceAt(to)).isEqualTo(piece);
        assertThat(board.hasPieceAt(from)).isFalse();
    }

    @Test
    void 기물이_존재하지_않는_곳에서는_이동을_시작할_수_없다() {
        // give
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(1));

        Position position = Position.of(Row.of(0), Column.of(7));
        Piece piece = new Piece(new JolMoveStorage(), Team.HAN, 2,  "卒");

        Board board = new Board(Map.of(position, piece));

        // when & then
        assertThatThrownBy(() -> board.move(from, to)).isInstanceOf(EmptyPositionException.class);
    }
}
