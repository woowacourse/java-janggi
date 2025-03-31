package janggi.board;

import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TurnTest {
    @Test
    @DisplayName("턴이 제대로 넘어가는지 확인")
    void turnOverTest() {
        //given
        Turn turn = new Turn(Team.CHO);
        //when
        Turn nextTurn = turn.turnOver();
        //then
        Assertions.assertThat(turn).isNotEqualTo(nextTurn);
    }
}
