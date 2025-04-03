package domain.state;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

class BlueTurnTest {
    @Test
    void 파란팀_차례일때_파란색_기물을_움직일수_있다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State blueTurn = new BlueTurn(board);

        Position source = new Position(Row.ZERO, Column.ONE);
        Position target = new Position(Row.EIGHT, Column.ONE);

        assertThatCode(() -> blueTurn.movePiece(PieceType.CHARIOT, source, target))
                .doesNotThrowAnyException();

    }

    @Test
    void 파란팀_차례일때_기물이_없는_위치에서_움직일수_없다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State blueTurn = new BlueTurn(board);

        Position source = new Position(Row.TWO, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);

        assertThatThrownBy(() -> blueTurn.movePiece(PieceType.EMPTY, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
