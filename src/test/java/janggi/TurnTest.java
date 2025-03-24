package janggi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnTest {

    @Test
    @DisplayName("짝수 턴은 초나라의 차례, 홀수 턴은 한나라의 차례다")
    void canProcessNextTurn() {
        // given
        Turn turn = Turn.create(); // 0

        // when
        Team oddTurn1 = turn.next(); // 1
        Team evenTurn1 = turn.next(); // 2
        Team oddTurn2 = turn.next(); // 3
        Team evenTurn2 = turn.next(); // 4

        // then
        assertThat(oddTurn1).isEqualTo(Team.HAN);
        assertThat(evenTurn1).isEqualTo(Team.CHO);
        assertThat(oddTurn2).isEqualTo(Team.HAN);
        assertThat(evenTurn2).isEqualTo(Team.CHO);

    }
}
