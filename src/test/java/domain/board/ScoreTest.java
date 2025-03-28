package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    void 한나라의_기물_점수를_계산() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        Score score = new Score(board);

        double pieceScore = score.calculatePieceScore(PieceColor.RED);

        assertThat(pieceScore).isEqualTo(73.5);
    }

    @Test
    void 초나라의_기물_점수를_계산() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        Score score = new Score(board);

        double pieceScore = score.calculatePieceScore(PieceColor.BLUE);

        assertThat(pieceScore).isEqualTo(72);
    }
}