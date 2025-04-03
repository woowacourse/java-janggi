package domain.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetUpTest {

    private State state;

    @BeforeEach
    void setUp() {
        state = new SetUp();
    }

    @Test
    void 게임_시작_전에는_기물을_움직일_수_없다() {
        Position source = new Position(Row.ZERO, Column.ONE);
        Position target = new Position(Row.EIGHT, Column.ONE);

        assertThatThrownBy(() -> state.movePiece(PieceType.CHARIOT, source, target))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void startGame_호출하면_BlueTurn_상태가_된다() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();

        State newState = state.startGame(board, PieceColor.BLUE);

        assertThat(newState).isInstanceOf(BlueTurn.class);
    }
}
