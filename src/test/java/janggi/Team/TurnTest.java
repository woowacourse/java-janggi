package janggi.Team;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 현재_순서에_맞는_팀을_반환한다() {
        // Given
        final Turn turn = new Turn();

        // When & Then
        Assertions.assertThat(turn.getCurrentTeam())
                .isEqualTo(Team.CHO);
    }

    @Test
    void 현재_순서를_종료한다() {
        // Given
        final Turn turn = new Turn();

        // When
        turn.turnOver();

        // Then
        Assertions.assertThat(turn.getCurrentTeam())
                .isEqualTo(Team.HAN);
    }

    @Test
    void 현재_순서인_팀을_반환하고_다음_순서로_넘긴다() {
        // Given
        final Turn turn = new Turn();

        // When & Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(turn.getAndTurnOver())
                    .isEqualTo(Team.CHO);
            softAssertions.assertThat(turn.getCurrentTeam())
                    .isEqualTo(Team.HAN);
        });
    }
}