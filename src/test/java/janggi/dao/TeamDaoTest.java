package janggi.dao;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamDaoTest {

    private final TeamDao teamDao = new TeamDao();

    @DisplayName("초나라 팀 아이디를 찾는다.")
    @Test
    void findBlueTeamByIdTest() {

        // given
        final int teamId = 2;

        // when & then
        assertThat(teamDao.findTeamById(teamId)).isEqualTo(BLUE);
    }

    @DisplayName("한나라 팀 아이디를 찾는다.")
    @Test
    void findRedTeamByIdTest() {

        // given
        final int teamId = 1;

        // when & then
        assertThat(teamDao.findTeamById(teamId)).isEqualTo(RED);
    }

    @DisplayName("id로 팀을 못찾는 경우 예외가 발생한다.")
    @Test
    void findTeamByIdThrowExceptionTest() {

        // given
        final int teamId = 999;

        // when & then
        assertThatThrownBy(() -> teamDao.findTeamById(teamId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 팀입니다.");
    }

    @DisplayName("한나라의 id를 찾는다.")
    @Test
    void findRedIdByTeamNameTest() {

        // given

        // when & then
        assertThat(teamDao.findIdByTeam(RED)).isEqualTo(1);
    }

    @DisplayName("초나라의 id를 찾는다.")
    @Test
    void findBlueIdByTeamNameTest() {

        // given

        // when & then
        assertThat(teamDao.findIdByTeam(BLUE)).isEqualTo(2);
    }
}
