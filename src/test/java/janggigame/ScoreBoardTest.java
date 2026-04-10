package janggigame;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreBoardTest {
    @Test
    @DisplayName("현재 보드판의 점수를 계산한 점수판을 만들 수 있다.")
    void from_calculateScore_테스트_1() {
        Board board = new Board();
        board.placePieces(Side.CHO, Placement.LEFT_ELEPHANT);
        board.placePieces(Side.HAN, Placement.LEFT_ELEPHANT);

        ScoreBoard scoreBoard = ScoreBoard.from(board.getState());

        double choScore = scoreBoard.getScoreBySide(Side.CHO);
        double hanScore = scoreBoard.getScoreBySide(Side.HAN);
        assertThat(choScore).isEqualTo(72.0);
        assertThat(hanScore).isEqualTo(73.5);
    }
}
