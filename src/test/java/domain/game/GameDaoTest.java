package domain.game;

import database.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;

class GameDaoTest {
    private GameDao gameDao;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DbConnection.getInstance().getConnection();
        gameDao = new GameDao(connection);
        connection.setAutoCommit(false);
    }

    // AUTO_INCREMENT 값 리셋하는 메서드
    private void resetAutoIncrement() throws SQLException {
        String resetAutoIncrementSql = "ALTER TABLE player AUTO_INCREMENT = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(resetAutoIncrementSql)) {
            preparedStatement.executeUpdate();
        }
    }
}