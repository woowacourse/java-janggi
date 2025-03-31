package dao;

import static dao.DatabaseConfig.getOption;
import static dao.DatabaseConfig.getPassword;
import static dao.DatabaseConfig.getServer;
import static dao.DatabaseConfig.getUsername;
import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.JanggiStatus;
import domain.janggi.Team;
import domain.janggi.Turn;
import dto.JanggiDto;
import entity.JanggiEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiDaoTest {

    private final JanggiDao janggiDao = new JanggiDao();

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"
                + getServer()
                + "/" + "janggi_test"
                + getOption(), getUsername(), getPassword());
        janggiDao.deleteAll(connection);
    }

    @AfterEach
    void close() throws SQLException {
        janggiDao.deleteAll(connection);
        connection.close();
    }

    @Nested
    class ValidCases {

        @DisplayName("장기 게임을 저장하고 장기 게임 번호를 반환한다.")
        @Test
        void create() throws SQLException {
            // given
            String title = "title";
            JanggiStatus status = JanggiStatus.PROCESS;
            Turn turn = new Turn(Team.GREEN);

            // when
            int janggiId = janggiDao.create(connection, title, status, turn);

            // then
            JanggiEntity expected = new JanggiEntity(janggiId, title, turn.currentTeam(), status);
            assertThat(janggiDao.findJanggiEntityById(connection, janggiId))
                    .isEqualTo(expected);
        }

        @DisplayName("저장된 모든 장기 게임을 조회한다.")
        @Test
        void findAllJanggiEntities() throws SQLException {
            // given
            int firstId = janggiDao.create(connection, "first title", JanggiStatus.PROCESS, new Turn(Team.RED));
            int secondId = janggiDao.create(connection, "second title", JanggiStatus.FINISH, new Turn(Team.GREEN));
            int thirdId = janggiDao.create(connection, "third title", JanggiStatus.PROCESS, new Turn(Team.GREEN));

            // when
            List<JanggiEntity> result = janggiDao.findAllJanggiEntities(connection);

            // then
            assertThat(result).containsExactlyInAnyOrder(
                    new JanggiEntity(firstId, "first title", Team.RED, JanggiStatus.PROCESS),
                    new JanggiEntity(secondId, "second title", Team.GREEN, JanggiStatus.FINISH),
                    new JanggiEntity(thirdId, "third title", Team.GREEN, JanggiStatus.PROCESS)
            );
        }

        @DisplayName("장기 게임 번호를 통해 해당 장기 게임을 조회한다.")
        @Test
        void findJanggiEntityById() throws SQLException {
            // given
            String title = "title";
            JanggiStatus status = JanggiStatus.PROCESS;
            Team team = Team.GREEN;
            int janggiId = janggiDao.create(connection, title, status, new Turn(team));

            // when & then
            JanggiEntity expected = new JanggiEntity(janggiId, title, team, status);
            assertThat(janggiDao.findJanggiEntityById(connection, janggiId))
                    .isEqualTo(expected);
        }

        @DisplayName("장기 게임 번호를 통해 해당 장기 게임의 턴을 갱신한다.")
        @Test
        void updateTurnByJanggiId() throws SQLException {
            // given
            int janggiId = janggiDao.create(connection, "title", JanggiStatus.PROCESS, new Turn(Team.RED));

            // when
            janggiDao.updateTurnByJanggiId(connection, janggiId, Team.GREEN);

            // then
            assertThat(janggiDao.findJanggiEntityById(connection, janggiId).team())
                    .isEqualTo(Team.GREEN);
        }

        @DisplayName("데이터베이스의 모든 장기 게임을 삭제한다.")
        @Test
        void deleteAll() throws SQLException {
            // given
            janggiDao.create(connection, "first title", JanggiStatus.PROCESS, new Turn(Team.RED));
            janggiDao.create(connection, "second title", JanggiStatus.FINISH, new Turn(Team.GREEN));
            janggiDao.create(connection, "third title", JanggiStatus.PROCESS, new Turn(Team.GREEN));

            // when
            janggiDao.deleteAll(connection);

            // then
            assertThat(janggiDao.findAllJanggiEntities(connection)).isEmpty();
        }
    }
}
