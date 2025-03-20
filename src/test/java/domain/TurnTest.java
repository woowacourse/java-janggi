package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 현재_라운드가_어떤_팀의_차례인지_반환한다() {
        Turn turn = new Turn();

        Assertions.assertThat(turn.getCurrnetTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 라운드가_한번_진행되면_어떤_팀의_차례인지_반환한다() {
        Turn turn = new Turn();
        turn.increaseRound();
        Assertions.assertThat(turn.getCurrnetTeam()).isEqualTo(Team.RED);
    }
}
