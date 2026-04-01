package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    @DisplayName("턴 생성 시 초 팀으로 시작한다")
    void startsWithChuTeam() {
        // given
        // when
        Turn turn = Turn.of();

        // then
        Assertions.assertEquals(turn.getTeam(), Team.CHU);
    }

    @Test
    @DisplayName("초 팀에서 턴을 전환하면 한 팀으로 바뀐다.")
    void switchesFromChuToHan() {
        // given
        Turn turn = Turn.of();

        // when
        Team change = turn.change();

        // then
        Assertions.assertEquals(change, Team.HAN);
    }
}
