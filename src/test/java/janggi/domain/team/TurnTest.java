package janggi.domain.team;

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

    @Test
    void 규칙에_따른_후공이_맞는지_확인한다() {
        // Given
        final Turn turn = new Turn();
        final TeamType target = TeamType.HAN;

        // When & Then
        Assertions.assertThat(turn.isLastOrder(target))
                .isTrue();
    }
}
