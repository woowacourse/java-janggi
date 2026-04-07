package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ScoreBoardTest {

    @Test
    void 점수를_차감한다() {
        // given
        CampType cho = CampType.CHO;
        PieceRule pieceRule = PieceRule.SOLDIER;
        Piece piece = new Piece(pieceRule, cho);

        ScoreBoard scoreBoard = ScoreBoard.create();

        Double scoreBeforeMinus = scoreBoard.getScoreBoard().get(cho);
        double expectedScore = scoreBeforeMinus - pieceRule.getScore();
        // when
        scoreBoard.minusScore(cho, piece);
        // then
        assertThat(scoreBoard.getScoreBoard()).containsEntry(cho, expectedScore);
    }

    @Test
    void 보드_정보를_통해_점수판을_복구한다() {
        // given
        Map<Position, Piece> pieces = Map.of(
                new Position(0, 0), new Piece(PieceRule.CHARIOT, CampType.CHO),
                new Position(9, 8), new Piece(PieceRule.CHARIOT, CampType.HAN)
        );

        // when
        ScoreBoard scoreBoard = ScoreBoard.restore(pieces);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(scoreBoard.getScoreBoard().get(CampType.CHO)).isEqualTo(13.0);
            softly.assertThat(scoreBoard.getScoreBoard().get(CampType.HAN)).isEqualTo(14.5);
        });
    }
}
