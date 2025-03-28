package janggi.team;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 현재_순서에_맞는_팀을_반환한다() {
        // Given
        final Turn turn = new Turn();

        // When & Then
        Assertions.assertThat(turn.getCurrentTeam())
                .isEqualTo(TeamType.CHO);
    }

    @Test
    void 현재_순서를_종료한다() {
        // Given
        final Turn turn = new Turn();

        // When
        turn.turnOver();

        // Then
        Assertions.assertThat(turn.getCurrentTeam())
                .isEqualTo(TeamType.HAN);
    }
}
