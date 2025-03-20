package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TeamTest {

    @Nested
    class ValidCases {

        @DisplayName("다음 순서의 팀을 반환한다.")
        @ParameterizedTest
        @MethodSource("provideTeamCases")
        void nextTeam(
            Team currentTeam,
            Team expectedTeam
        ) {
            // when & then
            assertThat(currentTeam.nextTeam()).isEqualTo(expectedTeam);
        }

        static Stream<Arguments> provideTeamCases() {
            // given
            return Stream.of(
                Arguments.of(Team.RED, Team.GREEN),
                Arguments.of(Team.GREEN, Team.RED)
            );
        }
    }
}
