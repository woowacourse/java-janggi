package domain.player;

import database.DbConnection;
import domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerDaoTest {
    private PlayerDao playerDao;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DbConnection.getInstance().getConnection();
        playerDao = new PlayerDao(connection);
        connection.setAutoCommit(false);
    }

    @DisplayName("플레이어 DB 저장 테스트")
    @Test
    public void test1() throws SQLException {
        // given
        String playerName = "레몬";
        Team team = Team.BLUE;
        int gameId = 100;

        // when
        Player savedPlayer = playerDao.insertPlayer(playerName, gameId, team);

        // then
        Assertions.assertThat(savedPlayer.getId()).isEqualTo(5);
        Assertions.assertThat(savedPlayer.getName()).isEqualTo("레몬");
        Assertions.assertThat(savedPlayer.getTeam()).isEqualTo(Team.BLUE);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.rollback();
        resetAutoIncrement();
        connection.setAutoCommit(true);
        connection.close();
    }

    private void resetAutoIncrement() throws SQLException {
        String resetAutoIncrementSql = "ALTER TABLE player AUTO_INCREMENT = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(resetAutoIncrementSql)) {
            preparedStatement.executeUpdate();
        }
    }
}
