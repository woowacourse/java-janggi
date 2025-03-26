package janggi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnTest {

    @Test
    @DisplayName("짝수 턴은 초나라의 차례, 홀수 턴은 한나라의 차례다")
    void getCurrentTeamTurn() {
        // given
        Turn turn = Turn.start(); // 1

        // when
        Team oddTurn1 = turn.getCurrentTeam(); // 1
        turn.next();

        Team evenTurn1 = turn.getCurrentTeam(); // 2
        turn.next();

        Team oddTurn2 = turn.getCurrentTeam(); // 3
        turn.next();

        Team evenTurn2 = turn.getCurrentTeam(); // 4

        // then
        assertThat(oddTurn1).isEqualTo(Team.HAN);
        assertThat(evenTurn1).isEqualTo(Team.CHO);
        assertThat(oddTurn2).isEqualTo(Team.HAN);
        assertThat(evenTurn2).isEqualTo(Team.CHO);

    }
}
