package domain.game;

import database.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private GameDao gameDao;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DbConnection.getInstance().getConnection();
        gameDao = new GameDao(connection);
        connection.setAutoCommit(false);
    }

    @DisplayName("게임 저장 테스트")
    @Test
    void test1() {

        //given
        int gameId = 52;

        //when
        Games games = gameDao.insertGame(gameId);
        // then
        Assertions.assertThat(games.getGameId()).isEqualTo(52);
        Assertions.assertThat(games.getGameStatus()).isEqualTo(null);
        Assertions.assertThat(games.getBluePlayerId()).isEqualTo(-1);
        Assertions.assertThat(games.getRedPlayerId()).isEqualTo(-1);
        Assertions.assertThat(games.getCurrentTurn()).isEqualTo(0);
    }

    // AUTO_INCREMENT 값 리셋하는 메서드
    private void resetAutoIncrement() throws SQLException {
        String resetAutoIncrementSql = "ALTER TABLE player AUTO_INCREMENT = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(resetAutoIncrementSql)) {
            preparedStatement.executeUpdate();
        }
    }
}