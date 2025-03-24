package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PlayerTest {

    @Test
    @DisplayName("이름과 팀으로 사용자를 생성한다")
    void createPlayer() {
        // given
        String name = "루키";
        TeamType teamType = TeamType.CHO;

        // when & then
        assertThatCode(() -> new Player(name, teamType))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "CHO, HAN, false",
            "HAN, CHO, false",
            "CHO, CHO, true"
    })
    @DisplayName("같은 팀 여부를 반환한다")
    void isSameTeamTest(TeamType playerTeam, TeamType otherTeam, boolean expected) {
        // given
        String name = "루키";
        TeamType teamType = playerTeam;
        Player player = new Player(name, teamType);

        // when
        boolean actual = player.isSameTeam(otherTeam);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
