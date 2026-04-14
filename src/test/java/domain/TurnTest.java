package domain;

import domain.board.Team;
import domain.game.Turn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    @DisplayName("턴 생성 시 초 팀으로 시작한다")
    void startsWithChuTeam() {
        // given
        Team team = Team.CHU;
        // when
        Turn turn = Turn.of(team);

        // then
        Assertions.assertEquals(team, turn.getTeam());
    }

    @Test
    @DisplayName("초 팀에서 턴을 전환하면 한 팀으로 바뀐다.")
    void switchesFromChuToHan() {
        // given
        Turn turn = Turn.of(Team.CHU);

        // when
        Team changeTeam = turn.change();

        // then
        Assertions.assertEquals(Team.HAN, changeTeam);
    }
}
