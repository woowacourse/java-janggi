package janggi.dao;

import janggi.domain.team.TeamType;
import janggi.dto.TeamTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class TeamDaoTest {

    private TeamDao teamDao;

    @BeforeEach
    void setUp() {
        teamDao = new TeamDao(new TestConnectionManager());
    }

    @Nested
    class 쿼리에_따라_각_CRUD가_정상적으로_동작하는지_테스트한다 {

        @Test
        void 초기_팀을_추가한다() {
            assertThatNoException()
                    .isThrownBy(() -> teamDao.insertInitialTeam(TeamType.CHO));
        }

        @Disabled
        @Test
        void ID로_팀을_조회한다() {
            final int id = 1;
            final var team = teamDao.findTeamById(id);

            assertThat(team)
                    .isInstanceOf(TeamTypeDto.class);
        }


        @Test
        void 팀을_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(teamDao::deleteAllTeamIfExists);
        }


        @Test
        void 저장된_팀을_불러온다() {
            assertThatNoException()
                    .isThrownBy(teamDao::findTeams);
        }
        
        @Disabled
        @Test
        void 해당_팀의_순서로_변경한다() {
            // Given
            final TeamType currentTeam = TeamType.HAN;

            // When
            teamDao.updateTeamOrder(currentTeam);

            // Then
            assertThat(teamDao.findTeamById(3).isCurrent())
                    .isTrue();
        }

    }
}
