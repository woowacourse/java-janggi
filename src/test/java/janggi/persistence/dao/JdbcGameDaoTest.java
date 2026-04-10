package janggi.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.dto.GameSessionDto;
import janggi.persistence.DatabaseInitializer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameDaoTest {

    private Connection connection;
    private JdbcGameDao gameDao;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.setAutoCommit(false);
        DatabaseInitializer.initialize(connection);
        gameDao = new JdbcGameDao();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("새로운 게임을 저장하면 생성된 ID를 반환하고 DB에 기록된다")
    void insertGame() throws SQLException {
        GameManager manager = createNewGameManager();
        long gameId = gameDao.insert(connection, manager);
        verifyGameTurn(gameId, "CHO");
    }

    @Test
    @DisplayName("게임의 턴을 변경하면 DB에 업데이트된다")
    void updateGame() throws SQLException {
        insertDummyGame(1L, "CHO");
        GameManager manager = createLoadedGameManager(1L);
        manager.switchTurn();
        gameDao.update(connection, manager);
        verifyGameTurn(1L, "HAN");
    }

    @Test
    @DisplayName("게임 ID로 세션 DTO를 정확히 조회한다")
    void findById() throws SQLException {
        insertDummyGame(1L, "CHO");
        GameSessionDto session = gameDao.findById(connection, 1L);
        assertThat(session.currentTurn()).isEqualTo("CHO");
    }

    private GameManager createNewGameManager() {
        return GameManager.loadGame(Players.from("testCho", "testHan"), Board.initialize(), null);
    }

    private GameManager createLoadedGameManager(long gameId) {
        return GameManager.loadGame(Players.from("testCho", "testHan"), Board.initialize(), gameId);
    }

    private void insertDummyGame(long gameId, String turn) throws SQLException {
        String sql = "INSERT INTO GAME (game_id, cho_player_name, han_player_name, current_turn, is_finished) VALUES (?, '초', '한', ?, FALSE)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.setString(2, turn);
            statement.executeUpdate();
        }
    }

    private void verifyGameTurn(long gameId, String expectedTurn) throws SQLException {
        String sql = "SELECT current_turn FROM GAME WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            verifyResultSetTurn(statement, expectedTurn);
        }
    }

    private void verifyResultSetTurn(PreparedStatement statement, String expectedTurn) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            assertThat(resultSet.getString("current_turn")).isEqualTo(expectedTurn);
        }
    }
}
