package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.Team;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {
    @CsvSource(value = {"GREEN:false", "RED:true"}, delimiterString = ":")
    @ParameterizedTest
    void 같은_팀인지_여부를_반환한다(Team team, boolean expected) {
        // given
        Soldier soldier = new Soldier(Team.RED);

        // when
        boolean result = soldier.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @CsvSource(value = {"GREEN:true", "RED:false"}, delimiterString = ":")
    @ParameterizedTest
    void 다른_팀인지_여부를_반환한다(Team team, boolean expected) {
        // given
        Soldier soldier = new Soldier(Team.RED);

        // when
        boolean result = soldier.isDifferentTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
