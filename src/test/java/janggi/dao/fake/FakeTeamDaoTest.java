package janggi.dao.fake;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakeTeamDaoTest {

    private FakeTeamDao fakeTeamDao;

    @BeforeEach
    void setUp() {
        fakeTeamDao = new FakeTeamDao();
    }

    @Nested
    class FAKE_객체를_만들어_테스트한다 {

        @Test
        void 초기_팀을_추가한다() {
            // When
            fakeTeamDao.insertInitialTeam(TeamType.CHO);

            // Then
            assertThat(fakeTeamDao.getTeamTypes().size())
                    .isEqualTo(TeamType.values().length);
        }

        @Test
        void ID로_팀을_조회한다() {
            // Given
            final int id = 1;
            fakeTeamDao.insertInitialTeam(TeamType.CHO);

            // When & Then
            assertThat(fakeTeamDao.findTeamById(id))
                    .isEqualTo(fakeTeamDao.getTeamTypes().getFirst());
        }

        @Test
        void 팀을_모두_삭제한다() {
            // When
            fakeTeamDao.deleteAllTeamIfExists();

            // Then
            assertThat(fakeTeamDao.getTeamTypes().isEmpty())
                    .isTrue();
        }

        @Test
        void 해당_팀의_순서로_변경한다() {
            // Given
            final TeamType currentTeam = TeamType.HAN;
            fakeTeamDao.insertInitialTeam(TeamType.CHO);

            // When
            fakeTeamDao.updateTeamOrder(currentTeam);

            // Then
            assertThat(fakeTeamDao.findTeams().getFirst().isCurrent())
                    .isTrue();
        }
    }
}
