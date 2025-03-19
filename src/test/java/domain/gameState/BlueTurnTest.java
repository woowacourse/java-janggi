package domain.gameState;

import domain.board.*;
import domain.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlueTurnTest {
    @Test
    void 파란팀_차례일때_파란색_기물을_움직일수_있다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State blueTurn = new BlueTurn(board);

        Position source = new Position(Row.ZERO, Column.ONE);
        Position target = new Position(Row.EIGHT, Column.ONE);
        Piece piece = board.getPieceBy(source);

        assertThatCode(() -> blueTurn.movePiece(piece, source, target))
                .doesNotThrowAnyException();

    }

    @Test
    void 파란팀_차례일때_파란색이_아닌_기물을_움직일수_없다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State blueTurn = new BlueTurn(board);

        Position source = new Position(Row.ONE, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);
        Piece piece = board.getPieceBy(source);

        assertThatThrownBy(() -> blueTurn.movePiece(piece, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 파란팀_차례일때_기물이_없는_위치에서_움직일수_없다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State blueTurn = new BlueTurn(board);

        Position source = new Position(Row.TWO, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);
        Piece piece = board.getPieceBy(source);

        assertThatThrownBy(() -> blueTurn.movePiece(piece, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}