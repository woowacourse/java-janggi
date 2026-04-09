package janggi.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameRepositoryTest {

    private Connection connection;
    private JanggiGameRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.setAutoCommit(false);
        DatabaseInitializer.initialize(connection);
        repository = new JanggiGameRepository();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("새로운 게임을 저장하면 DB에 해당 게임의 초기 상태가 정확히 기록되어야 한다")
    void insertGame() throws SQLException {
        GameManager manager = createInitialGameManager();

        long gameId = repository.save(connection, manager);

        verifyGameInserted(gameId, "CHO", false);
    }

    @Test
    @DisplayName("게임의 턴을 변경하면 DB에 해당 변경 사항이 즉각 반영되어야 한다")
    void updateTurn() throws SQLException {
        insertDummyGame(1L, "CHO");
        GameManager manager = createTurnChangedGameManager();

        repository.save(connection, manager);

        verifyGameTurn(1L, "HAN");
    }

    private GameManager createInitialGameManager() {
        return GameManager.newGame(Players.from("testCho", "testHan"), Board.initialize());
    }

    private GameManager createTurnChangedGameManager() {
        GameManager manager = createInitialGameManager();
        manager.switchTurn();
        manager.assign(1);
        return manager;
    }

    private void insertDummyGame(long gameId, String turn) throws SQLException {
        String sql = "INSERT INTO GAME (game_id, cho_player_name, han_player_name, current_turn, is_finished) VALUES (?, '초', '한', ?, FALSE)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.setString(2, turn);
            statement.executeUpdate();
        }
    }

    private void verifyGameInserted(long gameId, String expectedTurn, boolean expectedFinished) throws SQLException {
        String sql = "SELECT current_turn, is_finished FROM GAME WHERE game_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, gameId);
            verifyGameResultSet(stmt, expectedTurn, expectedFinished);
        }
    }

    private void verifyGameTurn(long gameId, String expectedTurn) throws SQLException {
        verifyGameInserted(gameId, expectedTurn, false);
    }

    private void verifyGameResultSet(PreparedStatement stmt, String expectedTurn, boolean expectedFinished)
            throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            assertThat(rs.getString("current_turn")).isEqualTo(expectedTurn);
            assertThat(rs.getBoolean("is_finished")).isEqualTo(expectedFinished);
        }
    }
}
