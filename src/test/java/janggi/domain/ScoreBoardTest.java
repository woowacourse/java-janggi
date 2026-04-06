package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import org.junit.jupiter.api.Test;

class ScoreBoardTest {

    @Test
    void 점수를_차감한다() {
        // given
        CampType cho = CampType.CHO;
        PieceRule pieceRule = PieceRule.SOLDIER;
        Piece piece = new Piece(pieceRule, cho);

        ScoreBoard scoreBoard = new ScoreBoard();

        Double scoreBeforeMinus = scoreBoard.getScoreBoard().get(cho);
        double expectedScore = scoreBeforeMinus - pieceRule.getScore();
        // when
        scoreBoard.minusScore(cho, piece);
        // then
        assertThat(scoreBoard.getScoreBoard()).containsEntry(cho, expectedScore);
    }
}
