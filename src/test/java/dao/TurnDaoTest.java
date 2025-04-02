package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {
    private static TurnDao turnDao;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        // given
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Turn (id TINYINT NOT NULL PRIMARY KEY, turn INT NOT NULL)");
            statement.execute("INSERT INTO Turn (id, turn) VALUES (1, 0)");
        }
        connection.close();

        turnDao = new TurnDao();
    }

    @Test
    @DisplayName("초기 턴의 값은 0이다.")
    void initTurnTest() {
        // when // then
        assertThat(turnDao.readTurnDB()).isEqualTo(0);
    }

    @Test
    @DisplayName("턴을 증가시키면 1씩 증가한다.")
    void incrementTurnTest() {
        // when
        turnDao.incrementTurnDB();

        // then
        assertThat(turnDao.readTurnDB()).isEqualTo(1);
    }

    @Test
    @DisplayName("턴을 리셋하면 0으로 초기화한다.")
    void resetTurnTest() {
        // when
        turnDao.incrementTurnDB();
        turnDao.resetTurnDB();

        // then
        assertThat(turnDao.readTurnDB()).isEqualTo(0);
    }
}