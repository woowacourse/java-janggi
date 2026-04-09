package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Soldier;
import java.util.List;
import org.junit.jupiter.api.Test;

class ScoreCalculatorTest {
    private final ScoreCalculator calculator = new ScoreCalculator();

    @Test
    void 팀의_기물_점수를_합산한다() {
        double score = calculator.calculate(List.of(
                new Chariot(Team.CHO),
                new Cannon(Team.CHO),
                new Soldier(Team.CHO)
        ));

        assertThat(score).isEqualTo(22.0);
    }

    @Test
    void 궁은_점수가_0이다() {
        double score = calculator.calculate(List.of(new General(Team.CHO)));

        assertThat(score).isEqualTo(0.0);
    }

    @Test
    void 기물이_없으면_점수는_0이다() {
        double score = calculator.calculate(List.of());

        assertThat(score).isEqualTo(0.0);
    }

    @Test
    void 모든_기물의_점수를_합산한다() {
        double score = calculator.calculate(List.of(
                new Chariot(Team.CHO),
                new Chariot(Team.CHO),
                new Cannon(Team.CHO),
                new Cannon(Team.CHO),
                new Horse(Team.CHO),
                new Horse(Team.CHO),
                new Elephant(Team.CHO),
                new Elephant(Team.CHO),
                new Guard(Team.CHO),
                new Guard(Team.CHO),
                new Soldier(Team.CHO),
                new Soldier(Team.CHO),
                new Soldier(Team.CHO),
                new Soldier(Team.CHO),
                new Soldier(Team.CHO),
                new General(Team.CHO)
        ));

        assertThat(score).isEqualTo(72.0);
    }
}
