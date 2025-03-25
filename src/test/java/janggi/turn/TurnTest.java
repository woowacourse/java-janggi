package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void Turn을_생성한다() {
        // Given

        // When & Then
        Assertions.assertThatCode(() -> new Turn(Team.HAN))
                .doesNotThrowAnyException();
    }

    @Test
    void Turn을_초나라_팀으로_초기화한다() {
        // Given
        final Turn turn = Turn.initialize();

        // When & Then
        assertThat(turn.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 다음_턴으로_이동한다() {
        // Given
        final Turn turn = Turn.initialize();

        // When & Then
        assertThat(turn.moveNextTurn().getTeam()).isEqualTo(Team.HAN);
    }
}
