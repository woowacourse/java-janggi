package dao;

import static org.assertj.core.api.Assertions.assertThat;

import infra.db.DbBootstrap;
import infra.db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRoomTest {
    private final GameRoom gameRoom = new GameRoom();

    @BeforeEach
    void setUp() {
        DbBootstrap.initialize();
    }

    @Test
    void 게임을_생성하면_DB에_초기_턴과_상태가_저장된다() throws SQLException {
        long gameId = gameRoom.createGame();

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT current_turn, status FROM game WHERE game_id = ?")) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("current_turn")).isEqualTo("CHO");
                assertThat(resultSet.getString("status")).isEqualTo("PROGRESS");
            }
        }
    }
}

