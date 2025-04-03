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

class FinishedTest {

    private Board board;
    private State state;

    @BeforeEach
    void setUp() {
        board = new BoardFactory().createBoard();
        state = new Finished(board);
    }

    @Test
    void 게임이_끝난_후에는_기물을_움직일_수_없다() {
        Position source = new Position(Row.ZERO, Column.ONE);
        Position target = new Position(Row.EIGHT, Column.ONE);

        assertThatThrownBy(() -> state.movePiece(PieceType.CHARIOT, source, target))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void 게임_종료_상태임을_확인() {
        State finished = new Finished(board);

        assertThat(finished.isFinished()).isTrue();
    }

    @Test
    void 승자를_정상적으로_반환() {
        PieceColor pieceColor = state.determineWinner();

        assertThat(pieceColor).isEqualTo(PieceColor.RED);
    }
}
