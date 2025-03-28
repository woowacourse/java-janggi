package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.piece.TeamType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreCalculatorTest {

    @Test
    @DisplayName("초나라의 점수를 정확히 계산한다.")
    void calculateChoScoreTest() {
        List<Piece> pieces = List.of(
                new Guard(TeamType.HAN),
                new Guard(TeamType.HAN),
                new Guard(TeamType.CHO),
                new Guard(TeamType.CHO),
                new Chariot(TeamType.HAN),
                new Chariot(TeamType.HAN),
                new Chariot(TeamType.CHO),
                new Chariot(TeamType.CHO),
                new Elephant(TeamType.CHO),
                new Elephant(TeamType.CHO),
                new Elephant(TeamType.HAN)
        );
        ScoreCalculator calculator = new ScoreCalculator(pieces);
        double score = calculator.calculateChoScore();
        assertThat(score).isEqualTo(38);
    }

    @Test
    @DisplayName("각 한나라의 점수를 정확히 계산한다.")
    void calculateHanScoreTest() {
        List<Piece> pieces = List.of(
                new Guard(TeamType.HAN),
                new Guard(TeamType.HAN),
                new Guard(TeamType.CHO),
                new Guard(TeamType.CHO),
                new Chariot(TeamType.HAN),
                new Chariot(TeamType.HAN),
                new Chariot(TeamType.CHO),
                new Chariot(TeamType.CHO),
                new Elephant(TeamType.CHO),
                new Elephant(TeamType.CHO),
                new Elephant(TeamType.HAN)
        );
        ScoreCalculator calculator = new ScoreCalculator(pieces);
        double score = calculator.calculateHanScore();
        assertThat(score).isEqualTo(36.5);
    }
}
