package domain.board;

import domain.board.formation.FormationType;
import domain.coordination.Coordination;
import domain.piece.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class BoardTest {

    @ParameterizedTest
    @CsvSource(value = {
            "5,6",
            "4,7",
            "6,7"
    })
    void 기물을_움직였을_때_보드판에_실제로_저장된다(int column, int row) {
        Board board = BoardFactory.create(FormationType.DEFAULT, FormationType.DEFAULT);

        Coordination from = Coordination.of(5, 7);
        Piece fromPiece = board.board.get(from);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> board.move(from, to))
                .doesNotThrowAnyException();
        Piece toPiece = board.board.get(to);
        assertThat(toPiece).isEqualTo(fromPiece);
    }
}
