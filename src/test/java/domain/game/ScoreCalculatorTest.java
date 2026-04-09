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
    void 초_팀의_기물_점수를_합산한다() {
        double score = calculator.calculate(List.of(
                new Chariot(Team.CHO),
                new Cannon(Team.CHO),
                new Soldier(Team.CHO)
        ), Team.CHO);

        assertThat(score).isEqualTo(22.0);
    }

    @Test
    void 한_팀은_1점5의_보너스_점수가_있다() {
        double score = calculator.calculate(List.of(
                new Chariot(Team.HAN),
                new Cannon(Team.HAN),
                new Soldier(Team.HAN)
        ), Team.HAN);

        assertThat(score).isEqualTo(23.5);
    }

    @Test
    void 궁은_점수가_0이다() {
        double score = calculator.calculate(List.of(new General(Team.CHO)), Team.CHO);

        assertThat(score).isEqualTo(0.0);
    }

    @Test
    void 초_팀_모든_기물의_점수를_합산한다() {
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
        ), Team.CHO);

        assertThat(score).isEqualTo(72.0);
    }
}
