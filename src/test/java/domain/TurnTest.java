package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TurnTest {

    @Nested
    class ValidCases {

        @DisplayName("턴을 넘겨 현재 팀을 바꾼다.")
        @ParameterizedTest
        @MethodSource("provideTeamCases")
        void change(
                Turn currentTurn,
                Team changedTeam
        ) {
            // when
            currentTurn.change();

            // then
            assertThat(currentTurn.currentTeam()).isEqualTo(changedTeam);
        }

        static Stream<Arguments> provideTeamCases() {
            // given
            return Stream.of(
                    Arguments.of(new Turn(Team.RED), Team.GREEN),
                    Arguments.of(new Turn(Team.GREEN), Team.RED)
            );
        }
    }
}
