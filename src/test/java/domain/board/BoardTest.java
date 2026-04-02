package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import domain.board.formation.FormationType;
import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        Coordination to = Coordination.of(column, row);

        Piece fromPiece = board.getBoard().get(from);

        assertThatCode(() -> board.move(from, to))
                .doesNotThrowAnyException();

        Map<Coordination, Piece> after = board.getBoard();
        Piece toPiece = after.get(to);
        Piece fromPieceAfterMove = after.get(from);

        assertThat(toPiece).isEqualTo(fromPiece);
        assertThat(fromPieceAfterMove).isInstanceOf(EmptyPiece.class);
    }
}
