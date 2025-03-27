package dao;

import static dao.DatabaseConfig.OPTION;
import static dao.DatabaseConfig.PASSWORD;
import static dao.DatabaseConfig.SERVER;
import static dao.DatabaseConfig.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.janggi.JanggiStatus;
import domain.janggi.Team;
import domain.janggi.Turn;
import dto.JanggiDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + "janggi_test" + OPTION, USERNAME,
                PASSWORD);
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
        void create() {
            // given
            String title = "title";
            JanggiStatus status = JanggiStatus.PROCESS;
            Turn turn =  new Turn(Team.GREEN);

            // when
            int janggiId = janggiDao.create(connection, title, status, turn);

            // then
            JanggiDto expected = new JanggiDto(janggiId, title, turn, status);
            assertThat(janggiDao.findJanggiDtoById(connection, janggiId))
                    .isEqualTo(expected);
        }

        @DisplayName("저장된 모든 장기 게임을 조회한다.")
        @Test
        void findAllJanggiDtos() {


        }
    }
}
