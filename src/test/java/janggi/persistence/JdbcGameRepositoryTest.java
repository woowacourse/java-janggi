package janggi.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.piece.PieceType;
import janggi.persistence.dao.JdbcBoardDao;
import janggi.persistence.dao.JdbcGameDao;
import janggi.persistence.repository.JdbcGameRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {

    private Connection connection;
    private JdbcGameRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.setAutoCommit(false);
        DatabaseInitializer.initialize(connection);
        repository = new JdbcGameRepository(new JdbcGameDao(), new JdbcBoardDao());
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

        GameManager gameManager = repository.save(connection, manager);

        verifyGameInserted(gameManager, "CHO", false);
    }

    @Test
    @DisplayName("게임의 턴을 변경하면 DB에 해당 변경 사항이 즉각 반영되어야 한다")
    void updateTurn() throws SQLException {
        insertDummyGame(1L, "CHO");
        GameManager manager = createTurnChangedGameManager();

        GameManager gameManager = repository.save(connection, manager);

        verifyGameTurn(gameManager, "HAN");
    }

    @Test
    @DisplayName("초기 보드 상태를 저장하면 32개의 기물이 정상적으로 DB에 기록되어야 한다")
    void insertBoard() throws SQLException {
        GameManager manager = createInitialGameManager();

        GameManager gameManager = repository.save(connection, manager);

        verifyTotalPiecesCount(gameManager, 32);
    }

    @Test
    @DisplayName("보드 상태를 업데이트하면 변경된 특정 기물의 좌표가 DB에 정확히 반영되어야 한다")
    void updateBoard() throws SQLException {
        insertDummyGame(1L, "CHO");
        insertDummyPiece(1L, "CHO", "PALACE", "0", 8, 4);
        GameManager manager = createUpdatedGameManager();

        repository.save(connection, manager);

        verifyPieceLocation(1L, "CHO", "PALACE", "0", 7, 4);
    }

    private GameManager createInitialGameManager() {
        return GameManager.newGame(Players.from("testCho", "testHan"), Board.initialize());
    }

    private GameManager createTurnChangedGameManager() {
        GameManager manager = createInitialGameManager();
        manager.switchTurn();
        manager.assign(1L);
        return manager;
    }

    private GameManager createUpdatedGameManager() {
        GameManager manager = createInitialGameManager();
        manager.assign(1L);
        Board board = manager.getBoard();
        Destinations destinations = PieceType.PALACE.determineDestinations(new Position(8, 4), Side.CHO, board);
        manager.movePiece(new Position(8, 4), new Position(7, 4), destinations);
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

    private void insertDummyPiece(long gameId, String side, String type, String num, int row, int col)
            throws SQLException {
        String sql = "INSERT INTO BOARD (game_id, side, piece_type, piece_number, row_index, column_index) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bindPieceParameters(statement, gameId, side, type, num, row, col);
            statement.executeUpdate();
        }
    }

    private void bindPieceParameters(PreparedStatement stmt, long gameId, String side, String type, String num, int row,
                                     int col) throws SQLException {
        stmt.setLong(1, gameId);
        stmt.setString(2, side);
        stmt.setString(3, type);
        stmt.setString(4, num);
        stmt.setInt(5, row);
        stmt.setInt(6, col);
    }

    private void verifyGameInserted(GameManager gameManager, String expectedTurn, boolean expectedFinished)
            throws SQLException {
        String sql = "SELECT current_turn, is_finished FROM GAME WHERE game_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, gameManager.getId());
            verifyGameResultSet(stmt, expectedTurn, expectedFinished);
        }
    }

    private void verifyGameTurn(GameManager gameManager, String expectedTurn) throws SQLException {
        verifyGameInserted(gameManager, expectedTurn, false);
    }

    private void verifyGameResultSet(PreparedStatement stmt, String expectedTurn, boolean expectedFinished)
            throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            assertThat(rs.getString("current_turn")).isEqualTo(expectedTurn);
            assertThat(rs.getBoolean("is_finished")).isEqualTo(expectedFinished);
        }
    }

    private void verifyTotalPiecesCount(GameManager gameManager, int expectedCount) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BOARD WHERE game_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, gameManager.getId());
            verifyCountResultSet(stmt, expectedCount);
        }
    }

    private void verifyCountResultSet(PreparedStatement stmt, int expectedCount) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            assertThat(rs.getInt(1)).isEqualTo(expectedCount);
        }
    }

    private void verifyPieceLocation(long gameId, String side, String type, String num, int row, int col)
            throws SQLException {
        String sql = "SELECT row_index, column_index FROM BOARD WHERE game_id = ? AND side = ? AND piece_type = ? AND piece_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            bindIdentifierParameters(stmt, gameId, side, type, num);
            verifyLocationResultSet(stmt, row, col);
        }
    }

    private void bindIdentifierParameters(PreparedStatement stmt, long gameId, String side, String type, String num)
            throws SQLException {
        stmt.setLong(1, gameId);
        stmt.setString(2, side);
        stmt.setString(3, type);
        stmt.setString(4, num);
    }

    private void verifyLocationResultSet(PreparedStatement stmt, int expectedRow, int expectedCol) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            assertThat(rs.getInt("row_index")).isEqualTo(expectedRow);
            assertThat(rs.getInt("column_index")).isEqualTo(expectedCol);
        }
    }
}
