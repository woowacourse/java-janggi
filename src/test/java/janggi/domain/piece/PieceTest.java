package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.ChariotStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PieceTest {

    @DisplayName("해당 기물에 맞는 Camp를 주면 기물의 점수를 반환한다")
    @Test
    void getScoreIfCampMatches_GiveCorrectCamp_ReturnScore() {
        Camp camp = Camp.CHO;
        Piece piece = new Chariot(camp, new ChariotStrategy());

        assertThat(piece.getScoreIfCampMatches(camp)).isEqualTo(13);
    }

    @DisplayName("해당 기물에 맞는 Camp를 주면 0을 반환한다")
    @Test
    void getScoreIfCampMatches_GiveIncorrectCamp_ReturnZero() {
        Camp camp = Camp.CHO;
        Piece piece = new Chariot(camp, new ChariotStrategy());

        Camp differentCamp = Camp.HAN;

        assertThat(piece.getScoreIfCampMatches(differentCamp)).isEqualTo(0);
    }
}
