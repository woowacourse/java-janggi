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

class RedTurnTest {
    @Test
    void 빨간팀_차례일때_빨간색_기물을_움직일수_있다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State RedTurn = new RedTurn(board);

        Position source = new Position(Row.ONE, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);

        assertThatCode(() -> RedTurn.movePiece(PieceType.CHARIOT, source, target))
                .doesNotThrowAnyException();

    }

    @Test
    void 빨간팀_차례일때_기물이_없는_위치에서_움직일수_없다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        State RedTurn = new RedTurn(board);

        Position source = new Position(Row.TWO, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);

        assertThatThrownBy(() -> RedTurn.movePiece(PieceType.EMPTY, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
