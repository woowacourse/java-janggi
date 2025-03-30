package janggi.db;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Team;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {
    Connection connection = new Connection();

    @Disabled
    @DisplayName("팀이 턴 테이블에 제대로 들어가는지 확인합니다.")
    @Test
    void addTeamTest() {
        Team initTeam = Team.BLUE;

        TurnDao turnDao = new TurnDao(connection);
        turnDao.addTeam(initTeam);
    }

    @Disabled
    @DisplayName("팀이 턴 테이블에 제대로 들어가는지 확인합니다.")
    @Test
    void readTeamTest() {
        Team initTeam = Team.BLUE;

        TurnDao turnDao = new TurnDao(connection);
        Team currentTeam = turnDao.readTeam();

        assertThat(currentTeam).isEqualTo(initTeam);
    }
}
