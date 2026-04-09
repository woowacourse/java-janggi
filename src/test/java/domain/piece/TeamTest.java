package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TeamTest {

    @ParameterizedTest
    @CsvSource({
            "CHO, 0.0",
            "HAN, 1.5",
            "NONE, 0.0"
    })
    void 진영은_덤_점수를_관리한다(Team team, double bonusScore) {
        assertThat(team.bonusScore()).isEqualTo(bonusScore);
    }
}
