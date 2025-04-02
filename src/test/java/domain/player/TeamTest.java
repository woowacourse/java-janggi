package domain.player;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TeamTest {

    private static Stream<Arguments> currentTeamAndResult() {
        return Stream.of(
                Arguments.of(Team.HAN, Team.CHO),
                Arguments.of(Team.CHO, Team.HAN)
        );
    }

    @ParameterizedTest
    @MethodSource("currentTeamAndResult")
    void 현재_팀의_상대팀을_반환한다(final Team currentTeam, final Team expected) {
        // when
        Team result = Team.getOtherTeam(currentTeam);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
