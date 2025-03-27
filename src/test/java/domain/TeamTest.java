package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TeamTest {

    @ParameterizedTest
    @CsvSource({
            "CHO, HAN", "HAN, CHO"
    })
    void 적팀을_알_수_있다(Team team, Team expected) {
        // when
        Team enemy = team.getEnemy();
        // then
        assertThat(enemy).isEqualTo(expected);
    }
}