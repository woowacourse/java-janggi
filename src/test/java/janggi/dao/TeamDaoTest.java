package janggi.dao;

import static janggi.domain.Team.BLUE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TeamDaoTest {

    private final TeamDao teamDao = new TeamDao();

    @Test
    void findTeamById() {

        // given
        int teamId = 2;

        // when
        assertThat(teamDao.findTeamById(teamId)).isEqualTo(BLUE);
    }

}
