package game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import piece.Team;

class TurnTest {

    @Test
    void 현재_라운드가_어떤_팀의_차례인지_반환한다() {
        Turn turn = new Turn();

        assertThat(turn.getCurrnetTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 라운드가_한번_진행되면_어떤_팀의_차례인지_반환한다() {
        Turn turn = new Turn();
        turn.increaseRound();
        assertThat(turn.getCurrnetTeam()).isEqualTo(Team.RED);
    }

}
