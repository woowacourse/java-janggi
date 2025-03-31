package janggi.team;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamScoreTest {

    @DisplayName("정상: 팀 점수 객체 생성 확인 (초)")
    @Test
    void createTeamScoreCho() {
        assertThat(TeamScore.from(TeamName.CHO)).isNotNull();
    }

    @DisplayName("정상: 팀 점수 객체 생성 확인 (한)")
    @Test
    void createTeamScoreHan() {
        assertThat(TeamScore.from(TeamName.HAN)).isNotNull();
    }
}
