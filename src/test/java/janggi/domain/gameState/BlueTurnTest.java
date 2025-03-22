package janggi.domain.gameState;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.BoardSetup;
import janggi.domain.board.Column;
import janggi.domain.board.InitialBoard;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

class BlueTurnTest {
    @Test
    void 파란팀_차례일때_파란색_기물을_움직일수_있다() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        State blueTurn = new BlueTurn(playingBoard);

        Position source = new Position(Row.ZERO, Column.ONE);
        Position target = new Position(Row.EIGHT, Column.ONE);

        assertThatCode(() -> blueTurn.movePiece(PieceType.CHARIOT, source, target))
                .doesNotThrowAnyException();

    }

    @Test
    void 파란팀_차례일때_파란색이_아닌_기물을_움직일수_없다() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        State blueTurn = new BlueTurn(playingBoard);

        Position source = new Position(Row.ONE, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);

        assertThatThrownBy(() -> blueTurn.movePiece(PieceType.CHARIOT, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 파란팀_차례일때_기물이_없는_위치에서_움직일수_없다() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        State blueTurn = new BlueTurn(playingBoard);

        Position source = new Position(Row.TWO, Column.ONE);
        Position target = new Position(Row.THREE, Column.ONE);

        assertThatThrownBy(() -> blueTurn.movePiece(PieceType.HORSE, source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
