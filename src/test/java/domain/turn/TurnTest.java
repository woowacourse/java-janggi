package domain.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Nested
    class ValidCases {

        @DisplayName("턴을 진행하면 상대 팀으로 바뀐다.")
        @Test
        void proceed() {
            // given
            Turn turn = new Turn(Team.GREEN);

            // when
            turn.proceed();

            // then
            assertThat(turn.getCurrentTeam()).isEqualTo(Team.RED);
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("턴은 반드시 팀을 가져야 한다.")
        @Test
        void validateNotNull() {
            // given
            Team nullTeam = null;

            // when & then
            assertThatThrownBy(() -> new Turn(nullTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("턴은 반드시 팀을 가져야 합니다.");
        }
    }
}
