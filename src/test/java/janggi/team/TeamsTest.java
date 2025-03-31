package janggi.team;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.BoardSetup;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamsTest {
    @DisplayName("정상: 팀 전환 확인")
    @Test
    public void switchTurn() {
        Team teamCho = TeamFactory.createTeam(BoardSetup.of(List.of("초", "HEHE")));
        Team teamHan = TeamFactory.createTeam(BoardSetup.of(List.of("한", "HEHE")));
        Teams teams = new Teams(teamCho, teamHan);

        Team switchedTeam = teams.switchTurn(teamHan);

        assertThat(switchedTeam).isEqualTo(teamCho);
    }

    @DisplayName("정상: 상대 팀 확인")
    @Test
    public void checkOpponent() {
        Team teamCho = TeamFactory.createTeam(BoardSetup.of(List.of("초", "HEHE")));
        Team teamHan = TeamFactory.createTeam(BoardSetup.of(List.of("한", "HEHE")));
        Teams teams = new Teams(teamCho, teamHan);

        Team opponentTeam = teams.checkOpponent(teamCho);

        assertThat(opponentTeam).isEqualTo(teamHan);
    }
}
