package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.PieceDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    @DisplayName("각 기물의 점수를 계산한다.")
    @Test
    void calculateScoreTest() {
        PieceDao pieceDao = new PieceDao();
        Board board = BoardGenerator.generate(pieceDao);

        assertThat(board.calculateChuScore()).isEqualTo(72.0);
        assertThat(board.calculateHanScore()).isEqualTo(73.5);
    }
}
