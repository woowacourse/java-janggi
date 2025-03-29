package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    @DisplayName("첫 턴은 청팀이다")
    void firstTurn() {
        Turn firstTurn = Turn.First();
        assertThat(firstTurn.getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    @DisplayName("청팀 다음 턴은 홍팀이다")
    void turnNextBlue() {
        Turn turn = new Turn(Team.BLUE);
        assertThat(turn.getNextTurn().getTeam()).isEqualTo(Team.RED);
    }

    @Test
    @DisplayName("홍팀 다음 턴은 청팀이다")
    void turnNextRed() {
        Turn turn = new Turn(Team.RED);
        assertThat(turn.getNextTurn().getTeam()).isEqualTo(Team.BLUE);
    }
}
