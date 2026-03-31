package janggi.domain.board;

import janggi.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnTest {

    @Test
    @DisplayName("현재 진영이 한일 때 진영을 바꾸면 초가 된다.")
    void testChangeTurn() {
        // given
        Turn turn = new Turn(Team.HAN);

        // when
        turn = turn.changeTurn();

        // then
        assertThat(turn.getTeam()).isEqualTo(Team.CHO);
    }
}
