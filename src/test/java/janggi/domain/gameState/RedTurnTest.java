package janggi.domain.gameState;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.gameState.RedTurn;
import janggi.domain.gameState.State;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RedTurnTest {
    @Test
    void 빨간팀_차례일때_빨간색_기물을_움직일수_있다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State RedTurn = new RedTurn(board);

        Position source = new Position(Row.ONE, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);
        Piece piece = board.getPieceBy(source);

        assertThatCode(() -> RedTurn.movePiece(PieceType.CHARIOT, source, target))
                .doesNotThrowAnyException();

    }

    @Test
    void 빨간팀_차례일때_빨간색이_아닌_기물을_움직일수_없다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State RedTurn = new RedTurn(board);

        Position source = new Position(Row.ZERO, Column.ONE);
        Position target = new Position(Row.EIGHT, Column.ONE);
        Piece piece = board.getPieceBy(source);

        assertThatThrownBy(() -> RedTurn.movePiece(PieceType.CHARIOT, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 빨간팀_차례일때_기물이_없는_위치에서_움직일수_없다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State RedTurn = new RedTurn(board);

        Position source = new Position(Row.TWO, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);
        Piece piece = board.getPieceBy(source);

        assertThatThrownBy(() -> RedTurn.movePiece(PieceType.HORSE, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}