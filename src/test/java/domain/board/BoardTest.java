package domain.board;

import domain.coordination.Coordination;
import domain.piece.Piece;
import domain.piece.error.PieceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class BoardTest {

    @ParameterizedTest
    @CsvSource(value = {
            "5,6",
            "4,7",
            "6,7"
    })
    void 기물을_움직였을_때_보드판에_실제로_저장된다(int column, int row) {
        Board board = BoardFactory.create("1", "1");
        Coordination from = Coordination.of(5, 7);
        Piece fromPiece = board.board.get(from);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> board.move(from, to)).doesNotThrowAnyException();
        assertThat(board.board.get(to)).isEqualTo(fromPiece);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,6", "1,5"})
    void 경로에_기물이_있다면_이동할_수_없다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        assertThatThrownBy(() -> board.move(Coordination.of(1, 10), Coordination.of(column, row)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,9", "1,8"})
    void 경로에_기물이_없다면_이동할_수_있다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        assertThatCode(() -> board.move(Coordination.of(1, 10), Coordination.of(column, row)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"1,7", "2,10"})
    void 도착지에_아군이_있다면_이동할_수_없다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        assertThatThrownBy(() -> board.move(Coordination.of(1, 10), Coordination.of(column, row)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,6", "2,5"})
    void 포_경유지에_기물이_없으면_이동할_수_없다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        assertThatThrownBy(() -> board.move(Coordination.of(2, 8), Coordination.of(column, row)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,1", "2,2"})
    void 포_경유지에_포가_있으면_이동할_수_없다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        assertThatThrownBy(() -> board.move(Coordination.of(2, 8), Coordination.of(column, row)))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 포_도착지에_포가_있으면_이동할_수_없다() {
        Board board = BoardFactory.create("1", "1");

        board.move(Coordination.of(1, 7), Coordination.of(2, 7));

        assertThatThrownBy(() -> board.move(Coordination.of(2, 8), Coordination.of(2, 3)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,5", "3,4"})
    void 마_경로에_기물이_있다면_이동할_수_없다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        board.move(Coordination.of(2, 1), Coordination.of(1, 3));

        assertThatThrownBy(() -> board.move(Coordination.of(1, 3), Coordination.of(column, row)))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 마_경로에_기물이_없다면_이동할_수_있다() {
        Board board = BoardFactory.create("1", "1");

        board.move(Coordination.of(2, 1), Coordination.of(1, 3));

        assertThatCode(() -> board.move(Coordination.of(1, 3), Coordination.of(2, 1)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"1,7", "6,8"})
    void 상_경로에_기물이_있다면_이동할_수_없다(int column, int row) {
        Board board = BoardFactory.create("1", "1");

        assertThatThrownBy(() -> board.move(Coordination.of(3, 10), Coordination.of(column, row)))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 상_경로에_기물이_없다면_이동할_수_있다() {
        Board board = BoardFactory.create("1", "1");

        board.move(Coordination.of(5, 7), Coordination.of(5, 6));

        assertThatCode(() -> board.move(Coordination.of(7, 10), Coordination.of(5, 7)))
                .doesNotThrowAnyException();
    }
}
