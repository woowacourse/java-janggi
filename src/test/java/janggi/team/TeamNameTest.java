package janggi.team;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamNameTest {
    @DisplayName("정상: 팀 이름이 일치하는지 확인")
    @Test
    void matchTeamName() {
        TeamName teamName = TeamName.CHO;

        assertThat(teamName.matchTeamName("초")).isTrue();
    }
}
