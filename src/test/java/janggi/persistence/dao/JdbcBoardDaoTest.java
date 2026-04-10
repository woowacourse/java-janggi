package janggi.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
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

class JdbcBoardDaoTest {

    private Connection connection;
    private JdbcBoardDao boardDao;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.setAutoCommit(false);
        DatabaseInitializer.initialize(connection);
        boardDao = new JdbcBoardDao();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("보드 객체를 전달하면 32개의 기물이 DB에 일괄 삽입된다")
    void insertAll() throws SQLException {
        insertDummyGame(1L);
        Board board = Board.initialize();
        boardDao.insertAll(connection, 1L, board);
        verifyTotalPiecesCount(1L, 32);
    }

    @Test
    @DisplayName("특정 게임의 모든 기물 데이터를 DB에서 삭제한다")
    void deleteByGameId() throws SQLException {
        insertDummyGame(1L);
        insertDummyPiece(1L);
        boardDao.deleteByGameId(connection, 1L);
        verifyTotalPiecesCount(1L, 0);
    }

    @Test
    @DisplayName("게임 ID로 보드 도메인 객체를 완벽히 조립하여 반환한다")
    void findByGameId() throws SQLException {
        insertDummyGame(1L);
        insertDummyPiece(1L);
        Board board = boardDao.findByGameId(connection, 1L);
        assertThat(board.piecePosition()).hasSize(1);
    }

    private void insertDummyGame(long gameId) throws SQLException {
        String sql = "INSERT INTO GAME (game_id, cho_player_name, han_player_name, current_turn, is_finished) VALUES (?, '초', '한', 'CHO', FALSE)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private void insertDummyPiece(long gameId) throws SQLException {
        String sql = "INSERT INTO BOARD (game_id, side, piece_type, piece_number, row_index, column_index) VALUES (?, 'CHO', 'PALACE', '0', 8, 4)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private void verifyTotalPiecesCount(long gameId, int expectedCount) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BOARD WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            verifyCountResultSet(statement, expectedCount);
        }
    }

    private void verifyCountResultSet(PreparedStatement statement, int expectedCount) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            assertThat(resultSet.getInt(1)).isEqualTo(expectedCount);
        }
    }
}
