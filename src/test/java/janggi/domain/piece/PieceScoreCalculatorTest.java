package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceScoreCalculatorTest {

    @Test
    @DisplayName("초나라 진영의 점수를 계산할 때 1.5점의 어드밴티지가 포함되어야 한다")
    void calculateChoScoreWithAdvantage() {
        List<Piece> pieces = List.of(new Piece(Side.CHO, PieceType.CHARIOT, "1"));
        double score = PieceScoreCalculator.calculateScore(Side.CHO, pieces);
        assertThat(score).isEqualTo(13.0 + 1.5);
    }

    @Test
    @DisplayName("한나라 진영의 점수를 계산할 때는 어드밴티지가 포함되지 않아야 한다")
    void calculateHanScoreWithoutAdvantage() {
        List<Piece> pieces = List.of(new Piece(Side.HAN, PieceType.CHARIOT, "1"));
        double score = PieceScoreCalculator.calculateScore(Side.HAN, pieces);
        assertThat(score).isEqualTo(13.0);
    }

    @Test
    @DisplayName("피아 식별을 통해 자신의 기물 점수만 합산하여야 한다")
    void calculateOnlyOwnPieceScore() {
        List<Piece> pieces = List.of(
                new Piece(Side.CHO, PieceType.CANNON, "1"),
                new Piece(Side.HAN, PieceType.GUARD, "1")
        );
        double score = PieceScoreCalculator.calculateOwnPieceScore(Side.CHO, pieces);
        assertThat(score).isEqualTo(7.0);
    }
}
