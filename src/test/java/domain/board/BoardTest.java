package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.formation.FormationType;
import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.error.PieceException;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

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

    @ParameterizedTest
    @CsvSource(value = {
            "CHO,72.0",
            "HAN,73.5"
    })
    void 남아있는_기물의_점수를_계산할_수_있다(Team team, double score) {
        Board board = BoardFactory.create(FormationType.DEFAULT, FormationType.DEFAULT);

        assertThat(board.scoreOf(team)).isEqualTo(score);
    }

    @Test
    void 이동_경로에_기물이_있으면_보드에서_이동할_수_없다() {
        Board board = BoardFactory.create(FormationType.DEFAULT, FormationType.DEFAULT);

        assertThatThrownBy(() -> board.move(Coordination.of(1, 10), Coordination.of(1, 6)))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 도착지의_기물이_아군이면_보드에서_이동할_수_없다() {
        Board board = BoardFactory.create(FormationType.DEFAULT, FormationType.DEFAULT);

        assertThatThrownBy(() -> board.move(Coordination.of(1, 10), Coordination.of(2, 10)))
                .isInstanceOf(PieceException.class);
    }
}
